/**
 * Created by root on 02/08/17.
 */
public class ScannerTest extends TestGroupImpl {

    public static void main(String[] args) {
        ScannerTest scannerTest = new ScannerTest();
        scannerTest.start();
    }

    @Override
    public IEnergyTestCase[] getTests() {
        return new IEnergyTestCase[] {
            new ScannerNextTest()
        };
    }

    @Override
    public String getBarChartName() {
        return "test-%s-scanner-next-20mb.csv";
    }

    @Override
    public String getDistributionChartName() {
        return "test-%s-scanner-next-boxplot-20mb.csv";
    }
}