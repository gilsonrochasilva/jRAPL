import java.io.IOException;

/**
 * Created by root on 29/06/17.
 */
public class ReadOnlyTest extends TestGroupImpl {

    public static void main(String[] args) throws IOException {
        ReadOnlyTest readerTest = new ReadOnlyTest();
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
            new StringReaderTest(),
            new BufferedInputStreamTest(),
            new FileInputStreamTest(),
            new PushbackInputStreamTest(),
            new StringBufferInputTest(),
            new ByteArrayInputStreamTest(),
            new LineNumberInputStreamTest(),
            new ReadFilesAllLinesTest(),
            new ReadFilesLineTest(),
            new BufferedReaderFilesLineTest(),
            new ScannerNextTest()
        };
    }

    @Override
    public String getBarChartName() {
        return "test-%s-reader-only-20mb.csv";
    }

    @Override
    public String getDistributionChartName() {
        return "test-%s-reader-only-boxplot-20mb.csv";
    }
}