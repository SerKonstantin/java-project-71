package hexlet.code;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Differ {
    public static String generate(String filepath1, String filepath2) {
        String defaultStyle = "stylish";
        return generate(filepath1, filepath2, defaultStyle);
    }

    public static String generate(String filepath1, String filepath2, String format) {
        String dataString1 = readStringFromFile(filepath1);
        String dataString2 = readStringFromFile(filepath2);
        String extension1 = FilenameUtils.getExtension(filepath1);
        String extension2 = FilenameUtils.getExtension(filepath2);

        Map<String, ?> data1 = Parser.mapOf(dataString1, extension1);
        Map<String, ?> data2 = Parser.mapOf(dataString2, extension2);

        List<Map<String, ?>> diffData = Comparator.compareMaps(data1, data2);

        return Formatter.generate(diffData, format);
    }

    public static String readStringFromFile(String filepath) {
        Path path = Paths.get(filepath).toAbsolutePath().normalize();
        if (!Files.exists(path)) {
            throw new RuntimeException("File '" + path + "' doesn't exist");
        }
        try {
            return Files.readString(path);
        } catch (Exception readError) {
            throw new RuntimeException("Failed to read file '" + path + "'", readError);
        }
    }
}
