import java.io.IOException;

/**
 * Created by root on 29/06/17.
 */
public class ReaderTest extends TestGroupImpl {

    public static void main(String[] args) throws IOException {
        ReaderTest readerTest = new ReaderTest();
        readerTest.start();
    }

    @Override
    public IEnergyTestCase[] getTests() {
        return new IEnergyTestCase[] {
            new BufferedReaderTest(),
            new LineNumberReaderTest(),
            new CharArrayReaderTest(),
            new PushbackReaderTest(),
            new FileReaderTest(),
            new StringReaderTest()
        };
    }

    @Override
    public String getBarChartName() {
        return "test-%s-reader-read-20mb.csv";
    }

    @Override
    public String getDistributionChartName() {
        return "test-%s-reader-read-boxplot-20mb.csv";
    }
}