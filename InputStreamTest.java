import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public abstract class InputStreamTest extends EnergyTest {

    public InputStreamTest() {
        super();
    }

    protected void testRead() throws IOException {
        double dramEnergy = 0D;
        double cpuEnergy = 0D;
        double packageEnergy = 0D;
        double uncoreEnergy = 0D;
        double wallClockTimeUse = 0D;

        double energyDistribution[] = new double[SAMPLES_DISTRIBUTION];

        for (int i = 0; i < ITERATIONS; i++) {
            InputStream reader = getInputStreamInstance();

            double wallClockStart = System.currentTimeMillis() / 1000D;
            double[] before = EnergyCheckUtils.getEnergyStats();

            int value = 0, fake = 0;
            while ((value = reader.read()) != -1) fake = value;
            reader.close();

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

    abstract InputStream getInputStreamInstance() throws FileNotFoundException;

    public static void main(String[] args) throws IOException {
        String result = "CLASS,UNCORE-ENERGY,DRAM-ENERGY,CPU-ENERGY,UNCORE-POWER,DRAM-POWER,CPU-POWER";
        String distributionResult = "CLASS,MAX,Q3,Q2,Q1,MIN";

        BufferedInputStreamTest bufferedInputStreamTest = new BufferedInputStreamTest();
        bufferedInputStreamTest.testRead();

        FileInputStreamTest fileInputStreamTest = new FileInputStreamTest();
        fileInputStreamTest.testRead();

        PushbackInputStreamTest pushbackInputStreamTest = new PushbackInputStreamTest();
        pushbackInputStreamTest.testRead();

        StringBufferInputTest stringBufferInputTest = new StringBufferInputTest();
        stringBufferInputTest.testRead();

        ByteArrayInputStreamTest byteArrayInputStreamTest = new ByteArrayInputStreamTest();
        byteArrayInputStreamTest.testRead();

        LineNumberInputStreamTest lineNumberInputStreamTest = new LineNumberInputStreamTest();
        lineNumberInputStreamTest.testRead();

        EnergyCheckUtils.ProfileDealloc();

        result = result.concat("\n").concat(bufferedInputStreamTest.getResult());
        result = result.concat("\n").concat(fileInputStreamTest.getResult());
        result = result.concat("\n").concat(pushbackInputStreamTest.getResult());
        result = result.concat("\n").concat(stringBufferInputTest.getResult());
        result = result.concat("\n").concat(byteArrayInputStreamTest.getResult());
        result = result.concat("\n").concat(lineNumberInputStreamTest.getResult());

        distributionResult = distributionResult.concat("\n").concat(bufferedInputStreamTest.getDistributionResult());
        distributionResult = distributionResult.concat("\n").concat(fileInputStreamTest.getDistributionResult());
        distributionResult = distributionResult.concat("\n").concat(pushbackInputStreamTest.getDistributionResult());
        distributionResult = distributionResult.concat("\n").concat(stringBufferInputTest.getDistributionResult());
        distributionResult = distributionResult.concat("\n").concat(byteArrayInputStreamTest.getDistributionResult());
        distributionResult = distributionResult.concat("\n").concat(lineNumberInputStreamTest.getDistributionResult());

        Util.saveReport("test-" + TEST_NUMBER + "-input-stream-read-20mb.csv", result);
        Util.saveReport("test-" + TEST_NUMBER + "-input-stream-read-boxplot-20mb.csv", distributionResult);
    }
}
