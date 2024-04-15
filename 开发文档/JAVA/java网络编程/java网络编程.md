# 第一章 基本网络概念

## 1.1 网络

## 1.2 网络的分层

**主机网络层**

**网络层**

**传输层**

**应用层**

## 1.3 IP、TCP、UDP

**IP地址和域名**

**端口**

## 1.4 Internet

**Internet地址分块**

**网络地址转换**

网络地址转换（Network Address Translation，NAT）

路由器会监视出站和入站连接，调整IP数据报中的地址。对于出站的包，它将源地址改为路由器的外部地址；对于入站的包，它将目的地址改为一个本地地址

**防火墙**

**代理服务器**

代理服务器与防火墙有关。如果防火墙会组织一个网络上的主机与外界直接建立连接，那么代理服务器就起到了中间人的作用。

如果防火墙组织一个机器连接外部网络，这个机器可以请求本地代理服务器的web页面，然后代理服务器会请求Web服务器的页面。

防火墙一般工作在传输层或网络层，代理服务器通常在应用层（SOCKS代理工作在传输层，可以代理所有TCP/UDP，而不用考虑应用层）。

代理服务器实现缓存：当请求web服务器的文件时，代理服务器首先查看此文件是否已在缓存中。如果文件在缓存中，那么代理服务器将提供缓存中的文件。如果不在，那么代理服务器将请求该文件，并存在自己的缓存中，再转发给请求访。

**CS模型**

## 1.5 Internet标准

IETF：不太正式的民间组织

W3C：厂商组织，缴纳会员费

IETF标准包括TCP/IP、MIME、SMTP

W3C标准包括HTTP、HTML、XML

**IETF RFC**

RFC（request for comments）虽然是征求意见稿，但是都是已完成的工作

**W3C推荐**



# 第二章 流

网络程序所做的很大一部分工作都是在简单的输入和输出：将字节从一个系统移动到另一个系统。



Java的IO建立在流（stream）上。输入流读取数据；输出流写入数据。所有流都有相同的基本方法来写入数据，创建一个流之后，读写通常可以忽略具体细节。



过滤器（filter）流可以串联到输入流或者输出流上。读写数据时，过滤器可以修改数据（例如加密或者压缩），或者只是提供额外的方法，将读写数据转换为其他格式。



阅读器（reader）和书写器（writer）可以串联到输入输出流上，允许程序读写文本（即字符）而不是字节。只要正确地使用，他们可以处理很多字符编码。



流是同步的。当一个线程请求一个流读/写一段数据，在做任何其他操作前，它要等待所读写的数据。Java还支持使用通道和缓冲区的非阻塞IO。



## 2.1 输出流

```java
public abstract class OutputStream implements Closeable, Flushable {//基本流类

    //以下是主要方法
    
    public abstract void write(int b) throws IOException;

    public void write(byte b[]) throws IOException {
    }

    public void write(byte b[], int off, int len) throws IOException {
    }

    public void flush() throws IOException {
    }

    public void close() throws IOException {
    }

}
```

OutputStream 的子类使用这些方法像某种特定介质写入数据：

- FileOutputStream：将数据写入文件
- TelnetOutputStream：将数据写入网络连接
- ByteArrayOutputStream：将数据写入可扩展的字节数据

```java
public abstract void write(int b) throws IOException;
```

是一个抽象方法，它们在子类中实现

FileOutputStream 中使用了原生 native 方式（底层源码），ByteArrayOutputStream 中将参数拼接在了流的最后面。

该参数将会以无符号字节（8 位二进制）的形式进行拼接，参数范围在0~255，如果超过这个范围，也只会保留最低的 8 位。



下面是一个循环输出字符流的例子，这个out不一定是文件输出流，其他的流也可以

```java
public static void generateCharacters(OutputStream out){
    int firstPrintableCharacter = 33;
    int numberOfPrintableCharacters = 94;
    int numberOfCharacterPerLine = 72;

    int start = firstPrintableCharacter;
    while (true) {
        try {
            for (int i = start; i < start + numberOfCharacterPerLine; i++) {
                out.write(
                    ((i - firstPrintableCharacter) % numberOfPrintableCharacters)
                    + firstPrintableCharacter);
            }
            out.write('\r');//回车
            out.write('\n');//换行
            start = ((start+1) - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
```

上面是一个一个字节写入数据的，还可以使用另外两个重载方法进行多字节写入

```java
public static void generateCharacters(OutputStream out) throws IOException{
    int firstPrintableCharacter = 33;
    int numberOfPrintableCharacters = 94;
    int numberOfCharacterPerLine = 72;
    int start = firstPrintableCharacter;
    byte[] line = new byte[numberOfCharacterPerLine+2];//+2对应回车符和换行符

    while (true) {
        for (int i = start; i < start + numberOfCharacterPerLine; i++) {
            line[i-start] = (byte)((i-firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter);
        }
        line[72] = (byte)'\r';//回车
        line[73] = (byte)'\n';//换行
        out.write(line);
        start = ((start+1) - firstPrintableCharacter) % numberOfPrintableCharacters + firstPrintableCharacter;
    }
}
```

与网络硬件的缓存一样，流还可以在软件中得到缓冲，即可以用java代码缓存。一般来说，可以通过BufferedOutputStream或BufferedWriter串联到底层流上来实现。

因此在写入数据完成后，刷新（flush）输出流非常重要。

例如，假设已经向使用了HTTP Keep-Alive的HTTP 1.1 服务器写入了300字节请求，通常你会等待响应，然后在发送更多的数据。不过，如果输出流有一个1024字节的缓冲区，那么这个流在发送缓冲区中的数据之前会等待更多的数据到达。在服务器响应到达之前不会向流写入等多数据，但是响应永远也不会到来，因为请求还没有发送。这样使得服务器发送了数据，但是因为数据不够大，客户端没有接收到；客户端没有接收到消息，也没有进行下一步请求；形成一个死锁的状态。

使用 flush 可以刷新数据流，立即将输出流中的数据发送出去，即使没有满

当结束一个流时，要通过调用它的 close() 方法将其关闭。这会释放与这个流关联的所有资源。如果这个流来自一个网络连接，那么关闭这个流也会终止这个链接。一旦输出流关闭，继续写入会抛出IOException异常。ByteOutputStream仍然可以转换为实际字节数组，DigestOutputStream仍然可以返回其摘要。

 在一个长时间运行的程序中，如果未能关闭一个流，则可能会泄露文件句柄、网络端口和其他资源。Java6及早期版本中，在finally中关闭流。

对于 try-catch 语句块，要在语句块外声明变量，语句块内初始化变量，在关闭 close 的时候需要进行非 null 判断

```java
FileOutputStream out = null;
try {
    out = new FileOutputStream("data.txt");
    generateCharacters(out);
} catch (FileNotFoundException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
}finally {
    if (out!=null){
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

在java 7 之后，可以使用 带资源的try

```java
try(OutputStream out = new FileOutputStream("data.txt")){
    out.write('\r');
}catch(IOException e){
    e.printStackTrace();
}
```

这样不用再显式的关闭流。使用带资源try，需要该资源实现Closeable接口，这包括几乎所有需要释放的对象



## 2.2 输入流

```java
public abstract class InputStream implements Closeable {//基本输入流类

    // MAX_SKIP_BUFFER_SIZE is used to determine the maximum buffer size to
    // use when skipping.
    private static final int MAX_SKIP_BUFFER_SIZE = 2048;
    
