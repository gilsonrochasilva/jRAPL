import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class FileWriterTest extends EnergyTestImpl implements IEnergyTestCase {

    private String data;

    public FileWriterTest() throws IOException {
        this.data = new String(Files.readAllBytes(Paths.get(FILE_READER)));
    }

    @Override
    public void testImplementation() {
        try {
            FileWriter fileWriter = new FileWriter(OUT_WRITER + UUID.randomUUID().toString());
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "FW";
    }
}