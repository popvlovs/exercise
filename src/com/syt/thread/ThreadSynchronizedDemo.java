package com.syt.thread;

/**
 * Created by Think on 2017/8/9.
 */

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 银行账户
 *
 */
class Account {
    private double balance;     // 账户余额

    /**
     * 存款
     * @param money 存入金额
     */
    public void deposit(double money) {
        synchronized(this)
        {
            System.out.println("开始执行存入方法");
            double newBalance = balance + money;
            try {
                Thread.sleep(1000);   // 模拟此业务需要一段处理时间
            }
            catch(InterruptedException ex) {
                ex.printStackTrace();
            }
            balance = newBalance;
            System.out.println("结束执行存入方法");
        }
    }

    synchronized public void syncTest()
    {
        Lock lock = new ReentrantLock();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("执行类内其他方法");
    }

    /**
     * 获得账户余额
     */
    public double getBalance() {
        return balance;
    }
}

class AddMoneyThread implements Runnable {
    private Account account;    // 存入账户
    private double money;       // 存入金额

    public AddMoneyThread(Account account, double money) {
        this.account = account;
        this.money = money;
    }

    @Override
    public void run() {

        account.deposit(money);
    }
}

class OtherThread implements Runnable {
    private Account account;    // 存入账户

    public OtherThread(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        while (true)
        {
            account.syncTest();
        }
    }
}

public class ThreadSynchronizedDemo {

    public static void main(String[] args) {
        Account account = new Account();
        ExecutorService service = Executors.newFixedThreadPool(6);

        for(int i = 1; i <= 5; i++) {
            service.execute(new AddMoneyThread(account, 1));
        }

        service.execute(new OtherThread(account));

        service.shutdown();

        while(!service.isTerminated()) {}

        System.out.println("账户余额: " + account.getBalance());
    }
}
