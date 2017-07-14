import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class FileReaderTest extends ReaderTest {

    public static void main(String[] args) throws IOException {
        FileReaderTest fileReaderTest = new FileReaderTest();
        fileReaderTest.testRead();

        EnergyCheckUtils.ProfileDealloc();

        System.out.println(fileReaderTest.getResult());
    }

    @Override
    public String getSigla() {
        return "FR";
    }

    @Override
    public Reader getReaderInstance() throws FileNotFoundException {
        return new FileReader("/home/gilson/Documents/EstudoDirigido/largepagewithimages.html"); // 20mb
//        return new FileReader("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-13"); // 140mb
//        return new FileReader("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-14"); // 316mb
    }
}
