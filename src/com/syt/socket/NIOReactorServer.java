package com.syt.socket;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

/**
 * Created by Think on 2017/8/19.
 */
public class NIOReactorServer {

    public static void main(String[] args) {
        Server.start();
    }

    static class Server implements Runnable {

        public static void start() {
            new Thread(new Server()).start();
        }

        public void run() {

            // 初始化channel
            try (ServerSocketChannel channel = ServerSocketChannel.open();
                 Selector selector = Selector.open()) {
                channel.bind(new InetSocketAddress("127.0.0.1", 9090));
                channel.configureBlocking(false);
                channel.register(selector, SelectionKey.OP_ACCEPT);

                while (true) {
                    if (selector.select(1000) == 0) {
                        continue;
                    }

                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        handleKey(key, channel, selector);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void handleKey(SelectionKey key, ServerSocketChannel channel, Selector selector) {
            if (!key.isValid()) {
                return;
            }

            try {
                if (key.isAcceptable()) {
                    SocketChannel clientChannel = channel.accept();
                    clientChannel.configureBlocking(false);
                    clientChannel.register(selector, SelectionKey.OP_READ);
                }

                if (key.isReadable()) {
                    SocketChannel clientChannel = (SocketChannel) key.channel();
                    ByteBuffer buf = ByteBuffer.allocate(1024);
                    try {

                        if (clientChannel.read(buf) == -1) {
                            clientChannel.close();
                            key.cancel();
                        }

                        buf.flip();

                        CharBuffer charBuf = Charset.forName("UTF-8").newDecoder().decode(buf);
                        System.out.println(charBuf.toString());

                        clientChannel.write(Charset.forName("UTF-8").newEncoder().encode(charBuf));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}