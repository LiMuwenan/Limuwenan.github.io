# 1 前言

**为什么需要webpack**

开发时，我们会使用React、Vue、ES6、Less、Sass等语法进行开发。这样的代码要想在浏览器运行碧玺经过编译成浏览器能够识别的JS、CSS才可以；还有经过打包，代码会被压缩做兼容性处理，大大提高性能

**有哪些打包工具**

- Grunt
- Gulp
- Parcel
- Webpack
- Rollup
- Vite

 

# 2 基本使用

`webpack`会以某个文件或者多个文件作为打包入口，将我们真个项目所有文件编译组合成一个或多个文件输出出去；输出的文件就是编译好的文件，就可以在浏览器运行。我们将`webpack`的输出文件叫`bundle`

## 2.1 功能介绍

- 开发模式：仅能编译JS中的`ES module`语法
- 生产模式：能编译JS中的`ES module`语法，还能压缩JS代码

## 2.2 基本配置

**资源目录**

```
webpack_code # 项目根目录
	src # 项目源码
		js # js 文件目录
		main.js # 项目主文件
```

**创建**

```
# 找到项目根目录，初始化，生成一个package.json文件
npm init -y
```

初始配置项，这都是node的配置

```json
{
  "name": "workspace", // 项目名称
  "version": "1.0.0", // 版本
  "description": "",
  "main": "index.js", // npm包的入口文件
  "scripts": {
    "test": "echo \"Error: no test specified\" && exit 1",
    "build:dev": "webpack --mode development", // 以开发模式打包
    "build:prod": "webpack --mode production" // 以生产模式打包
  },
  "repository": {
    "type": "git",
    "url": "https://gitee.com/ligen0121/workspace.git"
  },
  "keywords": [],
  "author": "",
  "license": "ISC",
  "dependencies": { // 项目依赖
    "webpack": "^5.74.0",
    "webpack-cli": "^4.10.0"
  }
}
```

创建几个初始js文件

包括一个入口文件

然后再创建`webpack.config.js`

```js
const path = require("path")

module.exports = {
    // 入口
    entry: "./src/index.js",
    // 输出
    output: {
        // 文件输出路径
        path: path.resolve(__dirname, "dist")
        // 文件名
        filename: "main.js"
    },
    // 加载器
    module:  {
        rules: [
            // loader的配置

        ]
    },
    // 插件
    plugins: [
        // plugins配置
    ],
    // 模式
    mode: "development"
}
```

**下载依赖**

```
npm i webpack webpack-cli
```

**webpack打包**

指定入口文件和打包模式

```
webpack ./src/index.js --mode development
```

默认打包输出到`dist/main.js`

## 2.3 自动引入资源

当我们的资源越来越多，每次手动引入十分麻烦

通过插件完成自动引入

webpack打包过程

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/front/webpack/webpack_process.png)

需要哪个插件就到官网找到对应名称，命令行下载

### 2.3.1 html-webpack-plugin

**安装使用**

```
npm i html-webpack-plugin
```

然后在`webpack.config.js`中配置`plugins`。引入并且实例化

```js
const HtmlWebpackPlugin = require('html-webpack-plugin')

module.exports = {
	// ...
    // 插件
    plugins: [
        // plugins配置
        new HtmlWebpackPlugin()
    ],
    // ...
}
```

配置好后进行一次打包，这时候`dist`文件夹中不仅只有一个js文件，还会出现一个`html`，并且其中有一个`script`标签，自动引入了打包过的js文件

**更多配置**

这样直接打包出来的html和我们原来的html没有关系，可以通过配置让它按照我们的html生成内容

```js
// 插件
    plugins: [
        // plugins配置
        new HtmlWebpackPlugin({
            template: './public/index.html', // 从哪个文件生成
            filename: 'app.html', // 输出文件的名称
            inject: 'body', // 生成的代码放在模板的哪个标签中
        })
    ],
```

## 2.4 清理之前的打包内容

就是一个`clean: true`选项

```js
 // 输出
    output: {
        // 文件输出路径
        path: path.resolve(__dirname, "dist"),
        // 文件名
        filename: "main.js",
        // 清理之前的打包内容
        clean: true,
    },
```

## 2.5 模式配置

**直接配置**

- 开发模式：development
- 生产模式：production

