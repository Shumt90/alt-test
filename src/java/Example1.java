package data;

import java.util.concurrent.TimeUnit;

public class Example1 {

  static int a, b;

  public static void main(String[] args) throws InterruptedException {

    Thread t1 = new Thread(() -> {

      int r2 = b;

      if (r2 != 0) {
        a = 42;
      }

      System.out.println(String.format("a: %d, b: %d", a, b));

    });

    Thread t2 = new Thread(() -> {
      int r1 = a;

      if (r1 != 0) {
        b = 42;
      }

      System.out.println(String.format("a: %d, b: %d", a, b));
    });
    t1.start();
    TimeUnit.SECONDS.sleep(2);
    t2.start();

    while (t2.isAlive()) {
    }

    while (t1.isAlive()) {
    }

  }
}
