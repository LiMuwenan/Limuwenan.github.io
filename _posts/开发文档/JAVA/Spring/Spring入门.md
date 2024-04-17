# 1 Spring

导入spring web mvc会自动导入其他的包

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-webmvc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-webmvc</artifactId>
    <version>5.2.0.RELEASE</version>
</dependency>

<!-- https://mvnrepository.com/artifact/org.springframework/spring-jdbc -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.2.0.RELEASE</version>
</dependency>
```

## 1.1 优点

- 免费开源
- 非入侵式、轻量级
- 控制反转（IOC）、面向切面编程（AOP）
- 支持事务处理，对框架的整合





# 2 IOC

## 2.1 IOC原型

最初

Dao层，Service层

用户通过service实现任务，service通过dao层交换数据，用户不会直接和dao打交道



```java
//dao层
public interface UserDao {
    void getUser();
}


public class UserDaoImpl implements UserDao{

    @Override
    public void getUser() {
        System.out.println("默认获取用户数据");
    }
}
```



```java
//service
public interface UserService {
    void getUser();
}

public class UserServiceImpl implements UserService{

	private UserDao userDao = new UserDaoImpl();

    @Override
    public void getUser() {
        userDao.getUser();
    }
}
```

这里如果用户有新的需要，即不适用默认的方式获取用户数据，就需要在userService中修改userDao的new方法。耦合度很高很难增加新的需求



IOC思想下

```java
public class UserServiceImpl implements UserService{

//    private UserDao userDao = new UserDaoImpl();//写死之后有新需求很难改
    private UserDao userDao;

    public void setUserDao(UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public void getUser() {
        userDao.getUser();
    }
}
```

增加set方法，userDao不直接实现，而是通过用户调用setUserDao实现

如果有新的需求，只需要增加新的dao层接口的实现类

```java
public class UserMysqlImpl implements UserDao{
    public void getUser(){
        System.out.println("MySQL获取数据");
    }
}

public class UserDaoImpl implements UserDao{

    @Override
    public void getUser() {
        System.out.println("默认获取用户数据");
    }
}
```

用户

```java
public class MyTest {
//调用不同的接口实现就可以产生不同的结果
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();

        userService.setUserDao(new UserMysqlImpl());

        userService.getUser();

        userService.setUserDao(new UserDaoImpl());

        userService.getUser();
    }
}
```

## 2.2 修改为spring

```xml
<!--spring配置-->
<?xml version="1.0" encoding="UTF8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <beans>
        <bean id="UserDaoImpl" class="cn.edu.xidian.dao.UserDaoImpl"/>
        <bean id="UserMysqlImpl" class="cn.edu.xidian.dao.UserMysqlImpl"/>

        <bean id="UserServiceImpl" class="cn.edu.xidian.service.UserServiceImpl">
            <!--ref:对象引用 value：具体值-->
            <property name="userDao" ref="UserDaoImpl"/>
        </bean>
    </beans>

</beans>
```

测试

```java
public class MyTest {

    public static void main(String[] args) {

        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserServiceImpl userService =(UserServiceImpl) context.getBean("UserServiceImpl");

        userService.getUser();
    }
}
```

## 2.3 IOC创建对象方式

默认使用无参构造创建对象

有参方式创建对象

```xml
<!--下标传参-->
<!--使用Spring来创建对象，在Spring这些都成为bean-->
<bean id="hello" class="cn.edu.xidian.domain.Hello">
    <!--根据下表传参，0即是第一个参数-->
    <constructor-arg index="0" value="ligen"/>
    <constructor-arg index="1" value="26"/>
</bean>


<!--类型传参-->
<!--使用Spring来创建对象，在Spring这些都成为bean-->
<bean id="hello" class="cn.edu.xidian.domain.Hello">
    <!--根据类型自动匹配，有相同类型按顺序匹配-->
    <constructor-arg type="int" value="26"/>
    <constructor-arg type="java.lang.String" value="ligen"/>
</bean>

