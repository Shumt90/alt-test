package mergeksortedlists

type ListNode struct {
	Val  int
	Next *ListNode
}

func mergeKLists(lists []*ListNode) *ListNode {

	var nullCount = 0

	var head *ListNode = nil
	var current *ListNode = nil
	var minIndex int

	for {

		nullCount = 0
		minIndex = -1

		for i := 0; i < len(lists); i++ {

			if lists[i] == nil {
				nullCount++
				continue
			}

			if minIndex == -1 {
				minIndex = i
				continue
			}

			if lists[minIndex].Val > lists[i].Val {
				minIndex = i
			}
		}

		if nullCount == len(lists) {
			break
		}

		if head != nil {
			current.Next = lists[minIndex]
			current = current.Next
		} else {
			head = lists[minIndex]
			current = head
		}

		lists[minIndex] = lists[minIndex].Next

	}

	return head

}
