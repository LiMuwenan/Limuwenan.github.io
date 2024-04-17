package cn.edu.xidian;


import java.util.concurrent.*;

public class Pool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);//有两个线程都到了阻塞状态，这两个线程才会继续执行

        Future<?> submit = executorService.submit(() -> {
            System.out.println("t1,start");
            try {
                Thread.sleep(1000);
                cyclicBarrier.await();//线程阻塞
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        Future<?> submit2 = executorService.submit(() -> {
            System.out.println("t2,start");
            try {
                Thread.sleep(3000);
                cyclicBarrier.await();//线程阻塞
                System.out.println("end");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

        Future<?> submit3 = executorService.submit(() -> {
            System.out.println("t3,start");
            try {
                Thread.sleep(1000);
                cyclicBarrier.await();//线程阻塞
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });
        Future<?> submit4 = executorService.submit(() -> {
            System.out.println("t4,start");
            try {
                Thread.sleep(3000);
                cyclicBarrier.await();//线程阻塞
                System.out.println("end");
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        });

    }
}

//RecursiveAction 不需要返回结果的时候使用
//RecursiveTask<V> 需要返回结果的时候使用
class MyThread extends RecursiveTask<Integer>{

    private int n;//求解1-n的和

    public MyThread(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        //终止条件
        if(n==1){
            return 1;
        }
        MyThread t1  = new MyThread(n-1);
        t1.fork();//启动一个线程执行该任务
        int res = n + t1.join();//获取任务结果
        return res;
    }
}
