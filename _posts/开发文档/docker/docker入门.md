# 第三章 Docker安装



## 3.6 Docker存储驱动的选择

每个docker容器都有一个本地存储空间用于保存层叠的镜像层（Image Layer）以及挂在的容器文件系统。默认情况下，容器的所有读写操作都发生在其镜像层上或挂在的文件系统中。

在Linux上，Docker可选择的一些存储驱动包括AUFS、OVerlay2、Device Mapper、Btrfs 和 ZFS。

在Windows上只有一种：Windows Filter。

存储驱动的选择是节点级别的，即每个Docker主机只能选择一种驱动。

在Linux上，在 /etc/docker/daemon.json 文件修改存储引擎配置

```json
{
	"storage-driver": "overlay2"
}
```

配置需要重启镜像和容器之后生效，因为不同的存储引擎有不同的存储目录。

如果希望在切换存储引擎之后能够继续使用之前的镜像和容器，需要将镜像保存为Docker格式，上传到某个镜像仓库，修改本地Docker存储引擎并重启，之后从镜像仓库将镜像拉取到本地，最后重启容器。

通过下面的命令检查Docker当前的存储驱动类型

```shell
docker system info
```

### 3.6.1 Device Mapper 配置

默认情况下，Device Mapper 采用 loopback mounted sparse file 作为底层实现来为 Docker 提供存储支持。如果是开箱即用就可以使用默认配置。

为了达到 Device Mapper 在生产环境中的最佳性能，读者需要将底层实现修改为 direct-lvm 模式。在Docker 17.06 之后才能使用该 direct-lvm 作为存储驱动。



### 3.6.2 让Docker自动设置direct-lvm

1. 将下面的存储驱动配置添加到/etc/docker/daemon.json中

```json
{
    "storage-dirver": "devicemapper",
    "storage-opt": [
        "dm.directlvm_device=/dev/xdf",
        "dm.thinp_percent=95",
        "dm.thinp_metapercent=1",
        "dm.thinp_autoextend_threshold=80",
        "dm.thinp_autoextend_percent=20",
        "dm.directlvm_device_force=false"
    ]
}
```

Device Mapper 和 LVM 是很复杂的知识点，下面简单介绍一下个配置项的含义：

- dm.directlvm_device：设置了块设备的位置。为了存储的最佳性能以及可用选哪个，块设备应当位于高性能存储设备或者外部RAID阵列
- dm.thinp_percent=95：设置了镜像和容器允许使用的最大存储空间占比，默认95
- dm.thinp_metapercent：设置了元数据存储允许使用的存储空间大小
- dm.thinp_autoextend_threshold：设置了LVM自动扩展精简池的与之，默认80
- dm.thinp_autoextend_percent：表示当出发精简池自动扩容机制的时候，扩容的大小应当占现有空间的比例
- dm.directlvm_device_force：允许用户决定是否将块设备格式化为新的文件系统

2. 重启docker
3. 确认Docker已成功运行，并且块设备配置已被成功加载

### 3.6.3 手动配置 Device Mapper 的 direct-lvm

下面列出的内容是用户需要了解并在配置的时候仔细斟酌。

- 块设备：使用direct-lvm模式的时候，可能用到块设备。这些块设备应该位于高性能的存储设备之上。
- LVM配置：Docker的Device Mapper存储驱动底层利用LVM来实现，因此需要配置LVM所需的物理设备、卷组、逻辑卷和精简池。读者应当使用专用的物理卷并将其配置在相同的卷组中。这个卷组不应当被Docker之外的工作负载所使用。此外还需要配置额外两个逻辑卷，分别用于u才能出数据和源数据信息。另外，要创建LVM配置文件、指定LVM自动扩容的出发与之，以及自动扩容的大小，并且为自动扩容配置相应的监控，保证自动扩容会被触发。
- Docker配置：修改Docker配置文件之前要先保存原始文件再进行修改（/etc/docker/daemon.json）



# 第四章 纵观Docker

## 4.1 运维视角

用户安装Docker的时候涉及到两个主要的组件：Docker客户端和Docker daemon（服务端或引擎）

daemon实现了Docker引擎的API

使用Linux默认安装时，客户端与daemon之间的通信是通过本地IPC/UNIX Socket完成的（/var/run/docker.sock）；在Windows上是通过名为npip:////./pipe/docker_engine的管道完成的。



### 4.1.1 镜像

将Docker镜像理解为一个包含了OS文件系统和应用的对象。在Docker中，镜像实际上等价于未运行的容器。

