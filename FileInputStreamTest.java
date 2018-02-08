import java.io.FileInputStream;
import java.io.IOException;

public class FileInputStreamTest extends EnergyTestImpl implements IEnergyTestCase {

    @Override
    public void testImplementation() {
        try {
            FileInputStream reader = new FileInputStream(FILE_READER);
            int value = 0, fake = 0;
            while ((value = reader.read()) != -1) fake = value;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "FIS";
    }
}
