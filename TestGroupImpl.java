import java.io.BufferedInputStream;
import java.io.IOException;

/**
 * Created by root on 17/08/17.
 */
public abstract class TestGroupImpl implements TestConfiguration {

    protected void start() {
        String result = "CLASS,UNCORE-ENERGY,DRAM-ENERGY,CPU-ENERGY,UNCORE-POWER,DRAM-POWER,CPU-POWER";
        String distributionResult = "CLASS,MAX,Q3,Q2,Q1,MIN";

        for (IEnergyTestCase iEnergyTestCase : getTests()) {
            iEnergyTestCase.execute();

            result = result.concat("\n").concat(iEnergyTestCase.getEnergyConsumptionReport());
            distributionResult = distributionResult.concat("\n").concat(iEnergyTestCase.getDistributionReport());
        }

        EnergyCheckUtils.ProfileDealloc();

        generateReports(result, distributionResult);
    }

    private void generateReports(String result, String distributionResult) {
        try {
            String barChartName = String.format(getBarChartName(), TEST_NUMBER);
            String distributionResultName = String.format(getDistributionChartName(), TEST_NUMBER);

            Util.saveReport(barChartName, result);
            Util.saveReport(distributionResultName, distributionResult);

            Process exec = Runtime.getRuntime().exec(String.format("/usr/bin/python2.7 /home/gilson/Documents/git/github/gilsonrochasilva/jRAPL/data-analysis/micro-benchmark-v1.py %s %s", barChartName, distributionResultName));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(exec.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    abstract IEnergyTestCase[] getTests();

    abstract String getBarChartName();

    abstract String getDistributionChartName();
}
