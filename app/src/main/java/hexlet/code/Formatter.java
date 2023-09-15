package hexlet.code;

import hexlet.code.formatters.Stylish;

import java.util.List;

public class Formatter {
    public static String generate(List<List<Object>> data, String format) {
        switch (format) {
            case "stylish":
                return Stylish.generate(data);

            default:
                throw new RuntimeException("Invalid format");
        }
    }
}
