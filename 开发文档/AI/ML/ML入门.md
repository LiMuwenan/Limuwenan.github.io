# 1 概述

## 1.1 监督学习和无监督学习

**监督学习 Supervised learning**

有目标的训练：

- 手动对输入进行分类，每种输入都有对应的输出标签，机器学习不同分类的特征，最终得到可分类的模型
- 邮件过滤
- 语音转文字
- 翻译

回归拟合

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E5%9B%9E%E5%BD%92.png)

分类

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E5%88%86%E7%B1%BB.png)



**无监督学习 Unsupervised learning**

没有目标的训练：

- 对于输入的训练集，将相同的类聚集在一起，称为**聚类算法**，但是这些类簇并没有明确的标签 

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E8%81%9A%E7%B1%BB.png)

## 1.2 线性回归模型和代价函数

一些约定俗成的东西：

$x$为输入，$y$为输出，$m$为样本总数，$(x, y)$表示输入对应的输出，$(x^{(i)},y^{(i)})$表示第$i$个样本

假设在房价模型中认为你和函数就是一条直线，得到一个函数模型
$$
f_{w,b}(x)=wx+b
$$
每一个输入$x$都对应一个$\hat y$预测值

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E7%BA%BF%E6%80%A7%E5%9B%9E%E5%BD%92%E6%A8%A1%E5%9E%8B1.png)

### 代价函数 cost function 

计算预测值和真实值的误差

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E4%BB%A3%E4%BB%B7%E5%87%BD%E6%95%B0.png)

在真实你和中，尽可能使代价函数计算出来值最小。

代价函数是关于参数的函数，最后使用梯度下降法求得极值，同时就得到了合理的各参数的值。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E4%BB%A3%E4%BB%B7%E5%87%BD%E6%95%B02.png)

###  梯度下降

局部最优

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E6%A2%AF%E5%BA%A6%E4%B8%8B%E9%99%8D%E6%B3%95.png)
$$
w = w-\alpha \frac{\partial}{\partial w}J(w,b)\\
b = b-\alpha \frac{\partial}{\partial b}J(w,b)\\

tmp\_w =w-\alpha \frac{\partial}{\partial w}J(w,b)\\
tmp\_b =b-\alpha \frac{\partial}{\partial b}J(w,b)\\
w=tmp\_w\\
b=tmp\_b\\
$$
注意同时更新参数

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E6%A2%AF%E5%BA%A6%E4%B8%8B%E9%99%8D2.png)

上图直观得描述了参数更新原理

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E5%A4%9A%E7%89%B9%E5%BE%81%E6%A2%AF%E5%BA%A6%E4%B8%8B%E9%99%8D.png)

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E6%A2%AF%E5%BA%A6%E4%B8%8B%E9%99%8D%E6%B1%82%E5%AF%BC.png)

```python
if __name__=='__main__':
    # 准备数据
    x = np.arange(1, 21)
    y = np.arange(1, 21)

    # f = w * x + b
    w = 2
    b = 0.5
    a = 0.009
    
    # 1000 次训练
    for i in range(1000):
        tw = 0
        tb = 0
        # 计算代价函数
        for j in range(20):
            t = (w * x[j] + b - y[j])
            tw += t * x[j]
            tb += t
        print(tw, tb)
        w = w - a * tw / 20
        b = b - a * tb / 20
        
    # 打印结果
    print(w)
    print(b)
```

### 特征缩放

在一些情况下，某个特征的变化不会很明显，这个时候就可以放大特征，让他分散的更加均匀，有利于训练

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E5%9D%87%E5%80%BC%E5%BD%92%E4%B8%80%E5%8C%96.png)

### **迭代次数曲线和自动收敛**

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E8%87%AA%E5%8A%A8%E6%94%B6%E6%95%9B.png)

根据迭代次数和代价函数的值可以画出来一个曲线，通常使用$10^{-3}$来表示自动收敛边界。即两次迭代计算的代价函数差值小于这个边界，即认为已经收敛

## 1.4 逻辑回归

分类

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E9%80%BB%E8%BE%91%E5%87%BD%E6%95%B0sigmod.png)

### 决策边界

将边界函数代入激活函数，就可以得到分类结果

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E5%86%B3%E7%AD%96%E8%BE%B9%E7%95%8C1.png)

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E5%86%B3%E7%AD%96%E8%BE%B9%E7%95%8C2.png)

### 逻辑回归的损失函数

损失函数。

回归中的代价函数在分类时容易出现多个局部极小值点，对于分类无益，使用下面的损失函数来规避这个问题

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E6%8D%9F%E5%A4%B1%E5%87%BD%E6%95%B0.png)

损失函数简化

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E7%AE%80%E5%8C%96%E6%8D%9F%E5%A4%B1%E5%87%BD%E6%95%B0.png)

损失函数求导

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E6%8D%9F%E5%A4%B1%E5%87%BD%E6%95%B0%E6%B1%82%E5%AF%BC.png)

