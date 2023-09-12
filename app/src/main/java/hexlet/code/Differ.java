package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        String dataString1 = fileToString(filepath1);
        String dataString2 = fileToString(filepath2);
        switch (format1) {
            case "json":
                data1 = parseJson(dataString1);
                data2 = parseJson(dataString2);
                break;
            case "yml":
                data1 = parseYml(dataString1);
                data2 = parseYml(dataString2);
            default:
                throw new RuntimeException("Unsupported format");
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

    public static String fileToString(String filepath) {
        Path path = Paths.get(filepath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new RuntimeException("File '" + path + "' doesn't exist");
        }
        try {
            return Files.readString(path);
        } catch (Exception readError) {
            throw new RuntimeException("Failed to read file '" + path + "'", readError);
        }
    }

    public static Map<String, ?> parseJson(String dataString) {
        Map<String, ?> data;
        ObjectMapper mapper = new ObjectMapper();
        try {
            return data = mapper.readValue(dataString, new TypeReference<>() { });
        } catch (Exception mappingError) {
            throw new RuntimeException("Failed to read as json", mappingError);
        }
    }

    // TODO
    public static Map<String, ?> parseYml(String dataString) {
        return null;
    }
}
