[TOC]



# 1 Servlet简介

servlet配置：

idea中可以配置默认打开的路径，作为项目的根目录

根目录即/web目录，需要访问某个文件夹下的html页面， 即在路径中加上子文件夹



Servlet类统一放在项目的Controller包下

## 1.1 Servlet是什么

Java Servlet 是运行在 Web 服务器或应用服务器上的程序，它是作为来自 Web 浏览器或其他 HTTP 客户端的请求和 HTTP 服务器上的数据库或应用程序之间的中间层。

使用 Servlet，您可以收集来自网页表单的用户输入，呈现来自数据库或者其他源的记录，还可以动态创建网页。

Java Servlet 通常情况下与使用 CGI（Common Gateway Interface，公共网关接口）实现的程序可以达到异曲同工的效果。但是相比于 CGI，Servlet 有以下几点优势：

- 性能明显更好。
- Servlet 在 Web 服务器的地址空间内执行。这样它就没有必要再创建一个单独的进程来处理每个客户端请求。
- Servlet 是独立于平台的，因为它们是用 Java 编写的。
- 服务器上的 Java 安全管理器执行了一系列限制，以保护服务器计算机上的资源。因此，Servlet 是可信的。
- Java 类库的全部功能对 Servlet 来说都是可用的。它可以通过 sockets 和 RMI 机制与 applets、数据库或其他软件进行交互。

## 1.2 Servlet架构

![image-20210414154546332](C:\Users\80740\AppData\Roaming\Typora\typora-user-images\image-20210414154546332.png)



## 1.3 Servlet任务

Servlet 执行以下主要任务：

- 读取客户端（浏览器）发送的显式的数据。这包括网页上的 HTML 表单，或者也可以是来自 applet 或自定义的 HTTP 客户端程序的表单。
- 读取客户端（浏览器）发送的隐式的 HTTP 请求数据。这包括 cookies、媒体类型和浏览器能理解的压缩格式等等。
- 处理数据并生成结果。这个过程可能需要访问数据库，执行 RMI 或 CORBA 调用，调用 Web 服务，或者直接计算得出对应的响应。
- 发送显式的数据（即文档）到客户端（浏览器）。该文档的格式可以是多种多样的，包括文本文件（HTML 或 XML）、二进制文件（GIF 图像）、Excel 等。
- 发送隐式的 HTTP 响应到客户端（浏览器）。这包括告诉浏览器或其他客户端被返回的文档类型（例如 HTML），设置 cookies 和缓存参数，以及其他类似的任务。

## 1.4 创建servlet的方式

（1）实现javax.servlet.Servlet接口

（2）继承javax.servlet.GenericServlet类

（3）继承javax.servlet.http.HttpServlet类

## 1.5 配置servlet

### 1.5.1 web.xml中配置

```xml
<servlet>
    <!--servlet程序名，一般用类名-->
    <servlet-name>demo</servlet-name>
    <!--servlet包名.servlet类名-->
    <servlet-class>HelloWorld</servlet-class>
    <!--启动时即会加载，没有这句则是第一次调用时加载-->
    <load-on-startup>1</load-on-startup>
    <!--通过servlet类的init(ServletConfig config)可以拿到这个初始化参数-->
    <init-param>
    	<param-name>name</param-name>
        <param-value>value</param-value>
    </init-param>
</servlet>
<servlet-mapping>
    <servlet-name>demo</servlet-name>
    <!--配置地址,相对于根目录的地址-->
    <url-pattern>/HelloWorld</url-pattern>
</servlet-mapping>
```

### 1.5.2 java程序中配置

```java
//注解
@WebServlet("/HelloServlet");
//这两种配置方式只能使用一个
```

1.  优先精确匹配
2.  路径匹配，路径匹配优先长路径
3.  扩展名匹配不包括路径，没有斜杠
4.  前面都没匹配上

### 1.6.1 精确匹配

```xml
<!--无论请求哪个路径，都是同一个servlet进行处理-->
<servlet>
    <!--servlet程序名，一般用类名-->
    <servlet-name>demo</servlet-name>
    <!--servlet包名.servlet类名-->
    <servlet-class>HelloWorld</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>demo</servlet-name>
    <!--配置地址-->
    <url-pattern>/HelloWorld</url-pattern>
    <url-pattern>/HelloWorld1</url-pattern>
</servlet-mapping>
```

### 1.6.2 路径匹配

```xml
<!--通过通配符进行匹配-->
<servlet>
    <!--servlet程序名，一般用类名-->
    <servlet-name>demo</servlet-name>
    <!--servlet包名.servlet类名-->
    <servlet-class>HelloWorld</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>demo</servlet-name>
    <!--配置地址-->
    <url-pattern>/HelloWorld/*</url-pattern>
</servlet-mapping>
```

### 1.6.3 扩展名匹配

```xml
<!--前面通配，后面用扩展名限定-->
<servlet>
    <!--servlet程序名，一般用类名-->
    <servlet-name>demo</servlet-name>
    <!--servlet包名.servlet类名-->
    <servlet-class>HelloWorld</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>demo</servlet-name>
    <!--配置地址-->
    <url-pattern>*.do</url-pattern>
</servlet-mapping>
```

### 1.6.4 省略匹配

```xml
<servlet>
    <!--servlet程序名，一般用类名-->
    <servlet-name>demo</servlet-name>
    <!--servlet包名.servlet类名-->
    <servlet-class>HelloWorld</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>demo</servlet-name>
    <!--配置地址-->
    <url-pattern>/</url-pattern>
</servlet-mapping>
```

