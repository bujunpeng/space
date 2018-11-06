package com.bjp.duoxianchengThread1;

/**
 * @author bujunpeng@nuoyuan.com.cn
 * @date 2018/11/6 14:54
 * @desc 多线程生产与消费
 * 1.每次生产和消费都属于原子操作
 * 2.生产和消费用的同一个锁(水池)
 * 3.每次生产者加1滴水,都会唤醒所有消费者前来消费
 * 4.消费者获得锁后 都要进行判断是否有水,没水就wait
 *
 */

/**
 * 水池,初始有10滴水
 * add()  加1滴水
 * sub()  减1滴水
 */
public class Thread6 {
    private int sum = 10;

    public void add() {
        this.sum += 1;
    }

    public void sub() {
        this.sum -= 1;
    }

    public int getSum() {
        return sum;
    }

}

/**
 * 生产者,包含50滴水,每次向水池加1滴水
 * 单独线程,可以有多个
 */
class Produce implements Runnable {
    Thread6 thread6;

    //构造方法,传入水池对象
    public Produce(Thread6 thread6) {
        this.thread6 = thread6;
    }

    @Override
    public void run() {
        for (int i = 0; i < 50; i++) {
            //每加1滴水,都是原子操作,要锁住水池
            synchronized (thread6) {
                //加1滴水
                thread6.add();
                //打印加水日志
                System.out.println(Thread.currentThread().getName() + "-----" + (thread6.getSum() - 1) + " + 1 = " + thread6.getSum());
                //模拟加水耗时100毫秒
                try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
                //加完水后notifyAll, 水池开锁,并将所有在水池外等待的线程都唤醒
                thread6.notifyAll();
            }
        }
    }
}

/**
 * 消费者,每次向外抽1滴水
 * 单独线程,while(true)循环,可以有多个
 */
class Consumer implements Runnable {
    Thread6 thread6;

    //构造函数,传入水池对象
    public Consumer(Thread6 thread6) {
        this.thread6 = thread6;
    }

    @Override
    public void run() {
        //抽水过程,无限循环
        while (true) {
            //抽水过程是原子操作,先给水池加锁,每次抽1滴水
            synchronized (thread6) {
                //锁住水池后,先判断是否有水
                if (thread6.getSum() > 0) {//有水
                    //抽1滴水
                    thread6.sub();
                    //打印抽水日志
                    System.out.println(Thread.currentThread().getName() + "-----" + (thread6.getSum() + 1) + " - 1 = " + thread6.getSum());
                    //模拟抽水耗时100毫秒
                    try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
                } else {//没有水
                    //本抽水线程在此处等待
                    try {thread6.wait();} catch (InterruptedException e) {return;}
                }
            }
        }
    }
}

/**
 * 主线程
 */
class Main6 {
    public static void main(String[] args) throws InterruptedException {
        //创建一个水池
        Thread6 thread6 = new Thread6();

        //创建5个抽水口(线程)
        Thread consumerA = new Thread(new Consumer(thread6), "consumerA");
        Thread consumerB = new Thread(new Consumer(thread6), "consumerB");
        Thread consumerC = new Thread(new Consumer(thread6), "consumerC");
        Thread consumerD = new Thread(new Consumer(thread6), "consumerD");
        Thread consumerE = new Thread(new Consumer(thread6), "consumerE");

        //创建5个加水口(线程)
        Thread produceA = new Thread(new Produce(thread6), "produceA");
        Thread produceB = new Thread(new Produce(thread6), "produceB");
        Thread produceC = new Thread(new Produce(thread6), "produceC");
        Thread produceD = new Thread(new Produce(thread6), "produceD");
        Thread produceE = new Thread(new Produce(thread6), "produceE");

        //启动10个线程
        consumerA.start();
        consumerB.start();
        consumerC.start();
        consumerD.start();
        consumerE.start();
        produceA.start();
        produceB.start();
        produceC.start();
        produceD.start();
        produceE.start();

        //等待结束
        Thread.sleep(60000);
        //打印结束后,还存活的线程名称和状态
        Thread[] t = new Thread[Thread.currentThread().getThreadGroup().activeCount()];
        Thread.currentThread().getThreadGroup().enumerate(t);
        for (Thread x : t) {
            System.out.println(x.getName() + "--" + x.getState());
        }
    }
}