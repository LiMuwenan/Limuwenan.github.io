# 1 一个简单的tcp服务器通讯

代码路径 %\http\\%

## 1.1 单次监听与发送

```java
//启动服务器之后，如果用浏览器访问该地址，服务器会打印出来请求http头和内容
package cn.edu.xidian;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        //创建一个服务器
        ServerSocket server = new ServerSocket();
        //绑定端口
        server.bind(new InetSocketAddress(2837));
        System.out.println("服务器已启动，监听在2837端口：\n");
        //进行监听，这里是个阻塞方法
        Socket accept = server.accept();

        //拿到数据进行读取
        InputStream inputStream = accept.getInputStream();
        //这里定义读取1MB数据
        byte[] buf = new byte[1024];
//        int len = inputStream.read(buf);
        //读取任意长度
        int len;
        while ((len = inputStream.read(buf)) != -1)
            System.out.println(new String(buf, 0, len));
    }

}

```

```java
package cn.edu.xidian;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        //不绑定端口就随机生成
        Socket socket = new Socket();
        //连接到一个地址
        socket.connect(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 2837));
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.next();
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        socket.close();
    }
}

```

## 1.2 持久监听与发送

```java
package cn.edu.xidian;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception{
        //创建一个服务器
        ServerSocket server = new ServerSocket();
        //绑定端口
        server.bind(new InetSocketAddress(2837));
        System.out.println("服务器已启动，监听在2837端口：\n");
        //进行监听，这里是个阻塞方法
        while(true){//去掉while形成单次监听
            Socket accept = server.accept();

            //拿到数据进行读取
            InputStream inputStream  = accept.getInputStream();
            //这里定义读取1MB数据
            byte[] buf = new byte[1024];
//        int len = inputStream.read(buf);
            //读取任意长度
            int len;
            while ((len = inputStream.read(buf)) != -1)
                System.out.println(new String(buf,0,len));
        }

    }
}

```

```java
package cn.edu.xidian;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        //不绑定端口就随机生成
        Socket socket = new Socket();
        //连接到一个地址
        socket.connect(new InetSocketAddress(InetAddress.getByName("127.0.0.1"),2837));
        while (true) {//去掉while形成单次发送
            Scanner scanner = new Scanner(System.in);
            String msg = scanner.next();
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
//            outputStream.close();
//            socket.close();
        }
    }
}

```

## 1.3 多线程监听

```java
package cn.edu.xidian;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        //创建一个服务器
        ServerSocket server = new ServerSocket();
        //绑定端口
        server.bind(new InetSocketAddress(2837));
        System.out.println("服务器已启动，监听在2837端口：\n");

        //多线程监听
        while(true){
            Socket accept = server.accept();
            //启动线程
            new MsgHandler(accept).start();
        }
    }

}

```

```java
package cn.edu.xidian;

import java.io.InputStream;
import java.net.Socket;

public class MsgHandler extends Thread{
    Socket accept = null;
    public MsgHandler(Socket accept){
        this.accept = accept;
    }

    @Override
    public void run(){
        InputStream inputStream = null;
        try{
            //拿到数据进行读取
            inputStream = accept.getInputStream();
            //这里定义读取1MB数据
            byte[] buf = new byte[1024];
//        int len = inputStream.read(buf);
            //读取任意长度
            int len;
            while ((len = inputStream.read(buf)) != -1)
                System.out.println(new String(buf, 0, len));
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try{
                    inputStream.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

    }
}
```

# 2 浏览器发送请求并响应

```java
//浏览器访问服务器，服务器做出响应
package cn.edu.xidian.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Http {
    public static void main(String[] args) throws Exception{
        //创建服务器
        ServerSocket server = new ServerSocket();
        //绑定端口
        server.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"),2387));
        //监听
        Socket accept = server.accept();
        InputStream inputStream = accept.getInputStream();
        int len;
        byte[] buf = new byte[1024];
        len = inputStream.read(buf);
//        while((len = inputStream.read(buf)) != -1){
            System.out.println(new String(buf,0,len));
//        }
        //做出响应
        OutputStream outputStream = accept.getOutputStream();
        //响应格式要求
        //这里是标准的http协议头
        outputStream.write("http1.1 200 ok\r\nContent-type:text/html\r\nContent-length:14\r\n\r\n<h1>Hello</h1>".getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

        accept.close();

    }
}
```

