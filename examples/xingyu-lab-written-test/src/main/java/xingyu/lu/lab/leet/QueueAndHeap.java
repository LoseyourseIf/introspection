package xingyu.lu.lab.leet;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @Description:
 * @Version: 1.0
 * @Author: XingYu.Lu
 * @CreateTime: 2024-07-17  15:17
 */
public class QueueAndHeap {


    public static void main(String[] args) {

        Deque<Integer> deque = new ArrayDeque<>();
        // FIFO Queue
        deque.offer(1);
        deque.offer(2);
        deque.offer(3);
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());
        System.out.println(deque.pollFirst());


        // LIFO Heap
        deque.offer(1);
        deque.offer(2);
        deque.offer(3);
        System.out.println(deque.pollLast());
        System.out.println(deque.pollLast());
        System.out.println(deque.pollLast());

    }
}
