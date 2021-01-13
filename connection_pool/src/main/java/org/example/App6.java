package org.example;

import java.util.List;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;


/**
 * У нас есть source, который является набором чисел, необходимо поместить числа в редис (cachedDataFromSourceAndGet) и продолжить обработку пайплайны(main)
 */
public class App6 {

  static ReactiveRedisOperations<Long, List<Integer>> reactiveRedisOperations = null;//mock

  public static void main(String[] args) {

    cachedDataFromSourceAndGet()
        .log()
        .subscribe();

  }

  static Flux<Integer> cachedDataFromSourceAndGet() {
    return Flux.from(source()
                         .collectList()
                         .doOnNext(v -> reactiveRedisOperations.opsForValue().set(1L, v)))
        .flatMap(v -> Flux.fromStream(v.stream()));
  }

  static Flux<Integer> source() {
    return Flux.just(2, 4);
  }

}
