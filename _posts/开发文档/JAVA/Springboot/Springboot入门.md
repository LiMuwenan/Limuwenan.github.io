# 1 简介

[Springboot官方下载安装](https://spring.io/projects/spring-boot#overview)

[Springboot官方手册](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)

# 2 HelloSpringBoot

两种创建项目方式

- 从官网下载demo导入

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/Springboot/demo项目结构.png" style="zoom:75%;" />

创建controller等包需要在demo包下，否则启动会错误。只能扫描application启动类同级的包或者子包(这里DemoApplication就是启动类)

依赖，是自动配置好的

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.5.4</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>cn.edu.xidian</groupId>
	<artifactId>demo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>demo</name>
	<description>Demo project for Spring Boot</description>
	<properties>
		<java.version>1.8</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

    <build>
        <!--		打jar包插件-->
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```

在controller中写一个接口就能立刻使用

```java
package cn.edu.xidian.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @RequestMapping("/hello")
    public String hello(){
        return "Hello World!";
    }
}

```

- 从idea创建

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/Springboot/idea创建1.jpg" style="zoom:67%;" />

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/Springboot/idea创建2.jpg" alt="idea创建2" style="zoom:67%;" />

在resources中添加banner.txt可以自己设置启动文字（佛祖）



# 3 Springboot自动配置原理

## 3.1 依赖配置

如果我们需要引入jar包，只需要配置`groupId`和`artifactId`，版本号springboot帮我们自动配置好了

需要的jar包依旧需要引入依赖，只是不用写版本号了



```XML
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
</dependencies>  
```

如果不想使用springboot自动配置的版本，我们可以使用如下形式填写自己想要的版本号

具体的xxx需要在pom文件中查找

```XML
<properties>
	<xxx.version>1.8</xxx.version>
</properties>
```



## 3.2 启动器

在pom.xml中，可以找到springboot的父配置，其中配置了很多jar包依赖

springboot将所有的功能场景，变为一个个的启动器，需要什么功能，就加入什么[启动器](https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters)



**主程序**

```java
//该注解标注这个类是一个Springboot应用
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        
        //获取到run可以getBean
        //ConfigurableApplicationContext run =  SpringApplication.run(DemoApplication.class, args);
        //System.out.println(run.getBean("getCar"));
    }

}
```

**注解**

```
@SpringBootConfiguration：springboot的配置
    @Configuration：spring配置类
    @Component：spring组件

@EnableAutoConfiguration：自动配置
    @AutoConfigurationPackage：自动配置包
```

## 3.3 包扫描

springboot会自动扫描主程序级别的包以及子包

如果想要扫描上级的包就需要加注解

```
@SpringBootApplication(scanBasePackages = "cn.edu")
或者
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan("cn.edu")
```

## 3.4 热部署

在pom文件中导入

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

修改项目之后使用ctrl+f9



付费功能使用jrebel



# 4 注解

## 4.1 @SpringBootApplication

该注解标注该类是springboot应用

```java
//该注解标注这个类是一个Springboot应用
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
```

该注解是以下三个注解的合成注解

```java
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(
    excludeFilters = {@Filter(
    type = FilterType.CUSTOM,
    classes = {TypeExcludeFilter.class}
), @Filter(
    type = FilterType.CUSTOM,
    classes = {AutoConfigurationExcludeFilter.class}
)}
)

@SpringBootConfiguration -》 @Configuration 代表当前是一个配置类
@ComponentScan 指定包扫描
@EnableAutoConfiguration -》 @AutoConfigurationPackage 自动配置
						 -》 @Import({AutoConfigurationImportSelector.class}) 加载了127个场景的自动配置，启动的时候默认全部加载，最终会按需配置(@Conditional)
```



## 4.2 @Configuration

将该类变为spring容器的组件

配置类也是组件

```java
//组件默认为单例
//@configuration(proxyBeanMethods = false) //默认为true，false就不是单例了
@Configuration //告诉Springboot这是一个配置类
public class MyConfig {

    @Bean //从容器中添加组件，以方法名作为组件id，返回类型是组件类型。这个代替spring-beans.xml的配置
    public User user(){
        return new User("ligen",18);
    }

}
```



## 4.3 @Import

以数组形式，将特定的类以无参构造的形式创建组件加入容器

```java
@Import({User.class, DBHelper.class})//以数组形式，将特定的类以无参构造的形式创建组件
@Configuration //告诉Springboot这是一个配置类
public class MyConfig {

    @Bean //从容器中添加组件，以方法名作为组件id，返回类型是组件类型。这个代替spring-beans.xml的配置
    public User user(){
        return new User("ligen",18);
    }

}
```



## 4.4 @Conditional

这个组件可以用在类或者方法上

达到某种条件就注册组件等等，可以查看源码看该注解的继承树

```java
@ConditionalOnBean(name = "tom")//容器中有tom组件时才注册user组件
@Bean //从容器中添加组件，以方法名作为组件id，返回类型是组件类型。这个代替spring-beans.xml的配置
public User user(){
return new User("ligen",18);
}
```



## 4.5 @ImportResource

用在类前，将指定路径下的配置文件解析为配置类

```java
@ImportResource("classpath:spring-beans.xml")
```



## 4.6 @ConfigurationProperties

从 配置文件中读取数据，prefix前缀代表在配置文件中的前缀

```java
@Component//只有在容器中的组件才能用下面的注解
@ConfigurationProperties(prefix = "mycar")
public class Car {

    private String brand;
    private Integer price;
}
```



```
myCar.brand=BYD
myCar.price=10
```



## 4.7 JSR303校验

### 4.7.1 @Email

该注解标注该字段必须是邮箱的格式

```java
@Email()
private String lastName;
```

该校验用于在后端检验数据是否合法：比如必须为null，或者不为null，或者为邮箱格式等



### 4.7.2

新版本需要导入该依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

并且类前面需要加注解

```
@Validated
```

以下注解使用在字段前

- **@Null**	对象必须为null
- **@NotNull**	对象必须不为null，无法检查长度为0的字符串
- **@NotBlank**	字符串必须不为Null，且去掉前后空格长度必须大于0
- **@AssertTrue**	对象必须为true
- **@AssertFalse**	对象必须为false
- **@Max(Value)**	必须为数字，且小于或等于Value
- **@Min(Value)**	必须为数字，且大于或等于Value
- **@DecimalMax(Value)**	必须为数字( BigDecimal )，且小于或等于Value。小数存在精度
- **@DecimalMin(Value)**	必须为数字( BigDecimal )，且大于或等于Value。小数存在精度
- **@Digits(integer,fraction)**	必须为数字( BigDecimal )，integer整数精度，fraction小数精度
- **@Size(min,max)**	对象(Array、Collection、Map、String)长度必须在给定范围
- **@Email**	字符串必须是合法邮件地址
- **@Past**	Date和Calendar对象必须在当前时间之前
- **@Future**	Date和Calendar对象必须在当前时间之后
- **@Pattern(regexp=“正则”)**	String对象必须符合正则表达式



# 5 springboot配置

再/resources/文件夹下有springboot的全局配置文件，该文件可以是.properties或者.yml(.yaml)

```yaml
#application.properties
	语法结构：key=value
	
    name=ligen
	student.name=ligen
	student.age=20
	
