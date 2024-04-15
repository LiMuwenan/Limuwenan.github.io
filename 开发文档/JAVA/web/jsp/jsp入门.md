# 1 语法

## 1.1 脚本程序

脚本程序可以包含任意量的Java语句、变量、方法或表达式，只要它们在脚本语言中是有效的。

脚本程序的语法格式：

```jsp
<% 代码片段 %>
```

或者，您也可以编写与其等价的XML语句，就像下面这样：

```jsp
<jsp:scriptlet>
   代码片段
</jsp:scriptlet>
```

任何文本、HTML标签、JSP元素必须写在脚本程序的外面。

下面给出一个示例，同时也是本教程的第一个JSP示例：

```jsp
<html>
<head><title>Hello World</title></head>
<body>
Hello World!<br/>
<%
out.println("Your IP address is " + request.getRemoteAddr());
%>
</body>
</html>
```

## 1.2 中文编码问题

如果我们要在页面正常显示中文，我们需要在 JSP 文件头部添加以下代码：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
```

接下来我们将以上程序修改为：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
				    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>
Hello World!<br/>
<%
out.println("你的 IP 地址 " + request.getRemoteAddr());
%>
</body>
</html>
```

这样中文就可以正常显示了。

## 1.3 JSP声明

一个声明语句可以声明一个或多个变量、方法，供后面的Java代码使用。在JSP文件中，您必须先声明这些变量和方法然后才能使用它们。

JSP声明的语法格式：

```jsp
<%! declaration; [ declaration; ]+ ... %>
```

或者，您也可以编写与其等价的XML语句，就像下面这样：

```jsp
<jsp:declaration>
   代码片段
</jsp:declaration>
```

程序示例：

```jsp
<% int i = 0; %> 
<% int a, b, c; %> 
<% Circle a = new Circle(2.0); %> 
```

## 1.4 JSP表达式

一个JSP表达式中包含的脚本语言表达式，先被转化成String，然后插入到表达式出现的地方。

由于表达式的值会被转化成String，所以您可以在一个文本行中使用表达式而不用去管它是否是HTML标签。

表达式元素中可以包含任何符合Java语言规范的表达式，但是不能使用分号来结束表达式。

JSP表达式的语法格式：

```jsp
<%= 表达式 %>
```

同样，您也可以编写与之等价的XML语句：

```jsp
<jsp:expression>
   表达式
</jsp:expression>
```

程序示例：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>
<p>
   今天的日期是: <%= (new java.util.Date()).toLocaleString()%>
</p>
</body> 
</html> 
```

运行后得到以下结果：

```jsp
今天的日期是: 2016-6-25 13:40:07
```

## 1.5 JSP注释

JSP注释主要有两个作用：为代码作注释以及将某段代码注释掉。

JSP注释的语法格式：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>
<%-- 该部分注释在网页中不会被显示--%> 
<p>
   今天的日期是: <%= (new java.util.Date()).toLocaleString()%>
</p>
</body> 
</html> 
```

运行后得到以下结果：

```
今天的日期是: 2016-6-25 13:41:26
```

| **语法**       | 描述                                                 |
| :------------- | :--------------------------------------------------- |
| <%-- 注释 --%> | JSP注释，注释内容不会被发送至浏览器甚至不会被编译    |
| \<!-- 注释 --> | HTML注释，通过浏览器查看网页源代码时可以看见注释内容 |
| <\%            | 代表静态 <%常量                                      |
| %\>            | 代表静态 %> 常量                                     |
| \'             | 在属性中使用的单引号                                 |
| \"             | 在属性中使用的双引号                                 |

## 1.6 JSP指令

