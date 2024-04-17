# 1 React入门

## 1.1 安装引入

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>hello_react</title>

    <!--    引入react核心库-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react.development.js"></script>
    <!--    引入react-dom，用于支持react操作DOM-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react-dom.development.js"></script>
    <!--    引入babel，用于将jsx转为js-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/babel.min.js"></script>
</head>
<body>
    <!-- 准备好一个容器 -->
    <div id="test"></div>

    <script type="text/babel"> /* 此处写babel */
        // 1.创建虚拟DOM
        const VDOM = <h1>Hello, React</h1>
        // 2.渲染DOM到页面
        ReactDOM.render(VDOM, document.getElementById('test'))
    </script>
</body>
</html>
```

babel用来将react和es6转换为js

### 1.1.1 使用js创建虚拟DOM

这种方式在嵌套标签的时候很繁琐。使用jsx的方式可以直接写标签

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>hello_react</title>

    <!--    引入react核心库-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react.development.js"></script>
    <!--    引入react-dom，用于支持react操作DOM-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react-dom.development.js"></script>
    <!--    引入babel，用于将jsx转为js-->
<!--    <script crossorigin="anonymous" type="text/javascript" src="../js/babel.min.js"></script>-->
</head>
<body>
    <!-- 准备好一个容器 -->
    <div id="test"></div>

    <script type="text/javascript"> /* 此处写babel */
        // 1.创建虚拟DOM
        const VDOM = React.createElement('h1',{id:'title'}, 'hello')
        // 2.渲染DOM到页面
        ReactDOM.render(VDOM, document.getElementById("test"))
    </script>
</body>
</html>
```

## 1.2 JSX语法

### 1.2.1 取值

1. 定义虚拟DOM，不要加引号
2. 标签中混入js表达式时要用`{}`
3. 样式的类名指定使用`className`
4. 内联样式要用`style={{key:value}}`的形式，且小驼峰
5. 虚拟DOM必须只有一个根标签
6. 原生标签小写开头；自定义组件大写开头

```html
<body>
    <!-- 准备好一个容器 -->
    <div id="test"></div>

    <script type="text/babel"> /* 此处写babel */
    const hello = 'Hello, React'
    // 1.创建虚拟DOM
    const VDOM = (
        <span>
            <h1>{hello.toLocaleLowerCase()}</h1>
        </span>
    )
    // 2.渲染DOM到页面
    ReactDOM.render(VDOM, document.getElementById('test'))
    </script>
</body>
```

### 1.2.2 遍历标签

```jsx
<body>
    <!-- 准备好一个容器 -->
    <div id="test"></div>

    <script type="text/babel"> /* 此处写babel */
    const data = ['Angular', 'React', 'Vue']
    // 1.创建虚拟DOM
    const VDOM = (
        <div>
            <h1>前端js框架列表</h1>
            <ul>
                {
                    data.map((item, index) => {
                        return <li key={index}>{item}</li>
                    })
                }
            </ul>
        </div>
    )
    // 2.渲染DOM到页面
    ReactDOM.render(VDOM, document.getElementById('test'))
    </script>
</body>
```

## 1.3 模块与组件

### 1.3.1 模块

对一个模块拆分为一个js文件

### 1.3.2 组件

实现一个局部功能的所有资源（html\js\css\image）

# 2 React面向组件编程

## 2.1 组件

组件最重要的三个属性：

- refs
- state
- props

### 2.1.1 函数式组件

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>hello_react</title>

    <!--    引入react核心库-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react.development.js"></script>
    <!--    引入react-dom，用于支持react操作DOM-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react-dom.development.js"></script>
    <!--    引入babel，用于将jsx转为js-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/babel.min.js"></script>
</head>
<body>
    <!-- 准备好一个容器 -->
    <div id="test"></div>

    <script type="text/babel"> /* 此处写babel */
        // 1. 创建函数式组件
        function Demo() {
            return <h2>函数式定义组件</h2>
        }

        // 2.渲染组件
        ReactDOM.render(<Demo/>, document.getElementById('test'))
        // 执行render之后
        // 1. React解析组件标签，找到了Demo组件
        // 2. 发现组件是使用函数定义的，随后调用该函数，将返回的虚拟DOM转为真实DOM
    </script>