## 1.7 请求转发和重定向

请求转发是由多个servlet处理同一个请求，servlet1处理完交给servlet2继续处理

```java
//字符串"order"是要转给哪个servlet的路径
req.getRequestDispatcher("/order").forward(req,resp);
```

### 1.7.1 传递数据

两个servlet传递数据可以使用以下方法：

```java
//以key-value的形式传递数据
req.setAttribute("name","李根");
```

### 1.7.2 重定向

```java
//方法一
String site = new String("http://www.runoob.com");

response.setStatus(302);//重定向状态码
response.setStatus(response.SC_MOVED_TEMPORARILY);//这上上面的任写一句
response.setHeader("Location", site);

//方法二
resp.sendRedirect("www.baidu.com");//这个方法会自己加302的响应头
```



## 1.8 响应头

```java
//在响应头中添加信息，使浏览器5秒后跳转到指定的网址
resp.addHeader("Refresh","5; URL=http://www.baidu.com");
```



# 2 Servlet生命周期

Servlet 生命周期可被定义为从创建直到毁灭的整个过程。以下是 Servlet 遵循的过程：

- Servlet 初始化后调用 **init ()** 方法。

- Servlet 调用 **service()** 方法来处理客户端的请求。

- Servlet 销毁前调用  **destroy()** 方法。

- 最后，Servlet 是由 JVM 的垃圾回收器进行垃圾回收的。

  ## init() 方法

  init 方法被设计成只调用一次。它在第一次创建 Servlet 时被调用，在后续每次用户请求时不再调用。因此，它是用于一次性初始化，就像 Applet 的 init 方法一样。

  Servlet 创建于用户第一次调用对应于该 Servlet 的 URL 时，但是您也可以指定 Servlet 在服务器第一次启动时被加载。

  当用户调用一个 Servlet 时，就会创建一个 Servlet 实例，每一个用户请求都会产生一个新的线程，适当的时候移交给 doGet 或 doPost 方法。init() 方法简单地创建或加载一些数据，这些数据将被用于 Servlet 的整个生命周期。

  ```java
  public void init() throws ServletException {
    // 初始化代码...
  }
  ```

  ## service() 方法

  service() 方法是执行实际任务的主要方法。Servlet 容器（即 Web 服务器）调用 service() 方法来处理来自客户端（浏览器）的请求，并把格式化的响应写回给客户端。

  每次服务器接收到一个 Servlet 请求时，服务器会产生一个新的线程并调用服务。service() 方法检查 HTTP  请求类型（GET、POST、PUT、DELETE 等），并在适当的时候调用 doGet、doPost、doPut，doDelete 等方法。

  下面是该方法的特征：

  ```java
  public void service(ServletRequest request, ServletResponse response) 
        throws ServletException, IOException{
  }
  ```

  ## doGet() 方法

  GET 请求来自于一个 URL 的正常请求，或者来自于一个未指定 METHOD 的 HTML 表单，它由 doGet() 方法处理。

  ```java
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      // Servlet 代码
  }
  ```

  ## doPost() 方法

  POST 请求来自于一个特别指定了 METHOD 为 POST 的 HTML 表单，它由 doPost() 方法处理。

  ```java
  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
      // Servlet 代码
  }
  ```

  ## destroy() 方法

  destroy() 方法只会被调用一次，在 Servlet 生命周期结束时被调用。destroy() 方法可以让您的 Servlet 关闭数据库连接、停止后台线程、把 Cookie 列表或点击计数器写入到磁盘，并执行其他类似的清理活动。

  在调用 destroy() 方法之后，servlet 对象被标记为垃圾回收。destroy 方法定义如下所示：

  ```java
    public void destroy() {
      // 终止化代码...
    }
  ```

## 2.1 Hello word

```java
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

// 扩展 HttpServlet 类
@WebServlet("/HelloWorld")
public class HelloWorld extends HttpServlet {

    private String message;

    public void init() throws ServletException
    {
        // 执行必需的初始化
        message = "Hello World";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // 设置响应内容类型
        response.setContentType("text/html;charset=utf-8");
        
        //保证服务器处理字符串按照utf-8处理,客户端并不是按照这个设置处理
//        resp.setCharacterEncoding("utf-8");
        //这个设置是两边都是utf-8
        resp.addHeader("content-type","text/html;charset=utf-8");

        // 实际的逻辑是在这里
        PrintWriter out = response.getWriter();
        out.println("<h1>" + message + "</h1>");
    }

    public void destroy()
    {
        // 什么也不做
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    
    <servlet>
        <!--servlet程序名，一般用类名-->
        <servlet-name>demo</servlet-name>
        <!--servlet包名.servlet类名-->
        <servlet-class>HelloWorld</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>demo</servlet-name>
        <!--配置地址-->
        <url-pattern>/HelloWorld</url-pattern>
    </servlet-mapping>

</web-app>
```

# 3 Servlet表单数据

__GET方法__

GET 方法向页面请求发送已编码的用户信息。页面和已编码的信息中间用 ? 字符分隔，如下所示：

```xml
http://www.test.com/hello?key1=value1&key2=value2
```

GET 方法是默认的从浏览器向 Web 服务器传递信息的方法，它会产生一个很长的字符串，出现在浏览器的地址栏中。如果您要向服务器传递的是密码或其他的敏感信息，请不要使用 GET 方法。GET 方法有大小限制：请求字符串中最多只能有 1024 个字符。

这些信息使用 QUERY_STRING  头传递，并可以通过 QUERY_STRING 环境变量访问，Servlet 使用 **doGet()** 方法处理这种类型的请求。

