import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class BufferedReaderTest extends EnergyTestImpl implements IEnergyTestCase {

    @Override
    public void testImplementation() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FILE_READER));
            int value = 0, fake = 0;
            while ((value = reader.read()) != -1) fake = value;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "BR";
    }
}