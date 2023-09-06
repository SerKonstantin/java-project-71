package hexlet.code;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Differ {
    public static String generate(String filepath1, String filepath2) throws Exception {
        String json1 = fileToJson(filepath1);
        String json2 = fileToJson(filepath2);

        return "TODO-1";
    }

    public static String fileToJson(String filepath) throws Exception {
        Path path = Paths.get(filepath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new Exception("File '" + path + "' doesn't exist");
        }
        return Files.readString(path);
    }
}