```shell
docker image ls
```

运行该命令可以查看主机上运行的镜像

Docker主机上获取镜像的操作称为拉取。如果使用Linux，那么会拉取ubuntu:lastest镜像；如果使用Windows，则会拉取microsoft/powershell:nanoserver镜像

```shell
docker image pull 要拉取的镜像名称和版本
docker image pull ubuntu:latest
docker image pull centos
docker image pull microsoft/powershell:nanoserver
```

如果拉取如nginx的应用容器，则读者会得到一个包含操作系统的镜像，并且在镜像中还包含了运行Nginx所需的代码



### 4.1.2 容器

使用下面的命令启动镜像

启动容器的命令如下：

```
docker container run -it 要启动的镜容器
docker container run -it ubuntu:lastest /bin/bash
docker container run -it microsoft/powershell:nanoserver pwsh.exe
```

docker container run 命令告诉 docker daemon启动新的容器，参数 -it 告诉Docker开启容器的交互模式，并将用户当前的shell连接到容器的终端。然后是要启动的镜像名称以及将要启动容器中的哪个应用。

容器是基于镜像启动的，同一个镜像启动多次可以产生多个容器



### 4.1.3 连接到运行中的容器

使用下面的命令进行连接，可以将shell连接到一个运行中的容器终端

```
docker container exec -it loving_meninsky bash
```

具体的名称 `loving_meninsky` 需要使用 `docker container ls -a` 进行查看

未运行的容器在连接之前需要先启动

```
docker start name
```



## 4.2 开发视角

容器即应用。

Dockerfile纯文本文件，描述了如何将应用构建到Docker镜像中

```
docker image build -t test:latest .
```

使用当前目录的dockerfile文件构建一个名称test的镜像

构建完成之后，主机就会出现一个新的名为test的镜像

```
docker container run -d \
```

启动该镜像

然后访问 localhost:8080 来测试该应用



# 第五章 Docker引擎

## 5.1 简介

Docker引擎是用来运行和管理容器的核心软件。

基于开放容器计划（OCI）的标准要求，Docker引擎采用的组件是可替换的。

Docker引擎主要由以下组件构成：

- Docker客户端
- Docker守护进程
- containerd
- runc

它们共同负责容器的创建和运行

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/docker/docker%E6%80%BB%E4%BD%93%E9%80%BB%E8%BE%91.png)



## 5.2 详解

Docker首次发布时，引擎由两个核心组件构成：LXC和Docker daemon。

Docker daemon是单一的二进制文件，包含客户端、API、容器运行时、镜像构建等

LXC提供了对命名空间（Namespace）和控制组（CGroup）等基础工具的操作能力，它们是基于Linux内核的容器虚拟化技术。



### 5.2.1 摆脱LXC

为什么要拜托LXC：

1. LXC是基于Linux的，这对一个立志于跨平台的项目是个问题；
2. 核心组件依赖外部工具，会给项目带来风险，影响发展；

因此Docker公司开发了Libcontainer的自研工具，用于替代LXC



### 5.2.2 摒弃大而全的Docker daemon

Docker daemon的整体性带来了很多问题：

- 难于变更
- 运行越来越慢

将其拆解为小而专的工具，这些小工具是可替换的。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/docker/Docker%E5%BC%95%E6%93%8E%E6%9E%B6%E6%9E%84.png)



### 5.2.3 开放容器计划（OCI）的影响

规定了

- 镜像规范
- 容器运行规范

### 5.2.4 runc

runc是OCI容器运行时规范的参考实现。

runc是一个轻量级的、针对Libcontainer进行了包装的命令行交互工具。

runc只有一个作用，就是创建容器。

### 5.2.5 containerd

该工具的主要功能是管理容器的生命周期，包括start、stop、pause、rm等。该工具在系统中以daemon的方式运行。Kubernetes也可以通过cri-containerd使用containerd。

该工具随着时间推移还增加了镜像管理功能。



### 5.2.6 启动一个新的容器

```
docker container run --name ctrl -it alpine:latest sh
```

当使用Docker命令行工具执行上述命令时，Docker客户端会将其转换为合适的API格式并发送到正确的API断电上。

API是在daemon中实现的。一旦daemon接收到创建新容器的命令，它就会向containerd发出调用。使用CRUD风格的API，通过gRPC与containerd进行通信。

containerd并不负责创建容器，它将指挥runc来创建。containerd将Docker镜像转换为OCI bundle，并让runc创建新容器。

