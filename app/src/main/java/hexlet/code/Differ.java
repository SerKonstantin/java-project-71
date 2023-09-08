package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class Differ {
    public static String generate(String filepath1, String filepath2) {
        String json1 = fileToString(filepath1);
        String json2 = fileToString(filepath2);

        Map<String, ?> data1;
        Map<String, ?> data2;
        ObjectMapper mapper = new ObjectMapper();
        try {
            data1 = mapper.readValue(json1, new TypeReference<>() { });
            data2 = mapper.readValue(json2, new TypeReference<>() { });
        } catch (Exception mappingError) {
            throw new RuntimeException("Failed to read as json", mappingError);
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
}
