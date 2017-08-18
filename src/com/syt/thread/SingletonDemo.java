package com.syt.thread;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Think on 2017/8/15.
 */
public class SingletonDemo {

    private static SingletonDemo instance = null;

    private SingletonDemo(){}

    public static SingletonDemo getInstance() {
        try {

            if (instance == null){
                //创建实例之前可能会有一些准备性的耗时工作
                Thread.sleep(300);
                synchronized (SingletonDemo.class) {
                    instance = new SingletonDemo();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return instance;
    }
}
