# 1 命令

## 1.1 仓库

    在当前目录新建一个Git代码库
    $ git init
    
    新建一个目录，将其初始化为Git代码库
    $ git init [project-name]
    
    下载一个项目和它的整个代码历史
    $ git clone [url]

## 1.2 配置

    显示当前的Git配置
    $ git config --list
    
    编辑Git配置文件
    $ git config -e [--global]
    
    设置提交代码时的用户信息
    $ git config [--global] user.name "[name]"
    $ git config [--global] user.email "[email address]"

## 1.3 增加/删除文件

    添加指定文件到暂存区
    $ git add [file1] [file2] ...
    
    添加指定目录到暂存区，包括子目录
    $ git add [dir]
    
    添加当前目录的所有文件到暂存区
    $ git add .
    
    添加每个变化前，都会要求确认
    对于同一个文件的多处变化，可以实现分次提交
    $ git add -p
    
    删除工作区文件，并且将这次删除放入暂存区
    $ git rm [file1] [file2] ...
    
    停止追踪指定文件，但该文件会保留在工作区
    $ git rm --cached [file]
    
    改名文件，并且将这个改名放入暂存区
    $ git mv [file-original] [file-renamed]
    
    代码提交
    
    提交暂存区到仓库区
    $ git commit -m [message]
    
    提交暂存区的指定文件到仓库区
    $ git commit [file1] [file2] ... -m [message]
    
    提交工作区自上次commit之后的变化，直接到仓库区
    $ git commit -a
    
    提交时显示所有diff信息
    $ git commit -v
    
    使用一次新的commit，替代上一次提交
    如果代码没有任何新变化，则用来改写上一次commit的提交信息
    $ git commit --amend -m [message]
    
    重做上一次commit，并包括指定文件的新变化
    $ git commit --amend [file1] [file2] ...

## 1.4 分支

    列出所有本地分支
    $ git branch
    
    列出所有远程分支
    $ git branch -r
    
    列出所有本地分支和远程分支
    $ git branch -a
    
    新建一个分支，但依然停留在当前分支
    $ git branch [branch-name]
    
    新建一个分支，并切换到该分支
    $ git checkout -b [branch]
    
    新建一个分支，指向指定commit
    $ git branch [branch] [commit]
    
    新建一个分支，与指定的远程分支建立追踪关系
    $ git branch --track [branch] [remote-branch]
    
    切换到指定分支，并更新工作区
    $ git checkout [branch-name]
    
    切换到上一个分支
    $ git checkout -
    
    建立追踪关系，在现有分支与指定的远程分支之间
    $ git branch --set-upstream [branch] [remote-branch]
    
    合并指定分支到当前分支
    $ git merge [branch]
    
    选择一个commit，合并进当前分支
    $ git cherry-pick [commit]
    
    删除分支
    $ git branch -d [branch-name]
    
    删除远程分支
    $ git push origin --delete [branch-name]
    $ git branch -dr [remote/branch]
    
    标签
    
    列出所有tag
    $ git tag
    
    新建一个tag在当前commit
    $ git tag [tag]
    
    新建一个tag在指定commit
    $ git tag [tag] [commit]
    
    删除本地tag
    $ git tag -d [tag]
    
    删除远程tag
    $ git push origin :refs/tags/[tagName]
    
    查看tag信息
    $ git show [tag]
    
    提交指定tag
    $ git push [remote] [tag]
    
    提交所有tag
    $ git push [remote] --tags
    
    新建一个分支，指向某个tag
    $ git checkout -b [branch] [tag]

