import java.io.*;

public class FileInputStreamTest extends InputStreamTest {

    public static void main(String[] args) throws IOException {
        FileInputStreamTest fileInputStreamTest = new FileInputStreamTest();
        fileInputStreamTest.testRead();

        EnergyCheckUtils.ProfileDealloc();

        System.out.println(fileInputStreamTest.getResult());
    }

    @Override
    public String getSigla() {
        return "FIS";
    }

    @Override
    InputStream getInputStreamInstance() throws FileNotFoundException {
        return new FileInputStream("/home/gilson/Documents/EstudoDirigido/largepagewithimages.html"); // 20mb
//        return new FileInputStream("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-13"); // 140mb
//        return new FileInputStream("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-14"); // 316mb
    }
}