<!--通过参数明设置-->
<!--使用Spring来创建对象，在Spring这些都成为bean-->
<bean id="hello" class="cn.edu.xidian.domain.Hello">
    <!--通过参数明设置-->
    <constructor-arg name="str" value="ligen"/>
    <constructor-arg name="age" value="26"/>
</bean>
```

默认情况下，配置文件被加载的时候，里面的类就被初始化了

# 3 Hello Spring

1. 编写hello实体类

```java
public class Hello {
    private String str;

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return "Hello{" +
                "str='" + str + '\'' +
                '}';
    }
}
```

2. 编写元数据配置文件applicationContext

```xml
<?xml version="1.0" encoding="UTF8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--使用Spring来创建对象，在Spring这些都成为bean-->
    <bean id="hello" class="cn.edu.xidian.domain.Hello">
        <property name="str" value="Spring"/>
    </bean>

</beans>
```

3.测试

```java
public class MyTest {
    public static void main(String[] args) {
        //获取spring上下文对象
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        //我们的对象都在spring中管理
        Hello hello = (Hello) context.getBean("hello");

        System.out.println(hello.toString());
    }
}
```

# 4 spring配置

## 4.1 别名

```xml
<!--使用的时候既可以使用id，也可以使用alias-->
<alias name="hello" alias="hello2"/>

<!--使用Spring来创建对象，在Spring这些都成为bean-->
<bean id="hello" class="cn.edu.xidian.domain.Hello">
    <!--通过参数明设置-->
    <constructor-arg name="str" value="ligen"/>
    <constructor-arg name="age" value="26"/>
</bean>
```



## 4.2 bean配置

```xml
<!--使用Spring来创建对象，在Spring这些都成为bean-->
<!--
    id:bean的唯一标识符
    class:该bean对应的类，全限定名
    name:别名
-->
<bean id="hello" class="cn.edu.xidian.domain.Hello" name="hello3">
    <!--通过参数明设置-->
    <constructor-arg name="str" value="ligen"/>
    <constructor-arg name="age" value="26"/>
</bean>
```



## 4.3 import

将多个配置文件导入到一个中，方便程序初始化

```
<import resource="bean1.xml"/>
<import resource="bean2.xml"/>
<import resource="bean3.xml"/>
```





# 5 DI依赖注入

依赖：bean对象创建依赖容器

注入：bean对象中的所有属性，由容器来注入

## 5.1 构造器注入

[见第二章第三节IOC创建对象方式](# 2.3 IOC创建对象方式)

## 5.2 set方式注入

```xml
public class Student {

    private String name;
    private Address address;
    private String[] books;
    private List<String> hobbies;
    private Map<String,String> card;
    private Set<String> games;
    private Properties info;
}

<bean id="address" class="cn.edu.xidian.domain.Address">
    <property name="str" value="yanta"/>
</bean>
<bean id="student" class="cn.edu.xidian.domain.Student">
    <!--普通注入:value-->
    <property name="name" value="ligen"/>
    <!--bean注入:ref-->
    <property name="address" ref="address"/>

    <!--数组注入-->
    <property name="books">
        <array>
            <value>红楼梦</value>
            <value>水浒传</value>
            <value>西游戏</value>
            <value>三国演义</value>
        </array>
    </property>

    <!--list注入-->
    <property name="hobbies">
        <list>
            <value>码代码</value>
            <value>打游戏</value>
        </list>
    </property>

    <!--map注入-->
    <property name="card">
        <map>
            <entry key="ligen" value="xidian"/>
        </map>
    </property>

    <!--set注入-->
    <property name="games">
        <set>
            <value>The Witcher</value>
            <value>Diablo II</value>
        </set>
    </property>

    <!--properties注入-->
    <property name="info">
        <props>
            <prop key="学号">20031221723</prop>
        </props>
    </property>