## 1.5 查看信息

    显示有变更的文件
    $ git status
    
    显示当前分支的版本历史
    $ git log
    
    显示commit历史，以及每次commit发生变更的文件
    $ git log --stat
    
    搜索提交历史，根据关键词
    $ git log -S [keyword]
    
    显示某个commit之后的所有变动，每个commit占据一行
    $ git log [tag] HEAD --pretty=format:%s
    
    显示某个commit之后的所有变动，其"提交说明"必须符合搜索条件
    $ git log [tag] HEAD --grep feature
    
    显示某个文件的版本历史，包括文件改名
    $ git log --follow [file]
    $ git whatchanged [file]
    
    显示指定文件相关的每一次diff
    $ git log -p [file]
    
    显示过去5次提交
    $ git log -5 --pretty --oneline
    
    显示所有提交过的用户，按提交次数排序
    $ git shortlog -sn
    
    显示指定文件是什么人在什么时间修改过
    $ git blame [file]
    
    显示暂存区和工作区的差异
    $ git diff
    
    显示暂存区和上一个commit的差异
    $ git diff --cached [file]
    
    显示工作区与当前分支最新commit之间的差异
    $ git diff HEAD
    
    显示两次提交之间的差异
    $ git diff [first-branch]...[second-branch]
    
    显示今天你写了多少行代码
    $ git diff --shortstat "@{0 day ago}"
    
    显示某次提交的元数据和内容变化
    $ git show [commit]
    
    显示某次提交发生变化的文件
    $ git show --name-only [commit]
    
    显示某次提交时，某个文件的内容
    $ git show [commit]:[filename]
    
    显示当前分支的最近几次提交
    $ git reflog

## 1.6 远程同步

    下载远程仓库的所有变动
    $ git fetch [remote]
    
    显示所有远程仓库
    $ git remote -v
    
    显示某个远程仓库的信息
    $ git remote show [remote]
    
    增加一个新的远程仓库，并命名
    $ git remote add [shortname] [url]
    
    取回远程仓库的变化，并与本地分支合并
    $ git pull [remote] [branch]
    
    上传本地指定分支到远程仓库
    $ git push [remote] [branch]
    
    强行推送当前分支到远程仓库，即使有冲突
    $ git push [remote] --force
    
    推送所有分支到远程仓库
    $ git push [remote] --all
    
    撤销
    
    恢复暂存区的指定文件到工作区
    $ git checkout [file]
    
    恢复某个commit的指定文件到暂存区和工作区
    $ git checkout [commit] [file]
    
    恢复暂存区的所有文件到工作区
    $ git checkout .
    
    重置暂存区的指定文件，与上一次commit保持一致，但工作区不变
    $ git reset [file]
    
    重置暂存区与工作区，与上一次commit保持一致
    $ git reset --hard
    
    重置当前分支的指针为指定commit，同时重置暂存区，但工作区不变
    $ git reset [commit]
    
    重置当前分支的HEAD为指定commit，同时重置暂存区和工作区，与指定commit一致
    $ git reset --hard [commit]
    
    重置当前HEAD为指定commit，但保持暂存区和工作区不变
    $ git reset --keep [commit]
    
    新建一个commit，用来撤销指定commit
    后者的所有变化都将被前者抵消，并且应用到当前分支
    $ git revert [commit]
    
    暂时将未提交的变化移除，稍后再移入
    $ git stash
    $ git stash pop

## 1.7 其他

    生成一个可供发布的压缩包
    $ git archive

## 1.8 Gitee


本地创建一个文件夹作为仓库，进入该文件夹，执行：  
```git
git init
```

在Gitee上创建一个仓库，并添加自己电脑的ssh公钥。公钥在设置中添加而不是仓库中。  

并复制ssh地址  



Git 全局设置:
```
git config --global user.name "Ligen"
git config --global user.email "807404872@qq.com"
```
创建 git 仓库:
```
mkdir mupdftest
cd mupdftest
git init
touch README.md
git add README.md
git commit -m "first commit"
git remote add origin git@gitee.com:ligen0121/mupdftest.git
git push -u origin master
```
已有仓库?
```
cd existing_git_repo
git remote add origin git@gitee.com:ligen0121/mupdftest.git
git push -u origin master
```

```git
git remote -v  查看链接的仓库
git remote rm reponame  断开链接的仓库
```

fatal: in unpopulated submodule 'xxx'  
在xxx的上一级目录执行

```git
git rm -rf --cached xxx/
git add xxx
```

# 3 git

**Git相关的配置文件：**

1）、Git\etc\gitconfig  ：Git 安装目录下的 gitconfig   --system 系统级

2）、C:\Users\Administrator\ .gitconfig   只适用于当前登录用户的配置  --global 全局

## 设置用户名与邮箱（用户标识，必要）

当你安装Git后首先要做的事情是设置你的用户名称和e-mail地址。这是非常重要的，因为每次Git提交都会使用该信息。它被永远的嵌入到了你的提交中：

