<meta charset='UTF-8'/>

# 安装JDK

[Java网址](https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html)  

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/javaenviroment/1.png"/>
<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/javaenviroment/2.png"/>
<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/javaenviroment/3.png"/>
<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/javaenviroment/4.png"/>  

  1. 打开环境变量，配置JAVA_HOME  

  变量值为<font color=red>__JAVA_HOME__</font>，路径为jdk路径，例如 <font color=red>__C:\Program Files\Java\jdk1.8.0\_251__</font>

  2. 配置CLASSPATH

  变量名为<font color=red>__CLASSPATH__</font>，变量值为<font color=red>__.;%JAVA\_HOME%\lib;%JAVA_HOME%\lib\tools.jar__</font>

  3. 配置PATH

  在path中添加<font color=red>__%JAVA\_HOME%\bin;%JAVA_HOME%\jre\bin;__</font>  

  4. 验证安装

  java -version显示版本号

  

# 安装maven

[maven网址](http://maven.apache.org/download.cgi)


  1. 解压

  将下载下来的maven zip文件包进行解压，解压的目录随意，但为了管理方便，建议你解压到常用的软件安装目录，假设你解压到 D:\apache\maven\apache-maven-3.3.9目录下

  2. 配置环境变里

  添加 <font color=red>__M2\_HOME__</font> 和 <font color=red>__MAVEN_HOME__</font> 环境变量到 Windows 环境变量，并将其指向你的 Maven 文件夹（比如你解压到D:\apache\maven\apache-maven-3.3.9）。

  3. 配置PATH

  更新系统变量path的值：编辑环境变量Path，追加<font color=red>__%MAVEN_HOME%\bin\;__</font>

  4. 验证安装
	

  mvn -version  



5. 修改配置文件settings.xml

   - 本地仓库修改

   ```xml
    <!-- localRepository
      | The path to the local repository maven will use to store artifacts.
      |
      | Default: ${user.home}/.m2/repository
     <localRepository>/path/to/local/repo</localRepository>
     -->
   	<localRepository>D:\tools\repository</localRepository>
   ```

   - 修改默认jdk版本

   ```xml
   <profile>     
       <id>JDK-1.8</id>       
       <activation>       
           <activeByDefault>true</activeByDefault>       
           <jdk>1.8</jdk>       
       </activation>       
       <properties>       
           <maven.compiler.source>1.8</maven.compiler.source>       
           <maven.compiler.target>1.8</maven.compiler.target>       
           <maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>       
       </properties>       
   </profile>
   ```

   - 添加国内镜像源

   ```xml
   <!-- 阿里云仓库 -->
   <mirror>
       <id>alimaven</id>
       <mirrorOf>central</mirrorOf>
       <name>aliyun maven</name>
       <url>http://maven.aliyun.com/nexus/content/repositories/central/</url>
   </mirror>
   
   <!-- 中央仓库1 -->
   <mirror>
       <id>repo1</id>
       <mirrorOf>central</mirrorOf>
       <name>Human Readable Name for this Mirror.</name>
       <url>http://repo1.maven.org/maven2/</url>
   </mirror>
   
   <!-- 中央仓库2 -->
   <mirror>
       <id>repo2</id>
       <mirrorOf>central</mirrorOf>
       <name>Human Readable Name for this Mirror.</name>
       <url>http://repo2.maven.org/maven2/</url>
   </mirror>
   ```

   

  

 # 配置vscode

  安装插件
  1. Language Support for Java(TM) by Red Hat Java Java运行支持
  2. Debugger for Java  Java调试，不调试的话可以不装
  3. Java Test Runner 运行单元测试需要的插件
  4. Maven for Java Maven支持插件

  安装完插件后，首选项->设置->搜索java.home，在settings.json中添加
  ```json
      "java.configuration.maven.userSettings": "D:\\Program Files (x86)\\apache-tomcat-9.0.39",
      "java.home": "C:\\PROGRA~1\\Java\\jdk1.8.0_251", 
      "editor.suggestSelection": "first",
      "vsintellicode.modify.editor.suggestSelection": "automaticallyOverrodeDefaultValue",
      "java.configuration.checkProjectSettingsExclusions": false,
      "editor.fontSize": 18,
      "files.autoGuessEncoding": true,
      "java.semanticHighlighting.enabled": true,
      "java.requirements.JDK11Warning": false,
      "explorer.confirmDelete": false,
  ```

  在工程所在目录添加.vscode文件夹，其中包含launch.json，这个可以自动生成
  ```json
{
    // 使用 IntelliSense 了解相关属性。 
    // 悬停以查看现有属性的描述。
    // 欲了解更多信息，请访问: https://go.microsoft.com/fwlink/?linkid=830387
    "version": "0.2.0",
    "configurations": [
        {
            "type": "java",
            "name": "Debug (Launch) - Current File",
            "request": "launch",
            "mainClass": "${file}"
        },
        {
            "type": "java",
            "name": "Debug (Launch)-Start<browser>",//breowser文件夹下的Start类
            "request": "launch",
            "mainClass": "edu.xd.browser.Start",//包名
            "projectName": "browser"//工程名
        }
    ]
}
  ```


# vscode



 ## vscode中建立运行maven项目

 ## vscode中建立运行web项目



# eclipse



 ## eclipse中建立运行maven项目

### eclipse配置maven

  D:\Program Files\apache-maven-3.6.3\为maven解压目录
  1. 创建一个maven包的仓库目录

  例：D:\Program Files\apache-maven-3.6.3\res

  2. 更改仓库地址

  在D:\Program Files\apache-maven-3.6.3\conf的settings.xml中添加
  ```xml
  <localRepository>D:\Program Files\apache-maven-3.6.3\res</localRepository>
  ```

  3. eclipse中设置maven

  Window->Preferences->Maven->Installations->Add->Directory选择刚解压的Maven的路径，点击Finish，然后将它选为默认。  

  Window->Preferences->Maven->User Settings->Global Settings选择到上一步修改的那个settings.xml文件，点击OK即可  

  Window->Preferences->Maven->User Settings->User Settings选择到上一步修改的那个settings.xml文件，点击OK即可  

### 创建maven项目

  1. 建立maven工程

  File->New->Maven Project,点击Next,然后在中间带有滚动条的面板中选择quickstart,然后Next  

  groupId：公司域名反写+项目名，Artifact Id：项目名.点击Finsh即可。

  2. ctrl+F11可以运行程序看效果


### 运行maven项目

  1. 配置pom.xml

  把需要的依赖添加进pom.xml的\<dependencies\>\</dependencies\>标签中
  ```xml
  <dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>3.141.59</version>
  </dependency>
  ```

  2. 编译maven项目

  右键pom.xml，Run As->Maven Build然后Goals中填写maven命令，例，compile,run等  

  compile时出现No compiler is provided in this environment. Perhaps you are running on a JRE rather than a JDK?
  在compile前配置正确的jdk环境 Window->Preferences->Java->Installation jre->Add正确的jdk路径而不是jre路径
  出现Build Success则是成功  

 ## eclipse中建立运行web项目





# IDEA

配置maven

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/javaenviroment/5.png)

