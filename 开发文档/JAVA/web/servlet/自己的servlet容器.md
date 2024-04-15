# 1 简单的servlet容器

tomcat打印日志乱码问题解决，

将logging.properties里的java.util.logging.ConsoleHandler.encoding = UTF-8这句话注释掉

## 1.1 Catalina

```java
package cn.edu.xidian.mytomcat;

import cn.edu.xidian.mytomcat.servlet.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Catalina {

    static{
        //访问localhost:2387/index.html即可进入IndexServlet这个Servlet
        Container.SERVLET_CONTAINER.put("index.html", new IndexServlet());
    }

    public static void main(String[] args) throws IOException {
        ServerSocket server =  new ServerSocket();
        server.bind(new InetSocketAddress(2387));

        while(true){
            Socket accept = server.accept();
            InputStream in = accept.getInputStream();
            StringBuilder sb = new StringBuilder();
            int len;
            byte[] buf = new byte[512];
            while ((len = in.read(buf))!=-1){
                sb.append(new String(buf,0,len));
                if (len<512){
                    accept.shutdownInput();
                }
            }
            //构建一个请求对象
            Request request = Request.buildRequest(sb.toString());
            String url = request.getUrl();
            Servlet servlet = Container.SERVLET_CONTAINER.get(url);

            //构建一个响应
            Response response = new Response();
            response.setOs(accept.getOutputStream());


            servlet.service(request,response);
        }

    }
}
```

## 1.2 Container

```java
package cn.edu.xidian.mytomcat;

import cn.edu.xidian.mytomcat.servlet.IndexServlet;

import java.util.*;

public class Container {
    public final static Map<String, IndexServlet> SERVLET_CONTAINER = new HashMap(8);


}
```

## 1.3 Servlet

```java
package cn.edu.xidian.mytomcat;

public interface Servlet {

    /**
     * 初始化方法
     */
    void init();

    /**
     * 处理请求的服务
     * @param request
     * @param response
     */
    void service(Request request,Response response);

    /**
     * 销毁方法
     */
    void destroy();

}
```

## 1.4 IndexServlet

```java
package cn.edu.xidian.mytomcat.servlet;

import cn.edu.xidian.mytomcat.Request;
import cn.edu.xidian.mytomcat.Response;
import cn.edu.xidian.mytomcat.Servlet;


public class IndexServlet implements Servlet {

    public IndexServlet(){
        init();
    }

    @Override
    public void init() {

    }

    @Override
    public void service(Request request, Response response) {
        System.out.println("这是首页");
        response.write("<h1>Hello World!</h1>");
    }

    @Override
    public void destroy() {

    }
}
```

## 1.5 Request, Response

```java
package cn.edu.xidian.mytomcat;

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
    private OutputStream os;

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

    public OutputStream getOs() {
        return os;
    }

    public void setOs(OutputStream os) {
        this.os = os;
    }

    public void addHeader(String key, String value){
        this.getHeaders().put(key,value);
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
    public void write(){
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

    public void write(String content){
        this.setData(content);
        this.write();
    }
}
```

```java
package cn.edu.xidian.mytomcat;

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
        request.setUrl(lines[1].substring(1));
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

# 2 尝试多servlet

`Catalina`中将能够找到的`servlet`写死了，现在更新，加一个`web.properties`的配置文件

```
index = cn.edu.xidian.mytomcat.servlet.IndexServlet
home = cn.edu.xidian.mytomcat.servlet.HomeServlet
order = cn.edu.xidian.mytomcat.servlet.OrderServlet
```

## 2.1 Catalina

```java
package cn.edu.xidian.mytomcat;

import cn.edu.xidian.mytomcat.servlet.IndexServlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Catalina {

    static{
        try {
            Container.WEB_CONFIGURE.load(Catalina.class.getClassLoader().getResourceAsStream("web.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ServerSocket server =  new ServerSocket();
        server.bind(new InetSocketAddress(2387));

        while(true){
            Socket accept = server.accept();
            InputStream in = accept.getInputStream();
            StringBuilder sb = new StringBuilder();
            int len;
            byte[] buf = new byte[512];
            while ((len = in.read(buf))!=-1){
                sb.append(new String(buf,0,len));
                if (len<512){
                    accept.shutdownInput();
                }
            }
            //构建一个请求对象
            Request request = Request.buildRequest(sb.toString());
            String url = request.getUrl();
            if("favicon.ico".equals(url)){
                continue;
            }
            //容器中查找servlet。第一次是空的时候去if里创建，之后直接从Container中就能拿到，体现了生命周期的init
            Servlet servlet = Container.SERVLET_CONTAINER.get(url);
            //图标请求会导致报错
            if(servlet == null){
                //如果容器里没有，去配置文件中找
                String fullClassName = Container.WEB_CONFIGURE.getProperty(url);//全类名
                if(!"".equals(fullClassName)&&fullClassName!=null){
                    //创建一个servlet并放入容器
                    servlet = (Servlet) Class.forName(fullClassName).newInstance();
                }else{
                    servlet = new IndexServlet();
                }
                Container.SERVLET_CONTAINER.put(url, servlet);
            }

            //构建一个响应
            Response response = new Response();
            response.setOs(accept.getOutputStream());


            servlet.service(request,response);
        }

    }
}

```

## 2.2 Container

```java
package cn.edu.xidian.mytomcat;

import java.util.*;

public class Container {
    public final static Properties WEB_CONFIGURE = new Properties();
    public final static Map<String, Servlet> SERVLET_CONTAINER = new HashMap(8);


}
```

## 2.3 404

```java
package cn.edu.xidian.mytomcat.servlet;

import cn.edu.xidian.mytomcat.Request;
import cn.edu.xidian.mytomcat.Response;
import cn.edu.xidian.mytomcat.Servlet;

public class NotFound implements Servlet {
    public NotFound(){
        init();
    }

    @Override
    public void init() {
        System.out.println("404 Not Found");
    }

    @Override
    public void service(Request request, Response response) {
        response.write("<h1>404 Not Found</h1>");
    }

    @Override
    public void destroy() {

    }
}
```

## 2.4 增加获取ip

```java
//在request里增加remoteHost字段保存fang'we 
request.setRemoteHost(accept.getInetAddress().getHostAddress());
```

