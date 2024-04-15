# 第1章 概述

## 1.2 分层

TCP/IP协议族只分四层：应用层、运输层、网络层、链路层

## 1.7 分用

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/%E6%95%B0%E6%8D%AE%E6%8A%A5%E5%88%86%E7%94%A8.png)

IP数据报通过协议号将数据报发给不同的协议进行处理，所以TCP、UDP应用可以使用相同端口号

# 第2章 链路层

## 2.2 以太网和IEEE 802封装

## 2.4 SLIP：串行线路IP

Serial Line IP

排队算法将交互数据流优先于成块数据流接收发送，保证交互数据流低时延

## 2.7 环回接口实验

## 2.8 最大传输单元MTU

数据链路层对数据帧长度做的限制，最大传输单元MTU。

如果IP层要传输的数据比MTU大，就需要将IP层数据进行分片。[11.5章节讨论](# 保留链接)

## 2.10 串行线路吞吐量计算

# 第3章 IP：网际协议

## 3.2 IP首部

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/IP%E6%95%B0%E6%8D%AE%E6%8A%A5%E6%A0%BC%E5%BC%8F.png)

不包含选项字段，IP首部20字节

- 4位版本号
- 4位首部长度：表示了IP首部长度。带可选项时最大为15，每单位4字节（上图中显示一行），所以IP首部最长为60字节
- 8位服务类型：3bit优先权字段（现已被忽略）；4bit TOS子字段；1bit未用位但必须置0。TOS4位分别表示：最小时延、最大吞吐量、最高可靠性和最小费用，4位只能由1位置1

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/TOS%E6%9C%8D%E5%8A%A1%E7%B1%BB%E5%9E%8B%E6%8E%A8%E8%8D%90%E5%80%BC.png)

> 因为大多数实现都不设置TOS字段，所以只能由SLIP来自己判断端口号来确定是不是交互应用的连接

- 16位总长度：指整个IP数据报的长度。总长度=首部+有效载荷；通过总长度和首部长度可以计算有效载荷的偏移量和长度

16位标识：数据报id，通常每发送一份值+1。[11.5章节详细讨论](# 保留链接)

- 3位标志
- 13位片偏移
- 8位生存时间TTL：数据报可以经过最多的路由器跳数，每经过一个路由器，TTL减1；字段为0，数据报被丢弃，并发送ICMP通知源主机。
- 8位协议码：通过协议标识，将IP数据报分到不同的协议进行处理。[分用](# 1.7 分用)
- 16位首部校验和：只对首部进行校验，数据不管

> 首部中每个16bit进行二进制反码求和，结果保存在校验和中
>
> 接收方校验时对发送方的校验和进行了求和，如果没有差错，数据全为1，如果不全为1，代表有错，并丢弃报文

- 32位源IP和32位目的IP：

- 选项
  - 安全和处理限制（军用领域）
  - 记录路径
  - 时间戳
  - 宽松的源站选路
  - 严格的源站选路



## 3.3 IP路由选择

IP层可以设置为路由功能和非路由功能，如果有路由功能，则可以转发IP数据报

对于一个机器，收到一份数据报，如果目的地址是自己就做处理，如果不是自己，IP层有路由功能则会转发，没有路由功能则会丢弃。

详情到[9.2节 选路原理](# 9.2 选路原理)

路由表中包含：

- 目的地址
- 下一跳路由器地址
- 标志
- 数据报指定网络接口

IP路由选择需要完成以下功能：

- 寻找能与目的IP地址完全匹配的条目
- 寻找能与目的网络号相匹配的条目
- 寻找标为“default”的条目





## 3.8 ifconfig



## 3.9 netstat



# 第4章 ARP：地址解析协议

## 4.2 一个例子

ARP协议：主机A要发送到主机B，已知主机A、B的IP地址，通过ARP请求报文，得到主机B的MAC地址。

## 4.3 ARP高速缓存

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/%E4%B8%BB%E6%9C%BAARP%E5%91%BD%E4%BB%A4.png)

表示了本机ARP缓存中，目的IP地址和MAC的对应关系

## 4.4 ARP的分组格式



![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/ARP%E5%88%86%E7%BB%84%E6%A0%BC%E5%BC%8F.png)

以太网目的地址、以太网源地址：MAC地址

帧类型：ARP请求值为`0x0806`

硬件类型：值为1表示以太网地址，即MAC。

协议类型：`0x0800`表示IP地址

硬件地址长度：表示硬件地址长度，即MAC长度，默认6

协议地址长度：表示协议地址长度，即IP地址长度，默认4

op：ARP请求值为1，ARP应答值为2，RARP请求值为3，RARP应答值为4

发送端以太网地址：

发送端IP地址：

目的以太网地址：

目的IP地址：

一下是WireShark中ARP请求报文

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/ARP%E8%AF%B7%E6%B1%82%E6%8A%A5%E6%96%87.png)

请求报文中携带了目的IP地址，没有携带目的MAC地址，下面的应答报文中，通过`192.168.200.104`IP地址找到了对应MAC地址

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/ARP%E5%BA%94%E7%AD%94%E6%8A%A5%E6%96%87.png)