```
git config --global user.name "kuangshen"  #名称
git config --global user.email 24736743@qq.com   #邮箱
```

只需要做一次这个设置，如果你传递了--global 选项，因为Git将总是会使用该信息来处理你在系统中所做的一切操作。如果你希望在一个特定的项目中使用不同的名称或e-mail地址，你可以在该项目中运行该命令而不要--global选项。总之--global为全局配置，不加为某个项目的特定配置。

## 3.1 基本理论

### 3.1.1 三个区域

Git本地有三个工作区域：工作目录（Working Directory）、暂存区(Stage/Index)、资源库(Repository或Git Directory)。如果在加上远程的git仓库(Remote Directory)就可以分为四个工作区域。文件在这四个区域之间的转换关系如下：

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git-region.jpg" alt="region" style="zoom: 80%;" />

- Workspace：工作区，就是你平时存放项目代码的地方
- Index / Stage：暂存区，用于临时存放你的改动，事实上它只是一个文件，保存即将提交到文件列表信息
- Repository：仓库区（或本地仓库），就是安全存放数据的位置，这里面有你提交到所有版本的数据。其中HEAD指向最新放入仓库的版本
- Remote：远程仓库，托管代码的服务器，可以简单的认为是你项目组中的一台电脑用于远程数据交换

本地的三个区域确切的说应该是git仓库中HEAD指向的版本：

<img src="https://github.com/LiMuwenan/PicBed/blob/master/img/dev/git/git-directionary.jpg?raw=true" style="zoom:67%;" />

- Directory：使用Git管理的一个目录，也就是一个仓库，包含我们的工作空间和Git的管理空间。
- WorkSpace：需要通过Git进行版本控制的目录和文件，这些目录和文件组成了工作空间。
- .git：存放Git管理信息的目录，初始化仓库的时候自动创建。
- Index/Stage：暂存区，或者叫待提交更新区，在提交进入repo之前，我们可以把所有的更新放在暂存区。
- Local Repo：本地仓库，一个存放在本地的版本库；HEAD会只是当前的开发分支（branch）。
- Stash：隐藏，是一个工作状态保存栈，用于保存/恢复WorkSpace中的临时状态。

### 3.1.2 工作流程

git的工作流程一般是这样的：

１、在工作目录中添加、修改文件；

２、将需要进行版本管理的文件放入暂存区域；

３、将暂存区域的文件提交到git仓库。

因此，git管理的文件有三种状态：已修改（modified）,已暂存（staged）,已提交(committed)

<img src="https://github.com/LiMuwenan/PicBed/blob/master/img/dev/git/git-routine.jpg?raw=true" style="zoom:67%;" />

## 3.2 项目搭建

### 3.2.1 本地仓库搭建

创建本地仓库的方法有两种：一种是创建全新的仓库，另一种是克隆远程仓库。

1、创建全新的仓库，需要用GIT管理的项目的根目录执行：

```
# 在当前目录新建一个Git代码库
$ git init
```

2、执行后可以看到，仅仅在项目目录多出了一个.git目录，关于版本等的所有信息都在这个目录里面。

#### 3.2.2 克隆远程仓库

1、另一种方式是克隆远程目录，由于是将远程服务器上的仓库完全镜像一份至本地！

```
# 克隆一个项目和它的整个代码历史(版本信息)
$ git clone [url]  # https://gitee.com/kuangstudy/openclass.git
```

2、去 gitee 或者 github 上克隆一个测试！

## 3.3 git文件操作

### 3.3.1 文件的四种状态

版本控制就是对文件的版本控制，要对文件进行修改、提交等操作，首先要知道文件当前在什么状态，不然可能会提交了现在还不想提交的文件，或者要提交的文件没提交上。

- Untracked: 未跟踪, 此文件在文件夹中, 但并没有加入到git库, 不参与版本控制. 通过git add 状态变为Staged.
- Unmodify: 文件已经入库, 未修改, 即版本库中的文件快照内容与文件夹中完全一致. 这种类型的文件有两种去处, 如果它被修改, 而变为Modified. 如果使用git rm移出版本库, 则成为Untracked文件
- Modified: 文件已修改, 仅仅是修改, 并没有进行其他的操作. 这个文件也有两个去处, 通过git add可进入暂存staged状态, 使用git  checkout 则丢弃修改过, 返回到unmodify状态, 这个git checkout即从库中取出文件, 覆盖当前修改 !
- Staged: 暂存状态. 执行git commit则将修改同步到库中, 这时库中的文件和本地文件又变为一致, 文件为Unmodify状态. 执行git reset HEAD filename取消暂存, 文件状态为Modified

