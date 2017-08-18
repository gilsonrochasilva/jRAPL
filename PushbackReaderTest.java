import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;

public class PushbackReaderTest  extends EnergyTestImpl implements IEnergyTestCase {

    @Override
    public void testImplementation() {
        try {
            PushbackReader reader = new PushbackReader(new FileReader(FILE_READER));
            int value = 0, fake = 0;
            while ((value = reader.read()) != -1) fake = value;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "PBR";
    }
}
