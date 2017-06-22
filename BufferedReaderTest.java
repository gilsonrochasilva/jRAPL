import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BufferedReaderTest {

    public static void main(String[] args) throws Exception {
        final int ITERATIONS = 10;
        final int MAINTHREAD = 1;
        final int THREADS = 1;
        final int RMITERATION = 1;
        final int WARMUP = 3;
        final int NOWARMUP = 0;
        final int N = 1;
        final int capacity = 1;

        String socketNumCheck = EnergyCheckUtils.EnergyStatCheck();
        int sockNum = socketNumCheck.contains("@") ? socketNumCheck.split("@").length : 1;
        int powerLimitEnable = 0;

        DataPrinter.printTitle(sockNum);
        System.out.format("Conf: Iterations=%s, threads=%s, N=%s, capacity=%s\n", ITERATIONS, THREADS, N, capacity);

        EnergyCalc.preInit(0, THREADS, 0, 0, 0, 0, 0, 0, ITERATIONS, WARMUP);

        readLineOperation(THREADS, N, ITERATIONS, MAINTHREAD);
    }

    private static void readLineOperation(int threads, final int total, int iterations, int mainThread) throws IOException, InterruptedException {
        TimeCheckUtils mainTimeHelper = new TimeCheckUtils();
        DataPrinter ener = new DataPrinter("BufferedReader.readLine()", mainThread);

        for (int i = 0; i < iterations; i++) {
            ener.timePreamble = mainTimeHelper.getCurrentThreadTimeInfo();
            ener.wallClockTimeStart = System.currentTimeMillis()/1000.0;
            ener.preEnergy= EnergyCheckUtils.EnergyStatCheck();

            ExecutorService executors = Executors.newFixedThreadPool(threads);
            for (int j = 0; j < threads; j++) {
                executors.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL oracle = new URL("http://docker02/largepagewithimages.html"); // 20mb
                            //URL oracle = new URL("http://docker02/server.log.2015-11-13"); // 140mb
//                            URL oracle = new URL("http://docker02/server.log.2015-11-14"); // 316mb
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(oracle.openStream()));

                            String output = new String();
                            String line;
                            //while ((line = bufferedReader.readLine()) != null) System.out.println(line);
                            while ((line = bufferedReader.readLine()) != null) {
                                output.concat(line);
                            }
                            bufferedReader.close();
                        } catch (MalformedURLException malformedURLException) {
                            malformedURLException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }
                });
            }

            executors.shutdown();
            executors.awaitTermination(1, TimeUnit.DAYS);

            ener.timeEpilogue = mainTimeHelper.getCurrentThreadTimeInfo();
            ener.wallClockTimeEnd = System.currentTimeMillis() / 1000.0;
            ener.postEnergy = EnergyCheckUtils.EnergyStatCheck();
            ener.dataReport();
        }
    }
}
