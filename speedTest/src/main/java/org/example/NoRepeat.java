package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.function.Function;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class NoRepeat {

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(NoRepeat.class.getSimpleName())
        .forks(1)
        .build();

    new Runner(opt).run();
  }

  @Benchmark
  public static void test() {
    try {
      NoRepeat.results(NoRepeat::countSort);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  static class Pair implements Comparable<Pair> {

    public Pair(String item, Integer count) {
      this.item = item;
      this.count = count;
    }

    private String item;
    private Integer count;

    @Override
    public int compareTo(Pair o) {
      if (o.count.equals(this.count)) {
        return 1;
      }

      return o.count.compareTo(count);
    }

    @Override
    public boolean equals(Object obj) {
      Pair o = (Pair) obj;
      return o.item.equals(this.item);
    }

    @Override
    public int hashCode() {
      return this.item.hashCode();
    }
  }

  public static List<String> swapSort(List<String> target) {
    target = new ArrayList<>(target);//ебаная джава
    String current = null;
    String next = null;
    for (int i = 0; i < target.size() - 1; i++) {
      current = target.get(i);
      next = target.get(i + 1);
      if (current.equals(next)) {
        if (!swap(target, next, i + 1)) {
          throw new RuntimeException("No solution");
        }
      }
    }

    return target;
  }

  private static boolean swap(List<String> target, String element, int index) {
    if (trySwapRight(target, element, index, index + 1)) {
      return true;
    } else {
      return trySwapLeft(target, element, index, index - 2);
    }
  }


  private static boolean trySwapLeft(List<String> target, String element, int index, int leftIndex) {
    String current = null;
    if (leftIndex < 0) {
      return false;
    }
    current = target.get(leftIndex);
    if (!current.equals(element) && !target.get(leftIndex + 1).equals(element) && (leftIndex == 0 || !target.get(leftIndex - 1).equals(element))) {
      target.set(index, current);
      target.set(leftIndex, element);
      return true;
    } else {
      return trySwapLeft(target, element, index, --leftIndex);
    }
  }

  private static boolean trySwapRight(List<String> target, String element, int index, int rightIndex) {
    String current = null;
    if (rightIndex >= target.size()) {
      return false;
    }
    current = target.get(rightIndex);
    if (!doSwap(target, element, current, index, rightIndex)) {
      return trySwapRight(target, element, index, ++rightIndex);
    } else {
      return true;
    }
  }

  // 2 3 3 1 1 1
  private static boolean doSwap(List<String> target, String element, String current, int index, int targetIndex) {

    if (!element.equals(current)) {
      target.set(index, current);
      target.set(targetIndex, element);
      return true;
    }
    return false;
  }

  public static List<String> countSort(List<String> target) {

    List<String> result = new ArrayList<>();
    Map<String, Integer> cntTable = new HashMap<>();
    TreeSet<Pair> pairs = new TreeSet<>();

    target.forEach(item -> {
      if (cntTable.containsKey(item)) {
        cntTable.put(item, cntTable.get(item) + 1);
      } else {
        cntTable.put(item, 1);
      }
    });

    cntTable.forEach((k, v) ->
                     {
                       pairs.add(new Pair(k, v));
                     }
    );

    int itemsCnt = 0;
    while (true) {
      for (Pair p : pairs) {
        if (p.count == 0) {
          itemsCnt++;
          continue;
        }
        if (!result.isEmpty() && p.item.equals(result.get(result.size() - 1))) {
          throw new RuntimeException("No solution");
        }
        result.add(p.item);
        p.count--;
      }
      if (itemsCnt >= pairs.size()) {
        break;
      }
    }

    return result;

  }

  public static void results(Function<List<String>, List<String>> sortMethod) {
    var target = List.of("1", "1", "1", "2", "2", "3");
    sortMethod.apply(target);
  }

}