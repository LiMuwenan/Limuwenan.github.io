# 1 简介

TB级别消息队列

- Kafka
  - 分布式流媒体平台
  - 应用：系统消息、日志收集、用户行为跟踪、流式处理
- Kafka特点
  - 高吞吐量、消息持久化、高可靠性、高扩展性
- Kafka术语
  - Broker：每一个Kafka服务器称为Broker
  - Zookeeper：独立的应用，不属于Kafka。用该应用管理集群（Cluster）
  - Topic：主题，存放消息的地方
  - Partition：主题的子分区
  - Offset：消息在分区内存放的索引
  - Leader Replica：主副本，用来冗余的保存消息。当有请求时可以返回消息
  - Follower Replica：从副本，主副本出问题从从副本恢复

## 1.1 简单配置

[kafaka官方下载](https://kafka.apache.org/downloads)

解压到磁盘进行配置

- /config/zookeeper.properties

```properties
# the directory where the snapshot is stored.
# 缓存数据存放路径
dataDir=d:/Program/data/zookeeper
# the port at which the clients will connect
clientPort=2181
# disable the per-ip limit on the number of connections since this is a non-production config
maxClientCnxns=0
# Disable the adminserver by default to avoid port conflicts.
# Set the port to something non-conflicting if choosing to enable this
```

- /config/server.properties

```properties
# A comma separated list of directories under which to store log files
log.dirs=d:/Program/data/kafka-logs
```

- /config/consumer.properties

默认是test-consumer-group

```properties
# consumer group id
group.id=community-consumer-group
```



### 1.1.1 启动和关闭

到kafka解压目录目录下执行该命令启动

- 这是启动zookeeper

```
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
```

- 启动kafka

```
bin\windows\kafka-server-start.bat config\server.properties
```



### 1.1.2 创建kafka主题

命令行到 bin\windows文件夹下

这个命令是创建主题，主题代表分类和位置

- 9092是默认的端口号
- --replication-factor 1：一个副本
- --partitions 1：一个分区
- --topic test：主题的名字

```
kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test
```

然后执行命令查看该端口下的topics

```
kafka-topics.bat --list --bootstrap-server localhost:9092
```

### 1.1.3 给主题发送消息

以生产者模式的身份发送消息

- --broker-list localhost:9092：给 哪些服务器发消息
- --topic test：给什么主题发送消息

```
kafka-console-producer.bat --broker-list localhost:9092 --topic test
```



### 1.1.4 读取消息，消费者身份

以消费者身份读取数据

- --bootstrap-server localhost:9092：从哪些服务器读取消息
- --topic test：从什么主题读取消息
- --from-beginning：从头开始读取

```
kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic test --from-beginning
```



## 1.2 阻塞队列

- BlockingQueue
  - 解决线程通信的问题
  - 阻塞方法：put take
- 生产者消费者模式
  - 生产者：产生数据的线程
  - 消费者：使用数据的线程
- 实现类
  - ArrayBlockingQueue
  - LinkedBlockingQueue
  - PriorityBlockingQueue SynchronousQueue DelayQueue

```java
public class Base {
    public static void main(String[] args) {
        BlockingQueue queue = new ArrayBlockingQueue(10);
        new Thread(new Producer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
        new Thread(new Consumer(queue)).start();
    }

}

class Producer implements  Runnable{

    private BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        try{
            for (int i = 0; i < 100; i++) {
                Thread.sleep(20);
                //阻塞方法
                queue.put(i);
                System.out.println(Thread.currentThread().getName()+"生产了"+i+"："+queue.size());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

class Consumer implements  Runnable{
    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue){
        this.queue = queue;
    }
    @Override
    public void run() {
        try{
            while(true){
                Thread.sleep(new Random().nextInt(500));
                //阻塞方法
                queue.take();
                System.out.println(Thread.currentThread().getName()+"消费了"+queue.size());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
```



# 2 Springboot中简单使用

导入依赖

```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
    <version>2.2.7.RELEASE</version>
</dependency>
```

使用前需要先启动zookeeper和kafka

```java
@SpringBootTest
public class KafkaTests {

    @Autowired
    private KafkaProducer kafkaProducer;

    @Test
    public void testKafka(){
        //主动发送消息
        kafkaProducer.sendMessage("test","hello");
        kafkaProducer.sendMessage("test","world");

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //被动接收消息
    }

}

//封装生产者与消费者代码
@Component
class KafkaProducer{

    @Autowired
    private KafkaTemplate kafkaTemplate;

    //发送消息
    public void sendMessage(String topic,String content){
        kafkaTemplate.send(topic,content);
    }
}

@Component
class KafkaConsumer{

    @KafkaListener(topics={"test"})//监听test主题
    public void handleMessage(ConsumerRecord record){
        System.out.println(record.value());
    }

}
```

