import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by root on 19/07/17.
 */
public class Util {

    public static void saveReport(String fileName, String content) throws IOException {
        Path path = Paths.get("/home/gilson/Documents/git/github/gilsonrochasilva/jRAPL/data-analysis/inputs/" + fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(content);
        }
    }

    public static double quartil(int quartil, double[] elementos) {
        int k = (int)((quartil * (elementos.length + 1D)) / 4D);

        double parteA = elementos[k - 1];
        double parteB = (((quartil * (elementos.length + 1D)) / 4D) - k);
        double parteC = elementos[k] - elementos[k - 1];

        return parteA + parteB * parteC;
    }

    public static void main(String[] args) {
//        double[] elements = {7.1, 7.4, 7.5, 7.7, 7.8, 7.9};
//        double[] elements = {903.88,1036.92,1098.04,1011.26 ,1020.70,915.38,1014.53,1097.79 ,934.52,1214.08,993.45,1120.19 ,860.41,1039.19,950.38,941.83 ,936.78,1086.98,1144.94,1066.12};
        double[] elements = {0.2570190000037087, 0.25888099999542646, 0.28244000000120195, 0.2840730000011149, 0.2890620000032982, 0.30542000000406233, 0.357527999999661};

        Arrays.sort(elements);

        System.out.println("Max: " + elements[elements.length - 1]);
        System.out.println("Q3: " + quartil(3, elements));
        System.out.println("Q2: " + quartil(2, elements));
        System.out.println("Q1: " + quartil(1, elements));
        System.out.println("Min: " + elements[0]);
    }

    public static double getPower(double energy, double time) {
        return time == 0 ? energy : energy / time;
    }

    public static double getEnergyDelta(double after, double before) {
        return after - before;
    }

    public static void cleanTempFolder() {
        File dir = new File("/home/gilson/Documents/EstudoDirigido/writer-out");
        for (File file: dir.listFiles()) file.delete();
    }
}
