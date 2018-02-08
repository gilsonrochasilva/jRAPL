import java.io.IOException;

public class InputStreamTest extends TestGroupImpl {

    public static void main(String[] args) throws IOException {
        InputStreamTest inputStreamTest = new InputStreamTest();
        inputStreamTest.start();
    }

    @Override
    public IEnergyTestCase[] getTests() {
        return new IEnergyTestCase[] {
            new BufferedInputStreamTest(),
            new FileInputStreamTest(),
            new PushbackInputStreamTest(),
            new StringBufferInputTest(),
            new ByteArrayInputStreamTest(),
            new LineNumberInputStreamTest()
        };
    }

    @Override
    public String getBarChartName() {
        return "test-%s-input-stream-20mb.csv";
    }

    @Override
    public String getDistributionChartName() {
        return "test-%s-input-stream-boxplot-20mb.csv";
    }
}
