package org.example;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class NullTest {

  public static void main(String[] args) {
    Mono.just(1)
        .map(v->null)
        .log()
        .block();
  }
}