</bean>
```



## 5.3 其他方式

p注入和c注入

需要导入命名空间



# 6 bean命名空间

## 6.1 单例模式

只存在一个该对象，多次`get`也只有一个

```xml
<bean id="student" class="cn.edu.xidian.domain.Student" scope="singleton">
```



## 6.2 原型模式

可以存在多个对象，每次`get`都会产生一个该对象

```xml
<bean id="student" class="cn.edu.xidian.domain.Student" scope="prototype">
```



## 6.3 其他的





# 7 bean的自动装配

- 自动装配式spring满足bean依赖的一种方式
- spring会在上下文中自动寻找，并自动给bean装配属性



spring中有三种自动装配方式：

1. xml中显式配置
2. java中显式配置
3. 隐式的自动配置



## 7.1 xml中显式配置

[xml显式配置](# 5 DI依赖注入)



```xml
<?xml version="1.0" encoding="UTF8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="cat" class="cn.edu.xidian.domain.Cat">

    </bean>
    <bean id="dog" class="cn.edu.xidian.domain.Dog">

    </bean>

    <bean id="person" class="cn.edu.xidian.domain.Person">
        <property name="cat" ref="cat"/>
        <property name="dog" ref="dog"/>
        <property name="name" value="ligen"/>
    </bean>
</beans>
```



## 7.2 java中显式配置



## 7.3 隐式的自动配置

在xml配置中，dog，cat进行了重复的配置。

```xml
<!--
	byName方式的自动装配
	不需要在person中注册cat和dog对象
	spring帮助我们自动为这两个对象进行了赋值
	如果名称不唯一就会出错

	自动装配bean的id需要和实体类的字段名保持一致
-->
<bean id="cat" class="cn.edu.xidian.domain.Cat">

</bean>
<bean id="dog" class="cn.edu.xidian.domain.Dog">

</bean>
<!--
	注释掉上面的dog之后，会报空指针异常
	因为程序在person中找不到dog2222这个对象名称
-->
<bean id="dog2222" class="cn.edu.xidian.domain.Dog">

</bean>

<bean id="person" class="cn.edu.xidian.domain.Person" autowire="byName">
    <property name="name" value="ligen"/>
</bean>
```





```xml
<!--
	byType方式的装配

	会自己在上下文中找与person字段类型相同的对象进行装配
	如果实体类中有多个该类型，这两个类型就会相同
-->
<bean id="cat" class="cn.edu.xidian.domain.Cat">

</bean>
<bean id="dog" class="cn.edu.xidian.domain.Dog">

</bean>

<bean id="person" class="cn.edu.xidian.domain.Person" autowire="byType">xml
    <property name="name" value="ligen"/>
</bean>
```



- byName的时候，需要保证所有bean的id唯一，并且这个bean需要和自动注入的属性的set方法的值一致
- byType的时候，需要保证所有bean的class唯一，并且bean需要和自动注入的属性的set方法的值一致



## 7.4 注解方式开发

使用注解须知：

1. 导入约束，context约束
2. 配置注解支持

 

```xml
<?xml version="1.0" encoding="UTF8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    
    <!--指定扫描该包下的注解使生效-->
    <context:component-scan base-package="cn.edu.xidian.domain"/>
    <!--全局性的-->
    <context:annotation-config/>

</beans>
```



### 7.4.1 @Atuowired

```xml
<?xml version="1.0" encoding="UTF8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>
    <bean id="cat" class="cn.edu.xidian.domain.Cat"/>
    <bean id="dog" class="cn.edu.xidian.domain.Dog"/>
    <bean id="person" class="cn.edu.xidian.domain.Person"/>

</beans>
```

直接在属性上使用该注解，也可以在set方法上使用

该注解先按类型查找，后按名称查找

```java
public class Person {

    @Autowired
    private Cat cat;
    @Autowired
    private Dog dog;
    private String name;
}
```

使用该注解我们可以不写set方法，这个自动装配的属性必须在IOC容器中存在，且符合byType



### 7.4.2 @Qualifier

```xml
<?xml version="1.0" encoding="UTF8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       https://www.springframework.org/schema/context/spring-context.xsd">

    <context:annotation-config/>
    <bean id="cat" class="cn.edu.xidian.domain.Cat"/>
    <bean id="dog" class="cn.edu.xidian.domain.Dog"/>
    <bean id="dog44" class="cn.edu.xidian.domain.Dog"/>
    <bean id="person" class="cn.edu.xidian.domain.Person">
    </bean>

