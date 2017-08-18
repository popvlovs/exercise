package com.syt.socket;

/**
 * Created by Think on 2017/8/10.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    private static final int ECHO_SERVER_PORT = 6789;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(ECHO_SERVER_PORT)) {
            System.out.println("服务器已经启动...");
            while (true) {
                Socket client = server.accept();
                new Thread(new ClientHandler(client)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket client;

        public ClientHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {

            DataInputStream dis = null;
            DataOutputStream dos = null;
            try{
                while(true){
                    //读取客户端数据
                    dis = new DataInputStream(client.getInputStream());
                    String reciver = dis.readUTF();
                    System.out.println("客户端发过来的内容:" + reciver);

                    dos = new DataOutputStream(client.getOutputStream());
                    dos.writeUTF(reciver);
                    dos.flush();
                }
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                try {
                    if(dis != null){
                        dis.close();
                    }
                    if(client != null){
                        client = null;
                    }
                    if (dos != null)
                        dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}