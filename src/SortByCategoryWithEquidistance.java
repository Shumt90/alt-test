import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toMap;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

//TODO: отсортировать лист таким образом, что бы все элементы были равнораспределены по результирующему листу,
// и при повторном запуске цикла результат не менялся
public class SortByCategoryWithEquidistance {

  public static void main(String[] args) {

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
        .forEach(System.out::println);

  }


  private static Comparator<Product> equiDistanceSort(HashMap<String, Integer> categoryWeight, Integer maxDistance) {

    return (productA, productB) -> {

      categoryWeight.computeIfAbsent(productA.category, (v) -> 0);
      categoryWeight.computeIfAbsent(productB.category, (v) -> 0);

      if (productA.compare == null) {
        productA.compare = categoryWeight.get(productA.category);
        categoryWeight.put(productA.category, productA.compare+maxDistance);
      }

      if (productB.compare == null) {
        productB.compare = categoryWeight.get(productB.category);
        categoryWeight.put(productB.category, productB.compare+maxDistance);
      }

      return (productA.compare).compareTo(productB.compare);

    };

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
