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
        if (isPrimitive(value)) {
            return value.toString();
        } else if (value instanceof String) {
            return "'" + value + "'";
        } else if (value == null) {
            return "null";
        } else {
            return "[complex value]";
        }
    }

    public static boolean isPrimitive(Object value) {
        return value instanceof Byte
                || value instanceof Short
                || value instanceof Integer
                || value instanceof Long
                || value instanceof Float
                || value instanceof Double
                || value instanceof Boolean
                || value instanceof Character;
    }
}
