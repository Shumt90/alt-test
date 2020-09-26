import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Solution {

  public boolean isMatch(String value, String regexp) {

    System.out.println("===========" + value + " === " + regexp);

    var tokens = divide(regexp);

    Allocations[] allocations = new Allocations[tokens.size()];

    int i = 0;
    for (var token : tokens) {
      var allocation = new Allocations(token);
      allocation.combinations = getCombinations(value, token);
      if (!token.wild && allocation.combinations.isEmpty()) {
        return false;
      }
      allocations[i] = allocation;
      i++;
    }

    System.out.println(Arrays.toString(allocations));

    return haveWay(allocations, 0, value.length());

  }

  boolean haveWay(Allocations[] allocations, int offset, int end) {

    if (offset == allocations.length - 1) {
      return areEnd(allocations, offset, end);
    } else {
      for (var allocation : allocations[offset].combinations) {

        if (allocations[offset + 1].token.wild && allocations[offset + 1].combinations.isEmpty()) {
          return haveWay(allocations, offset + 1, end);
        } else {
          for (var allocation2 : allocations[offset + 1].combinations) {
            if (allocation.to - allocation2.from==1 && haveWay(allocations, offset + 1, end)) {
              return true;
            }
          }
        }
      }
    }

    return false;
  }

  boolean areEnd(Allocations[] allocations, int offset, int end) {

    if (allocations[offset].token.wild) {
      if (offset == 0) {
        return false;
      }
      return areEnd(allocations, offset - 1, end);
    }

    for (var allocation : allocations[offset].combinations) {
      if (allocation.to == end) {
        return true;
      }
    }

    return false;
  }


  List<Allocation> getCombinations(String value, Token token) {

    var allocations = new ArrayList<Allocation>();

    if (!token.wild) {

      int maxOffset = value.length() - token.nextCost - token.prevCost - token.value.length();

      for (int offset = 0; offset <= maxOffset; offset++) {

        if (equalsIgnoreDot(value.substring(offset + token.prevCost, offset + token.prevCost + token.value.length()), token.value)) {
          allocations.add(new Allocation(offset + token.prevCost, token.value.length() + token.prevCost));
        }

      }
    } else {
      int maxOffset = value.length() - token.nextCost - token.prevCost;
      for (int i = 0; i < maxOffset; i++) {

        boolean match = true;
        for (int i2 = i; i2 < maxOffset; i2++) {
          if (!equalsIgnoreDot(token.value.charAt(0), value.charAt(token.prevCost + i2))) {
            match = false;
            break;
          }
        }

        if (match) {
          allocations.add(new Allocation(token.prevCost + i, token.prevCost + maxOffset));
        }

      }

    }

    return allocations;
  }

  private boolean equalsIgnoreDot(char a, char b) {
    if (a == '.' || b == '.') {
      return true;
    }
    return a == b;
  }

  private boolean equalsIgnoreDot(String a, String b) {
    if (a.length() != b.length()) {
      return false;
    }

    for (int i = 0; i < a.length(); i++) {
      if (a.charAt(i) != '.' && b.charAt(i) != '.' && a.charAt(i) != b.charAt(i)) {
        return false;
      }
    }

    return true;
  }

  private LinkedList<Token> divide(String regexp) {

    var tokens = new LinkedList<Token>();

    int beg = 0;
    int end = 0;
    for (int i = 0; i < regexp.length(); i++) {
      if (regexp.charAt(i) == '*') {
        if (i - 1 - beg > 0) {
          var token = regexp.substring(beg, i - 1);
          tokens.add(new Token(token.length(), token, false));
        }

        tokens.add(new Token(0, regexp.substring(i - 1, i), true));

        beg = i + 1;
      }
      end = i;

    }

    if (beg <= end) {
      var token = regexp.substring(beg, end + 1);
      tokens.add(new Token(token.length(), token, false));
    }

    int allCost = 0;
    for (var token : tokens) {
      allCost += token.minCost;
    }

    int prevCost = 0;
    for (var token : tokens) {
      allCost -= token.minCost;

      token.nextCost = allCost;
      token.prevCost = prevCost;

      prevCost += token.minCost;
    }

    return tokens;
  }

  private static class Token {

    int minCost;
    String value;
    boolean wild;
    int prevCost;
    int nextCost;

    public Token(int minCost, String value, boolean wild) {
      this.minCost = minCost;
      this.value = value;
      this.wild = wild;
    }

    @Override
    public String toString() {
      return "Token{" +
          "minCost=" + minCost +
          ", value='" + value + '\'' +
          ", wild=" + wild +
          ", prevCost=" + prevCost +
          ", nextCost=" + nextCost +
          '}';
    }
  }

  private static class Allocations {

    Token token;
    List<Allocation> combinations = new ArrayList<>();

    public Allocations(Token token) {
      this.token = token;
    }

    @Override
    public String toString() {
      return "Allocations{" +
          "token=" + token +
          ", \ncombinations=" + combinations +
          '}';
    }
  }

  private static class Allocation {

    int from;
    int to;

    public Allocation(int from, int to) {
      this.from = from;
      this.to = to;
    }

    @Override
    public String toString() {
      return "\nAllocation{" +
          "from=" + from +
          ", to=" + to +
          "}";
    }
  }
}
