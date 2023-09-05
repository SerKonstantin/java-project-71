package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "gendiff", version = "1.0", mixinStandardHelpOptions = true)
public class App implements Runnable {
    public static void main(String[] args) {
        App app = new App();
        new CommandLine(app).execute(args);
    }

    public void run() {
        var diff = Differ.generate("str1", "str2");
        System.out.println(diff);
    }

}
