
<meta charset='UTF-8'/>  

[TOC]




# 1 selenium库

[参考链接](https://www.selenium.dev/documentation/en/webdriver/browser_manipulation/)

# 2 安装与启动

通过maven安装依赖，pom.xml中插入下面的代码
	
```xml
<dependency>
	<groupId>org.seleniumhq.selenium</groupId>
	<artifactId>selenium-java</artifactId>
	<version>3.X</version>
</dependency>
```
如果只在指定浏览器设置，例如火狐，则如下
```xml
<dependency>
  <groupId>org.seleniumhq.selenium</groupId>
  <artifactId>selenium-firefox-driver</artifactId>
  <version>3.X</version>
</dependency>
```

安装浏览器驱动

[chrome-webdriver下载地址-国内镜像](http://npm.taobao.org/mirrors/chromedriver/)

下载时驱动版本需要和浏览器版本对应

下载完成放到chrome安装路径下`C:\Program Files\Google\Chrome\Application`

另：

将可执行文件添加到 `PATH` 中

大多数驱动程序需要 Selenium 额外的可执行文件才能与浏览器通信。您可以在启动 WebDriver 之前手动指定可执行文件的存放位置，但这会使测试的可移植性降低，因为可执行文件必须位于每台 计算机上的同一位置，或包含在测试代码存储库中。

通过将包含 WebDriver 二进制文件的文件夹添加到系统 path 环境变量中，Selenium 将能够找到其他二进制文件，而无需您的测试代码来定位驱动程序的确切位置。

- 创建一个目录来放置可执行文件，例如 *C:\WebDriver\bin* 或 */opt/WebDriver/bin*
- 将目录添加到您的 path 中：
  - 在 Windows 上 - 以管理员身份打开命令提示符，然后运行以下命令将目录永久添加到计算机上所有用户的路径中：

```shell
setx PATH "%PATH%;C:\WebDriver\bin"
```



启动浏览器并转到界面

```java
public class BrowserController {

    /**
     * 启动浏览器
     */
    public void startup(){

        //设置驱动文件路径,第二个参数是驱动文件的路径
        System.setProperty("webdriver.chrome.driver", "d:\\Program\\WebDriver\\bin\\chromedriver.exe");

        //实例化
        WebDriver webDriver = new ChromeDriver();

        //转到页面
        webDriver.get("https://www.selenium.dev/zh-cn/documentation/webdriver/browser_manipulation/");

    }
}
```



# 3 操作浏览器



## 3.1 浏览器导航

### 3.1.1 打开网站

```java
// 简便的方法
driver.get("https://selenium.dev");

// 更长的方法
driver.navigate().to("https://selenium.dev");
```

### 3.1.2 获取当前url

```java
driver.getCurrentUrl();
```

### 3.1.3 后退与前进

```java
driver.navigate().back();
driver.navigate().forward();
```

### 3.1.4 刷新

```java
driver.navigate().refresh();
```

### 3.1.5 获取标题

```java
driver.getTitle();
```

## 3.2 窗口和标签页

### 3.2.1 获取窗口句柄

```java
driver.getWindowHandle();
```

### 3.2.2 切换窗口或标签页

单击在 <a href=“[https://seleniumhq.github.io](https://seleniumhq.github.io/)"target="_blank”>新窗口 中打开链接， 则屏幕会聚焦在新窗口或新标签页上，但 WebDriver 不知道操作系统认为哪个窗口是活动的。 要使用新窗口，您需要切换到它。 如果只有两个选项卡或窗口被打开，并且你知道从哪个窗口开始， 则你可以遍历 WebDriver， 通过排除法可以看到两个窗口或选项卡，然后切换到你需要的窗口或选项卡。

```java
// 存储原始窗口的 ID
String originalWindow = driver.getWindowHandle();

// 检查一下，我们还没有打开其他的窗口
assert driver.getWindowHandles().size() == 1;

// 点击在新窗口中打开的链接
driver.findElement(By.linkText("new window")).click();

// 等待新窗口或标签页
wait.until(numberOfWindowsToBe(2));

// 循环执行，直到找到一个新的窗口句柄
for (String windowHandle : driver.getWindowHandles()) {if(!originalWindow.contentEquals(windowHandle)) {driver.switchTo().window(windowHandle);
break;
}
}

// 等待新标签完成加载内容
wait.until(titleIs("Selenium documentation"));
```

不过，Selenium 4 提供了一个新的 api [NewWindow ](https://selenium.dev/documentation/zh-cn/webdriver/browser_manipulation/#创建新窗口(或)新标签页并且切换)， 它创建一个新选项卡 (或) 新窗口并自动切换到它。

```java
// 打开新标签页并切换到新标签页
driver.switchTo().newWindow(WindowType.TAB);

// 打开一个新窗口并切换到新窗口
driver.switchTo().newWindow(WindowType.WINDOW);
```

### 3.2.3 关闭窗口或者标签页

```java
//关闭标签页或窗口
driver.close();

//切回到之前的标签页或窗口
//originalWindow为前面获取的旧标签的句柄
driver.switchTo().window(originalWindow);
```

## 3.3 退出浏览器

完成浏览器会话之后应该使用quit而不是close

```java
driver.quit();
```

- 退出将会
  - 关闭所有与 WebDriver 会话相关的窗口和选项卡
  - 结束浏览器进程
  - 结束后台驱动进程
  - 通知 Selenium Grid 浏览器不再使用，以便可以由另一个会话使用它(如果您正在使用 Selenium Grid)

调用 quit() 失败将留下额外的后台进程和端口运行在机器上，这可能在以后导致一些问题。

## 3.4 Frame和IFrame

## 3.5 窗口管理

### 3.5.1 获取窗口大小

以像素为单位

```java
// 分别获取每个尺寸
int width = driver.manage().window().getSize().getWidth();
int height = driver.manage().window().getSize().getHeight();

// 或者存储尺寸并在以后查询它们
Dimension size = driver.manage().window().getSize();
int width1 = size.getWidth();
int height1 = size.getHeight();
```

### 3.5.2 设置窗口大小

```java
driver.manage().window().setSize(new Dimension(1024, 768));
```

### 3.5.3 得到窗口位置

```java
// 分别获取每个尺寸
int x = driver.manage().window().getPosition().getX();
int y = driver.manage().window().getPosition().getY();

// 或者存储尺寸并在以后查询它们
Point position = driver.manage().window().getPosition();
int x1 = position.getX();
int y1 = position.getY();
```

### 3.5.4 设置窗口位置

```java
// 将窗口移动到主显示器的左上角
driver.manage().window().setPosition(new Point(0, 0));
```

### 3.5.5 最大化窗口

```java
driver.manage().window().maximize();
```

### 3.5.6 最小化窗口

最小化当前浏览上下文的窗口. 这种命令的精准行为将作用于各个特定的窗口管理器.

最小化窗口通常将窗口隐藏在系统托盘中.

注意: 此功能适用于Selenium 4以及更高版本.

```java
driver.manage().window().minimize();
```

### 3.5.7 全屏窗口

填满屏幕，和最大化略有不同

```java
driver.manage().window().fullscreen();
```

### 3.5.8 屏幕截图

用于捕获当前浏览上下文的屏幕截图. WebDriver端点 [屏幕截图](https://www.w3.org/TR/webdriver/#dfn-take-screenshot) 返回以Base64格式编码的屏幕截图.

```java
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.*;
import org.openqa.selenium.*;

public class SeleniumTakeScreenshot {
    public static void main(String args[]) throws IOException {
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.example.com");
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./image.png"));
        driver.quit();
    }
}
```

### 3.5.9 元素屏幕截图

用于捕获当前浏览上下文的元素的屏幕截图. WebDriver端点 屏幕截图 返回以Base64格式编码的屏幕截图.

```java
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.IOException;

public class SeleniumelementTakeScreenshot {
    public static void main(String args[]) throws IOException {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.example.com");
        WebElement element = driver.findElement(By.cssSelector("h1"));
        File scrFile = element.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./image.png"));
        driver.quit();
    }
}
```

## 3.6 执行脚本

```java
//Creating the JavascriptExecutor interface object by Type casting
JavascriptExecutor js = (JavascriptExecutor)driver;
//Button Element
WebElement button =driver.findElement(By.name("btnLogin"));
//Executing JavaScript to click on element
js.executeScript("arguments[0].click();", button);
//Get return value from script
String text = (String) js.executeScript("return arguments[0].innerText", button);
//Executing JavaScript directly
js.executeScript("console.log('hello world')");
```

### 3.6.1 滚动页面

将页面向下滚动指定像素

```java
((JavascriptExecutor) driver).executeScript("window.scrollBy(0, 250)");
```





## 3.7 打印页面

打印当前浏览器内的页面

*注意: 此功能需要无头模式下的Chromium浏览器*

```java
import org.openqa.selenium.print.PrintOptions;

driver.get("https://www.selenium.dev");
printer = (PrintsPage) driver;

PrintOptions printOptions = new PrintOptions();
printOptions.setPageRanges("1-2");

Pdf pdf = printer.print(printOptions);
String content = pdf.getContent();
```

# 4 定位元素

## 4.1 定位元素

```java
WebElement cheese = driver.findElement(By.id("cheese"));
```

如示例所示，在 WebDriver 中定位元素是在 `WebDriver` 实例对象上完成的。 `findElement(By)` 方法返回另一个基本对象类型 `WebElement`。

- `WebDriver` 代表浏览器
- `WebElement` 表示特定的 DOM 节点（控件，例如链接或输入栏等）

一旦你已经找到一个元素的引用，你可以通过对该对象实例使用相同的调用来缩小搜索范围：

```java
WebElement cheese = driver.findElement(By.id("cheese"));
WebElement cheddar = cheese.findElement(By.id("cheddar"));
```

为了稍微提高性能，我们应该尝试使用一个更具体的定位器：WebDriver 支持通过 CSS 定位器查找元素，我们可以将之前的两个定位器合并到一个搜索里面:

```java
driver.findElement(By.cssSelector("#cheese #cheddar"));
```

## 4.2 定位多个元素

我们正在处理的文本中可能会有一个我们最喜欢的奶酪的订单列表：

```html
<ol id=cheese>
 <li id=cheddar>…
 <li id=brie>…
 <li id=rochefort>…
 <li id=camembert>…
</ol>
```

因为有更多的奶酪无疑是更好的，但是单独检索每一个项目是很麻烦的，检索奶酪的一个更好的方式是使用复数版本 `findElements(By)` 。此方法返回 web 元素的集合。如果只找到一个元素，它仍然返回(一个元素的)集合。如果没有元素被定位器匹配到，它将返回一个空列表。

```java
List<WebElement> muchoCheese = driver.findElements(By.cssSelector("#cheese li"));
```

## 4.3 相对定位



# 5 等待



## 5.1 显示等待

*显示等待* 是Selenium客户可以使用的命令式过程语言。它们允许您的代码暂停程序执行，或冻结线程，直到满足通过的 *条件* 。这个条件会以一定的频率一直被调用，直到等待超时。这意味着只要条件返回一个假值，它就会一直尝试和等待

由于显式等待允许您等待条件的发生，所以它们非常适合在浏览器及其DOM和WebDriver脚本之间同步状态。

为了弥补我们之前的错误指令集，我们可以使用等待来让 *findElement* 调用等待直到脚本中动态添加的元素被添加到DOM中:

```java
WebDriver driver = new ChromeDriver();
driver.get("https://google.com/ncr");
driver.findElement(By.name("q")).sendKeys("cheese" + Keys.ENTER);
// Initialize and wait till element(link) became clickable - timeout in 10 seconds
WebElement firstResult = new WebDriverWait(driver, Duration.ofSeconds(10))
        .until(ExpectedConditions.elementToBeClickable(By.xpath("//a/h3")));
// Print the first result
System.out.println(firstResult.getText());
```

可以重构的更简单

```java
WebElement foo = new WebDriverWait(driver, Duration.ofSeconds(3))
          .until(driver -> driver.findElement(By.name("q")));
```



## 5.2 隐式等待

还有第二种区别于[显示等待](https://www.selenium.dev/zh-cn/documentation/webdriver/waits/#explicit-wait) 类型的 *隐式等待* 。通过隐式等待，WebDriver在试图查找_任何_元素时在一定时间内轮询DOM。当网页上的某些元素不是立即可用并且需要一些时间来加载时是很有用的。

默认情况下隐式等待元素出现是禁用的，它需要在单个会话的基础上手动启用。将[显式等待](https://www.selenium.dev/zh-cn/documentation/webdriver/waits/#explicit-wait)和隐式等待混合在一起会导致意想不到的结果，就是说即使元素可用或条件为真也要等待睡眠的最长时间。

*警告:* 不要混合使用隐式和显式等待。这样做会导致不可预测的等待时间。例如，将隐式等待设置为10秒，将显式等待设置为15秒，可能会导致在20秒后发生超时。

隐式等待是告诉WebDriver如果在查找一个或多个不是立即可用的元素时轮询DOM一段时间。默认设置为0，表示禁用。一旦设置好，隐式等待就被设置为会话的生命周期。

```java
WebDriver driver = new FirefoxDriver();
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
driver.get("http://somedomain/url_that_delays_loading");
WebElement myDynamicElement = driver.findElement(By.id("myDynamicElement"));
```



## 5.3 流畅等待

流畅等待实例定义了等待条件的最大时间量，以及检查条件的频率。

用户可以配置等待来忽略等待时出现的特定类型的异常，例如在页面上搜索元素时出现的`NoSuchElementException`。

```java
// Waiting 30 seconds for an element to be present on the page, checking
// for its presence once every 5 seconds.
Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
  .withTimeout(Duration.ofSeconds(30))
  .pollingEvery(Duration.ofSeconds(5))
  .ignoring(NoSuchElementException.class);

WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
  public WebElement apply(WebDriver driver) {
    return driver.findElement(By.id("foo"));
  }
});
```

# 6 javascript警告框、提示框和确认框



## 6.1 Alerts警告

```java
//Click the link to activate the alert
driver.findElement(By.linkText("See an example alert")).click();

//Wait for the alert to be displayed and store it in a variable
Alert alert = wait.until(ExpectedConditions.alertIsPresent());

//Store the alert text in a variable
String text = alert.getText();

//Press the OK button
alert.accept();
```



## 6.2 Confirm

```java
//Click the link to activate the alert
driver.findElement(By.linkText("See a sample confirm")).click();

//Wait for the alert to be displayed
wait.until(ExpectedConditions.alertIsPresent());

//Store the alert in a variable
Alert alert = driver.switchTo().alert();

//Store the alert in a variable for reuse
String text = alert.getText();

//Press the Cancel button
alert.dismiss();
```



## 6.3 Prompt

```java
//Click the link to activate the alert
driver.findElement(By.linkText("See a sample prompt")).click();

//Wait for the alert to be displayed and store it in a variable
Alert alert = wait.until(ExpectedConditions.alertIsPresent());

//Type your message
alert.sendKeys("Selenium");

//Press the OK button
alert.accept();
```



# 7 Http代理



# 8 页面加载策略





# 9 网络元素

## 9.1 Find Element

```java
WebDriver driver = new FirefoxDriver();

driver.get("http://www.google.com");

// Get search box element from webElement 'q' using Find Element
WebElement searchBox = driver.findElement(By.name("q"));

searchBox.sendKeys("webdriver");
  
```



## 9.2 Find Elements

```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.util.List;

public class findElementsExample {
    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();
        try {
            driver.get("https://example.com");
            // Get all the elements available with tag name 'p'
            List<WebElement> elements = driver.findElements(By.tagName("p"));
            for (WebElement element : elements) {
                System.out.println("Paragraph text:" + element.getText());
            }
        } finally {
            driver.quit();
        }
    }
}
  
```

## 9.3 Find Element From Element

从父元素获得元素

```java
WebDriver driver = new FirefoxDriver();
driver.get("http://www.google.com");
WebElement searchForm = driver.findElement(By.tagName("form"));
WebElement searchBox = searchForm.findElement(By.name("q"));
searchBox.sendKeys("webdriver");
```



## 9.4 Find Elements From Element

```java
  import org.openqa.selenium.By;
  import org.openqa.selenium.WebDriver;
  import org.openqa.selenium.WebElement;
  import org.openqa.selenium.chrome.ChromeDriver;
  import java.util.List;

  public class findElementsFromElement {
      public static void main(String[] args) {
          WebDriver driver = new ChromeDriver();
          try {
              driver.get("https://example.com");

              // Get element with tag name 'div'
              WebElement element = driver.findElement(By.tagName("div"));

              // Get all the elements available with tag name 'p'
              List<WebElement> elements = element.findElements(By.tagName("p"));
              for (WebElement e : elements) {
                  System.out.println(e.getText());
              }
          } finally {
              driver.quit();
          }
      }
  }
  
```



## 9.5 Get Active Element

获得在当前上下文中具有焦点的DOM元素

```java
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

public class activeElementTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("http://www.google.com");
            driver.findElement(By.cssSelector("[name='q']")).sendKeys("webElement");

            // Get attribute of current active element
            String attr = driver.switchTo().activeElement().getAttribute("title");
            System.out.println(attr);
        } finally {
            driver.quit();
        }
    }
}
  
```



## 9.6 Is Element Enabled

查看页面元素是否启用，如果没有启动则返回false

```java
//navigates to url
driver.get("https://www.google.com/");

//returns true if element is enabled else returns false
boolean value = driver.findElement(By.name("btnK")).isEnabled();
```



## 9.7 Is Element Selected

此方法确定是否 *已选择* 引用的元素. 此方法广泛用于复选框, 单选按钮, 输入元素和选项元素.

返回一个布尔值, 如果在当前浏览上下文中 **已选择** 引用的元素, 则返回 **True**, 否则返回 **False**.

```java
//navigates to url
driver.get("https://the-internet.herokuapp.com/checkboxes");

//returns true if element is checked else returns false
boolean value = driver.findElement(By.cssSelector("input[type='checkbox']:first-of-type")).isSelected();

```



## 9.8 Get Element TagName

获取当前被引用元素的tagName

```java
//navigates to url
driver.get("https://www.example.com");

//returns TagName of the element
String value = driver.findElement(By.cssSelector("h1")).getTagName();

```

## 9.9 Get Element Rect

用于获取参考元素的尺寸和坐标.

提取的数据主体包含以下详细信息:

- 元素左上角的X轴位置
- 元素左上角的y轴位置
- 元素的高度
- 元素宽度

```java
// Navigate to url
driver.get("https://www.example.com");

// Returns height, width, x and y coordinates referenced element
Rectangle res =  driver.findElement(By.cssSelector("h1")).getRect();

// Rectangle class provides getX,getY, getWidth, getHeight methods
System.out.println(res.getX());
  
```

## 9.10 获取元素CSS值

获取当前浏览上下文中元素的特定计算样式属性的值.

```java
// Navigate to Url
driver.get("https://www.example.com");

// Retrieves the computed style property 'color' of linktext
String cssValue = driver.findElement(By.linkText("More information...")).getCssValue("color");

  
```

## 9.11 获取元素文本

获取特定元素渲染后的文本.

```java
// Navigate to url
driver.get("https://example.com");

// Retrieves the text of the element
String text = driver.findElement(By.cssSelector("h1")).getText();
  
```



# 10 Keyboard

Keyboard代表一个键盘事件

## 10.1 SendKey

```java
import org.openqa.selenium.By;  
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class HelloSelenium {
  public static void main(String[] args) {
    WebDriver driver = new FirefoxDriver();
    try {
      // Navigate to Url
      driver.get("https://google.com");

      // Enter text "q" and perform keyboard action "Enter"
      driver.findElement(By.name("q")).sendKeys("q" + Keys.ENTER);
    } finally {
      driver.quit();
    }
  }
}
  
```



## 10.2 KeyDown

用于模拟辅助按键的动作

```java
WebDriver driver = new ChromeDriver();
try {
  // Navigate to Url
  driver.get("https://google.com");

  // Enter "webdriver" text and perform "ENTER" keyboard action
  driver.findElement(By.name("q")).sendKeys("webdriver" + Keys.ENTER);

  Actions actionProvider = new Actions(driver);
  Action keydown = actionProvider.keyDown(Keys.CONTROL).sendKeys("a").build();
  keydown.perform();
} finally {
  driver.quit();
}
  
```



## 10.3 KeyUp

模拟抬起或释放操作



## 10.4 Clear

清楚可编辑元素的内容，若元素不可编辑则返回错误

```java
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class clear {
  public static void main(String[] args) {
    WebDriver driver = new ChromeDriver();
    try {
      // Navigate to Url
      driver.get("https://www.google.com");
      // Store 'SearchInput' element
      WebElement searchInput = driver.findElement(By.name("q"));
      searchInput.sendKeys("selenium");
      // Clears the entered text
      searchInput.clear();
    } finally {
      driver.quit();
    }
  }
}
  
```



# 11 接管浏览器

接管chrome之前，需要先执行以下命令，同时将该参数加在浏览器快捷上做启动参数

```
chrome.exe --remote-debugging-port=9333 --user-data-dir="D:\杂项文档\autobrowser"
```

允许程序再9333端口调试chrome，并且信息保存在 D盘



```
public static void main(String[] args) {
    System.setProperty(CHROME_DRIVER_NAME, CHROME_DRIVER_87_PATH);
    ChromeOptions option = new ChromeOptions();
    option.setExperimentalOption("debuggerAddress", "127.0.0.1:9222");
    ChromeDriver driver = new ChromeDriver(option);
    System.out.println(driver.getTitle());
    driver.findElementByXPath("//*[@id=\"sb_form_q\"]").sendKeys("Java");
    driver.findElementByXPath("//*[@id=\"sb_form_q\"]").sendKeys(Keys.ENTER);

    System.out.println(driver.getTitle());

}
```









