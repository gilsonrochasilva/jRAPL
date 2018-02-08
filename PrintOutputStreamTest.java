import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class PrintOutputStreamTest extends EnergyTestImpl implements IEnergyTestCase {

    private byte[] data;

    public PrintOutputStreamTest() throws IOException {
        this.data = new String(Files.readAllBytes(Paths.get(FILE_READER))).getBytes();
    }

    @Override
    public void testImplementation() {
        try {
            PrintStream fileWriter = new PrintStream(new FileOutputStream(new File(OUT_WRITER + UUID.randomUUID().toString())));
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "POS";
    }
}