</body>
</html>
```

### 2.1.2 类式组件

类式组件

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>hello_react</title>

    <!--    引入react核心库-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react.development.js"></script>
    <!--    引入react-dom，用于支持react操作DOM-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react-dom.development.js"></script>
    <!--    引入babel，用于将jsx转为js-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/babel.min.js"></script>
</head>
<body>

    <div id="test"></div>

    <script type="text/babel">
        // 1.创建类式组件
        class ClassDemo extends React.Component{
            render() {
                return <h2>我是函数式组件，适用于复杂组件</h2>
            }
        }

        // 2.渲染
        ReactDOM.render(<ClassDemo/>, document.getElementById('test'))
        // 执行了ReactDOM.render
        // 1.发现组件是类定义的，随后new出来该类的实例，并通过实例调用原型链上的render方法
        // 2.将render返回的虚拟DOM转换为真实的DOM
    </script>
</body>
</html>
```

## 2.2 组件核心属性--state

- 状态切换
- 事件监听

```jsx
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>hello_react</title>

    <!--    引入react核心库-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react.development.js"></script>
    <!--    引入react-dom，用于支持react操作DOM-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react-dom.development.js"></script>
    <!--    引入babel，用于将jsx转为js-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/babel.min.js"></script>
</head>
<body>
    <div id="test"></div>

    <script type="text/babel">
        // 1.创建组件
        class Weather extends React.Component {
            // state在类的实例对象上，想要修改，先在constructor中定义相关属性
            constructor(props) {
                super(props);

                this.state = {
                    isHot : true,
                    wind: '微风'
                }

                this.handleClick = this.handleClick.bind(this) // 解决函数里this指向问题
            }

            handleClick() {
                // handleClick放在Weather的原型对象上，供实例使用
                // 通过Weather实例调用handleClick时，handleClick中的this就是Weather实例
                // 也就是只有通过Weather实例对象调用该方法时，这里面的this才是实例对象
                // 由于handleClick是最为onClick的回调，不是实例调用的，是直接调用
                // 类中的方法默认开启了局部的严格模式。this就是undefined

                // 状态里的数据不能直接更改 this.state.isHot = false 错误的
                this.setState({
                    isHot: !this.state.isHot
                })
            }


            render() {
                // render里的this是组件实例对象
                // 如果这里this.handleClick()带括号，在载入组件的时候就会被调用一次
                return <h1 onClick={this.handleClick}>今天天气很{this.state.isHot ? '炎热' : '凉爽'}, {this.state.wind}</h1>
            }
        }

        // 2.渲染组件
        ReactDOM.render(<Weather/>, document.getElementById('test'))
    </script>
</body>
</html>
```

## 2.3 组件核心属性--props

- 外部传值
- 批量传值
- 类型限制
- props只读，不能修改

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>hello_react</title>

    <!--    引入react核心库-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react.development.js"></script>
    <!--    引入react-dom，用于支持react操作DOM-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react-dom.development.js"></script>
    <!--    引入babel，用于将jsx转为js-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/babel.min.js"></script>
<!--    类型检查-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/prop-types.js"></script>
</head>
<body>
    <div id="test1"></div>
    <div id="test2"></div>
    <div id="test3"></div>
    <script type="text/babel">
        // 创建组件
        class Person extends React.Component {
            render() {
                // props 依靠外部传值
                return (
                    <ul>
                        <li>姓名：{this.props.name}</li>
                        <li>性别：{this.props.sex}</li>
                        <li>年龄：{this.props.age + 1}</li>
                    </ul>
                )
            }
        }

        // 渲染
        // <Person/> 标签中传入的值就是外部值
        // 如果传入的年龄是字符串，变化后会是字符串拼接 801，如果传入的是数字{80}，就是 81
        ReactDOM.render(<Person name="tom" sex="男" age={80}/>, document.getElementById('test1'))
        // 批量传值
        const p = {name: 'haha', sex: '女', age: '90'}
        ReactDOM.render(<Person {...p}/>, document.getElementById('test2'))

        // 传值进行必要性和数据类型限制
        Person.propTypes = { // 给类加属性规则
            name: PropTypes.string.isRequired,
            sex: PropTypes.string,
            age: PropTypes.number
        }
        ReactDOM.render(<Person name="lisa" sex="男" age={85}/>, document.getElementById('test3'))
    </script>
