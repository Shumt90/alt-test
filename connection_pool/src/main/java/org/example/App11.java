package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import reactor.core.publisher.Sinks;

public class App11 {

  public static void main(String[] args) throws JsonProcessingException {

    var om = new ObjectMapper();


    var map1 = new ConcurrentHashMap<String, AtomicInteger>();
    var map2 = new HashMap<String, Integer>();

    map1.put("1", new AtomicInteger(1));
    map2.put("1", 1);


    var json1 = om.writeValueAsString(map1);
    var json2 = om.writeValueAsString(map2);

    System.out.println(json1.equals(json2));

  }
}
