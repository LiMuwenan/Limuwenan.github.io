# 第二章 简单动态字符串

简单动态字符串（simple dynamic string, SDS）

## 2.1 SDS 的定义

每一个 sds.h/sdshdr 结构表示一个 SDS 值

```c
struct sdshdr{
	int len;//记录buf数组中已经使用的字节的数量，不包括结束符
	int free;//记录buf数组中未使用的字节的数量
	char buf[];//用于保存字符串
}
```

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/redis/%E8%AE%BE%E8%AE%A1%E4%B8%8E%E5%AE%9E%E7%8E%B0/sds1.png)

在上图的结构中，

- free 属性是 0 ，表示 SDS 没有未分配的空间
- len 属性是 5，表示 SDS 字符的长度（不包括结束符）
- buf 属性是 char 类型数组

buf 中保留 c 风格结束符可以方便的使用 c 的原生打印等函数来对 buf 进行操作

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/redis/%E8%AE%BE%E8%AE%A1%E4%B8%8E%E5%AE%9E%E7%8E%B0/sds2.png)

上图显式有 5 个未使用的字节

## 2.2 SDS 与 c 字符串的区别

单纯的使用 c 字符串不能满足 Redis 对于字符串的安全性、效率和功能方面的要求。

### 2.2.1 常数复杂度获取字符串长度

不用遍历整个字符串来获取长度，不会让获取长度称为性能瓶颈

### 2.2.2 杜绝缓冲区溢出

当字符串拼接时可能造成缓冲区溢出。

SDS 的空间分配策略完全杜绝了发生缓冲区溢出的可能性；当 SDS API 需要对 SDS 继续修改时， API 会先检查 SDS 的空间是否满足修改所需要的要求，如果不满足的话， API 会自动将 SDS 的空间扩展至执行修改所需要的大小，然后才执行实际的修改操作，所以使用 SDS 既不需要手动修改 SDS的空间大小，也不会出现前面所说的缓冲区溢出问题。



### 2.2.3 减少修改字符串时带来的内存重分配次数

有时候字符出的修改十分的频繁，多次重新分配内存会拖慢系统性能。因此在重分配时会预留一些空间，即多分配一些空间。

**1. 空间预分配**

redis 的预分配策略：SDS 修改后长度小于 1MB，当分配后的 len 为 m，也会同样分配 m 大小的free。总长度为 2m+1；SDS 修改后长度大于 1MB，那么 free 的长度为 1MB。即 free 最大为 1MB

**2. 惰性空间释放**

需要缩短字符串时，不会真的释放多余的空间，而是先修改 free 和 len 的值，等待将来使用。



### 2.2.4 二进制安全

C 字符串通常会以空格做字符串的结尾，而 SDS API 会将数据当作二进制数据，以二进制的方式来处理数据，数据在写入时是什么样，被读取时还是什么样。

Redis 不仅可以保存文本数据，也可以保存任意格式的二进制数据



### 2.2.5 兼容部分 C 字符串函数



# 第三章 链表

当一个列表键包含了数量比较多的元素，又或者列表中包含的元素都是比较长的字符串时，Redis 就会使用链表作为列表键的底层实现。

## 3.1 链表和链表节点的实现

每个链表节点使用一个 adlist.h/listNode 结构来表示：

```c
typedef struct list{
    listNode* head;//表头节点
    listNode* tail;//表尾节点
    unsigned long len;//链表所包含的节点数量
    void* (*dup)(void *ptr);//节点值复制函数，指针函数，返回一个 void* 类型的函数
    void (*free)(void *ptr);//节点值释放函数，返回类型 void，可以赋值给其他函数
    //void (*fun)(void *ptr)
    //fun = free;
    //调用方式：fun(xxx)
    int (*match)(void *ptr, void *key);//节点值对比函数
} list;
```

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/redis/%E8%AE%BE%E8%AE%A1%E4%B8%8E%E5%AE%9E%E7%8E%B0/list.png)

