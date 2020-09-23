import java.util.concurrent.TimeUnit;

/**
 * Why we can see writes but not reads
 */
public class Example2 {

  static int a, b;

  public static void main(String[] args) throws InterruptedException {

    Thread t1 = new Thread(() -> {
      //int r=b;
      long i=0;
      a = 41;

      while (b!=41) {

      }

    });

    Thread t2 = new Thread(() -> {
      //int r=a;
      long i=0;
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      b = 41;

      while (a!=41) {

      }
    });
    Thread t3 = new Thread(() -> {
      while (true) {
        try {
          TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

        System.out.println(String.format("a: %d, b: %d", a,b));
      }
    });
    t1.start();
    t2.start();
   // t3.start();

    while (t2.isAlive()) {
    }

    while (t1.isAlive()) {
    }
    System.out.println("end");

  }
}