__POST 方法__

另一个向后台程序传递信息的比较可靠的方法是 POST 方法。POST 方法打包信息的方式与 GET 方法基本相同，但是 POST  方法不是把信息作为 URL 中 ?  字符后的文本字符串进行发送，而是把这些信息作为一个单独的消息。消息以标准输出的形式传到后台程序，您可以解析和使用这些标准输出。Servlet  使用 doPost() 方法处理这种类型的请求。



- **getParameter()：**您可以调用 request.getParameter() 方法来获取表单参数的值。
- **getParameterValues()：**如果参数出现一次以上，则调用该方法，并返回多个值，例如复选框。
- **getParameterNames()：**如果您想要得到当前请求中的所有参数的完整列表，则调用该方法。

```html
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>传递多个参数</title>
	</head>
	<body>
        <!--action属性中是servlet路径-->
		<form action="parametervalue" name="test" method="get">
			<!--发送到parameter处理-->
			你的爱好：<br />
			<input type="checkbox" name="fond" value="sport"/>体育</input>
			<input type="checkbox" name="fond" value="music"/>音乐</input>
			<input type="checkbox" name="fond" value="paint"/>美术</input>
			<input type="checkbox" name="fond" value="reading"/>看书</input>
			<br/>
			<input type="submit" value="提交"/>
		</form>
	</body>
</html>
```

```java
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class ParameterValue extends HttpServlet{
    public void init(){}

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)
    throws ServletException,IOException{
        response.setContentType("text/html;charset=GBK");
        PrintWriter out =response.getWriter();
        String[] fond=request.getParameterValues("fond");//取得name为fond的项的value值
        out.println("Your hoobies are:<br>");
        String str="";
        for(int i=0;i<fond.length;++i){
            if("sport".equals(fond[i])){
                str="体育";
            }else if("music".equals(fond[i])){
                str="音乐";
            }else if("paint".equals(fond[i])){
                str="美术";
            }else if("calligraphy".equals(fond[i])){
                str="书法";
            }else if("reading".equals(fond[i])){
                str="看书";
            }
            out.println(str+"<br/>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        super.doGet(request,response);
    }
}
```

![image-20210414164753574](C:\Users\80740\AppData\Roaming\Typora\typora-user-images\image-20210414164753574.png)

![image-20210414164810556](C:\Users\80740\AppData\Roaming\Typora\typora-user-images\image-20210414164810556.png)



# 4 HTTP状态码

