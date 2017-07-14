import java.io.*;

public class LineNumberReaderTest extends ReaderTest {

    public static void main(String[] args) throws IOException {
        LineNumberReaderTest lineNumberReaderTest = new LineNumberReaderTest();
        lineNumberReaderTest.testRead();

        EnergyCheckUtils.ProfileDealloc();

        System.out.println(lineNumberReaderTest.getResult());
    }

    @Override
    public String getSigla() {
        return "LNR";
    }

    @Override
    public Reader getReaderInstance() throws FileNotFoundException {
        return new LineNumberReader(new FileReader("/home/gilson/Documents/EstudoDirigido/largepagewithimages.html")); // 20mb
//        return new LineNumberReader(new FileReader("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-13")); // 140mb
//        return new LineNumberReader(new FileReader("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-14")); // 316mb
    }
}
