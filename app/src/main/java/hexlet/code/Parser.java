package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class Parser {
    public static Map<String, ?> mapOf(String filepath) {
        String format = FilenameUtils.getExtension(filepath);
        switch (format) {
            case "json" -> {
                return parseJson(filepath);
            }
            case "yml" -> {
                return parseYml(filepath);
            }
            default -> throw new RuntimeException("Unsupported format");
        }
    }

    public static Map<String, ?> parseJson(String filepath) {
        String dataString = fileToString(filepath);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(dataString, new TypeReference<>() { });
        } catch (Exception mappingError) {
            throw new RuntimeException("Failed to read as .json", mappingError);
        }
    }

    public static Map<String, ?> parseYml(String filepath) {
        String dataString = fileToString(filepath);
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(dataString, new TypeReference<>() { });
        } catch (Exception mappingError) {
            throw new RuntimeException("Failed to read as .yml", mappingError);
        }
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
