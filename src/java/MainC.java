package data;

import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

public class MainC {

    private static int uniqueCount;
    private static byte[] buf1 = new byte[1];
    private static byte[] buf2 = new byte[2];
    private static byte[] buf3 = new byte[3];
    private static byte[] buf4 = new byte[4];
    private static byte[] buf5 = new byte[5];
    private static byte[] buf6 = new byte[6];
    private static byte[] buf7 = new byte[7];
    private static byte[] buf8 = new byte[8];
    private static byte[] buf9 = new byte[9];
    private static byte[] buf10 = new byte[10];
    private static byte[] buf11 = new byte[11];
    private static byte[] buf12 = new byte[12];

    private static int getValue(Iterator<Integer> integers) throws IOException {

        if (integers == null) {
            return readNextIntUnicode();
        } else {
            return integers.next();
        }
    }

    private static int[] getForInput(Iterator<Integer> integers) throws IOException {

        uniqueCount = 0;

        int count = getValue(integers);

        int[] values = new int[1_000_000];

        for (int i = 0; i < count; ++i) {

            int curLine = getValue(integers);

            int extendedPosition = reversedBinarySearch(values, uniqueCount, curLine);

            if (extendedPosition != -1) {
                insert(values, extendedPosition, uniqueCount++, curLine);
            }

        }

        return values;
    }

    private static void insert(int[] values, int extendedPosition, int uniqueCount, int value) {

        int cache;

        for (int i = extendedPosition; i <= uniqueCount; ++i) {

            cache = values[i];
            values[i] = value;
            value = cache;

        }

    }

    /**
     * @return -1 if found
     */
    private static int reversedBinarySearch(int[] values, int uniqueCount, int value) {

        int left = 0;
        int right = uniqueCount;

        int half = right - left;

        while (half > 0) {

            int centerOffset = left + (half) / 2;

            if (values[centerOffset] == value || value == values[right] || value == values[left]) {
                return -1;
            }

            if (values[centerOffset] > value) {
                right = centerOffset - 1;
            } else {
                left = centerOffset + 1;
            }

            half = right - left;
        }

        return left;

    }

    public static void main(String[] args) throws IOException {

        //long beforeUsedMem = Runtime.getRuntime().freeMemory();

        int[] res = getForInput(null);

        FileOutputStream fdo = new FileOutputStream(FileDescriptor.out);

        for (int i = 0; i < uniqueCount; ++i) {
/*            System.out.println(toChar(res[i]));
            System.out.flush();*/
            fdo.write(toChar(res[i]));
            fdo.write((byte) '\n');
            fdo.flush();
        }

/*
       long afterUsedMem = Runtime.getRuntime().freeMemory();

        System.out.println((beforeUsedMem-afterUsedMem)/1024);
*/

/*
        long beforeUsedMem = Runtime.getRuntime().freeMemory();

        System.out.println(Arrays.equals(testFilter(getForInput(Arrays.asList(5, 2, 2, 2, 8, 8).iterator())), new int[]{2, 8}));
        System.out.println(Arrays.equals(testFilter(getForInput(Arrays.asList(5, 0, 1, 2, 3, 4).iterator())), new int[]{0, 1, 2, 3, 4}));
        System.out.println(Arrays.equals(testFilter(getForInput(Arrays.asList(5, -1, -2, -2, -8, -8).iterator())), new int[]{-8, -2, -1}));
        System.out.println(Arrays.equals(testFilter(getForInput(Arrays.asList(5, -1, 2, 2, -8, -8).iterator())), new int[]{-8, -1, 2}));
        System.out.println(Arrays.equals(testFilter(getForInput(Arrays.asList(5, Integer.MAX_VALUE, 8, Integer.MAX_VALUE, 2,
                Integer.MAX_VALUE).iterator())), new int[]{2, 8, Integer.MAX_VALUE}));

        System.out.println(Arrays.equals(testFilter(getForInput(Arrays.asList(5, Integer.MAX_VALUE,
                Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE).iterator()))
                , new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE}));

        long afterUsedMem = Runtime.getRuntime().freeMemory();

        System.out.println(afterUsedMem - beforeUsedMem);*/
    }

    private static int[] testFilter(int[] values) {
        return Arrays.copyOfRange(values, 0, uniqueCount);
    }

    public static int readNextIntUnicode() throws IOException {
        int step = 10;
        int digital = 0;
        int inversion = 1;
        for (int i = System.in.read(); i != 10; i = System.in.read()) {

            if (i == 45) {
                inversion = -1;
                continue;
            }

            digital *= step;
            digital += map(i);
        }
        digital *= inversion;

        return digital;

    }

    private static int map(int unicode) {
        switch (unicode) {
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

        throw new RuntimeException("unicode?" + unicode);
    }

    private static final byte[] MIN_VALUE = new byte[]{'-', '2', '1', '4', '7', '4', '8', '3', '6', '4', '8'};

    public static byte[] toChar(int i) {
        if (i == Integer.MIN_VALUE) {
            return MIN_VALUE;
        }

        int size = (i < 0) ? stringSize(-i) + 1 : stringSize(i);
        byte[] buf = getInCache(size);
        //char[] buf =new char[size];
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
            case 5:
                return buf5;
            case 6:
                return buf6;
            case 7:
                return buf7;
            case 8:
                return buf8;
            case 9:
                return buf9;
            case 10:
                return buf10;
            case 11:
                return buf11;
            case 12:
                return buf12;
        }

        throw new RuntimeException("unexpected size");
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
            if (i == 0) {
                break;
            }
        }
        if (sign != 0) {
            buf[--charPos] = sign;
        }
    }

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

    static int stringSize(int x) {
        for (int i = 0; ; i++) {
            if (x <= sizeTable[i]) {
                return i + 1;
            }
        }
    }

    final static int[] sizeTable = {9, 99, 999, 9999, 99999, 999999, 9999999,
        99999999, 999999999, Integer.MAX_VALUE};

}
