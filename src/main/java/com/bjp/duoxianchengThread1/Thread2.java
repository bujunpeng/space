package com.bjp.duoxianchengThread1;

/**
 * @author bujunpeng@nuoyuan.com.cn
 * @date 2018/11/2 10:40
 * @desc
 */
public class Thread2 implements Runnable {
    private Object lock1;
    private Object lock2;


    public Thread2(Object lock1, Object lock2) {
        this.lock1 = lock1;
        this.lock2 = lock2;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + "1");
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() + "2");
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + "3");
                    lock2.notify();
                }
                try {lock1.wait();} catch (Exception e) {e.printStackTrace();}

            }

        }
    }
}

class Main2 {
    public static void main(String[] args) throws InterruptedException {
        Object a = new Object();
        Object b = new Object();
        new Thread(new Thread2(a, b), "A").start();
        Thread.sleep(100);
        new Thread(new Thread2(b, a), "B").start();
    }
}