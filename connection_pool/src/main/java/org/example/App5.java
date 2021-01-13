package org.example;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class App5 {

  public static void main(String[] args) throws InterruptedException {
    var pub = Flux.just(1, 2)
        .replay(Duration.ofMillis(500));

    pub.connect();
    pub.subscribe(System.out::println);

    TimeUnit.SECONDS.sleep(1);

    pub.subscribe(System.out::println);
  }


}