runc与操作系统内核接口进行通信，基于所有必要的工具来创建容器。容器进程作为runc的子进程启动，启动完毕，runc将退出。



### 5.2.7 该模型优势

将所有用于启动、管理容器的逻辑和代码从daemon中移出，意味着容器运行时与Docker daemon解耦。



### 5.2.8 shim

shim是实现无daemon的容器。

containerd指挥来创建新容器，每次创建容器它都会fork一个新的runc实例。创建完成后runc退出。

一旦容器的父进程runc退出，相关联的containerd-shim就会成为容器的父进程。作为容器的父进程，shim的部分职责如下：

- 保持所有STDIN和STDOUT流是开启状态，从而当daemon重启的时候，容器不会因为管道的关闭和终止
- 将容器的退出状态反馈给daemon

### 5.2.9 在Linux中的实现

Linux系统中，前面谈到的组件由单独的二进制来实现，具体包括dockerd(docker daemon)、docker-containerd(containerd)、docker-containerd-shim(shim)和docker-runc(runc)。

通过在Docker宿主机的Linux系统中执行ps命令可以看到以上组件的进程。当然，有些进程在运行容器的时候才可见。



### 5.2.10 daemon的作用

daemon的主要功能包括镜像管理、镜像构建、REST API、身份验证、安全、核心网络及编排。





# 第六章 Docker镜像

## 6.1 简介

Docker镜像可以理解为VM模板

## 6.2 详解

构建时、运行前：镜像

运行时：容器

### 6.2.1 镜像和容器

通常使用 `docker container run` 和 `docker service create` 命令从某个镜像启动一个或多个容器。一旦容器从镜像启动后，二者之间就变成了相互依赖的关系，并且在镜像上启动的容器全部停止之前，镜像是无法被删除的。



### 6.2.2 镜像通常比较小

容器的目的就是运行应用或服务，这意味着容器的镜像中必须包含应用/服务运行所必须的操作系统的应用文件。但是容器又追求快速和小巧，这意味着构建镜像的时候需要裁剪掉不必要的部分，保持较小的体积。

例如：Docker镜像中不会包含6个shell让用户选择，通常之后一个精简shell；镜像中还不会包含内核，容器都是共享所在Docker主机的内核。

### 6.2.3 拉取镜像

Linux Docker主机本地镜像仓库通常位于 `/var/lib/docker/<storage-device>`

Windows Docker主机则是 `C:\ProgramData\docker\windowsfilter`

```
docker image pull iamgename
```



### 6.2.4 镜像命名



### 6.2.5 镜像仓库服务

Docker 镜像存储在镜像仓库服务（image registry）中。Docker客户端的镜像仓库服务是可配置的，默认使用Docker Hub。

镜像仓库服务可以设置多个镜像仓库，且每个镜像仓库包含多个镜像

**官方和非官方镜像仓库**

Docker Hub也分为官方仓库和非官方仓库。

大部分流行的操作系统和应用都在 Docker Hub的官方仓库中有对应的镜像。



### 6.2.6 镜像命名和标签

只需要给出镜像的名字和标签，就能在官方仓库中定位一个镜像（采用":"分隔）。

```
docker image pull centos:latest
```

上面的命令即拉取centos镜像，标签是latest

### 6.2.7 为镜像打多个标签

同一个镜像（镜像ID相同），可能会有不同的标签。

使用命令拉取镜像的时候，带上 `-a` 参数就可以将该名称的所有镜像拉取下来。

同一个ID的镜像，有不同的标签，使用 `docker image ls` 展现出的列表会将不同标签的列举为两行，实际上因为ID相同只会下载一个。

### 6.2.8 过滤docker image ls的输出内容

Docker提供`--filter`参数来过滤`docker iamge ls`命令返回的镜像列表内容。

```
docker iamge ls --filter dangling=true
```

上面的命令的意思是，返回悬虚镜像（dangling），即没有标签的镜像。

出现这种情况的原因是，新构建了一个镜像，但是该镜像所打的标签已经存在，所以这个标签会从旧镜像上移除，旧镜像就没有标签了。

```
docker image prune
```

该命令可以移除所有悬虚镜像。如果添加 `-a` 参数，Docker 会额外移出没有被使用的镜像。

Docker目前支持的过滤器：

- dangling：返回悬虚镜像（true）或非悬虚镜像（false）
- before：需要镜像名称或者ID作为参数，返回在指定镜像之前被创建的全部镜像。
- since：返回指定镜像之后被创建的全部镜像
- label：根据标注的名称或值，对镜像进行过滤
- reference：正则表达式