## 4.6 ARP代理

如果ARP请求从一个网络的主机发往另一个网络的主机，那么连接这两个网络的路由器可以回答该请求，这个过程称作`ARP代理`





# 第5章 RARP：逆地址解析协议

## 5.2  RARP分组格式

帧类型：`0x8035`

请求操作代码：3

应答操作码：4





# 第6章 ICMP：Internet控制报文协议

## 6.1 引言

ICMP传递差错报文及其他信息，通常被IP层或者TCP、UDP使用，一些ICMP报文把差错报文返回给用户进程。

ICMP报文在IP数据报内部传输

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/ICMP%E5%9C%A8IP.png)

ICMP报文格式，4字节首部固定，8位类型和8位代码确定具体功能

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/ICMP%E6%8A%A5%E6%96%87%E6%A0%BC%E5%BC%8F.png)

## 6.2 ICMP报文类型

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/ICMP%E6%8A%A5%E6%96%87%E7%B1%BB%E5%9E%8B.png)

不会对差错报文再进行差错报文回显，防止差错报文中出现差错，往复循环。

下面是不会产生差错报文的情况：

1. ICMP差错报文
2. 目的地址是广播地址或多播地址的IP数据报
3. 作为链路层广播的数据报
4. 不是IP分片的第一篇
5. 源地址不是单个主机的数据报（源地址是零地址、环回地址、广播地址或多播地址）。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/ping%E4%B8%AD%E7%9A%84icmp%E8%AF%B7%E6%B1%82%E4%B8%8E%E5%BA%94%E7%AD%94.png)

## 6.3 ICMP地址掩码请求与应答

ICMP地址掩码请求用于无盘系统在引导过程中获取自己的子网掩码。系统广播它的ICMP请求报文，格式如下：

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/ICMP%E5%9C%B0%E5%9D%80%E6%8E%A9%E7%A0%81%E8%AF%B7%E6%B1%82%E5%92%8C%E5%BA%94%E7%AD%94%E6%8A%A5%E6%96%87.png)

ICMP报文中的标识符和序列号由发送端任意指定，这些值在应答中返回，发送端把应答与请求进行匹配。

## 6.4 ICMP时间戳请求与应答

ICMP时间戳请求允许系统向另一个系统查询当前时间，返回建议值是午夜（哪天午夜？需要先确定日期）开始计算的毫秒数。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/ICMP%E6%97%B6%E9%97%B4%E6%88%B3%E8%AF%B7%E6%B1%82%E5%92%8C%E5%BA%94%E7%AD%94%E6%8A%A5%E6%96%87.png)

## 6.5 ICMP端口不可达差错



# 第7章 Ping程序

## 7.2 Ping程序

`Ping`是内核进程

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/ICMP%E5%9B%9E%E6%98%BE%E8%AF%B7%E6%B1%82%E5%92%8C%E5%9B%9E%E6%98%BE%E5%BA%94%E7%AD%94%E6%8A%A5%E6%96%87%E6%A0%BC%E5%BC%8F.png)

unix系统在将ICMP报文中的标识符设置为发送进程PID，这样多个Ping程序能够正确回显对应信息。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/winping.png)

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/linuxping.png)

上面是`win`系统ping程序显示，下面是`linux`系统ping程序显示

ttl代表了生存时间，每经过一次路由ttl-1

time为icmp报文发送时间与收到回显报文的时间差，即往返时间



## 7.3 IP记录路由选项

使用`ping`程序的`-R`选项可以回显路由过程，每个节点收到ICMP回显就会把IP地址加入到回显中，需要系统开启选项功能。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/IP%E9%A6%96%E9%83%A8%E8%AE%B0%E5%BD%95%E8%B7%AF%E7%94%B1%E9%80%89%E9%A1%B9%E7%9A%84%E4%B8%80%E8%88%AC%E6%A0%BC%E5%BC%8F.png)