</beans>
```



```java
public class Person {
    //配置文件中没有了dog类，导致dog3和dog4会报错
    //通过名字
    //如果加一个@Qulifier注解，就会将dog3字段自动匹配到配置文件的id为dog的bean

    @Autowired
    private Cat cat;
    @Autowired
    @Qualifier(value="dog")
    private Dog dog3;
    @Autowired
    @Qualifier(value="dog44")
    private Dog dog4;
    }
```



### 7.4.3 @Resource

 使用该注解，先查找类型再查找名称，都查找不到就会报错

```java
public class Person {

    @Resource
    private Cat cat;
    @Resource
    private Dog dog3;
    @Resource
    private Dog dog4;
}
```

下面的方式相当于@Autowired和@Qulifier结合

```java
@Resource(name="dog44")
private Dog dog4;
```





### 7.4.4 @Component

以上为使用注解方式的自动装配



```xml
<?xml version="1.0" encoding="UTF8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">

    <!--指定扫描该包下的注解使生效-->
    <context:component-scan base-package="cn.edu.xidian.domain"/>
    <context:annotation-config/>

</beans>
```

该注解可以使实体类对象自动配置到spring容器中，该注解放在类前

```java
package cn.edu.xidian.domain;


import org.springframework.stereotype.Component;

@Component
public class User {

    public String name = "ligen";
    
//    @Value("ligen")
//    public String name;
}

```

还有三个注解用于MVC架构下的注解

- dao【@Repository】
- service【@Service】
- controller【@Controller】

这三个注解和@Component的功能是相同的，都会将该类加入到spring容器中，分别用于不同的包或层下

### 7.4.5 @Value

使用该注解可以直接指定值，也可以从配置文件直接取值

```java
@Value(${mail.username})
String username;
```



### 7.4.6 @Scope

标注其作用域，单例模式，原型模式等

```java
@Repository
@Scope("singleton")
public class UserDao {

}
```





# 8 使用java方式配置spring

完全不用Spring的配置xml，只需要java

```java
//这段java配置代替了applicationContext.xml的配置，相当于配置了一个bean
package cn.edu.xidian.config;


import cn.edu.xidian.domain.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//必须有这个注解
@Configuration
public class MyConfig {

    @Bean
    public User getUserBean(){
        return new User();
    }

}
```

实体类：

```java
//实测，不加该注解也可以使用
@Component
public class User {

    private String name = "ligen";

    public User(String name) {
        this.name = name;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
```

使用方法如下：

```java
public class MyTest {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);

        User user = (User) context.getBean("getUserBean");

        System.out.println(user);
    }
}
```

## 8.1 注解

### 8.1.1 @ComponentScan

```java
@ComponentScan("cn.edu.xidian.domain")
public class MyConfig {

//    @Bean
    public User getUserBean(){
        return new User();
    }

}
```





### 8.2.2 @import

将两个配置类导入为一个

```

