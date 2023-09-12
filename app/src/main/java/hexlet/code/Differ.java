package hexlet.code;

import org.apache.commons.io.FilenameUtils;

import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Differ {
    public static String generate(String filepath1, String filepath2) {
        String format1 = FilenameUtils.getExtension(filepath1);
        String format2 = FilenameUtils.getExtension(filepath2);
        if (!format1.equals(format2)) {
            throw new RuntimeException("Files have different format");
        }

        Map<String, ?> data1;
        Map<String, ?> data2;
        switch (format1) {
            case "json" -> {
                data1 = Parser.parseJson(filepath1);
                data2 = Parser.parseJson(filepath2);
            }
            case "yml" -> {
                data1 = Parser.parseYml(filepath1);
                data2 = Parser.parseYml(filepath2);
            }
            default -> throw new RuntimeException("Unsupported format");
        }

        SortedSet<String> combinedKeys = new TreeSet<>(data1.keySet());
        combinedKeys.addAll(data2.keySet());

        StringBuilder sb = new StringBuilder("{\n");
        for (var key: combinedKeys) {
            if (!data1.containsKey(key)) {
                sb.append("  + ")
                        .append(key)
                        .append(": ")
                        .append(data2.get(key))
                        .append("\n");
            } else if (!data2.containsKey(key)) {
                sb.append("  - ")
                        .append(key)
                        .append(": ")
                        .append(data1.get(key))
                        .append("\n");
            } else if (data1.get(key).equals(data2.get(key))) {
                sb.append("    ")
                        .append(key)
                        .append(": ")
                        .append(data1.get(key))
                        .append("\n");
            } else {
                sb.append("  - ")
                        .append(key)
                        .append(": ")
                        .append(data1.get(key))
                        .append("\n")
                        .append("  + ")
                        .append(key)
                        .append(": ")
                        .append(data2.get(key))
                        .append("\n");
            }
        }
        sb.append("}");

        return sb.toString();
    }
}
