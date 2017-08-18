package com.syt.thread;

/**
 * Created by Think on 2017/8/10.
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;


/**
 * Created by Zen9 on 2016/3/16.
 */


class Producer extends Thread{
    private BlockingQueue<String> blockingQueue;
    public Producer(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        String[] strArr = new String[]{"AAAA","BBBB","CCCC"};
        for (int i = 0; i < 99999; i++) {
            System.out.println(getName() + " 生产者准备生产集合元素！");
            try {
                Thread.sleep(200);
                // 尝试放入元素，如果队列已满，则线程被阻塞
                blockingQueue.put(strArr[i%3]);
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(getName() + "生产完成：" + blockingQueue);
        }
    }
}

class Consumer extends Thread{
    private BlockingQueue<String> blockingQueue;
    public Consumer(BlockingQueue<String> blockingQueue){
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(getName() + " 消费者准备消费集合元素！");
            try {
                Thread.sleep(200);
                // 尝试取出元素，如果队列已空，则线程被阻塞
                blockingQueue.take();
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println(getName() + "消费完成：" + blockingQueue);
        }
    }
}

public class BlockingQueueDemo {
    public static void main(String[] args) {
        // 创建容量为1的BlockingQueue
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<String>(1);
        // 启动3个生产者线程
        new Producer(blockingQueue).start();
        new Producer(blockingQueue).start();
        new Producer(blockingQueue).start();
        // 启动1个消费者线程
        new Consumer(blockingQueue).start();
    }
}
