package org.example;

import java.util.Map;
import java.util.stream.Stream;
import org.example.model.Data;

public class TestMap {

  public static void test() {

    for (int i = 0; i < 1_000_000; i++) {
      var v = fromMap(Map.of("value", i));
      if (v.getValue() == Integer.MAX_VALUE) {
        System.out.println("against jit optimization");
      }
    }

  }

  public static Data fromMap(Map<String, Object> params) {
    var request = new Data();
    Stream.of(request.getClass().getDeclaredFields())
        .forEach(field -> {
          var fieldName = field.getName();
          if (params.containsKey(fieldName)) {
            try {
              var value = params.get(fieldName);
              field.setAccessible(true);
              field.set(request, value);
            } catch (IllegalAccessException e) {
              System.out.println("sock");
            }
          }
        });
    return request;
  }
}
