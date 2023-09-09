package hexlet.code;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DifferTest {
    String[] filepaths = {
        "src/test/resources/testData0.json",
        "src/test/resources/testData1.json",
        "src/test/resources/testData2.json",
        "src/test/resources/image.png",
        "src/test/resources/noFile.json"
    };

    @Test
    public void generateTest() {
        String actual = Differ.generate(filepaths[0], filepaths[1]);
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
    public void generatePartlyEmptyTest() {
        String actual = Differ.generate(filepaths[0], filepaths[2]);
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
    public void generateFullyEmptyTest() {
        String actual = Differ.generate(filepaths[2], filepaths[2]);
        String expected = """
                {
                }""";
        assertEquals(actual, expected);
    }

    @Test
    public void generateNoFileTest() {
        assertThrows(RuntimeException.class, () -> Differ.generate(filepaths[0], "wrongPathToFile"));
    }

    @Test
    public void generateInvalidFormatTest() {
        assertThrows(RuntimeException.class, () -> Differ.generate(filepaths[0], filepaths[3]));
    }
}