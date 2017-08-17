import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by root on 02/08/17.
 */
public class ScannerTest extends EnergyTest {

    public ScannerTest() {
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
            String filePath = "/home/gilson/Documents/EstudoDirigido/largepagewithimages.html";
            Scanner scanner = new Scanner(new File(filePath));

            double wallClockStart = System.currentTimeMillis() / 1000D;
            double[] before = EnergyCheckUtils.getEnergyStats();

            while (scanner.hasNext()) {
                String token = scanner.next();
            }
            scanner.close();

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

    @Override
    String getSigla() {
        return "SCN";
    }

    public static void main(String[] args) throws IOException {
        String result = "CLASS,UNCORE-ENERGY,DRAM-ENERGY,CPU-ENERGY,UNCORE-POWER,DRAM-POWER,CPU-POWER";
        String distributionResult = "CLASS,MAX,Q3,Q2,Q1,MIN";

        ScannerTest scannerTest = new ScannerTest();
        scannerTest.testRead();

        EnergyCheckUtils.ProfileDealloc();

        result = result.concat("\n").concat(scannerTest.getResult());

        distributionResult = distributionResult.concat("\n").concat(scannerTest.getDistributionResult());

        Util.saveReport("test-" + TEST_NUMBER + "-scanner-next-20mb.csv", result);
        Util.saveReport("test-" + TEST_NUMBER + "-scanner-next-boxplot-20mb.csv", distributionResult);
    }
}