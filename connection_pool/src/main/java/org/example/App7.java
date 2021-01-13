package org.example;

import java.util.List;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * У нас есть source, который является набором чисел, необходимо поместить числа в редис (cachedDataFromSourceAndGet) и продолжить обработку пайплайны(main)
 */
public class App7 {

  static ReactiveRedisOperations<Long, List<Integer>> reactiveRedisOperations = null;//mock



  public static void main(String[] args) {

    cachedDataFromSourceAndGet()
        .subscribe(System.out::println);

  }

  static Flux<Integer> cachedDataFromSourceAndGet() {
    var source = source();
    saveToRedis(source.share());
    return source;
  }

  static void saveToRedis(Flux<Integer> data){

    data.collectList().doOnNext(System.out::println).block();

  }

  static Flux<Integer> source() {
    return Flux.fromIterable(getList());
  }

  static List<Integer> getList(){
    System.out.println("get list");
    return List.of(1,2);
  }

}