## 2.1 封装了http头

```java
package cn.edu.xidian.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Http {
    public static void main(String[] args) throws Exception{
        //创建服务器
        ServerSocket server = new ServerSocket();
        //绑定端口
        server.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"),2387));
        //监听
        while(true)
        {
            Socket accept = server.accept();
            InputStream inputStream = accept.getInputStream();
            int len;
            byte[] buf = new byte[1024];
            len = inputStream.read(buf);
//        while((len = inputStream.read(buf)) != -1){
            System.out.println(new String(buf,0,len));
//        }
            //做出响应
            OutputStream outputStream = accept.getOutputStream();
            //响应格式要求
            outputStream.write(getResponse("<h1>Hello</h1>"));
            outputStream.flush();
            outputStream.close();

            accept.close();
        }
    }

    //用来封装报文
    public static byte[] getResponse(String html){
        return ("http1.1 200 ok\r\nContent-type:text/html\r\nContent-length:"+String.valueOf(html.length()) +"\r\n\r\n" + html).getBytes(StandardCharsets.UTF_8);
    }
}

```

## 2.2 返回一个html文件

```java
package cn.edu.xidian.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Http {
    public static void main(String[] args) throws Exception{
        //创建服务器
        ServerSocket server = new ServerSocket();
        //绑定端口
        server.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"),2387));
        //监听
        while(true)
        {
            Socket accept = server.accept();
            InputStream inputStream = accept.getInputStream();
            int len;
            byte[] buf = new byte[1024];
            len = inputStream.read(buf);
//        while((len = inputStream.read(buf)) != -1){
            System.out.println(new String(buf,0,len));
//        }
            //做出响应
            OutputStream outputStream = accept.getOutputStream();
            //响应格式要求
            outputStream.write(getResponse(getPage("index.html")));
            outputStream.flush();
            outputStream.close();

            accept.close();
        }
    }

    //用来封装报文
    public static byte[] getResponse(String html){
        return ("http1.1 200 ok\r\nContent-type:text/html\r\nContent-length:"+String.valueOf(html.length()) +"\r\n\r\n" + html).getBytes(StandardCharsets.UTF_8);
    }

    //读取html文件
    public static String getPage(String filePath) throws Exception{
        StringBuilder sb=  new StringBuilder();

        //源文件放在src下，经过编译之后，这个会自己到out/http/下找这个文件
        InputStream resource = Http.class.getClassLoader().getResourceAsStream("index.html");
        byte[] buf = new byte[1024];
        int len;
        while((len = resource.read(buf))!= -1)
        {
            sb.append(new String(buf,0,len));
        }

        return sb.toString();
    }
}

```

```java
//进行任意html返回。上面的那个写死了文件名
package cn.edu.xidian.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Http {
    public static void main(String[] args) throws Exception {
        //创建服务器
        ServerSocket server = new ServerSocket();
        //绑定端口
        server.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 2387));
        System.out.println("开启监听：\n");
        //监听
        while (true) {
            StringBuilder sb = new StringBuilder();

            Socket accept = server.accept();
            InputStream in = accept.getInputStream();
            int len;
            byte[] buf = new byte[512];
            while ((len = in.read(buf)) != -1) {
//                System.out.println(new String(buf, 0, len));
                sb.append(new String(buf,0,len));
                if (len < 512) {
                    accept.shutdownInput();
                }
            }

            //使用正则进行分割，分割后相当于数组，通过下标访问
            String url = sb.toString().split("\r\n")[0].split(" ")[1].substring(1);
            //忽略图标请求
            if("favicon.ico".equals(url)||"".equals(url))
            {
                continue;
            }
            //定义一个响应
            OutputStream out = accept.getOutputStream();
            out.write(getResponse(getPage(url)));
            out.flush();
            out.close();
            accept.close();
        }
    }

    //用来封装报文
    //这里字节长度和文本长度不一样，报文是按字节算的。
   	//utf-8中，中文占3个字节，直接使用html.length()在读取英文时没有问题，中文会出现问题
    public static byte[] getResponse (String html){
        return ("http/1.1 200 ok\r\nContent-type:text/html;charset=utf-8\r\nContent-length:" + html.getBytes(StandardCharsets.UTF_8).length + "\r\n\r\n" + html).getBytes(StandardCharsets.UTF_8);
    }

    //读取html文件
    public static String getPage (String filePath) throws Exception {
        StringBuilder sb = new StringBuilder();

        //源文件放在src下，经过编译之后，这个会自己到out/http/下找这个文件
        InputStream resource = Http.class.getClassLoader().getResourceAsStream(filePath);
        //没找到需要的页面
        if(resource == null)
        {
            resource = Http.class.getClassLoader().getResourceAsStream("404.html");
        }
        byte[] buf = new byte[1024];
        int len;
        while ((len = resource.read(buf)) != -1) {
            sb.append(new String(buf, 0, len));
        }

        return sb.toString();
    }
}

```