[菜鸟教程](https://www.runoob.com/servlet/servlet-http-status-codes.html)

| 100     | Continue                      | 只有请求的一部分已经被服务器接收，但只要它没有被拒绝，客户端应继续该请求。 |
| ------- | ----------------------------- | ------------------------------------------------------------ |
| 101     | Switching Protocols           | 服务器切换协议。                                             |
| 200     | OK                            | 请求成功。                                                   |
| 201     | Created                       | 该请求是完整的，并创建一个新的资源。                         |
| 202     | Accepted                      | 该请求被接受处理，但是该处理是不完整的。                     |
| 203     | Non-authoritative Information |                                                              |
| 204     | No Content                    |                                                              |
| 205     | Reset Content                 |                                                              |
| 206     | Partial Content               |                                                              |
| 300     | Multiple Choices              | 链接列表。用户可以选择一个链接，进入到该位置。最多五个地址。 |
| 301     | Moved Permanently             | 所请求的页面已经转移到一个新的 URL。                         |
| 302     | Found                         | 所请求的页面已经临时转移到一个新的 URL。                     |
| 303     | See Other                     | 所请求的页面可以在另一个不同的 URL 下被找到。                |
| 304     | Not Modified                  |                                                              |
| 305     | Use Proxy                     |                                                              |
| 306     | *Unused*                      | 在以前的版本中使用该代码。现在已不再使用它，但代码仍被保留。 |
| 307     | Temporary Redirect            | 所请求的页面已经临时转移到一个新的 URL。                     |
| 400     | Bad Request                   | 服务器不理解请求。                                           |
| 401     | Unauthorized                  | 所请求的页面需要用户名和密码。                               |
| 402     | Payment Required              | *您还不能使用该代码。*                                       |
| 403     | Forbidden                     | 禁止访问所请求的页面。                                       |
| **404** | **Not Found**                 | **服务器无法找到所请求的页面。**.                            |
| 405     | Method Not Allowed            | 在请求中指定的方法是不允许的。                               |
| 406     | Not Acceptable                | 服务器只生成一个不被客户端接受的响应。                       |
| 407     | Proxy Authentication Required | 在请求送达之前，您必须使用代理服务器的验证。                 |
| 408     | Request Timeout               | 请求需要的时间比服务器能够等待的时间长，超时。               |
| 409     | Conflict                      | 请求因为冲突无法完成。                                       |
| 410     | Gone                          | 所请求的页面不再可用。                                       |
| 411     | Length Required               | "Content-Length" 未定义。服务器无法处理客户端发送的不带 Content-Length 的请求信息。 |
| 412     | Precondition Failed           | 请求中给出的先决条件被服务器评估为 false。                   |
| 413     | Request Entity Too Large      | 服务器不接受该请求，因为请求实体过大。                       |
| 414     | Request-url Too Long          | 服务器不接受该请求，因为 URL 太长。当您转换一个 "post" 请求为一个带有长的查询信息的 "get" 请求时发生。 |
| 415     | Unsupported Media Type        | 服务器不接受该请求，因为媒体类型不被支持。                   |
| 417     | Expectation Failed            |                                                              |
| 500     | Internal Server Error         | 未完成的请求。服务器遇到了一个意外的情况。                   |
| 501     | Not Implemented               | 未完成的请求。服务器不支持所需的功能。                       |
| 502     | Bad Gateway                   | 未完成的请求。服务器从上游服务器收到无效响应。               |
| 503     | Service Unavailable           | 未完成的请求。服务器暂时超载或死机。                         |
| 504     | Gateway Timeout               | 网关超时。                                                   |
| 505     | HTTP Version Not Supported    | 服务器不支持"HTTP协议"版本。                                 |

## 设置状态码

| 序号 | 方法 & 描述|                                                  |
| ---- | ----------|-------------------------------------------------- |
| 1    | **public void setStatus ( int statusCode )** |该方法设置一个任意的状态码。setStatus 方法接受一个 int（状态码）作为参数。如果您的响应包含了一个特殊的状态码和文档，请确保在使用 *PrintWriter* 实际返回任何内容之前调用 setStatus。 |
| 2    | **public void sendRedirect(String url)** |该方法生成一个 302 响应，连同一个带有新文档 URL 的 *Location* 头。 |
| 3    | **public void sendError(int code, String message)** |该方法发送一个状态码（通常为 404），连同一个在 HTML 文档内部自动格式化并发送到客户端的短消息。 |

```java
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/showError")
// 扩展 HttpServlet 类
public class showError extends HttpServlet {

    // 处理 GET 方法请求的方法
    public void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
    {
        // 设置错误代码和原因
        response.sendError(407, "Need authentication!!!" );
    }
    // 处理 POST 方法请求的方法
    public void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException
    {
        doGet(request, response);
    }
}
```

![image-20210414165757709](C:\Users\80740\AppData\Roaming\Typora\typora-user-images\image-20210414165757709.png)



# 5 Servlet异常处理

当一个 Servlet 抛出一个异常时，Web 容器在使用了 exception-type 元素的 **web.xml** 中搜索与抛出异常类型相匹配的配置。

必须在 web.xml 中使用 **error-page** 元素来指定对特定**异常** 或 HTTP **状态码** 作出相应的 Servlet 调用。

```xml
<!-- servlet 定义 -->
<servlet>
        <servlet-name>ErrorHandler</servlet-name>
        <servlet-class>ErrorHandler</servlet-class>
</servlet>
<!-- servlet 映射 -->
<servlet-mapping>
        <servlet-name>ErrorHandler</servlet-name>
        <url-pattern>/ErrorHandler</url-pattern>
</servlet-mapping>

<!-- error-code 相关的错误页面 -->
<error-page>
    <error-code>404</error-code>
    <location>/ErrorHandler</location>
</error-page>
<error-page>
    <error-code>403</error-code>
    <location>/ErrorHandler</location>
</error-page>

<!-- exception-type 相关的错误页面 -->
<error-page>
    <exception-type>
          javax.servlet.ServletException
    </exception-type >
    <location>/ErrorHandler</location>
</error-page>

<error-page>
    <exception-type>java.io.IOException</exception-type >
    <location>/ErrorHandler</location>
</error-page>
```
如果您想对所有的异常有一个通用的错误处理程序，那么应该定义下面的 error-page，而不是为每个异常定义单独的 error-page 元素：
```xml
<error-page>
    <exception-type>java.lang.Throwable</exception-type >
    <location>/ErrorHandler</location>
</error-page>
```



# 6 Servlet过滤器

Servlet 过滤器可以动态地拦截请求和响应，以变换或使用包含在请求或响应中的信息。

可以将一个或多个 Servlet 过滤器附加到一个 Servlet 或一组 Servlet。Servlet 过滤器也可以附加到  JavaServer Pages (JSP) 文件和 HTML 页面。调用 Servlet 前调用所有附加的 Servlet 过滤器。

Servlet 过滤器是可用于 Servlet 编程的 Java 类，可以实现以下目的：

- 在客户端的请求访问后端资源之前，拦截这些请求。
- 在服务器的响应发送回客户端之前，处理这些响应。

根据规范建议的各种类型的过滤器：

- 身份验证过滤器（Authentication Filters）。
- 数据压缩过滤器（Data compression Filters）。
- 加密过滤器（Encryption Filters）。
- 触发资源访问事件过滤器。
- 图像转换过滤器（Image Conversion Filters）。
- 日志记录和审核过滤器（Logging and Auditing Filters）。
- MIME-TYPE 链过滤器（MIME-TYPE Chain Filters）。
- 标记化过滤器（Tokenizing Filters）。
- XSL/T 过滤器（XSL/T Filters），转换 XML 内容。

过滤器通过 Web 部署描述符（web.xml）中的 XML 标签来声明，然后映射到您的应用程序的部署描述符中的 Servlet 名称或 URL 模式。

当 Web 容器启动 Web 应用程序时，它会为您在部署描述符中声明的每一个过滤器创建一个实例。

Filter的执行顺序与在web.xml配置文件中的配置顺序一致，一般把Filter配置在所有的Servlet之前。

## 6.1 过滤器方法

| 序号 | 方法 & 描述  |                                                |
| ---- | -----------|------------------------------------------------- |
| 1    | **public void doFilter (ServletRequest, ServletResponse, FilterChain)** |该方法完成实际的过滤操作，当客户端请求方法与过滤器设置匹配的URL时，Servlet容器将先调用过滤器的doFilter方法。FilterChain用户访问后续过滤器。 |
| 2    | **public void init(FilterConfig filterConfig)** |web 应用程序启动时，web 服务器将创建Filter  的实例对象，并调用其init方法，读取web.xml配置，完成对象的初始化功能，从而为后续的用户请求作好拦截的准备工作（filter对象只会创建一次，init方法也只会执行一次）。开发人员通过init方法的参数，可获得代表当前filter配置信息的FilterConfig对象。 |
| 3    | **public void destroy()** |Servlet容器在销毁过滤器实例前调用该方法，在该方法中释放Servlet过滤器占用的资源。 |

## 6.2 过滤器实例

```java
package com.runoob.test;

//导入必需的 java 库
import javax.servlet.*;
import java.util.*;

//实现 Filter 类
public class LogFilter implements Filter  {
    public void  init(FilterConfig config) throws ServletException {
        // 获取初始化参数
        String site = config.getInitParameter("Site"); 

        // 输出初始化参数
        System.out.println("网站名称: " + site); 
    }
    public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws java.io.IOException, ServletException {

        // 输出站点名称
        System.out.println("站点网址：http://www.runoob.com");

        // 把请求传回过滤链
        chain.doFilter(request,response);
    }
    public void destroy( ){
        /* 在 Filter 实例被 Web 容器从服务移除之前调用 */
    }
}
```

```java
//导入必需的 java 库
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/DisplayHeader")

//扩展 HttpServlet 类
public class DisplayHeader extends HttpServlet {

    // 处理 GET 方法请求的方法
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        String title = "HTTP Header 请求实例 - 菜鸟教程实例";
        String docType =
            "<!DOCTYPE html> \n";
            out.println(docType +
            "<html>\n" +
            "<head><meta charset=\"utf-8\"><title>" + title + "</title></head>\n"+
            "<body bgcolor=\"#f0f0f0\">\n" +
            "<h1 align=\"center\">" + title + "</h1>\n" +
            "<table width=\"100%\" border=\"1\" align=\"center\">\n" +
            "<tr bgcolor=\"#949494\">\n" +
            "<th>Header 名称</th><th>Header 值</th>\n"+
            "</tr>\n");

        Enumeration headerNames = request.getHeaderNames();

        while(headerNames.hasMoreElements()) {
            String paramName = (String)headerNames.nextElement();
            out.print("<tr><td>" + paramName + "</td>\n");
            String paramValue = request.getHeader(paramName);
            out.println("<td> " + paramValue + "</td></tr>\n");
        }
        out.println("</table>\n</body></html>");
    }
    // 处理 POST 方法请求的方法
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
```

````xml
<?xml version="1.0" encoding="UTF-8"?>  
<web-app>  
<filter>
  <filter-name>LogFilter</filter-name>
  <filter-class>com.runoob.test.LogFilter</filter-class>
  <init-param>
    <param-name>Site</param-name>
    <param-value>菜鸟教程</param-value>
  </init-param>
</filter>
<filter-mapping>
  <filter-name>LogFilter</filter-name>
    <!--为哪些servlet配置该过滤器-->
  <url-pattern>/*</url-pattern>
</filter-mapping>
    
<servlet>  
  <!-- 类名 -->  
  <servlet-name>DisplayHeader</servlet-name>  
  <!-- 所在的包 -->  
  <servlet-class>com.runoob.test.DisplayHeader</servlet-class>  
</servlet>  
<servlet-mapping>  
  <servlet-name>DisplayHeader</servlet-name>  
  <!-- 访问的网址 -->  
  <url-pattern>/TomcatTest/DisplayHeader</url-pattern>  
</servlet-mapping>  
</web-app>  
````

![image-20210414172305393](C:\Users\80740\AppData\Roaming\Typora\typora-user-images\image-20210414172305393.png)



# 7 Servlet Cookie处理

Cookie 是存储在客户端计算机上的文本文件，并保留了各种跟踪信息。

识别返回用户包括三个步骤：

- 服务器脚本向浏览器发送一组 Cookie。例如：姓名、年龄或识别号码等。
- 浏览器将这些信息存储在本地计算机上，以备将来使用。
- 当下一次浏览器向 Web 服务器发送任何请求时，浏览器会把这些 Cookie 信息发送到服务器，服务器将使用这些信息来识别用户。

## 7.1 Servlet Cookie 方法

| 序号 | 方法 & 描述 |                                                 |
| ---- | ----------|-------------------------------------------------- |
| 1    | **public void setDomain(String pattern)** |该方法设置 cookie 适用的域，例如 runoob.com。 |
| 2    | **public String getDomain()** |该方法获取 cookie 适用的域，例如 runoob.com。 |
| 3    | **public void setMaxAge(int expiry)** |该方法设置 cookie 过期的时间（以秒为单位）。如果不这样设置，cookie 只会在当前 session 会话中持续有效。 |
| 4    | **public int getMaxAge()** |该方法返回 cookie 的最大生存周期（以秒为单位），默认情况下，-1 表示 cookie 将持续下去，直到浏览器关闭。 |
| 5    | **public String getName()** |该方法返回 cookie 的名称。名称在创建后不能改变。 |
| 6    | **public void setValue(String newValue)** |该方法设置与 cookie 关联的值。 |
| 7    | **public String getValue()** |该方法获取与 cookie 关联的值。  |
| 8    | **public void setPath(String uri)** |该方法设置 cookie 适用的路径。如果您不指定路径，与当前页面相同目录下的（包括子目录下的）所有 URL 都会返回 cookie。 |
| 9    | **public String getPath()** |该方法获取 cookie 适用的路径。   |
| 10   | **public void setSecure(boolean flag)** |该方法设置布尔值，表示 cookie 是否应该只在加密的（即 SSL）连接上发送。 |
| 11   | **public void setComment(String purpose)** |设置cookie的注释。该注释在浏览器向用户呈现 cookie 时非常有用。 |
| 12   | **public String getComment()** |获取 cookie 的注释，如果 cookie 没有注释则返回 null。 |

## 7.2 Servlet设置Cookie

**(1) 创建一个 Cookie 对象：**您可以调用带有 cookie 名称和 cookie 值的 Cookie 构造函数，cookie 名称和 cookie 值都是字符串。

```java
Cookie cookie = new Cookie("key","value");
```

**(2) 设置最大生存周期：**您可以使用 setMaxAge 方法来指定 cookie 能够保持有效的时间（以秒为单位）。下面将设置一个最长有效期为 24 小时的 cookie。

```java
cookie.setMaxAge(60*60*24); 
```

**(3) 发送 Cookie 到 HTTP 响应头：**您可以使用 **response.addCookie** 来添加 HTTP 响应头中的 Cookie，如下所示：

```java
response.addCookie(cookie);
```

### 7.2.1 设置Cookie

```java
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/HelloForm")
public class HelloForm extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloForm() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // 为名字和姓氏创建 Cookie      
        Cookie name = new Cookie("name",
                URLEncoder.encode(request.getParameter("name"), "UTF-8")); // 中文转码
        Cookie url = new Cookie("url",
                      request.getParameter("url"));
        
        // 为两个 Cookie 设置过期日期为 24 小时后
        name.setMaxAge(60*60*24); 
        url.setMaxAge(60*60*24); 
        
        // 在响应头中添加两个 Cookie
        response.addCookie( name );
        response.addCookie( url );
        
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");
        
        PrintWriter out = response.getWriter();
        String title = "设置 Cookie 实例";
        String docType = "<!DOCTYPE html>\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                "<ul>\n" +
                "  <li><b>站点名：</b>："
                + request.getParameter("name") + "\n</li>" +
                "  <li><b>站点 URL：</b>："
                + request.getParameter("url") + "\n</li>" +
                "</ul>\n" +
                "</body></html>");
        }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
```

```html
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>
<form action="/TomcatTest/HelloForm" method="GET">
站点名 ：<input type="text" name="name">
<br />
站点 URL：<input type="text" name="url" /><br>
<input type="submit" value="提交" />
</form>
</body>
</html>
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app>
    <servlet>
        <!-- 类名 -->
        <servlet-name>HelloForm</servlet-name>
        <!-- 所在的包 -->
        <servlet-class>HelloForm</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>HelloForm</servlet-name>
        <!-- 访问的网址 -->
        <url-pattern>/HelloForm</url-pattern>
    </servlet-mapping>

    <servlet>
        <!-- 类名 -->
        <servlet-name>cookie</servlet-name>
        <!-- 所在的包 -->
        <servlet-class>ReadCookies</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>cookie</servlet-name>
        <!-- 访问的网址 -->
        <url-pattern>/ReadCookies</url-pattern>
    </servlet-mapping>
</web-app>
```

### 7.2.2 读取cookie

```java
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReadCookies
 */
@WebServlet("/ReadCookies")
public class ReadCookies extends HttpServlet {
    private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReadCookies() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Cookie cookie = null;
        Cookie[] cookies = null;
        // 获取与该域相关的 Cookie 的数组
        cookies = request.getCookies();
         
         // 设置响应内容类型
         response.setContentType("text/html;charset=UTF-8");
    
         PrintWriter out = response.getWriter();
         String title = "Delete Cookie Example";
         String docType = "<!DOCTYPE html>\n";
         out.println(docType +
                   "<html>\n" +
                   "<head><title>" + title + "</title></head>\n" +
                   "<body bgcolor=\"#f0f0f0\">\n" );
          if( cookies != null ){
            out.println("<h2>Cookie 名称和值</h2>");
            for (int i = 0; i < cookies.length; i++){
               cookie = cookies[i];
               if((cookie.getName( )).compareTo("name") == 0 ){
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    out.print("已删除的 cookie：" + 
                                 cookie.getName( ) + "<br/>");
               }
               out.print("名称：" + cookie.getName( ) + "，");
               out.print("值：" +  URLDecoder.decode(cookie.getValue(), "utf-8") +" <br/>");
            }
         }else{
             out.println(
               "<h2 class=\"tutheader\">No Cookie founds</h2>");
         }
         out.println("</body>");
         out.println("</html>");
        }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}
