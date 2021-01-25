package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fm.finch.json.json.Json;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Data;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class PutMap {


  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(PutMap.class.getSimpleName())
        .forks(1)
        .build();

    new Runner(opt).run();
  }


  @Benchmark
  public static void juggernaults_withOneKey() throws JsonProcessingException {

    Map<Integer, AtomicInteger> map = new HashMap<>();

    for (int i = 0; i < 1_000_000; i++) {

      var key = Integer.valueOf(1); //boxing

      if (map.get(key) == null){
        map.putIfAbsent(key, new AtomicInteger(0));
      }
      map.get(key).incrementAndGet();
    }
  }

  @Benchmark
  public static void juggernaults_withManyKeys() throws JsonProcessingException {

    Map<Integer, AtomicInteger> map = new HashMap<>();

    for (int i = 0; i < 1_000_000; i++) {

      var key = Integer.valueOf(i); //boxing

      if (map.get(key) == null){
        map.putIfAbsent(key, new AtomicInteger(0));
      }
      map.get(key).incrementAndGet();
    }
  }

  @Benchmark
  public static void kirill_withManyKeys() throws JsonProcessingException {
    Map<Integer, AtomicInteger> map = new HashMap<>();

    for (int i = 0; i < 1_000_000; i++) {

      var key = Integer.valueOf(i); //boxing

      var value = map.get(key);

      if (value==null){
        map.put(key, new AtomicInteger(1));
      }else {
        value.incrementAndGet();
      }
    }
  }

  @Benchmark
  public static void kirill_withOneKey() throws JsonProcessingException {
    Map<Integer, AtomicInteger> map = new HashMap<>();

    for (int i = 0; i < 1_000_000; i++) {

      var key = Integer.valueOf(1); //boxing

      var value = map.get(key);

      if (value==null){
        map.put(key, new AtomicInteger(1));
      }else {
        value.incrementAndGet();
      }
    }
  }


}
