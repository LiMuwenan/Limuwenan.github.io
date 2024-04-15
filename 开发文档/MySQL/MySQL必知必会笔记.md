# 1 了解SQL

一个库中可以有N张表，每个表中有行列，像excel

每行是一条记录

主键为一列或者一组列，其值能够唯一区分表中的每个行

- 任意两行都不具有相同的主键值；
- 每个行都必须具有一个主键值（主键列不允许NULL值）。





## 1.1 win10下载安装mysql

[进入网址下载最小的zip包](https://downloads.mysql.com/archives/community/)

添加环境变量

变量名MYSQL_HOME

变量路径mysql解压地址

添加PATH: %MYSQL_HOME%\bin

在安装目录下配置my.ini文件

```ini
[mysql]
#设置mysql客户端默认字符集
default-character-set=UTF8MB4
[mysqld]
#设置3306端口
port=3306
#mysql安装目录
basedir=D:\Program\mysql-8.0.21-winx64
#mysql数据存放目录
datadir=D:\Program\mysql-8.0.21-winx64\data
#允许最大连接数
max_connections=200
#允许连接失败的次数
max_connect_errors=10
#默认使用“mysql_native_password”插件认证
default_authentication_plugin=mysql_native_password
#服务端使用的字符集默认为8比特编码的latin1字符集
character-set-server=UTF8MB4
#开启查询缓存
explicit_defaults_for_timestamp=true
#创建新表时将使用的默认存储引擎
default-storage-engine=INNODB
#等待超时时间秒
wait_timeout=60
#交互式连接超时时间秒
interactive-timeout=600

```

管理员模式下进行配置

```
# 卸载服务（第一次安装可以不执行）
net stop mysql
mysqld remove

# 安装服务
mysqld --install

# 初始化密码
mysqld --initialize --console

p2ZsJITLw7>y

# 启动mysql修改密码
net start mysql

# 登录，修改root用户 密码和访问权限
$ mysql -u root -p'初始密码'
$ ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '新密码';
$ use mysql;
$ update user set host='%' where host='localhost';
$ delete from user where host !='%';
$ flush privileges;
$ ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY '新密码';
$ quit;

#测试登录
$ mysql -u root -p'150621'

```



# 2 MySQL简介

# 3 使用MySQL

## 3.2 选择数据库

```mysql
USE crashcourse;
# 输出 Database changed

# 创建数据库
CREATE DATABASE demo;

# 删除数据库
DROP DATABASE demo;

# 5版本设置密码
update user set password=PASSWORD('nima') where user='yonghu';
# 8版本设置密码
alter user 'root'@'localhost' identified by 'newpassword'; newpassword是要设的新密码
```

## 3.3 了解数据库和表

```mysql
SHOW DATABASES;
# 输出,返回一个可用数据库列表
+--------------------+
| Database           |
+--------------------+
| information_schema |
| mysql              |
| performance_schema |
| ruoyi              |
| servlet            |
+--------------------+

SHOW TABLES;
# 输出，返回当前选择的数据库的可用表的列表
+-------------------+
| Tables_in_servlet |
+-------------------+
| customers         |
| orderitems        |
| orders            |
| productnotes      |
| products          |
| vendors           |
| websites          |
+-------------------+

SHOW COLUMNS FROM customers;
# 输出，从给定表中返回行中的字段名，数据类型，是否允许NULL，键信息，默认值以及其他信息
# 这个不是列的内容，而是列本身的信息
# DESCRIBE customers;
# 上句同SHOW COLUMNS FROM效果  
+--------------+-----------+------+-----+---------+----------------+
| Field        | Type      | Null | Key | Default | Extra          |
+--------------+-----------+------+-----+---------+----------------+
| cust_id      | int       | NO   | PRI | NULL    | auto_increment |
| cust_name    | char(50)  | NO   |     | NULL    |                |
| cust_address | char(50)  | YES  |     | NULL    |                |
| cust_city    | char(50)  | YES  |     | NULL    |                |
| cust_state   | char(5)   | YES  |     | NULL    |                |
| cust_zip     | char(10)  | YES  |     | NULL    |                |
| cust_country | char(50)  | YES  |     | NULL    |                |
| cust_contact | char(50)  | YES  |     | NULL    |                |
| cust_email   | char(255) | YES  |     | NULL    |                |
+--------------+-----------+------+-----+---------+----------------+
```

# 4 检索数据

## 4.2 检索单个列

```mysql
SELECT prod_name FROM products;
# 输出
+----------------+
| prod_name      |
+----------------+
| .5 ton anvil   |
| 1 ton anvil    |
| 2 ton anvil    |
| Detonator      |
| Bird seed      |
| Carrots        |
| Fuses          |
| JetPack 1000   |
| JetPack 2000   |
| Oil can        |
| Safe           |
| Sling          |
| TNT (1 stick)  |
| TNT (5 sticks) |
+----------------+
# 上述语句利用 SELECT 语句从 products 表中检索一个名为 prod_name 的列。所需的列名在 SELECT 关键字之后给出，FROM关键字指出从其中检索数据的表名。
```

## 4.3 检索多个列

```mysql
SELECT prod_id, prod_name, prod_price FROM products;
# 输出
+---------+----------------+------------+
| prod_id | prod_name      | prod_price |
+---------+----------------+------------+
| ANV01   | .5 ton anvil   |       5.99 |
| ANV02   | 1 ton anvil    |       9.99 |
| ANV03   | 2 ton anvil    |      14.99 |
| DTNTR   | Detonator      |      13.00 |
| FB      | Bird seed      |      10.00 |
| FC      | Carrots        |       2.50 |
| FU1     | Fuses          |       3.42 |
| JP1000  | JetPack 1000   |      35.00 |
| JP2000  | JetPack 2000   |      55.00 |
| OL1     | Oil can        |       8.99 |
| SAFE    | Safe           |      50.00 |
| SLING   | Sling          |       4.49 |
| TNT1    | TNT (1 stick)  |       2.50 |
| TNT2    | TNT (5 sticks) |      10.00 |
+---------+----------------+------------+
# 这条语句使用SELECT语句从表products中选择数据。在这个例子中，指定了3个列名，列名之间用逗号分隔。
```

## 4.4 检索所有列

```mysql
SELECT * FROM products;

# 使用通配符可以检索出不知道列名的列
```

## 4.5 检索不同的行

```mysql
SELECT vend_id FROM products;
# 输出，从表products中检索vend_id列，其中有很多行是重复的
+---------+
| vend_id |
+---------+
|    1001 |
|    1001 |
|    1001 |
|    1002 |
|    1002 |
|    1003 |
|    1003 |
|    1003 |
|    1003 |
|    1003 |
|    1003 |
|    1003 |
|    1005 |
|    1005 |
+---------+
# 使用DISTINCT关键词屏蔽重复的行
SELECT DISTINCT vend_id FROM products;

+---------+
| vend_id |
+---------+
|    1001 |
|    1002 |
|    1003 |
|    1005 |
+---------+
# SELECT DISTINCT vend_id,prod_price除非两个列的内容都不同，否则都会被检索
```

## 4.6 限制结果

```mysql
SELECT prod_name FROM products LIMIT 5;
# 输出，原结果变为了不超过5行
+--------------+
| prod_name    |
+--------------+
| .5 ton anvil |
| 1 ton anvil  |
| 2 ton anvil  |
| Detonator    |
| Bird seed    |
+--------------+

SELECT prod_name FROM products LIMIT 4,5;
# 输出结果为从第4行开始，输出不超过5行。 下标从0开始
SELECT prod_name FROM products LIMIT 5 OFFSET 4;
```

## 4.7 使用完全限定的表名

```mysql
SELECT products.prod_name FROM crashcourse.products;
# 完全限定
```

# 5 排序检索数据

## 5.1 排序数据

```mysql
SELECT prod_name FROM products ORDER BY prod_name;
# 以字母顺序默认升序排列
```

## 5.2 按多个列排序

```mysql
SELECT prod_id, prod_price, prod_name FROM products ORDER BY prod_price, prod_name;
# 输出， 检索三个列，并按照后两列排序。只有当prod_price列完全相同时，才会按照prod_name列排序
+---------+------------+----------------+
| prod_id | prod_price | prod_name      |
+---------+------------+----------------+
| FC      |       2.50 | Carrots        |
| TNT1    |       2.50 | TNT (1 stick)  |
| FU1     |       3.42 | Fuses          |
| SLING   |       4.49 | Sling          |
| ANV01   |       5.99 | .5 ton anvil   |
| OL1     |       8.99 | Oil can        |
| ANV02   |       9.99 | 1 ton anvil    |
| FB      |      10.00 | Bird seed      |
| TNT2    |      10.00 | TNT (5 sticks) |
| DTNTR   |      13.00 | Detonator      |
| ANV03   |      14.99 | 2 ton anvil    |
| JP1000  |      35.00 | JetPack 1000   |
| SAFE    |      50.00 | Safe           |
| JP2000  |      55.00 | JetPack 2000   |
+---------+------------+----------------+
```

## 5.3 指定排序方向

```mysql
SELECT prod_id, prod_price, prod_name FROM products ORDER BY prod_price DESC;
# 输出， 按照字典序降序排列(默认是升序)
+---------+------------+----------------+
| prod_id | prod_price | prod_name      |
+---------+------------+----------------+
| JP2000  |      55.00 | JetPack 2000   |
| SAFE    |      50.00 | Safe           |
| JP1000  |      35.00 | JetPack 1000   |
| ANV03   |      14.99 | 2 ton anvil    |
| DTNTR   |      13.00 | Detonator      |
| FB      |      10.00 | Bird seed      |
| TNT2    |      10.00 | TNT (5 sticks) |
| ANV02   |       9.99 | 1 ton anvil    |
| OL1     |       8.99 | Oil can        |
| ANV01   |       5.99 | .5 ton anvil   |
| SLING   |       4.49 | Sling          |
| FU1     |       3.42 | Fuses          |
| FC      |       2.50 | Carrots        |
| TNT1    |       2.50 | TNT (1 stick)  |
+---------+------------+----------------+
SELECT prod_id, prod_price, prod_name FROM products ORDER BY prod_price DESC, prod_name;	
# 只有当prod_price列完全相同时，才会按照prod_name列排序
# prod_price列按照降序排列，prod_name按照升序排列
```

# 6 过滤数据

WHERE子句的位置： 在同时使用ORDER BY和WHERE子句时，应该让ORDER BY 位于WHERE之后

| 操作符  |        说明        |
| :-----: | :----------------: |
|    =    |        等于        |
|   <>    |       不等于       |
|   !=    |       不等于       |
|    <    |        小于        |
|   <=    |      小于等于      |
|    >    |        大于        |
|   >=    |      大于等于      |
| BETWEEN | 在指定的两个值之间 |

## 6.2 WHERE子句

```mysql
SELECT prod_name, prod_price FROM products WHERE prod_name = 'fuses';

# 输出，同时检索products表中的连个列，只返回prod_name为fuses的行
+-----------+------------+
| prod_name | prod_price |
+-----------+------------+
| Fuses     |       3.42 |
+-----------+------------+

SELECT prod_name, prod_price FROM products WHERE prod_price BETWEEN 5 AND 10;

# 输出，介于5和10之间的行
+----------------+------------+
| prod_name      | prod_price |
+----------------+------------+
| .5 ton anvil   |       5.99 |
| 1 ton anvil    |       9.99 |
| Bird seed      |      10.00 |
| Oil can        |       8.99 |
| TNT (5 sticks) |      10.00 |
+----------------+------------+

SELECT prod_name, prod_price FROM products WHERE prod_price IS NULL;

# 输出，空值检查，返回值为空的行，不是价格为0
```

## 7.1 组合WHERE子句

```mysql
SELECT prod_id, prod_price, prod_name FROM products WHERE vend_id = 1003 AND prod_price <= 10;

# 输出，检索出id为1003并且造价小于等于10的行
+---------+------------+----------------+
| prod_id | prod_price | prod_name      |
+---------+------------+----------------+
| FB      |      10.00 | Bird seed      |
| FC      |       2.50 | Carrots        |
| SLING   |       4.49 | Sling          |
| TNT1    |       2.50 | TNT (1 stick)  |
| TNT2    |      10.00 | TNT (5 sticks) |
+---------+------------+----------------+

SELECT prod_price, prod_name FROM products WHERE vend_id = 1002 OR vend_id = 1003;
# 输出，或关系
+------------+----------------+
| prod_price | prod_name      |
+------------+----------------+
|       3.42 | Fuses          |
|       8.99 | Oil can        |
|      13.00 | Detonator      |
|      10.00 | Bird seed      |
|       2.50 | Carrots        |
|      50.00 | Safe           |
|       4.49 | Sling          |
|       2.50 | TNT (1 stick)  |
|      10.00 | TNT (5 sticks) |
+------------+----------------+

# 当AND和OR嵌套式，AND的优先级高于OR
# 如果需要OR先执行，就需要使用括号
SELECT prod_name, prod_price FROM products WHERE (vend_id = 1002 OR vend_id = 1003) AND prod_price >= 10;
# 输出，先执行OR，再执行AND
+----------------+------------+
| prod_name      | prod_price |
+----------------+------------+
| Detonator      |      13.00 |
| Bird seed      |      10.00 |
| Safe           |      50.00 |
| TNT (5 sticks) |      10.00 |
+----------------+------------+

SELECT vend_id, prod_name, prod_price FROM products WHERE vend_id IN (1002,1003,1005) ORDER BY prod_name;
# 输出,满足括号内所有值的行
+---------+----------------+------------+
| vend_id | prod_name      | prod_price |
+---------+----------------+------------+
|    1003 | Bird seed      |      10.00 |
|    1003 | Carrots        |       2.50 |
|    1003 | Detonator      |      13.00 |
|    1002 | Fuses          |       3.42 |
|    1005 | JetPack 1000   |      35.00 |
|    1005 | JetPack 2000   |      55.00 |
|    1002 | Oil can        |       8.99 |
|    1003 | Safe           |      50.00 |
|    1003 | Sling          |       4.49 |
|    1003 | TNT (1 stick)  |       2.50 |
|    1003 | TNT (5 sticks) |      10.00 |
+---------+----------------+------------+

SELECT prod_name, prod_price FROM products WHERE vend_id NOT IN (1002,1003) ORDER BY prod_name;

# 输出，NOT关键字，否定跟在它之后的条件
+--------------+------------+
| prod_name    | prod_price |
+--------------+------------+
| .5 ton anvil |       5.99 |
| 1 ton anvil  |       9.99 |
| 2 ton anvil  |      14.99 |
| JetPack 1000 |      35.00 |
| JetPack 2000 |      55.00 |
+--------------+------------+
```

# 8 通配符过滤

使用通配符时，就需要使用关键字LIKE

```mysql
# % 通配符
SELECT prod_id, prod_name FROM products WHERE prod_name LIKE 'jet%';
# 输出，jet开头后面无所谓，所以是通配。同样观察到不区分大小写。通配符也可以用到前面、中间
+---------+--------------+
| prod_id | prod_name    |
+---------+--------------+
| JP1000  | JetPack 1000 |
| JP2000  | JetPack 2000 |
+---------+--------------+

# _ 通配符
SELECT prod_id, prod_name FROM products WHERE prod_name LIKE '_ ton anvil';
# 输出，这个通配符只匹配一个字符。0个或者多个都不行
+---------+-------------+
| prod_id | prod_name   |
+---------+-------------+
| ANV02   | 1 ton anvil |
| ANV03   | 2 ton anvil |
+---------+-------------+
```

# 9 使用正则进行搜索

### 9.2.1 基本使用

```mysql
SELECT prod_name FROM products WHERE prod_name REGEXP '1000';
# 输出，REGEXP后面跟随的是一个正则表达式，此式一位置检索包含1000的字符串
+--------------+
| prod_name    |
+--------------+
| JetPack 1000 |
+--------------+

SELECT prod_name FROM products WHERE prod_name REGEXP '.000';
# 输出，这里匹配的是 任意一个字符与000的连接结果
+--------------+
| prod_name    |
+--------------+
| JetPack 1000 |
| JetPack 2000 |
+--------------+
```

### 9.2.2 进行OR匹配

```mysql
SELECT prod_name FROM products WHERE prod_name REGEXP '1000|2000';
# 输出，这里匹配的是包含1000或者2000
+--------------+
| prod_name    |
+--------------+
| JetPack 1000 |
| JetPack 2000 |
+--------------+
```

### 9.2.3 匹配几个字符之一

```mysql 
SELECT prod_name FROM products WHERE prod_name REGEXP '[123] Ton';
# 输出，这里匹配的是包含1 ton或者2 ton或者3 ton。在正则表达式中多个数字或者字符可以使用中括号括起来表达其中之一
+-------------+
| prod_name   |
+-------------+
| 1 ton anvil |
| 2 ton anvil |
+-------------+
```

### 9.2.4 匹配范围

```mysql
SELECT prod_name FROM products WHERE prod_name REGEXP '[1-5] Ton';
# 输出，表达范围的方式[]
+--------------+
| prod_name    |
+--------------+
| .5 ton anvil |
| 1 ton anvil  |
| 2 ton anvil  |
+--------------+
```

### 9.2.5 匹配特殊字符

```mysql
# 特殊字符就是 . [] | - 等等
# 这个时候需要转义才可以
SELECT vend_name FROM vendors WHERE vend_name REGEXP '\\.';
# 输出，意思是包含.的内容，因为是特殊字符，所以需要使用转义字符
+--------------+
| vend_name    |
+--------------+
| Furball Inc. |
+--------------+
```

```mysql
\\f								# 换页
\\n								# 换行
\\r								# 回车
\\t								# 制表
\\v								# 纵向制表
```

### 9.2.6 匹配字符类

```mysql
[:alnum:]    					# 任意字母和数字(同[a-zA-Z0-9])
[:alpha:]						# 任意字符([a-zA-Z])
[:blank:]						# 空格和制表([\\t])
[:cntrl:]						# ASCII控制字符([ASCII 0到31和127])
[:digit:]						# 任意数字([0-9])
[:graph:]						# 与[:print:]相同但不包含空格
[:lower:]						# 任意小写字母([a-z])
[:print:]						# 任意可打印字符
[:punct:]						# 既不在[:alpha:]也不在[:cntrl:]的字符
[:space:]						# 包括空格在内的任意空白字符
[:upper:]						# 任意大写字母([A-Z])
[:xdigit:]						# 任意16进制数字([a-fA-F0-9])
```

### 9.2.7 匹配多个实例

```mysql
# 重复元字符
*								# 0个或多个匹配
+								# 1个或多个匹配
?								# 0个或1个匹配
{n}								# 指定数目的匹配
{n,}							# 不少于指定数目的匹配
{n,m}							# 匹配数目的范围，不超过255
```

```mysql
SELECT prod_name FROM products WHERE prod_name REGEXP '\\([0-9] sticks?\\)';
# 输出，\\对(进行了转义，sticks后的?是的sticks后面的s成为可选项，出现1次或者0次
+----------------+
| prod_name      |
+----------------+
| TNT (1 stick)  |
| TNT (5 sticks) |
+----------------+

SELECT prod_name FROM products WHERE prod_name REGEXP '[:digit:]{4}';
# 输出，{4}对前面数字出现的次数进行了限制，只出现4此，也就是4位数
+--------------+
| prod_name    |
+--------------+
| JetPack 1000 |
| JetPack 2000 |
+--------------+
```

### 9.2.8 定位符

```mysql
^								# 文本的开始
$								# 文本的结尾
[[:<:]]							# 词的开始
[[:>:]]							# 词的开始
```

```mysql
SELECT prod_name FROM products WHERE prod_name REGEXP '^[0-9\\.]';
# 输出，从文本开头开始匹配，出现数字或者.的项
+--------------+
| prod_name    |
+--------------+
| .5 ton anvil |
| 1 ton anvil  |
| 2 ton anvil  |
+--------------+
```

# 10 创建计算字段

## 10.2 拼接字段

拼接：将值连接在一起构成单个值

```mysql
SELECT Concat(vend_name,'(',vend_country,')') FROM vendors ORDER BY vend_name;
# 输出，将vend_name字段与vend_country字段拼接到一起变成了一个值，使用到Concat()函数
+----------------------------------------+
| Concat(vend_name,'(',vend_country,')') |
+----------------------------------------+
| ACME(USA)                              |
| Anvils R Us(USA)                       |
| Furball Inc.(USA)                      |
| Jet Set(England)                       |
| Jouets Et Ours(France)                 |
| LT Supplies(USA)                       |
+----------------------------------------+

SELECT Concat(vend_name,'(',vend_country,')') AS vend_title FROM vendors ORDER BY vend_name;
# 输出，加上AS关键字可以更换表名，方便操作
+------------------------+
| vend_title             |
+------------------------+
| ACME(USA)              |
| Anvils R Us(USA)       |
| Furball Inc.(USA)      |
| Jet Set(England)       |
| Jouets Et Ours(France) |
| LT Supplies(USA)       |
+------------------------+
```

## 10.3 执行算术计算

## 10.4 删除文本后的空格字段

```mysql
SELECT Concat(RTrim(vend_name),'(',RTrim(vend_country),')') AS vend_title FROM vendors ORDER BY vend_name;
# 就是去掉空格
+------------------------+
| vend_title             |
+------------------------+
| ACME(USA)              |
| Anvils R Us(USA)       |
| Furball Inc.(USA)      |
| Jet Set(England)       |
| Jouets Et Ours(France) |
| LT Supplies(USA)       |
+------------------------+
# RTrim()		去掉右边的空格
# LTrim()		去掉左边的空格
# Trim()		去掉两边的空格
```



# 11 使用数据处理函数

### 11.2.1 文本处理函数

|    函数     |       说明        |
| :---------: | :---------------: |
|   Left()    | 返回串左边的字符  |
|  Length()   |   返回串的长度    |
|  Locate()   | 找出串的一个子串  |
|   Lower()   |  将串转换为小写   |
|   LTrim()   | 去掉串左边的空格  |
|   Right()   | 返回串右边的字符  |
|   RTrim()   | 去掉串右边的空格  |
|  Soundex()  | 返回串的SOUNDEX值 |
| SubString() |  返回子串的字符   |
|   Upper()   |  将串转换为大写   |

SOUNDEX是将字符串转换为以发音为基础的字母数字模式，发音类似时这个值就是相同的。

### 11.2.2 日期和时间处理函数

|     函数      |              说明              |
| :-----------: | :----------------------------: |
|   AddDate()   |     增加一个日期(天、周等)     |
|   AddTime()   |     增加一个时间(时、分等)     |
|   CurDate()   |          返回当前日期          |
|   CurTime()   |          返回当前时间          |
|    Date()     |     返回日期时间的日期部分     |
|  DateDiff()   |        计算两个日期之差        |
|  Date_Add()   |     高度灵活的日期运算函数     |
| Date_Format() |  返回一个格式化的日期或时间串  |
|     Day()     |     返回一个日期的天数部分     |
|  DayOfWeek()  | 对于一个日期，返回对应的星期几 |
|    Hour()     |     返回一个时间的小时部分     |
|   Minute()    |     返回一个时间的分钟部分     |
|    Month()    |     返回一个日期的月份部分     |
|     Now()     |       返回当前日期和时间       |
|   Second()    |      返回一个时间的秒部分      |
|    Time()     |   返回一个日期时间的时间部分   |
|    Year()     |     返回一个日期的年份部分     |

### 11.2.3 数值处理函数

|  函数  |  说明  |
| :----: | :----: |
| Abs()  | 绝对值 |
| Cos()  |  余弦  |
| Exp()  | 指数值 |
| Mod()  |  取余  |
|  Pi()  | 圆周率 |
| Rand() | 随机数 |
| Sin()  | 正弦值 |
| Sqrt() | 平方根 |
| Tan()  | 正弦值 |

# 12 汇总数据

## 12.1 聚集函数

聚集函数：运行在行组上，计算和返回单个值的函数

|  函数   |       说明       |                    使用                     |
| :-----: | :--------------: | :-----------------------------------------: |
|  AVG()  |  返回某列平均值  |    SELECT AVG(prod_price) FROM products;    |
| COUNT() |   返回某列行数   | SELECT COUNT(*) AS num_cust FROM customers; |
|  MAX()  |  返回某列最大值  |    SELECT MAX(prod_price) FROM products;    |
|  MIN()  | 返回某列的最小值 |    SELECT MIN(prod_price) FROM products;    |
|  SUM()  |  返回某列值之和  |    SELECT SUM(quantity) FROM orderitems;    |

## 12.2 聚集不同值

以上的函数都可以如下使用：

```mysql
SELECT AVG(prod_price) AS avg_price FROM products WHERE vend_id = 1003;
+-----------+
| avg_price |
+-----------+
| 13.212857 |
+-----------+
SELECT AVG(DISTINCT prod_price) AS avg_price FROM products WHERE vend_id = 1003;
# 使用DISTINCT关键字将重复的价钱过滤掉。因为低于平均值的价钱被过滤，所以平均值升高
+-----------+
| avg_price |
+-----------+
| 15.998000 |
+-----------+
```

# 13 分组数据

## 13.2 创建分组

```mysql
SELECT vend_id, COUNT(*) AS num_prods FROM products GROUP BY vend_id;
# 输出，通过vend_id将所有行分组，并对每组所有行进行一个统计
+---------+-----------+
| vend_id | num_prods |
+---------+-----------+
|    1001 |         3 |
|    1002 |         2 |
|    1003 |         7 |
|    1005 |         2 |
+---------+-----------+
```

使用GROUP BY的一些注意事项：

- GROUP BY子句可以包含任意数目的列。这使得能对分组进行嵌套，为数据分组提供更细致的控制。
- 如果在GROUP BY子句中嵌套了分组，数据将在最后规定的分组上进行汇总。换句话说，在建立分组时，制定的所有列都一起计算（所以不能从个别的列取回数据）。
- GROUP BY子句中列出的每个列都必须是检索列或者有效的表达式（但不能是聚集函数）。如果在SELECT中使用表达式，则必须在GROUP BY子句中指定相同的表达式。不能使用别名。
- 除聚集计算语句外，SELECT 语句中的每个列都必须在GROUP BY子句中给出。
- 如果分组列中具有NULL值，则NULL值将作为一个分组返回 。如果列中有多行NULL值，他们将分为一组。
- GROUP BY子句必须出现在WHERE子句之后，ORDER BY子句之前

```mysql
# 使用WITH ROLLUP可以得到每个分组以及每个分组汇总级别的值 
SELECT vend_id, COUNT(*) AS num_prods FROM products GROUP BY vend_id WITH ROLLUP;
+---------+-----------+
| vend_id | num_prods |
+---------+-----------+
|    1001 |         3 |
|    1002 |         2 |
|    1003 |         7 |
|    1005 |         2 |
|    NULL |        14 |
+---------+-----------+
```

## 13.3 过滤分组

过滤分组，规定包括那些分组，排除哪些分组。

```mysql
# HAVING对分组进行过滤
SELECT cust_id,COUNT(*) AS orders FROM orders GROUP BY cust_id HAVING COUNT(*)>=2;
# 输出，这条语句对分组组内小于2的进行了过滤
+---------+--------+
| cust_id | orders |
+---------+--------+
|   10001 |      2 |
+---------+--------+
```

## 13.5 SELECT子句顺序

|   子句   |        说明        |    是否必须    |
| :------: | :----------------: | :------------: |
|  SELECT  | 要返回的列或表达式 |       是       |
|   FROM   |  从中检索数据的表  | 从表选择时使用 |
|  WHERE   |      行级过滤      |       否       |
| GROUP BY |      分组说明      |       否       |
|  HAVING  |      组级过滤      |       否       |
| ORDER BY |    输出排序顺序    |       否       |
|  LIMIT   |    要检索的行数    |       否       |

# 14 子查询

##  14.2 利用子查询进行过滤

```mysql
SELECT cust_id FROM orders WHERE order_num IN(
	SELECT order_num FROM orderitems WHERE prod_id = 'TNT2'
);
# 输出
+---------+
| cust_id |
+---------+
|   10001 |
|   10004 |
+---------+
# 在下面这条语句的结果中继续进行查询
# SELECT order_num FROM orderitems WHERE prod_id = 'TNT2'
+-----------+
| order_num |
+-----------+
|     20005 |
|     20007 |
+-----------+
```

## 14.3 作为计算字段使用子查询

```mysql
SELECT cust_name,cust_state,
	(SELECT COUNT(*) FROM orders WHERE orders.cust_id = customers.cust_id) AS orders)
	FROM customers ORDER BY cust_name;
# 输出，这里使用了完全限定名。COUNT(*)计算字段，起别名orders
# 这种在子查询中涉及到了其他的表，也称为相关子查询
+----------------+------------+--------+
| cust_name      | cust_state | orders |
+----------------+------------+--------+
| Coyote Inc.    | MI         |      2 |
| E Fudd         | IL         |      1 |
| Mouse House    | OH         |      0 |
| Wascals        | IN         |      1 |
| Yosemite Place | AZ         |      1 |
+----------------+------------+--------+
```

# 15 联结表

### 15.1.1 关系表

例子：一个表vendors存储供应商信息（名称、地址、电话等），另一个表products存储产品信息。vendors表中，供应商的唯一标识（id）作为主键。products表中在每行的产品信息中还有供应商id，不提供其他供应商的信息，从products表中的供应商id获取vendors表中的具体供应商信息。vendors表的主键又叫做products表的外键

**外键**：外键为某表中的一列， 它包含另一个表的主键值，定义了两个表之间的关系。

**可伸缩性**：能够适应不断增加的工作量而不失败。设计良好的数据库或应用程序称之为伸缩性好。

## 15.2 创建联结

```mysql
SELECT vend_name,prod_name,prod_price FROM vendors,products 
WHERE vendors.vend_id = products.vend_id ORDER BY vend_name,prod_name;
# 输出，通过WHERE子句，将id相同的行进行了联结，并输出
+-------------+----------------+------------+
| vend_name   | prod_name      | prod_price |
+-------------+----------------+------------+
| ACME        | Bird seed      |      10.00 |
| ACME        | Carrots        |       2.50 |
| ACME        | Detonator      |      13.00 |
| ACME        | Safe           |      50.00 |
| ACME        | Sling          |       4.49 |
| ACME        | TNT (1 stick)  |       2.50 |
| ACME        | TNT (5 sticks) |      10.00 |
| Anvils R Us | .5 ton anvil   |       5.99 |
| Anvils R Us | 1 ton anvil    |       9.99 |
| Anvils R Us | 2 ton anvil    |      14.99 |
| Jet Set     | JetPack 1000   |      35.00 |
| Jet Set     | JetPack 2000   |      55.00 |
| LT Supplies | Fuses          |       3.42 |
| LT Supplies | Oil can        |       8.99 |
+-------------+----------------+------------+
```

### 15.2.1 WHERE子句的重要性

**笛卡尔积**：由没有联结条件的表关系返回的结果为笛卡尔积。检索出的行的数目将是第一个表中的行数乘以第二个表中的行数。

```mysql
SELECT vend_name,prod_name,prod_price FROM vendors,products ORDER BY vend_name,prod_name;
# 输出，在没有WHERE子句的情况下， vendors.vend_name的每一行与products.prod_name,products.prod_price的每一行进行匹配，笛卡尔积（products.prod_name,products.prod_price看做一行，这两个行不再进行笛卡尔积匹配）
# 在这个结果中，有很多错误行，某个供应商应该是没有某项产品的。所以需要使用WHERE子句来限定正确的行
```

### 15.2.2 内部联结

上节中的联结称为**等值联结**，它基于两个表之间的相等测试。这种联结也称为内部联结。

```mysql
SELECT vend_name,prod_name,prod_price FROM vendors INNER JOIN products ON vendors.vend_id = products.vend_id;
# 输出，使用INNER JOIN进行两个表的联结，这个时候用ON关键字替代WHERE关键字，含义是一样的
+-------------+----------------+------------+
| vend_name   | prod_name      | prod_price |
+-------------+----------------+------------+
| Anvils R Us | .5 ton anvil   |       5.99 |
| Anvils R Us | 1 ton anvil    |       9.99 |
| Anvils R Us | 2 ton anvil    |      14.99 |
| LT Supplies | Fuses          |       3.42 |
| LT Supplies | Oil can        |       8.99 |
| ACME        | Detonator      |      13.00 |
| ACME        | Bird seed      |      10.00 |
| ACME        | Carrots        |       2.50 |
| ACME        | Safe           |      50.00 |
| ACME        | Sling          |       4.49 |
| ACME        | TNT (1 stick)  |       2.50 |
| ACME        | TNT (5 sticks) |      10.00 |
| Jet Set     | JetPack 1000   |      35.00 |
| Jet Set     | JetPack 2000   |      55.00 |
+-------------+----------------+------------+
```

### 15.2.3 连接多个表

```mysql
SELECT  prod_name,vend_name,prod_price,quantity FROM orderitems,products,vendors
WHERE products.vend_id = vendors.vend_id
AND orderitems.prod_id = products.prod_id
AND order_num = 20005;
# 输出，词条语句显示编号为20005的物品，订单物品存储在orderitems表中，。每个产品按其ID存储，它引用products表中的产品。这些产品通过供应商ID联结到vendors表中相应的供应商，供应商ID存储在每个产品的记录中。
+----------------+-------------+------------+----------+
| prod_name      | vend_name   | prod_price | quantity |
+----------------+-------------+------------+----------+
| .5 ton anvil   | Anvils R Us |       5.99 |       10 |
| 1 ton anvil    | Anvils R Us |       9.99 |        3 |
| TNT (5 sticks) | ACME        |      10.00 |        5 |
| Bird seed      | ACME        |      10.00 |        1 |
+----------------+-------------+------------+----------+
```

# 16 创建高级联结

## 16.1 使用表别名

```mysql
SELECT Concat(RTrim(vend_name), '(',RTrim(vend_country),')') AS vend_title FROM vendors ORDER BY vend_name;
# 输出，对上面的查询结果使用别名
+------------------------+
| vend_title             |
+------------------------+
| ACME(USA)              |
| Anvils R Us(USA)       |
| Furball Inc.(USA)      |
| Jet Set(England)       |
| Jouets Et Ours(France) |
| LT Supplies(USA)       |
+------------------------+

SELECT cust_name,cust_contact FROM customers AS c, orders AS o,orderitems AS oi
WHERE c.cust_id = o.cust_id
AND oi.order_num = o.order_num
AND prod_id = 'TNT2';
# 输出，对要查询的表进行别名替换，在WHERE子句中使用别名来方便查找
+----------------+--------------+
| cust_name      | cust_contact |
+----------------+--------------+
| Coyote Inc.    | Y Lee        |
| Yosemite Place | Y Sam        |
+----------------+--------------+
```

## 16.2 不同类型的联结

### 16.2.1 自联结

```mysql
# 子查询
SELECT prod_id,prod_name FROM products WHERE vend_id = (SELECT vend_id FROM products WHERE prod_id = 'DTNTR');
# 联结
SELECT p1.prod_id,p1.prod_name FROM products AS p1,products AS p2 WHERE p1.vend_id = p2.vend_id
AND p2.prod_id = 'DTNTR';
# 输出。子查询方式先查找到id，再用id作为过滤条件进行查询。自联结通常替代在同一个表中的子查询方式。
+---------+----------------+
| prod_id | prod_name      |
+---------+----------------+
| DTNTR   | Detonator      |
| FB      | Bird seed      |
| FC      | Carrots        |
| SAFE    | Safe           |
| SLING   | Sling          |
| TNT1    | TNT (1 stick)  |
| TNT2    | TNT (5 sticks) |
+---------+----------------+
```

### 16.2.2 自然联结

笛卡尔积

### 16.2.3 外部联结

外部联结和内部链接一个重要的区别就是：

内部链接不过滤的情况下是笛卡儿积，然后返回WHERE子句过滤出来数据

外部联结会保留主表的所有数据，将从表中与主表中有关联的数据返回

```mysql
SELECT customers.cust_id,orders.order_num FROM customers LEFT OUTER JOIN orders ON customers.cust_id  =orders.cust_id;
# 输出，在使用OUTER JOIN语法时，必须使用RIGHT或LEFT关键字指定包括其所有行的表(RIGHT指出的是OUTER JOIN右边的表，而LEFT指出的是OUTER JOIN左边的表)。上面的列子使用LEFT OUTER JOIN从FROM子句的左边表(customers)中选择所有行
# 如果使用LEFT，则左表的值会被保留，右表只会出现满足条件的行
```

## 16.3 使用带聚集函数的联结

## 16.4 使用联结和联结条件

# 17 组合查询

## 17.2 创建组合查询

### 17.2.1 使用UNION

```mysql
SELECT vend_id,prod_id,prod_price FROM products WHERE prod_price <=5 
UNION
SELECT vend_id,prod_id,prod_price FROM products WHERE vend_id IN (1001,1002);
# 输出，使用UNION关键字，将两个SELECT语句进行了组合，结果列表会显示出满足第一个语句或满足第二个语句的行，可以使用单条语句一个WHERE实现
+---------+---------+------------+
| vend_id | prod_id | prod_price |
+---------+---------+------------+
|    1003 | FC      |       2.50 |
|    1002 | FU1     |       3.42 |
|    1003 | SLING   |       4.49 |
|    1003 | TNT1    |       2.50 |
|    1001 | ANV01   |       5.99 |
|    1001 | ANV02   |       9.99 |
|    1001 | ANV03   |      14.99 |
|    1002 | OL1     |       8.99 |
+---------+---------+------------+
```

### 17.2.3 包含或取消重复的行

```mysql
# UNION关键字会自动取消重复行，只显示一次
# UNION ALL关键字可以将重复的行都显示出来
```

# 18 全文本搜索

在MySQL中，仅有`MyISAM`支持全文本搜索

## 18.2 使用全文本搜索

### 18.2.1 启用全文本搜索支持

一般在创建表时启用全文本搜索。

```mysql
CREATE TABLE productnotes
{
	note_id 	int 		NOT NULL AUTO_INCREMENT,
	prod_id 	char(10) 	NOT NULL,
	note_text	text		NULL,
	FULLTEXT(note_text)
}ENGINE=MyISAM;
```

全文本搜索比较费时，所以在插入更新数据时不使用全文本搜索，可以在全部数据更新过后进行一次调整。

### 18.2.2 进行全文本搜索

```mysql
SELECT note_text FROM productnotes WHERE Match(note_text) Against('rabbit');
# 输出，对note_text行进行全文本搜索，搜索包含rabbit的行
# 使用BINARY方式区分大小写
# Against()会包含一个等级值，词越靠前，等级值越高。
```

### 18.2.3 使用查询扩展

想要找出包含anvils或者与anvils有关的行

```mysql
SELECT note_text FROM productnotes WHERE Match(note_text) Against('anvils' WITH QUERY EXPANSION);
# 输出，可以检索出包含anvils的列。因为检索出的第一行包含customer和recommend，它们在第二行中被包含，也检索出了第二行
```

### 18.2.4 布尔文本搜索

```mysql
SELECT note_text FROM productnotes WHERE Match(note_text) Against('heavy -rope*' IN BOOLEAN MODE);
# 输出，检索包含词heavy的所有行，其中删除包含rope的行(rope与任意单个字符的组合)
```

| 布尔操作符 |         说明         |
| :--------: | :------------------: |
|     +      |   包含，词必须存在   |
|     -      |  排除，词必须不出现  |
|     >      | 包含，而且增加等级值 |
|     <      |  包含，且减少等级值  |
|     ()     |   把词组成子表达式   |
|     ~      |  取消一个词的排序值  |
|     **     |     词尾的通配符     |
|     ""     |     定义一个短语     |

# 19 插入数据

## 19.1 插入完整的行

```mysql
INSERT INTO Customers
VALUES(NULL,
      'Pep E. LaPew',
      '100 Main Street',
      'Los Angeles',
      'CA',
      '90046',
      'USA',
      NULL,
      NULL);
# 整行插入，写清表名以及每个列要插入的内容
# 像上面这样不指定列名相对不安全。也可以指定列名
INSERT INTO Customers(cust_name,
             cust_address,
             cust_city,
             cust_state,
             cust_zip,
             cust_country,
             cust_contact,
             cust_email)
VALUES('Pep E. LaPew',
      '100 Main Street',
      'Los Angeles',
      'CA',
      '90046',
      'USA',
      NULL,
      NULL);
# 在给出列名之后，即使插入顺序大乱也可以正确的插入数据
# 如果在插入的时候省略某个列，该列必须满足以下条件：
# 1. 该列定义为允许NULL值
# 2. 该列定义中给出默认值
```

## 19.2 插入多行数据

```mysql
# 第一种插入方式就像上面一样，连续多条INSET语句，每个语句之间使用分号分开。
# 第二种插入方式如下：
INSERT INTO Customers(cust_name,
             cust_address,
             cust_city,
             cust_state,
             cust_zip,
             cust_country)
VALUES('Pep E. LaPew',
      '100 Main Street',
      'Los Angeles',
      'CA',
      '90046',
      'USA'),
      ('M . Martian',
       '42 Galaxy Way',
       'New York',
       'NY',
       '11213',
       'USA');
# 这样插入要比多条INSERT插入更快
```

## 19.3 插入检索出的数据

使用`INSERT`插入`SELECT`的结果

```mysql
INSERT INTO customers(cust_id,
                     cust_contact,
                     cust_email,
                     cust_name,
                     cust_address,
                     cust_city,
                     cust_state,
                     cust_zip,
                     cust_country)
SELECT cust_id,
     cust_contact,
     cust_email,
     cust_name,
     cust_address,
     cust_city,
     cust_state,
     cust_zip,
     cust_country
FROM custnew;
# 这里当custnew表中的cust_id与前表重复，插入就会失败
```

# 20 更新和删除数据

## 20.1 更新数据

```mysql
UPDATE customers
SET cust_name = 'The Fudds',
	cust_email = 'elmer@fudd.com'
WHERE cust_id = 10005;
# UPDATE后跟表名，SET后跟要更新的列与值，不同列之间使用逗号
# 最后一定要使用WHERE语句确定需要更新的行，如果不指定行，则将更新表中的所有行(一定会被打死)
```

## 20.2 删除数据

```mysql
DELETE FROM customers WHERE cust_id = 10006;
# 删除id为10006的行，不指定则删除所有行

# 删掉表中的行，进行id自增的清除
TRUNCATE TABLE vendors;
```

**注意：**

删除一行数据之后，表中的主键是不连续的，因此如果需要主键连续可以做以下操作

```mysql
ALTER TABLE tablename auto_increment=1;--从需要的下标开始自增

# 删除id列
ALTER TABLE tablename drop id
# 新增id列
ALTER TABLE tablename ADD id INT NOT NULL AUTO_INCREMENT FIRST

```







# 21 创建和操纵表

## 21.1 创建表

```mysql
CREATE TABLE customers
(
    cust_id			int 		NOT NULL AUTO_INCREMENT,	# 自增
    cust_name		char(50)	NOT NULL,
    cust_num		int 		NOT NULL DEFAULT 1,			# 默认值为1
    cust_address	char(50)	NULL,
    PRIMARY KEY		(cust_id)								# 主键，可以有多列主键
)ENGINE=InnoDB;

CREATE TABLE `user`(
	`user_id` int NOT NULL AUTO_INCREMENT,
    `user_name` VARCHAR(30) DEFAULT NULL,
    `user_pwd` VARCHAR(30) DEFAULT NULL,
    PRIMARY KEY(`user_id`)
)ENGINE=InnoDB;
```

## 21.2 更新表

```mysql
ALTER TABLE vendors
ADD vend_phone CHAR(20);
# 在表中添加一个新的列
ALTER TABLE vendors
DROP COLUMN vend_phone;
# 删除表中的一个列
```

## 21.3 删除表

```mysql
DROP TABLE customers;
```

## 21.4 重命名表

```mysql
RENAME TABLE customers2 TO customers;
```

# 22 使用视图

## 22.1 视图

```mysql
SELECT cust_name,cust_contact FROM customers,orders,orderitems
WHERE customers.cust_id = orders.cust_id
AND orderitems.order_num = orders.order_num
AND prod_id = 'TNT2';
```

如果我们把这个语句包装成一个名为`productcustomers`的虚拟表，则可以轻松检索数据

```mysql
SELECT cust_name,cust_contact FROM productcustomers WHERE prod_id= 'TNT2';
```

它不包含表中应该有的任何列或数据，它包含的是一个SQL查询（与上面用以正确联结表的相同的查询）

## 22.1.1 为什么使用视图

- 重用SQL语句
- 简化复杂的SQL操作。
- 使用表的组成部分，而不是整个表
- 保护数据。可以给用户授予表的特定部分的访问权限而不是整个表的访问权限
- 更改数据格式和表示。视图可返回与底层表的表示和格式不同的数据。

### 22.1.2 视图的规则和限制

- 与表名一样，视图必须唯一命名
- 对于可以创建的视图数目没有限制
- 为了创建视图，必须具有足够的访问权限
- 视图可以嵌套
- ORDER BY 可以用在视图中，但如果从该视图检索数据SELECT中也有ORDER BY，那么视图中的会被覆盖
- 视图不能索引，也不能有关联的触发器或默认值
- 视图可以和表一起使用

## 22.2 使用视图

- 视图用`CREATE VIEW`语句来创建
- 使用`SHOW CREATE VIEW viewname`来查看创建视图的语句
- 用`DROP`删除视图，其语法为`DROP VIEW viewname`
- 更新视图时，可以先用`DROP`再用`CREATE`，也可以直接用`CREATE OR REPLACE VIEW`。如果要更新的视图不存在，则第二条语句会创建一个视图，如果要更新的视图存在，则第二条更新语句会替换原有视图

### 22.2.1 利用视图简化复杂的联结

```mysql
CREATE VIEW productcustomers AS 
SELECT cust_name,cust_contact,prod_id
FROM customers,orders,orderitems
WHERE customers.cust_id = orders.cust_id
AND orderitems.order_num = orders.order_num;

# mysql创建一个名为productcustomers的视图
# 使用下面的语句代替了上面SELECT语句的复杂操作
SELECT * FROM productcustomers;
```

### 22.2.5 更新视图

可以对视图使用插入、更新、删除等操作。更新一个视图将更新其基表。

但是在很多情况下是不能更新的：

- 分组
- 联结
- 子查询
- 并
- 聚集函数
- DISTINCT
- 导出列

# 23.使用存储过程

## 23.2 为什么要使用存储过程

- 通过把处理封装在容易使用的单元中，简化复杂的操作

- 由于不要求反复建立一系列处理步骤，这保证了数据的完整性。如果所有开发人员和应用程序都使用同一存储过程，则所有使用的代码都是相同的。

  这一点的延伸就是防止错误。需要执行的步骤越多，出错的可能性就越大。防止错误保证了数据的一致性。

- 简单化变动的管理。如果表名、列名或业务逻辑（或别的内容）有变化，只需要更改存储过程的代码。使用它的人员甚至不需要知道这些变化。

- 提高性能。因为使用存储过程比单独的SQL语句快。

- 存在一些只能用在单个请求中的MySQL元素和特性，存储过程可以使用它们来编写功能更强更灵活的代码

- 一般来说存储过程的编写比基本SQL语句复杂，编写存储过程需要更高的技能，更丰富的经验。

- 你可能没有创建存储过程的安全访问权限。许多数据库管理员限制存储过程的创建权限，允许用户使用存储过程，但不允许他们创建存储过程。

## 23.3 使用存储过程

### 23.3.1 执行存储过程

MySQL称存储过程的执行为调用，因此MySQL执行存储过程的语句为CALL。CALL接受存储过程的名字以及需要传递给它的任意参数。

```mysql
CALL productpricing(
	@pricelow,
    @pricehigh,
    @priceaverage
)

# 无参数调用
CALL productpricing();
```

### 23.3.2 创建存储过

```mysql
# //并不是注解符号，这里是设定的新的分隔符
DELIMITER //

CREATE PROCEDURE producing()
BEGIN
	SELECT Avg(prod_price) AS priceaverage FROM products;
END //

DELIMITER ;

# MySQL的分隔符为; 如果使用的是命令行程序，就需要使用DELIMITER关键字作为转义 存储过程中的分号标识。
# 其中DELIMITER //告诉命令行 // 是新的分隔符，所以后面的END;并定义为END//
# 最后再使用 DELIMITER ; 将分隔符复原
```

### 23.3.3 删除存储过程

```mysql
DROP PROCEDURE productpricing;
```

这样的删除如果过程不存在则会产生一个错误。

```mysql
DROP PROCEDURE IF EXISTS productoricing;
```

这样删除即使不存在也不会产生错误

### 23.3.4 使用参数

```mysql
DELIMITER //

CREATE PROCEDURE productpricing(
	OUT p1 DECIMAL(8,2),	# 8代表小数点左边可以存储的十进制数的位数，2代表小数点右边可以存储的十进制数的位数
    OUT ph DECIMAL(8,2),
    OUT pa DECIMAL(8,2)
)

BEGIN
	SELECT Min(prod_price)
    INTO p1 FROM products;
    SELECT Max(prod_price)
    INTO ph FROM products;
    SELECT Avg(prod_price)
    INTO pa FROM products;
    
END //

DELIMITER ;

# 此存储过程接受3个参数，每个参数必须具有指定的类型，这里使用十进制值。关键字OUT指出相应的参数用来从存储过程传出一个值（返回给调用者）。MySQL支持IN（传递给存储过程），OUT（从存储过程传出）和INOUT（对存储过程传入传出）类型的参数。
# 存储过程的代码位于BEGIN和END语句内，用来检索值并保存到变量
```

调用上面的存储过程需要制定三个参数变量

```mysql
CALL productpricing(
	@pricelow,
    @pricehigh,
    @priceaverage
);
# 上面创建存储过程检索出来的值，就会用过这个调用保存到这三个变量中
# 然后用下面的方法调用三个变量
SELECT @pricelow,@pricehigh,@priceaverage;
# 输出结果
+------------+-----------+---------------+
| @pricehigh | @pricelow | @priceaverage |
+------------+-----------+---------------+
|      55.00 |      2.50 |         16.13 |
+------------+-----------+---------------+
```

下面使用`IN`和`OUT`作为参数

```mysql
DELIMITER //

CREATE PROCEDURE ordertotal(
	IN onumber INT,				# IN意味着在调用的时候这里进行了一个传值，有参数传进来
    OUT ototal DECIMAL(8,2)		# OUT就是把结果保存到这个参数
)
BEGIN
	SELECT Sum(item_price*quantity)
    FROM orderitems WHERE order_num = onumber
    INTO ototal;
END//

DELIMITER ;
```

调用与结果如下：

```mysql
CALL ordertotal(20005,@total);
# 20005就是对应IN的传入参数
SELECT @total;
# 输出
+--------+
| @total |
+--------+
| 149.87 |
+--------+
```

### 23.3.5 智能存储过程

```mysql
# 一般的存储过程就是一个基础语句的集合，这个集合可以通过一条一条执行达到同样的效果。存储过程最有效的地方就是智能存储过程
-- Name: ordertotal
-- Parameters: 	onumber = order number
-- 				taxable = 0 if not taxable, 1 if taxable
-- 				ototal = order total variable
DELIMITER //

CREATE PROCEDURE ordertotal(
	IN onumber INT,
    IN taxable BOOLEAN,			-- boolean参数，如果增加税为真，否则为假
    OUT ototal DECIMAL(8,2)
)COMMENT 'Obtain order total, optionally adding tax' -- COMMENT不是必须的，这将在SHOW PROCEDURE STATUS中显示
BEGIN

	-- 	Declare variable for total
    DECLARE total DECIMAL(8,2);	-- 定义的局部变量
    -- 	Declare tax percentage
    DECLARE taxrate INT DEFAULT 6;
    
    -- 	Get the order total
	SELECT Sum(item_price*quantity)
    FROM orderitems WHERE order_num = onumber
    INTO total;
    
    -- 	Is this taxalbe?
    IF taxable THEN
		-- 	Yes, so add taxrate to the total
        SELECT total+(total/100*taxrate) INTO total;
	END IF;
    
    -- 	And finally, save to out variable
    SELECT total INTO ototal;
END//

DELIMITER ;
```

通过下面的语句可以调用它

```mysql
CALL ordertotal(20005, 0, @total);
SELECT @total;
# 输出
+--------+
| @total |
+--------+
| 149.87 |
+--------+

CALL ordertotal(20005, 1, @total);
SELECT @total;
# 输出
+--------+
| @total |
+--------+
| 158.86 |
+--------+
```

### 23.3.6 检查存储过程

为显示用来创建一个存储郭恒的CREATE语句，使用`SHOW CREATE PROCEDURE`语句

```mysql
SHOW CREATE PROCEDURE ordertotal;
```

为了获得包括何时、由谁创建等详细信息的存储过程列表，使用`SHOW PROCEDURE STATUS`

```mysql
SHOW PROCEDURE STATUS 
```

限制过程状态结果

```mysql
SHOW PROCEDURE STATUS LIKE 'ordertotal';
```

# 24 使用游标

## 24.1 游标

`MySQL`检索操作返回一组称为结果集的行。这组返回的行都是与`SQL`语句相匹配的行（零行或多行）。使用简单的`SELECT`语句，没有办法得到第一行、下一行或者前10行。也不存在每次一行地处理所有行的简单方法

有时候需要再检索出来的行中前进或后退一行或多行。这就是使用游标的原因。游标(cursor)是一个存储在`MySQL`服务器上的数据库查询，它不是一条`SELECT`语句 ，而是被该语句检索出来的结果集。在存储了游标之后，应用程序可以根据需要滚动或浏览其中的数据。

游标主要用于交互式应用，其中用户需要滚动屏幕上的数据，并对数据进行浏览或做出更改。

`MySQL`游标只能用于存储过程(和函数)

## 24.2 使用游标

- 在能够使用游标前，必须声明（定义）它。这个过程实际上没有检索数据，它只是定义要使用的`SELECT`语句
- 一旦声明后，必须打开游标以供使用。这个过程用前面定义的`SELECT`语句把数据实际检索出来。
- 对于填有数据的游标，根据需要取出(检索)各行
- 在结束游标使用时，必须关闭游标

### 24.2.1 创建游标

```mysql
DELIMITER //

 CREATE PROCEDURE processorders()
 BEGIN
	DECLARE ordernumbers CURSOR
    FOR
    SELECT order_num FROM orders;
END //

DELIMITER ;

# DECLARE语句命名游标，这里为ordernumbers。存储过程处理完成之后，游标就会消失。
```

### 24.2.2 打开和关闭游标

```mysql
OPEN ordernumbers;

# 处理OPEN语句时执行查询，存储检索出的数据以供浏览和滚动

CLOSE ordernumbers;

# CLOSE释放游标使用的所有内部内存和资源
# 游标是在存储过程中使用，如果没有使用CLOSE，当遇到END时会自动关闭游标
```

### 24.2.3 使用游标数据

在一个游标被打开后，可以使用`FETCH`语句分别访问它的每一行。`FETCH`指定检索什么数据（所需的列），检索出来的数据存储在什么地方。它还向前移动游标中的内部行指针，使下一条`FETCH`语句检索下一行

第一个例子，从游标中检索单个行（第一行）

```mysql
DELIMITER //

 CREATE PROCEDURE processorders()
 BEGIN
	-- 	Declare local variables
    DECLARE o INT;
    
    -- 	Declare the cursor
    DECLARE ordernumbers CURSOR FOR 
    SELECT order_num FROM orders;
    
    -- 	Open the cursor
    OPEN ordernumbers;
    
    -- 	Get order number
    FETCH ordernumbers INTO o;
    
    -- 	Close the cursor
    CLOSE ordernumbers;
    
END //

DELIMITER ;

# FETCH语句将ordernumbers游标从第一行开始检索，并存储到变量o中
```

循环检索数据

```mysql
DELIMITER //

 CREATE PROCEDURE processorders()
 BEGIN
	-- 	Declare local variables
    DECLARE done BOOLEAN DEFAULT 0;
    DECLARE o INT;
    
    -- 	Declare the cursor
    DECLARE ordernumbers CURSOR FOR 
    SELECT order_num FROM orders;
    
    -- 	Declare continue handler
    DECLARE CONTINUE HANDLER FOR SQLSTATE '02000' SET done=1;
    
    -- 	Open the cursor
    OPEN ordernumbers;
    
    -- 	Loop throught all rows
    REPEAT
    
		-- 	Get order number
		FETCH ordernumbers INTO o;
        
	-- 	End of loop
    UNTIL done END REPEAT;
		
    -- 	Close the cursor
    CLOSE ordernumbers;
    
END //

DELIMITER ;

# 为了使游标循环检索，将FETCH语句嵌入REPEAT语句中，并反复执行知道done值为真
# CONTINUE HANDLER它是在条件出现时被执行的代码。这里指出当SQLSTATE '02000'出现时，SET done=1。
# SQLSTATE ‘02000’是一个未找到条件，当REPEAT由于没有更多行提供循环而不能继续时出现这个条件
```

**注意：**`DECLARE`语句存在次序，局部变量定义必须在游标或句柄定义之前，句柄必须在游标之后。局部变量>游标>句柄



# 25 触发器

## 25.1 触发器

`MySQL`语句在需要时被执行，存储过程也是如此。但是如果需要某条语句在事件发生时执行，就需要使用到触发器。

触发器是`MySQL`响应以下任意语句而自动执行的一条`MySQL`语句(或位于`BEGIN`和``END`之间的一组语句)

- DELETE
- INSERT
- UPDATE

其他的语句不支持触发器

## 25.2 创建触发器

创建触发器需要给定四条信息：

- 唯一的触发器名
- 触发器关联的表
- 触发器应该响应的活动(DELETE, INSERT, UPDATE)
- 触发器何时执行

```mysql
CREATE TRIGGER newproduct AFTER INSERT ON products
FOR EACH ROW SELECT 'Product added';

# 创建一个名为newproduct的触发器，该触发器将在INSERT语句成功执行后执行。FOR EACH ROW表示将在每行成功插入时显示'Product added'
# 只有表支持触发器，视图不支持
```

**注意：**如果`BEFORE`触发器失败，则`MySQL`将不执行请求操作。此外，如果`BEFORE`触发器或语句本身失败，`MySQL`将不执行`AFTER`触发器

## 25.3 删除触发器

```mysql
DROP TRIGGER newproduct;
```

## 25.4 使用触发器

### 25.4.1 INSERT触发器

`INSERT`触发器在`INSERT`语句执行之前或之后执行。需要知道以下几点：

- 在`INSERT`触发器代码内，可引用一个名为`NEW`的虚拟表，访问被插入的行
- 在`BEFORE INSERT`触发器中，`NEW`中的值也可以被更新（允许更改被插入的值）
- 对于`AUTO_INCREMENT`列，`NEW`在`INSERT`执行之前包含`0`，在`INSERT`执行之后包含新的自动生成值

```mysql
CREATE TRIGGER neworder AFTER INSERT ON orders
FOR EACH ROW SELECT NEW.order_num INTO @order_num;

# 创建一个名为neworder的触发器，按照AFTER INSERT ON orders执行。在插入一个新订单到orders表时，MySQL生成一个新订单号并保存到order_num中。触发器从NEW.order_num取得这个值并返回它。

# 直接使用SELECT NEW.order_num会产生错误，这就将返回一个结果集，会产生错误

INSERT INTO orders(order_date,cust_id)
VALUES(Now(), 10001);
SELECT @order_num;
# 用来测试触发器
```

### 25.4.2 DELETE触发器

`DELETE`触发器在`DELETE`语句执行前或之后执行。需要知道以下两点：

- 在`DELETE`触发器代码内，你可以引用一个名为`OLD`的虚拟表，访问被删除的行
- `OLD`中的值全部都是只读的，不能更新

```mysql
CREATE TRIGGER deleteorder BEFORE DELETE ON orders
FOR EACH ROW
BEGIN
	INSERT INTO archive_orders(order_num,prder_date,cust_id)
    VALUES(OLD.order_num,OLD.order_date,OLD.cust_id);
END $$

# 任意订单被删除之前执行此触发器，使用一条INSERT语句将OLD中的值（要被删除的订单）保存到一个名为archive_orders的存档表中。
```

使用`BEFORE DELETE`触发器的优点（相对于`AFTER DELETE`触发器来说）为，如果由于某种原因，订单不能存档，`DELETE`本身将被放弃。使用`BEGIN END`可以处理多条语句

### 25.4.3 UPDATE触发器

`UPDATE`触发器在`UPDATE`语句执行前或之后执行。需要知道以下几点：

- 在该触发器中，你可以引用一个名为`OLD`的虚拟表访问以前(`UPDATE`之前的)值，引用一个`NEW`的虚拟表访问新更新的值
- 在`BEFORE UPDATE`触发器中，`NEW`中的值可能也被更新（允许更改将要用于`UPDATE`的语句中的值）
- `OLD`中的值全都是只读的，不能更新

```mysql
CREATE TRIGGER updatevendor BEFORE UPDATE ON venders
FOR EACH ROW 
SET NEW.vend_state = Upper(NEW.vend_state);

# 任何数据净化都需要在UPDATE语句执行之前
```

# 26 管理事物处理

## 26.1 事务处理

**事务处理**可以用来维护数据库的完整性，它保证成批的`MySQL`操作要么完全执行，要么完全不执行。

如果有一系列操作，而此时执行了一般数据库出了故障阻止了这个过程的完成，就会使数据库中的数据不完整

- **事务**指一组SQL语句
- **回退**指撤销指定SQL语句的过程
- **提交**指将未存储的SQL语句结果写入数据库表
- **保留点**指事务处理中设置的临时占位符，你可以对它发布回退

- 事务
  - 一组数据库操作，要么全部执行，要么全部放弃
- 事务的特性
  - 原子性：事务是应用中不可再分的最小执行体
  - 一致性：事务执行的结果，必须使数据从一个一致状态，变为另一个一致性状态
  - 隔离性：各个事务的执行互不干扰，任何事务的内部操作对其他的事务都是隔离的
  - 持久性：事务一旦提交，对数据所做的任何改变都要记录到永久存储器中



## 26.1.3 隔离性

事务的隔离性

- 常见并发异常
  - 第一类丢失更新、第二类丢失更新
  - 脏读、不可重复读、幻读
- 常见的隔离级别
  - Read Uncommitted：读取未提交的数据
  - Read Committed：读取已提交的数据
  - Repeatable Read：可重复读
  - Serializable：串行化

[举例详情见 - mysql技术内幕 6.5 锁问题](./MySQL技术内幕.md)



**事务隔离级别**

隔离级别与它会发生的异常

| 隔离级别         | 第一类丢失更新 | 脏读 | 第二类丢失更新 | 不可重复读 | 幻读 |
| ---------------- | -------------- | ---- | -------------- | ---------- | ---- |
| Read Uncommitted | Y              | Y    | Y              | Y          | Y    |
| Read Committed   | N              | N    | Y              | Y          | Y    |
| Repeatable Read  | N              | N    | N              | N          | Y    |
| Serializable     | N              | N    | N              | N          | N    |



### 26.1.2 实现机制

- 悲观锁（数据库）
  - 共享锁（S锁）
    - 事务A对某数据加了共享锁后，其他事务只能对该数据加共享锁（能读不能改），但不能加排他锁
  - 排他锁（X锁）
    - 事务A对某数据加了排他锁后，其他事务对该数据既不能加共享锁，也不能加排他锁
- 乐观锁（自定义）
  - 版本号、时间戳等
    - 在更新数据前，检查版本号是否发生变化。若发生变化则取消本次更新，否则就更新数据（版本号+1）



[代码编写方式](..\JAVA\Spring\Spring入门)



## 26.2 控制事务处理

### 26.2.1 使用ROLLBACK

```mysql
SELECT * FROM ordertotals;
START TRANSACTION;
DELETE FROM ordertotals;
SELECT * FROM ordertotals;
ROLLBACK;
SELECT * FROM ordertotals;

# 第一条语句验证表非空，然后开始事务处理
# 删表，再验证为空
# ROLLBACK回退到事务之前
# 再查询表非空
# ROLLBACK不能回退CREATE和DROP操作
```

### 26.2.2 使用COMMIT

```mysql
START TRANSACTION;
DELETE FROM orderitems WHERE order_num=20010;
DELETE FROM orders WHERE order_num = 20010;
COMMIT;

# 如果第一条成功，第二条失败，则会自动撤销事务
```

### 26.2.3 使用保留点

为了支持回退部分事务处理，必须能在事务处理块中合适的位置放置占位符。这样如果需要回退，就可以回退到某个占位符。

```mysql
SAVEPOINT deletel;

ROLLBACK deletel;
```

保留点越多，就可以按照自己的意愿灵活的回退

释放保留点

```mysql
RELEASE SAVEPOINT deletel;
```

### 26.2.4 更改默认提交行为

```mysql
SET autocommit=0;

# 更改默认提交行为。为真就是自动提交，为假就需要COMMIT提交
```



# 附录 MySQL关键字

```mysql
# USE
USE crashcourse;					# 使用名为crashcourse的数据库，接下来的操作都在该库上

# SHOW
SHOW DATABASES; 					# 显示所有数据库
SHOW TABLES;						# 显示该库中所有表名
## FROM
SHOW COLUMNS FROM customers;		# 返回customers表的所有列信息，不是列的内容

```

```mysql
# SELECT
SELECT prod_name FROM products;		# 从products表中检索prod_name列
SELECT prod_id, prod_name, prod_price 
	FROM products;					# 从products表中检索多个列
SELECT * FROM products;				# 从products表中检索所有列
## DISTINCT
SELECT DISTINCT vend_id 
	FROM products;					# 从products表中检索单个列，并去掉重复的行
									# 如果是从多个列中检索，就需要多个列同时重复
## LIMIT
SELECT prod_name 
	FROM products LIMIT 5;			# LIMIT关键字限制结果的最大行数
SELECT prod_name 
	FROM products LIMIT 5,4;		# 从第五行起(第0行起)显示不超过4行
## OFFSET
SELECT prod_name
	FROM products
	LIMIT 4 OFFSET 5;				# 此句同上，从偏置5个位置开始输出4行

## ORDER BY
SELECT prod_name 
	FROM products 
	ORDER BY prod_name;				# 以字母顺序默认升序排列
SELECT prod_id, prod_price, prod_name 
	FROM products 
	ORDER BY prod_price, prod_name;	# 只有当prod_price列完全相同时，才会按照prod_name列排序
## DESC
SELECT prod_id, prod_price, prod_name 
	FROM products 
	ORDER BY prod_price DESC;		# 按照字典序降序排列
	
## WHERE
SELECT prod_name, prod_price 
	FROM products 
	WHERE prod_name = 'fuses';		# 检索两个列，只返回符合WHERE条件的行。不区分大小写
SELECT prod_name, prod_price 
	FROM products WHERE prod_price 
	BETWEEN 5 AND 10				# 检索两个列，只返回符合WHERE条件的行。不区分大小写	
SELECT prod_name, prod_price FROM products 
	WHERE prod_price IS NULL;		# 返回值为空的行(不是为0)
```

```mysql
## AND
SELECT prod_id, prod_price, prod_name FROM products 
	WHERE vend_id = 1003 
	AND prod_price <= 10;			# 检索同时满足AND前后的条件的行
## OR
SELECT prod_id, prod_price, prod_name FROM products 
	WHERE vend_id = 1002 
	OR vend_id = 1003;				# 检索满足OR前面或者后面条件的行
## IN
SELECT vend_id, prod_name, prod_price FROM products 
	WHERE vend_id IN (1002,1003) 	# 检索满足括号内条件的行。这不是数学上的开区间，是枚举
	ORDER BY prod_name;
## NOT 
SELECT prod_name, prod_price FROM products 
	WHERE vend_id NOT IN (1002,1003) # NOT否定后面的条件，不检索1002，1003行
	ORDER BY prod_name;
```

```mysql
## LIKE
SELECT prod_id, prod_name FROM products 
	WHERE prod_name LIKE 'jet%';	# 匹配jet开头的字符
SELECT prod_id, prod_name FROM products 
	WHERE prod_name 
	LIKE '_ ton anvil';				# 只匹配一个字符
## REGEXP
SELECT prod_name FROM products 
	WHERE prod_name REGEXP '1000';	# REGEXP后面跟正则表达式
## Concat()
SELECT Concat(vend_name,'(',vend_country,')') 
	FROM vendors 
	ORDER BY vend_name;				# 使用Concat()函数拼接字段
## AS
SELECT Concat(vend_name,'(',vend_country,')') 
	AS vend_title
	FROM vendors 
	ORDER BY vend_name;				# 使用AS更换临时表名
## GROUP BY 
SELECT vend_id, COUNT(*) AS num_prods 
	FROM products 
	GROUP BY vend_id;				# 通过vend_id列对行进行分组
## HAVING
SELECT cust_id,COUNT(*) AS orders FROM orders 
	GROUP BY cust_id 
	HAVING COUNT(*)>=2;				# HAVING进行分组后过滤
```

```mysql
## INNER JOIN

## LEFT(RIGHT) OUTER JOIN

## UNION
```

