import java.io.IOException;

/**
 * Created by root on 29/06/17.
 */
public class WriterOnlyTest extends TestGroupImpl {

    public static void main(String[] args) throws IOException {
        WriterOnlyTest writerTest = new WriterOnlyTest();
        writerTest.start();
    }

    @Override
    public IEnergyTestCase[] getTests() {
        try {
            return new IEnergyTestCase[] {
                new BufferedWriterTest(),
                new FileWriterTest(),
                new StringWriterTest(),
                new PrintWriterTest(),
                new CharArrayWriterTest(),
                new FileOutputStreamTest(),
                new ByteArrayOutputStreamTest(),
                new BufferedOutputStreamTest(),
                new PrintOutputStreamTest(),
                new FileOutputStreamTest()
            };
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getBarChartName() {
        return "test-%s-writer-only-1mb.csv";
    }

    @Override
    public String getDistributionChartName() {
        return "test-%s-writer-only-boxplot-1mb.csv";
    }
}