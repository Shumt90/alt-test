import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(System.in));

        int count = Integer.parseInt(r.readLine());

        int max = 0;
        int cur = 0;

        for (int i = 0; i < count; ++i) {

            int curLine = Integer.parseInt(r.readLine());

            if (curLine == 1) {
                ++cur;
            } else{
                cur=0;
            }


            max = Math.max(max, cur);
        }

        System.out.println(max);

    }

}
