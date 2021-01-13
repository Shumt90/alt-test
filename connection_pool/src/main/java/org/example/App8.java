package org.example;

import java.util.List;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import reactor.core.publisher.Flux;


public class App8 {

  public static void main(String[] args) {

    System.out.println("Division: " +  ((double)2 / 5) + ",\nbinary remainder operator: " + ((double)2 % 5) +",\nbinary remainder operator on binary operator: "+(0b10 % 0b101));

  }

}