</body>
</html>
```

## 2.4 组件核心属性--refs

- 字符串形式（可能废弃）
- 回调形式
- React.creatRef()：该方法创建的ref只能保存一个标签。多个标签需要创建多个

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>hello_react</title>

    <!--    引入react核心库-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react.development.js"></script>
    <!--    引入react-dom，用于支持react操作DOM-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react-dom.development.js"></script>
    <!--    引入babel，用于将jsx转为js-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/babel.min.js"></script>
</head>
<body>
    <div id="test"></div>
    <script type="text/babel">
        // 组件
        class MyDemo extends React.Component {
            constructor(props) {
                super(props);

                this.handleClick = this.handleClick.bind(this)
                this.handleBlur = this.handleBlur.bind(this)
                this.handleBlur2 = this.handleBlur2.bind(this)

                // React.createRef()调用后可以返回一个容器，该容器可以存储被ref标识的节点
                // 该myrefs中只能存入一个标签。后标识ref标签的会顶掉前面
                this.myrefs = React.createRef();
            }

            // 字符串形式
            handleClick() {
                console.log(this)
                console.log(this.refs.input1.value)
            }

            // 回调形式
            handleBlur() {
                console.log(this.refs.input2.value)
            }

            // createRef
            handleBlur2() {
                console.log(this.myrefs.current.value)
            }

            // 字符串形式：ref={"input"}, 输出后在refs中{input1: input} key是ref属性的值，value是所在的标签名
            // 回调形式：ref={(currentNode)=>{this.refs.input2 = currentNode}}, 传入的第一个参数就是标签本身。相当于在refs中添加了一个属性input1，代表标签a
            // React.creatRef()
            // ref就是打标签，相当于原生id
            render() {
                return (
                    <div>
                        <input ref={"input1"} type="text" placeholder="点击按钮提示数据"/>
                        <button ref={"btn1"} onClick={this.handleClick}>点我提示左侧数据</button>
                        <input ref={(currentNode)=>(this.refs.input2 = currentNode)} onBlur={this.handleBlur} type="text" placeholder="失去焦点提示数据"/>
                        <input ref={this.myrefs} onBlur={this.handleBlur2} type="text" placeholder="失去焦点提示数据"/>
                    </div>
                )
            }
        }

        // 渲染
        ReactDOM.render(<MyDemo/>, document.getElementById('test'))
    </script>
</body>
</html>
```

## 2.5 组件事件

`<div>`标签中的事件`onClick`和`onBlur`等等，最终都会附加在`<div>`标签上

这就是事件委托--冒泡

`event.target`是发生事件的事件源头

```html
<body>
<div id="test"></div>
<script type="text/babel">
    // 组件
    class MyDemo extends React.Component {
        constructor(props) {
            super(props);

            this.handleClick = this.handleClick.bind(this)
        }

        handleClick(event) {
            console.log(this)
            console.log(event.target.value)
        }

        render() {
            return (
                <div>
                    <input onClick={this.handleClick} type="text" placeholder="点击按钮提示数据"/>
                    <button onClick={this.handleClick}>点我提示左侧数据</button>
                </div>
            )
        }
    }

    // 渲染
    ReactDOM.render(<MyDemo/>, document.getElementById('test'))
</script>
</body>
```

## 2.6 表单

包含表单的组件分类

- 非受控组件

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>hello_react</title>

    <!--    引入react核心库-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react.development.js"></script>
    <!--    引入react-dom，用于支持react操作DOM-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react-dom.development.js"></script>
    <!--    引入babel，用于将jsx转为js-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/babel.min.js"></script>
</head>
<body>
    <div id="test"></div>
    <script type="text/babel">
        // 创建组件
        class MyDemo extends React.Component {
            constructor(props) {
                super(props);

                this.handleSubmit = this.handleSubmit.bind(this)
            }
            // 非受控组件
            // 输入类DOM节点（单选、复选、输入框等等），点击提交现用现取就是非受控组件
            handleSubmit(event) {
                event.preventDefault() // 阻止默认事件；form提交的默认事件被阻止
                alert(`用户名：${this.username.value}，密码：${this.password.value}`)
            }

            render() {
                return(
                    <form action={"/"} onSubmit={this.handleSubmit}>
                        用户名：<input ref={currentNode => this.username = currentNode} name={"username"} type={"text"} placeholder="输入用户名"/>
                        密码：<input ref={currentNode => this.password = currentNode} name={"password"} type={"password"} placeholder="输入密码"/>
                        <button type={"submit"}>提交</button>
                    </form>
                )
            }
        }
        // 渲染
        ReactDOM.render(<MyDemo />, document.getElementById('test'))
    </script>
