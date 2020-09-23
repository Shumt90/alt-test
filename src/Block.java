import java.util.concurrent.TimeUnit;

public class Block {

  static volatile int i;

  public static void main(String[] args) {

    Object m1=new Object();

    Thread t1=new Thread(){
      @Override
      public void run() {
        for (int i=0;i<100_000_000; ++i){
          Block.i++;
        }
      }
    };
    t1.start();

    Thread t2=new Thread(){
      @Override
      public void run() {
        for (int i=0;i<100_000_000; ++i){
          Block.i++;
        }
      }
    };
    t2.start();

    while (t2.isAlive()||t1.isAlive()){

    }

    System.out.println(i);

  }



}
