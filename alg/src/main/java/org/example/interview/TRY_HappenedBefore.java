package org.example.interview;

public class TRY_HappenedBefore {

  static int a = 2;
  static volatile int b = 3;

  public static void main(String[] args) throws InterruptedException {

    Thread t1 = new Thread() {
      @Override
      public void run() {
        a = 4;
        b = 7;
      }
    };
    Thread t2 = new Thread() {
      @Override
      public void run() {
        int c = b;
        int k = a;

        System.out.println("c=" + c + " k=" + k);
      }

    };
    t2.start();
    t1.start();

    while (t1.isAlive() || t2.isAlive()) {

    }

  }
}
