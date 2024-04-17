# 第一章 初识 Linux shell



# 第二章 走进 shell

## 2.1 进入命令行

图形化界面之前，与系统交互的唯一方式就是 **文本命令行界面（command line interface，CLI）。** CLI 只能接收文本输入，也只能显示出文本和基本的图形输出



## 2.2 通过Linux控制台终端访问CLI

`Ctrl+Alt+[F1-F7]` 创建并切换虚拟控制台

```shell
setterm -inversescreen on # 交换背景色和前景色
setterm -background white # 设置背景为白色（black red green yellow blue magenta cyan white）
setterm -foreground # 设置前景色
-reset # 恢复默认并清屏
-store # 将当前使用的前景色和背景色存储，以后-reset就是这个设置
```

# 第三章 基本的bash shell 命令

## 3.1 启动shell

`/etc/passwd` 文件包含了系统用户账户以及每个用户的基本配置信息

>  christine:x\:501:501:Christine Bresnahan:/home/christine:/bin/bash

每个条目有七个字段，每个字段用冒号隔开

这个信息是说明，用户christine使用/bin/bash/作为自己的默认shell程序。意味着只要christine登录系统，bash shell就会自动启动



## 3.2 shell 提示符

默认的提示符是 `$`

在Ubuntu Linux 系统上

> christine@server01:~$

在CentOS上

> [christine@server01 ~]$

提示符还可以显示更多信息，[第六章]()降到了如何修改提示符



## 3.3 bash 手册

使用 `man` 命令来访问查询手册

```bash
man ls
```

将 man 命令写在其他命令之前来查找相关命令的使用方法

使用键盘的上下左右，PageUp，PageDown可以操作页面，q退出回到命令行



## 3.4 浏览文件系统

### 3.4.1 Linux文件系统

Window下的文件路径有盘符标记，在哪个盘存储就会有哪个盘的标记

但是在Linux中采用了 **虚拟目录** 的方式，虚拟目录将PC上所有的存储设备的文件路径都纳入单个目录结构中

在Linux PC 上安装的第一块硬盘称为 **根驱动器**。根驱动器包含了虚拟目录的核心，其他目录都是从那里开始构建的。

Linux在根驱动器上创建一些特别的目录，称为 **挂载点（mount point）**。挂载点是虚拟目录中用于分配额外存储设备的目录。虚拟目录会让文件和目录出现在这些挂载点目录中，然而实际上它们却存储在另外一个驱动器中。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/linux/shell/Linux%E6%96%87%E4%BB%B6%E7%BB%93%E6%9E%84.png)

一块硬盘和虚拟目录的根目录（/）关联起来，剩下的硬盘就可以挂载到虚拟目录结构的任何地方

<table>
    <tr>
    	<th>目录</th>
    	<th>用途</th>
    </tr>
    <tr>
    	<td>/</td>
    	<td>根目录，一般不会存储文件</td>
    </tr>
    <tr>
    	<td>/bin</td>
    	<td>二进制目录，通常存放用户级GNU工具</td>
    </tr>
    <tr>
    	<td>/boot</td>
    	<td>启动目录，存放启动文件</td>
    </tr>
    <tr>
    	<td>/dev</td>
    	<td>设备目录，在这里创建设备节点</td>
    </tr>
    <tr>
    	<td>/etc</td>
    	<td>系统配置文件目录</td>
    </tr>
    <tr>
    	<td>/gome</td>
    	<td>主目录，linux在这里创建用户目录</td>
    </tr>
    <tr>
    	<td>/lib</td>
    	<td>库目录，存放系统和应用程序的库文件</td>
    </tr>
    <tr>
    	<td>/media</td>
    	<td>媒体目录，可移动媒体设备常用挂载点</td>
    </tr>
    <tr>
    	<td>/mnt</td>
    	<td>挂载目录，另一个可移动媒体设备的常用挂载点</td>
    </tr>
    <tr>
    	<td>/opt</td>
    	<td>可选目录，存储第三方软件包和数据文件</td>
    </tr>
    <tr>
    	<td>/proc</td>
    	<td>进程目录，存放现有硬件及当前进程相关信息</td>
    </tr>
    <tr>
    	<td>/root</td>
    	<td>root用户主目录</td>
    </tr>
    <tr>
    	<td>/sbin</td>
    	<td>系统二进制目录，存放管理员级别GNU工具</td>
    </tr>
    <tr>
    	<td>/run</td>
    	<td>运行目录，存放系统运行时的临时数据</td>
    </tr>
    <tr>
    	<td>/srv</td>
    	<td>服务目录，存放本地服务的相关文件</td>
    </tr>
    <tr>
    	<td>/sys</td>
    	<td>系统目录，存放系统硬件信息的相关文件</td>
    </tr>
    <tr>
    	<td>/tmp</td>
    	<td>临时目录，可以在该目录中创建和删除临时工作文件</td>
    </tr>
    <tr>
    	<td>/usr</td>
    	<td>用户二进制目录，大量用户级GNU工具和数据文件都存储在这里</td>
    </tr>
    <tr>
    	<td>/var</td>
    	<td>可变目录，用以存放经常变化的文件，比如日志</td>
    </tr>
</table>





### 3.4.2 遍历目录

使用 `cd` 命令切换目录

可使用相对路径和绝对路径

`pwd` 命令显示当前位置的绝对路径



## 3.5 文件和目录列表

### 3.5.1 基本列表功能

`ls` 命令最基本的形式是显示当前目录下的文件和目录

通过配置 LS_COLORS 环境变量（[第六章讲到]()）来控制使不同类型的文件显示不同的颜色

使用参数 `-F` 也可以区分文件和目录

```bash
ls -F
```

可执行文件后面带 `*`，目录后面带 `/`，普通文件什么都没有

```bash
ls -a
```

显示所有文件，包括隐藏文件（以`.`开始的文件）

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ ls -a
> .  ..  .bash_logout  .bash_profile  .bashrc

会有三个带 `.bash` 的文件，他们是 bash shell 环境使用的隐藏文件，详细见[第六章]()

```bash
ls -R
```

`-R` 选项是递归选项，可以列出该目录下子目录内的文件



### 3.5.2 显示长列表

ls 命令使用参数 `-l` 会产生长列表输出

```bash
ls -l
```

> total 20
> drwx------  2 ligen ligen 4096 Apr  5 21:16 .
> drwxr-xr-x. 4 root  root  4096 Apr  5 13:00 ..
> -rw-r--r--  1 ligen ligen   18 Oct 31  2018 .bash_logout
> -rw-r--r--  1 ligen ligen  193 Oct 31  2018 .bash_profile
> -rw-r--r--  1 ligen ligen  231 Oct 31  2018 .bashrc

这样会显示具体的文件信息

