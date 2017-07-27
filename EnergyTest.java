/**
 * Created by root on 27/07/17.
 */
public abstract class EnergyTest {

    public static final String TEST_NUMBER = "7";
    public static int ITERATIONS = 10;
    public static int SAMPLES = 3;
    public static int SAMPLES_DISTRIBUTION = 7;
    public static int SLEEP_TIME = 1100;
    protected String result;
    protected String distributionResult;

    public EnergyTest() {
        this.distributionResult = new String();
        this.result = new String();
    }

    public String getResult() {
        return result;
    }

    public String getDistributionResult() {
        return distributionResult;
    }

    abstract String getSigla();
}