### 3.3.2 查看文件状态

上面说文件有4种状态，通过如下命令可以查看到文件的状态：

```
#查看指定文件状态
git status [filename]
#查看所有文件状态g
it status
# git add .                  添加所有文件到暂存区
# git commit -m "消息内容"    提交暂存区中的内容到本地仓库 -m 提交信息
```



### 3.3.3 忽略文件

有些时候我们不想把某些文件纳入版本控制中，比如数据库文件，临时文件，设计文件等

在主目录下建立".gitignore"文件，此文件有如下规则：

1. 忽略文件中的空行或以井号（#）开始的行将会被忽略。
2. 可以使用Linux通配符。例如：星号（*）代表任意多个字符，问号（？）代表一个字符，方括号（[abc]）代表可选字符范围，大括号（{string1,string2,...}）代表可选的字符串等。
3. 如果名称的最前面有一个感叹号（!），表示例外规则，将不被忽略。
4. 如果名称的最前面是一个路径分隔符（/），表示要忽略的文件在此目录下，而子目录中的文件不忽略。
5. 如果名称的最后面是一个路径分隔符（/），表示要忽略的是此目录下该名称的子目录，而非文件（默认文件或目录都忽略）。

在添加忽略文件夹后，如果在此文件夹创建文件，那么会被忽略，但是删除文件不会被忽略

```
#为注释
*.txt        #忽略所有 .txt结尾的文件,这样的话上传就不会被选中！
!lib.txt     #但lib.txt除外
/temp        #仅忽略项目根目录下的TODO文件,不包括其它目录temp
build/       #忽略build/目录下的所有文件
doc/*.txt    #会忽略 doc/notes.txt 但不包括 doc/server/arch.txt
```

# 4 练习

## 4.1 创建分支

```git
# 从暂存区提交到本地仓库
git commit - m "message"
```

```git
# 创建新分支，并切换到新分支
git branch newBranch			# 创建新分支
git checkout newBranck			# 切换到新分支

# 创建新分支，并切换到新分支
git checkout -b newBranch
```

## 4.2 合并分支
```git
# 创建新分支，并将两个分支分别提交，再新分支合并到主分支
git checkout -b newBranch		# 运用上一块的分支新建操作
git commit						# newBranch分支上，提交上去
git checkout master				# 切回主分支
git merge bugFix				# 合并分支到master

# 一般将dev分支要合并的master分支
# 那么就需要切换到master分支，在执行
# git merge dev
```

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git-gitmerge.jpg)

```git
# rebase合并分支，使得提交时间线更线性
git checkout -b bugFix
git commit					# bugFix分支上
git checkout master
git commit					# master分支上
git checkout bugFix			#在bugFix分支上执行操作将自己提交到另一个分支后面
git rebase master			# 将新分支bugFix提交到master后面，实际新旧分支是同时开发的(在master分支上rebase master							不起作用)
							# 如果bugFix在一系列结点的最顶端，只会移动最顶端的结点，如果在下面，就会移动bugFix上面的所有结点
							# git rebase master bugFix 无论在哪个分支上工作，都可以将bugFix移动到master后面
							# 参数接受hash
```

## 4.3 移动记录

```git
# HEAD 是一个对当前检出记录的符号引用 —— 也就是指向你正在其基础上进行工作的提交记录。

# HEAD 总是指向当前分支上最近一次提交记录。大多数修改提交树的 Git 命令都是从改变 HEAD 的指向开始的。

# HEAD 通常情况下是指向分支名的（如 bugFix）。在你提交时，改变了 bugFix 的状态，这一变化通过 HEAD 变得可见。

git checkout c4				# c4是该节点的哈希值
```

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git-HEAD.jpg)

