package cn.edu.xidian.procom;


//管程法
public class Buffer {
    public static void main(String[] args) {
        SyncBuffer syncBuffer = new SyncBuffer();

        Comsumer comsumer = new Comsumer(syncBuffer);
        Producer producer = new Producer(syncBuffer);

        new Thread(producer,"生产").start();
        new Thread(comsumer,"消费").start();

    }
}

//消费者
class Comsumer implements  Runnable{
    SyncBuffer syncBuffer;

    public Comsumer() {
    }

    public Comsumer(cn.edu.xidian.procom.SyncBuffer syncBuffer) {
        this.syncBuffer = syncBuffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("消费了"+syncBuffer.pop().id+"个产品");
        }
    }
}

//生产者
class Producer implements Runnable{
    SyncBuffer syncBuffer;

    public Producer() {
    }

    public Producer(cn.edu.xidian.procom.SyncBuffer syncBuffer) {
        this.syncBuffer = syncBuffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("生产了"+i+"只鸡");
            syncBuffer.push(new Product(i));
        }
    }
}

//产品
class Product{
    int id;

    public Product(int id) {
        this.id = id;
    }
}

//缓冲区
class SyncBuffer{
    //创建一个大小为10的缓存区
    Product products[] = new Product[10];

    int cnt = 0;//记录缓存区的位置

    //生产者放入产品
    public synchronized void push(Product product){
        //如果容器满了等待消费者消费
        if(cnt>=products.length){
            //通知消费者消费，生产者等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //如果不满继续生产
        products[cnt++] = product;

        //可以通知消费者消费
        this.notifyAll();

    }


    //消费者消费产品
    public synchronized  Product pop(){
        //如果有产品就可以消费
        if(cnt<=0){
            //等待生产者生产
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //可以消费
        cnt--;
        Product product = products[cnt];
        System.out.println("我拿走了"+product.id+"号产品");

        //通知生产
        this.notifyAll();

        return product;
    }
}