</body>
</html>
```

- 受控组件

```html
<body>
    <div id="test"></div>
    <script type="text/babel">
        // 创建组件
        class MyDemo extends React.Component {
            constructor(props) {
                super(props);

                this.handleSubmit = this.handleSubmit.bind(this)
                this.handleSaveUsername = this.handleSaveUsername.bind(this)
                this.handleSavePassword = this.handleSavePassword.bind(this)

                this.state = {
                    username : '初始',
                    password : '初始'
                }
            }
            // 受控组件
            // 随着输入将数据保存到了state
            handleSubmit(event) {
                event.preventDefault() // 阻止默认事件；form提交的默认事件被阻止
                alert(`用户名：${this.state.username}，密码：${this.state.password}`)
            }

            handleSaveUsername(event) {
                this.setState({
                    username: event.target.value
                })
            }

            handleSavePassword(event) {
                this.setState({
                    password: event.target.value
                })
            }

            render() {
                return(
                    <form action={"/"} onSubmit={this.handleSubmit}>
                        用户名：<input onChange={this.handleSaveUsername} name={"username"} type={"text"} placeholder="输入用户名"/>
                        密码：<input onChange={this.handleSavePassword} name={"password"} type={"password"} placeholder="输入密码"/>
                        <button type={"submit"}>提交</button>
                    </form>
                )
            }
        }
        // 渲染
        ReactDOM.render(<MyDemo />, document.getElementById('test'))
    </script>
</body>
```

## 2.7 柯里化

在事件函数中传参时，会因为在渲染时就会调用，因此写成回调函数形式

- 在事件绑定的时候直接写箭头函数，然后调用对应事件函数（这个事件函数不用返回函数，直接操作）
- 直接调用事件函数的话，需要让这个函数返回一个处理函数

就是回调

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>hello_react</title>

    <!--    引入react核心库-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react.development.js"></script>
    <!--    引入react-dom，用于支持react操作DOM-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react-dom.development.js"></script>
    <!--    引入babel，用于将jsx转为js-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/babel.min.js"></script>
</head>
<body>
    <div id="test"></div>
    <script type="text/babel">
        // 如果有多个输入组件，创建多个handleXXXX函数是不明智的
        // 将他们做成一个
        // 函数柯里化，通过调用函数的方式再返回函数

        // 创建组件
        class MyDemo extends React.Component {
            constructor(props) {
                super(props);

                this.handleSubmit = this.handleSubmit.bind(this)
                this.handleFormat = this.handleFormat.bind(this)
                this.state = {
                    username : '初始',
                    password : '初始'
                }
            }
            // 受控组件
            // 随着输入将数据保存到了state
            handleSubmit(event) {
                event.preventDefault() // 阻止默认事件；form提交的默认事件被阻止
                alert(`用户名：${this.state.username}，密码：${this.state.password}`)
            }

            // 然后返回一个函数，用来给onChange回调
            // 使用[]来对dataType进行取值
            // 使用function匿名函数，该函数内部无法取得this指针
            handleFormat(dataType) {
                return (event) => { // 这个返回回去给onChange调用，所以这里就被传进来event
                    this.setState({
                        [dataType]: event.target.value
                    })
                }
            }

            render() {
                return(
                    <form action={"/"} onSubmit={this.handleSubmit}>
                        用户名：<input onChange={this.handleFormat('username')} name={"username"} type={"text"} placeholder="输入用户名"/>
                        密码：<input onChange={this.handleFormat('password')} name={"password"} type={"password"} placeholder="输入密码"/>
                        <button type={"submit"}>提交</button>
                    </form>
                )
            }
        }
        // 渲染
        ReactDOM.render(<MyDemo />, document.getElementById('test'))
    </script>
</body>
</html>
```

## 2.8 组件生命周期

- componentDidMount：组件刚刚被加载就会执行
- componentWillUnmount：组件将要被卸载就会执行
- componentDidUpdate：state改变就会执行

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>hello_react</title>

    <!--    引入react核心库-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react.development.js"></script>
    <!--    引入react-dom，用于支持react操作DOM-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react-dom.development.js"></script>
    <!--    引入babel，用于将jsx转为js-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/babel.min.js"></script>
