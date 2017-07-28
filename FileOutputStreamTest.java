import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class FileOutputStreamTest extends OutputStreamTest {

    @Override
    public String getSigla() {
        return "FOS";
    }

    @Override
    OutputStream getOutputStreamInstance() throws FileNotFoundException {
        return new FileOutputStream(new File("/home/gilson/Documents/EstudoDirigido/writer-out/" + UUID.randomUUID().toString())); // 20mb
    }
}