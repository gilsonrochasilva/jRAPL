import java.io.IOException;

public class OutputStreamTest extends TestGroupImpl {

    public static void main(String[] args) {
        OutputStreamTest outputStreamTest = new OutputStreamTest();
        outputStreamTest.start();
    }

    @Override
    public IEnergyTestCase[] getTests() {
        try {
            return new IEnergyTestCase[] {
                new FileOutputStreamTest(),
                new ByteArrayOutputStreamTest(),
                new BufferedOutputStreamTest(),
                new PrintOutputStreamTest()
            };
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getBarChartName() {
        return "test-%s-output-stream-write-20mb.csv";
    }

    @Override
    public String getDistributionChartName() {
        return "test-%s-output-stream-write-boxplot-20mb.csv";
    }
}
