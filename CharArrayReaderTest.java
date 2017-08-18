import java.io.*;

public class CharArrayReaderTest extends EnergyTestImpl implements IEnergyTestCase {

    private StringBuffer data;

    public CharArrayReaderTest() {
        try {
            FileReader fileReader = new FileReader(FILE_READER);
            data = new StringBuffer();
            int value = 0;
            while ((value = fileReader.read()) != -1) data = data.append(value);
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testImplementation() {
        try {
            CharArrayReader reader = new CharArrayReader(data.toString().toCharArray());
            int value = 0, fake = 0;
            while ((value = reader.read()) != -1) fake = value;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "FR";
    }
}
