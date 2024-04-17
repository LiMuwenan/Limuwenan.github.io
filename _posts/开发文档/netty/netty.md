# 第1章 Java的I/O演进之路

## 1.1 I/O基础入门



### 1.1.1 Linux网络I/O模型简介

Linux内核将所有的外部设备看作一个文件，对一个文件的读写操作会调用内核提供的系统命令，返回一个`filedescriptor（fd，文件描述符）`。对一个socket的读写也会有相应的描述符，称为`socketfd（socket描述符）`，描述符就是一个数字，它指向内核中的一个结构体（文件路径，数据区等一些属性）

根据UNIX网络编程对I/O模型的分类，UNIX提供5中I/O模型。

1. 阻塞I/O模型：

默认情况下，所有文件操作都是阻塞的。套接字接口，在进程空间中调用`recvfrom`，其系统调用直到数据包到达且被复制到应用进程的缓冲区中或者发生错误时才返回，在此期间一直等待，进程在从调用recvfrom开始到它返回的整段时间内都是阻塞被阻塞的。

> 应用进程阻塞。单次recvfrom

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/netty/%E9%98%BB%E5%A1%9EIO%E6%A8%A1%E5%9E%8B.png)

2. 非阻塞I/O模型：

`recvfrom`从应用层到内核时，如果该缓冲区没有数据的话，就直接返回一个`EWOULDBLOCK`错误，一般使用非阻塞I/O模型进行`轮询`检查这个状态，看内核是不是有数据准备好。

> 应用进程不阻塞，轮询内核数据区，多次recvfrom

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/netty/%E9%9D%9E%E9%98%BB%E5%A1%9EIO%E6%A8%A1%E5%9E%8B.png)

3. I/O复用模型：

Linux提供`select/poll`，进程通信将一个或多个fd传递给select或poll调用，阻塞在select操作，这样select/poll可以帮我们监听多个fd是否处于就绪状态。select/poll顺序扫描fd是否就绪，而且支持的fd数量有限。Linux还提供`epoll`系统调用，epoll使用基于事件驱动驱动方式代替顺序扫描，因此性能更高。当有fd就绪时，立刻回调函数`rollback`

> select会阻塞；javanio中可以设置为非阻塞。有就绪事件时，调用一次recvfrom，数据从内核复制到应用缓冲区时阻塞

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/netty/IO%E5%A4%8D%E7%94%A8%E6%A8%A1%E5%9E%8B.png)

4. 信号驱动I/O模型：

首先开启套接口字信号驱动I/O功能，并通过系统调用`sigaction`执行一个信号处理函数（此系统调用立即返回，进程继续工作，非阻塞）。当数据准备就绪，就为该进程生成一个`SIGIO`信号，通过信号回调通知应用程序调用recvfrom来读取数据，并通知主循环函数处理数据。

> 有准备好的数据，上报SIGIO信号，调用recvfrom。数据从内核复制到应用缓冲区时阻塞

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/netty/%E4%BF%A1%E5%8F%B7%E9%A9%B1%E5%8A%A8IO%E6%A8%A1%E5%9E%8B.png)

5. 异步I/O：

告知内核启动某个操作，并让内核在操作完成后（包括将数据从内核复制到用户自己的缓冲区）通知我们。这种模型与信号驱动模型的主要区别是：信号驱动I/O由内核通知我们何时可以开始一个I/O操作；异步I/O模型由内核通知我们I/O操作何时已经完成。

> 有准备好的数据，上报SIGIO信号；数据复制完成，递交给aio_read。整个过程完成才会到应用进程

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/netty/%E5%BC%82%E6%AD%A5IO%E6%A8%A1%E5%9E%8B.png)

### 1.1.2 I/O多路复用技术

I/O多路复用把多个I/O的阻塞复用到同一个select的阻塞上，从而使单线程同时处理多个客户端请求。

主要使用场景：

- 服务器需要同时处理多个处于监听状态或者多个连接状态的套接字
- 服务器需要同时处理多种网络协议套接字

