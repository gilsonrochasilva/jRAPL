import java.util.Arrays;

/**
 * Created by root on 17/08/17.
 */
public abstract class EnergyTestImpl implements IEnergyTestCase, TestConfiguration {

    private String result;
    private String distributionResult;

    public EnergyTestImpl() {
        this.distributionResult = new String();
        this.result = new String();
    }

    @Override
    public void execute() {
        double dramEnergy = 0D;
        double cpuEnergy = 0D;
        double packageEnergy = 0D;
        double uncoreEnergy = 0D;
        double wallClockTimeUse = 0D;

        double energyDistribution[] = new double[TestConfiguration.SAMPLES_DISTRIBUTION];

        for (int i = 0; i < TestConfiguration.ITERATIONS; i++) {
            double wallClockStart = System.currentTimeMillis() / 1000D;
            double[] before = EnergyCheckUtils.getEnergyStats();

            testImplementation();

            double[] after = EnergyCheckUtils.getEnergyStats();
            double wallClockEnd = System.currentTimeMillis() / 1000D;

            if(i >= (TestConfiguration.ITERATIONS - TestConfiguration.SAMPLES_DISTRIBUTION)) {
                double _dramEnergy = Util.getEnergyDelta(after[0], before[0]);
                double _cpuEnergy = Util.getEnergyDelta(after[1], before[1]);
                double _packageEnergy = Util.getEnergyDelta(after[2], before[2]);
                double _uncoreEnergy = (_packageEnergy - _cpuEnergy);

                int indexDistribuition = (TestConfiguration.ITERATIONS - TestConfiguration.SAMPLES_DISTRIBUTION - i) * (-1);
                energyDistribution[indexDistribuition] = _uncoreEnergy + _dramEnergy + _cpuEnergy;
            }

            if(i >= (TestConfiguration.ITERATIONS - SAMPLES)) {
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

        result = result.concat(getId()).concat(",");
        result = result.concat(String.valueOf(uncoreEnergy)).concat(",");
        result = result.concat(String.valueOf(dramEnergy)).concat(",");
        result = result.concat(String.valueOf(cpuEnergy)).concat(",");
        result = result.concat(String.valueOf(Util.getPower(uncoreEnergy, wallClockTimeUse)).concat(","));
        result = result.concat(String.valueOf(Util.getPower(dramEnergy, wallClockTimeUse)).concat(","));
        result = result.concat(String.valueOf(Util.getPower(cpuEnergy, wallClockTimeUse)));

        Arrays.sort(energyDistribution);

        distributionResult = distributionResult.concat(getId()).concat(",");
        distributionResult = distributionResult.concat(String.valueOf(energyDistribution[energyDistribution.length - 1])).concat(",");
        distributionResult = distributionResult.concat(String.valueOf(Util.quartil(3, energyDistribution))).concat(",");
        distributionResult = distributionResult.concat(String.valueOf(Util.quartil(2, energyDistribution))).concat(",");
        distributionResult = distributionResult.concat(String.valueOf(Util.quartil(1, energyDistribution)).concat(","));
        distributionResult = distributionResult.concat(String.valueOf(energyDistribution[0]));
    }

    @Override
    public String getEnergyConsumptionReport() {
        return result;
    }

    @Override
    public String getDistributionReport() {
        return distributionResult;
    }
}
