import java.io.*;

public class LineNumberInputStreamTest extends InputStreamTest {

    public static void main(String[] args) throws IOException {
        LineNumberInputStreamTest lineNumberInputStreamTest = new LineNumberInputStreamTest();
        lineNumberInputStreamTest.testRead();

        EnergyCheckUtils.ProfileDealloc();

        System.out.println(lineNumberInputStreamTest.getResult());
    }

    @Override
    public String getSigla() {
        return "LNIS";
    }

    @Override
    public InputStream getInputStreamInstance() throws FileNotFoundException {
        return new LineNumberInputStream(new FileInputStream("/home/gilson/Documents/EstudoDirigido/largepagewithimages.html")); // 20mb
//        return new LineNumberInputStream(new FileInputStream("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-13")); // 140mb
//        return new LineNumberInputStream(new FileInputStream("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-14")); // 316mb
    }
}