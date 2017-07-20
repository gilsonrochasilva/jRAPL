import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by root on 19/07/17.
 */
public class Util {

    public static void saveReport(String fileName, String content) throws IOException {
        Path path = Paths.get("/home/gilson/Documents/git/github/gilsonrochasilva/jRAPL/data-analysis/inputs/" + fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
        }
    }
}