- 输出第一行包含总块数
- 文件权限（drwxrwxrwx）
  - 第一个字符：- 代表文件
  - d 目录
  - l 链接
  - c 字符型设备
  - b 块设备
  - n 网络设备
  - 然后分别是 文件属主权限、数组成员权限、其他用户的权限

- 文件的硬链接总数
- 文件属于哪个用户
- 文件属于哪个组
- 文件的大小（字节）
- 文件上次修改时间
- 文件名或目录名



### 3.5.3 过滤输出列表

使用 ls 命令还可以简单进行文件名称过滤

```bash
ls -l  my_script
```

该条命令就会列出文件 `my_script` 的信息，如果后面是目录名称，则会列出该目录中的所有文件，不进行过滤

如果不知道文件的确切名称，还可以进行模式匹配，支持正则过滤

- ？代表一个字符
- \* 代表零个或多个字符

```bash
ls -l my_sc?ript
ls -l my*
```



## 3.6 处理文件

### 3.6.1 创建文件

使用 `touch` 命令创建文件

```bash
touch test_name
```

touch 命令创建的文件，将创建者作为归属者

如果文件已经存在，运行上面的命令会改变文件的修改时间，但实际没有改变其内容

`ls -l` 显示的是修改时间，不是访问时间

如果要修改访问时间，使用

```bash
touch -a test_name
```

### 3.6.2 复制文件

`cp` 命令进行复制文件

```bash
cp source destination
```

destination 是目录，则source 文件被复制到该目录

destiantion 是具体文件名称，则新文件以该名称命名

使用 cp 命令复制的过程中，系统并不会提醒目标目录是否存在同名文件，为了安全起见，可以使用参数

```bash
cp -i source destination
```

该命令会询问是否覆盖已有文件

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ cp -i a aa/b
> cp: overwrite ‘aa/b’?

同样对于文件夹的复制可以使用递归选项`-R`

```bash
cp -R source destiantion
```

如果 destiantion 目录不存在，会创建该目录

使用 `-i` 参数可以逐个询问是否覆盖同名文件

源文件和目的文件都可以使用通配符

### 3.6.3 制表键自动补全



### 3.6.4 链接文件

如果需要在系统上维护同一文件的两份或多份副本，除了保存多份单独的物理文件副本之外，还可以采用保存一份物理文件副本和多个虚拟副本的方法。这种方式称为 **链接**

Linux 中包括两种文件链接：

- 符号链接
- 硬链接

**符号链接** 就是一个实实在在的文件，它指向存放在虚拟目录结构中某个地方的另一个文件，这两个通过符号链接在一起的文件，彼此的内容并不相同。

如果要创建链接文件，需要源文件存在

```bash
ln -s source destination
```

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ ll
> total 0
> -rw-rw-r-- 1 ligen ligen 0 Apr  5 22:13 a
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ ln -s a b
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]$ ll
> total 0
> -rw-rw-r-- 1 ligen ligen 0 Apr  5 22:13 a
> lrwxrwxrwx 1 ligen ligen 1 Apr  5 22:14 b -> a

如上面的输出所显示，b 文件原先不存在，创建链接后，b文件出现

接下来给 a 文件写入一些内容

> input into file a
>
>  
>
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ vim a
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ cat a
> input into file a
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]$ cat b
> input into file a

我们可以看到 b 的内容也改变了

接下来向 b 中输入一些内容

> input into file b
>
>  
>
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ vim b
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ cat b
> input into file a
> input into file b
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]$ cat a
> input into file a
> input into file b

可以看到 a 中的内容也跟随改变了

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]$ ll
> total 4
> -rw-rw-r-- 1 ligen ligen 36 Apr  5 22:18 a
> lrwxrwxrwx 1 ligen ligen  1 Apr  5 22:14 b -> a

最终可以看到，源文件变成了36字节，链接文件的大小并没有改变



**硬链接** 会创建独立的虚拟文件，其中包含了原始文件的信息及位置。但是它们从根本上而言是同一个文件。引用硬链接文件等同于引用了源文件

创建硬链接也需要源文件存在，创建硬链接不需要加参数

```bash
ls source destination
```

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ touch a
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ ll
> total 0
> -rw-rw-r-- 1 ligen ligen 0 Apr  5 22:22 a
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ ln a b
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ ll
> total 0
> -rw-rw-r-- 2 ligen ligen 0 Apr  5 22:22 a
> -rw-rw-r-- 2 ligen ligen 0 Apr  5 22:22 b
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]$ 

如上面的方式创建硬链接

接下来修改文件试试

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ ll
> total 0
> -rw-rw-r-- 2 ligen ligen 0 Apr  5 22:22 a
> -rw-rw-r-- 2 ligen ligen 0 Apr  5 22:22 b
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ vi a
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]$ cat a
> input into file a
>
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]$ cat b
> input into file a
>
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ vi b
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]$ cat b
> input into file a
> input into file b
>
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]$ cat a
> input into file a
> input into file b
>
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]\$ ll
> total 8
> -rw-rw-r-- 2 ligen ligen 37 Apr  5 22:24 a
> -rw-rw-r-- 2 ligen ligen 37 Apr  5 22:24 b
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z aa]$ 

可以看到修改两个文件，两个文件的内容都会关联变化

**注意区别：**

- 符号链接相当于windows中的快捷方式，无论怎么修改文件内容，这个快捷方式的大小是不会变化的
- 符号链接中删除源文件，会使得符号链接不可用；重新创建同名文件又会可用，但是内容不同了
- 硬链接相当于一份拷贝，不同于其他拷贝的是，这个内容是相关联的
- 硬链接中删除源文件，会使得链接彻底断开，新建的硬链接文件是可用的

### 3.6.5 重命名文件

使用 `mv` 命令可以移动文件（夹）或重命名

```bash
mv source destination
```



### 3.6.6 删除文件

使用 `rm` 命令删除文件或者文件夹

```bash
rm source
rm -i # 询问是否删除
rm -r # 递归删除
rm -f # 暴力删除
```



## 3.7 处理目录

### 3.7.1 创建目录

使用 `mkdir` 命令创建目录

```bash
mkdir newDir
```

假设需要创建一个多级目录

> mkdir newDir/sunDir/underDir

这个操作会报错，需要使用 `-p` 参数，该参数会自动创建不存在的父级目录



### 3.7.2 删除目录

使用 `rmdir` 删除目录

```bash
rmdir oldDir
```

默认情况下该命令只能删除空目录，包含文件的目录是不能被删除的

必须先删除文件夹中的文件才行



## 3.8 查看文件内容



### 3.8.1 查看文件类型

使用 `file` 命令查看文件的类型

```bash
file file_name
```

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ file aa
> aa: ASCII text
>
>  
>
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ file aaa
> aaa: directory

还可以展示出链接文件、脚本文件、二进制文件类型



