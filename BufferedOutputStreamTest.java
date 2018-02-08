import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class BufferedOutputStreamTest extends EnergyTestImpl implements IEnergyTestCase {

    private byte[] data;

    public BufferedOutputStreamTest() throws IOException {
        this.data = new String(Files.readAllBytes(Paths.get(FILE_READER))).getBytes();
    }

    @Override
    public void testImplementation() {
        try {
            BufferedOutputStream fileWriter = new BufferedOutputStream(new FileOutputStream(new File(OUT_WRITER + UUID.randomUUID().toString())));
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "BOS";
    }
}