## 2.3 形成一个动态的页面

```java
//形成一个动态页面
//获取到html内容后，将其中的固定内容替换成动态的内容，这个动态的内容可以从数据库等位置获取
String name = "李根";
String result = sb.toString().replace("{{ name }}",name);
```

 # 3 对项目进行打包发布

> 1. Project Structure->Project Settings->Artifacts->add->jar->from modules
>
> 2. 选择需要打包发布的程序
> 3. Build->build artifacts->build
> 4. java -jar xxx.jar即可运行jar包

## 3.1 对html页面路径进行更改

在jar包中，我们不希望包含静态页面，通常将jar包与`/pages`放在同级目录下，让jar包中的程序去`/pages`下寻找页面

```java
    //读取html文件
    public static String getPage (String filePath) throws Exception {
        StringBuilder sb = new StringBuilder();

        //源文件放在src下，经过编译之后，这个会自己到out/http/下找这个文件
        //寻找父路径
        String path = Http.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        path = path.substring(0,path.lastIndexOf("/")) + "/pages/";
        InputStream resource = new FileInputStream(path + filePath);
        //没找到需要的页面
        if(resource == null)
        {
            resource = Http.class.getClassLoader().getResourceAsStream("404.html");
        }
        byte[] buf = new byte[1024];
        int len;
        while ((len = resource.read(buf)) != -1) {
            sb.append(new String(buf, 0, len));
        }

        //形成一个动态页面
        String name = "李根";
        String result = sb.toString().replace("{{ name }}",name);

        return result;
    }
```

# 4 多线程进行监听

因为`server.accept()`方法是一个阻塞方法，在这里影响程序的运行，所以采用多线程就行优化。

## 4.1 多线程

```java
package cn.edu.xidian.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Http {
    public static void main(String[] args) throws Exception {
        //创建服务器
        ServerSocket server = new ServerSocket();
        //绑定端口
        server.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 2387));
        System.out.println("开启监听：\n");
        //监听
        int cnt = 1;
        while (true) {
            StringBuilder sb = new StringBuilder();

            Socket accept = server.accept();
            //每有一个服务另起一个线程
            new MyTask(accept).start();
        }
    }


}
```

```java
package cn.edu.xidian.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MyTask extends Thread{

    Socket accept = null;
    static int cnt = 1;

    public MyTask(Socket accept){
        this.accept  =accept;
    }

    @Override
    public void run(){
        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        OutputStream out = null;
        try{
            in = accept.getInputStream();
            int len;
            byte[] buf = new byte[512];
            while ((len = in.read(buf)) != -1) {
//                System.out.println(new String(buf, 0, len));
                sb.append(new String(buf,0,len));
                if (len < 512) {
                    accept.shutdownInput();
                }
            }

            //使用正则进行分割，分割后相当于数组，通过下标访问
            String url = sb.toString().split("\r\n")[0].split(" ")[1].substring(1);
            //忽略图标请求
            if("favicon.ico".equals(url)||"".equals(url))
            {
                return;
            }
            //定义一个响应
            out = accept.getOutputStream();
            if(out!=null)
            {
                System.out.println("对第" + cnt++ + "次请求进行响应!");
            }
            out.write(HttpUntils.getResponse(HttpUntils.getPage(url)));

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                out.flush();
                if(out!=null){
                    out.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
```