支持I/O复用的系统调用：select、pselect、poll、epoll。因为select的限制和缺陷，现在使用epoll。

1. **支持一个进程打开的socket描述符fd不受限制（仅受限于操作系统的最大文件句柄数）**

select最大缺陷就是单个进程所打开的FD有限制，由`FD_SETSIZE`设置，默认1024。这个值可以通过编译内核重新设置，但是会带来网络效率的下降。

epoll没有这个限制，在1GB内存机器上大约10万个句柄，具体可以使用`cat /proc/sys/fs/file-max`

2. **I/O效率不会随着FD数目的增加而线性下降**

传统select/poll在有一个很大的socket集合时，由于网络延时或者链路空闲，一时刻只有少数socket活跃，但是select和poll每次调用都会线性扫描全部集合，导致效率线性下降。

 epoll只对活跃socket进行操作，根据每个fd上面的callback函数实现，只有活跃的socket才会主动调用callback，idle状态的socket不会。

如果所有socket都处于活跃——在一个高速LAN环境，epoll不会比select/poll效率高太多，可能因为过多使用`epoll_ctl`，效率相比还会降低。

3. **使用mmap加速内核与用户空间的消息传递**

select、poll、epoll都需要内核把FD消息通知到用户空间。

epoll通过内核和用户空间mmap同一块内存实现。

4. **epoll的API更加简单**

包括创建一个epoll描述符、添加监听事件、阻塞等待所监听的事件发生、关闭epoll描述符等。







## 1.2 Java的演进

1. Java1.3：UNIX网络编程接口在I/O库中没有体现
2. Java1.4：发布NIO
   - 异步I/O缓冲区ByteBuffer
   - 异步I/O管道Pipe
   - 异步同步I/O管道Channel，包括ServerSocketChannel和SocketChannel
   - 多种字符集编解码能力
   - 非阻塞I/O多路复用器Selector
   - 基于Perl实现的正则库
   - 文件通道FileChannel
   - 不足：没有统一的文件属性（读写权限等）；API能力弱，目录的级联创建和递归遍历需要自己实现；底层存储系统的高级API无法使用；所有文件操作都是同步阻塞调用

3. Java1.7：NIO2.0
   - 获取文件属性的API
   - 提供AIO能力，支持基于文件的异步I/O操作和针对网络套接字的异步操作
   - 完成JSR-51定义的通道功能，包括对配置和多播数据报的支持



# 第2章 NIO入门

## 2.1 传统的BIO编程



BIO通常使用一个Acceptor线程负责监听客户端的连接，收到客户端连接请求之后为每个客户端建立一个新的线程处理。

缺点：当并发量足够大，线程和客户端数`1:1`增长。线程膨胀后系统性能急速下降，发生线程堆栈溢出、创建新线程失败等问题，最终导致进程宕机或者僵死。

笔记随附书上代码：BIO通信和伪异步通信。