即使求导看起来一样，但是实际上f函数的原型不同

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E5%9B%9E%E5%BD%92%E5%92%8C%E9%80%BB%E8%BE%91%E5%9B%9E%E5%BD%92.png)

## 1.5 过拟合

解决过拟合

- 增加训练样本
- 减少特征
- 正则化

### 正则化

保留一些特征，但是将该特征的影响降到很小，这样既不失去一些特征，又能很好的拟合数据

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E6%AD%A3%E5%88%99%E5%8C%96.png)

正则化项

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E6%AD%A3%E5%88%99%E5%8C%96%E9%A1%B9.png)

### 用于线性回归的正则方法

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E7%BA%BF%E6%80%A7%E5%9B%9E%E5%BD%92%E6%A2%AF%E5%BA%A6%E4%B8%8B%E9%99%8D.png)

简化一下

对$w_j$求到了，所以其它的$w$项就不需要了

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E7%BA%BF%E6%80%A7%E5%9B%9E%E5%BD%92%E6%AD%A3%E5%88%99%E5%8C%96%E5%AF%BC%E6%95%B0.png)

### 用于逻辑回归的正则方法

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E9%80%BB%E8%BE%91%E5%9B%9E%E5%BD%92%E6%AD%A3%E5%88%99%E5%8C%96%E5%AF%BC%E6%95%B0.png)

# 2 深度学习

## 2.1 简单的神经网络

- 输入层：4个参数
- 隐藏层：1个隐藏层，3个参数
- 输出层：1个参数

 ![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E7%AE%80%E5%8D%95%E7%A5%9E%E7%BB%8F%E7%BD%91%E7%BB%9C.png)

在设计神经网络的时候需要设计有多少个隐藏层和每个层有多少神经元

## 2.2 前向传播

从输入层的输入数据，一层一层递进，直到算到最终的输出层，这样的正向的计算过程就是前向传播

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E5%89%8D%E5%90%91%E4%BC%A0%E6%92%AD.png)

下面图中讲述了如何创建输入层、隐藏层、输出层（没有计算w,b的细节）

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/TensorFlow%E5%88%9B%E5%BB%BA%E7%A5%9E%E7%BB%8F%E7%BD%91%E7%BB%9C.png)

-  Sequential将两层连接到一起
- compile编译模型
- fit以x，y的数据进行训练，拟合模型，求了偏导数项。实现了反向传播
- predict进行预测

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E8%AE%AD%E7%BB%83%E8%BF%87%E7%A8%8B.png)

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E5%89%8D%E5%90%91%E4%BC%A0%E6%92%AD%E8%AF%A6%E7%BB%86.png)

### 图像训练举例

第二步编译模型的时候指定一个损失函数，该处指定的是**二元分类交叉熵损失函数**

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/tf%E5%9B%BE%E5%83%8F%E8%AE%AD%E7%BB%83%E4%BB%A3%E7%A0%81.png)

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E6%AD%A5%E9%AA%A4%E5%8E%9F%E7%90%86%E4%BB%A3%E7%A0%81%E5%AF%B9%E5%BA%94.png)

## 2.3 激活函数分类

- Sigmod
- Linear
- ReLU

## 2.4 多分类问题

softmax回归激活函数

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E9%80%BB%E8%BE%91%E5%9B%9E%E5%BD%92%E5%88%B0sofrmax.png)

一种手写数字识别的代码，输出层有10个神经元，每个神经元都有关于$z_1-z_{10}$的函数

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/softmax%E6%89%8B%E5%86%99%E6%95%B0%E5%AD%97%E8%AF%86%E5%88%AB.png)



## 2.5 Adam

Adam算法基于梯度下降算法，但是可以自适应的调整学习率，防止学习率过小或者过大

pytorch中也有类似下面中的，添加一个`optimizer`来确定使用`Adam`

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/Adam%E4%BB%A3%E7%A0%81.png)

## 2.6 卷积

以手写数字举例，在我们不使用全连接的神经网络时，可以使用像下面的方法

一个神经元可以只读取一小部分的像素而不是全部的像素，这样做的好处

- 更快的计算速度
- 可能只需要更少的数据
- 不容易过拟合

卷积就是从局部到整体的过程

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E5%8D%B7%E7%A7%AF%E4%B8%BE%E4%BE%8B.png)

## 2.7 评估模型

### 数据集分类

将数据集分为训练集和测试集，一般以$7:3$的比例分配

**模型选择和交叉验证**

创建模型的时候可以从一阶多项式到n阶进行假设，最终评估模型来选择哪个最好

将数据集分为训练集、交叉验证集和测试集，比例为$6:2:2$

训练集训练参数，然后选择在交叉验证集上表现最好的模型，到测试集上测试

[视频p71---p87关于评估的部分](https://www.bilibili.com/video/BV1Pa411X76s?p=71)

## 2.8 决策树

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E5%86%B3%E7%AD%96%E6%A0%91%E3%80%81.png)