```js
// 模式
    mode: "development"
```

**source map**

直接配置的模式，生成的代码不便于阅读和调试

通过配置`source map`，生成的代码会和源代码有一个对应关系，查看错误的时候会指示到源代码错误的位置

`inline-source-map`和`source-map`的区别是前者不会单独打包出一个`map`文件，后者会，它们都能用来锁定代码行数

# 3 资源模块

- asset/resource
- asset/inline
- asset/source
- asset

## 3.1 resource

之前使用`file-loader`实现。 发送一个单独的资源并导出url

**配置**

```js
// 加载器
module:  {
    rules: [
        // loader的配置
        {
            test: /\.png$/, // png文件
            type: "asset/resource"
        }
    ]
},
```

**引入使用**

原生js创建dom

```js
import helloWorld from "./hello_world";
import imgsrc from './assets/logo500x500.png'

helloWorld()

const img = document.createElement('img')
img.src = imgsrc
document.body.appendChild(img)
```

这样打包后，会自动将涉及到的资源文件识别打包，将其原名称变成一个字符串。

**配置输出文件名和文件路径**

第一种方法

```js
// 输出
output: {
    // 资源文件输出路径
    // 根据文件内容生成文件名，扩展名使用原扩展名
    assetModuleFilename: "images/[contenthash][ext]"
},
```

第二种方法

```js
// 加载器
    module:  {
        rules: [
            // loader的配置
            {
                test: /\.png$/, // png文件
                type: "asset/resource",
                generator: {
                    filename: 'images/[contenthash][ext]'
                }
            }
        ]
    },
```

## 3.2 inline

之前使用`url-loader`实现。导出一个资源的`data URI`

`inline`模式和上面的`resource`方式有所不同，`resource`的资源打包时会输出到一个路径，而`inline`资源会直接嵌入到页面（base64），在输出路径中是没有的

```js
// 加载器
module:  {
    rules: [
        {
            test: /\.svg$/,
            type: "asset/inline",
        }
    ]
},
```

## 3.3 source

之前使用`raw-loader`实现。导出资源的源代码。

```js
// 加载器
module:  {
    rules: [
        // loader的配置
        {
            test: /\.txt$/, // txt
            type: "asset/source",
        }
    ]
},
```

## 3.4 asset

通用资源处理，在导出一个`data URI`和发送一个单独的文件之间自动选择。之前通过使用`url-loader`并且配置资源体积限制实现。

大于8K生成会附带资源，小于8K生成base64，变成inline资源

```js
// 加载器
module:  {
    rules: [
        // loader的配置
        {
            test: /\.jpg$/,
            type: "asset",
            generator: {
                filename: 'images/[contenthash][ext]'
            },
            parser: {
                dataUrlCondition: {
                    maxSize: 10 * 1024, // 自定义转换大小10K
                },
            },
        }
    ]
},
```

## 3.5 css-loader

也用来加载一些外部资源