```



# 8 Servlet Session跟踪

HTTP 是一种"无状态"协议，这意味着每次客户端检索网页时，客户端打开一个单独的连接到 Web 服务器，服务器会自动不保留之前客户端请求的任何记录。

但是仍然有以下三种方式来维持 Web 客户端和 Web 服务器之间的 session 会话：

## 8.1 Cookies

一个 Web 服务器可以分配一个唯一的 session 会话 ID 作为每个 Web 客户端的 cookie，对于客户端的后续请求可以使用接收到的 cookie 来识别。

这可能不是一个有效的方法，因为很多浏览器不支持 cookie，所以我们建议不要使用这种方式来维持 session 会话。

## 8.2 隐藏的表单字段

一个 Web 服务器可以发送一个隐藏的 HTML 表单字段，以及一个唯一的 session 会话 ID，如下所示：

```
<input type="hidden" name="sessionid" value="12345">
```

该条目意味着，当表单被提交时，指定的名称和值会被自动包含在 GET 或 POST 数据中。每次当 Web 浏览器发送回请求时，session_id 值可以用于保持不同的 Web 浏览器的跟踪。

这可能是一种保持 session 会话跟踪的有效方式，但是点击常规的超文本链接（<A HREF...>）不会导致表单提交，因此隐藏的表单字段也不支持常规的 session 会话跟踪。

## 8.3 URL 重写

您可以在每个 URL 末尾追加一些额外的数据来标识 session 会话，服务器会把该 session 会话标识符与已存储的有关 session 会话的数据相关联。

例如，http://w3cschool.cc/file.htm;sessionid=12345，session 会话标识符被附加为 sessionid=12345，标识符可被 Web 服务器访问以识别客户端。

URL 重写是一种更好的维持 session 会话的方式，它在浏览器不支持 cookie 时能够很好地工作，但是它的缺点是会动态生成每个 URL 来为页面分配一个 session 会话 ID，即使是在很简单的静态 HTML 页面中也会如此。

## 8.4 HttpSession 对象

除了上述的三种方式，Servlet 还提供了 HttpSession 接口，该接口提供了一种跨多个页面请求或访问网站时识别用户以及存储有关用户信息的方式。

Servlet 容器使用这个接口来创建一个 HTTP 客户端和 HTTP 服务器之间的 session 会话。会话持续一个指定的时间段，跨多个连接或页面请求。

您会通过调用 HttpServletRequest 的公共方法 **getSession()** 来获取 HttpSession 对象，如下所示：

```
HttpSession session = request.getSession();
```

你需要在向客户端发送任何文档内容之前调用 *request.getSession()*。下面总结了 HttpSession 对象中可用的几个重要的方法：

| 序号 | 方法 & 描述                                             |                                                              |
| ---- | ------------------------------------------------------- | ------------------------------------------------------------ |
| 1    | **public Object getAttribute(String name)**             | 该方法返回在该 session 会话中具有指定名称的对象，如果没有指定名称的对象，则返回 null。 |
| 2    | **public Enumeration getAttributeNames()**              | 该方法返回 String 对象的枚举，String 对象包含所有绑定到该 session 会话的对象的名称。 |
| 3    | **public long getCreationTime()**                       | 该方法返回该 session 会话被创建的时间，自格林尼治标准时间 1970 年 1 月 1 日午夜算起，以毫秒为单位。 |
| 4    | **public String getId()**                               | 该方法返回一个包含分配给该 session 会话的唯一标识符的字符串。 |
| 5    | **public long getLastAccessedTime()**                   | 该方法返回客户端最后一次发送与该 session 会话相关的请求的时间自格林尼治标准时间 1970 年 1 月 1 日午夜算起，以毫秒为单位。 |
| 6    | **public int getMaxInactiveInterval()**                 | 该方法返回 Servlet 容器在客户端访问时保持 session 会话打开的最大时间间隔，以秒为单位。 |
| 7    | **public void invalidate()**                            | 该方法指示该 session 会话无效，并解除绑定到它上面的任何对象。 |
| 8    | **public boolean isNew()**                              | 如果客户端还不知道该 session 会话，或者如果客户选择不参入该 session 会话，则该方法返回 true。 |
| 9    | **public void removeAttribute(String name)**            | 该方法将从该 session 会话移除指定名称的对象。                |
| 10   | **public void setAttribute(String name, Object value)** | 该方法使用指定的名称绑定一个对象到该 session 会话。          |
| 11   | **public void setMaxInactiveInterval(int interval)**    | 该方法在 Servlet 容器指示该 session 会话无效之前，指定客户端请求之间的时间，以秒为单位。 |

### 8.4.1 实例

```java
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SessionTrack
 */
