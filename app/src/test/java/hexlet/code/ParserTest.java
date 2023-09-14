package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ParserTest {
    @Test
    public void parseJsonTest() {
        var actual = Parser.parseJson("src/test/resources/testData0.json");

        Map<String, Object> expected = new HashMap<>();
        // Looks redundant, but Map.of() doesn't allow more than 10 arguments
        expected.put("string1", "I won't change");
        expected.put("string2", "I'll change");
        expected.put("string to get rid off", "value1");
        expected.put("bool1", true);
        expected.put("bool2", false);
        expected.put("numbers", List.of(5, 6, 7, 8));
        expected.put("chars1", List.of("a", "b", "c"));
        expected.put("chars2", List.of("d", "e", "f"));
        expected.put("int to null", 45);
        expected.put("null to string", null);
        expected.put("null to null", null);
        expected.put("obj1", Map.of("nestedKey", "value", "isNested", true));

        assertEquals(actual, expected);
    }

    @Test
    public void parseYmlTest() {
        var actual = Parser.parseYml("src/test/resources/testData0.yml");

        Map<String, Object> expected = new HashMap<>();
        // Looks redundant, but Map.of() doesn't allow more than 10 arguments
        expected.put("string1", "I won't change");
        expected.put("string2", "I'll change");
        expected.put("string to get rid off", "value1");
        expected.put("bool1", true);
        expected.put("bool2", false);
        expected.put("numbers", List.of(5, 6, 7, 8));
        expected.put("chars1", List.of("a", "b", "c"));
        expected.put("chars2", List.of("d", "e", "f"));
        expected.put("int to null", 45);
        expected.put("null to string", null);
        expected.put("null to null", null);
        expected.put("obj1", Map.of("nestedKey", "value", "isNested", true));

        assertEquals(actual, expected);
    }

    @Test
    public void noFileTest() {
        assertThrows(RuntimeException.class, () -> Parser.parseJson("wrongPathToFile"));
    }

    @Test
    public void nonStringTest() {
        // Or non supported format test
        assertThrows(RuntimeException.class, () -> Parser.parseJson("image.png"));
    }

    @Test
    public void fakeFileTest() {
        assertThrows(RuntimeException.class, () -> Parser.parseJson("fake.json"));
    }
}
