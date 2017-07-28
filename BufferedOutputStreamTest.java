import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class BufferedOutputStreamTest extends OutputStreamTest {

    @Override
    public String getSigla() {
        return "BOS";
    }

    @Override
    public OutputStream getOutputStreamInstance() throws FileNotFoundException {
        return new BufferedOutputStream(new FileOutputStream("/home/gilson/Documents/EstudoDirigido/largepagewithimages.html")); // 20mb
//        return new BufferedInputStream(new FileInputStream("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-13")); // 140mb
//        return new BufferedInputStream(new FileInputStream("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-14")); // 316mb
    }
}