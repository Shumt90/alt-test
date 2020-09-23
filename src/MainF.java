import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainF {

    private static boolean skip;

    private static final byte[] fileBuf = new byte[1024];
    private static int fileBufLastRead;
    private static int fileBufOffset;
    private static final byte[] MIN_VALUE = new byte[]{'-', '2', '1', '4', '7', '4', '8', '3', '6', '4', '8'};
    final static byte[] DigitTens = {
            '0', '0', '0', '0', '0', '0', '0', '0', '0', '0',
            '1', '1', '1', '1', '1', '1', '1', '1', '1', '1',
            '2', '2', '2', '2', '2', '2', '2', '2', '2', '2',
            '3', '3', '3', '3', '3', '3', '3', '3', '3', '3',
            '4', '4', '4', '4', '4', '4', '4', '4', '4', '4',
            '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
            '6', '6', '6', '6', '6', '6', '6', '6', '6', '6',
            '7', '7', '7', '7', '7', '7', '7', '7', '7', '7',
            '8', '8', '8', '8', '8', '8', '8', '8', '8', '8',
            '9', '9', '9', '9', '9', '9', '9', '9', '9', '9',
    };

    final static byte[] DigitOnes = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
    };
    final static byte[] digits = {
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };
    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999,
            99999999, 999999999, Integer.MAX_VALUE};

    private static byte[] buf1 = new byte[1];
    private static byte[] buf2 = new byte[2];
    private static byte[] buf3 = new byte[3];
    private static byte[] buf4 = new byte[4];

    ////////////////////////////////////////////
    /////////////////////   TASK FUNCTIONS
    //////////////////////


    public static void main(String[] args) throws IOException, InterruptedException {

        long time = System.currentTimeMillis();

        FileOutputStream fdo = new FileOutputStream(FileDescriptor.out);

        int k = readNextIntDigital();

        byte[] values = new byte[k * k * 10];
        int[] curLine = new int[k * 10+1];
        int count = 0;

        for (int lineIndex = 0; lineIndex < k; ++lineIndex) {
            readNextByteLine(curLine);
            if (curLine[0]<1)
                continue;

            int digitIndex=1;

            while (digitIndex<=curLine[0]){

                byte cur = (byte) curLine[digitIndex];

                int extendedPosition = reversedBinarySearch(values, count, cur);
                insert(values, extendedPosition, count++, cur);

                ++digitIndex;

            }

        }
        for (int i = 0;i < count; ++i) {
            fdo.write(toChar(values[i], i + 1 == count));
            fdo.flush();
        }
//        System.out.print("\nTime: ");
//        System.out.println(System.currentTimeMillis() - time);

    }

    private static void insert(byte[] values, int extendedPosition, int uniqueCount, byte value) {

        byte cache;

        for (int i = extendedPosition; i <= uniqueCount; ++i) {

            cache = values[i];
            values[i] = value;
            value = cache;

        }

    }

    private static int reversedBinarySearch(byte[] values, int count, byte value) {

        int left = 0;
        int right = count;

        for (int dis = right - left; dis > 0; dis = right - left) {

            int centerOffset = left + (dis) / 2;

            if (values[centerOffset] == value) {
                return centerOffset;
            }

            if (values[centerOffset] > value)
                right = centerOffset - 1;
            else
                left = centerOffset + 1;

        }

        if (value > values[left] && left < count - 1) {
            return left + 1;
        } else {
            return left;
        }

    }


    ////////////////////////////////////////////
    /////////////////////   MEMORY SAVE FUNCTIONS
    //////////////////////

    public static byte[] toChar(byte i, boolean last) {

        int size = (i < 0) ? stringSize(-i) + 1 : stringSize(i);
        byte[] buf;
        if (!last) {
            buf = getInCache(size + 1);
            buf[buf.length - 1] = ' ';
        } else {
            buf = getInCache(size);
        }

        getChars(i, size, buf);
        return buf;
    }

    private static byte[] getInCache(int size) {
        switch (size) {
            case 1:
                return buf1;
            case 2:
                return buf2;
            case 3:
                return buf3;
            case 4:
                return buf4;
        }

        throw new RuntimeException("unexpected size " + size);
    }

    static void getChars(int i, int index, byte[] buf) {
        int q, r;
        int charPos = index;
        byte sign = 0;

        if (i < 0) {
            sign = '-';
            i = -i;
        }

        // Generate two digits per iteration
        while (i >= 65536) {
            q = i / 100;
            // really: r = i - (q * 100);
            r = i - ((q << 6) + (q << 5) + (q << 2));
            i = q;
            buf[--charPos] = DigitOnes[r];
            buf[--charPos] = DigitTens[r];
        }

        // Fall thru to fast mode for smaller numbers
        // assert(i <= 65536, i);
        for (; ; ) {
            q = (i * 52429) >>> (16 + 3);
            r = i - ((q << 3) + (q << 1));  // r = i-(q*10) ...
            buf[--charPos] = digits[r];
            i = q;
            if (i == 0) break;
        }
        if (sign != 0) {
            buf[--charPos] = sign;
        }
    }


    static int stringSize(int x) {
        for (int i = 0; ; i++)
            if (x <= sizeTable[i])
                return i + 1;
    }

    public static int readNextIntDigital() throws IOException {
        int step = 10;
        int digital = 0;
        for (int i = System.in.read(); i != '\n'; i = System.in.read()) {
            digital *= step;
            digital += map(i);
        }

        skip = true;

        return digital;

    }

    public static void readNextByteLine(int[] curLine) throws IOException {
        final int step = 10;
        int calculated = 0;
        int cur;
        int offset = 0;


        while (true) {

            cur = nextCharFromBuf();

            if (cur == ' ' || cur == '\n') {
                curLine[offset++] = calculated;
                calculated = 0;
            }

            if (cur == '\n') {
                return;
            }

            calculated *= step;
            calculated += map(cur);
        }

    }

    private static byte nextCharFromBuf() throws IOException {

        if (fileBufLastRead == fileBufOffset) {
            fileBufLastRead = System.in.read(fileBuf);
            fileBufOffset = 0;
        }

        return fileBuf[fileBufOffset++];


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
