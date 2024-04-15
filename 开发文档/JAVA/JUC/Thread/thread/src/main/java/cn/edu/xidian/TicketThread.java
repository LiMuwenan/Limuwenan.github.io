package cn.edu.xidian;

public class TicketThread implements Runnable{

    private int ticketNums = 10;

    @Override
    public void run() {
        while(true){
            if(ticketNums<=0)
                break;

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName()+"拿到了第"+ticketNums--+"票");
        }
    }

    public static void main(String[] args) {
        TicketThread ticketThread = new TicketThread();

        new Thread(ticketThread,"小明").start();
        new Thread(ticketThread,"老师").start();
        new Thread(ticketThread,"黄牛").start();
    }
}
