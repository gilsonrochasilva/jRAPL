import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by root on 29/06/17.
 */
abstract class WriterTest {

    private static int ITERATIONS = 10;
    private static int SAMPLES = 3;
    private static int SAMPLES_DISTRIBUTION = 7;
    private static int SLEEP_TIME = 1100;

    private String result;

    private String distributionResult;

    public WriterTest() {
        this.result = new String();
        this.distributionResult = new String();
    }

    protected void testWrite(String data) throws IOException {
        double dramEnergy = 0D;
        double cpuEnergy = 0D;
        double packageEnergy = 0D;
        double uncoreEnergy = 0D;
        double wallClockTimeUse = 0D;

        double energyDistribution[] = new double[SAMPLES_DISTRIBUTION];

        for (int i = 0; i < ITERATIONS; i++) {
            Writer writer = getWriterInstance();

            double wallClockStart = System.currentTimeMillis() / 1000D;
            double[] before = EnergyCheckUtils.getEnergyStats();

            writer.write(data);
            writer.close();

            double[] after = EnergyCheckUtils.getEnergyStats();
            double wallClockEnd = System.currentTimeMillis() / 1000D;

            if(i >= (ITERATIONS - SAMPLES_DISTRIBUTION)) {
                double _dramEnergy = getEnergyDelta(after[0], before[0]);
                double _cpuEnergy = getEnergyDelta(after[1], before[1]);
                double _packageEnergy = getEnergyDelta(after[2], before[2]);
                double _uncoreEnergy = (_packageEnergy - _cpuEnergy);

                int indexDistribuition = (ITERATIONS - SAMPLES_DISTRIBUTION - i) * (-1);
                energyDistribution[indexDistribuition] = _uncoreEnergy + _dramEnergy + _cpuEnergy;
            }

            if(i >= (ITERATIONS - SAMPLES)) {
                dramEnergy += getEnergyDelta(after[0], before[0]);
                cpuEnergy += getEnergyDelta(after[1], before[1]);
                packageEnergy += getEnergyDelta(after[2], before[2]);
                uncoreEnergy += (packageEnergy - cpuEnergy);
                wallClockTimeUse += (wallClockEnd - wallClockStart);
            }

            try { Thread.sleep(SLEEP_TIME); } catch (InterruptedException e) { }
        }

        dramEnergy /= SAMPLES;
        cpuEnergy /= SAMPLES;
        uncoreEnergy /= SAMPLES;
        wallClockTimeUse /= SAMPLES;

        result = result.concat(getSigla()).concat(",");
        result = result.concat(String.valueOf(uncoreEnergy)).concat(",");
        result = result.concat(String.valueOf(dramEnergy)).concat(",");
        result = result.concat(String.valueOf(cpuEnergy)).concat(",");
        result = result.concat(String.valueOf(getPower(uncoreEnergy, wallClockTimeUse)).concat(","));
        result = result.concat(String.valueOf(getPower(dramEnergy, wallClockTimeUse)).concat(","));
        result = result.concat(String.valueOf(getPower(cpuEnergy, wallClockTimeUse)));

        Arrays.sort(energyDistribution);

        distributionResult = distributionResult.concat(getSigla()).concat(",");
        distributionResult = distributionResult.concat(String.valueOf(energyDistribution[energyDistribution.length - 1])).concat(",");
        distributionResult = distributionResult.concat(String.valueOf(Util.quartil(3, energyDistribution))).concat(",");
        distributionResult = distributionResult.concat(String.valueOf(Util.quartil(2, energyDistribution))).concat(",");
        distributionResult = distributionResult.concat(String.valueOf(Util.quartil(1, energyDistribution)).concat(","));
        distributionResult = distributionResult.concat(String.valueOf(energyDistribution[0]));
    }

    public static double getPower(double energy, double time) {
        return time == 0 ? energy : energy / time;
    }

    public static double getEnergyDelta(double after, double before) {
        return after - before;
    }

    public String getResult() {
        return result;
    }

    public String getDistributionResult() {
        return distributionResult;
    }

    abstract String getSigla();

    abstract Writer getWriterInstance() throws IOException;

    public static void main(String[] args) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get("/home/gilson/Documents/EstudoDirigido/largepagewithimages.html")));
        String result = "CLASS,UNCORE-ENERGY,DRAM-ENERGY,CPU-ENERGY,UNCORE-POWER,DRAM-POWER,CPU-POWER";
        String distributionResult = "CLASS,MAX,Q3,Q2,Q1,MIN";
        String testNumber = "6";

        BufferedWriterTest bufferedWriterTest = new BufferedWriterTest();
        bufferedWriterTest.testWrite(data);

        FileWriterTest fileWriterTest = new FileWriterTest();
        fileWriterTest.testWrite(data);

        StringWriterTest stringWriterTest = new StringWriterTest();
        stringWriterTest.testWrite(data);

        PrintWriterTest printWriterTest = new PrintWriterTest();
        printWriterTest.testWrite(data);

        CharArrayWriterTest charArrayWriterTest = new CharArrayWriterTest();
        charArrayWriterTest.testWrite(data);

        EnergyCheckUtils.ProfileDealloc();

        result = result.concat("\n").concat(bufferedWriterTest.getResult());
        result = result.concat("\n").concat(fileWriterTest.getResult());
        result = result.concat("\n").concat(stringWriterTest.getResult());
        result = result.concat("\n").concat(printWriterTest.getResult());
        result = result.concat("\n").concat(charArrayWriterTest.getResult());

        distributionResult = distributionResult.concat("\n").concat(bufferedWriterTest.getDistributionResult());
        distributionResult = distributionResult.concat("\n").concat(fileWriterTest.getDistributionResult());
        distributionResult = distributionResult.concat("\n").concat(stringWriterTest.getDistributionResult());
        distributionResult = distributionResult.concat("\n").concat(printWriterTest.getDistributionResult());
        distributionResult = distributionResult.concat("\n").concat(charArrayWriterTest.getDistributionResult());

        Util.saveReport("test-" + testNumber + "-writer-write-20mb.csv", result);
        Util.saveReport("test-" + testNumber + "-writer-write-boxplot-20mb.csv", distributionResult);

        Util.cleanTempFolder();
    }

}