<!-- TOC -->

[TOC]

<!-- /TOC -->


## 第一章 Java程序设计概述
### 1.2.1 简单性
### 1.2.3 分布式
### 1.2.4 健壮性
### 1.2.5 安全性
### 1.2.7 可移植性
### 1.2.8 解释性
### 1.2.9 高性能
### 1.2.10 多线程
### 1.2.11 动态性

方法多态性，属性没有多态性 

例子：

**父类**

```java
public class Employee {
    int salary =10;

    public Employee(){

    }

    public int getSalary(){
        return salary;
    }

    public void talk(){
        System.out.println("终于要下班了");
    }
}
```

**子类**

```java
public class Manager extends Employee{
    int salary = 20;

    public Manager() {
    }

    @Override
    public int getSalary(){
        return salary;
    }

    @Override
    public void talk(){
        System.out.println("快来上班");
    }
}
```

**测试**

```java
public class Extends {
    public static void main(String[] args) {
        Employee employee = new Employee();
        Manager manager = new Manager();
        /**
         * 虚拟方法调用
         * 这里emplyee对象输出了自己的薪水，但是它的方法使用的是manager的方法
         * 因为，这里将manager的值赋给了employee，两个是同一块内存空间
         * 在内存结构中，两个对象的字段都放入了内存各自都有一个空间，但是方法只有一块空间
         * 子类重写了父类的方法，这块内存中的方法就成了子类的方法
         * 编译看左边：编译了一个Employee对象
         * 运行看右边：示例运行方法是右边Manager的对象
         */
        System.out.println(employee.salary+","+manager.salary);//10，20
        employee.talk();//下班
        manager.talk();//上班

        Employee e = manager;
        System.out.println(e.salary);//10
        e.talk();//上班
    }
}
```

**注意：**

在输出的时候，如果输出的是方法，就看在实例化的时候等号右边传进来是哪个类

如果输出的是变量，就要看实例化时等号左边接收的类是哪一个

[具体的看java虚拟机的第八章方法调用]()

```java
public static void main(String[] args) {
    Bird chicken = new Chicken();
    Bird duck = new Duck();

    chicken.eat();
    System.out.println(chicken.a);
    chicken.fly();

    duck.eat();
    System.out.println(duck.a);
    duck.fly();

    Chicken chicken1 = new Chicken();
    Duck duck1 = new Duck();

    chicken1.eat();
    chicken1.fly();
    System.out.println(chicken1.a);

    duck1.eat();
    duck1.fly();
    System.out.println(duck1.a);

    Bird bird1 = new Duck();
    bird1.eat();
    System.out.println(bird1.a);
}
```









## 第三章 Java基本程序设计结构

### 3.4.2 常量
*final*关键字定义常量，用该关键字赋值，值不能再修改  
常量定义在*main*之外，可以使同类的其他方法也能使用该量  
在*main*中定义其他方法不能使用，但是只能是*final int*，不能是*public final int*  
### 3.5.9 枚举类型
```java
enum Size{SMALL,MEDIUM,LARGE};

Size s = Size.SMALL;
//s输出值是SAMLL,和c语言中不一样，c中是0

public class EnumTest {
    //枚举类型和枚举类不同
    enum Size{SMALL,MEDIUM,LARGE}

    public static void main(String[] args) {
        //直接引用
        Size s1 = Size.SMALL;
        Size s2 = Size.MEDIUM;

        Size[] values = Size.values();//获得枚举类型的常量数组
        for (Size value : values) {
            System.out.print(value);
            System.out.print(":");
            System.out.println(value.ordinal());//获得枚举类的常量的序数
        }
    }
}
```
枚举类的构造形式

这是与普通类构造时不同的地方，直接加括号

```java
public enum EnumTest2 {//使用enum定义的类父类为Enum
    SPRING("春天"),//多个对象之间用逗号，最后一个用分号
    SUMMER("夏天"),
    AUTUMN("秋天"),
    WINTER("冬天");

    private String desc;

    private EnumTest2(String desc){
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}

public class EnumTest {

    public static void main(String[] args) {
        String desc = EnumTest2.SPRING.getDesc();//通过getter方法调用
        System.out.println(desc);
    }
}
```

使用enum实现接口

每个对象都能实现一次该接口的方法

```java
public enum EnumTest2 implements  info{
    SPRING("春天"){
        @Override
        public void show() {

        }
    },
    SUMMER("夏天"){
        @Override
        public void show() {

        }
    },
    AUTUMN("秋天"){
        @Override
        public void show() {

        }
    },
    WINTER("冬天"){
        @Override
        public void show() {

        }
    };
}
```

### 3.6.3 不可变字符串

当有一个字符串
```java
String greeting = "Hello";
```
如果想要修改最后一个字符，在C语言中可以直接修改'o'，但是在java中不能直接修改，可以这样操作：  
```java
greeting = greeting.substring(0,4) + 'p';
```

### 3.6.4 检测字符串是否相等

```java
s.equals(t);//区分大小写
s.equalsIgnoreCase(t);//不区分大小写
```
如果字符串s与字符串t相等，则返回true。

**注**：不要使用\==来判断字符串时候相等。这个元素安抚只能够确定两个字符串是否放在同一个位置上。如果虚拟机始终将相同的字符串共享，就可以使用==检测。实际上只有字符串常量是共享的，而'+'或substring等操作产生的结果并不共享。

### 3.6.6 码点与代码单元

char数据类型由UTF-16编码表示Unicode码点的代码单元。  
length方法返回UTF-16编码表示的给定字符串所需要的代码单元的数量。  
```java
String greeting = "Hello";
int n = greeting.length();//is 5
```
调用s.charAt(n)将返回第n个位置上的代码单元。  
如果想要得到第i个码点，应该这样写：
```java
int index = greeting.offsetByCodePoints(0,i);//从index处偏移i个代码点的索引
int cp = greeting.codePointAt(index);//返回指定索引处的字符码点
```

有些字符需要两个代码单元，调用charAt(i)可能会出错  
如果要遍历一个字符串，并且依次查看每一个码点，可以这样写：
```java
int cp = sentense.codePointAt(i);
if(Character.isSupplementaryCodePoint(cp))  
    i +=2;
else i++;
```

### 3.6.7 String API

java.lang.String  

```java
char charAt(int index)
//返回给定位置的代码单元  
int codePointAt(int index)
//返回从给定位置开始的码点
int offsetByCodePoints(int startIndex,int cpCount)
//返回从startIndex代码点开始，位移cpCount后的码点索引   
int compareTo（String other）
//按照字典顺序，如果字符串位于other之前，返回一个负数；如果之后返回一个整数，如果相等返回0
IntStream codePoint()
//将这个字符串的码点作为一个流返回。调用toArray将它们放在一个数组中
new String(int[] codePoints,int offset,int count)
//用数组从offset开始的count个码点构造一个字符串
boolean equals(Object other)
//比较字符串是否相等，返回true
boolean equalsIgnoreCase(String other)
//忽略大小写比较
boolean startsWith(String prefix)
//如果字符串以prefix开头，则返回true
boolean endWith(String suffix)
//如果字符串以sufffix开头，则返回true
int indexOf(String str)
int indexOf(String str,int fromIndex)
int indexOf(int cp)
int indexOf(int cp,int fromIndex)
//返回与字符串str或代码点cp匹配的第一个子串的开始位置。这个位置从索引0或fromIndex开始计算。如果原始串中不存在str，返回-1
int length()
//返回字符串长度
int codePointCount(int startIndex,int endIndex)
//返回startIndex和endIndex-1之间的代码点数量。没有配成对的代用字符将计入代码点
String replace(CharSequence oldString, CharSequence newString)
//返回一个新字符串。这个字符串用newString代替原始字符串中的所有oldString。可以用String或StringBuilder对象作为CharSequence参数
String substring(int beginIndex)
String substring(int beginIndex, int endIndex)
//返回一个新字符串。这个字符串包含原始字符串中从beginIndex到串尾或endIndex-1的所有代码单元
String toLowerCase()
String toUpperCase()
//返回一个新字符串。变小写或者变大写
String trim()
//返回一个新字符串。这个字符串删除了原始字符串头部和尾部的空格
String join(CharSequence delimiter, CharSequence... elements)
///返回一个新字符串，用给定的定界符连接所有元素。
```

### 3.6.9 构建字符串

