package hexlet.code.formatters;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class Json {
    public static String generateJsonString(List<List<?>> data) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.writeValueAsString(data);
        } catch (Exception writingError) {
            throw new RuntimeException("Failed to form a String", writingError);
        }
    }
}
