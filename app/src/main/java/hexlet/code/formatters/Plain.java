package hexlet.code.formatters;

import java.util.List;

public class Plain {
    public static String generate(List<List<?>> data) {
        StringBuilder sb = new StringBuilder();
        for (var dataLine: data) {
            var status = dataLine.get(0).toString();
            var key = dataLine.get(1);
            var value1 = valueToPlainStyle(dataLine.get(2));
            var value2 = valueToPlainStyle(dataLine.get(3));

            switch (status) {
                case "added" -> sb.append(String.format("Property '%s' was added with value: %s\n", key, value2));
                case "removed" -> sb.append(String.format("Property '%s' was removed\n", key));
                case "updated" ->
                        sb.append(String.format("Property '%s' was updated. From %s to %s\n", key, value1, value2));
                default -> { }
            }
        }
        return sb.toString();
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
