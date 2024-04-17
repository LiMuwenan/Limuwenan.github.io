package cn.edu.xidian;

public class RunnableTest implements Runnable{
    /**
     * 方式二：
     * 第一步：实现Runnable接口
     * 第二步：重写run方法
     * 第三步：匿名创建Thread类，调用start方法
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
        RunnableTest runnableTest = new RunnableTest();
        new Thread(runnableTest).start();

        for (int i = 0; i < 200; i++) {
            System.out.println("我在学习多线程: "+i);
        }
    }
}
