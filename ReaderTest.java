import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by root on 29/06/17.
 */
abstract class ReaderTest {

    private static int ITERATIONS = 10;
    private static int SAMPLES = 3;

    private String result;

    public ReaderTest() {
        this.result = new String();
    }

    protected void testRead() throws IOException {
        double dramEnergy = 0D;
        double cpuEnergy = 0D;
        double packageEnergy = 0D;
        double uncoreEnergy = 0D;
        double wallClockTimeUse = 0D;

        for (int i = 0; i < ITERATIONS; i++) {
            Reader reader = getReaderInstance();

            double wallClockStart = System.currentTimeMillis() / 1000D;
            double[] before = EnergyCheckUtils.getEnergyStats();

            int value = 0, fake = 0;
            while ((value = reader.read()) != -1) fake = value;
            reader.close();

            double[] after = EnergyCheckUtils.getEnergyStats();
            double wallClockEnd = System.currentTimeMillis() / 1000D;

            if(i >= (ITERATIONS - SAMPLES)) {
                dramEnergy += getEnergyDelta(after[0], before[0]);
                cpuEnergy += getEnergyDelta(after[1], before[1]);
                packageEnergy += getEnergyDelta(after[2], before[2]);
                uncoreEnergy += packageEnergy - cpuEnergy;
                wallClockTimeUse += wallClockEnd - wallClockStart;
            }

            try { Thread.sleep(1800); } catch (InterruptedException e) { }
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
    }

    public static double getPower(double energy, double time) {
        return time == 0 ? energy : energy / time;
    }

    public static double getEnergyDelta(double after, double before) {
        return (after - before) / 10D;
    }

    public String getResult() {
        return result;
    }

    abstract String getSigla();

    abstract Reader getReaderInstance() throws FileNotFoundException;

    public static void main(String[] args) throws IOException {
        String result = "CLASS,UNCORE-ENERGY,DRAM-ENERGY,CPU-ENERGY,UNCORE-POWER,DRAM-POWER,CPU-POWER";

        BufferedReaderTest bufferedReaderTest = new BufferedReaderTest();
        bufferedReaderTest.testRead();

        LineNumberReaderTest lineNumberReaderTest = new LineNumberReaderTest();
        lineNumberReaderTest.testRead();

        CharArrayReaderTest charArrayReaderTest = new CharArrayReaderTest();
        charArrayReaderTest.testRead();

        PushbackReaderTest pushbackReaderTest = new PushbackReaderTest();
        pushbackReaderTest.testRead();

        FileReaderTest fileReaderTest = new FileReaderTest();
        fileReaderTest.testRead();

        StringReaderTest stringReaderTest = new StringReaderTest();
        stringReaderTest.testRead();

        EnergyCheckUtils.ProfileDealloc();

        result = result.concat("\n").concat(bufferedReaderTest.getResult());
        result = result.concat("\n").concat(lineNumberReaderTest.getResult());
        result = result.concat("\n").concat(charArrayReaderTest.getResult());
        result = result.concat("\n").concat(pushbackReaderTest.getResult());
        result = result.concat("\n").concat(fileReaderTest.getResult());
        result = result.concat("\n").concat(stringReaderTest.getResult());

        System.out.println(result);
    }
}