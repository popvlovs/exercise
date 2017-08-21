package com.syt.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.StandardSocketOptions;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousChannelGroup;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;

/**
 * Created by Think on 2017/8/20.
 *
 * @author popvlovs
 */
public class AIOSocketServer {
    public static void main(String[] args) {
        Server server = new Server();
        server.start();

        // 阻塞主线程
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class Server {
        private AsynchronousServerSocketChannel channel;
        private AsynchronousChannelGroup group;
        private final static String IP = "127.0.0.1";
        private final static int PORT = 9090;

        private Server() {
            try {
                this.group = AsynchronousChannelGroup.withCachedThreadPool(Executors.newCachedThreadPool(), 10);
                openServerChannel();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void openServerChannel() {
            try {
                this.channel = AsynchronousServerSocketChannel.open(group);
                this.channel.bind(new InetSocketAddress(IP, PORT));
                this.channel.setOption(StandardSocketOptions.SO_REUSEADDR, true);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void start() {

            if (!this.channel.isOpen()) {
                openServerChannel();
            }

            channel.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
                public void completed(AsynchronousSocketChannel ch, Void att) {

                    // accept the next connection
                    channel.accept(null, this);

                    // handle this connection
                    handleAccept(ch);
                }

                public void failed(Throwable exc, Void att) {
                    exc.printStackTrace();
                }
            });
        }

        private void handleAccept(AsynchronousSocketChannel ch) {
            ByteBuffer buf = ByteBuffer.allocate(1024);
            System.out.println("监听到socket请求，处理线程为：" + Thread.currentThread().getId());

            // 在SockectChannel（ClientChannel）上循环注册读事件，直到连接关闭（Result == -1）
            ch.read(buf, null, new CompletionHandler<Integer, Void>() {
                @Override
                public void completed(Integer result, Void attachment) {
                    try {
                        if (result != -1) {
                            buf.flip();
                            CharBuffer charBuf = Charset.forName("utf8").newDecoder().decode(buf);
                            System.out.println(charBuf.toString());
                            ch.write(Charset.forName("utf8").newEncoder().encode(charBuf));
                            buf.flip();
                            buf.clear();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    ch.read(buf, null, this);
                }

                @Override
                public void failed(Throwable exc, Void attachment) {
                    exc.printStackTrace();
                }
            });
        }
    }
}