[Netty权威指南样例代码](https://github.com/LiMuwenan/pratice/tree/main/Netty/src/main/java/cn/ligen/practice/chapter02/bio)



## 2.2 伪异步IO编程

伪异步相比于同步阻塞，一个线程或者线程池处理N个客户端数据，增加处理能力。

当有新的客户端接入时，将客户端的Socket封装成一个Task，放入线程池中进行处理。

JDK线程池维护一个消息队列和N个活跃线程，对消息队列中的任务进程处理。由于线程池可以设置消息队列的大小和最大线程数，因此，它的占用资源是可控的。

[Netty权威指南样例代码](https://github.com/LiMuwenan/pratice/tree/main/Netty/src/main/java/cn/ligen/practice/chapter02/bio2)



### 2.2.3 伪异步I/O弊端分析

`InputStream read`和`OutputStream write`两个阻塞方法

read：当对方发送请求或者应答消息比较慢，或者网络传输慢，读取输入流时通信将被阻塞，如果输入方需要60秒，那么读取一方I/O线程也会阻塞60秒。

write：调用write方法写输出流，将会被阻塞，直到所有要发送的字节全部写入完毕，或者发生异常。当消息的接收方处理缓慢的时候，将不能即使地从TCP缓冲区读取数据，这将会导致发送方的TCP窗口大小不断减小，直到为0，双方处于`Keep-Alive`状态，消息发送方将不能再项TCP缓冲区写入消息。

当服务端消息处理不及时，线程任务不能及时销毁，则在线程池工作队列中会有大量线程阻塞；而新的客户端接入请求就会排在阻塞线程之后，发生连接超时。



## 2.3 NIO编程

### 2.3.1 NIO类库简介

1. 缓冲区Buffer

和流I/O直接将数据写入Stream对象中不同，NIO中所有数据都是缓冲区处理的。

2. 通道Channel

通过可以读、写或者同时进行。因为通道是全双工的，可以更好的映射底层API

3. 多路复用器Selector

Selector会不断轮询注册在它上面的Channel，如果某个Channel上面发生读或者写事件，这个Channel就处于就绪状态，会被Select轮询出来，然后通过SelectionKey可以获取就绪Channel的集合，进行后续I/O操作。

一个多路复用器可以同时轮询多个Channel，JDK使用epoll()代替传统select实现，所以没有最大连接句柄数限制，一个线程就可以接入成千上万客户端。

### 2.3.2 NIO服务端序列图

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/netty/NIO%E6%9C%8D%E5%8A%A1%E7%AB%AF%E9%80%9A%E4%BF%A1%E5%BA%8F%E5%88%97%E5%9B%BE.png)

nio步骤示例

```java
public class ReactorTask implements Runnable{

    Selector selector = null;
    ServerSocketChannel serverSocketChannel = null;

    public static void main(String[] args) throws IOException {
        // 1、打开ServerSocketChannel，用户建通客户端的连接，它是所有客户端连接的父管道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2、绑定监听端口，设置连接为非阻塞模式
        serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", 8080));
        serverSocketChannel.configureBlocking(false);
        // 3、创建Reactor线程，创建多路服务器并启动线程
        Selector selector = Selector.open();
        new Thread(new ReactorTask()).start();

    }

    @Override
    public void run() {
        try {
            // 4、将ServerSocketChannel注册到Reactor线程的多路复用器上，监听ACCEPT事件
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            // 5、多路复用器在线程run方法的无线循环体内轮询准备就绪的key
            while (true) {
                int num = selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    // 处理
                    // 6、多路复用器监听到有新客户端接入，处理新接入请求
                    SocketChannel channel = serverSocketChannel.accept();
                    // 7、设置客户端链路为非阻塞
                    channel.configureBlocking(false);
                    channel.socket().setReuseAddress(true);
                    // 8、将新接入客户端注册到Reactor线程的多路复用器上，监听读
                    channel.register(selector, SelectionKey.OP_READ);
                    // 9、异步读缓冲区
                    int readNumber = channel.read(ByteBuffer.allocateDirect(1024));
                    // 10、对Buffer进行编码
                    
                }
            }
        } catch (ClosedChannelException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
```

### 2.3.3 NIO创建的TimeServer源码分析

[Netty权威指南样例代码](https://github.com/LiMuwenan/pratice/tree/main/Netty/src/main/java/cn/ligen/practice/chapter02/nio)

```java
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.out.println("port converter error");
            }
        }

        MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
        new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
    }
}


public class MultiplexerTimeServer implements Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile boolean stop;

    public MultiplexerTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress("127.0.0.1", port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("The time server is start in port : " + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Server establish error.");
            System.exit(1);
        }
    }

    public void stop() {
        this.stop = true;
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            if (key.isAcceptable()) {
                // Accept new connection
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                // Add  the new connection to the selector
                socketChannel.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()) {
                // Read the data
                SocketChannel socketChannel = (SocketChannel) key.channel();
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int read = socketChannel.read(readBuffer);

                if (read > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, StandardCharsets.UTF_8);
                    System.out.println("The time serve receive order : " + body.trim());
                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body.trim())
                            ? LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                            : "BAD ORDER";
                    doWrite(socketChannel, currentTime);
                } else if (read < 0) {
                    key.cancel();
                    socketChannel.close();
                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String responses) throws IOException {
        if (responses != null && responses.trim().length() > 0) {
            byte[] bytes = (responses + "\r\n").getBytes();
            ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
            writeBuffer.put(bytes);
            writeBuffer.flip();
            channel.write(writeBuffer);
        }
    }
}
```

`MultiplexerTimeServer`作为一个独立线程处理客户端请求；

- 24~37行构造方法，ServerSocketChannel绑定地址、端口、非阻塞模式，初始化成功后，将ServerSocketChannel注册到Selector，监听`SelectionKey.OP_ACCEPT`
- 45-67行循环遍历Selector，无论是否有事件发生，selector根据设定时间被唤醒一次，检查事件；当有处于就绪状态的Channel时，返回`SelectionKey`
- 71~79处理消息，根据`SelectionKey`判断事件，进行对应操作；通过ServerSocketchannel的aceept接收客户端连接请求并建立`SocketChannel`，到这里相当于完成三次握手
- 80~100行读取请求消息，先创建一个`ByteBuffer`，因为不能知道客户端发送码流大小，先开辟一个·MB缓冲区。然后调用SocketChannel方法读取请求码流。设置为非阻塞模式后，read方法阻塞
  - 返回值大于0：读到字节，对字节进行编码
  - 返回值等于0：没有读到字节，属于正常场景，忽略
  - 返回值-1：链路已经关闭，需要关闭SocketChannel
- 104~111行返回数据



### 2.3.5 NIO创建的TimeClient源码分析

```java
public class TimeClientHandle implements Runnable{

    private String host;
    private int port;
    private Selector selector;

    private SocketChannel socketChannel;

    private volatile boolean stop;

    public TimeClientHandle(String host, int port) {
        this.host = host == null ? "127.0.0.1" : host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        while (!stop) {
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    try {
                        handleInput(key);
                    } catch (Exception e) {
                        if (key != null) {
                            key.cancel();
                            if (key.channel() != null) {
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        if (selector != null) {
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException {
        if (key.isValid()) {
            SocketChannel channel = (SocketChannel) key.channel();
            if (key.isConnectable()) {
                if (channel.finishConnect()) {
                    channel.register(selector, SelectionKey.OP_READ);
                    doWrite(channel);
                } else {
                    System.out.println("SocketChannel connect failed.");
                    System.exit(1);
                }
            }
            if (key.isReadable()) {
                ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                int readBytes = channel.read(readBuffer);
                if (readBytes > 0) {
                    readBuffer.flip();
                    byte[] bytes = new byte[readBuffer.remaining()];
                    readBuffer.get(bytes);
                    String body = new String(bytes, StandardCharsets.UTF_8);
                    System.out.println("Now is : " + body);
                    this.stop = true;
                } else if (readBytes < 0) {
                    key.cancel();
                    channel.close();
                }
            }
        }
    }

    private void doConnect() throws IOException {
        if (socketChannel.connect(new InetSocketAddress(host, port))) {
            socketChannel.register(selector, SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }

    private void doWrite(SocketChannel sc) throws IOException {
        byte[] req = "QUERY TIME ORDER".getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
        writeBuffer.put(req);
        writeBuffer.flip();
        sc.write(writeBuffer);
        if (!writeBuffer.hasRemaining()) {
            System.out.println("Send order 2 server succeed.");
        }
    }
}
```

- 11~22行构造函数，初始化，`SocketChannel`设置非阻塞
- 96~103行连接操作，`socketChannel.connect`方法对已经建立的连接返回true；否则false。将其注册到selector，新连接监听事件`SelectionKey.OP_CONNECT`
- 32~55行循环处理就绪事件
- 66~94行，对连接事件和可读事件进行处理

selector关闭时，系统底层会自动释放socketChannel资源。

以上代码没有处理`半包`、`粘包`等问题。



## 2.4 AIO编程

NIO 2.0引入了异步通道概念，并提供了异步文件通道和异步套接字通道的实现。异步通告提供以下两种方式获取操作结果：

- 通过`java.uitl.concurrent.Future`类来表示异步操作结果
- 在执行异步操作的时候传入一个`java.nio.channels`

`CompletionHandler`接口实现类为操作完成回调。

NIO 2.0是真正的异步非阻塞I/O，使用底层事件驱动I/O（AIO）。不再需要多路复用器selector，对注册通道进行轮询操作，从而简化NIO模型。



### 2.4.1 AIO创建的TimeServer源码分析

[Netty权威指南-样例代码](https://github.com/LiMuwenan/pratice/tree/main/Netty/src/main/java/cn/ligen/practice/chapter02/aio/server)

`AsyncTimeServerHandler.java`

21~30行构造函数，新建服务端，绑定端口

33~41行初始化`CountDownLatch`，然后进行阻塞，防止服务端执行完成退出。实际项目中不需要启动独立线程处理`AsynchronousServerSocketChannel`。

35行接收客户端连接，并且是一个异步操作，传递一个`CompletionHandler<AsynchronousSocketChannel, ? super A>`类型的handler实例处理接入客户端的连接操作

`AcceptCompletionHandler.java`

> CompletionHandler有两个接口
>
> ```java
>     @Override
>     public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandler attachment) {
>         attachment.asynchronousServerSocketChannel.accept(attachment, this);
>         ByteBuffer buffer = ByteBuffer.allocate(1024);
>         result.read(buffer, buffer, new ReadCompletionHandler(result));
>     }
> 
>     @Override
>     public void failed(Throwable exc, AsyncTimeServerHandler attachment) {
>         exc.printStackTrace();
>         attachment.latch.countDown();
>     }
> ```
>
> completed中 `attachment`获取成员变量再次调用节后客户端连接方法。如果有新客户端接入，系统将回调传入`CompletionHandler`实例的completed方法，表示新客户端接入成功。因为一个`AsynchronousServerSocketChannel`可以接入成千上万客户端，所以继续调用接入方法等待新客户端。
>
>  
>
> 第5行使用AsynchronousSocketChannel类的异步读操作，参数：
>
> - ByteBuffer dst：接收缓冲区，用于从异步Channel中读取数据包
> - A attachment：异步Channel携带的附件，通知回调的时候作为入参使用
> - CompletionHandler<Integer, ? super A>：接收通知回到的业务handler，本例中是读取消息handler

`ReadCompletionHandler.java`

19~21行构造函数将`AsynchronousSocketChannel`传递到handler中使用。

24~33行对接收到的消息进行处理，后通过doWrite方法将响应发送回客户端

51行进行异步写方法，completed方法中，将缓冲区中的数据全部发送



### 2.4.2 AIO创建的TimeClient源码分析

[Netty权威指南-样例代码](https://github.com/LiMuwenan/pratice/tree/main/Netty/src/main/java/cn/ligen/practice/chapter02/aio/client)

23~31行构造函数创建`AsynchronousSocketChannel`对象

36行异步连接，连接之后执行50行completion方法进行写数据处理，向服务端发起请求

62行异步读，读取服务端返回的数据



JDK底层通过线程池ThreadPollExecutor执行回调通知，异步回调通知类由`sun.nio.ch.AsynchronousChannelGroupImpl`实现，经过层层调用，最终回调我们设置的方法。

异步SocketChannel是被动执行对象，不需要独立线程处理。





## 2.5  4中I/O的对比

### 2.5.1 概念澄清

1. 异步非阻塞I/O

JDK 1.4中的nio `SocketChannel`、` ServerSocketChannel`以UNIX网络编程模型来说只能算是基于I/O复用技术非阻塞I/O，不能算异步非阻塞I/O

JDK 1.7中的`AsynchronousServerSocketChannel`、`AsynchronousSocketChannel`是真正的异步I/O



2. 多路复用器Selector

NIO 1.0基于select/poll

NIO 2.0基于epoll

通过轮询注册在多路复用器的Channel进行调用



## 2.6 选择Netty的理由

### 2.6.2 为什么选择Netty

优点：

- API简单、开发门槛低
- 功能强大，预置多种编解码功能，支持多种主流协议
- 定制能力强，可以通过ChannelHandler对通信框架进行灵活扩展
- 性能高、成熟稳定，社区huo'yue





# 第3章 Netty入门应用

## 3.2 服务端开发

例子中使用`netty-all-5.0.0-Alphal`版本

[Netty权威指南-样例代码](https://github.com/LiMuwenan/pratice/tree/main/Netty/src/main/java/cn/ligen/practice/chapter03/netty)

`TimeServer`

33~34行创建两个`NioEventLoopGroup`实例。该对象是线程组，包含了一组NIO线程，专门用于网络事件处理。创建两个，一个用于服务端接收客户端连接，另一个进行SocketChannel进行网络读写。

36行创建`ServerBootstrap`对象，Netty启动NIO服务端辅助启动类

37行将两个NIO线程组传入ServerBootstrap中，然后设置创建的Channel为`NioServerSocketChannel`；然后设置TCP参数，将backlog设置为1024；最后绑定I/O事件处理类`ChildChannelHandler`，用来处理IO读写

42行调用ServerBootstrap.bind方法绑定监听端口，调用同步阻塞方法sync等待绑定操作完成

44行sync阻塞，等待服务端链路关闭之后main函数再退出

48~49行进行线程组资源释放

`TimeServerHandler`

TimeServerHandler继承自`ChannelHandlerAdapter`用于网络事件读写，通常只需要关注`channelRead`和`exceptionCaught`方法

21行将消息强转为`ByteBuf`，类似java原生`ByteBuffer`，提供了更强大的能力

35行调用flush方法，将发送队列中的消息写入到SocketChannel中



## 3.3 Netty客户端开发

[Netty权威指南-样例代码](https://github.com/LiMuwenan/pratice/tree/main/Netty/src/main/java/cn/ligen/practice/chapter03/netty)

`TimeClient`

31行创建客户端处理I/O的线程组，再创建客户端辅助启动类；channel设置NioSocketChannel类，当创建该类之后，会使用handler中的initChannel方法进行初始化

43行进行异步连接，同步阻塞等待连接成功

46行等待客户端连接关闭，之后退出释放

`TimeClientHandler`

26~27行，客户端和服务端建立链路成功之后，Netty线程会调用`channelActive`方法，发送查询指令给服务端

31~37行，处理服务端返回的时间数据

40~43行，发生异常，释放客户端资源

# 第4章 TCP粘包/拆包问题的解决之道

## 4.1 TCP粘包/拆包

TCP是流数据，是一串没有界限的数据，TCP底层不知道流数据的具体含义，它根据TCP缓冲区的实际情况进行包的划分（MTU）。所以一个大数据报被拆分为多个小数据报，也有可能把多个小包封装成一个大包。

### 4.1.1 TCP粘包/拆包问题说明

### 4.1.2 TCP粘包/拆包发生的原因

- 应用程序write写入的字节大小大于套接字发送缓冲区大小
- 进行MSS大小的TCP分段
- 以太网的payload大于MTU进行IP分片

### 4.1.3 粘包问题的解决策略

- 消息定长，每个报文固定长度X，如果不够，空位补零
- 在包尾增加回车换行符等特殊字符进行分割，例如FTP协议
- 将消息分为消息头和消息体，消息头中包含表示消息总长度（或者消息体长度）的字段

## 4.2 未考虑TCP粘包导致功能异常案例

[Netty权威指南-样例代码](https://github.com/LiMuwenan/pratice/tree/main/Netty/src/main/java/cn/ligen/practice/chapter04/decoder)

按照设计，客户端100次循环，counter计数应该打印100，服务端也应该接收到100次

但是没有拆包，导致结果不符合预期

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/netty/%E6%9C%AA%E8%A7%A3%E5%86%B3%E7%B2%98%E5%8C%85%E5%AF%BC%E8%87%B4%E7%9A%84%E9%97%AE%E9%A2%98.png)





## 4.3 利用LineBasedFrameDecoder解决TCP粘包问题

[Netty权威指南-样例代码](https://github.com/LiMuwenan/pratice/tree/main/Netty/src/main/java/cn/ligen/practice/chapter04/line)

使用`LineBasedFrameDecoder`和`StringDecoder`进行解析

执行结果：

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/netty/netty%E8%87%AA%E5%B8%A6decoder.png)

### 4.3.4 LineBasedFrameDecoder和StringDecoder的原理解析

`LineBasedFrameDecoder`依次遍历ByrBuf中的可读字节，判断是否有`\n`或者`\r\n`，如果有就分割数据流，从可读索引到结束位置区间的字节组成一行。它支持以换行符为结束标志的解码器，支持携带结束符或者不携带结束符两种编码方式，同时支持配置单行的最大长度，如果连续读取到最大长度后仍然没有发现换行符，就会抛出异常，同时忽略掉之前的异常码流。

`StringDecoder`将接收到的码流转换为字符串，然后调用后面的Handler





# 第5章 分隔符和定长解码器的应用

分隔符解码器：DelimiterBasedFrameDecoder

定长解码器：FixedLengthFrameDecoder

## 5.1 DelimiterBasedFrameDecoder应用开发

EchoServer服务，以`$_`作为分隔符

[Netty权威指南-样例代码](https://github.com/LiMuwenan/pratice/tree/main/Netty/src/main/java/cn/ligen/practice/chapter05/delimiter)

### 5.1.1 DelimiterBasedFrameDecoder服务端开发

`EchoServer`

- 43~46行先创建分隔符，然后创建`DelimiterBasedFrameDecoder`对象，将其加入`ChannelPipeLine`中

​	DelimiterBasedFrameDecoder使用了两个参数的构造方法，第一个参数1024表示单条消息的最大长度，当达到该长度后没有查找到分隔符，就抛出`TooLongFrameException`异常，防止由于异常码流确实分隔符导致内存溢出；第二个参数是分隔符对象

`EchoServerHandler`

- 18~22行由于前面解码器已经解析到完整的消息体，最后将要回复消息拼接分隔符返回





## 5.2 FixedLengthFrameDecoder应用开发

[Netty权威指南-样例代码](https://github.com/LiMuwenan/pratice/tree/main/Netty/src/main/java/cn/ligen/practice/chapter05/fixed)

### 5.2.1 FixedLengthFrameDecoder服务端开发

initChannel中将DelimiterBasedFrameDecoder换成FixedLengthFrameDecoder解码器就可以





# 第6章 编解码技术



# 第10章 HTTP协议开发应用



## 10.1 HTTP协议介绍

### 10.1.1 HTTP协议的URL

> http://host\[":"port][abs_path]

- http表示要获取的网络资源
- host表示域名或者IP
- port指定端口，默认80
- abs_path表示请求资源的URI

## 10.2  Netty HTTP服务端入门开发

例子：文件服务器使用HTTP协议对外提供服务，当客户端通过浏览器访问文件服务器时，对访问路径进行检查，检查失败时返回HTTP 403错误，该页面无法访问；如果校验通过，以链接的方式打开当前文件目录，每个目录或者文件都是个超链接，可以递归访问。

如果是目录。可以递归访问它下面的子目录或者文件，如果是文件且可读，则可以在浏览器端直接打开，或者通过【目标另存为】下载该文件。

### 10.2.2 HTTP服务端开发