使用`--format`参数通过Go模板对输出内容进行格式化。

```
docker image ls --format "{{.Size}}"
docker image ls --format "{{.Repository}}" "{{.Tag}}" "{{.Size}}"
```



### 6.2.9 通过CLI方式搜索Docker Hub

`docker search` 命令允许通过CLI方式搜索Docker Hub。可以通过 “NAME”和“DESCRIPTION”进行匹配

> C:\Users\LG>docker search mysql
> NAME                           DESCRIPTION                                     STARS     OFFICIAL   AUTOMATED
> mysql                          MySQL is a widely used, open-source relation…   12644     [OK]
> mariadb                        MariaDB Server is a high performing open sou…   4855      [OK]
> percona                        Percona Server is a fork of the MySQL relati…   578       [OK]
> phpmyadmin                     phpMyAdmin - A web interface for MySQL and M…   544       [OK]

### 6.2.10 镜像和分层

Dockers镜像由一些松耦合的只读镜像组成。Docker负责堆叠这些镜像层，并且将它们表示为单个统一的对象。

在拉取镜像的时候，会显示如下格式的输出

```
591fsf48w98e7: Pull complete
f987a1v4n7g8e: Pull complete
we189v1d879d7: Pull complete
q948c1a68d7re: Pull complete
3s648j87t8wwe: Pull complete
```

由该字段结尾的就是被拉取的分层镜像。

使用下面的命令可以查看镜像的分层：

```
docker image inspect ubuntu:latest
```

所有Docker镜像都起始于一个基础镜像层，当进行修改或增加新的内容时，就会在当前镜像层之上，创建新的镜像层。

例如：一个基于Ubuntu的镜像，这是第一层；如果添加一个Python包，那么就会在第一层镜像上创建第二层。

Docker通过存储引擎（快照机制）的方式来实现镜像层对战，并保证多镜像层对外展示为统一的文件系统。



### 6.2.11 共享镜像层

多个镜像之间可以共享镜像层，这样可以有效节省空间并提升性能。如果再拉取新镜像时，如果某个镜像分层已经存在，就会以 `Already exists` 结尾，表示该镜像分层不需要拉取。



### 6.2.12 根据摘要拉取镜像

假设一个镜像 `golftrack:1.5` 有一个已知bug，拉取该镜像修复后又推回仓库中，并且还是1.5标签。此时，构建新镜像使用了原来有问题的镜像的标签，晕啊镜像被覆盖，但是生产环境中遗留了大量运行中的容器，没有好的办法区分是有问题的版本还是没问题的版本。

这个时候需要镜像摘要（Image Digest）了。

每一个镜像现在都有一个基于其内容的密码散列值，该散列值也称为摘要

每次查看镜像列表的时候使用 `--digests` 参数就可以看到镜像的摘要

```
docker image ls --digests centos
```

根绝摘要拉取镜像能够拉取到十分准确的镜像

```
docker image pull centos@fnaoij1as56d4fe68a7r
```

### 6.2.13 镜像散列值（摘要）

### 6.2.14 多架构的镜像

### 6.2.15 删除镜像

```
docker iamge rm 4564d8saf97
```

通过上面的命令就可以删除某个镜像。

删除操作会再当前主机上删除该镜像以及相关的镜像层。这意味着无法通过 `docker iamge ls` 看到删除后的镜像，并且对应的包含镜像层的数据的目录会被删除。但是某个镜像层被多个镜像共享，那只有当全部依赖该镜像层的镜像都被删除后，该镜像层才会被删除。

## 6.3 命令

```
docker image pull
docker image ls
docker image inspect
docker image rm
```

# 第七章 Docker容器

## 7.1 简介

容器是镜像运行时实例。

```
docker container run -it ubuntu /bin/bash
```

上面的命令会启动ubuntu镜像，并运行Bash shell作为其应用，并且`-it`参数将该应用和执行该命令的shell连接。

```
docker container stop
docker container start
```

使用上面的命令停止或再次启动容器。

## 7.2 详解

### 7.2.1 容器vs虚拟机

容器和虚拟机都依赖于宿主机才能运行。假设宿主机是需要运行4个业务的应用的物理服务器。

![image-20220527110516091](C:\Users\LG\AppData\Roaming\Typora\typora-user-images\image-20220527110516091.png)

在虚拟机模型中，首先要开启物理机并启动Hypervisor引导程序。一旦启动，就会占有机器上全部的物理资源，如CPU、RAM、存储和NIC。Hypervisor接下来就会将这些物理资源划分为虚拟资源，并且看起来与真实物理引擎资源完全一致。然后Hypervisor将这些资源打包进VM中。这样用户就可以使用这些虚拟机并在其中安装操作系统和应用。

