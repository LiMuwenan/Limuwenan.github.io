# Pytorch



## 1 基本语法

Pytorch是基于Numpy的科学计算包，提供了两大功能：

- 提供了使用GPU的功能
- 灵活性和速度

### 1.1 基本操作

#### 1.1.1 创建

引入包

```python
import torch
```

创建一个没有初始化的矩阵

```python
x = torch.empty(5, 3)
print(x)

--------------------------------
tensor([[-1.7986e+36,  5.8714e-43, -1.7986e+36],
        [ 5.8714e-43, -1.7986e+36,  5.8714e-43],
        [-1.7986e+36,  5.8714e-43, -1.7986e+36],
        [ 5.8714e-43, -1.7986e+36,  5.8714e-43],
        [-1.7986e+36,  5.8714e-43, -1.7986e+36]])
```

创建一个有初始化的矩阵

```python
x = torch.rand(5, 3)
print(x)

-----------------------------------
tensor([[0.1610, 0.9152, 0.8669],
        [0.7196, 0.8951, 0.6186],
        [0.1093, 0.1379, 0.9459],
        [0.7635, 0.2389, 0.4414],
        [0.5032, 0.9742, 0.5655]])
```

创建全0矩阵，并可以指定数据类型

```python
x = torch.zeros(5, 3, dtype=torch.long)
print(x)
```

通过数据创建张量

```python
x = torch.tensor([[2.3, 5.6], [1.1, 2.2]])
print(x)
--------------------
tensor([[2.3000, 5.6000],
        [1.1000, 2.2000]])
```

通过已有张量创建相同尺寸张量

输出张量尺寸

```python
x = x.new_ones(5, 3, dtype=torch.double)
print(x)
# like
y = torch.randn_like(x, dtype=torch.float)
print(y)
# 得到张量尺寸
print(x.size())

-------------------------------
tensor([[1., 1., 1.],
        [1., 1., 1.],
        [1., 1., 1.],
        [1., 1., 1.],
        [1., 1., 1.]], dtype=torch.float64)
tensor([[ 1.4509, -1.9947,  0.0212],
        [-0.9752, -1.2442, -0.2298],
        [-0.8437, -0.5259,  0.5727],
        [ 0.7791, -0.2974,  1.1061],
        [ 0.1475,  0.8510,  0.4596]])
torch.Size([5, 3])
```

#### 1.1.2 运算

加法

```python
# 加法
x = x.new_ones(5, 3, dtype=torch.double)
y = y.new_ones(5, 3, dtype=torch.double)
z = torch.empty(5, 3)
x = x + y
torch.add(x, y)
torch.add(x, y, out=z) # 保存到了z
print(z)

--------------------------
tensor([[3., 3., 3.],
        [3., 3., 3.],
        [3., 3., 3.],
        [3., 3., 3.],
        [3., 3., 3.]])
```

切片

```python
# 切片假设5行3列
print(x[:, 2]) # 打印出第0列和第1列
```

改变张量形状

```python
# 改变张量形状，将一个(5,3)改变为(3,5)
y = x.view(3, 5)
```

item

```python
# item 只有一个元素时可用
x = torch.rand(1)
print(x)
print(x.item())

------------------------------------------
tensor([0.1269])
0.1269134283065796
```

tensor转换为numpy

```python
# tensor转换为numpy
x = torch.ones(5)
print(x)
y = x.numpy()
print(y)
x.add_(1) # 就地的会改变y
print(y)
x = x + x
print(x)
print(y)
```

numpy转换为tensor

```python
# numpy转tensor
a = np.ones(5)
b = torch.tensor(a)
print(a)
print(b)
```

将tensor转换到任意设备上

```python
# 将tensor转换到任意设备上
device = torch.device('cuda') # device = torch.device('cpu')
x = x.to(device)
```

### 1.2 Pytorch中的autograd

目标

- 掌握自动求导中tensor的概念和操作
- 掌握自动求导中梯度Gradients概念和操作

**torch.Tensor**

`torch.Tensor`是整个package中的核心类，如果将属性requires_grad设置为True，他将追踪在这个类上定义的所有操作，当代码要进行反向传播的时候，直接调用.backward()就可以自动计算所有梯度，在这Tensor上所有的梯度将被累加进属性grad中

如果想要终止一个Tensor在计算图中的追踪回溯，只需要执行detach()接可以将改Tensor从计算图中撤下来，在未来的回溯计算中也不会计算该Tensor

