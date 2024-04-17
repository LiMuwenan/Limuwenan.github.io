# Spring

- Spring Core
  - Ioc, AOP
- Spring Data Access
  - Transations, Spring MyBatis
- Web Servlet
  - Spring MVC
- Integration
  - Email, Scheduling, AMQP, Security

IOC：控制反转，面向对象的设计思想。为了降低耦合度用到多态

Denpendency Injection：依赖注入，是IoC思想的实现方式

Ioc Container：Ioc 容器，是实现依赖注入的关键，本质上是一个工厂

通过引入Ioc容器，利用依赖注入的方式，实现了对象之间的解耦

就相当于，假如有 a 和 b 两个对象，在注入 IOC 之前，a 依赖于 b 那么对象 a 在初始化或者运行到某一点的时候，自己必须主动去创建对象 b 或者使用已经创建的对象 b ，无论是创建还是使用对象 b ，控制权都在自己手上 ，而注入 IOC 之后就变了，对象 a 与对象 b 之间失去了直接联系，当对象 a 运行到需要对象 b 的时候，IOC 容器会主动创建一个对象 b 注入到对象 a 需要的地方。其实通过上边这个举例可以很明显的就看出来，对象 a 获得依赖对象 b 的过程，由主动行为变为了被动行为，控制权颠倒过来了，这就是“控制反转”这个名称的由来。

# Spring MVC

Web开发

- 三层结构
  - 表现层、业务层、数据层
- MVC
  - Model：模型层
  - View：视图层
  - Controller：控制层
- 核心组件
  - 前端控制器：DispatcherServlet

MVC主要解决表现层的问题：

浏览器访问Controller，Controller调用业务层进行处理；然后将数据封装进model，model将数据给view，view将数据展现给浏览器

DispatcherServlet将路径解析，找到对应的Controller处理请求，Controller将处理好的数据封装好，通过View传递给前端控制器，前端控制器给前端模板进行渲染

# MyBaits

- 核心组件
  - SqlSessionFactory：用于创建SqlSession的工厂类
  - SqlSession：MyBatis的核心组件，用于执行SQL
  - 主配置文件：XML配置文件
  - Mapper接口：DAO接口
  - Mapper映射器：用于编写SQL，并将SQL和实体类映射的组件，采用XML、注解均可

# Redis

key：value

对某个实体（帖子，评论，回复）的点赞
    like:entity:entityType:entityId -> set(userId)

点赞频率可能较高，使用redis

- 使用Redis存储验证码
  - 验证码需要频繁的访问与刷新，对性能要求较高
  - 验证码不需要永久保存，通常在很短的时间后就会失效
  - 分布式部署时，存在Session共享的问题
- 使用Redis存储初登录凭证
  - 处理每次请求时，都要查询用户的登录凭证，访问的频率非常高
- 使用Redis缓存用户信息
  - 处理每次请求时，都要根据凭证查询用户信息，访问的频率非常高

# Kafka

阻塞队列

- Kafka
  - 分布式流媒体平台
  - 应用：系统消息、日志收集、用户行为跟踪、流式处理
- Kafka特点
  - 高吞吐量、消息持久化、高可靠性、高扩展性
- Kafka术语
  - Broker：每一个Kafka服务器称为Broker
  - Zookeeper：独立的应用，不属于Kafka。用该应用管理集群（Cluster）
  - Topic：主题，存放消息的地方
  - Partition：主题的子分区
  - Offset：消息在分区内存放的索引
  - Leader Replica：主副本，用来冗余的保存消息。当有请求时可以返回消息
  - Follower Replica：从副本，主副本出问题从从副本恢复

使用zookeeper管理集群，zookeepeer相当于注册中心

队列模式和发布订阅模式



封装好Event，创建生产者和消费者，然后将Event对象传给生产者，消费者就会消费该对象

# ElasticSearch

es会将要查询的数据在es中存储一份，被插入的数据会经过分词保存

- 简介
  - 一个分布式的、Restful风格的搜索引擎
  - 支持对各种类型数据的检索
  - 搜索速度快，可以提供实施的搜索服务
  - 便于水平扩展，每秒可以处理PB级别的海量数据
- 术语
  - 索引：对应mysql的database。6.0后对应表
  - 类型：对应mysql的table。6.0后废弃
  - 文档：对应mysql的一行数据
  - 字段：对应mysql的列。在es中以json字符串保存
  - 集群：
  - 节点：集群中的每一台服务器
  - 分片：一个大索引（表）拆成更多个索引
  - 副本：分片的备份

- 引入依赖
- 配置es
  - cluster_name、cluster_nodes
- Spring Boot Data ES
  - ElasticsearchTemplate
  - ElasticsearchRepository

```java
/**
* indexName：索引
* type : 类型
* shards： 分片
* replicas：副本
*/
@Document(indexName="discusspost", type = "_doc",shards = 6, replicas = 3)
public class DiscussPost{
    //主键
    @Id
    private int id;
    
    //整数字段
    @Field(type = FieldType.Integer)
    private int userId;
    
    //文本字段；analyzer：存储分词器，使分词量最多；searchAnalyzer：ik_smart，搜索时智能分词
    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "")
    private String titile;

}
```

# Spring Security

- 特征
  - 对身份的 认证 和 授权 提供全面的、可扩展的支持
  - 防止各种攻击，如会话固定攻击、点击劫持、csrf攻击等
  - 支持与Servlet API、Spring MVC等Web技术集成

十多个Fliter对整个权限进行控制，Filter在DispatcherServlet之前执行，Dispatcher在Interceptor之前

# HyperLogLog Bitmap

使用Redis进行网站数据的统计

Redis有两个高级数据类型

