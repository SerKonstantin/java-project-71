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
                Property 'string2' was updated. From 'I'll change' to 'I changed'
                """;
        assertEquals(actual, expected);
    }

    @Test
    public void appTestYmlJsonWithJsonStringOutput() {
        String[] args = {"-f", "json", "src/test/resources/testData0.yml", "src/test/resources/testData1.json"};
        cmd.execute(args);
        String actual = app.call();
        String expected = "["
                + "[\"unchanged\",\"bool1\",true,true],"
                + "[\"updated\",\"bool2\",false,\"Now I'm a string\"],"
                + "[\"unchanged\",\"chars1\",[\"a\",\"b\",\"c\"],[\"a\",\"b\",\"c\"]],"
                + "[\"updated\",\"chars2\",[\"d\",\"e\",\"f\"],[\"m\",\"m\",\"m\"]],"
                + "[\"added\",\"completely new string value\",null,\"value2\"],"
                + "[\"updated\",\"int to null\",45,null],"
                + "[\"added\",\"newObj\",null,{\"nestedKey\":\"value\",\"isNested\":true}],"
                + "[\"unchanged\",\"null to null\",null,null],"
                + "[\"updated\",\"null to string\",null,\"Some string\"],"
                + "[\"updated\",\"numbers\",[5,6,7,8],[4,8,15,16]],"
                + "[\"unchanged\",\"obj1\",{\"nestedKey\":\"value\",\"isNested\":true},"
                                        + "{\"nestedKey\":\"value\",\"isNested\":true}],"
                + "[\"removed\",\"string to get rid off\",\"value1\",null],"
                + "[\"unchanged\",\"string1\",\"I won't change\",\"I won't change\"],"
                + "[\"updated\",\"string2\",\"I'll change\",\"I changed\"]"
                + "]";
        assertEquals(actual, expected);
    }
}
