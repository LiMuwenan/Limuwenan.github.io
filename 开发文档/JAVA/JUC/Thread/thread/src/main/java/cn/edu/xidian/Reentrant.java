package cn.edu.xidian;


import jdk.nashorn.internal.ir.Block;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Reentrant {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(2, 500, TimeUnit.MILLISECONDS, 10);
        for (int i = 0; i < 6; i++) {
            int j =i;
            threadPool.execute(()->{
                System.out.println(j);
            });
        }
    }
}

class ThreadPool {
    //任务队列
    private BlockingQueue<Runnable> taskQueue;

    //线程集合
    private HashSet<Work> workers = new HashSet<>();

    //核心线程数
    private int coreSize;

    //线程空闲超时时间
    private long timeout;

    private TimeUnit timeUnit;

    public ThreadPool(int coreSize, long timeout, TimeUnit timeUnit, int queueCap) {
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.taskQueue = new BlockingQueue<>(queueCap);
    }

    //执行任务
    public void execute(Runnable task) {
        synchronized (workers) {
            //任务数没有超过核心数量就使用work启动
            if (workers.size() < coreSize) {
                Work work = new Work(task);
                System.out.println("创建新线程"+work.getName()+" "+task);
                workers.add(work);
                System.out.println("启动新线程"+work.getName()+" "+task);
                work.start();
            } else {
                System.out.println("加入任务队列"+task);
                taskQueue.add(task);//超过则加入任务队列
            }
        }
    }

    class Work extends Thread {
        private Runnable task;

        public Work(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            //执行任务
            //1.task不为空，执行
            //2.task执行完毕，再从阻塞队列中取任务执行
            while (task != null || (task = taskQueue.take()) != null) {
                try {
                    System.out.println("正在执行"+task);
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }
            synchronized (workers){
                System.out.println("结束执行"+task);
                workers.remove(this);//将该任务从执行集合移除
            }

        }
    }
}

//自定义阻塞队列
class BlockingQueue<T> {//也有现成的阻塞队列可用
    //1.任务队列
    private Queue<T> queue = new LinkedList<>();

    //2.锁。肯定有多个线程会同时从队列头获得线程
    private ReentrantLock lock = new ReentrantLock();

    //3.生产者条件变量
    private Condition fullWait = lock.newCondition();

    //4.消费者条件变量
    private Condition emptyWait = lock.newCondition();

    //5.容量
    private int cap;

    public BlockingQueue(int cap) {
        this.cap = cap;
    }

    //带超时的阻塞获取
    public T take(int time, TimeUnit timeUnit) {
        lock.lock();
        try {
            while (!queue.isEmpty()) {//队列中没有
                emptyWait.await(time, timeUnit);
            }
            T poll = queue.poll();
            //获取以后要进行生产
            fullWait.signal();
            return poll;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }

    //阻塞获取
    public T take() {
        lock.lock();
        try {
            while (!queue.isEmpty()) {//队列中没有
                emptyWait.await();
            }
            System.out.println("有新任务可获取");
            T poll = queue.poll();
            //获取以后要进行生产
            fullWait.signal();
            return poll;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } finally {
            lock.unlock();
        }
    }

    //阻塞添加
    public void add(T element) {
        lock.lock();
        try {
            while (size() == cap) {//容量满了
                fullWait.await();
            }
            System.out.println("产生了新任务");
            queue.add(element);//添加了新元素
            emptyWait.signal();//唤醒消费
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //获得当前的大小
    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}
