package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


class AppTest {
    private App app;
    private CommandLine cmd;

    @BeforeEach
    public void setup() {
        app = new App();
        cmd = new CommandLine(app);
    }

    @Test
    public void appTestJson() {
        String[] args = {"src/test/resources/testData0.json", "src/test/resources/testData1.json"};
        cmd.execute(args);
        String actual = app.call();
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
    public void appTestEmptyJson() {
        String path = "src/test/resources/testData2.json";
        String[] args = {path, path};
        cmd.execute(args);
        String actual = app.call();
        String expected = """
                {
                }""";
        assertEquals(actual, expected);
    }

    @Test
    public void appTestYml() {
        String[] args = {"src/test/resources/testData0.yml", "src/test/resources/testData1.yml"};
        cmd.execute(args);
        String actual = app.call();
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
    public void appTestInvalidFormat() {
        String[] args = {"src/test/resources/testData0.json", "src/test/resources/image.png"};
        cmd.execute(args);
        assertThrows(RuntimeException.class, () -> app.call());
    }

    @Test
    public void appTestInvalidPath() {
        String[] args = {"src/test/resources/testData0.json", "wrongPathToFile.json"};
        cmd.execute(args);
        assertThrows(RuntimeException.class, () -> app.call());
    }

    @Test
    public void appTestFakeFormat() {
        String[] args = {"src/test/resources/testData0.json", "src/test/resources/fake.json"};
        cmd.execute(args);
        assertThrows(RuntimeException.class, () -> app.call());
    }

    @Test
    public void appTestJsonWithPlainOutput() {
        String[] args = {"-f", "plain", "src/test/resources/testData0.json", "src/test/resources/testData1.json"};
        cmd.execute(args);
        String actual = app.call();
        String expected = """
                Property 'bool2' was updated. From false to 'Now I'm a string'
                Property 'chars2' was updated. From [complex value] to [complex value]
                Property 'completely new string value' was added with value: 'value2'
                Property 'int to null' was updated. From 45 to null
                Property 'newObj' was added with value: [complex value]
                Property 'null to string' was updated. From null to 'Some string'
                Property 'numbers' was updated. From [complex value] to [complex value]
                Property 'string to get rid off' was removed
                Property 'string2' was updated. From 'I'll change' to 'I changed'""";
        assertEquals(actual, expected);
    }

    @Test
    public void appTestYmlJsonWithJsonStringOutput() {
        String[] args = {"-f", "json", "src/test/resources/testData0.yml", "src/test/resources/testData1.json"};
        cmd.execute(args);
        String actual = app.call();
        String expected = "["
                + "{\"value2\":true,\"value1\":true,\"key\":\"bool1\",\"status\":\"unchanged\"},"
                + "{\"value2\":\"Now I'm a string\",\"value1\":false,\"key\":\"bool2\",\"status\":\"updated\"},"
                + "{\"value2\":[\"a\",\"b\",\"c\"],\"value1\":[\"a\",\"b\",\"c\"],\"key\":\"chars1\","
                    + "\"status\":\"unchanged\"},"
                + "{\"value2\":[\"m\",\"m\",\"m\"],\"value1\":[\"d\",\"e\",\"f\"],\"key\":\"chars2\","
                    + "\"status\":\"updated\"},"
                + "{\"value2\":\"value2\",\"value1\":null,\"key\":\"completely new string value\","
                    + "\"status\":\"added\"},"
                + "{\"value2\":null,\"value1\":45,\"key\":\"int to null\",\"status\":\"updated\"},"
                + "{\"value2\":{\"nestedKey\":\"value\",\"isNested\":true},\"value1\":null,\"key\":\"newObj\","
                    + "\"status\":\"added\"},"
                + "{\"value2\":null,\"value1\":null,\"key\":\"null to null\",\"status\":\"unchanged\"},"
                + "{\"value2\":\"Some string\",\"value1\":null,\"key\":\"null to string\",\"status\":\"updated\"},"
                + "{\"value2\":[4,8,15,16],\"value1\":[5,6,7,8],\"key\":\"numbers\",\"status\":\"updated\"},"
                + "{\"value2\":{\"nestedKey\":\"value\",\"isNested\":true},\"value1\":{\"nestedKey\":\"value\","
                    + "\"isNested\":true},\"key\":\"obj1\",\"status\":\"unchanged\"},"
                + "{\"value2\":null,\"value1\":\"value1\",\"key\":\"string to get rid off\",\"status\":\"removed\"},"
                + "{\"value2\":\"I won't change\",\"value1\":\"I won't change\",\"key\":\"string1\","
                    + "\"status\":\"unchanged\"},"
                + "{\"value2\":\"I changed\",\"value1\":\"I'll change\",\"key\":\"string2\",\"status\":\"updated\"}"
                + "]";
        assertEquals(actual, expected);
    }
}
