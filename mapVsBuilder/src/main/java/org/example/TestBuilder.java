package org.example;

import org.example.model.Data;

public class TestBuilder {

  public static void test(){

    for (int i = 0; i < 1_000_000; i++) {
      var v= Data.builder().value(i).build();
      if (v.getValue()==Integer.MAX_VALUE){
        System.out.println("against jit optimization");
      }
    }

  }
}
