package hexlet.code;

import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.List;

public class Formatter {
    public static String generate(List<List<?>> data, String format) {
        switch (format) {
            case "stylish":
                return Stylish.generate(data);
            case "plain":
                return Plain.generate(data);
            default:
                throw new RuntimeException("Invalid format");
        }
    }
}