Redis 的链表实现的特性可以总结如下：

- 双端：
- 无环：
- 有表头和表尾指针：
- 链表长度计数：
- 多态：链表节点使用 void* 指针来保存节点值，并且可以通过 list 结构的 dup、free、match 三个属性为节点值设置类型特性函数，所以链表可以用于保存各种不同类型的值





# 第四章 字典

字典是 redis 数据库的底层实现，也是哈希键的底层实现之一

当一个哈希键包含的键值对比较多，又或者键值对中的元素都是比较长的字符串时， Redis 就会使用字典作为哈希键的底层实现。

## 4.1 字典的实现

### 4.1.1 哈希表

Redis 字典所使用的哈希表由 dict.h/dictht 结构定义：

```c
typedef struct dictht{
    dictEntry **table;//哈希数组
    unsigned long size;//哈希表大小
    unsigned long sizemask;//哈希表大小掩码，用于计算索引值，总是等于 size-1
    unsigned long used;//该哈希表已有节点的数量
}dictht;
```

- table 属性是一个数组，数组中的每个元素都是一个指向 dict.h/dictEntry 结构的指针，每个 dictEntry 结构保存着一个键值对。
- size 属性记录了哈希表的大小，也即是 talbe 数组的大小，
- used 属性则记录了哈比表目前已有键值对的数量。 
- sizemask 属性的值总为 size-1，这个属性和哈希值一起决定一个键应该被放在 talbe 数组的哪个索引上。



### 4.1.2 哈希表节点

哈希表节点使用 dictEntry 结构表示，每个 dicEntry 结构都保存着一个键值对

```c
typedef struct dictEntry{
    void *key;//键
    union{
        void *val;
        uint_tu64;
        int64_ts64;
    }v;
    struct dictEntry *next;//指向下一个哈希表节点，形成链表
}dictEntry;
```

- key 属性保存着键值对中的键
- v 保存值，值可以是一个指针，也可以是 uint_64 整数或者 int64_t 整数
- next 指向另一个哈希表节点（拉链法解决哈希冲突）



### 4.1.3 字典

Redis 中的字段由 dict.h/dict 结构表示：

```c
typedef struct dict{
	dictType *type;//类型特定函数
	void* privdata;//私有数据
	dictht ht[2];//哈希表
	int rehashidx;//rehash索引，当rehash不再进行时，值为-1
}dict;
```

- type 和 privdata：针对不同类型的键值对，为创建多态字典设置

  - type：指向一个 dictType 结构的指针，每个 dictType 结构保存了一簇用于操作特定类型键值对的函数，Redis 会为用途不同的字典设置不同的类型特定函数。
  - privdata：保存了需要传给那些类型特定函数的可选参数

  ```c
  typedef struct dictType{
      //计算哈希值的函数
      unsigned int (*hashFunction)(const void *key);
      //复制键的函数
      void *(*keyDup)(void *privdata,const void *key);
      //复制值的函数
      void *(*valDup)(void *privdata,const void *obj);
      //对比键的函数
      int (*keyCompare)(void *privdata, const void *key1, const void *key2);
      //销毁键的函数
      void (*keyDestructor)(void *privdata, void *key);
      //销毁值的函数
      void (*valDestructor)(void *privdata, void *obj);
  }dictType;
  ```

- ht：包含两个项的数组，数组中的每个项都是一个 dictht 哈希表，一般情况下，字典只使用 ht[0] 哈比表，ht[1] 哈希表只会在对 ht[0] 哈希表进行 rehash 时使用

- rehashidx：记录了 rehash 的进度

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/redis/%E8%AE%BE%E8%AE%A1%E4%B8%8E%E5%AE%9E%E7%8E%B0/dict.png)



## 4.2 哈希算法

Redis 计算哈希值和索引值的方法如下：

```c
//使用字典设置的哈希函数，计算键key的哈希值
hash =  dict->type->hashFunction(key);

//使用哈希表的sizemask属性和哈希值，计算处索引值
//根据情况不同，ht[x]可以是ht[0]或者ht[1]
index = hash & dict->ht[x].sizemask;
```

