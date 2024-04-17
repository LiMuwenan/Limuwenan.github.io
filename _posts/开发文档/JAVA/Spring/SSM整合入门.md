# 建表

```mysql
USE spring;

DROP TABLE IF EXISTS `books`;

CREATE TABLE IF NOT EXISTS `books`(
	`book_id` INT(10) NOT NULL AUTO_INCREMENT COMMENT '书id',
	`book_name` VARCHAR(100) NOT NULL COMMENT '书名',
	`book_nums` INT(11) NOT NULL COMMENT '书数量',
	`book_detail` VARCHAR(200) NOT NULL COMMENT '描述',
	PRIMARY KEY (`book_id`)
)ENGINE = INNODB;

INSERT INTO `books` (`book_name`,`book_nums`,`book_detail`) 
VALUES('Java',2,'核心卷'),
			('MySQL',10,'必知必会'),
			('Linux',3,'鸟哥');
```

# 导入依赖

```xml
<dependencies>
    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.21</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.5</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/junit/junit -->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
        <scope>test</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.0.RELEASE</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/com.mchange/c3p0 -->
    <dependency>
        <groupId>com.mchange</groupId>
        <artifactId>c3p0</artifactId>
        <version>0.9.5.2</version>
    </dependency>
    <!--spring-jdbc-->
    <!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-jdbc</artifactId>
        <version>5.2.0.RELEASE</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis-spring</artifactId>
        <version>2.0.2</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/javax.servlet/servlet-api -->
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>servlet-api</artifactId>
        <version>2.5</version>
        <scope>provided</scope>
    </dependency>
    <!-- https://mvnrepository.com/artifact/javax.servlet.jsp/jsp-api -->
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>jsp-api</artifactId>
        <version>2.2</version>
        <scope>provided</scope>
    </dependency>
</dependencies>

<!--资源文件导出-->
<build>
    <resources>
        <resource>
            <directory>src/main/java</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
        <resource>
            <directory>src/main/resources</directory>
            <includes>
                <include>**/*.properties</include>
                <include>**/*.xml</include>
            </includes>
            <filtering>false</filtering>
        </resource>
    </resources>
</build>
```

# mybatis模块

## 配置数据库连接池

创建文件database.properties

```
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/spring?userSSL=false&userUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
jdbc.username=root
jdbc.password=150621
```

## mybatis配置文件

mybatis-config.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    
<!--    mybatis数据源的事情交给spring做-->
    <typeAliases>
        <package name="cn.edu.xidian.domain"/>
    </typeAliases>

</configuration>
```

## 实体类

```java
package cn.edu.xidian.domain;

public class Books {
    private int book_id;
    private String book_name;
    private int book_nums;
    private String book_detail;
}

```

### 书籍数据接口

```java
package cn.edu.xidian.dao;

import cn.edu.xidian.domain.Books;

import java.util.List;

public interface BookMapper {

    //增加书籍
    int addBooks(Books books);

    //删除书籍
    int deleteBooks(int id);


    //更新书籍
    int updateBooks(Books books);

    //查询一本书籍
    Books queryBookById(int id);

    //查询所有书籍
    List<Books> queryBooks();
}

```

### 映射文件

BookMapper.xml

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.edu.xidian.dao.BookMapper">
        <insert id="addBooks" parameterType="Books">
                INSERT INTO spring.books(book_id, book_name, book_nums, book_detail) 
                VALUES (#{book_id},#{book_name},#{book_nums},#{book_detail});
        </insert>
        
        <delete id="deleteBookById" parameterType="int">
                DELETE FROM spring.books WHERE book_id = #{book_id};
        </delete>
        
        <update id="updateBooks" parameterType="Books">
                UPDATE spring.books 
                SET book_name = #{book_name},book_nums=#{book_nums},book_detail=#{book_detail} 
                WHERE book_id = #{book_id};
        </update>
        
        <select id="queryBookById" parameterType="int">
                SELECT book_id,book_name,book_nums,book_detail 
                FROM spring.books WHERE book_id = #{book_id};
        </select>

        <select id="queryBooks" resultType="Books">
                SELECT book_id,book_name,book_nums,book_detail FROM spring.books;
        </select>

</mapper>
```

### 绑定映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    
<!--    mybatis数据源的事情交给spring做-->
    <typeAliases>
        <package name="cn.edu.xidian.domain"/>
    </typeAliases>
    
    <mappers>
        <mapper resource="cn/edu/xidian/dao/BookMapper.xml"/>
    </mappers>

