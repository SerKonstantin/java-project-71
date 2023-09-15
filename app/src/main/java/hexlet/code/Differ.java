package hexlet.code;

import org.apache.commons.io.FilenameUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Objects.deepEquals;

public class Differ {
    public static List<List<Object>> generate(String filepath1, String filepath2) {
        String format1 = FilenameUtils.getExtension(filepath1);
        String format2 = FilenameUtils.getExtension(filepath2);
        if (!format1.equals(format2)) {
            throw new RuntimeException("Files have different format");
        }

        Map<String, ?> data1;
        Map<String, ?> data2;
        switch (format1) {
            case "json" -> {
                data1 = Parser.parseJson(filepath1);
                data2 = Parser.parseJson(filepath2);
            }
            case "yml" -> {
                data1 = Parser.parseYml(filepath1);
                data2 = Parser.parseYml(filepath2);
            }
            default -> throw new RuntimeException("Unsupported format");
        }

        SortedSet<String> combinedKeys = new TreeSet<>(data1.keySet());
        combinedKeys.addAll(data2.keySet());

        List<List<Object>> diffData = new ArrayList<>();
        for (var key: combinedKeys) {
            if (!data1.containsKey(key)) {
                diffData.add(createDiffLine("added", key, data1.get(key), data2.get(key)));
            } else if (!data2.containsKey(key)) {
                diffData.add(createDiffLine("removed", key,  data1.get(key), data2.get(key)));
            } else if (deepEquals(data1.get(key), (data2.get(key)))) {
                diffData.add(createDiffLine("unchanged", key, data1.get(key), data2.get(key)));
            } else {
                diffData.add(createDiffLine("updated", key, data1.get(key), data2.get(key)));
            }
        }

        return diffData;
    }

    // This method seem redundant, but it is necessary because the List.of() method does not allow null elements.
    public static List<Object> createDiffLine(String status, String key, Object value1, Object value2) {
        var diffLine = new ArrayList<>();
        diffLine.add(status);
        diffLine.add(key);
        diffLine.add(value1);
        diffLine.add(value2);
        return diffLine;
    }
}