## 4.3 解决键冲突

链地址法解决哈希冲突

## 4.4 rehash

随着表越来越大，需要保持负载因子保持在一个合理的范围内，需要对哈希表进行扩容或者收缩。

Redis 字典 rehash 操作如下：

1. 为字典的 ht[1] 分配空间
2. 将 ht[0] 的键值对 rehash 到 ht[1] 上
3. 将 h[0] 释放，h[1] 设置为 h[0]，为 h[1] 设置一个新表，等待下次 rehash



**哈希表的扩展与收缩**

- 服务器目前没有在执行 BGSAVE 命令或者 BGREWRITEAOF 命令，并且哈希表的负载因子大于等于1
- 服务器目前正在执行 BGSAVE 命令或者 BGREWRITEAOF 命令，并且负载因此大于等于5

以上负载因子不同的原因是，执行以上命令的过程中，Redis 需要创建当前服务器进程的子进程，而大多数操作系统都采用写时复制技术来优化子进程的使用效率，所以在子进程存在期间，服务器会提高执行扩展操作所需的负载因子，从而尽可能地避免在子进程存在期间进行哈希表地扩展操作，这可以避免不必要的内存写入操作，从而最大限度地节约内存

负载因子小于0.1进行收缩操作

## 4.5 渐进式 rehash

上面 rehash 操作的第二步不是一次性完成的，而是分多次、渐进式的完成：

1. 为 ht[1] 分配空间，让字典同时持有 ht[0] 和 ht[1]
2. 在字典中维持一个索引计数器 rehashidx，并设置为0，表示 rehash 正在开始
3. 在 rehash 期间每次对字典的增删改查，还会顺带将 ht[0] 表在 rehashidx 索引上的所有键值对 rehash 到 ht[1]，当 rehash 工作完成后，程序将 rehashidx 的值加1
4. 随着不断执行，最终会将所有 ht[0] 复制到 ht[1]，再将 rehashidx 设置为 -1

**渐进式 rehash 执行期间的哈希表操作**

对于非添加操作，先对 ht[0] 操作，如果 ht[0] 没有，则对 ht[1] 操作；

如果是添加操作则直接对 ht[1] 操作



# 第五章 跳跃表

支持平均 O(logN)、最坏 O(N) 复杂度的节点查询，还可以通过顺序性操作来批量处理节点

跳跃表作为 Redis 有序集合键的底层实现之一。



## 5.1 跳跃表的实现

Redis 跳跃表由 redis.h/zskiplistNode 和 redis.h/zskiplist 两个结构定义

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/redis/%E8%AE%BE%E8%AE%A1%E4%B8%8E%E5%AE%9E%E7%8E%B0/zskiplist.png)

zskiplistNode 结构用于表示跳跃表节点

zskiplist 结构用于保存跳跃表节点的相关信息：节点数量、指向表头或者表尾的指针等

```c
typedef struct zskiplist{
    zskiplistNode *header;
    zskiplistNode *tail;
    int level;
    int length;
}zkiplistNode;
```

- header：指向跳跃表的表头节点
- tail：指向跳跃表的表尾节点
- level：记录目前跳跃表的层数最大的那个节点的层数（表头节点不计算）
- length：记录跳跃表的长度，目前包含节点的数量（表头节点不计算）



### 5.1.1 跳跃表节点

```c
typedef struct zskiplistNode{
    //层
    struct zskiplistLevel{
        //前进指针
        struct zskiplistNode* forward;
        //跨度
        unsigned int span;
    }level[];
    //后退指针
    struct zskiplistNode *backward;
    //分值
    double score;
    //成员对象
    robk *obj;
}zskiplistNode;
```

- level：层，节点中使用 L1、L2 等表示各层。每层都有前进指针和跨度，跨度记录了当前节点和前进指针所指节点的距离
- backward：节点中用 bw 字样标记节点的后退指针，指向位于当前节点的前一个节点。后退指针在程序从表头遍历使用。
- socre：分值从小到大排列。
- obj：o1、o2、o3 表示成员对象

