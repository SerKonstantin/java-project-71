package hexlet.code;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DifferTest {
    private static final String FOLDER_PATH = "src/test/resources/hexlet/code/differ_test_fixtures";
    private static String stylishExpected;
    private static String plainExpected;
    private static String jsonExpected;
    private static final String JSON_1 = makeFilepath("testData1.json");
    private static final String JSON_2 = makeFilepath("testData2.json");
    private static final String YAML_1 = makeFilepath("testData1.yml");
    private static final String YAML_2 = makeFilepath("testData2.yaml");

    public static String makeFilepath(String filename) {
        return FOLDER_PATH + "/" + filename;
    }

    @BeforeAll
    public static void readExpectedValues() throws Exception {
        stylishExpected = Util.readStringByFilepath(("stylish_expected"));
        plainExpected = Util.readStringByFilepath(makeFilepath("plain_expected"));
        jsonExpected = Util.readStringByFilepath(makeFilepath("json_expected"));
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    public void differTest(String path1, String path2) throws Exception {
        assertEquals(stylishExpected, Differ.generate(path1, path2));
        assertEquals(stylishExpected, Differ.generate(path1, path2, "stylish"));
        assertEquals(plainExpected, Differ.generate(path1, path2, "plain"));
        assertEquals(jsonExpected, Differ.generate(path1, path2, "json"));
    }

    static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of(JSON_1, JSON_2),
                Arguments.of(YAML_1, YAML_2),
                Arguments.of(JSON_1, YAML_2)
        );
    }

    @Test
    public void differTestWithTwoArgs() throws Exception {
        String expected = Differ.generate(JSON_1, JSON_2, "stylish");
        String actual = Differ.generate(JSON_1, JSON_2);
        assertEquals(expected, actual);
    }

    @Test
    public void differTestEmptyJson() throws Exception {
        String path = makeFilepath("empty.json");
        String actual = Differ.generate(path, path);
        String expected = """
                {
                }""";
        assertEquals(actual, expected);
    }

    @Test
    public void differTestCannotRead() {
        String path = makeFilepath("image.png");
        assertThrows(Exception.class, () -> Differ.generate(path, path));
    }

    @Test
    public void differTestInvalidPath() {
        String path = "wrongPathToFile.json";
        assertThrows(Exception.class, () -> Differ.generate(path, path));
    }

    @Test
    public void differTestFakeFormat() {
        String path = makeFilepath("fake.json");
        assertThrows(Exception.class, () -> Differ.generate(path, path));
    }

    @Test
    public void differTestInvalidFormat() {
        String format = "wrongFormat";
        assertThrows(Exception.class, () -> Differ.generate(JSON_1, JSON_2, format));
    }
}
