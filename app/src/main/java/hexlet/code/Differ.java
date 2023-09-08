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
    public static String generate(String filepath1, String filepath2) throws Exception {
        String json1 = fileToString(filepath1);
        String json2 = fileToString(filepath2);


        ObjectMapper mapper = new ObjectMapper();
        Map<String, ?> data1 = mapper.readValue(json1, new TypeReference<>() {});
        Map<String, ?> data2 = mapper.readValue(json2, new TypeReference<>() {});

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

    public static String fileToString(String filepath) throws Exception {
        Path path = Paths.get(filepath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new Exception("File '" + path + "' doesn't exist");
        }
        return Files.readString(path);
    }
}
