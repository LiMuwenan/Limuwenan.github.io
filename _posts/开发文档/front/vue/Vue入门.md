# 1 简介

[Vue 官方文档](https://cn.vuejs.org/v2/guide/)

## 1.1 Hello World

**使用 Vue 之前需要引入 Vue.js 并且进行 Vue 对象的实例化**

**Vue 实例和 Dom 容器是一一对应的关系**

固定的 `Hello world` 内容

```vue
<body>
    <div id = "root">
        <h1>Hello,world!</h1>
    </div>

    <script type="text/javascript">
        Vue.config.productionTip = false;//阻止 vue 在启动时生成生产提示

        //创建vue实例
        const mvvm = new Vue({
            el:'#root',//当前vue实例为哪个容器服务
        });

    </script>
</body>
```

将 `Hello world` 内容变为动态的

```vue
<body>
    <div id = "root">
        <h1>Hello,{{ name }}</h1>
        <h1>我的年龄是:{{age}}</h1>
    </div>

    <script type="text/javascript">
        Vue.config.productionTip = false;//阻止 vue 在启动时生成生产提示

        //创建vue实例
        const mvvm = new Vue({
            el:'#root',//当前vue实例为哪个容器服务
            data:{//data中用于存储数据，数据供el所指定的容器使用
                name:'ligen',
                age:18
            }
        });

    </script>
</body>
```

创建 `Vue` 实例时，可以采用上面对象式的写法，也可以采用函数式的写法

```vue
const mvvm = new Vue({
    el:'#root',//当前vue实例为哪个容器服务
    data:function(){
		return{
			name:'ligen',
			age:18,
		}
	}
});
```

**Vue 实例中（Vue 管理的函数）不要写箭头函数，写了箭头函数，Vue 实例中的 this 就不再代表 Vue 实例，而是变成了 Window**



# 2 模板语法

## 2.1 插值

原始文本

```html
<div id="root">
   <span>Hello,World!</span> 
</div>
```

通过使用插值语法，可以动态的改变内容

```vue
<div id="root">
   <span>Hello,{{content}}!</span> 
</div>
new Vue({
	el:"#root",
	data:{
		content:"ligen",
	}
})
```

上面的文本会动态的显示为 `Hello,ligen!`，只需要改变 `Vue` 对象中的 `content` 值

**不要对任何用户提供的内容使用插值语法，容易遭受XSS攻击**

同样的，在插值语法中，还可以使用 JavaScript 表达式

```vue
{{ number + 1 }}

{{ ok ? 'YES' : 'NO' }}

{{ message.split('').reverse().join('') }}
```

## 2.2 指令

指令是带有 `v-` 开头的前缀，被指令修饰的内容会被解释为一个 JavaScript 表达式

原始文本

```vue
<div id="root">
   <a href="url">我的年龄是:{{age}}</a>
</div>
```

浏览器将解释为连接到 `url` 地址

如果使用指令，改为下面这样

```vue
<div id="root">
   <a v-bind:href="url">我的年龄是:{{age}}</a>
</div>

new Vue({
	el:"#root",
	data:{
		content:"ligen",
		url:"https://www.baidu.com",
	}
})
```

浏览器会将 `url` 看作一个 `js` 表达式，将其解释为 `url` 变量的值



**注意：**

使用 `v-bind` 是一个单向的数据绑定，即当 `Vue` 实例的数据改变，会将使用了该实例数据的位置的值改变。但是如果反过来是不行的

```vue
<div id = "root">
    <h1>Hello,{{ name }}</h1>
    数据绑定：<input type="text" v-bind:name="name"/>
</div>

<script type="text/javascript">
    Vue.config.productionTip = false;//阻止 vue 在启动时生成生产提示

    //创建vue实例
    const mvvm = new Vue({
        el:'#root',//当前vue实例为哪个容器服务
        data:{//data中用于存储数据，数据供el所指定的容器使用
            name:'ligen',
            url:"https://www.baidu.com"
        }
    });
</script>
```

在 `input` 输入框中输入值是不会改变 `Hello,{{name}}` 的。但是如果使用 `v-model` 指令，当输入框的值改变时，`Vue` 实例中的 `name` 属性会跟着变化，进而影响 `Hello,{{name}}`

**`v-model` 只适用于表单元素**

### 2.2.1 动态参数

在 2.6.0 之后，可以使用动态参数

```vue
<!--
注意，参数表达式的写法存在一些约束，如之后的“对动态参数表达式的约束”章节所述。
-->
<div id="root">
    <a v-bind:[attributeName]="url"> ... </a>
</div>
new Vue({
	el:"#root",
	data:{
		attributeName:"href",
		url:"https://www.baidu.com",
	}
})
```

如果在一个 `Vue` 实例中有一个 `property` 是 `attributeName` ，并且值为 `href` ，这个绑定就相当于 `v-bind:href`

那么上面的语句会被翻译为

```vue
<a v-bind:href="https://www.baidu.com"> ... </a>
```

## 2.3 缩写

### 2.3.1 v-bind

```vue
<!-- 完整语法 -->
<a v-bind:href="url">...</a>

<!-- 缩写 -->
<a :href="url">...</a>

<!-- 动态参数的缩写 (2.6.0+) -->
<a :[key]="url"> ... </a>
```

### 2.3.2 v-on

```vue
<!-- 完整语法 -->
<a v-on:click="doSomething">...</a>

<!-- 缩写 -->
<a @click="doSomething">...</a>

<!-- 动态参数的缩写 (2.6.0+) -->
<a @[event]="doSomething"> ... </a>
```

# 3 计算属性和侦听器

[尚硅谷Vue2.0+Vue3.0全套教程丨vuejs从入门到精通](https://www.bilibili.com/video/BV1Zy4y1K7SH?p=19)

[官方文档](https://cn.vuejs.org/v2/guide/computed.html)

模板内的表达式非常便利，但是设计它们的初衷是用于简单运算的。在模板中放入太多的逻辑会让模板过重且难以维护。例如：

```vue
<div id="example">
  {{ message.split('').reverse().join('') }}
</div>
```

在这个地方，模板不再是简单的声明式逻辑。你必须看一段时间才能意识到，这里是想要显示变量 `message` 的翻转字符串。当你想要在模板中的多处包含此翻转字符串时，就会更加难以处理。

所以，对于任何复杂逻辑，你都应当使用**计算属性**。

## 3.1 计算属性

```vue
<div id="example">
    <p>Original message: "{{ message }}"</p>
    <p>Computed reversed message: "{{ reversedMessage }}"</p>
</div>
<script>
    var vm = new Vue({
        el: '#example',
        data: {
            message: 'Hello'
        },
        computed: {
            // 计算属性的 getter
            reversedMessage: function () {
                // `this` 指向 vm 实例
                return this.message.split('').reverse().join('')
            }
        }
    })
</script>
```

使用 `computed` 使得 `reversedMessage` 的值始终依赖 `message`

需要使用 `this` 指针来指向 `vm` 对象从而访问 `vm` 的属性

**注意：**

- 在调用计算属性的位置， `Vue` 会生成一个缓存，下一次的调用直接使用缓存中的值
- 第一次调用以及计算属性所依赖的值发生改变时都会调用计算属性中的 `get`



使用 `methods` 可以达到同样的效果，但是**计算属性是基于它们的响应式依赖进行缓存的**。只在相关响应式依赖发生改变时它们才会重新求值。这就意味着只要 `message` 还没有发生改变，多次访问 `reversedMessage` 计算属性会立即返回之前的计算结果，而不必再次执行函数。

上面的形式属于简写形式，全量形式：

```vue
computed: {
    // 计算属性的 getter
    fullName: {
        get(){
            // `this` 指向 vm 实例
            this.message.split('').reverse().join('')
        },
        set(){
            //set 方法
        }
    }
}
```

## 3.2 监视属性

```vue
<div id="demo">
    <p>Computed : "{{ fullName }}"</p>
</div>
<script>
    var vm = new Vue({
        el: '#demo',
        data: {
            firstName: 'Foo',
            lastName: 'Bar',
            fullName: 'Foo Bar'
        },
        watch: {
            firstName: function (val) {
                this.fullName = val + ' ' + this.lastName
            },
            lastName: function (val) {
                this.fullName = this.firstName + ' ' + val
            }
        }
    })
</script>
```

当 `firstName` 和 `lastName` 的值发生改变的时候，通过 `watch` 监听的 `fullName` 就会改变

`data` 属性出现嵌套时

```vue
<script>
    var vm = new Vue({
        el: '#demo',
        data: {
            numbers:{
                a:1,
            }
        },
        watch: {
            //错误形式
            a:{ },
            numbers.a:{ },
            //正确形式
            'numbers.a':{ },
        }
    })
</script>
```

如果想要监视 `numbers` 中所有属性的改变，需要添加 `deep` 属性

```vue
watch: {
    //错误形式
    numbers:{ },
    //正确形式
    numbers:{
    	deep:true,
    }
}
```





**区别：**

- 在同步任务中最好使用计算属性
- 监视属性中可以开启异步任务
- 被 `Vue` 所管理的函数最好写成普通函数，这样 `this` 的指向才是 `vm` 或组件实例对象
- 不被 `Vue` 所管理的函数（定时器回调函数、ajax 回调函数等），最好写成箭头函数，这样 `this`指针向外找最终找到 `vm` 对象



# 4 Class 与 Style 绑定

操作元素的 class 列表和内联样式是数据绑定的一个常见需求。因为它们都是 attribute，所以我们可以用 `v-bind` 处理它们：只需要通过表达式计算出字符串结果即可。不过，字符串拼接麻烦且易错。因此，在将 `v-bind` 用于 `class` 和 `style` 时，Vue.js 做了专门的增强。表达式结果的类型除了字符串之外，还可以是对象或数组。

## 4.1 Class

### 4.1.1 普通形式

使用 `v-bind` 可以动态的绑定 `class` 属性，且可以和 `html` 原生的属性 `class` 并存，然后合并

```vue
<div id="root"
        class="static"
        v-bind:class="{ active: isActive, 'text-danger': hasError }"//动态的
></div>
<script>
    const vm = new Vue({
        el:"#root",
        data:{
            isActive:true,
            hasError:false,
        }
    })
</script>
```

最终解释为

```vue
<div class="static active"></div>
```

上面的形式将样式内联定义在了模板中，也可以写在 `vm` 对象中；省略了内联形式，增加了自由度

```vue
<div id="root"
        class="static"
        v-bind:class="classObject"
></div>
<script>
    const vm = new Vue({
        el:"#root",
        data:{
            classObject:{
                active:true,
                'text-danger':false,
            }
        }
    })
</script>
```

### 4.1.2 计算属性

```vue
<script>
    const vm = new Vue({
        el:"#root",
        data:{
            // classObject:{
            //     active:true,
            //     'text-danger':false,
            // }
        },
        computed:{
            classObject:function(){
                return {
                    active:true,
                    'text-danger':false
                }
            }
        }
    })
</script>
```

### 4.1.3 数组形式

```vue
<div v-bind:class="[activeClass, errorClass]"></div>
<script>
    const vm = new Vue({
        el:"#root",
        data: {
          activeClass: 'active',
          errorClass: 'text-danger'
        }
    })
</script>
```

```vue
<div v-bind:class="arr"></div>
<script>
    const vm = new Vue({
        el:"#root",
        data: {
            arr:['active', 'text-danger'],
        }
    })
</script>
```



### 4.1.4 组件形式

当在一个自定义组件上使用 `class` property 时，这些 class 将被添加到该组件的根元素上面。这个元素上已经存在的 class 不会被覆盖。

例如，如果你声明了这个组件：

```vue
Vue.component('my-component', {
  template: '<p class="foo bar">Hi</p>'
})
```

然后在使用它的时候添加一些 class：

```vue
<my-component class="baz boo"></my-component>
```

HTML 将被渲染为：

```vue
<p class="foo bar baz boo">Hi</p>
```

对于带数据绑定 class 也同样适用：

```vue
<my-component v-bind:class="{ active: isActive }"></my-component>
```

当 `isActive` 为 truthy[[1\]](https://cn.vuejs.org/v2/guide/class-and-style.html#footnote-1) 时，HTML 将被渲染成为：

```vue
<p class="foo bar active">Hi</p>
```

## 4.2 style

### 4.2.1 对象形式

`v-bind:style` 的对象语法十分直观——看着非常像 CSS，但其实是一个 JavaScript 对象。CSS property 名可以用驼峰式 (camelCase) 或短横线分隔 (kebab-case，记得用引号括起来) 来命名：

```vue
<div v-bind:style="{ color: activeColor, fontSize: fontSize + 'px' }"></div>
<script>
    const vm = new Vue({
        el:"#root",
        data: {
          activeColor: 'red',
          fontSize: 30
        }
    })
</script>
```

直接绑定到一个样式对象通常更好，这会让模板更清晰：

```vue
<div v-bind:style="styleObject"></div>
<script>
    const vm = new Vue({
        el:"#root",
        data: {
          styleObject: {
            color: 'red',
            fontSize: '13px'
          }
        }
    })
</script>
```

同样的，对象语法常常结合返回对象的计算属性使用。

### 4.2.2 数组语法

`v-bind:style` 的数组语法可以将多个样式对象应用到同一个元素上：

```vue
<div v-bind:style="[baseStyles, overridingStyles]"></div>
```

### 4.2.3 自动添加前缀

当 `v-bind:style` 使用需要添加[浏览器引擎前缀](https://developer.mozilla.org/zh-CN/docs/Glossary/Vendor_Prefix)的 CSS property 时，如 `transform`，Vue.js 会自动侦测并添加相应的前缀。

### 4.2.4 多重值

> 2.3.0+

从 2.3.0 起你可以为 `style` 绑定中的 property 提供一个包含多个值的数组，常用于提供多个带前缀的值，例如：

```vue
<div :style="{ display: ['-webkit-box', '-ms-flexbox', 'flex'] }"></div>
```

这样写只会渲染数组中最后一个被浏览器支持的值。在本例中，如果浏览器支持不带浏览器前缀的 flexbox，那么就只会渲染 `display: flex`。



# 5 条件渲染

## 5.1 v-if

`v-if`

```vue
<div id="root">
    <h1 v-if="awesome">Vue is awesome!</h1>
    <h1 v-else>Vue is odd</h1>
</div>
<script>
    const vm = new Vue({
        el:"#root",
        data: {
            awesome:false,
        }
    })
</script>
```

标签中的内容根据 `awesome` 进行切换，`v-if`  可以配合 `v-else` 使用



使用 `template` 进行分组渲染，最终渲染结果会去掉 `template` 标签

```vue
<div id="root">
    <template v-if="ok">
        <h1>Title</h1>
        <p>Paragraph 1</p>
        <p>Paragraph 2</p>
    </template>
</div>
<script>
    const vm = new Vue({
        el:"#root",
        data: {
            ok:false,
        }
    })
</script>
```

## 5.2 key

这样渲染可以因为共用同一个 `label,input` 标签，点击按钮进行切换只会更换 `placeholder`

```vue
<template v-if="loginType === 'username'">
  <label>Username</label>
  <input placeholder="Enter your username">
</template>
<template v-else>
  <label>Email</label>
  <input placeholder="Enter your email address">
</template>
```

然后添加一个 `key`

```vue
<template v-if="loginType === 'username'">
  <label>Username</label>
  <input placeholder="Enter your username" key="username-input">
</template>
<template v-else>
  <label>Email</label>
  <input placeholder="Enter your email address" key="email-input">
</template>
```

这样 `label` 标签还在公用，`input` 都是各自的

## 5.3 v-show

`v-show` 用法与 `v-if` 相同

在需要频繁切换的地方使用`v-show`

`v-show` 不能配合 `template`

**v-if vs v-show**

`v-if` 是“真正”的条件渲染，因为它会确保在切换过程中条件块内的事件监听器和子组件适当地被销毁和重建。

`v-if` 也是**惰性的**：如果在初始渲染时条件为假，则什么也不做——直到条件第一次变为真时，才会开始渲染条件块。

相比之下，`v-show` 就简单得多——不管初始条件是什么，元素总是会被渲染，并且只是简单地基于 CSS 进行切换。

一般来说，`v-if` 有更高的切换开销，而 `v-show` 有更高的初始渲染开销。因此，如果需要非常频繁地切换，则使用 `v-show` 较好；如果在运行时条件很少改变，则使用 `v-if` 较好。



# 6 列表渲染

## 6.1 v-for

可以遍历数组、对象、字符串等

手动将一组信息展示在列表上

```vue
<div id="root">
    <ul>
       <li>{{ persons[0] }}</li>
       <li>{{ persons[1] }}</li>
       <li>{{ persons[2] }}</li>
    </ul>
</div>
<script>
    const vm = new Vue({
        el:"#root",
        data: {
            persons:[
                {id:'001',name:'ligen',age:18},
                {id:'002',name:'wd',age:19},
                {id:'003',name:'wl',age:12},
            ]
        }
    })
</script>
```

使用 `v-for`，从 `persons` 中获得元素，并展现。其中 `:key` 是取 `person.id` 作为这个标签的标识

```vue
<div id="root">
    <ul>
       <li v-for="person in persons" :key="person.id">{{ person }}</li>
    </ul>
</div>
```

也可以取其中的字段

```vue
<ul>
   <li v-for="person in persons">{{ person.name }}</li>
</ul>
```

隐藏内容，以类似传参的方式可以显示下标

```vue
<div id="root">
    <ul>
       <li v-for="(person,index) in persons">{{ person.name }} + {{index}}</li>
    </ul>
</div>
```

在 `v-for` 中使用对象，会将 `person` 对象的信息渲染出来

```vue
<div id="root">
    <ul>
       <li v-for="value in person">{{value}}</li>
    </ul>
</div>
<script>
    const vm = new Vue({
        el:"#root",
        data: {
            person:{
                id:'001',
                name:'ligen',
                age:18,
            }
        }
    })
</script>
```

也可以写成传参样式，`name` 代表键名，显示时应该放在 `value` 后面

```vue
<div id="root">
    <ul>
       <li v-for="(name,value) in person">{{value}}:{{name}}</li>
    </ul>
</div>
```

渲染为

```
id:001
name:ligen
age:18
```



# 7 事件处理

## 7.1 监听事件

为 `button` 绑定一个监听事件，该事件使 `Vue` 实例的属性 `age` 每次都加一，再显示到页面上

```vue
<div id = "root">
    <h1>Hello,{{ name }}</h1>
    <h1>我的年龄是:{{age}}</h1>
    <button type="button" v-on:click="age+=1">点我</button>
</div>

<script type="text/javascript">
    Vue.config.productionTip = false;//阻止 vue 在启动时生成生产提示

    //创建vue实例
    const vm = new Vue({
        el:'#root',//当前vue实例为哪个容器服务
        data:{
            name:'ligen',
            age:18,
        }
    })
</script>
```

## 7.2 复杂事件处理

真实的环境中不可能只是数值加一这样简单的操作，因此可以给点击事件绑定一个函数

```vue
<div id = "root">
    <h1>Hello,{{ name }}</h1>
    <h1>我的年龄是:{{age}}</h1>
    <button type="button" v-on:click="plus">点我</button>
</div>

<script type="text/javascript">
    Vue.config.productionTip = false;//阻止 vue 在启动时生成生产提示

    //创建vue实例
    const vm = new Vue({
        el:'#root',//当前vue实例为哪个容器服务
        data:{
            name:'ligen',
            age:18,
        },
        methods:{
            plus:function(){
                vm.age+=1
            }
        }
    })
</script>
```

`Vue` 实例的函数写在 `methods` 中

在该方法中，默认会传入一个 `event` 代表事件对象，可以从 `event` 获得事件相关的信息

当方法需要传入参数的时候，就不会传入 `event`，因此需要一个 `$event` 进行占位，继续传入

```java
<div id = "root">
    <h1>Hello,{{ name }}</h1>
    <h1>我的年龄是:{{age}}</h1>
    <button type="button" v-on:click="plus($event,2,1)">点我</button>
</div>

<script type="text/javascript">
    Vue.config.productionTip = false;//阻止 vue 在启动时生成生产提示

    //创建vue实例
    const vm = new Vue({
        el:'#root',//当前vue实例为哪个容器服务
        data:{
            name:'ligen',
            age:18,
        },
        methods:{
            plus:function(event,a,b){
                console.log(event)
                vm.age+=a;
            }
        }
    })
</script>
```



## 7.3 事件修饰符

在事件处理程序中调用 `event.preventDefault()` 或 `event.stopPropagation()` 是非常常见的需求。尽管我们可以在方法中轻松实现这点，但更好的方式是：方法只有纯粹的数据逻辑，而不是去处理 DOM 事件细节。

为了解决这个问题，Vue.js 为 `v-on` 提供了**事件修饰符**。之前提过，修饰符是由点开头的指令后缀来表示的。

- `.stop`
- `.prevent`
- `.capture`
- `.self`
- `.once`
- `.passive`

```vue
<!-- 阻止单击事件继续传播 -->
<!-- 事件由内到外传播，冒泡 -->
<a v-on:click.stop="doThis"></a>

<!-- 提交事件不再重载页面 -->
<form v-on:submit.prevent="onSubmit"></form>

<!-- 修饰符可以串联 -->
<a v-on:click.stop.prevent="doThat"></a>

<!-- 只有修饰符 -->
<form v-on:submit.prevent></form>

<!-- 添加事件监听器时使用事件捕获模式 -->
<!-- 即内部元素触发的事件先在此处理，然后才交由内部元素进行处理 -->
<div v-on:click.capture="doThis">...</div>

<!-- 只当在 event.target 是当前元素自身时触发处理函数 -->
<!-- 即事件不是从内部元素触发的 -->
<div v-on:click.self="doThat">...</div>
```

**使用修饰符时，顺序很重要；相应的代码会以同样的顺序产生。因此，用 `v-on:click.prevent.self` 会阻止所有的点击，而 `v-on:click.self.prevent` 只会阻止对元素自身的点击。**

2.1.4 新增

```vue
<!-- 点击事件将只会触发一次 -->
<a v-on:click.once="doThis"></a>
```

2.3.0 新增

Vue 还对应 [`addEventListener` 中的 `passive` 选项](https://developer.mozilla.org/en-US/docs/Web/API/EventTarget/addEventListener#Parameters)提供了 `.passive` 修饰符。

```vue
<!-- 滚动事件的默认行为 (即滚动行为) 将会立即触发 -->
<!-- 而不会等待 `onScroll` 完成  -->
<!-- 这其中包含 `event.preventDefault()` 的情况 -->
<div v-on:scroll.passive="onScroll">...</div>
```

这个 `.passive` 修饰符尤其能够提升移动端的性能。

**不要把 `.passive` 和 `.prevent` 一起使用，因为 `.prevent` 将会被忽略，同时浏览器可能会向你展示一个警告。请记住，`.passive` 会告诉浏览器你*不*想阻止事件的默认行为。**

### 7.3.1 prevent

```vue
<a v-bind:href="url" v-on:click="plus($event,2,1)">点我</a>
```

在执行 `plus` 方法之后会因为 `a` 标签而跳转到 `url`

在原来如果在方法中写 `event.preventDefault()` 就会阻止跳转

```vue
methods:{
    plus:function(event,a,b){
        console.log(event)
        event.preventDefault();
        vm.age+=a;
    }
}
```

使用 `Vue` 则可以直接在指令后增加修饰符达到上面的效果

```vue
<a v-bind:href="url" v-on:click.prevent="plus($event,2,1)">点我</a>
```

### 7.3.2 stop

```vue
<div v-on:click="show(1)">
    <button v-on:click="show(2)">点我吧</button>
</div>
```

在对 `button` 进行点击后，浏览器会先调用 `button` 上的点击事件，然后调用 `div` 上的点击事件

即先弹出 2 后弹出 1

如果使用 `event.stopPropagation()` 或者 `v-on:click.stop` 则可以阻止 `div` 上的点击事件

### 7.3.3 capture

事件捕获是从外向内的，使用了 `capture` 则事件会从捕获阶段就开始触发

```vue
<div v-on:click.capture="show(1)">
    <button v-on:click.capture="show(2)">点我吧</button>
</div>
```

即会先弹出 1，再弹出 2

### 7.3.4 self

```vue
<div v-on:click.self="show(1)">
    <button v-on:click="show(2)">点我吧</button>
</div>
```

点击 `button` 之后，`event.target` 是 `button`，因此在事件冒泡时，不会触发 `div` 的事件

### 7.3.5 passive

以滚动条为例，如果滚动事件绑定一个函数，处理 10000 次循环

当没有使用 `passive` 时，会等待循环结束，滚动条才会向下移动

如果使用 `passive` ，滚动条的滚动会立即处理，不会等待循环结束

## 7.4 按键修饰符

```vue
<input type="text" v-on:keyup.enter="display(name)" v-model:name="name"/>
```

在输入框输入完后，只有按 `enter` 键才会调用 `display` 方法

常用按键：

- `.enter`
- `.tab(只能配合keydown使用)`
- `.delete` (捕获“删除”和“退格”键)
- `.esc`
- `.space`
- `.up`
- `.down`
- `.left`
- `.right`

`Vue` 已经弃用了 `keycode`，因此不推荐。可以直接使用按键名称

## 7.5 系统修饰符

- `.ctrl`
- `.alt`
- `.shift`
- `.meta`

系统修饰符的两种情况：

- 做组合键，可以配合 `keyup` 使用。例如 `ctrl+1` ，当 `1` 键抬起触发事件
- 单个使用，必须使用 `keydown` 才能触发事件

- `.exact`：修饰符允许你控制由精确的系统修饰符组合触发的事件。

```vue
<!-- 即使 Alt 或 Shift 被一同按下时也会触发 -->
<button v-on:click.ctrl="onClick">A</button>

<!-- 有且只有 Ctrl 被按下的时候才触发 -->
<button v-on:click.ctrl.exact="onCtrlClick">A</button>

<!-- 没有任何系统修饰符被按下的时候才触发 -->
<button v-on:click.exact="onClick">A</button>
```