在容器模型中，服务器启动之后，所选择的操作系统会启动。Docker 中可以选择 Linux 或 Windows，OS也占用了全部的硬件资源。在OS之上需要安装容器引擎。容器引擎可以获取系统资源，比如进程树文件系统以及网络栈，接着将资源分割为安全的相互隔离的资源，称之为容器。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/docker/%E5%AE%B9%E5%99%A8%E5%BD%A2%E5%BC%8F.png)

从更高的层面讲，Hypervisor是硬件虚拟化：硬件物理资源划分为虚拟资源；容器是操作系统虚拟化，容器将系统资源划分为虚拟资源。

### 7.2.2 虚拟机额外开销

虚拟机模型将底层硬件资源划分到虚拟机中。每个虚拟机都是包含了虚拟CPU、虚拟RAM、虚拟磁盘等资源的一种软件结构。因此，每个虚拟机都需要有自己的操作系统来声明、初始化并管理这些虚拟资源。但操作系统本身是有额外开销的。每个操作系统都需要独立许可证，并且需要打补丁升级等等。

容器模型具有在宿主机操作系统中运行的单个内核。在一台主机上运行多个容器共享一个操作系统/内核。这意味着只有一个操作系统小号CPU、RAM等。

容器的启动速度也比虚拟机启动快得多。



### 7.2.3 运行的容器

### 7.2.4 检查Docker daemon

使用下面的命令检查Docker是否正在运行

```
docker version
```

> Client:
>  Cloud integration: v1.0.24
>  Version:           20.10.14
>  API version:       1.41
>  Go version:        go1.16.15
>  Git commit:        a224086
>  Built:             Thu Mar 24 01:53:11 2022
>  OS/Arch:           windows/amd64
>  Context:           default
>  Experimental:      true
>
> Server: Docker Desktop 4.8.2 (79419)
>  Engine:
>   Version:          20.10.14
>   API version:      1.41 (minimum version 1.12)
>   Go version:       go1.16.15
>   Git commit:       87a90dc
>   Built:            Thu Mar 24 01:46:14 2022
>   OS/Arch:          linux/amd64
>   Experimental:     false
>  containerd:
>   Version:          1.5.11
>   GitCommit:        3df54a852345ae127d1fa3092b95168e4a88e2f8
>  runc:
>   Version:          1.0.3
>   GitCommit:        v1.0.3-0-gf46b6ba
>  docker-init:
>   Version:          0.19.0
>   GitCommit:        de40ad0

只有当输出包含Client和Server内容时，代表运行成功。



### 7.2.5 启动一个简单容器

```
docker container run
```

### 7.2.6 容器进程

启动容器时的命令

```
docker container run -it centos /bin/bash
```

这个bash shell会成为容器中运行的且唯一运行的进程。

使用`ps -elf`可查看

> [root@a9624bf76713 /]# ps -elf
> F S UID        PID  PPID  C PRI  NI ADDR SZ WCHAN  STIME TTY          TIME CMD
> 4 S root         1     0  0  80   0 -  3013 -      05:11 pts/0    00:00:00 /bin/bash
> 4 R root        16     1  0  80   0 - 11167 -      05:11 pts/0    00:00:00 ps -elf

如果输入`exit`会退出bash shell，同时会停止容器。因为容器中如果没有进程在执行，他就会自己退出。

如果使用`Ctrl-PQ`组合键，会将容器放在后台运行。使用下面的命令将重新回到该容器

```
docker container exec -it containerID bash
```

使用下面的命令停止和删除容器

```
docker container stop id
docker container rm id
```



### 7.2.7 容器生命周期

运行、启动、停止、删除



### 7.2.8 停止容器

`docker container stop`命令向容器内的PID 1进程发送了 `SIGTERM` 信号。如果10s内进程没有终止，就会收到`SIGKILL`信号。

### 7.2.9 利用重启策略进行容器的自我修复

通常建议在运行容器的时候配置好重启策略。这是容器的一种自我修复能力，可以在指定事件或者错误后重启来完成自我修复。

重启策略应用于每个容器，可以作为参数被强制传入`docker-container run`命令中，或者在Compose文件中声明。

容器支持的重启策略包括：always、unless-stopped、on-failed

```
docker container run -it --restart always alpine sh
```

