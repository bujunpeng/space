package com.bjp.duoxianchengThread1;

/**
 * @author bujunpeng@nuoyuan.com.cn
 * @date 2018/11/5 15:56
 * @desc
 */
public class Thread3 implements Runnable{

    private Object suo;
    public Thread3(Object suo){
        this.suo = suo;
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        try {Thread.sleep(5000);} catch (Exception e) {e.printStackTrace();}
        synchronized (suo){
            System.out.println(Thread.currentThread().getName());
            try {Thread.sleep(5000);} catch (Exception e) {e.printStackTrace();}
            System.out.println("进入wait");
            try {
                suo.wait();
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

class Main3{
    public static void main(String[] args) throws InterruptedException {
        Object suo = new Object();
        Thread A = new Thread(new Thread3(suo),"A");
        A.start();
//        Thread.sleep(100);
//        new Thread(new Thread3(suo),"B").start();
        Thread.sleep(20000);
        A.interrupt();
    }
}
