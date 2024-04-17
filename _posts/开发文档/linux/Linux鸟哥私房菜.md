# 第一章 Linux是什么与如何学习

## 1.1 Linux是什么

## 1.2 Torvalds的Linux发展

## 1.3 Linux当前应用的角色

## 1.4 Linux该如何学习



# 第二章 主机规划与磁盘分区

## 2.1 Linux与硬件的搭配

### 2.1.3 各硬件装置在Linux中的文件名

在Linux系统中，每个装置都被当作一个文件来对待

IDE接口的硬盘的文件名即为 `/dev/sd[a-d]`

<table>
    <tr>
    	<th>装置</th>
        <th>装置在Linux内的文件名</th>
    </tr>
    <tr>
    	<td>SCSI/SATA/USB 硬盘</td>
    	<td>/dev/sd[a-p]</td>
    </tr>
    <tr>
    	<td>USB 快闪碟</td>
    	<td>/dev/sd[a-p]</td>
    </tr>
    <tr>
    	<td>Birtl/O 界面</td>
    	<td>/dev/vd[a-p]用于虚拟机内</td>
    </tr>
    <tr>
    	<td>软盘驱动器</td>
    	<td>/dev/fd[0-7]</td>
    </tr>
    <tr>
    	<td>打印机</td>
    	<td>/dev/lp[0-2]（25针打印机）/dev/usb/lp[0-15]（USB界面）</td>
    </tr>
    <tr>
    	<td>鼠标</td>
    	<td>/dev/input/mouse[0-15]（通用）/dev/psaux（PS/2 界面）</td>
    </tr>
</table>




## 2.2 磁盘分区



### 2.2.1 磁盘连接的方式与装置文件名的关系

实体机器一般都是 `/dev/sd[a-]` 的磁盘文件名称，在虚拟机环境下，一般都是 `/dev/vd/[a-p]` 这样的装置文件名

以SATA接口来说，由于 SATA/USB/SAS 等磁盘接口都是使用SCSI模块驱动的，因此这些接口的磁盘装置文件名称都是 `/dev/sd[a-p]` 的格式。对于安装的磁盘会以什么名称显示，**需要按照Linux核心检测到的顺序为准**

> 如果PC上有两个SATA磁盘和一个USB磁盘，主板上有6各SATA位。这两个SATA盘安装在SATA1和SATA5，那么这三个磁盘在系统中如何命名呢？
>
> 因为是按照检测到的顺序命名，所以
>
> 1. SATA1上的命名为：/dev/sda
> 2. SATA2上的命名为：/dev/sdb
> 3. USB在开机完成后才被检测到，所以是：/dev/sdc

如果一个磁盘被分为两个区，那么该如何命名？

磁盘主要组成有磁盘盘、机械手臂、读取头和主轴马达。数据都存储在磁盘盘面上，磁盘盘面又分出扇区（Sector）与磁道（Track）两种单位，扇区设计两种大小，512bytes 与 4Kbytes

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/linux/%E9%B8%9F%E5%93%A5/%E7%A3%81%E7%9B%98%E7%BB%84%E6%88%90.png)

早期磁盘第一个扇区含有的重要信息我们称为MBR（Master Boot Record）格式，近年来磁盘容量变大造成一些无法存取的情况。现在新的格式称为GPT（GUID partition table）



### 2.2.2 MSDOS（MBR）与GPT磁盘分区表（partition table）