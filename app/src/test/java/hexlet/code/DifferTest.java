package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DifferTest {
    private static String stylishExpected;
    private static String plainExpected;
    private static String jsonExpected;

    @BeforeAll
    public static void readExpectedValues() {
        Path stylishPath = Path.of("src/test/resources/hexlet/code/differ_expected/stylish");
        try {
            stylishExpected = Files.readString(stylishPath);
        } catch (Exception readError) {
            throw new RuntimeException("Failed to read " + stylishPath, readError);
        }

        Path plainPath = Path.of("src/test/resources/hexlet/code/differ_expected/plain");
        try {
            plainExpected = Files.readString(plainPath);
        } catch (Exception readError) {
            throw new RuntimeException("Failed to read " + plainPath, readError);
        }

        Path jsonPath = Path.of("src/test/resources/hexlet/code/differ_expected/json");
        try {
            jsonExpected = Files.readString(jsonPath);
        } catch (Exception readError) {
            throw new RuntimeException("Failed to read " + jsonPath, readError);
        }
    }


    @ParameterizedTest
    @MethodSource("provideTestCases")
    public void differTest(String path1, String path2, String format, String expected) {
        String actual = Differ.generate(path1, path2, format);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> provideTestCases() {
        String json1 = "src/test/resources/hexlet/code/differ_inputs/testData1.json";
        String json2 = "src/test/resources/hexlet/code/differ_inputs/testData2.json";
        String yaml1 = "src/test/resources/hexlet/code/differ_inputs/testData1.yml";
        String yaml2 = "src/test/resources/hexlet/code/differ_inputs/testData2.yaml";
        return Stream.of(
                Arguments.of(json1, json2, "stylish", stylishExpected),
                Arguments.of(yaml1, yaml2, "stylish", stylishExpected),
                Arguments.of(json1, yaml2, "stylish", stylishExpected),
                Arguments.of(json1, json2, "plain", plainExpected),
                Arguments.of(yaml1, yaml2, "plain", plainExpected),
                Arguments.of(json1, yaml2, "plain", plainExpected),
                Arguments.of(json1, json2, "json", jsonExpected),
                Arguments.of(yaml1, yaml2, "json", jsonExpected),
                Arguments.of(json1, yaml2, "json", jsonExpected)
        );
    }

    @Test
    public void differTestWithTwoArgs() {
        String json1 = "src/test/resources/hexlet/code/differ_inputs/testData1.json";
        String json2 = "src/test/resources/hexlet/code/differ_inputs/testData2.json";
        String expected = Differ.generate(json1, json2, "stylish");
        String actual = Differ.generate(json1, json2);
        assertEquals(expected, actual);
    }

    @Test
    public void differTestEmptyJson() {
        String path = "src/test/resources/hexlet/code/differ_inputs/empty.json";
        String actual = Differ.generate(path, path);
        String expected = """
                {
                }""";
        assertEquals(actual, expected);
    }

    @Test
    public void differTestCannotRead() {
        String path = "src/test/resources/hexlet/code/differ_inputs/image.png";
        assertThrows(RuntimeException.class, () -> Differ.generate(path, path));
    }

    @Test
    public void differTestInvalidPath() {
        String path = "wrongPathToFile.json";
        assertThrows(RuntimeException.class, () -> Differ.generate(path, path));
    }

    @Test
    public void differTestFakeFormat() {
        String path = "src/test/resources/hexlet/code/differ_inputs/fake.json";
        assertThrows(RuntimeException.class, () -> Differ.generate(path, path));
    }

    @Test
    public void differTestInvalidFormat() {
        String path = "src/test/resources/hexlet/code/differ_inputs/testData1.json";
        String format = "wrongFormat";
        assertThrows(RuntimeException.class, () -> Differ.generate(path, path, format));
    }
}