```git
# 通过指定提交记录哈希值的方式在 Git 中移动不太方便。在实际应用时，并没有像本程序中这么漂亮的可视化提交树供你参考，所以你就不得不用 git log 来查查看提交记录的哈希值。

# 并且哈希值在真实的 Git 世界中也会更长（译者注：基于 SHA-1，共 40 位）。例如前一关的介绍中的提交记录的哈希值可能是 fed2da64c0efc5293610bdd892f82a58e8cbc5d8。舌头都快打结了吧...

# 比较令人欣慰的是，Git 对哈希的处理很智能。你只需要提供能够唯一标识提交记录的前几个字符即可。因此我可以仅输入fed2 而不是上面的一长串字符。

# 使用相对引用的话，你就可以从一个易于记忆的地方（比如 bugFix 分支或 HEAD）开始计算。

# 相对引用非常给力，这里我介绍两个简单的用法：

# 使用 ^ 向上移动 1 个提交记录
# 使用 ~<num> 向上移动多个提交记录，如 ~3

git checkout bugFix^				# bugFix在这里是节点分支的名称，不是哈希值
```

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git-checkout.jpg)

```git
git checkout c6						# HEAD 移动到c6
git branch -f master HEAD			# 将master移动到c6
git branch -f bugFix HEAD~4			# bugFix向前移动4个节点
git checkout c1
```

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git-checkout~num.jpg)

## 4.4 撤销变更

```git
# git reset 通过把分支记录回退几个提交记录来实现撤销改动。你可以将这想象成“改写历史”。git reset 向上移动分支，原来指向的提交记录就跟从来没有提交过一样。

# 虽然在你的本地分支中使用 git reset 很方便，但是这种“改写历史”的方法对大家一起使用的远程分支是无效的哦！

# 为了撤销更改并分享给别人，我们需要使用 git revert为新提交记录 C2' 引入了更改 —— 这些更改刚好是用来撤销 C2 这个提交的。也就是说 C2' 的状态与 C1 是相同的。

git reset HEAD^						# 这个命令需要确定移动到HEAD的前几个位置，只写HEAD不行,回到前一次记录
git checkout pushed
git revert HEAD						# 撤销到HEAD的上一个节点
```

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git-reset.png)

## 4.5 整理提交树

```git
# 如果你想将一些提交复制到当前所在的位置（HEAD）下面的话， Cherry-pick 是最直接的方式了。
git cherry-pick bugFix
git cherry-pick side^
git cherry-pick another

# 还可以一步操作
git cherry-pick bugFix side^ another			# 将提交记录移动到当前(HEAD)记录的后面
												# 将bugFix side^ another移动到了master后面
```

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git-cherrypick.png)

```git
# 交互式 rebase 指的是使用带参数 --interactive 的 rebase 命令, 简写为 -i

# 如果你在命令后增加了这个选项, Git 会打开一个 UI 界面并列出将要被复制到目标分支的备选提交记录，它还会显示每个提交记录的哈希值和提交说明，提交说明有助于你理解这个提交进行了哪些更改。

# 当 rebase UI界面打开时, 你能做3件事:

#    调整提交记录的顺序（通过鼠标拖放来完成）
#    删除你不想要的提交（通过切换 pick 的状态来完成，关闭就意味着你不想要这个提交记录）
#    合并提交。 遗憾的是由于某种逻辑的原因，我们的课程不支持此功能，因此我不会详细介绍这个操作。简而言之，它允许你把多个提交记录合并成一个。

git rebase -i HEAD~4					# 复制当前结点在内的前四个结点，然后通过UI操作删除C2，移动排列
```

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git-交互rebase.png)

```git
# 来看一个在开发中经常会遇到的情况：我正在解决某个特别棘手的 Bug，为了便于调试而在代码中添加了一些调试命令并向控制台打印了一些信息。

# 这些调试和打印语句都在它们各自的提交记录里。最后我终于找到了造成这个 Bug 的根本原因，解决掉以后觉得沾沾自喜！

# 最后就差把 bugFix 分支里的工作合并回 master 分支了。你可以选择通过 fast-forward 快速合并到 master 分支上，但这样的话 master 分支就会包含我这些调试语句了。你肯定不想这样，应该还有更好的方式……

git rebase -i HEAD~3						# 只复制最后一个记录,此时在bugFix上工作
											# 此时用git rebase master 或者 git merge master都提示分支最新
git checkout master							# 
git merge bugFix							# 将bugFix合并到主分支   或者git rebase bugFix同一个效果


# 第二种解法
git checkout master 						# 工作到master上
git cherry-pick bugFix						# 将bugFix复制到master上
```

