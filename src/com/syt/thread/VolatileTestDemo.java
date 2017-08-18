package com.syt.thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Think on 2017/8/14.
 */
public class VolatileTestDemo {

    public static int count = 0;

    public static void increase() {

        //这里延迟1毫秒，使得结果明显
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
        }

        count++;

    }

    public static void main(String[] args) {

        //同时启动1000个线程，去进行i++计算，看看实际结果

        ExecutorService excutor = Executors.newCachedThreadPool();

        excutor.execute(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (count != 0) {
                        System.out.println("count != 0");
                    }
                }
            }
        });

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 1000; i++) {
            excutor.execute(new Runnable() {
                @Override
                public void run() {
                    VolatileTestDemo.increase();
                }
            });
        }

        excutor.shutdown();
        try {
            excutor.awaitTermination(60, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:Counter.count=" + VolatileTestDemo.count);
    }
}
