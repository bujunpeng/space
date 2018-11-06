package com.bjp.duoxianchengThread1;

/**
 * @author bujunpeng@nuoyuan.com.cn
 * @date 2018/11/6 11:14
 * @desc 1 通过构造函数传递参数
 * 2 通过set方法传递参数
 * 3 通过回调函数传递参数到线程中(动态)  本例
 */
class Data {
    public int value = 0;
}

class Work {
    public void process(Data data, Integer... numbers) {
        for (int n : numbers) {
            data.value += n;
        }
    }
}

public class Thread4 implements Runnable{
    private Work work;

    public Thread4(Work work) {
        this.work = work;
    }

    @Override
    public void run() {
        java.util.Random random = new java.util.Random();
        Data data = new Data();
        int n1 = random.nextInt(1000);
        int n2 = random.nextInt(2000);
        int n3 = random.nextInt(3000);
        work.process(data, n1, n2, n3); // 使用回调函数
        System.out.println(String.valueOf(n1) + "+" + String.valueOf(n2) + "+"
                + String.valueOf(n3) + "=" + data.value);
    }
}


class Main4 {
    public static void main(String[] args) {
        Thread4 thread4 = new Thread4(new Work());
        new Thread(thread4).start();

    }
}