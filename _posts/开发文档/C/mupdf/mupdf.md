<!-- TOC -->

- [序言](#序言)
- [致谢](#致谢)
- [1.介绍](#1介绍)
  - [1.1 什么是Mupdf？](#11-什么是mupdf)
  - [1.2 许可](#12-许可)
  - [1.3 依赖](#13-依赖)
- [2.关于这个手册](#2关于这个手册)
- [3.快速启动](#3快速启动)
  - [3.1 怎样打开文档和渲染页面](#31-怎样打开文档和渲染页面)
- [4.命名规则](#4命名规则)
  - [4.1 前缀](#41-前缀)
  - [4.2 命名](#42-命名)
  - [4.3 类型](#43-类型)
- [5.文本环境](#5文本环境)
  - [5.1 概述](#51-概述)
  - [5.2 创建](#52-创建)
  - [5.3 自定义分配](#53-自定义分配)
  - [5.4 多线程](#54-多线程)
  - [5.5 克隆](#55-克隆)
  - [5.6 销毁](#56-销毁)
  - [5.7 调整](#57-调整)
  - [5.8 总结](#58-总结)
- [6.错误处理](#6错误处理)
  - [6.1 概述](#61-概述)
    - [6.1.1 为什么fz_var是必要的？](#611-为什么fz_var是必要的)
    - [6.1.2 举例：如何使用保护局部变量fz_var](#612-举例如何使用保护局部变量fz_var)
  - [6.2 抛出异常](#62-抛出异常)
  - [6.3 处理异常](#63-处理异常)
  - [6.4 总结](#64-总结)
- [7.内存管理与存储](#7内存管理与存储)
  - [7.1 概述](#71-概述)
  - [7.2 创建存储](#72-创建存储)
  - [7.3 内存不足处理](#73-内存不足处理)
    - [7.3.1 操作](#731-操作)
- [8.文档接口](#8文档接口)
  - [8.1 概述](#81-概述)
  - [8.2 打开/关闭文档](#82-打开关闭文档)
  - [8.3 处理受密码保护的文档](#83-处理受密码保护的文档)
  - [8.4 处理可重排文件](#84-处理可重排文件)
  - [8.5 从文档中获取一页](#85-从文档中获取一页)
  - [8.6 页面解析](#86-页面解析)
  - [8.7 颜色管理](#87-颜色管理)
  - [8.8 渲染页面](#88-渲染页面)
  - [8.9 展示](#89-展示)
    - [8.9.1 查询](#891-查询)
    - [8.9.2 辅助功能](#892-辅助功能)
- [9.设备接口](#9设备接口)
  - [9.1 概述](#91-概述)
  - [9.2 设备方法](#92-设备方法)
  - [9.3 Cookie](#93-cookie)
    - [9.3.1 检查错误](#931-检查错误)
    - [9.3.2 在线程中使用cookie](#932-在线程中使用cookie)
    - [9.3.3 用cookie部分渲染](#933-用cookie部分渲染)
  - [9.4 设备提示](#94-设备提示)
  - [9.5 内置设备](#95-内置设备)
    - [9.5.1 bbox装置](#951-bbox装置)
    - [9.5.2 画图装置](#952-画图装置)
    - [9.5.3 显示清单装置](#953-显示清单装置)
    - [9.5.4 PDF输出装置](#954-pdf输出装置)
    - [9.5.5 结构化文本装置](#955-结构化文本装置)
    - [9.5.6 SVG输出装置](#956-svg输出装置)
    - [9.5.7 测试设备](#957-测试设备)
    - [9.5.8 跟踪设备](#958-跟踪设备)
- [10.构建模块](#10构建模块)
  - [10.1 概述](#101-概述)
  - [10.2 色彩空间](#102-色彩空间)
    - [10.2.1 色彩空间基础](#1021-色彩空间基础)
    - [10.2.2 色彩空间标签](#1022-色彩空间标签)
    - [10.2.3 色彩空间分离和deviceN](#1023-色彩空间分离和devicen)
    - [10.2.4 更多信息](#1024-更多信息)
  - [10.3 像素图](#103-像素图)
    - [10.3.1 概述](#1031-概述)
    - [10.3.2 预置alpha通道](#1032-预置alpha通道)
    - [10.3.3 保存](#1033-保存)
  - [10.4 位图](#104-位图)
  - [10.5 半色调](#105-半色调)
  - [10.6 图片](#106-图片)
  - [10.7 缓存区](#107-缓存区)
  - [10.8 转换](#108-转换)
  - [10.9 路径](#109-路径)
  - [10.10 文本](#1010-文本)
  - [10.11 底纹](#1011-底纹)
- [11.显示列表](#11显示列表)
  - [11.1 概述](#111-概述)
  - [11.2 创建](#112-创建)
  - [11.3 运行](#113-运行)
  - [11.4 参考计数](#114-参考计数)
  - [11.5 其他操作](#115-其他操作)
- [12.流接口](#12流接口)
  - [12.1 概述](#121-概述)
  - [12.2 创建](#122-创建)
  - [12.3 使用](#123-使用)
    - [12.3.1 发送字节](#1231-发送字节)
    - [12.3.2 读取对象](#1232-读取对象)
    - [12.3.3 读取比特](#1233-读取比特)
    - [12.3.4 读取整个流信息](#1234-读取整个流信息)
    - [12.3.5 搜索](#1235-搜索)
    - [12.3.6 元数据](#1236-元数据)
    - [12.3.7 销毁](#1237-销毁)
- [13.输出接口](#13输出接口)
  - [13.1 概述](#131-概述)
  - [13.2 创建](#132-创建)
  - [13.3 使用](#133-使用)
    - [13.3.1 写字节](#1331-写字节)
    - [13.3.2 写对象](#1332-写对象)
    - [13.3.3 写字符串](#1333-写字符串)
    - [13.3.4 搜索](#1334-搜索)
- [14.渲染输出格式](#14渲染输出格式)
  - [14.1 概述](#141-概述)
  - [14.2 带区写入](#142-带区写入)
  - [14.3 PNM](#143-pnm)
  - [14.4 PAM](#144-pam)
  - [14.5 PBM](#145-pbm)
  - [14.6 PKM](#146-pkm)
  - [14.7 PNG](#147-png)
  - [14.8 PSD](#148-psd)
  - [14.9 PWG/CUPS](#149-pwgcups)
    - [14.9.1 Contone](#1491-contone)
    - [14.9.2 Mono](#1492-mono)
  - [14.10 TGA](#1410-tga)
  - [14.11 PCL](#1411-pcl)
    - [14.11.1 Color](#14111-color)
    - [14.11.2 Mono](#14112-mono)
  - [14.12 postscript](#1412-postscript)
- [15.文档写接口](#15文档写接口)
  - [15.1 使用](#151-使用)
  - [15.2 实现](#152-实现)
- [16.渐进模式](#16渐进模式)
  - [16.1 概述](#161-概述)
  - [16.2 实现](#162-实现)
    - [16.2.1 渐进流](#1621-渐进流)
    - [16.2.2 粗糙渲染](#1622-粗糙渲染)
    - [16.2.3 直接下载](#1623-直接下载)
    - [16.2.4 案例实现](#1624-案例实现)
- [17.字体](#17字体)
  - [17.1 概述](#171-概述)
  - [17.2 内置字体](#172-内置字体)
  - [17.3 实现](#173-实现)
- [18.构建配置](#18构建配置)
  - [18.1 概述](#181-概述)
  - [18.2 配置文档](#182-配置文档)
  - [18.3 绘图器选择](#183-绘图器选择)
  - [18.4 文档处理器](#184-文档处理器)
  - [18.5 JPEG 2000支持](#185-jpeg-2000支持)
  - [18.6 Javascript](#186-javascript)
  - [18.7 字体](#187-字体)
- [19.图片接口](#19图片接口)
  - [19.1 概述](#191-概述)
  - [19.2 标准图片格式](#192-标准图片格式)
    - [19.2.1 压缩](#1921-压缩)
    - [19.2.2 解码](#1922-解码)
    - [19.2.3 显示列表](#1923-显示列表)
  - [19.3 创建图片](#193-创建图片)
  - [19.4 实现一个图像类型](#194-实现一个图像类型)
  - [19.5 图片缓存](#195-图片缓存)
- [20.文档操作接口](#20文档操作接口)
  - [20.1 概述](#201-概述)
  - [20.2 实现文档操作](#202-实现文档操作)
    - [20.2.1 识别和打开](#2021-识别和打开)
    - [20.2.2 文档层级函数](#2022-文档层级函数)
    - [20.2.3 页面层级函数](#2023-页面层级函数)
  - [20.3 标准文档处理](#203-标准文档处理)
    - [20.3.1 PDF](#2031-pdf)
    - [20.3.2 XPS](#2032-xps)
    - [20.3.3 EPUB](#2033-epub)
    - [20.3.4 HTML](#2034-html)
    - [20.3.5 SVG](#2035-svg)
    - [20.3.6 Image](#2036-image)
    - [20.3.7 CBZ](#2037-cbz)
- [21.内部存储](#21内部存储)
  - [21.1 概述](#211-概述)
  - [21.2 实现](#212-实现)
  - [21.3 参考计数](#213-参考计数)
  - [21.4 清除内存分配器](#214-清除内存分配器)
  - [21.5 使用存储](#215-使用存储)
    - [21.5.1 概述](#2151-概述)
    - [21.5.2 处理关键](#2152-处理关键)
    - [21.5.3 散列](#2153-散列)
    - [21.5.4 关键存储条目](#2154-关键存储条目)
    - [21.5.5 收集通过](#2155-收集通过)
- [22.内部设备](#22内部设备)
  - [22.1 线条](#221-线条)
  - [22.2 文本](#222-文本)
  - [22.3 图片](#223-图片)
  - [22.4 阴影](#224-阴影)
  - [22.5 剪切和蒙版](#225-剪切和蒙版)
  - [22.6 组和透明度](#226-组和透明度)
  - [22.7 平铺](#227-平铺)
  - [22.8 渲染标志](#228-渲染标志)
  - [22.9 设备颜色空间](#229-设备颜色空间)
- [23.内部路径](#23内部路径)
  - [23.1 创建](#231-创建)
  - [23.2 参考计数](#232-参考计数)
  - [23.3 存储](#233-存储)
  - [23.4 转型](#234-转型)
  - [23.5 边界](#235-边界)
  - [23.6 stroking](#236-stroking)
  - [23.7 walking](#237-walking)
- [24.内部文本](#24内部文本)
  - [24.1 创建](#241-创建)
  - [24.2 人口](#242-人口)
  - [24.3 衡量](#243-衡量)
  - [24.4 克隆](#244-克隆)
  - [24.5 语言](#245-语言)
  - [24.6 实现](#246-实现)
- [25.内部阴影](#25内部阴影)
  - [25.1 创建](#251-创建)
  - [25.2 边界](#252-边界)
  - [25.3 画图](#253-画图)
  - [25.4 分解](#254-分解)
- [26.内部流](#26内部流)
- [27.内部输出](#27内部输出)
- [28.内部色彩空间](#28内部色彩空间)
  - [28.1 非基于ICC的色彩](#281-非基于icc的色彩)
  - [28.2 基于ICC的色彩](#282-基于icc的色彩)
  - [28.3 色彩校准](#283-色彩校准)
- [29.颜色管理](#29颜色管理)
  - [29.1 概述](#291-概述)
- [30.PDF解释器详细信息](#30pdf解释器详细信息)
  - [30.1 概述](#301-概述)
  - [30.2 PDF文档](#302-pdf文档)
  - [30.3 PDF对象](#303-pdf对象)
    - [30.3.1 数组](#3031-数组)
  - [30.4 PDF运算符处理](#304-pdf运算符处理)
    - [30.4.1 运行处理](#3041-运行处理)
    - [30.4.2 过滤器处理](#3042-过滤器处理)
    - [30.4.3 缓存处理](#3043-缓存处理)
    - [30.4.4 输出处理](#3044-输出处理)
  - [30.5 PDF对象之间拷贝对象](#305-pdf对象之间拷贝对象)
    - [30.5.1 问题描述](#3051-问题描述)
    - [30.5.2 衔接对象](#3052-衔接对象)
    - [30.5.3 其他问题](#3053-其他问题)
    - [30.5.4 衔接图](#3054-衔接图)
- [31.XPS解释器细节](#31xps解释器细节)
  - [31.1 概述](#311-概述)
- [32.EPUB/HTML解释器细节](#32epubhtml解释器细节)
  - [32.1 CSS规则](#321-css规则)
  - [32.2 改变文本](#322-改变文本)
  - [32.3 双向文字](#323-双向文字)
- [33.SVG解释器细节](#33svg解释器细节)
- [34.Mu工具](#34mu工具)
  - [34.1 概述](#341-概述)
  - [34.2 清除](#342-清除)
  - [34.3 转换](#343-转换)
  - [34.4 创建](#344-创建)
  - [34.5 绘图](#345-绘图)
  - [34.6 提取](#346-提取)
  - [34.7 信息](#347-信息)
  - [34.8 合成](#348-合成)
  - [34.9 页面](#349-页面)
  - [34.10 物品集](#3410-物品集)
  - [34.11 海报](#3411-海报)
  - [34.12 运行](#3412-运行)
  - [34.13 展示](#3413-展示)
- [35 Mu官方库](#35-mu官方库)
- [36 过渡](#36-过渡)
- [37 Mu线程](#37-mu线程)
- [38 平台详情](#38-平台详情)
  - [38.1 概述](#381-概述)
  - [38.2 C库](#382-c库)
  - [38.3 安卓查看器](#383-安卓查看器)
  - [38.4 Java桌面](#384-java桌面)
    - [38.4.1 语言](#3841-语言)
    - [38.4.2 查看器](#3842-查看器)
  - [38.5 GS查看器](#385-gs查看器)
  - [38.6 Javascript](#386-javascript)
  - [38.7 Linux/Windows查看器](#387-linuxwindows查看器)
    - [38.7.1 非GL版本](#3871-非gl版本)
    - [38.7.2 GL版本](#3872-gl版本)
- [怎样对MuPDF做出贡献](#怎样对mupdf做出贡献)

<!-- /TOC -->


# 序言

这是关于MuPDF的书的开篇. 它远未完成，但提供尽可能地提供有用的信息. 我们为它提供最新版本的MuPDF(目前为1.12)，希望它会有用.欢迎任何反馈，更正或建议.请访问错误.ghostscript.com并打开一个以"MuPDF"为产品，"Docu-精神"作为组成部分.我们将努力从文档中提供新版本出现在mupdf.com的部分.  

# 致谢

非常感谢Tor Andersson创建MuPDF，感谢所有多年来为Artifex Software以及我的所有同事做出了贡献提供一个可以成长到成熟的环境. 特定感谢塞巴斯蒂安·拉斯穆森(Sebastian Rasmussen)耐心地读书并提出许多改进建议.最后，海伦·罗杰斯(Helen Rogers)值得称赞，因为他支持我.  

# 1.介绍

## 1.1 什么是Mupdf？

MuPDF是一个便携式C库，用于打开，处理和呈现各种格式的文档，包括PDF，XPS，SVG，电子出版物以及许多其他格式.常见的图像格式.  
此核心C库提供了一个API(称为MuPDF API)，该API允许这些文档上要执行的各种操作.确切动作是否可用取决于文档的格式，但始终包括渲染这些文件.除了该库之外，MuPDF发行版还包含各种内置工具在此API之上.  
这些工具包括简单的查看器，用于操作的工具文档，添加，删除页面或调整页面大小以及提取资源和其他文件中的信息.这些工具故意保持"稀薄"可能.繁重的工作全部由核心库执行，以便可重复使用.  
人们通常首先遇到MuPDF的地方是Linux或Android桌面查看器，但是这些仅仅是使用该库的某些功能.最后，MuPDF发行版包含绑定以反映MuPDF C API转换为其他语言，例如Java和Javascript.  

## 1.2 许可

MuPDF是根据两个许可证发行的.  

首先，它可以通过GNU Afferro通用许可证获得(因此，GNU AGPL).这是一个复杂的许可证，值得仔细研究和比我们这里有更多的空间.但是，一些关键点是：  
>+ 您可以在完全编写的软件中自由使用MuPDF供您自己使用，没有任何问题.您通过该软件的那一刻给任何其他人，或作为任何一部分提供给其他任何人"软件即服务"安装，您必须遵守以下规定条款.  
>+ 如果将MuPDF链接到您自己的软件中，则该软件的整体该商品必须获得GNU AGPL许可(或兼容许可1).  
>+ 如果将MuPDF用作"软件即服务"安装的一部分，则您必须根据GNU AGPL许可整个安装.
>+ 根据GNU AGPL发行软件需要您成为准备将完整的源代码提供给接收该副本的任何用户软件.不收取任何费用(名义媒体费用除外)这个.  
>+ 您必须确保该系统的所有最终用户都有能力使用MuPDF的更新版本更新软件.这包括嵌入式系统.  
>+ 根据GNU AGPL使用MuPDF，您将不获得任何保证，也没有支持.还有其他条款，我们强烈建议您阅读许可在开发基于代码的代码之前，请充分了解您的义务根据MuPDF.如果您发现您可以遵守GNU AGPL的所有条款，则可以使用MuPDF在您自己的项目中，无需任何许可费用.

但是，这些术语通常过于严格，以至于它们不适合对于生产商业产品的人来说非常重要，将源代码提供给商业产品的运走通常是不可接受的，并且"重新链接"要求-对于嵌入式用户而言，GNU AGPL的功能过于繁琐.出于这个原因，Artifex(MuPDF的开发者)提供了广告许可证.请联系sales@artifex.com，以获取适合您确切需求的报价.Artifex商业许可删除了GNU的所有繁重条款AGPL，包括需要许可您的整个应用程序，提供源代码以及以确保重新链接功能.如果您发现自己无法接受并遵守GNU的条款AGPL不愿从Artifex获得商业许可，因此您不能在所分发的任何软件中合法使用MuPDF(或以"软件作为服务").  

## 1.3 依赖

核心MuPDF库利用各种软件库.  
**Freetype** 适用于各种字体类型的渲染器.  
**Harfbuzz** OpenType字体整形器基于Freetype构建，这是e-pub所必需的.  
**JBig2dec** JBIG2图像的图像解码器.  
**JpegLib** 用于JPEG图像的图像解码器.  
**MuJS** 用于PDF文件的Javascript引擎.  
**OpenJPEG** 用于JPEG2000图像图像解码器.  
**ZLib** 压缩库.  
**LCMS2** ICC颜色管理引擎.  

另外，MuPDF库可以选择使用：  
**OpenSSL** 加密库，数字签名支持所必需.  

用于Linux和Windows的MuPDF查看器可以选择使用：  
**Curl** 一个http提取程序，用于在文件下载时显示它们.  
这些库与MuPDF打包在一起，在发行档案中
或作为git子模块.这些库有时可能会包含错误修复程序
尚未被接受并返回上游存储库的文件.因此，我们
强烈建议使用我们提供的库的版本，而不是使用
您可以在系统上找到的任何其他版本.  
LCMS2除外. MuPDF随附的LCMS2版本
API进行了更改，使其与香草LCMS2不兼容.因此不是
仅仅是使用所提供版本的建议，而是一项要求.
在文档中讨论了这种不兼容的原因我们的库版本.  

最后，MuPDF的商业版本可以选择使用不同的解码器
库：
**Luratech JBIG2** 用于JBIG2图像的图像解码器.
**Luratech JPEG2000** 图像解码器，用于JPEG 2000图像.
这些库通常在内存和CPU使用方面都更好，但是
不可用开源.因此，它们作为商业版本的一部分提供给我们的客户.然后，这些商业客户可以自由地
选择要使用的库.

# 2.关于这个手册

本书分为三部分.

第一部分描述了MuPDF C API，其背后的概念以及如何称它为.如果您希望从MuPDF构建应用程序，则您需要需要应该在这里.  
第二部分描述了一些用于构建MuPDF的模块.如果你希望扩展MuPDF，也许会打开新格式，或者提供新的操作文件一旦打开，这就是要参考的部分.  
第三部分描述了实际语言解释器的一些细节.这个主要是希望对文档进行低级操作的人感兴趣的文件格式(特别是PDF)，但新作者可能会感兴趣文档格式处理程序，以了解如何解决常见问题.  
第四部分介绍了一些工具，库和"帮助程序"与MuPDF绑定.这些帮助程序并非严格属于MuPDF li-大胆，但在实现基于应用程序的应用程序时仍然非常有用在上面.  
最后，我们有一部分专门讨论平台细节和不同的语言可用的绑定.  

# 3.快速启动

## 3.1 怎样打开文档和渲染页面

有关如何打开文档并呈现某些页面的简单示例，请参见docs/example.c.  
您在本示例中遇到的概念将在本手册中进行解释和扩展.以下各章. 阅读该示例时可能会很有用然后，为您提供所讨论想法的具体插图.  

# 4.命名规则

MuPDF中的函数和变量名称经过精心选择，遵循标准约定. 通过一贯使用相同的术语，我们希望人们能够通过与函数作用类似的名称容易的记住函数的功能，最大程度的减少翻阅手册的需要.

我们要求提交给MuPDF的任何代码都必须遵循相同的约定，并且会鼓励人们在自己的接口代码中遵循相同的样式与MuPDF.  

## 4.1 前缀

从历史上看，MuPDF所依赖的图形库被称为"fitz".
因此，所有MuPDF的API调用和大多数类型都以"fz_"开头.  

另外，我们有特定的API和类型提供的地方
格式处理程序(例如PDF或XPS). 这些功能/类型以开头
格式处理程序名称，例如"pdf"或"xps".  

所有导出的函数和类型都应以这种方式加上前缀，以避免
在调用应用程序中与符号冲突的可能性. 内部功能
不需要加上前缀，但我们鼓励您添加前缀.  

## 4.2 命名

所有功能均根据以下方案之一命名：  

>+ verb_noun  
>+ verb_noun_with_noun  
>+ noun_attribute  
>+ set_noun_attribute  
>+ noun_from_noun 从一个类型转换为另一个类型(避免noun_from_noun)  

唯一的例外是我们具有MuPDF特定功能，
模拟(或扩展)"well known"功能，在此我们模仿这些功能.
例如"fz_printf"或"fz_strdup".
此外，我们避免在函数名称中使用"get"，因为这通常是多余的.
相反，我们确实在需要的地方使用"set".
考虑以下例子:  
fz_aa_level(以检索当前的抗锯齿级别)，fz_set_aa_level(更新).  

MuPDF广泛使用了参考计数(请参见[21.3参考
计算](#213-参考计数)更多细节). 我们保留各种词语来表示该参考
正在使用：  

new 指示此调用创建一个新对象并返回对其的引用.
例如,fz_new_pixmap  

find 表示此调用从某个位置(通常是从高速缓存或一组标准对象)中找到一个对象，并返回一个新的
引用它. 例如，fz_find_color_convertor.

load 指示此调用创建一个新对象并返回对其的引用.
这类似于"new"，但暗示该操作将
读取一些数据并需要一些(可能是重要的)数据
在创建对象之前进行处理. 例如，fz_load_outline or 
fz_load_jpeg.

open 指示此调用将创建一个流对象，并返回一个引用
对此. 例如，fz_open_document.

keep 表示此调用将创建对现有引用的新引用
. 例如，fz_keep_colorspace.  

所有这些调用都返回一个对象引用. 调用者有责任
在所需的使用寿命内，将该参考安全地存储在某处
，并在调用方不再需要该引用时销毁该引用.  

如果没有使用上述保留字之一命名，则任何函数都不应返回引用的所有权.  

一旦释放了对给定对象的所有引用，系统将删除该对象本身.  

drop 指示此调用将放弃传入的对象的所有权.
例如，fz_drop_font.  

未能删除引用将导致内存泄漏. 过早删除引用可能会导致崩溃，原因是对象被销毁后才被访问.

可以通过使用单词"drop"，"close"或"free"调用函数来销毁用于销毁不受引用计数的对象的API函数.

与上述"find"相反，我们在搜索方面还有一个保留关键字：

lookup 指示该调用将返回借入的指针(或值). 例如，fz_lookup_pixmap_converter  

当我们已经分配了一个结构，并且希望初始化其部分或全部内部细节时，我们使用"init". 与此匹配的对应命名为"fin". 例如，fz_cmm_init_profile与fz_cmm_fin_profile.

有些对象是使用动词"create"的功能创建的. 有时，这些对象可以作为参考计数对象(例如"pdf_create_document")，在这种情况下，应像往常一样"drop"它们. 没有引用的计数对象应该被"destroy"(例如fz_destroy_mutex).

## 4.3 类型

MuPDF中使用了各种不同的整数类型  

>+ int 至少32位
>+ short 16位
>+ char 8位
>+ 数组大小、字符串大小和分配量都用size_t标识，在32位机上就是32位，在64位机上就是64位
>+ 数据缓存区用 unsigned char(or uint8_t)
>+ 为了简单起见，在所有构建中都使用int64_t表示文件/流中的偏移.

以前，MuPDF使用fz_off_t类型的文件偏移量.
根据构建类型或定义的FZ_LARGEFILE更改了大小，但是由于潜在的风险而被放弃. 根据不同的符号定义在生成之间更改API的形式很差，因为它可能导致意外崩溃.
另外，我们使用浮点数(并且在内部进行双精度化，尽管我们尽可能避免在API中使用浮点数). 假定这些符合IEEE标准.

# 5.文本环境

## 5.1 概述

MuPDF核心库旨在简化，可移植性和易于集成.
由于所有这些原因，它没有全局变量，没有线程库依赖性，并且具有定义良好的异常系统来处理运行时错误.
但是，为了尽可能地有用，该库必须具有某种状态，并且需要能够利用多线程环境.

这些看似矛盾的要求的解决方案是Context(fz_context)

MuPDF的每个调用者都应在使用该库的开始时创建一个Context，并在其末尾销毁它. 然后，该Context(或从中"cloned")将传递给每个MuPDF API调用.  

**Global State** 简而言之，Context包含该库的全局设置. 例如，文本和艺术线条渲染例程使用的抗锯齿级别是在Context中设置的，Epub或FB2文件的默认样式表也是如此.
此外，库也在那里存储自己的私人信息.  

**Error handling** MuPDF中的所有错误处理都是使用fz_try/fz_catch构造完成的;有关更多详细信息，请参见[第6章错误处理]().
这些构造可以嵌套，因此依赖于Context中维护的异常堆栈. 因此，至关重要的是，任何两个线程都不能同时使用相同的Context. 有关更多信息，请参见[第5.4节"多线程"].  

**Allocation**  将MuPDF嵌入系统时，通常需要控制使用的分配器. 可以在创建时将一组分配器函数提供给Context，并且所有分配器都将使用这些分配器函数执行. 有关更多信息，请参见第7章"内存管理"和"存储".  

**The Store** MuPDF使用内存缓存来提高性能，并避免文件中资源的重复解码. 该存储使用上下文维护，并在上下文及其克隆之间共享. 有关更多信息，请参见第7章"内存管理"和"存储".  

**Multi-threading** MuPDF不依赖于线程本身，但是可以在多线程环境中使用，以显着提高性能. MuPDF可以使用任何线程库. 必须在创建时将一组锁定/解锁函数传递给上下文，并且库将使用这些函数来确保它是线程安全的. 有关更多信息，请参见5.4多重读取.  

## 5.2 创建

用fz_new_context创建一个Context:  
```c++
/*
fz_new_context: Allocate context containing global state.  

The global state contains an exception stack, resource store,etc. Most functions in MuPDF take a context argument to beable to reference the global state. See fz_drop_context forfreeing an allocated context.  

alloc: Supply a custom memory allocator through a set offunction pointers. Set to NULL for the standard libraryallocator. The context will keep the allocator pointer, so thedata it points to must not be modified or freed during thelifetime of the context.  

locks: Supply a set of locks and functions to lock/unlockthem, intended for multi-threaded applications. Set to NULLwhen using MuPDF in a single-threaded applications. Thecontext will keep the locks pointer, so the data it points tomust not be modified or freed during the lifetime of thecontext.  

max_store: Maximum size in bytes of the resource store, beforeit will start evicting cached resources such as fonts andimages. FZ_STORE_UNLIMITED can be used if a hard limit is notdesired. Use FZ_STORE_DEFAULT to get a reasonable size.  

Does not throw exceptions, but may return NULL.
*/
fz_context *fz_new_context(const fz_alloc_context *alloc, const fz_locks_context *locks, unsigned int max_store);
```

举例：一个简单的单线程程序使用标准分配像这样：  

```c++
fz_context *ctx = fz_new_context(NULL,NULL,FZ_STORE_UNLIMITED);
```


## 5.3 自定义分配

在某些情况下，最好通过一组"custom"分配器强制所有分配.
这些被定义为fz_alloc_context，其地址传递到fz_new_context. 在返回的fz_new_context(和所有克隆)的生存期内，必须存在此结构.

```c++
typedef struct
{
  void *user;
  void *(*malloc)(void *, size_t);
  void *(*realloc)(void *, void *, size_t);
  void (*free)(void *, void *);
} fz_alloc_context;
```
malloc，realloc和free函数指针的语义与标准malloc，realloc和free标准函数的语义基本相同，不同之处在于它们使用一个附加的初始参数fz_alloc_context中指定的用户值.  

## 5.4 多线程

MuPDF本身不依赖线程系统，但是如果存在线程系统，它将使用一个.这对于确保可以同时从多个线程调用MuPDF至关重要.  
一个典型的示例可能是在打印机的多核处理器中.我们可以将PDF文件解释为显示列表，然后从该显示列表中渲染"band"以发送到打印机.通过使用多个线程，我们可以一次渲染多个波段，从而大大缩短了处理时间.  
在此示例中，尽管每个线程将呈现不同的内容，但它们可能会共享一些信息-例如，相同的字体可能会在多个频段中使用.与其让每个线程独立地从字体中呈现所需的所有字形，不如让每个线程都可以协作并共享结果，这将是很好的.  
因此，我们安排了诸如字体缓存之类的数据结构可以在不同线程之间共享.然而，这带来了危险.如果两个线程试图一次写入同一数据结构该怎么办？  
为了避免这个问题，我们依靠用户为我们提供一些锁定功能.

```c++
/*
Locking functions  

MuPDF is kept deliberately free of any knowledge of particularthreading systems. As such, in order for safe multi-threaded operation, we rely on callbacks to client provided functions.  

A client is expected to provide FZ_LOCK_MAX number of mutexes,and a function to lock/unlock each of them. These may berecursive mutexes, but do not have to be.  

If a client does not intend to use multiple threads, then itmay pass NULL instead of a lock structure.  

In order to avoid deadlocks, we have one simple rule internally as to how we use locks: We can never take lock nwhen we already hold any lock i, where 0 <= i <= n. In orderto verify this, we have some debugging code, that can beenabled by defining FITZ_DEBUG_LOCKING.
*/
typedef struct
{
  void *user;
  void (*lock)(void *user, int lock);
  void (*unlock)(void *user, int lock);
} fz_locks_context;
enum {
  ...
  FZ_LOCK_MAX
};
```  

如果要在多线程环境中使用MuPDF，则要求用户定义 fz_LOCK MAX锁(当前为4，尽管将来可能会更改)，以及用于锁定和解锁它们的功能.
在分线程中，可以通过pthread_mutex_t来实现锁定. 在Windows中，可以使用Mutex或CriticalSection(后者更轻巧).

不假定这些锁是递归的(尽管递归锁可以正常工作).
为避免死锁，MuPDF保证如果该线程已持有锁m(对于n> m)，则永远不要解锁n.
在多线程环境中使用MuPDF时，要遵循3条简单的规则：  

1. 不允许在不同线程中同时调用MuPDF
使用相同的Context.  

    在大多数情况下，为每个线程使用不同的Context是最简单的.只需在创建线程的同时创建一个新的Context.有关更多信息，请参见5.5克隆.  

2. 不允许在不同线程中同时调用MuPDF来使用同一文档.  

    一次只能有一个线程访问文档.从该文档创建显示列表后，多个线程可以安全地对其进行操作.
可以从几个不同的线程安全地使用该文档，只要有适当的保护措施可以防止同时使用这些文档.  

3. 不允许在不同线程中同时调用MuPDF来使用同一设备.  

    从不同的线程同时调用设备会导致设备混乱并可能崩溃.只要有适当的安全措施防止调用同时进行，从几个不同的线程调用设备是完全可以接受的.

## 5.5 克隆

上下文包含fz_try/fz_catch构造的异常堆栈.因此，尝试同时使用多个线程中的相同上下文会导致崩溃.  
解决方案是"clone"上下文.每个克隆都将共享相同的底层存储(并将继承相同的设置，例如分配器，锁等)，但是将具有自己的异常堆栈.其他设置(例如抗锯齿级别)将在克隆时从原始设置继承，但是可以根据需要更改为其他设置.  
例如，在查看器应用程序中，我们可能希望有一个贯穿文件运行的后台程序生成页面缩略图.为了不干扰前台进程，我们将克隆上下文，并在缩略图线程中使用克隆的上下文.我们可能会选择禁用缩略图处理的抗锯齿功能，以牺牲质量来提高速度.  
为缩略图处理的线程解码的任何图像都将保留在存储区中，因此如果查看者正常的渲染操作需要它们，这些图像将可用.  
使用fz_clone_context：  

```c++
/*
fz_clone_context: Make a clone of an existing context.This function is meant to be used in multi-threaded applications where each thread requires its own context, yet parts of the global state, for example caching, are shared.ctx: Context obtained from fz_new_context to make a copy of.ctx must have had locks and lock/functions setup when created.The two contexts will share the memory allocator, resource store, locks and lock/unlock functions. They will each have their own exception stacks though.Does not throw exception, but may return NULL.
*/
fz_context *fz_clone_context(fz_context *ctx);
```

举例:  
```c++
fz_context *worker_ctx = fz_clone_context(ctx);
```

为了使克隆的上下文安全工作，它们依赖于能够对某些操作进行锁定以使其成为原子.因此，如果基本上下文锁定失败，则fz_clone_context_将返回NULL(指示失败)  


## 5.6 销毁

执行完fz_context(原始的或"clone"上下文)后，您可以使用fz_drop_context销毁它.

```c++
/*
fz_drop_context: Free a context and its global state.
The context and all of its global state is freed, and any buffered warnings are flushed (see fz_flush_warnings). If NULL is passed in nothing will happen.
Does not throw exceptions.
*/
void fz_drop_context(fz_context *ctx);
```
举例:  
```c++
fz_drop_context(ctx);
```
## 5.7 调整

MuPDF的某些功能依赖于启发式方法来做出决策.调优上下文不是在库代码中对这些决策进行硬编码，而是允许调用者使用自己的"已调优"版本覆盖默认值.当前，我们在这里仅定义了2个调用，两者均与图像处理有关，但是可能会扩展为如果仅需要分区，第一个调整功能可以对MuPDF应该解码的图像进行精细控制：  
```c++
/*
fz_tune_image_decode_fn: Given the width and height of an image, the subsample factor, and the subarea of the image actually required, the caller can decide whether to decode the whole imageor just a subarea.  
arg: The caller supplied opaque argument.  
w, h: The width/height of the complete image.  
l2factor: The log2 factor for subsampling (i.e. image will bedecoded to (w>>l2factor, h>>l2factor)).  
subarea: The actual subarea required for the current operation.The tuning function is allowed to increase this in size if required.
*/
typedef void(fz_tune_image_decode_fn)(void *arg, int w, int h, int
l2factor, fz_irect *subarea);
```
允许对比立即需要的更大的区域进行解码的目的是，可以将这些更大的区域放入缓存中.这可能意味着可以从缓存中满足将来的请求，而不需要完整的新解码.这种情况的一个示例可能是MuPDF为查看器应用程序供电，并且页面缓慢地平移到屏幕上以显示越来越多的图像.这些调整功能将对此类决策的控制权交还给应用程序作者.
定义了此类型的功能以实现所需的策略后，可以使用以下方法将其设置为上下文：
```c++
/*
fz_tune_image_decode: Set the tuning function to use for
image decode.
image_decode: Function to use.
arg: Opaque argument to be passed to tuning function.
*/
void fz_tune_image_decode(fz_context *ctx, fz_tune_image_decode_fn
*image_decode, void *arg);
```

第二个功能允许对缩放图像时使用的缩放进行精细控制：  
```c++
/*
fz_tune_image_scale_fn: Given the source width and height of
image, together with the actual required width and height,
decide whether we should use mitchell scaling.
arg: The caller supplied opaque argument.
dst_w, dst_h: The actual width/height required on the target device.
src_w, src_h: The source width/height of the image.
Return 0 not to use the Mitchell scaler, 1 to use the Mitchell
scaler. All other values reserved.
*/
typedef int (fz_tune_image_scale_fn)(void *arg, int dst_w, int dst_h,int src_w, int src_h);
```

本质上，此例程使应用程序作者可以控制是否使用插值显示图像. MuPDF使用简单的" Mitchell"采样功能，而不是简单的线性插值.这提供了主观上更好的质量.默认是仅在缩小时使用Mitchell缩放器，以避免细节"dropping"图像，但是通过提供调整功能，应用程序作者可以选择在更多(或更少)的情况下使用它根据需要定义了此类功能以实现所需策略，可以使用以下方法将其设置为上下文：

```c++
/*
fz_tune_image_scale: Set the tuning function to use for
image scaling.
image_scale: Function to use.
arg: Opaque argument to be passed to tuning function.
*/
void fz_tune_image_scale(fz_context *ctx, fz_tune_image_scale_fn *image_scale, void *arg);
```

## 5.8 总结

The basic usage of Contexts is as follows:
1. Call  fz_new context to create a context. Pass in any custom allocators required. If you wish to use MuPDF from multiple threads at the same time, you must also pass in locking functions. Set the store size appropriately.
2. Call  fz_clone context to clone the context as many times as you need; typically once for each ‘worker’ thread.
3. Perform the operations required using MuPDF within fz_try/fz_catch constructs.
4. Call  fz_drop context with each cloned context.
5. Call  fz_drop context with the original context.


Things to remember:
1. A  fz_context can only be used in 1 thread at a time.
2. A  fz_document can only be used in 1 thread at a time.
3. A  fz_device can only be used in 1 thread at a time.
4. A  fz_context shares the store with all the  fz_contexts cloned from it.

# 6.错误处理

## 6.1 概述

MuPDF使用异常系统处理所有错误. 这在表面上类似于C++异常，但是(正如MuPDF用C编写)，它是使用包装setjmp/longjmp标准C函数的宏来实现的，最好不要偷偷摸摸，只是想到这些构造就是语言的扩展. 确实，我们已经竭尽全力确保所涉及的复杂性降至最低.除非另有说明，否则所有MuPDF API函数都可以引发异常，因此应在fz_try/fz_always/fz_catch构造中调用.永不引发异常的特定函数包括所有命名为 fz_keep ...，fz_drop ...和fz_free.再加上所有此类"destructor"都将默默接受NULL参数，fz_always块是清理整个过程中使用的资源的绝佳场所.
这种构造的一般解剖如下：
```c++
fz_try(ctx)
{
/* Do stuff in here that might throw an exception.
* NEVER return from here. ’break’ can be used to
* continue execution (either in the always block or
* after the catch block). */
}
fz_always(ctx)
{
/* Anything in here will always be executed, regardless
* of whether the fz_try clause exited normally, or an  
* exception was thrown. Try to avoid calling functions
* that can themselves throw exceptions here, or the rest
* of the fz_always block will be skipped - this is rarely
* what is wanted! NEVER return from here. ’break’ can be
* used to continue execution in, or after the catch block
* as appropriate. */
}
fz_catch(ctx)
{
/* This block will execute if (and only if) anything in
* the fz_try block calls fz_throw. We should clean up
* anything we need to. If we are in a nested fz_try/
* fz/catch block, we can call fz_rethrow to propagate
* the error to the enclosing catch. Unless the exception
* is rethrown (or a fresh exception thrown), execution
* continues after this block. */
}
```  

fz_always的内容是可选的.下面是一个有效的写法：  
```c++
fz_try(ctx)
{
/* Do stuff here */
}
fz_catch(ctx)
{
/* Clean up from errors here */
}
```
在理想的世界中，这就是全部. 不幸的是，有2条皱纹. 第一个相对简单，那就是您一定不能从fz_try块中返回. 这样做会损坏异常堆栈，并导致问题和崩溃. 为了减轻这种情况，您可以安全地退出fz_try，并且执行将传递到fz_always块中(如果有的话，或者在fz_catch块之后继续执行). 同样，您可以突破fz_Always块，并且执行将正确地传入fz_catch块中或之后，但是实际上这没有什么用处. 第二个，更令人费解. 如果您不希望了解造成此问题的漫长而复杂的原因，请跳过以下小节，然后阅读下面的更正示例. 只要您遵循摘要中给出的规则，就可以了.
### 6.1.1 为什么fz_var是必要的？

### 6.1.2 举例：如何使用保护局部变量fz_var

## 6.2 抛出异常

大多数客户端代码只需要担心捕获内核抛出的异常就无需担心.如果您要实现自己的设备或扩展MuPDF的核心，那么您将需要知道如何生成(并传递)自己的异常.
从整数代码和类似字符串的printf构造并抛出异常：
```c++
enum
{
  FZ_ERROR_NONE = 0,
  FZ_ERROR_MEMORY = 1,
  FZ_ERROR_GENERIC = 2,
  FZ_ERROR_SYNTAX = 3,
  FZ_ERROR_TRYLATER = 4,
  FZ_ERROR_ABORT = 5,
  FZ_ERROR_COUNT
};
void fz_throw(fz_context *ctx, int errcode, const char *, ...);
```
在所有的案例中，你应该使用到FZ_ERROR_GENERIC,例如：  
```c++
fz_throw(ctx, FZ_ERROR_GENERIC, " Failed to open file ’%s’" , filename);
```

FZ_ERROR_MEMORY保留用于因内存分配失败而引发的异常.这很少会被应用程序代码抛出.此类异常的典型生成器是 fz_malloc/fz_calloc/fz_realloc等.
FZ_ERROR_SYNTAX保留用于在解释文档文件时由于语法错误而引发的异常.这使解释器代码可以跟踪在文件中发现了多少语法错误，并在传递了合理的数目后中止解释.
FZ_ERROR_TRYLATER保留用于在渐进模式下由于缺少数据而引发的异常(有关更多详细信息，请参见第16章"渐进模式").捕获此类型的错误可能会触发不同的处理，从而在收到更多数据时重试该操作.
FZ_ERROR_ABORT保留用于应停止任何正在进行的操作的异常；例如，在页面上循环注释以呈现它们时，大多数异常都在顶层捕获并被忽略，以确保单个损坏的注释不会导致后续注释被跳过.
FZ_ERROR_ABORT可用于覆盖此行为，并使注释呈现过程尽快结束.

## 6.3 处理异常

一旦捕获到异常，大多数代码将简单地整理所有松散的资源(以防止泄漏)，并将异常重新扔给更高层的处理程序.
在程序的顶层，显然这不是一个选择. catch子句需要使用调用程序用于错误处理的任何过程来返回错误.
可以使用以下命令(从fz_catch块内部)读取捕获到的错误消息的详细信息：
```c++
const char *fz_caught_message(fz_context *ctx);
```
该错误将以这种方式保持可读性，直到在同一上下文中再次使用fz_try/fz_catch为止.
某些代码可以选择吞下该错误，然后以其他方式重试相同的代码.为此，我们可以使用以下方法找出错误的类型：
```c++
int fz_caught(fz_context *ctx);
```
有关可能的异常类型的列表，请参见6.2引发异常.
例如，如果在尝试将页面呈现为整页位图时抛出了异常，则完全有可能是由于内存不足.应用程序可能合理地决定一次重试渲染一次.
但是，如果由于文件损坏导致渲染失败，我们将无法通过重试获得任何收益-因此，应用程序应在尝试其他方法之前检查异常的类型，以确保它是fz_ERROR MEMORY.
为了简化决定是否传递给定类型的异常的工作，我们提供了一个便捷函数，只需重新抛出特定类型即可：
```c++
void fz_rethrow_if(fz_context *ctx, int errcode);
```

## 6.4 总结

The basic exception handling rules are as follows:
1. All MuPDF functions except those that explicitly state otherwise, throw exceptions on errors, and must therefore be called from within a fz_try/fz_catch construct.
2. A fz_try block must be paired with a fz_catch block, and optionally a fz_always block can appear between them.
3. Never return from a fz_try block.
4. A fz_try block will terminate when control reaches the end of the block, or when break is called.
5. Any local variable that is changed within a fz_try block may lose its value if an exception occurs, unless protected by fz_var call.
6. The contents of the fz_always block will always be executed (after the fz_try block and before the fz_catch block, if appropriate).
7. If an exception is thrown during the fz_try block, control will jump to the fz_always block (if there is one) and then continue to the fz_catch block.

# 7.内存管理与存储

## 7.1 概述

MuPDF运行时，它将各种对象保存在内存中，并将它们在其各个组件之间传递.例如，MuPDF可能会在PDF解释器中读取路径定义，然后将其首先传递到显示列表中，然后传递到渲染器中.  
为了避免不必要的数据复制，使用了参考计数方案.每个重要对象都有一个引用计数，因此，当代码的一个区域保留对某物(也许是显示列表)的引用时，就无需大量复制数据.在上面的示例中，PDF解释器可能包含一个引用，并且首先显示列表，然后呈现器可能获取其他引用.一些参考文献仅保留很短的时间，但是其他参考文献可以保留更长的时间.  
在显示文件的过程中，MuPDF将各种资源加载到内存中，例如字体和图像.通过在文件的整个处理过程中将这些资源保存在内存中，我们可以避免每次需要时都重新加载它们.  
渲染文档时，需要更多内存来保存字体的字形渲染版本或图像的解码版本.通过将这些已解码的版本保留在内存中，我们可以避免下次需要相同的字形或相同的图像时，无需对它们进行重新解码.
保留所有这些数据最终可能会使用大量内存，这对于某些系统可能是不可行的.同样，不保留任何内容都会导致性能急剧下降.
解决方案是在内存中保留尽可能多的内存，但又不要过多，以至于我们无法满足其他需求. MuPDF使用称为"商店"的机制来实现这一目标.  
存储是一种用于保存可能重复使用的数据块的机制.每当MuPDF需要这样的数据块时，它都会检查商店以查看数据是否已经存在-如果存在，则可以立即重用.如果不是，则代码本身形成数据(加载，计算或解码等)，然后将其放入存储中.  
MuPDF分配代码已绑定到商店中，因此，如果分配失败，则将对象从商店中逐出，然后重试分配.内存的这种"清理"意味着我们可以安全地保留大量缓存的数据，而不必担心它将导致我们内存不足.

## 7.2 创建存储

该存储是fz_new_context调用的一部分(请参见"context"一章)创建，并与通过fz_clone_context获得的任何上下文共享. 在此调用中，"存储限制"被指定为字节大小. FZ_STORE_ UNLIMITED的特殊值用于指示没有太多的内存.

## 7.3 内存不足处理

作为最后的手段，使用MuPDF的应用程序可以通过更改其策略来应对低内存事件. 例如，如果由于分配失败而无法渲染数据带，则可以退后并尝试使用较小的带大小. 或者，我们可以选择不使用显示列表，而每次都直接重新解释底层文件，从而加快了内存的交换速度.
为此，由于分配失败而引发的所有异常均具有fz_ERROR MEMORY类型，从而使调用者可以使用fz_caught轻松区分它们并做出相应的反应.

### 7.3.1 操作

有关商店的更多信息，请参见第二部分的第21章"内部存储".

# 8.文档接口

## 8.1 概述

尽管MuPDF处理多种不同的文件格式，但它提供了用于处理它们的统一API. fz_document API允许对文档执行所有常见操作，从而将实现细节隐藏在调用者的周围.  
并非所有文档类型都具有全部功能(例如，JPEG文件不支持注释)，但是API返回的是合理值.

## 8.2 打开/关闭文档

加载文档的最简单方法是从本地归档系统加载文档：
```c++
/*
fz_open_document: Open a PDF, XPS or CBZ document.   
Open a document file and read its basic structure so pages and  objects can be located. MuPDF will try to repair broken  documents (without actually changing the file contents).   
The returned fz_document is used when calling most other  document related functions.    
filename: a path to a file as it would be given to open(2).
*/
fz_document *fz_open_document(fz_context *ctx, const char *filename);
```  

对于嵌入式系统或安全应用程序，使用本地归档系统可能不合适，因此可以使用另一种方法来从fz_stream中打开文档.有关fz_stream的更多详细信息，请参见第12章Stream接口.  

```c++
/*
fz_open_document_with_stream: Open a PDF, XPS or CBZ document.  
Open a document using the specified stream object rather than opening a file on disk.  
magic: a string used to detect document type; either a file name or mime-type.
*/
fz_document *fz_open_document_with_stream(fz_context *ctx, const char *magic, fz_stream *stream);
```
几乎所有数据源都可以包装为fz_stream.有关更多详细信息，请参见第12章Stream接口.与MuPDF中的大多数其他对象一样，fz_document的引用计数是：  

```c++
/*
fz_keep_document: Keep a reference to an open document.  
Does not throw exceptions.
*/
fz_document *fz_keep_document(fz_context *ctx, fz_document *doc);  

/*
fz_drop_document: Release an open document.  
The resource store in the context associated with fz_document is emptied, and any allocations for the document are freed when the last reference is dropped.  
Does not throw exceptions.
*/
void fz_drop_document(fz_context *ctx, fz_document *doc);
```  
删除对文档的最后一个引用后，该文档使用的所有资源(包括商店中的资源)将被释放.  

## 8.3 处理受密码保护的文档

某些文档类型(例如PDF)可能需要密码才能打开文件.获取fz_document后，因此应使用fz_needs_password检查它是否需要密码：
```c++
/*
fz_needs_password: Check if a document is encrypted with a non-blank password.  
Does not throw exceptions.
*/
int fz_needs_password(fz_context *ctx, fz_document *doc);
```  
如果需要密码，则可以使用fz身份验证密码提供一个：  
```c++
/*
fz_authenticate_password: Test if the given password can decrypt the document.  
password: The password string to be checked. Some document specifications do not specify any particular text encoding, so neither do we.  
Returns 0 for failure to authenticate, non-zero for success.  
For PDF documents, further information can be given by examining the bits in the return code.  
Bit 0 => No password required Bit 1 => User password authenticated Bit 2 => Owner password authenticated Does not throw exceptions.
*/
int fz_authenticate_password(fz_context *ctx, fz_document *doc, const char *password);
```  

## 8.4 处理可重排文件
  
某些文档类型(例如Epub)要求在内容呈现之前对其进行布局.这是通过调用fz布局文档来完成的：
```c++
/*
fz_layout_document: Layout reflowable document types.  
w, h: Page size in points.  
em: Default font size in points.  
*/
void fz_layout_document(fz_context *ctx, fz_document *doc, float w, float h, float em);
```  

任何不可重排的文档类型(例如PDF)都将忽略此布局请求.
布局的结果将取决于目标宽度和高度，给定的字体大小以及有效的CSS样式.   
MuPDF具有一组内置的默认CSS样式，如果文档未提供其样式，则将使用这些样式.此外，用户可以提供一个最终集，该最终集将覆盖默认集中找到的所有规则.这样，可以更改渲染文档的外观(可能通过更改文档颜色或字体样式/大小).可以多次布置文档，以使这些属性中的更改生效.   
MuPDF提供了自己的默认CSS样式表，但是用户CSS样式表可以在上下文中覆盖此样式表：
```c++
/*
fz_user_css: Get the user stylesheet source text.
*/
const char *fz_user_css(fz_context *ctx);
/*
fz_set_user_css: Set the user stylesheet source text for use with HTML and EPUB.
*/
void fz_set_user_css(fz_context *ctx, const char *text);
```
用户CSS样式表以空终止的C字符串形式提供  
更改CSS或屏幕大小并重新布置文档时，内容将移动.为了使应用程序不会失去读者的位置，MuPDF提供了一种制作书签的机制，然后在内容布置到新位置后再次查找书签.
```c++
/*
Create a bookmark for the given page, which can be used to find the same location after the document has been laid out with different parameters.
*/
fz_bookmark fz_make_bookmark(fz_context *ctx, fz_document *doc, int page);
/*
Find a bookmark and return its page number.
*/
int fz_lookup_bookmark(fz_context *ctx, fz_document *doc, fz_bookmark mark);  
```

## 8.5 从文档中获取一页

放置了文档后，您大概希望能够对其进行处理. 首先要知道的是它包含多少页. 这可以通过调用fz_count_pages来实现：
```c++
/*
fz_count_pages: Return the number of pages in document
May return 0 for documents with no pages.
*/
int fz_count_pages(fz_context *ctx, fz_document *doc);
```
对于图像等文档类型，它们将显示为一页. 如果忘记布局可重排的文档，这将触发默认尺寸的布局并返回所需的页数.
一旦知道有多少页，就可以为所需的每个页面获取fz_page对象：
```c++
/*
fz_load_page: Load a page.
After fz_load_page is it possible to retrieve the size of the
page using fz_bound_page, or to render the page using
fz_run_page_*. Free the page by calling fz_drop_page.
number: page number, 0 is the first page of the document.
*/
fz_page *fz_load_page(fz_context *ctx, fz_document *doc, int number);
```
n页文档的页面编号为0到n-1. 与大多数其他对象类型一样，fz_page的引用计数：
```c++
/*
fz_keep_page: Keep a reference to a loaded page.
Does not throw exceptions.
*/
fz_page *fz_keep_page(fz_context *ctx, fz_page *page);
/*
fz_drop_page: Free a loaded page.
Does not throw exceptions.
*/
void fz_drop_page(fz_context *ctx, fz_page *page);
```
一旦删除了对页面的最后一个引用，它消耗的资源将全部自动释放.

## 8.6 页面解析

在MuPDF术语中(主要从PDF借用)，页面由页面内容，注释和链接组成.
页面内容(或仅是目录)通常是页面上的普通印刷品.文字，插图，任何页眉或页脚以及某些打印机标记.
注释通常是叠加在这些页面内容顶部的额外信息.例如，页面上的徒手书写，文字上的突出显示/下划线/删除线，便签等.注释通常是由文档发布后阅读文档的人(而非原始作者)添加到文档中的.
通过首先调用fz_first_annot，然后调用fz_next_annot，可以一次从一个页面枚举注释：
```c++
/*
fz_first_annot: Return a pointer to the first annotation on a page.  
Does not throw exceptions.
*/
fz_annot *fz_first_annot(fz_context *ctx, fz_page *page);
/*
fz_next_annot: Return a pointer to the next annotation on a page.  
Does not throw exceptions.
*/
fz_annot *fz_next_annot(fz_context *ctx, fz_annot *annot);
```
注释是按引用计数的，可以照常保存和删除：
```c++
/*
fz_keep_annot: Take a new reference to an annotation.
*/
fz_annot *fz_keep_annot(fz_context *ctx, fz_annot *annot);
/*
fz_drop_annot: Drop a reference to an annotation. If the reference count reaches zero, annot will be destroyed.
*/
void fz_drop_annot(fz_context *ctx, fz_annot *annot);
```
也可以通过将矩形传递给fz绑定注解来对其进行限制：
```c++
/*
fz_bound_annot: Return the bounding rectangle of the annotation.  
Does not throw exceptions.
*/
fz_rect *fz_bound_annot(fz_context *ctx, fz_annot *annot, fz_rect *rect);
```
返回时，将使用注释的边界框填充矩形.
链接描述页面上的"活动"区域；如果用户在这样的区域内"点击"，通常观众应该做出回应.一些链接移至文档中的其他位置，其他链接启动外部客户端，例如邮件或网站.
可以通过调用fz_load链接来读取页面上的链接：
```c++
/*
fz_load_links: Load the list of links for a page.  
Returns a linked list of all the links on the page, each with its clickable region and link destination. Each link is reference counted so drop and free the list of links by calling fz_drop_link on the pointer return from fz_load_links.  
page: Page obtained from fz_load_page.
*/
fz_link *fz_load_links(fz_context *ctx, fz_page *page);
```
这将返回fz链接结构的链接列表. link-> next给出链中的下一个.

## 8.7 颜色管理

某些格式(尤其是PDF)包含大量额外信息，以实现高质量的色彩管理工作流程. 文档界面(和相关的页面界面)具有一些启用此功能的方法.
文档可以具有定义的"输出意图"，以控制用于渲染输出的颜色空间(和配置文件)：
```c++
/*
Find the output intent colorspace if the document has defined one.
*/
fz_colorspace *fz_document_output_intent(fz_context *ctx, fz_document *doc);
```
如果调用者愿意接受，通常会在创建输出像素图之前对其进行询问.
PDF的每一页都可以考虑特定的专色(油墨)进行创作. 这些的详细信息可以从以下位置获得：
```c++
/*
fz_page_separations: Get the separations details for a page.
This will be NULL, unless the format specifically supports
separations (such as gproof, or PDF files). May be NULL even
so, if there are no separations on a page.
Returns a reference that must be dropped.
*/
fz_separations *fz_page_separations(fz_context *ctx, fz_page *page);
```
对于不支持专色的所有文档格式，返回的对象均为NULL(在撰写本文时，除PDF以外的所有格式). 对于PDF，所有不使用分隔符的页面的对象均为NULL.
有关使用这些对象的更多信息，请参见9.5.2高级渲染-叠印和专色.


## 8.8 渲染页面
要渲染页面，您首先需要知道页面的大小。可以通过调用fz_bound_page并传递fz_rect进行填充来发现这一点：
```c++
/*
fz_bound_page: Determine the size of a page at 72 dpi.
Does not throw exceptions.
*/
fz_rect *fz_bound_page(fz_context *ctx, fz_page *page, fz_rect *rect);
```
MuPDF通过将页面内容（和注释）处理到设备上来对其进行操作。 MuPDF中有各种不同的设备（您可以实现自己的设备）。有关更多信息，请参见第9章“设备”界面。目前，仅将设备视为页面上每个图形项依次调用的对象。处理页面的最简单方法是调用fz_run_page：
```c++
/*
fz_run_page: Run a page through a device.
page: Page obtained from fz_load_page.
dev: Device obtained from fz_new_*_device.
transform: Transform to apply to page. May include for example scaling and rotation, see fz_scale, fz_rotate and fz_concat.
Set to fz_identity if no transformation is desired.
cookie: Communication mechanism between caller and library rendering the page. Intended for multi-threaded applications,while single-threaded applications set cookie to NULL. Thecaller may abort an ongoing rendering of a page. Cookie also communicates progress information back to the caller. Thefields inside cookie are continually updated while the page isrendering.
*/
void fz_run_page(fz_context *ctx, fz_page *page, fz_device *dev, const
fz_matrix *transform, fz_cookie *cookie);
```
这将导致页面内容和注释中的每个图形对象依次被转换并馈送到设备中。为了更好地控制，您可能希望分别运行页面内容和注释：
```c++
/*
fz_run_page_contents: Run a page through a device. Just the mainpage content, without the annotations, if any.
page: Page obtained from fz_load_page.
dev: Device obtained from fz_new_*_device.
transform: Transform to apply to page. May include for example scaling and rotation, see fz_scale, fz_rotate and fz_concat.
Set to fz_identity if no transformation is desired.
cookie: Communication mechanism between caller and library rendering the page. Intended for multi-threaded applications,
while single-threaded applications set cookie to NULL. The caller may abort an ongoing rendering of a page. Cookie also communicates progress information back to the caller. The fields inside cookie are continually updated while the page is rendering.
*/
void fz_run_page_contents(fz_context *ctx, fz_page *page, fz_device
*dev, const fz_matrix *transform, fz_cookie *cookie);  

/*
fz_run_annot: Run an annotation through a device.
page: Page obtained from fz_load_page.
annot: an annotation.
dev: Device obtained from fz_new_*_device.
transform: Transform to apply to page. May include for example scaling and rotation, see fz_scale, fz_rotate and fz_concat.
Set to fz_identity if no transformation is desired.
cookie: Communication mechanism between caller and library rendering the page. Intended for multi-threaded applications,
while single-threaded applications set cookie to NULL. The caller may abort an ongoing rendering of a page. Cookie also communicates progress information back to the caller. The fields inside cookie are continually updated while the page is rendering.
*/
void fz_run_annot(fz_context *ctx, fz_annot *annot, fz_device *dev,
const fz_matrix *transform, fz_cookie *cookie); 
```
这些功能使查看器应用程序可以为页面内容和注释生成单独的显示列表。如果注释经常更改，这将很有用，因为它允许在每个注释而不是每个页面级别上进行重新生成/重绘。  
所有这三个功能（fz_run_page，fz_run_page.contents，fz_run_annot）都使用fz_cookie指针。 Cookie是一种控制页面处理的轻量级方法。有关更多详细信息，请参见9.3 Cookie。对于大多数简单情况，这可以为NULL。  

## 8.9 展示

某些文件格式（例如PDF）可以用作“演示文稿”，页面以幻灯片形式显示-如果可以的话，这是穷人的PowerPoint形式。 本质上，每个页面都包含一条记录，该记录说明在过渡到具有给定图形效果的下一页之前应该显示多长时间。  
核心MuPDF库从不负责将页面实际呈现给用户，因此，不可能期望它能够处理此类转换所需的所有工作。  
它可以做的是在两个特定领域提供帮助。 首先，它可以提供一些功能来帮助调用者完成查询所需转换的任务。 其次，它可以帮助提供一些辅助函数来生成常见转换各个阶段的位图。

### 8.9.1 查询

我们定义一个结构类型来保存任意过渡的细节以及一些不透明的状态：
```c++
enum {
  FZ_TRANSITION_NONE = 0, /* aka ’R’ or ’REPLACE’ */
  FZ_TRANSITION_SPLIT,
  FZ_TRANSITION_BLINDS,
  FZ_TRANSITION_BOX,
  FZ_TRANSITION_WIPE,
  FZ_TRANSITION_DISSOLVE,
  FZ_TRANSITION_GLITTER,
  FZ_TRANSITION_FLY,
  FZ_TRANSITION_PUSH,
  FZ_TRANSITION_COVER,
  FZ_TRANSITION_UNCOVER,
  FZ_TRANSITION_FADE
};
typedef struct fz_transition_s
{
  int type;
  float duration; /* Effect duration (seconds) */
  /* Parameters controlling the effect */
  int vertical; /* 0 or 1 */
  int outwards; /* 0 or 1 */
  int direction; /* Degrees */
  /* Potentially more to come */
  /* State variables for use of the transition code */
  int state0;
  int state1;
} fz_transition;
```
有了这样的结构，我们可以调用一个函数来填写它：
```c++
/*
fz_page_presentation: Get the presentation details for a given page.
transition: A pointer to a transition struct to fill out.
duration: A pointer to a place to set the page duration in seconds.
Will be set to 0 if no transition is specified for the page.
Returns: a pointer to the transition structure, or NULL if there is
no
transition specified for the page.
*/
fz_transition *fz_page_presentation(fz_context *ctx, fz_page *page, fz_transition *transition, float *duration);
```
该结构被定义为足以封装当前定义的PDF转换类型。 如果其他格式需要更多表现力，将来可能会扩展。 调用者可以使用此处的信息自由地直接实现其转换，也可以使用帮助器功能。

### 8.9.2 辅助功能

在第36章"过渡"中可以找到显示这些过渡的有用例程的详细信息。

# 9.设备接口

## 9.1 概述

在许多方面，设备接口是MuPDF的核心。当任何给定的文档处理程序被告知运行页面（fz_run_page）时，适当的文档解释器会将页面内容序列化为一系列图形操作，并调用设备接口以执行这些操作。 MuPDF中存在设备接口的许多不同实现。最明显的一种是绘图设备。调用此方法时，它将依次将图形对象渲染为Pixmap。另外，我们也可以使用“结构化文本”设备来捕获文本输出，并将其形成易于处理的结构（用于搜索或文本提取）。某些设备（例如SVG输出设备）会将图形对象重新打包为其他格式。这些设备的最终产品是一个新文档，其外观（尽可能）与初始页面相同。最后，诸如“显示列表”设备之类的设备设法既是该接口的实现者，也是该接口的调用者。呼叫者可以将页面内容一次运行到“显示列表”设备，然后将其快速重播多次，以转移到其他设备。是呈现带状页面或在查看器中平移和缩放文档时反复重绘的理想选择。通过实现新设备，呼叫者可以以新颖有趣的方式利用MuPDF的强大功能，也许可以利用设备的特定硬件功能。

## 9.2 设备方法

MuPDF中的每个设备都是fz_device结构的扩展。 它包含一系列函数指针，以实现对不同类型图形对象的处理。 这些功能指针通过便捷功能公开给调用者。 这些便利功能始终应优先于直接调用功能指针，因为它们在后台执行各种内部管理功能。 它们还可以处理函数指针为NULL的情况，这在设备对特定类别的图形对象不感兴趣时可能会发生。 我们不会在这里描述这些设备功能，而是将它们推迟到第2部分的第22章“设备内部”。虽然完全允许调用者自己调用设备便利功能，但是绝大多数应用程序作者永远不会做 因此，它将简单地将每个fz_device视为要传递给解释功能的“黑匣子”（请参见8.8渲染页面）。

## 9.3 Cookie

Cookie是一种轻量级的机制，用于控制和检测给定解释调用的行为(例如,fz_run_page,fz_run_page_contents,fz_run_annot,fz_run_display_list)  

要使用cookie需要进行简单定义：  
```c++
fz_cookie *cookie = { 0 };
```  
设置必填字段，例如：  
```c++
cookie.incomplete_ok = 1;
```
然后将&cookie作为最后一个参数传递给解释调用，用于
例：
```c++
fz_run_page(ctx, page, dev, transform, &cookie);
```
fz_cookie的内容和定义比其他结构更容易更改，因此始终将所有子字段初始化为0非常重要。
如上所述，是最安全的方法。
如果将新字段添加到结构中，则无需更改调用者代码，并且零值新字段的默认行为将始终保持不变。  

### 9.3.1 检查错误

显示页面时，如果遇到错误，该怎么办？ 我们可以选择完全停止解释，但这意味着相对不重要的错误（例如字体丢失或损坏）将阻止我们从页面中获取任何有用的信息。 我们可以选择忽略错误并继续，但是这对于（例如）打印运行来说是个问题，在这种情况下，我们不希望打印1000份文档，而只是发现图像丢失。 MuPDF采取的策略是在解释过程中吞下错误，但要在Cookie的错误字段中保留错误计数。 这样，调用者可以在最后检查cookie.errors == 0，以了解运行是否已成功完成而没有发生任何事件。

### 9.3.2 在线程中使用cookie

内容解释可能需要（相对）较长的时间。一旦开始，将很有用a）知道我们要进行的处理有多远，以及b）如果不再需要运行结果时能够中止处理。随着运行的进行，cookie中的2个字段将更新。首先，progress将设置为随着进度的增加而增加的数字。非正式地将其视为到目前为止已处理的对象数。在某些情况下（特别是在处理显示列表时），我们可以知道该值的上限，并且该值将作为progress_max给出。在没有上限的情况下，progress_max将设置为-1。上限可能从-1开始，然后在以后更改为已知值。这些值旨在使用户能够给出反馈，而不应视为对性能的保证。在运行内容时，解释器会定期检查cookie的中止字段。如果发现它不为零，则其余内容将被忽略。如果调用者确定启动后不再需要运行结果（也许用户更改了页面或关闭了文件），则应将cookie的abort字段设置为1。关于cookie的检查频率，或者解释器在设置了中止字段后将如何响应。设置中止标志永远不会有害，但是经常会有所帮助。一旦将标志设置为1，就永远不要将其重置为0，因为结果将不可预测。不管abort的设置如何，在运行结束之前都不能释放运行使用的资源。调用者仍然需要等待fz_run_page（或其他）调用完成，然后才能安全删除该页面等。

### 9.3.3 用cookie部分渲染

当在渐进模式下工作时，cookie也会发挥作用。 incomplete_ok和incomplete字段用于此目的。 请参阅第16章渐进模式以获取更多详细信息。

## 9.4 设备提示

设备提示是一种机制，可用于控制设备的行为以及解释程序对该设备的调用。他们非正式地提供有关设备将要做什么以及呼叫者需要担心的事情的提示。设备提示采用int中可以启用（设置）或禁用（清除）的位的形式。呼叫者可以查询这些提示以自定义其行为。
```c++
/*
fz_enable_device_hints : Enable hints in a device.
hints: mask of hints to enable.
*/
void fz_enable_device_hints(fz_context *ctx, fz_device *dev, int hints);
/*
fz_disable_device_hints : Disable hints in a device.
hints: mask of hints to disable.
*/
void fz_disable_device_hints(fz_context *ctx, fz_device *dev, int hints);
```
一些设备将提示设置为非零的默认值。例如，在运行文本提取操作（用于实现文本搜索）时，处理图像或阴影几乎没有意义。因此，文本提取设备设置FZ_IGNORE_IMAGE和FZ_IGNORE_SHADE。这样，解释功能（例如fz_run_page或fz_run_display_list）就不必费心准备要调用设备的图像，从而提高了性能。但是，如果您希望将页面内容提取到html文件中，则可能需要因此，您可以在运行提取之前禁用FZ_IGNORE_IMAGE提示，并且文本提取设备会知道将其包括在其输出结构中。这组提示将来可能会扩展，但是当前定义为：
```c++
enum
{
  /* Hints */
  FZ_DONT_INTERPOLATE_IMAGES = 1,
  FZ_MAINTAIN_CONTAINER_STACK = 2,
  FZ_NO_CACHE = 4,
};
```
启用FZ_DONT_INTERPOLATE_IMAGES会阻止绘图设备执行插值。禁用抗锯齿功能时，MuTool Draw使用此功能禁止插值。现在可以使用“调整上下文”（参见5.7节“调整”）对此进行更精细的控制。  
启用FZ_MAINTAIN_CONTAINER_STACK可使MuPDF维护一堆容器来帮助设备。这样可以有效地将一些设备中必须包含的逻辑转移到易于重用的位置。当前唯一使用此功能的设备是SVG设备，但是希望将来会有更多的设备使用它。  
启用FZ_NO_CACHE会告诉解释器尝试避免在内容运行结束后缓存任何对象。例如，在搜索PDF中的文本字符串时可以使用此方法，以避免将所有页面的图像，阴影，字体等和其他资源拉入内存，而这会浪费当前页面上使用的资源。

## 9.5 内置设备

MuPDF内置了多种设备，但不应作为最终的清单。 预计将编写其他设备来扩展MuPDF-实际上，由MuPDF构建的某些软件已经包含了自己的设备。

### 9.5.1 bbox装置

BBox设备是一种简单的设备，可以计算页面上所有标记操作的bbox。
```c++
/*
fz_new_bbox_device: Create a device to compute the bounding
box of all marks on a page.
The returned bounding box will be the union of all bounding
boxes of all objects on a page.
*/
fz_device *fz_new_bbox_device(fz_context *ctx, fz_rect *rectp);
```  
传递给fz_new_bbox_device的fz_rect必须明显处于范围内
在设备寿命期内，因为设备更新时会更新,用内容的边界框关闭。 

### 9.5.2 画图装置

Draw设备是MuPDF的核心渲染器。每个绘图设备实例都使用目标Pixmap构建（有关更多详细信息，请参见10.3 Pixmaps部分），并且传递给设备的每个图形对象都将渲染到该Pixmap中。
```c++
/*
fz_new_draw_device: Create a device to draw on a pixmap.
dest: Target pixmap for the draw device. See fz_new_pixmap*
for how to obtain a pixmap. The pixmap is not cleared by the
draw device, see fz_clear_pixmap* for how to clear it prior to
calling fz_new_draw_device. Free the device by calling
fz_drop_device.
*/
fz_device *fz_new_draw_device(fz_context *ctx, fz_pixmap *dest);
```
大多数情况下，我们渲染完整的像素图，但是存在一种机制允许我们在像素图中渲染给定的bbox：
```c++
/*
fz_new_draw_device_with_bbox: Create a device to draw on a pixmap.
dest: Target pixmap for the draw device. See fz_new_pixmap*
for how to obtain a pixmap. The pixmap is not cleared by the
draw device, see fz_clear_pixmap* for how to clear it prior to
calling fz_new_draw_device. Free the device by calling
fz_drop_device.
clip: Bounding box to restrict any marking operations of the
draw device.
*/
fz_device *fz_new_draw_device_with_bbox(fz_context *ctx, fz_pixmap *dest, const fz_irect *clip);
```
这对于更新页面的特定区域（例如，当注释已被编辑或移动时）而不重新绘制整个内容很有用。在渲染过程中，绘图设备可能会创建新的临时内部像素图以应对透明度和分组。这对于调用者是不可见的，可以安全地视为实现细节，但是在估计给定渲染操作的内存使用时应考虑到这一点。所需内部像素图的确切数量和大小取决于所显示图形对象的确切复杂性和组成。为了限制内存使用，一种典型的策略是在带区中渲染页面。而不是创建页面大小的单个像素图并进行渲染，而是在页面上为“切片”创建像素图，然后一次渲染一个。节省的内存不仅体现在基本像素图的成本上，还可以限制渲染过程中使用的内部像素图的大小。这样做的代价是页面内容确实需要重复运行。可以通过直接从文件重新解释来实现，但这可能很昂贵。下一个设备提供了一条路径来帮助您解决此问题。

**高级渲染-叠印和斑点**  

大多数格式都是根据一些相当简单的“知名”色彩空间来定义页面的，例如RGB和CMYK。某些格式（尤其是PDF）功能更强大，并允许使用各种非标准的“现场”墨水构造页面。当与套印之类的高级功能结合使用时，需要注意确保渲染完全符合预期。例如，如果将PDF页面构造为使用叠印渲染页面，则将其渲染为CMYK（或CMYK + Spots）像素图仅是严格意义上的。使用（例如）RGB像素图，CMYK颜色将在绘制时向下映射为RGB，从而丢失了正确叠印以后的图形对象所需的信息。尽管如此，虽然我们可能希望获得页面的“真实”再现，但我们可能要求它最终以RGB像素图的形式出现。因此，我们真正想要的是对叠印的工作方式进行“模拟”。一种工作方式是调用绘图设备并请求CMYK + Spots渲染，然后要求调用者将其手动转换为所需的目标色彩空间。这不符合MuPDF中以友好方式封装功能的普遍期望。因此，绘图设备会检查像素图的“分离”字段，以决定如何渲染。如果没有提供分隔值（即为NULL），则绘图设备会假定不需要任何形式的套印（或套印模拟）。如果存在间隔值，并且至少有一个未完全禁用的间隔，则绘图设备将在内部绘制CMYK + Spots像素图（其中，点是来自间隔值的非禁用间隔）。此渲染可以安全地进行启用套印处理的过程。在渲染结束时，绘图设备将从CMYK + Spots像素图向下转换为初始像素图的色彩空间。初始像素图中存在的任何专色剂都将从渲染的像素图中填充。任何没有的斑点都将转换为原色。因此，通过使用分隔对象创建传递到绘图设备中的初始像素图，并根据需要将着色剂正确设置为合成/斑点/禁用，可以根据需要控制套印或套印模拟。

### 9.5.3 显示清单装置

显示列表设备仅将对它的所有呼叫记录在列表中。 然后，此列表可以稍后以不同的转换多次播放到其他设备。
```c++
/*
fz_new_list_device: Create a rendering device for a display list.
When the device is rendering a page it will populate the
display list with drawing commsnds (text, images, etc.). The
display list can later be reused to render a page many times
without having to re-interpret the page from the document file
for each rendering. Once the device is no longer needed, free
it with fz_drop_device.
list: A display list that the list device takes ownership of.
*/
fz_device *fz_new_list_device(fz_context *ctx, fz_display_list *list);
```
有关使用显示列表的更多详细信息，请参见第11章“显示列表”。

### 9.5.4 PDF输出装置

PDF输出设备仍在开发中，因为其字体处理不完整。 尽管如此，对于某些类的文件它还是有用的。 最终用户可能更喜欢使用文档编写器界面（请参见第15章“文档编写器”界面）来包装此类，而不是直接调用它。 尽管如此，在生成PDF文件的特定部分（例如注释的外观流）时，这在特定情况下还是有用的。 “ PDF输出”设备将采用其调用的一系列图形操作，并将其与一系列必需的资源一起重新形成为一系列PDF操作。 然后可以将它们形成一个全新的PDF页面（或PDF批注），然后可以将其插入文档中.
```c++
/*
pdf_page_write: Create a device that will record the
graphical operations given to it into a sequence of
pdf operations, together with a set of resources. This
sequence/set pair can then be used as the basis for
adding a page to the document (see pdf_add_page).
doc: The document for which these are intended.
mediabox: The bbox for the created page.
presources: Pointer to a place to put the created
resources dictionary.
pcontents: Pointer to a place to put the created
contents buffer.
*/
fz_device *pdf_page_write(fz_context *ctx, pdf_document *doc, const
fz_rect *mediabox, pdf_obj **presources, fz_buffer **pcontents);
```

### 9.5.5 结构化文本装置

结构化文本设备用于从给定的图形流中提取文本及其在输出页面上的位置。 它还可以选择包括图像的详细信息及其在输出中的位置。
```c++
/*
fz_new_stext_device: Create a device to extract the text on a page.
Gather and sort the text on a page into spans of uniform style,
arranged into lines and blocks by reading order. The reading order
is determined by various heuristics, so may not be accurate.
sheet: The text sheet to which styles should be added. This can
either be a newly created (empty) text sheet, or one containing
styles from a previous text device. The same sheet cannot be used
in multiple threads simultaneously.
page: The text page to which content should be added. This will
usually be a newly created (empty) text page, but it can be one
containing data already (for example when merging multiple pages, or
watermarking).
*/
fz_device *fz_new_stext_device(fz_context *ctx, fz_stext_sheet *sheet,
fz_stext_page *page);
```
这可以用作搜索（包括找到匹配项时突出显示文本），导出文本文件（或基于文本和图像的文件（例如HTML））甚至进行更复杂的页面分析（例如发现哪些区域）的基础。 页面的内容是文本，什么是图形等）。 应该使用fz_new_stext_sheet创建一个（最初是空的）fz_stext_sheet，并使用fz新的stext页面创建一个空的fz_stext_page。 这些在对fz_new_stext_device的调用中使用。 将内容运行到该设备后，将使用该页面使用的常用样式填充工作表，并且将使用提取的文本的详细信息及其位置填充该页面。

### 9.5.6 SVG输出装置

### 9.5.7 测试设备

顾名思义，“测试”设备会测试一组使用了功能的给定页面内容。当前，这仅限于测试所使用的图形对象是灰度还是彩色。将来可能会添加其他功能的测试。
```c++
/*
fz_new_test_device: Create a device to test for features.
Currently only tests for the presence of non-grayscale colors.
is_color: Possible values returned:
0: Definitely greyscale
1: Probably color (all colors were grey, but there
were images or shadings in a non grey colorspace).
2: Definitely color
threshold: The difference from grayscale that will be tolerated.
Typical values to use are either 0 (be exact) and 0.02 (allow an
imperceptible amount of slop).
options: A set of bitfield options, from the FZ_TEST_OPT set.
passthrough: A device to pass all calls through to, or NULL.
If set, then the test device can both test and pass through to
an underlying device (like, say, the display list device). This
means that a display list can be created and at the end we’ll
know if its color or not.
In the absence of a passthrough device, the device will throw
an exception to stop page interpretation when color is found.
*/
fz_device *fz_new_test_device(fz_context *ctx, int *is_color, float
threshold, int options, fz_device *passthrough);
```
颜色检测功能的预期目的是允许应用程序（例如打印机）轻松检测给定页面是否需要使用彩色墨水，或者灰度渲染是否足够。该设备可以单独使用，也可以以直通设备的形式使用。

**独立使用**

以最简单的形式，可以通过将**passthrough**设置为**NULL**来独立创建设备。在进行每个后续设备调用时，设备将测试传递给它的图形对象，以查看它是否在给定的中性颜色阈值之内。如果是，则设备继续。如果不是，则将is所指向的int颜色设置为非零。对于诸如路径或文本之类的图形对象，这是一个简单的评估，几乎不需要时间。但是，对于“图像”或“底纹”，则有些棘手。可以在能够使用非中性色（可能是RGB或CMYK）的颜色空间中定义图像，但是图像本身只能在该空间中使用中性色。要正确确定是否需要颜色，需要更多的CPU密集处理。因此，设备默认情况下将仅查看颜色空间。可以检查最后返回的颜色值，以建立测试的置信度。 0表示“绝对是灰度”，1表示“可能是彩色”（即“看到的图像或阴影可能包含非中性色”），2表示“绝对是彩色”。如果呼叫者希望花费CPU周期来获得确定的答案，则可以将选项设置为FZ_TEXT_OPT_IMAGES |FZ_TEXT_OPT_SHADINGS以及图像和底纹。作为一种优化，鉴于检查非图像和阴影的速度要快得多，因此不设置选项就运行一次设备是值得的，然后根据需要仅在设置了选项的情况下再次运行设备。如果设备以**passthrough**作为**NULL**运行，则一旦遇到“确定的”非中性色，它将引发FZ_ABORT错误。这可以节省大量时间，因为当观察到第一个图形操作之一足以知道正在使用颜色时，它就避免了解释器需要遍历整个页面。

**直通使用**

如上所述，该设备的设想用例是检测页面内容是否需要颜色，以允许打印机决定是对彩色墨水进行光栅化还是对更快/更便宜的灰度进行扫描。这样的打印机通常将在带区模式下运行，这需要使用显示列表（或至少从中受益很大）。通过在直通模式下使用该设备，可以在构建列表的同时执行测试。只需像通常那样创建显示列表设备，然后将其作为**passthrough**传递到fz_new_test_device中即可。然后通过返回的测试设备运行页面内容。测试设备会将每个呼叫转接到基础列表设备，因此显示列表将正常构建。在此模式下运行时，设备将不再使用引发FZ_ABORT错误的“提前退出”优化。

### 9.5.8 跟踪设备

跟踪设备是一个简单的调试设备，它允许输出类似XML的设备调用表示。
```c++
/*
fz_new_trace_device: Create a device to print a debug trace of all
device calls.
*/
fz_device *fz_new_trace_device(fz_context *ctx, fz_output *out);
```
这是可视化显示列表内容的有用工具。

# 10.构建模块

## 10.1 概述

MuPDF使用了许多构造和概念，尽管这些构造和概念本身不值得拥有章节，但值得一提。

## 10.2 色彩空间

为了表示图形对象的给定颜色，我们需要两种颜色
组件值和指定颜色的颜色空间的详细信息。
颜色值被简单地定义为浮点数（通常介于0和1之间（包括0和1），
和颜色空间是使用fz_colorspace_structure定义的。
与MuPDF中的许多其他此类结构一样，这些都被引用计数
对象（请参见[21.3参考计数](#213-参考计数)）。

### 10.2.1 色彩空间基础

MuPDF包含一组内置的色彩空间，可以满足最简单的要求。 这些是“设备”色彩空间：
```c++
/*
fz_device_gray: Get colorspace representing device specific gray.
*/
fz_colorspace *fz_device_gray(fz_context *ctx);
/*
fz_device_rgb: Get colorspace representing device specific rgb.
*/
fz_colorspace *fz_device_rgb(fz_context *ctx);
/*
fz_device_bgr: Get colorspace representing device specific bgr.
*/
fz_colorspace *fz_device_bgr(fz_context *ctx);
/*
fz_device_cmyk: Get colorspace representing device specific CMYK.
*/
fz_colorspace *fz_device_cmyk(fz_context *ctx);
/*
fz_device_lab: Get colorspace representing device specific LAB.
*/
fz_colorspace *fz_device_lab(fz_context *ctx);
```
### 10.2.2 色彩空间标签

MuPDF允许索引色彩空间-使用调色板选择色彩空间
（通常）更大的颜色空间中的颜色值。
这些是使用fz_new_indexed_colorspace调用创建的：
```c++
fz_colorspace *fz_new_indexed_colorspace(fz_context *ctx, fz_colorspace *base, int high, unsigned char *lookup);
```
### 10.2.3 色彩空间分离和deviceN

MuPDF颜色空间是可扩展的，因此特定的文档处理程序可以实现-
调整自己的新空间。 PDF的实现方式就是一个很好的例子
分离和DeviceN色彩空间。
这些是特殊的空间，代表任意组合的一种或多种着色剂。
可以将它们映射为更标准的“等效”颜色空间，或（取决于基础设备的功能）他们的原始形式。

### 10.2.4 更多信息

有关色彩空间的更多信息，请参见[第28章“色彩空间内部”](#28内部色彩空间)。

## 10.3 像素图

### 10.3.1 概述

fz_pixmap结构用于表示连续色调像素的二维阵列。在整个MuPDF中，它都用作绘图设备渲染的目标，在处理过程中以及在图像解码过程中用作内部缓冲区。  
像素图可以具有任意数量的颜色分量以及可选的Alpha平面。每个组件样本都由一个无符号字符表示。  
像素图每个像素包含一组n个值，其中n = c + s + a。 c是像素图的颜色空间中的颜色分量数（如果颜色空间为NULL，则为0）。 s是像素图中的专色数量（通常为0）。如果没有Alpha平面，则a为0，否则为1。  
最初的c项称为“处理”颜色成分。根据像素图的颜色空间，这些可以是加法或减法。加性空格（例如Gray或RGB）的值为0，暗，255为亮。减法空格（例如CMYK）的值为0（浅色（无墨水），值为255）为深色（完整墨水）。  
接下来的条目是由像素图表示的专色。这些总是减法形式。
最后一个条目（如果a = 1）是alpha值。完全透明为0，完全不透明为255。  
像素图中的数据始终以“块状”格式打包存储。例如，RGB像素图将具有以下形式的数据：RGBRGBRGBRGB ...  
Alpha数据始终作为对应于像素的集合中的最后一个字节发送。因此，具有alpha平面的RGB像素图将具有以下格式的数据：RGBARGBARGBA ...  
带有橙色和绿色斑点的CMYK像素图将具有以下形式的数据：CMYKOGCMYKOGCMYKOG ...  
为了在像素图所使用的基础内存块的布局上提供更大的灵活性，它们具有一个“步幅”字段。这给出了从下面的扫描线上的像素表示的起始地址到同一像素表示的起始地址的字节数差。  
通常情况下，您希望步幅等于宽度乘以图像中的组件数量（包括Alpha），但是在特定情况下，这可能会有所不同。为了创建引用较大像素图的特定“子区域”的像素图，子像素图的步幅值将大于预期值（与原始像素图的步幅值相同），以考虑到每条原始线的额外像素没有解决。 Pixmap可以经常映射到特定于操作系统的位图表示，但是有时它们要求每条扫描线都按字对齐-再次提供跨度就可以做到这一点。自底向上位图可以使用负跨度实现。

### 10.3.2 预置alpha通道

按照惯例，MuPDF以“预乘alpha”格式保存像素图。 这意味着，当存在Alpha平面时，将按Alpha值缩放存储原色和专色的值。 因此，对于像素为R = G = B = 1且具有实心alpha的像素，我们的值为255，但是对于alpha值为0.5的像素，我们将存储的值为127。 之所以使用这种格式，是因为它简化了MuPDF中使用的许多绘图和合成操作。

### 10.3.3 保存

有关保存像素图的信息，请参见[第14章“渲染的输出格式”](#14渲染输出格式)。

## 10.4 位图

fz_bitmap结构用于表示2维数组单色像素。
它们是相当于fz_pixmap结构的每个组件1位。
MuPDF的核心渲染引擎当前不使用fz_bitmap，而是将它们用作输出渲染信息的步骤。
MuPDF中存在函数，可以通过半色调从fz_pixmap创建fz_bitmap。 请参阅第10.5节“半色调”。
```c++
/*
fz_new_bitmap_from_pixmap: Make a bitmap from a pixmap and a
halftone.
pix: The pixmap to generate from. Currently must be a single color
component + alpha (where the alpha is assumed to be solid).
ht: The halftone to use. NULL implies the default halftone.
CHAPTER 10. BUILDING BLOCKS
58
Returns the resultant bitmap. Throws exceptions in the case of
failure to allocate.
*/
fz_bitmap *fz_new_bitmap_from_pixmap(fz_context *ctx, fz_pixmap *pix,
fz_halftone *ht);
fz_bitmap *fz_new_bitmap_from_pixmap_band(fz_context *ctx, fz_pixmap
*pix, fz_halftone *ht, int band_start, int bandheight);
```
这两个函数都通过将fz半色调应用于连续色调值来制作位图来工作。 后者功能是前者的更通用的版本，它允许在乐队中进行渲染时进行正确的操作-即，使用进入半色调表的正确偏移量。 每个位图的数据都首先打包成最高有效字节。 多个组件打包在同一个字节中，因此转换为位图的CMYK像素图在第一个字节CMYKCMYK中将包含2个像素的数据，而第一个像素在最高半字节中。 通常的引用计数行为适用于fz_bitmap，fz_keep_bitmap和fz_drop_bitmap分别声明和释放引用。

## 10.5 半色调

fz_halftone结构表示一组磁贴，每个组件一个，尺寸可能不同。 这些图块中的每一个都是阈值的二维数组（实际上实现为单个组件fz_pixmap）。 在半色调（位图创建）过程中，如果连续色调值小于阈值，则在输出中保持未设置状态。 如果大于或等于，则在输出中进行设置。 为了方便起见，可以使用NULL指针表示默认半色调。 默认半色调也可以通过以下方式获取：
```c++
/*
fz_default_halftone: Create a ’default’ halftone structure
for the given number of components.
num_comps: The number of components to use.
Returns a simple default halftone. The default halftone uses
the same halftone tile for each plane, which may not be ideal
for all purposes.
*/
fz_halftone *fz_default_halftone(fz_context *ctx, int num_comps);
```
半色调的创建是一个专门的领域，已经进行了很多研究。 MuPDF中的机制旨在使人们可以自由地创建和调整半音以适合其特定的应用程序。 通常的引用计数行为适用于fz_halftones，fz_keep_halftone和fz_drop_halftone分别声明和释放引用。

## 10.6 图片

fz_image结构用于表示MuPDF中的通用Image对象。 可以将其视为一种封装，可以从该封装中检索图像的渲染（作为fz_pixmap）和（通常）原始源数据。 有关fz_images的进一步讨论，请参阅第2部分的第19章“图像”界面。尽管完全允许调用者创建图像，但在大多数情况下，它们会将它们视为“黑匣子”，以便随处传递。

## 10.7 缓存区

fz_buffer结构用于表示数据的任意缓冲区。 本质上，它们是任意字节块的表示形式（采用任何所需的编码），并具有简单的函数以字节，char，utf8和按位方式进行扩展，连接和写入。 MuPDF的内部函数和API级别函数都广泛使用fz_buffers。 通常的引用计数行为适用于fz_buffers，fz_keep_buffer和fz_drop_buffer分别声明和释放引用。

## 10.8 转换

fz_matrix结构用于表示用于变换点，形状和其他几何形状的二维矩阵。
fz_matrix结构的六个字段对应于以下形式的矩阵：
$$\begin{pmatrix}a&b&0\\c&d&0\\e&f&0\end{pmatrix}$$

这样的变换矩阵可以用于表示各种各样的不同操作，包括平移，旋转，缩放，高等以及它们的任何组合。
通常，将为特定目的（例如比例尺或平移）创建矩阵。 因此，我们有专门的构造调用。
```c++
/*
fz_scale: Create a scaling matrix.
The returned matrix is of the form [ sx 0 0 sy 0 0 ].
m: Pointer to the matrix to populate
sx, sy: Scaling factors along the X- and Y-axes. A scaling
factor of 1.0 will not cause any scaling along the relevant
axis.
Returns m.
Does not throw exceptions.
*/
fz_matrix *fz_scale(fz_matrix *m, float sx, float sy);
/*
fz_shear: Create a shearing matrix.
The returned matrix is of the form [ 1 sy sx 1 0 0 ].
m: pointer to place to store returned matrix
sx, sy: Shearing factors. A shearing factor of 0.0 will not
cause any shearing along the relevant axis.
Returns m.
Does not throw exceptions.
*/
fz_matrix *fz_shear(fz_matrix *m, float sx, float sy);
/*
fz_rotate: Create a rotation matrix.
The returned matrix is of the form
[ cos(deg) sin(deg) -sin(deg) cos(deg) 0 0 ].
m: Pointer to place to store matrix
degrees: Degrees of counter clockwise rotation. Values less
than zero and greater than 360 are handled as expected.
Returns m.
Does not throw exceptions.
*/
fz_matrix *fz_rotate(fz_matrix *m, float degrees);
/*
fz_translate: Create a translation matrix.
The returned matrix is of the form [ 1 0 0 1 tx ty ].
m: A place to store the created matrix.
tx, ty: Translation distances along the X- and Y-axes. A
translation of 0 will not cause any translation along the
relevant axis.
Returns m.
Does not throw exceptions.
*/
fz_matrix *fz_translate(fz_matrix *m, float tx, float ty);
```
从数学上讲，点是通过将它们相乘来变换的（扩展为3个元素长）。 例如（x’，y’），通过这样的矩阵通过映射（x，y）给出的点计算如下：
$$\begin{pmatrix}x^{'}&y^{'}&1\end{pmatrix}=\begin{pmatrix}x&y&1\end{pmatrix}=\begin{pmatrix}a&b&0\\c&d&0\\e&f&0\end{pmatrix}$$

MuPDF中有多种功能可以执行此类转换：
```c++
/*
fz_transform_point: Apply a transformation to a point.
transform: Transformation matrix to apply. See fz_concat,
fz_scale, fz_rotate and fz_translate for how to create a matrix.
point: Pointer to point to update.
Returns transform (unchanged).
Does not throw exceptions.
*/
fz_point *fz_transform_point(fz_point *restrict point, const fz_matrix
*restrict transform);
CHAPTER 10. BUILDING BLOCKS
62
fz_point *fz_transform_point_xy(fz_point *restrict point, const
fz_matrix *restrict transform, float x, float y);
```

可以使用以下函数对矩形进行变换，该函数考虑到变换可能会“翻转”矩形的事实（即，最小坐标可能在变换后最终变成最大坐标）：

```c++
/*
fz_transform_rect: Apply a transform to a rectangle.
After the four corner points of the axis-aligned rectangle
have been transformed it may not longer be axis-aligned. So a
new axis-aligned rectangle is created covering at least the
area of the transformed rectangle.
transform: Transformation matrix to apply. See fz_concat,
fz_scale and fz_rotate for how to create a matrix.
rect: Rectangle to be transformed. The two special cases
fz_empty_rect and fz_infinite_rect, may be used but are
returned unchanged as expected.
Does not throw exceptions.
*/
fz_rect *fz_transform_rect(fz_rect *restrict rect, const fz_matrix
*restrict transform);
```
同样，忽略一个转换的平移组件来转换一个点可能很有用，因此我们为此提供了一个便捷功能：
```c++
/*
fz_transform_vector: Apply a transformation to a vector.
transform: Transformation matrix to apply. See fz_concat,
fz_scale and fz_rotate for how to create a matrix. Any
translation will be ignored.
vector: Pointer to vector to update.
Does not throw exceptions.
*/
fz_point *fz_transform_vector(fz_point *restrict vector, const fz_matrix
*restrict transform);
```
可以通过将代表矩阵相乘来组合变换。 通过应用矩阵A然后应用矩阵B转换点，将得到与AB转换点相同的结果。
MuPDF提供了一种通过以下方式组合矩阵的API：

```c++
/*
fz_concat: Multiply two matrices.
The order of the two matrices are important since matrix
multiplication is not commutative.
Returns result.
Does not throw exceptions.
*/
fz_matrix *fz_concat(fz_matrix *result, const fz_matrix *left, const
fz_matrix *right);
```
或者，可以将运算专门应用于现有矩阵。 由于矩阵运算的非交换性质，因此在现有矩阵之前或之后应用新运算很重要。
例如，如果您有一个进行旋转的矩阵，并且希望将其与翻译结合使用，则必须决定是要在旋转之前（“前”）还是在旋转后（“后”）进行翻译。
MuPDF具有用于此类操作的各种API函数：
```c++
/*
fz_pre_scale: Scale a matrix by premultiplication.
m: Pointer to the matrix to scale
sx, sy: Scaling factors along the X- and Y-axes. A scaling
factor of 1.0 will not cause any scaling along the relevant
axis.
Returns m (updated).
Does not throw exceptions.
*/
fz_matrix *fz_pre_scale(fz_matrix *m, float sx, float sy);
/*
fz_post_scale: Scale a matrix by postmultiplication.
m: Pointer to the matrix to scale
sx, sy: Scaling factors along the X- and Y-axes. A scaling
factor of 1.0 will not cause any scaling along the relevant
axis.
Returns m (updated).
Does not throw exceptions.
CHAPTER 10. BUILDING BLOCKS
64
*/
fz_matrix *fz_post_scale(fz_matrix *m, float sx, float sy);
/*
fz_pre_shear: Premultiply a matrix with a shearing matrix.
The shearing matrix is of the form [ 1 sy sx 1 0 0 ].
m: pointer to matrix to premultiply
sx, sy: Shearing factors. A shearing factor of 0.0 will not
cause any shearing along the relevant axis.
Returns m (updated).
Does not throw exceptions.
*/
fz_matrix *fz_pre_shear(fz_matrix *m, float sx, float sy);
/*
fz_pre_rotate: Rotate a transformation by premultiplying.
The premultiplied matrix is of the form
[ cos(deg) sin(deg) -sin(deg) cos(deg) 0 0 ].
m: Pointer to matrix to premultiply.
degrees: Degrees of counter clockwise rotation. Values less
than zero and greater than 360 are handled as expected.
Returns m (updated).
Does not throw exceptions.
*/
fz_matrix *fz_pre_rotate(fz_matrix *m, float degrees);
/*
fz_pre_translate: Translate a matrix by premultiplication.
m: The matrix to translate
tx, ty: Translation distances along the X- and Y-axes. A
translation of 0 will not cause any translation along the
relevant axis.
Returns m.
Does not throw exceptions.
*/
fz_matrix *fz_pre_translate(fz_matrix *m, float tx, float ty);
```
最后，有时找到表示给定转换的逆矩阵是有用的。
这可以通过“反转”矩阵来实现。
并非在所有情况下都可能做到这一点，但是对于大多数“行为良好”的转换来说，这是可以实现的。
```c++
/*
fz_invert_matrix: Create an inverse matrix.
inverse: Place to store inverse matrix.
matrix: Matrix to invert. A degenerate matrix, where the
determinant is equal to zero, can not be inverted and the
original matrix is returned instead.
Returns inverse.
Does not throw exceptions.
*/
fz_matrix *fz_invert_matrix(fz_matrix *inverse, const fz_matrix *matrix);
/*
fz_try_invert_matrix: Attempt to create an inverse matrix.
inverse: Place to store inverse matrix.
matrix: Matrix to invert. A degenerate matrix, where the
determinant is equal to zero, can not be inverted.
Returns 1 if matrix is degenerate (singular), or 0 otherwise.
Does not throw exceptions.
*/
int fz_try_invert_matrix(fz_matrix *inverse, const fz_matrix *matrix);
```

## 10.9 路径

使用fz_path结构表示PDF（或等价的Postscript）样式路径。 PDF路径由一系列指令组成，这些指令描述“笔”在给定路径周围的运动。
第一条指令始终是“移至”指定位置。
随后的指令可以通过直线或给定控制点描述的曲线将笔的位置向前移动到页面上的新位置。可以用笔向上或向下进行指示。
一旦创建了路径，MuPDF便可以通过填充或对其进行描边来对其进行渲染。路径本身不知道如何使用-填充或笔触属性的详细信息从外部提供给此结构。
用于填充和抚摸的确切规则的描述超出了本文档的范围。有关更多信息，请参见“ PDF参考手册”或“ Postscript语言参考手册”。
有关路径的进一步讨论被推迟到第23章“路径内部”中的第2部分。
虽然完全允许应用程序调用者创建自己的路径（用于传递给设备功能），但对于调用者而言，将它们简单地视为要传递的“黑匣子”更为典型。

## 10.10 文本

MuPDF的中心文本类型是fz_text结构。过去，这种结构的确切定义已经发生了很大的变化，以适应不同输入格式的需求，并且有可能在未来继续这种趋势。因此，我们将实现隐藏在接口后面。尽管如此，值得一提的是一些影响该代码领域开发的设计目标。由于fz_text对象是唯一通过设备接口传递的文本对象，因此它们需要对多层信息进行编码。对于简单的渲染设备，它们需要具有足够的表现力，以允许我们精确渲染确切指定的字形。对于文本输出设备，它们需要具有足够的表现力以允许提取unicode值。理想情况下，给定任何输入格式，我们希望能够从中提取任何输出格式（包括相同格式）而不会丢失数据。这意味着即使我们当前不使用所有信息，我们的fz_text对象也必须具有足够的表现力以表示所有输入格式的功能超集。尽管单个表示足以依次封装页面上文本中每个字形的想法很吸引人，但事实并非如此。实际上，甚至无法简单地定义字形的发送顺序！最好考虑将文本按照应在页面上显示的顺序保存在源文件中，但这通常不是这种情况。文本的“逻辑顺序”可以被认为是大声朗读文本的顺序（如果您正在从页面上阅读）。在许多情况下（例如ePub文件），这是信息在文件本身中存储的顺序。可悲的是，对于其他格式，情况并非总是如此。尤其是PDF文件没有发送文本的特定定义顺序-由于每个字形分别位于页面上，因此文件可以（并且确实）以它们喜欢的任何顺序发送它们。尽管大多数包含欧洲语言的PDF文件都倾向于按预期的逻辑顺序发送文本，但不能保证总是如此。随着我们开始处理从右到左的文本，从上到下的文本，远东的脚本或多种不同的脚本或语言的文本，这种可能性变得更加遥远。经典案例的逻辑顺序可能与“双向”文本的呈现顺序明显不同。即使内部文档的表示顺序是逻辑顺序，实际显示文本的顺序也可能完全不同。例如，考虑希伯来语中的一些源文本。
如果单个字形是A，B，C，D等，则希伯来语文本的从右到左性质意味着它们将在页面上以“ DCBA”顺序显示。但是，如果我们在页面上有常规的西方（阿拉伯）数字，并穿插在希伯来文字中，则仍然是从左到右书写的。因此，A，B，C，D，1、2、3、4，E，F，G将显示为“ GFE01234DCBA”。处理此类字符串的算法相当复杂，因此最好将感兴趣的读者对此的进一步讨论重定向至技术报告9中定义的“ Unicode双方向算法”，网址为http://unicode.org/reports/tr9 。最终的复杂性来自需要“整形”的脚本。尽管简单的西方文字（广泛使用）在发送的字符（例如字母“ A”）和用于在页面上表示该字符的形状（例如字形“ A”）之间具有直接关系，但并非所有的脚本都适用。最简单的例子是连字。一段源文本可能包含字母“ f”，后跟字母“ i”（也许在“文件”一词中）。当在页面上排版时，通常不使用单个字形，而是使用组合字形“ fi”。当处理非西方脚本时，将它们作为从输入文本到输出呈现形式的“转换”步骤的概念得到了极大的扩展。特别是对于阿拉伯语和印度语文字（和一般的东方文字），字符组经常组合在一起以提供越来越复杂的字形。此过程称为整形，通常在运行双向算法后应用。不同的源格式以不同的方式处理此问题。 PDF文件中的文本字符串已经应用了布局和整形过程-实际上，它们是要在页面上显示的定位字形的列表。原始文本的unicode值经常根本不存在（当它们存在时，它们需要特定的工作才能得出）。 其他格式（例如EPUB）采用相反的方法，即直接指定Unicode值，然后离开显示应用程序（即MuPDF）转换为字形。 为了应付这些不同的输入要求，并允许我们将一种格式转换为另一种格式，我们需要fz_text对象同时封装两种形式的数据。 因此，我们的fz_text对象代表一个文本块，包括字体样式和位置，以及unicode和字形数据（取决于原始文件中信息的可用性）。 尽管无法保证，但我们尽可能地尝试以逻辑顺序提供该数据。 如果需要更多信息，则当前实现的详细信息包含在第2部分的第24章“文本内部”中，否则可以将其用作简单的黑匣子。 

## 10.11 底纹

PDF和其他输入格式中最强大的图形效果之一就是“阴影”。 fz_shade是表示阴影的中央类型，我们只需在fz_device接口上传递阴影细节即可。
因此，我们需要fz_shade具有足够的表现力，以应付来自所有可能来源的阴影，但是我们希望避免必须在所有设备中重现阴影处理代码。
因此，fz_shade被定义为具有足够的表现力，以封装PDF中发现的所有不同的阴影表示，而数据基本上保持不变。 当前，PDF是其他格式的阴影的超集。
如果这种情况发生变化，则fz阴影将根据需要扩展。
有关进一步的讨论，请参见[第2部分中的第25章“内部着色”](#25内部阴影)，因为对于应用程序作者来说，要编写自己的着色是不可用的（虽然不是不可想象的）。

# 11.显示列表

## 11.1 概述

尽管MuPDF被设计为尽可能快地解释页面内容，但从文档本机格式转换为图形操作流（通过fz_device接口调用）时不可避免地会产生一些开销。
如果您打算多次重画同一页面（可能是因为您正在查看器中平移和缩放页面），那么使用显示列表可能会更有利。
显示列表只是打包图形操作流的一种方法，以便可以有效地回放它们，可能使用不同的变换或剪辑矩形。
优化显示列表以使用尽可能少的内存，但显然（通常）与重新解释文件相比，内存用户更多。除了速度之外，显示列表的最大优点是可以安全地播放它们而无需触及基础文件。这意味着它们可以被使用
在其他线程中，而不必担心争用。
使用fz_display列表类型在MuPDF中实现显示列表。

## 11.2 创建

可以通过fz_ new_display列表调用创建空的显示列表。
```c++
/*
fz_new_display_list: Create an empty display list.
A display list contains drawing commands (text, images, etc.).
Use fz_new_list_device for populating the list.
mediabox: Bounds of the page (in points) represented by the display
list.
*/
fz_display_list *fz_new_display_list(fz_context *ctx, const fz_rect
*mediabox);
```

创建之后，可以通过创建写入它的显示列表设备实例来填充它。
```c++
/*
fz_new_list_device: Create a rendering device for a display list.
When the device is rendering a page it will populate the
display list with drawing commands (text, images, etc.). The
display list can later be reused to render a page many times
without having to re-interpret the page from the document file
for each rendering. Once the device is no longer needed, free
it with fz_drop_device.
list: A display list that the list device takes ownership of.
*/
fz_device *fz_new_list_device(fz_context *ctx, fz_display_list *list);
```
创建了这样的显示列表设备后，对该设备的任何调用（例如通过调用fz_run_page或类似调用）都将记录到显示列表中。
完成向显示屏的写入后列表（记住调用fz_close_device），则按正常方式处置设备（通过调用fz_drop_device）。
这样就使您拥有对显示列表本身的唯一引用。
写入显示列表不是线程安全的。 也就是说，请勿尝试一次从多个线程写入显示列表。 同样，在进行写操作时也不要尝试从显示列表中读取。

## 11.3 运行

要从列表中播放，只需调用fz_run_display_list。
```c++
/*
fz_run_display_list: (Re)-run a display list through a device.
list: A display list, created by fz_new_display_list and populated with objects from a page by running fz_run_page on a device obtained from fz_new_list_device.
dev: Device obtained from fz_new_*_device.
ctm: Transform to apply to display list contents. May include
for example scaling and rotation, see fz_scale, fz_rotate and
fz_concat. Set to fz_identity if no transformation is desired.
area: Only the part of the contents of the display list
visible within this area will be considered when the list is
run through the device. This does not imply for tile objects
contained in the display list.
cookie: Communication mechanism between caller and library
running the page. Intended for multi-threaded applications,
while single-threaded applications set cookie to NULL. The
caller may abort an ongoing page run. Cookie also communicates
progress information back to the caller. The fields inside
cookie are continually updated while the page is being run.
*/
void fz_run_display_list(fz_context *ctx, fz_display_list *list,
fz_device *dev, const fz_matrix *ctm, const fz_rect *area,
fz_cookie *cookie);
```
## 11.4 参考计数

与MuPDF中的大多数其他对象一样，fz显示列表是引用计数。 这意味着，一旦您完成了对显示列表的引用，就可以通过调用fz_drop显示列表来安全地丢弃它。
```c++
/*
fz_drop_display_list: Drop a reference to a display list, freeing it
if the reference count reaches zero.
Does not throw exceptions.
*/
void fz_drop_display_list(fz_context *ctx, fz_display_list *list);
```
如果您希望保留对显示列表的新引用，则可以生成一个
使用fz_keep_display_list。
```c++
/*
fz_keep_display_list: Keep a reference to a display list.
Does not throw exceptions.
*/
CHAPTER 11. DISPLAY LISTS
72
fz_display_list *fz_keep_display_list(fz_context *ctx, fz_display_list *list);
```
通常，很少要对显示列表进行新引用，直到对一个显示列表的写操作完成为止。 避免这种情况的好形式。

## 11.5 其他操作

还有一些其他操作可以在显示列表上有效执行。 首先，可以请求列表的边界。
```c++
/*
fz_bound_display_list: Return the bounding box of the page recorded
in a display list.
*/
fz_rect *fz_bound_display_list(fz_context *ctx, fz_display_list *list,
fz_rect *bounds);
```
请注意，这是写入显示列表的页面的边界框，而不是列表内容的边界框。 由于页面边框等原因，后者通常（但并非总是）比前者小。
其次，可以从显示列表创建新的fz_image。 这很有用
用于创建可扩展内容以嵌入其他文档类型； 例如，MuPDF利用此功能将EPUB文件中嵌入的SVG文件（用于插图和封面等）转换为方便的对象，以添加到文本流中。
```c++
/*
Create a new image from a display list.
w, h: The conceptual width/height of the image.
transform: The matrix that needs to be applied to the given
list to make it render to the unit square.
list: The display list.
*/
fz_image *fz_new_image_from_display_list(fz_context *ctx, float w, float
h, fz_display_list *list);
```
最后，可以非常迅速地检查给定的显示列表是否为空。
```c++
/*
Check for a display list being empty
list: The list to check.
Returns true if empty, false otherwise.
*/
int fz_display_list_is_empty(fz_context *ctx, const fz_display_list
*list);
```
# 12.流接口

## 12.1 概述

MuPDF旨在在各种不同的环境中运行。
因此，这意味着输入可以来自许多不同的来源。 在台式计算机上，输入可能作为后备存储上的文件输入。 对于网络提供的文件，输入可以通过网络流式传输。 对于嵌入式DRM的系统，可能需要即时解码数据。
类似地，数据可以通过多层编码以不同的方式封装在不同的格式中。
因此，MuPDF将“输入流”的概念抽象为可重用的类fz_stream。 默认情况下，核心库中提供了fz_streams的许多实现，但是此类的抽象性质允许调用者提供自己的实现，以根据需要无缝扩展系统功能。

## 12.2 创建

创建流的确切机制取决于该流的来源
特定的流，但是通常它将涉及对创建函数的调用，例如
作为fz_open_file.
```c++
/*
fz_open_file: Open the named file and wrap it in a stream.
filename: Path to a file. On non-Windows machines the filename should
be exactly as it would be passed to fopen(2). On Windows machines,
the path should be UTF-8 encoded so that non-ASCII characters can be
represented. Other platforms do the encoding as standard anyway (and
in most cases, particularly for MacOS and Linux, the encoding they
use is UTF-8 anyway).
*/
fz_stream *fz_open_file(fz_context *ctx, const char *filename);
```
存在可供选择的功能，以允许从C级FILE指针创建流：
```c++
/*
fz_open_file: Wrap an open file descriptor in a stream.
file: An open file descriptor supporting bidirectional
seeking. The stream will take ownership of the file
descriptor, so it may not be modified or closed after the call
to fz_open_file_ptr. When the stream is closed it will also close
the file descriptor.
*/
fz_stream *fz_open_file_ptr(fz_context *ctx, FILE *file);
```
直接从内存区创建：
```c++
/*
fz_open_memory: Open a block of memory as a stream.
data: Pointer to start of data block. Ownership of the data block is
NOT passed in.
len: Number of bytes in data block.
Returns pointer to newly created stream. May throw exceptions on
failure to allocate.
*/
fz_stream *fz_open_memory(fz_context *ctx, unsigned char *data, size_t
len);
```
从fz_buffers:
```c++
/*
fz_open_buffer: Open a buffer as a stream.
buf: The buffer to open. Ownership of the buffer is NOT passed in
(this function takes its own reference).
Returns pointer to newly created stream. May throw exceptions on
failure to allocate.
*/
fz_stream *fz_open_buffer(fz_context *ctx, fz_buffer *buf);
```
创建流的其他选项太多，无法在此处列出所有流，但是从头文件定义中可以明显看出它们的使用。 创建后，所有流都可以相同的方式使用。

## 12.3 使用

### 12.3.1 发送字节

从流中读取字节的最简单方法是调用fz_read_byte以从文件中读取下一个字节。 类似于标准fgetc，这将返回-1表示数据结束或下一个可用字节。
```c++
/*
fz_read_byte: Read the next byte from a stream.
stm: The stream t read from.
Returns -1 for end of stream, or the next byte. May
throw exceptions.
*/
int fz_read_byte(fz_context *ctx, fz_stream *stm);
```
要一次读取一个以上的字节，有两种不同的选择。
首先，也是最有效的，字节可以直接从底层缓冲区的流中读取。 对于给定的fz_stream *stm，流中的当前位置由stm->rp指向。 字节可以简单地读出，并且指针增加读取的数字。
为此，您必须首先知道有多少字节可读取。 这可以通过调用fz_available来实现。 如果没有字节已解码并等待读取，则此调用将触发对底层缓冲区的重新填充，这可能需要花费大量时间。
```c++
/*
fz_available: Ask how many bytes are available immediately from
a given stream.
stm: The stream to read from.
max: A hint for the underlying stream; the maximum number of
bytes that we are sure we will want to read. If you do not know
this number, give 1.
Returns the number of bytes immediately available between the
read and write pointers. This number is guaranteed only to be 0
if we have hit EOF. The number of bytes returned here need have
no relation to max (could be larger, could be smaller).
*/
size_t fz_available(fz_context *ctx, fz_stream *stm, size_t max);
```
为了避免不必要的工作，可以提供“ max”值作为提示，告诉触发的任何缓冲区重新填充操作实际需要多少字节。
指定最大值不能保证您实际可用的字节数。
一些调用者可能会觉得很尴尬-需要反复调用直到您获得足够的字节来填充所需长度的缓冲区，这可能很乏味。
因此，作为替代，我们提供了一个更简单的调用fz_read。
设计为与标准fread调用类似，它尝试将尽可能多的字节读取到提供的数据块中，并返回成功读取的实际字节数。
```c++
/*
fz_read: Read from a stream into a given data block.
stm: The stream to read from.
data: The data block to read into.
len: The length of the data block (in bytes).
Returns the number of bytes read. May throw exceptions.
*/
size_t fz_read(fz_context *ctx, fz_stream *stm, unsigned char *data,
size_t len);
```
通常，fz_read将不返回请求的字节数的唯一原因是如果我们点击了流的末尾。 这意味着对fz_read的调用将阻塞，直到准备好此类数据为止。 对于基于“快速”源（例如文件或内存）的流，这是不重要的区别。
对于基于（例如）http下载的流，这可能会导致严重的延迟和不可接受的用户体验。 为了缓解这个问题，我们提供了一种机制，通过这种机制，此类流可以通过
引发FZ_ERROR_TRYLATER错误。 有关更多详细信息，请参见[第16章](#16渐进模式)。
为了便于读取而不会阻塞（或使用大于要求的缓冲区），可以调用fz_available来找出可以安全请求的字节数。
如果不需要流中的数据，则可以使用fz_skip跳过该数据：
```c++
/*
fz_skip: Read from a stream discarding data.
stm: The stream to read from.
len: The number of bytes to read.
Returns the number of bytes read. May throw exceptions.
*/
size_t fz_skip(fz_context *ctx, fz_stream *stm, size_t len);
```
作为一种特殊情况，读取单个字节后，可以使用fz_unread_byte将其推回到流中：
```c++
/*
fz_unread_byte: Unread the single last byte successfully
read from a stream. Do not call this without having
successfully read a byte.
*/
void fz_unread_byte(fz_context *ctx FZ_UNUSED, fz_stream *stm);
```
读取一个字节，然后，如果再次成功将其推回的操作，则封装在一个便捷函数中，即fz_peek_byte：
```c++
/*
fz_peek_byte: Peek at the next byte in a stream.
stm: The stream to peek at.
Returns -1 for EOF, or the next byte that will be read.
*/
int fz_peek_byte(fz_context *ctx, fz_stream *stm);
```

### 12.3.2 读取对象

通常，在解析不同的文档格式时，从流中读取特定对象可能很有用，因此也存在便利功能。 首先，满足不同大小和字节序的整数：
```c++
/*
fz_read_[u]int(16|24|32|64)(_le)?
Read a 16/32/64 bit signed/unsigned integer from stream,
in big or little-endian byte orders.
Throws an exception if EOF is encountered.
*/
uint16_t fz_read_uint16(fz_context *ctx, fz_stream *stm);
uint32_t fz_read_uint24(fz_context *ctx, fz_stream *stm);
uint32_t fz_read_uint32(fz_context *ctx, fz_stream *stm);
CHAPTER 12. THE STREAM INTERFACE
79
uint64_t fz_read_uint64(fz_context *ctx, fz_stream *stm);
uint16_t fz_read_uint16_le(fz_context *ctx, fz_stream *stm);
uint32_t fz_read_uint24_le(fz_context *ctx, fz_stream *stm);
uint32_t fz_read_uint32_le(fz_context *ctx, fz_stream *stm);
uint64_t fz_read_uint64_le(fz_context *ctx, fz_stream *stm);
int16_t fz_read_int16(fz_context *ctx, fz_stream *stm);
int32_t fz_read_int32(fz_context *ctx, fz_stream *stm);
int64_t fz_read_int64(fz_context *ctx, fz_stream *stm);
int16_t fz_read_int16_le(fz_context *ctx, fz_stream *stm);
int32_t fz_read_int32_le(fz_context *ctx, fz_stream *stm);
int64_t fz_read_int64_le(fz_context *ctx, fz_stream *stm);
```
我们具有读取C样式字符串和换行符/返回终止行的功能：
```c++
/*
fz_read_string: Read a null terminated string from the stream into
a buffer of a given length. The buffer will be null terminated.
Throws on failure (including the failure to fit the entire string
including the terminator into the buffer).
*/
void fz_read_string(fz_context *ctx, fz_stream *stm, char *buffer, int
len);
/*
fz_read_line: Read a line from stream into the buffer until either a
terminating newline or EOF, which it replaces with a null byte
(’\0’).
Returns buf on success, and NULL when end of file occurs while no
characters
have been read.
*/
char *fz_read_line(fz_context *ctx, fz_stream *stm, char *buf, size_t
max);
```
### 12.3.3 读取比特

流（或流的部分）可以被视为一串位，首先打包最高有效位或最低有效位。
要从msb打包流中读取，请使用fz_read_bits：
```c++
/*
fz_read_bits: Read the next n bits from a stream (assumed to
be packed most significant bit first).
stm: The stream to read from.
n: The number of bits to read, between 1 and 8*sizeof(int)
inclusive.
Returns (unsigned int)-1 for EOF, or the required number of bits.
*/
unsigned int fz_read_bits(fz_context *ctx, fz_stream *stm, int n);
```
相反，要读取lsb打包流，请使用fz_read_rbits：
```c++
/*
fz_read_rbits: Read the next n bits from a stream (assumed to
be packed least significant bit first).
stm: The stream to read from.
n: The number of bits to read, between 1 and 8*sizeof(int)
inclusive.
Returns (unsigned int)-1 for EOF, or the required number of bits.
*/
unsigned int fz_read_rbits(fz_context *ctx, fz_stream *stm, int n);
```
无论使用哪种方式，读取n位都将以返回值的最低n位返回结果。
使用这些功能读取位后，如果需要返回按字节（或按对象）读取，则必须调用fz_sync_bits。
```c++
/*
fz_sync_bits: Called after reading bits to tell the stream
that we are about to return to reading bytewise. Resyncs
the stream to whole byte boundaries.
*/
void fz_sync_bits(fz_context *ctx FZ_UNUSED, fz_stream *stm);
```
此功能跳过与字节边界对齐所需的任意多的位。

### 12.3.4 读取整个流信息

作为便利功能，MuPDF提供了一种读取整个文档的机制。
流的内容放入fz_buffer。
```c++
/*
fz_read_all: Read all of a stream into a buffer.
stm: The stream to read from
initial: Suggested initial size for the buffer.
Returns a buffer created from reading from the stream. May throw
exceptions on failure to allocate.
*/
fz_buffer *fz_read_all(fz_context *ctx, fz_stream *stm, size_t initial);
```
如果在流的解码过程中遇到错误，这将引发错误（并因此不返回任何数据）。 有时候，“尽我们所能”并容忍有问题的数据可能会更可取。
对于这种情况，我们提供fz_read_best：
```c++
/*
fz_read_best: Attempt to read a stream into a buffer. If truncated
is NULL behaves as fz_read_all, otherwise does not throw exceptions
in the case of failure, but instead sets a truncated flag.
stm: The stream to read from.
initial: Suggested initial size for the buffer.
truncated: Flag to store success/failure indication in.
Returns a buffer created from reading from the stream.
*/
fz_buffer *fz_read_best(fz_context *ctx, fz_stream *stm, size_t initial,
int *truncated);
```

### 12.3.5 搜索

在读取流时，大多数流操作只是简单地使流指针前进。
始终可以使用fz_tell获取当前流的位置（故意
类似于标准的ftell调用）：
```c++
/*
fz_tell: return the current reading position within a stream
*/
int64_t fz_tell(fz_context *ctx, fz_stream *stm);
```
一些流允许您在其中搜索，即，将当前流指针更改为给定的偏移量。 为此，请使用fz_seek（故意类似于fseek）：
```c++
/*
fz_seek: Seek within a stream.
stm: The stream to seek within.
offset: The offset to seek to.
whence: From where the offset is measured (see fseek).
*/
void fz_seek(fz_context *ctx, fz_stream *stm, int64_t offset, int
whence);
```
如果流不支持搜索，则会引发错误。
由于fz_seek和fz_tell以字节粒度工作，因此按位读取流时应格外小心。 在期望fz_tell为您提供可以安全地fz_seek返回的值之前，请始终先使用fz_sync位。

### 12.3.6 元数据

有时，询问流的属性（例如，流的长度或它是否来自渐进源）可能很有用（请参见[第16章“渐进模式”](#16渐进模式)）。
尽管目前尚未实现，但也许将来某个特定的流用户可能想询问有关流的Mimetype或其压缩率的信息。
为此，我们有一个可扩展的系统来请求流上的元操作。 fz_stream_meta允许进行这样的调用，并使用键来标识所需的操作，并使用指针和大小参数来标识要传递的数据：
```c++
/*
fz_stream_meta: Perform a meta call on a stream (typically to
request meta information about a stream).
stm: The stream to query.
key: The meta request identifier.
size: Meta request specific parameter - typically the size of
the data block pointed to by ptr.
ptr: Meta request specific parameter - typically a pointer to
a block of data to be filled in.
Returns -1 if this stream does not support this meta operation,
or a meta operation specific return value.
*/
int fz_stream_meta(fz_context *ctx, fz_stream *stm, int key, int size,
void *ptr);
```
### 12.3.7 销毁

与大多数其他MuPDF对象一样，fz_streams被引用计数。
这样，可以使用fz_keep_stream获取其他引用，并可以使用fz_drop_stream销毁它们。
注意，必须注意不要在多个线程中同时使用fz_stream对象。 在一个线程中进行读取的行为不仅会使在另一个线程中进行下一次读取的点变得混乱，而且没有提供保护以使操作原子化，因此内部数据可能会变为损坏并导致崩溃。

# 13.输出接口

## 13.1 概述

与fz_streams抽象输入流的方式相同，MuPDF使用可重用的类fz_output抽象输出流。

## 13.2 创建

调用以创建输出流的确切函数取决于所需的特定流，但是它们通常遵循类似的格式。
一些常见的示例是：
```c++
/*
fz_new_output_with_file: Open an output stream that writes to a
FILE *.
file: The file to write to.
close: non-zero if we should close the file when the fz_output
is closed.
*/
fz_output *fz_new_output_with_file_ptr(fz_context *ctx, FILE *file, int
close);
/*
fz_new_output_with_path: Open an output stream that writes to a
given path.
filename: The filename to write to (specified in UTF-8).
append: non-zero if we should append to the file, rather than
overwriting it.
*/
fz_output *fz_new_output_with_path(fz_context *, const char *filename,
int append);
/*
fz_new_output_with_buffer: Open an output stream that appends
to a buffer.
buf: The buffer to append to.
*/
fz_output *fz_new_output_with_buffer(fz_context *ctx, fz_buffer *buf);
```
最常见的用例之一是获取输出流到stdout或stderr，我们为此提供了便利功能。 此外，我们还允许将stdout和stderr的流替换为其他fz输出，从而可以简单地为任何现有工具更改重定向：
```c++
/*
fz_stdout: The standard out output stream. By default
this stream writes to stdout. This may be overridden
using fz_set_stdout.
*/
fz_output *fz_stdout(fz_context *ctx);
/*
fz_stderr: The standard error output stream. By default
this stream writes to stderr. This may be overridden
using fz_set_stderr.
*/
fz_output *fz_stderr(fz_context *ctx);
/*
fz_set_stdout: Replace default standard output stream
with a given stream.
out: The new stream to use.
*/
void fz_set_stdout(fz_context *ctx, fz_output *out);
/*
fz_set_stderr: Replace default standard error stream
with a given stream.
err: The new stream to use.
*/
void fz_set_stderr(fz_context *ctx, fz_output *err);
```
## 13.3 使用

### 13.3.1 写字节

可以使用fz_write_byte将单个字节写入fz_output流：
```c++
/*
fz_write_byte: Write a single byte.
out: stream to write to.
x: value to write
*/
void fz_write_byte(fz_context *ctx, fz_output *out, unsigned char x);
```
可以使用fz_write将字节块写入fz_output流：
```c++
/*
fz_write: Write data to output. Designed to parallel
fwrite.
out: Output stream to write to.
data: Pointer to data to write.
size: Length of data to write.
*/
void fz_write(fz_context *ctx, fz_output *out, const void *data, size_t
size);
```
### 13.3.2 写对象

我们具有方便的功能，可以输出大小端格式的16位和32位整数：
```c++
/*
fz_write_int32_be: Write a big-endian 32-bit binary integer.
*/
void fz_write_int32_be(fz_context *ctx, fz_output *out, int x);
/*
fz_write_int32_le: Write a little-endian 32-bit binary integer.
*/
void fz_write_int32_le(fz_context *ctx, fz_output *out, int x);
/*
fz_write_int16_be: Write a big-endian 16-bit binary integer.
*/
void fz_write_int16_be(fz_context *ctx, fz_output *out, int x);
/*
fz_write_int16_le: Write a little-endian 16-bit binary integer.
*/
void fz_write_int16_le(fz_context *ctx, fz_output *out, int x);
```
还有一个用于输出utf-8编码的unicode字符的函数：
```c++
/*
fz_write_rune: Write a UTF-8 encoded unicode character.
*/
void fz_write_rune(fz_context *ctx, fz_output *out, int rune);
```
### 13.3.3 写字符串

要输出可打印的字符串，我们有简单的fputc，fputs和fputrune等效项：
```c++
/*
fz_putc: fputc equivalent for output streams.
*/
#define fz_putc(C,O,B) fz_write_byte(C, O, B)
/*
fz_puts: fputs equivalent for output streams.
*/
#define fz_puts(C,O,S) fz_write(C, O, (S), strlen(S))
/*
fz_putrune: fputrune equivalent for output streams.
*/
#define fz_putrune(C,O,R) fz_write_rune(C, O, R)
```
我们还提供了一系列增强的输出功能，以fprintf为模式：
```c++
/*
fz_vsnprintf: Our customised vsnprintf routine.
Takes %c, %d, %o, %s, %u, %x, as usual.
Modifiers are not supported except for zero-padding
ints (e.g. %02d, %03o, %04x, etc).
%f and %g both output in " as short as possible hopefully lossless
non-exponent"
form, see fz_ftoa for specifics.
%C outputs a utf8 encoded int.
%M outputs a fz_matrix*.
%R outputs a fz_rect*.
%P outputs a fz_point*.
%q and %( output escaped strings in C/PDF syntax.
%ll{d,u,x} indicates that the values are 64bit.
%z{d,u,x} indicates that the value is a size_t.
*/
size_t fz_vsnprintf(char *buffer, size_t space, const char *fmt, va_list
args);
/*
fz_snprintf: The non va_list equivalent of fz_vsnprintf.
*/
size_t fz_snprintf(char *buffer, size_t space, const char *fmt, ...);
/*
fz_printf: fprintf equivalent for output streams. See fz_snprintf.
*/
void fz_printf(fz_context *ctx, fz_output *out, const char *fmt, ...);
/*
fz_vprintf: vfprintf equivalent for output streams. See fz_vsnprintf.
*/
void fz_vprintf(fz_context *ctx, fz_output *out, const char *fmt,
va_list ap);
```
### 13.3.4 搜索

与fz_streams一样，fz_outputs通常会线性移动，但在特殊情况下，也是可以找到的。
```c++
/*
fz_seek_output: Seek to the specified file position. See fseek
for arguments.
Throw an error on unseekable outputs.
*/
void fz_seek_output(fz_context *ctx, fz_output *out, fz_off_t off, int
whence);
```
与在所有情况下都支持fz_tell的fz_streams不同，fz_outputs仅可搜索fz_tell输出：
```c++
/*
fz_tell_output: Return the current file position. Throw an error
on unseekable outputs.
*/
fz_off_t fz_tell_output(fz_context *ctx, fz_output *out);
```
# 14.渲染输出格式

## 14.1 概述

内置在渲染器中的MuPDF（请参阅第9.5.2节“绘图设备”）会为文档页面区域生成连续色调值的内存数组。 MuPDF库包含一些例程，可以将这些区域输出为多种不同的输出格式。
通常，这些设备都遵循相似的模式，从而可以根据特定应用程序的要求执行整页或带状渲染。
对于给定的格式XXX，通常定义了3个功能：
```c++
void fz_save_pixmap_as_XXX(fz_context *ctx, fz_pixmap *pixmap, char
*filename);
void fz_write_pixmap_as_XXX(fz_context *ctx, fz_output *out, fz_pixmap
*pixmap);
fz_band_writer *fz_new_XXX_band_writer(fz_context *ctx, fz_output *out);
```
第一个功能将像素映射输出为utf-8编码的文件名，作为XXX格式的文件。 如果像素图没有处于合适的色彩空间/ alpha配置中，则将引发异常。
第二个函数执行相同的操作，但是对给定的fz_output而不是命名文件。 fz_output的使用允许写入内存缓冲区，甚至可能随着写入的进行进一步加密或压缩。
第三个函数返回fz_band_writer做同样的事情。

## 14.2 带区写入

fz_band编写器机制的目的是允许带区渲染； 不必分配足够大的像素图来一次容纳整个页面，我们而是在页面上渲染带区并将其馈送到fz_band_writer，后者将它们组合成正确形成的XXX格式输出流。
通常，使用诸如fz_new_png_band_writer之类的调用来创建带区写入：
```c++
/*
fz_new_png_band_writer: Obtain a fz_band_writer instance
for producing PNG output.
*/
fz_band_writer *fz_new_png_band_writer(fz_context *ctx, fz_output *out);
```
页面输出通过调用fz_write_header开始。 这既为要发送的数据类型配置了波段编写器，又触发了文件头的输出：
```c++
/*
fz_write_header: Cause a band writer to write the header for
a banded image with the given properties/dimensions etc. This
also configures the bandwriter for the format of the data to be
passed in future calls.
w, h: Width and Height of the entire page.
n: Number of components (including spots and alphas).
alpha: Number of alpha components.
xres, yres: X and Y resolutions in dpi.
pagenum: Page number
cs: Colorspace (NULL for bitmaps)
seps: Separation details (or NULL).
Throws exception if incompatible data format.
*/
void fz_write_header(fz_context *ctx, fz_band_writer *writer, int w, int
h, int n, int alpha, int xres, int yres, int pagenum, const
fz_colorspace *cs, fz_separations *seps);
```
这样可以为整个图像设置数据的大小和格式。 然后，调用者继续从顶部到底部以水平条呈现页面，并将其传递到fz_write带：
```c++
/*
fz_write_band: Cause a band writer to write the next band
of data for an image.
stride: The byte offset from the first byte of the data
for a pixel to the first byte of the data for the same pixel
on the row below.
band_height: The number of lines in this band.
samples: Pointer to first byte of the data.
*/
void fz_write_band(fz_context *ctx, fz_band_writer *writer, int stride,
int band_height, const unsigned char *samples);
```
带区写入跟踪已写入的数据量，并在发送整个页面后写出所需的任何图像预告片。
对于可容纳多个页面的格式，对fz_write标头的新调用将再次启动该过程。
否则（或在最终图像之后），可以通过调用以下命令整齐地丢弃带区写入：
```c++
void fz_drop_band_writer(fz_context *ctx, fz_band_writer *writer);
```
## 14.3 PNM

支持的最简单的输出格式是PNM。 像素图可以是带有或不带有alpha的灰度或RGB（尽管在书写时始终会忽略alpha平面）。
```c++
/*
fz_save_pixmap_as_pnm: Save a pixmap as a PNM image file.
*/
void fz_save_pixmap_as_pnm(fz_context *ctx, fz_pixmap *pixmap, char
*filename);
void fz_write_pixmap_as_pnm(fz_context *ctx, fz_output *out, fz_pixmap
*pixmap);
fz_band_writer *fz_new_pnm_band_writer(fz_context *ctx, fz_output *out);
```

## 14.4 PAM

与PNM相关，我们有PAM。 此处的像素图格式可以是灰度，RGB或CMYK，带有或不带有Alpha（并且Alpha平面已写入文件）。 图像标题中的TUPLTYPE反映了颜色和alpha配置，尽管并非所有阅读器都支持所有变体。
```c++
/*
fz_save_pixmap_as_pam: Save a pixmap as a PAM image file.
*/
void fz_save_pixmap_as_pam(fz_context *ctx, fz_pixmap *pixmap, char
*filename);
void fz_write_pixmap_as_pam(fz_context *ctx, fz_output *out, fz_pixmap
*pixmap);
fz_band_writer *fz_new_pam_band_writer(fz_context *ctx, fz_output *out);
```

## 14.5 PBM

通过绘制为灰度连续色调（无Alpha），然后将其半色调为单色来生成适合于输出为PBM格式的位图。
```c++
/*
fz_save_bitmap_as_pbm: Save a bitmap as a PBM image file.
*/
void fz_save_bitmap_as_pbm(fz_context *ctx, fz_bitmap *bitmap, char
*filename);
void fz_write_bitmap_as_pbm(fz_context *ctx, fz_output *out, fz_bitmap
*bitmap);
fz_band_writer *fz_new_pbm_band_writer(fz_context *ctx, fz_output *out);
```
## 14.6 PKM

通过绘制为CMYK连续色调（无Alpha），然后半色调以得到1bpc cmyk来生成适合于输出为PKM格式的位图。
```c++
/*
fz_save_bitmap_as_pkm: Save a 4bpp cmyk bitmap as a PAM image file.
*/
void fz_save_bitmap_as_pkm(fz_context *ctx, fz_bitmap *bitmap, char
*filename);
void fz_write_bitmap_as_pkm(fz_context *ctx, fz_output *out, fz_bitmap
*bitmap);
fz_band_writer *fz_new_pkm_band_writer(fz_context *ctx, fz_output *out);
```
## 14.7 PNG

PNG格式将接受带有或不带有alpha的灰度或RGB像素图。 作为特殊情况，仅接受Alpha像素图并将其写入灰度。
```c++
/*
fz_save_pixmap_as_png: Save a pixmap as a PNG image file.
*/
void fz_save_pixmap_as_png(fz_context *ctx, fz_pixmap *pixmap, const
char *filename);
/*
Write a pixmap to an output stream in PNG format.
*/
void fz_write_pixmap_as_png(fz_context *ctx, fz_output *out, const
fz_pixmap *pixmap);
/*
fz_new_png_band_writer: Obtain a fz_band_writer instance
for producing PNG output.
*/
fz_band_writer *fz_new_png_band_writer(fz_context *ctx, fz_output *out);
```
由于PNG是一种有用且广泛使用的格式，因此我们还有另外两个功能。 它们采用fz_image或fz_pixmap并生成包含PNG编码版本的fz_buffer。 当在文档格式之间进行转换，因为我们经常可以使用图像的PNG版本来替换可能不支持的其他图像格式。

```c++
/*
Create a new buffer containing the image/pixmap in PNG format.
*/
fz_buffer *fz_new_buffer_from_image_as_png(fz_context *ctx, fz_image
*image);
fz_buffer *fz_new_buffer_from_pixmap_as_png(fz_context *ctx, fz_pixmap
*pixmap);
```
## 14.8 PSD

PSD格式由photoshop使用。 它特别有用，因为它可以包含大量专色及其“等效色”信息。 这意味着它是用于输出具有专色的颜色正确的渲染和正确的套印处理的选择格式。
可以使用灰度，RGB或CMYK像素图以及可选点来写入PSD文件。
没有Alpha。
```c++
/*
fz_save_pixmap_as_psd: Save a pixmap as a PSD image file.
*/
void fz_save_pixmap_as_psd(fz_context *ctx, fz_pixmap *pixmap, const
char *filename);
/*
Write a pixmap to an output stream in PSD format.
*/
void fz_write_pixmap_as_psd(fz_context *ctx, fz_output *out, const
fz_pixmap *pixmap);
/*
fz_new_psd_band_writer: Obtain a fz_band_writer instance
for producing PSD output.
*/
fz_band_writer *fz_new_psd_band_writer(fz_context *ctx, fz_output *out);
```
## 14.9 PWG/CUPS

PWG格式旨在封装打印机的输出。 因此，可以在标题中设置许多值。 为此，我们将这些字段显示为可以输入到输出函数中的选项结构。
```c++
typedef struct fz_pwg_options_s fz_pwg_options;
struct fz_pwg_options_s
{
/* These are not interpreted as CStrings by the writing code, but
* are rather copied directly out. */
char media_class[64];
char media_color[64];
char media_type[64];
char output_type[64];
unsigned int advance_distance;
int advance_media;
int collate;
int cut_media;
int duplex;
int insert_sheet;
int jog;
int leading_edge;
int manual_feed;
unsigned int media_position;
unsigned int media_weight;
int mirror_print;
int negative_print;
unsigned int num_copies;
int orientation;
int output_face_up;
unsigned int PageSize[2];
int separations;
int tray_switch;
int tumble;
int media_type_num;
int compression;
unsigned int row_count;
unsigned int row_feed;
unsigned int row_step;
/* These are not interpreted as CStrings by the writing code, but
* are rather copied directly out. */
char rendering_intent[64];
char page_size_name[64];
};
```

这里没有提供这些字段的文档-有关更多信息，请参见PWG规范。
PWG有两套输出功能，采用fz像素图（用于连续输出）和采用fz位图（用于半色调输出）的输出功能。
PWG文件的结构为标头（以标识格式），然后是页面流（图像）。 保存（或写入）完整文件的那些函数会将文件头作为其输出的一部分。 如果使用该选项附加到文件，则不会添加标题，因为我们假定将新页面信息附加到现有文件的末尾。
在没有自动输出标头的情况下（例如，使用乐队编写器时），必须通过调用以下命令手动触发标头输出：
```c++
/*
Output the file header to a pwg stream, ready for pages to follow it.
*/
void fz_write_pwg_file_header(fz_context *ctx, fz_output *out);
```

### 14.9.1 Contone
PWG编写器可以接受没有Alpha平面的灰度，RGB和CMYK格式的像素图。
可以使用以下方法将PWG文件保存到文件中：
```c++
/*
fz_save_pixmap_as_pwg: Save a pixmap as a pwg
filename: The filename to save as (including extension).
append: If non-zero, then append a new page to existing file.
pwg: NULL, or a pointer to an options structure (initialised to zero
before being filled in, for future expansion).
*/
void fz_save_pixmap_as_pwg(fz_context *ctx, fz_pixmap *pixmap, char
*filename, int append, const fz_pwg_options *pwg);
```
仅在我们不附加到现有文件的情况下才发送文件头。
或者，可以将页面发送到输出流。 有两个功能可以做到这一点。 第一个总是发送完整的PWG文件（包括标题）：
```c++
/*
Output a pixmap to an output stream as a pwg raster.
*/
void fz_write_pixmap_as_pwg(fz_context *ctx, fz_output *out, const
fz_pixmap *pixmap, const fz_pwg_options *pwg);
```
第二个页面仅发送页面数据，因此适合于发送文件中的第二个页面或后续页面。
或者，可以手动发送标头，然后此功能可用于文件中的所有页面。
```c++
/*
Output a page to a pwg stream to follow a header, or other pages.
*/
void fz_write_pixmap_as_pwg_page(fz_context *ctx, fz_output *out, const
fz_pixmap *pixmap, const fz_pwg_options *pwg);
```
最后，可以使用标准带区写入：
```c++
/*
fz_new_pwg_band_writer: Generate a new band writer for
contone PWG format images.
*/
fz_band_writer *fz_new_pwg_band_writer(fz_context *ctx, fz_output *out,
const fz_pwg_options *pwg);
```
在所有情况下，都可以为fz_pwg选项字段发送NULL值，在这种情况下，将使用默认值。
### 14.9.2 Mono

PWG编写器的单色版本与连续色调类似。它只能接受单色位图。
可以使用以下方法将PWG文件保存到文件中：
```c++
/*
fz_save_bitmap_as_pwg: Save a bitmap as a pwg
filename: The filename to save as (including extension).
append: If non-zero, then append a new page to existing file.
pwg: NULL, or a pointer to an options structure (initialised to zero
before being filled in, for future expansion).
*/
void fz_save_bitmap_as_pwg(fz_context *ctx, fz_bitmap *bitmap, char
*filename, int append, const fz_pwg_options *pwg);
```
仅在我们不附加到现有文件的情况下才发送文件头。
或者，可以将页面发送到输出流。有两个功能可以做到这一点。第一个总是发送完整的PWG文件（包括标题）：

```c++
/*
Output a bitmap to an output stream as a pwg raster.
*/
void fz_write_bitmap_as_pwg(fz_context *ctx, fz_output *out, const
fz_bitmap *bitmap, const fz_pwg_options *pwg);
```

第二个页面仅发送页面数据，因此适合于发送文件中的第二个页面或后续页面。
或者，可以手动发送标头，然后此功能可用于文件中的所有页面。
```c++
/*
Output a bitmap page to a pwg stream to follow a header, or other
pages.
*/
void fz_write_bitmap_as_pwg_page(fz_context *ctx, fz_output *out, const
fz_bitmap *bitmap, const fz_pwg_options *pwg);
```
最后，可以使用标准宽写入：

```c++
/*
fz_new_mono_pwg_band_writer: Generate a new band writer for
PWG format images.
*/
fz_band_writer *fz_new_mono_pwg_band_writer(fz_context *ctx, fz_output
*out, const fz_pwg_options *pwg);
```
在所有情况下，都可以为fz_pwg选项字段发送NULL值，在这种情况下，将使用默认值。

## 14.10 TGA

TGA编写器可以接受带有或不带有alpha的灰度，RGB和BGR格式的像素图。
```c++
/*
fz_save_pixmap_as_tga: Save a pixmap as a TGA image file.
Can accept RGB, BGR or Grayscale pixmaps, with or without
alpha.
*/
void fz_save_pixmap_as_tga(fz_context *ctx, fz_pixmap *pixmap, const
char *filename);
/*
Write a pixmap to an output stream in TGA format.
Can accept RGB, BGR or Grayscale pixmaps, with or without
alpha.
*/
void fz_write_pixmap_as_tga(fz_context *ctx, fz_output *out, fz_pixmap
*pixmap);
/*
fz_new_tga_band_writer: Generate a new band writer for TGA
format images. Note that image must be generated vertically
flipped for use with this writer!
Can accept RGB, BGR or Grayscale pixmaps, with or without
alpha.
is_bgr: True, if the image is generated in bgr format.
*/
fz_band_writer *fz_new_tga_band_writer(fz_context *ctx, fz_output *out,
int is_bgr);
```
## 14.11 PCL

### 14.11.1 Color

### 14.11.2 Mono

## 14.12 postscript

# 15.文档写接口

## 15.1 使用

除了打开现有文档外，MuPDF还包含一些功能，可轻松创建新文档。 此功能的最通用形式为fz_document_writer_interface的形式。
通过调用生成函数可以获得文档编写器。
最通用的目的是：
```c++
/*
fz_new_document_writer: Create a new fz_document_writer, for a
file of the given type.
path: The document name to write (or NULL for default)
format: Which format to write (currently cbz, pdf, pam, pbm,
pgm, pkm, png, ppm, pnm, svg, tga)
options: NULL, or pointer to comma separated string to control
file generation.
*/
fz_document_writer *fz_new_document_writer(fz_context *ctx, const char
*path, const char *format, const char *options);
```
或者，可以使用直接调用来生成特定的文档编写器，例如：
```c++
fz_document_writer * fz_new_cbz_writer（fz_context * ctx，const char * path，
const char * options）;
fz_document_writer * fz_new_pdf_writer（fz_context * ctx，const char * path，
const char * options）;
fz_document_writer * fz_new_svg_writer（fz_context * ctx，const char * path，
const char * options）;
fz_document_writer * fz_new_png_pixmap_writer（fz_context * ctx，const char
* path，const char * options）;
fz_document_writer * fz_new_tga_pixmap_writer（fz_context * ctx，const char
* path，const char * options）;
fz_document_writer * fz_new_pam_pixmap_writer（fz_context * ctx，const char
* path，const char * options）;
fz_document_writer * fz_new_pnm_pixmap_writer（fz_context * ctx，const char
* path，const char * options）;
fz_document_writer * fz_new_pgm_pixmap_writer（fz_context * ctx，const char
* path，const char * options）;
fz_document_writer * fz_new_ppm_pixmap_writer（fz_context * ctx，const char
* path，const char * options）;
fz_document_writer * fz_new_pbm_pixmap_writer（fz_context * ctx，const char
* path，const char * options）;
fz_document_writer * fz_new_pkm_pixmap_writer（fz_context * ctx，const char
* path，const char * options）;
```
一旦创建了fz_document_writer，就可以一次将页面写到文档中。 通过调用fz_begin_page开始该过程：
```c++
/*
fz_begin_page: Called to start the process of writing a page to
a document.
mediabox: page size rectangle in points.
Returns a fz_device to write page contents to.
*/
fz_device *fz_begin_page(fz_context *ctx, fz_document_writer *wri, const
fz_rect *mediabox);
```
此函数返回fz设备指针，应将其用于写入页面内容。 这可以通过执行一系列常规设备调用来完成（请参见[第9章“设备”界面](#9设备接口)），以将页面内容绘画出来。 最常见的方法之一是在另一个打开的文档上调用fz_run_page内容。 因此，这提供了一种用于将文档从一种格式转换为另一种格式的快速机制。
页面内容全部写完后，可通过调用fz_end_page来完成页面的确定：
```c++
/*
fz_end_page: Called to end the process of writing a page to a
document.
*/
void fz_end_page(fz_context *ctx, fz_document_writer *wri);
```
此时，只需重复fz_begin_page，输出，fz_end_page循环，许多格式将允许写入更多页面。
编写完所有页面后，可以通过调用fz_close_document_writer来最终完成生成的文档：
```c++
/*
fz_close_document_writer: Called to end the process of writing
pages to a document.
This writes any file level trailers required. After this
completes successfully the file is up to date and complete.
*/
void fz_close_document_writer(fz_context *ctx, fz_document_writer *wri);
```
最后，可以通过调用fz_drop_document_writer以常规方式释放文档编写器本身：
```c++
/*
fz_drop_document_writer: Called to discard a fz_document_writer.
This may be called at any time during the process to release all
the resources owned by the writer.
Calling drop without having previously called drop may leave
the file in an inconsistent state.
*/
void fz_drop_document_writer(fz_context *ctx, fz_document_writer *wri);
```
## 15.2 实现

对新型文档编写器的支持需要从fz_document_writer派生的新结构：
```c++
typedef struct
{
fz_document_writer_begin_page_fn *begin_page;
fz_document_writer_end_page_fn *end_page;
fz_document_writer_close_writer_fn *close_writer;
fz_document_writer_drop_writer_fn *drop_writer;
fz_device *dev;
} fz_document_writer;
```
举例：
```c++
typedef struct
{
fz_document_writer super;
<foo specific fields>
} foo_document_writer;
```
应该定义一个生成器函数以返回这样的实例，也许：
```c++
fz_document_writer *fz_new_foo_document_writer(fz_context *ctx, const
char *path, <foo specific params>) {
foo_document_writer *foo = fz_new_derived_document_writer(ctx,
foo_document_writer, foo_begin_page, foo_end_page, foo_close,
foo_drop);
<initialise foo specific fields>
return &foo->super;
}
```
这使用一个友好的宏，该宏分配所需大小的结构，根据需要初始化函数指针，并将该结构中的多余值归零。
```c++
/*
fz_new_document_writer_of_size: Internal function to allocate a
block for a derived document_writer structure, with the base
structure’s function pointers populated correctly, and the extra
space zero initialised.
*/
fz_document_writer *fz_new_document_writer_of_size(fz_context *ctx,
size_t size, fz_document_writer_begin_page_fn *begin_page,
fz_document_writer_end_page_fn *end_page,
fz_document_writer_close_writer_fn *close,
fz_document_writer_drop_writer_fn *drop);
#define fz_new_derived_document_writer(CTX,TYPE,BEGIN,END,CLOSE,DROP) \
((TYPE *)Memento_label(fz_new_document_writer_of_size(CTX,\
sizeof(TYPE),BEGIN,END,\
CLOSE,DROP),#TYPE))
```
文档编写器的实际工作是通过传递给fz_new_derived_document_writer的函数完成的。 在上面的示例中，它们是foo_begin_page，foo_end_page，foo_close和foo_drop。 它们分别具有以下4种类型。
```c++
/*
fz_document_writer_begin_page_fn: Function type to start
the process of writing a page to a document.
CHAPTER 15. THE DOCUMENT WRITER INTERFACE
107
mediabox: page size rectangle in points.
Returns a fz_device to write page contents to.
*/
typedef fz_device *(fz_document_writer_begin_page_fn)(fz_context *ctx,
fz_document_writer *wri, const fz_rect *mediabox);
/*
fz_document_writer_end_page_fn: Function type to end the
process of writing a page to a document.
dev: The device created by the begin_page function.
*/
typedef void (fz_document_writer_end_page_fn)(fz_context *ctx,
fz_document_writer *wri, fz_device *dev);
/*
fz_document_writer_close_writer_fn: Function type to end
the process of writing pages to a document.
This writes any file level trailers required. After this
completes successfully the file is up to date and complete.
*/
typedef void (fz_document_writer_close_writer_fn)(fz_context *ctx,
fz_document_writer *wri);
/*
fz_document_writer_drop_writer_fn: Function type to discard
an fz_document_writer. This may be called at any time during
the process to release all the resources owned by the writer.
Calling drop without having previously called close may leave
the file in an inconsistent state.
*/
typedef void (fz_document_writer_drop_writer_fn)(fz_context *ctx,
fz_document_writer *wri);
```
定义后，如果打算用作一般有用的文档编写器，则应将其挂接到fz_new_document编写器中，可以在其中通过适当的格式和选项字符串进行选择。

# 16.渐进模式

当以常规方式使用时，MuPDF需要打开整个文件，然后才能打开文件。对于某些应用程序，这可能是一个很大的限制-例如，当通过缓慢的Internet链接下载PDF文件时，仅查看第一页或第二页就足以知道它是否正确。普通的PDF文件要求在开始读取文件之前先显示文件的结尾，因为这是“预告片”所在的位置（实际上是整个文件的索引）。为了尽早显示第一页，Adobe（PDF格式的创建者）引入了“线性化” PDF文件的概念。这是一个PDF文件，尽管它是按照原始规范构造的，但文件中还包含一些其他信息，以允许快速访问首页。此信息称为“提示流”。此外，对文件中数据的顺序设置了额外的约束，以确保第一页将快速下载。不幸的是，线性化PDF文件远非万能药。该规范过于复杂，不清楚，因此该格式的阅读器和编写器都缺乏很好的支持。即使正确实现，它对于除第一个页面以外的其他页面的使用也很有限。因此，MuPDF尝试使用多种机制（称为“渐进模式”）的组合来解决问题。当以这种模式运行时，MuPDF不仅可以利用文件中的线性化信息（如果存在），还可以指导文件使用的实际下载机制。通过控制文件部分的提取顺序，可以在完成整个提取之前查看所需的任何页面。

为了获得最佳性能，文件应该既线性化，又可以在字节范围的支持链接上使用，但是仅使用其中之一仍然可以带来好处。
再加上渲染页面可以忽略（并检测）错误的功能，这意味着甚至可以在下载页面的所有内容（例如图像和字体）之前，对页面进行“粗糙渲染”。

## 16.1 概述
## 16.2 实现

### 16.2.1 渐进流
### 16.2.2 粗糙渲染

### 16.2.3 直接下载
### 16.2.4 案例实现



# 17.字体
## 17.1 概述

MuPDF中的字体由抽象的fz_font类型表示。 此引用计数结构封装了有关字体的基本信息，尤其是：  
**Glyph list** 每种字体都包含一个字形列表。  
**Glyph data** 如何绘制每个字形。 在传统字体中，此信息称为“轮廓数据”（或“轮廓”），但是某些字体类型（例如PDF中的Type 3字体）也可以封装其他数据，例如图像和颜色。  
**Unicode map** 大多数（但不是全部）字体都包含一些信息，这些信息使字形可以映射到它们表示的Unicode代码点或从它们表示的Unicode代码点映射。 没有这些信息，就不可能从文档中有意义地提取文本信息（例如用于剪切和粘贴）。  
**Font BBox** 所有字体都包含边框的信息，该边框覆盖了字体内的所有字形。 可悲的是，这经常是不正确的或不正确的，因此应不信任对待。  
**Glyph advances** 所有字体均包含简单的字形高级信息-绘制给定字形后将文本光标移动多远。 此信息可确保连续字符的正确间距为w.r.t. 彼此。  
**Kerning data** 大多数字体都包含简单的字距调整数据。 这允许根据特定字形值调整任意2个字形之间的字形前进。 字距调整的经典示例指出，A和其后一个字母的左边缘之间的间距通常在AV和AN之间不同。  
**Shape data** 某些字体允许字形序列自动“成形”。
西方字体的一个简单例子是字母“ f”和“ i”可以组合为单个连字字形“ fi”。 对于许多非拉丁文字（尤其是印度文字和东南亚文字），此过程的发生范围更大。 这可以像合并变音标记一样简单，也可以像完成字形序列的重新排列或替换以在最终呈现的页面上显示不同的外观一样复杂。
此过程称为“字体整形”，执行此过程所需的数据是特定于字体的，并且可以选择封装在字体本身中。

## 17.2 内置字体

可以配置到任何版本的MuPDF中的确切字体集（请参阅第18章构建配置）。
对于PDF，我们内置了一组由URW许可的基本字体。最初，PDF规范建议所有读者都可以使用14种标准字体，
并且任何其他字体都应嵌入文件中。此建议已更新，以建议所有字体（或至少是必需的子集）
）嵌入到所有文件中。尽管如此，找到许多要求未嵌入字体的PDF文件仍然很普遍。
为了应对尽可能广泛的脚本，MuPDF随附了（可以选择包括）Google的一系列“ Noto”字体。
如果这会在目标系统上占用太多空间，则可以选择使用提供的DroidSansFallback字体（同样来自Google），这在大小和覆盖范围之间是一个很好的折衷方案。
默认情况下，MuPDF不使用底层系统上存在的字体（例如，在Windows上，MuPDF不会在C：/ Windows /中查找字体）。
但是，如果您希望实现这种“系统字体”加载，MuPDF确实提供了挂钩。
fz_install_load_system_font_funcs调用采用了一组可用于此目的的函数指针。

## 17.3 实现

fz_fonts的实现细节仍在不断变化，因此目前不宜对其进行详细记录。

# 18.构建配置
## 18.1 概述
默认情况下，MuPDF会构建自己能够提供的几乎最强大的版本。 与任何给定应用程序相比，这可能导致更大的库大小。 在MuPDF的设计中已经采取了一些谨慎的措施，以尝试使链接器智能地删除实际上不需要的代码部分，但是仍然值得进行调整。

## 18.2 配置文档

为了简化调整构建的任务，所有配置选项都收集到一个头文件中，包括/mupdf/fitz/config.h。此文件分为两半；上半部分由一系列注释对组成，并注释了#defines，后半部分由#ifdef逻辑组成，以理解#define值集。因此，在构建时可用的配置选项由文件前半部分中的对描述。这些对中的每对都有一个注释，描述以下配置选项的功能，然后注释掉#defines，该选项将配置选项设置为其默认值。如果集成商要更改默认的构建选项，则有两个选项。首先，也是最简单的方式，他们可以通过取消注释相关行并对其进行编辑来编辑文件的前半部分，以设置所需的#define。或者，它们可以安排这些值由编译器预定义，通常是通过编辑Makefile中定义的CFLAGS。后一种方法具有117第18章。构建配置118的优点是不需要对源本身进行任何编辑，并允许从同一源树构建不同的配置。文件的后半部分永远不需要编辑。

## 18.3 绘图器选择

文件的第一部分介绍了将要内置到MuPDF中的绘图仪。
```c++
/*
Enable the following for spot (and hence overprint/overprint
simulation) capable rendering. This forces FZ_PLOTTERS_N on.
*/
#define FZ_ENABLE_SPOT_RENDERING
```
如果启用了此功能（即如果需要点渲染功能），则隐式启用以下部分中的所有绘图仪。 对于不需要现场渲染的人（即，不需要在屏幕上进行叠印模拟的任何人）可以将其关闭，并进一步配置以下选项：
```c++
/*
Choose which plotters we need.
By default we build the greyscale, RGB and CMYK plotters in,
but omit the arbitrary plotters. To avoid building
plotters in that aren’t needed, define the unwanted
FZ_PLOTTERS_... define to 0.
*/
/* #define FZ_PLOTTERS_G 1 */
/* #define FZ_PLOTTERS_RGB 1 */
/* #define FZ_PLOTTERS_CMYK 1 */
/* #define FZ_PLOTTERS_N 0 */
```
MuPDF中内置的绘图仪选择将决定哪些格式也可以渲染。 例如，如果您使用MuPDF定位仅灰度设备（例如，用于灰度电子书阅读器的电子墨水屏幕），则无需进行彩色渲染，并且可以禁用RGB和CMYK选项。
同样，如果您知道要以屏幕显示为目标，则可以省略灰度和CMYK绘图仪。
N个绘图仪是能够处理任何颜色深度的通用绘图仪，但对于特定的G，RGB或CMYK渲染，没有进行优化。
那些深度的绘图仪。 您可能会发现，通过启用N个绘图仪并禁用其他绘图仪，可以以某种速度换取空间。
如果希望渲染到G，RGB或CMYK以外的颜色空间，则必须启用N个绘图仪。
任何版本中都必须至少定义1个绘图仪系列。 如果所有绘图仪都被禁用，则将启用N个绘图仪。

## 18.4 文档处理器

一组文档处理程序提供了对MuPDF支持的不同文档类型的支持（请参见[第20章“文档处理程序”界面](#20文档操作接口)）。 可以根据需要启用或禁用每种处理程序类型：
```c++
/*
Choose which document handlers to include.
By default all but GPRF are enabled. To avoid building unwanted
ones, define FZ_ENABLE_... to 0.
*/
/* #define FZ_ENABLE_PDF 1 */
/* #define FZ_ENABLE_XPS 1 */
/* #define FZ_ENABLE_SVG 1 */
/* #define FZ_ENABLE_CBZ 1 */
/* #define FZ_ENABLE_IMG 1 */
/* #define FZ_ENABLE_TIFF 1 */
/* #define FZ_ENABLE_HTML 1 */
/* #define FZ_ENABLE_EPUB 1 */
/* #define FZ_ENABLE_GPRF 1 */
```
例如，为了省略对HTML的支持，我们将FZ ENABLE HTML定义为0。请注意，某些文档处理程序与其他文档处理程序共享其代码的大部分。 例如HTML和EPUB文档的年龄共享大量代码，因此仅禁用其中之一将不会对整体代码大小产生太大影响。

## 18.5 JPEG 2000支持
## 18.6 Javascript
## 18.7 字体


# 19.图片接口
## 19.1 概述

图片在文档格式中无处不在，并且有各种各样的格式，从全彩色到单色，从压缩到未压缩，
大到小。 有效表示和解码2d像素阵列的能力至关重要。
MuPDF使用抽象类型fz图像表示图像。 这采取基类的形式，可以在其上构建不同的实现。 使用标准fz_keep和fz_drop约定对所有fz_images进行引用计数：
```c++
/*
fz_drop_image: Drop a reference to an image.
image: The image to drop a reference to.
*/
void fz_drop_image(fz_context *ctx, fz_image *image);
/*
fz_keep_image: Increment the reference count of an image.
image: The image to take a reference to.
Returns a pointer to the image.
*/
fz_image *fz_keep_image(fz_context *ctx, fz_image *image);
```
## 19.2 标准图片格式
### 19.2.1 压缩
### 19.2.2 解码
### 19.2.3 显示列表
## 19.3 创建图片

要从标准类型创建图像，只需调用适当的函数即可。
例如，如果您有一个带有源数据的fz_buffer：
```c++
/*
fz_new_image_from_buffer: Create a new image from a
buffer of data, inferring its type from the format
of the data.
*/
fz_image *fz_new_image_from_buffer(fz_context *ctx, fz_buffer *buffer);
```
如果数据在文件中，请使用：
```c++
/*
fz_image_from_file: Create a new image from the contents
of a file, inferring its type from the format of the
data.
*/
fz_image *fz_new_image_from_file(fz_context *ctx, const char *path);
```

这会将数据加载到内存中，并在内部调用fz_new_image_from_buffer。
如果无法从其标头识别数据，并且需要更多信息，则可以在fz_compressed_buffer和图像中形成数据
创建于：
```c++
/*
fz_new_image_from_compressed_buffer: Create an image based on
the data in the supplied compressed buffer.
w,h: Width and height of the created image.
bpc: Bits per component.
colorspace: The colorspace (determines the number of components,
and any color conversions required while decoding).
xres, yres: The X and Y resolutions respectively.
interpolate: 1 if interpolation should be used when decoding
this image, 0 otherwise.
imagemask: 1 if this is an imagemask (i.e. transparent), 0
otherwise.
decode: NULL, or a pointer to to a decode array. The default
decode array is [0 1] (repeated n times, for n color components).
colorkey: NULL, or a pointer to a colorkey array. The default
colorkey array is [0 255] (repeatd n times, for n color
components).
buffer: Buffer of compressed data and compression parameters.
Ownership of this reference is passed in.
mask: NULL, or another image to use as a mask for this one.
Supplying a masked image as a mask to another image is
illegal!
*/
fz_image *fz_new_image_from_compressed_buffer(fz_context *ctx, int w,
int h, int bpc, fz_colorspace *colorspace, int xres, int yres, int
interpolate, int imagemask, float *decode, int *colorkey,
fz_compressed_buffer *buffer, fz_image *mask);
```
最后，如果我们有一个解码的fz_pixmap，我们可以从中形成一个新图像：
```c++
/*
fz_new_image_from_pixmap: Create an image from the given
pixmap.
pixmap: The pixmap to base the image upon. A new reference
to this is taken.
mask: NULL, or another image to use as a mask for this one.
A new reference is taken to this image. Supplying a masked
image as a mask to another image is illegal!
*/
fz_image *fz_new_image_from_pixmap(fz_context *ctx, fz_pixmap *pixmap,
fz_image *mask);
```
## 19.4 实现一个图像类型
## 19.5 图片缓存


# 20.文档操作接口
## 20.1 概述
## 20.2 实现文档操作
### 20.2.1 识别和打开
### 20.2.2 文档层级函数
### 20.2.3 页面层级函数
## 20.3 标准文档处理

MuPDF包含各种不同格式的文档处理程序。 默认情况下，其中哪个是内置/启用的取决于include / mupdf / fitz / config.h文件中的配置选项。
有关更多信息，请参见[第18章构建配置](#18构建配置)。

### 20.3.1 PDF

pdf文档处理程序提供了对PDF（便携式文档格式）的支持。 支持撰写本文时的所有当前版本（即PDF 2.0及以下）。 MuPDF包含的功能允许通过使用pdf_prefixed函数比通过标准fz_prefixed函数公开的内容更深入地访问PDF文件的内容和结构。 该库提供了pdf_specifics函数，可将fz_document_pointer安全地提升为pdf文档指针。 如果文档不是PDF，则它将返回NULL，表示无法使用pdf_functions。

### 20.3.2 XPS
### 20.3.3 EPUB
### 20.3.4 HTML
### 20.3.5 SVG
### 20.3.6 Image
### 20.3.7 CBZ


# 21.内部存储
## 21.1 概述

在第7章“内存管理”和“存储”中，我们介绍了存储的概念及其在充分利用系统可用内存中的用法。 在这里，我们解释了实现，因此文档处理程序作者（和应用程序程序员）可以利用相同的机制。

## 21.2 实现

通常，通过使用一组标准函数之一来实现对简单对象的这些保留和删除调用。 根据参考计数的预期大小，它们的范围很广，并且都处理确保线程安全所需的锁定：
```c++
void *fz_keep_imp(fz_context *ctx, void *p, int *refs);
void *fz_keep_imp8(fz_context *ctx, void *p, int8_t *refs);
void *fz_keep_imp16(fz_context *ctx, void *p, int16_t *refs);
int fz_drop_imp(fz_context *ctx, void *p, int *refs);
int fz_drop_imp8(fz_context *ctx, void *p, int8_t *refs);
int fz_drop_imp16(fz_context *ctx, void *p, int16_t *refs);
```
例如，fz_path结构定义为：
```c++
typedef struct {
int8_t refs;
} fz_path;
```
因此，可以简单地定义适当的保留和放置函数：

```c++
fz_path *fz_keep_path(fz_context *ctx, fz_path *path)
{
return fz_keep_imp8(ctx, &path->refs);
}
void fz_drop_path(fz_context *ctx, fz_path *path)
{
if (!fz_drop_imp8(ctx, &path->refs))
return;
/* code to free the contents of the path structure */
...
}
```
这些功能可以使用更复杂的变体来处理“可存储”的对象，而使用更复杂的版本来处理“可存储的关键”的对象，这些将在以下各节中进行说明。
无论采用哪种方法，这些对象对大多数用户而言基本上都是相同的-只需根据需要“保留”和“删除”它们即可。

## 21.3 参考计数
## 21.4 清除内存分配器
## 21.5 使用存储
### 21.5.1 概述
### 21.5.2 处理关键
### 21.5.3 散列
### 21.5.4 关键存储条目
### 21.5.5 收集通过



# 22.内部设备
## 22.1 线条
## 22.2 文本
## 22.3 图片
## 22.4 阴影
## 22.5 剪切和蒙版
## 22.6 组和透明度
## 22.7 平铺
## 22.8 渲染标志
## 22.9 设备颜色空间



# 23.内部路径
## 23.1 创建
## 23.2 参考计数
## 23.3 存储
## 23.4 转型
## 23.5 边界
## 23.6 stroking
## 23.7 walking


# 24.内部文本
## 24.1 创建
## 24.2 人口
## 24.3 衡量
## 24.4 克隆
## 24.5 语言
## 24.6 实现

# 25.内部阴影
## 25.1 创建
## 25.2 边界
## 25.3 画图
## 25.4 分解

# 26.内部流

# 27.内部输出

# 28.内部色彩空间
## 28.1 非基于ICC的色彩
## 28.2 基于ICC的色彩
## 28.3 色彩校准

# 29.颜色管理
## 29.1 概述

# 30.PDF解释器详细信息
## 30.1 概述

PDF文档处理程序基于MuPDF中的大量代码集，专门处理文档中找到的对象，结构和运算符。 此代码统称为PDF解释器。 尽管完全可以使用MuPDF打开PDF文件的文档并渲染页面，而完全不了解PDF解释器，但在许多情况下，更深入地访问解释器可能会有所帮助。 例如，为了重新排列PDF文档的页面或从中嵌入/提取文件，需要对底层PDF文档结构的简单访问。 实际上，对PDF文档结构的访问使得几乎任何操作都可以进行编码。

## 30.2 PDF文档

处理PDF文档的第一步是获取它的句柄。 这可以通过使用fz_open_document正常打开以获取fz_document指针来完成。 要将其“推广(promote)”为pdf文档，我们使用pdf_specifics调用：
```c++
/*
pdf_specifics: down-cast a fz_document to a pdf_document.
Returns NULL if underlying document is not PDF
*/
pdf_document *pdf_specifics(fz_context *ctx, fz_document *doc);
```

如果pdf_specifics返回非NULL，则说明您确实在处理PDF格式的文档。 使用pdf_document指针可以调用一系列新的API（请参见include / mupdf / pdf / document.h）。 就通过其组成对象处理PDF文件而言，最有用的方法之一是：
```c++
pdf_obj *pdf_trailer(fz_context *ctx, pdf_document *doc);
```
这将获得指向预告片字典对象表示形式的指针。

## 30.3 PDF对象

PDF文件由一系列对象组成。 这些对象可以有许多不同的类型，包括字典，流，数字，布尔值，名称，字符串等。有关完整的详细信息，请参见“ PDF参考手册”。 MuPDF将所有这些表示为pdf obj指针。 此类指针以通常的方式进行引用计数：
```c++
pdf_obj *pdf_keep_obj(fz_context *ctx, pdf_obj *obj);
void pdf_drop_obj(fz_context *ctx, pdf_obj *obj);
```

给定这样的指针，可以使用以下方法获取对象的实际类型：
```c++
int pdf_is_null(fz_context *ctx, pdf_obj *obj);
int pdf_is_bool(fz_context *ctx, pdf_obj *obj);
int pdf_is_int(fz_context *ctx, pdf_obj *obj);
int pdf_is_real(fz_context *ctx, pdf_obj *obj);
int pdf_is_number(fz_context *ctx, pdf_obj *obj);
int pdf_is_name(fz_context *ctx, pdf_obj *obj);
int pdf_is_string(fz_context *ctx, pdf_obj *obj);
int pdf_is_array(fz_context *ctx, pdf_obj *obj);
int pdf_is_dict(fz_context *ctx, pdf_obj *obj);
int pdf_is_indirect(fz_context *ctx, pdf_obj *obj);
int pdf_is_stream(fz_context *ctx, pdf_obj *obj);
```
如果对象属于测试类型，则所有这些都返回非零值，否则返回零。
要从PDF对象提取数据，可以使用以下功能之一：
```c++
/* safe, silent failure, no error reporting on type mismatches */
int pdf_to_bool(fz_context *ctx, pdf_obj *obj);
int pdf_to_int(fz_context *ctx, pdf_obj *obj);
fz_off_t pdf_to_offset(fz_context *ctx, pdf_obj *obj);
float pdf_to_real(fz_context *ctx, pdf_obj *obj);
char *pdf_to_name(fz_context *ctx, pdf_obj *obj);
char *pdf_to_str_buf(fz_context *ctx, pdf_obj *obj);
int pdf_to_str_len(fz_context *ctx, pdf_obj *obj);
```
实际上，在任何pdf obj指针上调用这些函数中的任何一个都是安全的。 如果对象不是预期的类型，则将返回“安全”默认值。

### 30.3.1 数组

数组对象由其他对象的列表组成，每个对象可能具有不同的类型。 因此，我们有一个功能来查询列表的长度：
```c++
int pdf_array_len(fz_context *ctx, pdf_obj *array);
```
掌握了这些知识之后，我们便可以从数组中获取所需的任何对象。
```c++
pdf_obj *pdf_array_get(fz_context *ctx, pdf_obj *array, int i);
```
理想情况下，我应该在0到length-1之间（尽管如果请求超出范围的元素，该函数只会返回NULL）。 请注意，仅借用了此函数返回的pdf obj参考。 就是说，如果您希望保持对象指针的时间超过调用的直接寿命，则应手动调用pdf_keep_obj使其保留，然后再调用pdf_drop_obj进行处理。 可以使用以下命令将对象插入给定索引的数组中：
```c++
void pdf_array_insert(fz_context *ctx, pdf_obj *array, pdf_obj *obj, int
index);
```
此后的所有对象都将重新排列数组。 或者，可以将对象放在给定点的数组中，覆盖已经存在的任何对象：
```c++
void pdf_array_put(fz_context *ctx, pdf_obj *array, int i, pdf_obj *obj);
```
如果需要扩展数组，则将其扩展，并且所有插入的对象都将创建为“ null”。 另外，可以使用以下方法将对象附加到数组：
```c++
void pdf_array_push(fz_context *ctx, pdf_obj *array, pdf_obj *obj);
```
在所有这些情况下，数组将对传入的对象采用新的引用-也就是说，在调用之后，数组和调用方都将保留对对象的引用。 如果要插入的对象是“借来的”参考，这是理想的。 在其他情况下，对象引用的所有权应向下传递到数组中，我们可以使用这些函数的替代形式：
```c++
void pdf_array_insert_drop(fz_context *ctx, pdf_obj *array, pdf_obj
*obj, int index);
void pdf_array_put_drop(fz_context *ctx, pdf_obj *array, int i, pdf_obj
*obj);
void pdf_array_push_drop(fz_context *ctx, pdf_obj *array, pdf_obj *obj);
```
这些函数之所以被命名为是因为它们等效于首先插入/放置/推入对象，然后将其删除，这具有很好的副作用，即在推送过程中遇到的任何错误仍然会导致对象被正确地删除，从而经常将调用者从 必须将调用包装在fz_try / fz_catch子句中。

## 30.4 PDF运算符处理

PDF文件中的图形内容以PDF“操作员(operators)”流的形式给出。这些运算符描述概念页面上的标记操作。为了显示PDF文件，解释器需要依次处理这些运算符。另外，最好通过直接在这些运算符流上进行运算来完成对PDF运算的某些操作（例如，编辑，清除和附加）。替代方案是，首先将运算符转换为图形对象，然后重新合成运算符流，从而导致往返转换问题以及结构的潜在损失。因此，MuPDF中的PDF解释器是围绕可扩展的pdf_processors类构造的。 pdf_processor是一组函数，每个运算符一个。解释器贯穿运算符并通过调用适当的函数来处理它们。通过更改使用中的pdf_processor，因此我们可以更改解释页面的效果。 MuPDF包含三种不同的pdf_processor实现，尽管该系统是故意开放式的，并且库的任何用户都可以提供更多的pdf_processor实现。有些甚至可以以强大的方式链接在一起。

### 30.4.1 运行处理

第一个也是最常用的处理器是pdf_run_processor。 该处理器的作用是解释传入的运算符并将其转换为设备调用（即在页面上呈现的图形对象）。 使用标准fz_run_page（和类似功能）时，这是自动使用的pdf处理器。 手动创建它们仍然很有用，尤其是在将它们与pdf过滤器处理器（或类似产品）耦合时。 可以使用以下方法创建此类处理器：
```c++
/*
pdf_new_run_processor: Create a new " run"
processor. This maps
from PDF operators to fz_device level calls.
dev: The device to which the resulting device calls are to be
sent.
ctm: The initial transformation matrix to use.
usage: A NULL terminated string that describes the ’usage’ of
this interpretation. Typically ’View’, though ’Print’ is also
defined within the PDF reference manual, and others are possible.
gstate: The initial graphics state.
nested: The nested depth of this interpreter. This should be
0 for an initial call, and will be incremented in nested calls
due to Type 3 fonts.
*/
pdf_processor *pdf_new_run_processor(fz_context *ctx, fz_device *dev,
const fz_matrix *ctm, const char *usage, pdf_gstate *gstate, int
nested);
```
该处理器的组成部分通常是名为pdf_run_...的函数，并且经常回调到主pdf解释器中（以处理在XObjects等中发现的嵌套内容流）。
### 30.4.2 过滤器处理
### 30.4.3 缓存处理
### 30.4.4 输出处理

## 30.5 PDF对象之间拷贝对象
### 30.5.1 问题描述
### 30.5.2 衔接对象
### 30.5.3 其他问题
### 30.5.4 衔接图

# 31.XPS解释器细节
## 31.1 概述

# 32.EPUB/HTML解释器细节
## 32.1 CSS规则
## 32.2 改变文本
## 32.3 双向文字

# 33.SVG解释器细节

# 34.Mu工具
## 34.1 概述

Mutool是有用的命令行实用程序的集合，这些实用程序集成到单个可执行文件中。 如前所述，MuPDF是一个C库，其中包含打开/呈现/处理各种格式的文档文件所需的所有功能。 这意味着大多数使用它的实用程序都被简化为仅调用该库的超薄外壳。 当考虑到MuPDF中内置的资源大小（字体，CMAP等）的其他因素时，这意味着使用共享库的单个副本的多个实用程序来构建单个可执行文件是很有意义的。 如果您在不带参数的命令行上运行mutool，将显示可能选项的列表：
```shell
$ mutool
usage: mutool <command> [options]
  clean -- rewrite pdf file
  convert -- convert document
  create -- create pdf document
  draw -- convert document
  extract -- extract font and image resources
  info -- show information about pdf resources
  merge -- merge pages from multiple pdf sources into a new pdf
  pages -- show information about pdf pages
  portfolio -- manipulate PDF portfolios
  poster -- split large page into many tiles
  run -- run javascript
  show -- show internal pdf objects
```
## 34.2 清除

CLEAN实用程序将生成输入PDF的清理版本。 它可以应用一系列不同的选项，可以通过不带任何选项运行mutool clean获得完整的列表：
```shell
$ mutool clean
usage: mutool clean [options] input.pdf [output.pdf] [pages]
  -p- password
  -g garbage collect unused objects
  -gg in addition to -g compact xref table
  -ggg in addition to -gg merge duplicate objects
  -gggg in addition to -ggg check streams for duplication
  -l linearize PDF
  -a ascii hex encode binary streams
  -d decompress streams
  -z deflate uncompressed streams
  -f compress font streams
  -i compress image streams
  -s clean content streams
  pages comma separated list of page numbers and ranges
```
这里的论点是很容易解释的，用法是最好的例子。 首先，最简单的说，可以使用clean来修复损坏的文件。 许多在野外发现的PDF文件都被破坏了-有时是由于传输/存档问题而损坏的，而仅仅是由于由错误的PDF编写软件创建的文件而令人失望。 运行干净通行证将尝试修复文件：
```shell
mutool clean in.pdf out.pdf
```

可以从PDF中提取单个页面（或页面范围）。 例如：
```shell
mutool clean -gggg in.pdf out.pdf 1-10,12
```
这将提取in.pdf的第1页至第10页和第12页，并将其输出到新的out.pdf中。 -gggg选项可确保将未使用的对象从PDF中删除。
可以使用以下方法将8页的PDF重新排列成小册子形式：
```shell
mutool clean -gggg in.pdf out.pdf 8,1,7,2,6,3,5,4
```
最后，是一个更奇特但又非常普遍的例子； 如果有人报告给定PDF第4页上出现的问题，则以下命令将解压缩该页面并扩展内容流，而无需解压缩图像或字体：
```shell
mutool clean -difgggg in.pdf out.pdf 4
```
如果此文件仍然存在相同的问题，则与原始文件相比，调试它通常要容易得多。

## 34.3 转换

convert实用程序使用不同的内部机制（文档编写器界面）执行与draw实用程序类似的任务。 对于任何给定的任务而言哪个更好通常取决于口味。

## 34.4 创建

创建工具允许从包含pdf运算符流以及格式化注释的简单文本文件生成PDF。

## 34.5 绘图

绘图实用程序是最常用的工具，能够将文档转换/呈现为多种位图和矢量格式。 它使用一组不同的内部机制执行与convert实用程序相似的任务。 对于任何给定的任务而言哪个更好通常取决于口味。

## 34.6 提取
## 34.7 信息
## 34.8 合成
## 34.9 页面
## 34.10 物品集
## 34.11 海报
## 34.12 运行
## 34.13 展示

# 35 Mu官方库

MuPDF API被设计为一组互锁的部件，可以用许多不同的方式将它们组装在一起以提供强大的功能范围。 这种多功能性的代价是需要一定数量的组装。对于那些希望使用更封装的解决方案的人，我们有一个帮助程序库MuOfficeLib，它可以处理许多

# 36 过渡


# 37 Mu线程


# 38 平台详情
## 38.1 概述
## 38.2 C库

在所有平台上，MuPDF构建一个C库，该库提供本书定义的标准C级API。 默认情况下，我们建立一个静态库。 可以构建动态库（确实可以在Android等平台上使用动态库），但是我们不保证发行版之间的ABI兼容性，因此默认情况下，我们并不在所有平台上都提供动态库。 标准的Unix makefile需要GNU Make，并检测第三方目录中是否存在第三方库。 如果存在，这些优先于任何系统库使用。 我们的库经常包含对标准漏洞的修正，我们尝试将此类修正上游传递给主要软件包的维护者。 鉴于我们所有的回归和质量控制测试都是在我们提供的版本中进行的，因此我们更希望人们使用我们的版本，并且在花费太多时间来解决它们之前，经常会要求使用这种版本来复制错误报告。

## 38.3 安卓查看器
## 38.4 Java桌面
### 38.4.1 语言
### 38.4.2 查看器
## 38.5 GS查看器
## 38.6 Javascript
## 38.7 Linux/Windows查看器
### 38.7.1 非GL版本
### 38.7.2 GL版本

# 怎样对MuPDF做出贡献