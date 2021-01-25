package org.example;

import java.util.HashMap;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

public class App10 {

  public static void main(String[] args) {

    Mono.just(1)
        .log()
        .then(Mono.just(get()))
        .block();

  }

  private static Integer get() {
    System.out.println("just first");
    return 2;
  }
}