### 3.8.2 查看整个文件

使用 `cat` 命令查看文件内容

```bash
cat file_name
-n # 显示行号
-b # 只给有文本的行加行号
-T # 不显示制表符
```

使用 `more` 命令查看

该命令可以分页，一页一页的查看

使用 `less` 命令查看

less命令比more更高级一些，可以执行翻页，搜索等功能



### 3.8.3 查看部分文件

使用 `tail` 命令显示文件最后几行的内容

```bash
tail file_name
```

参数 `-n num` 设定显示最后 num 行

参数 `-f` 动态显示文件内容

比如日志文件是不断变化的，使用`-f`命令查看的时候，就可以看到不断添加到文件中的内容



使用 `head` 命令查看文档前几行

```bash
head file_name
```

默认情况下，显示10行

使用参数 `-n num` 设定为前 num 行



# 第四章 更多bash shell命令

## 4.1 监测程序



### 4.1.1 探查进程

使用 `ps` 命令查看进程

```bash
ps
```

默认情况下，显示进程的PID，运行在哪个终端（TTY）等等

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ ps
> PID TTY          TIME CMD
> 12684 pts/0    00:00:00 bash
> 12805 pts/0    00:00:00 ps
>
>  
>
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ ps
>   PID TTY          TIME CMD
> 16709 pts/1    00:00:00 bash
> 16731 pts/1    00:00:00 ps

Linux系统中使用GNU ps命令支持3种不同的类型的命令行参数：

- Unix风格的参数，加单破折线
- BSD风格的参数，前面不加破折线
- GNU风格的参数，加双破折线

**1. Unix风格的参数**

常用的参数

<table>
    <tr>
    	<th>参数</th>
        <th>描述</th>
    </tr>
    <tr>
    	<td>-A</td>
    	<td>显示所有进程</td>
    </tr>
    <tr>
    	<td>-a</td>
    	<td>显示除控制进程外和无终端进程外的所有进程</td>
    </tr>
    <tr>
    	<td>-N</td>
    	<td>显示与指定参数不符的所有进程</td>
    </tr>
    <tr>
    	<td>-e</td>
    	<td>显示所有进程</td>
    </tr>
    <tr>
    	<td>-U userlist</td>
    	<td>显示属主的用户ID在userlist列表中的进程</td>
    </tr>
    <tr>
    	<td>-u userlist</td>
    	<td>显示有效用户ID在userlist中的进程</td>
    </tr>
    <tr>
    	<td>-F</td>
    	<td>显示更多额外输出信息</td>
    </tr>
    <tr>
    	<td>-f</td>
    	<td>显示完整格式的输出</td>
    </tr>
    <tr>
    	<td>-p pidlist</td>
    	<td>显示PID在pidlist中的进程</td>
    </tr>

如果想要查看系统上运行的所有进程，可用 `-ef` 参数组合

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ ps -ef
> UID        PID  PPID  C STIME TTY          TIME CMD
> root         1     0  0 Apr05 ?        00:00:03 /usr/lib/systemd/systemd --switched-root --system --des
> root         2     0  0 Apr05 ?        00:00:00 [kthreadd]
> root         3     2  0 Apr05 ?        00:00:01 [ksoftirqd/0]
> root         5     2  0 Apr05 ?        00:00:00 [kworker/0:0H]

- UID：启动这些进程的用户
- PID：进程的进程ID
- PPID：父进程的进程号
- C：进程生命周期中CPU利用率
- STIME：进程启动时的系统时间
- TTY：进程启动时的终端设备
- TIME：运行进程需要的累计CPU时间
- CMD：启动程序的名称

如果需要更多的信息可以使用 `-l`

**2. BSD风格参数**

**3. GNU长参数**

<table>
    <tr>
    	<th>参数</th>
    	<th>描述</th>
    </tr>
    <tr>
    	<td>--forest</td>
    	<td>层级结构显示父子进程的关系</td>
    </tr>
</table>

### 4.1.2 实时监测进程

ps命令只能查看系统在某个时间点的状态，`top` 命令可以进行一个实时监测

- NI：线程谦让度
- PR：进程优先级
- VIRT：进程占用虚拟内存总量
- RES：进程占用的物理内存总量
- SHR：进程和其他进程共享内存的内存总量
- S：进程状态（D可中断的休眠，R运行状态，S休眠状态，T跟踪状态和停止，Z僵化状态）



### 4.1.3 结束进程

Linux进程信号

<table>
    <tr>
    	<th>信号</th>
    	<th>名称</th>
    	<th>描述</th>
    </tr>
    <tr>
    	<td>1</td>
    	<td>HUP</td>
    	<td>挂起</td>
    </tr>
    <tr>
    	<td>2</td>
    	<td>INT</td>
    	<td>中断</td>
    </tr>
    <tr>
    	<td>3</td>
    	<td>QUIT</td>
    	<td>结束运行</td>
    </tr>
    <tr>
    	<td>9</td>
    	<td>KILL</td>
    	<td>无条件终止</td>
    </tr>
    <tr>
    	<td>11</td>
    	<td>SEGV</td>
    	<td>段错误</td>
    </tr>
    <tr>
    	<td>15</td>
    	<td>TERM</td>
    	<td>尽可能终止</td>
    </tr>
    <tr>
    	<td>17</td>
    	<td>STOP</td>
    	<td>无条件停止，但不终止</td>
    </tr>
    <tr>
    	<td>18</td>
    	<td>TSTP</td>
    	<td>停止或暂停，但继续在后台运行</td>
    </tr>
    <tr>
    	<td>19</td>
    	<td>CONT</td>
    	<td>在STOP或TSTP之后恢复执行</td>
    </tr>
</table>

**1. kill命令**

`kill`命令可以通过进程ID给进程发信号，默认情况下，kill命令会向命令行中列出的全部PID发送一个TERM信号

要发送进程信号，必须是进程的属主或者root用户



**2. killall命令**

该命令可以通过进程名称匹配来接触进程

```bash
killall http*
```

这会结束所有以http开头的进程





## 4.2 监测磁盘空间

### 4.2.1 挂载存储媒体

Linux文件系统将所有的磁盘都并入一个虚拟目录下。在使用新的存储媒体之前，需要把它放到虚拟目录下，这项工作称为挂载

图形化管理环境下，大多数发行版都能自动挂载

如果不支持自动挂载，就必须手动完成

**1. mount命令**

```bash
mount
```



> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ mount
> sysfs on /sys type sysfs (rw,nosuid,nodev,noexec,relatime)
> proc on /proc type proc (rw,nosuid,nodev,noexec,relatime)
> devtmpfs on /dev type devtmpfs (rw,nosuid,size=930568k,nr_inodes=232642,mode=755)
> securityfs on /sys/kernel/security type securityfs (rw,nosuid,nodev,noexec,relatime)
> tmpfs on /dev/shm type tmpfs (rw,nosuid,nodev)