    public abstract int read() throws IOException;
    public int read(byte b[]) throws IOException{}
    public int read(byte b[], int off, int len) throws IOException{}
    public long skip(long n) throws IOException{}
    public int available() throws IOException{}
    public void close() throws IOException{}
    public synchronized void mark(int readlimit) {}
    public synchronized void reset() throws IOException{}
    public boolean markSupported(){}
```

InputStream的具体子类使用这些方法从某种介质中读取数据

- TelnetInputStream：从网络连接中读取数据
- ByteArrayInputStream：从字节数组中读取数据

InputStream的基本方式是没有参数的 read() 方法啊，这方法从输入流中读取1字节数据，作为一个0~255的int返回。流结束通过返回-1来表示。read 方法会等待并阻塞其后任何代码的执行，直到有1字节的数据可以读取。因为每次只能读取一个字节，所以在读取汉字或多字节字符时，会形成乱码。

下面的代码将从输入流中读取10个字节放在byte数组中

```java
byte[] input = new byte[10];
for (int i = 0; i < input.length; i++) {
    int b = in.read();
    if (b == -1) break;
    input[i] = (byte) b;
}
for (int i = 0; i < input.length; i++) {
    System.out.print(input[i]+" ");
}
//文本文件：jaskifjiaj
//输出：106 97 115 107 105 102 106 105 97 106
```

虽然 read() 只读取1字节，但它会返回一个 int。这样把结果存储在字节数组中就需要进行转换。0~255的无符号数，转换为字节的时候，会变为 -128~127 的有符号字节。

然后可以将有符号字节再转换为无符号字节

```java
int c = b>=0 ? 0 : b+255;
```

其他的 read() 重载方法也可以多字节一次性读取数据

下面就是 read 多字节读取的方法，返回读取到的字节数目

```java
byte[] input = new byte[1024];
in = new FileInputStream("data1.txt");
int bytesRead = in.read(input);
System.out.println(bytesRead);//返回读取到的字节数
```

如果我们希望有足够的数据填满input再返回，就可以使用一个循环来进行读取

```java
byte[] input = new byte[1024];
in = new FileInputStream("data1.txt");
int bytesRead = in.read(input);
int byteToRead = 1024;
while (bytesRead < byteToRead) {
	bytesRead += in.read(input, bytesRead, byteToRead - bytesRead);
}
```

如果这两个方法在尝试读取暂时为空但是已经打开的网络缓冲区，它通常会返回0，表示没有数据可用，但流还没有关闭。这比单字节 read 好，在这种情况下，单字节会阻塞正在运行的线程。



所有三个read方法都返回-1来表示结束。如果流已经结束，而有没有读取的数据，多字节read会返回这些数据，直到缓冲区清空。其后任何一个read都会返回-1。结束符-1永远都不会放入数组中。

上面的循环读取例子中没有考虑当数据永远达不到1024字节的情况。因此可以加一个额外的判断

```java
while (bytesRead < byteToRead) {
    int res = in.read(input, bytesRead, byteToRead - bytesRead);
    if(res == -1) break;//流结束
    bytesRead += res;
}
```

如果不想等待所需的全部字节都立即可用，可以使用 available() 方法来确定不择色的情况下有多少字节可以读取。它会返回可以读取的最少字节数。

```
in.available()
```

事实上可能还有更多的字节可以读取，available 只是返回最少可读取的字节数

skip() 方法可以设置跳过多少个字节不进行处理，直接将读取指针指向某个位置。

输入流的关闭和输出流相同



```java
public synchronized void mark(int readlimit) {}
public synchronized void reset() throws IOException{}
public boolean markSupported(){}
```

mark() 方法标记流的当前位置，在以后某个时刻，可以使用 reset() 方法把流重制到 mark 的位置重新进行读取。

一个流在任意时刻只能有一个标记，而且当设定的参数过于远，会抛出IOException

Java 中只有BufferedInputStream和ByteInputStream支持标记，其他的输入流只有串联到缓冲区的时候才支持。

调用 mark() 之前，需要调用 markSupport() 方法进行确认。



## 2.3 过滤器流

InputStream和OutputStream可以读取字节，但是字节的含义（什么编码）需要程序员自己判断。

过滤器流有两个版本：过滤器流以及阅读器和书写器。过滤器流仍然主要将原始数据作为字节处理。阅读器和书写器处理多种编码文本的特殊情况。

过滤器以链的形式组织

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/javasocket/FilterStream.png)

链中的每个环节都接收前一个过滤器或流的数据，并把数据传递给链中的下一个环节。在这个示例中，从本地网络接口接收到一个压缩的加密文本文件，在这里本地代码将这个文件表示为 TelnetInputStream。通过一个 BufferedInputStream 缓冲这个数据来加速整个过程。由一个 CipherInputStream 将数据解密。再由一个 GZIPInputStream 解压解密后的数据。一个 InputStreamReader 将解压后的数据转换为 Unicode 文本。最后由应用程序处理。

每个过滤器输出流都有与 java.io.OutputStream 相同的 write()、close()、和 flush() 方法。每个过滤器输入流都有与 java.io.InputStream 相同的 read()、close() 和 avaliable() 方法。

**将过滤器串联起来**

```java
FileInputStream fin = new FileInputStream("data.txt");
BufferedInputStream bin = new BufferedInputStream(fin);
```

通过这样的方式，可能会同时使用 fin 和 bin 的 read() 方法。大多数情况下，应该只使用链中最后一个过滤器进行读写操作

**缓冲流**

BufferedOutputStream 类将写入的数据存储在缓冲区中（一个名为 buf 的保护字节数组），直到缓冲区满或者刷新输出流，然后它将数据一次性全部写入底层输出流。

BufferedInputStream 类也有一个作为缓冲区的保护字节数组。当调用某个流的 read() 方法时，它首先尝试从缓冲区（buf）中获得数据，只有当缓冲区中没有数据时，流才从底层的源中读取数据。这时，它会从源中尽可能多的读取数据存入缓冲区，而不管是否马上需要所有这些数据。

构造函数

```java
public BufferedInputStream(InputStream in)
public BufferedInputStream(InputStream in, int size)
public BufferedOutputStream(OutputStream out)
public BufferedOutputStream(OutputStream out, int size)
```

第一个参数是底层流，可以从中读取到未缓冲的数据，或者向其写入缓冲的数据。第二个参数是缓冲数组的字节数，输入流默认2048，输出流默认512

**PrintStream**

System.out 本身就是一个 PrintStream，可以使用下面两个构造函数将其他输出流串联到打印流

```java
public PrintStream(OutputStream out)
public PrintStream(OutputStream out, boolean autoFlush)
```

默认情况下，打印流应当显式刷新输出。如果 autoFlush 参数为 true，那么每次写入 1 个字节数组或换行，或者调用 println() 方法时，都会刷新输出流。

PrintStream 会出现的问题

- println() 的输出与平台有关。不同平台在输出时结束出不同。
- PrintStream 假定使用所在平台的默认编码方式。
- PrintStream 吞掉了所有异常

**在网络编程中不建议使用**

**数据流**

DataInputStream 和 DataOutputStream 类提供了一些方法，可以用二进制格式读写Java 的基本数据类型和字符串。所用的二进制格式主要用于在两个不同的Java程序之间交换数据（通过网络连接、数据文件、管道或者其他中间介质）。

输出流输出什么数据，输入流就能读取什么数据。

DataOutputStream 使用 writeXxxx 方式写入特定 Java 数据类型，所有的数据都以 big-endian 格式写入。

writeChars() 方法只是对 String 参数迭代处理，将各个字符按照顺序写为一个 2 字节的 big-endian Unicode 字符。

writeBytes() 方法迭代处理 String ，但只写入每个字符的低字节。因此，如果字符串中包含有 Latin-1 字符集以外的字符，其中的信息会丢失。对于制定了 ASCII 编码的网络协议来说，这个方法获取有用，但不推荐。

上面两种方式都不会对输出流的字符串长度进行编码，因此，你无法区分原始字符和作为一个字符串一部分的字符。

wrtieUTF() 方法则包括了字符串的长度，它将字符串本身用 Unicode UTF-8 编码的一个变体进行编码。

## 2.4 阅读器和书写器

一些就协议制定了 ASCII 编码方式，新的比如 HTTP 协议允许本地编码方式，可以指定各种字符集。

Java 的内置字符集是 Unicode 和 UTF-16 编码。为了应对各种编码的场景，对于输入流和输出流层次体系，Java提供了一个基本上完整的镜像，用来处理字符而不是字节。

两个抽象的超类定义了读写字符的基本 API 。java.io.Reader 和 java.io.Writer

Reader 和 Writer 最重要的子类是 InputStreamReader 和 OutputStreamWriter。

InputStreamReader 和 OutputStreamWriter 分别从底层流读取和向底层流输入，输入流将读取到的原始字节根据指定的编码方式转换为 Unicode 字符，输出流相反。

java.io 包中还提供了几个原始的 Reader 和 Writer，他们可以读取字节而不需要一个底层流：

- FileReader
- FileWriter
- StringReader
- StringWriter
- CharArrayReader
- CharArrayWriter

**Writer**

Writer 是 OutputStream 的映射，是抽象类，有两个保护类型的构造函数。

```java
protected Writer()
protected Writer(Object lock)
abstract public void write(char cbuf[], int off, int len) throws IOException;
public void write(int c) throws IOException
public void write(char cbuf[]) throws IOException
public void write(String str) throws IOException
public void write(String str, int off, int len) throws IOException
abstract public void flush() throws IOException;
abstract public void close() throws IOException;
```

`write(char cbuf[], int off, int len)` 方法是基础方法，其他 write() 方法都是根据它实现的。子类至少要覆盖这个方法的 flush() 和 close()



OutputStreamWrtier 是Writer 的最重要的具体子类。OutputStreamWriter 会从 Java 程序接收字符。它根据指定的编码方式将这些字符转换为字节，并写入底层输出流。它的构造函数制定了要写入的输出流和使用的编码方式：

```java
public OutputStreamWriter(OutputStream out, String charsetName)
    throws UnsupportedEncodingException
```



**Reader**

Reader 是 InputStream 的映射，是抽象类，有两个保护类型的构造函数。

```java
protected Reader()
protected Reader(Object lock)
abstract public int read(char cbuf[], int off, int len) throws IOException;
public int read(java.nio.CharBuffer target) throws IOException
public int read() throws IOException
public int read(char cbuf[]) throws IOException
public boolean ready() throws IOException
public boolean markSupported()
public void mark(int readAheadLimit) throws IOException
public void reset() throws IOException
abstract public void close() throws IOException;
```

`read(char cbuf[], int off, int len)` 方法是基础方法，其他 read() 方法根据这个实现

read() 方法将一个 Unicode 字符作为一个 int 类型返回，值 0~65535 之间的一个值，或者在流结束时返回 -1。

Reader 类中有一个 ready() 方法，它与 InputStream 的 available() 用于相同。ready() 只会返回 boolean 指示是否可以无阻塞的读取。

 InputStreamReader 是 Reader 的最重要的具体子类。

```java
public InputStreamReader(InputStream in)
public InputStreamReader(InputStream in, String charsetName)
        throws UnsupportedEncodingException
```

如果没有指定编码方式，使用平台默认编码方式，如果指定了一个未知编码，会抛出 UnsupportedEncodingException 异常。

按照指定编码方式读取的例子

```java
public static String getMacCyrillicString(InputStream in) throws IOException {
    InputStreamReader r = new InputStreamReader(in,"MacCyrillic");
    StringBuilder sb = new StringBuilder();
    int c;
    while((c = r.read())!=-1) sb.append((char) c);
    return sb.toString();
}
```



**过滤器阅读器和书写器**

InputStreamReader 和 OutputStreamWriter 类相当于在输入流和输出流之上的装饰器，把面向字节的接口改为面向字符的接口。完成之后，就可以将其他面向字符的过滤器放在使用 java.io.FilterReader 和 java.io.FilterWriter 类的阅读器或书写器之上。与过滤器流一样，有很多子类可以完成特定工作：

- BufferedReader
- BufferedWriter
- LineNumberReader
- PushbackReader
- PrintWriter

BufferedReader 和 BufferedWriter 类是基于字符的，对应于面向字节的 BufferedInputStream 和 BufferedOutputStream 类。字符类的默认缓冲大小是8192字符。

同样的也能一次读取一个字符或者按块读取

```java
public BufferedReader(Reader in, int sz)
public BufferedReader(Reader in)
public BufferedWriter(Writer out)
public BufferedWriter(Writer out, int sz)
```

BufferedReader 还有 readLine() 方法，每次读取一行，返回一个字符串

```java
public String readLine() throws IOException
```

```java
Reader in = new FileReader("data.txt");
BufferedReader r = new BufferedReader(in);
String s = null;
while((s = r.readLine())!=null) System.out.println(s);
```

BufferedWrtier 由 newLine() 可以写入一行，然后插入一个与平台有关的行分隔符

**PrintWriter**



# 第三章 线程



# 第四章 Internet地址

IPv4：4字节，点分十进制

IPv6：16字节，分为8个块，每个块2字节，4个十六进制数字。

> 2001:4860:4860:0000:0000:0000:0000:8888

中间都是0可以省略写法，双冒号缩写

> 2001:4860:4860::8888

省略写法在每个地址中只能使用一次

## 4.1 InetAddress类

java.net.InetAddress 类是 Java 对IP地址的高层表示。

大多数网络类都用到这个类，包括Socket、ServerSocket、URL、DatagramSocket、DatagramPacket等

**创建新的InetAddress对象**

InetAddress类没有公共构造函数。InetAddress有一些静态工厂方法，可以连接到DNS服务器来解析主机名。最常用的是InetAddress.getByName()

```java
InetAddress address = InetAddress.getByName("www.baidu.com");
System.out.println(address);
```

上面的代码根据地址名称解析到IP地址，然后进行输出

这个方法不只是设置了InetAddress类中的一个私有String字段。实际上它会建立与本地DNS服务器的一个连接，来查找名字和数字地址（如果之前查找过，会缓存在本地，就不需要建立网络连接了）。如果DNS服务器找不到这个地址，这个方法会抛出一个UnknownHostException异常。

还可以根据IP地址进行反向查询

```java
InetAddress hostname = InetAddress.getByName(ip);//根据点分十进制的IP地址来获取主机名
System.out.println(hostname.getHostName());//没有找到就输出传入的ip地址
```

如果找不到域名，就会返回传入的ip地址参数。

有可能一个IP地址对应了很多域名，这个时候会随即返回。如果要得到所有的域名，需要调用 getAllByName() 方法

```java
InetAddress[] allByName = InetAddress.getAllByName(ip);//获得所有的对应的域名
for(InetAddress name : allByName){
    System.out.println(address);
}
```

使用 getLocalHost() 方法会为执行该方法的主机返回一个 InetAddress 对象

```java
InetAddress me = InetAddress.getLocalHost();
System.out.println(me);
```

这个方法尝试连接DNS来得到一个真正的主机名和IP地址；如果失败，会返回一个回环地址。



如果已经知道一个确切的IP地址，可以由这个地址创建一个InetAddress对象，而不必使用 InetAddress.getByAddress() 与DNS进行交互。这个方法可以为不存在或者无法解析的主机创建地址：

```java
public static InetAddress getByAddress(byte[] addr)
        throws UnknownHostException
public static InetAddress getByAddress(String host, byte[] addr)
        throws UnknownHostException
```

第一个方法工厂方法，用一个IP地址创建一个InetAddress对象。第二个方法使用一个IP地址和一个主机名创建InetAddress对象。

```java
//因为字节的范围是 -128~127，因此将大数要进行一个字节转换
byte[] constAddress = {107,23,(byte)216,(byte)196};
InetAddress lessWrong = InetAddress.getByAddress(constAddress);
InetAddress lessWrongWithName = InetAddress.getByAddress("lesswrong.com",constAddress);
//这两个方法不会保证这个主机一定存在
```



**缓存**

由于DNS查询开销可能很大（中间多级查询或者查找不可达主机），所以InetAddress会缓存查找的结果。一旦得到一个给定的主机的地址，就不会再次查找，即使创建一个新的InetAddress对象。

有可能刚开始解析一个主机时失败，但随后再次尝试时解析会成功。由于从远程DNS服务器发来的信息还在传输中，第一次尝试超时。然后这个地址到达本服务器，所以下一次请求时可用。出于这个原因，Java 对于不成功的DNS查询只缓存10秒。

这个时间可以用系统属性 `networkaddress.cache.ttl` 和 `networkaddress.cache.negative.ttl` 控制。第一个指定成功的DNS查找保留的时间，第二个指定了不成功查找保留的时间。

**按IP地址查找**

调用 getByName() 方法并提供一个IP地址作为参数的时候，会为所请求的IP地址创建一个InetAddress对象，而不是检查DNS。

只有当请求主机名时（显式调用getHostName()）才会真正的进行DNS查找

**安全性问题**

从主机名创建一个新的InetAddress对象被认为一个潜在的不安全操作，因为这需要一个DNS查找。在默认安全管理控制下的不可信applet只允许获得它的初始主机的IP地址，这可能是本地主机。

不允许不可信代码由任何其他主机名创建InetAddress对象。不论代码使用InetAddress.getByName()方法、InetAddress.getByAllName()方法、InetAddress.getLocalHost() 方法，还是其他方法啊，都是如此。不可信代码可以由字符串形式的IP地址构造InetAddress对象，但不会为这样的地址完成DNS查找。

不可信代码调用InetAddress.getLocalHost()方法，会返回回环地址

要测试一个主机能否解析，用特定的SecurityManager类的方法是checkConnect()：

```java
public void checkConnect(String gostname, int port)
```

当port参数为-1时，这个方法检查能够调用DNS解析指定的hostname。如果参数大于-1，这个方法检查是否允许在指定端口对指定主机建立连接。

hostname参数可以是主机名，也可以是IP地址

**获取方法**

```java
public String getHostName()
public String getCanonicalHostName()
public byte[] getAddress()
public String getHostAddress()
```

因为没有setter方法，InetAddress的字段不能修改，所以该类是线程安全的

getHostName() 方法返回一个String，其中包含主机的名字，以及这个InetAddress对象表示的IP地址。如果这台机器没有主机名或安全管理器阻止确定主机名，就会返回点分十进制IP

getHostName() 方法只有在不知道主机名的时候才会请求DNS查询主机名，getCanonicalHostName() 方法无论是否知道主机名都要查询，并进行更新。

getHostAddress() 方法返回点分十进制的IP地址

getAdderss() 方法将IP以网络字节的形式返回。返回的字节是无符号的整数 0~255，因此在值大于127时会变为负数，需要做一个转换



**地址类型**

127.0.0.1 回环地址，就是本地

224.0.0.0 到 239.255.255.255 是组播地址，可以同时发送多个订购（？什么意思）的主机。

```java
public boolean isAnyLocalAddress()
public boolean isLoopbackAddress()
public boolean isLinkLocalAddress()
public boolean isSiteLocaladdress()
public boolean isMulticastAddress()
public boolean isMCGlobal()
public boolean isMCOrgLocal()
public boolean isMCSiteLocal()
public boolean isMCLinkLocal()
public boolean isMCNodeLocal()
```

如果地址是通配地址，isAnyLocalAddress() 返回 true。通配地址可以匹配本地系统中的任何地址。通配地址是全0，IPv4 0.0.0.0

如果地址是回环地址，isLoopbackAddress() 返回 true。回环地址直接在IP层连接同一台计算机，不需要通过物理硬件（物理层，链路层）

如果地址是一个IPv6本地连接地址，isLinkLocalAddress() 返回 true。IPv6本地连接地址可以用于帮助IPv6网络实现自动配置。路由器不会把发送给本地连接地址的包头替换成外网地址。所有本地连接地址都以 FE80:0000:0000:0000:0000 开头。

如果地址是一个IPv6本地网站地址，isSiteLocaladdress() 返回 true。本地网站地址可以由路由器在网站或校园内转发，但不应该转发到网站以外。所有本地连接地址都以 FEC0:0000:0000:0000:0000 开头。

如果地址是一个组播地址，isMulticastAddress() 返回 true。组播地址将内容广播给所有预定的计算机。224.0.0.0 到 239.255.255.255 是组播地址

如果地址是全球组播地址，isMCGlobal() 返回 true。全球组播地址可能在世界范围内都有订购者。IPv6范围 FF0E 或者 FF1E 开头。

如果地址是一个组织范围组播地址，isMCOrgLocal() 返回 true。组织范围组播地址可能在公司或者组织所有网站中有订购者。IPv6地址以FF08 或者 FF18 开头。

如果地址是一个网站范围组播地址，isMCSiteLocal() 返回 true。发送到网站范围地址的包只会在本地网站内传输。FF05 或者 FF15开头。

如果地址是一个子网范围组播地址，isMCLinkLocal() 返回 true。在自己子网内传输。FF02 或者 FF12

如果地址是一个本地接口组播地址，isMCNodeLocal() 返回 true。发送到本地接口地址的包不能发送到最初的网络接口以外。



**测试可达性**

InetAddress 类有两个 isReachable() 方法，可以测试一个特定节点对当前主机是否可达。

```java
public boolean isReachable(int timeout) throws IOException
public boolean isReachable(NetworkInterface netif, int ttl,
                               int timeout) throws IOException
```

这个方法尝试使用 traceroute（ICMP echo 请求） 查看指定地址是否可达。如果在 timeout 时间内响应，就是 true。如果出现网络错误，抛出 IOException。第二个方法允许指定从哪个本地网络接口建立连接。



**Object方法**

对于两个 InetAddress 对象，只要IP地址相等，在 equals 时就是相等的。

hashCode() 方法也是根据 IP 地址计算的。



## 4.2 Inet4Address 和 Inet6Address

```java
public final class Inet4Address extends InetAddress
public final class Inet6Address extends InetAddress
```

大多数情况下可以使用getAddress()方法来通过获得的字节数组大小确定是不是IPv6



**NetworkInterface类**

NetworkInterface 类表示一个本地IP地址。可以是一个物理网卡，也可以是一个虚拟网卡。NetworkInterface 类提供一些方法可以枚举所有本地地址，并由它们创建InetAddress对象，然后这些对象可以用来创建socket

**工厂方法**

```java
public static NetworkInterface getByName(String name) throws SocketException
```

getByName() 方法返回一个 NetworkInterface 对象，表示指定名字的网络接口。

在 UNIX 系统上，名称形式为 eth0,eth1。本地回环地址可能用 lo 表示

在 Windows 系统上，名称形式类是 CE31 ，ELX100

```java
NetworkInterface ni = NetworkInterface.getByName("eth0");
if (ni == null){
	System.out.println("No such interface : eth0");
}
```

getByInetAddress() 返回一个NetworkInterface对象，表示与该IP地址绑定的网络接口。

```java
InetAddress local = InetAddress.getByName("127.0.0.1");
NetworkInterface ni = NetworkInterface.getByInetAddress(local);
if (ni==null){
    System.out.println("No local loopback address");
}
```

getNetworkInterface() 方法返回一个java.util.Enumeration，这会列出本地主机上的所有网络接口

```java
Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
while(networkInterfaces.hasMoreElements()){
    NetworkInterface nii = networkInterfaces.nextElement();
    System.out.println(nii);
}
```

**获取方法**

有了NetworkInterface对象，接可以查询其IP地址和名字。

```java
public Enumeration<InetAddress> getInetAddresses()
```

一个网络接口可以绑定多个IP地址。上面的方法对于这个接口绑定的IP地址都包含一个InetAddress。下面的代码就是返回 eth0 接口绑定的所有IP地址

```java
NetworkInterface eth0 = NetworkInterface.getByName("eth0");
Enumeration<InetAddress> inetAddresses = eth0.getInetAddresses();
while(inetAddresses.hasMoreElements()){
    System.out.println(inetAddresses.nextElement());
}
```

```java
public String getName()
```

getName() 返回特定NetworkInterface对象，如 eth0或者lo

getDisplayNmae() 返回一个更友好的名称。没多大区和上面



## 4.3 一些有用的程序

**SpamCheck**

监视垃圾邮件发送者的服务，并通知客户端试图与之建立连接的主机是否是一个已知的垃圾邮件发送者。这个服务需要在数千甚至百万主机反复查询，查看试图建立连接的IP地址是否是一个已知的垃圾邮件发送者。

这个问题需要很快的响应速度，理想情况还可以缓存，负载应当分不到多个服务器上。

要查看一个IP地址是否是一个已知的垃圾邮件发送者，可以逆置这个地址的字节，增加黑名单的域，然后查找这个地址。如果要向sbl.spamhaus.org查询207.87.34.17是否是垃圾邮件发送者，就要查找主机名207.87.34.17.sbl.spamhaus.org

如果DNS查询成功（返回127.0.0.2），那么就是垃圾邮件发送者。

```java
public class SpamCheck {

    public static final String BLACKHOLE = "sbl.spamhaus.org";//这个地址可能随时更改，垃圾邮件黑名单

    public static void main(String[] args) {
        for (String arg : args){//参数列表
            if(isSpammer(arg)){
                System.out.println(arg + " is a known spammer.");
            }else{
                System.out.println(arg + " appears legitimate.");
            }
        }
    }