</configuration>
```

### 创建BookService并实现

```java
package cn.edu.xidian.service;

import cn.edu.xidian.domain.Books;

import java.util.List;

public interface BookService {

    //增加书籍
    int addBooks(Books books);

    //删除书籍
    int deleteBookById(int id);


    //更新书籍
    int updateBooks(Books books);

    //查询一本书籍
    Books queryBookById(int id);

    //查询所有书籍
    List<Books> queryBooks();
}

```



```java
package cn.edu.xidian.service;

import cn.edu.xidian.dao.BookMapper;
import cn.edu.xidian.domain.Books;

import java.util.List;

public class BookServiceImpl implements BookService{
    //service调用dao

    private BookMapper bookMapper;

    public BookServiceImpl() {
    }

    public BookServiceImpl(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public int addBooks(Books books) {
        return bookMapper.addBooks(books);
    }

    @Override
    public int deleteBookById(int id) {
        return bookMapper.deleteBookById(id);
    }

    @Override
    public int updateBooks(Books books) {
        return bookMapper.updateBooks(books);
    }

    @Override
    public Books queryBookById(int id) {
        return bookMapper.queryBookById(id);
    }

    @Override
    public List<Books> queryBooks() {
        return bookMapper.queryBooks();
    }
}

```

# Spring模块

## spring整合dao

spring-dao.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

<!--    1.关联数据库配置文件-->
    <context:property-placeholder location="classpath:database.properties"/>
<!--    2.连接池-->
<!--    spring原生连接池org.springframework.jdbc.datasource.DriverManagerDataSource-->
    <bean id="datasource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

<!--    3.sqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="datasource"/>
<!--        绑定mybatis配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>
    
<!--    4.配置dao接口扫描包，动态实现dao接口注入到容器中-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
<!--        value值和上面相同，就会动态将上面加载到下面来-->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
<!--        扫描dao接口-->
        <property name="basePackage" value="cn.edu.xidian.dao"/>
    </bean>
</beans>
```

## spring整合service

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

<!--    1.扫描service下的包,提供注解支持-->
    <context:component-scan base-package="cn.edu.xidian.service"/>

<!--    2.将我们所有业务类注入到spring，而可以通过配置或者注解实现-->
<!--    注解：BookMapperImpl加@Service 字段加@Autowired-->
    <bean id="bookServiceImpl" class="cn.edu.xidian.service.BookServiceImpl">
        <property name="bookMapper" ref="bookMapper"/>
    </bean>

<!--    3.声明事务配置-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
<!--        注入数据源-->
        <property name="dataSource" ref="datasource"/>
    </bean>
    
<!--    4.aop横切事务-->
</beans>
```

# SpringMVC模块

## 增加web支持

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

<!--    1.DispathcerServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:applicationContext.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>


<!--    2.乱码过滤-->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

<!--    3.默认session-->
    <session-config>
        <session-timeout>15</session-timeout>
    </session-config>


</web-app>
```

## 配置springmvc配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/mvc
       https://www.springframework.org/schema/mvc/spring-mvc.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

<!--    1.注解驱动-->
    <mvc:annotation-driven/>
<!--    2.静态资源过滤-->
    <mvc:default-servlet-handler/>
<!--    3.扫描包controller-->
    <context:component-scan base-package="cn.edu.xidian.controller"/>
<!--    4.视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```

# 查询书籍

## 创建首页

```jsp
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/9/6
  Time: 16:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>首页</title>

    <style>
      a{
        text-decoration: none;
        color:black;
        font-size: 18px;
      }
      h3{
        width:180px;
        height:38px;
        margin: 100px auto;
        text-align: center;
        line-height: 38px;
        background: deepskyblue;
        border-radius: 5px;
      }
    </style>

  </head>
  <body>
  <h3>
    <a href="${pageContext.request.contextPath}/book/allBook">进入书籍页面</a>
  </h3>

