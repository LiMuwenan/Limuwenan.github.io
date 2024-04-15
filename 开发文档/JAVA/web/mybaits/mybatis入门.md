# 参考内容

[B站狂神视频 - 【狂神说Java】Mybatis最新完整教程IDEA版通俗易懂](https://www.bilibili.com/video/BV1NE411Q7Nx)

[mybatis官方中文文档](https://mybatis.org/mybatis-3/zh/sqlmap-xml.htm)

# 1 简介

持久层框架



如何获取Mybatis？

- Github 地址：https://github.com/mybatis/mybatis-3

- Maven 

  ```xml
  <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
  <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.2</version>
  </dependency>
  ```

  

优点：

- 简单易学
- 灵活
- sql和代码的分离，提高可维护性
- 提供映射标签，支持对象与数据库的orm字段关系映射
- 提供对象关系映射标签，支持对象关系组件维护
- 提供xml标签，支持编写动态sql

# 2 mybatis

思路：搭建环境-->导入Mybatis-->编写代码-->测试

# 2.1 搭建环境

搭建数据库

新建项目maven

删除父工程src目录

导入依赖

## 2.2 创建一个模块

- 编写mybaits核心配置文件（通常放在java/main/resources目录下）

XML 配置文件中包含了对 MyBatis 系统的核心设置，包括获取数据库连接实例的数据源（DataSource）以及决定事务作用域和控制方式的事务管理器（TransactionManager）。后面会再探讨 XML 配置文件的详细内容，这里先给出一个简单的示例：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
  PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
  "http://mybatis.org/dtd/mybatis-3-config.dtd">
<!--第一次使用网址变红，将该网址添加到settings->langugae->scheme and DTDs中-->
<configuration>
  <environments default="development"><!--mysql-->
    <environment id="development"><!--mysql-->
      <transactionManager type="JDBC"/><!--事务管理-->>
      <dataSource type="POOLED">
        <property name="driver" value="${driver}"/><!--数据库驱动-->
        <property name="url" value="${url}"/><!--数据库地址-->
        <property name="username" value="${username}"/><!--数据库用户名-->
        <property name="password" value="${password}"/><!--数据库密码-->
      </dataSource>
    </environment>
  </environments>
    <!--每一个mapper.xml配置文件都需要在核心配置文件中注册-->
  <mappers>
      <!--包路径-->
    <mapper resource="org/mybatis/example/BlogMapper.xml"/>
  </mappers>
</configuration>
```



- 编写mybatis工具类

每个基于 MyBatis 的应用都是以一个 SqlSessionFactory 的实例为核心的。SqlSessionFactory 的实例可以通过 SqlSessionFactoryBuilder 获得。而 SqlSessionFactoryBuilder 则可以从 XML 配置文件或一个预先配置的 Configuration 实例来构建出 SqlSessionFactory 实例。

从 XML 文件中构建 SqlSessionFactory 的实例非常简单，建议使用类路径下的资源文件进行配置。 但也可以使用任意的输入流（InputStream）实例，比如用文件路径字符串或 file:// URL 构造的输入流。MyBatis 包含一个名叫 Resources 的工具类，它包含一些实用方法，使得从类路径或其它位置加载资源文件更加容易。

```java
package cn.edu.xidian.untils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class MybatisUntils {

    private static SqlSessionFactory sqlSessionFactory = null;

    static{
        String resource = "mybatis-config.xml";
        InputStream inputStream = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession getSqlSession(){
        return sqlSessionFactory.openSession();//传入参数true就会自动提交事务commit
    }
}

```



- 获取到`sqlSessionFactory`对象

```java
String resource = "org/mybatis/example/mybatis-config.xml";
InputStream inputStream = Resources.getResourceAsStream(resource);
SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
```

- 获取到sqlSession

```java
//从SqlSessionFactory中获取SqlSession
public static SqlSession getSqlSession(){
    return sqlSessionFactory.openSession();
}
```

## 2.3 编写代码

- 实体类

```java
package cn.edu.xidian.pojo;

public class User {
    private int user_id;
    private String user_name;
    private String suer_pwd;

	/**
	* 构造、setter、getter
	*/
}

```

- Dao接口

```java
package cn.edu.xidian.dao;

import cn.edu.xidian.pojo.User;

import java.util.List;

public interface UserDao{
    List<User> getUserList();
}
```

- 接口实现类
- `UserMapper.xml`用来替代`Dao`接口实现类，`sql`语句用`mybatis`替代了(mybatis将Dao命名为Mapper)

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--通过namespace唯一指定语句-->
<mapper namespace="cn.edu.xidian.dao.UserDao">
    <!--id的值是前面Dao接口里方法的名字-->
    <!--之前方法中要连接数据库，要执行sql，现在直接绑定就可以-->
    <!--resultTye是返回结果的类型，与要查询的类一致-->
    <select id="getUserList" resultType="cn.edu.xidian.pojo.User">
        select * from mybatis.user
    </select>

</mapper>
```

## 2.4 测试

```xml
 <!--约定大于配置，resource只能找到根目录下的配置文件，如果在文件夹中，需要用该方法添加进来-->
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
```

```java
package cn.edu.xidian.dao;

import cn.edu.xidian.domain.User;
import cn.edu.xidian.untils.MybatisUntils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserDaoTest{

    @Test
    public void test(){
        //1.获取SqlSession对象
        SqlSession sqlSession = MybatisUntils.getSqlSession();

        //2.方式一：getMapper
        UserDao userDao = sqlSession.getMapper(UserDao.class);
        List<User> userList = userDao.getUserList();

        //2.方式二：
//        List<User> userList = sqlSession.selectList("cn.edu.xidian.dao.UserDao.getUserList");

        for (int i = 0; i < userList.size(); i++) {
            System.out.println("user_name:"+userList.get(i).getUser_name()+",user_pwd:"+userList.get(i).getUser_pwd());
        }

        sqlSession.close();
    }

}
```

## 2.5 常用接口类

#### SqlSessionFactoryBuilder

这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。 因此 SqlSessionFactoryBuilder 实例的最佳作用域是方法作用域（也就是局部方法变量）。 你可以重用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例，但最好还是不要一直保留着它，以保证所有的 XML 解析资源可以被释放给更重要的事情。

#### SqlSessionFactory

SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。 使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏习惯”。因此 SqlSessionFactory 的最佳作用域是应用作用域。 有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。

#### SqlSession

每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。 绝对不能将 SqlSession 实例的引用放在一个类的静态域，甚至一个类的实例变量也不行。 也绝不能将 SqlSession 实例的引用放在任何类型的托管作用域中，比如 Servlet 框架中的 HttpSession。 如果你现在正在使用一种 Web 框架，考虑将 SqlSession 放在一个和 HTTP 请求相似的作用域中。 换句话说，每次收到 HTTP 请求，就可以打开一个 SqlSession，返回一个响应后，就关闭它。 这个关闭操作很重要，为了确保每次都能执行关闭操作，你应该把这个关闭操作放到 finally 块中。 下面的示例就是一个确保 SqlSession 关闭的标准模式：

```
try (SqlSession session = sqlSessionFactory.openSession()) {
  // 你的应用逻辑代码
}
```

在所有代码中都遵循这种使用模式，可以保证所有数据库资源都能被正确地关闭。

# 3 增删改查

增删改需要提交事务

```java
public static SqlSession getSqlSession(){
    return sqlSessionFactory.openSession();//传入参数true就会自动提交事务commit
}
```

## 3.1 namespace

`namespace`中的包名需要和接口包名一致

## 3.2 select

选择、查询语句：

- id：就是对应的`namespace`中的方法名

- resultType：`sql`语句执行的返回值

- parameterType：参数类型

  ```java
  //根据用户id查询用户
  User getUserById(int id);
  ```

  ```xml
  <select id="getUserById" resultType="cn.edu.xidian.domain.User" parameterType="int">
      select * from mybatis.user where user_id = #{id}
  </select>
  ```

如果需要传入多个参数有以下几种方法：

**第一种：**添加一个实体类：

```java
//如果需要传入年龄，名称等信息，就构造一个类包含这些信息
class User{
	private int age;
	private String name;
    
    //构造器，getter,setter
}
```

```java
//接口文件
class interface UserDao{
    
    @Select("insert into mybatis.user(age,name) values(#{age},#{name})")//这个注解可以放到xml文件中
    public void insertUser(User user);
    
}
```

**第二种：**下标传参

```java
//接口文件
class interface UserDao{
    
	@Select("insert into mybatis.user(age,name) values(#{0},#{1})")//这个注解可以放到xml文件中
    public void insertUser(int age,String name);
    //这里是根据参数顺序的下标
}
```

**第三种：**Map封装传参

```java
//接口文件
class interface UserDao{

	@Select("select * from mybatis.user limit #{startIndex},#{pageSize}")//这个注解可以放到xml文件中
	public void insertUser(Map<String,int> map);
	//这里是根据参数顺序的下标

}
```

```java
//测试文件
public void test{
	//...
	
	map.put("startIndex",1);
	map.put("pageSize",2);
	insertUser(map);
	
	//...

}
```

**第四种：**注解传参

```java
//接口文件
class interface UserDao{
    
	@Select("insert into mybatis.user(age,name) values(#{age},#{name})")//这个注解可以放到xml文件中
    public void insertUser(@param("age")int age,@param("name")String name);
    //这里是根据参数顺序的下标
}
```



## 3.3 测试

```xml
<!--select user by id-->
<select id="getUserById" resultType="cn.edu.xidian.domain.User" parameterType="int">
    select * from mybatis.user where user_id = #{user_id}
</select>
<!--insert into user by user object-->
<insert id="insertUser" parameterType="cn.edu.xidian.domain.User">
    insert into mybatis.user(user_name, user_pwd) values(#{user_name}, #{user_pwd})
</insert>
<!--update user by user object-->
<update id="updateUserById" parameterType="cn.edu.xidian.domain.User">
    UPDATE mybatis.user SET user_name = #{user_name}, user_pwd = #{user_pwd} where user_id = #{user_id}
</update>
<!--delete user by id-->
<delete id="deleteUserById" parameterType="int">
    DELETE FROM mybatis.user WHERE user_id = #{user_id}
</delete>
```

```java
//dao层接口
//根据用户id查询用户
User getUserById(int id);

//插入用户
void insertUser(User user);

//删除用户
void deleteUserById(int id);

//更新用户
void updateUserById(User user);
```

```java
//测试代码
@Test
public void insertUser(){
    SqlSession sqlSession = MybatisUntils.getSqlSession();

    UserDao userDao = sqlSession.getMapper(UserDao.class);

    userDao.insertUser(new User(1,"lisi","150621"));

    //提交事务
    sqlSession.commit();

    sqlSession.close();
}

@Test
public void updateUserByID(){
    SqlSession sqlSession = MybatisUntils.getSqlSession();

    UserDao userDao = sqlSession.getMapper(UserDao.class);

    userDao.updateUserById(new User(5,"zhangsna","15123"));

    //提交事务
    sqlSession.commit();

    sqlSession.close();
}

@Test
public void deleteUserById(){
    SqlSession sqlSession = MybatisUntils.getSqlSession();

    UserDao userDao = sqlSession.getMapper(UserDao.class);

    for (int i = 5; i < 9; i++) {
        userDao.deleteUserById(i);
    }

    sqlSession.commit();
    sqlSession.close();

}
```



# 4 配置解析

## 4.1 核心配置文件

[官方文档 - 关于核心配置文件的配置](https://mybatis.org/mybatis-3/zh/configuration.html)

通常核心配置文件被命名为`mybatis-config.xml`

- configuration（配置）
  - [properties（属性）](https://mybatis.org/mybatis-3/zh/configuration.html#properties)
  - [settings（设置）](https://mybatis.org/mybatis-3/zh/configuration.html#settings)
  - [typeAliases（类型别名）](https://mybatis.org/mybatis-3/zh/configuration.html#typeAliases)
  - [typeHandlers（类型处理器）](https://mybatis.org/mybatis-3/zh/configuration.html#typeHandlers)
  - [objectFactory（对象工厂）](https://mybatis.org/mybatis-3/zh/configuration.html#objectFactory)
  - [plugins（插件）](https://mybatis.org/mybatis-3/zh/configuration.html#plugins)
  - environments（环境配置）
    - environment（环境变量）
      - transactionManager（事务管理器）
      - dataSource（数据源）
  - [databaseIdProvider（数据库厂商标识）](https://mybatis.org/mybatis-3/zh/configuration.html#databaseIdProvider)
  - [mappers（映射器）](https://mybatis.org/mybatis-3/zh/configuration.html#mappers)

### 4.1.1 环境配置(enciroments)

`MyBatis` 可以配置成适应多种环境，这种机制有助于将 `SQL` 映射应用于多种数据库之中， 现实情况下有多种理由需要这么做。例如，开发、测试和生产环境需要有不同的配置；或者想在具有相同 `Schema` 的多个生产数据库中使用相同的 `SQL` 映射。还有许多类似的使用场景。

**不过要记住：尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。**

所以，如果你想连接两个数据库，就需要创建两个 `SqlSessionFactory` 实例，每个数据库对应一个。而如果是三个数据库，就需要三个实例，依此类推，记起来很简单：

- **每个数据库对应一个 SqlSessionFactory 实例**

为了指定创建哪种环境，只要将它作为可选的参数传递给 `SqlSessionFactoryBuilder` 即可。可以接受环境配置的两个方法签名是：

```java
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment);
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment, properties);
```

如果忽略了环境参数，那么将会加载默认环境，如下所示：

```java
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, properties);
```

`environments` 元素定义了如何配置环境。

```xml
<environments default="development"><!--要使用哪个环境就使用哪个id-->
    <environment id="development"><!--每个环境的id，随便设置-->
      <transactionManager type="JDBC"/><!--事务管理-->>
      <dataSource type="POOLED">
        <property name="driver" value="${driver}"/><!--数据库驱动-->
        <property name="url" value="${url}"/><!--数据库地址-->
        <property name="username" value="${username}"/><!--数据库用户名-->
        <property name="password" value="${password}"/><!--数据库密码-->
      </dataSource>
    </environment>
    
    <environment id="test">
      <transactionManager type="JDBC"/><!--事务管理-->>
      <dataSource type="POOLED">
        <property name="driver" value="${driver}"/><!--数据库驱动-->
        <property name="url" value="${url}"/><!--数据库地址-->
        <property name="username" value="${username}"/><!--数据库用户名-->
        <property name="password" value="${password}"/><!--数据库密码-->
      </dataSource>
    </environment>
</environments>
```

注意一些关键点:

- 默认使用的环境 ID（比如：default="development"）。
- 每个 environment 元素定义的环境 ID（比如：id="development"）。
- 事务管理器的配置（比如：type="JDBC"）。
- 数据源的配置（比如：type="POOLED"）。

默认环境和环境 `ID` 顾名思义。 环境可以随意命名，但务必保证默认的环境 `ID` 要匹配其中一个环境` ID`。





1. **事务管理器(transactionManager)**

在 `MyBatis` 中有两种类型的事务管理器（也就是 `type="[JDBC|MANAGED]"`）：

- JDBC – 这个配置直接使用了 JDBC 的提交和回滚设施，它依赖从数据源获得的连接来管理事务作用域。

- MANAGED – 这个配置几乎没做什么。它从不提交或回滚一个连接，而是让容器来管理事务的整个生命周期（比如 JEE 应用服务器的上下文）。 默认情况下它会关闭连接。然而一些容器并不希望连接被关闭，因此需要将 closeConnection 属性设置为 false 来阻止默认的关闭行为。例如:

  ```xml
  <transactionManager type="MANAGED">
    <property name="closeConnection" value="false"/>
  </transactionManager>
  ```

**提示：** 如果你正在使用` Spring + MyBatis`，则没有必要配置事务管理器，因为 `Spring` 模块会使用自带的管理器来覆盖前面的配置。

这两种事务管理器类型都不需要设置任何属性。它们其实是类型别名，换句话说，你可以用 `TransactionFactory` 接口实现类的全限定名或类型别名代替它们。

```java
public interface TransactionFactory {
  default void setProperties(Properties props) { // 从 3.5.2 开始，该方法为默认方法
    // 空实现
  }
  Transaction newTransaction(Connection conn);
  Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);
}
```

在事务管理器实例化后，所有在 `XML` 中配置的属性将会被传递给 `setProperties() `方法。你的实现还需要创建一个 Transaction 接口的实现类，这个接口也很简单：

```java
public interface Transaction {
  Connection getConnection() throws SQLException;
  void commit() throws SQLException;
  void rollback() throws SQLException;
  void close() throws SQLException;
  Integer getTimeout() throws SQLException;
}
```

使用这两个接口，你可以完全自定义 `MyBatis `对事务的处理。



2.**数据源**

`dataSource` 元素使用标准的 `JDBC` 数据源接口来配置 `JDBC` 连接对象的资源。

- 大多数 MyBatis 应用程序会按示例中的例子来配置数据源。虽然数据源配置是可选的，但如果要启用延迟加载特性，就必须配置数据源。

有三种内建的数据源类型（也就是 `type="[UNPOOLED|POOLED|JNDI]"`）：

**UNPOOLED**– 这个数据源的实现会每次请求时打开和关闭连接。虽然有点慢，但对那些数据库连接可用性要求不高的简单应用程序来说，是一个很好的选择。 性能表现则依赖于使用的数据库，对某些数据库来说，使用连接池并不重要，这个配置就很适合这种情形。`UNPOOLED` 类型的数据源仅仅需要配置以下 5 种属性：

- `driver` – 这是 JDBC 驱动的 Java 类全限定名（并不是 JDBC 驱动中可能包含的数据源类）。
- `url` – 这是数据库的 JDBC URL 地址。
- `username` – 登录数据库的用户名。
- `password` – 登录数据库的密码。
- `defaultTransactionIsolationLevel` – 默认的连接事务隔离级别。
- `defaultNetworkTimeout` – 等待数据库操作完成的默认网络超时时间（单位：毫秒）。查看 `java.sql.Connection#setNetworkTimeout()` 的 API 文档以获取更多信息。

作为可选项，你也可以传递属性给数据库驱动。只需在属性名加上`“driver.”`前缀即可，例如：

- `driver.encoding=UTF8`

这将通过 DriverManager.getConnection(url, driverProperties) 方法传递值为 `UTF8` 的 `encoding` 属性给数据库驱动。

**POOLED**– 这种数据源的实现利用“池”的概念将 JDBC 连接对象组织起来，避免了创建新的连接实例时所必需的初始化和认证时间。 这种处理方式很流行，能使并发 `Web` 应用快速响应请求。

除了上述提到 `UNPOOLED `下的属性外，还有更多属性用来配置` POOLED` 的数据源：

- `poolMaximumActiveConnections` – 在任意时间可存在的活动（正在使用）连接数量，默认值：10
- `poolMaximumIdleConnections` – 任意时间可能存在的空闲连接数。
- `poolMaximumCheckoutTime` – 在被强制返回之前，池中连接被检出（checked out）时间，默认值：20000 毫秒（即 20 秒）
- `poolTimeToWait` – 这是一个底层设置，如果获取连接花费了相当长的时间，连接池会打印状态日志并重新尝试获取一个连接（避免在误配置的情况下一直失败且不打印日志），默认值：20000 毫秒（即 20 秒）。
- `poolMaximumLocalBadConnectionTolerance` – 这是一个关于坏连接容忍度的底层设置， 作用于每一个尝试从缓存池获取连接的线程。 如果这个线程获取到的是一个坏的连接，那么这个数据源允许这个线程尝试重新获取一个新的连接，但是这个重新尝试的次数不应该超过 `poolMaximumIdleConnections` 与 `poolMaximumLocalBadConnectionTolerance` 之和。 默认值：3（新增于 3.4.5）
- `poolPingQuery` – 发送到数据库的侦测查询，用来检验连接是否正常工作并准备接受请求。默认是“NO PING QUERY SET”，这会导致多数数据库驱动出错时返回恰当的错误消息。
- `poolPingEnabled` – 是否启用侦测查询。若开启，需要设置 `poolPingQuery` 属性为一个可执行的 SQL 语句（最好是一个速度非常快的 SQL 语句），默认值：false。
- `poolPingConnectionsNotUsedFor` – 配置 poolPingQuery 的频率。可以被设置为和数据库连接超时时间一样，来避免不必要的侦测，默认值：0（即所有连接每一时刻都被侦测 — 当然仅当 poolPingEnabled 为 true 时适用）。

**JNDI** – 这个数据源实现是为了能在如` EJB `或应用服务器这类容器中使用，容器可以集中或在外部配置数据源，然后放置一个` JNDI `上下文的数据源引用。这种数据源配置只需要两个属性：

- `initial_context` – 这个属性用来在 InitialContext 中寻找上下文（即，initialContext.lookup(initial_context)）。这是个可选属性，如果忽略，那么将会直接从 InitialContext 中寻找 data_source 属性。
- `data_source` – 这是引用数据源实例位置的上下文路径。提供了 initial_context 配置时会在其返回的上下文中进行查找，没有提供时则直接在 InitialContext 中查找。

和其他数据源配置类似，可以通过添加前缀`“env.”`直接把属性传递给 `InitialContext`。比如：

- `env.encoding=UTF8`

这就会在 InitialContext 实例化时往它的构造方法传递值为 `UTF8` 的 `encoding` 属性。

你可以通过实现接口 `org.apache.ibatis.datasource.DataSourceFactory` 来使用第三方数据源实现：

```java
public interface DataSourceFactory {
  void setProperties(Properties props);
  DataSource getDataSource();
}
```

`org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory` 可被用作父类来构建新的数据源适配器，比如下面这段插入 `C3P0 `数据源所必需的代码：

```java
import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class C3P0DataSourceFactory extends UnpooledDataSourceFactory {

  public C3P0DataSourceFactory() {
    this.dataSource = new ComboPooledDataSource();
  }
}
```

为了令其工作，记得在配置文件中为每个希望` MyBatis `调用的 `setter` 方法增加对应的属性。 下面是一个可以连接至 `PostgreSQL` 数据库的例子：

```java
<dataSource type="org.myproject.C3P0DataSourceFactory">
  <property name="driver" value="org.postgresql.Driver"/>
  <property name="url" value="jdbc:postgresql:mydb"/>
  <property name="username" value="postgres"/>
  <property name="password" value="root"/>
</dataSource>
```

### 4.1.2 属性(properties)

这些属性可以在外部进行配置，并可以进行动态替换。你既可以在典型的 `Java` 属性文件中配置这些属性，也可以在 `properties `元素的子元素中设置。例如：

```xml
<!--这里是在mybatis-config.xml中配置，并且有顺序要求-->
<!--放在后面会报错，要放在最前面-->
<properties resource="org/mybatis/example/config.properties">
  <property name="username" value="dev_user"/>
  <property name="password" value="F2Fa3!33TYyg"/>
</properties>
```

使用`.properties`文件来配置
```
driver=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT
username=root
password=150621
```

不使用上面的配置方式，也可以直接在`mybatis-config.xml`中配置

设置好的属性可以在整个配置文件中用来替换需要动态配置的属性值。比如:

```xml
<dataSource type="POOLED">
  <property name="driver" value="${driver}"/>
  <property name="url" value="${url}"/>
  <property name="username" value="${username}"/>
  <property name="password" value="${password}"/>
</dataSource>
```

这个例子中的 `username` 和 `password` 将会由 `properties` 元素中设置的相应值来替换。 `driver` 和` url` 属性将会由 `config.properties` 文件中对应的值来替换。这样就为配置提供了诸多灵活选择。

配置实例：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
	<!--会优先查.properties文件的属性-->
    <properties resource="db.properties">
    </properties>
    
    <environments default="test">
        <environment id="dev">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=GMT"/>
                <property name="username" value="root"/>
                <property name="password" value="150621"/>
            </dataSource>
        </environment>

        <environment id="test">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <mapper resource="cn/edu/xidian/dao/UserMapper.xml"/>
    </mappers>

</configuration>
```



也可以在 `SqlSessionFactoryBuilder.build() `方法中传入属性值。例如：

```java
SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, props);

// ... 或者 ...

SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment, props);
```

如果一个属性在不只一个地方进行了配置，那么，`MyBatis` 将按照下面的顺序来加载：

- 首先读取在 properties 元素体内指定的属性。
- 然后根据 properties 元素中的 resource 属性读取类路径下属性文件，或根据 url 属性指定的路径读取属性文件，并覆盖之前读取过的同名属性。
- 最后读取作为方法参数传递的属性，并覆盖之前读取过的同名属性。

因此，通过方法参数传递的属性具有最高优先级，`resource/url` 属性中指定的配置文件次之，最低优先级的则是 `properties `元素中指定的属性。

从 `MyBatis 3.4.2 `开始，你可以为占位符指定一个默认值。例如：

```xml
<dataSource type="POOLED">
  <!-- ... -->
  <property name="username" value="${username:ut_user}"/> <!-- 如果属性 'username' 没有被配置，'username' 属性的值将为 'ut_user' -->
</dataSource>
```

这个特性默认是关闭的。要启用这个特性，需要添加一个特定的属性来开启这个特性。例如：

```xml
<properties resource="org/mybatis/example/config.properties">
  <!-- ... -->
  <property name="org.apache.ibatis.parsing.PropertyParser.enable-default-value" value="true"/> <!-- 启用默认值特性 -->
</properties>
```

**提示** 如果你在属性名中使用了 `":"` 字符（如：`db:username`），或者在 SQL 映射中使用了 OGNL 表达式的三元运算符（如： `${tableName != null ? tableName : 'global_constants'}`），就需要设置特定的属性来修改分隔属性名和默认值的字符。例如：

```xml
<properties resource="org/mybatis/example/config.properties">
  <!-- ... -->
  <property name="org.apache.ibatis.parsing.PropertyParser.default-value-separator" value="?:"/> <!-- 修改默认值的分隔符 -->
</properties>
<dataSource type="POOLED">
  <!-- ... -->
  <property name="username" value="${db:username?:ut_user}"/>
</dataSource>
```

### 4.1.3 类型别名(typeAliases)

类型别名可为 Java 类型设置一个缩写名字。 它仅用于 XML 配置，意在降低冗余的全限定类名书写。例如：

```xml
<!--放在第三个位置-->
<typeAliases>
  <typeAlias alias="Author" type="domain.blog.Author"/>
  <typeAlias alias="Blog" type="domain.blog.Blog"/>
  <typeAlias alias="Comment" type="domain.blog.Comment"/>
  <typeAlias alias="Post" type="domain.blog.Post"/>
  <typeAlias alias="Section" type="domain.blog.Section"/>
  <typeAlias alias="Tag" type="domain.blog.Tag"/>
</typeAliases>
```

当这样配置时，`Blog` 可以用在任何使用 `domain.blog.Blog` 的地方。

也可以指定一个包名，MyBatis 会在包名下面搜索需要的 Java Bean，比如：

```xml
<typeAliases>
  <package name="domain.blog"/>
</typeAliases>
<!--使用这种方式，在引用的时候将User写成user就可以-->
```

每一个在包 `domain.blog` 中的 Java Bean，在没有注解的情况下，会使用 Bean 的首字母小写的非限定类名来作为它的别名。 比如 `domain.blog.Author` 的别名为 `author`；若有注解，则别名为其注解值。见下面的例子：

```java
@Alias("author")
public class Author {
    ...
}
```

下面是一些为常见的 Java 类型内建的类型别名。它们都是不区分大小写的，注意，为了应对原始类型的命名重复，采取了特殊的命名风格。



###  4.1.4 设置(settings)

| 设置名                           | 描述             | 有效值         | 默认值         |
| :--------------------- | :---------------------------- | :----------------------------- | :-------------------------- |
| cacheEnabled    | 全局性地开启或关闭所有映射器配置文件中已配置的任何缓存。     | true \| false                  | true                   |
| lazyLoadingEnabled               | 延迟加载的全局开关。当开启时，所有关联对象都会延迟加载。 特定关联关系中可通过设置 `fetchType` 属性来覆盖该项的开关状态。 | true \| false                                                | false                                                 |
| aggressiveLazyLoading            | 开启时，任一方法的调用都会加载该对象的所有延迟加载属性。 否则，每个延迟加载属性会按需加载（参考 `lazyLoadTriggerMethods`)。 | true \| false                                                | false （在 3.4.1 及之前的版本中默认为 true）          |
| multipleResultSetsEnabled        | 是否允许单个语句返回多结果集（需要数据库驱动支持）。         | true \| false                      | true                                                  |
| useColumnLabel                   | 使用列标签代替列名。实际表现依赖于数据库驱动，具体可参考数据库驱动的相关文档，或通过对比测试来观察。 | true \| false                                                | true                                                  |
| useGeneratedKeys                 | 允许 JDBC 支持自动生成主键，需要数据库驱动支持。如果设置为 true，将强制使用自动生成主键。尽管一些数据库驱动不支持此特性，但仍可正常工作（如 Derby）。 | true \| false                                                | False                                                 |
| autoMappingBehavior              | 指定 MyBatis 应如何自动映射列到字段或属性。 NONE 表示关闭自动映射；PARTIAL 只会自动映射没有定义嵌套结果映射的字段。 FULL 会自动映射任何复杂的结果集（无论是否嵌套）。 | NONE, PARTIAL, FULL                                          | PARTIAL                                               |
| autoMappingUnknownColumnBehavior | 指定发现自动映射目标未知列（或未知属性类型）的行为。`NONE`: 不做任何反应`WARNING`: 输出警告日志（`'org.apache.ibatis.session.AutoMappingUnknownColumnBehavior'` 的日志等级必须设置为 `WARN`）`FAILING`: 映射失败 (抛出 `SqlSessionException`) | NONE, WARNING, FAILING                                       | NONE                                                  |
| defaultExecutorType              | 配置默认的执行器。SIMPLE 就是普通的执行器；REUSE 执行器会重用预处理语句（PreparedStatement）； BATCH 执行器不仅重用语句还会执行批量更新。 | SIMPLE REUSE BATCH                                           | SIMPLE                                                |
| defaultStatementTimeout          | 设置超时时间，它决定数据库驱动等待数据库响应的秒数。         | 任意正整数                                                   | 未设置 (null)                                         |
| defaultFetchSize                 | 为驱动的结果集获取数量（fetchSize）设置一个建议值。此参数只可以在查询设置中被覆盖。 | 任意正整数                                                   | 未设置 (null)                                         |
| defaultResultSetType             | 指定语句默认的滚动策略。（新增于 3.5.2）                     | FORWARD_ONLY \| SCROLL_SENSITIVE \| SCROLL_INSENSITIVE \| DEFAULT（等同于未设置） | 未设置 (null)                                         |
| safeRowBoundsEnabled             | 是否允许在嵌套语句中使用分页（RowBounds）。如果允许使用则设置为 false。 | true \| false                                                | False                                                 |
| safeResultHandlerEnabled         | 是否允许在嵌套语句中使用结果处理器（ResultHandler）。如果允许使用则设置为 false。 | true \| false                                                | True                                                  |
| mapUnderscoreToCamelCase         | 是否开启驼峰命名自动映射，即从经典数据库列名 A_COLUMN 映射到经典 Java 属性名 aColumn。 | true \| false                                                | False                                                 |
| localCacheScope                  | MyBatis 利用本地缓存机制（Local Cache）防止循环引用和加速重复的嵌套查询。 默认值为 SESSION，会缓存一个会话中执行的所有查询。 若设置值为 STATEMENT，本地缓存将仅用于执行语句，对相同 SqlSession 的不同查询将不会进行缓存。 | SESSION \| STATEMENT                                         | SESSION                                               |
| jdbcTypeForNull                  | 当没有为参数指定特定的 JDBC 类型时，空值的默认 JDBC 类型。 某些数据库驱动需要指定列的 JDBC 类型，多数情况直接用一般类型即可，比如 NULL、VARCHAR 或 OTHER。 | JdbcType 常量，常用值：NULL、VARCHAR 或 OTHER。              | OTHER                                                 |
| lazyLoadTriggerMethods           | 指定对象的哪些方法触发一次延迟加载。                         | 用逗号分隔的方法列表。                                       | equals,clone,hashCode,toString                        |
| defaultScriptingLanguage         | 指定动态 SQL 生成使用的默认脚本语言。                        | 一个类型别名或全限定类名。                                   | org.apache.ibatis.scripting.xmltags.XMLLanguageDriver |
| defaultEnumTypeHandler           | 指定 Enum 使用的默认 `TypeHandler` 。（新增于 3.4.5）        | 一个类型别名或全限定类名。                                   | org.apache.ibatis.type.EnumTypeHandler                |
| callSettersOnNulls               | 指定当结果集中值为 null 的时候是否调用映射对象的 setter（map 对象时为 put）方法，这在依赖于 Map.keySet() 或 null 值进行初始化时比较有用。注意基本类型（int、boolean 等）是不能设置成 null 的。 | true \| false                                                | false                                                 |
| returnInstanceForEmptyRow        | 当返回行的所有列都是空时，MyBatis默认返回 `null`。 当开启这个设置时，MyBatis会返回一个空实例。 请注意，它也适用于嵌套的结果集（如集合或关联）。（新增于 3.4.2） | true \| false                                                | false                                                 |
| logPrefix                        | 指定 MyBatis 增加到日志名称的前缀。                          | 任何字符串                                                   | 未设置                                                |
| logImpl                          | 指定 MyBatis 所用日志的具体实现，未指定时将自动查找。        | SLF4J \| LOG4J \| LOG4J2 \| JDK_LOGGING \| COMMONS_LOGGING \| STDOUT_LOGGING \| NO_LOGGING | 未设置                                                |
| proxyFactory                     | 指定 Mybatis 创建可延迟加载对象所用到的代理工具。            | CGLIB \| JAVASSIST                                           | JAVASSIST （MyBatis 3.3 以上）                        |
| vfsImpl                          | 指定 VFS 的实现                                              | 自定义 VFS 的实现的类全限定名，以逗号分隔。                  | 未设置                                                |
| useActualParamName               | 允许使用方法签名中的名称作为语句参数名称。 为了使用该特性，你的项目必须采用 Java 8 编译，并且加上 `-parameters` 选项。（新增于 3.4.1） | true \| false                                                | true                                                  |
| configurationFactory             | 指定一个提供 `Configuration` 实例的类。 这个被返回的 Configuration 实例用来加载被反序列化对象的延迟加载属性值。 这个类必须包含一个签名为`static Configuration getConfiguration()` 的方法。（新增于 3.2.3） | 一个类型别名或完全限定类名。                                 | 未设置                                                |
| shrinkWhitespacesInSql           | 从SQL中删除多余的空格字符。请注意，这也会影响SQL中的文字字符串。 (新增于 3.5.5) | true \| false                                                | false                                                 |
| defaultSqlProviderType           | Specifies an sql provider class that holds provider method (Since 3.5.6). This class apply to the `type`(or `value`) attribute on sql provider annotation(e.g. `@SelectProvider`), when these attribute was omitted. | A type alias or fully qualified class name                   | Not set                                               |

一个配置完整的 settings 元素的示例如下：

```xml
<settings>
  <setting name="cacheEnabled" value="true"/>
  <setting name="lazyLoadingEnabled" value="true"/>
  <setting name="multipleResultSetsEnabled" value="true"/>
  <setting name="useColumnLabel" value="true"/>
  <setting name="useGeneratedKeys" value="false"/>
  <setting name="autoMappingBehavior" value="PARTIAL"/>
  <setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
  <setting name="defaultExecutorType" value="SIMPLE"/>
  <setting name="defaultStatementTimeout" value="25"/>
  <setting name="defaultFetchSize" value="100"/>
  <setting name="safeRowBoundsEnabled" value="false"/>
  <setting name="mapUnderscoreToCamelCase" value="false"/>
  <setting name="localCacheScope" value="SESSION"/>
  <setting name="jdbcTypeForNull" value="OTHER"/>
  <setting name="lazyLoadTriggerMethods" value="equals,clone,hashCode,toString"/>
</settings>
```

### 4.1.5 映射器(mapper)

既然 `MyBatis` 的行为已经由上述元素配置完了，我们现在就要来定义 `SQL` 映射语句了。 但首先，我们需要告诉 MyBatis 到哪里去找到这些语句。 在自动查找资源方面，Java 并没有提供一个很好的解决方案，所以最好的办法是直接告诉 MyBatis 到哪里去找映射文件。 你可以使用相对于类路径的资源引用，或完全限定资源定位符（包括 `file:///` 形式的 URL），或类名和包名等。例如：

```xml
<!-- 使用相对于类路径的资源引用 -->
<mappers>
  <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
  <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
  <mapper resource="org/mybatis/builder/PostMapper.xml"/>
</mappers>

<!-- 使用完全限定资源定位符（URL） -->
<mappers>
  <mapper url="file:///var/mappers/AuthorMapper.xml"/>
  <mapper url="file:///var/mappers/BlogMapper.xml"/>
  <mapper url="file:///var/mappers/PostMapper.xml"/>
</mappers>

<!-- 使用映射器接口实现类的完全限定类名 -->
<mappers>
  <mapper class="org.mybatis.builder.AuthorMapper"/>
  <mapper class="org.mybatis.builder.BlogMapper"/>
  <mapper class="org.mybatis.builder.PostMapper"/>
</mappers>

<!-- 将包内的映射器接口实现全部注册为映射器 -->
<mappers>
  <package name="org.mybatis.builder"/>
</mappers>
```

## 4.2 作用域和生命周期

理解我们之前讨论过的不同作用域和生命周期类别是至关重要的，因为错误的使用会导致非常严重的并发问题。

#### SqlSessionFactoryBuilder

这个类可以被实例化、使用和丢弃，一旦创建了 `SqlSessionFactory`，就不再需要它了。 因此 `SqlSessionFactoryBuilder` 实例的最佳作用域是方法作用域（也就是`局部方法变量`）。 你可以重用 SqlSessionFactoryBuilder 来创建多个 SqlSessionFactory 实例，但最好还是不要一直保留着它，以保证所有的 XML 解析资源可以被释放给更重要的事情。

#### SqlSessionFactory

SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。 使用 SqlSessionFactory 的最佳实践是在应用运行期间不要重复创建多次，多次重建 SqlSessionFactory 被视为一种代码“坏习惯”。因此 SqlSessionFactory 的最佳作用域是应用作用域。 有很多方法可以做到，最简单的就是使用单例模式或者静态单例模式。

#### SqlSession

每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。 绝对不能将 SqlSession 实例的引用放在一个类的静态域，甚至一个类的实例变量也不行。 也绝不能将 SqlSession 实例的引用放在任何类型的托管作用域中，比如 Servlet 框架中的 HttpSession。 如果你现在正在使用一种 Web 框架，考虑将 SqlSession 放在一个和 HTTP 请求相似的作用域中。 换句话说，每次收到 HTTP 请求，就可以打开一个 SqlSession，返回一个响应后，就关闭它。 这个关闭操作很重要，为了确保每次都能执行关闭操作，你应该把这个关闭操作放到 finally 块中。 下面的示例就是一个确保 SqlSession 关闭的标准模式：

```java
try (SqlSession session = sqlSessionFactory.openSession()) {
  // 你的应用逻辑代码
}
```

# 5 结果集映射(reslutMap)

当实体类中的字段名(user_id,user_name,user_pwd)与数据库中的字段名不能一一对应的时候(不一致)，就会出现`null`错误



解决办法：

- 将`sql`语句改成别名，这样就能在结果映射集中一一对应

```sql
SELECT user_id as id,user_name as name, user_pwd as pwd FROM mybatis.user WHERE user_id = ${id}
```

- resultMap

```xml
<resultMap id="UserMap" type="User">
    <!--column是库表的列名，property是实体类的字段名-->
    <result column="user_id" property="user_id"/>
    <result column="user_name" property="name"/>
    <result column="user_pwd" property="pwd"/>
    <!--对象使用association-->
    <!--集合使用collection-->
</resultMap>
<!--resultMap的值对应上面标签中的id-->
<select id="getUserById" resultMap="UserMap">
    SELECT * FROM mybatis.user WHERE user_id = #{id}
</select>
```

这样就能够使数据库字段和实体类字段一一对应，从而可以直接查询到需要的值



- `constructor` - 用于在实例化类时，注入结果到构造方法中
  - `idArg` - ID 参数；标记出作为 ID 的结果可以帮助提高整体性能
  - `arg` - 将被注入到构造方法的一个普通结果
- `id` – 一个 ID 结果；标记出作为 ID 的结果可以帮助提高整体性能
- `result` – 注入到字段或 JavaBean 属性的普通结果
- `association` – 一个复杂类型的关联；许多结果将包装成这种类型
  - 嵌套结果映射 – 关联可以是 `resultMap` 元素，或是对其它结果映射的引用
- `collection` – 一个复杂类型的集合
  - 嵌套结果映射 – 集合可以是 `resultMap` 元素，或是对其它结果映射的引用
- `discriminator` – 使用结果值来决定使用哪个`resultMap`
  - `case` – 基于某些值的结果映射
    - 嵌套结果映射 – `case` 也是一个结果映射，因此具有相同的结构和元素；或者引用其它的结果映射

# 6 日志

## 6.1 日志工厂

如果一个数据库操作，出现了异常，我们需要排错，这时候使用日志就很很方便

`Mybatis`中常用的日志包

- SLF4J
- Apache Commons Logging
- Log4j 2
- Log4j
- JDK logging

```xml
<!--在核心配置文件中配置日志实现，value中使用实际要使用的jar包-->
<!--settings放在第二个位置-->
<settings>
    <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```

## 6.2 log4j

标准输出不需要导入包，`log4j`需要导入包

创建一个`.properties`文件，配置如下：

```
#将等级为DEBUG的日志信息输出到console和file这两个目的地，console和file的定义在下面的代码
log4j.rootLogger=DEBUG,console,file

#控制台输出的相关设置
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.Threshold=DEBUG
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern=[%c]-%m%n

#文件输出的相关设置
log4j.appender.file = org.apache.log4j.RollingFileAppender
log4j.appender.file.File=./log/gen.log	# 日志输出位置
log4j.appender.file.MaxFileSize=10mb
log4j.appender.file.Threshold=DEBUG
log4j.appender.file.layout=org.apache.log4j.PatternLayout
log4j.appender.file.layout.ConversionPattern=[%p][%d{yy-MM-dd}][%c]%m%n

#日志输出级别
log4j.logger.org.mybatis=DEBUG
log4j.logger.java.sql=DEBUG
log4j.logger.java.sql.Statement=DEBUG
log4j.logger.java.sql.ResultSet=DEBUG
log4j.logger.java.sql.PreparedStatement=DEBUG
```

```java
@Test
public void log4jTest(){
    //使用apache的log包，参数为当前类的class
//        Logger logger = Logger.getLogger(UserMapperTest.class);

    logger.info("info:进入了info log4jTest");
    logger.debug("debug:进入了debug方式");
    logger.error("error:进入了error方式");
}
```



# 7 分页

减少查询数据的压力

## 7.1 limit分页



`sql`中有语句

```mysql
SELECT * FROM user LIMIT 0,2;
# 该语句查询从第0个开始，2个数据
# 使用这种性质实现分页查询
SELECT * FROM user LIMIT 2,2;	# 相当于查询了第二页
```

在`mybatis`中实现：

```xml
<!--    limit-->
<!--    使用map传递了下标和页大小参数-->
<select id="getUserByLimit" resultType="cn.edu.xidian.domain.User" parameterType="map" >
    select * from mybatis.user limit #{startIndex},#{pageSize}
</select>
```

```java
List<User> getUserByLimit(Map<String,Integer> map);
```

测试代码：

```java
@Test
public void getUserByLimitTest(){
    SqlSession sqlSession = MybatisUntils.getSqlSession();

    UserMapper userMapper = sqlSession.getMapper(UserMapper.class);

    HashMap<String, Integer> map = new HashMap<>();
    map.put("startIndex",0);//key值要和sql语句中的变量名相同
    map.put("pageSize",2);

    List<User> userList = userMapper.getUserByLimit(map);

    for (User user : userList) {
        System.out.println(user.toString());
    }

    sqlSession.close();
}
```

## 7.2 RowBounds分页



# 8 注解开发

## 8.2 注解sql

在接口文件中，在接口前添加`sql`语句注解：

```java
//根据用户id查询用户
@Select("select * from mybatis.user where user_id = #{user_id}")
User getUserById(int id);
```

然后在`mybatis-config.xml`中绑定接口。之前绑定的是接口映射文件`UserMapper.xml`，现在改为绑定接口

```
<mappers>
  <mapper class = "cn.edu.xidian.dao.UserDao"/>
</mappers>
```

使用注解来映射简单语句会使代码显得更加简洁，但对于稍微复杂一点的语句，Java 注解不仅力不从心，还会让你本就复杂的` SQL `语句更加混乱不堪。 因此，如果你需要做一些很复杂的操作，最好用` XML` 来映射语句。



## 8.1 注解传参

```java
//接口文件
class interface UserDao{
    
	@Select("insert into mybatis.user(age,name) values(#{age},#{name})")//这个注解可以放到xml文件中
    public void insertUser(@param("age")int age,@param("name")String name);
    //这里是根据参数顺序的下标
}

```

- 基本类型和String需要加注解
- 引用类型不需要加

# 9 复杂查询

有两个表，分别为`student`和`teacher`，学生中有一列为`stu_tid`保存着谁是他的老师，现在需要使用联结表查询方式获得学生表及对应的老师

## 9.1 多对一查询

参考`mybatiss/mybatis-04`

多个学生对应一个老师，查询学生，并查到这个学生的老师

常规`mysql`语句

```mysql
select s.stu_id,s.stu_name,s.stu_tid,t.tech_id ,t.tech_name ,t.tech_subject 
from  student as s join teacher as t 
where s.stu_tid = t.tech_id;
```

如果直接用这句话在.`mybatis`中查询， 查询出来的结果`teacher`为`null`

这时需要使用子查询来解决这个问题

```xml
<!--这个属于多对一查询，多个学生对应一个老师-->
<resultMap id="StudentTeacher" type="Student">
    <result column="stu_id" property="stu_id"/>
    <result column="stu_name" property="stu_name"/>
    <!--实体类对象用association-->
    <!--集合对象用collection-->
    <!--调用getStudent查询，没有teacher，这时候用resultMap对应来查询teacher，select子标签就相当于子查询-->
    <association property="teacher" column="stu_tid" javaType="cn.edu.xidian.domain.Teacher" select="getTeacher"/>
</resultMap>

<select id="getStudent" resultMap="StudentTeacher">
    select * from student
</select>
<!--这里tech_id与stu_tid相等才是我们要的结果-->
<select id="getTeacher" resultType="cn.edu.xidian.domain.Teacher">
    select * from teacher where tech_id = #{stu_tid}
</select>
```

还有第二种方法，使用联结表查询，一条查询语句

```xml
<resultMap id="StudentTeacher" type="Student">
    <result column="sid" property="stu_id"/>
    <result column="sname" property="stu_name"/>
    <association property="teacher" javaType="cn.edu.xidian.domain.Teacher">
        <!--这里相当于给student的实体类teacher指定了如何查找teacher里面的字段-->
        <result property="tech_name" column="tname"/>
    </association>
</resultMap>
<select id="getStudent" resultMap="StudentTeacher">
    select s.stu_id as sid,s.stu_name as sname,t.tech_name as tname from student as s,teacher as t where s.stu_tid=t.tech_id
</select>
```

## 9.2 一对多查询

参考`mybatiss/mybatis-05`

一个老师对应多个学生，查询老师，并查询出这个老师的学生

`mysql`语句

```mysql
select s.stu_id as sid,s.stu_name as sname,t.tech_id as tid ,t.tech_name as tname
from mybatis.teacher as t,student as s  where stu_tid = tech_id;
```

使用联结表查询

```xml
<!--按结果嵌套查询-->
<resultMap id="TeacherMap" type="Teacher">
    <result column="tid" property="tech_id"/>
    <result column="tname" property="tech_name"/>
    <!--集合泛型的值，使用ofType-->
    <collection property="student" column="student" ofType="Student">
        <result column="sid" property="stu_id"/>
        <result column="sname" property="stu_name"/>
    </collection>
</resultMap>
<select id="getTeacherByIdIncludeStudent" resultType="Teacher" parameterType="int" resultMap="TeacherMap">
    select s.stu_id as sid,s.stu_name as sname,t.tech_id as tid ,t.tech_name as tname
    from mybatis.teacher as t,student as s  where stu_tid = tech_id and tech_id = #{tech_id}
</select>
```

`Teacher.java`实体类

```java
public class Teacher implements Serializable {
    private int tech_id;
    private String tech_name;

    private List<Student> student;

	/**
	*构造 getter setter
	*/
}
```

第二种使用子查询

```xml
<!--    子查询方式-->
<select id="getTeacherByIdIncludeStudent2" resultMap="TeacherMap2" resultType="Teacher">
    select * from mybatis.teacher where tech_id = #{tech_id}
</select>

<resultMap id="TeacherMap2" type="Teacher">
    <!--集合泛型的值，使用ofType-->
    <!--去掉javaTYpe和ofType也能跑通-->
    <collection property="student" column="tech_id" javaType="ArrayList" ofType="Student" select="getStudent">
    </collection>
</resultMap>

<select id="getStudent" resultType="Student">
    select * from student where stu_tid = #{tech_id}
</select>
```

# 10 动态Sql

对`sql`语言进行流程控制

- if
- choose (when, otherwise)
- trim (where, set)
- foreach

## 10.1 实验环境

```mysql
CREATE TABLE `blog`(
	`blog_id` varchar(50) NOT NULL COMMENT '博客id',
	`blog_title` varchar(100) NOT NULL COMMENT '博客标题',
	`blog_author` varchar(30) NOT NULL COMMENT '博客作者',
	`create_time` datetime NOT NULL COMMENT '创建时间',
	`blog_views` int(30) NOT NULL COMMENT '浏览器'
)ENGINE=INNODB DEFAULT CHARSET=utf8
```

## 10.2 IF

```xml
<select id="queryBlogIF" parameterType="map" resultType="Blog">
    select * from blog where true
    <if test="blog_title != null">
        and blog_title = #{blog_title}
    </if>
    <if test="blog_author != null">
        and blog_author = #{blog_author}
    </if>
</select>
```

如果传入`title`则在原本语句后添加第一个`if`标签的语句，后面`author`同理

## 10.3 CHOOSE WHEN OTHERWISE

有时候，我们不想使用所有的条件，而只是想从多个条件中选择一个使用。针对这种情况，`MyBatis` 提供了 `choose` 元素，它有点像 Java 中的 `switch` 语句。

还是上面的例子，但是策略变为：传入了 `“title”` 就按` “title” `查找，传入了` “author”` 就按 `“author”` 查找的情形。若两者都没有传入，就返回标记为` featured` 的 `BLOG`（这可能是管理员认为，与其返回大量的无意义随机 Blog，还不如返回一些由管理员精选的 Blog）。

```xml
<select id="queryBlogChoose" parameterType="map" resultType="Blog">
    select * from blog where true
    <choose>
        <!--如果进入第一个when后面的when标签不会进入-->
        <when test="blog_title != null">
            and blog_title = #{blog_title}
        </when>
        <when test="blog_author != null">
            and blog_author = #{blog_author}
        </when>
        <otherwise>
			
        </otherwise>
    </choose>
</select>
```

- `choose` - `switch`
- `when` - `case`
- `otherwise` - `default`

这里并不会将`when`标签执行完再执行`otherwise`，如果传入的值没有满足所有`when`标签才会执行`otherwise`

## 10.4 WHERE TRIM SET

```xml
<select id="queryBlogIF" parameterType="map" resultType="Blog">
    select * from blog where
    <if test="blog_title != null">
        blog_title = #{blog_title}
    </if>
    <if test="blog_author != null">
        and blog_author = #{blog_author}
    </if>
</select>
```

在实际的生产过程中，我们不会加入`where true`这样语句来规避`and`拼接错误

如果没有满足`if`条件语句就会变成

```mysql
 select * from blog where
```

如果第二条`if`语句成功会变成

```mysql
select * from blog where and blog_author = #{blog_author}
```

为了规避这样的错误，`mybatis`设置了`where`标签

使用了`where`标签，`mybatis`就会自动将`if`语句中的`and`或者`or`省略或者添加

```mysql
<select id="queryBlogWhere" parameterType="map" resultType="Blog">
    select * from blog
    <where>
        <if test="blog_title != null">
            and blog_title = #{blog_title}
        </if>
        <if test="blog_author != null">
            and blog_author = #{blog_author}
        </if>
    </where>
</select>
```



如果 `wherw` 元素与你期望的不太一样，你也可以通过自定义 `trim `元素来定制 `where` 元素的功能。比如，和 `where` 元素等价的自定义 `trim `元素为：

```xml
<!--前缀和前缀覆盖-->
<!--在WHERE标签中忽略掉AND OR-->
<trim prefix="WHERE" prefixOverrides="AND |OR ">
  ...
</trim>
```

`prefixOverrides` 属性会忽略通过管道符分隔的文本序列（注意此例中的空格是必要的）。上述例子会移除所有 `prefixOverrides` 属性中指定的内容，并且插入 `prefix` 属性中指定的内容。



用于动态更新语句的类似解决方案叫做 `set`。`set`元素可以用于动态包含需要更新的列，忽略其它不更新的列。比如：

```xml
<update id="updateBlog" parameterType="map">
    update blog
    <set>
        <if test="blog_views != null">
            blog_views = #{blog_views},
        </if>
        <if test="create_time != null">
            create_time = #{create_time},
        </if>
    </set>
    <where>
        blog_author = #{blog_author}
    </where>
</update>
```

这个例子中，`set` 元素会动态地在 行首插入` SET` 关键字，并会删掉额外的逗号（这些逗号是在使用条件语句给列赋值时引入的）。

来看看与 `set`元素等价的自定义 `trim` 元素吧：

```
<trim prefix="SET" suffixOverrides=",">
  ...
</trim>
```

注意，我们覆盖了后缀值设置，并且自定义了前缀值。

## 10.5 sql

如果有公共的`sql`片段，为了方便复用，可以使用`sql`标签方便重用

```xml
<sql id="if-title-author">
    <if test="blog_title != null">
        and blog_title = #{blog_title}
    </if>
    <if test="blog_author != null">
        and blog_author = #{blog_author}
    </if>
</sql>

<select id="queryBlogIF" parameterType="map" resultType="Blog">
    select * from blog where true
   <include refid="if-title-author"/>
</select>
```

通过`include`标签再次引用

## 10.6 FOREACH

`动态 SQL` 的另一个常见使用场景是对集合进行遍历（尤其是在构建 `IN` 条件语句的时候）。

原始语句：

```mysql
select * from mybatis.blog where blog_views in (1268,2000)
```

当需要枚举的值很多时，就体现了`foreach`的用途





```xml
<!--collection属性代表的map的键值，open close代表的是语句的开头与结尾，separator是分隔符-->
<select id="queryBlogForeach" parameterType="map" resultType="Blog">
    select * from mybatis.blog
    <where>
        <foreach collection="views" item="blog_views" open="(" close=")" separator=",">
            blog_views = #{blog_views}
        </foreach>
    </where>
</select>
```

```java
@Test
public void queryBlogForeach(){
    SqlSession sqlSession = MybatisUntils.getSqlSession();
    BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);

    HashMap map = new HashMap();
	//创建一个list作为in后面括号中的枚举值
    ArrayList<Integer> views = new ArrayList<Integer>();

    views.add(1268);
	//这个键值和xml中的collection的值相同
    map.put("views",views);

    List<Blog> blogs = blogMapper.queryBlogForeach(map);

    for (Blog blog: blogs) {
        System.out.println(blog);
    }

    sqlSession.close();
}
```

# 11 缓存

频繁连接数据库查询会很慢，很耗资源，为了提高速度所以设置缓存

`Mybatis`包含一个非常强大的缓存特性，它可以非常方便地定制和配置缓存。缓存可以极大的提升查询效率

`Mybaits`中默认定义了两级缓存：**一级缓存**和**二级缓存**

- 默认情况下只有一级缓存开启（SqlSession级别的缓存，也称为本地缓存）
- 二级缓存需要手动开启和配置，他是基于namespace的缓存
- 为了提高扩展性，mybatis定义了缓存cache，通过这个可以实现二级缓存



## 11.1 一级缓存

一级缓存就是`SqlSession open`到`SqlSession close`中间的部分有效

基本上就是这样。这个简单语句的效果如下:

- 映射语句文件中的所有 select 语句的结果将会被缓存。

```java
//查询
User user1 = userMapper.queryUserById(1);
System.out.println(user1);
System.out.println("======================");
User user2 = userMapper.queryUserById(1);
System.out.println(user2);


//日志内容
Opening JDBC Connection
Created connection 1276611190.
Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@4c178a76]
==>  Preparing: select * from mybatis.user where user_id = ? 
==> Parameters: 1(Integer)
<==    Columns: user_id, user_name, user_pwd
<==        Row: 1, wodeshijie, 12938797
<==      Total: 1
cn.edu.xidian.domain.User@6ff29830
======================
cn.edu.xidian.domain.User@6ff29830
Resetting autocommit to true on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@4c178a76]
Closing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@4c178a76]
Returned connection 1276611190 to pool.
    
//因为有缓存的原因，即使是第二次差查询，也只在数据库中进行了一遍查询
//如果换id查询就会有两次数据库查询操作
```

- 映射语句文件中的所有 insert、update 和 delete 语句会刷新缓存。

```java
//更新
HashMap map = new HashMap();

map.put("user_pwd","123142");
map.put("user_id",1);
//更新1好用户
userMapper.updateUserById(map);

sqlSession.commit();
//查询2号用户
User user1 = userMapper.queryUserById(2);
System.out.println(user1);

//日志内容
Opening JDBC Connection
Created connection 1709804316.
Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@65e98b1c]
==>  Preparing: update mybatis.user set user_pwd = ? where user_id = ? 
==> Parameters: 123142(String), 1(Integer)
<==    Updates: 1
Committing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@65e98b1c]
==>  Preparing: select * from mybatis.user where user_id = ? 
==> Parameters: 2(Integer)
<==    Columns: user_id, user_name, user_pwd
<==        Row: 2, flaj, 274832
<==      Total: 1
cn.edu.xidian.domain.User@2f465398
Resetting autocommit to true on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@65e98b1c]
Closing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@65e98b1c]
Returned connection 1709804316 to pool.
    
//修改了1号用户，查询了2号用户    
//从这里可以看出来，每当进行了数据的写操作，缓存都会更新
```

- 缓存会使用最近最少使用算法（LRU, Least Recently Used）算法来清除不需要的缓存。

- 缓存不会定时进行刷新（也就是说，没有刷新间隔）。

- 缓存会保存列表或对象（无论查询方法返回哪种）的 1024 个引用。

- 缓存会被视为读/写缓存，这意味着获取到的对象并不是共享的，可以安全地被调用者修改，而不干扰其他调用者或线程所做的潜在修改。

  **提示** 缓存只作用于 `cache` 标签所在的映射文件中的语句。如果你混合使用 Java API 和 XML 映射文件，在共用接口中的语句将不会被默认缓存。你需要使用` @CacheNamespaceRef `注解指定缓存作用域。

```java
//手动清除缓存
sqlSession.clearCache();
```





## 11.2 二级缓存

- 二级缓存也叫全局缓存，一级缓存作用域太低
- 基于namespace级别的缓存，一个命名空间，对应一个二级缓存
- 工作机制
  - 一个会话查询一条数据，这个数据会被放在当前会话的一级缓存中
  - 如果一级缓存失效了，启用的二级缓存才会启动
  - 新的会话查询信息，就可以从二级缓存中获取内容
  - 不同mapper查出的数据会放在对应的缓存中

```xml
<!--核心配置文件-->
<settings>
    <!--显式开启二级缓存-->
    <setting name="cacheEnabled"  value="true"/>
</settings>

<!--Mapper.xml文件-->
<cache
  eviction="FIFO"
  flushInterval="60000"
  size="512"
  readOnly="true"/>
```

这个更高级的配置创建了一个` FIFO` 缓存，每隔 `60 秒`刷新，最多可以存储结果对象或列表的 `512 `个引用，而且返回的对象被认为是只读的，因此对它们进行修改可能会在不同线程中的调用者产生冲突。

可用的清除策略有：

- `LRU` – 最近最少使用：移除最长时间不被使用的对象。
- `FIFO` – 先进先出：按对象进入缓存的顺序来移除它们。
- `SOFT` – 软引用：基于垃圾回收器状态和软引用规则移除对象。
- `WEAK` – 弱引用：更积极地基于垃圾收集器状态和弱引用规则移除对象。

默认的清除策略是 `LRU`。

`flushInterval（刷新间隔）`属性可以被设置为任意的正整数，设置的值应该是一个以毫秒为单位的合理时间量。 默认情况是不设置，也就是没有刷新间隔，缓存仅仅会在调用语句时刷新。

`size（引用数目）`属性可以被设置为任意正整数，要注意欲缓存对象的大小和运行环境中可用的内存资源。默认值是 `1024`。

`readOnly（只读）`属性可以被设置为 true 或 false。只读的缓存会给所有调用者返回缓存对象的相同实例。 因此这些对象不能被修改。这就提供了可观的性能提升。而可读写的缓存会（通过序列化）返回缓存对象的拷贝。 速度上会慢一些，但是更安全，因此默认值是 false。

**提示** 二级缓存是事务性的。这意味着，当 SqlSession 完成并提交时，或是完成并回滚，但没有执行 flushCache=true 的 insert/delete/update 语句时，缓存会获得更新。

```java
@Test
public void cacheTest(){
    SqlSession sqlSession1 = MybatisUntils.getSqlSession();
    UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
    User user1 = userMapper1.queryUserById(1);
    //一级缓存的失效就意味着对话的关闭
    sqlSession1.close();

    SqlSession sqlSession2 = MybatisUntils.getSqlSession();
    UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
    User user2 = userMapper2.queryUserById(1);



    System.out.println(user1);
    System.out.println("================");
    System.out.println(user1);


    sqlSession2.close();
}

//日志内容
Opening JDBC Connection
Created connection 590646109.
Setting autocommit to false on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@23348b5d]
==>  Preparing: select * from mybatis.user where user_id = ? 
==> Parameters: 1(Integer)
<==    Columns: user_id, user_name, user_pwd
<==        Row: 1, wodeshijie, 123142
<==      Total: 1
Resetting autocommit to true on JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@23348b5d]
Closing JDBC Connection [com.mysql.cj.jdbc.ConnectionImpl@23348b5d]
Returned connection 590646109 to pool.
Cache Hit Ratio [cn.edu.xidian.dao.UserMapper]: 0.5
User{user_id=1, user_name='wodeshijie', user_pwd='123142'}
================
User{user_id=1, user_name='wodeshijie', user_pwd='123142'}

//可以看出，当一级缓存失效之后，二级缓存还在起作用，不用再连接数据库去查询
```

