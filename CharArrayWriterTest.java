import java.io.CharArrayWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CharArrayWriterTest extends EnergyTestImpl implements IEnergyTestCase {

    private String data;

    public CharArrayWriterTest() throws IOException {
        this.data = new String(Files.readAllBytes(Paths.get(FILE_READER)));
    }

    @Override
    public void testImplementation() {
        try {
            CharArrayWriter charArrayWriter = new CharArrayWriter();
            charArrayWriter.write(data);
            charArrayWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "CAW";
    }
}