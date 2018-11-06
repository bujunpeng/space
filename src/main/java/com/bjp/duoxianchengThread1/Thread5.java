package com.bjp.duoxianchengThread1;

/**
 * @author bujunpeng@nuoyuan.com.cn
 * @date 2018/11/6 14:13
 * @desc
 */
public class Thread5 implements Runnable {
    private int ticket = 100;

    @Override
    public void run() {
        for (; ticket > 0; ) {

            synchronized (this) {
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + ":" + ticket--);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}


class Main5 {
    public static void main(String[] args) {
        Thread5 thread5 = new Thread5();
        new Thread(thread5, "A").start();
        new Thread(thread5, "B").start();
        new Thread(thread5, "C").start();
        new Thread(thread5, "D").start();
        new Thread(thread5, "E").start();

    }

}