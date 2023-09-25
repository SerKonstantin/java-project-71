package hexlet.code;

import org.apache.commons.io.FilenameUtils;

import java.util.List;
import java.util.Map;

public class Differ {
    public static String generate(String filepath1, String filepath2) throws Exception {
        String defaultStyle = "stylish";
        return generate(filepath1, filepath2, defaultStyle);
    }

    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        String dataString1 = Util.readStringByFilepath(filepath1);
        String dataString2 = Util.readStringByFilepath(filepath2);
        String extension1 = FilenameUtils.getExtension(filepath1);
        String extension2 = FilenameUtils.getExtension(filepath2);

        Map<String, ?> data1 = Parser.mapOf(dataString1, extension1);
        Map<String, ?> data2 = Parser.mapOf(dataString2, extension2);

        List<Map<String, ?>> diffData = Comparator.compareMaps(data1, data2);

        return Formatter.generate(diffData, format);
    }
}