上图中总字节应该为39字节，[IP的选项字段最多40字节](# 3.2 IP首部)，code、len、ptr分别占用1个字节，剩余37字节，可以存放9个IP地址。

- code指明IP选项类型，对于`-R`值为7
- len为选项长度，为39
- ptr为指针字段，指向下一个存放IP地址的位置

经过路由器时，将出口地址加入IP清单

# 第8章 Traceroute程序

## 8.2 Traceroute程序的操作

因为`ping -R`需要开启，并且只能记录9个IP地址，所以使用`traceroute`程序

traceroute过程：源主机向目的主机发送一个TTL为1的报文，第一个路由器将TTL值减1，丢弃该数据报，并发回一份超时ICMP报文，得到了第一个路由地址；再发送TTL值为2的报文，获得第二个路由地址；依此类推。

最后当目的主机接收报文时TTL值为1，不会返回超时报文，traceroute程序发送一份UDP数据报并选择一个大于30000的端口，当该数据报到达时，将使目的主机的UDP模块产生一份“端口不可达”错误的ICMP报文，以此区分是否到达目的之际。



## 8.3 Traceroute输出

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/traceroute%E8%BE%93%E5%87%BA.png)

输出第一行给出目的主机名和P地址，指出最大的TTL字段值为30，40字节数据报包含20字节IP首部、8字节UDP首部和12字节的用户数据；

每行数据以TTL数值为开始，跟随目的主机名和IP地址，发送3份数据报，并打印往返时间，如果3份都没收到则打印`*`

## ![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/%E8%BF%90%E8%A1%8Ctraceroute%E7%9A%84wireshark.png)

上图中，192.168.200.104为源主机，192.168.200.2为路由器网关，筛选了ICMP超时应答报文，可以看到tpye=11，code=0

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/ICMP%E8%B6%85%E6%97%B6%E6%8A%A5%E6%96%87.png)

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/%E6%9C%80%E7%BB%88%E7%9A%84ICMP%E7%AB%AF%E5%8F%A3%E4%B8%8D%E5%8F%AF%E8%BE%BE.png)

到达目的主机后产生的ICMP端口不可达报文

## 8.5 IP源站选路选项

宽松：`-g`选项，经过指定的路由

严格：`-G`





# 第9章 IP选路

## 9.2 选路原理

网络消息经过主机时，需要配置成路由模式，非路由模式下当消息包的目的地址不是自己时就会丢弃，路由模式下会进行转发。

IP搜索路由表的步骤：

1. 搜索匹配的主机地址
2. 搜索匹配的网络地址
3. 搜索默认表项

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/%E8%B7%AF%E7%94%B1%E8%A1%A8%E9%A1%B9.png)

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/Linux%E8%B7%AF%E7%94%B1%E8%A1%A8.png)

对于一个路由器，`Flags`有5中：

- U 路由可以使用
- G路由是一个网关（路由器）；如果没有该标志，说明目的地是直接相连的
- H该路由目的地址是一个主机；如果没有标志则认为是一个网络地址
- D该路由由重定向报文创建
- M该路由已被重定向报文修改

有G标志代表间接路由，间接路由IP层目的地址是真实目的地址，链路层目的地址是下一跳路由地址

`Refcnt`（wsl2中没有？）标记了正在使用路由的活跃进程个数，面向连接的TCP需要固定路由。

`Use`标记了通过该路由发出的分组数

### 9.2.2 初始化路由表

route命令创建路由表

> route add default sun 1

第三个参数代表目的地址，第四个参数代表网关路由器，第五个参数代表度量，如果度量大于1要设置G标志

其他方式：使用路由守护程序（[第十章](# 第10章 动态选路协议)）或者[路由发现协议](# 9.6 ICMP路由发现报文)



没有目的地址路由时，最终产生“主机不可达差错”或者“网络不可达差错”。如果是被转发的数据，那么给原始发送端发送一份ICMP不可达差错报文



## 9.3 ICMP主机与网络不可达差错

当路由器收到一份IP数据报但又不能转发时，就要发送一份ICMP主机不可达差错报文



## 9.4 转发或者不转发

内核变量 ipforwarding 决定主机是否转发数据报，不为0时转发。

## 9.5 ICMP重定向差错

当IP数据报应该被发送到另一个路由时，收到数据报的路由就要发送ICMP重定向差错报文给IP数据报的发送端。

1. 主机发送数据报给R1，R1经过路由再发送到R2
2. 当R1发现主机和R2可以直达
3. R1发送一个ICMP重定向报文给主机，主机下次发送的数据报直接走R2路由

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/main/img/dev/ComputerNetwork/TCPIP%E5%8D%B7/ICMP%E9%87%8D%E5%AE%9A%E5%90%91%E6%8A%A5%E6%96%87.png)

ICMP重定向报文的接收者必须查看三个IP地址：

1. 导致重定向的IP地址（ICMP重定向报文的数据位于IP数据报的首部）
2. 发送重定向报文的路由器的IP地址（包含重定向信息的IP数据中的源地址）
3. 应该采用的路由器IP地址（ICMP报文的5-8字节）

代码：

- 0：网络重定向
- 1：主机重定向
- 2：服务类型和网络重定向
- 3：服务类型和主机重定向