除了detach()，还可以使用with torch.no_grad()方式终止计算图回溯，不再进行方向传播求导。

**torch.Function**

Function类，每一个Tensor有一个grad_fn属性，代表引用了哪个Function创建了该Tensor

如果某个张量是用户自定义的，则其对应的grad_fn is None

```python
# required_grad
a = torch.ones(3, 3)
b = torch.ones(3, 3, requires_grad=True) # 创建时加了参数，打印也会有该参数标记
print(a)
print(b)

c = b + 2
print(c)
print(a.grad_fn)
print(c.grad_fn)

--------------------------------
tensor([[1., 1., 1.],
        [1., 1., 1.],
        [1., 1., 1.]])
tensor([[1., 1., 1.],
        [1., 1., 1.],
        [1., 1., 1.]], requires_grad=True)
tensor([[3., 3., 3.],
        [3., 3., 3.],
        [3., 3., 3.]], grad_fn=<AddBackward0>)
None
<AddBackward0 object at 0x00000275446BC3A0> # 回溯标记，由什么运算创建的该变量
```

求均值

```python
d = c.mean()
print(d)

-------------------------------
tensor(3., grad_fn=<MeanBackward0>)
```

反向传播backward()

```python
# 反向传播
a = torch.tensor(20., requires_grad=True)
b = torch.tensor(10., requires_grad=True)
f = a * b
f.backward() # 计算了一遍
print(a.grad) # f对a求导
print(b.grad) # f对b求导

-----------------------------------------
tensor(10.)
tensor(20.)
```





## 2 初步应用

### 2.1 构建一个神经网络

目标：

- 掌握用Pytorch构建神经网络的基本路程
- 掌握用Pytorch构建神经网络的实现过程

torch.nn：

- 使用Pytorch构建神经网络，主要的工具都在torch.nn包中
- nn依赖于autograd来定义模型，并对其自动求导

构建神经网络的典型流程

- 定义一个拥有可学习参数的神经网络
- 遍历训练集数据
- 计算损失值
- 将网络参数的梯度进行反向传播
- 以一定规则更新网络权重



# 自然语言处理

## 1 文本预处理

### 1.1 概述

目标

- 文本预处理的作用
- 预处理的主要环节

文本预处理及其作用：

- 文本预料在输送给模型前一般需要处理，如将文本转化为模型需要的装量，规范张量尺寸等

文本预处理包含的主要环节：

- 文本处理的基本方法
- 文本张量表示方法
- 文本语料的数据分析
- 文本特征处理
- 数据增强方法

文本处理的基本方法：

- 分词
- 词性标注
- 命名实体识别

文本张量表示方法：

- one-hot
- word2vec
- word embedding

文本语料书数据分析：

- 标签数量分布
- 句子长度分布
- 词频统计和关键词词云

文本特征处理：

- 添加n-gram特征
- 文本长度规范

数据增强方法：

- 回译数据增强法

### 1.2 jieba包

python的中文分词组件

```python
import jieba

content = "我们的世界就是这样的没有理由，今天好像还在按照某种规则运行，明天可能就不是了"
# 返回生成器使用cut，返回内容使用lcut
print(jieba.lcut(content, cut_all=False))  # 以精确模式分词
# 全模式分词，将所有成词的词切分出来
print(jieba.lcut(content, cut_all=True))
# 搜索引擎模型分词，在精确模式的基础上，对长词再次进行切分，提高召回率，适用于搜索引擎分词
print(jieba.lcut_for_search(content))

# 用户自定义词典
print(jieba.lcut("八一双路更名为八一南昌篮球队"))  # 不能按照预期分词
# 载入自定义词典
jieba.load_userdict("./user_dict.txt")
print(jieba.lcut("八一双路更名为八一南昌篮球队"))
```

### 1.3 hanlp

命名实体：通常我们将人名、地名、机构名等专有名词统称为命名实体：周杰伦、黑山县、孔子学院

简单说就是专有名词

