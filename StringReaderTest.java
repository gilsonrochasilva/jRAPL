import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

public class StringReaderTest extends EnergyTestImpl implements IEnergyTestCase {

    private StringBuffer data;

    public StringReaderTest() {
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
            StringReader reader = new StringReader(data.toString());
            int value = 0, fake = 0;
            while ((value = reader.read()) != -1) fake = value;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "SR";
    }
}
