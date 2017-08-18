package com.syt.thread;

/**
 * Created by Think on 2017/8/10.
 */

class PrintRunable implements Runnable {
    private Printer printer;

    public PrintRunable() {
        printer = new Printer();
    }

    @Override
    public void run() {
        String threadName = Thread.currentThread().getName();

        if (threadName.equals("PRINT52")) {

            printer.print52();
        } else if (threadName.equals("PRINTAZ")) {

            printer.printAZ();
        }
    }
}

class Printer {
    final static Object locker = new Object();

    public Printer() {
    }

    public void print52() {
        synchronized (locker) {
            for (int i = 0; i < 52; ++i) {
                System.out.println(i);

                try {
                    locker.notify();
                    locker.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            locker.notify();
        }
    }

    public void printAZ() {
        synchronized (locker) {
            for (int i = 0; i < 52; ++i) {
                char letter = i>25 ? (char)(i+39) : (char)(i+97);

                System.out.println(letter);

                try {

                    locker.notify();
                    locker.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            locker.notify();
        }
    }
}

public class ThreadPrintAZ52 {
    public static void main(String args[]) {
        Thread t1 = new Thread(new PrintRunable(), "PRINT52");
        t1.start();

        Thread t2 = new Thread(new PrintRunable(), "PRINTAZ");
        t2.start();
    }
}
