import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.Writer;

public class CharArrayWriterTest extends WriterTest {

    @Override
    public String getSigla() {
        return "CAW";
    }

    @Override
    public Writer getWriterInstance() throws IOException {
        return new CharArrayWriter();
    }
}