```





# 9 AOP



## 9.1 什么是AOP

AOP（Aspect Oriented Programming）面向切面编程：通过预编译方式和运行期动态代理实现程序功能的统一维护的一种技术。AOP使OOP的延续，利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高开发效率。

[AOP详解 -- csdn](https://blog.csdn.net/q982151756/article/details/80513340)

例子：

我们一般做活动的时候，一般对每一个接口都会做活动的有效性校验（是否开始、是否结束等等）、以及这个接口是不是需要用户登录。

按照正常的逻辑，我们可以这么做。

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/Spring/aop详解1.png" style="zoom:50%;" />

这有个问题就是，有多少接口，就要多少次代码copy。对于一个“懒人”，这是不可容忍的。好，提出一个公共方法，每个接口都来调用这个接口。这里有点切面的味道了。

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/Spring/aop详解2.png" style="zoom:70%;" />

同样有个问题，我虽然不用每次都copy代码了，但是，每个接口总得要调用这个方法吧。于是就有了切面的概念，我将方法注入到接口调用的某个地方（切点）。

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/Spring/aop详解3.png" style="zoom:67%;" />

这样接口只需要关心具体的业务，而不需要关注其他非该接口关注的逻辑或处理。
**红框处，就是面向切面编程。**



## 9.2 AOP中的相关概念

- Aspect（切面）： Aspect 声明类似于 Java 中的类声明，在 Aspect 中会包含着一些 Pointcut 以及相应的 Advice。
- Joint point（连接点）：表示在程序中明确定义的点，典型的包括方法调用，对类成员的访问以及异常处理程序块的执行等等，它自身还可以嵌套其它 joint point。是所有可能被织入Advice的候选点
- Pointcut（切点）：表示一组 joint point，这些 joint point 或是通过逻辑关系组合起来，或是通过通配、正则表达式等方式集中起来，它定义了相应的Advice要发生的地方。
- Advice 将要发生的地方。
- Advice（增强）：Advice 定义了在 Pointcut 里面定义的程序点具体要做的操作，它通过 before、after 和 around 来区别是在每个 joint point 之前、之后还是代替执行的代码。
- Target（目标对象）：织入 Advice 的目标对象.。
- Weaving（织入）：将 Aspect 和其他对象连接起来, 并创建 Adviced object 的过程
  





## 9.3 Spring实现AOP

添加maven依赖

```xml
<!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
<dependency>
    <groupId>org.aspectj</groupId>
    <artifactId>aspectjweaver</artifactId>
    <version>1.9.4</version>
    <scope>runtime</scope>
</dependency>
```

配置文件

```xml
<?xml version="1.0" encoding="UTF8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/aop
       http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!--注册bean-->
    <bean id="userService" class="cn.edu.xidian.service.UserServiceImpl"/>
    <bean id="beforeLog" class="cn.edu.xidian.log.BeforeLog"/>
    <bean id="afterLog" class="cn.edu.xidian.log.AfterLog"/>

    <!--配置aop-->
    <aop:config>
    <!--        切入点：expression：表达式 execution-->
    <!--        执行UserServiceImpl类下的所有方法-->
    <!--        (..)代表可以有参数-->
        <aop:pointcut id="pointcut1" expression="execution(* cn.edu.xidian.service.UserServiceImpl.* (..))"/>

    <!--        执行环绕增加-->
        <aop:advisor advice-ref="beforeLog" pointcut-ref="pointcut1"/>
        <aop:advisor advice-ref="afterLog" pointcut-ref="pointcut1"/>
    </aop:config>
</beans>
```

前置增强

```java
package cn.edu.xidian.log;

import org.springframework.aop.MethodBeforeAdvice;

import java.lang.reflect.Method;

//前置日志
public class BeforeLog implements MethodBeforeAdvice {

    /**
     * @param method 要执行的目标对象的方法
     * @param objects 参数
     * @param o 目标对象
     * */
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("执行了"+o.getClass().getName()+"的"+method.getName());
    }
}

```

后置增强

```java
package cn.edu.xidian.log;

import org.springframework.aop.AfterReturningAdvice;

import java.lang.reflect.Method;

public class AfterLog implements AfterReturningAdvice {
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        System.out.println("执行了"+method.getName()+"返回结果为"+o);
    }
}
```

测试接口

```java
package cn.edu.xidian.service;

public interface UserService {
    public void add();
    public void delete();
    public void update();
    public void query();
}
```

接口实现

```java
package cn.edu.xidian.service;

public class UserServiceImpl implements UserService{
    @Override
    public void add() {
        System.out.println("增加一个用户");
    }

    @Override
    public void delete() {
        System.out.println("删除一个用户");
    }

    @Override
    public void update() {
        System.out.println("更新一个用户");
    }