@WebServlet("/SessionTrack")
public class SessionTrack extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // 如果不存在 session 会话，则创建一个 session 对象
        HttpSession session = request.getSession(true);
        // 获取 session 创建时间
        Date createTime = new Date(session.getCreationTime());
        // 获取该网页的最后一次访问时间
        Date lastAccessTime = new Date(session.getLastAccessedTime());
         
        //设置日期输出的格式  
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    
        String title = "Servlet Session 实例 - 菜鸟教程";
        Integer visitCount = new Integer(0);
        String visitCountKey = new String("visitCount");
        String userIDKey = new String("userID");
        String userID = new String("Runoob");
        if(session.getAttribute(visitCountKey) == null) {
            session.setAttribute(visitCountKey, new Integer(0));
        }

    
        // 检查网页上是否有新的访问者
        if (session.isNew()){
            title = "Servlet Session 实例 - 菜鸟教程";
             session.setAttribute(userIDKey, userID);
        } else {
             visitCount = (Integer)session.getAttribute(visitCountKey);
             visitCount = visitCount + 1;
             userID = (String)session.getAttribute(userIDKey);
        }
        session.setAttribute(visitCountKey,  visitCount);
    
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
    
        String docType = "<!DOCTYPE html>\n";
        out.println(docType +
                "<html>\n" +
                "<head><title>" + title + "</title></head>\n" +
                "<body bgcolor=\"#f0f0f0\">\n" +
                "<h1 align=\"center\">" + title + "</h1>\n" +
                 "<h2 align=\"center\">Session 信息</h2>\n" +
                "<table border=\"1\" align=\"center\">\n" +
                "<tr bgcolor=\"#949494\">\n" +
                "  <th>Session 信息</th><th>值</th></tr>\n" +
                "<tr>\n" +
                "  <td>id</td>\n" +
                "  <td>" + session.getId() + "</td></tr>\n" +
                "<tr>\n" +
                "  <td>创建时间</td>\n" +
                "  <td>" +  df.format(createTime) + 
                "  </td></tr>\n" +
                "<tr>\n" +
                "  <td>最后访问时间</td>\n" +
                "  <td>" + df.format(lastAccessTime) + 
                "  </td></tr>\n" +
                "<tr>\n" +
                "  <td>用户 ID</td>\n" +
                "  <td>" + userID + 
                "  </td></tr>\n" +
                "<tr>\n" +
                "  <td>访问统计：</td>\n" +
                "  <td>" + visitCount + "</td></tr>\n" +
                "</table>\n" +
                "</body></html>"); 
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app>
  <servlet> 
    <!-- 类名 -->  
    <servlet-name>SessionTrack</servlet-name>
    <!-- 所在的包 -->
    <servlet-class>com.runoob.test.SessionTrack</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>SessionTrack</servlet-name>
    <!-- 访问的网址 -->
    <url-pattern>/TomcatTest/SessionTrack</url-pattern>
  </servlet-mapping>
