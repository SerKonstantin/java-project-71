package hexlet.code.formatters;

import java.util.List;

public class Plain {
    public static String generate(List<List<?>> data) {
        StringBuilder sb = new StringBuilder();
        for (var dataLine: data) {
            var value1 = valueToPlainStyle(dataLine.get(2));
            var value2 = valueToPlainStyle(dataLine.get(3));

            if (dataLine.get(0).equals("added")) {
                sb.append("Property '")
                        .append(dataLine.get(1))
                        .append("' was added with value: ")
                        .append(value2)
                        .append("\n");
            } else if (dataLine.get(0).equals("removed")) {
                sb.append("Property '")
                        .append(dataLine.get(1))
                        .append("' was removed\n");
            } else if (dataLine.get(0).equals("updated")) {
                sb.append("Property '")
                        .append(dataLine.get(1))
                        .append("' was updated. From ")
                        .append(value1)
                        .append(" to ")
                        .append(value2)
                        .append("\n");
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
