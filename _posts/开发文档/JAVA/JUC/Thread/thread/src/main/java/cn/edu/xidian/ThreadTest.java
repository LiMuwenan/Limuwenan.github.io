package cn.edu.xidian;

import java.lang.Thread;

public class ThreadTest extends Thread {
    /**
     * 方式一：
     * 第一步：继承Thread方法
     * 第二步：重写run方法
     * 第三步：调用start开启线程
     */

    @Override
    public void run() {
        //线程体
        for (int i = 0; i < 200; i++) {
            System.out.println("我在线程里: "+i);
        }
    }

    public static void main(String[] args) {

        //开启线程
        ThreadTest threadTest = new ThreadTest();
        threadTest.start();

        for (int i = 0; i < 200; i++) {
            System.out.println("我在学习多线程: "+i);
        }
    }
}
