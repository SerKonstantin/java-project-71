package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Objects.deepEquals;

public class Differ {
    public static List<List<?>> generate(String filepath1, String filepath2) {
        Map<String, ?> data1 = Parser.mapOf(filepath1);
        Map<String, ?> data2 = Parser.mapOf(filepath2);

        SortedSet<String> combinedKeys = new TreeSet<>(data1.keySet());
        combinedKeys.addAll(data2.keySet());

        List<List<?>> diffData = new ArrayList<>();
        for (var key: combinedKeys) {
            String status;
            if (!data1.containsKey(key)) {
                status = "added";
            } else if (!data2.containsKey(key)) {
                status = "removed";
            } else if (deepEquals(data1.get(key), (data2.get(key)))) {
                status = "unchanged";
            } else {
                status = "updated";
            }

            diffData.add(createDiffLine(status, key, data1.get(key), data2.get(key)));
        }
        // diffData may contain nulls
        return diffData;
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
