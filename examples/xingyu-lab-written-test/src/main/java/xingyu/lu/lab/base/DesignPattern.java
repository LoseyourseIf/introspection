package xingyu.lu.lab.base;


public class DesignPattern {


    static class SingletonPattern {
        /*
         * (╯‵□′)╯︵┻━┻
         * volatile 禁止指令重排序
         * 1.多线程间的可见性
         * 2.不保证原子性
         * 3.禁止指重排
         * @auther: xingyu.lu
         * @date: 2023/7/11 14:17
         */
       private volatile static SingletonPattern singletonObj;

       public static SingletonPattern getInstance(){
           if (null== singletonObj){
               /*
                * (╯‵□′)╯︵┻━┻
                * 一个变量在同一个时刻只允许一条线程对其进行 lock 操作
                * @return: xingyu.lu.lab.base.DesignPattern.SingletonPattern
                * @auther: xingyu.lu
                * @date: 2023/7/11 14:18
                */
               synchronized (SingletonPattern.class){
                   return new SingletonPattern();
               }
           }
           return singletonObj;
       }
    }

}