  </body>
</html>

```

## 创建展示页面

使用到了bootstrap样式，以及jstl

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/9/6
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书籍展示</title>
    <%--BootStrap美化界面--%>

    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="page-header">
                    <h1>
                        <small>书籍列表---显示所有书籍</small>
                    </h1>
                </div>
            </div>
        </div>

        <div class="row clearfix">
            <div class="col-md-12 column">
                <table class="table table-hover table-striped">
                    <thead>
                        <tr>
                            <th>书籍编号</th>
                            <th>书籍名称</th>
                            <th>书籍数量</th>
                            <th>书籍详情</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="book" items="${books}">
                            <tr>
                                <td>${book.book_id}</td>
                                <td>${book.book_name}</td>
                                <td>${book.book_nums}</td>
                                <td>${book.book_detail}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</body>
</html>

```

## BookController

```java
package cn.edu.xidian.controller;


import cn.edu.xidian.domain.Books;
import cn.edu.xidian.service.BookService;
import cn.edu.xidian.service.BookServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {

    //controller调用service
    @Autowired
    @Qualifier("bookServiceImpl")
    private BookService bookService;

    //查询全部的书籍，并且返回到一个书籍展示页面
    @RequestMapping("/allBook")
    public String list(Model model){
        List<Books> books = bookService.queryBooks();

        model.addAttribute("books",books);
        return "allBook";
    }
}

```

# 添加书籍

## BookController

```java
//跳转到增加书籍页面
@RequestMapping("/toAddBook")
public String toAddBook(){
    return "addBook";
}

//添加书籍的请求
@RequestMapping("/addBook")
public String addBook(Books books){
    System.out.println("addBook=>"+books);
    bookService.addBooks(books);
    return "redirect:/book/allBook";
}
```

## 展示页面新增

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/9/6
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书籍展示</title>
    <%--BootStrap美化界面--%>

    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="page-header">
                    <h1>
                        <small>书籍列表---显示所有书籍</small>
                    </h1>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-4 column">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAddBook">新增书籍</a>
            </div>
        </div>

        <div class="row clearfix">
            <div class="col-md-12 column">
                <table class="table table-hover table-striped">
                    <thead>
                        <tr>
                            <th>书籍编号</th>
                            <th>书籍名称</th>
                            <th>书籍数量</th>
                            <th>书籍详情</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="book" items="${books}">
                            <tr>
                                <td>${book.book_id}</td>
                                <td>${book.book_name}</td>
                                <td>${book.book_nums}</td>
                                <td>${book.book_detail}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</body>
</html>

```

## 创建添加书籍页面

```jsp
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/9/7
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增书籍</title>
    <%--BootStrap美化界面--%>

    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="page-header">
                    <h1>
                        <small>新增书籍</small>
                    </h1>
                </div>
            </div>
        </div>

        <form action="${pageContext.request.contextPath}/book/addBook" method="post">
            <div class="form-group">
                <label>书籍编号</label>
                <input type="text" name="book_id" class="form-control" required/>
            </div>
            <div class="form-group">
                <label>书籍名称</label>
                <input type="text" name="book_name" class="form-control" required/>
            </div>
            <div class="form-group">
                <label >书籍数量</label>
                <input type="text" name="book_nums" class="form-control" required/>
            </div>
            <div class="form-group">
                <label >书籍详情</label>
                <input type="text" name="book_detail" class="form-control" required/>
            </div>
            <div class="form-group">
                <input type="submit" class="form-control" value="添加"/>
            </div>
        </form>

    </div>

</body>
</html>

```

# 修改删除书籍

## BookConntroller

```java
//跳转到修改页面
@RequestMapping("/toUpdateBook")
public String toUpdateBook(int id, Model model){
    Books books = bookService.queryBookById(id);
    model.addAttribute("qBook",books);
    return "updateBook";
}

//修改书籍
@RequestMapping("/updateBook")
public String updateBook(Books books){
    bookService.updateBooks(books);

    return "redirect:/book/allBook";
}

//删除书籍
@RequestMapping("/deleteBook/{book_id}")
public String deleteBook(@PathVariable("book_id") int id){
    bookService.deleteBookById(id);
    return "redirect:/book/allBook";
}
```

## 创建修改页面，基本同新增页面

```jsp
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/9/7
  Time: 13:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改书籍</title>
    <%--BootStrap美化界面--%>

    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>修改书籍</small>
                </h1>
            </div>
        </div>
    </div>