`mount` 命令提供下面四部分信息：

- 媒体的设备文件名
- 媒体挂载到虚拟目录的挂载点
- 文件系统类型
- 已挂载媒体的访问状态

手动挂载的基本命令：

```bash
mount -t type device directory
```

type参数制定了磁盘被格式化的文件系统类型。

- vfat：windows长文件系统
- ntfs：Windos 10 以下广泛使用
- iso9660：标准CD-ROM文件系统

大多数U盘会格式化为vfat

手动将U盘 `/dev/sdb1` 挂载到 `media/disk` 

```bash
mount -t vfat /dev/sdb1 /media/disk
```

媒体设备挂载到了虚拟目录，root用户就有了对该设备的所有访问权限



**2. umount命令**

移除卸载

### 4.2.2 使用df命令

`df` 命令可以查看挂载磁盘的使用空间

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ df
> Filesystem     1K-blocks    Used Available Use% Mounted on
> devtmpfs          930568       0    930568   0% /dev
> tmpfs             941024       0    941024   0% /dev/shm
> tmpfs             941024     520    940504   1% /run
> tmpfs             941024       0    941024   0% /sys/fs/cgroup
> /dev/vda1       41147472 8486432  30757548  22% /
> tmpfs             188208       0    188208   0% /run/user/0
> tmpfs             188208       0    188208   0% /run/user/1001

命令输出：

- 设备的设备文件位置
- 能容纳多少个1024字节大小的块
- 已经使用的块
- 还有多少可用
- 已用空间比例
- 设备挂载在哪个挂载点上

使用 `-h` 参数按照易读方式输出

### 4.2.3 使用du命令

## 4.3 处理数据文件

### 4.3.1 排序数据

`sort` 命令进行字典序排序输出

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]\$ cat aa
> one
> two
> three
> 3
> 33
> 1
> 4
> 2
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ sort aa
> 1
> 2
> 3
> 33
> 4
> one
> three
> two

使用 `-n` 参数可以将数字字符识别成数字，按照数字来排序

使用 `-M` 参数按照月份排序

使用 `-k` 参数或者 `--key=pos1,pos2`，排序从pos1位置开始，到pos2结束

使用 `-t` 参数指定一个区分键位置的字符

> $ sort -t ':' -k 3 -n /etc/passwd

使用`:`分割，按照第3个位置进行排序

使用 `-r` 参数进行倒序（降序）输出

### 4.3.2 搜索数据

需要在一个大文件中查找数据的时候，可以使用 `grep` 命令来进行搜索

```bash
grep [option] pattern [file]
```

grep 命令会在输入或指定的文件中查找包含匹配指定模式的字符的行

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]\$ cat aa
> one
> two
> three
> 3
> 33
> 1
> 4
> 2
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]\$ grep 3 aa
> 3
> 33
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ grep [0-9] aa
> 3
> 33
> 1
> 4
> 2

使用 `-v` 参数可以进行反向输出（将匹配到的过滤掉）

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ grep -v [0-9] aa
> one
> two
> three

使用 `-n` 参数显示匹配到的行的行号

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]\$ cat -n aa
>      1	one
>      2	two
>      3	three
>      4	3
>      5	33
>      6	1
>      7	4
>      8	2
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ grep -n [0-9] aa
> 4:3
> 5:33
> 6:1
> 7:4
> 8:2

使用 `-c` 参数输出有多少行含有匹配模式

使用 `-e` 参数匹配多个匹配模式

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ grep -e 1 -e 3 aa
> 3
> 33
> 1

### 4.3.3 压缩数据

linux 文件压缩工具

<table>
    <tr>
    	<th>工具</th>
    	<th>文件扩展名</th>
    	<th>描述</th>
    </tr>
    <tr>
    	<td>bzip2</td>
    	<td>.bz2</td>
    	<td>采用Burrows-Wheeler块排序文本压缩算法和霍夫曼编码</td>
    </tr>
    <tr>
    	<td>compress</td>
    	<td>.Z</td>
    	<td>最初的Unix文件压缩工具</td>
    </tr>
    <tr>
    	<td>gzip</td>
    	<td>.gz</td>
    	<td>GNU压缩工具，用Lempel-Ziv编码</td>
    </tr>
    <tr>
    	<td>zip</td>
    	<td>.zip</td>
    	<td>Windows上PKZIP工具的Unix实现</td>
    </tr>
</table>

gzip是现在linux下的最流行的压缩软件

- gzip：用来压缩文件
- gzcat：用来查看压缩过的文本文件内容
- gunzip：用来解压文件

### 4.3.4 归档数据

使用最广泛的归档工具是 `tar`

```bash
tar function [options] object1 object2 ...
```

function 参数定义了 tar 命令应该做什么

<table>
    <tr>
    	<th>功能</th>
    	<th>长名称</th>
    	<th>描述</th>
    </tr>
    <tr>
    	<td>-A</td>
    	<td>--concatenate</td>
    	<td>将一个已有的tar归档文件追加到另一个</td>
    </tr>
    <tr>
    	<td>-c</td>
    	<td>--create</td>
    	<td>创建一个新的归档文件</td>
    </tr>
    <tr>
    	<td>-d</td>
    	<td>--diff --delete</td>
    	<td>检查归档文件和文件系统的不同之处 追加文件到已有tar归档文件末尾</td>
    </tr>
    <tr>
    	<td>-r</td>
    	<td>--append</td>
    	<td>追加文件到已有tar归档文件末尾</td>
    </tr>
    <tr>
    	<td>-t</td>
    	<td>--list</td>
    	<td>列出已有tar归档文件的内容</td>
    </tr>
    <tr>
    	<td>-u</td>
    	<td>--update</td>
    	<td>将比tar归档文件中已有的同名文件新的文件追加到该tar归档文件中</td>
    </tr>
    <tr>
    	<td>-x</td>
    	<td>--extract</td>
    	<td>从已有tar归档文件中提取文件</td>
    </tr>
    <tr>
    	<td>-C dir</td>
    	<td></td>
    	<td>切换到指定目录</td>
    </tr>
    <tr>
    	<td>-f file</td>
    	<td></td>
    	<td>输出结果到文件或设备file</td>
    </tr>
    <tr>
    	<td>-v</td>
    	<td></td>
    	<td>在处理文件时显示文件</td>
    </tr>
    <tr>
    	<td>-z</td>
    	<td></td>
    	<td>将输出重定向给gzip命令来压缩内容</td>
    </tr>
</table>

> tar -cvf test.tar test1/ test2/

上面的命令创建了一个名为test.tar的归档文件，含有test1和test2目录

> tar -tf test.tar

