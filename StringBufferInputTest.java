import java.io.FileReader;
import java.io.IOException;
import java.io.StringBufferInputStream;

public class StringBufferInputTest extends EnergyTestImpl implements IEnergyTestCase {

    private String data;

    public StringBufferInputTest() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            FileReader fileReader = new FileReader(FILE_READER);
            int value = 0;
            while ((value = fileReader.read()) != -1) stringBuffer = stringBuffer.append(value);
            fileReader.close();

            data = stringBuffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testImplementation() {
        try {
            StringBufferInputStream reader = new StringBufferInputStream(data);
            int value = 0, fake = 0;
            while ((value = reader.read()) != -1) fake = value;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "SBIS";
    }
}
