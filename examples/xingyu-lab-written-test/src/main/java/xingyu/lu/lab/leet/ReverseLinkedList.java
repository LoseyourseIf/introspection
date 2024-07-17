package xingyu.lu.lab.leet;

/**
 * @Description: ReverseLinkedList
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-07-16  22:54
 */
public class ReverseLinkedList {

    static class ListNode {
        int val;
        ListNode next;
        ListNode() {
        }
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            return "ListNode{" +
                    "val=" + val +
                    ", next=" + next +
                    '}';
        }
    }

    public static ListNode reverseList(ListNode head) {
        System.out.println("HEAD = " + head);
        if (null == head || null == head.next) {
            return head; // 递归终止条件
        }
        ListNode newHead = reverseList(head.next);
        System.out.println("NEW HEAD = " + newHead);
        System.out.println("BEFORE REVERSE = " + head);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public static void main(String[] args) {

        ListNode head = new ListNode(1,
                new ListNode(2,
                        new ListNode(3,
                                new ListNode(4,
                                        new ListNode(5, null)))));

        ListNode newHead = reverseList(head);
        System.out.println(newHead);

    }

}
