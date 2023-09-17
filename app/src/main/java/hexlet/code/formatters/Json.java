package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class Json {
    public static String generateJsonString(List<Map<String, ?>> data) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception writingError) {
            throw new RuntimeException("Failed to form a String", writingError);
        }
    }
}
