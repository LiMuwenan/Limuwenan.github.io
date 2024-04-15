# 1 线程

![](https://github.com/LiMuwenan/PicBed/blob/master/img/dev/java/JUC/JUC-%E7%BA%BF%E7%A8%8B%E8%B0%83%E5%BA%A6.png?raw=true)



- 新建（new）：创建后未启动
- 运行（Runnable）：start()
  - Ready：还未分配时间片
  - Running：分配了时间片
- 阻塞（Blocked）：线程被阻塞了，阻塞状态与等待状态的区别是，阻塞状态在等待着获取到一个排他锁，这个时间将在另一个线程放弃这个锁的时候发生；等待状态则是在等待一段时间或者唤醒动作的发生。在线程等待进入同步区域的时候，线程将进入这种状态。
- 无限期等待（Waiting）：处于这种状态的线程不会被分配执行时间，需要等待其他线程的唤醒。
  - Object::wait()
  - Thread::join()：A线程中调用B.join()，代表B线程执行完才会执行A
  - LockSupport::park()：LockSupport.park() LockSupport.unpark(t1)
- 限期等待（Timed Waiting）：处于这种状态的线程也不会被分配时间片，也不需要其他线程唤醒，到了设定时间系统就会自动唤醒
  - Thread::sleep()
  - 设置了Timeout参数的Object::wait()
  - 设置了Timeout参数的Thread::join()
  - LockSupport::parkNanos()
  - LockSupport::parkUntil()
- 结束（Terminated）：已终止线程的线程状态，线程已经结束执行



进程、线程、多线程

[多线程 - B站狂神说多线程详解](https://www.bilibili.com/video/BV1V4411p7EF?)

实现多线程的三种方式：

- 继承Thread类
- 实现Runnable接口
- 实现Callable接口

## 1.1 Thread

[java8官方文档 - java.lang.Thread](https://docs.oracle.com/javase/8/docs/api/index.html)

`Thread`创建线程分为三步：

- 继承Thread类
- 重写run方法
- 实例化类并调用start方法开启线程



```java
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
        //如果这里执行的run方法，就不会开启多线程
        threadTest.start();

        for (int i = 0; i < 200; i++) {
            System.out.println("我在学习多线程: "+i);
        }
    }
}
```





## 1.2 Runnable

[java8官方文档 - java.lang.Runnable](https://docs.oracle.com/javase/8/docs/api/index.html)

`Runnable`创建线程分为三步：

- 实现Runnable接口
- 实现run方法
- 使用Thread匿名对象调用start方法

```java
package cn.edu.xidian;

public class RunnableTest implements Runnable{
    /**
     * 方式二：
     * 第一步：实现Runnable接口
     * 第二步：实现run方法
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

```



## 1.3 Callable

`Callable`接口实现多线程：

- 实现Callable接口，需要返回值类型
- 实现call方法，需要抛出异常
- 创建目标对象
- 创建执行服务
- 提交执行
- 获取结果
- 关闭服务

这个接口就是创建线程池

```java
package cn.edu.xidian;

import java.util.concurrent.*;

public class CallableTest implements Callable {
    /**
     * - 实现Callable接口，需要返回值类型
     * - 实现call方法，需要抛出异常
     * - 创建目标对象
     * - 创建线程池
     * - 提交执行
     * - 获取结果
     * - 关闭服务
     * @return
     */
    private int ticketNums = 20;

    @Override
    public Object call() throws Exception {
        while(true){
            if(ticketNums<=0){
                break;
            }
            System.out.println(Thread.currentThread().getName()+" 买到第"+ticketNums--+"票");
        }

        return true;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CallableTest callableTest1 = new CallableTest();
        CallableTest callableTest2 = new CallableTest();
        CallableTest callableTest3 = new CallableTest();

        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        //提交执行
        Future<Boolean> r1 = executorService.submit(callableTest1);
        Future<Boolean> r2 = executorService.submit(callableTest1);
        Future<Boolean> r3 = executorService.submit(callableTest1);

        //获取结果
        boolean rs1 = r1.get();
        boolean rs2 = r2.get();
        boolean rs3 = r3.get();

        //关闭线程池
        executorService.shutdown();
    }

}

```



## 1.4 并发问题

当同时有很多用户进行操作访问的时候就是并发，这个时候如果是线程不安全的就会出现问题

```java
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

```

上面的程序就可能出现负票数或者两个人拿到了同一张票，这里就是线程不安全



# 2 进程状态

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/JUC/JUC-%E7%BA%BF%E7%A8%8B%E7%8A%B6%E6%80%81%E5%9B%BE.png)

进程状态

- NEW （新生）
- READY：
- Runnable（可运行）
- Blocked（阻塞）
- Terminated（终止）

线程状态

- NEW （新生）
- Runnable（可运行）
- Blocked（阻塞）
- Waiting（等待）
- Timed Waiting（计时等待）
- Terminated（终止）

调用`getState`方法查看线程状态



```java
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

//        thread.join();
        for (int i = 0; i < 100; i++) {
            System.out.println("main-->" + i);
        }
    }
}
```



## 2.1 创建状态

`new`操作符创建一个线程

```java
Thread t1 = new Thread();
try {
    Thread.sleep(2000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
System.out.println("t1："+ t1.getState());//NEW
```

线程被创建，但是还没有运行

## 2.2 就绪状态

```java
Thread t2 = new Thread(){
    @Override
    public void run() {
        while  (true){

        }
    }
};
t2.start();
try {
    Thread.sleep(2000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
System.out.println("t2："+ t2.getState());//RUNNABLE
```

调用`start`方法让线程进入就绪状态，等待CPU分配资源，或者已经在执行中

## 2.3 阻塞状态

`wait`, `sleep`, `join`, `lock`等方法使线程进入阻塞状态

t3处于==sleep==状态，因此是==TIMED_WAITING==

t6在等待t3释放锁，所以是==BLOCKED==

```java
Thread t3 = new Thread(){
    @Override
    public synchronized void run() {
        synchronized(State.class){
            try {
                Thread.sleep(200000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
};
t3.start();
try {
    Thread.sleep(2000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
System.out.println("t3："+ t3.getState());//TIMED_WAITING

Thread t6 = new Thread(){
    @Override
    public void run() {
        synchronized(State.class){
            try {
                Thread.sleep(200000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
};
t6.start();
try {
    Thread.sleep(2000);
} catch (InterruptedException e) {
    e.printStackTrace();
}
System.out.println("t6：" + t6.getState());//BLOCKED
```



## 2.4 终止状态

运行结束的线程状态为`terminate`，结束的线程不能再一次被启动

```java
Thread t4 = new Thread(){
    @Override
    public void run() {
//                System.out.println("t4 end");
    }
};
t4.start();

try {
    Thread.sleep(2000);
} catch (InterruptedException e) {
    e.printStackTrace();
}

System.out.println("t4："+ t4.getState());//TERMINATED
```

## 2.5 各种方法



| 方法                           | 说明                                                         |
| ------------------------------ | ------------------------------------------------------------ |
| setPriority(int new Priority)  | 更改线程优先级                                               |
| static void sleep(long millis) | 让线程休眠毫秒                                               |
| void join()                    | 等待该线程终止                                               |
| static void yield()            | 暂停当前正在执行的线程对象并执行其他线程，不是阻塞，重新回到就绪态 |
| void interrupt()               | 中断线程                                                     |
| boolean isAlive()              | 测试线程时候处于活动状态                                     |



 

```java
//yield
package cn.edu.xidian;

public class YieldTest{

}

class MyYield implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"线程开始执行");
        Thread.yield();
        System.out.println(Thread.currentThread().getName()+"线程结束执行");
    }

    public static void main(String[] args) {
        MyYield myYield = new MyYield();

        new Thread(myYield,"A").start();
        new Thread(myYield,"B").start();
    }
}

```





```java
//join
//使用了join方法，只有当新线程执行完，主线程才会继续执行，不会出现交替执行的情况
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
        thread.start();

        thread.join();
        for (int i = 0; i < 100; i++) {
            System.out.println("main-->" + i);
        }
    }
}

```





```java
//setPriority
Thread.MIN_PRIORITY = 1;
Thread.MAX_PRIORITY = 10;
Thread.NORM_PRIORITY = 5;
//数字越大优先级越高
//优先级高只会增加线程被CPU选中的概率
```



## 2.6 守护线程

```java
//开启守护线程
thread.setDaemon(true);
```

将==thread==线程设置为守护线程

通常在守护线程中运行一些服务。当只剩下守护线程的时候，虚拟机就会退出。



# 3 锁机制

前面[14.4 并发](# 14.4 并发)提到，在两个线程同时操作一个数据的时候会出现线程不安全的情况

```java
package cn.edu.xidian.sync;


//线程不安全的买票方式
public class UnSync {

    public static void main(String[] args) {
        BuyTicket station = new BuyTicket();

        new Thread(station,"小明").start();
        new Thread(station,"老师").start();
        new Thread(station,"黄牛").start();
    }

}

class BuyTicket implements Runnable{

    //总共十张票
    private int ticketNums = 10;
    boolean flag = true;//外部停止方式
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
        System.out.println(Thread.currentThread().getName()+"买到"+ticketNums--+"票");
    }

}

```



## 3.1 同步锁(synchronized)

关键字`synchronized`，当一个线程获得锁的时候，独占资源，其他线程必须等待，使用完后才会释放锁，这样会引发几个问题：

- 一个线程持有锁会导致其他所有需要此锁的线程挂起
- 在多线程竞争下，加锁、释放锁会导致比较多的上下文切换和调度延时，引起性能问题
- 如果一个优先级高的线程等待一个低优先级线程，会导致优先级倒置，引起性能问题

**普通对象或匿名代码块：给指定对象加锁（monitorenter和monitorexit）**

**实例方法：对当前方法加锁（ACC_SYNCHRONIZED标识符存在同步方法常量池中，线程会查看是否有这个标志，如果有就尝试获得锁）**

**静态方法：class对象**

上面的程序如果经过下面这样的改动，就不会出现买错票的情况。

`synchronized`关键字会自动地将多个线程排队，同时只能有一个线程访问这个资源

**一个线程获得一个方法锁的时候意味着锁定了该实例，所有加锁的方法别的线程都不能访问；而且这个锁是可重入锁，其他有锁的代码块该线程也是可以直接使用**

```java
private synchronized void buy() {
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
    System.out.println(Thread.currentThread().getName()+"买到"+ticketNums--+"票");
}
```

使用该关键字还等价于

```java
 public synchronized void method(){//锁定了该类的该实例
    //方法体
}
//等价
public void method(){
    this.intrisicLock.lock();//thread是要调用该方法的线程
    try{
        //方法体
    }finally {
        this.intrisicLock.unlock();
    }
}
```

除了给方法加锁，还可以给代码块加锁

```java
synchronized (Obj){
	//
}
```

上面的代码改成下面这样，出现的问题是，可以同步，但是会出现-1票。因为A线程进来看到还有1张票准备买，执行到同步代码块，这时候B看到也有1张票快执行到同步代码块，这时候A会买完变成0张，B会买变成-1张，这就是为什么会有-1的原因。

**静态方法加锁等价于锁定该类的所有实例**

```java
private Object lock = new Object();//这个对象的创建就是为了锁代码块而创建

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
    
    synchronized (lock){
        //买票
        System.out.println(Thread.currentThread().getName()+"买到"+ticketNums--+"票");

    }
}
```

## 3.2 ReentrantLock

- 可中断
- 可以设置超时时间
- 可以设置为公平锁
- 支持多个条件变量

**基本语法**

```java
ReentrantLock mylock = new ReentrantLock();//创建锁

mylock.lock();//加锁锁定

try{
    //同步区
}finally {
    mylock.unlock();//释放锁
}
```

### 3.2.1 可中断

线程没获得锁，可以打断阻塞状态，获得锁之后打断没有效果

```java
public class Reentrant {
    private static ReentrantLock mylock = new ReentrantLock();//创建锁

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            try {
                //没有竞争，此线程获得该锁
                //有竞争，可以被其他线程用interrupt打断
                System.out.println("t1:尝试");
                mylock.lockInterruptibly();//使锁可打断
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println("t1:没获得");
                return;
            } finally {
                System.out.println("t1:得到锁");
                mylock.unlock();
            }
        });
        mylock.lock();
        t1.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main 打断 t1");
        t1.interrupt();//打断线程t1等待锁的过程
    }
}
```

### 3.2.2 超时锁

设置尝试获取锁的最大等待时间

```
mylock.tryLock(10000, TimeUnit.SECONDS);
```

```java
private static ReentrantLock mylock = new ReentrantLock();//创建锁

public static void main(String[] args) {
    new Thread(()->{
        mylock.lock();
        System.out.println("t1获得锁");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mylock.unlock();
        System.out.println("t1释放锁");
    }).start();
    try {
        Thread.sleep(500);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    System.out.println("main尝试获得锁");
    //尝试一下没有获得，不会死等
    boolean lock = mylock.tryLock();//返回boolean表示是否获得锁
    if(!lock){
        System.out.println("main未获得锁");
    }else{
        System.out.println("main获得锁");
    }
    try {
        Thread.sleep(1000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    try {
        System.out.println("main再次尝试获得锁");
        boolean lock1 = mylock.tryLock(10000, TimeUnit.SECONDS);//返回boolean表示是否获得锁。在这里主线程会等待t1释放锁
        if(!lock1){
            System.out.println("main第二次未获得");
        }else{
            System.out.println("main第二次获得锁");
        }
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

### 3.2.3 公平锁

synchronized 在一个线程释放掉锁之后，线程阻塞队列中的所有线程都有去尝试获得锁，而不是根据先到先到的原则。

因此 ReentrantLock 可以设置为公平锁，使得先进入等待队列的线程先获得锁

```java
ReentrantLock mylock = new ReentrantLock(true);//创建锁
```

### 3.2.4 条件变量

使用流程：

- await 前需要获得锁
- await 执行后，**会释放锁**，进入 conditionObject 等待
- await 的线程被唤醒（或打断、或超时），会去重新竞争 lock 锁
- 竞争 lock 锁成功后，从 await 后继续执行

Condition 接口中的 await() 和 signal() 方法需要在 lock.lock() 和 lock.unlock() 方法的保护之内进行

```java
private static ReentrantLock mylock = new ReentrantLock();//创建锁
private static Condition waitCandy = mylock.newCondition();
private static Condition waitCake = mylock.newCondition();
private static boolean isCandy = false;
private static boolean isCake = false;

public static void main(String[] args) {
    new Thread(()->{
        mylock.lock();//上锁
        try{
            System.out.println("我需要糖果");
            while(!isCandy){
                System.out.println("还没有糖果，稍等");
                try {
                    waitCandy.await();//进入等待糖果的条件
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("终于拿到糖了");
        }finally {
            mylock.unlock();
        }
    }).start();

    new Thread(()->{
        mylock.lock();//上锁
        try{
            System.out.println("我需要蛋糕");
            while(!isCake){
                System.out.println("还没有蛋糕，稍等");
                try {
                    waitCake.await();//进入等待蛋糕的条件
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("终于拿到蛋糕了");
        }finally {
            mylock.unlock();
        }
    }).start();

    try {
        Thread.sleep(3000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    new Thread(()->{
        mylock.lock();
        try{
            isCandy = true;
            waitCandy.signal();
            isCake = true;
            waitCake.signal();
        }finally {
            mylock.unlock();
        }
    }).start();
    
}
```

## 3.3 ReentrantReadWriteLock

```java
ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();//写锁
ReentrantReadWriteLock.ReadLock readLock = reentrantReadWriteLock.readLock();//读锁
```

读写锁，可以实现多个线程同时进行读操作或者同时进行写操作，但是有一个在读另一个需要写，那么就会产生互斥

**注意：**

- 读锁不支持条件变量
- 重入时升级不支持：即持有读锁再想申请写锁，会导致死锁
- 重入时降级支持：即持有写锁可以获得读锁

## 3.4 StampedLock

不支持可重入的读写锁

```java
private static StampedLock lock = new StampedLock();
//写方法
public static void write(){
    long writeLock = lock.writeLock();//写锁
    try {
        Thread.sleep(500);//处理业务
    } catch (InterruptedException e) {
        e.printStackTrace();
    }finally {
        lock.unlockWrite(writeLock);//释放锁
    }
}

//读方法
public static int read(){
    long readLock = lock.tryOptimisticRead();//乐观读
    if(lock.validate(readLock)){//数据没有改动，锁未失效
        return 0;//返回结果
    }
    try{//数据改动，进行锁升级
        readLock = lock.readLock();//真正的加读锁
        return 0;//返回结果
    }finally {
        lock.unlockRead(readLock);//释放锁
    }
}
```

该锁也是读写锁，但是它由于底层实现原理与上面的不同，其效率更高。

乐观读的锁并没有真正的锁定，而是获得了一个戳用来后续的验证









# 4 线程通信

## 4.1 wait and notify

| 方法               | 说明                                         |
| ------------------ | -------------------------------------------- |
| wait()             | 表示线程一直等待，知道其他线程通知，会释放锁 |
| wait(long timeout) | 制定等待的毫秒数                             |
| notify()           | 唤醒一个处于等待状态的线程                   |
| notifyAll()        | 唤醒同一个对象所有调用wait()方法的线程       |

## 4.2 park and unpark

它们是==LockSupport==类中的方法

- park：暂停线程
- unpark：重启线程。线程暂停之前调用unpark可以预先使得park不生效

```java
public static void main(String[] args) {
    Thread t1 = new Thread(){
        @Override
        public void run() {
            System.out.println("t1:start");
            for (int i = 0; i < 50; i++) {
                System.out.println(i);
                if(i==25){
                    System.out.println("park");
                    LockSupport.park();//暂停线程，WAITING状态
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
```

## 4.3 条件变量

[3.2.4 条件变量](# 3.2.4 条件变量)

## 4.4 volatile

- 可见性

- 有序性：在不改变程序结果的前提下， cpu 会对 java 程序形成的指令进行重排（流水线），提高执行效率

  不是原子性的

没有该关键字的变量，每个线程都是独立拥有一份的。所以主线程修改了状态子线程并不是停止

加了该关键字，所有线程共享，主线程修改状态之后子线程会停止

volatile 关键字的变量，每次读取都是从主存中获得变量，而不是线程私有的内存区域

```java
private static /*volatile*/ boolean isCandy = false;

public static void main(String[] args) {
    new Thread(()->{
        while(!isCandy){

        }
        System.out.println("好吧，我暂停");
    }).start();

    try {
        Thread.sleep(500);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    System.out.println("求求你暂停吧");
    isCandy = true;

}
```

## 4.5 信号量

```java
//1.创建信号量
Semaphore semaphore = new Semaphore(5);

//2.占用信号量
try {
    semaphore.acquire();
} catch (InterruptedException e) {
    e.printStackTrace();
}finally {
    semaphore.release();//3.释放信号量
}
```

限制访问共享资源的线程数

## 4.6 CountdownLatch

用来进行线程同步协作，等待所有线程完成倒计时

一个线程等待其他线程执行完毕，每有一个线程执行完毕，计数器减一

```java
public static void main(String[] args) {
    CountDownLatch downLatch = new CountDownLatch(3);//初始化计数

    new Thread(()->{
        System.out.println("t1.start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t1.end");
        downLatch.countDown();//执行结束，计数减一
    }).start();

    new Thread(()->{
        System.out.println("t2.start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t2.end");
        downLatch.countDown();//执行结束，计数减一
    }).start();

    new Thread(()->{
        System.out.println("t3.start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("t3.end");
        downLatch.countDown();//执行结束，计数减一
    }).start();

    try {
        System.out.println("main wait...");
        downLatch.await();//等待
    } catch (InterruptedException e) {
        e.printStackTrace();
    }finally {
        System.out.println("main end");
    }
}
```

## 4.7 CyclicBarrier

等待多个线程都进入阻塞状态，再一起开始执行

当计数器变为 0 之后，会进行重置

```java
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

}
```

# 5 线程安全集合类

线程安全集合类：

- Hashtable，Vector 不推荐使用，底层使用 sysnchronized 实现线程安全，效率低
- 使用 Collections 装饰的线程安全集合，在原有的基础上增加了 sysnchronized 实现线程安全
  - Collections.synchronizedCollection
  - Collections.synchronizedList
  - Collections.synchronizedMap
  - Collections.synchronizedSet
  - Collections.synchronizedNavigableMap
  - Collections.synchronizedNavigableSet
  - Collections.synchronizedSortedMap
  - Collections.synchronizedSortedSet
- JUC包下的类
  - Blocking 大部分实现基于锁，并提供用来阻塞的方法
  - CopyOnWrite 之类的容器修改开销较大
  - Concurrent 类型的容器
    - 内部使用了很多 cas 优化，一般可以提高吞吐量
    - 弱一致性
      - 遍历时弱一致性，例如：利用迭代器遍历时，如果容器发生修改，迭代器仍然可以继续进行遍历，这时内容是旧的
      - 求大小弱一致性，size 操作未必准确
      - 读取弱一致性





# 6 各种类的用法

## 6.1 原子正数

原子性相关类

```java
public static void main(String[] args) {
    AtomicInteger atomicInteger = new AtomicInteger();//原子类的主要作用是使得他们的操作都是原子性的
    AtomicBoolean atomicBoolean = new AtomicBoolean();
    AtomicLong atomicLong = new AtomicLong();
    
    atomicInteger.updateAndGet((value)->{
        return value * 10;
    });//原子性的对这个原子数做一个更复杂的运算

}
```

## 6.2 原子引用

使一个复杂类型的操作变为原子性的

```java
AtomicReference<Accout> banlance = new AtomicReference<Accout>();
```

以上的类在 cas 操作的时候，只会比较一个变量的当前值和自己认为的值（该类取得这个值的时候，这期间可能有其他线程已经修改了这个变量）是否相同，如果相同，他只知道是相同的，不会知道该变量是否有被修改过（A->B->A）。因此可以是用下面的类，使用版本号机制

```java
AtomicStampedReference<String> ref = new AtomicStampedReference<>("A", 0);//初始值和初始版本号
ref.getReference();//获取值
int stamp = ref.getStamp();//获取版本号
ref.compareAndSet("A", "B", stamp, stamp + 1);//比较，加版本

AtomicMarkableReference<String> ref1 = new AtomicMarkableReference<>("A",true);//初始值和布尔值
//该类在不关心改变次数，只关心是否有过改变时使用
```

## 6.3 原子数组

```java
AtomicIntegerArray atomicArray = new AtomicIntegerArray(10);//长度为10
```



# 7 线程池

一次性创建一个固定大小的池子，里面放着所有的线程，包括空闲的不空闲的。如果有任务来，就从空闲的线程中拿出来一个执行任务，执行完又放回去。这样就保证了不会频繁的创建和销毁线程，减少系统的开销

## 7.1 自定义线程池

线程池的作用就是减少频繁创建线程的开销以及一次性需要启动很多线程，我们可以将多的线程放在一个线程阻塞队列里，这样避免有太多的线程在同时运行

**自定义阻塞队列**

```java
//自定义阻塞队列
class BlockingQueue<T>{//也有现成的阻塞队列可用
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

    //带超时的阻塞获取
    public T take(int time, TimeUnit timeUnit){
        lock.lock();
        try{
            while(!queue.isEmpty()){//队列中没有
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
    public T take(){
        lock.lock();
        try{
            while(!queue.isEmpty()){//队列中没有
                emptyWait.await();
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

    //阻塞添加
    public void add(T element){
        lock.lock();
        try{
            while(queue.size() == cap){//容量满了
                fullWait.await();
            }
            queue.add(element);//添加了新元素
            emptyWait.signal();//唤醒消费
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    //获得当前的大小
    public int size(){
        lock.lock();
        try{
            return queue.size();
        }finally {
            lock.unlock();
        }
    }
}
```

**线程池**

```java
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
                workers.add(work);
                work.start();
            } else {
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
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }
            synchronized (workers){
               workers.remove(this);//将该任务从任务队列移除
            }

        }
    }
}
```

**测试**

```java
public class Reentrant {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(2, 1000, TimeUnit.MILLISECONDS, 10);
        for (int i = 0; i < 5; i++) {
            int j =i;
            threadPool.execute(()->{
                System.out.println(j);
            });
        }
    }
}
```

## 7.2 普通线程池

类图

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/JUC/JUC-ThreadPool.png)



### 7.2.1 ThreadPoolExecutor

**线程池状态**

ThreadPoolExecutor 使用 int 的高 3 位来表示线程池的状态，低 29 位表示线程数量

- RUNNING：111
- SHUTDOWN：000，不会接收新任务，但会处理阻塞队列剩余的任务
- STOP：001，会中断正在执行的任务，并抛弃阻塞队列任务
- TIDYING：010，任务全执行完毕，活动线程为 0 即将进入中介
- TERMINATED：011，终结状态

**构造方法**

```java
public ThreadPoolExecutor(int corePoolSize,//核心线程数目
                          int maximumPoolSize,//最大线程数目
                          long keepAliveTime,//生存时间-针对救急线程
                          TimeUnit unit,//时间单位-针对救急线程
                          BlockingQueue<Runnable> workQueue,//阻塞队列
                          ThreadFactory threadFactory,//线程工厂-可以为线程创建时起名
                          RejectedExecutionHandler handler)//拒绝策略
```

- 线程池中刚才开始没有线程，当一个任务提交给线程池后，线程池会创建一个新线程来执行任务
- 当线程数达到 corePoolSize 并没有线程空闲，这时再加入的任务就会进入 workQueue 阻塞队列，知道有空闲线程
- 如果队列选择了有界队列，那么任务超过了队列的大小时，会创建 maxinumPoolSize - corePoolSize 数目的线程来救急
- 如果线程达到 maxinumPoolSize 仍然有新任务这时会执行拒绝策略。拒绝策略 JDK 提供了四种实现
  - AbortPolicy 让调用者抛出 RejectedExecutionException 异常，这是默认策略
  - CallerRunsPolicy 让调用者运行任务
  - DiscardPolicy 放弃本次任务
  - DiscardOldestPolicy 放弃队列中最早的任务，本任务取而代之
  - Dubbo 的实现，在抛出 RejectedExecutionException 异常之前会记录日志，并 dump 线程栈信息，方便定位问题
  - Netty 的实现，是创建一个新线程来执行任务
  - ActiveMQ 的实现，带超时等待（60s）尝试放入队列
  - PinPoint 的实现，它使用了一个拒绝策略链，会逐一尝试策略链中每种拒绝策略

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/JUC/JUC-RejectPolicy.png)

- 当高峰过去后，超过 corePoolSize 的救急线程如果一段时间没有任务做，需要结束节省资源，这个时间由 keepActiveTime 和 unit 决定

### 7.2.3 工厂方法Executors

**newFixedThreadPool**

```java
public static ExecutorService newFixedThreadPool(int nThreads) {
    return new ThreadPoolExecutor(nThreads, nThreads,
                                  0L, TimeUnit.MILLISECONDS,
                                  new LinkedBlockingQueue<Runnable>());
}
```

根据实现可以看到，该方法创建的线程池没有救急线程，因此也不需要超时时间

阻塞队列是无界的

适用于任务量已知的情形

```java
public class Pool {

    public static void main(String[] args) {
        //1.创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);//没有救急线程
        
        //2.执行线程方式一，启动了三个线程任务，也看到只有两个线程进行了这三个线程的执行
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "->1");//pool-1-thread-1->1
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "->2");//pool-1-thread-2->2
            }
        });
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "->3");//pool-1-thread-1->3
            }
        });

        //3.关闭连接
        executorService.shutdown();
    }
}

class MyThread implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            System.out.println(Thread.currentThread().getName()+"执行了"+i);
        }
    }
}
```

**newCachedThreadPool**

```java
public static ExecutorService newCachedThreadPool() {
    return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
                                  60L, TimeUnit.SECONDS,
                                  new SynchronousQueue<Runnable>());
}
```

根据这个实现可以看到，该方法创建的所有线程都是救急线程，生存时间 60s

核心线程数是 0 ，最大线程数是 MAX.VALUE

适用于任务密集，每个任务执行时间短的场景

**newSingleThreadExecutor**

```java
public static ExecutorService newSingleThreadExecutor() {
    return new FinalizableDelegatedExecutorService
        (new ThreadPoolExecutor(1, 1,
                                0L, TimeUnit.MILLISECONDS,
                                new LinkedBlockingQueue<Runnable>()));
}
```



适用于 希望多个任务排队执行，线程数固定为 1，任务多于 1 时，会放入无界队列排序。任务执行完毕，这唯一的线程也不会被释放

区别：

- 自己创建一个单线程串执行任务，如果任务执行失败而终止，那么没有任何的补救措施，而线程池还会创建一个新线程保证线程池的正常工作。
- newSingleThreadExecutor 的线程个数始终为 1
  - FinalizableDelegatedExecutorService 应用的是装饰器模式，只对外暴露了 ExecutorService 接口，因此不能调用 ThreadPoolExecutor 中的特有方法
- newFixedThreadPool(1)，以后还可以修改
  - 对外暴露的是 ThreadPoolExecutor 对象，可以强转后调用 setCorePoolSize 等方法进行修改

**[任务调度线程池](# 7.3 任务调度线程池)**

### 7.2.4 提交任务

```java
//执行任务
public void execute(Runnable command);

//提交任务 task，用返回值 Future 获得任务执行结果
public Future<?> submit(Runnable task);
public <T> Future<T> submit(Callable<T> task);
public <T> Future<T> submit(Runnable task, T result);

//提交 tasks 中所有任务，timeout是超时时间
public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks);
public <T> List<Future<T>> invokeAll(Collection<? extends Callable<T>> tasks,
                                     long timeout, TimeUnit unit);

//提交 tasks 中所有任务，哪个任务先成功执行完毕，返回此任务执行结果，其他任务取消
public <T> T invokeAny(Collection<? extends Callable<T>> tasks);
public <T> T invokeAny(Collection<? extends Callable<T>> tasks,
                       long timeout, TimeUnit unit);
```

### 7.2.5 关闭线程池

```java
/**
 * 线程池状态改编为SHUTDOWN
 * 不会接受新任务
 * 但已提交的任务会执行完
 * 此方法不会阻碍调用线程的执行
 */
void shutdown();
```



```java
/**
 * 线程池状态变为 STOP
 * 不会接收新任务
 * 会将队列中的任务返回
 * 并用 interrupt 的方式中断正在执行的任务
 */
List<Runnable> shutdownNow();
```

### 7.2.6 线程池数量

**CPU密集型**

通常采用 $CPU核数+1$ 能够实现最优的 CPU 利用效率，+1 保证当线程由于页缺失故障或 其他原因导致暂停时，额外的这个线程就能替代，保证 CPU 时钟周期不被浪费



**I/O密集型**

CPU 不总是处于繁忙的状态，例如数据库读写，对 CPU 压力较低， IO操作频繁

经验公式

$线程数 = 核数 * 期望CPU利用率 * 总时间（CPU计算时间+等待时间）/CPU计算时间$

## 7.3 任务调度线程池

任务调度线程池加入之前使用 java.util.Timer 来实现定时功能

**newScheduledThreadPool**

```java
public static ScheduledExecutorService newScheduledThreadPool(
    int corePoolSize,//核心线程数
    ThreadFactory threadFactory) {
    return new ScheduledThreadPoolExecutor(corePoolSize, threadFactory);
}
```

简单使用

```java
//1.创建线程池
ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(2);

//2.添加延时任务
scheduledExecutorService.schedule(()->{
    System.out.println("我的世界");
},1,TimeUnit.SECONDS);

//3.固定速率执行任务
scheduledExecutorService.scheduleAtFixedRate(()->{
    System.out.println("我的世界");
},0,2,TimeUnit.SECONDS);//从0秒开始，每两秒执行一次。至少等2秒

//4.延时任务
scheduledExecutorService.scheduleWithFixedDelay(()->{
    System.out.println("我的世界");
},0,2,TimeUnit.SECONDS);//这个方法的延时是从上一个任务结束的时候开始计算
```

当线程任务执行时间过长时，固定速率会受到影响。第二个任务至少在 period 秒执行，前面的任务执行 3 秒，后面的任务会在第三秒立即执行

## 7.4 结果获取

使用 submit 进行任务的提交，可以使用 Future 获得任务执行结果，该任务需要实现的是 Callable 接口，带有返回值

如果线程执行异常也可以通过该方法进行获取

```java
//提交任务 task，用返回值 Future 获得任务执行结果
public Future<?> submit(Runnable task);
public <T> Future<T> submit(Callable<T> task);
public <T> Future<T> submit(Runnable task, T result);
```

## 7.5 ForkJoin线程池

ForkJoin 线程池是一个用来执行递归方式的线程池

简单使用

```java
public class Pool {

    public static void main(String[] args) {
        //1.创建线程池
        ForkJoinPool pool = new ForkJoinPool(4);//无参的情况下，线程数等于cpu核心数

        //2.启动任务
        Integer invoke = pool.invoke(new MyThread(10));
        System.out.println(invoke);
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
```

