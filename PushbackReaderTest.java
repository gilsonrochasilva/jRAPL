import java.io.*;

public class PushbackReaderTest extends ReaderTest {

    public static void main(String[] args) throws IOException {
        PushbackReaderTest pushbackReaderTest = new PushbackReaderTest();
        pushbackReaderTest.testRead();

        EnergyCheckUtils.ProfileDealloc();

        System.out.println(pushbackReaderTest.getResult());
    }

    @Override
    public String getSigla() {
        return "PBR";
    }

    @Override
    public Reader getReaderInstance() throws FileNotFoundException {
        return new PushbackReader(new FileReader("/home/gilson/Documents/EstudoDirigido/largepagewithimages.html")); // 20mb
//        return new PushbackReader(new FileReader("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-13")); // 140mb
//        return new PushbackReader(new FileReader("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-14")); // 316mb
    }
}
