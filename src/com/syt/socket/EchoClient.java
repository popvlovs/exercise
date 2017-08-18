package com.syt.socket;

/**
 * Created by Think on 2017/8/10.
 */

import java.io.*;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class EchoClient {

    final static private ReentrantLock lock = new ReentrantLock();
    final static private Condition echo = lock.newCondition();
    static private boolean hsaEchoed = true;

    public static void main(String[] args) throws Exception {

        Socket client = new Socket("localhost", 6789);

        // start echo thread
        new Thread(new ServerEchoHandler(client)).start();

        // start scanner thread
        new Thread(new NativeScannerHandler(client)).start();
    }

    private static class NativeScannerHandler implements Runnable {
        private Socket client;

        public NativeScannerHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            DataOutputStream dos = null;
            BufferedReader br = null;
            try {
                while (true) {
                    if (!hsaEchoed) {
                        continue;
                    }
                    dos = new DataOutputStream(client.getOutputStream());

                    System.out.print("\n请输入:\t");
                    hsaEchoed = false;

                    // 键盘录入
                    br = new BufferedReader(new InputStreamReader(System.in));
                    String send = br.readLine();
                    //发送数据
                    dos.writeUTF(send);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                try {
                    if (dos != null) {
                        dos.close();
                    }
                    if (br != null) {
                        br.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class ServerEchoHandler implements Runnable {
        private Socket client;

        public ServerEchoHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {

            DataInputStream dis = null;
            try {
                while (true) {
                    //读取服务器端数据
                    dis = new DataInputStream(client.getInputStream());
                    String receive = dis.readUTF();

                    System.out.println("服务器端返回的是: " + receive);
                    hsaEchoed = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (dis != null) {
                        dis.close();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}