有时候需要将许多单词串联成一个字符串，采用字符串连接的方式效率低下。所以可以使用StringBuilder类。
```java
StringBuilder builder = new StringBuilder();

builder.append(str);//每次添加用append方法

String completedString = builder.toSTring();//将StringBuilder类型转换为String类型
```
java.lang.StringBuilder
```java
StringBuilder()
//构造一个空的字符串构建器
int length()
//返回构建器或缓冲器中的代码单元数量
StringBuilder append(String str)
//追加一个字符串并返回this
StringBuilder append(char c)
//
void setCharAt(int i,char c)
//将第i个代码单元设置为c
StringBuilder insert(int offset, Char c)
//在offset位置插入一个代码单元并返回this
StringBuilder delete(int startIndex, int endIndex)
//删除偏移量从startIndex到endIndex-1的代码单元并返回this
String toString()
//返回一个构建器或缓冲器内容相同的字符串
```

### 3.7.1 读取输入

[卷 II IO读写](# 2.2 文本输入与输出)

```java
Scanner in = new Scanner(System.in);

String name = in.nextLine();//可读取空格
String firstName = in.next();//空格作为分隔符
```
java.util.Scanner
```java
Scanner(InputStream in)
//用给定的输入流创建一个scanner对象
String nextLine()
//读取输入的下一行内容
String next()
//读取输入的下一个单词(空格分隔)
int nextInt()
double nextDouble()
boolean hasNext()
//检测输入中是否还有其他单词
boolean hasNextInt()
boolean hasNextDouble()
```

java.lang.System
```java
static Console console()
//如果有可能进行交互操作，就通过控制台为交互用户返回一个Console对象，否则返回null。对于任何一个通过控制台启动的程序，都可使用Console对象。
```

java.io.Console
```java
static char[] readPassword(String prompt, Object...args)
static String readLine(String prompt,Object...args)
//显示字符串prompt并且读取用户输入，直到输入行结束。args参数可以用来提供输入格式
```

### 3.7.2 格式化输出

System.out.printf();可以像C语言一样格式化输出。  

| 标志 |目的 || 标志 |目的 |
| :----:| :----: | :----:| :----: | :----: |
| + | 打印正负号 ||0|数字前面补0|
| 空格 | 正数前添加空格 ||-|左对齐|
| ( | 负数括起来 ||,|添加数字分组分隔符|
| #(对f格式) | 包含小数点 ||#(对x或0格式)|添加前缀0x或者0|
| $ | 给定被格式化的参数索引 ||<|格式化前面说明的数值|


### 3.7.3 文件输入与输出

对文件读取要用一个File对象构造Scanner：
```java
Scanner in = new(Paths.get("myfile.txt"),"UTF-8");
```
如果要写入一个文件，需要PrintWriter对象。  
```java
PrintWriter out = new PrintWriter("myfile.txt","UTF-8");
```

java.util.Scanner
```java
Scanner(File f)
//构造一个从给定文件读取数据的Scanner
Scanner(String data)
//构造一个从给定字符串读取数据的Scanner
```
java.io.PrintWriter
```java
PrintWriter(String fileName)
//构造一个将数据写入文件的PrintWriter。文件名由参数指定
```

java.nio.file.Paths
```java
static Path get(String pathname)
```

### 3.9 大数值

BigInteger和BigDecimal可以分别处理包含任意长度数字序列的数值。前者处理任意精度整数，后者处理任意精度浮点数。

```java
BigInteger a = BigInteger.valueOf(100);//将数字转换为大数值

BigInteger c = a.add(b);//c=a+b
BigInteger d = c.multiply(b.add(BigInteger.valueOf(2)));//d=c*(b+2)
```

java.lang.math.BigInteger
```java
BigInteger add(BigInteger other)
BigInteger subtract(BigInteger other)
BigInteger multiply(BigInteger other)
BigInteger divide(BigInteger other)
BigInteger mod(BigInteger other)
int compareTo(BigInteger other)
//如果这个大整数和other相等，返回0，如果小返回负数，如果大返回正数
static BigInteger valueOf(long x)
//返回数值等于x的大整数
```

java.math.BigInteger
```java
//以上方法中BigInteger都可以换为BigDecimal
Bigdecimal divide(Bigdecimal other RoundingMode mode)
//计算商且给出舍入方式，常用RoundingMode.HALF_UP，四舍五入
static BigDecimal valueOf(long x, int scale)
//返回值为x或x/(10^scale)的一个大实数
```

### 3.10 数组

```java
int[] a = new int[100]
//100个元素的一维数组
a.lengthint 
//获得元素个数

//初始化数组的方式
int[] array = {1,2,3,4,5};
int[] array = new int[]{1,2,3,4,5};
new int[] {1,2,3,4,5};

```

### 3.10.1 for-each

```java
for(int elements : a)
    System.out.println(elements);
```

### 3.10.3 数组拷贝

```java
int[] a = b;
//类似于C中b的地址给了a,通过a也可以访问b那个数组

b = Arrays.copyOf(c,c.length);
//c中的所有元素拷贝给b。第二个参数为b将来的长度。如果c大于这个长度，则相当于被截断存储，小于这个长度，b后面的数字被补0(false)
```

### 3.10.4 命令行参数

```java
java className para1 para2 para3
//args[0]:para1 ,args[1]:para2
```
java.util.Arrays
```java
//API
static String toString(type[] a)
//返回包含a中数据元素的字符串，放在括号内，用逗号分隔
static type copyOf(type[] a,int length)
static type copyOfRange(type[] a,int start,int end)
//返回与a类型相同的一个数组，其长度为length或end-start，数组元素为a的值
static void sort(type[] a)
//快排
static int binarySearch(type[] a,int start int end,type v)
//二分查找值v。找不到返回负数值r，-r-1为a有序时v应插入的位置
static void fill(type[] a,type v)
//将数组所有数据元素设置为v
static boolean equals(type[] a,type[] b)
//如果两个数组大小相同，并且下标对应元素相同，返回true
```

### 3.10.6 多维数组

```java
double[][] a;
a = new double[ROW][COL];

int[][] b = 
{
    {1,2,3,4},
    {5,6,7,8}
};

a = new int[ROW][];
a[i] = new int[j];
//这样可以对每一个a[i]分配不同的空间，不规则数组
```

## 第四章 面向对象程序设计概述

### 4.2.3 LocalDate

```java
static LocalTime now()
//构造表示一个当前日期的对象

static LocalTime of(int year, int month, int day)
//构造一个表示给定日期的对象

int getYear()
int getMonthValue()
int getDayOfMonth()
//得到当前日期的年、月、日

DayOfWeek getDayOfWeek()
//得到当前日期是星期几，调用getValue得到1~7

LocalDate plusDays(int n)
LocalDate minusDays(int n)
//生成当前日期之后或之前n天的日期

```
### 4.3.2 多个源文件的使用

有两个类文件Employee.java和EmployeeTest.java
编译的两种方法：  
>  1.javac Employee*.java  
>  2.javac EmployeeTest.java  

第一种方法中使用通配符编译，第二种方法中没有显示编译EmployeeTest.java,当java编译器发现EmployeeTest.java使用了Employee类时，就会查找Employee.class,如果没有找到就会找源文件自动编译  

### 4.3.5 隐式参数和显式参数  

```java
time.minusDays(5);
```
其中time属于隐式调用，数字5属于显式调用。在方法中，使用this可以替代这个隐式调用
```java
public void minusDays(int n){
    this.day -= n;
}
```

### 4.3.6 封装的优点

可变对象的引用不要这样写
```java
public Date getDay(){
    return hireDay;
}
```
做好写成这样
```java
public Date getDay() {
    return (Date)hireDay().clone();
}
```

### 4.3.9 final实例域

*final*关键字定义常量，用该关键字赋值，值不能再修改  
必须确保在每一个构造执行之后，这个域的值被设置好，并且在后面的操作不再进行修改  
*fianl*还用来修饰不可变类(类中的方法不会更改类的域)  

### 4.3.1 静态

```java
class Employee{
    private int id;
    private static int nextId;
}
```
每一个Employee类都有一个自己的id域，所有创建出来的Employee类，共享同一个nextId域

如果有一个
```java
public static final int nextId = 5;
//可以通过Employee.nextId直接访问nextId而不需要对象
```
方法同理



静态方法和静态成员变量的创建早于所在类的对象实例化，因此泛型类中不能有静态方法

### 4.4.4 工厂方法

### 4.5.0 方法参数



> 1.一个方法不能修改一个基本数据类型(数值型和布尔型)  
> 2.一个方法可以改变一个对象参数的内容  
> 3.一个方法不能让对象参数引用一个新的对象


### 4.6.3 无参数构造器

如果对象中没有构造器，系统会默认提供一个无参数构造；如果有至少一个构造，但不是无参的，那么调用无参构造就会报错。

### 4.6.7 初始化块

```java
class Employee{
    private static int nextId;

    {
        nextId = 1;
    }
}
//无论执行哪个构造，都会执行这个代码块，先执行代码块再执行构造
```

初始化步骤：  
> 1.所有数据被初始化为默认值  
> 2.按照在类声明中出现的次序，依次执行所有域初始化语句和初始化块  
> 3.如果构造器第一行调用了第二个构造器。则执行第二个构造主体  
> 4.执行这个构造主体  


### 4.7.2 静态导入

```java
import static java.util.*;
//静态导入可以直接使用静态类而不需要定义时加static关键字
```

### 4.8.1 设置类路径

```java
java -classpath /xxx/xxx/xxx;.;/xxx/xxx/xxx.jar xxx
```
也可以通过CLASSPATH环境变量搞定

### 4.9.1 文档注释

文档注释以/** 开始，以 */结束,并且需要包含  
> 1.包  
> 2.公有类与接口  
> 3.公有的和受保护的构造器及方法  
> 4.公有的和受保护的域  

每个/**...*/在标记之后紧跟自由格式文本，标记由@开始，例如@author 或者@param  
自由格式文本第一句应该是概要性的句子

### 4.9.2 类注释

类注释必须放在import语句之后，类定义之前

### 4.9.3 方法注释

> 1.@param变量描述 这个标记将对当前方法的param部分添加一个条目。这个描述可以占据多行，并可以使用HTML标记。一个方法的所有@param必须放到一起  
> 2.@return描述 这个标记将对当前方法添加return部分。这个描述可以跨越多行，并可以使用HTML标记。  
> 3.@throws类描述 这个标记将添加一个注释，用于标识这个方法可能抛出的异常。  


### 4.9.4 域注释

只需要对公有域进行注释，通常是静态常量。

### 4.9.5 通用注释

> 1. @author姓名 可以使用多条，每个对应一个作者  
> 2. @version文本 对当前版本的描述  
> 3. @since文本 可以是对某一特性从某版本开始引入的描述
> 4. @deprecated文本 这个标记将对类、方法或变量添加一个不再使用的注释  
> 5. @see引用  添加一些参考的超链接  

- 版权注释:版权注释主要用来声明公司的一些基本信息等

  ```java
  /** 
   * projectName: xxx
   * fileName: Tk.java 
   * packageName: xxxx
   * date: 2017年12月18日下午12:28:39 
   * copyright(c) 2017-2020 xxx公司
   */
  ```

- 类注释:类注释(Class)主要用来声明该类用来做什么，以及创建者、创建日期版本、包名等一些信息

  ```java
      /**
       * @version: V1.0
       * @author: fendo
       * @className: user
       * @packageName: user
       * @description: 这是用户类
       * @data: 2017-07-28 12:20
       **/
  ```

- 构造函数注释:构造函数注释(Constructor)主要用来声明该类的构造函数、入参等信息

  ```java
      **
      * @description: 构造函数
      * @param: [sid, pid]
      */  
  ```

- 方法注释(Methods)主要用来声明该类的作用、入参、返回值、异常等信息

  ```java
  /**
  * @author:  fendo
  * @methodsName: addUser
  * @description: 添加一个用户
  * @param:  xxxx
  * @return: String
  * @throws: 
  */
  ```

- 代码块注释

  ```java
      /**
       * 实例化一个用户
       * xxxxxxx
       */
      User user=new User();
  ```

- 单句注释

  ```java
  User user=new User();  //实例化一个用户
  ```

  

## 第五章 继承

### 5.1.2 覆盖方法

子类新增的方法不能直接访问父类的私有域

```java
class Employee{
    private int salary;

    public int getSalary()
    {
        
    }
}
class Manager extends Employee{
    public int getSalary()
    {

    }
}//不能直接通过Manger.getSalary访问到salary，需要getter setter方法

Manager.getSalary()中添加salary = super.getSalary()
//通过super关键字访问父类的相同方法，有同名方法时也成为覆盖。
```
子类与父类的同名方法覆盖了父类的方法，想要继续访问父类的该方法就使用super关键字。

### 5.1.3 子类构造

```java
public Manager(String name, double salary, int year, int month,int day){
    super(name, salary, year, month, day);
    bonus = 0;
}
```

使用super关键字调用父类的构造方法，且必须在第一句。

如果子类构造无参数，那么将自动调用父类的无参构造。若此时父类没有无参构造，那么将会报错。  

### 5.1.7 阻止继承：final

final修饰的类禁止继承，其中类中的方法也会自动变成final方法，域不受影响。

### 5.1.9 抽象类

关键字: abstract  

此类不能被实例化

包含一个或多个抽象方法的类必须被声明为抽象的。  

抽象类中可以有具体实现的方法，实现后这个方法就不需要abstract关键字。一个类中有未实现的抽象方法，这个类就还是抽象类。  


### 5.2.3 hashCode方法

散列码是由对象导出的一个整型值。散列码是没有规律的。如果x和y是两个不同的对象，那么x.hashCode()和y.hashCode()基本不会相同。 

对有hashCode()方法的对象，散列码由内容决定。对没有hashCode()方法的对象，散列码由存储地址决定。  

```java
//java.util.Object  
int hahsCode()
//返回对象的散列码  

static int hash(Object ... object)
//返回一个散列码，由所提供的对象的散列码组合得到  

static int hashCode(Object a)
//如果a为null返回0，否则返回hashCode()  

//java.lang.(类型)
static int hahsCode(类型)
//返回给定类型的散列码

//java.util.Arrays
static int hashCode(type[] a)
//返回数组a的散列码
```

### 5.2.4 toString()

Object.toString()

返回对象的字符串描述，例如：  

域A = xxx
域B = xxx  

### 5.3 泛型数组列表  

java.util.ArrayList<E>

这个类型不允许基本数据类型
```java 
ArrayList<type> array = new ArrayList<type>();
ArrayList<type> array = new ArrayList<>();
//以上两种初始化都可以  

ArrayList<E>()
//构造一个空数组列表

ArrayList<E>(int initialCapacity)
//构造指定大小的数组

boolean add(E obj)
//在数组列表末尾添加元素，返回true
void add(int index,E obj)
//在指定位置插入，后面的向后移动

int size()
//返回当前数组中包含的元素个数

void ensureCapacity(int capacity)
//确保数组列表在不重新分配存储空间的情况下保证给定数量的元素

void trimToSize()
//将数组的长度缩减到与元素个数相等
```


### 5.3.1 访问数组列表元素

```java

array.set(i,obj)
//使用set设置第i个元素的值

obj = array.get(i)
//get获取第i个元素

E remove(int index)
//删除index位置上的元素，并且返回

for(type e : obj){}
for(int i=0;i<obj.size();++i){}
//遍历方法
```

### 5.4.0 对象包装器与自动装箱

包装：  

        int -> Integer  
        long -> Long  
        float -> Float  
        double -> Double  
        short -> Short  
        byte -> Byte  
        character -> Character  
        void -> Void  
        boolean -> Boolean  

自动装箱、自动拆箱  

list.add(3) = list.add(Integer.valueOf(3))

int n = list.get(i)
int n = list.get(i).intvalueOf();


java.lang.Integer
```java
int intValue()
//以int的形式返回Integer对象的值(在Number类中覆盖了此方法)

static String toString(int i)
//以一个新String对象的形式返回给定数值i的十进制表示  

static String toString(int i, int radix)
//返回数值i的基于给定radix参数进制的表示  

static int parseInt(String s)
static int parseInt(String s, int radix)
//返回字符串s表示的整型数值，给定字符串表示的是十进制的整数（第一种），或者是radix参数进制的整数（第二种）  

static Integer valueOf(String s)
static Integer value Of(String s ,int radix)
//返回用s表示的整型数值进行初始化后的一个新Integer对象，给定字符串表示的是十进制的整数（第一种方法），或者是radix参数进制的整数（第二种）

```
java.text.NumberFormat

```java

Number parse(String s)
//返回数字值，假设给定的String表示一个数值  
```

### 5.5.0 参数数量可变的方法  



### 5.6 枚举类

[回看第三章3.5.9--枚举类型](# 3.5.9 枚举类型)

### 5.7 反射

### 5.7.1 Class类

程序运行期间，Java运行时系统始终为所有的对象维护一个被称为运行时的类型标识。这个信息跟踪着每个对象所属的类。虚拟机利用运行时类型信息选择相应的方法执行。保存这些信息的类被称为`Class类`

有两个类型`Employee`和`Manager`，他们中都有`getName()`方法可以获得名字

```java
Employee e;
System.out.println(e.getClass().getName()+" "+e.getName());

//这会输出 Employee Ligen
//如果e是Manager类型，则会输出Manager Ligen
```

静态方法获得类名

```java
String className = "java.until.Random";
Class c1 = Class.forName(className);
```

通过`.class`获得类

```java
Class c1 = Random.class;
```



### 5.7.2 捕获异常

详情见第7章  

当程序运行发生错误时，就会抛出异常。抛出异常后可以对异常就行处理。  

如果没有提供异常处理，程序就会终止，并在控制台打印出一条信息，其中给出了异常的类型。

```java

try {
    //正常执行，抛出异常被捕获就执行catch
} catch (Exception e) {
    //TODO: handle exception
}

```

### 5.7.3 利用反射分析类的能力  

利用`java.lang.reflect`包中的三个类`Field`、`Method`、`Constructor`分别用于描述类的域、方法和构造器。这三个类都有一个`getName`方法返回类的名称。`Feild`类由一个`getType`方法，用来描述域所属类型的`Class`对象。`Method`和`Constructor`类有能够报告参数类型的方法，`Method`类还有一个可以报告返回类型的方法。这三个类还有一个`getModifiers`的方法，它将返回一个整型数值，用不同的位开关描述`public`和`static`这样的修饰符使用状况。

利用`java.lang.reflect`包中的`Modifier`类的静态方法分析`getModifier`返回的整型数值。

`Class`类个`getFileds`，`getMethods`，`getConstructors`方法将分别返回类提供的域、方法和构造器

### 5.7.4 在运行时使用反射分析对象

 

### 5.7.5 使用反射编写泛型数组代码

### 5.7.6 调用任意方法

### 5.8.0 继承的设计技巧

> 1. 将公共操作和域放在超类
> 2. 不要使用受保护的域
> 3. 使用继承实现“is-a”关系
> 4. 除非所有继承的方法都有意义，否则不要使用继承
> 5. 在覆盖方法时，不要改变预期的行为
> 6. 使用多态，而非类型信息
> 7. 不要过多地使用反射  


## 第六章 接口、lambda表达式与内部类


### 6.1.1 接口的概念

接口可以理解为一种特殊的类，里面全部是由全局常量和公共的抽象方法所组成。接口是解决Java无法使用多继承的一种手段，但是接口在实际中更多的作用是制定标准的。或者我们可以直接把接口理解为100%的抽象类，既接口中的方法必须全部是抽象方法。（JDK1.8之前可以这样理解）

接口是java中实现多继承的一种形式。

使用一些类中的否写方法时，需要先实现某些接口。
例如使用Array的sort类就要实现Comparable接口  

### 6.1.2 接口的特性

> 1. 接口中每一个方法也是隐式抽象的,接口中的方法会被隐式的指定为 public abstract（只能是 public abstract，其他修饰符都会报错）
> 2. 接口中可以含有变量，但是接口中的变量会被隐式的指定为 public static final 变量（并且只能是 public，用 private 修饰会报编译错误）
> 3. 3.接口中的方法是不能在接口中实现的，只能由实现接口的类来实现接口中的方法。  

```java
//接口不能实例化  
x = new Comparable();  //ERROR  

//可以声明接口变量  
Comparable x; //OK  
```

### 6.1.3 接口与抽象类

> 1. 抽象类中的方法可以有方法体，就是能实现方法的具体功能，但是接口中的方法不行。
> 2. 抽象类中的成员变量可以是各种类型的，而接口中的成员变量只能是 public static final 类型的。
> 3. 接口中不能含有静态代码块以及静态方法(用 static 修饰的方法)，而抽象类是可以有静态代码块和静态方法。
> 4. 一个类只能继承一个抽象类，而一个类却可以实现多个接口。

### 6.2.3 对象克隆

```java
Employee original = new Employee("John Public",5000);
Employee copy = original;
copy.raiseSalary(10);//实际还是改变original的内容

Employee copy = original.clone();
copy.raiseSalary(10); //这样original不会改变

```

### 6.3.2 lambda表达式语法

```java
public static void main(String[] args) {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("我的世界");
            }
        };
        r1.run();
        /**
         * 无参数，无返回值
         * 接口的一种实现形式
         */
        Runnable r2 = () -> System.out.println("我的世界");
        r2.run();

        /**
         * 一个参数，无返回值
         */
        Consumer<String> con1 = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };
        con1.accept("我的世界");

        Consumer<String> con2 = (String s)-> {System.out.println(s);};
        con2.accept("我的世界2");

        /**
         * 数据类型，省略，由编译器推断。类型推断
         */
        Consumer<String> con3 = (s)-> {System.out.println(s);};
        con3.accept("我的世界3");
    
    
    	/**
         * 两个及以上的参数，多条执行语句，并有返回值
         */
        Comparator<Integer> com1 = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1-o2;
            }
        };
        System.out.println(com1.compare(1,2));
        Comparator<Integer> com2 = (o1,o2) -> {return o1-o2;};
        System.out.println(com2.compare(2,1));
    
    	 /**
         * 只有一条语句，return 和 大括号都可以省略
         */
        Comparator<Integer> com3 = (o1,o2) -> o1-o2;
        System.out.println(com3.compare(1,2));
    }

//lambda可以不需要类型和返回类型，这个可以通过上下文推导
(String first, String second) -> first.length() - second.length(); //该语句可以用在以int为上下文环境的情形下

```

### 6.3.3 函数式接口

### 6.3.4 方法引用

### 6.3.7 处理lambda表达式

使用lambda表达式的重点是延迟执行，希望之后再运行这串代码  
> 1.在一个单独的线程中运行代码  
> 2.多次运行代码  
> 3.在算法的适当位置运行代码（例如排序中）  
> 4.发生某种情况时运行代码（例如点击按钮）  
> 5.只在必要时运行代码  


```java
repeat(10, () -> System.out.println("Hello, world!"));  

public static void repeat(int n, Runnable action)
{
    for(int i=0;i<10;++i) action.run();
    //每次调用action.run(),都会调用lambda表达式主体
}

```

`lambda`表达式相当于一个匿名方法



### 6.4.0 内部类

定义在一个类中的类。使用原因如下：  
> 1.内部类可以访问该类定义所在作用域的所有数据  
> 2.内部类可以对同一个包中的其他类隐藏起来  
> 3.当想要定义一个回调函数且不想编写大量代码使用匿名内部类很方便  

### 6.4.6 匿名内部类  

```java

public void start(int interval, boolean beep)
{
    ActionListener listener = new ActionListener()
    {
        public void actionPerformed(ActionListener event)
        {
            System.out.println("xxxxx");

        }
    };
    //创建一个实现ActionListener接口的类的新对象，需要实现的方法actionPerformed定义在括号{}内
}
```

# 第八章 泛型程序设计

### 8.2 定义简单泛型类

用尖括号<>将类型括起来，一个泛型类可以拥有多个类型参数

```java
public class Pair<T, U> {
    private T first;
    private T second;
    
    private U third;

    public Pair() {
        this.first = null;
        this.second = null;
    }

    public Pair(T first, T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public void setFirst(T first) {
        this.first = first;
    }

    public T getSecond() {
        return second;
    }

    public void setSecond(T second) {
        this.second = second;
    }
}
```

**注意：**在java中，使用变量`E`表示集合的元素类型，`K`和`V`分别代表关键字和值的类型，`T`代表任意类型（需要多类型时，可以使用`U`，`S`等临近的字母）

### 8.3 泛型方法

在普通类中定义一个参数为`T`的泛型方法

```java
public class Pair {
    public static<T> void getClass(T... param){//将T作为参数，可以返回任意类型，包括T类型
        System.out.println("这是一个泛型方法:"+param);
    }

    public static void main(String[] args) {
        Pair.<String>getClass("ye","ww");
    }
}
```

在修饰符后加上尖括号和类型变量，返回值可以是任意类型。

使用时通常加上<类型\>来确定类型，不加偶尔会出问题。`T...`是多参数写法。这本质上是为了区分是不是有一个自定义类名字叫做T，所以需要加尖括号来明确这是一个泛型

### 8.4 类型变量的限定

上面的泛型方法中都没有对类型`T`进行限定，假如下面这段代码要求最小值

```java
public static<T> void min(T[] array){
    T smallest = array[0];
    for (int i = 0; i < array.length; i++) {
        if(smallest.compareTo(array[i])<0){
            smallest = array[i];
        }
    }
}
```

这里面有一个问题，就是类型`T`不一定有实现`Comparable`接口，所以我们可以对类型`T`进行一个限定

```java
public static<T extends Comparable> void min(T[] array)
```

这样这个泛型min方法就只能被实现了Comparable接口的类型调用。即使Comparable是接口，尖括号中也使用extends

多实现，使用`&`符号连接

```java
<T extends Comparable & Serializable>
```

### 8.4.1 通配符

```java
List<String> list1 = null;
List<Object> list2 = null;
List<?> list3 = null;
//list1和list2是并列关系，使用了通配符?，则list3是list1和list2的父类
//通配符即可以匹配所有的类型
//list3.add();是非法的。通配符不能添加元素，只能加null
//允许读取
```



### 8.5.1 类型擦除

对于虚拟机而言，只有普通类没有泛型类，所以虚拟机会将一个泛型类替换为普通类

在[8.2 定义简单泛型类](# 8.2 定义简单泛型类)中我们使用了`Pair<T>`的泛型类型（忽略类型`U`），因为`T`可以是任意方法，所以我们可以将类型擦除，替换为Object类

```java
public class Pair {
    private Object first;
    private Object second;
}
```

这样一个泛型类就变为了普通类

如果有类型变量限定时，会替换为第一个类型限定。

```java
<T extends Comparable & Serializable>
```

会替换为`Comparable`

### 8.5.2 翻译泛型表达式

如下面的语句虚拟机在执行时会翻译为两条指令：

```java
Pair<Manager> buddies = new Pair();
Manager manager = buddies.getFirst();
```

1. `buddies.getFirst()`在类型擦除后应该返回一个`Object`类型，所以先返回一个`Object`
2. 然后虚拟机会对`Object`继续强转，强转为`Manager`

### 8.5.3 翻译类型方法





### 8.6 约束与局限性

### 8.6.1 不能用基本类型实例化类型参数

对一个泛型类进行实例化的时候，不能使用

```java
Pair<int>
```

而必须使用

```java
Pair<Integer>
```

造成这样的原因是类型擦除，Pair类中只能包含Object类型的域

### 8.6.2 运行时类型查询只适用于原始类型

如果要查询一个类型是否是泛型类型

```java
if(a instanceof Pair<String>)
if(a instanceof Pair<T>)
```

这两种写法都会报错，这是一个编译器错误

```java
(Pair<String>)a
```

强制类型转化会得到一个警告

```java
Pair<String> c = ...;
Pair<Manager> d = ...;
return c.getClass()==d.getClass();
```

其比较结果是true，因为它们都是Pair.class

### 8.6.3 不能创建参数化类型的数组

数组会记住它的元素类型，如果试图存储其他类型的元素，就会抛出一个ArrayStoreException异常

不过对于泛型类型，上面这种机制会失效

```java
Pair<String>[] array = new Pair<String>[10];
```

这是因为擦除后，array的类型是Pair[]，可以把它转化为Object[]。

```java
Object[] obj = array;
obj[0]=new Pair<Employee>()
```

能够通过数组检查，不过仍然会导致一个类型错误，出于这个原因，不允许创建参数化类型的数组

**注意：**如果需要使用参数化类型的数组，唯一的办法就是使用ArrayList

```java
ArrayList<Pair<String>>
```



### 8.6.4 Varargs警告

### 8.6.5 不能实例化类型变量

### 8.6.6 不能构造泛型数组

```java
public static<T extends Comparable> T[] getMin(T[] a){
    T[] min = new T[2];
    return min;
}
```

这样看起来没有错，但是因为类型擦除的原因，这里会永远创建一个 Comparable[2] 的数组







### 8.6.7 泛型类的静态上下文中类型变量无效

### 8.6.8 不能抛出或捕获泛型类的实例

### 8.6.9 可以消除对受查异常的检查

### 8.6.10 注意擦除后的冲突







## 第九章 集合

### 9.1.1 将集合的接口与实现分离

假设自己写了两个类，一个用循环数组实现队列，一个用链表实现队列

```java
public class CirularArrayQueue<E> implements Queue<E>{}//循环数组
public class LinkedListQueue<E> implements Queue<E>{}//链表
```

这样在构建时，我们可以选择任意一种

```java
Queue<E> example = new CirluarArrayQueue<>(100);
Queue<E> example = new LinkedListQueue<>(100);
```

这样就是实现了集合的接口与实现的分离，如果需要不同的队列实现，只需要修改构造器就可以。

### 9.1.2 Collection接口

在java类库中，集合类的基本接口是Collection，有两个基本方法：

```java
public interface Collection<E>
{
    boolean add(E element);
    Iterator<E> iterator();
}
```

### 9.1.3 迭代器(iterator)

Iterator接口包含四个方法：

```java
public interface Iterator<E>
{
    E next();//访问下一个元素
    boolean hasNext();//是否有下一个元素
    void remove();
    default void forEachRemaining(Consumer<? super E> action);
}
```

通过反复调用next方法，可以逐个访问集合中的每个元素。但是到了集合的末尾，next方法可以抛出一个NoSuchElementException。因此需要再调用next前调用hasNext方法。
```java
//几种遍历方法
Collection<String> c = ...;
Iterator<String> iter = c.iterator();
while(iter.hasNext())
{
    String element = iter.next();
    //
}

for(String element : c)
{
    //
}
```

如果要删除两个相邻的元素，必须如下操作：

```java
it.remove();
it.next();//跳过将要删除的元素
it.remove();
```

### 9.1.4 泛型的实用方法

### 9.1.5 集合框架中的接口

### 9.2.1 链表

```java
List<String> a = new LinkedList<>();
```

### 9.2.2 数组列表

```java
List<String> a = new ArrayList<>();

//遍历方法
 //方法1
Iterator it1 = list.iterator();
while(it1.hasNext()){
    System.out.println(it1.next());
}

//方法2
for(Iterator it2 = list.iterator();it2.hasNext();){
     System.out.println(it2.next());
}

//方法3
for(String tmp:list){
    System.out.println(tmp);
}

//方法4
for(int i = 0;i < list.size(); i ++){
    System.out.println(list.get(i));
}
```

自定义排序方法

```java
//对一维数组
Arrays.sort(arr,(a,b)->a-b);
//对二维数组
Arrays.sort(arr,(a,b)->a[0]-b[0]);

//实现Comparator接口
//返回正数第二个参数排前面
//返回负数第一个参数排前面
class My implements Comparator<int[]>{
    @Override
    public int compare(int[] o1, int[] o2) {
        return o1[0]-o2[0];
    }
}
Comparator cmp = new My();
Arrays.sort(arr,cmp);
```



### 9.2.3 散列集

```java
Set<String> a = new HashSet<>();
//遍历
for(Integer item : a)
{//for-each

}

Iterator<Integer> it = a.iterator();
while(iter1.hasNext())
{//迭代器遍历
    iter1.next();
}
```

### 9.2.4 树集

```java
//有序
Set<String> a = new TreeSet<>();
//遍历
for(Integer item : a)
{//for-each
}

Iterator<Integer> it = a.iterator();
while(iter1.hasNext())
{//迭代器遍历
    iter1.next();
}
```

### 9.2.5 队列

### 9.3 哈希表有序表

补充

哈希表HashMap和HashSet，所有操作O(1)

有序表TreeMap和TreeSet，因为这个有序，当不是基础类型时，我们需要给 它一个比较器来比较。负责按地址值排序。所有操作O(logn)

### 9.3.1 映射

```java
Map<String,Employee> staff = new HashMap<>();
Map<String,Employee> staff = new TreeMap<>();
//遍历
Iterator<Entry<Integer,String>> it = staff.entrySet().iterator();//整个Hashtable的迭代器
while(it.hasNext())
{
	
}
Iterator<Integer> it = ht.keySet().iterator();//Key的迭代器

//映射视图
Set<Map.Entry<K,V>> entrySet()//返回Map.Entry对象(键值对)的一个集视图，可以从这个集中删除元素，同时从映射中删除，但是不能增加元素
Set<K> keySet()	//返回映射中所有键的一个集视图，可以从这个集中删除元素，键的相关联值也会从映射中删除，但不能增加元素
Collection<V> values()//返回映射中所有值得一个集合视图，效果同前
```











### 9.3.5 链接散列集和映射

```java
Map<String,String> staff = new LinkedHashMap<>();
Set<String> staff = new LinkedHashSet<>();
```

### 9.3.6 枚举集和映射

```java
EnumSet<T>
EnumMap<K,V>
```

### 9.3.7 标识散列映射







# 14 多线程

![](https://github.com/LiMuwenan/PicBed/blob/master/img/dev/java/JUC/JUC-%E7%BA%BF%E7%A8%8B%E8%B0%83%E5%BA%A6.png?raw=true)

进程、线程、多线程

[多线程 - B站狂神说多线程详解](https://www.bilibili.com/video/BV1V4411p7EF?)

实现多线程的三种方式：

- 继承Thread类
- 实现Runnable接口
- 实现Callable接口

## 14.1 Thread

[java8官方文档 - java.lang.Thread](https://docs.oracle.com/javase/8/docs/api/index.html)

`Thread`创建线程分为三步：

- 继承Thread类
- 重写run方法
- 实例化类并调用start方法开启线程



```java
package cn.edu.xidian;

import java.lang.Thread;

public class ThreadTest extends Thread {
    /**
     * 方式一：
     * 第一步：继承Thread方法
     * 第二步：重写run方法
     * 第三步：调用start开启线程
     */

    @Override
    public void run() {
        //线程体
        for (int i = 0; i < 200; i++) {
            System.out.println("我在线程里: "+i);
        }
    }

    public static void main(String[] args) {

        //开启线程
        ThreadTest threadTest = new ThreadTest();
        //如果这里执行的run方法，就不会开启多线程
        threadTest.start();

        for (int i = 0; i < 200; i++) {
            System.out.println("我在学习多线程: "+i);
        }
    }
}
```





## 14.2 Runnable

[java8官方文档 - java.lang.Runnable](https://docs.oracle.com/javase/8/docs/api/index.html)

`Runnable`创建线程分为三步：

- 实现Runnable接口
- 实现run方法
- 使用Thread匿名对象调用start方法

```java
package cn.edu.xidian;

public class RunnableTest implements Runnable{
    /**
     * 方式二：
     * 第一步：实现Runnable接口
     * 第二步：实现run方法
     * 第三步：匿名创建Thread类，调用start方法
     */
    
    @Override
    public void run() {
        //线程体
        for (int i = 0; i < 200; i++) {
            System.out.println("我在线程里: "+i);
        }
    }

    public static void main(String[] args) {

        //开启线程
        RunnableTest runnableTest = new RunnableTest();
        new Thread(runnableTest).start();

        for (int i = 0; i < 200; i++) {
            System.out.println("我在学习多线程: "+i);
        }
    }
}

```



## 14.3 Callable

`Callable`接口实现多线程：

- 实现Callable接口，需要返回值类型
- 实现call方法，需要抛出异常
- 创建目标对象
- 创建执行服务
- 提交执行
- 获取结果
- 关闭服务

这个接口就是创建线程池

```java
package cn.edu.xidian;

import java.util.concurrent.*;

public class CallableTest implements Callable {
    /**
     * - 实现Callable接口，需要返回值类型
     * - 实现call方法，需要抛出异常
     * - 创建目标对象
     * - 创建线程池
     * - 提交执行
     * - 获取结果
     * - 关闭服务
     * @return
     */
    private int ticketNums = 20;

    @Override
    public Object call() throws Exception {
        while(true){
            if(ticketNums<=0){
                break;
            }
            System.out.println(Thread.currentThread().getName()+" 买到第"+ticketNums--+"票");
        }

        return true;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CallableTest callableTest1 = new CallableTest();
        CallableTest callableTest2 = new CallableTest();
        CallableTest callableTest3 = new CallableTest();

        //创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        //提交执行
        Future<Boolean> r1 = executorService.submit(callableTest1);
        Future<Boolean> r2 = executorService.submit(callableTest1);
        Future<Boolean> r3 = executorService.submit(callableTest1);

        //获取结果
        boolean rs1 = r1.get();
        boolean rs2 = r2.get();
        boolean rs3 = r3.get();

        //关闭线程池
        executorService.shutdown();
    }

}

```



## 14.4 并发

当同时有很多用户进行操作访问的时候就是并发，这个时候如果是线程不安全的就会出现问题

```java
package cn.edu.xidian;

public class TicketThread implements Runnable{

    private int ticketNums = 10;

    @Override
    public void run() {
        while(true){
            if(ticketNums<=0)
                break;

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName()+"拿到了第"+ticketNums--+"票");
        }
    }

    public static void main(String[] args) {
        TicketThread ticketThread = new TicketThread();

        new Thread(ticketThread,"小明").start();
        new Thread(ticketThread,"老师").start();
        new Thread(ticketThread,"黄牛").start();
    }
}

```

上面的程序就可能出现负票数或者两个人拿到了同一张票，这里就是线程不安全



## 14.5 进程状态

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/JUC/JUC-%E7%BA%BF%E7%A8%8B%E7%8A%B6%E6%80%81%E5%9B%BE.png)

- NEW （新生）

- Runnable（可运行）
- Blocked（阻塞）
- Waiting（等待）
- Timed Waiting（计时等待）
- Terminated（终止）

调用`getState`方法查看线程状态



```java
package cn.edu.xidian;

public class JoinTest implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("线程vip来了-->"+i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JoinTest joinTest = new JoinTest();

        Thread thread = new Thread(joinTest);
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());

//        thread.join();
        for (int i = 0; i < 100; i++) {
            System.out.println("main-->" + i);
        }
    }
}
```



### 14.5.1 创建状态

`new`操作符创建一个线程

```java
new Thread(r)
```

线程被创建，但是还没有运行

### 14.5.2 就绪状态

```java
new Thread(r).start();
```

调用`start`方法让线程进入就绪状态，等待CPU分配资源



### 14.5.3 阻塞状态

`wait`, `sleep`, `join`, `lock`等方法使线程进入阻塞状态

### 14.5.4 各种方法



| 方法                           | 说明                                                         |
| ------------------------------ | ------------------------------------------------------------ |
| setPriority(int new Priority)  | 更改线程优先级                                               |
| static void sleep(long millis) | 让线程休眠毫秒                                               |
| void join()                    | 等待该线程终止                                               |
| static void yield()            | 暂停当前正在执行的线程对象并执行其他线程，不是阻塞，重新回到就绪态 |
| void interrupt()               | 中断线程                                                     |
| boolean isAlive()              | 测试线程时候处于活动状态                                     |



 

```java
//yield
package cn.edu.xidian;

public class YieldTest{

}

class MyYield implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"线程开始执行");
        Thread.yield();
        System.out.println(Thread.currentThread().getName()+"线程结束执行");
    }

    public static void main(String[] args) {
        MyYield myYield = new MyYield();

        new Thread(myYield,"A").start();
        new Thread(myYield,"B").start();
    }
}

```





```java
//join
//使用了join方法，只有当新线程执行完，主线程才会继续执行，不会出现交替执行的情况
package cn.edu.xidian;

public class JoinTest implements Runnable{
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println("线程vip来了-->"+i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JoinTest joinTest = new JoinTest();

        Thread thread = new Thread(joinTest);
        thread.start();

        thread.join();
        for (int i = 0; i < 100; i++) {
            System.out.println("main-->" + i);
        }
    }
}

```





```java
//setPriority
Thread.MIN_PRIORITY = 1;
Thread.MAX_PRIORITY = 10;
Thread.NORM_PRIORITY = 5;
//数字越大优先级越高
//优先级高只会增加线程被CPU选中的概率
```



## 14.6 守护线程

```java
//开启守护线程
thread.setDaemon(true);
```

通常在守护线程中运行一些服务。当只剩下守护线程的时候，虚拟机就会退出。



## 14.7 同步

前面[14.4 并发](# 14.4 并发)提到，在两个线程同时操作一个数据的时候会出现线程不安全的情况

```java
package cn.edu.xidian.sync;


//线程不安全的买票方式
public class UnSync {

    public static void main(String[] args) {
        BuyTicket station = new BuyTicket();

        new Thread(station,"小明").start();
        new Thread(station,"老师").start();
        new Thread(station,"黄牛").start();
    }

}

class BuyTicket implements Runnable{

    //总共十张票
    private int ticketNums = 10;
    boolean flag = true;//外部停止方式
    @Override
    public void run() {
        //买票
        while(flag){
            buy();
        }
    }

    private void buy() {
        //判断是否有票
        if(ticketNums<=0){
            flag=false;
            return;
        }

        //模拟延时
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //买票
        System.out.println(Thread.currentThread().getName()+"买到"+ticketNums--+"票");
    }

}

```



### 14.7.1 锁机制(synchronized)

关键字`synchronized`，当一个线程获得锁的时候，独占资源，其他线程必须等待，使用完后才会释放锁，这样会引发几个问题：

- 一个线程持有锁会导致其他所有需要此锁的线程挂起
- 在多线程竞争下，加锁、释放锁会导致比较多的上下文切换和调度延时，引起性能问题
- 如果一个优先级高的线程等待一个低优先级线程，会导致优先级倒置，引起性能问题

上面的程序如果经过下面这样的改动，就不会出现买错票的情况。

`synchronized`关键字会自动地将多个线程排队，同时只能有一个线程访问这个资源

**普通对象或匿名代码块：给指定对象加锁（monitorenter和monitorexit）**

**实例方法：对当前方法加锁（ACC_SYNCHRONIZED标识符存在同步方法常量池中，线程会查看是否有这个标志，如果有就尝试获得锁）**

**静态方法：class对象**

**一个线程获得一个方法锁的时候意味着锁定了该实例，所有加锁的方法别的线程都不能访问；而且这个锁是可重入锁，其他有锁的代码块该线程也是可以直接使用**

```java
private synchronized void buy() {
    //判断是否有票
    if(ticketNums<=0){
        flag=false;
        return;
    }

    //模拟延时
    try {
        Thread.sleep(10);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }

    //买票
    System.out.println(Thread.currentThread().getName()+"买到"+ticketNums--+"票");
}
```

使用该关键字还等价于

```java
 public synchronized void method(){//锁定了该类的该实例
    //方法体
}
//等价
public void method(){
    this.intrisicLock.lock();//thread是要调用该方法的线程
    try{
        //方法体
    }finally {
        this.intrisicLock.unlock();
    }
}
```

除了给方法加锁，还可以给代码块加锁

```java
synchronized (Obj){
	//
}
```

上面的代码改成下面这样，出现的问题是，可以同步，但是会出现-1票。因为A线程进来看到还有1张票准备买，执行到同步代码块，这时候B看到也有1张票快执行到同步代码块，这时候A会买完变成0张，B会买变成-1张，这就是为什么会有-1的原因。

**静态方法加锁等价于锁定该类的所有实例**

```java
private Object lock = new Object();//这个对象的创建就是为了锁代码块而创建

private void buy() {
    //判断是否有票
    if(ticketNums<=0){
        flag=false;
        return;
    }

    //模拟延时
    try {
        Thread.sleep(10);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    
    synchronized (lock){
        //买票
        System.out.println(Thread.currentThread().getName()+"买到"+ticketNums--+"票");

    }
}
```



`ReentrantLock`锁，可以达到`synchronized`同样的效果，并可以完成更多的功能。这个锁还称为可重入锁

```java
class BuyTicket implements Runnable{

    //总共十张票
    private int ticketNums = 10;
    boolean flag = true;//外部停止方式

    ReentrantLock myLock = new ReentrantLock();

    @Override
    public void run() {
        //买票
        while(flag){
            buy();
        }
    }

    private void buy() {

        myLock.lock();
        try{
            //判断是否有票
            if(ticketNums<=0){
                flag=false;
                return;
            }

            //模拟延时
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //买票
            System.out.println(Thread.currentThread().getName() + "买到" + ticketNums-- + "票");
        }finally {
            myLock.unlock();
        }
    }
}
```

有时候只需要对一个域加锁，这时候对整个方法或者代码块加锁的话开销就太大了，这个时候可以使用`voltaile`关键字，这个关键字可以禁止指令重排，并且对于多个线程，这个变量的改变对其他线程都是可见的

```java
private volatile int ticketNums = 10;
```

上面这样的代码会出现买票的时候几个人买了第十张票，几个人买了第七张票，这是因为`volatile`操作不是原子性的，它可以立即改变内存中变量的值并对其他线程可见，但是进行操作的时候，非原子性操作就会造成数据的误读。



## 14.8 线程通信



| 方法               | 说明                                         |
| ------------------ | -------------------------------------------- |
| wait()             | 表示线程一直等待，知道其他线程通知，会释放锁 |
| wait(long timeout) | 制定等待的毫秒数                             |
| notify()           | 唤醒一个处于等待状态的线程                   |
| notifyAll()        | 唤醒同一个对象所有调用wait()方法的线程       |



### 14.8.1 管程法

```java
package cn.edu.xidian.procom;


//管程法
public class Buffer {
    public static void main(String[] args) {
        SyncBuffer syncBuffer = new SyncBuffer();

        Comsumer comsumer = new Comsumer(syncBuffer);
        Producer producer = new Producer(syncBuffer);

        new Thread(producer,"生产").start();
        new Thread(comsumer,"消费").start();

    }
}

//消费者
class Comsumer implements  Runnable{
    SyncBuffer syncBuffer;

    public Comsumer() {
    }

    public Comsumer(cn.edu.xidian.procom.SyncBuffer syncBuffer) {
        this.syncBuffer = syncBuffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("消费了"+syncBuffer.pop().id+"个产品");
        }
    }
}

//生产者
class Producer implements Runnable{
    SyncBuffer syncBuffer;

    public Producer() {
    }

    public Producer(cn.edu.xidian.procom.SyncBuffer syncBuffer) {
        this.syncBuffer = syncBuffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("生产了"+i+"只鸡");
            syncBuffer.push(new Product(i));
        }
    }
}

//产品
class Product{
    int id;

    public Product(int id) {
        this.id = id;
    }
}

//缓冲区
class SyncBuffer{
    //创建一个大小为10的缓存区
    Product products[] = new Product[10];

    int cnt = 0;//记录缓存区的位置

    //生产者放入产品
    public synchronized void push(Product product){
        //如果容器满了等待消费者消费
        if(cnt>=products.length){
            //通知消费者消费，生产者等待
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        //如果不满继续生产
        products[cnt++] = product;

        //可以通知消费者消费
        this.notifyAll();

    }


    //消费者消费产品
    public synchronized  Product pop(){
        //如果有产品就可以消费
        if(cnt<=0){
            //等待生产者生产
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //可以消费
        cnt--;
        Product product = products[cnt];
        System.out.println("我拿走了"+product.id+"号产品");

        //通知生产
        this.notifyAll();

        return product;
    }
}

```





## 14.9 线程池

一次性创建一个固定大小的池子，里面放着所有的线程，包括空闲的不空闲的。如果有任务来，就从空闲的线程中拿出来一个执行任务，执行完又放回去。这样就保证了不会频繁的创建和销毁线程，减少系统的开销

`Executors`是一个工厂方法

| 方法                             | 说明                                       |
| -------------------------------- | ------------------------------------------ |
| newCachedThreadPool              | 必要时创建新线程，空闲线程保留60秒         |
| newFixedThreadPool               | 包含固定数量的线程，空闲线程一直保留       |
| newSingleThreadExecutor          | 只有一个线程池，该线程池顺序执行提交的任务 |
| newScheduledThreadPool           | 用于预定执行而构建的线程池                 |
| newSingleThreadScheduledExecutor | 用于预订执行而构建的单线程池               |

同时开始多个线程，最终都只会使用池子里的三个线程

```java
package cn.edu.xidian;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Pool {

    public static void main(String[] args) {
        //1.创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        //2.执行 及时调用多次，也只有3个线程
        executorService.execute(new MyThread());
        executorService.execute(new MyThread());
        executorService.execute(new MyThread());
        executorService.execute(new MyThread());
        executorService.execute(new MyThread());
        executorService.execute(new MyThread());

        //3.关闭连接
        executorService.shutdown();
    }
}

class MyThread implements Runnable{

    @Override
    public void run() {
        for (int i = 0; i < 2; i++) {
            System.out.println(Thread.currentThread().getName()+"执行了"+i);
        }
    }
}

```

如果使用`submit`提交，这会返回一个`Future`的对象

```java
public static void main(String[] args) {
    //1.创建线程池
    ExecutorService executorService = Executors.newFixedThreadPool(3);

    //2.执行 及时调用多次，也只有3个线程
    Future<?> r1 = executorService.submit(new MyThread());
    Future<?> r2 = executorService.submit(new MyThread());

    System.out.println(r1);
    System.out.println(r2);

    //3.关闭连接
    executorService.shutdown();
}
```

三种`submit`提交方法

```java
Future<?> .submit(Runnable task);
//可以调用isDone cancel isCancelled方法，get在完成的时候只会简单的返回null
Future<T> .submit(Runnable task,T result);
//get方法返回指定的result对象
Future<T> .submit(Callable<T> task);
//使用执行结果需要用
```

# 第二章 输入与输出

## 2.1 输入输出流

抽象类，`InpuStream`和`OutputStream`构成输入输出流的基础



### 2.1.1 读写字节

`InputStream`类有一个抽象方法：

```java
abstract int read();
```

这个方法将读入一个字节，并返回读入的字节，或者在遇到输入圆结尾时返回`-1`。在设计具体的输入流时，必须覆盖这个方法。

`OutputStream`类定义了下面的抽象方法：

```java
abstract void write(int b);
```

它可以向某个输出位置写成一个字节



### 2.1.3 组合输入输出流过滤器



`FileInputStream`和`FileOutputStream`可以提供附着在一个磁盘文件上的输入流和输出流，而你只需要向构造器提高完整的路径名称

```java
FileInputStream fin = new FileInputStram("employee.dat");
```

所有在`java.io`中的类都将相对路径名称解释为以用户工作目录开始



该类只支持字节级别的读写，我们只能从`fin`对象中读入字节和字节数组

```java
byte b = (byte)fin.read();
```

## 2.2 文本输入与输出

`OutputStreamWriter`类将使用选定的字符编码，把`Unicode`码元的输出流转换为字节流。

`InputStreamReader`类将包含字节的输入流转换为可以产生`Unicode`码元的读入器

下面的代码展示了如何让一个输入读入器可以从控制台读入键盘敲击信息，并将其转换为`Unicode`

```java
Reader in = new InputStreamReader(System.in);
//这个会使用主机系统所使用的的默认字符编码方式
```

应在总是在`InputStreamReader`的构造器中选择一种具体的编码方式

```java
Reader in = new InputStreamReader(new FileInputStream("data.txt"),StandardCharsets.UTF_8);
```

### 2.2.1 如何写出文本输出

对于文本输出，可以使用`PrintWriter`。这个类拥有以将文本格式打印字符串和数字的方法，它还有一个将`PrintWriter`链接到`FileWriter`的便捷方法

```java
PrintWriter out = new PrintWriter("data.txt",Boolean.parseBoolean("UTF_8"));
```

等同于：

```java
PrintWriter out = new PrintWriter(
		new FileOutputStream("data.txt"),Boolean.parseBoolean("UTF_8")
	);
```

执行下面的代码就可以将内容输出到文件

```java
//覆盖式写入
public static void main(String[] args) {

    PrintWriter out = null;
    FileOutputStream fos = null;
    try {
        fos = new FileOutputStream("data.txt");
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    }
    out = new PrintWriter(fos, Boolean.parseBoolean("UTF_8"));

    String name = "Harry ligen";
    double salary = 75000;
    out.print(name);//用out.write()有桐乡的效果
    out.print(' ');
    out.println(salary);

    out.close();//需要关闭输出流才会将打印器中的内容输出到文件，不关闭得话文件中是没有内容的
}
```

追加式写入：

```java
public static void main(String[] args) {

    PrintWriter out = null;
    FileWriter fw = null;
    try {
        fw = new FileWriter("data.txt",true);//true参数开启追加

    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    out = new PrintWriter(fw);

    String name = "Harry ligen";
    double salary = 75000;
    out.print(name);
    out.print(' ');
    out.println(salary);

    out.close();
}
```

### 2.2.2 如何读入文本输入

[卷 I 写入Scanner](# 3.7.1 读取输入)

我们可以从任何流中构建`Scanner`对象。

或者我们也可以将短小的文本文件像下面这样读入到一个字符串中

```java
String content = new String(Files.readAllBytes(Paths.get("data.txt")),"UTF8");
```

如果是需要一行一行地读入，那么可以调用：

```java
List<String> lines = Files.readAllLines(Paths.get("data.txt"), Charset.defaultCharset());
```

### 2.2.3 以文本格式存储对象

使用`String.split`方法可以按照指定的正则表达式分割字符串

```java
String[] lines = line.split(" ");
```

### 2.2.4 字符编码方式



## 2.3 读写二进制数据



## 2.4 对象输入输出流与序列化

### 2.4.1 保存和加载序列化对象

为了保存对象数据，首先需要打开一个`ObjectOutputStream`对象

```java
ObjectOutputStream out = ObjectOutputStream(new FileOutputStream("employee.dat"));
```

现在为了保存对象，可以直接使用`ObjectInputStream`的`writeObject`方法

```java
Employee harry = new Employee("Ligen",5000);
out.writeObject(harry);
out.close();
```

为了将这些对象读回，首先需要获得`ObjectInputStream`对象：

```java
ObjectInputStream in = new ObjectInputStream(new FileInputStream("data.txt"));
```

然后用`readObject`方法以这些对象被写出时的顺序获得它们：

```java
Employee e1 = (Employee) in.readObject();
```

如果想要正确的输入输出，必须使这个类实现`Serializable`接口

```java
class Employee implements Serializable{
    String name;
    int salary;

    public Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
```





## 2.5 操作文件

### 2.5.1 Path

绝对路径和相对路径

```java
Path absolute = Paths.get("c:\\home","harry");
Path relative = Paths.get("home","harry","data.txt");
```

静态的`Paths.get`方法可以接受一个或者多个字符串，并将它们用默认文件系统的路径分隔符连接起来。然后它解析连接起来的结果。如果路径不合法，将会抛出`InvalidPathException`异常

组合解析路径

调用`p.solve(q)`将按照下面规则解析：

- 如果`q`是绝对路径，则结果就是`q`
- 否则，根据文件系统规则，将`p+q`作为结果,其中`p`只保留盘符



### 2.5.2 读写文件

`Files`类可以使得普通文件操作变得快捷

```java
//从确定路径读取文件操作
byte[] buffer = Files.readAllBytes(path);
String content = new String(buffer,"UTF8");
```

如果需要输出文件内容

```java
String newContent = "java world";
Files.write(p,newContent.getBytes(StandardCharsets.UTF_8));
```

如果不想覆盖输出，只是追加的话

```java
//在后面添加StandardOpenOption.APPEND参数
Files.write(p,newContent.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);
```

以上方法适用于中等长度文件，如果是大文件或者二进制文件，还是适合使用输入输出流



### 2.5.3 创建文件和目录



## 2.7 正则表达式

[正则表达式](.\正则表达式)



# 第五章 数据库编程

### 5.3.1 数据库URL

在连接数据库时，我们需要使用各种与数据库相关的参数，例如：主机名、端口号和数据库名。

```java
jdbc:derby://localhost:8080/COREJAVA;create=true
jdbc:postgresql:COREJAVA
//derby是数据库类型，localhost:8080是地址和端口,COREJAVA是数据库的名称
```

### 5.3.4 注册驱动器类

通过使用DriverManager，可以用两种方式来注册驱动器。

第一种，在java程序中加载驱动器类

```java
Class.forName("org.postgresql.Driver");
//这条语句将使得驱动器类被加载，由此将执行可以注册驱动器的静态初始化器
```

第二种，设置jdbc.drivers属性。可以用命令行参数来指定这个属性

```
jdbc -Djdbc.drivers=org.postgresql.Driver ProgramName
```

或者在应用中用下面这样的调用来设置系统属性

```java
System.setProperty("jdbc.drivers","org.postgresql.Driver");
```

当有多个驱动器时，用冒号分割

```
org.postgresql.Driver:org.apache.derby.jdbc.ClientDriver
```

### 5.3.5 连接到数据库

用代码直接打开数据库

```java
package cn.edu.xidian;

import java.sql.*;

public class TestJDBC {


    // MySQL 8.0 以下版本 - JDBC 驱动名及数据库 URL
//    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//    static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB";

    // MySQL 8.0 以上版本 - JDBC 驱动名及数据库 URL
    //static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";  
    //static final String DB_URL = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=GMT";
    
    //注册驱动器，数据库地址
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/servlet?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT";

    //数据库用户名密码
    static final String USERNAME = "root";
    static final String PASSWORD = "150621";

    public static void main(String[] args)
    {
        Connection conn = null;
        Statement stmt = null;

        try{
            //注册JDBC驱动
            Class.forName(JDBC_DRIVER);

            //打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);

            //执行查询
            System.out.println("实例化statement对象...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT cust_id,cust_name FROM customers;";
            ResultSet rs = stmt.executeQuery(sql);


            //展开结果集数据库
            while(rs.next())
            {
                //通过字段检索
                int id = rs.getInt("cust_id");
                String name = rs.getString("cust_name");

                //输出数据
                System.out.print("ID: "+id);
                System.out.print(", 站点名称："+name);
                System.out.print("\n");

            }
            //完成后关闭
            rs.close();
            stmt.close();
            conn.close();
        }catch(SQLException se){
            //处理JDBC错误
            se.printStackTrace();
        }catch(Exception e){
            //处理Class.forName错误
            e.printStackTrace();
        }finally{
            //关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){

            }
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("GoodBye!");
    }
}

```

### 5.4.1 执行SQL语句

在执行SQL语句之前，首先需要创建一个Statement对象。这个对象需要调用DriverManager.getConnection方法所获得的Connection对象

```java
Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
Statement stmt = conn.createStatement();
```

接着把SQL语句放入一个字符串中

```java
String sql;
sql = "SELECT cust_id,cust_name FROM customers;";
```

再调用Statement的方法

```java
//执行查询操作
ResetSet rs = stmt.executeQuery(sql);
while(rs.next())
{
    //遍历查询结果
}
//执行更新操作
stmt.executeUpdate(sql);
```

### 5.4.2 管理连接、语句和结果集

每个Connection都可以创建一个或多个Statement对象，同一个Statement对象可以用于多个不相关的命令和查询。但是，一个Statement对象最多只能有一个打开的结果集。如果需要执行多个查询操作，且需要同时分析查询结果，那么必须创建多个Statement对象。使用完这些对象之后就需要调用close方法关闭这些对象。