上面的命令列出了tar文件test.tar的内容，但没有解压缩

>  tar -xvf test.tar

上面的命令从tar中提取内容



# 第五章 理解Shell

## 5.1 shell的类型

## 5.2 shell的父子关系

使用 `bash` 命令可以创建一个子shell，但是没有任何提示，可以先使用 `ps -f` 先输出当前线程，再启动bash，再输出线程，就能看到差异

### 5.2.1 进程列表

```bash
pwd;ls;cd /etc
```

使用 `;` 分隔连续的命令，这些命令会依次执行

进程列表即使用括号将这些命令括起来，使这些命令在子shell中执行

```bash
(pwd;ls;cd /etc)
```

### 5.2.2 别出心裁的子shell用法

**1. 探索后台模式**

`sleep` 命令接受一个参数，该参数是你希望进程等待（睡眠）的秒数。这个命令通常在脚本中引入一段时间的暂停，单位秒

```
sleep 10
```

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]\$ sleep 5&
> [1] 19803
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ ps -f
> UID        PID  PPID  C STIME TTY          TIME CMD
> ligen    19731 19730  0 21:56 pts/0    00:00:00 -bash
> ligen    19803 19731  0 22:32 pts/0    00:00:00 sleep 5
> ligen    19804 19731  0 22:32 pts/0    00:00:00 ps -f

这会使这个段等待到后台执行，并返回PID

`jobs` 命令显示后台的进程（作业）

参数 `-l` 还能够显示PID信息

**2. 将进程列表置入后台**

**3. 协程**

## 5.3 理解shell的内建命令

### 5.3.1 外部命令

### 5.3.2 内建命令



# 第六章 使用Linux环境变量

## 6.1 什么是环境变量

全局环境变量对所有的shell和子shell都是可见的

局部变量只对创建它们的shell可见

### 6.1.1 全局环境变量

系统环境变量基本上都是使用全大写字母，以区别于普通用户的环境变量

使用 `env` 或者 `printenv` 命令查看全局变量

如果只想查看某个环境变量的值

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ printenv HOME
> /home/ligen

使用 `echo` 也可以查看环境变量的值，需要在变量之前加一个 `$` 符号

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]\$ echo $HOME
> /home/ligen

本质的是 `$` 就相当于取值

### 6.1.2 局部环境变量

局部环境变量只能在定义它们的进程中可见。

使用 `set` 命令可以看到全局变量、局部环境变量和用户自定义变量



## 6.2 设置用户自定义变量

### 6.2.1 设置局部用户定义变量

直接使用 `=` 进行环境变量赋值

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]\$ echo $test
>
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]\$ test=ligen
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]\$ echo $test
> ligen

如果环境变量中包含空格，需要使用单引号

**这样的设置方式只是局部变量，新开一个shell就不能使用了**

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]\$ bash
> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]\$ echo $test



### 6.2.2 设置全局环境变量

设置全局环境变量的方法是先设置一个局部环境变量，再使用`export`命令来将该变量导出到全局变量中

在导入环境变量的时候不需要使用 `$`

> export ligen

## 6.3 删除环境变量

使用 `unset` 命令删除环境变量，不要使用 `$`

如果在子进程中删除了一个全局环境变量，这个全局环境变量在父进程中依然可用



## 6.4 默认的shell环境变量





## 6.5 设置PATH环境变量

PATH环境变量就是在使用外部命令时，shell进行命令查找的目录

在windows下也有同样的目录

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]\$ echo $PATH
> /usr/local/bin:/usr/bin:/usr/local/sbin:/usr/sbin:/home/ligen/.local/bin:/home/ligen/bin

不同的路径之间使用 `:` 进行隔离

如果要添加新的PATH环境变量，可以使用 `:` 直接拼接

```bash
PATH=$PATH:/home/ligen/bin
```

如果希望子shell也能使用这个新路径，那么需要进行导出



对PATH变量的修改只能持续到退出或重启系统

下一节永久修改

## 6.6 定位系统环境变量

bash shell的三种启动方式：

- 登录时作为默认登录的shell
- 作为非登录shell的交互式shell
- 作为运行脚本的非交互shell

### 6.6.1 登录shell

登录Linux系统时，bash shell会作为登录shell启动，从5个不同的启动文件里读取命令：

- /etc/profile
- $HOME/.bash_profile
- $HOME/.bashrc
- $HOME/.bash_login
- $HOME/.profile

### 6.6.2 交互式shell进程

如果shell不是在登录时启动的，比如使用 bash 命令启动，这种叫做交互式shell

**交互式shell不会访问 /etc/profile，只会检查用户HOME目录中的.bashrc**



### 6.6.3 非交互式shell

系统执行shell脚本时的shell

BASH_ENV 变量，启动非交互式shell进程时，它就会检查这个环境变量来查看要执行的启动文件。如果有指定的文件，shell会执行该文件里的命令