</head>
<body>
    <div id="test"></div>
    <script type="text/babel">
        // 组件
        class MyDemo extends React.Component {
            constructor(props) {
                super(props);

                this.state = {
                    opacity : 1 // 控制透明度
                }
            }

            // 组件
            // 组件挂在完毕调用一次
            componentDidMount() {
                this.timer = setInterval(()=>{
                    let x = this.state.opacity
                    x = x < 0 ? 1 : x-0.1
                    this.setState({
                        opacity: x
                    })
                }, 500)
            }

            // 组件卸载后，但是开始挂在的定时器还在，导致不能更新
            // 将要卸载组件之前会执行该函数
            componentWillUnmount() {
                clearInterval(this.timer)
            }

            // 手动卸载组件
            handleClick() {
                ReactDOM.unmountComponentAtNode(document.getElementById('test'))
            }

            render(){
                return (
                    <div>
                        <h2 style={{opacity: this.state.opacity}}>React学不会怎么办</h2>
                        <button onClick={this.handleClick}>卸载组件</button>
                    </div>
                )
            }
        }
        //渲染
        ReactDOM.render(<MyDemo/>, document.getElementById('test'))

    </script>
</body>
</html>
```

## 2.9 diffing

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>hello_react</title>

    <!--    引入react核心库-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react.development.js"></script>
    <!--    引入react-dom，用于支持react操作DOM-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/react-dom.development.js"></script>
    <!--    引入babel，用于将jsx转为js-->
    <script crossorigin="anonymous" type="text/javascript" src="../js/babel.min.js"></script>
</head>
<body>
    <div id="test"></div>
    <script type="text/babel">
        // 组件
        class MyDemo extends React.Component {
            constructor(props) {
                super(props);

                this.state = {
                    date : new Date()
                }
            }

            // 组件
            // 组件挂在完毕调用一次
            componentDidMount() {
                this.timer = setInterval(()=>{
                    this.setState({
                        date: new Date()
                    })
                }, 1000)
            }

            // 组件卸载后，但是开始挂在的定时器还在，导致不能更新
            // 将要卸载组件之前会执行该函数
            componentWillUnmount() {
                clearInterval(this.timer)
            }

            // 通过diffing算法对比DOM树，发现有些DOM是不变的，因此在render的时候不会再次刷新，只会刷新变化的DOM
            // input框虽然在h2中，但是input也不会被更新。diffing算法会多层对比
            // diff算法是根据DOM的key进行对比。对比时如果同一个key的数据不同。就会重新渲染。该key可以人为指定。
            render(){
                return (
                    <div>
                        <h2>hello</h2>
                        <h2>
                            现在是：{this.state.date.toTimeString()}
                            <input/>
                        </h2>
                    </div>
                )
            }
        }
        //渲染
        ReactDOM.render(<MyDemo/>, document.getElementById('test'))

    </script>
</body>
</html>
```

# 3 脚手架

react脚手架需要借助webpack

## 3.1 使用create-react-app创建react应用

### 3.1.1 react脚手架

1. 脚手架方便快速创建一个基于xxx库的模板项
   1. 包含了素有需要的配置（语法检查、jsx编译、devServer...）
   2. 下载好了所有相关的依赖
   3. 可以直接运行一个简单效果
2. react提供一个用于创建react项目的脚手架库：create-react-app
3. 项目整体技术架构为：react+webpack+es6+eslint
4. 使用脚手架开发：模块化、组件化、工程化

### 3.1.2 创建项目并启动

```shell
npm config set registry https://registry.npm.taobao.org # 淘宝镜像
npm config set registry https://registry.npmjs.org # 原始镜像
npm config set prefix xxx # 全局包安装地址
npm install -g packagename # 全局安装
```

1. 全局安装：npm install -g create-react-app
2. 切换到向创建项目的目录，使用命令：create-react-app hello-react
3. 进入项目文件夹：cd hello-react
4. 启动项目：npm start

执行了步骤2之后

```shell
npm start
	Starts the development server.

npm run build
	Bundles the app into static files for production.

npm test
	Starts the test runner.

npm run eject
	Removes this tool and copies build dependencies, configuration files
and scripts into the app directory. If you do this, you can’t go back!
```



### 3.1.3 react脚手架项目结构

```
- public 静态资源文件夹
	- favicon.icon 网站页签图标
	- index.html 主页图
	- logo192.png logo图
	- logo521.png logo图
	- manifest.json 应用加壳的配置文件
	- robots.txt 爬虫协议文件
- 源码文件夹
	- App.css App组件的样式
	- App.js App组件
	- App.test.js 测试文件
	- index.css 样式
	- index.js webpack入口文件
	- logo.svg logo图
	- reportWebVitals.js 页面性能分析文件（需要web-vitals库支持）
	- setypTests.js 应用整体测试
```

