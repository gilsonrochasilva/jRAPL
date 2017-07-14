import java.io.*;

public class BufferedReaderTest extends ReaderTest {

    public static void main(String[] args) throws IOException {
        BufferedReaderTest bufferedReaderTest = new BufferedReaderTest();
        bufferedReaderTest.testRead();

        EnergyCheckUtils.ProfileDealloc();

        System.out.println(bufferedReaderTest.getResult());
    }

    @Override
    public String getSigla() {
        return "BR";
    }

    @Override
    public Reader getReaderInstance() throws FileNotFoundException {
        return new BufferedReader(new FileReader("/home/gilson/Documents/EstudoDirigido/largepagewithimages.html")); // 20mb
//        return new BufferedReader(new FileReader("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-13")); // 140mb
//        return new BufferedReader(new FileReader("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-14")); // 316mb
    }
}