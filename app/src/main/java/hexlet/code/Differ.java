package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Objects.deepEquals;

public class Differ {
    public static String generate(String filepath1, String filepath2, String format) {
        Map<String, ?> data1 = Parser.mapOf(filepath1);
        Map<String, ?> data2 = Parser.mapOf(filepath2);

        SortedSet<String> combinedKeys = new TreeSet<>(data1.keySet());
        combinedKeys.addAll(data2.keySet());

        List<List<?>> diffData = new ArrayList<>();
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

        return Formatter.generate(diffData, format);
    }

    // This method seem redundant, but it is necessary because the List.of() method does not allow null elements.
    public static List<?> createDiffLine(String status, String key, Object value1, Object value2) {
        var diffLine = new ArrayList<>();
        diffLine.add(status);
        diffLine.add(key);
        diffLine.add(value1);
        diffLine.add(value2);
        return diffLine;
    }
}