- always：该策略除非是使用明确停止命令`docker container stop`，否则该容器停止后会不断尝试重启。但是通过`stop`命令停止的容器，在Docker daemon重启的时候，该容器也会重启
- unless-stopped：和上面策略的区别是，重启容器并不会使`stop`命令停止的容器重启
- on-failed：该策略会在退出容器且返回值不是0的时候重启容器。

### 7.2.10 Web服务器示例

启动一个Web服务器镜像

```
docker contianer run -d --name webserver -p 80:8080
```

`-d`参数使得该容器在后台运行，终端不会连接

`-p`参数将主机的80端口和容器的8080端口映射，意味着当访问主机的80端口时，会直接映射访问容器的8080端口



### 7.2.11 查看容器详情

有时候没有指定镜像启动的应用参数，应用也会启动。这是因为在构建镜像的时候，通过嵌入指令的方式来列举希望容器运行时启动的默认应用。

可以使用 `docker image inspect` 查看

>  "Cmd": [
>                 "/bin/sh",
>                 "-c",
>                 "#(nop) ",
>                 "CMD [\"/bin/bash\"]"
>             ],

该项中展示将会执行的命令或应用



### 7.2.12 快速清理

停止并删除所有容器

```
docker container rm $(docker contianer ls -aq) -f
```



## 7.3 命令

```
docker container run
docker container ls
docker container exec
docker container stop
docker container start
docker container rm
docker container inspect
```



# 第八 应用容器化

Docker 的核心思想就是如何将应用整合到容器中，并且能在容器中实际运行。

将应用整合到容器并且运行起来的过程叫做“容器化”或“Docker化”。

## 8.1 简介

完整的应用容器化过程主要分为以下几个步骤：

1. 编写应用代码
2. 创建一个Dockerfile，其中包括当前应用的描述、以来以及该如何运行这个应用
3. 对该Dockerfile执行docker image build命令
4. 等待Docker将应用程序构建到Docker镜像中

## 8.2 详解

本小节内容主要分为以下几点：

- 单体应用容器化
- 生产环境中的多阶段构建
- 最佳实践

### 8.2.1 单体应用容器化

接下来将逐步展示《深入浅出Docker》附带的应用实践。

1. 获取应用代码
2. 分析Dockerfile
3. 构建应用镜像
4. 运行该应用
5. 测试应用
6. 容器应用化细节
7. 生产环境中多阶段构建
8. 最佳实践

**1.获取应用代码**

[本书附带资源，已上传到私人git仓库](https://gitee.com/ligen0121/all-document/blob/dev/%E5%BC%80%E5%8F%91%E6%96%87%E6%A1%A3/docker/docker%E9%85%8D%E5%A5%97%E8%B5%84%E6%BA%90.zip)

本例使用 `psweb` 应用



**2.分析Dockerfile**

代码目录中，有一个名称为 `Dockerfile` 的文件，该文件包含了对当前应用的描述，并且能指导Docker完成镜像的构建。

在Docker中，包含应用文件的目录通常被称为构建上下文（Build Context）。通常将Dockerfile放到构建上下文的根目录下。

具体内容

```dockerfile
# Test web-app to use with Pluralsight courses and Docker Deep Dive book
# Linux x64
FROM alpine

LABEL maintainer="nigelpoulton@hotmail.com"

# Install Node and NPM
RUN apk add --update nodejs nodejs-npm

# Copy app to /src
COPY . /src

WORKDIR /src

# Install dependencies
RUN  npm install

EXPOSE 8080

ENTRYPOINT ["node", "./app.js"]

```

该文件的描述含义：以 `alpine` 镜像作为当前镜像的基础，指定维护者（maintainer）为 `nigelpoulton@hotmail.com`，安装 `Node.js` 和 `NPM`。将应用的代码复制到镜像当中，设置新的工作目录，安装依赖包，记录应用的网络端口，最后设置 `app.js` 设置为默认运行应用。

每个Dockerfile文件的第一行都是FROM指令，指定镜像作为将要构建镜像的基础镜像层。

接下来，LABEL标签方式指定了维护者为xxxx。每个标签是一个键值对，通过增加标签可以添加自定义的元数据。

`RUN apk add --update nodejs npm` 指令使用 `alpine` 的 `apk` 包管理器将 `nodejs` 和 `nodejs-npm` 安装到当前镜像中。`RUN`指令会在`FROM`指定的镜像基础之上来运行。

`COPY . /src` 指令将应用相关文件从构建上下文复制到当前镜像中，并且新建一个镜像层来存储。

Dockerfile 通过 WORKDIR 指令，为Dockerfile中尚未执行的指令设置工作目录。该目录与镜像有关，并且会作为元数据记录到镜像配置中，但不会创建新的镜像层。

然后 `RUN npm install`根据`package.json`中的配置信息，使用`npm`来安装当前应用的相关依赖包。`npm`命令会在前文设置的工作目录中执行，并且在镜像中新建镜像层来保存相应的依赖文件。

然后设置 8080 端口对外提供服务。这个配置被镜像作为元数据保存下来，并不产生新的镜像层。

最后通过 `ENTRYPOINT` 指令来指定当前镜像的入口程序，也是配置元数据信息，不产生新的镜像层。

最终的镜像层次

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/docker/imagelayer.png)

