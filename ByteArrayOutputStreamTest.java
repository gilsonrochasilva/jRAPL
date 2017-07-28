import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;

public class ByteArrayOutputStreamTest extends OutputStreamTest {

    @Override
    public String getSigla() {
        return "BAOS";
    }

    @Override
    OutputStream getOutputStreamInstance() throws FileNotFoundException {
        return new ByteArrayOutputStream();
    }
}