</web-app>
```



# 9 Servlet数据库访问

[菜鸟教程](https://www.runoob.com/servlet/servlet-database-access.html)

```java
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DatabaseAccess
 */
@WebServlet("/DatabaseAccess")
public class DatabaseAccess extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
    static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB";
    
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "123456"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DatabaseAccess() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection conn = null;
        Statement stmt = null;
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String title = "Servlet Mysql 测试 - 菜鸟教程";
        String docType = "<!DOCTYPE html>\n";
        out.println(docType +
        "<html>\n" +
        "<head><title>" + title + "</title></head>\n" +
        "<body bgcolor=\"#f0f0f0\">\n" +
        "<h1 align=\"center\">" + title + "</h1>\n");
        try{
            // 注册 JDBC 驱动器
            Class.forName("com.mysql.jdbc.Driver");
            
            // 打开一个连接
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行 SQL 查询
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT id, name, url FROM websites";
            ResultSet rs = stmt.executeQuery(sql);

            // 展开结果集数据库
            while(rs.next()){
                // 通过字段检索
                int id  = rs.getInt("id");
                String name = rs.getString("name");
                String url = rs.getString("url");
    
                // 输出数据
                out.println("ID: " + id);
                out.println(", 站点名称: " + name);
                out.println(", 站点 URL: " + url);
                out.println("<br />");
            }
            out.println("</body></html>");

            // 完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        } catch(SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch(Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 最后是用于关闭资源的块
            try{
                if(stmt!=null)
                stmt.close();
            }catch(SQLException se2){
            }
            try{
                if(conn!=null)
                conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
       
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <servlet>
        <servlet-name>DatabaseAccess</servlet-name>
        <servlet-class>DatabaseAccess</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>DatabaseAccess</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```



# 10 Servlet上传文件

```java
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";

    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    /**
     * 上传数据及保存文件
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath = request.getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;


        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);

            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        request.setAttribute("message",
                                "文件上传成功!");
                    }
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "错误信息: " + ex.getMessage());
        }
        // 跳转到 message.jsp
        request.getServletContext().getRequestDispatcher("/message.jsp").forward(
                request, response);
    }
}
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
        http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         id="WebApp_ID" version="2.5">
    <servlet>
        <display-name>UploadServlet</display-name>
        <servlet-name>UploadServlet</servlet-name>
        <servlet-class>UploadServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>UploadServlet</servlet-name>
        <url-pattern>/TomcatTest/UploadServlet</url-pattern>
    </servlet-mapping>
</web-app>
```



# 11 Servlet 网页重定向

```java
//这个跳转是客户端自己的行为，不是服务器的行为
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PageRedirect
 */
@WebServlet("/PageRedirect")
public class PageRedirect extends HttpServlet{

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException
    {
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");

        // 要重定向的新位置
        String site = new String("http://www.runoob.com");
		
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);
    }
} 
```

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
        http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         id="WebApp_ID" version="2.5">
    <servlet>
        <servlet-name>PageRedirect</servlet-name>
        <servlet-class>PageRedirect</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>PageRedirect</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

# 12 路径问题

站点根目录：http:localhost:8080

对于浏览器而言，他的根目录是站点根目录

项目根目录：http:localhost:8080/study/

对于项目工程而言，服务端根目录是项目根目录

```java
//项目路径http:localhost:8080/study/
//第一个servlet页面http:localhost:8080/study/user
//在UserServlet中有两个请求转发,UserServlet配置在/user路径下

//如果从http:localhost:8080/study/u/user访问，将转到
//http:localhost:8080/study/u/order
req.getRequestDispatcher("order").forward(req,resp);

//这个是在项目路径后拼接/order
//如果项目在/study/u/
//那么将转到/study/u/order
req.getRequestDispatcher("/order").forward(req,resp);//这个转到http:localhost:8080/study/order
```

```html
<!--如果页面在http:localhost:8080/study/u/index.html-->
<!--会转到http:localhost:8080/study/u/user-->
<form action="user" >
    
</form>

<!--如果页面在http:localhost:8080/study/u/index.html-->
<!--会转到http:localhost:8080/study/user-->
<form action="/user" >
    
</form>
```

在jsp页面`<head>`标签内或者`<html>`前加上：

```jsp
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServletPath()+path+"/";
%>
<!--head内加上-->
<base href = "<%=basePath%>">
<!--这样就不需要考虑站点根目录和项目根目录的差异-->
```

# 13 密码验证