[指令详细介绍](# 2 指令)

JSP指令用来设置与整个JSP页面相关的属性。

JSP指令语法格式：

```jsp
<%@ directive attribute="value" %>
```

这里有三种指令标签：

| **指令**           | **描述**                                                  |
| :----------------- | :-------------------------------------------------------- |
| <%@ page ... %>    | 定义页面的依赖属性，比如脚本语言、error页面、缓存需求等等 |
| <%@ include ... %> | 包含其他文件                                              |
| <%@ taglib ... %>  | 引入标签库的定义，可以是自定义标签                        |



## 1.7 JSP行为

JSP行为标签使用XML语法结构来控制servlet引擎。它能够动态插入一个文件，重用JavaBean组件，引导用户去另一个页面，为Java插件产生相关的HTML等等。

行为标签只有一种语法格式，它严格遵守XML标准：

```jsp
<jsp:action_name attribute="value" />
```

行为标签基本上是一些预先就定义好的函数，下表罗列出了一些可用的JSP行为标签：：

| **语法**        | **描述**                                                   |
| :-------------- | :--------------------------------------------------------- |
| jsp:include     | 用于在当前页面中包含静态或动态资源                         |
| jsp:useBean     | 寻找和初始化一个JavaBean组件                               |
| jsp:setProperty | 设置 JavaBean组件的值                                      |
| jsp:getProperty | 将 JavaBean组件的值插入到 output中                         |
| jsp:forward     | 从一个JSP文件向另一个文件传递一个包含用户请求的request对象 |
| jsp:plugin      | 用于在生成的HTML页面中包含Applet和JavaBean对象             |
| jsp:element     | 动态创建一个XML元素                                        |
| jsp:attribute   | 定义动态创建的XML元素的属性                                |
| jsp:body        | 定义动态创建的XML元素的主体                                |
| jsp:text        | 用于封装模板数据                                           |



## 1.8 JSP隐含对象

JSP支持九个自动定义的变量，江湖人称隐含对象。这九个隐含对象的简介见下表：

| **对象**    | **描述**                                                     |
| :---------- | :----------------------------------------------------------- |
| request     | **HttpServletRequest**类的实例                               |
| response    | **HttpServletResponse**类的实例                              |
| out         | **PrintWriter**类的实例，用于把结果输出至网页上              |
| session     | **HttpSession**类的实例                                      |
| application | **ServletContext**类的实例，与应用上下文有关                 |
| config      | **ServletConfig**类的实例                                    |
| pageContext | **PageContext**类的实例，提供对JSP页面所有对象以及命名空间的访问 |
| page        | 类似于Java类中的this关键字                                   |
| exception   | **exception** 类的对象，代表发生错误的 JSP 页面中对应的异常对象 |



## 1.9 控制流语句

JSP提供对Java语言的全面支持。您可以在JSP程序中使用Java API甚至建立Java代码块，包括判断语句和循环语句等等。

### 1.9.1 判断语句

**if…else** 块，请看下面这个例子：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! int day = 3; %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>
<h3>IF...ELSE 实例</h3>
<% if (day == 1 || day == 7) { %>
      <p>今天是周末</p>
<% } else { %>
      <p>今天不是周末</p>
<% } %>
</body> 
</html> 
```

运行后得到以下结果：

```
IF...ELSE 实例
今天不是周末
```

现在来看看switch…case块，与if…else块有很大的不同，它使用out.println()，并且整个都装在脚本程序的标签中，就像下面这样：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! int day = 3; %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>
<h3>SWITCH...CASE 实例</h3>
<% 
switch(day) {
case 0:
   out.println("星期天");
   break;
case 1:
   out.println("星期一");
   break;
case 2:
   out.println("星期二");
   break;
case 3:
   out.println("星期三");
   break;
case 4:
   out.println("星期四");
   break;
case 5:
   out.println("星期五");
   break;
default:
   out.println("星期六");
}
%>
</body> 
</html> 
```

浏览器访问，运行后得出以下结果：

```
SWITCH...CASE 实例

星期三
```



### 1.9.2 循环语句

在JSP程序中可以使用Java的三个基本循环类型：for，while，和 do…while。

让我们来看看for循环的例子，以下输出的不同字体大小的"菜鸟教程"：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%! int fontSize; %> 
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>
<h3>For 循环实例</h3>
<%for ( fontSize = 1; fontSize <= 3; fontSize++){ %>
   <font color="green" size="<%= fontSize %>">
    菜鸟教程
   </font><br />
<%}%>
</body> 
</html> 
```

## 1.10 路径问题

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



# 2 指令

JSP指令用来设置整个JSP页面相关的属性，如网页的编码方式和脚本语言。

语法格式如下：

```jsp
<%@ directive attribute="value" %>
```

指令可以有很多个属性，它们以键值对的形式存在，并用逗号隔开。

JSP中的三种指令标签：

| **指令**           | **描述**                                                |
| :----------------- | :------------------------------------------------------ |
| <%@ page ... %>    | 定义网页依赖属性，比如脚本语言、error页面、缓存需求等等 |
| <%@ include ... %> | 包含其他文件                                            |
| <%@ taglib ... %>  | 引入标签库的定义                                        |



## 2.1 Page指令

Page指令为容器提供当前页面的使用说明。一个JSP页面可以包含多个page指令。

Page指令的语法格式：

```jsp
<%@ page attribute="value" %>
```

等价的XML格式：

```xml
<jsp:directive.page attribute="value" />
```



## 2.2 属性

下表列出与Page指令相关的属性：

| **属性**           | **描述**                                            |
| :----------------- | :-------------------------------------------------- |
| buffer             | 指定out对象使用缓冲区的大小                         |
| autoFlush          | 控制out对象的 缓存区                                |
| contentType        | 指定当前JSP页面的MIME类型和字符编码                 |
| errorPage          | 指定当JSP页面发生异常时需要转向的错误处理页面       |
| isErrorPage        | 指定当前页面是否可以作为另一个JSP页面的错误处理页面 |
| extends            | 指定servlet从哪一个类继承                           |
| import             | 导入要使用的Java类                                  |
| info               | 定义JSP页面的描述信息                               |
| isThreadSafe       | 指定对JSP页面的访问是否为线程安全                   |
| language           | 定义JSP页面所用的脚本语言，默认是Java               |
| session            | 指定JSP页面是否使用session                          |
| isELIgnored        | 指定是否执行EL表达式                                |
| isScriptingEnabled | 确定脚本元素能否被使用                              |



## 2.3 Include指令

JSP可以通过include指令来包含其他文件。被包含的文件可以是JSP文件、HTML文件或文本文件。包含的文件就好像是该JSP文件的一部分，会被同时编译执行。

Include指令的语法格式如下：

```jsp
<%@ include file="文件相对 url 地址" %>
```

**include** 指令中的文件名实际上是一个相对的 URL 地址。

如果您没有给文件关联一个路径，JSP编译器默认在当前路径下寻找。

等价的XML语法：

```jsp
<jsp:directive.include file="文件相对 url 地址" />
```



## 2.4 Taglib指令

JSP API允许用户自定义标签，一个自定义标签库就是自定义标签的集合。

Taglib指令引入一个自定义标签集合的定义，包括库路径、自定义标签。

Taglib指令的语法：

```jsp
<%@ taglib uri="uri" prefix="prefixOfTag" %>
```

uri属性确定标签库的位置，prefix属性指定标签库的前缀。

等价的XML语法：

```jsp
<jsp:directive.taglib uri="uri" prefix="prefixOfTag" />
```

# 3 动作元素

与JSP指令元素不同的是，JSP动作元素在请求处理阶段起作用。JSP动作元素是用XML语法写成的。

利用JSP动作可以动态地插入文件、重用JavaBean组件、把用户重定向到另外的页面、为Java插件生成HTML代码。

动作元素只有一种语法，它符合XML标准：

```jsp
<jsp:action_name attribute="value" />
```

动作元素基本上都是预定义的函数，JSP规范定义了一系列的标准动作，它用JSP作为前缀，可用的标准动作元素如下：

| 语法            | 描述                                            |
| :-------------- | :---------------------------------------------- |
| jsp:include     | 在页面被请求的时候引入一个文件。                |
| jsp:useBean     | 寻找或者实例化一个JavaBean。                    |
| jsp:setProperty | 设置JavaBean的属性。                            |
| jsp:getProperty | 输出某个JavaBean的属性。                        |
| jsp:forward     | 把请求转到一个新的页面。                        |
| jsp:plugin      | 根据浏览器类型为Java插件生成OBJECT或EMBED标记。 |
| jsp:element     | 定义动态XML元素                                 |
| jsp:attribute   | 设置动态定义的XML元素属性。                     |
| jsp:body        | 设置动态定义的XML元素内容。                     |
| jsp:text        | 在JSP页面和文档中使用写入文本的模板             |



## 3.1 常见的属性

所有的动作要素都有两个属性：id属性和scope属性。

- id属性：

  id属性是动作元素的唯一标识，可以在JSP页面中引用。动作元素创建的id值可以通过PageContext来调用。

  

- scope属性：

  该属性用于识别动作元素的生命周期。 id属性和scope属性有直接关系，scope属性定义了相关联id对象的寿命。 scope属性有四个可能的值： (a) page, (b)request, (c)session, 和 (d) application。

  



## 3.2 \<jsp:include>动作元素

<jsp:include>动作元素用来包含静态和动态的文件。该动作把指定文件插入正在生成的页面。语法格式如下：

```jsp
<jsp:include page="相对 URL 地址" flush="true" />
```

　前面已经介绍过include指令，它是在JSP文件被转换成Servlet的时候引入文件，而这里的jsp:include动作不同，插入文件的时间是在页面被请求的时候。

以下是include动作相关的属性列表。

| 属性  | 描述                                       |
| :---- | :----------------------------------------- |
| page  | 包含在页面中的相对URL地址。                |
| flush | 布尔属性，定义在包含资源前是否刷新缓存区。 |

### 实例

以下我们定义了两个文件 **date.jsp** 和 **main.jsp**，代码如下所示：

date.jsp文件代码：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<p>
   今天的日期是: <%= (new java.util.Date()).toLocaleString()%>
</p>
```

main.jsp文件代码：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>

<h2>include 动作实例</h2>
<jsp:include page="date.jsp" flush="true" />

</body>
</html>
```

现在将以上两个文件放在服务器的根目录下，访问main.jsp文件。显示结果如下：

```
include 动作实例

今天的日期是: 2016-6-25 14:08:17
```



## 3.2 \<jsp:useBean>动作元素

**jsp:useBean** 动作用来加载一个将在JSP页面中使用的JavaBean。

这个功能非常有用，因为它使得我们可以发挥 Java 组件复用的优势。

jsp:useBean动作最简单的语法为：

```jsp
<jsp:useBean id="name" class="package.class" />
```

在类载入后，我们既可以通过 jsp:setProperty 和 jsp:getProperty 动作来修改和检索bean的属性。

以下是useBean动作相关的属性列表。

| 属性     | 描述                                                        |
| :------- | :---------------------------------------------------------- |
| class    | 指定Bean的完整包名。                                        |
| type     | 指定将引用该对象变量的类型。                                |
| beanName | 通过 java.beans.Beans 的 instantiate() 方法指定Bean的名字。 |

在给出具体实例前，让我们先来看下 jsp:setProperty 和 jsp:getProperty 动作元素：



## 3.3 \<jsp:setProperty>动作元素

jsp:setProperty用来设置已经实例化的Bean对象的属性，有两种用法。首先，你可以在jsp:useBean元素的外面（后面）使用jsp:setProperty，如下所示：

```jsp
<jsp:useBean id="myName" ... />
...
<jsp:setProperty name="myName" property="someProperty" .../>
```

此时，不管jsp:useBean是找到了一个现有的Bean，还是新创建了一个Bean实例，jsp:setProperty都会执行。第二种用法是把jsp:setProperty放入jsp:useBean元素的内部，如下所示：

```jsp
<jsp:useBean id="myName" ... >
...
   <jsp:setProperty name="myName" property="someProperty" .../>
</jsp:useBean>
```

此时，jsp:setProperty只有在新建Bean实例时才会执行，如果是使用现有实例则不执行jsp:setProperty。

jsp:setProperty动作有下面四个属性,如下表：

| 属性     | 描述                                                         |
| :------- | :----------------------------------------------------------- |
| name     | name属性是必需的。它表示要设置属性的是哪个Bean。             |
| property | property属性是必需的。它表示要设置哪个属性。有一个特殊用法：如果property的值是"*"，表示所有名字和Bean属性名字匹配的请求参数都将被传递给相应的属性set方法。 |
| value    | value 属性是可选的。该属性用来指定Bean属性的值。字符串数据会在目标类中通过标准的valueOf方法自动转换成数字、boolean、Boolean、 byte、Byte、char、Character。例如，boolean和Boolean类型的属性值（比如"true"）通过 Boolean.valueOf转换，int和Integer类型的属性值（比如"42"）通过Integer.valueOf转换。 　　value和param不能同时使用，但可以使用其中任意一个。 |
| param    | param 是可选的。它指定用哪个请求参数作为Bean属性的值。如果当前请求没有参数，则什么事情也不做，系统不会把null传递给Bean属性的set方法。因此，你可以让Bean自己提供默认属性值，只有当请求参数明确指定了新值时才修改默认属性值。 |



## 3.4 \<jsp:getProperty>动作元素

jsp:getProperty动作提取指定Bean属性的值，转换成字符串，然后输出。语法格式如下：

```jsp
<jsp:useBean id="myName" ... />
...
<jsp:getProperty name="myName" property="someProperty" .../>
```

下表是与getProperty相关联的属性：

| 属性     | 描述                                   |
| :------- | :------------------------------------- |
| name     | 要检索的Bean属性名称。Bean必须已定义。 |
| property | 表示要提取Bean属性的值                 |

### 实例

以下实例我们使用了Bean:

```jsp
package com.runoob.main;

public class TestBean {
   private String message = "菜鸟教程";
 
   public String getMessage() {
      return(message);
   }
   public void setMessage(String message) {
      this.message = message;
   }
}
```

编译以上实例文件 TestBean.java ：

```
$ javac TestBean.java
```

编译完成后会在当前目录下生成一个 **TestBean.class** 文件， 将该文件拷贝至当前 JSP 项目的 **WebContent/WEB-INF/classes/com/runoob/main** 下( com/runoob/main 包路径，没有需要手动创建)。

下面是一个很简单的例子，它的功能是装载一个Bean，然后设置/读取它的message属性。



现在让我们在main.jsp文件中调用该Bean:

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>

<h2>Jsp 使用 JavaBean 实例</h2>
<jsp:useBean id="test" class="com.runoob.main.TestBean" />
 
<jsp:setProperty name="test" 
                    property="message" 
                    value="菜鸟教程..." />
 
<p>输出信息....</p>
 
<jsp:getProperty name="test" property="message" />

</body>
</html>
```

## 3.5 \<jsp:forward> 动作元素

　jsp:forward动作把请求转到另外的页面。jsp:forward标记只有一个属性page。语法格式如下所示：

```jsp
<jsp:forward page="相对 URL 地址" />
```

以下是forward相关联的属性：

| 属性 | 描述                                                         |
| :--- | :----------------------------------------------------------- |
| page | page属性包含的是一个相对URL。page的值既可以直接给出，也可以在请求的时候动态计算，可以是一个JSP页面或者一个 Java Servlet. |

### 实例

以下实例我们使用了两个文件，分别是： date.jsp 和 main.jsp。

date.jsp 文件代码如下：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<p>
   今天的日期是: <%= (new java.util.Date()).toLocaleString()%>
</p>
```

main.jsp文件代码：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>

<h2>forward 动作实例</h2>
<jsp:forward page="date.jsp" />
</body>
</html>
```

现在将以上两个文件放在服务器的根目录下，访问main.jsp文件。显示结果如下：

```
今天的日期是: 2016-6-25 14:37:25
```



## 3.6 \<jsp:plugin>动作元素

jsp:plugin动作用来根据浏览器的类型，插入通过Java插件 运行Java Applet所必需的OBJECT或EMBED元素。

如果需要的插件不存在，它会下载插件，然后执行Java组件。 Java组件可以是一个applet或一个JavaBean。

plugin动作有多个对应HTML元素的属性用于格式化Java 组件。param元素可用于向Applet 或 Bean 传递参数。

以下是使用plugin 动作元素的典型实例:

```jsp
<jsp:plugin type="applet" codebase="dirname" code="MyApplet.class"
                           width="60" height="80">
   <jsp:param name="fontcolor" value="red" />
   <jsp:param name="background" value="black" />
 
   <jsp:fallback>
      Unable to initialize Java Plugin
   </jsp:fallback>
 
</jsp:plugin>
```

如果你有兴趣可以尝试使用applet来测试jsp:plugin动作元素，<fallback>元素是一个新元素，在组件出现故障的错误时发送给用户错误信息。



## 3.7 \<jsp:element> 、 \<jsp:attribute>、 \<jsp:body>动作元素

<jsp:element> 、 <jsp:attribute>、 <jsp:body>动作元素动态定义XML元素。动态是非常重要的，这就意味着XML元素在编译时是动态生成的而非静态。

以下实例动态定义了XML元素：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>
<jsp:element name="xmlElement">
<jsp:attribute name="xmlElementAttr">
   属性值
</jsp:attribute>
<jsp:body>
   XML 元素的主体
</jsp:body>
</jsp:element>
</body>
</html>
```

## 3.8 \<jsp:text>动作元素

<jsp:text>动作元素允许在JSP页面和文档中使用写入文本的模板，语法格式如下：

```jsp
<jsp:text>模板数据</jsp:text>
```

以上文本模板不能包含重复元素，只能包含文本和EL表达式（注：EL表达式将在后续章节中介绍）。请注意，在XML文件中，您不能使用表达式如 ${whatever > 0}，因为>符号是非法的。 你可以使用 ${whatever gt 0}表达式或者嵌入在一个CDATA部分的值。

```jsp
<jsp:text><![CDATA[<br>]]></jsp:text>
```

如果你需要在 XHTML 中声明 DOCTYPE,必须使用到<jsp:text>动作元素，实例如下：

```jsp
<jsp:text><![CDATA[<!DOCTYPE html
PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"DTD/xhtml1-strict.dtd">]]>
</jsp:text>
<head><title>jsp:text action</title></head>
<body>

<books><book><jsp:text>  
    Welcome to JSP Programming
</jsp:text></book></books>

</body>
</html>
```

# 4 隐式对象

JSP隐式对象是JSP容器为每个页面提供的Java对象，开发者可以直接使用它们而不用显式声明。JSP隐式对象也被称为预定义变量。

JSP所支持的九大隐式对象：

| **对象**    | **描述**                                                     |
| :---------- | :----------------------------------------------------------- |
| request     | **HttpServletRequest** 接口的实例                            |
| response    | **HttpServletResponse** 接口的实例                           |
| out         | **JspWriter**类的实例，用于把结果输出至网页上                |
| session     | **HttpSession**类的实例                                      |
| application | **ServletContext**类的实例，与应用上下文有关                 |
| config      | **ServletConfig**类的实例                                    |
| pageContext | **PageContext**类的实例，提供对JSP页面所有对象以及命名空间的访问 |
| page        | 类似于Java类中的this关键字                                   |
| Exception   | **Exception**类的对象，代表发生错误的JSP页面中对应的异常对象 |



## 4.1 request对象

request对象是javax.servlet.http.HttpServletRequest 类的实例。每当客户端请求一个JSP页面时，JSP引擎就会制造一个新的request对象来代表这个请求。

request对象提供了一系列方法来获取HTTP头信息，cookies，HTTP方法等等。



## 4.2 response对象

response对象是javax.servlet.http.HttpServletResponse类的实例。当服务器创建request对象时会同时创建用于响应这个客户端的response对象。

response对象也定义了处理HTTP头模块的接口。通过这个对象，开发者们可以添加新的cookies，时间戳，HTTP状态码等等。



## 4.3 out对象

out对象是 javax.servlet.jsp.JspWriter 类的实例，用来在response对象中写入内容。

最初的JspWriter类对象根据页面是否有缓存来进行不同的实例化操作。可以在page指令中使用buffered='false'属性来轻松关闭缓存。

JspWriter类包含了大部分java.io.PrintWriter类中的方法。不过，JspWriter新增了一些专为处理缓存而设计的方法。还有就是，JspWriter类会抛出IOExceptions异常，而PrintWriter不会。

下表列出了我们将会用来输出boolean，char，int，double，String，object等类型数据的重要方法：

| **方法**                     | **描述**                 |
| :--------------------------- | :----------------------- |
| **out.print(dataType dt)**   | 输出Type类型的值         |
| **out.println(dataType dt)** | 输出Type类型的值然后换行 |
| **out.flush()**              | 刷新输出流               |



## 4.4 session对象

session对象是 javax.servlet.http.HttpSession 类的实例。和Java Servlets中的session对象有一样的行为。

session对象用来跟踪在各个客户端请求间的会话。



## 4.5 application对象

application对象直接包装了servlet的ServletContext类的对象，是javax.servlet.ServletContext 类的实例。

这个对象在JSP页面的整个生命周期中都代表着这个JSP页面。这个对象在JSP页面初始化时被创建，随着jspDestroy()方法的调用而被移除。

通过向application中添加属性，则所有组成您web应用的JSP文件都能访问到这些属性。



## 4.6 config对象

config对象是 javax.servlet.ServletConfig 类的实例，直接包装了servlet的ServletConfig类的对象。

这个对象允许开发者访问Servlet或者JSP引擎的初始化参数，比如文件路径等。

以下是config对象的使用方法，不是很重要，所以不常用：

```jsp
config.getServletName();
```

它返回包含在<servlet-name>元素中的servlet名字，注意，<servlet-name>元素在 WEB-INF\web.xml 文件中定义。

## 4.7 pageContext 对象

pageContext对象是javax.servlet.jsp.PageContext 类的实例，用来代表整个JSP页面。

这个对象主要用来访问页面信息，同时过滤掉大部分实现细节。

这个对象存储了request对象和response对象的引用。application对象，config对象，session对象，out对象可以通过访问这个对象的属性来导出。

pageContext对象也包含了传给JSP页面的指令信息，包括缓存信息，ErrorPage URL,页面scope等。

PageContext类定义了一些字段，包括PAGE_SCOPE，REQUEST_SCOPE，SESSION_SCOPE， APPLICATION_SCOPE。它也提供了40余种方法，有一半继承自javax.servlet.jsp.JspContext 类。

其中一个重要的方法就是 removeAttribute()，它可接受一个或两个参数。比如，pageContext.removeAttribute("attrName") 移除四个scope中相关属性，但是下面这种方法只移除特定 scope 中的相关属性：

```jsp
pageContext.removeAttribute("attrName", PAGE_SCOPE);
```

## 4.8 page 对象

这个对象就是页面实例的引用。它可以被看做是整个JSP页面的代表。

page 对象就是this对象的同义词。

## 4.9 exception 对象

exception 对象包装了从先前页面中抛出的异常信息。它通常被用来产生对出错条件的适当响应。

# 5 客户端请求

# 6 服务器请求

# 7 HTTP状态码

# 8 表单处理

我们在浏览网页的时候，经常需要向服务器提交信息，并让后台程序处理。浏览器中使用 GET 和 POST 方法向服务器提交数据。



## 8.1 GET 方法

GET方法将请求的编码信息添加在网址后面，网址与编码信息通过"?"号分隔。如下所示：

```
http://www.runoob.com/hello?key1=value1&key2=value2
```

GET方法是浏览器默认传递参数的方法，一些敏感信息，如密码等建议不使用GET方法。

用get时，传输数据的大小有限制 （注意不是参数的个数有限制），最大为1024字节。



## 8.2 POST 方法

一些敏感信息，如密码等我们可以通过POST方法传递，POST提交数据是隐式的。

POST提交数据是不可见的，GET是通过在url里面传递的（可以看一下你浏览器的地址栏）。

JSP使用getParameter()来获得传递的参数，getInputStream()方法用来处理客户端的二进制数据流的请求。



## 8.3 JSP 读取表单数据

- **getParameter():** 使用 request.getParameter() 方法来获取表单参数的值。
- **getParameterValues():** 获得如checkbox类（名字相同，但值有多个）的数据。 接收数组变量 ，如checkbox类型
- **getParameterNames():**该方法可以取得所有变量的名称，该方法返回一个 Enumeration。
- **getInputStream():**调用此方法来读取来自客户端的二进制数据流。



### 8.3.1 使用URL的 GET 方法实例

以下是一个简单的URL,并使用GET方法来传递URL中的参数：

```jsp
http://localhost:8080/testjsp/main.jsp?name=菜鸟教程&url=http://ww.runoob.com
```

testjsp 为项目地址。

以下是 main.jsp 文件的JSP程序用于处理客户端提交的表单数据，我们使用getParameter()方法来获取提交的数据：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>
<h1>使用 GET 方法读取数据</h1>
<ul>
<li><p><b>站点名:</b>
   <%= request.getParameter("name")%>
</p></li>
<li><p><b>网址:</b>
   <%= request.getParameter("url")%>
</p></li>
</ul>
</body>
</html>
```

### 8.3.2 使用表单的 GET 方法实例

以下是一个简单的 HTML 表单，该表单通过GET方法将客户端数据提交 到 **main.jsp** 文件中：

```jsp
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>

<form action="main.jsp" method="GET">
站点名: <input type="text" name="name">
<br />
网址: <input type="text" name="url" />
<input type="submit" value="提交" />
</form>

</body>
</html>
```

### 8.3.3 使用表单的 POST 方法实例

接下来让我们使用POST方法来传递表单数据，修改main.jsp与Hello.htm文件代码，如下所示：

main.jsp文件代码：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>
<h1>使用 POST 方法读取数据</h1>
<ul>
<li><p><b>站点名:</b>
<%
// 解决中文乱码的问题
String name = new String((request.getParameter("name")).getBytes("ISO-8859-1"),"UTF-8");
%>
   <%=name%>
</p></li>
<li><p><b>网址:</b>
   <%= request.getParameter("url")%>
</p></li>
</ul>
</body>
</html>
```

代码中我们使用 **new String((request.getParameter("name")).getBytes("ISO-8859-1"),"UTF-8")**来转换编码，防止中文乱码的发生。

以下是test.htm修改后的代码：

```jsp
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>

<form action="main.jsp" method="POST">
站点名: <input type="text" name="name">
<br />
网址: <input type="text" name="url" />
<input type="submit" value="提交" />
</form>

</body>
</html>
```

## 8.4 传递 Checkbox 数据到JSP程序

复选框 checkbox 可以传递一个甚至多个数据。

以下是一个简单的HTML代码，并将代码保存在test.htm文件中：

```jsp
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>

<form action="main.jsp" method="POST" target="_blank">
<input type="checkbox" name="google" checked="checked" /> Google
<input type="checkbox" name="runoob"  /> 菜鸟教程
<input type="checkbox" name="taobao" checked="checked" /> 
                                                淘宝
<input type="submit" value="选择网站" />
</form>

</body>
</html>
```

以上代码在浏览器访问如下所示：

以下为main.jsp文件代码，用于处理复选框数据：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>
<h1>从复选框中读取数据</h1>
<ul>
<li><p><b>Google 是否选中:</b>
   <%= request.getParameter("google")%>
</p></li>
<li><p><b>菜鸟教程是否选中:</b>
   <%= request.getParameter("runoob")%>
</p></li>
<li><p><b>淘宝是否选中:</b>
   <%= request.getParameter("taobao")%>
</p></li>
</ul>
</body>
</html>
```

## 8.5 读取所有表单参数

以下我们将使用 **HttpServletRequest** 的 **getParameterNames()** 来读取所有表单参数,该方法可以取得所有变量的名称，该方法返回一个枚举。

一旦我们有了一个 Enumeration（枚举），我们就可以调用 hasMoreElements() 方法来确定是否还有元素，以及使用nextElement（）方法来获得每个参数的名称。

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>
<h1>读取所有表单参数</h1>
<table width="100%" border="1" align="center">
<tr bgcolor="#949494">
<th>参数名</th><th>参数值</th>
</tr>
<%
   Enumeration paramNames = request.getParameterNames();

   while(paramNames.hasMoreElements()) {
      String paramName = (String)paramNames.nextElement();
      out.print("<tr><td>" + paramName + "</td>\n");
      String paramValue = request.getParameter(paramName);
      out.println("<td> " + paramValue + "</td></tr>\n");
   }
%>
</table>
</body>
</html>
```

以下是test.htm文件的内容:

```jsp
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>

<form action="main.jsp" method="POST" target="_blank">
<input type="checkbox" name="google" checked="checked" /> Google
<input type="checkbox" name="runoob"  /> 菜鸟教程
<input type="checkbox" name="taobao" checked="checked" /> 
                                                淘宝
<input type="submit" value="选择网站" />
</form>

</body>
</html>
```

# 9 过滤器

## 9.1 JSP 过滤器实例

以下是 Servlet 过滤器的实例，将输出网站名称和地址。本实例让您对 Servlet 过滤器有基本的了解，您可以使用相同的概念编写更复杂的过滤器应用程序：

```java
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

DisplayHeader.java 文件代码如下：

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

## 9.2 Web.xml 中的 Servlet 过滤器映射（Servlet Filter Mapping）

定义过滤器，然后映射到一个 URL 或 Servlet，这与定义 Servlet，然后映射到一个 URL 模式方式大致相同。在部署描述符文件 **web.xml** 中为 filter 标签创建下面的条目：

```xml
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
```

上述过滤器适用于所有的 Servlet，因为我们在配置中指定 **/\*** 。如果您只想在少数的 Servlet 上应用过滤器，您可以指定一个特定的 Servlet 路径。

现在试着以常用的方式调用任何 Servlet，您将会看到在 Web 服务器中生成的日志。您也可以使用 Log4J 记录器来把上面的日志记录到一个单独的文件中。

# 10 Cookie处理

Cookie 是存储在客户机的文本文件，它们保存了大量轨迹信息。在 Servlet 技术基础上，JSP 显然能够提供对 HTTP cookie 的支持。

通常有三个步骤来识别回头客：

- 服务器脚本发送一系列 cookie 至浏览器。比如名字，年龄，ID 号码等等。
- 浏览器在本地机中存储这些信息，以备不时之需。
- 当下一次浏览器发送任何请求至服务器时，它会同时将这些 cookie 信息发送给服务器，然后服务器使用这些信息来识别用户或者干些其它事情。

本章节将会传授您如何去设置或重设 cookie 的方法，还有如何访问它们及如何删除它们。

> JSP Cookie 处理需要对中文进行编码与解码，方法如下：
>
> ```
> String   str   =   java.net.URLEncoder.encode("中文", "UTF-8");            //编码
> String   str   =   java.net.URLDecoder.decode("编码后的字符串","UTF-8");   // 解码
> ```

## 10.1 Cookie 剖析

Cookie 通常在 HTTP 信息头中设置（虽然 JavaScript 能够直接在浏览器中设置 cookie）。在 JSP 中，设置一个 cookie 需要发送如下的信息头给服务器：

```
HTTP/1.1 200 OK
Date: Fri, 04 Feb 2015 21:03:38 GMT
Server: Apache/1.3.9 (UNIX) PHP/4.0b3
Set-Cookie: name=runoob; expires=Friday, 04-Feb-17 22:03:38 GMT; 
                 path=/; domain=runoob.com
Connection: close
Content-Type: text/html
```

正如您所见，Set-Cookie 信息头包含一个键值对，一个 GMT（格林尼治标准）时间，一个路径，一个域名。键值对会被编码为URL。有效期域是个指令，告诉浏览器在什么时候之后就可以清除这个 cookie。

如果浏览器被配置成可存储 cookie，那么它将会保存这些信息直到过期。如果用户访问的任何页面匹配了 cookie 中的路径和域名，那么浏览器将会重新将这个 cookie 发回给服务器。浏览器端的信息头长得就像下面这样：

```
GET / HTTP/1.0
Connection: Keep-Alive
User-Agent: Mozilla/4.6 (X11; I; Linux 2.2.6-15apmac ppc)
Host: zink.demon.co.uk:1126
Accept: image/gif, */*
Accept-Encoding: gzip
Accept-Language: en
Accept-Charset: iso-8859-1,*,utf-8
Cookie: name=xyz
```

JSP 脚本通过 request 对象中的 getCookies() 方法来访问这些 cookie，这个方法会返回一个 Cookie 对象的数组。

## 10.2 Servlet Cookie 方法

下表列出了 Cookie 对象中常用的方法：

| **序号** | **方法** **&** **描述**                                      |
| :------- | :----------------------------------------------------------- |
| 1        | **public void setDomain(String pattern)**设置 cookie 的域名，比如 runoob.com |
| 2        | **public String getDomain()**获取 cookie 的域名，比如 runoob.com |
| 3        | **public void setMaxAge(int expiry)**设置 cookie 有效期，以秒为单位，默认有效期为当前session的存活时间 |
| 4        | **public int getMaxAge()**获取 cookie 有效期，以秒为单位，默认为-1 ，表明cookie会活到浏览器关闭为止 |
| 5        | **public String getName()**返回 cookie 的名称，名称创建后将不能被修改 |
| 6        | **public void setValue(String newValue)**设置 cookie 的值    |
| 7        | **public String getValue()**获取cookie的值                   |
| 8        | **public void setPath(String uri)**设置 cookie 的路径，默认为当前页面目录下的所有 URL，还有此目录下的所有子目录 |
| 9        | **public String getPath()**获取 cookie 的路径                |
| 10       | **public void setSecure(boolean flag)**指明 cookie 是否要加密传输 |
| 11       | **public void setComment(String purpose)**设置注释描述 cookie 的目的。当浏览器将 cookie 展现给用户时，注释将会变得非常有用 |
| 12       | **public String getComment()**返回描述 cookie 目的的注释，若没有则返回 null |

## 10.3 使用 JSP 设置 cookie

使用 JSP 设置 cookie 包含三个步骤：

**(1)创建一个 cookie 对象：** 调用 cookie 的构造函数，使用一个 cookie 名称和值做参数，它们都是字符串。

```
Cookie cookie = new Cookie("key","value");
```

请务必牢记，名称和值中都不能包含空格或者如下的字符：

```
[ ] ( ) = , " / ? @ : ;
```

**(2) 设置有效期：**调用 setMaxAge() 函数表明 cookie 在多长时间（以秒为单位）内有效。下面的操作将有效期设为了 24 小时。

```
cookie.setMaxAge(60*60*24); 
```

**(3) 将 cookie 发送至 HTTP 响应头中：**调用 response.addCookie() 函数来向 HTTP 响应头中添加 cookie。

```
response.addCookie(cookie);
```

------

### 10.3.1 实例演示

main.jsp 文件代码如下所示：

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%
   // 编码，解决中文乱码   
   String str = URLEncoder.encode(request.getParameter("name"),"utf-8");  
   // 设置 name 和 url cookie 
   Cookie name = new Cookie("name",
           str);
   Cookie url = new Cookie("url",
              request.getParameter("url"));

   // 设置cookie过期时间为24小时。
   name.setMaxAge(60*60*24); 
   url.setMaxAge(60*60*24); 

   // 在响应头部添加cookie
   response.addCookie( name );
   response.addCookie( url );
%>
<html>
<head>
<title>设置 Cookie</title>
</head>
<body>

<h1>设置 Cookie</h1>

<ul>
<li><p><b>网站名:</b>
   <%= request.getParameter("name")%>
</p></li>
<li><p><b>网址:</b>
   <%= request.getParameter("url")%>
</p></li>
</ul>
</body>
</html>
```

以下是一个简单的 HTML 表单通过 GET 方法将客户端数据提交到 main.jsp 文件中，并设置 cookie：

```jsp
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>菜鸟教程(runoob.com)</title>
</head>
<body>

<form action="main.jsp" method=GET>
站点名: <input type="text" name="name">
<br />
网址: <input type="text" name="url" />
<input type="submit" value="提交" />
</form>

</body>
</html>
```

将以上 HTML 代码保存到 test.htm 文件中。

将该文件放置于当前 jsp 项目的 WebContent 目录下（与 main.jsp 同一个目录）。

## 10.4 使用 JSP 读取 Cookie

想要读取 cookie，您就需要调用 request.getCookies() 方法来获得一个 javax.servlet.http.Cookie 对象的数组，然后遍历这个数组，使用 getName() 方法和 getValue() 方法来获取每一个 cookie 的名称和值。

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>获取 Cookie</title>
</head>
<body>
<%
   Cookie cookie = null;
   Cookie[] cookies = null;
   // 获取 cookies 的数据,是一个数组
   cookies = request.getCookies();
   if( cookies != null ){
      out.println("<h2> 查找 Cookie 名与值</h2>");
      for (int i = 0; i < cookies.length; i++){
         cookie = cookies[i];
        
         out.print("参数名 : " + cookie.getName());
         out.print("<br>");
         out.print("参数值: " + URLDecoder.decode(cookie.getValue(), "utf-8") +" <br>");
         out.print("------------------------------------<br>");
      }
  }else{
      out.println("<h2>没有发现 Cookie</h2>");
  }
%>
</body>
</html>
```



## 10.5 使用 JSP 删除 cookie

删除 cookie 非常简单。如果您想要删除一个 cookie，按照下面给的步骤来做就行了：

- 获取一个已经存在的 cookie 然后存储在 Cookie 对象中。
- 将 cookie 的有效期设置为 0。
- 将这个 cookie 重新添加进响应头中。


### 10.5.1 实例演示

下面的程序删除一个名为 "name" 的 cookie，当您第二次运行 cookie.jsp时，name 将会为 null。

```jsp
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>获取 Cookie</title>
</head>
<body>
<%
   Cookie cookie = null;
   Cookie[] cookies = null;
   // 获取当前域名下的cookies，是一个数组
   cookies = request.getCookies();
   if( cookies != null ){
      out.println("<h2> 查找 Cookie 名与值</h2>");
      for (int i = 0; i < cookies.length; i++){
         cookie = cookies[i];
         if((cookie.getName( )).compareTo("name") == 0 ){
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            out.print("删除 Cookie: " + 
            cookie.getName( ) + "<br/>");
         }
         out.print("参数名 : " + cookie.getName());
         out.print("<br>");
         out.print("参数值: " + URLDecoder.decode(cookie.getValue(), "utf-8") +" <br>");
         out.print("------------------------------------<br>");
      }
  }else{
      out.println("<h2>没有发现 Cookie</h2>");
  }
%>
</body>
</html>
```



# 11 Session



# 12 文件上传



# 13 页面重定向



# 14 标准标签库



# 15 连接数据库



# 16 XML数据处理



# 17 JavaBean



# 18 自定义标签



# 19 表达是语言



# 20 异常处理



# 21 调试



# 22 帐号存在验证



使用`ajax`技术

前端页面代码

```jsp
<!--将文本传回后端进行验证，后端在数据库中搜索-->
<script>
    document.getElementById("username").addEventListener("blur",function(){//blur是焦点离开事件
        //发送一个请求，通过请求结果判定是否已经存在
        var username = document.getElementById("username").value;
        var xhr = new XMLHttpRequest();

        xhr.open("get","/shop/checkuser.do?username="+username);//发送get请求检测用户名是否已经存在
        xhr.send(null);
        xhr.onreadystatechange = function(){
            if(this.readyState===4){
                console.log(this.responseText);
                if("用户已经存在" === this.responseText)
                    document.getElementById("msg").innerText=this.responseText;
                else if("用户名可用" === this.responseText)
                    document.getElementById("msg").innerText=this.responseText;
            }
        }
    })
</script>
```

```java
//这个会被前端this.responseText捕获到，直接进行判断
if(is){
    out.write("用户已经存在");
}else{
    out.write("用户名可用");
}
```

进行调整

```jsp
<script src="static/js/ajax.js"></script>
<script>
    document.getElementById("username").addEventListener("blur",function(){//blur是焦点离开事件
        var url  = "/shop/checkuser.do?username="+document.getElementById("username").value;
        $.get(url,function (data){
            document.getElementById("msg").innerText= data;
        })
    })
</script>
```

```js
var $ = {
    get:function(url,fn){
        //发送一个请求，通过请求结果判定是否已经存在
        var xhr = new XMLHttpRequest();

        xhr.open("get",url);//发送get请求检测用户名是否已经存在
        xhr.send(null);
        xhr.onreadystatechange = function(){
            if(this.readyState===4){
                console.log(this.responseText);
                fn(this.responseText);
            }
        }
    },
    post:function(url,data,fn){
        //发送一个请求，通过请求结果判定是否已经存在
        var xhr = new XMLHttpRequest();

        xhr.open("get",url);//发送get请求检测用户名是否已经存在
        xhr.send(data);
        xhr.onreadystatechange = function(){
            if(this.readyState===4){
                console.log(this.responseText);
                fn(this.responseText);
            }
        }

    }
}
```

## 22.1 表单提交验证

如果在帐号存在情况下或者不符合规则情况下禁止提交

```jsp
<form method="post" action="member/member.do" onsubmit="return submitForm()">
    用户名：<input type="text" name="username" id="username"><span style="color: greenyellow" id="msg"></span><br>
    密码：<input type="password" name="password"><br>
    性别：男<input type="radio" name="gender" value="1">
    女<input type="radio" name="gender" value="0"><br>
    <input type="submit" name="submit"><br>
</form>
<script src="static/js/ajax.js"></script>
<script>
    //正则校验正好
    function submitForm(){
        //测试用户名
        if(/^[0-9A-Za-z]{5,12}$/.test((document.getElementById("username").value))){
            return true;
        }

        return false;
    }
</script>
```

