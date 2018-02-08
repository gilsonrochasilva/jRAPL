import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ByteArrayOutputStreamTest extends EnergyTestImpl implements IEnergyTestCase {

    private byte[] data;

    public ByteArrayOutputStreamTest() throws IOException {
        this.data = new String(Files.readAllBytes(Paths.get(FILE_READER))).getBytes();
    }

    @Override
    public void testImplementation() {
        try {
            ByteArrayOutputStream fileWriter = new ByteArrayOutputStream();
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "BAOS";
    }
}