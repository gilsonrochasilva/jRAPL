import java.io.IOException;

public class ReadFileNioTest extends TestGroupImpl {

    public static void main(String[] args) throws IOException {
        ReadFileNioTest readFileNioTest = new ReadFileNioTest();
        readFileNioTest.start();
    }

    @Override
    public IEnergyTestCase[] getTests() {
        return new IEnergyTestCase[] {
            new ReadFilesAllLinesTest(),
            new ReadFilesLineTest(),
            new BufferedReaderFilesLineTest()
        };
    }

    @Override
    public String getBarChartName() {
        return "test-%s-read-nio-20mb.csv";
    }

    @Override
    public String getDistributionChartName() {
        return "test-%s-read-nio-boxplot-20mb.csv";
    }
}