```python
# hanlp中英文分词
# 加载CTB_CONVSEG预训练模型进行分词任务
tokenizer = hanlp.load("CTB6_CONVSEG")
print(tokenizer("我们的世界就是这样的没有理由，今天好像还在按照某种规则运行，明天可能就不是了"))
print(tokenizer("English we are"))
# 英文分词
tokenizer = hanlp.utils.rules.tokenize_english
print(tokenizer("English we are"))

# 中文命名实体识别
tokenizer = hanlp.load(hanlp.pretrained.ner.MSRA_NER_BERT_BASE_ZH)
print(tokenizer(list("上海华安工业集团公司董事长谭旭光和秘书张婉霞来到美国纽约现代艺术博物馆参观")))
# 英文命名实体识别
tokenizer = hanlp.load(hanlp.pretrained.ner.CONLL03_NER_BERT_BASE_CASED_EN)
print(tokenizer(["White House", "New York", "Beijing"]))
```

### 1.4 词性标注

```
我 rr 人称代词
爱 v 动词
自然语言 n 名词
处理 vn 动名词
```

```python
# import jieba.posseg as pseg
# jieba中文词性标注
print(pseg.lcut("我爱北京天安门"))
# hanlp中文词性标注
# tokenizer = hanlp.load(hanlp.pretrained.pos.CTB5_POS_RNN_FASTTEXT_ZH)
print(tokenizer(['我', '的', '希望', '是', '希望', '和平']))
# hanlp英文词性标注
# tokenizer = hanlp.load(hanlp.pretrained.pos.PTB_POS_RNN_FASTTEXT_EN)
print(tokenizer(['I', 'banked', '2', 'dollars', 'in', 'the', 'bank']))
```

## 2 文本张量表示

文本张量表示方法：

- one-hot
- word2vec
- word embedding

### 2.1 one-hot做词的映射

```python
# 导入用于对象保存与加载的joblib
import joblib
# 导入keras中的词汇映射器Toeknizer
from keras.preprocessing.text import Tokenizer

# 假定vocab为语料集
vocab = {"周杰伦", "陈奕迅", "王力宏", "李宗盛", "鹿晗"}
# 实例化一个词汇映射器对象
t = Tokenizer(num_words=None, char_level=False)
# 使用映射器你和现有文本数据
t.fit_on_texts(vocab)

for token in vocab:
    zero_list = [0] * len(vocab)
    # 使用映射器转换文本数据，每个词汇对应从1开始的自然数
    # 返回样式如：[[2]]，取出其中的数字需要[0][0]
    token_idx = t.texts_to_sequences([token])[0][0] - 1
    zero_list[token_idx] = 1
    print(token, "的one-hot编码为：", zero_list)

# 使用joblib工具保存映射器
tokenizer_path = "./Toeknizer"
joblib.dump(t, tokenizer_path)

-------------------------------------
李宗盛 的one-hot编码为： [1, 0, 0, 0, 0]
陈奕迅 的one-hot编码为： [0, 1, 0, 0, 0]
鹿晗 的one-hot编码为： [0, 0, 1, 0, 0]
王力宏 的one-hot编码为： [0, 0, 0, 1, 0]
周杰伦 的one-hot编码为： [0, 0, 0, 0, 1]
```

加载之前的onthot编码

```python
# 加载之前做好的编码
t = joblib.load(tokenizer_path)
# 编码的token，我们随便取一个
token = "李宗盛"
token_idx = t.texts_to_sequences([token])[0][0] - 1
zero_list = [0] * len(vocab)
zero_list[token_idx] = 1
print(token, "的one-hot编码是：", zero_list)
--------------------------------------------
李宗盛 的one-hot编码是： [0, 0, 1, 0, 0]
```

### 2.2 word2vec

一种将词汇表示成向量的无监督训练方法，该过程将构建神经网络模型，将网络参数作为词汇的向量表示，包含CBOW和skipgram两种训练模式

CBOW(Continuous bag of words)模式：

旁边预测中间

skipgram：

中间预测旁边



使用fasttext工具实现word2vec的训练和使用

1. 获取训练数据
2. 训练词向量
3. 模型超参数设定
4. 模型效果检验
5. 模型的保存与重加载

```python
import fasttext

# 从词库进行训练
# 数据表征维度dim，默认100，数据量大的时候维度需要更大
# 数据循环次数epoch，默认5，数据量够多的话不需要循环很多次
# 学习率lr，默认0.05
# 线程数thread：默认12个线程
model = fasttext.train_unsupervised('./ewik9', 'cbow', dim=100, epoch=10, lr=0.02)

# 获取一个词的向量
print(model.get_word_vector('the'))

# 查看相近词
# 词越相近，说明训练的越好
print(model.get_nearest_neighbors('the'))


# 模型保存与重加载
model.save_model('ewik9.bin')

# 加载模型
model = fasttext.load_model('ewik9.bin')

# 再次使用
print(model.get_nearest_neighbors('the'))
```

