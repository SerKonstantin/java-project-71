package hexlet.code.formatters;

import java.util.List;

public class Stylish {
    public static String generate(List<List<?>> data) {
        StringBuilder sb = new StringBuilder("{\n");
        for (var dataLine: data) {
            var status = dataLine.get(0).toString();
            var key = dataLine.get(1);
            var value1 = valueToString(dataLine.get(2));
            var value2 = valueToString(dataLine.get(3));

            switch (status) {
                case "added" -> sb.append(String.format("  + %s: %s\n", key, value2));
                case "removed" -> sb.append(String.format("  - %s: %s\n", key, value1));
                case "unchanged" -> sb.append(String.format("    %s: %s\n", key, value1));
                default -> {
                    sb.append(String.format("  - %s: %s\n", key, value1));
                    sb.append(String.format("  + %s: %s\n", key, value2));
                }
            }
        }
        sb.append("}");
        return sb.toString();
    }

    // Need custom .toString to deal with nulls
    public static String valueToString(Object value) {
        if (value == null) {
            return "null";
        } else {
            return value.toString();
        }
    }
}