那如果BASH_ENV变量没有设置，shell脚本到哪里去获得它们的环境变量呢？别忘了有些 shell脚本是通过启动一个子shell来执行的（参见第5章）。子shell可以继承父shell导出过的变量。 举例来说，如果父shell是登录shell，在/etc/profile、/etc/profile.d/*.sh和$HOME/.bashrc文件中 设置并导出了变量，用于执行脚本的子shell就能够继承这些变量。 要记住，由父shell设置但并未导出的变量都是局部变量。子shell无法继承局部变量。 对于那些不启动子shell的脚本，变量已经存在于当前shell中了。所以就算没有设置 BASH_ENV，也可以使用当前shell的局部变量和全局变量



### 6.6.4 环境变量持久化

对全局环境变量来说（Linux系统中所有用户都需要使用的变量），可能更倾向于将新的或修 改过的变量设置放在/etc/profile文件中，但这可不是什么好主意。如果你升级了所用的发行版， 这个文件也会跟着更新，那你所有定制过的变量设置可就都没有了。 

最好是在/etc/profile.d目录中创建一个以.sh结尾的文件。把所有新的或修改过的全局环境变量设置放在这个文件中。 在大多数发行版中，存储个人用户永久性bash shell变量的地方是\$HOME/.bashrc文件。这一 点适用于所有类型的shell进程。但如果设置了BASH_ENV变量，那么记住，除非它指向的是 $HOME/.bashrc，否则你应该将非交互式shell的用户变量放在别的地方。



## 6.7 数组变量



# 第七章 理解Linux文件权限

## 7.1 Linux 的安全性

用户权限是通过创建用户时分配的用户ID来跟踪的，UID是数值，每个用户都有唯一的UID，但在登录时用的不是UID，而是登录名。登录名是用户用来登录系统的最长八字符的字符串，同时会关联一个对应的密码。

### 7.1.1 /etc/passwd 文件

Linux使用 `/etc/passwd` 文件来保存登录名和对应的UID

Linux被分配的UID是0

Linux系统会为各种各样的功能创建不同的用户账户，这些账户不是真正的用户。这都是系统账户，用来在系统上运行各种服务进程



/etc/passwd 中包含了如下信息：

- 登录用户名
- 用户密码
- 用户UID
- 用户组ID
- 用户账户的文本描述
- 用户HOME目录位置
- 用户默认shell

### 7.1.2 /etc/shadow 文件

`/etc/shadow` 文件对Linux系统密码管理提供了更多控制

只有root用户可以访问

每个用户都会被保存一条

```
ligen:$6$2vbJ9bMW$hOJUIgPti7TtrfwSloElyVUvcISwcGG2JrQXmVvkxvBbTdk4R68/HmNDcdlPvIi0oBDBJAKtoyIu9Yz1IYC7k/:19087:0:99999:7:::
```

包含九个字段：

- 与/etc/passwd文件中的登录名字段对应的登录名
- 加密后的密码
- 自上次修改密码后过去的天数密码
- 多少天后才能更改密码
- 多少天后必须更改密码
- 密码过期前提前多少天提醒用户更改密码
- 密码过期后多少天禁用用户账户
- 用户账户被禁用日期
- 预留字段给将来使用

### 7.1.3 添加新用户

使用 `useradd` 命令向系统中添加新用户，可以一次性创建新用户账户及设置用户HOME目录结构。useradd命令使用系统的默认值以及命令行参数来设置用户账户，这个系统默认值被设置在/etc/default/useradd。可以使用 `-D` 参数来查看

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ useradd -D
> GROUP=100
> HOME=/home
> INACTIVE=-1
> EXPIRE=
> SHELL=/bin/bash
> SKEL=/etc/skel
> CREATE_MAIL_SPOOL=yes

- 新用户被添加到GID为100的公共组
- 新用户的HOME目录将会位于/home/loginame
- 新用户账户密码在过期后不会被禁用
- 新用户账户未设置过期日期
- 新用户账户将bash shell作为默认shell
- 系统会将/etc/skel目录下的内容复制到用户的HOME目录下
- 系统为该用户账户在mail目录下创建一个用于接收邮件的文件

<table>
    <tr>
    	<th>参数</th>
    	<th>描述</th>
    </tr>
    <tr>
    	<td>-d</td>
    	<td>为主目录指定一个名称</td>
    </tr>
    <tr>
    	<td>-g initial_group</td>
    	<td>指定用户登录组GID或组名</td>
    </tr>
    <tr>
    	<td>-G group ...</td>
    	<td>指定除登录组之外的附加组</td>
    </tr>
    <tr>
    	<td>-r</td>
    	<td>创建系统账户</td>
    </tr>
    <tr>
    	<td>-p passwd</td>
    	<td>指定用户账户的默认密码</td>
    </tr>
    <tr>
    	<td>-s shell</td>
    	<td>指定默认登录shell</td>
    </tr>
    <tr>
    	<td>-u</td>
    	<td>为账户指定唯一的UID</td>
    </tr>
</table>

修改useradd的默认参数，使用 -D 参数后跟随下表参数

<table>
    <tr>
    	<th>参数</th>
    	<th>描述</th>
    </tr>
    <tr>
    	<td>-b default_home</td>
    	<td>更改默认HOME目录</td>
    </tr>
    <tr>
    	<td>-e expiration_date</td>
    	<td>更改默认过期日期</td>
    </tr>
    <tr>
    	<td>-f inactive</td>
    	<td>更改默认密码到期禁用天数</td>
    </tr>
    <tr>
    	<td>-g group</td>
    	<td>更改默认组名或GID</td>
    </tr>
    <tr>
    	<td>-s shell</td>
    	<td>更改默认登录shell</td>
    </tr>
</table>

### 7.1.4 删除用户

如果要删除用户，使用 `userdel` 命令，只会从 /etc/passwd 中删除用户信息，但不会删除任何属于该账户的文件

如果加上 `-r` 参数，userdel 会删除用户的 HOME 目录以及邮件目录



### 7.1.5 修改用户

用户账户修改工具

<table>
    <tr>
    	<th>命令</th>
    	<th>描述</th>
    </tr>
    <tr>
    	<td>usermod</td>
    	<td>修改用户账户的字段，指定主要组以及附加组的所属关系</td>
    </tr>
    <tr>
    	<td>passwd</td>
    	<td>修改已有账户密码</td>
    </tr>
    <tr>
    	<td>chpasswd</td>
    	<td>从文件中读取登录密码对，并更新密码</td>
    </tr>
    <tr>
    	<td>chage</td>
    	<td>修改密码的过期日期</td>
    </tr>
    <tr>
    	<td>chfn</td>
    	<td>修改账户的备注信息</td>
    </tr>
    <tr>
    	<td>chsh</td>
    	<td>修改用户的默认登录shell</td>
    </tr> 
</table>

**1. usermod**

修改 /etc/passwd 中的字段

- -l 修改用户账户登录名
- -L 锁定账户，使用户无法登录
- -p 修改账户密码
- -U 解除锁定，使用户能够登录

**2. passwd和chpasswd**

```shell
passwd test
```

如果像上面指定要修改哪个用户的密码，只有root用户才可以办到。

不指定任何用户就是修改自己的密码

`-e` 参数可以强制用户下次登录时修改密码



chpasswd 可以从文件中批量读取登录名和密码对，进行修改。登录名和密码对用 `:` 分割

**3. chsh、chfn和chage**

## 7.2 使用Linux组

组内用户共享权限

### 7.2.1 /etc/group

组信息保存在 `/etc/group` 中

> [ligen@iZm5e7k8oio9ii1nmiq6n8Z ~]$ cat /etc/group | grep ligen
> ligen:\x:1001:
> ligen1:\x:1002:
> ligen2:\x:1003:

有四个字段：

- 组名
- 组密码
- GID
- 属于该组的用户列表

### 7.2.2 创建新组

`groupadd` 命令在系统上创建新组

默认创建新组，默认没有用户被分配到该组。然后使用 `usermod` 命令将用户移动到组

```shell
usermod -G test ligen1
```

将用户 ligen1 添加到组 test

### 7.2.3 修改组

`groupmod` 修改组

参数 `-n` 修改组名

```shell
groupmod test -n test1 
```

将组 test 改为 test1

参数 `-g` 修改GID

```shell
groupmod test -g 465465
```

## 7.3 理解文件权限

### 7.3.1 使用文件权限符

```shell
-rwxrwxr-x ligen ligen test
```

其中第一组rwx属于用于ligen的权限，ligen拥有读写执行权限

第二组rwx属于组ligen的权限，在ligen组的用户可以读写执行

第三组r-x属于其他用户权限，不在ligen组且不是用户ligen的权限



### 7.3.2 默认文件权限

`umask` 不太好用

## 7.4 改变安全性设置



### 7.4.1 改变权限

`chmod` 命令用来改变文件和目录的安全性设置，该命令格式如下：

```shell
chmod options mode file
```

可以直接使用 chmod 777 filename 来直接赋予权限

### 7.4.2 改变所属关系

`chown` 改变文件的属主，`chgrp` 改变文件属组

```shell
chown options owner[.group] file
```

> chown ligen1 aa

将aa文件的属主变为ligen1

> chgrp test aa

将aa文件的属组变为test

## 7.5 共享文件



# 第八章 管理文件系统



# 第九章 安装软件程序

## 9.3 基于Red Hat 的系统

- yum：在Red Hat和Fedora中使用
- urpm：在Mandriva中使用
- zypper：在openSUSE中使用

### 9.3.1 列出已安装包

列出已安装列表

```bash
yum list installed
```

列出某软件的镜像版本信息

```bash
yum list python
```

列出是否安装某软件

```bash
yum list installed python
```

找出系统上某个软件属于什么软件包

```bahs
yum provides file_name
```

### 9.3.2 用yum安装软件

从软件包仓库下载安装软件，并且会自动下载所依赖的软件

```bash
yum install package_name
```

也可以手动下载rpm包，进行手动安装

```bash
yum localinstall package_name.rpm
```

### 9.3.3 用 yum 更新软件

```bash
yum list updates # 列出需要更喜你的软件
yum update package_name
yum update
```

## 9.3.4 用yum卸载软件

```bash
yum remove package_name # 只删除软件包而保留配置文件和数据文件
yum erase package_name
```

### 9.3.5 处理损坏的包依赖关系

有时候在安装多个软件包时，某个包的依赖关系可能会被另一个包的安装覆盖掉，这叫做损坏的包依赖关系

可以先尝试调用

```
yum clean all
```

然后试着用

```
yum update
```

只要清理了放错位置的文件就可以了



如果还不能结局，试下面的命令

```bash
yum deplist package_name
```

这个命令显示了所有包的依赖关系以及什么软件可以提供这些库依赖关系。知道了它需要哪个依赖包，就可以安装了

这样如果还没有解决，可以使用下面的命令

```bash
yum update --skip-broken
```

允许忽略依赖关系损坏的那个包，去继续更新其他软件包



### 9.3.6 yum软件仓库

列出正在从哪些仓库中获取软件

```bash
yum repolist
```

如果仓库中没有需要的软件，可以编辑一下配置文件。yum仓库定义文件位于 /etc/yum.repos.d。需要添加正确的URL，并且获得必要的加密密钥

## 9.4 从源码安装



# 第十章 使用编辑器

## 10.1 vim编辑器

### 10.1.1 检查vim软件包

### 10.1.2 vim基础

vim有两种模式：

- 普通模式
- 插入模式

刚打开vim编辑文件的时候是普通模式，在普通模式按键会将按键解释成命令

在插入模式下，vim会将你在当前光标位置输入的每个键都插入到缓冲区

**光标移动：**

- 在普通模式中使用方向键或者h,j,k,l（对应上下左右）可以进行光标移动

- PageDown（Ctrl+F）下翻一屏
- PageUp（Ctrl+B）上翻一屏
- G：移动到缓冲区的最后一行
- num G：移动到缓冲区的第num行
- gg：移动到缓冲区第一行

使用 `:` 进入命令行模式：

- q：未修改，直接退出
- q!：取消所有修改并退出
- w filename：另存为
- wq：保存退出

### 10.1.3 编辑数据

- x：删除当前光标所在位置的字符
- dd：删除当前光标所在行
- dw：删除当前光标所在位置的单词
- d$：删除当前光标所在位置到行尾
- J：删除当前光标所在行行尾的换行符
- u：撤销前一编辑命令
- a：当前光标后追加数据
- A：当前光标行尾追加数据
- r char：用char替换当前光标所在位置的单个字符
- R text：用text覆盖当前光标所在位置的数据，直到按下ESC

可以用数字来重复执行命令

- 2x：即代表执行两次x命令

### 10.1.4 复制和粘贴

使用删除命令删除的字符，可以通过 `p` 命令进行粘贴

复制功能：

- yw：复制单词
- y$：复制到行尾

### 10.1.5 查找和替换

查找时，使用 `/` 命令，在后面跟上要查找的字符

按 n 键继续查找

替换命令`：`

- s/old/new：用new替换old第一次出现的地方
- s/old/new/g：一行命令替换所有old
- n,m s/old/new/g：替换行号n和m之间所有old
- %s/old/new/g：替换你整个文件中所有old
- %s/old/new/gc：替换你整个文件中所有old，每次都会出现提示



# 第十一章 构建基本脚本

## 11.1 使用多个命令

使用分号将两个命令写在同一行

> [root@iZm5e7k8oio9ii1nmiq6n8Z ~]# date;who
> Tue Apr 26 15:56:11 CST 2022
> root     pts/0        2022-04-26 15:18 (222.173.116.150)
> [root@iZm5e7k8oio9ii1nmiq6n8Z ~]# date
> Tue Apr 26 15:56:20 CST 2022
> [root@iZm5e7k8oio9ii1nmiq6n8Z ~]# who
> root     pts/0        2022-04-26 15:18 (222.173.116.150)

## 11.2 创建shell脚本文件

先创建一个文本文件，并且在文件开头指定要使用的shell

> #!/bin/bash

通常在脚本中使用 `#` 做注释；注释不会被处理，但是第一行是例外，用来指定用什么shell执行脚本

> #!/bin/bash
>
> date
>
> who

要执行脚本，可以将脚本所在目录加入环境变量，或者使用相对或绝对路径来执行

还需要给脚本赋予可执行权限才可以



## 11.3 显示消息

shell命令产生的输出，会显示在脚本所运行的控制台显示器上

通过 `echo` 命令打印输出

要打印的文本可以不用引号括起来

如果要打印的文本中有引号，需要用另一种引号括起来（单引号和双引号）

echo 命令固定在输出的文本后有一个回车，使用 `-n` 命令可以取消回车输出

## 11.4 使用变量



### 11.4.1 环境变量

在脚本中使用的环境变量必须是全局环境变量

如果要使用一些特殊字符，需要用 `\` 转义

> echo $ligen
>
> echo I\\'m

### 11.4.2 用户变量

在脚本中临时定义变量，作用域在脚本中

使用等号将赋值给用户变量，在变量、等号和值之间不能出现空格（这是和其他编程语言区别的地方）





# 附录

## 实用命令

### 文本处理

`tail`

从文件尾输出文件

> tail -n -10 filename # -n参数，指定输出行数，输出文件后10行
>
> tail -n10 filename # 同上
>
> tail -n 10 filename # 同上
>
> tail -10 filename # 同上
>
> tail -n +10 filename # 从文件的第十行输出到末尾
>
> tail -f filename # 循环打印，文件中有新增就会打印到屏幕上

`awk gawk`

awk命令会遍历文件

> \#  $0 代表了整行记录
>
> \# $num 代表了一行按照IFS分割后的分块
>
> awk '{print NR}' filename # 输出记录数，NR代表已经处理的记录数
>
> 
>
> awk '/模式串/{print $0}' # 在每个处理前面添加正则表达式
>
> awk 'BEGIN{cnt}{if(NR>1){cnt=cnt+$4}}END{print cnt}' # BEGIN中定义变量，只执行一次；从第二行开始计算某个；END最后输出这个计算的值
>
> awk '{cnt+=$4}END{print cnt}' # 同上，直接忽略了文件头的字符串，ba 
>
> substr 求子串
>
> length 求字符串长度
>
> split 分割字符串
>
> 内部重定向到文件时可以使用双引号""将路径括起来



`grep`

过滤

> cat filename | grep "模式"
>
> grep -c "模式" filename # 输出符合规则的行数，要匹配所有行可以将模式指定为""
>
> grep -n "模式" filename # 打印行号
>
> grep -v "模式" # 输出未匹配的行

`tr`

压缩替换

> tr -s 'n' 'N' # 将出现的小写n替换为大写N
>
> tr -s ' ' '\n' # 将出现的空格替换为换行符，连续出现只会替换一次
>
> tr -s 'now' 'N' # 分别将n、o、w字符替换为大写N

### 统计

`uniq`

> uniq -c # 将连续多行一样的文本压缩为一行，并输出 出现次数和文本

`sort`

> sort -n # 按照数字排序
>
> sort -k #  按指定列进行排序
>
> sort -r # 降序排列
>
> sort -b # 忽略每行前面开始出现的空格
>
> sort -t # 设置字段分割符



### 逻辑控制

`for`

> for item in 1 4 5;do;done # 带list的循环
>
> for num in {0..500};do;done # 数字范围内循环，闭区间
>
> for num in {0..500..3};do;done # 数字范围内循环，闭区间，每隔3个数字
>
> for ((a=0;a<=500;a=a+7));do;done # c风格for循环，这个应该算c风格条件判断



>数值比较
>
>n1 -eq n2 检查n1是否等于n2
>
>n1 -ge n2 检查n1是否大于等于n2
>
>n1 -gt n2 检查n1是否大于n2
>
>n1 -le n2 检查n1是否小于等于n2
>
>n1 -lt n2 检查n1是否小于n2
>
>n1 -ne n2 检查n1是否不等于n2
>
>字符串比较
>
>str1 = str2
>
>str1 != str2
>
>str1 < str2
>
>str1 > str2
>
>-n str1 检查str1的长度是否不为0
>
>-z str1 检查str1的长度是否为0
>
>文件判断
>
>-d file
>
>-e file
>
>-f file
>
>-r file
>
>-s file
>
>-w file
>
>-x file
>
>-O file
>
>-G file
>
>file1 -nt file2
>
>file1 -ot file2

### 系统分析

`top`

> -d：指定刷新时间间隔，秒
>
> -n：运行n次后推出
>
> -p：指定pid
>
> -u：指定用户
>
> -H：显示线程详细
>
> top - 16:36:47 up 68 days,  7:35,  7 users,  load average: 0.54, 0.92, 2.77
> Tasks: 696 total,   1 running, 695 sleeping,   0 stopped,   0 zombie
> %Cpu(s):  7.8 us,  2.6 sy,  0.0 ni, 89.1 id,  0.0 wa,  0.0 hi,  0.4 si,  0.0 st
> MiB Mem :  31833.5 total,    325.9 free,  21708.1 used,   9799.5 buff/cache
> MiB Swap:   2048.0 total,      0.0 free,   2048.0 used.   9598.1 avail Mem 
>
> PID USER      PR  NI    VIRT    RES    SHR S  %CPU  %MEM     TIME+ COMMAND
>
>  354075 root      20   0   36.5g   1.2g   2968 S  55.6   4.0   8869:38 java
> 2042358 test01    20   0 8514804 734156  19928 S  27.8   2.3   4:55.35 java
> 1371008 test01    20   0 8588340 911880      0 S  11.1   2.8 564:35.84 java
> 2049252 test01    20   0   15696   4624   3516 R  11.1   0.0   0:00.04 top
>    1230 redis     20   0   61500   4024   2128 S   5.6   0.0 154:17.03 redis-server
>
> > 第一行：
> >
> > - 16:36:47 系统当前时间
> > - 68 days,  7:35 已运行时间
> > - 7 users 连接用户数
> > - load average: 0.54, 0.92, 2.77，系统负载，任务队列平均长度，1、5、15分钟
> >
> > 第二行：
> >
> > - Tasks: 696 total 进程总数
> > - 1 running 运行中进程数
> > - 695 sleeping 休眠中进程数
> > - 0 stopped 停止进程数
> > - 0 zombie 僵尸进程数
> >
> > 第三行：
> >
> > - 7.8 us 用户空间占用百分比
> > - 2.6 sy 系统空间占用百分比
> > - 0.0 ni 用户进程空间内改变过优先级的进程占用CPU百分比
> > - 89.1 id 空闲CPU百分比
> > - 0.0 wa 等待输入输出的CPU时间百分比
> > - 0.0 hi 硬中断（Hardware IRQ）占用CPU的百分比
> > - 0.4 si 软中断（Software Interrupts）占用CPU的百分比
> > - 0.0 st 用于有虚拟cpu的情况，用来指示被虚拟机偷掉的cpu时间
> >
> > 第四行：
> >
> > - 31833.5 total 物理内存总量
> > - 325.9 free 空闲内存总量
> > - 21708.1 used 使用内存总量
> > - 9799.5 buff/cache 用作内核缓存内存总量
> >
> > 进程：
> >
> > - PID 进程id
> > - USER：所属用户
> > - PR：优先级
> > - NI：nice值，负值表示高优先级，正值表示低
> > - VIRT：使用虚拟内存空间
> > - RES：进程使用的内存大小
> > - SHR：共享内存
> > - S ：进程状态。D=不可中断的睡眠状态 R=运行 S=睡眠 T=跟踪/停止 Z=僵尸进程
> > - %CPU：上次更新到现在的CPU时间占用百分比
> > - %MEM：进程使用的物理内存百分比
> > - TIME+：进程使用的CPU时间总计，单位1/100秒
> > - COMMAND：命令名/命令行

`ps`

> ps -ef 进程信息
>
> ps --forest 进程父子关系
>
> ps -u 指定用户进程
