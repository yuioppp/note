
```java
class Solution {
    public ListNode reverse(ListNode head,ListNode tail) {
        ListNode pre = null;
        while (head != tail) {
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if(head==null||head.next==null)
            return head;
        ListNode tail=head;
        for(int i=0;i<k;i++){
            if(tail==null)
                return head;
            tail=tail.next;
        }
        ListNode res=reverse(head,tail);
        head.next=reverseKGroup(tail,k);
        return res;
    }
}
```
