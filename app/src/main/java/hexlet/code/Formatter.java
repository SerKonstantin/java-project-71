package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.List;

public class Formatter {
    public static String generate(List<List<?>> data, String format) {
        return switch (format) {
            case "stylish" -> Stylish.generate(data);
            case "plain" -> Plain.generate(data);
            default -> throw new RuntimeException("Invalid format");
        };
    }
}
