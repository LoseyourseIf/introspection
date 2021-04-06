package xingyu.lu.lab.ynii;


/**
 * 笔试 单链表
 * @author xingyu.lu
 **/
public class SinglyLinkedList<T> {

    private int listSize;
    private ListNode<T> head;
    private int index;

    private class ListNode<T> {

        ListNode<T> next;
        T item;

        public ListNode(T item) {
            this.item = item;
        }

        @Override
        public String toString() {
            if (null == this.next) {
                return this.item.toString();
            } else {
                return this.item.toString() + " , " + this.next.toString();
            }
        }

        private void link(T data) {
            if (this.next == null) {
                this.next = new ListNode<T>(data);
            } else {
                this.next.link(data);
            }
        }

        private T remove(ListNode<T> prev, int index) {
            if (SinglyLinkedList.this.index == index) {
                prev.next = this.next;
                this.next = null;
                SinglyLinkedList.this.listSize--;
                return this.item;
            } else {
                SinglyLinkedList.this.index++;
                return this.next.remove(this, index);
            }
        }

        private int get(T data) {
            if (data.equals(this.item)) {
                return SinglyLinkedList.this.index;
            }
            if (null == this.next) {
                return -1;
            }
            SinglyLinkedList.this.index++;
            return this.next.get(data);
        }

        private T get(int index) {
            if (SinglyLinkedList.this.index == index) {
                return this.item;
            } else {
                SinglyLinkedList.this.index++;
                return this.next.get(index);
            }
        }

    }

    //检查是否为空
    public boolean isEmpty() {
        return listSize == 0 || this.head == null;
    }

    //获取长度
    public int size() {
        return listSize;
    }

    //插入
    public int insert(T data) {
        if (this.isEmpty()) {
            this.head = new ListNode<T>(data);
        } else {
            this.head.link(data);
        }
        return listSize++;
    }

    //删除
    public T remove(int index) {
        if (this.isEmpty()) {
            return null;
        }
        if (index < 0 || this.listSize <= index) {
            throw new IllegalArgumentException();
        }
        if (index == 0) {
            ListNode<T> temp = this.head;
            this.head = this.head.next;
            temp.next = null;
            this.listSize--;
            return temp.item;
        } else {
            this.index = 0;
            return this.head.remove(this.head, index);
        }
    }

    //查找
    public int search(T data) {
        if (null == data) {
            return -1;
        }
        if (this.isEmpty()) {
            return -1;
        }
        this.index = 0;
        return this.head.get(data);
    }

    //查找
    public T search(int index) {
        if (index < 0 || this.listSize <= index) {
            throw new IllegalArgumentException();
        }
        if (this.isEmpty()) {
            return null;
        }
        this.index = 0;
        return this.head.get(index);
    }

    @Override
    public String toString() {
        if (this.isEmpty()) {
            return "SinglyLinkedList{}";
        }
        return "SinglyLinkedList{" + this.head.toString() + "}";
    }
}
