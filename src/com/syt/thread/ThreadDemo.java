package com.syt.thread;

/**
 * Created by Think on 2017/8/9.
 */
class RunnableDemo implements Runnable {
    private Thread t;
    private String threadName;

    RunnableDemo(String name) {
        threadName = name;
    }

    synchronized public void run() {
        try {
            while (true) {
                System.out.println("Running " + threadName);
                // 让线程睡眠一会
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Thread " + threadName + " interrupted.");
        }
        System.out.println("Thread " + threadName + " exiting.");
    }

    public void start() {
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    public void join() throws InterruptedException {
        System.out.println("Waiting for " + threadName);
        if (t != null)
        {
            t.join();
        }
    }
}

public class ThreadDemo {

    public static void main(String args[]) throws InterruptedException {
        Thread t1 = new Thread(new RunnableDemo("Thread-1"), "Thread-1");
        t1.start();

        Thread t2 = new Thread(new RunnableDemo("Thread-2"), "Thread-2");
        t2.start();

        Thread.sleep(3000);
        Thread.sleep(3000);

        Thread.sleep(3000);
        t1.start();
    }
}