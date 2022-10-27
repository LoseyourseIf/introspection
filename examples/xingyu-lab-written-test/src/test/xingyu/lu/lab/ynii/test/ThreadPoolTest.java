package xingyu.lu.lab.ynii.test;

import org.junit.Test;
import xingyu.lu.lab.threadpool.ThreadPoolService;

public class ThreadPoolTest {

    class subTask implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " subTask test ");
        }
    }

    @Test
    public void test() {
        for (int i = 0; i < 1000; i++) {
            ThreadPoolService.executeRunnable(new subTask());
        }
    }

}
