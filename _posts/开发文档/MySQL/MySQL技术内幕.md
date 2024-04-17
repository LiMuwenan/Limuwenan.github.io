   # 第1章 MySQL体系结构和存储引擎 
   ## 1.1 定义数据库和实例

**数据库：**物理操作系统文件或其他形式文件类型的集合。在` MySQL ` 数据库中，数据库文件可以是` frm `、` MYD `、` MYI `、` ibd `结尾的文件。当 使用` NDB `引擎时，数据库的文件可能不是操作系统上的文件，而是存 放于内存之中的文件，但是定义仍然不变。

**实例：**` MySQL `数据库由后台线程以及一个共享内存区组成。共享内 存可以被运行的后台线程所共享。需要牢记的是，数据库实例才是真 正用于操作数据库文件的。

## 1.2 MySQL体系结构

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/MySQL/%E6%8A%80%E6%9C%AF%E5%86%85%E5%B9%95/%E4%BD%93%E7%B3%BB%E7%BB%93%E6%9E%84.png)

由几部分组成：

- 连接池组件
- 管理服务和工具组件
- SQL接口组件
- 查询分析器组件
- 优化器组件
- 缓冲组件
- 插件式存储引擎
- 物理文件

独特的插件式存储引擎结构，存储引擎是基于表的

## 1.3 MySQL存储引擎

### 1.3.1 InnoDB存储引擎

