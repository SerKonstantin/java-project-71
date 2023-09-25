package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Stylish {
    public static String generate(List<Map<String, ?>> data) {
        StringBuilder sb = new StringBuilder("{\n");
        for (var dataLine: data) {
            var status = dataLine.get("status").toString();
            var key = dataLine.get("key");
            var value = dataLine.get("value");
            var value1 = dataLine.get("value1");
            var value2 = dataLine.get("value2");

            switch (status) {
                case "added" -> sb.append(String.format("  + %s: %s\n", key, value));
                case "removed" -> sb.append(String.format("  - %s: %s\n", key, value));
                case "unchanged" -> sb.append(String.format("    %s: %s\n", key, value));
                default -> {
                    sb.append(String.format("  - %s: %s\n", key, value1));
                    sb.append(String.format("  + %s: %s\n", key, value2));
                }
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
