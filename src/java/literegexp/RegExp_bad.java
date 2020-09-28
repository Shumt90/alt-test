package literegexp;

import java.util.List;

public class RegExp_bad {

  public static void main(String[] args) {

    var worker = new Solution_bad();

    List.of(new TestCase("b.", "bb", true),
            new TestCase("b.", "bbb", false),
            new TestCase("*b.", "bb", true),
            new TestCase("b.*", "bbb", true),
            new TestCase("*11*11*.2.", "1111023", true),
            new TestCase("*22*11*.2.", "22211023", true),
            new TestCase("*11*11*.2.", "11111111111111023", true),
            new TestCase("*11*11*.2.", "111111111111110233", false),
            new TestCase("*.......", "111", false)
    ).stream()
        .filter(v -> worker.isMatch(v.value, v.regexp) != v.isMatch)
        .forEach(System.out::println);
  }


}