### 训练过程

1.  如何选择每个节点的分类特征
   - 尽量使得分类后左右两边的类别区别最大（以DNA划分，可以明确得到左边是猫右边是狗，以其它的划分两天都有猫狗混杂）

2. 什么时候停止拆分
   - 什么时候可以明确的确定一个类
   - 到达树的最大深度
   - 分类已经比较明确，再细分下去意义不大
   - 树太高会过拟合

使用熵函数判断分类是否是“纯”的

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E7%86%B5%E5%87%BD%E6%95%B0.png)

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E7%86%B5%E5%87%BD%E6%95%B02.png)



### 降低熵

熵：就是分类的纯度，熵越高，分类越杂

熵$H(0.5)$减去加权平均熵得到信息增益，信息增益高代表熵的减少多，代表更 纯，分类更好

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E4%BF%A1%E6%81%AF%E5%A2%9E%E7%9B%8A.png)

### 决策树建立过程

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/%E5%86%B3%E7%AD%96%E6%A0%91%E5%BB%BA%E7%AB%8B%E8%BF%87%E7%A8%8B.png)

在按照同一个特征分类的时候，也要以该特征的不同值计算多次来确定最大信息增益。

### 随机森林

单个决策树可能对数据的变化十分的敏感，因此可以建立多个决策树，是的算法模型更加健壮

[p98--p100，随机森林，何时使用决策树](https://www.bilibili.com/video/BV1Pa411X76s?p=98)

## 2.9 聚类

`k-means`算法是最常见的聚类算法

第一步，随机选取两个（或n个）质心。根据每个数据点到质心的距离就可以将数据分类两组

 ![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/kmeans1.png)



 第二步，在第一步的基础上，计算每个簇中的平均值，这个平均值就是新的质心。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/AI/ML/kmeans2.png)

然后重复第一步和第二步，直到不再变化