**1. 层**

通过层加快放问速度

**2. 前进指针**

从表头向表尾遍历

**3. 跨度**

使用跨度计算一个结点的排位

**4. 后退指针**

后退指针在程序从表头遍历使用。

**5. 分值和成员**

分值从小到大排列。

节点成员对象是一个指针，指向一个字符串对象，保存 SDS 值。

分值相同的对象按照成员对象字典序的大小排序



### 5.1.2 跳跃表

通过 zskiplist 结构持有 zskiplistNode

```c
typedef struct zskiplist{
    zskiplistNode *header;//表头和表尾节点
    zskiplistNode *tail;//
    int level;//层数最大的层数数目
    int length;//节点数量
}zkiplistNode;
```



# 第六章 整数集合

## 6.1 整数集合的实现

可以保存类邢型 int64_t、int32_t、int64_t 的整数值

intset.h/intset 结构：

```c
typedef struct intset{
    uint32_t encoding;//编码方式
    uint32_t length;//集合包含的元素数量
    int8_t contents[];//保存元素的数组
}
```

- contents 数组是整数集合的底层实现：整数集合的每个元素都是 contents 数组的一个数组项，各个项在数组中按值从小到大排列，并且不包含任何重复项。contents 的真正属性取决于 encoding 属性。
  - encoding 为 INTSET_ENC_INT16 ，contents 为 int16_t
  - encoding 为 INTSET_ENC_INT32 ，contents 为 int32_t
  - encoding 为 INTSET_ENC_INT64 ，contents 为 int64_t
- length属性记录了整数集合包含的元素当我量，contents 长度。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/redis/%E8%AE%BE%E8%AE%A1%E4%B8%8E%E5%AE%9E%E7%8E%B0/intset.png)

- encoding 是 INTSET_ENC_INT64
- length 是 4
- contents 包含 4 个数，；因为 -2675256175807981027 是 int64_t ，必须把 1 3 5 也提升为 int64_t

## 6.2 升级

升级整数集合并添加新元素分为三步：

1. 根据新元素类型，扩展整数集合底层数组大小，为新元素分配空间
2. 旧数组放到新数组，维持有序，空出新元素位置
3. 添加新元素

所以添加元素时间复杂度 O(N)

新元素长度比所有元素长，因此比所有元素大或者小，也就小于或者大于所有元素



## 6.3 升级的好处

- 提升灵活性
- 节约内存

## 6.4 降级

不能降级

# 第七章 压缩列表

是列表键和哈希键的底层实现之一。当一个列表键只包含少量列表项，并且每个列表项要么就是小整数值，要么就是短字符串。

## 7.1 压缩列表的构成

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/redis/%E8%AE%BE%E8%AE%A1%E4%B8%8E%E5%AE%9E%E7%8E%B0/zlist.png)

- zlbytes：4字节，表示压缩列表占用字节
- zltail：4字节，表尾节点距离压缩列表的起始地址字节数
- zllen：2字节，包含的节点数量
- entryx：节点
- zlend：标记列表末端



## 7.2 压缩列表节点的构成

每个压缩列表节点可以保存一个字节数组或者一个整数值

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/redis/%E8%AE%BE%E8%AE%A1%E4%B8%8E%E5%AE%9E%E7%8E%B0/zlistnode.png)



### 7.2.1 previous_entry_length

该属性以字节为单位，记录前一个节点的长度

- 如果前一个节点长度小于254字节，那么 previous_entry_length 为一字节：保存前一个节点长度
- 如果前一个节点长度大于254字节，那么 previous_entry_length 为五字节，第一个字节设置为 0xFE，后四个字节保存长度



### 7.2.2 encoding

encoding 记录了节点 content 所保存数据的类型以及长度



### 7.2.3 content



## 7.3 连锁更新

