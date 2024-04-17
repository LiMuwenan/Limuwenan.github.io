# 1 JSON语法

JSON 语法是 JavaScript 对象表示语法的子集。

- 数据在名称/值对中
- 数据由逗号分隔
- 大括号 {} 保存对象
- 中括号 [] 保存数组，数组可以包含多个对象

**JSON 名称/值对**：

JSON 数据的书写格式是：

```json
key : value
```

名称/值对包括字段名称（在双引号中），后面写一个冒号，然后是值：

```json
"name" : "菜鸟教程"
```

这很容易理解，等价于这条 JavaScript 语句：

```js
name = "菜鸟教程"
```

**JSON 值:**

JSON 值可以是：

- 数字（整数或浮点数）
- 字符串（在双引号中）
- 逻辑值（true 或 false）
- 数组（在中括号中）
- 对象（在大括号中）
- null

**JSON 数字:**

JSON 数字可以是整型或者浮点型：

```json
{ "age":30 }
```

**JSON 对象:**

JSON 对象在大括号 {} 中书写：

```json
{	key1 : value1, 
	key2 : value2, 
	... 
	keyN : valueN 
}
```

对象可以包含多个名称/值对：

```json
{ 	"name":"菜鸟教程" , 
 	"url":"www.runoob.com" 
}
```

这一点也容易理解，与这条 JavaScript 语句等价：

```js
name = "菜鸟教程"
url = "www.runoob.com"
```

**JSON 数组:**

JSON 数组在中括号 [] 中书写：

数组可包含多个对象：

```json
[
    { key1 : value1-1 , key2:value1-2 }, 
    { key1 : value2-1 , key2:value2-2 }, 
    { key1 : value3-1 , key2:value3-2 }, 
    ...
    { keyN : valueN-1 , keyN:valueN-2 }, 
]
```

```json
{    "sites": [        
    	{ "name":"菜鸟教程" , "url":"www.runoob.com" },         
    	{ "name":"google" , "url":"www.google.com" },         
    	{ "name":"微博" , "url":"www.weibo.com" }    
	] 
}
```

在上面的例子中，对象 **sites** 是包含三个对象的数组。每个对象代表一条关于某个网站（name、url）的记录。

**JSON 布尔值:**

JSON 布尔值可以是 true 或者 false：

```json
{ "flag":true }
```

**JSON null:**

JSON 可以设置 null 值：

```json
{ "runoob":null }
```

# 2 JSON对象

**对象语法：**

```json
{ 	
    "name":"runoob", 
    "alexa":10000, 
    "site":null 
}
```

JSON 对象使用在大括号({})中书写。

对象可以包含多个 **key/value（键/值）**对。

key 必须是字符串，value 可以是合法的 JSON 数据类型（字符串, 数字, 对象, 数组, 布尔值或 null）。

key 和 value 中使用冒号(:)分割。

每个 key/value 对使用逗号(,)分割。

# 3 JSON数组

```json
{
"name":"网站",
"num":3,
"sites":[ "Google", "Runoob", "Taobao" ]
}
```

JSON 数组在中括号中书写。

JSON 中数组值必须是合法的 JSON 数据类型（字符串, 数字, 对象, 数组, 布尔值或 null）。

JavaScript 中，数组值可以是以上的 JSON 数据类型，也可以是 JavaScript 的表达式，包括函数，日期，及 *undefined*。



# 4 Gson

[google官网的指导文档](https://github.com/google/gson/blob/master/UserGuide.md#TOC-Overview)

[google官方的指导手册]()