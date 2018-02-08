import java.io.IOException;

/**
 * Created by root on 29/06/17.
 */
public class WriterTest extends TestGroupImpl {

    public static void main(String[] args) throws IOException {
        WriterTest writerTest = new WriterTest();
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
                new CharArrayWriterTest()
            };
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String getBarChartName() {
        return "test-%s-writer-write-20mb.csv";
    }

    @Override
    public String getDistributionChartName() {
        return "test-%s-writer-write-boxplot-20mb.csv";
    }
}