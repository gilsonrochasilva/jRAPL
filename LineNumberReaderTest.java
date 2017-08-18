import java.io.*;

public class LineNumberReaderTest extends EnergyTestImpl implements IEnergyTestCase {

    @Override
    public void testImplementation() {
        try {
            LineNumberReader reader = new LineNumberReader(new FileReader(FILE_READER));
            int value = 0, fake = 0;
            while ((value = reader.read()) != -1) fake = value;
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "LNR";
    }
}