public\index.html

```html
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8" />
<!--    指定网页页签图标-->
<!--    %PUBLIC_URL%代表了public文件夹路径-->
    <link rel="icon" href="%PUBLIC_URL%/favicon.ico" />
<!--    用于开启理想视口，用于做移动端网页适配-->
    <meta name="viewport" content="width=device-width, initial-scale=1" />
<!--    用于配置浏览器页签+地址栏的颜色(仅支持安卓手机浏览器)-->
    <meta name="theme-color" content="#123400" />
<!--    描述网站信息，简介-->
    <meta
      name="description"
      content="Web site created using create-react-app"
    />
<!--   ios网页添加到主屏图标 -->
    <link rel="apple-touch-icon" href="%PUBLIC_URL%/logo192.png" />
<!--    应用加壳-->
    <link rel="manifest" href="%PUBLIC_URL%/manifest.json" />
    <title>React App</title>
  </head>
  <body>
<!--  如果浏览器不支持js，这句话就会显示-->
    <noscript>You need to enable JavaScript to run this app.</noscript>
    <div id="root"></div>
  </body>
</html>

```

src\index.js

```js
import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>    <!-- React检查代码规范-->
    <App />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
```

## 3.2 Hello React

### 3.2.1 组件模块化

文件结构

```
src
	components
		Hello
			Hello.jsx
			Hello.css
	App.js
	index.js
```



### 3.2.2 样式模块化

样式没有模块化的时候，如果有同名的样式，标签中`className`同名

```css
.title{

}
```

后渲染的样式会覆盖前面的样式。如果更换名称，在组件很多的时候会很麻烦

**模块化**

组件文件夹中的`css`文件，其原本引入方式是`import './Hello.css'`；通过给`css`文件更名为`Hello.module.css`，在引入时使用`import hello from './Hello.module.css'`

```jsx
import React, {Component} from "react";
import hello from './Hello.module.css'

class Hello extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <h2 className={hello.title}>Hello, React</h2>
        )
    }
}

export default Hello
```

### 3.2.3 组件化编码流程

1. 拆分组件：拆分界面，抽取组件
2. 实现静态组件：使用组件实现静态页面效果
3. 实现动态组件
   1. 动态显示初始化数据
      1. 数据类型
      2. 数据名称
      3. 保存在哪个组件好
   2. 交互

### 3.2.4 组件组合使用ToDoList

- 父组件给子组件传值（props、state）
  - 传函数
    - 父组件中返回箭头函数（传参防止渲染调用）
    - 子组件中调用形式
      - 直接对事件进行绑定`this.props.xxx(xxx)`
      - 事件处绑定箭头函数，在箭头函数中调自己的函数，自己的函数中调`this.props.xxx(xxx)`是调用不成功的
      - 父组件中如果不是箭头函数的形式，二中可调用成功
      - 详情见代码TodoList的例子
- 子组件给父组件传值
  - 回调函数（父通过props传给子一个函数，更新数据后子调该函数）
  - state

- 添加一个新的生成UUID的库（npm i nanoid）
- 样式交互
  - 鼠标事件
  - 鼠标悬浮高亮
  - 显示删除按钮
- 状态在哪里，修改状态的方法就在哪里
  - 从App.js传进去的复选框状态，如果只在Item标签修改，App组件拿不到子孙组件的状态。从App组件传进去一个回调函数，在App中修改该状态再传进去
- 对props传入的对象进行类型限制



# 4 React Ajax

jQuery：引入jQuery来操作ajax比较笨重

axios：选择该库

- 安装axios(npm i axios)

## 4.1 跨域问题

启动一个服务器，然后进行ajax请求

```js
// 绑定在按钮上的一个回调
getStudentData() {
    // 请求成功返回Promise实例
    axios.get('http://localhost:9546/test1').then(
        response => {
            // 取回来的数据放在response.data
            console.log('成功了', response.data)
        },
        error => {
            console.log('失败了', error)
        }
    )
}
```

控制台输出如下错误。客户端从3000端口出进行请求，到9546端口，正常出去了；响应回来的时候被Ajax引擎拦截了

```
Access to XMLHttpRequest at 'http://localhost:9546/test1' from origin 'http://localhost:3000' has been blocked by CORS policy: No 'Access-Control-Allow-Origin' header is present on the requested resource.
```

