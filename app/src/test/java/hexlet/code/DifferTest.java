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
    private static String json1 = makeFilepath("testData1.json");
    private static String json2 = makeFilepath("testData2.json");
    private static String yaml1 = makeFilepath("testData1.yml");
    private static String yaml2 = makeFilepath("testData2.yaml");

    public static String makeFilepath(String filename) {
        return FOLDER_PATH + "/" + filename;
    }

    @BeforeAll
    public static void readExpectedValues() throws Exception {
        stylishExpected = Util.readStringByFilename(FOLDER_PATH, "stylish_expected");
        plainExpected = Util.readStringByFilename(FOLDER_PATH, "plain_expected");
        jsonExpected = Util.readStringByFilename(FOLDER_PATH, "json_expected");
    }

    @ParameterizedTest
    @MethodSource("provideTestCases")
    public void differTest(String path1, String path2, String format, String expected) throws Exception {
        String actual = Differ.generate(path1, path2, format);
        assertEquals(expected, actual);
    }

    static Stream<Arguments> provideTestCases() {
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
    public void differTestWithTwoArgs() throws Exception {
        String expected = Differ.generate(json1, json2, "stylish");
        String actual = Differ.generate(json1, json2);
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
        assertThrows(Exception.class, () -> Differ.generate(json1, json2, format));
    }
}