[官网loader](https://webpack.docschina.org/loaders/)

```js
module: {
    rules: [{
        test: /\.txt$/, // 类型
        use: 'raw-loader', // 使用的loader
    }],
}
```



用来加载`css`，需要先安装`css-loader`、`style-loader`

```
npm i css-loader
```

添加样式和规则

```js
module:  {
    rules: [
        // loader的配置
        {
            test: /\.css$/,
            use: [
                'style-loader',
                'css-loader', // 遵循从下到上的加载原则
            ]
        },
    ],
}
```

同样的方式还可以处理`less`、`sass`等。上面这样方式会直接在`html`中嵌入`style`标签。

**抽离css**

使用插件`mini-css-extract-plugin`

```
npm i mini-css-extract-plugin
```

配置

```js
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
module:  {
    rules: [
        // loader的配置
        {
            test: /\.css$/,
            use: [
                MiniCssExtractPlugin.loader,
                'css-loader', // 遵循从下到上的加载原则
            ]
        }
    ]
},
// 插件
plugins: [
    // plugins配置
    new MiniCssExtractPlugin({
        filename: 'styles/css/[contenthash].css',
    }),
],
```

这种形式的会在输出文件夹中输出一个`css`，然后在`html`中使用`link`标签来引入样式

**压缩css**

安装插件`css-minimizer-webpack-plugin`

```
npm i css-minimizer-webpack-plugin
```

这个插件不在`plugins`配置中，而是在`optimization`中

```js
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin')
// 优化
optimization: {
    minimizer: [
        // css压缩
        new CssMinimizerPlugin(),
    ]
},
```

## 3.6 字体资源

```
// 加载器
module:  {
    rules: [
        // loader的配置
        {
            test: /\.(ttf|eot|wof)$/, // 字体
            type: "asset/resource"
        }
    ]
},
```

## 3.7 csv-loader

## 3.8 xml-loader

## 3.9 babel-loader

转换各种高级语法到commonJS

```
npm i babel-loader @babel/core @babel/preset-env
```

- babel-loader：webpack应用babel解析的桥梁
- @babel/core：babel核心
- @babel/preset-env：babel预设，一组babel插件的集合

```js
module:  {
    rules: [
        // loader的配置
        {
            test: /\.js$/,
            exclude: /node_modules/,
            use: {
                loader: 'babel-loader',
                options: {
                    presets: ['@babel/preset-env'],
                },
            },
        },
    ]
}
```

还需要导入

```
npm i @babel/runtime @babel/plugin-transform-runtime
```

# 4 代码分离

## 4.1 单入口节点

```
entry: 'index.js'
```

就和前面的代码一样，只有一个入口文件

## 4.2 多入口

```js
// 入口
entry: {
    index: './src/index.js',
    another: './src/another.js',
},
```

如果只是这样的配置多入口，会报错，因为输出文件名会冲突，大家都输出成了同一个文件名

```js
// 输出
output: {
    // 文件输出路径
    path: path.resolve(__dirname, "dist"),
        // 文件名
        filename: "[name].js", // 自动获取名称。输出不同的名称
}
```

这样的配置会将引入的模块打包进对应的js，如果两个入口文件引入同一个模块，还是会重复打包，造成不必要的文件体积过大。

**防止重复**

```js
// 入口
entry: {
    index: {
        import: './src/index.js',
            dependOn: 'lodashs', // 共享模块抽离叫什么名字
    },
    another: {
        import: './src/another.js',
             dependOn: 'lodashs', // 共享模块抽离叫什么名字
    },
    lodashs: 'lodash', // 抽离哪个模块，抽出来的名字这里叫shared
}
```

**多入口还可以通过split-chunks-plugin和ECMAScript来抽离代码**

## 4.3 懒加载

有些模块在没有点击的时候不需要加载，所以初始加载只加载必要的东西。当触发某个事件需要某模块时再加载

## 4.4 预加载

## 4.5 浏览器缓存

因为浏览器会做缓存，导致我们修改过文件后在浏览器中没有重新加载。使用下载的配置更改输出文件名称来解决缓存问题。

```js
// 输出
output: {
    // 文件输出路径
    path: path.resolve(__dirname, "dist"),
    // 文件名
    filename: "scripts/[name].[contenthash].js",
}
```

同样的可以利用缓存机制使浏览器不重新加载库文件

这里必须使用到`split-chunk-plugin`



## 5 拆分环境

自动拆分开发环境和生产环境配置

## 5.1 公共路径

html中是相对路径或其它绝对路径，通过设置`publicPath`配置，来指定资源的绝对路径，可以将资源指定为对应服务器

```js
output: {
    publicPath: "http://localhost:8080",
}
```

## 5.2 环境变量

```js
const path = require("path")
const HtmlWebpackPlugin = require('html-webpack-plugin')
const MiniCssExtractPlugin = require('mini-css-extract-plugin')
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin')

module.exports = (env, argv) => {
    const mode = argv.mode === 'production' ? 'prod' : 'dev'
    return {
        
    }
}
```

## 5.3 代码压缩

`webpack`自带了`terser`进行压缩，但是如果使用`css`压缩的`css-minimizer-webpack-plugin`就需要再进行配置`terser`

```js
// 优化
optimization: {
    minimizer: [
        // css压缩
        new CssMinimizerPlugin(),
        new TerserPlugin(),
    ]
},
```

## 5.4 npm脚本

在`package.json`中配置

```json
"scripts": {
    "test": "echo \"Error: no test specified\" && exit 1",
    "build:dev": "webpack --mode development",
    "build:prod": "webpack --mode production",
},
```

