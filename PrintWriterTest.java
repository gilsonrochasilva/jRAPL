import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.UUID;

public class PrintWriterTest extends WriterTest {

    @Override
    public String getSigla() {
        return "PW";
    }

    @Override
    public Writer getWriterInstance() throws IOException {
        return new PrintWriter("/home/gilson/Documents/EstudoDirigido/writer-out/" + UUID.randomUUID().toString()); // 20mb
    }
}