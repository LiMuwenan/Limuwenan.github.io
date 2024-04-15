package cn.edu.xidian.sync;


import java.util.concurrent.locks.ReentrantLock;

//线程不安全的买票方式
public class UnSync {

    public static void main(String[] args) {
        BuyTicket  station = new BuyTicket();

        new Thread(station,"小明").start();
        new Thread(station,"老师").start();
        new Thread(station,"黄牛").start();
    }

}

class BuyTicket implements Runnable{

    //总共十张票
    private volatile int ticketNums = 10;
    boolean flag = true;//外部停止方式

    ReentrantLock myLock = new ReentrantLock();

    @Override
    public void run() {
        //买票
        while(flag){
            buy();
        }
    }

    private void buy() {

            //判断是否有票
            if(ticketNums<=0){
                flag=false;
                return;
            }

            //模拟延时
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //买票
            System.out.println(Thread.currentThread().getName() + "买到" + ticketNums + "票");
            ticketNums = ticketNums -1;

    }
}