#application.yml
	语法结构：key: 空格 value
	
	name: ligen
	#对象
	student:
	 name: ligen
	 age: 20
	 
	#数组
	pets: 
	 - cat
	 - dog
```

## 5.1 使用yaml配置文件为对象赋值

实体类

```java
package cn.edu.xidian.domain;

import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Component("person")
@ConfigurationProperties(prefix = "person")
public class Person {
    private String name;
    private Integer age;
    private Boolean happer;
    private Date birth;
    private Map<String,Object> maps;
    private List<Object> lists;
    private Dog dog;

}	

```

application.yaml

```yaml
person:
  name: ligen
  age: 20
  happy: false
  birth: 1997/01/21
#  maps: {english: 80, math: 90 }
  maps:
    english: 80
    math: 20
# lists: [code, music, girl]
  lists:
    - code
    - music
    - girl
  dog:
    name: 旺财
    age: 3
    
    # 还可以这样写,把ko的值赋值给age
    ko: 123
    age: ${person.ko}
```

测试类

```java
@SpringBootApplication
public class YamlApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext run = SpringApplication.run(YamlApplication.class, args);

        Person person = run.getBean("person",Person.class);

        System.out.println(person);
    }

}
```





导入yaml依赖可以在写yaml时候又提示

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-configuration-processor</artifactId>
    <optional>true</optional>
</dependency>
```

然后再配置一下，使打jar包的时候不包含该配置

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <excludes>
                    <exclude>
                        <groupId>org.springframework.boot</groupId>
                        <artifactId>spring-boot-configuration-processor</artifactId>
                    </exclude>
                </excludes>
            </configuration>
        </plugin>
    </plugins>
