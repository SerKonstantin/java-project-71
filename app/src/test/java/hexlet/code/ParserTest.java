package hexlet.code;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class ParserTest {
    @Test
    public void parseJsonTest() {
        var actual = Parser.parseJson("src/test/resources/testData0.json");
        var expected = Map.of(
                 "host", "hexlet.io",
                 "timeout", 50,
                 "proxy", "123.234.53.22",
                 "follow", false
         );
        assertEquals(actual, expected);
    }

    @Test
    public void parseYmlTest() {
        var actual = Parser.parseYml("src/test/resources/testData0.yml");
        var expected = Map.of(
                "host", "hexlet.io",
                "timeout", 50,
                "proxy", "123.234.53.22",
                "follow", false
        );
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
