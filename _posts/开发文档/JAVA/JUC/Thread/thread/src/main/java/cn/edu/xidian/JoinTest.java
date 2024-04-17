package cn.edu.xidian;

public class JoinTest implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("线程vip来了-->"+i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JoinTest joinTest = new JoinTest();

        Thread thread = new Thread(joinTest);
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());

        thread.setDaemon(true);

//        thread.join();
        for (int i = 0; i < 100; i++) {
            System.out.println("main-->" + i);
        }
    }
}