</build>
```



## 5.2 JSR303校验





## 5.3 配置文件位置

springboot支持几个配置文件位置，配置文件优先级从上到下

- ./config/application.yaml（.是项目根目录）
- ./application.yaml

- classpath: ./config/application.yaml（.是resources目录）
- classpath: ./application.yaml



# 6 web开发

## 6.1 静态资源访问

### 6.1.1 静态资源目录

静态资源目录：resource文件夹下

- \static
- \public
- \resources
- \META-INF\resources

在这几个路径下的静态资源文件，可以用过url地址（http://localhost:8080/re.png）访问



因为静态资源映射是

```
spring.mvc.static-path-pattern=/resources/**
```

这意味着，当controller请求处理不了的时候，就会给静态资源访问，能够访问到就返回，访问不到则失败

如果有controller路径和静态资源相同，先访问controller



### 6.1.2 静态资源访问前缀

静态资源访问默认是没有前缀的，像上面的访问路径（http://localhost:8080/re.png）

我们可以配置一个访问前缀

```yaml
spring:
  mvc:
    static-path-pattern: /res/**
```

这个时候就必须访问（http://localhost:8080/res/re.png）



### 6.1.3 自定义静态资源文件夹

```yaml
spring:
  mvc:
    static-path-pattern: /res/**

  web:
    resources:
      static-locations: [classpath:/haha/]
```

这样就可以访问到自定义文件夹/haha/的内容





## 6.2 欢迎页面

在静态资源目录放`index.html`

然后通过访问项目根目录即可访问到该页面（好像是小bug，如果加了静态资源访问前缀，是访问不到的，就必须访问/前缀/index.html）



## 6.3 Favicon

将名称为favicon.ico的图片放到静态资源目录，即可自动访问到



## 6.4 参数传递

参考文档[Spring MVC入门 # 7 传递参数](../Spring/SpringMVC入门.md/# 7 传递参数)



# 7 视图解析与模板引擎

springboot默认打jar包，jsp页面不能再压缩包内渲染，所以springboot默认不支持jsp页面

springboot建议我们使用thymleaf模板引擎

导入依赖

```xml
<!--        thymleaf-->
<!--        启动器里包含下面两个-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>

<dependency>
    <groupId>org.thymeleaf</groupId>
    <artifactId>thymeleaf-spring5</artifactId>
</dependency>
<dependency>
    <groupId>org.thymeleaf.extras</groupId>
    <artifactId>thymeleaf-extras-java8time</artifactId>
</dependency>
</dependencies>
```



html页面添加头

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org">
```



## 7.1 语法基础

- Simple expressions
  - Variable Expressions: `${...}`
  - Selection Varible Expressions:`*{...}`
  - Message Expressions(国际化消息):`#{...}`
  - Link URL Expressions:`@{...}`
  - Fragment Expressions:`~{...}`
- Literals
  - Text literals: `'one text','Another one!'`
  - Number literals: `0 , 34 , 3.0 , 12.3 ,…`
  - Boolean literals:`true , false`
  - Null literal: `null`
  - Literal tokens: `one , sometext , main ,…`

- Text operations:
  - String concatenation:` +`
  - Literal substitutions: `|The name is ${name}|`
- Arithmetic operations:
  - Binary operators: `+ , - , * , / , %`
  - Minus sign (unary operator): `-`
- Boolean operations:
  - Binary operators: `and , or`
  - Boolean negation (unary operator): `! , not`
- Comparisons and equality:
  - Comparators: `> , < , >= , <= ( gt , lt , ge , le )`
  - Equality operators:` == , != ( eq , ne )`
- Conditional operators:
  - If-then:` (if) ? (then)`
  - If-then-else:` (if) ? (then) : (else)`
  - Default: `(value) ?: (defaultvalue)`
- Special tokens:
  - No-Operation: `_`



### 7.1.1 页面取值

thymeleaf在进行页面取值的时候，例如这样的语句`${page.from}`，即使`page`对象并没有`from`字段，它也会自动调用`page`里面的`getFrom`方法来获取变量





controller

```java
@Controller
public class MyRequest {

    @RequestMapping("/hello/{id}/{name}/{pet.name}")
    public String hello(Person person){
        System.out.println(person.getAge());
        System.out.println(person.getName());
        System.out.println(person.getPet().getName());
        return "id+name";
    }

    @RequestMapping("/hello/thy")
    public String thymeleaf(Model model){
        model.addAttribute("msg1","<h5>ligen1</h5>");
        model.addAttribute("msg2","ligen2");
        model.addAttribute("msg3","ligen3");
        model.addAttribute("msg4","ligen4");
        model.addAttribute("msg5","<h5>ligen4</h5>");
        model.addAttribute("link","http://www.baidu.com");
        return "test";
    }

    @RequestMapping("/h1/thy")
    public String thymeleaf1(Model model){
        model.addAttribute("link","http://www.baidu.com");
        return "test";
    }
}
```

html，页面放在templates文件夹中

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="">
    <!--xmlns:th="http://www.thymeleaf.org"取值会报错-->
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>

<div th:text="${msg1}">name</div><!--获取到后不会对内容进行转义-->
<h1 th:text="*{msg2}">name</h1>
<h1 th:text="#{msg3}">name</h1><!--这是输出内容  国际化相关 ??msg3_zh_CN??-->
<h1 th:text="@{msg4}">name</h1>
<div th:utext="${msg5}"></div><!--获取到后会对内容进行转义，显示成html形式-->

<div>
    <!--下面的hello是在requestMapping中配置的，这个会被替代，如果是在配置文件中配置server.servlet.context-path，这个hi不会被替代的-->
    <!--去内部链接，在当前链接后拼接link本身(没有取出值),相对路径地址,转到http://localhost:8080/hello/link-->
    <!--访问路径为http://localhost:8080/hello/thy,这里只会把最后的thy替换成link-->
    <a href="www.bilibili.com" th:href="@{link}">去bilibili</a>
    <!--去内部链接，在当前链接后拼接link本身(没有取出值),相对路径，转到http://localhost:8080/link-->
    <a href="www.bilibili.com" th:href="@{/link}">去bilibili</a>
    <a href="www.bilibili.com" th:href="${link}">去bilibili</a><!--去外部链接-->
</div>

</body>
</html>
```

当不想在标签中使用thymeleaf时，可以使用行内写法

```html
<p>
    [[${msg}]]
</p>
```

如果需要同时将固定值和动态值放在一个引号中，可以这样写

用两个竖线将所有内容括起来

```
<p th:href="@{|/discuss/detail/${map.post.id}|}"> </p>
```



## 7.2 公共页面抽取

创建一个页面，命名为common（实际名称随意），再其中定义一个公共片段

```html
<footer th:fragment="copy">
  &copy; 2011 The Good Thymes Virtual Grocery
</footer>
```

我们通常使用三种方式：

- th:insert
- th:replace
- th:include

其中common应该替换为该文件相对template文件夹的相对路径

```html
<body>

  ...

  <div th:insert="common :: copy"></div>

  <div th:replace="common :: copy"></div>

  <div th:include="common :: copy"></div>
  
</body>
```

语法严谨一些可以是

```html
<body>

  ...

  <div th:insert="~{common :: copy}"></div>

  <div th:replace="~{common :: copy}"></div>

  <div th:include="~{common :: copy}"></div>
  
</body>
```

得到结果

```html
<body>

  ...

  <div>
    <footer>
      &copy; 2011 The Good Thymes Virtual Grocery
    </footer>
  </div>

  <footer>
    &copy; 2011 The Good Thymes Virtual Grocery
  </footer>

  <div>
    &copy; 2011 The Good Thymes Virtual Grocery
  </div>
  
</body>
```



## 7.3 内容遍历

当我们在对表格的内容进行填充时，大量的内容不可能一一手打，所以使用模板自带的循环遍历的方式进行填充



创建内容并添加到上下文环境

```java
//模板循环遍历内容
List<User> users = Arrays.asList(new User("ligen","123"),
        new User("wendan","456"),
        new User("zy","789"));
model.addAttribute("users",users);
```

前端取值

```html
<tr class="gradeX"  th:each="user:${users}">
    <td>id</td>
    <td th:text="${user.username}"></td>
    <td th:text="${user.pwd}">Win 95+</td>
</tr>
```

所以就是依靠这个th:each

```html
th:each="user:${users}"
对users的内容进行遍历，每项的内容保存在user中，然后通过${}符号进行取值
```



### 7.3.1 遍历状态

在使用`th:each`进行遍历的时候，还有一个遍历状态内容可选，

- index：当前索引（从0开始）
- count：当前计数（从1开始）
- size：总数
- current：当前对象
- even/odd：当前迭代是偶数还是奇数（下标按计数方式，从1开始）
- first：是否为第一个
- last：是否为最后一个

```html
<tr class="gradeX"  th:each="user,status:${users}">
    <td th:text="${status.count}">id</td>
    <td th:text="${user.username}"></td>
    <td th:text="${user.pwd}"></td>
</tr>
```

添加一个`status`状态，即可使用上面的属性对遍历状态进行监控



### 7.3.2 生成数列

该语句自动生成一个等差数列

```html
th:each="i:${#numbers.sequence(page.getFrom,page.to)}"
```



## 7.4 条件判断

条件成立才会显示

```html
<span th:if="${count==1}">我的</span>
```



## 7.5 日期格式化

```html
<span th:text="${#dates.format(canshu,'yyyy-MM-dd HH:mm:ss')}"></span>
```

格式化日期输出，可以指定任意格式的日期



## 7.6 class状态

`th:class="|page-item ${page.current==i?'active':''}|"`，到哪个页面哪里就是激活的状态，这里用`||`括起来，静态值会一直显示，动态值通过三目运算符获得

```html
<li th:class="|page-item ${page.current==i?'active':''}|" th:each="i:${#numbers.sequence(page.getFrom,page.to)}">
            <a class="page-link" th:href="@{${page.path}(current=${i})}" th:text="${i}">1</a>
</li>
```



7.7 



# 8 拦截器

挨个给每个页面的请求添加一个登录状态检查是十分麻烦的，所以可以使用拦截器进行统一添加



要使用拦截器需要实现`HandlerInterceptor`接口，该接口下有三个方法需要实现：

- preHandle：预先处理
- postHandle：后处理
- afterCompletion：在postHandle和code主体（页面渲染后）执行完后执行（清理工作）

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/Springboot/拦截器流程.png" style="zoom:50%;" />





如果有多个拦截器被注册，那么执行顺序是注册顺序

## 8.1 登录拦截

拦截器代码

拦截器，需要实现HandlerInterceptor接口

```java
/**
 * 登录检查
 */
public class LoginIntercepter implements HandlerInterceptor {
    /**
     * 执行前检查
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录检查逻辑
        HttpSession session = request.getSession();

        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser!=null){//有用户登录过
            //放行
            return true;
        }
        //拦截
        //未登录状态跳转到登录页
        //因为是重定向的，可以将错误消息放到session中
        session.setAttribute("msg","你还未进行登录");
        response.sendRedirect("/");
        return false;
    }

    /**
     * 执行后检查
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 页面渲染后执行，模板引擎之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

```

然后配置对哪些请求进行拦截

创建一个配置类将拦截器添加到容器中，需要实现WebMvcConfigurer接口

```java
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    /**
     * 配置拦截器
     * @param registry 相当于一个拦截器中心
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        /**
         * 对LoginIntercepter进行配置
         * 拦截所有请求
         * 除了/和/login请求
         */
        registry.addInterceptor(new LoginIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/","/login");
    }
}
```

**注意：**

在拦截的时候可能会将css等静态资源也进行了拦截

这里第一个`/`代表的是`static`目录

```java
 registry.addInterceptor(new LoginIntercepter())
                .addPathPatterns("/**")
                .excludePathPatterns("/","/login","/css/**","/js/**","/fonts/**","/images/**");
