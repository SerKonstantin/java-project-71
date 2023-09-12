package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DifferTest {
    @Test
    public void generateTestJson() {
        String path1 = "src/test/resources/testData0.json";
        String path2 = "src/test/resources/testData1.json";
        String actual = Differ.generate(path1, path2);
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
        assertEquals(actual, expected);
    }

    @Test
    public void generatePartlyEmptyTestJson() {
        String path1 = "src/test/resources/testData0.json";
        String path2 = "src/test/resources/testData2.json";
        String actual = Differ.generate(path1, path2);
        String expected = """
                {
                  - follow: false
                  - host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                }""";
        assertEquals(actual, expected);
    }

    @Test
    public void generateFullyEmptyTestJson() {
        String path = "src/test/resources/testData2.json";
        String actual = Differ.generate(path, path);
        String expected = """
                {
                }""";
        assertEquals(actual, expected);
    }

    @Test
    public void generateInvalidFormatTestJson() {
        String path1 = "src/test/resources/testData0.json";
        String path2 = "src/test/resources/image.png";
        assertThrows(RuntimeException.class, () -> Differ.generate(path1, path2));
    }

    @Test
    public void generateTestYml() {
        String path1 = "src/test/resources/testData0.yml";
        String path2 = "src/test/resources/testData1.yml";
        String actual = Differ.generate(path1, path2);
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
        assertEquals(actual, expected);
    }
}