![localstack](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git-localstack.jpg)

```git
# 你之前在 newImage 分支上进行了一次提交，然后又基于它创建了 caption 分支，然后又提交了一次。

# 此时你想对的某个以前的提交记录进行一些小小的调整。比如设计师想修改一下 newImage 中图片的分辨率，尽管那个提交记录并不是最新的了。

git rebase -i HEAD~2 							# 排列两个结点重新提交
git commit --amend 								# 在caption上，对c2'结点进行了修改
git rebase -i HEAD~2 							# 排列两个结点重新提交
git branch -f master							# 将master分支移动到caption处

# 使用cherry-pick也可以解决
```

![commit amend](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git-commitamend.png)

## 4.6 GIT TAG

```git
# 给某个提交记录加上永久的标签。比如软件发布新的大版本，或者是修正一些重要的 Bug 或是增加了某些新特性，有没有比分支更好的可以永远指向这些提交的方法呢？
# 我们将这个标签命名为 v1，并且明确地让它指向提交记录 C1，如果你不指定提交记录，Git 会用 HEAD 所指向的位置。

git tag v1 c1					# 给c1结点加上v1标签
git tag v1						# 给HEAD加上v1标签
```

```git
# 由于标签在代码库中起着“锚点”的作用，Git 还为此专门设计了一个命令用来描述离你最近的锚点（也就是标签），它就是 git describe！

# git describe 的语法是：

git describe <ref>

# <ref> 可以是任何能被 Git 识别成提交记录的引用，如果你没有指定的话，Git 会以你目前所检出的位置（HEAD）。

# 它输出的结果是这样的：

<tag>_<numCommits>_g<hash>

# tag 表示的是离 ref 最近的标签， numCommits 是表示这个 ref 与 tag 相差有多少个提交记录， hash 表示的是你所给定的 ref 所表示的提交记录哈希值的前几位。

# 当 ref 提交记录上有某个标签时，则只输出标签名称

git describe master 会输出：

v1_2_gC2					# master结点距离v1结点有两个记录，正指向c2结点
```

## 4.7 远程操作

```git
# clone 远程仓库
git clone url
```

```git
# 你可能想问这些远程分支的前面的 o/ 是什么意思呢？好吧, 远程分支有一个命名规范 —— 它们的格式是:

<remote name>/<branch name>
# 因此，如果你看到一个名为 o/master 的分支，那么这个分支就叫 master，远程仓库的名称就是 o。

# 大多数的开发人员会将它们主要的远程仓库命名为 origin，并不是 o。这是因为当你用 git clone 某个仓库时，Git 已经帮你把远程仓库的名称设置为 origin 了
# o/master只会在远程仓库分支提交后更新
```

```git
# git fecth 会将本地缺失的记录从远程仓库下载下来，但不会同步代码

# git fetch 不会做的事

# git fetch 并不会改变你本地仓库的状态。它不会更新你的 master 分支，也不会修改你磁盘上的文件。

# 理解这一点很重要，因为许多开发人员误以为执行了 git fetch 以后，他们本地仓库就与远程仓库同步了。它可能已经将进行这一操作所需的所有数据都下载了下来，但是并没有修改你本地的文件。我们在后面的课程中将会讲解能完成该操作的命令 :D

# 所以, 你可以将 git fetch 的理解为单纯的下载操作。
```

```git
# git pull

# 使用git pull命令将远程仓库(右)边的新分支，拉到本地，续在o/master之后，同时o/master会向前移动
# 然后将本地master和o/master合并

# 效果等同于
git fecth 
git meger o/master


# 创建新分支拉取远程，再将新的临时分支合并到主线
git fetch origin(别名) 分支名
git branch -a	# 查看远程分支
git checkout -b master(本地分支名) origin/master(远程分支名)	# 创建本地分支并关联远程分支
git pull origin master(远程分支名)
```

![pull](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git-pull.jpg)

# 5 重新学习

[GeekHour视频](https://www.bilibili.com/video/BV1HM411377j?p=3&vd_source=143acef25d8161c363937654b7802b50)

## 5.1 区域区分

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/%E5%B7%A5%E4%BD%9C%E5%8C%BA%E5%9F%9F.png)

