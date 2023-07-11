package xingyu.lu.lab.base;


public class DesignPattern {


    static class SingletonPattern {

       private volatile static SingletonPattern singletonObj;

       public static SingletonPattern getInstance(){
           if (null== singletonObj){
               synchronized (SingletonPattern.class){
                   return new SingletonPattern();
               }
           }
           return singletonObj;
       }
    }




}
