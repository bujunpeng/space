package com.bjp.duoxianchengThread1;

/**
 * @author bujunpeng@nuoyuan.com.cn
 * @date 2018/11/1 10:51
 * @desc
 */
public class Thread1 extends Thread {
    public Thread1(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(this.getName() + "线程" + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class Main1{
    public static void main(String[] args) {
        Thread[] threads = new Thread[10];
        System.out.println(Thread.activeCount()+":"+Thread.enumerate(threads));
        for (int i = 0; i < threads.length; i++) {
            if (threads[i]!=null)
            System.out.println(threads[i].getName());
        }
        Thread1 A = new Thread1("A");
        Thread1 B = new Thread1("B");
        A.start();
        B.start();

        System.out.println(Thread.activeCount()+":"+Thread.enumerate(threads));
        for (int i = 0; i < threads.length; i++) {
            if (threads[i]!=null)
            System.out.println(threads[i].getName());
        }

    }
}