- 工作区：.git
- 暂存区：.git/index
- 本地仓库：.git/objects

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/%E6%96%87%E4%BB%B6%E7%8A%B6%E6%80%81.png)



- 未跟踪：新建文件
- 已修改：修改了本地仓库的文件
- 未修改：本地仓库的文件
- 已暂存：add文件到暂存区

## 5.2 添加和提交

分别添加两个文件file1.txt和file2.txt，并将file1.txt添加到暂存区

`git add file1.txt`

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/%E6%96%87%E4%BB%B6%E7%8A%B6%E6%80%811.png)

file1经过add命令添加到暂存区，显示为绿色

file2还没有进行任何操作，为未跟踪，显示为红色

可以看到git给了提示，两个状态可以如下转化

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/%E6%96%87%E4%BB%B6%E7%8A%B6%E6%80%812.png)

最后使用`git commit -m`命令将暂存区的文件提交

## 5.3 回退版本

`git reset`

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git%20reset.png)

- --soft：撤销commit，不会撤销add
- --mixed：撤销commit和add，文件恢复到新建未跟踪或仓库文件已经修改的还没有add的状态
- --hard：撤销commit和add，还会将从未跟踪状态提交的文件删除以及将有过修改的仓库文件删除修改

## 5.4 比较差异

`git diff`

- 查看文件在工作区、暂存区、本地仓库之间的差异
- 查看两个版本之间的差异
- 查看两个分支之间的差异

新建三个文件，内容分别为111、222、333

**工作区和本地仓库之间的差异**

修改文件file2和file3，执行`git diff`命令

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git%20diff1.png)

可以看到file2文件中将内容“222”更改为“22”

在file3文件中新增了一行“3333”

**暂存区和本地仓库之间的差异**

延续上面的例子，将file3文件经过add命令添加到暂存区

执行`git diff --cahced`命令得到如下结果

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git%20diff2.png)

可以看到只有file3文件输出不同的信息，此时file2的文件修改还在工作区里

**比较两个版本之间的差异**

`git diff id1 id2`，跟随提交id

**比较两个分支之间的差异**

`git diff branch1 branch2`，跟随分支名称

## 5.5 删除文件

`git rm`删除文件

**rm直接删除**

linux下执行rm或者windows下扔到回收站，操作后显示

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git%20rm1.png)

该步操作后还需要将工作区文件添加到暂存区再提交。等同于文件做了修改还未提交的状态

**git rm删除文件**

`git rm`删除文件，git为我们自动执行了add

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/git%20rm2.png)

可以看到file2文件已经在暂存区中，状态为删除。

`git ls-files`命令查看暂存区文件时也没有该文件。

## 5.6 忽略文件

**需要在文件未跟踪时就添加到忽略文件**

```yml
# 忽略文件
filename.txt # 单一忽略
*.log # 通配符

# 忽略文件夹
floder/ # 忽略任意文件夹下的folder文件夹
floder/**/*.txt # **可以匹配任意层级的文件夹
/floder/ # 忽略当前目录下的floder文件夹
```

## 5.7 分支操作

### 5.7.1 合并分支

合并分支：`git merge dev`，将dev分支合并到当前分支

命令后跟随的是支线分支，分支合并后不会被删除

`git log --graph`可以看到合并流

### 5.7.2 冲突解决

自动合并失败，需要手动解决冲突

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/%E5%90%88%E5%B9%B6%E5%86%B2%E7%AA%811.png)

**git merge --abort**中止合并

任意编辑器打开冲突文件

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/%E5%90%88%E5%B9%B6%E5%86%B2%E7%AA%812.png)

解决冲突后再进行一次提交，查看提交树

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/%E5%86%B2%E7%AA%81%E6%8F%90%E4%BA%A4%E6%A0%91.png)

看到从dev和master各进行了一次提交更改，合并时时产生了冲突

### 5.7.3 rebase

分支树如下

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/rebase1.png)

rebase可以在任意分支上进行操作

如果在dev分支上进行操作,dev分支会接到master后面

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/rebase2.png)

如果在master上提交，master会接到dev分支后面

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/git/rebase3.png)

rebase操作会比较两个分支的HEAD的公共父节点，然后将之间的节点移植