[p105以及后面的视频还没有看](https://www.bilibili.com/video/BV1Pa411X76s?p=105)





[以下笔记学习于龙曲良博士得pytorch课程](https://www.bilibili.com/video/BV1ge41137b2?)

# 3 pytorch

## 3.1 实战：简单回归

求一个 $y=wx+b$ 的参数 $w,b$

有损失函数 $loss = \sum(WX+b-y)^2$

损失函数达到最小值的时候，就是得到 $w,b$ 值的时候

```python
import torch
import numpy as np


# 计算损失, points是样本
def compute_error_for_line_given_points(b, w, points):
    totalError = 0
    for i in range(0, len(points)):
        x = points[i, 0]
        y = points[i, 1]
        totalError += (y - (w * x + b)) ** 2  # 计算损失
    return totalError / float(len(points))


# 梯度下降
def step_gradient(b_current, w_current, points, learningRate):
    b_gradient = 0
    w_gradient = 0
    N = float(len(points))
    for i in range(0, len(points)):
        x = points[i, 0]
        y = points[i, 1]
        # 下面两项是w b参数的梯度下降公式
        b_gradient += -(2 / N) * (y - ((w_current * x) + b_current))
        w_gradient += -(2 / N) * (y - ((w_current * x) + b_current)) * x
    new_b = b_current - (learningRate * b_gradient)
    new_w = w_current - (learningRate * w_gradient)
    return [new_b, new_w]


# 进行迭代
def gradient_descent_runner(points, starting_b, starting_w, learningRate, num_iterations):
    b = starting_b
    w = starting_w
    for i in range(num_iterations):
        b, w = step_gradient(b, w, points, learningRate)
    return [b, w]


if __name__ == '__main__':
    # 装载数据
    points = np.array([[1, 2],
                       [2, 4],
                       [3, 6],
                       [5, 11],
                       [10, 22]])
    learningRate = 0.001
    b = 0
    w = 0

    b, w = gradient_descent_runner(points, b, w, learningRate, 2000)
    print(w, b)
```

## 3.2 实战：分类

手写数字，MNIST数据集

每张图片大小 28*28 像素，数据集中总共70000张图片，我们分以8:2的比例分为训练集和测试集

在处理的时候，把28*28的矩阵图像，reshape变为$[1, 784]$，1行784列，降维

输入数据$x=[1, 784]$

三层线性模型
$$
H_1 = XW_1 + b_1\\
H_2 = H_1W_2 + b_2\\
H_3 = H_2W_3 + b_3\\
$$
$x$的维度是$[1, 784]$，$w_1$的维度是$[784, d1]$，最终$H_1$的维度是$[1, d1]$

以此类推，$H_2$的维度是$[1, d2]$，$H_3$的维度是$[1, d3]$

手写数字最终分10类，所以骑士$d_3$最终应该是10，输出一个$[10,1]$的列向量，哪个位置有值，就说明该数字大概率是几

最终$H_3$后面再加一个激活函数。（该课程给每一层都加了激活函数ReLU）

```python
import torch
from torch import nn
from torch.nn import functional as F
from torch import optim

import torchvision
from matplotlib import pyplot as plt
from utils import plot_image, plot_curve, one_hot

# 1. load dataset
batch_size = 256
    # 下载数据集，并指定是训练集
    # numpy格式的数据转为tensor
    # 正则化
train_loader = torch.utils.data.DataLoader(
    torchvision.datasets.MNIST('../mnist_data', train=True, download=False,
                               transform=torchvision.transforms.Compose([
                                   torchvision.transforms.ToTensor(),
                                   torchvision.transforms.Normalize(
                                       (0.1307,), (0.3081,)
                                   )
                               ])),
    batch_size=batch_size, shuffle=True)
test_loader = torch.utils.data.DataLoader(
    torchvision.datasets.MNIST('../mnist_data/', train=False, download=False,
                               transform=torchvision.transforms.Compose([
                                   torchvision.transforms.ToTensor(),
                                   torchvision.transforms.Normalize(
                                       (0.1307,), (0.3081,)
                                   )
                               ])),
    batch_size=batch_size, shuffle=True)


# 随机取数据打印
x, y = next(iter(train_loader))
print(x.shape, y.shape, x.min(), y.min())
plot_image(x, y, 'image sample')

# 创建网络
class Net(nn.Module):

    def __init__(self):
        super(Net, self).__init__()

        # xw+b
        self.fc1 = nn.Linear(28*28, 256) # 第一层
        self.fc2 = nn.Linear(256, 64) # 第二层
        self.fc3 = nn.Linear(64, 10) # 第三层

    # 建立模型的前向传播过程
    def forward(self, x):
        # x: [b, 1, 28, 28]
        # h1 = xw1+b
        x = F.relu(self.fc1(x))
        # h2 = h1w2+b
        x = F.relu(self.fc2(x))
        # h3 = h2w3+b
        x = self.fc3(x)

        return x

net = Net()
optimizer = optim.SGD(net.parameters(), lr=0.01, momentum=0.9)


train_loss = []


for epoch in range(3):
    for batch_idx, (x, y) in enumerate(train_loader):

        # x: [b, 1, 28, 28] y: [512]
        # [b, 1, 28, 28] ==> [b, 784]
        x = x.view(x.size(0), 28*28)
        # ==> [b, 10]
        out = net(x)
        # [b, 10]
        y_onehot = one_hot(y)
        # loss = mse(out, y_onehot)
        loss = F.mse_loss(out, y_onehot)


        optimizer.zero_grad()
        loss.backward()
        # w' = w - lr*grad
        optimizer.step()

        train_loss.append(loss.item())

        # 打印看损失
        if batch_idx % 10 == 0:
            print(epoch, batch_idx, loss.item())

# 损失下降曲线
plot_curve(train_loss)
# we get optimal [w1, b1, w2, b2, w3, b3]


# 在测试集上看效果
total_correct = 0
for x, y in test_loader:
    x = x.view(x.size(0), 28*28)
    out = net(x)
    # out: [b, 10] ==> pred: [b]
    pred = out.argmax(dim=1) # 取最大值所在的索引
    correct = pred.eq(y).sum().float().item()
    total_correct += correct

total_num = len(test_loader.dataset)
acc = total_correct / total_num
print('test acc: ', acc)

```

用到的工具类

```python
import  torch
from matplotlib import pyplot as plt


# 下降曲线
def plot_curve(data):
    fig = plt.figure()
    plt.plot(range(len(data)), data, color='blue')
    plt.legend(['value'], loc='upper right')
    plt.xlabel('step')
    plt.ylabel('value')
    plt.show()

# 画图
def plot_image(img, label, name):
    fig = plt.figure()
    for i in range(6):
        plt.subplot(2, 3, i + 1)
        plt.tight_layout()
        plt.imshow(img[i][0]*0.3081+0.1307, cmap='gray', interpolation='none')
        plt.title("{}: {}".format(name, label[i].item()))
        plt.xticks([])
        plt.yticks([])
    plt.show()

# 将数据转换为一行
def one_hot(label, depth=10):
    out = torch.zeros(label.size(0), depth)
    idx = torch.LongTensor(label).view(-1, 1)
    out.scatter_(dim=1, index=idx, value=1)
    return out

```

## 3.3 基本操作

### 基本创建

```python
x = torch.rand(3, 3)  # 0-1之间采样
print(x)
print(x.size())
print(x.shape)
print(x.type())
print(torch.tensor(1.))
print(torch.randn(3, 3))  # 正态分布
print(torch.zeros(3, 3))  # 全零
print(torch.ones(3, 3))  # 全1
print(torch.full([2, 3], 7))  # 全部赋值为后面的值
print(torch.arange(1, 10))  # 等差数列
print(torch.arange(1, 10, 3))  # 等差数列, 3是公差
print(torch.linspace(0, 10, steps=4))  # 0-10等分为4个数
print(torch.logspace(1, 2, steps=8))  # 10的1次方到10的2次方，等分为8个数，可以设置底数
print(torch.eye(3, 4))  # 对角线
print(torch.randperm(10))  # 10个位置随机打散 

--------------------------
tensor([[0.5334, 0.4669, 0.8948],
        [0.6866, 0.9588, 0.1355],
        [0.4859, 0.9496, 0.7848]])
torch.Size([3, 3])
torch.Size([3, 3])
torch.FloatTensor
tensor(1.)
tensor([[ 0.0703, -0.0716,  0.2639],
        [ 0.5773, -0.2028, -1.3900],
        [ 0.7923,  0.7599, -0.3329]])
tensor([[0., 0., 0.],
        [0., 0., 0.],
        [0., 0., 0.]])
tensor([[1., 1., 1.],
        [1., 1., 1.],
        [1., 1., 1.]])
tensor([[7, 7, 7],
        [7, 7, 7]])
tensor([1, 2, 3, 4, 5, 6, 7, 8, 9])
tensor([1, 4, 7])
tensor([ 0.0000,  3.3333,  6.6667, 10.0000])
tensor([ 10.0000,  13.8950,  19.3070,  26.8270,  37.2759,  51.7947,  71.9686,
        100.0000])
tensor([[1., 0., 0., 0.],
        [0., 1., 0., 0.],
        [0., 0., 1., 0.]])
```

从numpy导入数据

```python
a = np.array([2, 3, 3])
print(torch.tensor(a))
print(torch.from_numpy(a))

-------------------------
tensor([2, 3, 3], dtype=torch.int32)
tensor([2, 3, 3], dtype=torch.int32)
```

小写tensor接收现有数据，大写Tensor接收维度

```python
print(torch.tensor([2, 3]))
print(torch.Tensor(2, 3))
print(torch.tensor([1, 2, 3, 4]))
print(torch.Tensor([1, 2, 3, 4]))

--------------------------------------
tensor([2, 3])
tensor([[-5.1724e+33,  4.9886e-43,  1.6109e-19],
        [ 1.8888e+31,  0.0000e+00,  0.0000e+00]])
tensor([1, 2, 3, 4])
tensor([1., 2., 3., 4.])
```

### 切片

```python
a = torch.rand(4, 3, 28, 28)  # 4维
# 4维，4组[3, 28, 28]
# 4张图片，每个图片是3通道，28*28像素
print(a[0].shape)  # 取第一张图片
print(a[0, 0].shape)  # 取第一张图片的第一个通道
print(a[0, 0, 2, 4])  # 取第一张图片的第一个通道的[2, 4]索引像素

# 冒号在前代表取这个数字前面的，冒号在后代表取这个数字后面的
print(a[:2].shape)  # 取第一张和第二张图片，下标从0开始，左开右闭
print(a[:2, :1, :, :].shape)  # 取第一张和第二张图片，取第一个通道，左开右闭
print(a[:2, 1:, :, :].shape)  # 取第一张和第二张图片，取第二三个通道，左开右闭
# 负号代表反向索引，一行数组按顺序从后向前标记
# [1, 2, 3, 4]
# [-4, -3, -2, -1]
print(a[:2, -1:, :, :].shape)  # 取第一张和第二张图片，取第三个通道，左开右闭，反向索引
print(a[:, :, 0:28:2, 0:28:2].shape)  # 两个像素取一个，隔行。2有点像公差
print(a[:, :, ::2, ::2].shape)  # ::就相当于是全部区间，算是上一行的简写
# 第一个0代表我要操作的第一个维度，也就是图片张数
# tensor([0, 2]) 代表我取第0张和第2张
# 第二个参数必须是tensor
print(a.index_select(0, torch.tensor([0, 2])).shape)
# 第一个1代表我要操作的第二个维度，也就是图片通道数
# tensor([0, 2]) 代表我取第0个通道和第2个通道
print(a.index_select(1, torch.tensor([0, 2])).shape)


----------------------------
torch.Size([3, 28, 28])
torch.Size([28, 28])
tensor(0.2841)
torch.Size([2, 3, 28, 28])
torch.Size([2, 1, 28, 28])
torch.Size([2, 2, 28, 28])
torch.Size([2, 1, 28, 28])
torch.Size([4, 3, 14, 14])
torch.Size([4, 3, 14, 14])
torch.Size([2, 3, 28, 28])
torch.Size([4, 2, 28, 28])
```

### 维度变换

- view
- reshape

```python
a = torch.rand(4, 1, 28, 28) # 4张单通道图片
# view和reshape几乎一致,变化的时候不考虑数据的实际意义
print(a)
print(a.shape)
print(a.view(4, 28 * 28)) # 合并了后三维，这里明显元素数量是不变的
print(a.view(4, 28 * 28).shape) # 打平了

------------------------------
维度变换
tensor([[[[0.5642, 0.1171, 0.6789,  ..., 0.4663, 0.0067, 0.9747],
          [0.3212, 0.3808, 0.6895,  ..., 0.9125, 0.6671, 0.9446],
          [0.9156, 0.7842, 0.4021,  ..., 0.0570, 0.9403, 0.0750],
          ...,
          [0.8874, 0.3825, 0.5497,  ..., 0.5946, 0.8964, 0.9297],
          [0.5209, 0.2341, 0.1085,  ..., 0.0259, 0.8455, 0.2209],
          [0.5857, 0.8299, 0.1303,  ..., 0.7095, 0.2924, 0.6871]]],


        [[[0.1840, 0.8029, 0.4443,  ..., 0.0501, 0.1859, 0.7801],
          [0.4445, 0.9749, 0.5675,  ..., 0.0734, 0.9479, 0.9376],
          [0.6218, 0.5331, 0.0155,  ..., 0.4899, 0.8079, 0.0918],
          ...,
          [0.9046, 0.2880, 0.8036,  ..., 0.9376, 0.6478, 0.7305],
          [0.2678, 0.6180, 0.1096,  ..., 0.4329, 0.7087, 0.1792],
          [0.3854, 0.8486, 0.4914,  ..., 0.3995, 0.2137, 0.3017]]],


        [[[0.3124, 0.7772, 0.4204,  ..., 0.0768, 0.3774, 0.6074],
          [0.3296, 0.8975, 0.5874,  ..., 0.8012, 0.8309, 0.1456],
          [0.6894, 0.1869, 0.4023,  ..., 0.4458, 0.9587, 0.8217],
          ...,
          [0.8269, 0.7860, 0.7444,  ..., 0.6394, 0.9035, 0.5518],
          [0.7023, 0.0081, 0.4442,  ..., 0.9396, 0.7994, 0.6250],
          [0.7633, 0.8903, 0.7737,  ..., 0.2106, 0.7089, 0.6703]]],


        [[[0.7503, 0.3868, 0.4362,  ..., 0.5992, 0.8978, 0.2486],
          [0.2234, 0.2597, 0.6134,  ..., 0.4099, 0.6800, 0.3336],
          [0.3187, 0.2827, 0.0732,  ..., 0.4304, 0.2838, 0.7815],
          ...,
          [0.9837, 0.6338, 0.5305,  ..., 0.6417, 0.7894, 0.9288],
          [0.3198, 0.7546, 0.8201,  ..., 0.1042, 0.6630, 0.7715],
          [0.6224, 0.8931, 0.8658,  ..., 0.9252, 0.7985, 0.2519]]]])
torch.Size([4, 1, 28, 28])
tensor([[0.5642, 0.1171, 0.6789,  ..., 0.7095, 0.2924, 0.6871],
        [0.1840, 0.8029, 0.4443,  ..., 0.3995, 0.2137, 0.3017],
        [0.3124, 0.7772, 0.4204,  ..., 0.2106, 0.7089, 0.6703],
        [0.7503, 0.3868, 0.4362,  ..., 0.9252, 0.7985, 0.2519]])
torch.Size([4, 784])
```

- squeeze
- unsqueeze

```python
print(a.shape)
# unsqueeze相当于插入一个维度，数据维持不变
print(a.unsqueeze(0).shape)  # 第一维之前插入，在图片前又增加一维，可以理解为组的概念，这个组有4张图片
print(a.unsqueeze(-1).shape)  # 在图片像素后增加一维，可以理解像素上还携带某种参数
print(a.unsqueeze(4).shape)  # 在图片像素后增加一维，可以理解像素上还携带某种参数
print(a.unsqueeze(-4).shape)  # 负数还是反向的意思
b = torch.rand(32)
print(b.shape)
# 扩展b到[1, 32, 1, 1]
b = b.unsqueeze(1).unsqueeze(2).unsqueeze(0)
print(b.shape)  # 理解为，1张图片，有32个通道，每个通道一个像素点

# squeeze删除维度
print(b.squeeze().shape)  # 将没用的维度自动删除掉
print(b.squeeze(0).shape)
print(b.squeeze(1).shape) # 这个维度有用，所以没有挤压掉

------------------------------
挤压 展开
torch.Size([4, 1, 28, 28])
torch.Size([1, 4, 1, 28, 28])
torch.Size([4, 1, 28, 28, 1])
torch.Size([4, 1, 28, 28, 1])
torch.Size([4, 1, 1, 28, 28])
torch.Size([32])
torch.Size([1, 32, 1, 1])
torch.Size([32])
torch.Size([32, 1, 1])
torch.Size([1, 32, 1, 1])
```

- transpose
- t
- permute

```python
a = torch.rand(3, 4)
print(a.t())  # 只能适用于2维
a = torch.rand(4, 1, 32, 32)
print(a.transpose(0, 1).shape)  # 维度变换的时候一定要人为跟踪，清楚里面的出具关系
print(a.permute(0, 2, 3, 1).shape)  # 直接指定原来的维度和现在的维度关系
------------------------------
转置
tensor([[0.1177, 0.1311, 0.8804],
        [0.3438, 0.9490, 0.3892],
        [0.6050, 0.4042, 0.6659],
        [0.5697, 0.3408, 0.6146]])
torch.Size([1, 4, 32, 32])
torch.Size([4, 32, 32, 1])
```

- expand
- repeat

```python
print('-' * 30)
print('扩展')
a = torch.rand(4, 32, 14, 14)
print(b.shape)  # 经过了展开操作
# expand操作必须保证变换前的维度和变换后的维度相同
# 变换前后同维上数量不同的时候，变换前必须是1
# 即[1, 32, 1, 1] => [4, 32, 14, 14] 可行，[1, 26, 1, 1] => [4, 32, 14, 14]不可行
print(b.expand(4, 32, 14, 14).shape)

# 第一个维度拷贝4次，第二个维度拷贝32次，后两个维度拷贝1次
print(b.repeat(4, 32, 1, 1).shape)

------------------------------
扩展
torch.Size([1, 32, 1, 1])
torch.Size([4, 32, 14, 14])
torch.Size([4, 1024, 1, 1])
```

### 拼接与拆分

- cat

```python
a = torch.rand(4, 32, 8)  # 4个班32个学生的8门成绩
b = torch.rand(5, 32, 8)
# 拼接的时候其余维度需要相同
print(torch.cat([a, b], dim=0).shape)  # 拼接第一个维度

------------------------------
拼接与拆分
torch.Size([9, 32, 8])
```

- stack

```python
# 两个班级，每个班32个人的8门成绩 ==》 [2, 32, 8]
print(torch.stack([torch.rand(32, 8), torch.rand(32, 8)], dim=0).shape)
----------------------------------
torch.Size([2, 32, 8])
```

- split

- chunk

```python
# 将数据按指定的比例分好
c = torch.rand(3, 32, 8)
aa, bb = c.split([1, 2], dim=0)
print(aa.shape)
print(bb.shape)
# 等分
aa, bb = c.split(2, dim=0)
print(aa.shape)
print(bb.shape)
----------------------------------
torch.Size([1, 32, 8])
torch.Size([2, 32, 8])
torch.Size([2, 32, 8])
torch.Size([1, 32, 8])
```

### 基本运算

```python
a = torch.rand(2, 3)
print(a)
b = torch.rand(3)  # 进行了自动扩展
print(b)
# 加法
print(a + b)
print(torch.add(a, b))
# 减法
# 乘法
torch.matmul(a, b)  # 矩阵点乘
# 除法
# 次方
a = torch.full([2, 2], 3)
print(a.pow(2))
print(a ** 2)
print(a.pow(2).sqrt())
print(a ** (0.5))
# 自然指数
print(torch.exp(torch.ones(2, 2)))
print(torch.log(2))
# 两个tensor是否相等
# torch.eq(a, b) # 每个位置比较，该位置相等返回1，不等返回0
# torch.gt() # 绝对大于
# torch.le() # 小于等于
# torch.ge() # 大于等于
# torch.lt() # 绝对小于

------------------------------
运算
tensor([[0.1985, 0.0444, 0.5888],
        [0.7430, 0.4785, 0.1366]])
tensor([0.7295, 0.4922, 0.5521])
tensor([[0.9279, 0.5366, 1.1409],
        [1.4725, 0.9707, 0.6887]])
tensor([[0.9279, 0.5366, 1.1409],
        [1.4725, 0.9707, 0.6887]])
tensor([[9, 9],
        [9, 9]])
tensor([[9, 9],
        [9, 9]])
tensor([[3., 3.],
        [3., 3.]])
tensor([[1.7321, 1.7321],
        [1.7321, 1.7321]])
tensor([[2.7183, 2.7183],
        [2.7183, 2.7183]])
```

### 统计运算

- norm

```python
a = torch.full([8], 1)
b = a.view(2, 4)
c = a.view(2, 2, 2)
# 1范数 2范数
# a.norm(1)
# a.norm(2)
```

- mean sum

- prod
- max,min,argmin,argmax

```python
a = torch.arange(1, 9).view(2, 4).float()  # 1-8
print(a.min(), a.max(), a.mean(), a.prod())
print(a.sum(), a.mean())
print(a.argmax(), a.argmin())  # 返回最大值和最小值所在的索引
------------------------------
统计运算
tensor(1.) tensor(8.) tensor(4.5000) tensor(40320.)
tensor(36.) tensor(4.5000)
tensor(7) tensor(0)
```

- kthvalue,topk

```python
print(a.topk(2, dim=1))  # 维度上最大值
print(a.kthvalue(2))  # 每个维度上第二大的数字
--------------------------------------------
torch.return_types.topk(
values=tensor([[4., 3.],
        [8., 7.]]),
indices=tensor([[3, 2],
        [3, 2]]))
torch.return_types.kthvalue(
values=tensor([2., 6.]),
indices=tensor([1, 1]))
```

### 高级操作

- where

```python
# c = torch.where(condition, A, B)
# 数据来源是A或B
# condition A B 是同维度的，如果满足条件走A，不满足条件走B
```

- gather

## 3.4 常见损失函数

$$
loss = \sum[y-(xw+b)]^2\\
L2-norm=||y-(xw+b)||_2\\
loss=norm(y-(xw+b))^2\\
$$

## 3.5 实战：二维函数优化

$$
f(x,y)=(x^2+y-11)^2+(x+y^2-7)^2\\
$$

```python
import torch
import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

def himmelblau(x):
    # x[0]是x, x[1]是y
    return (x[0] ** 2 + x[1] - 11) ** 2 + (x[0] + x[1] ** 2 - 7) ** 2


x = np.arange(-6, 6, 0.1)
y = np.arange(-6, 6, 0.1)
print('x,y range:', x.shape, y.shape)
X, Y = np.meshgrid(x, y)
print('X,Y maps:', X.shape, Y.shape)
Z = himmelblau([X, Y])

# fig = plt.figure() # 3.6.2版本画图报错
# ax = fig.gca(projection='3d')
# ax.plot_surface(X, Y, Z)
# ax.view_init(60, -30)
# ax.set_xlabel('x')
# ax.set_ylabel('y')
# plt.show()

# 随机梯度下降求解
x = torch.tensor([0., 0.], requires_grad=True)
optimizer = torch.optim.Adam([x], lr=1e-3)  # Adam是常用的梯度下降算法
for step in range(20000):
    pred = himmelblau(x)

    optimizer.zero_grad()  # 梯度信息清零
    pred.backward()  # 求梯度
    optimizer.step()  # 更新参数

    if step % 2000 == 0:
        print('step {}: x = {}, f(x) = {}'.format(step, x.tolist(), pred.item()))
```

## 3.6 实战：多分类问题

```python
import torch
from torch.nn import functional as F
import torch.optim as optim
import torch.nn as nn
import torchvision

learning_rate = 0.001
batch_size = 200
epochs = 10

# 下载数据集
train_loader = torch.utils.data.DataLoader(
    torchvision.datasets.MNIST('../mnist_data', train=True, download=False,
                               transform=torchvision.transforms.Compose([
                                   torchvision.transforms.ToTensor(),
                                   torchvision.transforms.Normalize(
                                       (0.1307,), (0.3081,)
                                   )
                               ])),
    batch_size=batch_size, shuffle=True)
test_loader = torch.utils.data.DataLoader(
    torchvision.datasets.MNIST('../mnist_data/', train=False, download=False,
                               transform=torchvision.transforms.Compose([
                                   torchvision.transforms.ToTensor(),
                                   torchvision.transforms.Normalize(
                                       (0.1307,), (0.3081,)
                                   )
                               ])),
    batch_size=batch_size, shuffle=True)

# 第一层
w1 = torch.randn(200, 784, requires_grad=True)
b1 = torch.randn(200, requires_grad=True)
# 第二层
w2 = torch.randn(200, 200, requires_grad=True)
b2 = torch.randn(200, requires_grad=True)
# 第三层
w3 = torch.randn(10, 200, requires_grad=True)
b3 = torch.randn(10, requires_grad=True)

# 数据初始化，没有初始化会导致loss不变
torch.nn.init.kaiming_normal_(w1)
torch.nn.init.kaiming_normal_(w2)
torch.nn.init.kaiming_normal_(w3)

# 建立前向传播模型
def forward(x):
    x = x @ w1.t() + b1
    x = F.relu(x)
    x = x @ w2.t() + b2
    x = F.relu(x)
    x = x @ w3.t() + b3
    x = F.relu(x)

    return x


optimizer = optim.SGD([w1, b1, w2, b2, w3, b3], lr=learning_rate)
criteon = nn.CrossEntropyLoss()

for epoch in range(epochs):
    for batch_idx, (data, target) in enumerate(train_loader):
        data = data.view(-1, 28 * 28)

        logits = forward(data)
        loss = criteon(logits, target)

        optimizer.zero_grad()
        loss.backward()
        optimizer.step()

        if batch_idx % 20 == 0:
            print('loss:', loss)

    test_loss = 0
    correct = 0
    for data, target in test_loader:
        data = data.view(-1, 28 * 28)
        logits = forward(data)
        test_loss += criteon(logits, target).item()

        pred = logits.data.max(1)[1]
        correct += pred.eq(target.data).sum()

    test_loss /= len(test_loader.dataset)
    print('\nTest set: Average loss: {:.4f}, Accuracy: {}/{} ({:.0f}%)\n'.format(
        test_loss, correct, len(test_loader.dataset),
        100. * correct / len(test_loader.dataset)
    ))
```

## 3.7 指定设备

```python
device = torch.device('cuda:0')
device = torch.device('cpu')
.to(device)
```

