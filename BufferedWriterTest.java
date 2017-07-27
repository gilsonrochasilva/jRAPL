import java.io.*;
import java.util.UUID;

public class BufferedWriterTest extends WriterTest {

    @Override
    public String getSigla() {
        return "BW";
    }

    @Override
    public Writer getWriterInstance() throws IOException {
        return new BufferedWriter(new FileWriter("/home/gilson/Documents/EstudoDirigido/writer-out/" + UUID.randomUUID().toString())); // 20mb
    }
}