[更详细见第二章](# 第2章 InnoDB存储引擎)

该引擎支持事务，其设计目标主要面向在线事务处理的应用。其特点是行锁设计、支持外键，并支持类似于 `Oracle` 的非锁定读，即默认读取操作不会产生锁。

` InnoDB `通过使用多版本并发控制（` MVCC `）来获得高并发性，并且实现了` SQL `标准的4种隔离级别，默认为` REPEATABLE `级别。同时，使用一种被称为` next-key locking `的策略来避免[幻读（ phantom ）]()现象的产生。除此之外，` InnoDB `储存引擎还提供了插入缓冲（` insert buffer `）、二次写（` double write `）、自适应哈希索引（` adaptive hash index `）、预读（` read ahead `）等高性能和高可用的功能。

对于表中数据的存储，` InnoDB `存储引擎采用了聚集（` clustered `）的方 式，因此每张表的存储都是按主键的顺序进行存放。如果没有显式地 在表定义时指定主键，` InnoDB `存储引擎会为每一行生成一个6字节的`  ROWID `，并以此作为主键

### 1.3.2 MyISAM存储引擎

该引擎不支持事务、表所设计，支持全文索引，主要面向一些`OLAP`数据库应用。`MyISAM`存储引擎的另 一个与众不同的地方是它的缓冲池只缓存（`cache`）索引文件，而不缓冲数据文件，这点和大多数的数据库都非常不同。



` MyISAM `存储引擎表由` MYD `和` MYI `组成，` MYD `用来存放数据文件，`  MYI `用来存放索引文件。可以通过使用` myisampack `工具来进一步压缩 数据文件，因为` myisampack `工具使用赫夫曼（` Huffman `）编码静态算 法来压缩数据，因此使用` myisampack `工具压缩后的表是只读的，当然 用户也可以通过` myisampack `来解压数据文件。



### 1.3.3 NDB存储引擎

### 1.3.4 Memory存储引擎

### 1.3.5

### 1.3.6

### 1.3.7

### 1.3.8

## 1.4 存储引擎之间的比较

[官方存储引擎对比](https://dev.mysql.com/doc/refman/8.0/en/storage-engines.html)

<div class="table">
<a name="idm45589401668640"></a><p class="title"><b>Table&nbsp;16.1&nbsp;Storage Engines Feature Summary</b></p>
<div class="table-contents">
<table frame="box" rules="all" summary="Summary of features supported per storage engine."><colgroup><col style="width: 10%"><col style="width: 16%"><col style="width: 16%"><col style="width: 16%"><col style="width: 16%"><col style="width: 16%"></colgroup><thead><tr><th scope="col">Feature</th>
<th scope="col">MyISAM</th>
<th scope="col">Memory</th>
<th scope="col">InnoDB</th>
<th scope="col">Archive</th>
<th scope="col">NDB</th>
</tr></thead><tbody><tr><th scope="row">B-tree indexes</th>
<td>Yes</td>
<td>Yes</td>
<td>Yes</td>
<td>No</td>
<td>No</td>
</tr><tr><th scope="row">Backup/point-in-time recovery (note 1)</th>
<td>Yes</td>
<td>Yes</td>
<td>Yes</td>
<td>Yes</td>
<td>Yes</td>
</tr><tr><th scope="row">Cluster database support</th>
<td>No</td>
<td>No</td>
<td>No</td>
<td>No</td>
<td>Yes</td>
</tr><tr><th scope="row">Clustered indexes</th>
<td>No</td>
<td>No</td>
<td>Yes</td>
<td>No</td>
<td>No</td>
</tr><tr><th scope="row">Compressed data</th>
<td>Yes (note 2)</td>
<td>No</td>
<td>Yes</td>
<td>Yes</td>
<td>No</td>
</tr><tr><th scope="row">Data caches</th>
<td>No</td>
<td>N/A</td>
<td>Yes</td>
<td>No</td>
<td>Yes</td>
</tr><tr><th scope="row">Encrypted data</th>
<td>Yes (note 3)</td>
<td>Yes (note 3)</td>
<td>Yes (note 4)</td>
<td>Yes (note 3)</td>
<td>Yes (note 3)</td>
</tr><tr><th scope="row">Foreign key support</th>
<td>No</td>
<td>No</td>
<td>Yes</td>
<td>No</td>
<td>Yes (note 5)</td>
</tr><tr><th scope="row">Full-text search indexes</th>
<td>Yes</td>
<td>No</td>
<td>Yes (note 6)</td>
<td>No</td>
<td>No</td>
</tr><tr><th scope="row">Geospatial data type support</th>
<td>Yes</td>
<td>No</td>
<td>Yes</td>
<td>Yes</td>
<td>Yes</td>
</tr><tr><th scope="row">Geospatial indexing support</th>
<td>Yes</td>
<td>No</td>
<td>Yes (note 7)</td>
<td>No</td>
<td>No</td>
</tr><tr><th scope="row">Hash indexes</th>
<td>No</td>
<td>Yes</td>
<td>No (note 8)</td>
<td>No</td>
<td>Yes</td>
</tr><tr><th scope="row">Index caches</th>
<td>Yes</td>
<td>N/A</td>
<td>Yes</td>
<td>No</td>
<td>Yes</td>
</tr><tr><th scope="row">Locking granularity</th>
<td>Table</td>
<td>Table</td>
<td>Row</td>
<td>Row</td>
<td>Row</td>
</tr><tr><th scope="row">MVCC</th>
<td>No</td>
<td>No</td>
<td>Yes</td>
<td>No</td>
<td>No</td>
</tr><tr><th scope="row">Replication support (note 1)</th>
<td>Yes</td>
<td>Limited (note 9)</td>
<td>Yes</td>
<td>Yes</td>
<td>Yes</td>
</tr><tr><th scope="row">Storage limits</th>
<td>256TB</td>
<td>RAM</td>
<td>64TB</td>
<td>None</td>
<td>384EB</td>
</tr><tr><th scope="row">T-tree indexes</th>
<td>No</td>
<td>No</td>
<td>No</td>
<td>No</td>
<td>Yes</td>
</tr><tr><th scope="row">Transactions</th>
<td>No</td>
<td>No</td>
<td>Yes</td>
<td>No</td>
<td>Yes</td>
</tr><tr><th scope="row">Update statistics for data dictionary</th>
<td>Yes</td>
<td>Yes</td>
<td>Yes</td>
<td>Yes</td>
<td>Yes</td>
</tr></tbody></table>
</div><div class="table-contents"><table cellpadding="0" cellspacing="0" style="position: fixed; top: 0px; display: none; left: 401px; width: 763px;"><thead><tr><th scope="col" style="width: 113.25px;">Feature</th>
<th scope="col" style="width: 129.656px;">MyISAM</th>
<th scope="col" style="width: 129.656px;">Memory</th>
<th scope="col" style="width: 129.656px;">InnoDB</th>
<th scope="col" style="width: 129.656px;">Archive</th>
<th scope="col" style="width: 129.719px;">NDB</th>
</tr></thead></table></div>
</div>

## 1.5 连接MySQL

### 1.5.1 TCP/IP

`TCP/IP`套接字方式是`MySQL`数据库在任何平台下都提供的连接方式



在通过` TCP `/IP连接到` MySQL `实例时，` MySQL `数 据库会先检查一张权限视图，用来判断发起请求的客户端IP是否允许 连接到` MySQL `实例。该视图在` mysql `架构下，表名为` user `



### 1.5.2 命名管道和共享内存

### 1.5.3 UNIX域套接字





# 第2章 InnoDB存储引擎



## 2.3 InnoDB 体系架构

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/MySQL/%E6%8A%80%E6%9C%AF%E5%86%85%E5%B9%95/InnoDB%E5%AD%98%E5%82%A8.png)

从图可见，`InnoDB` 存储引擎有多个内存块，可以认 为这些内存块组成了一个大的内存池，负责如下工作：

- 维护所有进程/线程需要访问的多个内部数据结构。
- 缓存磁盘上的数据，方便快速地读取，同时在对磁盘文件的数据修 改之前在这里缓存。 
- 重做日志（`redo log`）缓冲。

后台线程的主要作用是负责刷新内存池中的数据，保证缓冲池中的内 存缓存的是最近的数据。此外将已修改的数据文件刷新到磁盘文件， 同时保证在数据库发生异常的情况下`InnoDB`能恢复到正常运行状态。

### 2.3.1 后台线程

**1. Master Thread**

` Master Thread `是一个非常核心的后台线程，主要负责将缓冲池中的数据异步刷新到磁盘，保证数据的一致性，包括脏页的刷新、合并插入缓冲（` INSERT BUFFER `）、` UNDO `页的回收等。2.5节会详细地介绍各个版本中` Master Thread `的工作方式。

**2. IO Thread**

在` InnoDB `存储引擎中大量使用了` AIO `（` Async IO `）来处理写IO请求， 这样可以极大提高数据库的性能。而` IO Thread `的工作主要是负责这些`  IO `请求的回调（` call back `）处理。` InnoDB 1.0`版本之前共有4个` IO Thread `，分别是` write `、` read `、` insert buffer `和` log IO thread `。

可以通过命令`SHOW ENGINE INNODB STATUS`来观察`InnoDB`中的`IO Thread`

可以看到` IO Thread 0 `为` insert buffer thread `。` IO Thread 1 `为` log thread `。之 后就是根据参数` innodb_read_io_threads `及` innodb_write_io_threads `来设 置的读写线程，并且读线程的ID总是小于写线程。

**3. Purge Thread**

事务被提交后，其所使用的` undolog `可能不再需要，因此需要`  PurgeThread `来回收已经使用并分配的` undo `页。在` InnoDB 1.1`版本之 前，` purge `操作仅在` InnoDB `存储引擎的` Master Thread `中完成。而从`  InnoDB 1.1`版本开始，` purge `操作可以独立到单独的线程中进行，以此 来减轻` Master Thread `的工作，从而提高` CPU `的使用率以及提升存储引 擎的性能。

从` InnoDB 1.2`版本开始，` InnoDB `支持多个` Purge Thread `，这样做的目的 是为了进一步加快` undo `页的回收。同时由于` Purge Thread `需要离散地读 取` undo `页，这样也能更进一步利用磁盘的随机读取性能。

**4. Page Cleaner Thread**

` Page Cleaner Thread `是在` InnoDB 1.2.x `版本中引入的。其作用是将之前 版本中脏页的刷新操作都放入到单独的线程中来完成。而其目的是为 了减轻原` Master Thread `的工作及对于用户查询线程的阻塞，进一步提 高` InnoDB `存储引擎的性能。



### 2.3.2 内存

**1. 缓冲池**

` InnoDB `存储引擎是基于磁盘存储的，并将其中的记录按照页的方式进 行管理。因此可将其视为基于磁盘的数据库系统（` Disk-base Database `）。在数据库系统中，由于` CPU `速度与磁盘速度之间的鸿沟，基于磁盘的数据库系统通常使用缓冲池技术来提高数据库的整体性能。



缓冲池简单来说就是一块内存区域，通过内存的速度来弥补磁盘速度 较慢对数据库性能的影响。在数据库中进行读取页的操作，首先将从 磁盘读到的页存放在缓冲池中，这个过程称为将页“` FIX `”在缓冲池中。 下一次再读相同的页时，首先判断该页是否在缓冲池中。若在缓冲池 中，称该页在缓冲池中被命中，直接读取该页。否则，读取磁盘上的 页。 



对于数据库中页的修改操作，则首先修改在缓冲池中的页，然后再以 一定的频率刷新到磁盘上。这里需要注意的是，页从缓冲池刷新回磁 盘的操作并不是在每次页发生更新时触发，而是通过一种称为[Checkpoint](# 2.4 Checkpoint技术) 的机制刷新回磁盘。同样，这也是为了提高数据库的整体 性能。



缓冲池中缓存的数据页类型有：索引页、数据页、` undo  `页、插入缓冲（` insert buffer `）、自适应哈希索引（` adaptive hash index `）、` InnoDB `存储的锁信息（` lock info `）、数据字典信息（` data dictionary `）等。不能简单地认为，缓冲池只是缓存索引页和数据页， 它们只是占缓冲池很大的一部分而已。图很好地显示了` InnoDB `存 储引擎中内存的结构情况。

![image-20220218200342682](https://github.com/LiMuwenan/PicBed/blob/master/img/dev/MySQL/%E6%8A%80%E6%9C%AF%E5%86%85%E5%B9%95/InnoDBBuffer.PNG?raw=true)

从`InnoDB 1.0.x`版本开始，允许有多个缓冲池实例。每个页根据哈希值 平均分配到不同缓冲池实例中。

**2. LRU List、Free List、Flush List**



通常来说，数据库中的缓冲池是通过` LRU `（` Latest Recent Used `，最近 最少使用）算法来进行管理的。即最频繁使用的页在` LRU `列表的前 端，而最少使用的页在` LRU `列表的尾端。当缓冲池不能存放新读取到 的页时，将首先释放` LRU `列表中尾端的页。

在` InnoDB `存储引擎中，缓冲池中页的大小默认为` 16KB `，同样使用`  LRU `算法对缓冲池进行管理。稍有不同的是` InnoDB `存储引擎对传统的`  LRU `算法做了一些优化。在` InnoDB `的存储引擎中，` LRU `列表中还加入 了` midpoint `位置。新读取到的页，虽然是最新访问的页，但并不是直 接放入到` LRU `列表的首部，而是放入到` LRU `列表的` midpoint `位置。这 个算法在` InnoDB `存储引擎下称为` midpoint insertion strategy `。在默认配 置下，该位置在` LRU `列表长度的5/8处。

在` InnoDB `存储引擎中，把` midpoint `之后的列表称为` old `列表，之前的列 表称为` new `列表。可以简单地理解为` new `列表中的页都是最为活跃的热 点数据



那为什么不采用朴素的` LRU `算法，直接将读取的页放入到` LRU `列表的 首部呢？这是因为若直接将读取到的页放入到` LRU `的首部，那么某些`  SQL `操作可能会使缓冲池中的页被刷新出，从而影响缓冲池的效率。 常见的这类操作为索引或数据的扫描操作。

` LRU `列表用来管理已经读取的页，但当数据库刚启动时，` LRU `列表是空的，即没有任何的页。这时页都存放在` Free `列表中。当需要从缓冲池中分页时，首先从` Free `列表中查找是否有可用的空闲页，若有则将 该页从` Free `列表中删除，放入到` LRU `列表中。否则，根据` LRU `算法， 淘汰` LRU `列表末尾的页，将该内存空间分配给新的页。当页从` LRU `列 表的` old `部分加入到` new `部分时，称此时发生的操作为` page made young `，而因为` innodb_old_blocks_time `的设置而导致页没有从` old `部分 移动到` new `部分的操作称为` page not made young `。

` InnoDB `存储引擎从` 1.0.x `版本开始支持压缩页的功能，即将原本` 16KB  `的页压缩为` 1KB `、` 2KB `、` 4KB `和` 8KB `。而由于页的大小发生了变化，`  LRU `列表也有了些许的改变。对于非` 16KB `的页，是通过` unzip_LRU `列 表进行管理的。

对于压缩页的表，每个表的压缩比率可能各不相同。可能存在有的表 页大小为` 8KB `，有的表页大小为` 2KB `的情况。` unzip_LRU `是怎样从缓冲 池中分配内存的呢？ 

首先，在` unzip_LRU `列表中对不同压缩页大小的页进行分别管理。其次，通过伙伴算法进行内存的分配。例如对需要从缓冲池中申请页为`  4KB `的大小，其过程如下： 

1）检查` 4KB `的` unzip_LRU `列表，检查是否有可用的空闲页； 

2）若有，则直接使用； 

3）否则，检查` 8KB `的` unzip_LRU `列表； 

4）若能够得到空闲页，将页分成2个` 4KB `页，存放到` 4KB `的` unzip_LRU  `列表；

 5）若不能得到空闲页，从` LRU `列表中申请一个` 16KB `的页，将页分为1 个` 8KB `的页、2个` 4KB `的页，分别存放到对应的` unzip_LRU `列表中。



在` LRU `列表中的页被修改后，称该页为脏页（` dirty page `），即缓冲池 中的页和磁盘上的页的数据产生了不一致。这时数据库会通过`  CHECKPOINT `机制将脏页刷新回磁盘，而` Flush `列表中的页即为脏页 列表。需要注意的是，脏页既存在于` LRU `列表中，也存在于` Flush `列表 中。` LRU `列表用来管理缓冲池中页的可用性，` Flush `列表用来管理将页 刷新回磁盘，二者互不影响。



**3. 重做日志缓冲**

 ` InnoDB `存储引擎首先将重做日志 信息先放入到这个缓冲区，然后按一定频率将其刷新到重做日志文 件。重做日志缓冲一般不需要设置得很大，因为一般情况下每一秒钟 会将重做日志缓冲刷新到日志文件，因此用户只需要保证每秒产生的 事务量在这个缓冲大小之内即可。

  在通常情况下，` 8MB `的重做日志缓冲池足以满足绝大部分的应用，因 为重做日志在下列三种情况下会将重做日志缓冲中的内容刷新到外部 磁盘的重做日志文件中。 

  - Master Thread 每一秒将重做日志缓冲刷新到重做日志文件；
  - 每个事务提交时会将重做日志缓冲刷新到重做日志文件； 
  - 当重做日志缓冲池剩余空间小于1/2时，重做日志缓冲刷新到重做日 志文件。

**4. 额外的内存池**

在`InnoDB`存储引擎中，对内存的管理是 通过一种称为内存堆（`heap`）的方式进行的。在对一些数据结构本身 的内存进行分配时，需要从额外的内存池中进行申请，当该区域的内 存不够时，会从缓冲池中进行申请。



## 2.4 Checkpoint技术

如果一条` DML `语 句，如` Update `或` Delete `改变了页中的记录，那么此时页是脏的，即缓冲 池中的页的版本要比磁盘的新。数据库需要将新版本的页从缓冲池刷 新到磁盘。 倘若每次一个页发生变化，就将新页的版本刷新到磁盘，那么这个开 销是非常大的。若热点数据集中在某几个页中，那么数据库的性能将 变得非常差。同时，如果在从缓冲池将页的新版本刷新到磁盘时发生 了宕机，那么数据就不能恢复了。为了避免发生数据丢失的问题，当 前事务数据库系统普遍都采用了` Write Ahead Log `策略，即当事务提交 时，先写重做日志，再修改页。当由于发生宕机而导致数据丢失时， 通过重做日志来完成数据的恢复。这也是事务` ACID `中D（` Durability `持 久性）的要求。

因此` Checkpoint `（检查点）技术的目的是解决以下几个问题： 

- 缩短数据库的恢复时间：只需要重做 `Checkpoint` 之后的命令
- 缓冲池不够用时，将脏页刷新到磁盘：将缓冲池刷新淘汰的脏页落盘
- 重做日志不可用时，刷新脏页：重做日志循环使用，当前面的记录要被覆盖时，就要强制 `Checkoutpoint` 来刷新

有两种 `Checkoutpoint` 的方式：

- Sharp Checkpoint
- Fuzzy Checkpoint

`Sharp Checkpoint` 数据库即将关闭所有的脏页都刷新回磁盘

`Fuzzy Checkpoint` 在数据库运行的部分刷新，可能发生的情况：

- Master Thread Checkpoint 
- FLUSH_LRU_LIST Checkpoint 
- Async/Sync Flush Checkpoint 
- Dirty Page too much Checkpoint

对应了`  checkpoint  `要解决的几个问题

对于` Master Thread `（` 2.5 `节会详细介绍各个版本中` Master Thread `的实 现）中发生的` Checkpoint `，差不多以每秒或每十秒的速度从缓冲池的 脏页列表中刷新一定比例的页回磁盘。这个过程是异步的，即此时`  InnoDB `存储引擎可以进行其他的操作，用户查询线程不会阻塞。

` FLUSH_LRU_LIST Checkpoint `是因为` InnoDB `存储引擎需要保证` LRU `列 表中需要有差不多` 100 `个空闲页可供使用。在` InnoDB1.1.x `版本之前， 需要检查` LRU `列表中是否有足够的可用空间操作发生在用户查询线程 中，显然这会阻塞用户的查询操作。倘若没有` 100 `个可用空闲页，那么`  InnoDB `存储引擎会将` LRU `列表尾端的页移除。如果这些页中有脏页那么需要进行` Checkpoint `，而这些页是来自` LRU `列表的，因此称为`  FLUSH_LRU_LIST Checkpoint `。而从` MySQL 5.6 `版本，也就是` InnoDB1.2.x `版本开始，这个检查被放在 了一个单独的` Page Cleaner Thread `中进行

` Async `/` Sync Flush Checkpoint `指的是重做日志文件不可用的情况，这时 需要强制将一些页刷新回磁盘，而此时脏页是从脏页列表中选取的。

` Dirty Page too much `，即脏页的数量太 多，导致` InnoDB `存储引擎强制进行` Checkpoint `。其目的总的来说还是 为了保证缓冲池中有足够可用的页。



## 2.5 Master Thread工作方式

### 2.5.1 InnoDB1.0.x 版本之前的Master Thread

### 2.5.2 InnoDB1.2.x 版本之前的Master Thread

### 2.5.3 InnoDB1.2.x 版本的Master Thread

## 2.6 InnoDB关键特性

InnoDB存储引擎的关键特性包括： 

- [插入缓冲（Insert Buffer）](# 2.6.1 插入缓冲)

- [两次写（Double Write）]() 

- [自适应哈希索引（Adaptive Hash Index） ]()

- [异步IO（Async IO） ]()

- [刷新邻接页（Flush Neighbor Page）]()

### 2.6.1 插入缓冲

**1. Insert Buffer**

` Insert Buffer `不是缓冲池的一个组成部分（缓冲池中只是有相关信息），它和数据页一样，也是物理页的一个组成部分。

在` InnoDB `存储引擎中，主键是行唯一的标识符。通常应用程序中行记录的插入顺序是按照主键递增的顺序进行插入的。因此，插入聚集索引（` Primary Key `）一般是顺序的，不需要磁盘的随机读取。

若主键类是` UUID `这样的`   `类，那么插入和辅助索引一样，同样是随机的。即使主键是自增类型，但是插入的是指定的值，而不是` NULL `值，那么同样可能导致插入并非连续的情况。

但是不可能每张表上只有一个聚集索引，更多情况下，一张表上有多个非聚集的辅助索引（` secondary index `）。

```mysql
CREATE TABLE t(
	a INT AUTO_INCREMENT,
	b VARCHAR(30),
	PRIMARY KEY(a),
	key(b)
);
```

在这样的情况下产生了一个非聚集的且不是唯一的索引。在进行插入 操作时，数据页的存放还是按主键a进行顺序存放的，但是对于非聚集 索引叶子节点的插入不再是顺序的了，这时就需要离散地访问非聚集 索引页，由于随机读取的存在而导致了插入操作性能下降。当然这并 不是这个b字段上索引的错误，而是因为B+树的特性决定了非聚集索 引插入的离散性。

**Insert Buffer 主要完成的事情：**

` Insert Buffer `使得对于非聚集索引的插入或更新操作，不是每一次直接插入到索引页中，而是先判断插入的非 聚集索引页是否在缓冲池中，若在，则直接插入；若不在，则先放入 到一个` Insert Buffer `对象中，好似欺骗。数据库这个非聚集的索引已经 插到叶子节点，而实际并没有，只是存放在另一个位置。然后再以一 定的频率和情况进行` Insert Buffer `和辅助索引页子节点的` merge `（合 并）操作，这时通常能将多个插入合并到一个操作中（因为在一个索 引页中），这就大大提高了对于非聚集索引插入的性能。

使用` Insert Buffer `需要两个条件：

- 索引是辅助索引（`secondary index`）
- 索引不是唯一（`unique`）

辅助索引不能是唯一的，因为在插入缓冲时，数据库并不去查找索引 页来判断插入的记录的唯一性。如果去查找肯定又会有离散读取的情 况发生，从而导致`Insert Buffer`失去了意义。

**2. Change Buffer**

` InnoDB `从` 1.0.x `版本开始引入了` Change Buffer `，可将其视为` Insert Buffer  `的升级。可以对` DML `都进行缓冲：` Insert Buffer `,`  Delete Buffer `,`  Purge Buffer `

对一条记录进行` UPDATE `操作可能分为两个过程：

- 将记录标记为已删除
- 真正将记录和删除

因此` Delete Buffer `对应` UPDATE `操作的第一个过程，即将记录标记为删 除。` Purge Buffer `对应` UPDATE `操作的第二个过程，即将记录真正的删 除。

**3. Insert Buffer的内部实现**

使用场景：非唯一辅助索引的插入操作

` Insert Buffer `的数据结构是一棵` B+ `树。` MySQL 4.1 `之后全局只有一棵` Insert Buffer B+ `树，负责对所有的 表的辅助索引进行` Insert Buffer `。这棵` B+ `树存放在共享表空间中，默 认也就是` ibdata1 `中。因此，试图通过独立表空间` ibd `文件恢复表中数据 时，往往会导致` CHECK TABLE `失败。这是因为表的辅助索引中的数 据可能还在` Insert Buffer `中，也就是共享表空间中，所以通过` ibd `文件进 行恢复后，还需要进行` REPAIR TABLE `操作来重建表上所有的辅助索 引。

` Insert Buffer `非叶子节点的` search key `

<table>
    <tr>
    	<td>space</td>
    	<td>marker</td>
    	<td>offset</td>
    </tr>
</table>

` space `：待插入记录的表空间`  id `。每张表都有唯一的`  space id `

` marker `：兼容老版本的` Insert Buffer `

` offset `：页所在的偏移量

当一个辅助索引要插入到页（` space `，` offset `）时，如果这个页不在缓冲 池中，那么` InnoDB `存储引擎首先根据上述规则构造一个` search key `，接 下来查询` Insert Buffer `这棵` B+ `树，然后再将这条记录插入到` Insert Buffer B+ `树的叶子节点中。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/MySQL/%E6%8A%80%E6%9C%AF%E5%86%85%E5%B9%95/InsertBufferB%2B.png)

从` Insert Buffer `叶子节点的第5列开始，就是实际插入记录的各个字段 了。因此较之原插入记录，` Insert Buffer B+ `树的叶子节点记录需要额 外` 13 `字节的开销。

因为启用` Insert Buffer `索引后，辅助索引页（` space `，` page_no `）中的记 录可能被插入到` Insert Buffer B+ `树中，所以为了保证每次` Merge Insert Buffer `页必须成功，还需要有一个特殊的页用来标记每个辅助索引页 （` space `，` page_no `）的可用空间。这个页的类型为` Insert Buffer Bitmap `。 每个` Insert Buffer Bitmap `页用来追踪` 16384 `个辅助索引页，也就是` 256 `个 区（` Extent `）。每个` Insert Buffer Bitmap `页都在` 16384 `个页的第二个页 中。

**4. Merge Insert Buffer**

` Merge Insert Buffer `操作可能发生在以下几种情况：

- 辅助索引页被读取到缓冲池时
- `Insert Buffer Bitmap `页追踪到该辅助索引页已无可用空间时
- `Master Thread `

第一种情况为当辅助索引页被读取到缓冲池中时，例如这在执行正常 的` SELECT `查询操作，这时需要检查` Insert Buffer Bitmap `页，然后确认 该辅助索引页是否有记录存放于` Insert Buffer B+ `树中。若有，则将`  Insert Buffer B+ `树中该页的记录插入到该辅助索引页中。可以看到对 该页多次的记录操作通过一次操作合并到了原有的辅助索引页中，因 此性能会有大幅提高。

第二种情况为` Insert Buffer Bitmap `页用来追踪每个辅助索引页的可用空间，并至少有`  1 / 32 `页的空间。若插入辅助索引记录时检测到插入记录后可用空间会 小于`1/ 32 `页，则会强制进行一个合并操作，即强制读取辅助索引页， 将` Insert Buffer B+ `树中该页的记录及待插入的记录插入到辅助索引页 中。

第三种情况为在` Master Thread  `线程中每秒或每` 10 `秒会进行一次` Merge Insert Buffer `的操作

# 第3章 文件

# 第4章 表

# 第5章 索引与算法

## 5.1 InnoDB存储引擎索引概述

` InnoDB `存储引擎支持以下几种常见索引：

- `B+ `树索引
- 全文索引
- 哈希索引

` InnoDB `的哈希索引是自适应的，根据表的情况自动生成，不能认为干预

` B+ `树索引就是传统意义上的索引，这是目前关系型数据库系统中查找 最为常用和最为有效的索引。

` B+ `树索引能找到的只是被查找数据行所在的页。然后数据 库通过把页读入到内存，再在内存中进行查找，最后得到要查找的数 据

## 5.2 数据结构与算法

## 5.3 B+树

` B+ `树是为磁盘或其他直接存取辅助设备设计的一种平衡查找 树。在` B+ `树中，所有记录节点都是按键值的大小顺序存放在同一层的 叶子节点上，由各叶子节点指针进行连接。

## 5.4 B+树索引

数据库中的` B+ `树索引可以分为聚集索引（` clustered inex `）和辅助索引 （` secondary index `），但是不管是聚集还是辅助的索引，其内部都 是` B+ `树的，即高度平衡的，叶子节点存放着所有的数据。聚集索引与 辅助索引不同的是，叶子节点存放的是否是一整行的信息。

### 5.4.1 聚集索引

` InnoDB `存储引擎表是索引组织表，即表中数据按 照主键顺序存放。而聚集索引（` clustered index `）就是按照每张表的主键构造一棵` B+ `树，同时叶子节点中存放的即为整张表的行记录数据， 也将聚集索引的叶子节点称为数据页。聚集索引的这个特性决定了索 引组织表中数据也是索引的一部分。同` B+ `树数据结构一样，每个数据 页都通过一个双向链表来进行链接。

每张表只有一棵` B+ `树，因此只能由一个主键

聚集索引的存储并不是物理上连续的，而是逻辑上连续的。这 其中有两点：一是前面说过的页通过双向链表链接，页按照主键的顺 序排序；另一点是每个页中的记录也是通过双向链表进行维护的，物 理存储上可以同样不按照主键存储。

聚集索引的另一个好处是，它对于**主键的排序查找和范围查找速度非 常快**。

### 5.4.2 辅助索引

对于辅助索引（` Secondary Index `，也称非聚集索引），叶子节点并不 包含行记录的全部数据。叶子节点除了包含键值以外，每个叶子节点 中的索引行中还包含了一个书签（` bookmark `）。该书签用来告诉`  InnoDB `存储引擎哪里可以找到与索引相对应的行数据。由于` InnoDB `存 储引擎表是索引组织表，因此` InnoDB `存储引擎的辅助索引的书签就是 相应行数据的聚集索引键。

当通过辅助索引来寻找数据时，` InnoDB `存储引擎 会遍历辅助索引并通过叶级别的指针获得指向主键索引的主键（辅助索引的` bookmark `），然后 再通过主键索引来找到一个完整的行记录。

### 5.4.3 B+树索引的分裂

主键通常是递增的，也就是顺序插入，如果从中间分裂（1-10 分裂为 1-4 和 5-10，1-4部分不会再有新的插入）

`InnoDB`存储引擎的`Page Header`中有以下几个部分用来保存插入的顺序 信息： 

- ❑PAGE_LAST_INSERT 

- ❑PAGE_DIRECTION 

- ❑PAGE_N_DIRECTION 

通过这些信息，`InnoDB`存储引擎可以决定是向左还是向右进行分裂， 同时决定将分裂点记录为哪一个。若插入是随机的，则取页的中间记录作为分裂点的记录，这和之前介绍的相同。

若往同一方向进行插入 的记录数量为5，并且目前已经定位（`cursor`）到的记录（`InnoDB`存储 引擎插入时，首先需要进行定位，定位到的记录为待插入记录的前一条记录）之后还有3条记录，则分裂点的记录为定位到的记录后的第三 条记录，否则分裂点记录就是待插入的记录。

### 5.4.4 B+树索引的管理

**1. 索引管理**

索引的创建和删除可以通过两种方法，一种是ALTER TABLE，另一种 是CREATE/DROP INDEX。

通过命令SHOW INDEX FROM可以观察到表的索引

❑Table：索引所在的表名。 

❑Non_unique：非唯一的索引，可以看到primary key是0，因为必须是 唯一的。 

❑Key_name：索引的名字，用户可以通过这个名字来执行DROP INDEX。 

❑Seq_in_index：索引中该列的位置，如果看联合索引idx_a_c就比较 直观了

❑Column_name：索引列的名称。 

❑Collation：列以什么方式存储在索引中。可以是A或NULL。B+树索 引总是A，即排序的。如果使用了Heap存储引擎，并且建立了Hash索 引，这里就会显示NULL了。因为Hash根据Hash桶存放索引数据，而 不是对数据进行排序。

 ❑Cardinality：非常关键的值，表示索引中唯一值的数目的估计值。 Cardinality表的行数应尽可能接近1，如果非常小，那么用户需要考虑 是否可以删除此索引。 

❑Sub_part：是否是列的部分被索引。如果看idx_b这个索引，这里显 示100，表示只对b列的前100字符进行索引。如果索引整个列，则该字 段为NULL。

 ❑Packed：关键字如何被压缩。如果没有被压缩，则为NULL。 

❑Null：是否索引的列含有NULL值。可以看到idx_b这里为Yes，因为 定义的列b允许NULL值。 

❑Index_type：索引的类型。InnoDB存储引擎只支持B+树索引，所以 这里显示的都是BTREE。 

❑Comment：注释。

`Cardinality`值非常关键，优化器会根据这个值来判断是否使用这个索 引。但是这个值并不是实时更新的，即并非每次索引的更新都会更新 该值，因为这样代价太大了。

**2. Fast Index Creation**

`MySQL 5.5`版本之前（不包括`5.5`）存在的一个普遍被人诟病的问题是` MySQL`数据库对于索引的添加或者删除的这类`DDL`操作，`MySQL`数` `据库的操作过程为：

- ❑首先创建一张新的临时表，表结构为通过命令ALTER TABLE新定义 的结构。 ❑然后把原表中数据导入到临时表。 
- ❑接着删除原表。 
- ❑最后把临时表重名为原来的表名。 

可以发现，若用户对于一张大表进行索引的添加和删除操作，那么这 会需要很长的时间。更关键的是，若有大量事务需要访问正在被修改 的表，这意味着数据库服务不可用。

对于辅助索引的创建，`InnoDB`存储引擎（`1.0.x`版本开始）会对创建索引的表加上一个`S `锁。在创建的过程中，不需要重建表，因此速度较之前提高很多，并` `且数据库的可用性也得到了提高。删除辅助索引操作就更简单了，` InnoDB`存储引擎只需更新内部视图，并将辅助索引的空间标记为可` `用，同时删除`MySQL`数据库内部视图上对该表的索引定义即可。

由于`FIC`在索引的创建的过程中对表加上了`S`锁，因此在创建的过程中` `只能对该表进行读操作，若有大量的事务需要对目标表进行写操作，` `那么数据库的服务同样不可用。此外，`FIC`方式只限定于辅助索引，` `对于主键的创建和删除同样需要重建一张表。

**3. Online Schema Change**

在线架构改变，简称 OSC，实现的一种在线执行DDL的方式

实现OSC步骤如下： 

- ❑init，即初始化阶段，会对创建的表做一些验证工作，如检查表是否 有主键，是否存在触发器或者外键等。 
- ❑createCopyTable，创建和原始表结构一样的新表。 
- ❑alterCopyTable：对创建的新表进行ALTER TABLE操作，如添加索 引或列等。 
- ❑createDeltasTable，创建deltas表，该表的作用是为下一步创建的触发 器所使用。之后对原表的所有DML操作会被记录到createDeltasTable 中。
-  ❑createTriggers，对原表创建INSERT、UPDATE、DELETE操作的触 发器。触发操作产生的记录被写入到deltas表。 
- ❑startSnpshotXact，开始OSC操作的事务。 
- ❑selectTableIntoOutfile，将原表中的数据写入到新表。为了减少对原 表的锁定时间，这里通过分片（chunked）将数据输出到多个外部文 件，然后将外部文件的数据导入到copy表中。分片的大小可以指定， 默认值是500 000。 
- ❑dropNCIndexs，在导入到新表前，删除新表中所有的辅助索引。 
- ❑loadCopyTable，将导出的分片文件导入到新表。 
- ❑replayChanges，将OSC过程中原表DML操作的记录应用到新表中， 这些记录被保存在deltas表中。 
- ❑recreateNCIndexes，重新创建辅助索引。 
- ❑replayChanges，再次进行DML日志的回放操作，这些日志是在上述 创建辅助索引中过程中新产生的日志。 
- ❑swapTables，将原表和新表交换名字，整个操作需要锁定2张表，不 允许新的数据产生。由于改名是一个很快的操作，因此阻塞的时间非 常短。

**4. Online DDL**

虽然FIC可以让InnoDB存储引擎避免创建临时表，从而提高索引创建 的效率。但正如前面小节所说的，索引创建时会阻塞表上的DML操 作。OSC虽然解决了上述的部分问题，但是还是有很大的局限性。 MySQL 5.6版本开始支持Online DDL（在线数据定义）操作，其允许 辅助索引创建的同时，还允许其他诸如INSERT、UPDATE、DELETE 这类DML操作，这极大地提高了MySQL数据库在生产环境中的可用 性。 

此外，不仅是辅助索引，以下这几类DDL操作都可以通过“在线”的方 式进行操作： 

- ❑辅助索引的创建与删除 
- ❑改变自增长值 
- ❑添加或删除外键约束
-  ❑列的重命名

LOCK部分为索引创建或删除时对表添加锁的情况，可有的选择为： 

（1）NONE 执行索引创建或者删除操作时，对目标表不添加任何的锁，即事务仍 然可以进行读写操作，不会收到阻塞。因此这种模式可以获得最大的 并发度。 

（2）SHARE 这和之前的FIC类似，执行索引创建或删除操作时，对目标表加上一 个S锁。对于并发地读事务，依然可以执行，但是遇到写事务，就会 发生等待操作。如果存储引擎不支持SHARE模式，会返回一个错误信 息。 

（3）EXCLUSIVE 在EXCLUSIVE模式下，执行索引创建或删除操作时，对目标表加上 一个X锁。读写事务都不能进行，因此会阻塞所有的线程，这和COPY 方式运行得到的状态类似，但是不需要像COPY方式那样创建一张临 时表。 

（4）DEFAULT DEFAULT模式首先会判断当前操作是否可以使用NONE模式，若不 能，则判断是否可以使用SHARE模式，最后判断是否可以使用 EXCLUSIVE模式。也就是说DEFAULT会通过判断事务的最大并发性 来判断执行DDL的模式。 

InnoDB存储引擎实现Online DDL的原理是在执行创建或者删除操作的 同时，将INSERT、UPDATE、DELETE这类DML操作日志写入到一个 缓存中。待完成索引创建后再将重做应用到表上，以此达到数据的一 致性。

## 5.5 Cardinality值

## 5.6 B+树索引的使用

### 5.6.1 不同应用中树索引的使用

在`OLTP`应用中，查询操作只从数据库中取得一小` `部分数据，一般可能都在`10`条记录以下，甚至在很多时候只取`1`条记` `录，如根据主键值来取得用户信息，根据订单号取得订单的详细信` `息，这都是典型`OLTP`应用的查询语句。在这种情况下，`B`+树索引建` `立后，对该索引的使用应该只是通过该索引取得表中少部分的数据。` `这时建立`B`+树索引才是有意义的，否则即使建立了，优化器也可能选` `择不使用索引。

，在`OLAP`应` `用中，都需要访问表中大量的数据，根据这些数据来产生查询的结` `果，这些查询多是面向分析的查询，目的是为决策者提供支持。如这` `个月每个用户的消费情况，销售额同比、环比增长的情况。因此在` OLAP`中索引的添加根据的应该是宏观的信息，而不是微观，因为最` `终要得到的结果是提供给决策者的。例如不需要在`OLAP`中对姓名字` `段进行索引，因为很少需要对单个用户进行查询。但是对于`OLAP`中` `的复杂查询，要涉及多张表之间的联接操作，因此索引的添加依然是` `有意义的。但是，如果联接操作使用的是`Hash Join`，那么索引可能又` `变得不是非常重要了，所以这需要`DBA`或开发人员认真并仔细地研究` `自己的应用。不过在`OLAP`应用中，通常会需要对时间字段进行索` `引，这是因为大多数统计需要根据时间维度来进行数据的筛选。

### 5.6.2 联合索引

对多个列进行索引。

### 5.6.3 覆盖索引

### 5.6.4 优化器选择不适用索引的情况

### 5.6.5 索引提示

### 5.6.6 Multi-RAnge Read优化

### 5.6.7

## 5.7 哈希算法

### 5.7.1 哈希表

### 5.7.2 InnoDB存储引擎中的哈希算法

`InnoDB`存储引擎使用哈希算法来对字典进行查找，其冲突机制采用链` `表方式，哈希函数采用除法散列方式。对于缓冲池页的哈希表来说，` `在缓冲池中的`Page`页都有一个`chain`指针，它指向相同哈希函数值的` `页。而对于除法散列，`m`的取值为略大于`2`倍的缓冲池页数量的质数。` `例如：当前参数`innodb_buffer_pool_size`的大小为`10M`，则共有`640`个` 16KB`的页。对于缓冲池页内存的哈希表来说，需要分配`640`×`2`=`1280`个` `槽，但是由于`1280`不是质数，需要取比`1280`略大的一个质数，应该是` 1399`，所以在启动时会分配`1399`个槽的哈希表，用来哈希查询所在缓` `冲池中的页。

那么`InnoDB`存储引擎的缓冲池对于其中的页是怎么进行查找的呢？上` `面只是给出了一般的算法，怎么将要查找的页转换成自然数呢？` `其实也很简单，`InnoDB`存储引擎的表空间都有一个`space_id`，用户所` `要查询的应该是某个表空间的某个连续`16KB`的页，即偏移量`offset`。` InnoDB`存储引擎将`space_id`左移`20`位，然后加上这个`space_id`和`offset`，` `即关键字`K`=`space_id＜＜20+space_id+offset`，然后通过除法散列到各` `个槽中去。

### 5.7.3 自适应哈希索引

自适应哈希索引采用之前讨论的哈希表的方式实现。不同的是，这仅` `是数据库自身创建并使用的，`DBA`本身并不能对其进行干预。自适应` `哈希索引经哈希函数映射到一个哈希表中，因此对于字典类型的查找` `非常快速，如`SELECT*FROM TABLE WHERE index_col='xxx'`。但是对于范围查找就无能为力了。

## 5.8 全文检索

### 5.8.1 概述

```mysql
SELECT*FROM blog WHERE content like'xxx%'
```

上面的语句符合B+树索引的要求，可以快速查找

但是不符合用户使用习惯，用户更多的是下面这样的查询

```mysql
SELECT*FROM blog WHERE content like'%xxx%'
```

这样的全文搜索才是应用最广泛的，但是不符合B+树索引，需要进行全扫描

### 5.8.2 倒排索引

全文检索通常使用倒排索引（inverted index）来实现。倒排索引同 B+树索引一样，也是一种索引结构。它在辅助表（auxiliary table）中 存储了单词与单词自身在一个或多个文档中所在位置之间的映射。这 通常利用关联数组实现，其拥有两种表现形式：

❑inverted file index，其表现形式为{单词，单词所在文档的ID}

❑full invertedindex，其表现形式为{单词，(单词所在文档的ID，在具 体文档中的位置)}

### 5.8.3 InnoDB全文检索

`InnoDB`存储引擎从`1.2.x`版本开始支持全文检索的技术，其采用`full inverted index`的方式.在`InnoDB`存储引擎中，将(`DocumentId`，` Position`)视为一个“`ilist`”。因此在全文检索的表中，有两个列，一个是` word`字段，另一个是`ilist`字段，并且在`word`字段上有设有索引。由于`InnoDB`存储引擎在`ilist`字段中存放了`Position`信息，故可以进` `行`Proximity Search`，而`MyISAM`存储引擎不支持该特性。由于`InnoDB`存储引擎在`ilist`字段中存放了`Position`信息，故可以进` `行`Proximity Search`，而`MyISAM`存储引擎不支持该特性。

正如之前所说的那样，倒排索引需要将`word`存放到一张表中，这个表` `称为`Auxiliary Table`（辅助表）。在`InnoDB`存储引擎中，为了提高全文` `检索的并行性能，共有`6`张`Auxiliary Table`，目前每张表根据`word`的` Latin`编码进行分区。

`Auxiliary Table`是持久的表，存放于磁盘上。然而在`InnoDB`存储引擎的` `全文索引中，还有另外一个重要的概念`FTS Index Cache`（全文检索索` `引缓存），其用来提高全文检索的性能。` FTS Index Cache`是一个红黑树结构，其根据（`word`，`ilist`）进行排` `序。这意味着插入的数据已经更新了对应的表，但是对全文索引的更` `新可能在分词操作后还在`FTS Index Cache`中，`Auxiliary Table`可能还没` `有更新。`InnoDB`存储引擎会批量对`Auxiliary Table`进行更新，而不是每` `次插入后更新一次`Auxiliary Table`。当对全文检索进行查询时，` Auxiliary Table`首先会将在`FTS Index Cache`中对应的`word`字段合并到` Auxiliary Table`中，然后再进行查询。这种`merge`操作非常类似之前介` `绍的`Insert Buffer`的功能，不同的是`Insert Buffer`是一个持久的对象，并` `且其是`B`+树的结构。然而`FTS Index Cache`的作用又和`Insert Buffer`是类` `似的，它提高了`InnoDB`存储引擎的性能，并且由于其根据红黑树排序` `后进行批量插入，其产生的`Auxiliary Table`相对较小。

当数据库关闭时，在FTS Index Cache中的数据库会同步到磁盘上的 Auxiliary Table中。然而，如果当数据库发生宕机时，一些FTS Index Cache中的数据库可能未被同步到磁盘上。那么下次重启数据库时，当 用户对表进行全文检索（查询或者插入操作）时，InnoDB存储引擎会 自动读取未完成的文档，然后进行分词操作，再将分词的结果放入到 FTS Index Cache中。

### 5.8.4 全文索引

```
MATCH(col1,col2,...)AGAINST(expr[search_modifier])
search_modifier{

}
```

`AGAINST`指定了使用何种方法去进 行查询。

**1. Natural Language**

全文检索通过MATCH函数进行查询，默认采用Natural Language模 式，其表示查询带有指定word的文档。

对于没有全文索引的文档可以使用

```mysql
SELECT * FROM fts WHERE body LIKE '%pease%'
```

如果使用了全文索引技术，则可以

```mysql
SELECT * FROM fts WHERE MATCH(body) AGAINST('pease' IN NATURAL LANGUAGE MODE);
```

在WHERE条件中使用MATCH函数，查询返回的结果是根据相关性 （Relevance）进行降序排序的，即相关性最高的结果放在第一位。相 关性的值是一个非负的浮点数字，0表示没有任何的相关性。

相关性计算方式：

- word 是否在文档中出现
- word 在文档中出现的次数
- word 在索引列中的数量
- 多少个文档包含该 word

对于 `InnoDB` 存储引擎的全文检索，还需要考虑以下的因素：

- 查询的 word 在 stopword 列中，忽略该字符串的查询
- 查询的 word 的字符长度是否在区间 [innodb_ft_min_token_size, innodb_ft_min_token_size]内
- 如果词在 `stopword` 中，则不对该词进行查询

**2. Boolean**

`IN BOOLEAN MODE`模式进行全文检索

```mysql
SELECT * FROM fts WHERE MATCH(body) AGAINST('+pease-hot' IN BOOLEAN MODE);
```

代表有 `pease` 没有 `hot` 的文档

操作符：

- `+` 表示一定存在
- `-` 表示一定不存在
- 没有操作符代表可有可无
- `@distance`表示查询的多个单词之间的距离是否在 `distance` 之内，单位是字节
- `>` 表示出现该单词时增加相关性
- `<` 表示出现该单词时降低相关性
- `~` 表示允许出现该单词，但是出现时相关性为负
- `*` 表示以该单词开头的单词
- `''` 表示短语

**3. Query Expansion**

# 第6章 锁

## 6.1 什么是锁



## 6.2 lock与latch

latch 是轻量级锁，因为其要求锁定的时间必须非常短。若持续时间长，则应用的性能会非常差。在 InnoDB 引擎中，latch 又分为 mutex（互斥量）和 rwlock（读写锁）。其目的是用来保证并发线程操作临界资源的正确性，并且通常没有死锁检测的机制。

lock 的对象是事务，用来锁定的是数据库中的对象，如表、页、行。并且一般 lock 的对象仅在事务 commit 或 rollback 后进行释放（不同事物隔离级别释放的时间可能不同）。

<table>
    <tr>
    	<th></th>
        <th>lock</th>
        <th>latch</th>
    </tr>
    <tr>
    	<td>对象</td>
        <td>事务</td>
        <td>线程</td>
    </tr>
    <tr>
    	<td>保护</td>
        <td>数据库内容</td>
        <td>内存数据结构</td>
    </tr>
    <tr>
    	<td>持续时间</td>
        <td>整个事务过程</td>
        <td>临界资源</td>
    </tr>
    <tr>
    	<td>模式</td>
        <td>行锁、表锁、意向锁</td>
        <td>读写锁、互斥量</td>
    </tr>
    <tr>
    	<td>死锁</td>
        <td>通过watis-for graph、time out</td>
        <td>无死锁检测与处理机制。仅通过应用程序加锁的顺序保证无死锁的情况发生</td>
    </tr>
        <td>存在于</td>
        <td>Lock Manager的哈希表中</td>
        <td>每个数据结构的对象中</td>
    </tr>
</table>

## 6.3 InnoDB存储引擎中的锁

### 6.3.1 锁的类型

`InnoDB`存储引擎实现了如下两种标准的行级锁：

- 共享锁（S Lock），允许事务读一行数据
- 排他锁（X Lock），允许事务删除或更新一行数据

如果一个事务`T1`已经获得了行`r`的共享锁，那么另外的事务`T2`可以立` `即获得行`r`的共享锁，因为读取并没有改变行`r`的数据，称这种情况为` `锁兼容（`Lock Compatible`）。

但若有其他的事务`T3`想获得行`r`的排他` `锁，则其必须等待事务`T1`、`T2`释放行`r`上的共享锁——这种情况称为锁不兼容。

`InnoDB`存储引擎支持多粒度（`granular`）锁定，这种锁定允许事务在行级上的锁和表级上的锁同时存在。为了支持在不同粒度上进行加锁操作，`InnoDB`存储引擎支持一种额外的锁方式，称之为意向锁（Intention Lock）。意向锁是将锁定的对象分为多个层次，意向锁意味着事务希望在更细粒度上进行加锁

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/MySQL/%E6%8A%80%E6%9C%AF%E5%86%85%E5%B9%95/%E5%B1%82%E6%AC%A1%E7%BB%93%E6%9E%84.png)

- 如果需要对页上的记录`r`进行上`X`锁，那么分别需要对数据库`A`、` `表、页上意向锁`IX`，最后对记录`r`上`X`锁。举例来说，在对记录`r`加`X `锁之前，已经有事务对表`1`进行了`S`表锁，那么表`1`上已存在`S`锁，之后` `事务需要对记录`r`在表`1`上加上`IX`，由于不兼容，所以该事务需要等待` `表锁操作的完成。

  -` `意向共享锁（`IS Lock`），事务想要获得一张表中某几行的共享锁
  -` `意向排他锁（`IX Lock`），事务想要获得一张表中某几行的排他锁

### 6.3.2 一致性非锁定读

一致性的非锁定读（`consistent nonlocking read`）是指`InnoDB`存储引擎` `通过行多版本控制（`multi versioning`）的方式来读取当前执行时间数` `据库中行的数据。如果读取的行正在执行`DELETE`或`UPDATE`操作，` `这时读取操作不会因此去等待行上锁的释放。相反地，`InnoDB`存储引` `擎会去读取行的一个快照数据。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/MySQL/%E6%8A%80%E6%9C%AF%E5%86%85%E5%B9%95/%E4%B8%80%E8%87%B4%E6%80%A7%E9%9D%9E%E9%94%81%E5%AE%9A%E8%AF%BB.png)

之所以称为非锁定读，因为不需要等待访问的行上的X锁的释放

快照数据是该行之前版本的数据，该实现是通过`undo`段来完成，`undo`用来在事务中回滚数据，因此快照数据本身是没有额外开销的。读取快照数据不需要上锁，因为没有事务需要对历史数据进行修改。

每行数据都有隐藏的字段：

- ROW ID
- 事务 ID
- 回滚指针：指向执行更新操作前的最后一个历史版本

非锁定一致性读是 `InnoDB`引擎的默认读取方式，在不同事务隔离级别下，读取的方式不同，并不是在 每个事务隔离级别下都是采用非锁定的一致性读。

在事务隔离级别READ COMMITTED和REPEATABLE READ（InnoDB 存储引擎的默认事务隔离级别）下，InnoDB存储引擎使用非锁定的一 致性读。然而，对于快照数据的定义却不相同。**在READ COMMITTED事务隔离级别下，对于快照数据，非一致性读总是读取 被锁定行的最新一份快照数据。而在REPEATABLE READ事务隔离级 别下，对于快照数据，非一致性读总是读取事务开始时的行数据版 本。**

<table>
    <tr>
        <th></th>
    	<th>事务A</th>
        <th>事务B</th>
        <th>解释</th>
    <tr>
    <tr>
        <td>1</td>
        <td>BEGIN</td>
        <td></td>
        <td>开始事务A</td>
    </tr>
    <tr>
    	<td>2</td>
        <td>SELECT</td>
        <td></td>
        <td>事务A获得数据id=1</td>
    </tr>
    <tr>
    	<td>3</td>
        <td></td>
        <td>BEGIN</td>
        <td>开启事务B</td>
    </tr>
    <tr>
    	<td>4</td>
        <td></td>
        <td>SET</td>
        <td>事务B将id设置为3</td>
    </tr>
    <tr>
    	<td>5</td>
        <td>SELECT</td>
        <td></td>
        <td>事务A获得数据id=1，事务B没有提交，因此还是id=1</td>
    </tr>
    <tr>
    	<td>6</td>
        <td></td>
        <td>COMMIT</td>
        <td>事务B提交</td>
    </tr>
    <tr>
    	<td>7</td>
        <td>SELECT</td>
        <td></td>
        <td>在READCOMMITTED级别下获取最新结果，得到3，在REPEATABLE级别下获得旧结果，得到1</td>
    </tr>
    <tr>
    	<td>8</td>
        <td>COMMIT</td>
        <td></td>
        <td></td>
    </tr>
</table>
### 6.3.3 一致性锁定读

`InnoDB`存储引擎对于 `SELECT`语句支持两种一致性的锁定读（`locking read`）操作：

- `SELECT ... FOR UPDATE`
- `SELECT ... LOCK IN SHARE MODE`

`SELECT ... FOR UPDATE`对行加一个排他锁，其他事务不能对已锁定的行加上任何锁。

`SELECT ... LOCK IN SHARE MODE`对读取行记录加一个共享锁，其他事物可以向被锁定的行加共享锁，但是如果加排他锁会被阻塞。

对于一致性非锁定读，即时使用了上面的查询语句也可以进行读取（[参照6.3.2章节](# 6.3.2 一致性非锁定读)）

### 6.3.4 自增长与锁

插入操作会依据这个自增长的计数器值加`1`赋予自增长列。这个实现方` `式称做`AUTO-INC Locking`。这种锁其实是采用一种特殊的表锁机制，` `为了提高插入的性能，锁不是在一个事务完成后才释放，而是在完成` `对自增长值插入的`SQL`语句后立即释放。

虽然`AUTO-INC Locking`从一定程度上提高了并发插入的效率，但还是` `存在一些性能上的问题。首先，对于有自增长值的列的并发插入性能` `较差，事务必须等待前一个插入的完成（虽然不用等待事务的完` `成）。其次，对于`INSERT`…`SELECT`的大数据量的插入会影响插入的` `性能，因为另一个事务中的插入会被阻塞。

从`MySQL 5.1.22`版本开始，`InnoDB`存储引擎中提供了一种轻量级互斥` `量的自增长实现机制，这种机制大大提高了自增长值插入的性能。并` `且从该版本开始，`InnoDB`存储引擎提供了一个参数` innodb_autoinc_lock_mode`来控制自增长的模式

<table>
    <tr>
    	<th>插入类型</th>
        <th>说明</th>
    </tr>
    <tr>
    	<td>insert-like</td>
        <td>所有插入语句，INSERT,REPLACE,INSERT...SELECT,REPLACE...SELECT,LOAD DATA</td>
    </tr>
    <tr>
    	<td>simple inserts</td>
    	<td>插入前已经确定的插入语句：INSERT,REPLACE，不包括INSERT...ON DUPLICATE KEY UPDATE这类</td>
    </tr>
    <tr>
    	<td>bulk inserts</td>
    	<td>插入前不能确定的语句：INSERT...SELECT,REPLACE...SELECT,LOAD DATA</td>
    </tr>
    <tr>
    	<td>mixed-mode inserts</td>
    	<td>插入中有一部分是值自增长的，有一部分是确定的</td>
    </tr>
</table>

`innodb_autoinc_lock_mode`参数说明

<table>
    <tr>
    	<th>值</th>
        <th>说明</th>
    </tr>
    <tr>
    	<td>0</td>
        <td>5.1.22之前的实现方式，AUTO-INC Locking方式，新版不推荐使用</td>
    </tr>
    <tr>
    	<td>1</td>
    	<td>对于'simple insert'该值使用互斥量，对'bulk'使用传统AUTO-INC</td>
    </tr>
    <tr>
    	<td>2</td>
    	<td>对所有INSERT-like使用互斥量方式，但是每次插入时，由于并发，自增长值可能不是连续的。基于该模式不能使用Statement-Base Replication，应该使用row-base replication</td>
    </tr>
</table>

### 6.3.5 外键和锁

对于外键值的插入或更新，首先需要查询父表中的记录，即`SELECT `父表。但是对于父表的`SELECT`操作，不是使用一致性非锁定读的方` `式，因为这样会发生数据不一致的问题，因此这时使用的是` SELECT…LOCK IN SHARE MODE`方式，即主动对父表加一个`S`锁。` `如果这时父表上已经这样加`X`锁，子表上的操作会被阻塞

<table>
    <tr>
    	<th>时间</th>
        <th>会话A</th>
        <th>会话B</th>
    </tr>
    <tr>
    	<td>1</td>
        <td>BEGIN</td>
        <td></td>
    </tr>
    <tr>
    	<td>2</td>
    	<td>DELETE FROM parent WHERE id=3</td>
        <td></td>
    </tr>
    <tr>
    	<td>3</td>
    	<td></td>
        <td>BEGIN</td>
    </tr>
    <tr>
    	<td>4</td>
    	<td></td>
        <td>INSERT INTO child SELECT 2,3(第二列是外键，该语句被阻塞)</td>
    </tr>    
</table>

在上面的例子中，两个会话都没有提交或者回滚，而会话`B`会被阻塞。因为`id=3`的父表在会话`A`中已经加了一个`X`锁，而此时会话`B`中又需要加一个`S`锁，这时候的`INSERT`会被阻塞。

如果访问父表时是一个一致性的非锁定读，这时会话`B`会读到父表有`id=3`的记录，如果`A`提交，父表中就不存在了。数据在父、子表存在不一致的情况。

也就是说有从表时尽量不要给主表加X锁，避免影响并发性能。

## 6.4 锁的算法

### 6.4.1 行锁的三种算法

`InnoDB`存储引擎有`3`种行锁的算法，其分别是：

- ❑`Record Lock`：单个行记录上的锁
- ❑`Gap Lock`：间隙锁，锁定一个范围，但不包含记录本身` `
- ❑`Next-Key Lock`∶`Gap Lock+Record Lock`，锁定一个范围，并且锁定记` `录本身` 

`Record Lock`总是会去锁住索引记录，如果`InnoDB`存储引擎表在建立的` `时候没有设置任何一个索引，那么这时`InnoDB`存储引擎会使用隐式的` `主键来进行锁。

`Next-Key Lock`是结合了`Gap Lock`和`Record Lock`的一种锁定算法，在` Next-Key Lock`算法下，`InnoDB`对于行的查询都是采用这种锁定算法。

`Gap Lock `和` Next-Key Lock `是为了解决幻读，阻止多个事务将记录插入到同一个范围

当` Next-Key Lock `之锁定唯一索引（唯一索引由多个列组成，仅查找其中一个不会退化）时会退化为` Record Lock`

举例：

```mysql
CREATE TABLE z(a INT, b INT, PRIMARY KEY(a),KEY(b));
INSERT INTO z SELECT 1,1
INSERT INTO z SELECT 3,1
INSERT INTO z SELECT 5,3
INSERT INTO z SELECT 7,6
INSERT INTO z SELECT 10,8
```

执行

```mysql
SELECT * FROM z WHERE b=3 FOR UPDATE
```

对`b`列进行索引，因为使用`Next-Key`加锁，且有两个索引，需要分别锁定。

对于聚集索引，`a=5`只有一条，所以从`Next-Key`退化为`Record Lock`。对于辅助索引，会加上`Next-Key Lock`，锁定范围(`1,3`),(`3,6`)

此时会话`B`再执行

```mysql
SELECT * FROM z WHERE a=5 LOCK IN SHARE MODE;
INSERT INTO z SELECT 4,2
INSERT INTO z SELECT 6,5
```

第一条由于加了排他锁，会阻塞；第二条和第三条在`Next-Key`锁定范围也会阻塞

如果没有范围锁定，会话`B`还可以插入`b=3`的行记录，导致会话`A`查询的值不正确，导致了幻读。

用户可以使用下面的方式关闭 Gap Lock:

- 事务隔离级别设置为 READ COMMITTED
- 将参数 innodb_locks_unsafe_for_binlog 设置为1

在上述的配置下，除了外键约束和唯一性检查依然需要的Gap Lock， 其余情况仅使用Record Lock进行锁定。但需要牢记的是，上述设置破 坏了事务的隔离性，并且对于replication，可能会导致主从数据的不一 致。此外，从性能上来看，READ COMMITTED也不会优于默认的事 务隔离级别READ REPEATABLE。 

在InnoDB存储引擎中，对于INSERT的操作，其会检查插入记录的下 一条记录是否被锁定，若已经被锁定，则不允许查询。

### 6.4.2 解决Phantom Problem

在默认的隔离级别 REPEATABLE READ 下，InnoDB 采用默认的 Next-Key Locking 机制避免 Phantom Problem。

Phantom Problem 是指在同一事物下，连续两次执行同样 SQL 可能导致不同的结果，第二次执行可能返回之前不存在的行

举例：

表 t 由 1，2，5 三个值组成，事务 T1 执行

```mysql
SELECT * FROM t WHERE a>2 FOR UPDATE
```

`T1 `还没有提交，上述的结果应该返回` 5.`如果同时事务` T2 `插入了` 4 `这个值（提交），那么` T1 `再次执行就会返回` 4`，`5`，当前事务看到了其他事务的结果，违反了隔离性。

如果采用了` Next-Key Locking `算法锁定了$(2,+\infty)$这个范围，因此` T2 `插入` 4 `会阻塞，避免了幻读

`InnoDB`存储引擎默认的事务隔离级别是`REPEATABLE READ`，在该隔` `离级别下，其采用`Next-Key Locking`的方式来加锁。而在事务隔离级别` READ COMMITTED`下，其仅采用`Record Lock`，因此在上述的示例` `中，会话`A`需要将事务的隔离级别设置为`READ COMMITTED`（**这里的读提交可能是写错了，应该是可重复读**）。

## 6.5 锁问题

- 脏读
- 幻读
- 不可重复读
- 第一类丢失更新
- 第二类丢失更新

### 6.5.1 脏读

脏数据是指事务对缓冲池中的数据进行了修改，但是还没有提交。

脏读的一个重要原因是隔离级别是 **READ UNCOMMITTED**

**脏读：一个事务读到了另一个事务未提交的数据，违反了隔离性。**

<table>
    <tr>
    	<th>时间</th>
    	<th>会话A</th>
        <th>会话B</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>1</td>
    	<td>SET @@tx_isolation='read-ncommitted'</td>
    	<td></td>
        <td></td>
    </tr>
    <tr>
        <td>2</td>
    	<td></td>
    	<td>SET @@tx_isolation='read-ncommitted'</td>
        <td></td>
    </tr>
    <tr>
        <td>3</td>
    	<td>BEGIN</td>
    	<td>BEGIN</td>
        <td></td>
    </tr>
    <tr>
        <td>4</td>
    	<td></td>
    	<td>SELECT * FROM t</td>
        <td>查询到 1</td>
    </tr>
    <tr>
        <td>5</td>
    	<td>INSERT INTO t SELECT 2</td>
    	<td></td>
        <td></td>
    </tr>
    <tr>
        <td>6</td>
    	<td></td>
    	<td>SELECT * FROM t</td>
        <td>查询到 1,2</td>
    </tr>
</table>

会话 B 事务第二次查询查询到了会话 A 的未提交数据，使得两次查询结果不同。

**解决：隔离级别提高到 READ COMMITTED**

### 6.5.2 不可重复读

不可重复读的重要条件是两个会话的隔离级别是 **READ COMMITTED**

**不可重复读：一个事务读到了另一个事务提交的数据，违反了隔离性。和脏读的区别就是会话2是否提交。**

<table>
    <tr>
    	<th>时间</th>
    	<th>会话A</th>
        <th>会话B</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>1</td>
    	<td>SET @@tx_isolation='read-committed'</td>
    	<td></td>
        <td></td>
    </tr>
    <tr>
        <td>2</td>
    	<td></td>
    	<td>SET @@tx_isolation='read-committed'</td>
        <td></td>
    </tr>
    <tr>
        <td>3</td>
    	<td>BEGIN</td>
    	<td>BEGIN</td>
        <td></td>
    </tr>
    <tr>
        <td>4</td>
    	<td>SELECT * FROM t</td>
    	<td></td>
        <td>查询到 1</td>
    </tr>
    <tr>
        <td>5</td>
    	<td></td>
    	<td>INSERT INTO t SELECT 2</td>
        <td>插入数据 2</td>
    </tr>
    <tr>
        <td>6</td>
    	<td></td>
    	<td>COMMIT</td>
        <td></td>
    </tr>
    <tr>
        <td>7</td>
    	<td>SELECT * FROM t</td>
    	<td></td>
        <td>查询到 1,2</td>
    </tr>
</table>

会话 A 事务查询到了 会话 B 事务提交的数据，使得两次查询结果不同。

一般来说，不可重复读的问题是可以接受的，因为其读到的是已经提交的数据。

草，幻读和不可重读的区别简单说就是 **幻读偏向插入数据，不可重复读偏向更新数据**

好多地方喜欢分开

单纯的READ REPEATABLE可以解决不可重复读，不能解决幻读，加Next-Key Lock

**解决：隔离级别提高到 READ REPEATABLE，并且采用 Next-Key Lock 锁算法**

### 6.5.3 丢失更新

**丢失更新分为两种情况，一种是回滚丢失，一种是提交丢失**

<table>
    <tr>
    	<th>时间</th>
    	<th>会话A</th>
        <th>会话B</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>1</td>
    	<td>BEGIN</td>
    	<td>BEGIN</td>
        <td></td>
    </tr>
    <tr>
        <td>2</td>
    	<td>SELECT * FROM t</td>
    	<td>SELECT * FROM t</td>
        <td>查询为 100</td>
    </tr>
    <tr>
        <td>3</td>
    	<td></td>
    	<td>UPDATE 200</td>
        <td>更新为200</td>
    </tr>
    <tr>
        <td>4</td>
    	<td></td>
    	<td>COMMIT</td>
        <td></td>
    </tr>
    <tr>
        <td>5</td>
    	<td>ROLLBACK</td>
    	<td></td>
        <td></td>
    </tr>
    <tr>
        <td>6</td>
    	<td>SELECT * FROM t</td>
    	<td></td>
        <td>查询到 恢复为100，使得事务2的更新丢失</td>
    </tr>
</table>

<table>
    <tr>
    	<th>时间</th>
    	<th>会话A</th>
        <th>会话B</th>
        <th>说明</th>
    </tr>
    <tr>
        <td>1</td>
    	<td>BEGIN</td>
    	<td>BEGIN</td>
        <td></td>
    </tr>
    <tr>
        <td>2</td>
    	<td>SELECT * FROM t</td>
    	<td>SELECT * FROM t</td>
        <td>查询为 100</td>
    </tr>
    <tr>
        <td>3</td>
    	<td></td>
    	<td>UPDATE 200</td>
        <td>更新为200</td>
    </tr>
    <tr>
        <td>4</td>
    	<td></td>
    	<td>COMMIT</td>
        <td></td>
    </tr>
    <tr>
        <td>5</td>
    	<td>UPDATE 100</td>
    	<td></td>
        <td>更新为300</td>
    </tr>
    <tr>
        <td>6</td>
    	<td>COMMIT</td>
    	<td></td>
        <td></td>
    </tr>
    <tr>
        <td>7</td>
    	<td>SELECT * FROM t</td>
    	<td></td>
        <td>查询到变为100，使得事务2的更新丢失</td>
    </tr>
</table>

丢失更新就是一个事务的提交或者回滚，使得另一个事务的提交结果被覆盖

**解决：加行锁，在事务A进行的时候，事务B的更新操作被阻塞**

## 6.6 阻塞

## 6.7 死锁

## 6.7.1 死锁的概念

解决死锁的方法：

- 事务超时时间
- wait-for graph（等待图）

第一种方法，在事务超时之后事务进行回滚，但是如果超时的事务权重比较大，占用了很多 undo log，回滚该事务可能比回滚另一个事务要占用更多时间

第二种方法，需要保存锁的信息链表和事务等待链表，这两个链表可以构成一个图，如果有回路，则说明存在死锁。事务为图的节点，事务 T1 指向 T2 边的定义为：

- 事务 T1 等待事务 T2 所占用的资源
- 事务 T1 最终等待 T2 所占用的资源，也就是事务之间在等待相同的资源，而事务 T1 发生在事务 T2 的后面

InnoDB 通常选择回滚 undo 量最小的事务



### 6.7.2 死锁的概率

### 6.7.3 死锁示例



## 6.8 锁升级

锁升级是指将当前锁的粒度降低。

会触发锁升级的情况：

- 由一条单独的 SQL 语句在一个对象上持有的锁的数量超过了阈值
- 所资源占用的内存超过了激活内存的 40% 时



# 第7章 事务

事务的特性（ACID）：

- 原子性 (atomicity)
- 一致性 (consistency)
- 隔离性 (isolation)
- 持久性 (durability)

## 7.1 认识事务

### 7.1.1 概述

事务：要么都完成，要么都撤销

**原子性：**指整个数据库事务是不可分割的工作单位。只有使事务中所有的数据库操作都执行成功，才算整个事务成功。事务中任何一个 SQL 语句执行失败，已经执行成功的 SQL 语句也必须撤销，数据可状态应该退回到执行事务前的状态。

**一致性：**一致性指事务将数据库从一种状态转换为另一种状态。在事务开始之前和结束之后，数据库的完整性约束没有遭到破坏。事务的原子性保障了一致性。

**隔离性：**事务的隔离性要求每个读写事务的对象对其他事务的操作对象能相互隔离，即该事务提交前对其他事物都不可见，通常用锁来实现。

**持久性：**事务一旦提交，其结果就是永久性的，宕机后是可恢复的。



### 7.1.2 分类

从事务理论的角度，可以把事务分为以下集中类型：

- 扁平事务
- 带有保存点的扁平事务
- 链事务
- 嵌套事务
- 分布式事务

**扁平事务**是事务类型中最简单的一种，也可能是实际生产环境中使用最频繁的一种。所有操作都处在同一个层次，由 BEGIN WORK 开始，由 COMMIT WORK 或 ROLLBACK WORK 结束，这里面的操作是原子性的，要么都执行，要么都回滚。

扁平事务的主要限制是不能提交或者回滚事务的某一部分，或者分几个步骤提交。

举例，假设一个度假事务：

BEGIN WORK

S1 : 预定杭州到上海的高铁

S2 : 上海浦东飞米兰，预定去米兰航班

S3 : 在米兰转火车到佛罗伦萨，预定佛罗伦萨车票

但是执行到` S3 `发现没有当天的火车票了，无奈只能在米兰停留一晚。如果是扁平化事务，则需要回滚到杭州，从杭州再执行` S1`，`S2`。因此出现了带有保存点的扁平事务。

**带有保存点的扁平事务**，除了支持` `扁平事务支持的操作外，允许在事务执行过程中回滚到同一事务中较` `早的一个状态。这是因为某些事务可能在执行过程中出现的错误并不` `会导致所有的操作都无效，放弃整个事务不合乎要求，开销也太大。` `保存点（`Savepoint`）用来通知系统应该记住事务当前的状态，以便当` `之后发生错误时，事务能回到保存点当时的状态。` `

保存点用`SAVE WORK`函数来建立，通知系统记录当前的处理状态。` `当出现问题时，保存点能用作内部的重启动点，根据应用逻辑，决定` `是回到最近一个保存点还是其他更早的保存点。

保存点编号是持续递增的，假设已经经过了保存点` 4`，进行回滚到保存点` 2`，下一个保存点的编号应该是` 5`，而不是` 3`

**链事务**可视为保存点模式的一种变种。带有保 存点的扁平事务，当发生系统崩溃时，所有的保存点都将消失，因为 其保存点是易失的（volatile），而非持久的（persistent）。这意味着 当进行恢复时，事务需要从开始处重新执行，而不能从最近的一个保 存点继续执行。

链事务的思想是：在提交一个事务时，释放不需要的数据对象，将必 要的处理上下文隐式地传给下一个要开始的事务。注意，提交事务操 作和开始下一个事务操作将合并为一个原子操作。这意味着下一个事 务将看到上一个事务的结果，就好像在一个事务中进行的一样。第一个事务的提交操作触发第二个事务的开始

链事务与带有保存点的扁平事务不同的是，带有保存点的扁平事务能 回滚到任意正确的保存点。而链事务中的回滚仅限于当前事务，即只 能恢复到最近一个的保存点。对于锁的处理，两者也不相同。链事务 在执行COMMIT后即释放了当前事务所持有的锁，而带有保存点的扁 平事务不影响迄今为止所持有的锁。

**嵌套事务**是一个层次结构框架，由一个顶层事务控制各个层次的事务

子事务提交后并没有真正生效，需要等待父事务的提交才能生效；父事务的回滚回导致所有已经提交的子事务一同发生回滚。在 Moss 的理论中，高层事务只负责逻辑控制，只有叶子事务才进行数据库访问，发送消息，获取其他类型资源等。

嵌套事务中，父事务持有的锁可以选择任意个传递给子事务，子事务获得新的锁之后，父事务也将持有该锁，并能够给另一个子事务传递该锁

**分布式事务**通常是一个在分布式环境下运行的扁平事务，同一个事务需要访问两个不同的数据库。



## 7.2 事务的实现

事务的隔离性由锁来实现，原子性、一致性、持久性通过数据库的` redo log `和` undo log `来完成。` redo log `为重做日志，用来保证事务的原子性和持久性。` undo log `用来保证事务的一致性。

`redo `恢复提交事务修改的页操作，`undo `回滚记录到某个特定版本。`redo `通常是物理日志，记录的是页的物理修改操作。` undo `是逻辑日志，根据每行记录进行记录。



### 7.2.1 redo

**1. 基本概念**

重做日志用来实现事务的持久性，其由两部分组成：一是内存中的重做日志缓冲（`redo log buffer`），其是易失的

二是重做日志文件（`redo log file`），是持久的。

**每执行一条DML语句，先进行redo log buffer的写入，再在某个时间点写入redo log file，这就是WAL（write ahead logging）**

`InnoDB`是事务的存储引擎，其通过`Force Log at Commit`机制实现事务` `的持久性，即当事务提交（`COMMIT`）时，必须先将该事务的所有日志写入到重做日志文件进行持久化，待事务的`COMMIT`操作完成才算` `完成。这里的日志是指重做日志，在`InnoDB`存储引擎中，由两部分组` `成，即`redo log`和`undo log`。`redo log`用来保证事务的持久性，`undo log `用来帮助事务回滚及(多版本并发控制)`MVCC`的功能。`redo log`基本上都是顺序写的，在数据库运行时不需要对`redo log`的文件进行读取操作。而`undo log`是需要进行随机读写的。

为了确保每次日志都写入重做日志文件，**在每次将重做日志缓冲写入重做日志文件后(我觉得这里说的是数据写入到redo log buffer后)，`InnoDB`存储引擎都需要调用一次`fsync`操作**。由于重做日志文件打开并没有使用`O_DIRECT`选项，因此重做日志缓冲先写入文件系统缓存。为了确保重做日志写入磁盘，必须进行一次`fsync`操作。由于`fsync`的效率取决于磁盘的性能，因此磁盘的性能决定了事务提交的性能，也就是数据库的性能。

`InnoDB`存储引擎允许用户手工设置非持久性的情况发生，以此提高数据库的性能。即**当事务提交时，日志不写入重做日志文件，而是等待一个时间周期后再执行`fsync`操作。**由于并非强制在事务提交时进行一次`fsync`操作，显然这可以显著提高数据库的性能。但是当数据库发生` `宕机时，由于部分日志未刷新到磁盘，因此会丢失最后一段时间的事` `务。

在`MySQL`数据库中还有一种二进制日志（`binlog`），其用来进行` POINT`-`IN`-`TIME`（`PIT`）的恢复及主从复制（`Replication`）环境的建` `立。从表面上看其和重做日志非常相似，都是记录了对于数据库操作` `的日志。然而，从本质上来看，两者有着非常大的不同。` `首先，重做日志是在`InnoDB`存储引擎层产生，而二进制日志是在` MySQL`数据库的上层产生的，并且二进制日志不仅仅针对于`InnoDB`存` `储引擎，`MySQL`数据库中的任何存储引擎对于数据库的更改都会产生` `二进制日志。` `其次，两种日志记录的内容形式不同。`MySQL`数据库上层的二进制日` `志是一种逻辑日志，其记录的是对应的`SQL`语句。而`InnoDB`存储引擎` `层面的重做日志是物理格式日志，其记录的是对于每个页的修改。二进制日` `志只在事务提交完成后进行一次写入。而`InnoDB`存储引擎的重做日志` `在事务进行中不断地被写入，这表现为日志并不是随事务提交的顺序` `进行写入的

**2. log block**

在`InnoDB`存储引擎中，重做日志都是以`512`字节进行存储的。这意味着重做日志缓存、重做日志文件都是以块（`block`）的方式进行保存的，称之为重做日志块（`redo log block`），每块的大小为`512`字节。若一个页中产生的重做日志数量大于`512`字节，那么需要分割为多个重做日志块进行存储。此外，由于重做日志块的大小和磁盘扇区大小一样，都是`512`字节，因此重做日志的写入可以保证原子性，不需要` doublewrite`技术。

重做日志块除了日志本身之外，还由日志块头（`log block header`）及日志块尾（`log block tailer`）两部分组成。重做日志头一共占用`12`字节，重做日志尾占用`8`字节。故每个重做日志块实际可以存储的大小为` 492`字节（`512-12-8`）。

**3.log group**

log group为重做日志组，其中有多个重做日志文件。虽然源码中已支 持log group的镜像功能，但是在ha_innobase.cc文件中禁止了该功能。 因此InnoDB存储引擎实际只有一个log group。

log group是一个逻辑上的概念，并没有一个实际存储的物理文件来表 示log group信息。log group由多个重做日志文件组成，每个log group 中的日志文件大小是相同的

重做日志文件中存储的就是之前在log buffer中保存的log block，因此 其也是根据块的方式进行物理存储的管理，每个块的大小与log block 一样，同样为512字节。在InnoDB存储引擎运行过程中，log buffer根 据一定的规则将内存中的log block刷新到磁盘。这个规则具体是： 

❑事务提交时 

❑当log buffer中有一半的内存空间已经被使用时

 ❑log checkpoint时 

对于log block的写入追加（append）在redo log file的最后部分，当一个 redo log file被写满时，会接着写入下一个redo log file，其使用方式为 round-robin。

虽然log block总是在redo log file的最后部分进行写入，有的读者可能 以为对redo log file的写入都是顺序的。其实不然，因为redo log file除 了保存log buffer刷新到磁盘的log block，还保存了一些其他的信息， 这些信息一共占用2KB大小，即每个redo log file的前2KB的部分不保 存log block的信息。对于log group中的第一个redo log file，其前2KB的 部分保存4个512字节大小的块

|      名称       | 大小字节 |
| :-------------: | :------: |
| log file header |   512    |
|   checkpoint1   |   512    |
|       空        |   512    |
|   checkpoint2   |   512    |

需要特别注意的是，上述信息仅在每个log group的第一个redo log file 中进行存储。log group中的其余redo log file仅保留这些空间，但不保 存上述信息。正因为保存了这些信息，就意味着对redo log file的写入 并不是完全顺序的。因为其除了log block的写入操作，还需要更新前 2KB部分的信息，这些信息对于InnoDB存储引擎的恢复操作来说非常 关键和重要。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/MySQL/%E6%8A%80%E6%9C%AF%E5%86%85%E5%B9%95/log.png)

在log filer header后面的部分为InnoDB存储引擎保存的checkpoint（检 查点）值，其设计是交替写入，这样的设计避免了因介质失败而导致 无法找到可用的checkpoint的情况。

**4. 重做日志格式**

不同的数据库操作会有对应的重做日志格式。此外，由于InnoDB存储 引擎的存储管理是基于页的，故其重做日志格式也是基于页的。虽然 有着不同的重做日志格式，但是它们有着通用的头部格式，

重做日志格式：

<table>
    <tr>
    	<td>redo_log_type</td>
    	<td>space</td>
    	<td>page_no</td>
    	<td>redo log body</td>
    </tr>
</table>

通用头部格式由以下3部分组成：

- redo_log_type : 重做日志的类型
- space : 表空间的ID
- page_no : 页的偏移量

**5. LSN**

`LSN`是`Log Sequence Number`的缩写，其代表的是日志序列号。在` InnoDB`存储引擎中，`LSN`占用`8`字节，并且单调递增。`LSN`表示的含义有：

❑重做日志写入的总量

❑`checkpoint`的位置

❑页的版本

`LSN`表示事务写入重做日志的字节的总量。例如当前重做日志的`LSN `为`1 000`，有一个事务`T1`写入了`100`字节的重做日志，那么`LSN`就变为了`1100`，若又有事务`T2`写入了`200`字节的重做日志，那么`LSN`就变为了` 1 300`。可见`LSN`记录的是重做日志的总量，其单位为字节。

`LSN`不仅记录在重做日志中，还存在于每个页中。在每个页的头部，` `有一个值`FIL_PAGE_LSN`，记录了该页的`LSN`。在页中，`LSN`表示该` `页最后刷新时`LSN`的大小。因为重做日志记录的是每个页的日志，因` `此页中的`LSN`用来判断页是否需要进行恢复操作。例如，页`P1`的`LSN `为`10 000`，而数据库启动时，`InnoDB`检测到写入重做日志中的`LSN`为` 13 000`，并且该事务已经提交，那么数据库需要进行恢复操作，将重` `做日志应用到`P1`页中。同样的，对于重做日志中`LSN`小于`P1`页的` LSN`，不需要进行重做，因为`P1`页中的`LSN`表示页已经被刷新到该位` `置。

`Log sequence number`表示当前的`LSN`，`Log flushed up to`表示刷新到重做日志文件的`LSN`，`Last checkpoint at`表示刷新到磁盘的`LSN`。在生产环境中这三个值可能是依次变小的。

**6. 恢复**

`InnoDB`存储引擎在启动时不管上次数据库运行时是否正常关闭，都会尝试进行恢复操作。因为重做日志记录的是物理日志，因此恢复的速` `度比逻辑日志，如二进制日志，要快很多。与此同时，`InnoDB`存储引擎自身也对恢复进行了一定程度的优化，如顺序读取及并行应用重做日志，这样可以进一步地提高数据库恢复的速度。

` `由于`checkpoint`表示已经刷新到磁盘页上的`LSN`，因此在恢复过程中仅需恢复`checkpoint`开始的日志部分。当数据库在` checkpoint`的`LSN`为`10 000`时发生宕机，恢复操作仅恢复`LSN 10 000`～` 13 000`范围内的日志

### 7.2.2 undo

**1. 基本概念**

`undo`存放在数据库内部的 一个特殊段（`segment`）中，这个段称为`undo`段（`undo segment`）。` undo`段位于共享表空间内。

用户通常对`undo`有这样的误解：`undo`用于将数据库物理地恢复到执行` `语句或事务之前的样子——但事实并非如此。`undo`是逻辑日志，因此` `只是将数据库逻辑地恢复到原来的样子。所有修改都被逻辑地取消` `了，但是数据结构和页本身在回滚之后可能大不相同。这是因为在多` `用户并发系统中，可能会有数十、数百甚至数千个并发事务。数据库` `的主要任务就是协调对数据记录的并发访问。比如，一个事务在修改` `当前一个页中某几条记录，同时还有别的事务在对同一个页中另几条` `记录进行修改。因此，不能将一个页回滚到事务开始的样子，因为这` `样会影响其他事务正在进行的工作。**也就是说不是单纯的用原来的页替换现在的页**

除了回滚操作，`undo`的另一个作用是`MVCC`，即在`InnoDB`存储引擎中` MVCC`的实现是通过`undo`来完成。当用户读取一行记录时，若该记录` `已经被其他事务占用，当前事务可以通过`undo`读取之前的行版本信` `息，以此实现非锁定读取。` `最后也是最为重要的一点是，`undo log`会产生`redo log`，也就是`undo log `的产生会伴随着`redo log`的产生，这是因为`undo log`也需要持久性的保` `护。

**2. undo存储管理**





## 7.3 事务控制语句

在`MySQL`命令行的默认设置下，事务都是自动提交（`auto commit`）` `的，即执行`SQL`语句后就会马上执行`COMMIT`操作。因此要显式地开` `启一个事务需使用命令`BEGIN`、`START TRANSACTION`，或者执行命` `令`SET AUTOCOMMIT`=`0`，禁用当前会话的自动提交。

❑`START TRANSACTION`|`BEGIN`：显式地开启一个事务。` `

❑`COMMIT`：要想使用这个语句的最简形式，只需发出`COMMIT`。也` `可以更详细一些，写为`COMMIT WORK`，不过这二者几乎是等价的。` COMMIT`会提交事务，并使得已对数据库做的所有修改成为永久性` `的。` `

❑`ROLLBACK`：要想使用这个语句的最简形式，只需发出` ROLLBACK`。同样地，也可以写为`ROLLBACK WORK`，但是二者几` `乎是等价的。回滚会结束用户的事务，并撤销正在进行的所有未提交` `的修改。` `

❑`SAVEPOINT identifier`∶`SAVEPOINT`允许在事务中创建一个保存点，` `一个事务中可以有多个`SAVEPOINT`。` `

❑`RELEASE SAVEPOINT identifier`：删除一个事务的保存点，当没有` `一个保存点执行这句语句时，会抛出一个异常。` `

❑`ROLLBACK TO`[`SAVEPOINT`]`identifier`：这个语句与`SAVEPOINT`命` `令一起使用。可以把事务回滚到标记点，而不回滚在此标记点之前的` `任何工作。例如可以发出两条`UPDATE`语句，后面跟一个` SAVEPOINT`，然后又是两条`DELETE`语句。如果执行`DELETE`语句期` `间出现了某种异常情况，并且捕获到这个异常，同时发出了` ROLLBACK TO SAVEPOINT`命令，事务就会回滚到指定的` SAVEPOINT`，撤销`DELETE`完成的所有工作，而`UPDATE`语句完成的` `工作不受影响。` `

❑`SET TRANSACTION`：这个语句用来设置事务的隔离级别。`InnoDB `存储引擎提供的事务隔离级别有：`READ UNCOMMITTED`、`READ COMMITTED`、`REPEATABLE READ`、`SERIALIZABLE`。

`START TRANSACTION`、`BEGIN`语句都可以在`MySQL`命令行下显式` `地开启一个事务。但是在存储过程中，`MySQL`数据库的分析器会自动` `将`BEGIN`识别为`BEGIN`…`END`，因此在存储过程中只能使用`START TRANSACTION`语句来开启一个事务。` `

`COMMIT`和`COMMIT WORK`语句基本是一致的，都是用来提交事务。` `不同之处在于`COMMIT WORK`用来控制事务结束后的行为是`CHAIN `还是`RELEASE`的。如果是`CHAIN`方式，那么事务就变成了链事务。

`SAVEPOINT`记录了一个保存点，可以通过`ROLLBACK TO SAVEPOINT`来回滚到某个保存点

## 7.4 隐式提交的SQL语句

以下这些SQL语句会产生一个隐式的提交操作，即执行完这些语句 后，会有一个隐式的COMMIT操作。 

❑DDL语句：ALTER DATABASE...UPGRADE DATA DIRECTORY NAME，ALTER EVENT，ALTER PROCEDURE，ALTER TABLE， ALTER VIEW，CREATE DATABASE，CREATE EVENT，CREATE INDEX，CREATE PROCEDURE，CREATE TABLE，CREATE TRIGGER，CREATE VIEW，DROP DATABASE，DROP EVENT， DROP INDEX，DROP PROCEDURE，DROP TABLE，DROP TRIGGER，DROP VIEW，RENAME TABLE，TRUNCATE TABLE。 

❑用来隐式地修改MySQL架构的操作：CREATE USER、DROP USER、GRANT、RENAME USER、REVOKE、SET PASSWORD。 

❑管理语句：ANALYZE TABLE、CACHE INDEX、CHECK TABLE、 LOAD INDEX INTO CACHE、OPTIMIZE TABLE、REPAIR TABLE。

## 7.5 对于事务操作的统计

## 7.6 事务的隔离级别

SQL 标准下的隔离级别：

- READ UNCOMMITTED：未提交读。不加锁进行实现
- READ COMMITTED：读提交。MVCC：每个事务的COMMIT都会更新快照
- REPEATABLE READ：可重复读。MVCC：每个事务保存自己的快照，即使其他的提交了也不更新
- SERIALIZABLE：序列化。加锁进行实现

InnoDB 默认 REPEATABLE READ，并且加上了 Next-Key Locking 算法，避免了幻读，使得能达到 SQL 标准的 SERIALIZABLE 级别，达到完全的隔离性要求。一般只在分布式事务中只用 SERIALIZABLE

在SERIALIZABLE的事务隔离级别，InnoDB存储引擎会对每个SELECT 语句后自动加上LOCK IN SHARE MODE，即为每个读取操作加一个共享锁。因此在这个事务隔离级别下，读占用了锁，对一致性的非锁定读不再予以支持。

在READ COMMITTED的事务隔离级别下，除了唯一性的约束检查及外键约束的检查需要gap lock，InnoDB存储引擎不会使用gap lock的锁算法。在MySQL 5.1中，READ COMMITTED事务隔离级别默认只能工作在 replication 二进制日志为 ROW 的格式下，如果是 STATEMENT 会出现一些错误：

- STATEMENT 格式记录的是master上产生的SQL语句，因此在master服务器上执行的顺序为先删后插，但是在STATEMENT格式中记录的是先插后删，造成了不一致。

要避免主从不一致的问题可以将隔离级别提高到 REPEATEABLE READ。

在 5.1 之后支持了 ROW 格式的二进制日志记录格式，避免了这种情况的发生，所以可以放心的使用 READ COMMITTED





## 7.7 分布式事务

### 7.7.1 MySQL数据库分布式事务

InnoDB存储引擎提供了对XA事务的支持，并通过XA事务来支持分布 式事务的实现。分布式事务指的是允许多个独立的事务资源 （transactional resources）参与到一个全局的事务中。事务资源通常是 关系型数据库系统，但也可以是其他类型的资源。全局事务要求在其 中的所有参与的事务要么都提交，要么都回滚，这对于事务原有的 ACID要求又有了提高。另外，**在使用分布式事务时，InnoDB存储引擎的事务隔离级别必须设置为SERIALIZABLE**

XA事务由一个或多个资源管理器（Resource Managers）、一个事务管 理器（Transaction Manager）以及一个应用程序（Application Program）组成。 

❑资源管理器：提供访问事务资源的方法。通常一个数据库就是一个 资源管理器。 

❑事务管理器：协调参与全局事务中的各个事务。需要和参与全局事 务的所有资源管理器进行通信。 

❑应用程序：定义事务的边界，指定全局事务中的操作。

在MySQL数据库的分布式事务中，资源管理器就是MySQL数据库， 事务管理器为连接MySQL服务器的客户端。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/MySQL/%E6%8A%80%E6%9C%AF%E5%86%85%E5%B9%95/%E5%88%86%E5%B8%83%E5%BC%8F%E4%BA%8B%E5%8A%A1%E6%A8%A1%E5%9E%8B.png)

分布式事务使用两段式提交（two-phase commit）的方式。在第一阶 段，所有参与全局事务的节点都开始准备（PREPARE），告诉事务管 理器它们准备好提交了。在第二阶段，事务管理器告诉资源管理器执 行ROLLBACK还是COMMIT。如果任何一个节点显示不能提交，则所 有的节点都被告知需要回滚。可见与本地事务不同的是，分布式事务 需要多一次的PREPARE操作，待收到所有节点的同意信息后，再进行 COMMIT或是ROLLBACK操作。

### 7.7.2 内部XA事务

在MySQL数据库中还存在另外一种分布式事务，其在存储引擎与 插件之间，又或者在存储引擎与存储引擎之间，称之为内部XA事务。

最为常见的内部XA事务存在于binlog与InnoDB存储引擎之间。由于复 制的需要，因此目前绝大多数的数据库都开启了binlog功能。在事务 提交时，先写二进制日志，再写InnoDB存储引擎的重做日志。对上述 两个操作的要求也是原子的，即二进制日志和重做日志必须同时写 入。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/MySQL/%E6%8A%80%E6%9C%AF%E5%86%85%E5%B9%95/%E4%B8%BB%E4%BB%8E%E4%B8%8D%E4%B8%80%E8%87%B4.png)

如果执行完①、②后在步骤③之前MySQL数据库发生了 宕机，则会发生主从不一致的情况。为了解决这个问题，MySQL数据 库在binlog与InnoDB存储引擎之间采用XA事务。当事务提交时， InnoDB存储引擎会先做一个PREPARE操作，将事务的xid写入，接着 进行二进制日志的写入，如图7-24所示。如果在InnoDB存储引擎提交 前，MySQL数据库宕机了，那么MySQL数据库在重启后会先检查准 备的UXID事务是否已经提交，若没有，则在存储引擎层再进行一次提 交操作

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/MySQL/%E6%8A%80%E6%9C%AF%E5%86%85%E5%B9%95/%E4%BF%9D%E8%AF%81%E4%B8%BB%E4%BB%8E%E4%B8%80%E8%87%B4.png)



## 7.8 不好的事务习惯

### 7.8.1 在循环中提交

### 7.8.2 使用自动提交

使用START TRANSACTION，BEGIN来显式地开启一个事务。 在显式开启事务后，在默认设置下（即参数completion_type等于0）， MySQL会自动地执行SET AUTOCOMMIT=0的命令，并在COMMIT或 ROLLBACK结束一个事务后执行SET AUTOCOMMIT=1。

### 7.8.3 使用自动回滚

## 7.9 长事务

长事务就是执行时间比较长的事务。

由于事务ACID的特性，这个操作被封装 在一个事务中完成。这就产生了一个问题，在执行过程中，当数据库 或操作系统、硬件等发生问题时，重新开始事务的代价变得不可接 受。数据库需要回滚所有已经发生的变化，而这个过程可能比产生这 些变化的时间还要长。因此，对于长事务的问题，有时可以通过转化 为小批量(mini batch)的事务来进行处理。当事务发生错误时，只需要 回滚一部分数据，然后接着上次已完成的事务继续进行。

# 第8章 备份与恢复

## 8.1 备份与恢复概述

根据备份的方法分为：

- Hot Backup
- Cold Backup
- Warm Backup

Hot Backup 指在数据库运行中直接备份，对运行中的数据库没有任何影响；

Cold Backup 指在数据库停止的情况下进行备份，这种备份方式简单，一般只需要拷贝相关的数据库物理文件即可。

Warm Backup 同样是在数据库运行时进行备份，但会对当前数据的操作有影响。例如加一个全局读锁以保证备份数据的一致性。
