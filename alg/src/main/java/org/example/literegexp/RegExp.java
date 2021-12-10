package org.example.literegexp;

import java.util.List;

public class RegExp {

  public static void main(String[] args) {

    var worker = new Solution();

    List.of(
            new TestCase("a", "aa", false),
            new TestCase("a*", "aa", true),
            new TestCase(".*", "ab", true),
            new TestCase("c*a*b", "aab", true),
            new TestCase("mis*is*p*.", "mississippi", false),
            new TestCase("b.", "bb", true),
            new TestCase("b.*", "bbb", true),
            new TestCase("bbb.g*", "bbb", false),
            new TestCase("bbb.g*", "bbbb", true)
    ).stream()
            .filter(v -> worker.isMatch(v.value, v.regexp) != v.isMatch)
            .forEach(RegExp::mock);
  }


  private static void mock(Object o) {
    System.out.println("^^^^^^^^^^");
  }


}