通过代理方式解决跨域问题。

代理中间人也开在3000端口。在`package.json`中配置`"proxy":"要给哪个服务器发请求"`

```json
"proxy": "http://localhost:9546"
```

然后在Ajax请求中修改地址

```js
// 这里需要的基础 axios、Promise、NodeJS
getStudentData() {
    // 请求成功返回Promise实例
    // 端口不一样，出现了跨域问题。请求发送成功，数据没能返回。
    // 通过代理解决，将地址修改为package.json中的代理地址
    axios.get('http://localhost:3000/test1').then(
        response => {
            // 取回来的数据放在response.data
            console.log('成功了', response.data)
        },
            error => {
            console.log('失败了', error)
        }
    )
}
```

**在这个代理中，如果请求的自己端口有该资源，就不会走代理端口**

如果需要配置两个代理地址，就不能在`package.json`中做配置了

在`src`文件夹下创建`setupProxy.js`，然后按照下方的方式进行配置；

老版本使用`proxy`而不是`createProxyMiddleware`

```js
const {createProxyMiddleware} = require("http-proxy-middleware");

module.exports = function(app) {
    app.use(
        createProxyMiddleware(
            '/api1', { // 请求路径中有api1就会走目标代理
                target: 'http://localhost:9546',
                changeOrigin: true, // 控制服务器中收到的请求头中Host字段的值是原地址
                pathRewrite: {'^/api1':''} // 将匹配到的这个路径智控，方便跳转真实路径
            }
        ),createProxyMiddleware(
            '/api2', { // 请求路径中有api1就会走目标代理
                target: 'http://localhost:9547',
                changeOrigin: true,
                pathRewrite: {'^/api2':''} // 将匹配到的这个路径智控，方便跳转真实路径
            }
        ),
    )
}
```

react ajax请求代码

```js
    // 这里需要的基础 axios、Promise、NodeJS
    getStudentData() {
        // 请求成功返回Promise实例
        // 端口不一样，出现了跨域问题。请求发送成功，数据没能返回。
        // 通过代理解决，将地址修改为package.json中的代理地址
        // 填入api1代表将要转到哪个代理
        axios.get('http://localhost:3000/api1/test1').then(
            response => {
                // 取回来的数据放在response.data
                console.log('成功了', response.data)
            },
            error => {
                console.log('失败了', error)
            }
        )
    }
```

## 4.2 github搜索

## 4.3 消息发布订阅

普通传值方法比较麻烦，使用`PubSubJS`消息队列用来发布订阅消息

```
npm i pubsub-js
```