```

对以上的请求进行放行





# 9 文件上传

创建文件上传表单

```html
 <form role="form" th:action="@{/upload}" method="post" enctype="multipart/form-data"><!--这里是固定写法-->
                            <div class="form-group">
                                <label for="exampleInputEmail1">Email address</label>
                                <input type="email" name="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputPassword1">Password</label>
                                <input type="password" name="pwd" class="form-control" id="exampleInputPassword1" placeholder="Password">
                            </div>
                            <div class="form-group">
                                <label for="exampleInputFile">User Header</label>
                                <input type="file" name="header" id="exampleInputFile">
                                <!--如果一次上传多张则使用mutiple属性-->
                            </div>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"> Check me out
                                </label>
                            </div>
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </form>
```

创建controller对上传的表单进行处理

```java
/**
 * 测试文件上传功能
 */
@Controller
public class FormController {

    @RequestMapping({"/form_layouts.html","/form_layouts"})
    public String fileLayouts(){
        return "form/form_layouts";
    }

    /**
     * 处理上传后的文件
     * @param eamil
     * @param pwd
     * @param header 单文件
     * @return
     */
    @PostMapping("/upload")
    public String fileUpload(String eamil,String pwd, MultipartFile header){//多文件上传使用MultipartFile[]
        System.out.println(header.getSize());

        if(!header.isEmpty()){//上传的文件不为空
//            try {//获取到流可以进行各种处理
//                header.getInputStream();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            //保存文件到服务器
            try {
                header.transferTo(new File("d:\\"+header.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "index";
    }
}
```

配置文件上传大小限制

```
# 默认文件上传大小
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-requeat-size=10MB
```



# 10 错误处理

将错误页面放在/template/error/文件夹下，springboot配置了自动跳转（好神奇~ :laughing: ）

- @ControllerAdvice
  - 用于修饰类，表示该类是Controller的全局配置类
  - 在此类中，可以对Controller进行如下三种全局配置
  - 异常处理方案、绑定数据方案、绑定参数方案
- @ExceptionHandler
  - 用于修饰方法，该方法会在Controller出现异常后被调用，用于处理捕获到的异常
- @ModelAttribute
  - 用于修饰方法，该方法会在Controller执行前被调用，用于为Model对象绑定参数
- @DataBinder
  - 用于修饰方法，该方法会在Controller执行前被被调用，用于绑定参数的转换器



 需要注意几个问题：

- @ControllerAdvice会自动扫描全部类，增加annotations让它只扫描带@Controller的类

- @ExceptionHandler可以指定捕获哪些异常，这里的Exception.class指定捕获所有异常
- 发生错误时，有可能是请求页面错误，也有可能是异步请求返回json错误。所以通过x-request-with来判断是否是异步请求，返回响应的错误信息

```java
@ControllerAdvice(annotations = Controller.class)
public class ExceptionAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler({Exception.class})
    public void handlerException(Exception e , HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.error("服务器发生异常"+e.getMessage());
        for (StackTraceElement element : e.getStackTrace()){
            logger.error(element.toString());
        }
        String xRequestWith = request.getHeader("x-request-with");
        if("XMLHttpRequest".equals(xRequestWith)){
            //异步请求
            response.setContentType("application/plain;charset=utf-8");
            PrintWriter writer = response.getWriter();
            writer.write(CommunityUtils.getJSONString(1,"服务器异常!"));
        }else{
            response.sendRedirect(request.getContextPath()+"/error");
        }
    }
}
```



# 11 servlet原生注解注入和spring注入（Servlet, Filter, Listener）

## 11.1 Servlet

编写原生servlet

```java
/**
 * 原生servlet，想要生效必须在主类中加入注解@ServletComponentScan
 * 原生类没有走springboot的拦截器，而是走过滤器，所以该页面访问没有要求登录
 */
@WebServlet("/my")//servlet3.0的注解，能够直接对值进行访问进入到该servlet
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("hi");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

```

在主类增加包扫描

```java
@ServletComponentScan//对原生servlet提供支持
@SpringBootApplication
public class ManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManageApplication.class, args);
    }

}
```

**注意：**原生类没有走springboot的拦截器，而是走过滤器，所以该页面访问没有要求登录



## 11.2 Filter



```java
package cn.edu.xidian.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;


@WebFilter(urlPatterns = {"/css/*"})//拦截/css/的所有请求。 /css/*是servlet的写法，在springboot中为/css/**
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("MyFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("MyFilter do");
        filterChain.doFilter(servletRequest, servletResponse);//放行，使得请求和响应能够通过
    }

    @Override
    public void destroy() {
        System.out.println("MyFilter destroy");
    }
}
```





## 11.3 Listener

Listener在Filter之前执行

```java
package cn.edu.xidian.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class MyServletContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("ServletContext init");//项目初始化
    }
}

```





## 11.4 RegistrationBean

- ServletRegistrationBean
- FilterRegistrationBean
- ServletListenerRegistrationBean

```java
package cn.edu.xidian.servlet;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class MyRestrationConfig {

    @Bean//放入容器
    public ServletRegistrationBean myServlet(){
        MyServlet myServlet = new MyServlet();//创建自己的servlet
        return new ServletRegistrationBean(myServlet,"/my");//将自己的servlet注册，并配置访问路径

    }

    @Bean
    public FilterRegistrationBean myFilter(){
        MyFilter myFilter = new MyFilter();

        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(myFilter);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/my"));
        return filterRegistrationBean;//第一种方式，直接设置路径过滤

        //针对servlet过滤
//        return new FilterRegistrationBean(myFilter,myServlet());//这里传入的servlet表示，servlet是什么路径，这个filter就会过滤哪个路径
    }

    @Bean
    public ServletListenerRegistrationBean myListener(){
        MyServletContextListener listener = new MyServletContextListener();
        return new ServletListenerRegistrationBean(listener);
    }

}
```



# 12 数据库访问

导入数据库相关包

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jdbc</artifactId>
</dependency>
```

上面的包中没有数据库驱动，因为官方并不知道我们要链接哪个数据库，所以需要自己配置

使用Hikari数据源

```yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/springboot?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT
    username: root
    password: 150621
    type: com.zaxxer.hikari.HikariDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
```

进行测试

自动注入jdbc模板类，这个类可以进行任何数据库操作

```java
@SpringBootTest
class ManageApplicationTests {

    @Autowired
    JdbcTemplate jdbcTemplate;//使用自动注入一个模板类

    @Test
    void contextLoads() {

        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select * from test");//查找数据
        for (Map<String, Object> stringObjectMap : mapList) {
            Iterator it = stringObjectMap.keySet().iterator();
            while(it.hasNext()){
                String next = (String)it.next();
                System.out.println(stringObjectMap.get(next));
            }
        }
    }

}

```





