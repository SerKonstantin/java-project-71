package hexlet.code;

import java.util.ArrayList;
import java.util.List;

public class Formatter {
    public static String generate(List<List<Object>> data, String format) {
        switch (format) {
            case "stylish":
                return generateStylish(data);
            default:
                throw new RuntimeException("Invalid format");
        }
    }

    public static String generateStylish(List<List<Object>> data) {
        StringBuilder sb = new StringBuilder("{\n");

        for (var dataLine: data) {
            if (dataLine.get(0).equals("added")) {
                sb.append("  + ")
                        .append(dataLine.get(1))
                        .append(": ")
                        .append(dataLine.get(3))
                        .append("\n");
            } else if (dataLine.get(0).equals("removed")) {
                sb.append("  - ")
                        .append(dataLine.get(1))
                        .append(": ")
                        .append(dataLine.get(2))
                        .append("\n");
            } else if (dataLine.get(0).equals("unchanged")) {
                sb.append("    ")
                        .append(dataLine.get(1))
                        .append(": ")
                        .append(dataLine.get(2))
                        .append("\n");
            } else {
                sb.append("  - ")
                        .append(dataLine.get(1))
                        .append(": ")
                        .append(dataLine.get(2))
                        .append("\n")
                        .append("  + ")
                        .append(dataLine.get(1))
                        .append(": ")
                        .append(dataLine.get(3))
                        .append("\n");
            }
        }
        sb.append("}");

        return sb.toString();
    }
}