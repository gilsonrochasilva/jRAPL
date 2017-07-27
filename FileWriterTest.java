import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.UUID;

public class FileWriterTest extends WriterTest {

    @Override
    public String getSigla() {
        return "FW";
    }

    @Override
    public Writer getWriterInstance() throws IOException {
        return new FileWriter("/home/gilson/Documents/EstudoDirigido/writer-out/" + UUID.randomUUID().toString()); // 20mb
    }
}