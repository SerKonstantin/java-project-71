package hexlet.code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import static java.util.Objects.deepEquals;

public class Comparator {
    public static List<Map<String, ?>> compareMaps(Map<String, ?> data1, Map<String, ?> data2) {
        SortedSet<String> combinedKeys = new TreeSet<>();
        combinedKeys.addAll(data1.keySet());
        combinedKeys.addAll(data2.keySet());

        List<Map<String, ?>> diffData = new ArrayList<>();

        for (var key: combinedKeys) {
            var diffLine = new HashMap<String, Object>();
            diffLine.put("key", key);
            if (!data1.containsKey(key)) {
                diffLine.put("status", "added");
                diffLine.put("value", data2.get(key));
            } else if (!data2.containsKey(key)) {
                diffLine.put("status", "removed");
                diffLine.put("value", data1.get(key));
            } else if (deepEquals(data1.get(key), (data2.get(key)))) {
                diffLine.put("status", "unchanged");
                diffLine.put("value", data1.get(key));
            } else {
                diffLine.put("status", "updated");
                diffLine.put("value1", data1.get(key));
                diffLine.put("value2", data2.get(key));
            }

            diffData.add(diffLine);
        }

        return diffData;
    }
}
