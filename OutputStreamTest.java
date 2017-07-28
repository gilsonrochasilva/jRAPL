import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public abstract class OutputStreamTest extends EnergyTest {

    public OutputStreamTest() {
        super();
    }

    protected void testWrite(byte[] data) throws IOException {
        double dramEnergy = 0D;
        double cpuEnergy = 0D;
        double packageEnergy = 0D;
        double uncoreEnergy = 0D;
        double wallClockTimeUse = 0D;

        double energyDistribution[] = new double[SAMPLES_DISTRIBUTION];

        for (int i = 0; i < ITERATIONS; i++) {
            OutputStream writer = getOutputStreamInstance();

            double wallClockStart = System.currentTimeMillis() / 1000D;
            double[] before = EnergyCheckUtils.getEnergyStats();

            writer.write(data);
            writer.close();

            double[] after = EnergyCheckUtils.getEnergyStats();
            double wallClockEnd = System.currentTimeMillis() / 1000D;

            if(i >= (ITERATIONS - SAMPLES_DISTRIBUTION)) {
                double _dramEnergy = Util.getEnergyDelta(after[0], before[0]);
                double _cpuEnergy = Util.getEnergyDelta(after[1], before[1]);
                double _packageEnergy = Util.getEnergyDelta(after[2], before[2]);
                double _uncoreEnergy = (_packageEnergy - _cpuEnergy);

                int indexDistribuition = (ITERATIONS - SAMPLES_DISTRIBUTION - i) * (-1);
                energyDistribution[indexDistribuition] = _uncoreEnergy + _dramEnergy + _cpuEnergy;
            }

            if(i >= (ITERATIONS - SAMPLES)) {
                dramEnergy += Util.getEnergyDelta(after[0], before[0]);
                cpuEnergy += Util.getEnergyDelta(after[1], before[1]);
                packageEnergy += Util.getEnergyDelta(after[2], before[2]);
                uncoreEnergy += packageEnergy - cpuEnergy;
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
        result = result.concat(String.valueOf(Util.getPower(uncoreEnergy, wallClockTimeUse)).concat(","));
        result = result.concat(String.valueOf(Util.getPower(dramEnergy, wallClockTimeUse)).concat(","));
        result = result.concat(String.valueOf(Util.getPower(cpuEnergy, wallClockTimeUse)));

        Arrays.sort(energyDistribution);

        distributionResult = distributionResult.concat(getSigla()).concat(",");
        distributionResult = distributionResult.concat(String.valueOf(energyDistribution[energyDistribution.length - 1])).concat(",");
        distributionResult = distributionResult.concat(String.valueOf(Util.quartil(3, energyDistribution))).concat(",");
        distributionResult = distributionResult.concat(String.valueOf(Util.quartil(2, energyDistribution))).concat(",");
        distributionResult = distributionResult.concat(String.valueOf(Util.quartil(1, energyDistribution)).concat(","));
        distributionResult = distributionResult.concat(String.valueOf(energyDistribution[0]));
    }

    abstract OutputStream getOutputStreamInstance() throws FileNotFoundException;

    public static void main(String[] args) throws IOException {
        String data = new String(Files.readAllBytes(Paths.get("/home/gilson/Documents/EstudoDirigido/largepagewithimages.html")));
        String result = "CLASS,UNCORE-ENERGY,DRAM-ENERGY,CPU-ENERGY,UNCORE-POWER,DRAM-POWER,CPU-POWER";
        String distributionResult = "CLASS,MAX,Q3,Q2,Q1,MIN";

        FileOutputStreamTest fileOutputStreamTest = new FileOutputStreamTest();
        fileOutputStreamTest.testWrite(data.getBytes());

        ByteArrayOutputStreamTest byteArrayOutputStreamTest = new ByteArrayOutputStreamTest();
        byteArrayOutputStreamTest.testWrite(data.getBytes());

        BufferedOutputStreamTest bufferedOutputStreamTest = new BufferedOutputStreamTest();
        bufferedOutputStreamTest.testWrite(data.getBytes());

        PrintOutputStreamTest printOutputStreamTest = new PrintOutputStreamTest();
        printOutputStreamTest.testWrite(data.getBytes());

        EnergyCheckUtils.ProfileDealloc();

        result = result.concat("\n").concat(fileOutputStreamTest.getResult());
        result = result.concat("\n").concat(byteArrayOutputStreamTest.getResult());
        result = result.concat("\n").concat(bufferedOutputStreamTest.getResult());
        result = result.concat("\n").concat(printOutputStreamTest.getResult());

        distributionResult = distributionResult.concat("\n").concat(fileOutputStreamTest.getDistributionResult());
        distributionResult = distributionResult.concat("\n").concat(byteArrayOutputStreamTest.getDistributionResult());
        distributionResult = distributionResult.concat("\n").concat(bufferedOutputStreamTest.getDistributionResult());
        distributionResult = distributionResult.concat("\n").concat(printOutputStreamTest.getDistributionResult());

        Util.saveReport("test-" + TEST_NUMBER + "-output-stream-write-20mb.csv", result);
        Util.saveReport("test-" + TEST_NUMBER + "-output-stream-write-boxplot-20mb.csv", distributionResult);

        Util.cleanTempFolder();
    }
}
