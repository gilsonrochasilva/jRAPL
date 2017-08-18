import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ReadFilesLineTest extends EnergyTestImpl implements IEnergyTestCase {

    @Override
    public void testImplementation() {
        try (Stream<String> stream = Files.lines(Paths.get(FILE_READER))) {
            stream.forEach((String line) -> { String fake = line; });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "RFL";
    }
}