- HyperLogLog
  - 采用一种基数算法，用于完成独立总数的统计
  - 占据空间小，无论统计多少个数据，只占12k的内存空间
  - 不精确的统计算法、标准误差为0.81%
- Bitmap
  - 不是一种独立的数据结构，实际上是字符串
  - 支持按位存取数据，可以将其看成是byte数组
  - 适合存储大量连续的数据的布尔值

- UV（Unique Visitor）
  - 独立访客，需要通过用户IP排重统计数据
  - 每次访问都要进行统计
  - HyperLogLog，性能好，存储空间小
- DAU（Daily Active User）
  - 日活跃用户，需通过用户ID排重统计数据
  - 访问过一次，则认为其活跃
  - Bitmap，性能好、且可以统计精确结果

# Quartz

- JDK线程池
  - ExecutorService
  - ScheduledExecutorService
- Spring 线程池
  - ThredPoolTaskExecutor
  - ThredPoolTaskScheduler
- 分布式定时任务
  - Spring Quartz

实现定时任务

# Caffine

本地缓存（Caffine）->二级缓存（Redis，通常和本地缓存不在同一台服务器上）->DB

设置缓存后性能提高20倍





1.核心功能：
  \- 发帖、评论、私信、转发；
  \- 点赞、关注、通知、搜索；
  \- 权限、统计、调度、监控；
2.核心技术：
  \- Spring Boot、SSM
  \- Redis、Kafka、ElasticSearch
  \- Spring Security、Quartz、Caffeine
3.项目亮点：
  \- 项目构建在Spring Boot+SSM框架之上，并统一的进行了状态管理、事务管理、异常处理；
  \- 利用Redis实现了点赞和关注功能；
  \- 利用Kafka实现了异步的站内通知；
  \- 利用ElasticSearch实现了全文搜索功能，可准确匹配搜索结果，并高亮显示关键词；
  \- 利用Caffeine+Redis实现了两级缓存，并优化了热门帖子的访问，经过测试吞吐量提高20倍
  \- 利用Spring Security实现了权限控制，实现了多重角色、URL级别的权限管理；
  \- 利用HyperLogLog、Bitmap分别实现了UV、DAU的统计功能，100万用户数据只需*M内存空间；
  \- 利用Quartz实现了任务调度功能，并实现了定时计算帖子分数、定时清理垃圾文件等功能；
  \- 利用Actuator对应用的Bean、缓存、日志、路径等多个维度进行了监控，并通过自定义的端点对数据库连接进行了监控。













# 2 开发首页

- 开发流程

  - 1次请求执行过程

- 分布实现
  - 开发社区首页，显示前10个帖子
  - 开发分页组件，分页显示所有帖子

# 3 发送邮件

- 邮箱设置
  - 启用客户端SMTP服务
- Spring Email
  - 导入jar包
  - 邮箱参数配置
  - 使用JavaSendMail发送邮件
- 模板引擎
  - 使用thymeleaf发送HTML邮件 

# 4 注册

- 访问注册页面
  - 点击顶部区域的链接，打开注册页面
- 提交注册数据
  - 通过表单提交验证
  - 服务端验证账号是否已经存在、邮箱是否已经注册
  - 服务端发送激活邮件
- 激活注册账号
  - 点击邮件中的链接，访问服务端的激活服务

# 5 登录

- 访问登陆页面
- 登录
  - 验证账号，密码，验证码
  - 成功，生成登陆凭证，发放给客户端
  - 登录凭证可以用session，也可以数据库，为了后期方便redis，这里使用数据库
  - 失败，跳转回登录页
- 退出
  - 将凭证改为失效状态
  - 跳转至网站首页

## 5.6 检查登录状态

用户在未登录的情况下，虽然看不到个人设置页面，但是他可以通过直接访问路径的方法来访问该页面。为了避免这个漏洞，我们需要增加一个拦截器方法来拦截这些请求



- 拦截器
  - 在方法前标注自定义注解
  - 拦截所有请求，只处理带该注解的方法
- 自定义注解
  - 常用元注解（创建自定义注解时需要）
    - @Target：在类、方法还是字段上作用
    - @Retention：编译时有效还是运行时有效
    - @Document：生成文档时显示该注解吗
    - @Inherited：当有类继承该类时，这个注解会被继承吗
  - 如何读取注解
    - Method.getDeclaredAnnotations()
    - Method.getAnnotation(Class<T\> annotationClass)

# 6 用户信息



## 6.1 显示用户信息

- 拦截器示例
  - 定义拦截器，实现HandlerInterceptor
  - 配置拦截器，指定拦截路径和排除路径
- 拦截器应用
  - 在请求开始时查询登录用户
  - 在本次请求中持有用户数据
  - 在模板视图上显示用户数据
  - 在请求结束时清理用户

## 6.2 账号设置

- 上传文件
  - 请求：必须是post请求
  - 表单：enctype = "mutipart/form-data" 必须用这个属性
  - SpringMVC：通过 MutilpartFile 上传文件
- 开发流程
  - 访问账号设置页面
  - 上传头像
  - 获取头像

# 11 交流

## 11.1 点赞

- 点赞
  - 对帖子或者评论点赞
  - 第一次点赞，第二次取消
- 首页点赞数量
  - 统计帖子的点赞数量
- 详情页点赞数量
  - 统计点赞数量
  - 显示点赞状态

# 12 优化登录模块

- 使用Redis存储验证码
  - 验证码需要频繁的访问与刷新，对性能要求较高
  - 验证码不需要永久保存，通常在很短的时间后就会失效
  - 分布式部署时，存在Session共享的问题
- 使用Redis存储初登录凭证
  - 处理每次请求时，都要查询用户的登录凭证，访问的频率非常高
- 使用Redis缓存用户信息
  - 处理每次请求时，都要根据凭证查询用户信息，访问的频率非常高