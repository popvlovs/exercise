package com.syt.thread;

/**
 * Created by Think on 2017/8/9.
 */

class Lock1 implements Runnable{
    @Override
    public void run(){
        try{
            System.out.println("Lock1 running");
            while(true){
                synchronized(DeadlockDemo.obj1){
                    System.out.println("Lock1 lock obj1");
                    Thread.sleep(3000);//获取obj1后先等一会儿，让Lock2有足够的时间锁住obj2
                    synchronized(DeadlockDemo.obj2){
                        System.out.println("Lock1 lock obj2");
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
class Lock2 implements Runnable{
    @Override
    public void run(){
        try{
            System.out.println("Lock2 running");
            while(true){
                synchronized(DeadlockDemo.obj2){
                    System.out.println("Lock2 lock obj2");
                    Thread.sleep(3000);
                    synchronized(DeadlockDemo.obj1){
                        System.out.println("Lock2 lock obj1");
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}

public class DeadlockDemo {

    static Object obj1 = new Object();
    static Object obj2 = new Object();

    public static void main(String[] args) {
        Thread t1 = new Thread(new Lock1(), "Thread1");
        t1.start();

        Thread t2 = new Thread(new Lock2(), "Thread2");
        t2.start();
    }
}
