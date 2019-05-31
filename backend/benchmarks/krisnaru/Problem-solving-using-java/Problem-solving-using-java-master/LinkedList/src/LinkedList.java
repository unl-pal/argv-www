
public class LinkedList {

	public static void main(String[] args){
		ListNode head1 = constructList1();
//		LinkedNode head2 = constructList2();
//		printList(head1);
//		printList(head2);
//		LinkedNode mergeHead = mergeRecursive(head1, head2);
//		printList(mergeHead);
	//	cloneRandomList(constructRandomList());
		//printList(mergeHead);
		//cloneRandomList(constructRandomList());
		System.out.println(isPalindrome(head1, head1));
	}
	
    public ListNode removeNthFromEnd(ListNode head, int n) {
        if(head == null) return null;
        ListNode fast = head;
        while(n > 0 && fast != null) {
            fast = fast.next;
            n--;
        }
        if(fast == null) return null;
        fast = fast.next;
        ListNode slow = head;
        while(fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        slow.next = slow.next.next;
        return head;
    }
	
	static ListNode getIntersection(ListNode headA, ListNode headB) {
		ListNode p1 = headA;
		ListNode p2 = headB;
	    while(p1 != p2) {
	        p1 = p1 == null ? headB : p1.next;
	        p2 = p2 == null ? headA : p2.next;
	    }
	    return p1;
	}
	
	static ListNode constructRandomList(){
		ListNode head = new ListNode(1);
		head.next = new ListNode(3);
		head.next.next  = new ListNode(5);
		head.next.next.next = new ListNode(7);
		head.next.next.next.rand = head;
		head.next.next.rand = head.next;
		head.next.rand = head;
		head.rand = head.next.next;
		return head;
	}
	
	static boolean isPalindrome(ListNode begin, ListNode end){
		if(end.next == null){
			return begin.data == end.data;
		}
		if(isPalindrome(begin, end.next))
		{
			begin = begin.next;
			return begin.data == end.data;
		}
		return false;
	}
	
	static void cloneRandomList(ListNode head){
		ListNode tempHead = head;
//		LinkedNode clone = new LinkedNode(head.data);
//		LinkedNode tempClone = clone;
//		
		while(tempHead != null){
			ListNode clone = new ListNode(tempHead.data);
			clone.next = tempHead.next;
			tempHead.next = clone;
			
			tempHead = clone.next;
		}
		
		printList(head);
		
		tempHead = head;
		
		while(tempHead!= null && tempHead.next != null){
			tempHead.next.rand = tempHead.rand.next;
			tempHead = tempHead.next.next;
		}
		
		tempHead = head;
		ListNode cloneHead = tempHead.next;
		while(tempHead.next != null){
			ListNode cloneTemp = tempHead.next;
			tempHead.next = tempHead.next.next;
			if(cloneTemp.next != null)
			cloneTemp.next = cloneTemp.next.next;
		}
		
		while(cloneHead != null){
			System.out.print(cloneHead.rand.data+"=>");
			cloneHead = cloneHead.next;
		}
	}
	
	
	static ListNode constructList1(){
		ListNode head = new ListNode(1);
		head.next = new ListNode(0);
		head.next.next  = new ListNode(1);

		return head;
	}
	
	static ListNode constructList2(){
		ListNode head = new ListNode(2);
		head.next = new ListNode(4);
		head.next.next  = new ListNode(6);
		head.next.next.next = new ListNode(8);
		
		return head;
	}
	
	static void printList(ListNode head){
		while(head != null){
			System.out.print(head.data+"=>");
			head = head.next;
		}
		System.out.println();
	}
	
	static ListNode reverse(ListNode head){
		ListNode rev = null;
		if(head.next == null){
			rev =  head;
		}
		else
		{
			rev = reverse(head.next);
			head.next.next = head;
			head.next = null;
		}
		return rev;
	}
	
	static ListNode mergeRecursive(ListNode head1, ListNode head2){
	
		if(head1 == null){
			return head2;
		}
		if(head2 == null){
			return head1;
		}
		ListNode mergeHead = null;
		if(head1.data < head2.data){
		    mergeHead = head1;
			mergeHead.next = mergeRecursive(head1.next, head2);
		}
		else
		{
		    mergeHead = head2;
			mergeHead.next = mergeRecursive(head1, head2.next);
		}
		return mergeHead;
	}
	
	static ListNode merge(ListNode head1, ListNode head2){

		if(head1 == null){
			return head2;
		}
		if(head2 == null){
			return head1;
		}
		ListNode head3 = null;
		if(head1.data < head2.data){
			head3 = head1;
			head1 = head1.next;
		}
		else{
			head3 = head2;
			head2 = head2.next;
		}
		
		ListNode temp = head3;
		while(head1 != null && head2 != null){
			if(head1.data < head2.data){
				temp.next = head1;
				head1 = head1.next;
			}
			else{
				temp.next = head2;
				head2 = head2.next;
			}
			temp = temp.next;
		}
		while(head1 != null){
			temp.next = head1;
			head1 = head1.next;
			temp = temp.next;
		}
		
		while(head2 != null){
			temp.next = head2;
			head2 = head2.next;
			temp = temp.next;
		}
		
		return head3;
	}
}

class ListNode{
	int data;
	ListNode next;
	ListNode rand;
	public ListNode(int data){
		this.data = data;
	}
}