### 2.3 word embedding

```PYTHON
import torch
import json
from torch.utils.tensorboard import SummaryWriter

# 实例化一个摘要写入对象
writer = SummaryWriter()

# 随机初始化一个100*5的矩阵，认为它是我们已经得到的词的嵌入矩阵
# 代表100个词汇，每个词汇被表示成50维向量
embedding = torch.randn(100, 50)

# 导入事先准备好的100个中文词汇文件，形成meta列表原始词汇
meta = list(map(lambda x: x.strip(), fileinput.FileInput("./vocab100.csv")))
writer.add_embedding(embedding, metadata=meta)
writer.close()
```

## 3 文本数据分析

目标：

- 了解文本分析的作用
- 掌握常用的几种文本数据分析方法

文本数据分析的作用：文本数据分析能够有效帮助我们理解语料，快速检查出语料可能存在的问题，并知道之后模型训练过程中一些超参数的选择

常用的几种文本数据分析方法：

- 标签数量分布
- 句子长度分布
- 词频统计与关键词词云

数据样本

```
第一列 具体平稳
第二列 0 1 表示的是正面评价还是负面评价
```

### 标签分布

获得训练集和验证集的标签数量分布

```PYTHON
import seaborn as sns
import pandas as pd
import matplotlib.pyplot as plt

# 设置显示风格
plt.style.use('fivethirtyeight')

# 分别读取训练tsv和验证tsv
train_data = pd.read_csv("train_data.tsv", sep="\t")
valid_data = pd.read_csv("valid_data.tsv", sep="\t")

# 获得训练数据标签数量分布
sns.countplot("label", data=train_data)
plt.title("train_data")
plt.show()

# 获取验证胡数据标签数量分布
sns.countplot("label", data=valid_data)
plt.title("valid_data")
plt.show()
```

### 句子长度分布

获得训练集和验证集的句子长度分布

```python
# 在训练数据中添加新的句子长度列，每个元素的值都是对应的句子列的长度
train_data["sentence_length"] = list(map(lambda x: len(x), train_data["sentence"]))

# 绘制句子长度列数量分布
sns.countplot("sentence_length", data=train_data)
# 主要关注count长度分布的纵坐标，不需要绘制横坐标，横坐标范围通过dist图进行查看
plt.xticks([])
plt.show()

# 绘制dist长度分布图
sns.displot((train_data["sentence_length"]))

# 主要关注dist长度分布横坐标，不需要绘制纵坐标
plt.yticks([])
plt.show()

# 在验证数据中添加新的句子长度列，每个元素的值都是对应的句子列的长度
train_data["sentence_length"] = list(map(lambda x: len(x), valid_data["sentence"]))

# 绘制句子长度列数量分布
sns.countplot("sentence_length", data=valid_data)
# 主要关注count长度分布的纵坐标，不需要绘制横坐标，横坐标范围通过dist图进行查看
plt.xticks([])
plt.show()

# 绘制dist长度分布图
sns.displot((valid_data["sentence_length"]))

# 主要关注dist长度分布横坐标，不需要绘制纵坐标
plt.yticks([])
plt.show()
```

绘制句子长度散点图

```python
# 绘制训练集句子长度散点图
sns.stripplot(y='sentence_length', x='label', data=train_data)
plt.show()

# 绘制验证集句子长度散点图
sns.stripplot(y='sentence_length', x='label', data=valid_data)
plt.show()
```

### 词频词云

获得训练集与验证集不同词汇总数统计

```
import jieba
# 扁平化列表工具包
from itertools import chain

# 获得训练集句子分词总数
train_vocab = set(chain(*map(lambda x: jieba.lcut(x), train_data["sentence"])))
print("训练集词汇总数：", len(train_vocab))
# 获得验证集句子分词总数
valid_vocab = set(chain(*map(lambda x: jieba.lcut(x), train_data["sentence"])))
print("验证集集词汇总数：", len(valid_vocab))
```

获得训练集上正负样本的高频词形容词词云

