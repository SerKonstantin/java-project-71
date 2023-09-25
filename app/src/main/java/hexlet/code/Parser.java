package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.util.Map;

public class Parser {
    public static Map<String, ?> mapOf(String dataString, String format) throws JsonProcessingException {
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

    public static Map<String, ?> parseJson(String dataString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(dataString, new TypeReference<>() { });
    }

    public static Map<String, ?> parseYaml(String dataString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        return mapper.readValue(dataString, new TypeReference<>() { });
    }
}
