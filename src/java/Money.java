package data;

import java.util.Random;

public class Money {

  public static void main(String[] args) {

    int total = 0;

    int one = 0;

    Random random = new Random();

    for (int i = 0; i < 1000; ++i) {

      total++;

      int cur = 0;

      for (int j = 0; j < 10; ++j) {
        cur = random.nextInt(2);
      }

      if (cur == 1) {
        one++;
      }

    }

    System.out.println(String.format("Total %d one %d", total, one));

  }


}

