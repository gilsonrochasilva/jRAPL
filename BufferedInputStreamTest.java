import java.io.*;

public class BufferedInputStreamTest extends InputStreamTest {

    public static void main(String[] args) throws IOException {
        BufferedInputStreamTest bufferedInputStreamTest = new BufferedInputStreamTest();
        bufferedInputStreamTest.testRead();

        EnergyCheckUtils.ProfileDealloc();

        System.out.println(bufferedInputStreamTest.getResult());
    }

    @Override
    public String getSigla() {
        return "BIS";
    }

    @Override
    public InputStream getInputStreamInstance() throws FileNotFoundException {
        return new BufferedInputStream(new FileInputStream("/home/gilson/Documents/EstudoDirigido/largepagewithimages.html")); // 20mb
//        return new BufferedInputStream(new FileInputStream("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-13")); // 140mb
//        return new BufferedInputStream(new FileInputStream("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-14")); // 316mb
    }
}