    <form action="${pageContext.request.contextPath}/book/updateBook" method="post">
        <%--因为编号不需要修改，但是又需要传值，所以将这个编号设置为隐藏域--%>
        <input type="hidden" name="book_id" value="${qBook.book_id}"/>
        <div class="form-group">
            <label>书籍编号</label>
            <input type="text" name="book_id" class="form-control" value="${qBook.book_id}" required/>
        </div>
        <div class="form-group">
            <label>书籍名称</label>
            <input type="text" name="book_name" class="form-control" value="${qBook.book_name}" required/>
        </div>
        <div class="form-group">
            <label >书籍数量</label>
            <input type="text" name="book_nums" class="form-control" value="${qBook.book_nums}" required/>
        </div>
        <div class="form-group">
            <label >书籍详情</label>
            <input type="text" name="book_detail" class="form-control" value="${qBook.book_detail}" required/>
        </div>
        <div class="form-group">
            <input type="submit" class="form-control" value="修改"/>
        </div>
    </form>

</div>

</body>
</html>

```

## 展示页面新增

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/9/6
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书籍展示</title>
    <%--BootStrap美化界面--%>

    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="page-header">
                    <h1>
                        <small>书籍列表---显示所有书籍</small>
                    </h1>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-4 column">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAddBook">新增书籍</a>
            </div>
        </div>

        <div class="row clearfix">
            <div class="col-md-12 column">
                <table class="table table-hover table-striped">
                    <thead>
                        <tr>
                            <th>书籍编号</th>
                            <th>书籍名称</th>
                            <th>书籍数量</th>
                            <th>书籍详情</th>
                            <th>操作</th>
                        </tr>
                    </thead>

                    <tbody>
                        <c:forEach var="book" items="${books}">
                            <tr>
                                <td>${book.book_id}</td>
                                <td>${book.book_name}</td>
                                <td>${book.book_nums}</td>
                                <td>${book.book_detail}</td>
                                <td>
                                    <a href="${pageContext.request.contextPath}/book/toUpdateBook?id=${book.book_id}">修改</a>
                                    &nbsp; | &nbsp;
                                    <a href="${pageContext.request.contextPath}/book/${book.book_id}">删除</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</body>
</html>

```

# 搜索功能

## 修改展示页面

增加搜索框

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/9/6
  Time: 16:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>书籍展示</title>
    <%--BootStrap美化界面--%>

    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="page-header">
                    <h1>
                        <small>书籍列表---显示所有书籍</small>
                    </h1>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-md-4 column">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAddBook">新增书籍</a>
            </div>
            <div class="col-md-4 column"></div>
            <div class="col-md-4 column">
                <%--搜索书籍--%>
                <form action="${pageContext.request.contextPath}/book/searchBook" method="get" style="float:right">
                    <input type="text" placeholder="请输入要查询的书籍名称" name="searchBookName" class="form-control"/>
                    <input type="submit" value="查询" class="btn btn-primary"/>
                </form>
            </div>
        </div>

        <div class="row clearfix">
        	<%--其余部分--%>
        </div>
    </div>

</body>
</html>

```

## 创建搜索详情页面

```jsp
<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/9/7
  Time: 15:15
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>详情</title>
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>书籍列表---显示搜索书籍</small>
                </h1>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4 column">
            <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAddBook">新增书籍</a>
        </div>
        <div class="col-md-4 column"></div>
        <div class="col-md-4 column">
            <%--搜索书籍--%>
            <form action="${pageContext.request.contextPath}/book/searchBook" method="get" style="float:right">
                <input type="text" placeholder="请输入要查询的书籍名称" name="searchBookName" class="form-control"/>
                <input type="submit" value="查询" class="btn btn-primary"/>
            </form>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th>书籍编号</th>
                    <th>书籍名称</th>
                    <th>书籍数量</th>
                    <th>书籍详情</th>
                    <th>操作</th>
                </tr>
                </thead>

                <tbody>
                    <tr>
                        <td>${books.book_id}</td>
                        <td>${books.book_name}</td>
                        <td>${books.book_nums}</td>
                        <td>${books.book_detail}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/book/toUpdateBook?id=${books.book_id}">修改</a>
                            &nbsp; | &nbsp;
                            <a href="${pageContext.request.contextPath}/book/deleteBook/${books.book_id}">删除</a>
                        </td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>

```

## 修改BookMapper接口

```java
//根据名称搜索书籍
Books searchBookByName(String name);
```

```xml
<select id="searchBookByName" resultType="Books" parameterType="String">
        SELECT book_id,book_name,book_nums,book_detail FROM spring.books WHERE book_name=#{book_name};
</select>
```

## 修改BookService接口

```java
//根据名称搜索书籍
Books searchBookByName(String name);
```

实现类

```java
@Override
public Books searchBookByName(String name) {
    return bookMapper.searchBookByName(name);
}
```
