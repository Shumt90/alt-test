import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class FTEst {
    public static void main(String[] args) throws IOException {
        some();
    }


    private static void some() throws IOException {
        int k = (int) (Math.random() * 100);
        int all = 0;
        int[] allE = new int[k * k * 10];

        //BufferedWriter writer = new BufferedWriter(new FileWriter("auto-test.txt"));
        OutputStreamWriter testData = new OutputStreamWriter(new FileOutputStream("auto-test.txt"), StandardCharsets.UTF_8);
        OutputStreamWriter expected = new OutputStreamWriter(new FileOutputStream("expected.txt"), StandardCharsets.UTF_8);

        testData.write(String.valueOf(k));

        for (int i = 0; i < k; ++i) {

            testData.write("\n");

            int n = (int) (Math.random() * (k * 10));

            testData.write(String.valueOf(n));

            for (int j = 0; j < n; ++j) {

                int r = (int) (Math.random() * 100);

                allE[all++] = r;

                testData.write(" ");
                testData.write(String.valueOf(r));
            }
        }

        testData.flush();

        int[] r = Arrays.copyOfRange(allE, 0, all);
        Arrays.sort(r);
        for (int i = 0; i < r.length; ++i) {
            if (i != 0)
                expected.write(" ");
            expected.write(String.valueOf(r[i]));
        }

        expected.flush();
    }
}
