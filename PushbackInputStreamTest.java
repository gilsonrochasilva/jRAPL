import java.io.*;

public class PushbackInputStreamTest extends InputStreamTest {

    public static void main(String[] args) throws IOException {
        PushbackInputStreamTest pushbackInputStreamTest = new PushbackInputStreamTest();
        pushbackInputStreamTest.testRead();

        EnergyCheckUtils.ProfileDealloc();

        System.out.println(pushbackInputStreamTest.getResult());
    }

    @Override
    public String getSigla() {
        return "PBIS";
    }

    @Override
    InputStream getInputStreamInstance() throws FileNotFoundException {
        return new PushbackInputStream(new FileInputStream("/home/gilson/Documents/EstudoDirigido/largepagewithimages.html")); // 20mb
//        return new PushbackInputStream(new FileInputStream("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-13")); // 140mb
//        return new PushbackInputStream(new FileInputStream("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-14")); // 316mb
    }
}