[尚硅谷71-73](https://www.bilibili.com/video/BV1wy4y1D7JT?p=71&vd_source=143acef25d8161c363937654b7802b50)



# 5 路由

## 5.1 SPA应用

SPA（Single Page Application）单页应用。

- 整个应用只有一个html进行展示，所有得页面切换效果都是由React操作DOM完成。

- 单页应用不会刷新页面（刷新按钮不会转，路径不会变）

## 5.2 路由

- 什么是路由？
  - 一个路由就是一个映射关系（key 为路径， value 可能是function或component）
- 路由分类
  - 后端路由
    - 路径：处理函数
    - http.handleFunc()
  - 前端路由
    - 路径：组件
    - <Route path="/test" component{Test}>

### 5.2.1 路由基本使用

安装`react-router`库，该库有三个版本（web, native, any），

- 安装web(npm i react-router-dom)

内置组件

- <BrowserRouter>
- <HashRouter>
- <Route>
- <Redirect>
- <Link>
- <NavLink>
- <Switch>

其它

- history对象
- match对象
- withRouter函数

见案例

### 5.2.2 路由

在`index.js`文件中引入`BrowserRouter`，然后使用该标签包裹`App`

```js
import React from "react";
import ReactDOM from 'react-dom/client'
import {BrowserRouter} from "react-router-dom";
import App from './App'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <BrowserRouter>
        <App/>
    </BrowserRouter>
);
```

需要使用路由的地方

跳转位置，选项卡使用`NavLink`标签替换传统的`<a>`，`to`替换传统的`href`

```
<div className={"link-group"}>
    {/*路由链接*/}
    <NavLink className={"list-group-item"} to={"/about"}>About</NavLink>
    <NavLink className={"list-group-item"} to={"/home"}>Home</NavLink>
</div>
```

展示内容的位置注册路由

使用`<Routes>`标签和`<Route>`标签，`path`属性中填写路由路径，`element`展示路由后要展示的组件

```
<div className={"panel-body"}>
    {/*    呈现路由组件的位置注册路由*/}
    <Routes>
        <Route path={"/about"} element={<About/>}/>
        <Route path={"/home"} element={<Home/>}/>
    </Routes>
</div>
```

### 5.2.3 重定向

`<Navigate>`，出发了该标签，就会自动跳转到对应路由路径，渲染对应组件

```
export default function Home() {
    const [sum, setSum] = useState(1)
    return (
        <div>
            <h3>我是Home得内容</h3>
            {sum === 2 ? <Navigate to={"/about"} replace={false}/> : <h4>当前sum值是：{sum}</h4>}
            <button onClick={()=>{setSum(2)}}>点我将sum变为2</button>
        </div>
    )
}
```

[129-144 Router6相关](https://www.bilibili.com/video/BV1wy4y1D7JT?p=129&spm_id_from=pageDriver&vd_source=143acef25d8161c363937654b7802b50)



# 6 UI组件库

- material-ui
- [ant-design](https://ant.design/index-cn)

安装(npm -i antd)

[组件](https://ant.design/components/overview-cn/)

找到想要的组件，直接去官网找代码

需要引入组件和样式

```js
import {Button} from 'antd'
import 'antd/dist/antd.css'
```

有时候只需要引入部分样式或者定制，这需要进行配置，详情见官网

[94-96 antd相关](https://www.bilibili.com/video/BV1wy4y1D7JT?p=94&vd_source=143acef25d8161c363937654b7802b50)

# 7 redux

## 7.1 文档

- 英文文档：https://redus.js.org
- 中文文档：http://www.redux.org.cn
- Github：https://github.com/reactjs/redux

`redux`是一个专门用于做状态管理的JS库（不是react插件库），可以用在三大前端框架中。集中式管理react应用中多个组件共享的状态

什么时候需要使用`redux`

- 某个组件的状态，需要让其他组件随时拿到（共享）
- 一个组件需要改变另一个组件的状态（通信）
- 尽量不用

[97-114 redux相关](https://www.bilibili.com/video/BV1wy4y1D7JT?p=97&vd_source=143acef25d8161c363937654b7802b50)





# 8 打包项目

将`react`项目代码打包为js代码



# 附录

## I js类

js类

```js
<body>
    <script type="text/javascript">
        // 创建一个Person类
        class Person {
            // 构造器方法
            constructor(name, age) {
                this.name = name
                this.age = age
            }

            // 一般方法
            speak() {
                //speak方法放在了哪里？类的原型对象上，供实例使用
                console.log(`我叫${this.name}, 今年${this.age}`) // 注意是`而不是'
            }
        }

        //创建一个Person实例对象
        const p1 = new Person('tom', 18)
        const p2 = new Person('jerry', 19)

        console.log(p1)
        console.log(p2)
        p1.speak()
        p2.speak()

        // 创建Student类
        class Student extends Person {
            constructor(name, age, grade) {
                super(name, age);
                this.grade = grade
            }
        }
        const s1 = new Student('小张',15, '高一')
        console.log(s1)
        s1.speak() // 学生的说话对象在对象原型链上
    </script>
</body>
```

## II 原生事件绑定

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>hello_react</title>
</head>
<body>
    <button id="btn1">按钮1</button>
    <button id="btn2">按钮2</button>
    <button onclick="demo()">按钮3</button>

    <script type="text/javascript">
        const btn1 = document.getElementById('btn1')
        btn1.addEventListener('click', ()=>{
            alert('按钮1被点击了')
        })

        const btn2 = document.getElementById('btn2')
        btn2.onclick = () => {
            alert('按钮2被点击了')
        }

        function demo() {
            alert('按钮3被点击了')
        }
    </script>
</body>
</html>
```

## III 类中this的指向

```html
<body>

    <div id="test"></div>
    <script type="text/javascript">
        // 类中this的指向
        class Person {
            constructor(name, age) {
                this.name = name
                this.age = age
            }

            speak() { // babel翻译默认开了严格模式，this就是undefined
                console.log(this)
            }
        }

        const p1 = new Person('tom',18)
        p1.speak() // 通过实例调用speak方法
        const x = p1.speak
        x() // 这里不是实例对象调用，26行this变成了undefined


    </script>
</body>
```

