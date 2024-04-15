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
