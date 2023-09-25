package hexlet.code;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {
    private App app;
    private CommandLine cmd;

    @BeforeEach
    public void setup() {
        app = new App();
        cmd = new CommandLine(app);
    }

    @Test
    public void appTestSuccessStatus() {
        String path = "src/test/resources/hexlet/code/differ_test_fixtures/testData1.json";
        String[] args = {path, path};
        int exitCode = cmd.execute(args);
        assertEquals(0, exitCode);
    }

    @Test
    public void appTestFailStatus() {
        String path = "src/test/resources/hexlet/code/differ_test_fixtures/image.png";
        String[] args = {path, path};
        int exitCode = cmd.execute(args);
        assertEquals(1, exitCode);
    }
}