    public static boolean isSpammer(String arg){
        try {
            InetAddress address = InetAddress.getByName(arg);//从IP地址参数获得InetAddress对象
            byte[] quad = address.getAddress();
            String query = BLACKHOLE;
            for (byte octet : quad) {
                int unsignedByte = octet < 0 ? octet + 256 : octet;//将字节向无符号字节转换
                query = unsignedByte + "." + query;
            }
            InetAddress.getByName(query);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
```

**处理Web服务器日志文件**





# 第五章 URL和URI

## 5.1 URI

统一资源标识符（Uniform Resource Identifier, URI）是采用一种特定语法标识一个资源的字符串。所标识的资源可能是服务器上的一个文件或者邮件地址或者新闻消息等等。

资源是URI标识的内容，URI则是一个字符串。

URI的语言由一个模式和一个模式特定部分组成，模式和模式特定部分用一个冒号分隔

> 模式:模式特定部分

模式特定部分的语法取决于所用的模式，当前模式包括：

- data：链接中直接包含的Base64编码数据 
- file：本地磁盘上的文件
- ftp：ftp服务器
- http：使用超文本传输协议的国际互联网服务器
- mailto：电子邮件地址
- magnet：可以通过对灯网络下载资源
- telnet：与基于Telnet的服务的连接
- urn：统一资源名（Uniform Resource Name, URN）

java中还有一些非标准的定制模式（rmi，jar，jndi，jdbc）

> http://www.baidu.com/rr/123.txt
>
> http://www.baidu.com/rr/?search=123

上面的形式是直接访问资源，下面的形式通过?带上参数，进行一些查询传参操作



模式部分由小写字母、数字和加号、点及连号符组成。典型URI的其他三部分（授权机构、路径和查询）分别由ASCII字母数字符号组成（A-Z、a-z、0-9）.此外还可以使用`-、_、.、!、~` 。定界符（/、&、=）有预定语义。

所有其它字符，应当用%转义，其后是该字符的按UTF-8编码的十六进制码



**URLs**

URL是一个URI，除了标识一个资源，还会为资源提供一个特定的网络位置，客户端可以用它来获得这个资源。

URI只能告诉资源是什么，但是无法告诉他在哪里

java.net.URI：只标识资源

java.net.URL：既能标识资源，又能获得资源



URL的语法为

> protocol://userInfo@host:port/path?query#fragment

这里的协议（protocol）是对URI模式（scheme）的另一种叫法。在URL中，协议部分可以是file、ftp、http、https、magnet、telnet等

URL的host部分是提供所需资源的服务器的名字，可以是主机名或者ip地址

用户信息（userInfo）部分是服务器的登录信息（可选）

端口号（port）可选，如果服务在其默认端口下运行就不需要填写

用户信息、主机和端口号组合构成权威机构（authority）

路径（path）指向指定服务器上的一个特定目录。

查询（query）字符串向服务器提供了附加参数，一般只在http URL 中使用

片段（fragment）指向远程资源的某个特定部分。例如 markdown文件的某个小节



**相对URL**

假设浏览器正在浏览

> http://www/baidu.com/javafaq/javatutorial.html

这个时候单机该页面的一个超链接

> <a\> href="javafaq.html"</a\>

浏览器会从当前正在浏览的页面地址截掉html，拼接上超链接的地址

> http://www/baidu.com/javafaq/javafaq.html

最后得到上面的地址文档



如果相对链接以 `/` 开头，那么它相对于文档的根目录，而不是相对于当前文件。

>  <a\> href="/project/ipv6"</a\>

最终得到（这个在web项目中需要看项目根目录是什么）

> http://www/baidu.com/project/ipv6



## 5.2 URL类

URL 类是一个 final 类，不通过继承方式来配置不同的实例，而是通过策略模式进行扩展

```java
public final class URL implements java.io.Serializable
```

**创建新的URL**

URL类的构造函数

```java
public URL(String spec) throws MalformedURLException
public URL(String protocol, String host, int port, String file)
        throws MalformedURLException
public URL(String protocol, String host, String file)
            throws MalformedURLException
public URL(String protocol, String host, int port, String file,
               URLStreamHandler handler) throws MalformedURLException
public URL(URL context, String spec) throws MalformedURLException
public URL(URL context, String spec, URLStreamHandler handler)
        throws MalformedURLException
```

如果URL语法不正确会抛出异常

**从字符串构造URL**

```java
public URL(String spec) throws MalformedURLException
```

从一个绝对URL创建URL实例。如果能够支持这个协议，那么能构造成功，如果不支持该协议，就会抛出异常

```java
public void testProtocol() throws MalformedURLException {
    //超文本传输协议
    testProtocol("http://www.adc.org");
    //安全http
    testProtocol("https://www.amazon.com/exec/obidos/order2/");
    //文件传输协议
    testProtocol("ftp://ibiblio.org/");
    //简单邮件协议
    testProtocol("mailto:bear.gen.li@outlook.com");
    //telnet
    testProtocol("telnet://dibner.poly.edu/");
    //本地文件访问
    testProtocol("file://etc/passwd");
    //gopher
    testProtocol("gopher://gopher.anc.org.za/");
    //轻量组目录访问协议
    testProtocol("ldap://ldap.itd.umich.edu/");
    //JAR
    testProtocol("jar:http://cafeaulait.org");
    //NFS，网络文件系统
    testProtocol("nfs://utopia.poly.edu/usr/tmp/");
    //jdbc
    testProtocol("jdbc:mysql://luna.org:3306/NEWS");
    //rmi
    testProtocol("rmi://www.baidu.com/test");
    //HotJava定制协议
    testProtocol("doc:/UsersGuide/release.html");
    testProtocol("netdoc:/UsersGuide/release.html");
    testProtocol("systemresource://www.adc.org/");
    testProtocol("verbatim://www.adc.org");
}

public void testProtocol(String url){
    try {
        URL u = new URL(url);
        System.out.println(u.getProtocol() + " is supported");
    } catch (MalformedURLException e) {
        String protocol = url.substring(0,url.indexOf(':'));
        System.out.println(protocol + " is not supported");
    }
}
```

**由组成部分构造URL**

还可以通过指定协议、主机名和文件来构造一个URL

```java
public URL(String protocol, String host, int port, String file)
        throws MalformedURLException
public URL(String protocol, String host, String file)
            throws MalformedURLException
```

如果没有设定端口号，默认设置为-1，在查找的时候会使用协议默认端口号

file 名称需要以 `/` 开头

下面的代码就根据协议、域名、文件构造了一个url

```java
public void createURLByFile(){
    try {
        URL url = new URL("http","www.baidu.com","/search?123");
        System.out.println(url);//http://www.baidu.com/search?123
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }
}
```

**构造相对URL**

```java
public URL(URL context, String spec) throws MalformedURLException
public URL(URL context, String spec, URLStreamHandler handler)
        throws MalformedURLException
```

根据一个URL构造另一个URL，只能去除最后的文件，然后拼接新的文件

```java
public void createURLByURL(){
    try {
        URL url = new URL("http://www.baidu.com/java/index.html");
        URL url2 = new URL(url,"index1.html");
        System.out.println(url2);
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }
}
```

**从URL获取数据**

URL类中有以下方法可以从中获取数据

```java
public final InputStream openStream() throws java.io.IOException
public URLConnection openConnection() throws java.io.IOException
public URLConnection openConnection(Proxy proxy)throws java.io.IOException
public final Object getContent() throws java.io.IOException
public final Object getContent(Class[] classes)throws java.io.IOException
```

openStream() 返回 InputStream 可以从这个流读取数据；如果需要更多个过程控制，需要使用 openConnection()

```java
public final InputStream openStream() throws java.io.IOException
```

openStream() 方法连接到URL的资源，在客户端和服务器之间完成必要的握手，返回一个InputStream。如果获取的HTML，流中就是HTML；如果是ASCII文本，流中就是ASCII。他不会包含任何http首部信息等任何其他信息

```java
InputStream in = null;
try {
    URL u = new URL("http://www.baidu.com?search=132");
    in = u.openStream();
    int c;
    while((c=in.read())!=-1) System.out.write(c);
} catch (MalformedURLException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
}finally {
    try {
        if(in!=null) in.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```



```java
public URLConnection openConnection() throws java.io.IOException
public URLConnection openConnection(Proxy proxy)throws 
```

openConnection() 方法为指定的URL打开一个socket，并返回一个URLConnection对象。该对象表示一个网络资源打开的连接。

```java
//从打开的链接获得输入流
try {
    URL u1 = new URL("http://www.baidu.com");
    try {
        URLConnection uc = u1.openConnection();
        InputStream in1 = uc.getInputStream();
        int c;
    while((c=in1.read())!=-1) System.out.write(c);
    } catch (IOException e) {
        e.printStackTrace();
    }
} catch (MalformedURLException e) {
    e.printStackTrace();
}
```

如果希望与服务器直接通信，应当使用这个方法。通过该对象，可以访问服务器发送的所有数据，包括原始文本、以及指定的所有元数据。

```java
public final Object getContent() throws java.io.IOException
public final Object getContent(Class[] classes)throws java.io.IOException
```

getContent() 方法获得URL引用的数据，尝试建立某种类型的对象。如果URL指定的是文本，返回InputStream对象，如果是图像，返回一个java.awt.ImageProducer

```java
//获得随机对象
try {
    URL u2 = new URL("http://www.baidu.com");
    Object content = u2.getContent();
    //content类型强制转换为适当的类型
} catch (MalformedURLException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
}
```

getContent() 方法，在从服务器获取的数据首部中查找Content-Type字段，如果服务器没有使用MIME首部，或者发送了一个不熟悉的Content-Type，getContent() 会返回某种InputStream。如果无法获取该对象，抛出IOException

重载方法可以选择希望该方法作为什么对象返回。



**分解URL**

URL由以下5个部分组成：

- 模式，协议
- 授权机构
- 路径
- 片段标识符
- 查询字符串

> http://www.baidu.com/hh/ee/index.html?search=8329#toc

模式是 http，授权机构是www.baidu.com，路径是 /hh/ee/index，片段标识符是 toc，查询字符串是 search=8329

授权机构可以进一步划分为用户信息、主机、端口号

> http://admin@www.baidu.com:8080

```java
public String getProtocol()//返回协议
public String getHost()//返回主机名
public int getPort()//返回端口号，如果返回的-1就是默认端口号
public int getDefaultPort()//返回默认端口号，如果该协议没有默认端口号，返回-1
public String getFile()//主机名之后的/到分段标识符#之前
public String getPath()//和上面的区别是不包含查询字符串
public String getRef()//返回片段标识部分，不包含开头的#
public String getQuery()//返回查询字符串
public String getUserInfo()//返回用户信息，有时候还有口令
public String getAuthority()//返回授权机构，会包括用户信息
```

**相等性比较**

URL没有实现Comparable接口

仅当两个URL都指向相同的主机、端口和路径上相同的资源，而且有相同的片段标识符和查询字符串才认为是相等的。

equals() 方法会尝试调用DNS解析主机，来判断是否相同。即判断 `http://www.baidu.com` 和 `http://baidu.com/` 是否相同。因此equals() 可能会发生IO阻塞，所以应该避免存储在依赖 equals() 的结构中

> http://www.baidu.com 和 http://www.baidu.com/index.html 不同
>
> http://www.baidu.com 和 http://www.baidu.com:80 不同

在 equals() 方法中还会使用一个 sameFile(URL other) 方法，判断是不是同一个文件，不会包括分片标识符

> http://www.baidu.com/index.html#123
>
> http://www.baidu.com/index.html#456 是同一个URL

**比较**

URL有3个方法可以将一个实例转换为另一种形式

```java
public String toString()//转换为绝对URL路径，调用的是下面的这个方法
public String toExternalForm()//转化为一个字符串，在HTML或者WEB浏览器对话框中使用。
public URI toURI() throws URISyntaxException//转换为URI类
```



## 5.3 URI类

URI类是对URL的抽象，不仅包括统一资源定位符（URL），还包括统一资源名（URN）。

java.net.URI 和 java.net.URL 的区别：

- URI类完全由关于资源的标识和URI的解析。它没有提供方法来获取URI所标识资源的表示
- 相比URL类，URI类与相关的规范更一致
- URI对象可以表示相对URL。URL类存储在URI之前会将其绝对化

URL对象是对应网络获取的应用层协议，而URI对象纯粹用于解析和处理字符串。URI类没有网络获取功能。

**构造一个URI**

构造函数

```java
public URI(String str) throws URISyntaxException
public URI(String scheme, String ssp, String fragment)
        throws URISyntaxException
public URI(String scheme, String host, String path, String fragment)
        throws URISyntaxException
public URI(String scheme,
               String authority,
               String path, String query, String fragment)
        throws URISyntaxException
public URI(String scheme,
               String userInfo, String host, int port,
               String path, String query, String fragment)
        throws URISyntaxException
```

第一个构造函数可以从一个合法的URI字符串构造一个URI对象

```java
URI u = new URI("http://www.baidu.com");
```

第二个构造函数需要模式特定部分，用于非层次URI

没有指定模式就会创建一个相对URI。第二部分URI类会用百分号进行转义，这部分不会有语法错误

```java
URI u = new URI("http","//www.baidu.com",null);
URI u1 = new URI(null,"/www.baidu.com","today");
```

第三个构造函数用于层次URI。如果无法根据提供的各个部分构成一个有效的URI层次。

如果指定了模式，且path不是以`/`开头，那么会抛出URISyntaxException异常

```java
URI u4 = new URI("http","www.baidu.com","/java.index.html","today");
```

第四个构造函数与前一个基本相同，只是添加了查询字符串的部分。

第五个构造函数是前两个构造函数调用的主层次URI，这个方法将授权机构分为用户信息、主机、端口

```java
URI u5 = new URI("ftp","anonymous:elharo@ibiblio.org","ftp.oreilly.com",21,"/pub/stylesheet",null,null);
```

如果能够确定你的URI是有效的，可以使用URI类的静态工厂方法创建URI对象，create() 方法。

如果URI不正确，会抛出一个IllegalArgumentException



**URI的各部分**

如果有模式部分，就是绝对URI

如果没有模式部分，就是相对URI

下面的方法可以判断一个URI是不是绝对的

```java
public boolean isAbsolute()
```

判断是不是层次URI

```java
public boolean isOpaque()
```

对于层次URI，那么对于层次URI的所有不同部分都有响应的获取方法

```java
public String getAuthority();
public String getFragment();
public String getHost();
public String getPath();
public String getPort();
public String getQuery();
public String getUserInfo();
```

百分号转义会改为它们实际表示的字符，如果是%3C会改为<

如果需要原始的部分，有getRawFoo()方法

```java
public String getRawAuthority();
public String getRawFragment();
public String getRawPath();
public String getRawQuery();
public String getRawSchemeSpecificPart();
```



**解析相对URI**

URI类提供了3个方法可以在相对和绝对URI之间来回换

```java
public URI resolve(URI uri)
public URI resolve(String uri)
public URI relativize(URI uri)
```

resolve() 方法将uri参数与调用它的URI进行比较，并用它构造一个新的URI对象

```java
URI absolute = new URI("http://www.example.com/");
URI relative = new URI("images/logo.png");
URI resolved = absolute.resolve(relative);
System.out.println(resolved);//http://www.example.com/images/logo.png
```

将一个绝对URI和相对URI进行了拼接

如果调用resolve() 方法的URI类不包含绝对URI，那么会尽可能的解析出一个URI

relativize() 方法可以将绝对URI变为相对URI

```java
URI relativize = absolute.relativize(resolved);
System.out.println(relativize);//images/logo.png
```



**相等性和比较**

相等的URI必须是层次的或者不透明的。

比较模式和授权机构不考虑大小写。

转义字符在比较前不解码

> http://www/A 和 http://www/%41 是不相等的

URI 实现了Comparable，所以可以进行排序：

1. 先比较模式
2. 模式相同，认为层次URI小于相同模式的不透明URI
   1. 都是不透明URI，以模式特定部分排序
   2. 上面的都相等，根据据片段比较URI
3. 如果都是层次URI，根据授权机构排序，授权机构根据用户信息、主机、端口排序
   1. 授权机构相同，对路径排序
   2. 路径相等，对查询字符串排序
   3. 查询字符串相等，对片段排序

**字符串表示**



## 5.4 x-www-form-urlencoded

URL包含的字符必须是来自ASCII的一个固定子集：

- 大小写英文字母
- 数字
- 标点符号：`-_.!~*',`
- 字符：`:/&?@#;$+=%` 都有特殊用途。这些字符出现在路径或者查询字符串中都会被编码

除了ASCII数字、字母和前面指定的标点符号外，所有其他字符都要转换为字节，每个字节要写为百分号后面加两个十六进制数字。空格会被编码为%20



**URLEncoder**

对字符串完成URL编码，需要将这个字符串和字符集传入URLEncoder.encode() 方法

```java
try {
	String encode = URLEncoder.encode("This我的世界Hello123@#$%^*", "UTF-8");
    System.out.println(encode);//This%E6%88%91%E7%9A%84%E4%B8%96%E7%95%8CHello123%40%23%24%25%5E*
} catch (UnsupportedEncodingException e) {
	e.printStackTrace();
}
```

这个方法是一个比较积极的方法，会把大多数符号都进行转义

**URLDecoder**

URLDecoder.decode()方法会对 x-www-form-url-encoded 格式编码的字符串进行解码。将加号转变为空格，百分号转义字符变为对应的字符

```java
public void decoder(String str){
    try {
        String decode = URLDecoder.decode(str, "UTF-8");
        System.out.println(decode);//This我的世界Hello123@#$%^*
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    }
}
```

对上面进行编码过的字符串解码就会得到原值

如果字符串中有一个百分号，但是后面没有十六进制数字或者解析不到对应的字符，那么就会抛出IllegalArgumentException



## 5.5 代理

客户端访问服务器，客户端先将请求发送到代理服务器，然后代理服务器进行过滤等操作，将请求发送给服务器，最后又通过代理服务器返回数据。



**系统属性**

对于基本操作，所要做的就是设置一些系统属性，指示本地代理服务器的地址。如果纯粹使用HTTP代理，则将http.proxyHost设置为代理服务器的域名或IP地址，将http.proxyPort设置为代理服务器的端口（默认80）

如果代理需要一个用户名和口令，则需要安装一个Authenticator

如果希望一台主机不被代理，而是直接连接，则要把http.nonProxyHosts属性设置为其主机名和IP地址。如果多个主机都不需要代理，可以用竖线分割这些主机名。

```java
public static String setProperty(String key, String value)
```

> System.setProperty("http.proxyPort","80");
>
> System.setProperty("http.nonProxyHosts","java.com|xml.com");
>
> System.setProperty("http.proxyHost","*.com");

`*` 通配符

如果使用FTP代理服务器，可以使用ftp.proxyHost等属性来进行设置。

Java不支持任何其他应用层协议，但是如果对所有TCP连接都是用传输层SOCKS代理，可以用socksProxyHost和socksProxyPort系统属性来确定

**Proxy类**

Proxy 类允许从 Java 程序中对代理服务器进行更细粒度的控制，它允许你为不同的远程主机选择不同的代理服务器。

这个不是 java.lang.reflect 包中的代理，是 java.net.Proxy

三种代理：

- Proxy.Type.DIRECT
- Proxy.Type.HTTP
- Proxy.Type.SOCKS

关于代理的其他重要信息包括端口地址，用SocketAddress对象表示。

```java
SocketAddress address = new InetSocketAddress("proxy.example.com",80);
Proxy proxy = new Proxy(Proxy.Type.HTTP,address);
```

上面的代码，表示proxy.example.com端口80上的一个HTTP代理服务器

打开一个URLConnection的时候可以用Proxy

**ProxySelector类**

每个虚拟机中都有一个java.net.ProxySelector类，用来确定不同连接的代理服务器。

默认的ProxySelector只检查各种系统属性和URL协议，来决定如何连接到不同的主机。

可以创建自己的ProxySelctor子类来替代默认的选择器，用它根据协议、主机、路径、日期时间和其他标准来选择不同的代理。

这个类的关键方式是下面的

```java
public abstract List<Proxy> select(URI uri);
```

Java方法为这个方法传入一个URI对象，这表示需要连接的主机。

对于用URL类生成的连接，这个对象通常形式是 http://www.example.com/ 或者 ftp://ftp.example.com/pub/。对于Socket类生成的纯TCP连接，URI形式为socket://www.example.com:80。然后ProxySelector为这种类型的对象选择正确的代理，并返回到一个List<Proxy\>中

第二个抽象方法

```java
public abstract void connectFailed(URI uri, SocketAddress sa, IOException ioe);
```

回调方法，用来警告程序代理服务器实际上没有建立连接。

下面的例子展示了 一个ProxySelector，尝试使用位于proxy.example.com的代理服务器完成所有HTTP连接，除非这个代理服务器之前未能成功解析与一个特定URL的连接，如果是这样它会直连。

```java
public class LocalProxySelector extends ProxySelector {
    
    private List<URI> failed = new ArrayList<>();
    
    @Override
    public List<Proxy> select(URI uri) {
        List<Proxy> result = new ArrayList<>();
        if (failed.contains(uri) || !"http".equalsIgnoreCase(uri.getScheme())){
            //之前失败的，或者模式不是http
            result.add(Proxy.NO_PROXY);
        }else{
            //创建proxy
            SocketAddress proxyAddress = new InetSocketAddress("proxy.example.com",8000);
            Proxy proxy = new Proxy(Proxy.Type.HTTP,proxyAddress);
            result.add(proxy);
        }
        
        return result;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        failed.add(uri);
    }
}
```

使用自定义selector

```java
ProxySelector selector = new LocalProxySelector();
ProxySelector.setDefault(selector);
```

之后虚拟机打开的所有连接都会向这个ProxySelector询问将要使用的代理。

## 5.6 通过GET与服务器端程序通信

```java
URL u = new URL("https://www.baidu.com/");//确定URL
try {
    InputStream inputStream = u.openStream();//获得输入流
    BufferedInputStream bin = new BufferedInputStream(inputStream);
    int c;
    while((c=bin.read())!=-1) System.out.print((char)c);
} catch (IOException e) {
    e.printStackTrace();
}
```

## 5.7 访问口令保护的网站

对于基于cookie的非标准认证网站，比较难实现：不同网站的cookie认证有很大区别，实现cookie认证往往要实现一个完整的web浏览器，而且需要提供充分的HTML表单和cookie支持。

这一节讨论标准认证，第七章讨论非标准认证

**Authenticator类**

java.net 包下的类，可以用它为使用HTTP认证自我保护的网站提供用户名和口令。

该类是一个抽象类，为了让URL类使用这个子类，要把它传递给 Authenticator.setDefault() 静态方法，将它安装为默认认证程序

```java
public synchronized static void setDefault(Authenticator a)
```

设置的值是一个具体得实现子类

```java
Authenticator.setDefault(new DialogAuthenticator());
```



只需要安装一次，此后当URL类需要用户名和口令时，他就会使用Authenticator.requestPasswordAuthentication()静态方法访问这个DialogAuthenticator

```java
public static PasswordAuthentication requestPasswordAuthentication(
                                        InetAddress addr,
                                        int port,
                                        String protocol,
                                        String prompt,
                                        String scheme)
```

addr参数是需要认证的主机，port参数是该主机上的端口号，protocol参数是访问网站的应用层协议。HTTP服务器提供prompt。这一般是需要认证的域的域名。scheme是所使用的认证模式（这里的模式和前面URI的模式不是同义词，这是HTTP认证模式，通常是basic）

不可信的应用不允许向用户询问用户名和口令。

只有可信应用在拥有 requestPasswordAuthentication NetPermission 权限时才允许。否则，会抛出 SecurityException 异常

Authenticator 子类必须覆盖 getPasswordAuthentication() 方法。在这个方法中，要从用户或其他来源收集用户名和口令，把它作为 java.net.PasswordAuthentication 类的一个实例返回：

```java
protected PasswordAuthentication getPasswordAuthentication() {
        return null;
}
```

如果不希望对这个请求完成认证，就返回null，java告诉服务器不知道如何认证这个连接；如果提交了不正确的用户名和密码，java会再次调用该方法进行认证。一般5次之后 openStream() 抛出 ProtocolException 异常

用户名和口令缓存在同一个虚拟机会话中，一旦为一个域设置了正确的口令，就不应该再次询问，除非讲包含这个口令的char数组清零，显式的删除口令

有众多从超类中继承的 final 方法可以获得最后一次调用 requestPasswordAuthentication() 方法给出的信息。getRequestorType() 方法返回两个命名常量 Authenticator.RequestorType.PROXY 和 Authenticator.RequestorType.SERVER，表示请求认证的是服务器还是代理服务器。



**PasswordAuthentication 类**

该类是一个final类，只有用户名和口令属性。

```java
public final class PasswordAuthentication {

    private String userName;
    private char[] password;

    public PasswordAuthentication(String userName, char[] password) {
        this.userName = userName;
        this.password = password.clone();
    }

    public String getUserName() {
        return userName;
    }

    public char[] getPassword() {
        return password;
    }
}
```

当不需要口令是，可以从char[] 中清除。



**JPasswordField类**

这是 Swing 中的组件

# 第六章 HTTP

HTTP超文本传输协议，实际上是一个数据格式。它可以用来传输图片、word文档、.exe.文件，或者其他可以用字节表示的东西。

## 6.1 HTTP协议

HTTP指定客户端与服务器如何建立连接、客户端如何从服务器请求数据，服务器如何响应请求，以及最后如何关闭连接。

HTTP使用TCP/IP来传输数据，对于从客户端到服务器的每一个请求，都有4个步骤：

1. 默认情况下，客户端在端口80打开与服务器的一个TCP连接，URL中还可以指定其他端口
2. 客户端向服务器发送消息，请求指定路径上的资源。这个资源包括一个首部，可选的（取决于请求性质）还有一个空行，后面是这个请求的数据
3. 服务器向客户端发送响应，响应以响应码开头，后面是包含元数据的首部、一个空行以及所请求的文档或错误信息。
4. 服务器关闭连接

在HTTP1.1及以后，可以通过一个TCP连接连续发送多次请求和响应。

格式

```http
GET /index.html HTTP/1.1
User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:20.0)
Gecko/20100101 Firefox/20.0
Host: en.wikipedia.org
Connection: keep-alive
Accept-Language: en-US,en;q=0.5
Accept-Encoding: gzip, deflate
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8
//空行
```

一个请求行、一个包含元数据的HTTP首部、一个空行

User-Agent：表明使用的浏览器和机器

Host：指定服务器名称

Accept：告诉服务器，客户端可以处理什么类型的数据，会指出MIME媒体类型

MIME分为两级：类型和子类型。类型非常概括地展示包含的是什么数据：图片、文本、视频。子类型标识数据的特定类型：GIF图像、JPEG图像、TIFF图像。例如，HTML内容类型 text/html，那么类型是text，子类型是html

- text/* 表示人可读的文字
- iamge/* 图片
- model/* 表示3D模型
- audio/* 表示声音
- video/* 表示移动的图片，可能包括声音
- application/* 表示二进制数据
- message/* 表示协议特定的信封。如email和http响应
- multipart/* 表示多个文档和资源的容器

[查看最新的MIME类型文件列表](http://www.inan.org/assignments/mdedia-types/)

最后，请求以一个空行结束，包括两个回车/换行对，/r/n/r/n

一旦服务器看到这个空行，他就开始通过同一个连接向客户端发送他的响应。这个相应以一个状态行开始，后面是一个首部，然后是一个空行，最后是请求的资源

```http
HTTP/1.1 200 OK
Date: Sun, 21 Apr 2013 15:12:46 GMT
Server: Apache
Connection: close
Content-Type: text/html; charset-ISO-8859-1
Content-length: 115
//空行
data data ...
```

第一行是服务器使用的HTTP协议，后面是响应码

后面再指示出日志、服务器软件、承诺服务器结束发送时会关闭连接、MIME数据类型、报文长度



响应码

100-199：提供信息的响应

200-299：成功

300-399：表示重定向

400-499：客户端错误

500-599：服务器错误



**Keep-Alive**

在 HTTP 1.0 中为每个请求打开一个新连接。大多数web会话打开和关闭连接的时间远远大于实际数据传输时间。

HTTP 1.1 和之后，服务器不必在发送响应后就关闭连接。可以保持连接打开，在同一个socket上等待来自客户端的新请求。可以在一个TCP连接上连续发送多个请求和响应。

客户在请求HTTP首部中包括一个 Conncetion 字段，指定为 Keep-Alive，指示它希望重用 socket

> Connection : Keep-Alive

URL类默认支持HTTP Keep-Alive，可以利用系统属性来设置：

- http.keepAlive
- http.maxConnections 打开的最大连接数
- http.keepAlive.remainingData 使 Java 在丢弃连接之后进行清理
- sun.net.http.errorstream.enableBuffering 尝试缓冲400和500级想用的相对小的错误流，从而能释放连接
- sun.net.http.errorstream.bufferSize 为缓冲错误流使用的字节数，默认4096字节
- sun.net.http.errorstream.timeout 为错误流超时前毫秒数，默认300毫秒



## 6.2 HTTP方法

主要有4种方法：

- GET，可以获取一个资源的表示，没有副作用。GET输出通常会缓存，可以通过正确的首部来控制
- POST，将资源的一个表示上传到已知URL服务器，主要用于不能重复的不安全操作
- PUT，将资源表示上传到已知URL的如武器。这个方法有副作用，但是它有幂等性。幂等性指可以重复这个方法而不用担心它是否失败。如果连续两次把同一个文档上传的同一个服务器的同一个位置，与只放一次相比，它的状态是相同的。
- DELETE，从指定URL删除一个资源，它也有幂等性。不确定是否删除成功，可以删除两次。

因为GET请求在URL种包括了所有必要的信息，所以可以对GET请求加书签（浏览器书签），或者进行链接和搜索等。POST、PUT、DELETE则不行。

特殊的HTTP方法：

- HEAD：相当于GET，只返回首部
- OPTIONS：允许客户端询问服务器可以如何处理一个指定的资源
- TRACE：会回显客户端请求来进行调试

## 6.3 请求主体

一般HTTP报文构成：

- 请求行
- 首部
- 空行
- 主体，数据

主体可以包含任意的字节。一般需要在HTTP首部要包括两个字段来指定主体的性质：

- 一个Content-length字段，指定主体中有多少字节
- 一个Content-type字段，指定类的MIME媒体类型

## 6.4 Cookie

Cookie：用来在连接之间储存客户端状态的文本串

cookie 在请求和响应的HTTP首部，从服务器传递到客户端，再从客户端传回服务器。服务器使用cookie来指示会话ID、购物车内容、登录凭据、用户首选项等。

要在浏览器中设置一个cookie，服务器会在HTTP首部包含一个Set-Cookie首部行。

> HTTP/1.1 200 OK
>
> Content-Type: text/html
>
> Set-Cookie: cart=AXTVDSAF

如果浏览器向同一个服务器做出请求，它会在请求首部中添加Cookie

> GET /index.html HTTP/1.1
>
> Host: www.example.org
>
> Cookie: cart=AXTVDSAF
>
> Accept: text/html

只要服务器不重用Cooike，这会使它在多个HTTP连接上跟踪哥哥用户的会话。

服务器可以包含多个 Set-Cookie 字段

除了简单的设置k-v对，还可以设置作用域：过期日期、路径、域、端口、版本和安全选项

作用域受路径限制，默认作用域是最初的URL和其子目录

> 在 http://www.baidu.com/XOM/ 设置了cookie
>
> 那么可以应用到  http://www.baidu.com/XOM/abc/ 但是不能应用到  http://www.baidu.com/other/

可以使用 Path 属性改变默认作用域。

> Set-Cookie: user=elharo; Path=/restricted

这个是将 user=elharo 的cookie，设置作用域到 /restricted 目录中可用

可以使用同样的方法设置

> 域 Domain=.example.com 在该子域可用
>
> 过期时间 expire=Wdy, DD-Mon-YYY HH:MM:SS GMT 过期后浏览器会删除这个cookie
>
> 过期时间 Max-Age=600 设置过期时间，单位秒
>
> 安全属性 secure 使用该属性就会用HTTPS替代HTTP

为了针对cookie窃取攻击（XSRF）提高安全性，cookie可以设置httpOnly属性，这会告诉浏览器只通过HTTP或HTTPS返回cookie，不能由js返回



**CookieManager**

抽象类 java.net.CookieHandler 定义存储和获取cookie的一个API。

CookieManager 是 CookieHandler 的一个具体子类

```java
CookieManager manager = new CookieManager();
CookieHandler.setDefault(manager);
```

通过这样设置，对于用URL类连接的服务器，Java会储存这些服务器所发送的cookie，再在后序的请求中向这些服务器发回所存储的cookie，再在后续的请求中发送这些cookie。

接收cookie的策略可以使用CookiePolicy配置：

- CookiePolicy.ACCEPT_ALL 接受所有cookie
- CookiePolicy.ACCEPT_NONE 不接受任何cookie
- CookiePolicy.ACCEPT_ORIGINAL_SERVER 只接受第一方 cookie

```java
CookieManager manager = new CookieManager();
manager.setCookiePolicy(CookiePolicy.ACCEPT_ORIGINAL_SERVER);//设置策略
CookieHandler.setDefault(manager);
```

还可以自行实现CookiePolicy接口自行实现接收cookie的策略

下面的例子是从授权机构和域名中过滤掉.gov的cookie

```java
public class NoGovernmentCookies implements CookiePolicy {
    @Override
    public boolean shouldAccept(URI uri, HttpCookie cookie) {
        if(uri.getAuthority().toLowerCase().endsWith(".gov")
            ||cookie.getDomain().toLowerCase().endsWith(".gov") ){
            return false;
        }
        return true;
    }
}
```

**CookieStore**

从本地获取cookie

```java
CookieManager manager = new CookieManager();
CookieStore store = manager.getCookieStore();
```

CookieStore 类允许增加、删除和列出cookie，使用户能够控制在正常HTTP请求和响应之外发送的cookie

```java
public void add(URI uri, HttpCookie cookie);
public List<HttpCookie> get(URI uri);
public List<HttpCookie> getCookies();
public List<URI> getURIs();
public boolean remove(URI uri, HttpCookie cookie);
public boolean removeAll();
```

这个库的每个cookie都封装在一个HttpCookie对象中，它提供了一些方法来检查cookie属性

HTTPCookie类

# 第七章 URLConnection

URLCoonection 抽象类，表示指向 URL 指定资源的活动连接。

和 URL 类相比，它对服务器（特别是HTTP服务器）的交互提供了更多的控制。URLConnection可以检查服务器发送的首部，并相应的做出响应。它可以设置客户端请求中使用的首部字段。它可以用POST、PUT和其他HTTP秦桧方法向服务器发送数据。

URLConnection类是java协议处理器机制的一部分，这个机制还包括URLStreamHandler类。协议处理器机制：他们将处理协议的细节，提供相应的用户接口，并完成完整web浏览器所完成的其他操作。如果要实现一个特定的协议处理器，需要继承实现 URLConnection 类，用子类的方式实现。

该类的具体子类都在 sun.net 包中。

该类的许多字段都是 protected 类型的。

通常根据运行时环境，使用反射机制根据当前协议调用对应的实现类。

## 7.1 打开URLConnection

直接使用URLConnection类的程序遵循以下步骤：

1. 构造一个URL对象
2. 调用这个URL对象的openConnection()获取一个对应该URL的URLConnection对象
3. 配置这个URLConnection
4. 读取首部字段
5. 获得输入流并读取数据
6. 获得输出流并写入数据
7. 关闭连接

URLConnection类仅有一个构造函数，且是保护类型。

获得该实例的办法有：通过URL对象获取，或者使用该类子类获取

```java
URL url = new URL("http://www.baidu/com");
URLConnection urlConnection = url.openConnection();
//从url获取的URLConnection
//
```

在该抽象类中，只有 connect() 方法没有实现，需要依靠具体的子类来实现

```java
abstract public void connect() throws IOException;
```

该方法建立与服务器的连接，因此依赖于服务类型（FTP、HTTP）

sun.net.www.protocol.file.FileURLConnection 的 connect() 方法将URL转换为和当目录中的一个文件名，创建该文件的MIME信息，然后打开一个指向该文件的缓冲 FileInputStream。该方法会创建一个 sun.net.www.http.HttpClient 对象，由它连接服务器

第一次构造URLConnection 的时候，它是未连接的，也就是说本地和远程主机无法发送和接收数据，没有socket连接。使用 getContent()、getInputStream()、getHeaderField() 等需要连接的方法，在未打开连接的情况下，它们会自行打开连接。

## 7.2 读取服务器的数据

使用 URLConnection 对象从一个 URL类获取数据所需的步骤：

1. 构造一个URL对象
2. 调用这个URL对下给你的openConnection()方法，获取对应该URL的URLConnection对象
3. 调用这个URLConnection的getInputStream()流
4. 读取3获得的输入流

下面是一个使用 getInputStream()方法下载web页面的例子

```java
public void getWebPage(){
    try {
        URL u = new URL("http://www.baidu.com/");//构造URL
        URLConnection urlConnection = u.openConnection();//获得链接
        try(InputStream raw =  urlConnection.getInputStream()){
            InputStream buffer = new BufferedInputStream(raw);
            //将InputStream串联到Reader
            Reader reader = new InputStreamReader(buffer);
            int c;
            while((c=reader.read())!=-1){
                System.out.print((char)c);
            }
        }
    } catch (MalformedURLException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

URLConnection 提供了对HTTP首部的访问，可以配置发送给服务器的请求参数，除了读取服务器数据之外，还可以向服务器写入数据

## 7.3 读取首部

**获取指定的首部字段**

常用首部：

- Content-Type
- Content-length
- Content-encoding
- Date
- Last-modified
- Expires

```java
public String getContentType()
```

返回响应主体的MIME内容类型。如果没有内容类型，返回null

使用正确字符集下载web页面的例子

```java
try {
    String encoding = "ISO-8859-1";
    URL u = new URL("http://www.baidu.com");
    URLConnection uc = u.openConnection();
    String contentType = uc.getContentType();
    int encodingStart = contentType.indexOf("charset=");//有没有描述字符集
    if(encodingStart!=-1){
        encoding = contentType.substring(encodingStart + 8);
        InputStream in = new BufferedInputStream(uc.getInputStream());
        Reader r = new InputStreamReader(in,encoding);//传入特定字符集
        int c;
        while((c=r.read())!=-1){
            System.out.print((char)c);
        }
        r.close();
    }
} catch (MalformedURLException e) {
    e.printStackTrace();
} catch (IOException e) {
    e.printStackTrace();
}
```



```java
public int getContentLength()
```

该方法获得内容中有多少字节。如果没有Content-length首部就返回-1。

有可能内容太大，int存不下，也会返回-1

可以使用

```java
public long getContentLengthLong()
```

从web网站下载二进制文件，在下载二进制文件的时候，由于不知道何时关闭连接，因此可以先得到主体长度，根据该长度读取响应的字节数



```java
public class BinarySaver {
    public static void main(String[] args) {

    }

    public static void saveBinary(URL u) throws IOException {
        URLConnection uc = u.openConnection();
        String contentType = uc.getContentType();
        int contentLength = uc.getContentLength();
        if (contentType.startsWith("text/") || contentLength == -1) {
            throw new IOException("This is not binary file");
        }

        try (InputStream raw = uc.getInputStream()) {
            InputStream buffer = new BufferedInputStream(raw);
            byte[] data = new byte[contentLength];//创建相应大小的字节
            int offset = 0;
            while (offset < contentLength) {//读取内容
                int byteRead = buffer.read(data, offset, data.length - offset);//按块读取
                if (byteRead == -1) break;
                offset += byteRead;
            }
            if(offset!=contentLength){
                throw new IOException("Only read "+offset+" bytes; Expected "+contentLength+" bytes");//没读够
            }
            String filename = u.getFile();//获得文件名
            filename = filename.substring(filename.lastIndexOf("/")+1);
            try(FileOutputStream fout = new FileOutputStream(filename)){
                fout.write(data);
                fout.flush();//输出记得flush
            }
            
        }

    }
```



```java
public String getcontentEncoding()
```

返回内容的编码方式，如果没有编码方式，就返回null。

Web上常用的编码方式x-gzip，可以使用 java.util.zip.GZipInputStream 直接解码。

内容编码方式和字符编码方式不同，字符编码方式由Content-type首部或者文档内部信息确定，指出如何将字符编码为字节；内容编码方式则指出字节如何编码为其他字节。



```java
public long getDate()
```

getDate() 返回一个 long，指出文档是何时发送的（从服务器角度），按照 GMT 时间，1970年1月1日子夜后过去了多久毫秒计算



```java
public long getExpiration()
```

有些文档有基于服务器的国企日子，指示应当何时从缓存中删除文档，并从服务器重新下载。



```java
public long getLastModified()
```

返回文档的最后修改日期

**获取任意首部字段**

```java
public String getHeaderField(String name)
```

返回指定首部的值

```java
String contentType = uc.getHeaderField("content-type");
String contentEncoding = uc.getHeaderField("content-encoding")
```

还可以用该方法获得其他首部的值

```java
public String getHeaderFieldKey(int n)
```

这个方法返回第n个首部字段的键。请求行为第0个，没有键返回null。

```java
public String getHeaderField(int n)
```

这个方法返回第n个首部字段的值。

```java
public long getHeaderFieldDate(String name, long default)
```

这个方法首先获取由name参数指定的首部字段，然后尝试将这个字符出啊你转换为一个long，指示 从GMT1970年经过的时间，来表示日期的首部字段。如果找不到指定首部，就用指定的default转换为日期String

```java
public int getHeaderFieldInt(String name, int default)
```

这个方法获得首部字段name，尝试转换为int。如果不包含将default转换为int



## 7.4 缓存

例如向网站图标这样的资源，浏览器只会向服务器请求一次，之后都缓存在本地，以后的都不用向服务器请求。一些HTTP首部（包括Expires和Cache-Control）可以控制缓存。

默认使用GET会进行缓存，HTTPS或POST不会缓存，可以用字段控制：

- Expires首部指示可以缓存这个资源表示，直到指定的时间为止
- Cache-control首部
  - max-age=[seconds]：从现在直到缓存过期之前的秒数
  - s-maxage=[seconds]：从现在，直到缓存项在共享缓存中过期之前的秒数
  - public：可以缓存一个经过认证的响应，否则不能保存
  - private：仅单个用户缓存可以保存响应，而共享缓存不应保存
  - no-cache：缓存项仍然可以缓存，不过客户端在每次访问时要用一个Etag或Last-modified首部重新验证响应状态
  - no-stroe：不缓存

Cache-control会覆盖Expires

- Last-modified：指示资源最后一次修改的日期。可以用HEAD请求来查看首部，只有当本地缓存和该字段时间不一致才会真正的请求GET
- Etag：是资源改变时这个资源的唯一标识符。客户端可以使用HEAD检查这个标识，只有当本地缓存副本有不同的Etag时，才会真正执行GET

```http
HTTP/1.1 200 OK
Date: Sun, 21 Apr 2013 15:12:46 GMT
Server: Apache
Connection: close
Content-Type: text/html; charset=ISO-8859-1
Cache-control: max-age=604800
Expires: Sun, 21 Apr 2013 15:12:46 GMT
Last-modified: Sat, 20 Apr 2013 09:55:04 GMT
ETag: "g1a56sf4g8s2"
```

上面的首部中，这个资源可以缓存604800秒或者缓存到一周以后。这个资源最后的修改日期4月20日，有一个ETag，如果本地缓存有更加新的副本，没必要缓存。

**Java的Web缓存**

默认情况下，Java并不完成缓存。要设置URL类使用的系统级缓存，需要有：

- ResponseCache的一个具体子类
- CacheRequest的一个具体子类
- CacheResponse的一个具体子类

第一个子类用来处理后两个子类的，需要把它传递到静态方法 ResponseCache.setDefault()。

一旦设置了缓存，只要系统尝试加载一个新的URL，它首先会在这个缓存中查找。如果缓存返回了所要的内容，URLConnection 就不需要与远程服务器连接。

ResponseCache提供了两个抽象方法，可以存储和获取系统缓存中的数据：

```java
public abstract CacheResponse
    get(URI uri, String rqstMethod, Map<String, List<String>> rqstHeaders)
    throws IOException;
public abstract CacheRequest put(URI uri, URLConnection conn)  throws IOException;
```

put() 方法返回的 CacheRequest 包装了一个 OutputStream，URL将把读取的可缓存数据写入这个输出流。

```java
public abstract class CacheRequest {
    public abstract OutputStream getBody() throws IOException;
    public abstract void abort();
}
```

子类中的 getOutputStream() 方法（应该是 getBody()吧）应当返回一个 OutputStream，指向缓存中的数据。这个数据与同时传入的 put() 方法的URI对应。

> 如果数据存储在一个文件中，就要返回连接到该文件的FileOutputStream。协议处理器会把读取的数据赋值到这个OutputStream。如果复制时出现问题，协议处理器就会调用 abort() 方法。这个方法应当从缓存中为这个请求存储的数据

get() 方法从数据流中获得数据和首部，包装在CacheResponse对象中返回。如果所需的URI不在缓存中，返回null。

```java
public abstract class CacheResponse {
    public abstract Map<String, List<String>> getHeaders() throws IOException;

    public abstract InputStream getBody() throws IOException;
}
```

这两个方法一个返回请求数据，一个返回首部。缓存最初的响应时，数据和首部都要存储。首部以一个不可修改的映射返回，它的键是HTTP首部字段名，值是每个指定HTTP的值列表。

下面是一个CacheRequest的实现子类

```java
public class SimpleCacheRequest extends CacheRequest {
    
    private ByteArrayOutputStream out = new ByteArrayOutputStream();
    
    @Override
    public OutputStream getBody() throws IOException {
        return out;
    }

    @Override
    public void abort() {
        out.reset();
    }
    
    public byte[] gtData(){
        if(out.size()==0) return null;
        return out.toByteArray();
    }
}
```

下面是一个简单实现的CacheResponse

它绑定到一个SimpleCacheRequest和一个CacheControl。共享引用将数据从请求类传给响应类。如果在文件中存储相应，就需要共享文件名。除了SimpleCacheRequest对象（要从中读取数据），还必须将最初的URLConnection对象传递给构造函数。这个对象用来读取HTTP首部，可以存储这个首部方便后面获取。

```java
public class SimpleCacheResponse extends CacheResponse {

    private final Map<String,List<String>> headers;
    private final SimpleCacheRequest request;
    private final Date expires;
    private final CacheControl control;


    public SimpleCacheResponse(SimpleCacheRequest request, URLConnection uc, CacheControl control) {
        this.request = request;
        this.control = control;
        this.expires = new Date(uc.getExpiration());
        this.headers = Collections.unmodifiableMap(uc.getHeaderFields());
    }

    @Override
    public Map<String, List<String>> getHeaders() throws IOException {
        return headers;
    }

    @Override
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(request.gtData());
    }
    
    public CacheControl getControl(){
        return control;
    }
    
    public boolean isExpired(){
        Date now = new Date();
        if(control.getMaxAge().before(now)) return true;
        else if(expires != null && control.getMaxAge() != null){
            return expires.before(now);
        }else{
            return false;
        }
    }
}
```

下面是一个ResponseCache子类示例，在请求时存储和获取缓存的值，同时还要注意原来的Cache-control首部。

```java
public class MemoryCache extends ResponseCache {

    private final Map<URI, SimpleCacheResponse> responses = new ConcurrentHashMap<>();
    private final int maxEntries;

    public MemoryCache(){
        this(100);
    }

    public MemoryCache(int maxEntries){
        this.maxEntries = maxEntries;
    }

    @Override
    public CacheResponse get(URI uri, String rqstMethod, Map<String, List<String>> rqstHeaders) throws IOException {
        if("GET".equals(rqstMethod)){
            SimpleCacheResponse response = responses.get(uri);
            //检查过期日期
            if(response != null && response.isExpired()){
                responses.remove(response);
                response = null;
            }
            return response;
        }else{
            return null;
        }
    }

    @Override
    public CacheRequest put(URI uri, URLConnection conn) throws IOException {
        if(responses.size() >= maxEntries) return null;

        CacheControl control = new CacheControl(conn.getHeaderField("Cache-Control"));
        if(control.isNoStore()){
            return null;
        }else if(!conn.getHeaderField(0).startsWith("GET")){
            //只缓存GET
            return null;
        }

        SimpleCacheRequest request = new SimpleCacheRequest();
        SimpleCacheResponse response = new SimpleCacheResponse(request,conn,control);

        responses.put(uri,response);
        return request;
    }
}
```



## 7.5 配置连接

URLConnection类有7个保护的实例字段，定义了客户端如何向服务器做出请求。

```java
protected URL url;//指定了这个URLConnection连接的URL，构造时设置，此后不能变
protected boolean doInput = true;//URLCoonection可以用于读取服务器、写入服务器或者同时用于读/写服务器。
protected boolean doOutput = false;//程序是否可以使用URLConnection将输出发回服务器。
protected boolean allowUserInteraction = defaultAllowUserInteraction;//标识该URLConnection是否需要与用户交互
protected boolean useCaches = defaultUseCaches;//有些客户端可以从本地获取文档，而不是从服务器获取
protected long ifModifiedSince = 0;//存储的文件进行修改过的时间
protected boolean connected = false;//如果连接已经打开，这个字段为true。没有直接读取或改变connected值得方法
```

这些字段都有对应的设置方法和获取方法

**超时**

有4个方法可以查询和修改连接的超时值，底层socket等待远程服务器的响应时，等待多长时间后会抛出SocketTimeoutException异常

```java
public void setConnectTimeout(int timeout)
public int getConnectTimeout()
public void setReadTimeout(int timeout)
public int getReadTimeout()
```

setConnectionTimeout()/getConnectionTimeout()控制socket的等待建立连接的时间

setReadTimeout()/getReadTimeout()控制输入流等待数据到达的时间



## 7.6 配置客户端请求HTTP首部

使用 setRequestProperty() 方法可以在连接打开前为这个URLConnection的首部增加属性，每个属性可以对应多个值，用逗号隔开。

该方法需要在连接打开前使用，否则抛出 IllegalStateException 异常

如果要给一个属性增加值，需要使用 addRequestProperty() 方法，set 方法会覆盖之前的属性值，add 方法是增量的

## 7.7 向服务器写入数据

在URLConnction中，如果需要向服务器提交数据（POST、PUT），可以使用

```java
urlConnection.getOutputStream()
```

方法获得一个输出流。

URLConnection 在默认的情况下不启用输出，所以在获取输出流之前需要先 setDoOutput(true) 方法启用输出，此时会从GET方法变为POST方法



如果获得了OutputStream，可以将它串联到BufferedOutputstream或者OutputWriter进行缓冲等等（装饰器模式）

下面是一个例子：

```java
URL url = new URL("http://www.baidu.com/s");
URLConnection urlConnection = url.openConnection();
//从url获取的URLConnection
//
//准备POST
urlConnection.setDoOutput(true);

OutputStream raw = urlConnection.getOutputStream();
BufferedOutputStream buffered = new BufferedOutputStream(raw);
OutputStreamWriter out = new OutputStreamWriter(buffered);
out.write("wd=search");
out.flush();//注意刷新输出流
out.close();

InputStream inputStream = urlConnection.getInputStream();//获取响应数据
```



## 7.8 URLConnection安全

```java
public Permission getPermission() throws IOException
```

getPermission() 方法返回一个 java.security.Permission 对象，指出连接这个URL所需的权限。如果没有需要的权限，返回null

## 7.9 猜测MIME媒体类型

有时候我们并不知道返回的是什么MIME类型，所以需要猜测

```java
public static String guessContentTypeFromName()
```

根据URL对象文件扩展名来猜测对象的内容类型

第二种猜测方式是

```java
public static String guessContentTypeFromStream(InputStream in)
```

根据输入流的前几个字节来判断是什么类型的文件。该输入流需要支持标记，在读取了前几个字节之后，该流需要返回到头重新读取





## 7.10 HttpURLConnection

java.net.HttpURLConnection 类是 URLconnection 类的抽象子类，该子类提供了处理 http URL 的很多方法。

可以获得和设置请求方法、确定是否重定向、获得响应码和消息、是否使用代理服务器等。

创建方法：

- 使用 http URL 的 openConnection() 方法
- 对 URL 的 openConnection() 方法的返回值进行强转

**请求方法**

```java
public void setRequestMethod(String method) throws ProtocolException
```

该方法来设置使用哪种请求：GET、POST、HEAD、PUT、DELETE、OPTIONS、TRACE

**HEAD**

只返回HTTP首部

**DELETE**

删除指定URL的文件

因为有风险，服务器可能需要提供身份认证

**PUT**

上传文件等

**OPTIONS**

询问某个特定URL支持哪些选项（支持哪些请求），包含在Allow属性中。

**TRACE**

发送HTTP首部到服务端



**断开与服务器连接**

```java
public abstract void disconnect()
```

**处理服务器响应**

```java
public int getResponseCode() thorws IOException
```

响应码后面的文本字符串称为响应消息，可以由

```java
public String getResponseMessage() throws IOException
```

返回

**错误条件**

有时候没有请求到页面（返回404），服务器端不会直接返回404，而是返回一个帮助页面，帮助查找错误在哪里

该错误页面可以使用 getErrorStream() 方法得到

```java
public InputStream getErrorStream()
```

在 catch 块中调用

**重定向**

在重定向时，有可能服务器会将用户从一个可信网站转移到一个不可信网站

默认情况下，HttpUELConnection 会跟随重定向。不过，HttpURLConnection 有两个静态方法，允许你确定是否跟随重定向

```java
public static boolean getFollowRedirects()
public static void setFollowRedirects(boolean follow)
```

如果跟随重定向，第一个方法返回 true。如果安全管理器不允许此修改，set 方法抛出 SecurityException 异常

Java 中两个方法针对各个实例配置重定向

```java
public boolean getInstanceFollowRedirects()
public void setInstanceFollowRedirects(boolean followRedirects)
```

**代理**

许多防火墙后面的用户或者使用AOL或其它大吞吐量ISP的用户会通过代理服务器访问Web。usingProxy()方法可以住处某个 httpURLConnection 是否通过代理服务器

```java
public abstract boolean usingProxy()
```

如果使用了代理，这个方法返回true



**流模式**

通常首部有一个 Content-length 属性表示主体有多少字节。但是通常在写首部的时候还不知道主体的字节数。在 Java 中，对于 HttpURLConnection 获取的 OutputStream，将写入此OutputStream的所有内容缓存，直到流关闭，这样就知道有多少字节了。

这样的解决方案对于大的表单或者上传文件时负担会很大。

对于已知大小的文件，可以将数据大小告诉HttpConnection对象，如果不知道对象大小，可以使用分块传输编码方式

```java
public void setChunkedStreamingMode(int chunkLength)
```

该编码方式需要在连接URL之前设置

分块传输编码方式会妨碍身份认证和重定向，如果试图向重定向的URL或需要口令认证的URL发送分块文件，就会抛出HttpRetryException异常

```java
public void setFixedLengthStreamingMode(int contentLength)
public void setFixedLengthStreamingMode(long contentLength)
```

通过这个方法设置已知大小的主体长度

# 第八章 客户端Socket

Socket为程序员封装了网络相关（传输层、网络层）等数据报的校验、包分解、包重传等工作

## 8.1 使用Socket

Socket是两台主机之间的一个连接。由7个基本步骤：

- 连接远程主机
- 发送数据
- 接收数据
- 关闭连接
- 绑定端口
- 监听入站数据
- 在绑定端口上接受来自远程主机的连接

Java的Socket类提供了对应前4个操作的方法。后面3个操作仅服务器需要，这些操作由ServerSocket类实现

- 程序用构造函数创建一个新的Socket
- Socket尝试连接远程主机

一旦建立了连接，本地和远程主机就从这个socket得到输入输出流，使用这两个流相互发送数据。连接是全双工的，两台主机都可以同时发送和接收数据。

## 8.2 用Telnet研究协议

telnet 默认连接 23 端口

**用Socket从服务器读取**

> telnet time.nist.gov 13

从该服务器获取时间。

>  59705 22-05-06 07:33:03 50 0 0 597.0 UTC(NIST) *

读取这个Socket的InputStream时，就会得到这个结果。

时间的格式为 `JJJJJ YY-MM-DD HH:MM:SS TT L H msADV UTC(NIST) OTM`

- JJJJJ 是修正儒略日（从1858年11月17日子时以来的整天数）
- YY-MM-DD 是年份后两位和月，日
- HH:MM:SS 时间
- TT 指美国目前采用标准时间还是日光节省时间：00表示标准时间，50表示日光节省时间
- L 指示当前月最后一天子夜是否增加或减去一个闰秒
- H 表示服务器健康程度：0表示健康，1表示最多相差5秒，2表示超过5秒，3表示误差不确定，4表示处于维护状态
- msADV 是一个毫秒数，NIST把这个数增加到它发送的时间，对网络延迟的大致补偿
- 字符串 UTC(NIST) 是一个常量，OTM几乎是一个常量（通常是*）

上面是使用 telnet 工具进行了请求，接下来使用 java 代码中的 Socket 连接来处理

```java
Socket socket = null;
try {
    socket = new Socket(hostname, port);
    socket.setSoTimeout(15000);//设置超时时间
    InputStream in = socket.getInputStream();
    StringBuilder time = new StringBuilder();
    InputStreamReader reader = new InputStreamReader(in, "ASCII");
    for (int c = reader.read(); c != -1; c = reader.read()) {
        time.append((char) c);
    }
    System.out.println(time);
} catch (IOException e) {
    e.printStackTrace();
} finally {
    if (socket != null) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

如果服务器拒绝这个连接，socket会抛出一个ConnectException，或者路由器无法确定如何将你的包发送到服务器，则要抛出一个NoRouteToHostExceptin异常

在实际的生产过程中，可能需要根据不同的协议和内容改变流的解析方式。

**用Secket写入服务器**

```java
public void output() {
    Socket socket = null;
    try {
        socket = new Socket("dict.org", 2628);
        socket.setSoTimeout(15000);
        OutputStream out = socket.getOutputStream();//向服务器的输出流
        Writer writer = new OutputStreamWriter(out, "UTF-8");
        writer = new BufferedWriter(writer);
        InputStream in = socket.getInputStream();//获得输入流,服务器的响应
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(in, "UTF-8")
        );
        String[] words = new String[]{"gold", "food"};
        for (String word : words) {
            define(word, writer, reader);
        }

        writer.write("quit\r\n");//退出
        writer.flush();
    } catch (IOException e) {
        System.out.println(e);
    } finally {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

public void define(String word, Writer writer, BufferedReader reader) throws IOException {
    writer.write("DEFINE eng-lat " + word + "\r\n");
    writer.flush();

    for (String line = reader.readLine(); line != null; line = reader.readLine()) {
        if (line.startsWith("250")) {//OK
            return;
        } else if (line.startsWith("552")) {//无匹配
            System.out.println("No definition found for " + word);
            return;
        } else if (line.matches("\\d\\d\\d .*")) continue;
        else if (line.trim().equals(".")) continue;
        else System.out.println(line);
    }


}
```

**半关闭Socket**

close() 方法会同时关闭输入流和输出流。有时候只想关闭一个，使用下面的方法

```java
public void shutdownInput() throws IOException
public void shutdownOutput() throws IOException
```

这里实际没有关闭Socket，而是将流的读取调整到了末尾

```java
public boolean isInputShutdown()
public boolean isOutputShutdown()
```

判断输入输出流是否是关闭的



## 8.3 构造和连接Socket

java.net.Socket 类是 Java 完成客户端 TCP 操作的基础类。其他建立TCP网络连接的面向客户端的类（URL、URLConnection、Applet、JEditorPane）都会调用 Socket 类的方法



**基本的构造函数**

```java
public Socket(String host, int port)
        throws UnknownHostException, IOException
public Socket(InetAddress address, int port) throws IOException
```

每个Socket需要指定连接的主机和端口。该主机可以是String或者InetAddress

这些构造函数会连接scoket，构造函数返回前就会与目标主机建立连接。如果未能打开连接会抛出 IOException 或者 UnknownHostException 异常

这个过程当中因为会尝试连接远程主机的 socket，因此可以用这个对象确定远程主机是否允许在某个端口建立连接（对每个端口创建Socket，如果抛出异常就是不能创建）

**选择从哪个本地接口连接**

有两个构造函数可以指定要连接的主机和端口，以及从哪个接口和端口连接：

```java
public Socket(String host, int port, InetAddress localAddr,
                  int localPort) throws IOException
public Socket(InetAddress address, int port, InetAddress localAddr,
                  int localPort) throws IOException
```

这个Socket连接到前两个参数指定的主机和端口。它从后两个参数指定的本地网络接口和端口来连接。



**构造但不连接**

```java
public Socket()
public Socket(Proxy proxy)
protected Socket(SocketImpl impl) throws SocketException
```

上面的三个构造函数是用来创建未连接的Socket对象的

```java
try(Socket socket = new Socket()){
    //上面的socket没有建立连接
    //创建socket选项
    SocketAddress address = new InetSocketAddress("time.nist.gov",13);
    socket.connect(address);//建立连接
    //使用socket...
}catch (IOException e){
    System.out.println(e);
}
```

可以传入一个int作为第二个参数，代表超时时间。使用该Socket构造函数，可以支持不同类型的socket



**Socket地址**

SocketAddress 类表示一个连接端点。这是一个空的抽象类，除了一个默认的构造函数外没有其它的方法。现在大多数使用的Socket地址都是InetSocketAddress实例。

SocketAddress 类的主要用途是为暂时的socket连接信息（如IP地址和端口）提供一个方便的存储，即使最初的socket已断开并被垃圾回收，这些信息也可以重用来创建新的Socket

使用下面的两个方法分别返回目标主机地址和客户端地址，方便重用

```java
public SocketAddress getRemoteSocketAddress()
public SocketAddress getLocalSocketAddress()
```

InetSocketAddress 类是 SocketAddress 的子类。构造方法：

```java
public InetSocketAddress(InetAddress address, int port)
public InetSocketAddress(String host, int port)
public InetSocketAddress(int port)
```

还可以使用静态方法 InetSocketAddress.createUnresolved()，从而不再在DNS中查找主机

它还有一个检查这个对象的方法

```java
public fianl InetAddress getAddress()
public final int getPort()
public final String getHostName()
```

**代理服务器**

```java
public Socket(Proxy proxy)
```

Socket 使用的代理服务器由 socksProxyHost 和 socksProxyPort 系统属性控制，这些属性应用于系统中的所有 Socket。可以为参数传入 Proxy.NO_PROXY，完全绕过代理，进行直连。

```java
SocketAddress socketAddress = new InetSocketAddress("myproxy.example.com",1090);
Proxy proxy = new Proxy(Proxy.Type.SOCKS,socketAddress);
Socket s = new Socket(proxy);
SocketAddress remote = new InetSocketAddress("login.ibiblio.org",25);
s.connect(remote);
```

上面的代码使用了 myproxy.example.com 地址对 login.ibiblio.org 发起了代理连接，SOCKS 是 java 识别的唯一底层代理。HTTP类型是应用层代理不是传输层代理。

**获取Socket信息**

```java
public InetAddress getInetAddress()//获取服务器地址
public int getPort()//服务器端口
public InetAddress getLocalAddress()//客户端地址
public int getLocalPort()//客户端端口
```

这些属性只能获取不能设置，一旦连接建立，这个属性不能更改

**关闭还是连接**

如果Socket关闭，isClosed()方法会返回true。Socket从来没有建立连接或者已经关闭都会返回false

isConnection() 方法的意思是，该Socket是否从未连接过一个远程主机。确实连接过返回 true，即使已经关闭。否则false

isBound() 是否成功绑定到本地的出站端口。



**toString()**



## 8.4 设置Socket选项

客户端 Socket 的选项：

- TCP_NODELAY
- SO_BINDADDR
- SO_TIMEOUT
- SO_LINGER
- SO_SNDBUF
- SO_RCVBUF
- SO_KEEPALIVE
- OOBINLINE
- IP_TOS

**TCP_NODLAY**

```java
public void setTcpNoDelay(boolean on) throws SocketException
public boolean getTcpNoDelay() throws SocketException
```

设置为 true 可确保包会尽可能地发送，无论包的大小。正常情况下小数据包（一字节）在发送前会组合为更大的包。在发送另一个包之前，本地客户端要等待远程主机对前一个包的确认（Nagle算法）。这种算法在小数据量信息稳定传输的应用层会变得很慢（要等待发送）。设置为 true 就会打破这种等待。

**SO_LINGER**

```java
public void setSoLinger(boolean on, int linger) throws SocketException
public int getSoLinger() throws SocketException
```

制定了 Socket 关闭时如何处理尚未发送的数据。默认情况下， close() 方法立即返回，系统尝试发送剩余数据。如果延迟设置为0，那么当Socket关闭时，剩余的数据会被丢弃。如果该选项打开且延迟设置为任意正数，close() 方法会阻塞指定的秒数，等待数据发送和确认。当过去设置的秒数后，Socket关闭，剩余的数据丢弃。



**SO_TIMEOUT**

```java
public synchronized void setSoTimeout(int timeout) throws SocketException
public synchronized int getSoTimeout() throws SocketException
```

正常情况下，从Socket读取数据时，read() 调用会阻塞尽可能长的时间来得到足够的字节。设置该选项可以确保这个调用不会超过设定的阻塞时间。时间到期就会抛出 InterruptedIOExcrption 异常，不过 Socket 仍然是连接的。单位是毫秒数，0被解释为无限超时。

**SO_RCVBUF和SO_SNDBUF**

TCP使用缓冲区来提升网络性能。

```java
public synchronized void setReceiveBufferSize(int size)
	throws SocketException
public synchronized int getReceiveBufferSize()
    throws SocketException
public synchronized void setSendBufferSize(int size)
    throws SocketException
public synchronized int getSendBufferSize() throws SocketException
```

SO_RCVBUF 选项控制用于网络输入的建议的接收缓冲区大小；SO_SNDBUF 选项控制用于网络输入的建议的发送缓冲区大小。

虽然他们能够分开设置，但是通常底层会取两者较小的一个，并且这只是一个建议值，底层不一定采用。

如果参数小于等于0，抛出 IllegalArgumentException 异常



**SO_KEEPALIVE**

如果打开了该选项，客户端会偶尔通过一个空闲连接发送一个数据包，以确保服务器未崩溃。如果服务器没能响应这个包，客户端会持续尝试11分钟，直到接收到响应，或者到12分钟未收到响应，客户端就会关闭socket。如果没有该选项，不活动的客户端可能永远不知道服务器已经崩溃，从而永远存在下去

```java
public void setKeepAlive(boolean on) throws SocketException
public boolean getKeepAlive() throws SocketException
```



**OBBINLINE**

TCP包括一个可以发送单字节带外“紧急”数据的特性（Out of Band，OOB）。这个数据会立即发送。当接收方收到紧急数据，会优先处理紧急数据。

```java
public void sendUrgentData (int data) throws IOException
```

这个方法几乎会立即发送参数中的最低位字节。如果必要，当前缓存的所有数据将首先刷新输出。

```java
public void setOOBInline(boolean on) throws SocketException
public boolean getOOBInline() throws SocketException
```

因为Java并不区分紧急数据和非紧急数据，这使它不能理想地发挥作用。



**SO_REUSEADDR**

一个Socket关闭时，可能不会立即释放本地端口，尤其是当Socket关闭时若仍有一个打开的连接，就不会释放本地端口。因为关闭连接时，网络上可能还存在该连接的数据，防止这些数据被该端口的新连接接收。

该选项打开，允许另一个Socket绑定到已经有Socket打开的端口。默认是关闭的

```java
public synchronized void setReceiveBufferSize(int size)
    throws SocketException
public synchronized int getReceiveBufferSize()
    throws SocketException
```

**IP_TOS服务类型**

不同类型的Internet服务有不同的性能需求。服务类型存储在IP首部中一个名为IP_TOS的8位字段中。可以使用下面的方面检查和设置Socket放在这个字段中的值：

```java
public void setTrafficClass(int tc) throws SocketException
public int getTrafficClass() throws SocketException
```

设置的值在0-255之间，否则抛出 IllegalArgumentException

加速转发、保证转发

## 8.5 Socket异常

Socket类的大多数方法都声明抛出IOException或其子类java.net.SocketException

```java
public class SocketException extends IOException
```

对于SocketException还有一些子类提供更多的信息

```java
public class BindException extends SocketException
public class ConnectException extends SocketException
public class NoRouteToHostException extends SocketException
```

如果试图在一个正在使用的端口上构造Socket或ServerSocket，或者没有足够的端口权限，抛出BindException异常

当连接的远程主机拒绝，而拒绝的原因通常是由于主机忙或没有进程在监听该端口，抛出ConnectException

连接超时，抛出NoRouteToHostException

java.net包还包括了ProtocolException异常，他也是IOException的子类

```java
public class ProtocolException extends IOException
```

当网络接收的数据违反TCP/IP规范的时候，会抛出该异常



## 8.6 GUI应用中的Socket

# 第九章 服务器Socket

对于服务器，Java提供了一个ServerSocket类表示服务器Socket。ServerSocket在服务器上运行，监听入站TCP连接。每个服务器Socket监听服务器机器上的一个特定端口。当远程主机上的一个客户端尝试连接这个端口时，服务器就会被唤醒，协商建立客户端和服务器之间的连接，并返回一个常规的Socekt对象，表示两台主机之间的Socket。

## 9.1 使用ServerSocket

ServerSocket包含了编写服务器所需的全部内容。

服务器程序的基本生命周期：

1. 使用一个ServerSocket()构造函数在一个特定端口创建一个新的ServerSocket。
2. ServerSocket使用其accept()方法监听这个端口的入站连接。accept()会一直阻塞，直到一个客户端尝试建立连接，此时accept()将返回一个连接客户端和服务器的Socket对象。
3. 根据服务器类型，会调用Socket的getInputStream()方法或者getOutputStream()方法，或者两个都有调用，以获得与客户端通信的输入和输出流。
4. 服务器和客户端根据已协商的协议交互，直到要关闭连接。
5. 服务器或客户端关闭连接。
6. 服务器返回到步骤2，等待下一次连接。

下面是对应第八章中查询时间程序的服务器程序：

```java
public class DayTime {

    public final static int PORT = 13;

    public static void main(String[] args) {
        try (ServerSocket socket = new ServerSocket(PORT)) {
            //增加while(true)监听多个连接
            while (true) {
                try (Socket conn = socket.accept()) {//这里accept接收到的socket是客户端的socket
                    Writer out = new OutputStreamWriter(conn.getOutputStream());//装饰
                    Date now = new Date();
                    out.write(now.toString() + "\r\n");//网络编程中最好自己加换行，不然会根据平台的默认添加，导致其它平台无法区分
                    out.flush();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
```

accept()方法会阻塞服务器，直到有连接。分两个catch来检查同一个异常（看起来是同一个异常）是因为两个try语句造成IOException的原因是不同的。

服务器发送给客户端的数据，通过accept()方法返回的客户端Socket对象的OutputSteam进行返回。

**提供二进制数据**

发送二进制的文本数据，只需要一个写byte数组的OutputStream，而不是写String的Writer



**多线程服务器**

在上面的程序中，可以连续处理多个连接请求，但是只有当前一个连接处理完之后才能接收下一个连接。即使在一个连接中需要处理的数据很少，也有可能碰到崩溃的客户端使服务器挂起几秒，直到它注意到客户端socket已经中断。也就是说会影响连接的处理效率。

Java程序应当生成一个线程与客户端交互，这样服务器就能够尽快处理下一个连接。与完整的子进程相比，线程对服务器带来的负载更小。创建太多的进程会有很大开销。但是如果协议足够简单且处理很快，服务器完成处理后会关闭连接，那么服务器不需要生成线程，可以立即处理客户端请求，这样更快。

操作系统将把连接到某个端口的连接放进一个队列中。这个队列的大小根据操作系统的不同而不同。队列如果已经满了，系统会拒绝多余的连接。一些ServerSocket函数允许修改默认队列长度，但不能超过操作系统的限制。

多线程Daytime程序

```java
public class MutithreadDayTime {

    public final static int PORT = 13;

    public static void main(String[] args) {
        try(ServerSocket server = new ServerSocket(PORT)){//在port端口监听
            while (true){
                try{
                    Socket connection = server.accept();//服务端监听到客户端
                    Thread task = new DaytimeThread(connection);
                    task.start();//启动线程
                }catch(IOException e){
                    System.out.println("Couldn't start server");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //内部类
    private static class DaytimeThread extends Thread{
        private Socket connection;//客户端socket

        DaytimeThread(Socket connection){
            this.connection = connection;
        }

        @Override
        public void run(){
            try{
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                Date now = new Date();//新连接接进来的时间
                out.write(now.toString()+"\r\n");
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try{
                    connection.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
```

**用Socket写入服务器**

前面的例子中都是客户端发起连接请求，服务器通过客户端Socket的OutputStream向客户端返回数据。这个过程中并没有从客户端读取任何数据。

如果需要从客户端读取数据，则需要同时获取一个 InputStream 和一个 OutputStream。写入和读取的时机需要看协议的规定。

echo协议是客户端向服务器发送一条消息，服务器也返回一条消息。

下面写一个 EchoServer

```java
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class EchoServer {
    public final static int PORT = 7;

    public static void main(String[] args) {
        ServerSocketChannel serverChannel;
        Selector selector;
        try {
            serverChannel = ServerSocketChannel.open();
            ServerSocket ss = serverChannel.socket();
            InetSocketAddress address = new InetSocketAddress(PORT);
            ss.bind(address);
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            Set<SelectionKey> readKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        SelectionKey clientKey = client.register(
                                selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                        ByteBuffer buffer = ByteBuffer.allocate(100);
                        clientKey.attach(buffer);
                    }
                    if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer output = (ByteBuffer) key.attachment();
                        output.flip();
                        client.write(output);
                        output.compact();
                    }
                } catch (IOException e) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    e.printStackTrace();
                }
            }
        }
    }
}
```

**关闭服务器Socket**

如果使用完一个服务器Socket，应该及时关闭，释放端口。

不要把关闭ServerSocket和关闭Socket混淆。关闭ServerSocket会释放本地主机的一个端口，允许另一个ServerSocket绑定到这个端口。它还会中断该ServerSocket已经接受的目前处于打开状态的所有Socket。

在try-finally块中采用close-if-not-null来关闭资源。

```java
ServerSocket server = null;
try{
	server = new ServerSocket(PORT);
	//...
}finally{
	if(server!=null){
		try{
			server.close();		
		}catch(IOEXception e){
            //...
        }
	}
}
```

可以使用无参构造来改进。无参构造不会抛出任何异常，也不会绑定到端口，需要在构造之后调用bind()方法来绑定一个Socket地址：

```java
ServerSocket server = new ServerSocket();
try{
    SocketAddress address =  new InetSocketAddress(port);
    server.bind(address);
    //...
}finally{
    try{
        server.close();
    }catch(IOException e){
        //
    }
}
```

关闭一个ServerSocket后，不能再重新连接，即使是同一个端口也不行。如果已经关闭，isClosed()方法返回true。

对于无参构造的ServerSocket，isClosed()会返回false，不认为是关闭的；isBound()方法会指出ServerSocket是否已经绑定到一个端口。如果曾经绑定过，即使ServerSocket已经关闭，也会返回true。

## 9.2 日志

**日志记录内容**

日志中通常希望记录两个主要内容

- 请求
- 服务器错误



## 9.3 构造服务器Socket

ServerSocket有4个公共的构造函数

```java
public ServerSocket() throws IOException
public ServerSocket(int port) throws IOException
public ServerSocket(int port, int backlog) throws IOException
public ServerSocket(int port, int backlog, InetAddress bindAddr) throws IOException
```

如果要创建一个在端口80的HTTP服务器，可以写为：

```java
ServerSocket httpServer = new ServerSocekt(80);
```

构造时传入0，会随机指定端口

要指定入栈请求队列的长度为50

```java
ServerSocket httpServer = new ServerSocekt(80,50);
```

默认地，如果一个主机有多个网络接口或IP地址，服务器Socket会在所有接口和IP地址的指定端口上监听。

使用上面最后一个构造函数可以指定该ServerSocket在哪个IP地址上工作。

**构造但不绑定端口**

使用无参构造创建一个ServerSocket，将需要使用bind()方法进行绑定。

```java
public void bind(SocketAddress endpoint) throws IOException
public void bind(SocketAddress endpoint, int backlog) throws IOException
```

这个特性的主要用途是，允许程序在绑定端口之前设置服务器socket选项。有些选项在服务器socket绑定后必须固定。

## 9.4 获得服务器Socket的有关信息

ServerSocket提供了两个方法来获得这个ServerSocekt占用的本地地址和端口。

```java
public InetAddress getInetAddress()
```

这个方法会返回服务器使用的地址。如果本地有多个IP，则不确定会获得哪个IP。

```java
public int getLocalPort()
```

顾名思义，这个方法返回该ServerSocket监听在哪个端口。

## 9.5 Socket选项

ServerSocket支持3个选项：

- SO_TIMEOUT
- SO_REUSEADDR
- SO_RCVBUF

**SO_TIMEOUT**

SO_TIMEOUT是accept()在抛出java.io.InterruptedIOException异常前等待入站连接的时间，以毫秒计。

setSoTimeout()方法会设置ServerSocket的该值

```java
public synchronized void setSoTimeout(int timeout) throws SocketException
public synchronized int getSoTimeout() throws IOException
```

调用accpet()后开始倒数计时。如果超时，accept()抛出SocketTimeoutException。

**SO_REUSEADDR**

该选项确定是否允许一个新的Socket绑定到之前使用过的一个端口，而此时可能还有一个发送到原Socket的数据正在网络上传输。

```java
public void setReuseAddress(boolean on) throws SocketException
public boolean getReuseAddress() throws SocketException
```

默认值依赖于具体平台。



**SO_RCVBUF**

该选项设置了ServerSocket接受的客户端Socket默认接收缓冲区大小

```java
public synchronized void setReceiveBufferSize (int size) throws SocketException
public synchronized int getReceiveBufferSize() throws SocketException
```

如果要设置比64KB更大的缓冲区，就必须在绑定之前设置。

**服务类型**



## 9.6 HTTP服务器

HTTP是一个很大的协议，但是一些特定服务器不需要其中的很多功能。本节将列举一些单一功能的服务器。





# 10 安全Socket



# 11 非阻塞IO

因为CPU速度快于网络，传统的Java解决方案是缓冲和多线程。多个线程可以同时为不同的连接生成数据，并将数据放在缓冲区中，直到网络确实准备好发送这些数据；对于简单服务器，如果不需要非常高的性能，这样做是可行的。不过生成多个线程并在线程之间切换的开销是不容忽视的。



## 11.1 示例



## 11.2 缓冲区

在新IO模型中，所有的IO都需要缓冲。在新的IO模型中，不再和输入输出流交换数据，而是要从缓冲区中读写数据。

从编程角度看，流和通道之间的关键区别在于流是基于字节的，而通道是基于块的。流设计为按顺序一个字节借一个字节地传送数据。处于性能考虑也可以传送字节数组。不过，基本的概念都是一次传送一个字节的数据。与之不同，通道会传送缓冲区中的数据块，可以读写通道的字节之前，这些字节必须已经存储在缓冲区中，而且一次会读写一个缓冲区数据。

流和通道/缓冲区之间的第二个区别是，通道和缓冲区支持同一对象的读写。也不总是如此。例如：指向CDROM上某个文件时，只能读不能写。

缓冲区由数据列表和4个记录信息的部分组成：

- 位置（position）：

缓冲区中将读取或写入的下一个位置。这个位置从0开始，最大值等于缓冲区大小。可以用下面两个方法获取和设置：

```java
public final int position()
public fianl Buffer position(int newPosition)
```

- 容量（capacity）：

缓冲区可以保存的元素的最大数目。容量在创建缓冲区时设置，此后不能改变。可以用以下方法读取：

```java
public fianl int capacity()
```

- 限度（limit）：

缓冲区中可访问数据的末尾位置。只要不改变限度，就无法读写超过这个位置的数据，即使缓冲区有更大的容量也没有用。限度可以用下面两个方法获取和设置：

```java
public final int limit()
public final Buffer limit(int newLimit)
```

- 标记（mark）：

缓冲区中客户端指定的索引。通过调用mark()可以将标记位置为当前位置。调用reset()可以将当前位置设置为所标记的位置：

```java
public final Buffer mark()
public fianl Buffer reset()
```

如果将位置设置为低于现有标记，则丢弃这个标记。

Buffer的公共超类，提供了另外几个方法：

clear()方法将位置设置为0，并将限度设置为容量，从而将缓冲区清空(不是真正的清空，老数据没有被删除)。这样就可以完全重新填充缓冲区了

```java
public final Buffer clear()
```

rewind()将位置设置为0，但不改变限度：这允许重新读取缓冲区

```java
public final Buffer rewind()
```

flip()方法将限度设置为当前位置，位置设置为0：

```java
public fianl Buffer flip()
```

remaining方法返回缓冲区中当前位置与限度之间的元素数。如果剩余元素大于0，hasRemaining()方法返回true：

```java
public final int remaining()
public fianl boolean hasRemaining()
```

**创建缓冲区**

在使用缓冲区的时候，一般要使用具体的子类（ByteBuffer、IntBuffer等），而不是使用Buffer超类。

每种类型的缓冲区都有几个工厂方法，以各种方式创建这个类型的特定于实现的子类。空的缓冲区一般由allocate()创建。预填充数据的缓冲区由wrap()方法创建。前一个常用于输入，后一个常用于输出。

**分配**

基本的allocate()方法只返回一个指定固定容量的新缓冲区。

```java
ByteBuffer buffer = ByteBuffer.allocate(100);
```

游标位于缓冲区开始的位置，即0。用allocate()创建的缓冲区基于Java数组，可以通过array()和arrayOffset()方法来访问。

```java
byte[] data = buffer.array()
```

array()实际暴露了缓冲区的私有数据，谨慎使用。

通常情况是使用数据填充缓冲区，获取其后备数组，然后操作这个数组。开始处理数组之后就不要再写缓冲区。

**直接分配**

ByteBuffer(但不包括其它缓冲区类)有另外一个allocateDirect()方法，该方法不为缓冲区创建后备数组。VM会对以太网卡、核心内存或其它位置上的缓冲区使用直接内存访问，依次实现直接分配的ByteBuffer。该方法使用同allocate()。

在直接缓冲区上调用array()和arrayOffset()会抛出UnsupportedOperationException异常。直接缓冲区在一些虚拟上工作更快，不过创建直接缓冲区比间接缓冲区代价更高，所以只能在缓冲区可能只持续较短时间时才分配这种缓冲区。其细节非常依赖于VM。

**包装**

如果已经有了要输出的数据数组，一般要用缓冲区进行包装，而不是分配一个新缓冲区，然后一次一部分复制到这个缓冲区：

```java
byte[] data = "Some data".getBytes("UTF-8");
ByteBuffer buffer1 = ByteBuffer.wrap(data);
```

缓冲区包含数组的一个引用，这数组将作为它的后备数组。由包装创建的缓冲区肯定不是直接缓冲区。



**填充和排空**

缓冲区有一个当前位置，由position()方法标识。缓冲区位置从0开始，标识下一个将要读写的位置。

```java
CharBuffer buffer = CharBuffer.allocate(12);
buffer.put('H');
buffer.put('e');
buffer.put('l');
buffer.put('l');
buffer.put('o');
```

上面的代码执行后，缓冲区位置为5。缓冲区填充只能到容量大小，如果超出设置容量，put()方法抛出BufferOverflowException异常。

如果要再次读写之前的数据，需要执行flip()方法，位置设置为0，限度为当前位置。

这时候调用get()方法来获取当前位置元素。位置达到限度，hasRemaining()返回false。

Buffer还有下面两个方法可以使用

```java
public abstract byte get(int index)
public abstract ByteBuffer put(int index, byte b)
```

这两种方法可以获取和填充缓冲区特定位置的数据，并不改变“位置（offset）”

**批量方法**

操作数组块。

```java
public ByteBuffer get(byte[] dst)
public ByteBuffer get(byte[] dst, int offset, int length)
public ByteBuffer put(byte[] src)
public ByteBuffer put(byte[] src, int offset, int length)
```

put()方法将数组写到指定的数组中，get()方法将数据读到指定的数组中。

**数据转换**

Java中基本类都可以用字节表示，所以在ByteBuffer类中提供了读取适量字节来转换为其它基本类型的方法。下面列举一个

```java
public abstract char getChar()
public abstract ByteBuffer putChar(char value)
```

传统IO模型中由DataOutputStream和DataInputStream完成任务。ByteBuffer类还提供数据是大端还是小端：

```java
buffer.order(ByteOrder.LITTLE_ENDIAN)
```

**视图缓冲区**

如果程序员直到SocketChannel读取的ByteBuffer只包含一种特定基本数据类型的元素，那么就有必要创建一个视图缓冲区。这是一个适当类型的新的Buffer对象，它从当前位置开始由底层ByteBuffer提取数据。修改视图缓冲区会反应到底层缓冲区。

下面是创建对应类型的试图缓冲区的方法：

```java
public abstract ShortBuffer asShortBuffer()
```



**压缩缓冲区**

大多数可写缓冲区都支持compact()方法。

压缩时，将缓冲区中所有剩余的数据都移到缓冲区的开头，为元素释放更多空间。

使用NIO时，一边读写，然后将剩余的数据再压缩到头，方便读写。



**复制缓冲区**

复制缓冲区并不克隆，它与原缓冲区共享数据，但是拥有独立的标记、限度和位置。

```java
public abstract ByteBuffer duplicate()
```

希望通过多个通道大致并行地传输相同地数据时，复制非常有用。可以为每个通道建立主缓冲区副本，让每个通道以其自己的速度运行。



**分片缓冲区**

分片缓冲区是原缓冲区地一个子区间，其起始位置是原缓冲区的当前位置，容量是原缓冲区的限度位置。

```java
public abstract ByteBuffer slice()
```

**标记和重置**

如果要重新读取某些数据，可以使用标记和重置

```java
public final Buffer mark()
public final  Buffer reset()
```

**Object方法**

缓冲区类还实现了Comparable接口，当满足下列条件时，认为两个缓冲区相等：

- 它们都具有相同的类型
- 缓冲区中剩余的元素个数相同
- 相同相对位置上的元素彼此相等

## 11.3 通道

通道将缓冲区的数据块移入或移出到各种IO源，如文件、socket、数据报等。通道类的层次结构相当复杂，有多个接口和许多可选操作。不过对于网络编程来说，实际上只有3个重要的通道类：SocketChannel、ServerSocketChannel、DatagramChannel。

### 11.3.1 SocketChannel

ScoektChannel类可以读写TCP Socket。数据必须编码到ByteBuffer对象中来完成读/写。每个SocketChannel都与一个对等端(peer)Socket对象关联，这个Socket可以用于高级配置，但有些应用采用默认选项就可以正常运行，对于这些应用，可以忽略这个需求。

**连接**

SocketChannel类没有任何公共构造函数。使用两个静态open()方法来创建：

```java
public static SocketChannel open(SocketAddress remote) throws IOException
public static SocketChannel open() throws IOException
```

第一个方法会建立连接。这个方法将阻塞。下面是一个连接的例子：

```java
SocketAddress address = new InetSocketAddress("www.example.com",80);
SocketChannel channel = SocketChannel.open(address);
```

为了在连接前配置通道和socket的各种选项，可以使用无阻塞方式打开通道，第二种方法：

```java
SocketChannel channel = SocketChannel.open();
SocketAddress address = new InetSocketAddress("www.example.com",80);
channel.configureBlocking(false);
channel.connect();
```

使用非阻塞通道时，connect()方法会立即返回，甚至在建立连接前就会返回。在等待操作系统建立连接时，程序可以做其它操作。不过在实际使用连接之前，必须调用finishConnect()方法：

```java
public abstract boolean finishConnect() throws IOException
```

这对非阻塞模式是必须的。对于阻塞通道方式，这个方法立即返回true。

如果连接现在可以使用，该方法返回true。如果连接还没有建立，返回false；如果连接无法建立，抛出异常。

如果程序要检查连接是否完成，可以调用以下两个方法：

```java
public abstract boolean isConnected();
public abstract boolean isConnectionPending();
```

如果连接打开，前一个返回true；如果连接仍在建立但尚未打开，后一个返回true。

**读取**

为了读取SocketChannel，首先需要一个ByteBuffer，通道可以在其中存储数据。然后将这个ByteBuffer传给read()方法：

```java
public abstract int read(ByteBuffer dst) throws IOException;
```

通道会用尽可能多的数据填充缓冲区，然后返回放入的字节数。如果遇到流末尾，通道会用所有剩余的字节填充缓冲区，而且在下一次调用read()时返回-1。如果通道是阻塞的，这个方法将至少读取一个字节，或者返回-1，也可能抛出一个异常。但如果通道是非阻塞的，可能返回0。

因为数据将存储在缓冲区的当前位置，这个位置随着数据的增加自动更新，不需要人为维护：

```java
while(buffer.hasRemaining() && channel.read(buffer)!=-1);
```

如果从一个源能够填充多个缓冲区，这种称为散布（scatter）。下面两个方法接受一个ByteBuffer对象数组作为参数，按顺序填充数组中的ByteBuffer

```java
public final long read(ByteBuffer[] dsts) throws IOException;
public abstract long read(ByteBuffer[] dsts, int offset, int length) throws IOException;
```

第一个方法填充所有缓冲区，第二种方法填充一个区间的缓冲区。

**写入**

要想进行写入，只需填充一个ByteBuffer，然后传入写入方法，这个方法在把数据复制到输出时将缓冲区排空，这与读取过程正好相反。

```java
public abstract int write(ByteBuffer src) throws IOException;
```

与读取一样，如果通道是非阻塞的，这个方法不能保证会写入缓冲区的全部内容。

```java
while(buffer.hasRemaining() && channel.write(buffer)!=-1);
```

将多个缓冲区的数据写入到一个Scoket，这称为聚集（gather）。

```java
public final long write(ByteBuffer[] srcs) throws IOException；
public abstract long write(ByteBuffer[] srcs, int offset, int length) throws IOException;
```

**关闭**

关闭方法

```java
public void close() throws IOException;
```

如果通道已经关闭，再进行关闭将没有任何效果。如果试图读写一个已经关闭的通道，将抛出异常。如果不确定通道是否已经关闭，可以用isOpen()检查：

```java
public boolean isOpen()
```

通道已经关闭时，返回false，如果是打开的，返回true



### 11.3.2 ServerSocketChannel

ServerSocketChannel只有一个目的：接受入站连接。你无法连接，写入或连接ServerSocketChannel。它只能接受一个入站连接。这个类声明了4个方法以及从超类中继承了几个方法，主要与向Selector注册来得到入站连接通知有关。

**创建ServerSocketChannel**

使用静态工厂方法 ServerSocketChannel.open() 创建该类对象，该方法并不打开一个新的Socket，只是创建了一个对象。使用之前，调用socket()来获得相应的对等端(peer)ServerSocket。可以使用任何ServerSocket的各种设置方法随意配置任何服务器选项。然后，对于希望绑定的端口，将这个ServerSocket连接到对应该端口的SocketAddress。下面是一个在80端口上打开ServerSocketChannel的例子：

```java
try {
    ServerSocketChannel server = ServerSocketChannel.open();
    ServerSocket socket = server.socket();
    SocketAddress address = new InetSocketAddress(80);//设定80端口
    socket.bind(address);
    //server.bind(address); java7之后有了自己的bind方法
} catch (IOException e) {
    e.printStackTrace();
}
```

**接受连接**

一旦打开并绑定了ServerSocketChannel对象，accept()方法就可以监听入站连接了：

```java
public abstract SocketChannel accept() throws IOException
```

accept()可以在阻塞或非阻塞模式下操作。在阻塞模式下，accept()方法等待入站连接，然后它接受一个连接，并返回连接到远程客户端的一个SocketChannel对象。在建立连接之前，线程无法进行任何操作。这种策略适用于立即响应每一个请求的简单服务器。

ServerSocketChannel()还可以在非阻塞模式下进行，这时如果没有入站连接，accept()会返回null。非阻塞模式更适用于需要为每个连接完成大量工作的服务器，这样就可以并行地处理多个请求。非阻塞模式通常与Selector结合使用。为了使ServerSocketChannel处于非阻塞模式，需要向其configureBlocking()方法传入false。

accept()方法声明为出现错误时抛出一个IOException，有几个子类来查看更详细的信息：

- ClosedChannelException：关闭后无法重新打开一个ServerSocketChannel
- AsynchronousCloseException：一个阻塞ServerSocketChannel在等待时，另一个线程中断了该线程。
- NotYetBoundException：调用了open()，但在调用accept()之前没有将ServerSocketChannel的对等端(peer)ServerSocket与地址绑定。这是一个运行时异常
- SercurityException：安全管理器拒绝这个应用程序绑定所请求的端口。

### 11.3.3 Channels类

Channels类是一个简单的工具类，可以将传统的基于IO的流、阅读器和书写器包装在通道中，也可以从通道转换为基于IO的流、阅读器和书写器。如果出于性能考虑，希望在程序中的一部分使用新IO模型，但同时仍要与处理流的传统API交互，这个类很有用。

他有一些方法可以将流转换为通道，还有一些将通道转换为流。

### 11.3.4 异步通道（Java 7）

Java 7 引入了 AsynchronousSocketChannel 和 AsynchronousServerSocketChannel 类。这两个类与 SocketChannel 和 ServerSocketChannel 不同的是，读写异步通道会立即返回，甚至IO完成之前就会返回。所读写的数据会由一个 Future 或 CompletionHandler 进一步处理。connect()和accept()方法也会异步执行，并返回 Future，这里不是用Selector。

下面一个例子，假设一个程序需要在启动时完成大量初始化工作，另外有一些涉及网络连接的操作，每个操作将花费几秒钟时间。可以并行开始多个异步操作，然后完成你的本地初始化，再请求这些网络操作的结果：

```java
SocketAddress address = new InetSocketAddress("localhost",80);
AsynchronousSocketChannel client = AsynchronousSocketChannel.open();
Future<Void> connected = client.connect(address);

ByteBuffer buffer = ByteBuffer.allocate(74);//缓冲区大小
//等待连接完成
connected.get();

//从连接读取
Future<Integer> future = client.read(buffer);

//其他工作

//等待读取完成
future.get();

//回绕并清空缓冲区
buffer.flip();
WritableByteChannel out = Channels.newChannel(System.out);
out.write(buffer);
```

这个方法的好处是，网络连接在并行运行，与此同时程序可以做其他事情。准备好处理来自网络的数据时，会停下来，通过调用Future.get()等待这些数据。

### 11.3.5 Socket选项（Java 7）

通道类分别有3个方法来获取、设置和列出所支持的选项：

```java
<T> T getOption(SocketOption<T> name) throws IOException
<T> NetworkChannel setOption(SocketOption<T> name, T value) throws IOException
Set<SocketOption<?>> supportedOptions()
```

SocketOption类是一个泛型类，制定了各个选项的名字和类型，类型参数<T\>确定这个选项是一个boolean、Integer还是NetworkInterface。StandardSocketOptions类为Java能识别的11个选项提供了对应的常量

- SocketOption<NetworkInterface\> StandardSocketOptions.IP_MULTICAST_IF
- SocketOption<Boolean\> StandardSocketOptions.IP_MULTICAST_LOOP
- SocketOption<Integer\> StandardSocketOptions.IP_MULTICAST_TTL
- SocketOption<Integer\> StandardSocketOptions.IP_TOS
- SocketOption<Boolean\> StandardSocketOptions.SO_BROADCAST
- SocketOption<Boolean\> StandardSocketOptions.SO_KEEPLIVE
- SocketOption<Integer\> StandardSocketOptions.SO_LINGER
- SocketOption<Integer\> StandardSocketOptions.SO_RCVBUF
- SocketOption<Boolean\> StandardSocketOptions.SO_REUSEADDR
- SocketOption<Integer\> StandardSocketOptions.SO_SNDBUF
- SocketOption<Boolean\> StandardSocketOptions.TCP_NODELAY

下面是一个设置示例

```java
NetworkChannel channel = SocketChannel.open();
channel.setOption(StandardSocketOptions.SO_LINGER,240);
```

## 11.4 就绪选择

新IO API的第二部分是就绪选择，即能够选择读写时不阻塞的Socket。

为了完成就绪选择，要将不同的通道注册到一个Selector对象，每个通道分配一个SelectionKey，然后程序可以询问这个Selector对象，那些通道已经准备就绪可以无阻塞地完成希望完成的操作，可以请求Selector对象返回相应键集合。

[9.1 使用ServerSocket 一节中的 EchoServer程序是该两个类的使用](# 9.1 使用ServerSocket)

**Selector类**

Selector唯一的构造函数是一个保护类型的方法。一般情况下使用静态工厂方法Selector.open()来创建新的选择器：

```java
public static Selector open() throws IOException
```

然后将Selector类注册到Channel类组，register()放在在SelectableChannel类中声明。

```java
public final SelectionKey register(Selector sel, int ops) throws ClosedChannelException
public final SelectionKey register(Selector sel, int ops,Object att) throws ClosedChannelException
```

第一个参数是通道要向哪个Selector进行注册；第二个参数是SelectionKey类中的一个命名常量，标识通道所注册的操作。

SelectionKey定义了4个命名常量，用于选择操作类型：

- SelectionKey.OP_ACCEPT
- SelectionKey.OP_CONNECT
- SelectionKey.OP_READ
- SelectionKey.OP_WRITE

如果需要用多个常量设置，使用“或(|)”将不同的操作合并。

第三个参数是可选的，这个对象通常用于存储连接的状态。

不同的通道注册到选择器后，就可以随时查询选择器，找出哪些通道已经准备好可以进行处理。通道可能已经准备好完成某些操作，但对另一些操作还没有准备好。例如：可能一个通道已经准备就绪可以读取，但还不能写入。

```java
public abstract int selectNow() throws IOException
```

另外两个方法是阻塞的：

```java
public abstract int select() throws IOException
public abstract int select(long timeout) throws IOException
```

第一个方法在返回前会等待，直到至少有一个注册的通道准备好就可以进行处理。

第二个方法返回前只等待不超过timeout毫秒。

当直到有通道已经准备好处理时，可以使用SelectedKeys()方法获取就绪通道：

```java
public abstract Set<SelectionKey> selectedKeys()
```

迭代处理返回的集合时，要依次处理各个SelectionKey。还可以从迭代器中删除键，告诉选择器这个键已经得到处理。否则选择器在以后循环时还会一直通知你还有这个键。

最后，当准备关闭服务器或不再需要选择器时，应当将它关闭：

```java
public abstract void close() throws IOException
```

这个步骤会释放与选择器有关的所有资源，它取消了向选择器注册的所有键，并中断被这个选择器的某个选择方法所阻塞的线程。

**SelectionKey类**

SelectionKey对象相当于通道的指针。它们还可以保存一个对象附件，一般会存储这个通道上的连接的状态。

将一个通道注册到一个选择器时(register())，会返回一个SelectionKey对象。selectedKey()方法可以再Set中再次返回相同的对象。一个通道可以注册到多个选择器。

当从所选择的键集合中获取一个SelectionKey时，通常首先要测试这些键能进行哪些操作。有以下4种可能：

```java
public final boolean isAcceptable()
public final boolean isConnectable()
public final boolean isReadable()
public final boolean isWritable()
```

这个测试并不总是必须的。有些情况下，选择器只测试一种可能性，也只返回完成这种操作的键。但如果选择器确实要测试多种就绪状态，就要在操作前先测试通道对于哪个操作进入就绪状态。也有可能通道准备好就可以完成多个操作。

一旦了解了与键关联的通道准备好完成何种操作，就可以用channel()方法来获取这个通道：

```java
public abstract SelectableChannel channel()
```

如果再保存状态信息的SelectionKey存储了一个对象，就可以用attachment()方法获取该对象：

```java
public final Object atachment()
```

最后，如果结束使用连接，就要撤销其SelectionKey对象的注册，这样选择器就不会浪费资源再去查询它是否准备就绪。可以使用这个键的cancel()方法来撤销注册：

```java
public abstract void cancel()
```

不过，只有在未关闭通道时这个步骤才有必要。如果关闭通道，会自动在所有选择器中撤销对应这个通道的所有键的注册。类似的，关闭选择器会使这个选择器中的所有键都失效。

# 12 UDP
