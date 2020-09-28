package org.example.sergey;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class SolutionSergey {


  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(SolutionSergey.class.getSimpleName())
        .forks(1)
        .build();

    new Runner(opt).run();
  }


  /**
   * отсортировать лист таким образом, что бы все элементы были равнораспределены по результирующему листу, и при повторном запуске цикла результат не менялся
   * <p>
   * Выборка категории 1,1,1,2,2,3 Неправильно: категория 1, 1 .... т.к. две идут подряд Неправильно: категория 1, 2, 1, .... т.к. 1 повторилась до того как вставлено 3
   * <p>
   * Правильно н-р 1, 2, 3, 1, 2, 1
   */
  @Benchmark
  public static void test() {

    var data = List.of(
        new Product("продукт 1", "категория 1"),
        new Product("продукт 2", "категория 1"),
        new Product("продукт 3", "категория 1"),
        new Product("продукт 4", "категория 2"),
        new Product("продукт 5", "категория 2"),
        new Product("продукт 6", "категория 3")
    );

    newSort(data);
  }

  static void newSort(List<Product> products) {
    Set<Integer> set = new HashSet<>();
    List<ArrayList<Integer>> list = new ArrayList<>();
    List<Integer> result = new ArrayList<>();
    List<Product> resultProd = new ArrayList<>();
    for (int i = 0; i < products.size(); i++) {
      set.add(Integer.parseInt(products.get(i).toString().substring(20)));
    }
    for (int i = 0; i < products.size(); i++) {
      ArrayList<Integer> list1 = new ArrayList<>();
      for (Integer integer : set) {
        list1.add(integer);
        set.remove(integer);
        list.add(list1);
        break;
      }
    }
    for (int i = 0; i < products.size(); i++) {
      for (int j = 0; j < list.size(); j++) {
        if (Integer.parseInt(products.get(i).toString().substring(20)) == list.get(j).get(0)) {
          list.get(j).add(Integer.parseInt(products.get(i).toString().substring(20)));
        }
      }
    }
    int maxSize = 0;
    for (int i = 0; i < list.size(); i++) {
      maxSize += list.get(i).size();
      list.get(i).remove(0);
    }
    maxSize = maxSize - list.size();
    int i = 0;
    while (result.size() < maxSize) {
      if (!list.get(i).isEmpty()) {
        result.add(list.get(i).remove(0));
      }
      i++;
      if (i == list.size()) {
        i = 0;
      }
    }
    //System.out.println(result);
    for (int y = 0; y < products.size(); y++) {
      for (int y1 = 0; y1 < products.size(); y1++) {
        if (Integer.parseInt(products.get(y1).toString().substring(20)) == result.get(y)) {
          resultProd.add(products.get(y1));
          break;
        }
      }
    }
   /* for(int h = 0; h < resultProd.size(); h++){
      System.out.println(resultProd.get(h).toString());
    }*/
  }

  /**
   * Можно менять
   */
  public static class Product {

    private final String value;
    private final String category;

    public Product(String value, String category) {
      this.value = value;
      this.category = category;
    }

    public String getValue() {
      return value;
    }

    public String getCategory() {
      return category;
    }

    @Override
    public String toString() {
      return value + " " + category;
    }

  }
}