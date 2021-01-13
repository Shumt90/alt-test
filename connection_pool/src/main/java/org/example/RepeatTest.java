package org.example;

import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

public class RepeatTest {

  private static final BlockingQueue<Long> QUEUE = new ArrayBlockingQueue<>(1000);

  public static void main(String[] args) throws InterruptedException {
    Flux.generate((SynchronousSink<Long> synchronousSink) -> {
      if (QUEUE.size() == 0) {
        System.out.println("complete");
        synchronousSink.complete();
        return;
      }

      synchronousSink.next(Objects.requireNonNull(QUEUE.poll()));
    })
        .doOnNext(System.out::println)
        .repeatWhen(publisher -> publisher.delayElements(Duration.ofSeconds(3)))
        .subscribe();

    QUEUE.add(1L);
    QUEUE.add(2L);
    QUEUE.add(3L);

    TimeUnit.SECONDS.sleep(5);

    QUEUE.add(4L);

    TimeUnit.HOURS.sleep(1);

  }
}
