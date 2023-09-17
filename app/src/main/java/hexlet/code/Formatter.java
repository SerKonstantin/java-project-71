package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.List;
import java.util.Map;

public class Formatter {
    public static String generate(List<Map<String, ?>> data, String format) {
        return switch (format) {
            case "stylish" -> Stylish.generate(data);
            case "plain" -> Plain.generate(data);
            case "json" -> Json.generateJsonString(data);
            default -> throw new RuntimeException("Invalid format");
        };
    }
}
