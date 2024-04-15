# 1 简介

- 分布式的、RESTful风格的搜索引擎
- 支持对各种类型的数据的检索
- 搜索速度快，可以提供实时的搜索服务
- 便于水平扩展，每秒可以处理PB级别的数据

相关术语：

6.0 版本开始一个索引对应一张表，7.0废弃类型

- 索引：对应MySQL的数据库，一个索引是一个库
- 类型：对应表，一个类型是一张表
- 文档：对应 表中的 一行，文档通常采用json结构
- 字段：对应表中的列

和分布式的概念统一：

- 集群：多个服务器组合到一起
- 节点：集群中的单个服务器
- 分片：对索引进行进一步划分
- 副本：分片的副本

## 1.1 下载配置

[官方下载地址](https://www.elastic.co/cn/downloads/elasticsearch)

下载好只有进行配置，/config/elasticsearch.yml

```
# 修改集群名称
cluster.name: application
# 数据存储位置
path.data: d:/Program/data/elasticsearch-7.12.1/data
# 日志存储位置
path.data: d:/Program/data/elasticsearch-7.12.1/logs
```

配置环境变量

## 1.2 下载中文分词插件

[插件下载链接](https://github.com/medcl/elasticsearch-analysis-ik/releases)

解压到 elasticsearch安装目录下 的 plugins\ik\目录下，这是固定的

插件版本需要和elasticsearch版本匹配

## 1.3 简单使用

### 1.3.1 通过cmd使用

\bin\elasticsearch.bat

启动后可以查看集群状态

```
curl -X GET "localhost:9200/_cat/health?v"
```

查看节点状态

```
curl -X GET "localhost:9200/_cat/nodes?v"
```

查看索引数量

```
curl -X GET "localhost:9200/_cat/indices?v"
```

新建索引

test是新建索引的名字

```
curl -X PUT "localhost:9200/test"
```

删除索引

```
curl -X DELETE "localhost:9200/test"
```

### 1.3.2 通过http请求使用

以下命令同上，请求时选择好请求方式 GET PUT DELETE

```
localhost:9200/_cat/health?v
localhost:9200/_cat/nodes?v
localhost:9200/_cat/indices?v
localhost:9200/test
localhost:9200/test
```

**增加一条数据PUT**

test索引下的第一条数据

_doc为占位符

```
localhost:9200/test/_doc/1
```

数据内容在请求时携带

**修改数据是同样的操作，同时版本号+1**

**查找该数据GET**

```
localhost:9200/test/_doc/1
```

**删除该数据DELETE**

```
localhost:9200/test/_doc/1
```

**搜索数据**

```
localhost:9200/test/_search
```



这一系列操作是如何将数据保存到Elasticsearch服务器中

实际使用时我们将一段搜索内容进行分词，然后使用这些分词到服务器中搜索，同时匹配 各种json字段

# 2 搜索条件

## 2.1 搜索标题

从数据库中查找title中包含互联网的词条

```
localhost:9200/test/_search?q=title:"互联网"
```

其中

```
q=title:"xxx"
```

这个部分中 title 是 保存的 JSON 格式信息的 KEY， 匹配其 VALUE 中有 xxx 的词条

## 2.2 联查

下面的请求方式对两个 KEY 的信息进行联查

```
localhost:9200/test/_search?q=title:"互联网"&q=content:"招聘"
```



## 2.3 分词查询

注意和上面区分，这里的搜索 关键字 没有双引号

这样的关键词可以进行分词处理

它会查找content中包含”运营实习“，”运营“，”实习“等内容的词条

```
localhost:9200/test/_search?q=content:运营实习
```



## 2.4 多重匹配

使用POSTMAN进行携带请求体数据的请求

在Body中添加数据

```json
{
    "query":{
        "multi_match":{
            "query":"互联网",
            "fields":["title","conent"]
        }
    }
}
```

搜索的内容是 `互联网`，对 `title`和`content`的内容进行匹配

# 3 整合到Spring

9200是http端口，9300是tcp端口