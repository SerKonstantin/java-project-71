package hexlet.code.formatters;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Plain {
    public static String generate(List<Map<String, ?>> data) {
        return data.stream()
                .map(dataLine -> {
                    var status = dataLine.get("status").toString();
                    var key = dataLine.get("key");
                    var value1 = valueToPlainStyle(dataLine.get("value1"));
                    var value2 = valueToPlainStyle(dataLine.get("value2"));

                    return switch (status) {
                        case "added" -> String.format("Property '%s' was added with value: %s", key, value2);
                        case "removed" -> String.format("Property '%s' was removed", key);
                        case "updated" ->
                                String.format("Property '%s' was updated. From %s to %s", key, value1, value2);
                        default -> null;
                    };
                })
                .filter(line -> line != null)
                .collect(Collectors.joining("\n"));
    }

    public static String valueToPlainStyle(Object value) {
        if (value instanceof List || value instanceof Map) {
            return "[complex value]";
        } else if (value instanceof String) {
            return "'" + value + "'";
        } else if (value == null) {
            return "null";
        } else {
            return value.toString();
        }
    }
}
