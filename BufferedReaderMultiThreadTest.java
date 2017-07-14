import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BufferedReaderMultiThreadTest extends ReaderTest {

    public BufferedReaderMultiThreadTest() throws FileNotFoundException {
//        super(new BufferedReader(new FileReader("/home/gilson/Dropbox/UFPA/Mestrado/Estudo Dirigido/Assets/largepagewithimages.html"))); // 20mb
//        super(new BufferedReader(new FileReader("/home/gilson/Dropbox/UFPA/Mestrado/Estudo Dirigido/Assets/server.log.2015-11-13"))); // 140mb
//        super(new BufferedReader(new FileReader("/home/gilson/Dropbox/UFPA/Mestrado/Estudo Dirigido/Assets/server.log.2015-11-14"))); // 316mb
    }

    public static void main(String[] args) throws IOException {
        new BufferedReaderMultiThreadTest().testRead();
    }

    public static void start() throws IOException, InterruptedException {
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

    /**
     * Remover o multi-thread
     *
     * @param threads
     * @param total
     * @param iterations
     * @param mainThread
     * @throws IOException
     * @throws InterruptedException
     */
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
                            URL url = new URL("http://docker02/largepagewithimages.html"); // 20mb
                            //URL url = new URL("http://docker02/server.log.2015-11-13"); // 140mb
//                            URL url = new URL("http://docker02/server.log.2015-11-14"); // 316mb
                            //Mudar para FileReader
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));

                            String output = new String();
                            String line;
                            //while ((line = bufferedReader.readLine()) != null) System.out.println(line);
                            while ((line = bufferedReader.readLine()) != null);
//                            while ((line = bufferedReader.readLine()) != null) {
//                                output = output.concat(line);
//                            }
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

    @Override
    String getSigla() {
        return "BR";
    }

    @Override
    Reader getReaderInstance() throws FileNotFoundException {
        return null;
    }
}
