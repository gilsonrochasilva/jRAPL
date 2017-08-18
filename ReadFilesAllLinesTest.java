import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class ReadFilesAllLinesTest extends EnergyTestImpl implements IEnergyTestCase {

    @Override
    public void testImplementation() {
        try {
            List<String> allLines = Files.readAllLines(Paths.get(FILE_READER));
            Stream<String> stream = allLines.stream();
            stream.forEach((String line) -> { String fake = line; });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "RFAL";
    }
}
