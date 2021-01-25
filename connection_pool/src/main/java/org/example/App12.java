package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.SneakyThrows;

public class App12 {

  public static void main(String[] args) throws JsonProcessingException, InterruptedException {

    var map = new ConcurrentHashMap<String, AtomicInteger>();

    increment("1", map);

    var t2 = new Thread() {
      @SneakyThrows
      @Override
      public void run() {
        increment("1", map);
      }
    };

    t2.start();

    System.out.println(map); //save to storage
    map.clear();

    TimeUnit.MILLISECONDS.sleep(1000);

    System.out.println(map);


  }

  private static void increment(String ip, Map<String, AtomicInteger> map) throws InterruptedException {
    var counter = map.get(ip);

    if (counter == null) {
      var newCounter = new AtomicInteger(0);

      counter = map.putIfAbsent(ip, newCounter);
      if (counter == null) {
        counter = newCounter;
      }
    }

    TimeUnit.MILLISECONDS.sleep(50);
    counter.incrementAndGet();
  }
}