1：此处修改为自己解压的Maven目录

2：勾选**Override**，修改为自己目录下的**settings.xml**目录

3：修改为自己的本地仓库地址，一般会自动识别。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/javaenviroment/6.png)

勾选，自动导入包

## IDEA创建普通java项目并运行

> 1. 打开IDEA,File->New->New project
> 2. 不用从模版打开，直接两次next，输入项目名称，例如HelloWorld
> 3. run，默认设置，打印出HelloWorld





## IDEA创建maven项目并运行

> 1. 打开IDEA,File->New->New project

> 2. 在左侧选择maven，右侧选择qiuckstart(选择自己需要的)

> 3. 填写groupId,ArtifactId,点击完成，等待项目创建 
> 4. run->edit configure

手动添加jar包

> 1.Project Structure -> Libraries
>
> 2.在其中添加jar包所在的路径，系统会自己找到，就可以导入包了

## IDEA创建web项目

> 1. 在pom.xml中添加tomcat插件信息
>
>    ```xml
>    <project>
>      <build>
>        <plugins>
>          <plugin>
>            <groupId>org.apache.tomcat.maven</groupId>
>            <artifactId>tomcat7-maven-plugin</artifactId>
>            <version>2.2</version>
>            <configuration>
>              <server>myserver</server>
>            </configuration>
>          </plugin>
>        </plugins>
>       </build>
>    </project>
>    ```

> 2. eidt configuration 。配置maven，名称为xxx，路径是项目路径，命令填写tomcat7:run.然后运行，在浏览器中打开



另一种方案，个人认为简单方便

> 1. 新建普通java项目
>2. 右键项目名称->Add Frameworks Support->勾选web application->ok
> 
>3. 在WEB-INF文件夹下创建classes文件夹
> 4. File->Project Structure->Modules->Paths在这里改变输出路径，将两个路径都改到刚才创建的classes下
>5. 配置运行环境，Add Configuration->tomcat local->Deploment在这里给Deploy at the server startup中添加war_exploede,在把下面的路径改为一个斜杠
> 6. 在xml中配置好就可以运行了

# Tomcat

    1. 打开环境变量，配置CATALINA_HOME  

  变量值为<font color=red>__CATALINA_HOME__</font>，路径为tomcat路径，例如 <font color=red>__D:\Program\apache-tomcat-9.0.39__</font>

    2. 配置CATALINA_BASE

  变量名为CATALINA_HOME，变量值为<font color=red>__D:\Program\apache-tomcat-9.0.39__</font>

    3. 配置PATH

  在path中添加<font color=red>__%CATALINA_HOME%\bin;%CATALINA_HOME%\lib;__</font>  

4. 解决乱码

  D:\Program\apache-tomcat-9.0.39\conf中的logging.properties文件中

```
java.util.logging.ConsoleHandler.encoding = UTF—8改为GBK
```



