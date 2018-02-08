import java.io.ByteArrayInputStream;
import java.io.FileReader;
import java.io.IOException;

public class ByteArrayInputStreamTest extends EnergyTestImpl implements IEnergyTestCase {

    private byte[] data;

    public ByteArrayInputStreamTest() {
        try {
            StringBuffer stringBuffer = new StringBuffer();
            FileReader fileReader = new FileReader(FILE_READER);
            int value = 0;
            while ((value = fileReader.read()) != -1) stringBuffer = stringBuffer.append(value);
            fileReader.close();

            data = stringBuffer.toString().getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void testImplementation() {
        try {
            ByteArrayInputStream reader = new ByteArrayInputStream(data);
            int value = 0, fake = 0;
            while ((value = reader.read()) != -1) fake = value;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "BAIS";
    }
}