```python
import jieba.posseg as psg

# 获得训练集上正负样本的高频形容词词云
def get_a_list(text):
    # 使用jieba的词性标注方法切分文本，获得具有词性属性flag和词汇属性word的对象
    # 从而判断flag是否为形容词，来返回对应的词汇
    r = []
    for g in psg.lcut(text):
        if g.flag == "a":
            r.append(g.word)
    return r


# 导入绘制词云的工具包
from wordcloud import WordCloud


def get_word_cloud(keywords_list):
    # 实例化绘制词云的类，其中参数font_path是字体路径，为了能够显示中文
    # max_words指词云图像最多显示多少个词，background_color为背景色
    wordcloud = WordCloud(font_path="./SimHei.ttf", max_words=100)
    # 将传入的列表转换成词云生成器需要的字符串形式
    keywords_string = " ".join(keywords_list)
    # 生成词云
    wordcloud.generate(keywords_string)

    # 绘制图像并显示
    plt.figure()
    plt.imshow(wordcloud, interpolation="bilinear")
    plt.axis("off")
    plt.show()


# 获得训练集上的正样本
p_train_data = train_data[train_data["label"] == 1]["sentence"]
# 对正样本的每个句子的形容词
train_p_a_vocab = chain(*map(lambda x: get_a_list(x), p_train_data))

# 获得训练集上的负样本
n_train_data = train_data[train_data["label"] == 0]["sentence"]
# 对正样本的每个句子的形容词
train_n_a_vocab = chain(*map(lambda x: get_a_list(x), n_train_data))

# 绘制
get_word_cloud(train_p_a_vocab)
get_word_cloud(train_n_a_vocab)
```

获得验证集上正负样本的形容词词云

```python
# 获得验证集上正负样本的形容词词云
# 获得验证集上正样本
p_valid_data = valid_data[valid_data["label"] == 1]["sentence"]

# 对正样本的每个句子的形容词
valid_p_a_vocab = chain(*map(lambda x: get_a_list(x), p_valid_data))

# 获得验证集上负样本
n_valid_data = valid_data[valid_data["label"] == 0]["sentence"]

# 获得负样本的每个句子的形容词
valid_n_a_vocab = chain(*map(lambda x: get_a_list(x), n_valid_data))

# 调用绘制词云函数
get_word_cloud(valid_p_a_vocab)
get_word_cloud(valid_n_a_vocab)
```

## 4 文本特征处理

目标：

- 了结文本特征处理的作用
- 掌握实现常见文本特征处理的方法

文本特征处理的作用：

- 文本特征处理包括为语料添加具有普适性的文本特征，如n-gram特征，以及对加入特征之后的文本语料进行必要的处理，如长度规范，这些特征处理工作能够有效的将重要的文本特征加入模型训练中，增强模型评估指标

常见文本特征处理方法：

- 添加n-gram特征
- 文本长度规范

### n_gram

什么是n-gram特征：

- 给定一段文本序列，其中n个词或字的相邻共现特征即n-gram特征，常用的n-gram特征是bi-gram和tri-gram，分别对应n为2和3

举例：

```
假定分词列表['我们'，'永远'，'学习']
对应数值映射[1, 32, 51]
当我们添加二元特征，即假设'我们'和'永远'相邻，且特征为97
那么新的映射就是[1, 32, 51, 97]，这就是n-gram
```

提取n-garm特征代码：

```python
# 返回配对结果(1, 4), (6, 2)
def create_ngram_set(input_list):
    return set(zip(*[input_list[1:] for i in range(ngram_range)]))


input_list = [1, 4, 5, 6, 2]
res = create_ngram_set(input_list)
print(res)
```

### 文本长度规范

文本长度规范及其作用

- 一般模型的输入需要等尺寸大小，因此在进入模型前需要对每条文本数值映射后的长度进行规范，此时将根据句子长度分布分析出覆盖绝大多数文本的合理长度，对超长文本进行截断，对不足文本进行补齐

```python
from keras.preprocessing import sequence
# cutlen根据数据分析中句子长度分布，覆盖90%左右语料的最短长度
# 这里cut_len = 10
cutlen = 10


def padding(x_train):
    # 使用sequence.pad_sequence即可完成
    return sequence.pad_sequences(x_train, cutlen)


x_train = [[1, 23, 5, 32, 55, 63, 2, 21, 78, 32, 23, 1],
           [2, 32, 1, 23, 1]]

res = padding(x_train)
print(res)
```

