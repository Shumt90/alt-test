import java.util.List;

//TODO:
public class SortByCategoryWithEquidistanceFormalization {


  /**
   * отсортировать лист таким образом, что бы все элементы были равнораспределены по результирующему листу,
   * и при повторном запуске цикла результат не менялся
   *
   * Выборка категории 1,1,1,2,2,3
   * Неправильно: категория 1, 1 .... т.к. две идут подряд
   * Неправильно: категория 1, 2, 1, .... т.к. 1 повторилась до того как вставлено 3
   *
   * Правильно н-р 1, 2, 3, 1, 2, 1
   */
  public static void main(String[] args) {

    var data = List.of(
        new Product("продукт 1", "категория 1"),
        new SortByCategoryWithEquidistance.Product("продукт 2", "категория 1"),
        new SortByCategoryWithEquidistance.Product("продукт 3", "категория 1"),
        new SortByCategoryWithEquidistance.Product("продукт 4", "категория 2"),
        new SortByCategoryWithEquidistance.Product("продукт 5", "категория 2"),
        new SortByCategoryWithEquidistance.Product("продукт 6", "категория 3")
    );

    //System.out.println(sortedData); //TODO

  }


  /**
   * Можно менять
   */
  static class Product {

    private final String value;
    private final String category;

    public Product(String value, String category) {
      this.value = value;
      this.category = category;
    }
  }
}
