package org.example.interview;

import java.util.concurrent.TimeUnit;

/**
 * Какой лаг будет между 'updated' and 'got update'
 */
public class WhenWillUpdated {

  static int b;

  public static void main(String[] args) throws InterruptedException {

    Thread t1 = new Thread(() -> {

      while (b == 0) {
      }

      System.out.println("got update");

    });

    Thread t2 = new Thread(() -> {
      b = 1;
      System.out.println("updated");
    });
    t1.start();
    TimeUnit.SECONDS.sleep(1);
    t2.start();

    while (t2.isAlive()) {
    }

    System.out.println(String.format("in main thread b: %d", b));

    while (t1.isAlive()) {
    }

  }

}
