# 1 MVC

- Controller
  - 取得表单数据
  - 调用业务逻辑
  - 转向指定页面
- Model
  - 业务逻辑service
  - 保存数据的状态dao
- View
  - 显示页面



# 2 Hello SpringMVC

1.导包

```xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/javax.servlet.jsp/jsp-api -->
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
        <scope>provided</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/javax.servlet/servlet-api -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
        <scope>provided</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.0.RELEASE</version>
    </dependency>
    <!--spring-jdbc-->
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>5.2.0.RELEASE</version>
    </dependency>
</dependencies>
```

2.添加tomcat依赖

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/Spring/SpringMVC给tomcat添加依赖.jpg" style="zoom:67%;" />

3.配置DispathcerServlet到web.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

<!--    1.注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
<!--        关联一个springmvc的配置文件：【servlet-name】-servlet.xml-->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
<!--        启动级别1-->
        <load-on-startup>1</load-on-startup>
    </servlet>

<!--    / 匹配所有的请求：不包括.jsp-->
<!--    /* 匹配所有的请求：包括.jsp-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

4.配置springMVC

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">


<!--    处理器映射器-->
<!--    使用这个处理器映射器就需要将业务注册-->    
    <bean class = "org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
<!--    处理器适配器-->
    <bean class = "org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>

<!--    视图解析器DispatcherServlet给他的ModelAndView-->
<!--    获取ModelAndView数据-->
<!--    解析其中的视图名称-->
<!--    拼接前后缀-->
<!--    将数据渲染到视图-->
<!--    这个是在哪个文件夹中去找-->
    <bean class ="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
<!--        前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
<!--        后缀-->
        <property name="suffix" value=".jsp"/>
    </bean>

<!--    注册Handler-->
<!--    这里设置的是访问路径-->
    <bean id="/hello" class="cn.edu.xidian.controller.HelloController"/>
</beans>
```

5.业务Controller

```java
package cn.edu.xidian.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        //模型与视图
        ModelAndView mv = new ModelAndView();

        //封装信息
        mv.addObject("msg","HelloSpringMVC");

        //设置视图名称
        mv.setViewName("hello");//WEB-ING/jsp/hello.jsp（这是项目路径，不是访问路径）

        return mv;
    }
}
```

6.将业务bean注册到springmvc配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">


<!--    处理器映射器-->
    <bean class = "org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
<!--    处理器适配器-->
    <bean class = "org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>

<!--    视图解析器DispatcherServlet给他的ModelAndView-->
<!--    获取ModelAndView数据-->
<!--    解析其中的视图名称-->
<!--    拼接前后缀-->
<!--    将数据渲染到视图-->
<!--    这个是在哪个文件夹中去找-->
    <bean class ="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
<!--        前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
<!--        后缀-->
        <property name="suffix" value=".jsp"/>
    </bean>

<!--    注册Handler-->
<!--    这里设置的是访问路径-->
    <bean id="/hello" class="cn.edu.xidian.controller.HelloController"/>
</beans>
```

7.创建需要跳转到的页面

```jsp
<%--
该页面文件夹路径： /WEB-INF/jsp/hello.jsp
--%>
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/9/2
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <%--
这个msg和业务中的addObject方法匹配
--%>
${msg}
</body>
</html>

```



# 3 SpringMVC执行原理

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/Spring/SpringMVC执行流程.png" style="zoom:75%;" />

SpringMVC 的执行流程如下。

1. 用户点击某个请求路径，发起一个 HTTP request 请求，该请求会被提交到 DispatcherServlet（前端控制器）；

2. 由 DispatcherServlet 请求一个或多个 HandlerMapping（处理器映射器），并返回一个执行链（HandlerExecutionChain）。
3. DispatcherServlet 将执行链返回的 Handler 信息发送给 HandlerAdapter（处理器适配器）；
4. HandlerAdapter 根据 Handler 信息找到并执行相应的 Handler（常称为 Controller）；
5. Handler 执行完毕后会返回给 HandlerAdapter 一个 ModelAndView 对象（Spring MVC的底层对象，包括 Model 数据模型和 View 视图信息）；
6. HandlerAdapter 接收到 ModelAndView 对象后，将其返回给 DispatcherServlet ；
7. DispatcherServlet 接收到 ModelAndView 对象后，会请求 ViewResolver（视图解析器）对视图进行解析；
8. ViewResolver 根据 View 信息匹配到相应的视图结果，并返回给 DispatcherServlet；
9. DispatcherServlet 接收到具体的 View 视图后，进行视图渲染，将 Model 中的模型数据填充到 View 视图中的 request 域，生成最终的 View（视图）；
10. 视图负责将结果显示到浏览器（客户端）。





## 3.1 SpringMVC接口

Spring MVC 涉及到的组件有 DispatcherServlet（前端控制器）、HandlerMapping（处理器映射器）、HandlerAdapter（处理器适配器）、Handler（处理器）、ViewResolver（视图解析器）和 View（视图）。下面对各个组件的功能说明如下。

- #### DispatcherServlet

DispatcherServlet 是前端控制器，从图 1 可以看出，Spring MVC 的所有请求都要经过 DispatcherServlet 来统一分发。DispatcherServlet 相当于一个转发器或中央处理器，控制整个流程的执行，对各个组件进行统一调度，以降低组件之间的耦合性，有利于组件之间的拓展。

- #### HandlerMapping

HandlerMapping 是处理器映射器，其作用是根据请求的 URL 路径，通过注解或者 XML 配置，寻找匹配的处理器（Handler）信息。

- #### HandlerAdapter


HandlerAdapter 是处理器适配器，其作用是根据映射器找到的处理器（Handler）信息，按照特定规则执行相关的处理器（Handler）。

- #### Handler


Handler 是处理器，和 Java Servlet 扮演的角色一致。其作用是执行相关的请求处理逻辑，并返回相应的数据和视图信息，将其封装至 ModelAndView 对象中。

- #### View Resolver


View Resolver 是视图解析器，其作用是进行解析操作，通过 ModelAndView 对象中的 View 信息将逻辑视图名解析成真正的视图 View（如通过一个 JSP 路径返回一个真正的 JSP 页面）。

- #### View


View 是视图，其本身是一个接口，实现类支持不同的 View 类型（JSP、FreeMarker、Excel 等）。

以上组件中，需要开发人员进行开发的是处理器（Handler，常称Controller）和视图（View）。通俗的说，要开发处理该请求的具体代码逻辑，以及最终展示给用户的界面。



## 3.2 SpringMVC视图解析器



### 3.2.1 URLBasedViewResolver

UrlBasedViewResolver 是对 ViewResolver 的一种简单实现，主要提供了一种拼接 URL 的方式来解析视图。



UrlBasedViewResolver 通过 prefix 属性指定前缀，suffix 属性指定后缀。当 ModelAndView 对象返回具体的 View 名称时，它会将前缀 prefix 和后缀 suffix 与具体的视图名称拼接，得到一个视图资源文件的具体加载路径，从而加载真正的视图文件并反馈给用户。

使用 UrlBasedViewResolver 除了要配置前缀和后缀属性之外，还需要配置“viewClass”，表示解析成哪种视图。示例代码如下。



```xml
<bean id="viewResolver" class="org.springframework.web.servlet.view.UrlBasedViewResolver">            
    <property name="viewClass" value="org.springframework.web.servlet.view.InternalResourceViewResolver"/> <!--不能省略-->
    <!--前缀-->
    <property name="prefix" value="/WEB-INF/jsp/"/>
    <!--后缀-->
    <property name="suffix" value=".jsp"/>  
 </bean>
```

此处解析为 /WEB-INF/jsp/register.jsp 和 /WEB-INF/jsp/login.jsp



### 3.2.2 InternalResourceViewResolver

InternalResourceViewResolver 为“内部资源视图解析器”，是日常开发中最常用的视图解析器类型。它是 URLBasedViewResolver 的子类，拥有 URLBasedViewResolver 的一切特性。

InternalResourceViewResolver 能自动将返回的视图名称解析为 InternalResourceView 类型的对象。InternalResourceView 会把 Controller 处理器方法返回的模型属性都存放到对应的 request 属性中，然后通过 RequestDispatcher 在服务器端把请求 forword 重定向到目标 URL。也就是说，使用 InternalResourceViewResolver 视图解析时，无需再单独指定 viewClass 属性。示例代码如下。

```xml
<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
    <property name="prefix" value="/WEB-INF/jsp/"/>
    <property name="suffix" value=".jsp"/>
</bean>
```



### 3.2.3 FreeMarkerViewResolver

FreeMarkerViewResolver 是 UrlBasedViewResolver 的子类，可以通过 prefix 属性指定前缀，通过 suffix 属性指定后缀。

FreeMarkerViewResolver 最终会解析逻辑视图配置，返回 freemarker 模板。不需要指定 viewClass 属性。

FreeMarkerViewResolver 配置如下。

```xml
<bean class="org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver">
    <property name="prefix" value="fm_"/>
    <property name="suffix" value=".ftl"/>
</bean>
```

下面指定 FreeMarkerView 类型最终生成的实体视图（模板文件）的路径以及其他配置。需要给 FreeMarkerViewResolver 设置一个 FreeMarkerConfig 的 bean 对象来定义 FreeMarker 的配置信息，代码如下。

```xml
<bean class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
    <property name="templateLoaderPath" value="/WEB-INF/ftl" />
</bean>
```

定义了 templateLoaderPath 属性后，Spring 可以通过该属性找到 FreeMarker 模板文件的具体位置。当有模板位于不同的路径时，可以配置 templateLoaderPath 属性，来指定多个资源路径。

然后定义一个 Controller，让其返回 ModelAndView，同时定义一些返回参数和视图信息。

```java
@Controller
@RequestMapping("viewtest")
public class ViewController {
    @RequestMapping("freemarker")
    public ModelAndView freemarker() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("username", "BianChengBang");
        mv.setViewName("freemarker");
        return mv;
    }
}
```

当 FreeMarkerViewResolver 解析逻辑视图信息时，会生成一个 URL 为“前缀+视图名+后缀”（这里即“fm_freemarker.ftl”）的 FreeMarkerView 对象，然后通过 FreeMarkerConfigurer 的配置找到 templateLoaderPath 对应文本文件的路径，在该路径下找到该文本文件，从而 FreeMarkerView 就可以利用该模板文件进行视图的渲染，并将 model 数据封装到即将要显示的页面上，最终展示给用户。



在 /WEB-INF/ftl 文件夹下创建 fm_freemarker.ftl，代码如下。

```jsp
<html>
<head>
<title>FreeMarker</title>
</head>
<body>
    <b>Welcome!</b>
    <i>${username }</i>
</body>
</html>
```



# 4 使用注解开发SpringMVC

springmvc资源文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd">

<!--    自动扫描包，让指定包下的注解生效，由IOC容器统一管理-->
    <context:component-scan base-package="cn.edu.xidian.controller"/>
<!--    让springmvc不处理静态资源 css js html mp3 mp4-->
    <mvc:default-servlet-handler/>

<!--    支持mvc注解驱动-->
<!--    在spring中一般采用@RequestMapping注解来完成映射关系-->
<!--    要想使该注解生效，必须向上下文注册DefaultAnnotationHandlerMapping-->
<!--    和一个AnnotationMethodHandlerAdapter-->
<!--    这两个实例分别在类级别和方法级别处理-->
<!--    而annotation-driven配置帮助我们自动完成上述两个实例的配置-->
    <mvc:annotation-driven/>

<!--    视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
    

</beans>

```

实体控制器类

```java
package cn.edu.xidian.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//不用再到springmvc配置文件中注册
@Controller
public class HelloController {

    //直接设置访问路径
    @RequestMapping("/hello")
    public String hello(Model model){
        //封装数据
        model.addAttribute("msg","springmvc");


        //这个hello就是要访问的页面名称 /WEB-INF/jsp/
        return "hello";//这个return的结果会被视图解析器处理
    }

}

```

其他的部分同上

## 4.1@Controller

该注解用于声明某个类的实例是一个控制器

```java
import org.springframework.stereotype.Controller;

@Controller
public class HelloController {
	//处理请求的方法
}

```

SpringMVC使用扫描机制找到应用中所有基于注解的控制器类，，所以需要在配置文件中生命spring-context，并且使用`<context:component-scan/>`元素指定控制器类的基本包

```xml
<!--    自动扫描包，让指定包下的注解生效，由IOC容器统一管理-->
<context:component-scan base-package="cn.edu.xidian.controller"/>
```



## 4.2 @RequestMapping

该注解用在方法或者类的前面，参数为客户端访问路径

当类和方法前都有该注解路径那么访问时需要将两个路径进行拼接

该注解常用属性：

- value
- path
- name
- method
- params
- header
- consumers
- produces



### 4.2.1 value属性

value属性是@RequestMapping注解的默认属性，如果只有value属性的时候可以省略属性名

```java
@RequestMapping(value="/hello");
@RequestMapping("/hello");
```

value属性支持通配符匹配

```java
@RequestMapping("/hello/*");
```

这会访问到所有/hello/下的路径



### 4.2.2 path属性

path属性和value属性都用来作为映射使用。

暂时学到value和path相同



### 4.2.3 name属性

name属性相当于方法注释

```java
@RequestMapping(value="/hello",name="访问hello页面");
```



### 4.2.4 method属性

method属性用于表示该方法支持哪些HTTP请求。如果省略，默认全部支持

```java
@RequestMapping(method= RequestMethod.GET);
@RequestMapping(method= {RequestMethod.GET,RequestMethod.POST});
```



### 4.2.5 params属性

params属性用于指定请求中规定的参数

```java
@RequestMapping(value = "toUser",params = "type")
public String toUser() {
    
    return "showUser";
}
```

上面表示请求中必须包含type参数时才能执行该请求，也就使必须是

```
http://localhost:8080/toUser?type=xxx
```

才能正常访问

```java
@RequestMapping(value = "toUser",params = "type=1")
public String toUser() {
    
    return "showUser";
}
```

必须是

```
http://localhost:8080/toUser?type=1
```

才能正常访问



### 4.2.6 header属性

header属性表示请求中必须包含指定的header值

```java
@RequestMapping(value = "toUser",headers = "Referer=http://www.xxx.com") 
```

表示请求header中必须包含指定的"Referer"请求头，并且值为"http://www.xxx.com"才执行该请求



### 4.2.7 consumers属性

consumers 属性用于指定处理请求的提交内容类型（Content-Type），例如：application/json、text/html。



### 4.2.8 produces属性

produces 属性用于指定返回的内容类型，返回的内容类型必须是 request 请求头（Accept）中所包含的类型。如

```java
@RequestMapping(value = "toUser",produces = "application/json")
```

除此之外，produces 属性还可以指定返回值的编码。如

```java
 @RequestMapping(value = "toUser",produces = "application/json,charset=utf-8")
```

表示返回 utf-8 编码。



1. **方法级别注解**

   ```java
   package net.biancheng.controller;
   import org.springframework.stereotype.Controller;
   import org.springframework.web.bind.annotation.RequestMapping;
   @Controller
   public class IndexController {
       @RequestMapping(value = "/index/login")
       public String login() {
           return "login";
       }
       @RequestMapping(value = "/index/register")
       public String register() {
           return "register";
       }
   }
   ```

   上述示例中有两个 RequestMapping 注解语句，它们都作用在处理方法上。在整个 Web 项目中，@RequestMapping 映射的请求信息必须保证全局唯一。

   用户可以使用如下 URL 访问 login 方法（请求处理方法），在访问 login 方法之前需要事先在 /WEB-INF/jsp/ 目录下创建 login.jsp。

2. **类级别注解**

   ```java
   package net.biancheng.controller;
   import org.springframework.stereotype.Controller;
   import org.springframework.web.bind.annotation.RequestMapping;
   @Controller
   @RequestMapping("/index")
   public class IndexController {
       @RequestMapping("/login")
       public String login() {
           return "login";
       }
       @RequestMapping("/register")
       public String register() {
           return "register";
       }
   }
   ```




## 4.3 @XXXMapping

- @PostMapping
- @GetMapping
- @DeleteMapping
- @PutMapping

用这几个注解可以省略@RequestMapping中的method属性



## 4.4 @ResponseBody

该注解配合@Controller注解使用，用在方法上，返回值会直接打印在页面上





# 5 REST风格

Spring REST 风格可以简单理解为：使用 URL 表示资源时，每个资源都用一个独一无二的 URL 来表示，并使用 HTTP 方法表示操作，即准确描述服务器对资源的处理动作（GET、POST、PUT、DELETE），实现资源的增删改查。

- GET：表示获取资源
- POST：表示新建资源
- PUT：表示更新资源
- DELETE：表示删除资源

```
//传统风格
http://localhost:8080?add=1&reduce=2

//RESTFUL
http://localhost:8080/add/1/reduce/2
```



```java
package cn.edu.xidian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RestfulController {

//传统请求url: http://localhost:8080/add?a=1&b=2
    @RequestMapping("/add")
    public String test(Model model,int a,int b){
        int res = a+b;
        model.addAttribute("msg","结果为"+res);
        return "test";
    }
    
    //restful请求url： http://localhost:8080/add/1/2
    @RequestMapping(value="/add/{a}/{b}",method = RequestMethod.GET)
    public String test2(Model model, @PathVariable int a, @PathVariable int b){
        int res = a+b;
        model.addAttribute("msg","结果为"+res);
        model.addAttribute("a","结果为"+a);
        model.addAttribute("b","结果为"+b);
        return "test";
    }
}
```



# 6 重定向和转发

转发是服务器行为，重定向是客户端行为。

**1）转发过程**

客户浏览器发送 http 请求，Web 服务器接受此请求，调用内部的一个方法在容器内部完成请求处理和转发动作，将目标资源发送给客户；在这里转发的路径必须是同一个 Web 容器下的 URL，其不能转向到其他的 Web 路径上，中间传递的是自己的容器内的 request。

在客户浏览器的地址栏中显示的仍然是其第一次访问的路径，也就是说客户是感觉不到服务器做了转发的。转发行为是浏览器只做了一次访问请求。

**2）重定向过程**

客户浏览器发送 http 请求，Web 服务器接受后发送 302 状态码响应及对应新的 location 给客户浏览器，客户浏览器发现是 302 响应，则自动再发送一个新的 http 请求，请求 URL 是新的 location 地址，服务器根据此请求寻找资源并发送给客户。

在这里 location 可以重定向到任意 URL，既然是浏览器重新发出了请求，那么就没有什么 request 传递的概念了。在客户浏览器的地址栏中显示的是其重定向的路径，客户可以观察到地址的变化。重定向行为是浏览器做了至少两次的访问请求。





在 Spring MVC 框架中，控制器类中处理方法的 return 语句默认就是转发实现，只不过实现的是转发到视图。示例代码如下：

```java
//不用再到springmvc配置文件中注册
@Controller
@RequestMapping(path="/hello")
public class HelloController {

    //直接设置访问路径
    @RequestMapping("/h1")
    public String hello1(Model model){
        //封装数据
        model.addAttribute("msg","springmvc");


        //这个hello就是要访问的页面名称 /WEB-INF/jsp/hello.jsp
        return "hello";//这个return的结果会被视图解析器处理
    }

}
```

在 Spring MVC 框架中，重定向与转发的示例代码如下：

```java
package cn.edu.xidian.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class RedirectAndForwardController {

    //访问url: http://localhost:8080/hello/redirect
    @RequestMapping("/redirect")
    public String redirect(){
        return "redirect:/hello/isRedirect";//1.重定向到一个请求方法
    }

    @RequestMapping("/isRedirect")
    public String isRedirect(){
    //2.这个请求方法重定向到视图页面
        return "redirect";
    }

    //访问url: http://localhost:8080/hello/forward
    @RequestMapping("/forward")
    public String forward(){
		//1.转发到一个请求方法
        return "forward:/hello/isForward";
    }

    @RequestMapping("/isForward")
    public String isForward(){
    	//2.转发到视图
        return "forward";
    }
}
```

重定向会改变url，转发不会改变url



在 Spring MVC 框架中，不管是重定向或转发，都需要符合视图解析器的配置，如果直接转发到一个不需要 DispatcherServlet 的资源，例如：

```
return "forward:/html/my.html";
```

则需要使用 mvc:resources 配置：

```
<mvc:resources location="/html/" mapping="/html/**" />
```



# 7 传递参数

前端页面表单

```html
<form action = "/hello/{age}/{name}/{pet.name}" method = get>
    <input name="age" type="text"/>
    <input name="name" type="text"/>
    <input name="pet.name" type="text"/>
    <input type="submit"/>
</form>
```





## 7.1 通过处理方法的形参接收请求参数

通过url请求给方法传递参数，形参名和url路径名称完全相同。用于接收get和post

```java
//这样的形式通过测试发现，如果直接写url可以识别，但是通过表单提交不行
package cn.edu.xidian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GetParameters {
    //http://localhost:8080/login?name=ligen&pwd=123
    @RequestMapping("/login")
    public String test1(String name, String pwd, Model model){
        model.addAttribute("name",name);
        model.addAttribute("pwd",pwd);
        return "test";
    }
}
```



## 7.2 通过@PathVariable接收URL中的请求参数

该注解用来实现[restful风格](# 5 REST风格)的传参

```java
package cn.edu.xidian.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GetParameters {
    //请求url:http://localhost:8080/login/ligen/12314
    @RequestMapping("/login/{name}/{pwd}")
    public String test1(@PathVariable String name, @PathVariable String pwd, Model model){
        model.addAttribute("name",name);
        model.addAttribute("pwd",pwd);
        return "test";
    }
}

```



## 7.3 通过@RequestParam接收请求参数传参

上面的方式中，如果url中参数名称有问题就会访问失败，该注解可以将url参数名称与方法形参名称对应，但是这样通过方法形参名称就不能再访问了

```java
package cn.edu.xidian.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GetParameters {
    //http://localhost:8080/login?username=ligen&pwd=123
    //http://localhost:8080/login?name=ligen&pwd=123会访问失败
    @RequestMapping("/login")
    public String test1(@RequestParam("username") String name, String pwd, Model model){
        model.addAttribute("name",name);
        model.addAttribute("pwd",pwd);
        return "test";
    }
}

```



## 7.3 通过bean实体接收请求参数

```java
package cn.edu.xidian.controller;

import cn.edu.xidian.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GetParameters {
    //http://localhost:8080/login?name=ligen&pwd=123
    //自动将name和pwd装配为user了
    @RequestMapping("/login")
    public String test1(User user, Model model){
        model.addAttribute("name",user.getName());
        model.addAttribute("pwd",user.getPwd());
        return "test";
    }
}

```



## 7.4 通过HttpServletRequest接收请求参数

```java
package cn.edu.xidian.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GetParameters {
    @RequestMapping("/login")
    //http://localhost:8080/login?name=ligen&pwd=123
    public String test1(HttpServletRequest request, Model model){
        model.addAttribute("name",request.getParameter("name"));
        model.addAttribute("pwd",request.getParameter("pwd"));
        return "test";
    }
}
```



## 7.5 通过@ModelAttribute接收请求参数