重定向报文必须由路由器产生，主机使用





## 9.6 ICMP路由发现报文

路由器启动时，定期广播或者多播通告报文，一份通告报文有效30分钟，通过中包含ip地址和优先级。

主机在引导期间发送三份路由器请求报文，一旦接收到通告报文则停止发送。

主机和路由器会相互间听请求报文。



# 第10章 动态选路协议

## 10.2 动态选路

前面为静态路由，所有路由表都是通过默认配置或者route命令添加，或者是ICMP重定向报文添加。

相邻路由器之间进行通信，告知对方每个路由器当前连接的网络，这时就有了动态选路。

如果路由守护进程发现前往同一目的地址有多条路由，那么会以某种算法选择最近的路加入路由表；如果发现链路断开，则删除受影响路由或者增加另一条路径。



在一个局域网内称为自治系统（AS， Autonomous System），域内可以自由选择路由选路协议，称为内部网关协议（IGP, Interior Gateway Protocol）或者域内选路协议（intradomain routing protocol）

IGP中的协议：``路由选择信息协议`（RIP，Routing Information Protocol） 和 `开放最短路径优先`（OSPF，Open Shortest Path First）

自治系统间选路协议（EGP，Exterier Gateway Protocol）中使用``边界网关协议`（BGP，Border Gateway Protocol）



## 10.4 RIP：选路信息协议

距离向量协议：跳数

### 10.4.1 报文格式

RIP报文包含在UDP数据包中

图10-2 10-3

字段：

- 命令字段：1表示请求，2表示应答，3，4舍弃，5轮询，6轮询表项
- 版本字段：1，第二版RIP之后为2
- 地址系列：20个字节，对于IP地址来说是2。20字节可以通告25条路由
- 度量：跳数

RIP常用UDP端口号是520

1. 初始化：启动一个路由守护程序，判断启动了哪些接口，并在每个接口上发送请求报文，要求其他路由器发送完整路由表。命令字段设置为1，地址系列设置为0，度量字段设置为16，这是要求另一端发送完成路由表的特殊请求报文。
2. 接收到请求：如果是特殊请求，则发送完整路由表。如果是普通请求，处理每一个表项：如果有连接到指明地址的路由，则将度量设置为接收请求路由的跳数，否则设置为16。16为特殊值，无穷大。然后发回响应
3. 接收到响应。更新表项
4. 定期选路更新。每过30秒，所有或部分路由器将其完整路由表发送给相邻路由器。发送路由表可以是广播形式，或是点对点。
5. 触发更新。每当一条路由的度量发生变化时，就对它进行更新，不需要发送完整路由表。

每条路有有定时器，RIP发现一条路由3分钟未更新，将路由度量设置为无穷大并删除。

例：

A路由器路由表中，到网络$z$的跳数7跳；当相邻的路由器D通告自己路由表，到网络$z$的条数3跳；此时A会更新自己的路由表，将到达网络$z$的条数更新为$3+1=4$跳





## 10.6 OSPF：开放最短路径优先

以自身路由器为根节点，使用Dijkstra最短路径算法计算最短路径，生成一个图。每个链路的费用可以由管理员设置。

OSPF直接使用IP

相对于RIP的优点：

1. OSPF可以对每个IP服务类型（FTP、SMTP、DNS、ICMP等）计算各自路由表
2. 给每个网络接口指定无维数费用（什么是无维数？）；可以通过吞吐率、往返时间、可靠性或其他性能进行计算费用；可以给每个IP服务类型设置费用
3. 存在多条费用相同路径，可以同时使用，有利于平均分配流量。
4. OSPF支持子网：可以划分不同子网指定不同的路由选择算法
5. 安全性：鉴别机制，简单密钥或者MD5加密密钥



## 10.7 BGP：边界网关协议

每个自治系统之间的网关协议

1. 从相邻AS处获得子网可达性信息
2. 向本AS内部的所有路由器传播这些可达性信息
3. 基于可达性信息和AS策略，决定到达子网的路由

自治系统的IP数据报分为本地流量和通过流量，自治系统中，本地流量是起始或者终止于该系统的流量，其他为通过流量。

AS分为几种类型：

1. 残桩自治系统：与其他自治系统只有单个连接，只有本地流量
2. 多接口自治系统：与其他自治系统有多个连接，但拒绝传送通过流量
3. 转送自治系统：与其他自治系统有多个连接，在一些策略下可以转送通过流量

BGP使用TCP作为传输层协议，两个运行BGP的系统之间建立一条TCP连接，然后交换整个路由表。BGP列举了到每个目的地址的路由。

## 10.8 CIDR：无类型域间选路



# 第11章 UDP：用户数据报协议

