import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

public class PrintWriterTest extends EnergyTestImpl implements IEnergyTestCase {

    private String data;

    public PrintWriterTest() throws IOException {
        this.data = new String(Files.readAllBytes(Paths.get(FILE_READER)));
    }

    @Override
    public void testImplementation() {
        try {
            PrintWriter printWriter = new PrintWriter(OUT_WRITER + UUID.randomUUID().toString());
            printWriter.write(data);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "PW";
    }
}