将两个静态方法封装成一个工具类

```java
package cn.edu.xidian.http;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HttpUntils {
    //用来封装报文
    public static byte[] getResponse (String html){
        return ("http/1.1 200 ok\r\nContent-type:text/html;charset=utf-8\r\nContent-length:" + html.getBytes(StandardCharsets.UTF_8).length + "\r\n\r\n" + html).getBytes(StandardCharsets.UTF_8);
    }

    //读取html文件
    public static String getPage (String filePath) throws Exception {
        StringBuilder sb = new StringBuilder();

        //源文件放在src下，经过编译之后，这个会自己到out/http/下找这个文件
        //非打包
        InputStream resource = Http.class.getClassLoader().getResourceAsStream(filePath);
        //寻找父路径
//        String path = Http.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        path = path.substring(0,path.lastIndexOf("/")) + "/pages/";
//        InputStream resource = new FileInputStream(path + filePath);
        //没找到需要的页面
        if(resource == null)
        {
            resource = Http.class.getClassLoader().getResourceAsStream("404.html");
        }
        byte[] buf = new byte[1024];
        int len;
        while ((len = resource.read(buf)) != -1) {
            sb.append(new String(buf, 0, len));
        }

        //形成一个动态页面
        String name = "李根";
        String result = sb.toString().replace("{{ name }}",name);

        return result;
    }
}

```

## 4.2 线程池优化

```java
//这个是Http.java中的while线程循环
//其中MyTask改为实现Runnable接口，之前是继承Thread
while (true) {
            StringBuilder sb = new StringBuilder();
            Socket accept = server.accept();
            //定义一个线程池
            ExecutorService executorService = Executors.newFixedThreadPool(20);
            //每有一个服务另起一个线程
            executorService.submit(new MyTask(accept));
        }
```

# 5 封装请求与响应

```java
package cn.edu.xidian.http;

import java.io.InputStream;
import java.util.*;

public class Request {
    private String type;//类型
    private String url;//链接地址
    private String protocal;//协议

    private String contentType;//请求头类型

    private Map<String,String> headers = new HashMap<>(8);

    private Map<String,String> attributes = new HashMap<>(8);

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProtocal() {
        return protocal;
    }

    public void setProtocal(String protocal) {
        this.protocal = protocal;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "Request{" +
                "type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", protocal='" + protocal + '\'' +
                ", contentType='" + contentType + '\'' +
                ", headers=" + headers +
                ", attributes=" + attributes +
                '}';
    }

    public static Request buildRequest(String requestStr) {
        Request request = new Request();
        //split的长度为1说明只有请求头，为2说明有请求体
        String[] split = requestStr.split("\r\n\r\n");
        //请求行 和 请求头
        String[] lineAndHeader = split[0].split("\r\n");
        String[] lines = lineAndHeader[0].split(" ");
        request.setType(lines[0]);
        request.setUrl(lines[1].subString(1));
        request.setProtocal(lines[2]);

        for(int i=1;i<lines.length;++i){
            String[] header = lineAndHeader[i].split(": ");
            request.getHeaders().put(header[0].trim().toLowerCase(),header[1].trim().toLowerCase());
        }

        request.setContentType(request.getHeaders().get("content-type"));


        //处理请求体
        if(split.length == 2)
        {

        }
        return request;
    }
}

```

