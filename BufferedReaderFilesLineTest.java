import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class BufferedReaderFilesLineTest extends EnergyTestImpl implements IEnergyTestCase {

    @Override
    public void testImplementation() {
        try {
            BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(FILE_READER));
            Stream<String> stream = bufferedReader.lines();
            stream.forEach((String line) -> { String fake = line; });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "BRFL";
    }
}
