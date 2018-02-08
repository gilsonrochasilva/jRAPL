import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class ScannerNextTest extends EnergyTestImpl implements IEnergyTestCase {

    @Override
    public void testImplementation() {
        try {
            Scanner scanner = new Scanner(new File(FILE_READER));
            while (scanner.hasNext()) {
                String token = scanner.next();
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getId() {
        return "SCN";
    }
}