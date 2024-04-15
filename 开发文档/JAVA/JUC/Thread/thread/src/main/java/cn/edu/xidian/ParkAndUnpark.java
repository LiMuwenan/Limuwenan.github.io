package cn.edu.xidian;

import java.util.concurrent.locks.LockSupport;

import static java.lang.Thread.sleep;

public class ParkAndUnpark {

    public static void main(String[] args) {
        Thread t1 = new Thread(){
            @Override
            public void run() {
                System.out.println("t1:start");
                for (int i = 0; i < 50; i++) {
                    System.out.println(i);
                    if(i==25){
                        System.out.println("park");
                        LockSupport.park();//暂停线程
                        System.out.println("continue");
                    }
                }

            }
        };
        t1.start();
        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LockSupport.unpark(t1);//重启线程
    }
}
