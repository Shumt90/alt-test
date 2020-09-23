import java.util.Arrays;
import java.util.concurrent.locks.LockSupport;

public class WordTearing {

  static volatile String b ;

  public static void main(String[] args) {

    Thread t1 = new Thread(() -> {
      for (int i = 0; i < 1000000; ++i) {

        if (compare()) {
          throw new RuntimeException(String.format("iteration %d value %s", i, b));
        }

        b = "123";

      }
    });

    Thread t2 = new Thread(() -> {
      for (int i = 0; i < 1000000; ++i) {

        if (compare()) {
          throw new RuntimeException(String.format("iteration %d value %s", i, b));
        }

        b = "456";
      }
    });
    t1.start();
    t2.start();

    while (t2.isAlive() || t1.isAlive()) {

    }


  }

  private synchronized static boolean compare(){
    return (b!=null )&&(!"123".equals(b)) && (!"456".equals(b));
  }


}
