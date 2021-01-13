package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fm.finch.json.json.Json;
import java.util.ArrayList;
import lombok.Data;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class StringArrayOptimization {

  static ObjectMapper om = new ObjectMapper();


  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(StringArrayOptimization.class.getSimpleName())
        .forks(1)
        .build();

    new Runner(opt).run();
  }

  @Benchmark
  public static void finchJsonSimple() throws JsonProcessingException {
    var data = new A();
    data.list= Json.parse(testData());
    var stingData = om.writeValueAsString(data);
  }

  @Benchmark
  public static void primitive() throws JsonProcessingException {
    var data = new B();
    data.list= om.readTree(testData());
    var stingData = om.writeValueAsString(data);
  }

  @Benchmark
  public static void kirill() throws JsonProcessingException {
    var data = new C();
    data.list= getArrayFromString(testData());
    var stingData = om.writeValueAsString(data);
  }

  @Data
  static class A{
    Json list;
  }

  @Data
  static class B{
    JsonNode list;
  }

  @Data
  static class C{
    Object list;
  }

  static String testData(){
    return "[[10,11,12,13,14,15,17,16],[2]]";
  }

  private static Object getArrayFromString(String data) {
    if (data == null) {
      return new Object[]{};
    } else {
      var chars = data.toCharArray();
      var arr = new ArrayList<>();
      var accum = new ArrayList<>();
      var numberBuilder = new StringBuilder();
      for (var i = 1; i < chars.length - 1; i++) {
        var ch = chars[i];
        if (']' == ch) {
          accum.add(Long.valueOf(numberBuilder.toString()));
          numberBuilder.setLength(0);
          arr.add(accum);
          accum = new ArrayList<>();
        } else {
          if (',' == ch) {
            if (numberBuilder.length() > 0) {
              accum.add(Long.valueOf(numberBuilder.toString()));
              numberBuilder.setLength(0);
            }
          } else {
            if('[' != ch) {
              numberBuilder.append(ch);
            }
          }
        }

      }
      return arr;
    }
  }
}
