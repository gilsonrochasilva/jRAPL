import java.io.*;

public class StringBufferInputTest extends InputStreamTest {

    public static void main(String[] args) throws IOException {
        StringBufferInputTest stringBufferInputTest = new StringBufferInputTest();
        stringBufferInputTest.testRead();

        EnergyCheckUtils.ProfileDealloc();

        System.out.println(stringBufferInputTest.getResult());
    }

    @Override
    public String getSigla() {
        return "SBIS";
    }

    @Override
    InputStream getInputStreamInstance() throws FileNotFoundException {
        FileReader fileReader = new FileReader("/home/gilson/Documents/EstudoDirigido/largepagewithimages.html"); // 20mb
//        FileReader fileReader = new FileReader("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-13"); // 140mb
//        FileReader fileReader = new FileReader("/home/gilson/Documents/EstudoDirigido/server.log.2015-11-14"); // 316mb

        StringBuffer data = new StringBuffer();
        try {
            int value = 0;
            while ((value = fileReader.read()) != -1) data = data.append(value);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new StringBufferInputStream(data.toString());
    }
}
