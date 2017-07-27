import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class StringWriterTest extends WriterTest {

    @Override
    public String getSigla() {
        return "SW";
    }

    @Override
    public Writer getWriterInstance() throws IOException {
        return new StringWriter();
    }
}