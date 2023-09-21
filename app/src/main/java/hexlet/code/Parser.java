package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public class Parser {
    public static Map<String, ?> mapOf(String dataString, String format) {
        switch (format) {
            case "json" -> {
                return parseJson(dataString);
            }
            case "yml", "yaml" -> {
                return parseYaml(dataString);
            }
            default -> throw new RuntimeException("Unsupported format");
        }
    }

    public static Map<String, ?> parseJson(String dataString) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(dataString, new TypeReference<>() { });
        } catch (Exception mappingError) {
            throw new RuntimeException("Failed to read as .json", mappingError);
        }
    }

    public static Map<String, ?> parseYaml(String dataString) {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            return mapper.readValue(dataString, new TypeReference<>() { });
        } catch (Exception mappingError) {
            throw new RuntimeException("Failed to read as .yml", mappingError);
        }
    }
}
