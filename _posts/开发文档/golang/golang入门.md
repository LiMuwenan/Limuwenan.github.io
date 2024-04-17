

[golang尚硅谷入门教程](https://www.bilibili.com/video/BV1ME411Y71o)

# 命令

```
go run // 用于运行源码文件，只接受一个文件
go build // 用于编译源码文件或代码包
go get // 下载
go install // 编译并安装代码包或者源码文件；产生归档文件和可执行文件
```



# 1 数据类型

- 基本数据类型
  - 整数类型：int, int8, int16, int32, int64, uint, uint8, uint16, uint32, uint64, byte
  - 浮点类型：float32, float64
  - 字符类型：使用 byte 表示
  - 布尔型：bool
  - 字符串：string
- 派生复杂类型：
  - 指针
  - 数组
  - 结构体
  - 管道
  - 函数
  - 切片
  - 接口
  - map

# 2 输入输出

```
fmt.Scanln()
fmt.Scanf()
```





# 3 包、函数



函数名首字母小写，不能跨包使用：private

函数名首字母大写，可以跨包使用：public

## 闭包



## defer

```go
func sum(n1 int, n2 int) int {
	// 有一个defer栈
	defer fmt.Println("ok1 n1=", n1) // 输出顺序3
	defer fmt.Println("ok1 n2=", n2) // 输出顺序2
	
	res := n1 + n2
	fmt.Println("ok3 res=", res) // 输出顺序1
	return res
}

func main() {
	res := sum(10, 20)
	fmt.Println("res=", res) // 输出顺序4
}
```

## 字符串函数

字符串遍历，如果有中文，需要先使用 rune函数转切片再遍历

# 4 错误处理

defer-panic-recover

```go
func test() {
	defer func() { // 匿名函数
		err := revcoer() // 是内置函数，可以捕获的异常
		if err != nil {
			fmt.Println("err=", err)
		}
    }()
	num1 := 10
	num2 :=0
	res := num1 / num2
}
```

errors.New("自定义错误")

```go
panic() 抛出一个错误信息

func test() {
	defer func() {
		err := recover()
		if err != nil {
			panic(errors.New("跑出来"))
		}
	}()
	num1 := 10
	num2 := 0
	res := num1 / num2
	fmt.Println(res)
}

func main() {
	defer func() {
		err := recover()
		if err != nil {
			fmt.Println("出错了", err)
		}
	}()
	fmt.Println("测试")
	test()
}
// 测试
// 出错了 跑出来
```

# 5 数组，切片

数组创建

```go
var arr = [6]int{2,4,6,1,2,4}
var arr [...]int{1,2,3}
var arr [...]int{1: 800, ,0: 900} //第一个元素是900，第二个800
```

数组遍历

```go
var arr [6]int
for index, value := range arr {
	// 遍历
	// index是下标， value是值
}
	var arr = [6]int{2,4,6,1,2,4}
	for index, value := range arr {
		fmt.Println(index, value)
	}
	for i:=0;i<len(arr);i++ {
		fmt.Println(i, arr[i])
	}
```

切片创建

```go
// 方式一
var arr [10]int
slice := arr[1:3] // 左闭右开，取arr数组的元素。且是引用的
// 方式二
var slice []int = make([]int, 4, 10) // 4 大小， 10 容量
```



# 6 map

```go
var map_name map[keytype]valuetype
// 需要申请内存再使用
map_name = make(map[keytype]valuetype, 10)
```



# 7 结构体

定义

```go
// 结构体定义
type struct_name struct {
	//
}
func main() {
    var name struct_name
}
```

方法定义

```go
// 定义了struct_name结构体的一个方法
// 方法名字是test()，没有返回值
func (name struct_name) test() {
    // name 相当于this指针
}
```

继承

```go
type Person struct {
	Name string
	Age int
}

func (p Person) speak() {
	fmt.Println("我说了一句话")
}

type Student struct {
	Person //extend
	Feild string
}

func (s Student) speak() { // 方法重载了 
	fmt.Println("学生说了一句话")
}

func main() {
	person := Person{
		"ligen",
		25,
	}
	fmt.Println(person.Name, person.Age)
	student := Student{
		person,
		"cs",
	}
	fmt.Println(student.Name, student.Age, student.Feild)
	person.Age = 26
	fmt.Println(student.Name, student.Age, student.Feild) // 年龄不会变

	student1 := Student{
		Person{
			"wendan",
			26,
		},
		"ed",
	}
	fmt.Println(student1.Name, student1.Age, student1.Feild)

}
```



# 8 接口

创建一个接口，一个struct实现了一个接口的所有方法，会自动实现该接口，不用明确写出来

```go
type Animal interface {
	say()
	eat()
}

type Dog struct {

}

func (d Dog) say()  {
	fmt.Println("Dog say")
}

//func (d Dog) eat() {
//	fmt.Println("Dog eat")
//}

func main() {
	var a Animal = Dog{} // 未实现Animal接口，Dog不能成为Animal接口的实例化对象
	a.eat()
	a.say()

}
```

```go
type Animal interface {
	say()
	eat()
}

type Dog struct {

}

func (d Dog) say()  {
	fmt.Println("Dog say")
}

func (d Dog) eat() {
	fmt.Println("Dog eat")
}

func main() {
	var a Animal = Dog{}
	a.eat()
	a.say()

}
```

序列化标签

```go
type Sname struct{
	Name string  `json:"name"` // 打上标签，序列化后key为标签
	Age int `json:"age"`
}

name := Sname{
	Name: "ligen"
    Age: 15
}
```



# 9 文件

打开文件

```go
// 打开文件
os.Open(name string) (file *File, err error) // 传入路径，返回文件指针
// 关闭文件
filename.close() // File 结构体的方法
// 带缓存的读
reader := buffio.Reader(filename)
for {
    str, err := reader.ReaderString("\n") // 每读到换行符就结束一次
    if err == io.EOF { // 读到文件末尾
        
    }
}
```

# 10 协程

```go
func test() {

}
func main() {
	go test() // 开启了一个线程
}
```

锁

```go
// 锁对象
lock sync.Mutex

lock.Lock() // 上锁

lock.Unlock() // 解锁
```

管道

```go
var 变量名 chan 数据类型 // 管道中放什么数据类型的数据
变量名 = make(chan 数据类型，容量)

// 向管道写入数据
变量名<-数据1
// 从管道取数据
数据1=<-变量名
// 关闭管道。管道关闭后不能写只能读
close(管道名称)
// for-range遍历
```

select 是一种可以处理多个通道之间选择机制，程序会随机选择一个 case 进行执行

```go
ch := make(chan int, 10)
ch<-1
ch<-10
select {
    case i := <-ch:
        fmt.Println("i:1", i)
        break
    case j := <-ch:
        fmt.Println("j:2", j)
        break
}
```







# 11 反射



# 12 网络

## 12.1 tcp socket

```go
// server
func main() {
	listen, err := net.Listen("tcp", "127.0.0.1:8888") // 监听
    if err != nil {
        
    }
    defer listen.Close() // 关闭
    
    for {
        conn, err := listen.Accept() // 循环等待
        if err != nil {
            
        } else {
            // 处理逻辑
        }
    }
}

// client

func main() {
    conn, err := net.Dial("tcp", "192.168.200.7:8888")
    if err != nil {
        
    }
}
```

## 12.2 http

httpServer的简单代码

```go
// 2. 创建处理函数
func handle(response http.ResponseWriter, request *http.Request) {
	response.Write([]byte("<h1>hello,world</h1>")) // 访问该路径后的处理操作
}

func main() {
	http.HandleFunc("/test", handle) // 1.绑定一个路径
	http.ListenAndServe(":9546", nil) // 3.启动服务监听
}
```

httpClient的简单代码

```go
func main() {
	client := new(http.Client) // 1.创建客户端

	// 2.构造请求
	request, _ := http.NewRequest("GET","http://localhost:9546/test1", nil)

	// 3.发送请求, 得到响应
	response, _ := client.Do(request)

	body := response.Body
	bytes, _ := io.ReadAll(body)
	fmt.Printf(string(bytes))
}
```

# 13 Go And ReactJS

[使用 Go 和 ReactJS 构建聊天系统](https://studygolang.com/articles/22423)

## 13.1 项目初始化

我们将通过设置两个项目来开始这个课程。一旦我们完成了枯燥的设置，就可以开始添加新功能并构建我们的应用程序，将看到一些积极的结果！

### 目标

在这部分课程结束后，你将掌握：

- 在 `backend/` 目录创建基本的 Go 应用
- 在 `frontend/` 目录创建基本的 ReactJS 应用

通过实现这两个部分，你将能够在接下来的几节课程中为聊天系统添加一些功能。

### 准备工作

为了完成本系列教程，我们先要做以下的准备工作。

- 需要安装 `npm`
- 需要安装 `npx`。这个可以输入 `npm install -g npx` 安装。
- Go 语言版本需要满足 1.11+。
- 需要一个代码编辑器来开发这个项目，例如 VS

### 设置 Go 后端项目

如果你熟悉 Go 的话，这一步非常简单，我们首先要在项目目录中创建一个名为 `backend` 的新目录。

这个 `backend` 目录将包含该项目的所有 Go 代码。然后，我们将通过以下命令来初始化我们的项目：

```shell
$ cd backend
$ export GO111MODULE=on
$ go mod init github.com/TutorialEdge/realtime-chat-go-react
```

应该在 `backend` 目录中使用 go modules 初始化我们的项目，初始化之后我们就可以开始写项目并使其成为一个完整的 Go 应用程序。

- **go.mod** - 这个文件有点像 NodeJS 项目中的 package.json。它详细描述了我们项目所需的包和版本，以便项目的构建和运行。
- **go.sum** - 这个文件用于校验，它记录了每个依赖库的版本和哈希值。

> 注意 - 有关 Go modules 新特性的更多信息，请查看官方 Wiki 文档: [Go Modules](https://github.com/golang/go/wiki/Modules)

### 检查 Go 项目

一旦我们在 `backend/` 目录中调用了 `go mod init`，我们将检查一下一切是否按预期工作。

在 `backend/` 目录中添加一个名为 `main.go` 的新文件，并在其中添加以下 Go 代码：

```go
package main

import "fmt"

func main() {
    fmt.Println("Chat App v0.01")
}
```

将该内容保存到 `main.go` 后，运行后会得到如下内容：

```shell
$ go run main.go
Chat App v0.01
```

如果成功执行，我们可以继续设置我们的前端应用程序。

### 设置 React 前端项目

设置前端会稍微复杂一点，首先我们要在项目的根目录中创建一个 `frontend` 目录，它将容纳我们所有的 ReactJS 代码。

> 注意 - 我们将使用 [facebook/create-react-app](https://github.com/facebook/create-react-app) 来生成我们的 React 前端。

```shell
$ cd frontend
```

然后，你需要使用 `create-react-app` 包创建一个新的 ReactJS 应用程序。这可以用 `npm` 安装：

```shell
$ npm install -g create-react-app
```

安装完成后，你应该能够使用以下命令创建新的 ReactJS 应用程序：

```shell
$ npx create-react-app .
```

运行这些命令之后，你应该可以看到我们的 `frontend/` 目录生成了基本的 ReactJS 应用程序。

我们的目录结构应如下所示：

```shell
node_modules/
public/
src/
.gitignore
package.json
README.md
yarn.lock
```

### 本地运行 ReactJS 程序

现在已经成功创建了基本的 ReactJS 应用程序，我们可以测试一下是否正常。输入以下命令来运行应用程序：

```shell
$ npm start
```

如果一切正常的话，将会看到 ReactJS 应用程序编译并在本地开发服务器上运行：[http://localhost:3000](http://localhost:3000/)

```plain
Compiled successfully!

You can now view frontend in the browser.

    Local:            http://localhost:3000/
    On Your Network:  http://192.168.1.234:3000/

Note that the development build is not optimized.
To create a production build, use yarn build.
```

现在已经拥有有一个基本的 ReactJS 应用程序了，我们可以在接下来的教程中进行扩展。

### 总结

太棒了，现在已经成功设置了我们项目的前端和后端部分，接下来我们可以添加一些酷炫的新功能。

## 13.2 后端实现

现在我们已经建立好了基本的前端和后端，现在需要来完善一些功能了。

在本节中，我们将实现一个基于 WebSocket 的服务器。

在该系列教程结束时，我们将有一个可以于后端双向通信的前端应用程序，。

### 服务

我们可以使用 `github.com/gorilla/websocket` 包来设置 WebSocket 服务以及处理 WebSocket 连接的读写操作。

这需要在我们的 `backend/` 目录中运行此命令来安装它：

```shell
$ go get github.com/gorilla/websocket
```

一旦我们成功安装了这个包，我们就可以开始构建我们的 Web 服务了。我们首先创建一个非常简单的 `net/http` 服务：

```go
package main

import (
    "fmt"
    "net/http"
)

func setupRoutes() {
    http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
        fmt.Fprintf(w, "Simple Server")
    })
}

func main() {
    setupRoutes()
    http.ListenAndServe(":8080", nil)
}
```

可以通过调用 `go run main.go` 来启动服务，该服务将监听 [http://localhost:8080](http://localhost:8080/) 。如果用浏览器打开此连接，可以看到输出 `Simple Server`。

### WebSocket 协议

在开始写代码之前，我们需要了解一下理论。

WebSockets 可以通过 TCP 连接进行双工通信。这让我们可以通过单个 TCP 套接字来发送和监听消息，从而避免通过轮询 Web 服务器去通信，每次轮询操作都会执行 TCP 握手过程。

WebSockets 大大减少了应用程序所需的网络带宽，并且使得我们在单个服务器实例上维护大量客户端。

### 连接

WebSockets 肯定有一些值得考虑的缺点。比如一旦引入状态，在跨多个实例扩展应用程序的时候就变得更加复杂。

在这种场景下需要考虑更多的情况，例如将状态存储在消息代理中，或者存储在数据库/内存缓存中。

### 实现

在实现 WebSocket 服务时，我们需要创建一个端点，然后将该端点的连接从标准的 HTTP 升级到 WebSocket。

值得庆幸的是，`gorilla/websocket` 包提供了我们所需的功能，可以轻松地将 HTTP 连接升级到 WebSocket 连接。

> 注意 - 你可以查看官方 WebSocket 协议的更多信息：[RFC-6455](https://tools.ietf.org/html/rfc6455)

### 创建 WebSocket 服务端

现在已经了解了理论，来看看如何去实践。我们创建一个新的端点 `/ws`，我们将从标准的 `http` 端点转换为 `ws` 端点。

此端点将执行 3 项操作，它将检查传入的 HTTP 请求，然后返回 `true` 以打开我们的端点到客户端。然后，我们使用定义的 `upgrader` 升级为 WebSocket 连接。

最后，我们将开始监听传入的消息，然后将它们打印出来并将它们传回相同的连接。这可以让我们验证前端连接并从新创建的 WebSocket 端点来发送/接收消息：

```go
package main

import (
    "fmt"
    "log"
    "net/http"

    "github.com/gorilla/websocket"
)

// 我们需要定义一个 Upgrader
// 它需要定义 ReadBufferSize 和 WriteBufferSize
var upgrader = websocket.Upgrader{
    ReadBufferSize:  1024,
    WriteBufferSize: 1024,

    // 可以用来检查连接的来源
    // 这将允许从我们的 React 服务向这里发出请求。
    // 现在，我们可以不需要检查并运行任何连接
    CheckOrigin: func(r *http.Request) bool { return true },
}

// 定义一个 reader 用来监听往 WS 发送的新消息
func reader(conn *websocket.Conn) {
    for {
        // 读消息
        messageType, p, err := conn.ReadMessage()
        if err != nil {
            log.Println(err)
            return
        }
        // 打印消息
        fmt.Println(string(p))

        if err := conn.WriteMessage(messageType, p); err != nil {
            log.Println(err)
            return
        }
    }
}

// 定义 WebSocket 服务处理函数
func serveWs(w http.ResponseWriter, r *http.Request) {
    fmt.Println(r.Host)

    // 将连接更新为 WebSocket 连接
    ws, err := upgrader.Upgrade(w, r, nil)
    if err != nil {
        log.Println(err)
    }

    // 一直监听 WebSocket 连接上传来的新消息
    reader(ws)
}

func setupRoutes() {
    http.HandleFunc("/", func(w http.ResponseWriter, r *http.Request) {
        fmt.Fprintf(w, "Simple Server")
    })

    // 将 `/ws` 端点交给 `serveWs` 函数处理
    http.HandleFunc("/ws", serveWs)
}

func main() {
    fmt.Println("Chat App v0.01")
    setupRoutes()
    http.ListenAndServe(":8080", nil)
}
```

如果没有问题的话，我们使用 `go run main.go` 来启动服务。

### 客户端

现在已经设置好了服务，我们需要一些能够与之交互的东西。这是我们的 ReactJS 前端发挥作用的地方。

我们先尽量让客户端保持简单，并定义一个 `api/index.js` 文件，它将包含 WebSocket 连接的代码。

```js
// api/index.js
var socket = new WebSocket("ws://localhost:8080/ws");

let connect = () => {
    console.log("Attempting Connection...");

    socket.onopen = () => {
        console.log("Successfully Connected");
    };

    socket.onmessage = msg => {
        console.log(msg);
    };

    socket.onclose = event => {
        console.log("Socket Closed Connection: ", event);
    };

    socket.onerror = error => {
        console.log("Socket Error: ", error);
    };
};

let sendMsg = msg => {
    console.log("sending msg: ", msg);
    socket.send(msg);
};

export { connect, sendMsg };
```

因此，在上面的代码中，我们定义了我们随后导出的 2 个函数。分别是 `connect()` 和 `sendMsg(msg)`。

第一个函数，`connect()` 函数，连接 WebSocket 端点，并监听例如与 `onopen` 成功连接之类的事件。如果它发现任何问题，例如连接关闭的套接字或错误，它会将这些问题打印到浏览器控制台。

第二个函数，`sendMsg(msg)` 函数，允许我们使用 `socket.send()` 通过 WebSocket 连接从前端发送消息到后端。

现在我们在 React 项目中更新 `App.js` 文件，添加对 `connect()` 的调用并创建一个触发 `sendMsg()` 函数的 `` 元素。

```js
// App.js
import React, { Component } from "react";
import "./App.css";
import { connect, sendMsg } from "./api";

class App extends Component {
    constructor(props) {
        super(props);
        connect();
    }

    send() {
        console.log("hello");
        sendMsg("hello");
    }

    render() {
        return (
            <div className="App">
                <button onClick={this.send}>Hit</button>
            </div>
        );
    }
}

export default App;
```

使用 `npm start` 成功编译后，我们可以在浏览器中看到一个按钮，如果打开浏览器控制台，还可以看到成功连接的 WebSocket 服务运行在 [http://localhost:8080](http://localhost:8080/)。

> 问题 - 单击此按钮会发生什么？你在浏览器的控制台和后端的控制台中看到了什么输出？

### 总结

结束了本系列的第 2 部分。我们已经能够创建一个非常简单的 WebSocket 服务，它可以回显发送给它的任何消息。

这是开发应用程序的关键一步，现在我们已经启动并运行了基本框架，我们可以开始考虑实现基本的聊天功能并让这个程序变得更有用！

## 13.3 前端实现

### Header 组件

我们先来创建一个非常简单的 Header 组件。我们需要在 `frontend/src/` 目录下 创建一个叫 `components/` 的新目录，并在其中添加一个 `Header/` 目录，它将容纳 Header 组件的所有文件。

```plain
- src/
- - components/
- - - Header/
- - - - Header.jsx
- - - - index.js
- - - - Header.scss
```

> 注意 - 每当我们创建一个新组件时，我们将在 `components/` 目录中创建一个新目录，我们会在该目录中创建这三个文件（*.jsx，*.js，*.scss）。

#### Header.jsx

我们需要在 `Header.jsx` 文件中实现函数组件。这将用于呈现网站的标题：

```js
import React from "react";
import "./Header.scss";

const Header = () => (
    <div className="header">
        <h2>Realtime Chat App</h2>
    </div>
);

export default Header;
```

#### Header.scss

接下来，我们需要加上一些样式。 由于 ReactJS 项目没有处理 `scss` 文件的能力，因此我们首先需要在 `frontend/` 目录中运行以下命令来安装 `node-sass`：

```shell
$ yarn add node-sass
```

安装完成后，我们就可以添加样式了：

```css
.header {
    background-color: #15223b;
    width: 100%;
    margin: 0;
    padding: 10px;
    color: white;

    h2 {
        margin: 0;
        padding: 0;
    }
}
```

#### index.js

最后，我们要导出 `Header` 组件以至于其他组件可以导入它并在它们自己的 `render()` 函数中展示它：

```js
import Header from "./header.jsx";

export default Header;
```

#### 更新 App.js

现在已经创建好了 `Header` 组件，我们需要将它导入 `App.js`，然后通过将它添加到我们的 `render()` 函数中来展示它，如下所示：

```js
// App.js
// 从相对路径导入组件
import Header from './components/Header/Header';
// ...
render() {
    return (
        <div className="App">
            <Header />
            <button onClick={this.send}>Hit</button>
        </div>
    );
}
```

保存这个文件后，我们的前端应用程序需要重新编译，然后可以看到 `Header` 组件成功展示在浏览器页面的顶部。

> 恭喜 - 你已经成功创建了第一个 React 组件！

### 历史聊天记录组件

我们已经构建并渲染了一个非常简单的组件，所以我们再来构建一个更复杂一点的组件。

在这个小节中，我们将创建一个历史聊天记录组件，它用来显示我们从 WebSocket 服务收到的所有消息。

我们将在 `components/` 目录中创建一个新文件夹叫 `ChatHistory/`。同样，我们需要为这个组件创建三个文件。

#### ChatHistory.jsx

我们从 `ChatHistory.jsx` 文件开始吧。它比之前的要稍微复杂一些，因为我们将构建一个 `Class` 组件，而不是我们上面 Header 组件的 `Function` 组件。

> 注意 - 我们可以使用 `ES6 calss` 定义类组件。如果你想了解更多有关信息，建议查看官方文档：[功能和类组件](https://reactjs.org/docs/components-and-props.html#function-and-class-components)

在这个组件中，你会注意到有一个 `render()` 函数。 `render()` 函数返回一个用于展示此特定组件的 `jsx`。

该组件将通过 `props` 从 App.js 函数中接收一组聊天消息，然后将它们按列表由上往下展示。

```js
import React, { Component } from "react";
import "./ChatHistory.scss";

class ChatHistory extends Component {
    render() {
        const messages = this.props.chatHistory.map((msg, index) => (
            <p key={index}>{msg.data}</p>
        ));

        return (
            <div className="ChatHistory">
                <h2>Chat History</h2>
                {messages}
            </div>
        );
    }
}

export default ChatHistory;
```

#### ChatHistory.scss

我们在 `ChatHistory.scss` 中来为 `ChatHistory` 组件添加一个小样式，只是简单的修改一下背景颜色和填充及边距：

```css
.ChatHistory {
    background-color: #f7f7f7;
    margin: 0;
    padding: 20px;
    h2 {
        margin: 0;
        padding: 0;
    }
}
```

#### Index.js

最后，我们需要导出新组件，就像使用 `Header` 组件一样，这样它就可以在 `App.js` 中被导入并展示：

```js
import ChatHistory from "./ChatHistory.jsx";

export default ChatHistory;
```

### 更新 App.js 和 api/index.js

现在我们又添加了 `ChatHistory` 组件，我们需要实际提供一些消息。

在本系列的前一部分中，我们建立了双向通信，回显发送给它的任何内容，因此每当我们点击应用程序中的发送消息按钮时，都会收到一个新消息。

来更新一下 `api/index.js` 文件和 `connect()` 函数，以便它从 WebSocket 连接收到新消息时用于回调：

```js
let connect = cb => {
    console.log("connecting");

    socket.onopen = () => {
        console.log("Successfully Connected");
    };

    socket.onmessage = msg => {
        console.log(msg);
        cb(msg);
    };

    socket.onclose = event => {
        console.log("Socket Closed Connection: ", event);
    };

    socket.onerror = error => {
        console.log("Socket Error: ", error);
    };
};
```

因此，我们在函数中添加了一个 `cb` 参数。每当我们收到消息时，都会在第 10 行调用此 `cb` 会调函数。

当我们完成这些修改，就可以通过 `App.js` 来添加此回调函数，并在获取新消息时使用 `setState` 来更新状态。

我们将把 `constructor` 函数 `connect()` 移动到 `componentDidMount()` 函数中调用，该函数将作为组件生命周期的一部分自动调用（译者注：在 render() 方法之后调用）。

```js
// App.js
    componentDidMount() {
        connect((msg) => {
            console.log("New Message")
            this.setState(prevState => ({
                chatHistory: [...this.state.chatHistory, msg]
            }))
            console.log(this.state);
        });
    }
```

然后更新 `App.js` 的 `render()` 函数并展示 `ChatHistory` 组件：

```js
render() {
    return (
        <div className="App">
            <Header />
            <ChatHistory chatHistory={this.state.chatHistory} />
            <button onClick={this.send}>Hit</button>
        </div>
    );
}
```

当我们编译并运行前端和后端项目时，可以看到每当点击前端的发送消息按钮时，它会继续通过 `WebSocket` 连接向后端发送消息，然后后端将其回传给前端，最终在 `ChatHistory` 组件中成功展示！

### 总结

我们成功地改进了前端应用程序，并将其视为聊天应用程序。在本系列的下一部分中，将重点关注以下内容：

- 改进前端：添加新的发送消息组件以允许我们发送自定义消息
- 改进后端：处理多个客户端以及跨客户端的通信。

## 13.4 处理多客户端

这节主要实现处理多个客户端消息的功能，并将收到的消息广播到每个连接的客户端。在本系列的这一部分结束时，我们将：

- 实现了一个池机制，可以有效地跟踪 WebSocket 服务中的连接数。
- 能够将任何收到的消息广播到连接池中的所有连接。
- 当另一个客户端连接或断开连接时，能够通知现有的客户端。

在本课程的这一部分结束时，我们的应用程序看起来像这样：

![img](https://raw.githubusercontent.com/studygolang/gctt-images/master/chat-system-in-go-and-react-course-series/image_3.png)

### 拆分 Websocket 代码

现在已经完成了必要的基本工作，我们可以继续改进代码库。可以将一些应用程序拆分为子包以便于开发。

现在，理想情况下，你的 `main.go` 文件应该只是 Go 应用程序的入口，它应该相当小，并且可以调用项目中的其他包。

> 注意 - 我们将参考非官方标准的 Go 项目结构布局 - [golang-standards/project-layout](https://github.com/golang-standards/project-layout)

让我们在后端项目目录中创建一个名为 `pkg/` 的新目录。在此期间，我们将要创建另一个名为 `websocket/` 的目录，该目录将包含 `websocket.go` 文件。

我们将把目前在 `main.go` 文件中使用的许多基于 WebSocket 的代码移动到这个新的 `websocket.go` 文件中。

> 注意 - 需要注意的一件事是，当复制函数时，需要将每个函数的第一个字母大写，我们希望这些函数对项目的其余部分可导出。

```go
package websocket

import (
    "fmt"
    "io"
    "log"
    "net/http"

    "github.com/gorilla/websocket"
)

var upgrader = websocket.Upgrader{
    ReadBufferSize:  1024,
    WriteBufferSize: 1024,
    CheckOrigin: func(r *http.Request) bool { return true },
}

func Upgrade(w http.ResponseWriter, r *http.Request) (*websocket.Conn, error) {
    ws, err := upgrader.Upgrade(w, r, nil)
    if err != nil {
        log.Println(err)
        return ws, err
    }
    return ws, nil
}

func Reader(conn *websocket.Conn) {
    for {
        messageType, p, err := conn.ReadMessage()
        if err != nil {
            log.Println(err)
            return
        }

        fmt.Println(string(p))

        if err := conn.WriteMessage(messageType, p); err != nil {
            log.Println(err)
            return
        }
    }
}

func Writer(conn *websocket.Conn) {
    for {
        fmt.Println("Sending")
        messageType, r, err := conn.NextReader()
        if err != nil {
            fmt.Println(err)
            return
        }
        w, err := conn.NextWriter(messageType)
        if err != nil {
            fmt.Println(err)
            return
        }
        if _, err := io.Copy(w, r); err != nil {
            fmt.Println(err)
            return
        }
        if err := w.Close(); err != nil {
            fmt.Println(err)
            return
        }
    }
}
```

现在已经创建了这个新的 `websocket` 包，然后我们想要更新 `main.go` 文件来调用这个包。首先必须在文件顶部的导入列表中添加一个新的导入，然后可以通过使用 `websocket.` 来调用该包中的函数。像这样：

```go
package main

import (
    "fmt"
    "net/http"

    "realtime-chat-go-react/backend/pkg/websocket"
)

func serveWs(pool *websocket.Pool, w http.ResponseWriter, r *http.Request) {
    fmt.Println("WebSocket Endpoint Hit")
    conn, err := websocket.Upgrade(w, r)
    if err != nil {
        fmt.Fprintf(w, "%+v\n", err)
    }

    client := &websocket.Client{
        Conn: conn,
        Pool: pool,
    }

    pool.Register <- client
    client.Read()
}

func setupRoutes() {
    pool := websocket.NewPool()
    go pool.Start()

    http.HandleFunc("/ws", func(w http.ResponseWriter, r *http.Request) {
        serveWs(pool, w, r)
    })
}

func main() {
    fmt.Println("Distributed Chat App v0.01")
    setupRoutes()
    http.ListenAndServe(":8080", nil)
}
```

经过这些修改，我们应该检查一下这些是否破坏了现有的功能。尝试再次运行后端和前端，确保仍然可以发送和接收消息：

```shell
$ cd backend/
$ go run main.go
```

如果成功，我们可以继续扩展代码库来处理多客户端。

到目前为止，目录结构应如下所示：

```plain
- backend/
- - pkg/
- - - websocket/
- - - - websocket.go
- - main.go
- - go.mod
- - go.sum
- frontend/
- ...
```

### 处理多客户端

现在已经完成了基本的操作，我们可以继续改进后端并实现处理多个客户端的功能。

为此，我们需要考虑如何处理与 WebSocket 服务的连接。每当建立新连接时，我们都必须将它们添加到现有连接池中，并确保每次发送消息时，该池中的每个人都会收到该消息。

#### 使用 Channels

我们需要开发一个具有大量并发连接的系统。在该连接的持续时间内都会启动新的 `goroutine` 去处理每一个连接。这意味着我们必须关心这些并发 `goroutine` 之间的通信，并确保线程安全。

当进一步实现 `Pool` 结构时，我们必须考虑使用 `sync.Mutex` 来阻塞其他 `goroutine` 同时访问/修改数据，或者我们也可以使用 `channels`。

对于这个项目，我认为最好使用 `channels` 并且以安全的方式在多个并发的 `goroutine` 中进行通信。

> 注意 - 如果想进一步了解 Go 中的 `channels`，可以在这里查看我的其他文章：[Go Channels Tutorial](https://tutorialedge.net/golang/go-channels-tutorial/)

#### client.go

我们先创建一个名为 `client.go` 新文件，它将存在于 `pkg/websocket` 目录中，在文件中将定义一个包含以下内容的 `Client` 结构体：

- **ID**：特定连接的唯一可识别字符串
- **Conn**：指向 `websocket.Conn` 的指针
- **Pool**：指向 `Pool` 的指针??

还需要定义一个 `Read()` 方法，该方法将一直监听此 `Client` 的 websocket 连接上发出的新消息。

如果收到新消息，它将把这些消息传递给池的 `Broadcast` channel，该 channel 随后将接收的消息广播到池中的每个客户端。

```go
package websocket

import (
    "fmt"
    "log"

    "github.com/gorilla/websocket"
)

type Client struct {
    ID   string
    Conn *websocket.Conn
    Pool *Pool
}

type Message struct {
    Type int    `json:"type"`
    Body string `json:"body"`
}

func (c *Client) Read() {
    defer func() {
        c.Pool.Unregister <- c
        c.Conn.Close()
    }()

    for {
        messageType, p, err := c.Conn.ReadMessage()
        if err != nil {
            log.Println(err)
            return
        }
        message := Message{Type: messageType, Body: string(p)}
        c.Pool.Broadcast <- message
        fmt.Printf("Message Received: %+v\n", message)
    }
}
```

太棒了，我们已经在代码中定义了客户端，继续实现池。

#### Pool 结构体

我们在 `pkg/websocket` 目录下创建一个新文件 `pool.go`。

首先定义一个 `Pool` 结构体，它将包含我们进行并发通信所需的所有 `channels`，以及一个客户端 `map`。

```go
package websocket

import "fmt"

type Pool struct {
    Register   chan *Client
    Unregister chan *Client
    Clients    map[*Client]bool
    Broadcast  chan Message
}

func NewPool() *Pool {
    return &Pool{
        Register:   make(chan *Client),
        Unregister: make(chan *Client),
        Clients:    make(map[*Client]bool),
        Broadcast:  make(chan Message),
    }
}
```

我们需要确保应用程序中只有一个点能够写入 WebSocket 连接，否则将面临并发写入问题。所以，定义了 `Start()` 方法，该方法将一直监听传递给 `Pool` channels 的内容，然后，如果它收到发送给其中一个 channel 的内容，它将采取相应的行动。

- **Register** - 当新客户端连接时，`Register channel` 将向此池中的所有客户端发送 `New User Joined...`
- **Unregister** - 注销用户，在客户端断开连接时通知池
- **Clients** - 客户端的布尔值映射。可以使用布尔值来判断客户端活动/非活动
- **Broadcast** - 一个 channel，当它传递消息时，将遍历池中的所有客户端并通过套接字发送消息。

代码：

```go
func (pool *Pool) Start() {
    for {
        select {
        case client := <-pool.Register:
            pool.Clients[client] = true
            fmt.Println("Size of Connection Pool: ", len(pool.Clients))
            for client, _ := range pool.Clients {
                fmt.Println(client)
                client.Conn.WriteJSON(Message{Type: 1, Body: "New User Joined..."})
            }
            break
        case client := <-pool.Unregister:
            delete(pool.Clients, client)
            fmt.Println("Size of Connection Pool: ", len(pool.Clients))
            for client, _ := range pool.Clients {
                client.Conn.WriteJSON(Message{Type: 1, Body: "User Disconnected..."})
            }
            break
        case message := <-pool.Broadcast:
            fmt.Println("Sending message to all clients in Pool")
            for client, _ := range pool.Clients {
                if err := client.Conn.WriteJSON(message); err != nil {
                    fmt.Println(err)
                    return
                }
            }
        }
    }
}
```

#### websocket.go

太棒了，我们再对 `websocket.go` 文件进行一些小修改，并删除一些不再需要的函数和方法：

```go
package websocket

import (
    "log"
    "net/http"

    "github.com/gorilla/websocket"
)

var upgrader = websocket.Upgrader{
    ReadBufferSize:  1024,
    WriteBufferSize: 1024,
    CheckOrigin: func(r *http.Request) bool { return true },
}

func Upgrade(w http.ResponseWriter, r *http.Request) (*websocket.Conn, error) {
    conn, err := upgrader.Upgrade(w, r, nil)
    if err != nil {
        log.Println(err)
        return nil, err
    }

    return conn, nil
}
```

### 更新 main.go

最后，我们需要更新 `main.go` 文件，在每个连接上创建一个新 `Client`，并使用 `Pool` 注册该客户端：

```go
package main

import (
    "fmt"
    "net/http"

    "github.com/TutorialEdge/realtime-chat-go-react/pkg/websocket"
)

func serveWs(pool *websocket.Pool, w http.ResponseWriter, r *http.Request) {
    fmt.Println("WebSocket Endpoint Hit")
    conn, err := websocket.Upgrade(w, r)
    if err != nil {
        fmt.Fprintf(w, "%+v\n", err)
    }

    client := &websocket.Client{
        Conn: conn,
        Pool: pool,
    }

    pool.Register <- client
    client.Read()
}

func setupRoutes() {
    pool := websocket.NewPool()
    go pool.Start()

    http.HandleFunc("/ws", func(w http.ResponseWriter, r *http.Request) {
        serveWs(pool, w, r)
    })
}

func main() {
    fmt.Println("Distributed Chat App v0.01")
    setupRoutes()
    http.ListenAndServe(":8080", nil)
}
```

### 测试

现在已经做了所有必要的修改，我们应该测试已经完成的工作并确保一切按预期工作。

启动你的后端应用程序：

```shell
$ go run main.go
Distributed Chat App v0.01
```

如果你在几个浏览器中打开 [http://localhost:3000](http://localhost:3000/)，可以看到到它们会自动连接到后端 WebSocket 服务，现在我们可以发送和接收来自同一池内的其他客户端的消息！

![img](https://raw.githubusercontent.com/studygolang/gctt-images/master/chat-system-in-go-and-react-course-series/image_4.png)

### 总结

在本节中，我们设法实现了一种处理多个客户端的方法，并向连接池中连接的每个人广播消息。

现在开始变得有趣了。我们可以在下一节中添加新功能，例如自定义消息。

## 13.5 优化前端

欢迎来到本系列的第 5 部分！如果你已经学到这儿了，那么我希望你享受学习 Go 的乐趣并运用 Go 和 React 建立自己的聊天系统！

在本节中，我们将再次关注前端，并对其进行优化，以便可以输入自定义的聊天消息，并且以更好的方式显示新的聊天消息。

### 聊天输入组件

我们需要创建一个新的组件。该组件基本上只渲染 `<input />` 的内容，然后监听 `onKeyDown` 事件（译者注：`onkeydown` 事件会在用户按下键盘按键时触发）。当用户在 `<input />` 元素内按键时，它将触发 `onKeyDown` 事件的函数。

```js
import React, { Component } from "react";
import "./ChatInput.scss";

class ChatInput extends Component {
    render() {
        return (
            <div className="ChatInput">
                <input onKeyDown={this.props.send} />
            </div>
        );
    }
}

export default ChatInput;
```

然后，我们将为新的输入组件定义一些样式：

```css
.ChatInput {
    width: 95%;
    display: block;
    margin: auto;

    input {
        padding: 10px;
        margin: 0;
        font-size: 16px;
        border: none;
        border-radius: 5px;
        border: 1px solid rgba(0, 0, 0, 0.1);
        width: 98%;
        box-shadow: 0 5px 15px -5px rgba(0, 0, 0, 0.1);
    }
}
```

定义了组件和样式，现在只需要导出它。

```js
import ChatInput from "./ChatInput.jsx";

export default ChatInput;
```

#### 更新 App.js

我们创建了 `ChatInput` 组件，现在需要更新 `App.js`，以便它使用新组件并将已经定义的 `send()` 函数传递给该组件。

```js
render() {
    return (
        <div className="App">
            <Header />
            <ChatHistory chatHistory={this.state.chatHistory} />
            <ChatInput send={this.send} />
        </div>
    );
}
```

我们已经传入了定义的 `send()` 函数，该函数现在只是向 WebSocket 端点发送一个简单的 “Hello” 字符串。我们需要修改它，以便接收触发它的事件的上下文。

通过传递这个事件，我们将能够查询按下的键是否是 `Enter` 键，如果是，我们将 `<input />` 字段的值发送到 WebSocket 端点，然后清除 `<input />`：

```js
send(event) {
    if(event.keyCode === 13) {
        sendMsg(event.target.value);
        event.target.value = "";
    }
}
```

#### 测试

现在已经创建了 `ChatInput` 组件，我们来运行 Go WebSocket 服务和前端，尝试发送一些自定义消息，看看是否都按预期工作。

### 优化聊天记录组件

现在，我们有一个相当丑陋但功能正常的聊天记录界面，它显示从 WebSocket 服务向连接的客户端广播的每一条消息。

这条消息只是以 JSON 格式显示，没有额外的样式，所以现在让我们看一下通过创建另一个 `Message` 组件来优化它。

#### Message 组件

我们先定义 `Message.jsx` 文件。该组件将通过 `prop` 展示接收的消息。然后它将解析成名为 `message` 的 `prop`，并将其存储在组件状态中，然后我们可以在 `render` 函数中使用它。

```js
// src/components/Message/Message.jsx
import React, { Component } from "react";
import "./Message.scss";

class Message extends Component {
    constructor(props) {
        super(props);
        let temp = JSON.parse(this.props.message);
        this.state = {
            message: temp
        };
    }

    render() {
        return <div className="Message">{this.state.message.body}</div>;
    }
}

export default Message;
```

跟之前一样，我们还需要定义一个 `index.js` 文件，以使其在项目的其余部分中可导出：

```js
// src/components/Message/index.js
import Message from "./Message.jsx";

export default Message;
```

到此为止，我们的组件样式还是比较基本的，只是在一个框中显示消息，我们再设置一些 `box-shadow`，使聊天界面有点视觉深度。

```css
.Message {
    display: block;
    background-color: white;
    margin: 10px auto;
    box-shadow: 0 5px 15px -5px rgba(0, 0, 0, 0.2);
    padding: 10px 20px;
    border-radius: 5px;
    clear: both;

    &.me {
        color: white;
        float: right;
        background-color: #328ec4;
    }
}
```

### 更新历史聊天记录组件

创建好了 `Message` 组件，我们现在可以在 `ChatHistory` 组件中使用它。我们需要更新 `render()` 函数，如下所示：

```js
render() {
    console.log(this.props.chatHistory);
    const messages = this.props.chatHistory.map(msg => <Message message={msg.data} />);

    return (
        <div className='ChatHistory'>
            <h2>Chat History</h2>
            {messages}
        </div>
    );
};
```

在第 3 行，可以看到已更新的 `.map` 函数返回 ``组件，并将消息 `prop` 设置为 `msg.data`。随后会将 JSON 字符串传递给每个消息组件，然后它将能够按照自定义的格式解析和展示它。

现在我们可以看到，每当我们从 WebSocket 端点收到新消息时，它就会在 `ChatHistory` 组件中很好地展示出来！

## 13.6 Docker部署

在本节中，我们将专注于将 Docker 添加到后端应用程序中。

为什么要这么做呢？在我们研究诸如身份验证，负载均衡和部署之类的问题前，使用容器技术部署应用程序是个标准的做法。

### 为什么用 Docker

如果这是你第一次听说 Docker 容器化技术，那么你可能会质疑使用它的原因。

对我来说，其中一个主要原因是它让部署变得更加容易。你可以将基于 docker 的应用程序部署到支持 Docker 的任何服务器或平台。

这意味着，无论你在何处部署，都可以使用简单的命令启动应用程序。

不仅如此，它还解决了 “在我的机器上运行好好的” 这个问题，因为在你的 `Dockerfile` 中，可以指定应用程序启动时所需的确定环境。

### 开始

首先我们得在计算机上安装 Docker。可以参考：[Docker 指南](https://www.docker.com/get-started)

在安装了 docker 并让它运行后，我们就可以创建 `Dockerfile` 了：

```shell
FROM golang:1.11.1-alpine3.8
RUN mkdir /app
ADD . /app/
WORKDIR /app
RUN go mod download
RUN go build -o main ./...
CMD ["/app/main"]
```

我们定义了 Dockerfile 文件之后，就可以使用 `docker` cli 构建 Docker 镜像：

> 注意 - 如果你的网速比较差，下一个命令可能需要等待一段时间才能执行，但是，由于有缓存后续命令会快得多。

```shell
$ docker build -t backend .
Sending build context to Docker daemon  11.26kB
Step 1/8 : FROM golang:1.11.1-alpine3.8
 ---> 95ec94706ff6
Step 2/8 : RUN apk add bash ca-certificates git gcc g++ libc-dev
 ---> Running in 763630b369ca
 ...
```

成功完成 `build` 步骤后，我们可以将该容器启动起来：

```shell
$ docker run -it -p 8080:8080 backend
Distributed Chat App v0.01
WebSocket Endpoint Hit
Size of Connection Pool:  1
&{ 0xc000124000 0xc0000902a0 {0 0}}
Message Received: {Type:1 Body:test}
Sending message to all clients in Pool
```

正如你所见，在运行此命令并刷新客户端后，可以看到现在已经连接到 Docker 化的应用服务，也可以看到终端正在打印日志。

如果现在想要将此应用程序部署到 AWS 上，这会大大简化一些过程。现在可以利用 AWS 的 ECS 服务的一些命令来部署和运行我们的容器。

同样的，如果想要使用 Google 云，我们可以将其部署到 Google 的容器产品中，无需额外的工作！这只是突出 Docker 化的巨大好处之一。

### 前端为什么不使用 Docker

在这一点上，你可能想知道为什么不对 `frontend/` 应用程序做同样的事情？原因是我们打算将前端应用部署到 AWS S3 服务。

当部署上线时，前端不需要任何花哨的服务，我们只需要能够可靠地提供构建的前端文件。

### 总结

因此，在本节中，我们设法将 Docker 添加到后端应用程序中，这对持续开发和部署的人员有益。