**3.容器化当前应用/构建具体镜像**

```
docker iamge build -t web:latest .
```

命令执行结束后，检查本地Docker镜像库查看是否包含新建镜像

> REPOSITORY   TAG       IMAGE ID       CREATED         SIZE
> web          latest    31f813d0cf26   8 minutes ago   80.7MB

`docker image inspect web:latest`即可查看Dockerfile的配置项

**4.推送镜像到仓库**

创建镜像后，可以使用 `docker image push` 命令推送到默认仓库保存。

推送镜像之前需要使用DockerID登录Docker Hub。

```
docker login
```

推送之前还要为镜像打标签，推送过程需要如下信息

- Registry（镜像仓库服务）
- Repository（镜像仓库）
- Tag（镜像标签）

Registry和Tag可以不指定，默认Registry=docker.io和Tag=latest。但是Docker并没有给Repository提供默认值，而是从被推送镜像中的REPOSITORY属性值获取。

刚才构建的镜像 web:latest ，在push的时候，会尝试将镜像推送到 docker.io/web:latest 中，但是 nigelpoulton 这个用户没有 web 这个镜像仓库的访问权限，所以只能尝试推送到 nigelpoulton 这个二级命名空间。因此需要使用 nigelpoulton 这个ID，为当前镜像重新打上标签

```
docker image tag web:latest nigelpoulton/web:latest
docker image tag <current-tag> <new-tag>
```

指定了额外标签，没有覆盖原来的标签



**5.运行应用程序**

上面例子的应用程序运行

```
docker container run -d --name c3 -p 5537:8080 web:latest
```

基于 web:latest 镜像，创建了一个名称为c3的容器。

然后通过指定的端口就可以访问到该应用



**6.APP测试**

注意防火墙

**7.详述**

Dockerfile中的注释以`#`开头

每一条指令 `INSTRUCTION argument`，指令不区分大小写，习惯使用大写提升可读性。

如果指令向镜像中添加了新的文件或程序，是会新建镜像层的；如果只是配置相关就是元数据。

可以通过下面的命令查看构建镜像的过程中都执行了哪些命令

```
docker image history
```

该输出每行都对应了Dockerfile中的一条指令，顺序自下而上。CREATE BY展示了具体的指令。

SIZE 列对应不为0的指令，就是会新建镜像层的指令。

使用FROM来使用官方基础镜像是最好的。



### 8.2.2 生产环境中的多阶段构建

因为 `RUN` 命令会新建镜像层，所以在使用的尽量使用 `&&` 和 `\` 将命令连接起来，这样能够产生最少的镜像层。

有些构建可能会用到构建工具，这些构建工具在生产环境中是不需要的，所以可以移出。

这里应用建造者模式，使用两种Dockerfile，一种开发环境，一种生产环境。

这样方式相比多阶段构建还是复杂的。

多阶段构建方式hi使用一个Dockerfile，其中包含多个FROM指令。每一个FROM指令都是一个新的构建阶段，并且可以方法的复制之前阶段的构件。

```dockerfile
FROM node:latest AS storefront
WORKDIR /usr/src/atsea/app/react-app
COPY react-app .
RUN npm install
RUN npm run build

FROM maven:latest AS appserver
WORKDIR /usr/src/atsea
COPY pom.xml .
RUN mvn -B -f pom.xml -s /usr/share/maven/ref/settings-docker.xml dependency:resolve
COPY . .
RUN mvn -B -s /usr/share/maven/ref/settings-docker.xml package -DskipTests

