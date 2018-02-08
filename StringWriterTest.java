import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class StringWriterTest extends EnergyTestImpl implements IEnergyTestCase {

    private String data;

    public StringWriterTest() throws IOException {
        this.data = new String(Files.readAllBytes(Paths.get(FILE_READER)));
    }

    @Override
    public void testImplementation() {
        try {
            StringWriter stringWriter = new StringWriter();
            stringWriter.write(data);
            stringWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "SW";
    }
}