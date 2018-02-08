import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class BufferedWriterTest extends EnergyTestImpl implements IEnergyTestCase {

    private String data;

    public BufferedWriterTest() throws IOException {
        this.data = new String(Files.readAllBytes(Paths.get(FILE_READER)));
    }

    @Override
    public void testImplementation() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(OUT_WRITER + UUID.randomUUID().toString()));
            bufferedWriter.write(data);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "BW";
    }
}