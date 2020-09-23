package org.example;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

//TODO: отсортировать лист таким образом, что бы все элементы были равнораспределены по результирующему листу,
// и при повторном запуске цикла результат не менялся
public class SortByCategoryWithEquidistance {


  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(SortByCategoryWithEquidistance.class.getSimpleName())
        .forks(1)
        .build();

    new Runner(opt).run();
  }


/*  public static void main(String[] args) {
    test();
  }*/


  @Benchmark
  public static void test() {

    HashMap<String, Integer> categoryWeight = new HashMap<>();

    var data = List.of(
        new Product("продукт 1", "категория 1"),
        new Product("продукт 2", "категория 1"),
        new Product("продукт 3", "категория 1"),
        new Product("продукт 4", "категория 2"),
        new Product("продукт 5", "категория 2"),
        new Product("продукт 6", "категория 3")
    );

    data.stream()
        .sorted(equiDistanceSort(categoryWeight, data.size()))
        .forEach(SortByCategoryWithEquidistance::mock);

  }

  private static void mock(Product product) {

  }

  private static Comparator<Product> equiDistanceSort(HashMap<String, Integer> categoryWeight, Integer maxDistance) {

    return (productA, productB) -> {

      if (productA.compare == null) {
        upStep(productA, categoryWeight, maxDistance);
      }

      if (productB.compare == null) {
        upStep(productB, categoryWeight, maxDistance);
      }

      return (productA.compare).compareTo(productB.compare);

    };

  }

  private static void upStep(Product product, HashMap<String, Integer> categoryWeight, Integer maxDistance){
    if (product.compare == null) {

      var step = categoryWeight.get(product.category);

      product.compare = step==null?0:step;

      categoryWeight.put(product.category, product.compare+maxDistance);

    }
  }

  static class Product {

    private final String value;
    private final String category;
    public Integer compare;

    public Product(String value, String category) {
      this.value = value;
      this.category = category;
    }

    public String getCategory() {
      return category;
    }

    @Override
    public String toString() {
      return "Product{" +
          "value='" + value + '\'' +
          ", category='" + category + '\'' +
          ", compare=" + compare +
          '}';
    }
  }
}