    @Override
    public void query() {
        System.out.println("查询一个用户");
    }
}
```

测试

```java
import cn.edu.xidian.service.UserService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        //动态代理代理的使接口，这里给成了实现类,所以错误
//        UserServiceImpl userService = (UserServiceImpl) context.getBean("userService");

        UserService user = (UserService) context.getBean("userService");

        user.add();
    }
}
```



## 9.4 Spring自定义类实现AOP

配置文件

```xml
<!--    方式二：自定义的前置类和后置类-->
    <aop:config>
<!--        ref:要引用的类-->
        <aop:aspect ref="LogClass">
<!--            切入点-->
            <aop:pointcut id="pointcut1" expression="execution(* cn.edu.xidian.service.UserServiceImpl.* (..))"/>
<!--            增强-->
            <aop:before method="before" pointcut-ref="pointcut1"/>
            <aop:after method="after" pointcut-ref="pointcut1"/>
        </aop:aspect>
    </aop:config>
```

切面类

```java
package cn.edu.xidian.log;

public class LogClass {
    public void before(){
        System.out.println("===========方法执行前===============");
    }
    public void after(){
        System.out.println("===========方法执行后===============");
    }
}
```



## 9.5 spring注解实现AOP



# 10 整合Mybatis

导入相关包：

- junit
- mybatis
- mysql数据库
- spring相关
- aop

编写配置文件



## 10.1 第一种方式



就像给普通项加入mybatis框架



## 10.2 Mybatis-Spring

导入mybatis-spring包

```xml
<!-- https://mvnrepository.com/artifact/org.mybatis/mybatis-spring -->
<dependency>
    <groupId>org.mybatis</groupId>
    <artifactId>mybatis-spring</artifactId>
    <version>2.0.2</version>
</dependency>
```

进行applicationContext.xml的配置

配置SqlSessionFactory

```xml
<?xml version="1.0" encoding="UTF8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">


<!--    DataSource:使用spring的数据源替换mybatis的配置
我们这里使用spring提供的jdbc  spring-jdbc jar包-->
    <bean id="datasource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/spring?useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=GMT"/>
        <property name="username" value="root"/>
        <property name="password" value="150621"/>
    </bean>

<!--    SqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--这里ref引用的就是上面配置好的数据源-->
        <property name="dataSource" ref="datasource"/>