```java
package cn.edu.xidian.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Response {

    private String protocol = "HTTP/1.1";
    private Integer code = 200;
    private String msg = "OK";
    private String contentType = "text/html;charset=utf-8";
    private String contentLength;

    private Map<String,String> headers = new HashMap(){{
        put("content-type",contentType);
    }};

    private String data;

    public Response(){}
    public Response(String protocol, Integer code, String msg) {
        this.protocol = protocol;
        this.code = code;
        this.msg = msg;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        this.setContentLength(data.getBytes().length+"");
    }

    public String getContentType() {
        return getHeaders().get("content-type");
    }

    public void setContentType(String contentType) {
        this.getHeaders().put("content-type",contentType);
    }

    public String getContentLength() {
        return getHeaders().get("content-length");
    }

    public void setContentLength(String contentLength) {
        this.getHeaders().put("content-length",this.data.getBytes().length+"");
    }


    //组合响应
    public String buildResponse(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.getProtocol()).append(" ")
                .append(this.getCode()).append(" ")
                .append(this.getMsg()).append("\r\n");
        for(Map.Entry<String,String> entry : this.getHeaders().entrySet()){
            sb.append(entry.getKey()).append(" ").append(entry.getValue()).append("\r\n");
        }
        sb.append("\r\n").append(this.getData());

        return sb.toString();
    }

    //输出响应
    public void write(OutputStream os){
        try {
            os.write(buildResponse().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(os!=null)
            {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

```

## 5.1 改造工具类

```java
package cn.edu.xidian.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Http {
    public static void main(String[] args) throws Exception {
        //创建服务器
        ServerSocket server = new ServerSocket();
        //绑定端口
        server.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 2387));
        System.out.println("开启监听：\n");
        //监听
        int cnt = 1;
        while (true) {
            StringBuilder sb = new StringBuilder();
            Socket accept = server.accept();
            //定义一个线程池
            ExecutorService executorService = Executors.newFixedThreadPool(20);
            //每有一个服务另起一个线程
            executorService.submit(new MyTask(accept));
        }
    }


}

```

```java
package cn.edu.xidian.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MyTask implements Runnable{

    Socket accept = null;
    static int cnt = 1;

    public MyTask(Socket accept){
        this.accept  =accept;
    }

    @Override
    public void run(){
        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        OutputStream out = null;
        try{
            in = accept.getInputStream();
            int len;
            byte[] buf = new byte[512];
            while ((len = in.read(buf)) != -1) {
//                System.out.println(new String(buf, 0, len));
                sb.append(new String(buf,0,len));
                if (len < 512) {
                    accept.shutdownInput();
                }
            }

            //构建一个请求对象
            Request request = Request.buildRequest(sb.toString());

            //使用正则进行分割，分割后相当于数组，通过下标访问
            String url = request.getUrl();
            //忽略图标请求
            if("favicon.ico".equals(url)||"".equals(url))
            {
                return;
            }
            //拿到一个输出流
            out = accept.getOutputStream();
            if(out!=null)
            {
                System.out.println("对第" + cnt++ + "次请求进行响应!");
            }

            Response response = new Response();
            response.setData("<h1>131</h1>");
            response.write(out);

        }catch(Exception e){
            e.printStackTrace();
        }finally {

        }
    }
}

```

```java
package cn.edu.xidian.http;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HttpUntils {

//    //用来封装报文
//    public static byte[] getResponse (String html){
//        return ("http/1.1 200 ok\r\nContent-type:text/html;charset=utf-8\r\nContent-length:" + html.getBytes(StandardCharsets.UTF_8).length + "\r\n\r\n" + html).getBytes(StandardCharsets.UTF_8);
//    }

    //读取html文件
    public static String getPage (String filePath) throws Exception {
        StringBuilder sb = new StringBuilder();

        //源文件放在src下，经过编译之后，这个会自己到out/http/下找这个文件
        //非打包
        InputStream resource = Http.class.getClassLoader().getResourceAsStream(filePath);
        //寻找父路径
//        String path = Http.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        path = path.substring(0,path.lastIndexOf("/")) + "/pages/";
//        InputStream resource = new FileInputStream(path + filePath);
        //没找到需要的页面
        if(resource == null)
        {
            resource = Http.class.getClassLoader().getResourceAsStream("404.html");
        }
        byte[] buf = new byte[1024];
        int len;
        while ((len = resource.read(buf)) != -1) {
            sb.append(new String(buf, 0, len));
        }

        //形成一个动态页面
        String name = "李根";
        String result = sb.toString().replace("{{ name }}",name);

        return result;
    }
}

```