FROM java:8-jdk-alpine
RUN adduser -Dh /home/gordon gordon
WORKDIR /static
COPY --from=storefront /usr/src/atsea/app/react-app/build/ .
WORKDIR /app
COPY --from=appserver /usr/src/atsea/target/AtSea-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java", "-jar", "/app/AtSea-0.0.1-SNAPSHOT.jar"]
CMD ["--spring.profiles.active=postgres"]
```

该文件中有3个FROM指令。每一个指令都是单独的构建阶段，各个阶段在内部从0开始编号

- 阶段0：storefront
- 阶段1：appserver
- 阶段2：production

重点在阶段2中的`COPY --from` 命令，它从之前的阶段构建的镜像中仅复制生产环境相关的应用代码，而不需要复制生产环境不需要的构件。

使用样例程序的 `atsea-simple-shop-app/app` 完成构建镜像。

通过 `docker image ls` 命令查看构建结果。

> 暂时略

可以看到，输出内容第一行显示了在storefront阶段拉取的node:latest镜像，下一行内容为该阶段生成的镜像。因为包含了很多构建工具，因此体积很大。

第3~4行是在appserver阶段拉取的镜像，也很大

最后一行是最后的阶段，可以明显地看到该镜像的体积相对很小，因为它省略了很多构建工具，达到尽可能小的体积。

### 8.2.3 最佳实践

**1.利用构建缓存**



**2.合并镜像**



**3.使用no-install-recommends**



**4.不要安装MSI包**



# 第九章 使用Docker Compose部署应用



# 第十章 Docker Swarm



# 第十一章 Docker网络







# 第十三章 卷与持久化



# 第十四章 使用Docker Stack部署应用



# 第十五章 Docker安全



# 第十六章 企业版工具



# 第十七章 企业级特性



# 附录 docker启动常用软件的命令

注：一般在启动镜像的时候需要进行文件夹挂载，这样即镜像中产生的数据文件可以在宿主机保存，方便。

## 1 Redis

```bash
docker search redis
docker pull redis:3.2
# 总是开机启动
# 挂载端口,宿主机端口:镜像内端口
# 给容器取名字
# 挂载目录
# 后台启动redis镜像
# 以配置文件方式启动，最终以挂载目录的配置文件启动
docker run --restart=always
			-p 6379:6379
			--name redis_test2
			-v D:/Program/Docker/mount/redis/redis_test/redis_test.conf:/etc/redis/redis.conf
			-v D:/Program/Docker/mount/redis/redis_test/data:/data
			-d redis:3.2
			redis-server /etc/redis/redis.conf
```

redis配置文件

```
# 限制只能本地访问
# bind 127.0.0.1

# 开启保护模式，限制为本地访问，默认 yes
# protected-mode no

#默认no，改为yes意为以守护进程方式启动，可后台运行，除非kill进程，改为yes会使配置文件方式启动redis失败
daemonize no  

# 数据库个数（可选），我修改了这个只是查看是否生效。。
databases 16 

# 输入本地redis数据库存放文件夹（可选）
# dir  ./   

# redis持久化（可选）
# appendonly yes  

# redis 连接密码
# requirepass  123456 
```



> docker run --restart=always -p 6379:6379 --name redis_tinode -v D:/Program/Docker/mount/redis/redis_tinode/redis_tinode.conf:/etc/redis/redis.conf -v D:/Program/Docker/mount/redis/redis_tinode/data:/data -d redis:3.2 redis-server /etc/redis/redis.conf

## 2 MySQL

```
docker pull mysql:8.0
docker run -p 3306:3306
	-e MYSQL_ROOT_PASSWORD=150621
	-e TZ=Asia/Shanghai
	-v D:/Program/Docker/mount/mysql/data:/var/lib/mysql/
	-v D:/Program/Docker/mount/mysql/logs:/var/log/mysql/
	-v D:/Program/Docker/mount/mysql/conf/my.cnf:/etc/mysql/my.cnf
	--name mysql_tinode
	--restart=always
	--privileged=true
	-d mysql:8.0.21
```

配置文件

```

```

> docker run -p 3306:3306 --privileged=true -e MYSQL_ROOT_PASSWORD=150621 -e TZ=Asia/Shanghai -v D:/Program/Docker/mount/mysql/data:/var/lib/mysql/:rw -v D:/Program/Docker/mount/mysql/logs:/var/log/mysql/:rw -v D:/Program/Docker/mount/mysql/conf/:/etc/mysql/ --name mysql_tinode --restart=always -d mysql:8.0.21



## 3 Nacos/nacos-server

```
docker pull nacos/nacos-server:v2.0.4
docker run --name nacos_tinode -e MODE=standalone -p 8848:8848 -p 9848:9848 -p 9849:9849 -d nacos/nacos-server:v2.0.4
```

