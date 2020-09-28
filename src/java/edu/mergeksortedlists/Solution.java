package edu.mergeksortedlists;

/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode() {} ListNode(int val) { this.val = val; } ListNode(int val, ListNode next) { this.val = val; this.next = next; } }
 */
class Solution {

  public ListNode mergeKLists(ListNode... lists) {

    var nullCount = 0;

    ListNode head = null;
    ListNode current = null;
    int minIndex;

    while (true) {

      nullCount = 0;
      minIndex = -1;

      for (int i = 0; i < lists.length; i++) {

        if (lists[i] == null) {
          nullCount++;
          continue;
        }

        if (minIndex == -1) {
          minIndex = i;
          continue;
        }

        if (lists[minIndex].val > lists[i].val) {
          minIndex = i;
        }
      }

      if (nullCount == lists.length) {
        break;
      }

      if (head != null) {
        current.next = lists[minIndex];
        current = current.next;
      } else {
        head = lists[minIndex];
        current = head;
      }

      lists[minIndex] = lists[minIndex].next;

    }

    return head;

  }
}