<!--        绑定mybatis配置文件,这里可以配置全部的mybatis配置文件中的设置-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:cn/edu/xidian/mapper/*.xml"/>
    </bean>

<!--    这个模板类就是mybatis中的sqlSession-->

    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
<!--        只能使用构造器注入sqlSessionFactory，因为没有set方法-->
        <constructor-arg index="0" value="sqlSessionFactory"/>
    </bean>
</beans>
```

对mapper接口进行实现，并加入到spring配置中

```java
package cn.edu.xidian.mapper;

import cn.edu.xidian.domain.User;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.List;

public class UserMapperImpl implements UserMapper{

    //我们所有操作，现在都使用SqlSessionTemplate类

    private SqlSessionTemplate sqlSession;

    public UserMapperImpl(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    public UserMapperImpl() {
    }

    public SqlSessionTemplate getSqlSession() {
        return sqlSession;
    }

    public void setSqlSession(SqlSessionTemplate sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Override
    public List<User> getUserList() {
        //这里可以直接使用SqlSessiontemplate类的方法
        UserMapper userMapper =  sqlSession.getMapper(UserMapper.class);
        return userMapper.getUserList();
    }
}

```

模板类配置

```xml
<bean id="userMapper" class="cn.edu.xidian.mapper.UserMapperImpl">
    <property name="sqlSession" ref="sqlSession"/>
</bean>
```

测试

```java
import cn.edu.xidian.domain.User;
import cn.edu.xidian.mapper.UserMapper;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest {

    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserMapper userMapper = (UserMapper) context.getBean("userMapper");

        List<User> users = userMapper.getUserList();

        for (User user : users) {
            System.out.println(user.toString());
        }
    }
}

```

### 10.2.1 SqlSessionDaoSupport

用这个方法就不需要SqlSession注入

```xml
<!--    这个模板类就是mybatis中的sqlSession-->
<bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
<!--        只能使用构造器注入sqlSessionFactory，因为没有set方法-->
    <constructor-arg index="0" ref="sqlSessionFactory"/>
</bean>
```

mapper实现类

```java
package cn.edu.xidian.mapper;

import cn.edu.xidian.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

//使用继承SqlSessionDaoSupport类，简化了SqlSessionTemplate类的方法
public class UserMapperImpl2 extends SqlSessionDaoSupport implements UserMapper{
    @Override
    public List<User> getUserList() {
        SqlSession sqlSession = getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return mapper.getUserList();
    }
}

```

Spring配置文件

```xml
<?xml version="1.0" encoding="UTF8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">


<!--    DataSource:使用spring的数据源替换mybatis的配置
我们这里使用spring提供的jdbc  spring-jdbc jar包-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="url" value="jdbc:mysql://localhost:3306/spring?useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=GMT"/>
        <property name="username" value="root"/>
        <property name="password" value="150621"/>
    </bean>

<!--    SqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--这里ref引用的就是上面配置好的数据源-->
        <property name="dataSource" ref="dataSource"/>
<!--        绑定mybatis配置文件,这里可以配置全部的mybatis配置文件中的设置-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <property name="mapperLocations" value="classpath:cn/edu/xidian/mapper/*.xml"/>
    </bean>
<!--给继承SqlSessionDaoSupport的类进行注册-->
    <bean id="userMapper2" class="cn.edu.xidian.mapper.UserMapperImpl2">
        <property name="sqlSessionFactory" ref="sqlSessionFactory"/>
    </bean>
</beans>
```

测试

```java
import cn.edu.xidian.domain.User;
import cn.edu.xidian.mapper.UserMapper;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest {

    @Test
    public void test(){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        UserMapper userMapper = (UserMapper) context.getBean("userMapper2");

        List<User> users = userMapper.getUserList();

        for (User user : users) {
            System.out.println(user.toString());
        }
    }
}

```





## 10.3 事务

- 原子性
- 一致性
- 隔离性
- 持久性



- 声明式事务
  - 通过XML配置，声明某方法的事务特征
  - 通过注解，声明某方法的事务特征
- 编程式事务
  - 通过TransactionTemplate管理事务，并通过它执行数据库操作



### 10.3.1 注解

```java
/**
 * isolation定义事务隔离级别
 * propagation定义该事务的传播路径
 *              事务A调用事务B
 *             REQUIRED：仅支持当前事务（A事务），不存在事务则创建
 *             REQUIRED_NEW：在B中创建一个新事务，将A事务暂停
 *             NESTED：如果存在事务A，则事务B嵌套在A中继续，有独立的提交和回滚
 * @return
 */
@Transactional(isolation = Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
public Object save(){
    //新增用户，调用了service

    //新增帖子，调用了service

    return "ok";
}
```



### 10.3.2 编程式事务

```java
@Autowired
private TransactionTemplate transactionTemplate;

public Object save(){
    transactionTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);//设置隔离级别
    transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);//这只传播行为

    //泛型同我们需要的返回类型
    return transactionTemplate.execute(new TransactionCallback<Object>() {
        @Override
        public Object doInTransaction(TransactionStatus status) {
            //新增用户


            //新增帖子
            
            
            return "ok";
        }
    });
}
```



交给spring容器管理事务

```xml
<!--    结合aop进行事务织入-->
    <tx:advice id="txAdvice" transaction-manager="transactionManager">
<!--        给指定方法配置事务-->
        <tx:attributes>
            <tx:method name="add"/>
            <tx:method name="delete"/>
            <tx:method name="update"/>
        </tx:attributes>
    </tx:advice>
    
<!--    事务注入-->
    <aop:config>
        <aop:pointcut id="txPointCut" expression="execution(*  cn.edu.xidian.mapper.*.*(..))"/>
        <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
    </aop:config>
```



