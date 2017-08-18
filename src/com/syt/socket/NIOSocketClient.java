package com.syt.socket;

import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by Think on 2017/8/15.
 */
public class NIOSocketClient {
    private static ExecutorService tp = Executors.newCachedThreadPool();
    private static final int sleep_time = 1000 * 1000 * 1000;

    public static void main(String[] args) {
        tp.execute(new EchoClient());
    }


    public static class EchoClient implements Runnable {
        private Socket client = null;
        private PrintWriter writer = null;

        public void run() {
            try {
                client = new Socket();
                client.connect(new InetSocketAddress("localhost", 8000));
                writer = new PrintWriter(client.getOutputStream(), true);
                writer.print("H");
                LockSupport.parkNanos(sleep_time);
                writer.print("e");
                LockSupport.parkNanos(sleep_time);
                writer.print("l");
                LockSupport.parkNanos(sleep_time);
                writer.print("l");
                LockSupport.parkNanos(sleep_time);
                writer.print("o");
                LockSupport.parkNanos(sleep_time);
                writer.print("!");
                LockSupport.parkNanos(sleep_time);
                writer.println();
                writer.flush();
            } catch (Exception e) {
            }
        }

    }
}
