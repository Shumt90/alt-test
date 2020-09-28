package edu.mergeksortedlists;

public class Main {

  public static void main(String[] args) {
    var sol = new Solution();

    System.out.println(sol.mergeKLists(create(1, 2, 3), create(1, 2, 3)));
    System.out.println(sol.mergeKLists(create(1, 4, 5), create(1, 3, 4), create(2, 6)));
  }

  static ListNode create(int... values) {

    ListNode prev = null;
    for (int i = values.length - 1; i >= 0; i--) {
      prev = new ListNode(values[i], prev);
    }

    return prev;
  }
}
