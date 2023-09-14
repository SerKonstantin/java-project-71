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
                    bool1: true
                  - bool2: false
                  + bool2: Now I'm a string
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: [m, m, m]
                  + completely new string value: value2
                  - int to null: 45
                  + int to null: null
                  + newObj: {nestedKey=value, isNested=true}
                    null to null: null
                  - null to string: null
                  + null to string: Some string
                  - numbers: [5, 6, 7, 8]
                  + numbers: [4, 8, 15, 16]
                    obj1: {nestedKey=value, isNested=true}
                  - string to get rid off: value1
                    string1: I won't change
                  - string2: I'll change
                  + string2: I changed
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
                    bool1: true
                  - bool2: false
                  + bool2: Now I'm a string
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: [m, m, m]
                  + completely new string value: value2
                  - int to null: 45
                  + int to null: null
                  + newObj: {nestedKey=value, isNested=true}
                    null to null: null
                  - null to string: null
                  + null to string: Some string
                  - numbers: [5, 6, 7, 8]
                  + numbers: [4, 8, 15, 16]
                    obj1: {nestedKey=value, isNested=true}
                  - string to get rid off: value1
                    string1: I won't change
                  - string2: I'll change
                  + string2: I changed
                }""";
        assertEquals(actual, expected);
    }
}