## 5 文本数据增强

目标：

- 了解文本数据增强的作用
- 掌握实现常见的文本数据增强的具体方法

常见文本数据增强方法：

- 回译数据增强法

### 回译数据增强法

回译数据增强法一般基于google翻译接口，将文本数据翻译成另外一种语言（选择小语种），之后再翻译回原语言，即可认为得到与原语料同标签的新语料，新语料加入到原数据即可认为数据增强

```python
from keras.preprocessing import sequence
# 回译文本增强
p_sample1 = "酒店设施非常不错"
p_sample2 = "这里价格挺便宜的"
n_sample1 = "拖鞋发霉了，太差了"
n_sample2 = "电视不好用"

# 导入google翻译接口工具
from googletrans import Translator
# 实例化翻译对象
translator = Translator()
# 进行第一次批量翻译
translations = translator.translate([p_sample1, p_sample2, n_sample1, n_sample2], dest="ko")
# 获得翻译后的结果
ko_res = list(map(lambda x: x.text, translations))
# 打印结果
print("中间翻译结果：")
print(ko_res)

# 最后在翻译回中文
translations = translator.translate(ko_res, dest="zh-cn")
cn_res = list(map(lambda x: x.text, translations))
print("回译结果：")
print(cn_res)
```

## 6 案例：新闻主题分类任务

目标：

- 了解有关新闻主题分类和有关数据
- 掌握使用浅层网络构建新闻主题分类器的实现过程

以一段新闻报道中的文本描述内容为输入，使用模型帮助我们判断它最有可能属于哪一种类型的新闻。

案例分五步：

1. 构建带有Embedding层的文本分类模型
2. 对数据进行batch处理
3. 构建训练与验证函数
4. 进行模型训练和验证
5. 查看embedding层嵌入的词向量

### 第一步

```python
import torch
from torchtext.datasets import AG_NEWS  # 新闻分类数据
import torch.nn as nn
import torch.nn.functional as F
import hanlp

# 获取数据集
train_data, test_data = AG_NEWS(root='../data/news/', split=('train', 'test'))
# for x in enumerate(train_data):
#     print(x[1])
# 指定BATCH_SIZE
BATCH_SIZE = 16

# 进行可用设备检测，有GPU的话将优先使用GPU
device = torch.device("cuda" if torch.cuda.is_available() else "cpu")


class TextSentiment(nn.Module):
    def __init__(self, vocab_size, embed_dim, num_class):
        # vocab_szie 整个语料包含不同词汇总数
        # embed_dim 指定词嵌入的维度
        # num_class 文本分类的类别总数
        super(self).__init__()
        # 实例化embedding层，sparse=True代表每次对该层求解梯度时，只更新部分权重
        self.embedding = nn.Embedding(vocab_size, embed_dim, sparse=True)
        # 实例化线性层，参数分别是embeding_dim和num_class
        self.fc = nn.Linear(embed_dim, num_class)
        # 为各层初始化权重
        self.init_weights()

    def init_weights(self):
        # 指定初始化权重的取值范围数
        init_range = 5
        # 各层的权重参数都是初始化为均匀分布
        self.embedding.weight.data.uniform_(-init_range, init_range)
        self.fc.weight.data.uniform_(-init_range, init_range)
        # 偏置初始化为0
        self.fc.bias.dta.zero()

    def forward(self, text):
        # text: 文本数值映射后的结果
        # return 与类别尺寸相同的张量，用以判断文本类别

        # 获得embedding的结果embedded
        # embedded.shape
        # (m, 32)其中m是BATCH_SIZE大小的数据中词汇总数
        embedded = self.embedding(text)
        # 接下来我们需要将(m, 32)转化成(BATCH_SIZE, 32)
        # 以便通过fc层后能计算相应的损失
        # 首先，我们已经知道m的值远大于BATCH_SIZE
        # 用m整除BATCH_SIZE获得包含个BATCH_SIZE个数c
        c = embedded.size(0)
        # 之后再从embedded中取c*BATCH_SIZE个向量得到新的embedded
        # 这个新的向量可以整除BATCH_SIZE
        embedded = embedded[: BATCH_SIZE * c]
        # 因为我们想利用平均池化的方法求embedded中指定行数的列的平均数
        # 但平均池化方法是作用在行上的，并且需要3维输入
        # 因此我们对新的embedded进行转置并扩展维度
        embedded = embedded.transpose(1, 0).unsqueeze(0)
        # 然后就是调用平均池化的方法，并且核的大小为c
        # 即取每c的元素计算一次均值作为结果
        embedded = F.adaptive_avg_pool1d(embedded, output_size=c)
        # 最后还需要减去新增的维度，然后专职回去输送给fc层
        return self.fc(embedded[0].transpose(1, 0))


def get_vocab(data):
    """
    计算不同单词的总数
    :return:
    """
    cab_set = set()
    for d in enumerate(data):
        tokenizer = hanlp.load(hanlp.pretrained.tok.CTB6_CONVSEG)
        t = tokenizer(d[1][1])
        # print(t)
        for e in enumerate(t):
            cab_set.add(e)
    return len(cab_set)


# 获得整个预料包含的不同词汇总数
VOCAB_SIZE = get_vocab(train_data)
# VOCAB_SIZE = 1
print("VOCAB_SIZE:", VOCAB_SIZE)
# 指定词嵌入维度
EMBED_DIM = 32
# 获得类别总数
NUM_CLASS = 4
# 实例化模型
model = TextSentiment(VOCAB_SIZE, EMBED_DIM, NUM_CLASS).to(device)
```

