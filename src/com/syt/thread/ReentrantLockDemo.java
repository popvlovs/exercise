package com.syt.thread;

/**
 * Created by Think on 2017/8/10.
 */

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ProductQueue<T> {

    private final T[] items;

    private final Lock lock = new ReentrantLock();

    private Condition notFull = lock.newCondition();

    private Condition notEmpty = lock.newCondition();

    //
    private int head, tail, count;

    public ProductQueue(int maxSize) {
        items = (T[]) new Object[maxSize];
    }

    public ProductQueue() {
        this(10);
    }

    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            while (this.full()) {
                notFull.await();
            }
            items[tail] = t;
            if (++tail == getCapacity()) {
                tail = 0;
            }
            ++count;
            notEmpty.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public T take() throws InterruptedException {
        lock.lock();
        try {
            // wait for notnull
            while (this.empty()) {
                notEmpty.await();
            }

            // push an item
            T ret = items[head];
            items[head] = null;//GC
            //
            if (++head == getCapacity()) {
                head = 0;
            }
            --count;

            // notify notfull
            notFull.signalAll();
            return ret;
        } finally {
            lock.unlock();
        }
    }

    public int getCapacity() {
        return items.length;
    }

    public int size() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }

    private boolean full() {
        return count == getCapacity();
    }

    private boolean empty() {
        return count == 0;
    }
}

class ProduceThread implements Runnable {

    private ProductQueue<String> productQueue;

    public ProduceThread(ProductQueue<String> productQueue) {
        this.productQueue = productQueue;
    }

    @Override
    public void run() {
        try {
            this.productQueue.put("product");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class ConsumeThread implements Runnable {
    private ProductQueue<String> productQueue;

    public ConsumeThread(ProductQueue<String> productQueue) {
        this.productQueue = productQueue;
    }

    @Override
    public void run() {
        try {
            String product = this.productQueue.take();
            System.out.println("消费产品：" + product);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ReentrantLockDemo {

    public static void main(String args[]) throws IOException{
        ProductQueue<String> productQueue = new ProductQueue<>();

        ExecutorService service = Executors.newFixedThreadPool(6);

        try {
            for (int i = 0; i < 3; ++i) {
                service.execute(new ConsumeThread(productQueue));
            }

            for (int i = 0; i < 3; ++i) {
                service.execute(new ProduceThread(productQueue));
            }

            service.shutdown();

            service.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}
