package org.example;

import java.util.HashMap;
import java.util.Map;

public class App9 {

  public static void main(String[] args) {
    var m = new HashMap<String , Object>(){
      {
        put("",null);
      }
    };
    m.put("",null);
  }
}