### 第二步

```python
def generate_batch(batch):
    """
    申城batch数据函数
    :param batch: 由样本张量和对应标签的元组组成的batch_size大小的列表
            [(sample1, label1), ..., (sampleN, labelN)]
    :return: 样本张量和标签各自的列表形式
            text = tensor([sample1, ..., sampleN])
            label = tensor([label1, ..., labelN])
    """
    # 从batch中获得标签张量
    label = torch.tensor([entry[0] for entry in batch])
    # 从batch中获得样本张量
    text = torch.tensor([entry[1] for entry in batch])
    text = torch.cat(text)
    # 返回结果
    return text, label
```

### 第三步

```python
def train(train_data):
    """
    模型训练
    """
    train_loss = 0
    train_acc = 0

    # 使用数据加载器生成BATCH_SIZE大小的数据进行批次训练
    # data就是N多个generate_batch函数处理后的BATCH_SIZE大小的数据生成器
    data = DataLoader(train_data, batch_size=BATCH_SIZE, shuffle=True, collate_fn=generate_batch)
    optimizer = optim.Adam(model.parameters(), lr=0.01)
    # 对data进行循环遍历，使用每个batch的数据进行参数更新
    for i, (text, label) in enumerate(data):
        # 设置优化器初始梯度为0
        optimizer.zero_grad()
        # 模型输入一个批次数据，获得输出
        output = model(text)
        # 根据真是标签与模型输出计算损失
        loss = criterion(output, label)
        train_loss += loss.item()
        # 反向传播
        loss.backward()
        # 参数更新
        optimizer.step()
        # 准确率更新
        train_acc += (output.argmax(1) == label).sum().item()
    # 调整优化学习率
    scheduler.step()
    # 返回本轮训练的平均损失和平均准确率
    return train_loss / len(train_data), train_acc / len(train_data)


def valid(valid_data):
    loss = 0
    acc = 0
    data = DataLoader(valid_data, batch_size=BATCH_SIZE, collate_fn=generate_batch)
    for text, label in data:
        with torch.no_grad():
            output = model(text)
            loss = criterion(output, label)
            loss += loss.item()
            acc += (output.argmax(1) == label).sum().item()
    return loss / len(valid_data), acc / len(valid_data)
```

### 第四步

```python
from torch.utils.data.dataset import random_split

# 指定训练轮数
N_EPOCHS = 10
# 定义验证损失
min_valid_loss = float('inf')

# 选择损失函数
criterion = torch.nn.CrossEntropyLoss().to(device)
# 优化器
optimizer = optim.SGD(model.parameters(), lr=4)
# 选择优化器步长调节方法stepLR
scheduler = torch.optim.lr_scheduler.StepLR(optimizer, 1, gamma=0.9)
# 从train_dataset取0.95做训练集
train_len = int(len(train_data) *0.95)
# 然后乱序划分
sub_train, sub_valid = random_split(train_data, [train_len, len(train_data) - train_len])

# 开始训练
for epoch in range(N_EPOCHS):
    train_loss, train_acc = train(sub_train)
    valid_loss, valid_acc = train(sub_valid)
```

### 第五步

