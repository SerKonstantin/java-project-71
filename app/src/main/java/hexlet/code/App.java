package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.util.concurrent.Callable;

@Command(name = "gendiff", version = "0.0.1", mixinStandardHelpOptions = true)
public class App implements Callable {
    public static void main(String[] args) {
        App app = new App();
        int exitCode = new CommandLine(app).execute(args);
        System.exit(exitCode);
    }

    @Parameters(index = "0", description = "path to first file", paramLabel = "filepath1")
    private String filepath1;

    @Parameters(index = "1", description = "path to second file", paramLabel = "filepath2")
    private String filepath2;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]",
            paramLabel = "format", defaultValue = "stylish")
    private String format;

    @Override
    public final Integer call() {
        try {
            String diff = Differ.generate(filepath1, filepath2, format);
            System.out.println(diff);
            return 0;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 1;
        }
    }
}