## 12.1 自定义数据源

上面的例子使用了Hikari数据源，这里我们使用阿里的Druid

该自定义过程使用Druid

[druid中文文档](https://github.com/alibaba/druid/wiki/%E5%B8%B8%E8%A7%81%E9%97%AE%E9%A2%98)

导入jar包

```xml
<!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>druid</artifactId>
    <version>1.1.17</version>
</dependency>
```

创建一个数据源配置类

```java
package cn.edu.xidian.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatasourcesConfig {


    @ConfigurationProperties("spring.datasource")//在配置文件中获取以spring.datasource为前缀的属性
    @Bean
    public DataSource dataSource(){

        DruidDataSource dataSource = new DruidDataSource();//该部分可以使用自己想要的任意数据源

        //可以手动写，也可以通过注解获取配置文件的配置
//        dataSource.setUrl("jdbc:mysql://localhost:3306/springboot?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT");
//        dataSource.setUsername("root");
//        dataSource.setPassword("150621");

        return dataSource;
    }
}
```

配置文件中编写

```yml
# Druid Datasource
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/springboot?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT
    username: root
    password: 150621
    driver-class-name: com.mysql.cj.jdbc.Driver
```

## 12.2 开启druid内置监控页面并配置相关功能

StatViewServlet是一个标准的javax.servlet.http.HttpServlet，需要配置在你web应用中的WEB-INF/web.xml中。

```xml
<servlet>
    <servlet-name>DruidStatView</servlet-name>
    <servlet-class>com.alibaba.druid.support.http.StatViewServlet</servlet-class>
</servlet>
<servlet-mapping>
    <servlet-name>DruidStatView</servlet-name>
    <url-pattern>/druid/*</url-pattern>
</servlet-mapping>
```

根据配置中的url-pattern来访问内置监控页面，如果是上面的配置，内置监控页面的首页是/druid/index.html

现在使用springboot，可以使用原生servlet方式注入一个servlet

我们在DatasourcesConfig中进行注册

```java
/**
     * 配置监控页面
     * @return
     */
//使用原生servlet注入方式进行内置页面注册
@Bean
public ServletRegistrationBean statViewServlet(){
    return new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
}
```

使用统计监控功能，监控页面有详细sql语句数值

```java
 @ConfigurationProperties("spring.datasource")//在配置文件中获取以spring.datasource为前缀的属性
@Bean
public DataSource dataSource(){

    DruidDataSource dataSource = new DruidDataSource();
    try {
        dataSource.setFilters("stat");
    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }

    return dataSource;
}
```

使用web应用监控

```java
/**
 * WebStatFilter用于监控webjdbc数据
 */
@Bean
public FilterRegistrationBean webStatFilter(){
    FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
//        filterRegistrationBean.setUrlPatterns(Arrays.asList("/my"));//对该路径进行监控，没有配置则对所有路径进行监控
    filterRegistrationBean.addInitParameter("exclusions","*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");//对静态资源以及druid请求进行过滤，将不会计入统计页
    return filterRegistrationBean;
}
```

增加防火墙监控功能

```java
dataSource.setFilters("wall,stat");
```

## 12.3 使用Druid-starter

[Druid-starter文档](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter)

导入依赖

```xml
<dependency>
   <groupId>com.alibaba</groupId>
   <artifactId>druid-spring-boot-starter</artifactId>
   <version>1.1.17</version>
</dependency>
```

有了上面的依赖就不需要再导入druid依赖，也不需要在数据源配置类中配置很多东西，可以使用配置文件解决

```yml
# Druid Datasource
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/springboot?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT
    username: root
    password: 150621
    driver-class-name: com.mysql.cj.jdbc.Driver
    druid:
      stat-view-servlet:
        enabled: true
        login-password: 123456
        login-username: ligen


      web-stat-filter:
        exclusions: '*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*'
        enabled: true
      filters: stat,wall
```





## 12.4 整合Mybatis

导入mybatis依赖

```xml
<!--mybatis-->
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.2.0</version>
</dependency>
```

整合时注意注解、注入等等

编写mybatis核心配置文件,实际不需要写什么内容

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

</configuration>
```

关联文件，在yml中配置

```yml
# mybatis
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml
  mapper-locations: classpath:mybatis/mapper/UserMapper.xml
```

创建mapper接口相关内容

类接口,在主类前面使用注解@MapperScan指定mapper包，就可以不适用@Mapper注解

```java
@Mapper//注意注解
public interface UserMapper {

    //方法实现写在mapper.xml文件中
    public List<User> getUser();
}
```

接口映射文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="cn.edu.xidian.mapper.UserMapper">

    <select id="getUser" resultType="cn.edu.xidian.bean.User">
        select cust_name as username, cust_pwd as pwd from test
    </select>
    <!--表中名称cusr_name，bean中名字username，使用as或者结果集映射可以一一对应-->

</mapper>
```

service层

```java
@Service//注意注解
public class UserService {

    @Autowired
    UserMapper userMapper;

    /**
     * UserMapper public User getUser();
     */
    public List<User> getUser(){
        return userMapper.getUser();
    }
}
```

测试

```java
@SpringBootTest
class ManageApplicationTests {

    @Autowired
    UserService userService;//这里必须提前创建并且注入，否则查询不到

    @Test
    void mybatisTest(){
        List<User> list =userService.getUser();
        for (User user : list) {
            System.out.println(user.getUsername());
            System.out.println(user.getPwd());
        }
    }

}
```





## 12.5 整合redis





# 13 单元测试

导入依赖,如果要兼容junit4则需要引入第二个依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>
<!--用来兼容junit4-->
<dependency>
    <groupId>org.junit.vintage</groupId>
    <artifactId>junit-vintage-engine</artifactId>
    <scope>test</scope>
</dependency>
```

我们经常使用的注解@Test在这两个版本中所在包也不同

```java
import org.junit.jupiter.api.Test;//5
import org.junit.Test;//4
```

## 13.1 常用测试注解

- **@Test**：表示该方法是测试方法
- **@ParameterizedTest**：表示该方法为参数化测试
- **@RepeatedTest**：表示该方法可重复执行
- **@DisplayName：**为测试类或者方法设置展示名称
- **@BeforeEach：**表示在每个单元测试（@Test, @RepeatedTest, @ParameterizedTest, or @TestFactory）之前执行
- **@AfterEach：**表示在每个单元测试（@Test, @RepeatedTest, @ParameterizedTest, or @TestFactory）之后执行
- **@BeforeAll：**表示在所有单元测试（@Test, @RepeatedTest, @ParameterizedTest, and @TestFactory）之前执行，在整个测试类只运行一次
- **@AfterAll：**表示在所有单元测试（@Test, @RepeatedTest, @ParameterizedTest, and @TestFactory）之后执行，同上
- **@Tag：**
- **@Disabled：**表示测试类或者方法不执行
- **@Timeout：**表示测试方法运行超时将返回错误
- **@ExtendWith：**为测试类或者方法扩展类引用，对接各种平台框架

```java
@DisplayName("对junit5功能测试")
public class Junit5Test {

    @DisplayName("测试DisplayName")
    @Test
    public void testDisplayName(){
        System.out.println("1");
    }

    @Test
    public void test2(){
        System.out.println(2);
    }

    @BeforeEach
    public void testBeforeEach(){
        System.out.println("测试即将开始");
    }

    @AfterEach
    public void testAfterEach(){
        System.out.println("测试即将结束");
    }

    @BeforeAll
    public static void testBeforeAll(){
        System.out.println("所有测试即将开始");
    }

    @AfterAll
    public static void testAfterAll(){
        System.out.println("所有测试结束");
    }
    
     /**
     * 重复次数
     */
    @RepeatedTest(value = 3)
    @Test
    public void testRepeat(){
        System.out.println(5);
    }

    /**
     * 测试超时，while(true)竟然不行
     * @throws InterruptedException
     */
    @Timeout(value = 1,unit = TimeUnit.SECONDS)
    @Test
    public void testTimeout() throws InterruptedException {
        Thread.sleep(2000);
    }
}

/**
 * 所有测试即将开始
 * 测试即将开始
 * 2
 * 测试即将结束
 * 测试即将开始
 * 1
 * 测试即将结束
 * 所有测试结束
 */
```



## 13.2 Assertion

断言机制：测试时，我们预期某种情况一定会发生，就使用断言机制，如果没有发生，那么说明程序出现了错误

### 13.2.1 简单断言

assertEquals，判断值是否相等

assertNotEquals

```java
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssertionsTest {

    @Test
    public void testSimpleAssertion(){
        int ans = sum(2,3);
        assertEquals(5,ans);//前面是期望值，后面是结果值
        assertEquals(6,ans);//前面是期望值，后面是结果值，不符合期望则返回详细错误
        /**
         * org.opentest4j.AssertionFailedError:
         * Expected :6
         * Actual   :5
         */
    }

    int sum(int a,int b){
        return a+b;
    }    
}
```

assertSame，判断是不是同一块内存

assertNotSame

```java

Object obj1 = new Object();
Object obj2 = new Object();

//        assertEquals(obj1,obj2,"不相等");
/**
 * org.opentest4j.AssertionFailedError: 不相等 ==>
 * Expected :java.lang.Object@df27fae
 * Actual   :java.lang.Object@24a35978
 */
//        assertSame(obj1,obj2,"不同的对象");
/**
 * org.opentest4j.AssertionFailedError: 不同的对象 ==>
 * Expected :java.lang.Object@16f7c8c1
 * Actual   :java.lang.Object@2f0a87b3
 */
```

### 13.2.2 数组断言

assertArrayEquals

assertArrayNotEquals

```java
int[] arr1  = new int[]{1,3};
int[] arr2  = new int[]{1,2};
assertArrayEquals(arr1,arr2,"数组不同");
/**
 * org.opentest4j.AssertionFailedError: 数组不同 ==> array contents differ at index [1], expected: <3> but was: <2>
 */
```



### 13.2.3 组合断言

```java
/**
 * 组合断言
 * 参数为lamda表达式
 */
assertAll("组合断言测试",
        ()->assertEquals(1,1),
        ()->assertEquals(2,3));
/**
 * expected: <2> but was: <3>
 * Comparison Failure: 
 * Expected :2
 * Actual   :3
 */
```



### 13.2.4 异常断言

```java
/**
 * 期望这里抛出异常，没抛出就有错
 */
assertThrows(ArithmeticException.class,()->{int i=10/1;},"数学运算异常");
/**
 * org.opentest4j.AssertionFailedError: 数学运算异常 ==> Expected java.lang.ArithmeticException to be thrown, but nothing was thrown.
 */
```



### 13.2.5 超时断言



### 13.2.6 快速失败

直接抛出失败信息，使测试停止

```java
fail("message");
```



## 13.3 Assumptions

前置条件和断言稍有不同，当断言执行成功，表示该测试方法失败，会在日志中打印失败

前置条件执行成功，表示测试方法跳过，但是这个方法就会被跳过，在日志中也是跳过而不是失败

```java
/**
 * 前置条件
 */
Assumptions.assumeFalse(true); 
```



## 13.4 嵌套测试

使用注解@Nested

注意两点：

- 内部测试类的@BeforeEach不会在外部测试类方法之前运行
- 内层的测试方法可以使外层的@Before等生效

```java
package cn.edu.xidian;

import org.junit.jupiter.api.*;

import java.util.EmptyStackException;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 嵌套测试测试
 */
public class NestedTest {

    Stack<Object> stack;

    @Test
    @DisplayName("is instantiated with new Stack()")
    void isInstantiatedWithNew() {
        new Stack<>();
//        assertNotNull(stack);//内部测试类的@BeforeEach不会在外部测试类方法之前运行

    }

    @Nested
    @DisplayName("when new")
    class WhenNew {

        @BeforeEach
        void createNewStack() {
            stack = new Stack<>();
        }

        @Test
        @DisplayName("is empty")
        void isEmpty() {
            assertTrue(stack.isEmpty());
        }

        @Test
        @DisplayName("throws EmptyStackException when popped")
        void throwsExceptionWhenPopped() {
            assertThrows(EmptyStackException.class, stack::pop);
        }

        @Test
        @DisplayName("throws EmptyStackException when peeked")
        void throwsExceptionWhenPeeked() {
            assertThrows(EmptyStackException.class, stack::peek);
        }

        @Nested
        @DisplayName("after pushing an element")
        class AfterPushing {

            String anElement = "an element";

            @BeforeEach
            void pushAnElement() {
                stack.push(anElement);
            }

            @Test
            @DisplayName("it is no longer empty")
            void isNotEmpty() {
//                assertNull(stack);//内层的测试方法可以使外层的@Before等生效
                assertFalse(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when popped and is empty")
            void returnElementWhenPopped() {
                assertEquals(anElement, stack.pop());
                assertTrue(stack.isEmpty());
            }

            @Test
            @DisplayName("returns the element when peeked but remains not empty")
            void returnElementWhenPeeked() {
                assertEquals(anElement, stack.peek());
                assertFalse(stack.isEmpty());
            }
        }
    }
}

```



## 13.5 参数化测试

[junit5参数化测试官方文档](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests)

使用参数化测试，可以用@ParameterizeTest来代替@Test，并且参数化测试需要一个指定数据源Source

- **@ValueSource:**传入各种类型的参数
- **@NullSource:**传入null值
- **@EmptySource:**传入空的列表，数组等
- **@EnumSource:**传入枚举类型
- **@MethodSource:**将一个以流的方式作为返回值的方法传入测试方法
- **@CsvSource:**传入一个csv格式的参数
- **@CsvFileSource:**传入一个csv格式的文件

```java
package cn.edu.xidian;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ParameterizeTest {

    @ParameterizedTest
    @ValueSource(ints = { 1, 2, 3 , 4 })//将每个参数传入测试方法进行一遍测试
    void testWithValueSource(int argument) {
        assertTrue(argument > 0 && argument < 4);
    }

    @ParameterizedTest
    @NullSource
    @EmptySource
    @ValueSource(strings = { " ", "   ", "\t", "\n" })
    void nullEmptyAndBlankStrings(String text) {
        assertTrue(text == null || text.trim().isEmpty());
    }

    @ParameterizedTest
    @MethodSource("stringProvider")
    void testWithExplicitLocalMethodSource(String argument) {
        assertNotNull(argument);
    }

    static Stream<String> stringProvider() {//以流的形式返回字符串
        return Stream.of("apple", "banana");
    }

    @ParameterizedTest
    @CsvSource({
            "apple,         1",
            "banana,        2",
            "'lemon, lime', 0xF1",
            "strawberry,    700000"
    })
    void testWithCsvSource(String fruit, int rank) {
        assertNotNull(fruit);
        assertNotEquals(0, rank);
    }


    @ParameterizedTest
    @CsvFileSource(resources = "/two-column.csv", numLinesToSkip = 1)
    void testWithCsvFileSourceFromClasspath(String country, int reference) {
        assertNotNull(country);
        assertNotEquals(0, reference);
    }

    @ParameterizedTest
    @CsvFileSource(files = "src/test/resources/two-column.csv", numLinesToSkip = 1)
    void testWithCsvFileSourceFromFile(String country, int reference) {
        assertNotNull(country);
        assertNotEquals(0, reference);
    }
}

```

# 14 日志

[slf4j或者logback](..\..\库\slf4j\slf4j入门.md)

利用[AOP方式](# 18 AOP)进行日志切入

```java
@Component
@Aspect
public class ServiceLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    @Pointcut("execution(* com.edu.ligen.nowcoder.service.*.*(..))")
    public void pointcut(){

    }

    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        //用户[ip],在[time]，访问了[com.edu.ligen.nowcoder.service.xxx()]
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String ip = request.getRemoteHost();
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String method = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        logger.info(String.format("用户[%s],在[%s]访问了[%s].",ip,now,method));
    }

}
```



# 15 指标监控

导入依赖

```xml
<!--指标监控功能-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```



# 16 会话管理

- HTTP的基本性质
  - 简单的
  - 可扩展的
  - 无状态的
  - 有会话的
- Cookie
  - 服务器发送到浏览器，并保存在浏览器端的一小块数据
  - 浏览器下次访问该服务器时，会自动携带该块数据发送给服务端
- Session
  - JavaEE标准，用于在服务端记录客户端信息
  - 数据存放在服务端更加安全，同时增加了服务端的压力



![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/project/nowcoder/Cookie.png)

浏览器在第一次访问一个服务器的时候，它不会携带cookie信息，它也没有cookie信息；然后访问之后，服务器发出响应，此时会携带一个cookie信息，这个信息将被保存在本地；当下一次浏览器对该服务进行访问的时候，会携带该cookie（没有过期）进行访问



![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/project/nowcoder/session.png)

浏览器访问服务器，服务器端会创建一个session会话，与cookie不同的是，这个session不会发送回浏览器；关于这个session的信息，服务器会响应一个cookie并携带sessionId；浏览器进行第二次访问的时候，发送回cookie携带sessionId，服务器端会用sessionId对已有的会话进行查找，从而得知该浏览器是前面的哪个用户







# 17 ajax

异步请求，不需要刷新网页请求

简单使用



前端

```java
<div class="col-sm-10">
    <input type="email" class="form-control" id="your-email" onblur="javascript:isvalid();"
           placeholder="请输入您的邮箱!" required>
    <div class="invalid-feedback" id="emailvalid" th:text="${emailMsg}">
        该邮
    </div>
</div>
```

ajax

```java
<script>
    function isvalid(){
        var path = CONTEXT_PATH+"/email";
        var email = $("#your-email").val();
        console.log(email);
        $.ajax({
            url:path,
            data: {"email":email},
            success: function (data){
                if(data.toString() == "invalid"){
                    $("#your-email").attr("class","form-control is-invalid");
                    $("#emailvalid").html("该邮箱不存在！");
                }else if(data.toString() == "valid"){
                    $("#your-email").attr("class","form-control");
                    console.log("asfsaf");
                }
            }
        })
    }
</script>
```

controller

```java
/**
 * ajax验证邮箱
 * @param email
 * @return
 */
@RequestMapping(value = "/email",method = RequestMethod.GET)
@ResponseBody
public String emailIsValide(String email){
    User user = userService.selectByEmail(email);
    String emailMsg = "";
    if(user==null){
        emailMsg = "invalid";
    }else{
        emailMsg = "valid";
    }
    return emailMsg;
}
```

需要注意的几个问题：

- 标签必须有id属性，方便jQuery取值
- ajax参数data必须有key值，且和controller接收参数保持一致
- controller返回的参数会保存到data里，data接收后进行逻辑判断并处理



# 18 AOP

[SpringAOP概念 # 9 AOP](..\Spring\Spring入门)

[AOP详解 -- csdn](https://blog.csdn.net/q982151756/article/details/80513340)

```java
@Component
@Aspect
public class AlphaAspect {

    /**
     * 定义一个切点
     * 对@Pointcut的定义就是要将这些方法切入到哪些jointpoint
     * pointcut就是一组匹配规则，我该给哪些jointpoint来执行这些方法
     * 对所有service有效
     * 第一个 * 代表返回值
     * 后面的 * 所有类
     * *(..)所有方法的所有参数
     */
    @Pointcut("execution(* com.edu.ligen.nowcoder.service.*.*(..))")
    public void pointcut(){

    }

    @Before("pointcut()")
    public void before(){
        System.out.println("before");
    }

    @After("pointcut()")
    public void after(){
        System.out.println("after");
    }

    /**
     * 返回值之后
     */
    @AfterReturning("pointcut()")
    public void afterReturning(){
        System.out.println("afterReturning");
    }

    @AfterThrowing("pointcut()")
    public void afterThrowing(){
        System.out.println("afterThrowing");
    }

    /**
     * 前后都有
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable{
        System.out.println("around before");
        Object obj = joinPoint.proceed();
        System.out.println("around after");
        return obj;
    }

}
```

# 19 整合Redis

- 引入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

- 配置redis
  - 配置数据库参数
  - 编写配置类，构造RedisTemplate
- 访问Redis
  - redisTemplate.opsForValue()
  - redisTemplate.opsForHash()
  - redisTemplate.opsForList()
  - redisTemplate.opsForSet()
  - redisTemplate.opsForZSet()

在application.yml中进行简单配置

```yml
spring:
  redis:
    host: localhost
    database: 10
    port: 6379
```

编写配置类

```java
@Configuration
public class RedisConfig {
    
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory factory){
        //为redis设置连接工厂，这样才能连接数据库
        RedisTemplate<String,Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        
        //设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        //设置value的序列化方式
        template.setValueSerializer(RedisSerializer.json());
        //设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        //设置hash的value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());
        
        //更新配置
        template.afterPropertiesSet();
        return template;
    }
}
```

测试

```java
@SpringBootTest
public class RedisTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testString(){
        String redisKey = "test:count";
        redisTemplate.opsForValue().set(redisKey,2);
    }

    @Test
    public void testHash(){
        String redisKey = "test:user";
        redisTemplate.opsForHash().put(redisKey,"id",1);
        redisTemplate.opsForHash().put(redisKey,"username","ligen");

        System.out.println(redisTemplate.opsForHash().get(redisKey,"id"));
        System.out.println(redisTemplate.opsForHash().get(redisKey,"username"));
    }

    @Test
    public void testList(){
        String redisKey="test:list";
        redisTemplate.opsForList().leftPush(redisKey,100);
        redisTemplate.opsForList().leftPush(redisKey,99);
        redisTemplate.opsForList().leftPush(redisKey,101);

        System.out.println(redisTemplate.opsForList().size(redisKey));
        System.out.println(redisTemplate.opsForList().index(redisKey,0));//获取某个位置索引对应的数据
        System.out.println(redisTemplate.opsForList().range(redisKey,0,2));//0-2位置上的数据
    }

    @Test
    public void testSet(){
        String redisKey = "test:set";
        redisTemplate.opsForSet().add(redisKey,"liu","guan","zhang");

        System.out.println(redisTemplate.opsForSet().size(redisKey));
        System.out.println(redisTemplate.opsForSet().pop(redisKey));//随即弹出一个值
    }

    @Test
    public void testSortSet(){
        String redisKey = "test:students";
        redisTemplate.opsForZSet().add(redisKey,"ligen",25);
        redisTemplate.opsForZSet().add(redisKey,"ligen1",24);
        redisTemplate.opsForZSet().add(redisKey,"ligen2",28);
        redisTemplate.opsForZSet().add(redisKey,"ligen3",21);

        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));//统计数量
        System.out.println(redisTemplate.opsForZSet().score(redisKey,"ligen"));//取值
        System.out.println(redisTemplate.opsForZSet().rank(redisKey,"ligen1"));//从小到大的排名，下标索引从0开始
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey,"ligen2"));//从大到小的排名
    }
}
```

编程式事务

```java
//编程式事务
@Test
public void testTransation(){
Object obj = redisTemplate.execute(new SessionCallback() {
    @Override
    public Object execute(RedisOperations operations) throws DataAccessException {
        operations.multi();//开启事务
        
        //事务内容
        //redis在事务内的操作是不会立刻执行的，直到事务提交才会一次性执行
        
        return operations.exec();//提交事务
    }
});
}
```

# 20 整合Kafka

- 引入依赖

```xml
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
    <version>2.2.7.RELEASE</version>
</dependency>
```

- 配置kafka
  - 配置server、consumer
- 访问kafka
  - 生产者：kafkaTemplate.send(topic,data)
  - 消费者：@KafaListener(topic = {"text"}) public void handleMessage(ConsumerRecord record){ }

在properties中对kafka进行配置

```properties
spring.kafka.bootstrap-servers=localhost:9092
spring.kafka.consumer.group-id=community-consumer-group
spring.kafka.consumer.enable-auto-commit=true # 消费者按照偏移量读取，是否将这个偏移量进行记录
spring.kafka.consumer.auto-consumer-interval=3000 # 自动提交怕频率 3 sec
```

使用生产者发送消息，消费者被动的接收消息

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



# 21 整合ElasticSearch

7版本和6版本差距较大，这里列举7版本的实现

[Spring Boot 官方文档 - Elasticsearch](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#features.nosql.elasticsearch)

[Spring Boot - Elasticsearch 4.3.0](https://docs.spring.io/spring-data/elasticsearch/docs/4.3.0/reference/html/#elasticsearch.clients)

- 导入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
</dependency>
```

- 配置Elasticsearch
  - 在properties或者yml中配置
  - rest.uris：服务器IP地址，本机为127.0.0.1
- Spring Data Elasticsearch
  - ElasticsearchTemplate
  - ElasticsearchRepository

- Elasticsearch底层有redis，而我们项目也引入了redis，需要解决这个冲突
  - 在启动类中加入下段代码，将配置改为false

  - ```java
    @PostConstruct
    public void init(){
        //解决es启动依赖redis的问题
        //NettyRuntime and Netty4Utils
        System.setProperty("es.set.netty.runtime.available.processors","false");
    }
    ```

## 21.1 使用Template





## 21.2 使用Repository

Repository代码由Springboot自动生成

**在实体类上增加注解**

indexname：对应es中的索引

shards：分片

replicas：副本

字段注解注解了类型

```java
@Document(indexName = "discusspost",shards = 6,replicas = 1)
public class DiscussPost {
    @Id
    private int id;
    @Field(type= FieldType.Integer)
    private int userId;
    //存储时拆分尽量多的次，搜索时只能匹配，提出不相关的分词
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String title;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String content;
    @Field(type= FieldType.Integer)
    private int type; //0普通 1置顶
    @Field(type= FieldType.Integer)
    private int status; //0普通 1精华 2拉黑
    @Field(type= FieldType.Date)
    private Date createTime;
    @Field(type= FieldType.Integer)
    private int commentCount;
    @Field(type= FieldType.Double)
    private double score;
}
```

**测试**

```java
@SpringBootTest
class NowcoderApplicationTests {

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private DiscussPostRepository discussPostRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Test
    void insertTest() {
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(0, 0, 20);

        //将数据插入elasticsearch服务器
        if(discussPosts!=null){
            for (DiscussPost discussPost : discussPosts) {
                discussPostRepository.save(discussPost);//插入一条数据
            }
        }
//        discussPostRepository.saveAll(discussPosts);//插入多条数据
    }

    @Test
    void updateTest(){
        //调用save重新插入就是修改
        //因为有id所以知道是修改哪条数据
    }

    @Test
    void searchTest(){
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬","title","content"))//构造搜索条件
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))//构造排序条件
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))//构造排序条件
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))//构造排序条件
                .withPageable(PageRequest.of(0,10))//分页
                .withHighlightFields(//高亮
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),//对该标签进行高亮
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                ).build();
        //查询
    }
}
```

未完



# 附录

**jar包爆模板错误**

如果原代码是这样

```
return "/index";
```

那么去掉 "/"

```
return "index";
```