```python
# 打印词向量
print(model.state_dict()['embedding.weight'])
```

## 7 经典序列模型

目标：

- 了解HMM和CRF模型的输入输出、作用、使用过程、差异

HMM的输入输出：

- 隐含马尔科夫模型，一般以文本序列数据为输入，以该序列的隐含序列为输出

隐含序列：

- 序列数据中每个单元包含的隐性信息，这些隐性信息之间也存在一定关联

```
"人生该如何起头"
["人生", "该", "如何", "起头"]
那么对应词性的隐含序列就是
["n", "r", "r", "v"]
```

HMM模型的作用：

- 在NLP领域，HMM解决文本序列标注问题，如分词、词性标注

模型使用过程简述：

1. HMM模型表示为lambda = HMM(A, B, pi)，其中A，B，pi都是模型参数，分别是：转移概率矩阵，发射概率矩阵和初始概率矩阵
2. 开始训练HMM模型，语料是事先准备好的一定数量的观测序列及其对应的隐含序列，通过不断训练求一组参数，使由观测序列到对应隐含序列的概率最大
3. 训练过程中，隐含序列中每个单元的可能性只与上一个单元有关
4. 训练后得到新模型lambda = HMM(A, B, pi)
5. 之后给定输入序列(x1, x2, ..., xn)经过模型计算lambda(x1, x2, ..., xn)得到对应隐含序列的条件概率分布
6. 使用维特比算法从隐含序列的条件概率分布中找出概率最大的隐含序列



CRF模型的输入和输出：

- 隐含序列

CRF模型作用：

- 找隐含序列

CRF模型使用过程简述：

1. 模型表示lambda = CRF(w1, w2, ..., wn)，其中w1, ..., wn是模型参数
2. 输入一组观测序列语料和对应的隐含序列
3. 进行训练，得到新参数
4. 之后给定输入序列(x1, x2, ..., xn)经过模型计算lambda(x1, x2, ..., xn)得到对应隐含序列的条件概率分布
5. 使用维特比算法从隐含序列的条件概率分布中找出概率最大的隐含序列

HMM和CRF模型之间的差异：

- HMM模型存在隐马假设，而CRF不存在，因此HMM计算比CRF快
- 因为存在假设，HMM准确率低

## 8 RNN模型

目标：

- 什么是RNN模型
- RNN模型的作用
- RNN模型的分类

什么是RNN模型：

- RNN(Recurrent Neural Network)，循环神经网络，以序列数据为输入，以序列形式输出

只有一层，一个文本序列，依次将分词序列传入，一次传入一个词，每个词进去都得到新参数供下一个词使用

RNN模型作用：

- RNN能够很好的利用序列之间的关系，因此针对自然界具有连续性的输入序列，如人类语言、语音等进行很好的处理，广泛应用于NLP领域的各项任务，如文本分类、情感分析、意图识别、机器翻译等

RNN分类：

- 传统RNN
- LSTM
- Bi-LSTM
- GRU
- Bi-GRU

**N vs N RNN**

N个输入对应N个输出

RNN最基础的形式，最大的特点就是输入输出等长，因此适用范围较小，可用于生成等长度的诗句

**N vs 1 RNN**

在最后一个隐藏层输出上进行线性变换，用在文本分类上。使用sigmod或者softmax

**1 vs N RNN**

如果输入不是序列而输出是一个序列，将这个输入作用N次，得到N个输出，用于图片生成文字任务

**N vs M RNN**

也被称为seq2seq，输入数据先经过N层，然后变换为一个变量，这里相当于Nvs1，然后再将该输出经过M层，相当于1vsM，得到最终的M大小的序列输出

### 8.1 传统RNN模型

目标：

- 了解传统RNN内部结构和计算公式
- pytorch中的RNN工具
- 传统RNN优势与缺点

```python
import torch
import torch.nn as nn

# 输入张量x中特征维度大小
input_size = 5
# 隐藏层张量h中特征维度的大小
hidden_size = 6
# 隐藏层数量
num_layers = 1
# 激活函数
nonlinearity = 0
batch_size = 3

rnn = nn.RNN(input_size, hidden_size, num_layers)
# 输入张量x,序列长度，批次数量batch_size
input = torch.randn(1, batch_size, input_size)
# 隐藏张量
h0 = torch.randn(num_layers, batch_size, hidden_size)

output, hn = rnn(input, h0)
```
