import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class MainD {

    ////////////////////////////////////////////
    /////////////////////   TASK FUNCTIONS
    //////////////////////

    private static byte[] toPrint;
    private static FileOutputStream fdo;

    public static void main(String[] args) throws IOException, InterruptedException {

        fdo = new FileOutputStream(FileDescriptor.out);

        byte count = readNextIntUnicode();

        if (count == 0) {
            return;
        }

        boolean[] state = new boolean[count*2];

        toPrint=new byte[count*2];

        begin:
        while (true) {

            filterAndPrint(state);

            state[state.length - 1] = true;

            filterAndPrint(state);

            for (int i = state.length - 1; i != -1; --i) {
                if (!state[i]) {

                    if (i == 0) {
                        break begin;
                    }

                    state[i] = true;

                    for (int j = i + 1; j < state.length; ++j) {
                        state[j] = false;
                    }

                    continue begin;
                }
            }

            break;

        }


    }

    private static void filterAndPrint(boolean[] state) throws IOException {

        int b = 0;

        for (boolean value : state) {

            b += value ? -1 : 1;
            if (b < 0)
                return;
        }
        if (b == 0) {

            for (int i=0; i<state.length;++i){
                toPrint[i]=(byte)( state[i]?')':'(');
            }

            fdo.write(toPrint);
            fdo.write('\n');
            fdo.flush();
        }


    }


    ////////////////////////////////////////////
    /////////////////////   MEMORY SAVE FUNCTIONS
    //////////////////////

    public static byte readNextIntUnicode() throws IOException {
        int step = 10;
        int digital = 0;
        int inversion = 1;
        for (int i = System.in.read(); i != '\n'; i = System.in.read()) {

            if (i == 45) {
                inversion = -1;
                continue;
            }

            digital *= step;
            digital += map(i);
        }
        digital *= inversion;

        return (byte) digital;

    }

    private static byte map(int digital) {
        switch (digital) {
            case 48:
                return 0;
            case 49:
                return 1;
            case 50:
                return 2;
            case 51:
                return 3;
            case 52:
                return 4;
            case 53:
                return 5;
            case 54:
                return 6;
            case 55:
                return 7;
            case 56:
                return 8;
            case 57:
                return 9;
        }

        throw new RuntimeException("digital?" + digital);
    }

}
