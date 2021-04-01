package xingyu.lu.lab.list.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import xingyu.lu.lab.list.SinglyLinkedList;

/**
 * @author xingyu.lu
 **/
public class SinglyLinkedListTest {

    @Test
    public void testSinglyLinkedList() {
        SinglyLinkedList<String> linkedList = new SinglyLinkedList<String>();
        System.out.println("Init | " + linkedList.toString());
        linkedList.insert("a");
        linkedList.insert("b");
        linkedList.insert("c");
        linkedList.insert("d");
        linkedList.insert("e");
        linkedList.insert("f");
        linkedList.insert("g");
        System.out.println("Add | " + linkedList.toString());
        assert (!linkedList.isEmpty());
        assert (linkedList.size() == 7);
        String sStr = linkedList.search(3);
        assert (sStr.equals("d"));
        int sIndex = linkedList.search("d");
        assert (sIndex == 3);
        String rmStr = linkedList.remove(3);
        System.out.println("Remove Index 3 | " + linkedList.toString());
        assert (rmStr.equals("d"));
        assert (linkedList.size() == 6);
        String curStr = linkedList.search(3);
        assert (curStr.equals("e"));
        System.out.println(linkedList.toString());
    }
}
