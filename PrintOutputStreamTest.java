import java.io.*;

public class PrintOutputStreamTest extends OutputStreamTest {

    @Override
    public String getSigla() {
        return "POS";
    }

    @Override
    public OutputStream getOutputStreamInstance() throws FileNotFoundException {
        return new PrintStream(new FileOutputStream("/home/gilson/Documents/EstudoDirigido/largepagewithimages.html")); // 20mb
//        return new BufferedInputStream(new FileInputStream("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-13")); // 140mb
//        return new BufferedInputStream(new FileInputStream("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-14")); // 316mb
    }
}