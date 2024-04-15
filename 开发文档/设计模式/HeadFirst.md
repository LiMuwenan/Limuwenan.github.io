# 设计原则

**设计原则1：**识别应用中变化的方法，把它们和不变的方面分开

**设计原则2：**针对接口变成，而不是针对实现编程

**设计原则3：**优先使用组合而不是继承

**设计原则4：**尽量做到交互的对象之间的松耦合设计

**设计原则5：**开闭原则，对扩展开放，对修改关闭

**设计原则6：**依赖于抽象，不依赖具体

**设计原则7：**最少知识原则：减少对象之间的交互，尽可能地降低类与类之间地耦合







# 1 策略模式

## 1.1 继承

最初有一个 Duck 类，并且有方法

- 叫：quack()
- 戏水：swim()
- 外貌：display()

以及两个子类 MallardDuck 和 RedHeadDuck

![](../../PicBed/img/dev/designpattern/HeadFirst/Strategy1.PNG)

如果有了新的需求，比如鸭子会飞。

简单的做法是在Duck类中添加fly()方法

![](../../PicBed/img/dev/designpattern/HeadFirst/Strategy2.PNG)

**但是，可能一些Duck的子类并不需要飞行，比如橡皮鸭等等，这会造成这些类在飞**

![](../../PicBed/img/dev/designpattern/HeadFirst/Strategy3.PNG)

为了解决这个问题，可以在橡皮鸭类中，将fly()方法覆盖为不会飞

![](../../PicBed/img/dev/designpattern/HeadFirst/Strategy4.PNG)

有一个木头鸭子既不会飞也不会叫

![](../../PicBed/img/dev/designpattern/HeadFirst/Strategy5.PNG)

这样造成的问题是，可能有很多子类，都不需要fly()方法，然后又在子类中将这个方法覆盖为不做任何事，有大量的冗余，如果将来可能要扩展功能，又会需要改变这些子类的方法

## 1.2 接口

因此使用接口来进行组合式的添加行为，将fly()方法和quack()方法

![](../../PicBed/img/dev/designpattern/HeadFirst/Strategy6.PNG)

这个时候看起来好像是一个很好的做法，用到了组合的概念，但是如果需要对所有的子类的行为做出一些改变？需要改变每种鸭子的叫声，这就需要修改大量的代码

## 1.3 分离

**设计原则1：**识别应用中变化的方法，把它们和不变的方面分开

根据这条设计原则，可以将Duck的fly()和quack()行为抽出来，因为这两个行为是会改变的

**设计原则2：**针对接口变成，而不是针对实现编程

我们可以使用接口来表示每一种行为（FlyBehavior和QuackBehivor），行为的实现将实现其中一个接口。

使用一组类表示Fly，一组类表示Quack

![](../../PicBed/img/dev/designpattern/HeadFirst/Strategy7.PNG)

![](../../PicBed/img/dev/designpattern/HeadFirst/Strategy8.PNG)

通过这样的设计，其他类型就可以复用这样的行为，并且可以动态的改变每种类型的对应Fly和Quack行为，

上面所说的针对接口编程说的是广义的接口，不单单是java中的interface类型

对实现编程的例子：

> Dog d = new Dog();
>
> d.bark();//声明的变量d是Animal的一个具体实现，并且强迫我们对bark针对具体实现进行了编程

对接口编程则是：

> Animal animal = new Dog();
>
> animal.makeSound();//多态的调用dog的makeSound

更好的办法是子类型实例化不是硬编码，也在运行时确定

> a = getAnimal();
>
> a.makeSound();

## 1.4 整合

Duck将委托其飞行和嘎嘎叫行为，而不是使用Duck类（子类）中定义的嘎嘎叫个飞行方法。

![](../../PicBed/img/dev/designpattern/HeadFirst/Strategy9.PNG)

```java
public abstract class Duck {
    public FlyBehavior flyBehavior;
    public QuackBehavior quackBehavior;

    public void performQuack(){
        quackBehavior.quack();
    }

    public abstract void swim();

    public abstract void display();

    public void performFly(){
        flyBehavior.fly();
    }
}
```

对于一个继承了Duck的具体子类MallardDuck，它的接口实例化一个自己对应的行为类

quackBehavior 和 flyBehavior 都是从父类 Duck 中继承来的

**在构造器中进行实例化是一种针对实现编程的行为，不可取。本书后面的章节会修正这一点**

```java
public class MallardDuck extends Duck{

    public MallardDuck(){
        quackBehavior = new Quack();
        flyBehavior = new FlyWithWings();
    }

    @Override
    public void swim() {

    }

    @Override
    public void display() {
        System.out.println("I'm real Mallard duck");
    }
}

```

测试代码

基类 Duck

```java
public abstract class Duck {
    public FlyBehavior flyBehavior;
    public QuackBehavior quackBehavior;

    public void performQuack(){
        quackBehavior.quack();
    }

    public void performFly(){
        flyBehavior.fly();
    }

    public void swim(){
        System.out.println("All ducks float, even decoys!");
    }

    public abstract void display();
}

```

FlyBehavior接口和该接口的实现类

```java
public interface FlyBehavior {
    void fly();
}

public class FlyWithWings implements FlyBehavior{
    @Override
    public void fly() {
        System.out.println("I'm fly with wings!");
    }
}

public class FlyNoWay implements FlyBehavior{
    @Override
    public void fly() {
        System.out.println("I'm can't fly!");
    }
}
```

QuackBehavior接口和该接口的实现类

```java
public interface QuackBehavior {
    void quack();
}

public class Quack implements QuackBehavior{
    @Override
    public void quack() {
        System.out.println("quack quack quack");
    }
}

public class Squack implements QuackBehavior{
    @Override
    public void quack() {
        System.out.println("squack squack squack");
    }
}

public class MuteQuack implements QuackBehavior{
    @Override
    public void quack() {
        System.out.println("I'm can't quack...silence");
    }
}
```

测试

```java
public class Main {
    public static void main(String[] args) {
        Duck duck = new MallardDuck();
        duck.display();
        duck.performFly();//调用了MallardDuck的perform()方法，然后委托给该对象的flyBehavior（使用了该接口的fly方法）
        duck.performQuack();
    }
}

//输出
//I'm real Mallard duck
//I'm fly with wings!
//quack quack quack
```

## 1.5 动态设置

在duck基类中添加两个set方法动态的设置flyBehavior和quackBehivor接口的实例对象

```java
public void setFlyBehavior(FlyBehavior fb){
    flyBehavior = fb;
}

public void setQuackBehavior(QuackBehavior qb){
    quackBehavior = qb;
}
```

进行测试

```java
public class Main {
    public static void main(String[] args) {
        Duck duck = new MallardDuck();
        duck.display();
        duck.performFly();
        duck.performQuack();
        //开始我们的绿头鸭会飞，后来它受伤了
        duck.setFlyBehavior(new FlyNoWay());
        duck.performFly();
    }
}

//I'm real Mallard duck
//I'm fly with wings!
//quack quack quack
//I'm can't fly!
```



类图

![](../../PicBed/img/dev/designpattern/HeadFirst/Strategy10.PNG)

**设计原则3：**优先使用组合而不是继承

25页设计题作业

![](../../PicBed/img/dev/designpattern/HeadFirst/Strategy11.PNG)

# 2 观察者模式

## 2.1 场景

Weather-O-Rama给我们需求：它们检测设备数据，并收集到气象站，WeatherData追踪气象站的数据，并将该数据显示在显示设备上。最初又三种显示状态：当前状况、气象统计、简单预报

我们要实现这个需求并留下扩展能力

![](../../PicBed/img/dev/designpattern/HeadFirst/Observer1.PNG)

我们的主要任务是：创建一个应用，使用WeatherData对象来更新三个显示

气象站发给我们的源码对象

![](../../PicBed/img/dev/designpattern/HeadFirst/Observer2.PNG)

前三个方法用来获取数据；无论这三个值什么时候进行了更新，都会调用measurementsChanged()方法

我们需要对measurementsChanged()进行实现，来更新显示

简单实现

```java
public class WeatherData {
    
    public void measurementsChanged(){
        //1.首先进行了值得获取
        float temp = getTemperature();
        float humidity = getHumidity();
        float pressure = getPressure();
        
        //2.显示状态更新
        currentConditionDisplay.update(temp,humidity,pressure);
        statisticsDisplay.update(temp,humidity,pressure);
        forecastDisplay.update(temp,humidity,pressure);
    }
    
    public float getTemperature(){}
    
    public float getHumidity(){}
    
    public float getPressure(){}
}
```

上面的代码中，由于第二块状态更新是有可能发生变化的，这里的硬编码方式会在将来扩展时出现问题

## 2.2 观察者模式

报社运营：

1. 报社出版报纸
2. 客户向报社订阅报纸，每次出版都会交付给客户
3. 当客户不想看的时候可以不再订阅
4. 只要报社还在运营，就会有不同的客户进行订阅

**我们把出版者叫做主体（subject），订阅者叫做观察者（observer）**

![](../../PicBed/img/dev/designpattern/HeadFirst/Observer3.PNG)

观察者对象不想再做观察者时，给主题发送通知，观察者就可以离开了

**观察者模式定义对象之间的一对多依赖，这样一来，当一个对象改变状态时，它的所有依赖者都会收到通知并自动更新**

观察者模式类图

![](../../PicBed/img/dev/designpattern/HeadFirst/Observer4.PNG)

**设计原则4：**尽量做到交互的对象之间的松耦合设计



## 2.3 气象站实现

气象站类图

![](../../PicBed/img/dev/designpattern/HeadFirst/Observer5.PNG)

主题、观察者接口

```java
public interface Subject {
    void registerObserver(Observer ob);//注册具体的观察者

    void removeObserver(Observer ob);

    void notifyObserver();
}

public interface Observer {

    void update(float temp,float humidity,float pressure);
}


public interface displayElement {//显示接口
    void display();
}
```

WeatherData实现主题接口

```java
public class WeatherData implements Subject{
    
    private List<Observer> observers;//使用list持有Observer
    private float temp;
    private float humidity;
    private float pressure;
    
    public WeatherData(){
        observers = new ArrayList<>();
    }

    @Override
    public void registerObserver(Observer ob) {//注册一个Observer
        observers.add(ob);
    }

    @Override
    public void removeObserver(Observer ob) {//删除一个Observer
        observers.remove(ob);
    }

    @Override
    public void notifyObserver() {//有了状态更新，通知到每个观察者
        for(Observer ob : observers){
            ob.update(temp,humidity,pressure);
        }
    }

    public void measurementsChanged(){
        notifyObserver();
    }
    
    public void setMeasurements(float temperature,float humidity,float pressure){
        this.temp = temperature;
        this.humidity = humidity;
        this.pressure = pressure;
        measurementsChanged();
    }
}
```

显示元素

```JAVA
public class CurrentConditionDisplay implements Observer, DisplayElement {//实现观察者和显示元素接口

    private float temperature;
    private float humidity;
    private float pressure;
    private WeatherData weatherData;

    public CurrentConditionDisplay(WeatherData weatherData) {
        this.weatherData = weatherData;//自己订阅主题
        weatherData.registerObserver(this);//在主题中注册自己
    }

    @Override
    public void update(float temp, float humidity, float pressure) {
        this.temperature = temp;
        this.humidity = humidity;
        this.pressure = pressure;
        display();
    }

    @Override
    public void display() {
        System.out.println("Current condition" + temperature + "F degrees and " + humidity + "% humidity");
    }
}
```

测试

```java
public class WeatherStation {

    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();//1.创建主题对象

        CurrentConditionDisplay currentConditionDisplay = new CurrentConditionDisplay(weatherData);
        ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);//有多个观察者才能观察出效果

        weatherData.setMeasurements(80,65,30.4f);//模拟气象值
    }
}
```

## 2.4 主动拉取

上面的观察者和主题之间的关系时，主题变化，就会推送给所有观察者，然后这些观察者就会更新状态。这样有可能很多观察者获得了它们本来不需要的信息

我们可不可以修改为，当观察者需要信息的时候，就找主题拉取呢？

JFrame和安卓等等的监听器就是一种拉取消息的观察者，将一个观察者（按钮监听器）注册给一个主题（按钮）；可以使用内部类的方式或者lambda表达式的方式

按钮这样的可以主动拉取，每点一次获取一次数据；这样的实时显示就是在数据更新后进行推送（每个观察者只获取自己需要的值）

按需推送，在update方法中不传递具体的参数，添加每种属性的获取方法

```java
@Override
public void notifyObserver() {//有了状态更新，通知到每个观察者
    for(Observer ob : observers){
        ob.update();
    }
}

public float getTemp() {
    return temp;
}

public float getHumidity() {
    return humidity;
}

public float getPressure() {
    return pressure;
}
```

观察者在update的时候使用getter获取该值

```java
@Override
public void update() {//按需获取
    this.temperature = weatherData.getTemp();
    this.humidity = weatherData.getHumidity();
    this.pressure = weatherData.getPressure();
    display();
}
```

# 3 装饰器模式

## 3.1 场景

咖啡店订单，有一个基类，继承出四种基本的饮品

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Decorator1.png)

对于这四种基本的饮品，还可以加各种小料，如果将每一种组合都继承基类，那么将会造成类爆炸，并且当修改某一个小料的价格时，将会是恐怖的维护量

这违反了前面两个设计原则：尽量将不变和变化分开、多使用组合而不是继承

**设计原则5：**开闭原则，对扩展开放，对修改关闭

## 3.2 装饰器模式

1. 首先只有一个 DarkRoast 对象
2. 需要加入 Mocha，就用Mocha对象包裹DarkRoast
3. 需要加入Whip，就用Whip对象包裹Mocha
4. 。。。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Decorator2.png)

- 装饰者有着和所装饰对象同样的基类
- 你可以用一个或者多个装饰者包裹一个对象
- 鉴于装饰者有着和所装饰对象同样的基类，在需要原始对象的场合，我们可以传递一个被装饰的对象
- 装饰者在委托给所装饰对象之前或（与）之后添加自己的行为，来做剩下的工作
- 对象可以在任何时候被装饰，因此我们可以在运行时用任意数量的装饰者动态的装饰对象

装饰者模式动态地将额外责任附加到对象上。对于扩展功能，装饰者提供子类化之外的弹性替代方案

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Decorator3.png)

## 3.3 代码实现

创建基类

这是一个抽象类，包括描述和计算价格，价格方法为抽象方法，由子类自己实现

```java
public abstract class Beverage {

    String description = "Unknown Beverage";

    public String getDescription(){
        return description;
    }

    public abstract double cost();
}
```

创建一个装饰者，装饰者继承自基类。装饰者中有一个 Beverage 就是我们要包裹的对象

描述和价格都需要子类进行实现

```java
public abstract class CondimentDecorator extends Beverage{

    Beverage beverage; //每个装饰者将要包裹Beverage

    public abstract String getDescription();//需要每个调料装饰者都重新实现getDescription

}
```

创建从基类继承出来的基础饮品

```java
public class DarkRoast extends Beverage{
    public DarkRoast() {
        description = "DarkRoast";
    }

    @Override
    public double cost() {
        return 2.0;
    }
}

public class Espresso extends Beverage{
    public Espresso() {
        description = "Espresso";//设置描述
    }

    @Override
    public double cost() {
        return 1.99;//返回 Espresso 的价格
    }
}

public class HouseBlend extends Beverage{

    public HouseBlend(){
        description = "HouseBlend";
    }

    @Override
    public double cost() {
        return .89;
    }
}

```

创建从装饰者类继承出来的小料类

```java
public class Mocha extends CondimentDecorator{

    public Mocha(Beverage beverage) {//传入一个被包装对象
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.20;//先调用被包装对象的价格，再加上当前小料的价格
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Mocha";//在每种饮料的后面注释添加的每样东西
    }
}

public class Soy extends CondimentDecorator{

    public Soy(Beverage beverage) {//传入一个被包装对象
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.15;//先调用被包装对象的价格，再加上当前小料的价格
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Soy";//在每种饮料的后面注释添加的每样东西
    }
}

public class Whip extends CondimentDecorator{

    public Whip(Beverage beverage) {//传入一个被包装对象
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        return beverage.cost() + 0.25;//先调用被包装对象的价格，再加上当前小料的价格
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Whip";//在每种饮料的后面注释添加的每样东西
    }
}

```

测试调用

每次加料都会创建一个新的对象，新对象重新计算过价格和描述

底下测试中用了大量 new 来创建新对象，后面我们会用工厂和生成器模式来优化

```java
public class Main {
    public static void main(String[] args) {
        Beverage beverage = new Espresso();//一杯浓缩，底材
        System.out.println(beverage.getDescription() + " $ "+beverage.cost());//输出一杯浓缩的价格

        Beverage beverage2 = new DarkRoast();
        beverage2 = new Mocha(beverage2);//加料,Mocha
        beverage2 = new Mocha(beverage2);
        beverage2 = new Whip(beverage2);
        System.out.println(beverage2.getDescription() + " $ " + beverage2.cost());
    }
}
//Espresso $ 1.99
//DarkRoast, Mocha, Mocha, Whip $ 2.6500000000000004
```

- 如果你的代码依赖于具体组件类型，装饰者会破坏这个代码。只要只针对抽象组件类型编码，装饰者的使用会对你的代码保持透明。但是，一旦你开始针对具体组件编码，你要重新思考应用设计以及装饰者的使用

## 3.4 引入新需求

现在需要引入容量，以及按照容量售卖

小杯、中杯、大杯分别为10，15，20

这里说的是，加料的量也不同，根据不同的量来制定价格

新的Beverage类

```java
public abstract class Beverage {
    public  enum Size{TALL,GRANDE,VENTI};
    Size size = Size.TALL;

    String description = "Unknown Beverage";

    public String getDescription(){
        return description;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public abstract double cost();
}
```

装饰者类怎么修改？

```java
public abstract class CondimentDecorator extends Beverage{

    Beverage beverage; //每个装饰者将要包裹Beverage

    public abstract String getDescription();//需要每个调料装饰者都重新实现getDescription

    public Size getSize(){
        return beverage.size;
    }
}

public class Soy extends CondimentDecorator{

    public Soy(Beverage beverage) {//传入一个被包装对象
        this.beverage = beverage;
    }

    @Override
    public double cost() {
        double cost = beverage.cost();
        if(beverage.getSize() == Size.TALL){
            cost+=0.10;
        }else if(beverage.getSize() == Size.GRANDE){
            cost+=0.15;
        }else{
            cost+=0.20;
        }
        return cost;//先调用被包装对象的价格，再加上当前小料的价格
    }

    @Override
    public String getDescription() {
        return beverage.getDescription() + ", Soy";//在每种饮料的后面注释添加的每样东西
    }
}
```

# 4 工厂模式

## 4.1 场景

有一个披萨店，在订单方法中制作披萨

```java
public Pizza orderPizza(){
    Pizza pizza = new Pizza();
    
    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();
    
    return pizza;
}
```

但是上面的方法中，用到了 `Pizza pizza = new Pizza();` 将披萨的生成写死了，如果需要更多的类型披萨这样的代码就不合适

对上面的代码进行改进

```java
public Pizza orderPizza(String type){
    Pizza  pizza = null;

    if("cheese".equals(type)){
        pizza = new CheesePizza();
    }else if("bacon".equals(type)){
        pizza = new BaconPizza();
    }else if("greek".equals(type)){
        pizza = new GreekPizza();
    }

    pizza.prepare();
    pizza.bake();
    pizza.cut();
    pizza.box();

    return pizza;
}
```

使用type参数，指定需要什么类型的pizza

但是如果我们需要增加类型和减少类型，会破坏我们之前原则——对修改关闭，对扩展开放

而且在上面的方法中，中间if代码块是变化的，而下面的打包代码块是不变的，因此我们应该将这两个部分分开来

## 4.2 简单工厂

使用简单工厂，将创建披萨的过程封装起来，我们只需要传入需要的类型就可以得到对应的披萨

```java
public class PizzaStore {
    SimplePizzaFactory simplePizzaFactory;
    
    public PizzaStore(){
        simplePizzaFactory = new SimplePizzaFactory();
    }
    public Pizza orderPizza(String type){
        Pizza  pizza = null;
        
        pizza = simplePizzaFactory.createPizza(type);

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }
}
```

将4.1中if代码封装进工厂的createPizza中，订单方法中使用时不用管如何创建pizza，返回什么pizza

对于简单工厂模式，如果只有一个地方使用工厂创建显得多此一举，如果有多个方法中都要创建披萨的时候，工厂方法就会显示出作用

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Factory1.png)

## 4.3 场景

如果披萨店要开加盟店，有纽约风味和芝加哥风味，它们需要遵循同样的打包过程，但是制作的披萨口味可能不同

因此可以将这个工厂抽象出来，做成一个抽象方法，放在PizzaStroe类中，让披萨的活动局限于PizzaStore类中。

如果采用下面的方式，披萨可能在纽约工厂类中被其他步骤加工

```java
NYPizzaStore nyFactory = new NYPizzaStore();
PizzaStore nyStore = new PizzaStore(nyFactory);
nyStore.orderPizza("Veggie");
```

## 4.4 工厂模式

将PizzaStore做成抽象类

```java
public abstract class PizzaStore {

    public Pizza orderPizza(String type){//为了不让子类随意修改这个方法，可以声明为final
        Pizza  pizza = null;

        pizza = createPizza(type);//createPizza方式不再在工厂中了

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    abstract Pizza createPizza(String type);//抽象的工厂对象
}

```

对不同区域使用不同的子类，都继承自PizzaStore，它们在各自的create方法中创建自有的口味的披萨

```java
public class ChicagoStylePizzaStore extends PizzaStore{
    @Override
    Pizza createPizza(String type) {
        if("bacon".equals(type)){
            return new ChicagoStyleBaconPizza();
        }else if("cheese".equals(type)){
            return new ChicagoStyleCheesePizza();
        }else if("greek".equals(type)){
            return new ChicagoStyleGreekPizza();
        }else return null;
    }
}

public class NYStylePizzaStore extends PizzaStore{
    @Override
    Pizza createPizza(String type) {
        if("bacon".equals(type)){
            return new NYStyleBaconPizza();
        }else if("cheese".equals(type)){
            return new NYStyleCheesePizza();
        }else if("greek".equals(type)){
            return new NYStyleGreekPizza();
        }else return null;
    }
}
```

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Factory2.png)

PizzaStore的子类只是子类，它们怎么能决定所有的事情？在NYStylePizzaStore中没有任何决定逻辑的代码？

> 对于orderPizza()放在在抽象PizzaStore中定义，而不是子类中。因此，该方法不知道哪个子类实际运行了代码制作了披萨

由于PizzaStore是抽象的，orderPizza()又不知道涉及到了哪一个具体类，所以说就是被解耦了。

orderPizza()调用了createPizza()，某个子类被调用，具体使用的哪个子类，由下单时选择的披萨店子类决定

```java
public abstract class PizzaStore {

    public Pizza orderPizza(String type){
        Pizza  pizza = null;

        pizza = createPizza(type);//createPizza方式不再在工厂中了

        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();

        return pizza;
    }

    protected abstract Pizza createPizza(String type);//抽象的工厂对象
}
```

工厂方法处理对象的创建，并将对象创建封装在子类中（基类中是抽象的，需要继承实现），使得基类中的代码和子类的创建代码解耦

```java
abstract Product factoryMethod(String type);
//abstract 依靠子类创建
//Product 返回的产品基类
//factoryMethod 工厂方法
```

## 4.5 测试

完善一下Pizza类

```java
public abstract class Pizza {
    public String name;
    public String dough;//面团类型
    public String sauce;//酱料
    List<String> toppings = new ArrayList<>();//馅料
    
    public void prepare(){
        System.out.println("Preparing " + name);
        System.out.println("Tossing dough...");
        System.out.println("Adding sauce...");
        System.out.println("Adding toppings: ");
        for(String topping : toppings){
            System.out.println(" " + topping);
        }
    }
    public void bake(){
        System.out.println("Bake for 25 minutes");
    }
    public void cut(){
        System.out.println("Cutting the pizza into diagonal slices");
    }
    public void box(){
        System.out.println("Place pizza in official PizzaStore box");
    }

    public String getName() {
        return name;
    }
}
```

纽约风格披萨和芝加哥风格的披萨

```java
public class NYStyleCheesePizza extends Pizza{
    
    public NYStyleCheesePizza(){
        name = "NY Style Sauce and Cheese Pizza";
        dough = "Thin Crust Dough";
        sauce = "Marinara Sauce";
        
        toppings.add("Grated Reggiano Cheese");
    }
    
}

public class ChicagoStyleCheesePizza extends Pizza{
    
    public ChicagoStyleCheesePizza(){
        name = "Chicago Style Deep Dish Cheese Pizza";
        dough = "Extra Thick Crust Dough";
        sauce = "Plum Tomato Sauce";
        
        toppings.add("Shredded Mozzaralla Cheese");
    }

    @Override
    public void cut() {
        System.out.println("Cutting the Pizza into square slices");//芝加哥风格的切片
    }
}
```

测试

```java
public class Main {
    public static void main(String[] args) {
        PizzaStore nyPizzaStore = new NYStylePizzaStore();
        Pizza pizza = nyPizzaStore.orderPizza("cheese");
        System.out.println();
        PizzaStore chiPizzaStore = new ChicagoStylePizzaStore();
        Pizza pizza2 = chiPizzaStore.orderPizza("cheese");
    }

//    Preparing NY Style Sauce and Cheese Pizza
//    Tossing dough...
//    Adding sauce...
//    Adding toppings:
//    Grated Reggiano Cheese
//    Bake for 25 minutes
//    Cutting the pizza into diagonal slices
//    Place pizza in official PizzaStore box
//
//    Preparing Chicago Style Deep Dish Cheese Pizza
//    Tossing dough...
//    Adding sauce...
//    Adding toppings:
//    Shredded Mozzaralla Cheese
//    Bake for 25 minutes
//    Cutting the Pizza into square slices
//    Place pizza in official PizzaStore box
    
}
```

**对工厂模式比较重要的就是类分为两组，一组是创建者类，另一组是产品类，创建者的顶层基类包含一个抽象的创建方法，由它的子类们实现这个创建方法**

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Factory3.png)

## 4.6 依赖倒置原则

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Factory4.png)

如果我们是这样一种设计，在PizzaStore中按照传入参数（店铺风格、披萨类型）制作披萨，会导致店铺和每一个披萨实体类高耦合，也就是PizzaStore类依赖于每一格Pizza类

根据依赖倒置原则，我们应该以来抽象而不是具体

**设计原则6：**依赖于抽象，不依赖具体

这和“针对接口编程，不针对实现编程很像”，不过这个更强调抽象的概念。

高层组件不应该依赖于低层组件，而且它们都应该依赖于一个抽象

应用该原则

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Factory5.png)

高层的PizzaStore不直接依赖于Pizza类族，而是依赖于Pizza的抽象；低层的Pizza类族依赖于Pizza抽象类

避免依赖倒置原则的几条指南：

- 变量不应该持有到具体类的引用：如果使用new，就会持有到具体类的引用，使用工厂绕开
- 类不应该派生自具体类：如果派生自具体类，就会依赖一个具体类，应该派生自抽象类或接口
- 方法不应该覆盖其任何基类中的已实现的方法：如果覆盖了已实现的方法，那么基类就不是一个真正适合被继承的抽象，基类中这些已实现的方法，应该由所有子类共享

## 4.7 场景

在前面的例子中，我们只有工厂不同，现在对不同的工厂，我们有不同配料的产品，同样是cheese披萨，纽约店和芝加哥店的配料是不同的

- 芝加哥芝士披萨配料
  - 李子番茄酱、Mozzarella芝士、Parmesan芝士、牛至
- 纽约芝士披萨配料
  - 意式番茄酱、Reggiano芝士、蒜

每个地区的店都用的是不同的原料，每个地区的配料可以看成一组

## 4.8 抽象工厂

设置原料工厂

```java
public interface PizzaIngredientFactory {

    public Dough createDough();//面团
    public Sauce createSauce();//酱汁
    public Cheese createCheese();//芝士
    public Veggies[] createVeggies();//蔬菜
    public Pepperoni createPepperoni();//意式香肠
    public Clams createClam();//蛤蜊

}

public class NYPizzaIngredientFactory implements PizzaIngredientFactory{//纽约原料工厂
    @Override
    public Dough createDough() {
        return new ThinCrustDough();
    }

    @Override
    public Sauce createSauce() {
        return new MarinaraSauce();
    }

    @Override
    public Cheese createCheese() {
        return new ReggianoCheese();
    }

    @Override
    public Veggies[] createVeggies() {
        return new Veggies[]{new Garlic(),new Mushroom(),new Onion(),new RedPepper()};
    }

    @Override
    public Pepperoni createPepperoni() {
        return new SlicedPepperoni();
    }

    @Override
    public Clams createClam() {
        return new FreshClams();
    }
}

```

Pizza类重写

```java
public abstract class Pizza {
    String name;

    Dough dough;
    Sauce sauce;
    Veggies[] veggies;
    Cheese cheese;
    Pepperoni pepperoni;
    Clams clam;

    abstract void prepare();//因为不同的地方的原料不同，所以具体实现留给各个门店

    public void bake() {
        System.out.println("Bake for 25 minutes");
    }

    public void cut() {
        System.out.println("Cutting the pizza into diagonal slices");
    }

    public void box() {
        System.out.println("Place pizza in official PizzaStore box");
    }

    public String getName() {
        return name;
    }
}
```

芝加哥披萨和纽约披萨只有原料不同，制作步骤是相同的，所以使用一个CheesePizza类来制作出纽约风味和芝加哥风味，区别在于使用哪个原料工厂

```java
public class CheesePizza extends Pizza{
    PizzaIngredientFactory pizzaIngredientFactory;

    public CheesePizza(PizzaIngredientFactory pizzaIngredientFactory){//初始化时设置具体的原料工厂
        this.pizzaIngredientFactory = pizzaIngredientFactory;
    }

    @Override
    void prepare() {
        System.out.println("Preparing " + name);
        dough = pizzaIngredientFactory.createDough();//披萨所使用的原料从原料工厂解耦
        sauce = pizzaIngredientFactory.createSauce();
        cheese = pizzaIngredientFactory.createCheese();
    }
}
```

纽约店铺重写

```java
public class NYStylePizzaStore extends PizzaStore {
    @Override
    protected Pizza createPizza(String type) {
        Pizza pizza = null;
        PizzaIngredientFactory ingredientFactory = new NYPizzaIngredientFactory();//根据店铺不同使用不同的工厂实现
        if ("clam".equals(type)) {
            pizza =  new ClamPizza(ingredientFactory);
        } else if ("cheese".equals(type)) {
            pizza =  new CheesePizza(ingredientFactory);
        } else return null;
        
        return pizza;
    }
}
```

**抽象工厂模式提供一个接口来创建相关或者依赖对象的家族，而不需要制定具体的类。工厂的工厂**

## 4.9 工厂方法和抽象工厂方法的比较

工厂方法：

- 通过继承，在子类中实现基类的抽象方法，不同的子类实现方法不同
- 工厂方法模式的整体思想就是用子类来创建，客户只需要知道所用的抽象类型



抽象工厂方法：

- 通过对象组合，将各种原料工厂的产品组合起来
- 为创建产品家族提供了一个抽象，这个类的子类定义如何产生这些产品

工厂方法，NYStylePizzaStore和ChicagoStylePizzaStore继承PizzaStore，它们根据传入的参数产生不同口味的披萨

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Factory6.png)

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Factory7.png)

# 5 单例模式

单例模式不但要提供保证只有一个实例存在，还要保证能够全局访问

一个全局变量可以提供后者，但提供不了前者。全局变量也倾向于怂恿开发人员用大量的小对象的全局引用来污染命名空间

使用多个类加载器可能导致出现单例失效

## 5.1 经典的单例模式

懒汉式

```java
public class Singleton {

    private static Singleton uniqueInstance;//使用静态变量持有Singleton类的唯一实例

    private Singleton(){} //构造器私有，只有Singleton可以实例化这个类

    public static Singleton getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }

}
```

这样的单例是非线程安全的

## 5.2 线程安全单例

同步懒汉式

```java
public class Singleton {

    private static Singleton uniqueInstance;//使用静态变量持有Singleton类的唯一实例

    private Singleton(){} //构造器私有，只有Singleton可以实例化这个类

    public static synchronized Singleton getInstance(){
        if(uniqueInstance == null){
            uniqueInstance = new Singleton();
        }
        return uniqueInstance;
    }

}
```

这样的在方法上同步，会导致同步开销大，在实际的应用中，其实只要设施了好了单例，就不需要再同步了

## 5.3 饿汉式

如果经常需要getInstance()，那么我们需要改进上面的代码；如果只是偶尔，那可以放过他

饿汉式：静态初始化中创建

```java
public class Singleton {

    private static Singleton uniqueInstance = new Singleton();//使用静态变量持有Singleton类的唯一实例

    private Singleton(){} //构造器私有，只有Singleton可以实例化这个类

    public static Singleton getUniqueInstance() {
        return uniqueInstance;
    }   

}
```

## 5.4 Double Check

```java
public class Singleton {

    private static volatile Singleton uniqueInstance;
    
    private Singleton(){} //构造器私有，只有Singleton可以实例化这个类
    
    private static Singleton getInstance(){
        if (uniqueInstance==null){//检查实例，如果没有进入同步区域
            synchronized (Singleton.class){//只有第一次检查才同步
                if (uniqueInstance==null){//进入后如果还是空的才创建
                    uniqueInstance = new Singleton();
                }
            }
        }
        return uniqueInstance;
    }
}
```

volatile 关键字很重要，new操作不是原子性，因为重排的缘故，B线程认为实例不是null，直接返回，实际上还是空的。具体解释参照java并发编程艺术

# 6 命令模式

## 6.1 场景

设计一个可编程遥控器，假设每两个按钮一对，开功能和关功能。

我们要遥控的对象可能包括灯（开关）、窗帘（开合）、热水器（开关、设定温度）等等，每一个设备对象都有不同的功能，且接口不同。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Commad1.png)

还有很多其他类似的类

我们不可能设计遥控器时写一大堆的嵌套 if-else，这样的作法使得命令的发出者和被命令的对象高度耦合

```java
if(slot==Light) light.on();
else if(slot==Hotthub) hottub.jetsOn()
```

我们需要的是：遥控器不知道工作内容是什么，只要有个命令对象知道如何和正确的对象沟通，把事情做好就好了。这样遥控器和灯（设备）就解耦了。

## 6.2 餐馆

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Commad2.png)

餐馆中的角色和责任：

- 订单小纸片是一个对象，用来请求准备点赞。和其他对象一样，可以被四处传递，从服务员到订单柜台，或者到下一班服务员。他有一个接口，这个接口只包含一个方法，orderUp()，该方法封装准备餐点所需要的所有动作。它也有一个到需要准备它的对象（厨师）的引用。这些被封装起来，服务员不必知道订单上有什么，她只需要将订单给订单窗口就好了。
- 服务员的工作是接受订单，然后调用orderUp()方法。她从顾客那里takeOrder()获得订单
- 厨师是真正来执行订单上命令的人。服务员调用了orderUp，厨师接管并创建餐品。**服务员和厨师之间通过订单对象进行了解耦。**

命令模式下的对象调用

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Commad3.png)



餐馆和命令模式对比：

- 顾客--客户
- createOrder--createCommandObject
- 订单--命令对象
- takeOrder--setCommand
- 服务员--调用者
- orderUp--execute
- 厨师--接收者

## 6.3 测试

实现Commad接口

```java
public interface Command {
    public void execute();
}
```

实现开灯和开门的命令

```java
public class LightOnCommand implements Command{
    Light light;

    public LightOnCommand(Light light){//构造器被传入要控制的特定的灯，并藏在灯实例变量中。当execute被调用，灯对象成为请求的接收者
        this.light = light;
    }

    @Override
    public void execute() {//不同命令（针对不同设备）执行的内容不同
        light.on();
    }
}

public class GarageDoorOpenCommand implements Command{

    GarageDoor garageDoor;

    public GarageDoorOpenCommand(GarageDoor garageDoor) {
        this.garageDoor = garageDoor;
    }

    @Override
    public void execute() {
        garageDoor.up();
    }
}
```

使用命令对象，将命令对象绑定要遥控器槽位

```java
public class SimpleRemoteControl {
    Command slot; // 假设遥控器只有一个命令槽
    public SimpleRemoteControl(){}

    //我们有一个方法，用于设置控制器槽的命令，如果这段代码的客户要求更改按钮行为，就可以多次调用
    public void setCommand(Command command){
        slot = command;
    }

    //当按钮被按下，这个方法被调用，我们要做的是把当前命令绑定到槽，并调用其execute()方法
    public void buttonWasPressed(){
        slot.execute();//遥控器根本不知道会执行什么命令
    }
}
```

测试

```java
public class Main {//命令客户
    public static void main(String[] args) {
        SimpleRemoteControl simpleRemoteControl = new SimpleRemoteControl();//遥控器对象
        Light light = new Light();//要控制的设备
        Command command = new LightOnCommand(light);//设备命令

        simpleRemoteControl.setCommand(command);//向遥控器对象设置命令
        simpleRemoteControl.buttonWasPressed();//按钮按下，命令执行

        GarageDoor garageDoor = new GarageDoor();
        Command command1 = new GarageDoorOpenCommand(garageDoor);
        simpleRemoteControl.setCommand(command1);
        simpleRemoteControl.buttonWasPressed();
    }
}

//输出
//Light is on
//GarageDoor is open
```

## 6.4 命令模式定义

命令模式：把请求封装为对象，以便于不用的请求、队列、日志请求来参数化其他对象，并支持可撤销的操作。

当调用者调用execute方法的时候，接收者的具体方法会被调用

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Commad4.png)

## 6.5 扩展功能

我们的遥控器有3对槽位，并且还有undo功能

带槽位的遥控器

```java
public class RemoteControl {
    Command[] onCommands;
    Command[] offCommands;

    public RemoteControl(){
        onCommands = new Command[3];//初始化三个命令槽
        offCommands = new Command[3];

        Command noCommand = new NoCommand();
        for(int i=0;i<3;++i){
            onCommands[i] = noCommand;
            offCommands[i] = noCommand;
        }
    }

    public void setCommand(int slot,Command onCommand,Command offCommand){//在slot槽位设置开关命令
        onCommands[slot] = onCommand;//没有被设置具体命令的槽执行没有命令的方法，该方法什么都不做，省去了执行时判断的步骤
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPushed(int slot){//执行对应槽位的命令
        onCommands[slot].execute();
    }

    public void offButtonWasPushed(int slot){//执行对应槽位的命令
        offCommands[slot].execute();
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n----- Remote Control -----\n");
        for(int i=0;i< onCommands.length;++i){
            stringBuilder.append("[slot " + i + "] " + onCommands[i].getClass().getName()+"    "+offCommands[i].getClass().getName()+"\n");
        }
        return stringBuilder.toString();
    }
}
```

灯的类和命令类

```java
public class  Light {

    public void on(){
        System.out.println("Light is on");
    }

    public void off(){
        System.out.println("Light is off");
    }
}

public class LightOnCommand implements Command{
    Light light;

    public LightOnCommand(Light light){//构造器被传入要控制的特定的灯，并藏在灯实例变量中。当execute被调用，灯对象成为请求的接收者
        this.light = light;
    }

    @Override
    public void execute() {//不同命令（针对不同设备）执行的内容不同
        light.on();
    }
}

public class LightOffCommand implements Command{
    Light light;

    public LightOffCommand(Light light){
        this.light = light;
    }
    @Override
    public void execute() {
        light.off();
    }
}

```

车库的类和命令类

```java
public class GarageDoor {
    public void up(){
        System.out.println("GarageDoor is open");
    }

    public void down(){
        System.out.println("GarageDoor is close");
    }

    public void stop(){}

    public void lightOn(){}

    public void lightOff(){
        System.out.println("GarageDoor light is off");
    }
}

public class GarageDoorOpenCommand implements Command{

    GarageDoor garageDoor;

    public GarageDoorOpenCommand(GarageDoor garageDoor) {
        this.garageDoor = garageDoor;
    }

    @Override
    public void execute() {
        garageDoor.up();
    }
}

public class GarageDoorCloseWithLightCommand implements Command{
    GarageDoor garageDoor;

    public GarageDoorCloseWithLightCommand(GarageDoor garageDoor) {
        this.garageDoor = garageDoor;
    }

    @Override
    public void execute() {
        garageDoor.lightOff();
        garageDoor.down();
    }
}
```

CD机的类和命令类

```java
public class Stereo {
    public void on(){
        System.out.println("Stereo is on");
    }

    public void setCD(){
        System.out.println("Stereo set CD");
    }

    public void setVolume(){
        System.out.println("Stereo set volume");
    }

    public void off(){
        System.out.println("Stereo is off");
    }
}

public class StereoOnWithCDCommand implements Command{
    Stereo stereo;

    public StereoOnWithCDCommand(Stereo stereo){
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.on();
        stereo.setCD();
        stereo.setVolume();
    }
}

public class StereoOffCommand implements Command{
    Stereo stereo;

    public StereoOffCommand(Stereo stereo){
        this.stereo = stereo;
    }

    @Override
    public void execute() {
        stereo.off();
    }
}
```

测试

```java
public class Main {//命令客户
    public static void main(String[] args) {
        RemoteControl remote = new RemoteControl();

        Light light = new Light();
        GarageDoor door = new GarageDoor();
        Stereo stereo = new Stereo();

        //灯命令
        Command lightOnCommand = new LightOnCommand(light);
        Command lightOffCommand = new LightOffCommand(light);

        //车库命令
        Command GarageDoorOpenCommand = new GarageDoorOpenCommand(door);
        Command GarageDoorCloseWithLightCommand = new GarageDoorCloseWithLightCommand(door);

        //CD机命令
        Command stereoOnWithCDCommand = new StereoOnWithCDCommand(stereo);
        Command stereoOffCommand = new StereoOffCommand(stereo);

        //设置命令
        remote.setCommand(0,lightOnCommand,lightOffCommand);
        remote.setCommand(1,GarageDoorOpenCommand,GarageDoorCloseWithLightCommand);
        remote.setCommand(2,stereoOnWithCDCommand,stereoOffCommand);

        System.out.println(remote);

        //使用按键
        remote.onButtonWasPushed(0);
        remote.offButtonWasPushed(0);
        remote.onButtonWasPushed(1);
        remote.offButtonWasPushed(1);
        remote.onButtonWasPushed(2);
        remote.offButtonWasPushed(2);

    }
}
//输出
//-----Remote Control---- -
//[slot 0]command.LightOnCommand command.LightOffCommand
//[slot 1]command.GarageDoorOpenCommand command.GarageDoorCloseWithLightCommand
//[slot 2]command.StereoOnWithCDCommand command.StereoOffCommand
//
//Light is on
//Light is off
//GarageDoor is open
//GarageDoor light is off
//GarageDoor is close
//Stereo is on
//Stereo set CD
//Stereo set volume
//Stereo is off
```

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Commad5.png)

如果Command接口只有一个抽象方法是，在Java中还可以使用lambda表达式来进行方法的调用，这样就不需要创建很多的命令类。

## 6.6 撤销

在命令接口中添加undo接口

```java
public interface Command {
    public void execute();
    public void undo();
}
```

对灯实现撤销功能

```java
public class LightOnCommand implements Command{
    Light light;

    public LightOnCommand(Light light){//构造器被传入要控制的特定的灯，并藏在灯实例变量中。当execute被调用，灯对象成为请求的接收者
        this.light = light;
    }

    @Override
    public void execute() {//不同命令（针对不同设备）执行的内容不同
        light.on();
    }

    @Override
    public void undo() {
        light.off();
    }
}

public class LightOffCommand implements Command{
    Light light;

    public LightOffCommand(Light light){
        this.light = light;
    }
    @Override
    public void execute() {
        light.off();
    }

    @Override
    public void undo() {
        light.on();
    }
}
```

在遥控器添加对撤销的支持

```java
public class RemoteControlWithUndo {
    Command[] onCommands;
    Command[] offCommands;
    Command undoCommand;//记录上一条执行的命令

    public RemoteControlWithUndo(){
        onCommands = new Command[3];//初始化三个命令槽
        offCommands = new Command[3];

        Command noCommand = new NoCommand();
        for(int i=0;i<3;++i){
            onCommands[i] = noCommand;//没有被设置具体命令的槽执行没有命令的方法，该方法什么都不做，省去了执行时判断的步骤
            offCommands[i] = noCommand;
        }
        undoCommand = noCommand;
    }

    public void setCommand(int slot,Command onCommand,Command offCommand){//在slot槽位设置开关命令
        onCommands[slot] = onCommand;
        offCommands[slot] = offCommand;
    }

    public void onButtonWasPushed(int slot){//执行对应槽位的命令
        onCommands[slot].execute();
        undoCommand = onCommands[slot];
    }

    public void offButtonWasPushed(int slot){//执行对应槽位的命令
        offCommands[slot].execute();
        undoCommand = offCommands[slot];
    }
    
    public void undoButtonWasPushed(){
        undoCommand.undo();
    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n----- Remote Control -----\n");
        for(int i=0;i< onCommands.length;++i){
            stringBuilder.append("[slot " + i + "] " + onCommands[i].getClass().getName()+"    "+offCommands[i].getClass().getName()+"\n");
        }
        return stringBuilder.toString();
    }
}
```

## 6.7 组合命令

通过一个按键，执行一组命令

```java
public class MacroCommand implements Command{//宏命令
    public Command[] commands;

    @Override
    public void execute() {
        for(Command command : commands){
            command.execute();
        }
    }

    @Override
    public void undo() {

    }
}
```



```java
MacroCommand onMacro = new MacroCommand();
MacroCommand offMacro = new MacroCommand();
onMacro.commands = new Command[]{lightOnCommand,GarageDoorOpenCommand,stereoOnWithCDCommand};
offMacro.commands = new Command[]{lightOffCommand,GarageDoorCloseWithLightCommand,stereoOffCommand};
```

# 7 适配器和外观模式

## 7.1 现实中的适配器

在英国要使用美国的电器，就需要一个交流电适配器。

英国的插座为3孔，美国的插座为2孔，且交流电频率不同。使用适配器可以改变英国插座的接口，供美国的产品使用



假设有一个软件系统，现在需要把一个新的厂商类库整合进去，但新的厂商设计的接口和之前的厂商不同。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Adapter1.png)

已有系统和厂商类的代码都是不能修改的，所以这个时候需要一个适配器来连接两个模块

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Adapter2.png)

## 7.2 场景

假设有一个鸭子接口，并有一个绿头鸭实现

```java
public interface Duck {
    public void quack();//呱呱叫
    public void fly();
}

public class MallardDuck implements Duck{
    @Override
    public void quack() {
        System.out.println("Quack");
    }

    @Override
    public void fly() {
        System.out.println("I'm flying");
    }
}
```

还有一个火鸡接口，并且有一个火鸡实现

```java
public interface Turkey {
    public void gobble();//咯咯叫
    public void fly();
}

public class WildTurkey implements Turkey{
    @Override
    public void gobble() {
        System.out.println("Gobble");
    }

    @Override
    public void fly() {
        System.out.println("I'm flying a short distance");
    }
}
```

现在Duck短缺，需要临时拉一个火鸡类凑数，可以创建一个火鸡类的适配器

```java
public class TurkeyAdapter implements Duck {//实现目标类的接口
    Turkey turkey;//被适配为Duck接口
    
    public TurkeyAdapter(Turkey turkey){//将要适配的对象引用进来
        this.turkey = turkey;
    }
    
    @Override
    public void quack() {//在目标类接口中调用要适配对象
        turkey.gobble();
    }

    @Override
    public void fly() {//在目标类接口中调用要适配对象
        turkey.fly();
    }
}
```

测试适配器

```java
public class Main {
    public static void main(String[] args) {
        Duck duck = new MallardDuck();
        Turkey turkey = new WildTurkey();//火鸡类
        Duck turkeyAdapter = new TurkeyAdapter(turkey);//装进适配器

        System.out.println("----Duck----");
        testDuck(duck);
        System.out.println("----Turkey----");
        testDuck(turkeyAdapter);
    }

    static void testDuck(Duck duck){
        duck.quack();
        duck.fly();
    }
}
```

在上面的例子中：

- Turkey类就是英国插座，就是被适配的接口
- TurkeyAdapter是适配器，将被适配的接口转换为目标接口

- Duck是接口是被客户调用的接口，需要将被适配类转换为该接口

经过适配器，将客户类和被适配类解耦了

## 7.3 适配器模式

适配器模式将一个类的接口转换成客户期望的另一个接口。适配器让原来接口不兼容的类可以合作。

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Adapter3.png)

适配器模式包含两种模式：对象适配器和类适配器

上面的类图是对象适配器模式，下面的图是类适配器

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Adapter4.png)

在类适配器中，需要使用到多继承，这在java语言中无法使用。

适配器类同时继承目标接口类（Duck）和被适配类（Turkey），Turkey类中没有和Duck类中相同的方法，适配器把Duck类的方法调用转到Turkey类的方法调用上

对象适配器：

- 使用组合，不但可以适配被适配类，还可以适配子类
- 适配器中的代码和被适配子类组合工作（子类传入适配器中就能工作，适配器类中有一个被适配对象）

类适配器：

- 只能针对一个特定的被适配类，但是不用实现整个被适配类，需要时还可以覆盖被适配者的行为
- 适配器类中不需要被适配对象

装饰者模式：不改变接口，只添加责任

适配器模式：转化一个接口为另一个接口

外观模式：使接口更简单



## 7.4 场景

在家里看电影，有很多的步骤需要做：

- 打开屏幕
- 拉上窗帘
- 调暗灯光
- 设置功放
- 。。。
- 开始放电影

这一系列操作都需要在看电影时进行操作，并且看完之后还要挨个再关掉。这无疑是一个很大的工作量

如果使用外观模式，我们可以用一个外观接口来实现这些操作

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Facade1.png)

## 7.5 测试

外观类构建

```java
public class HomeTheaterFacade {
    Amplifier amp;
    Tuner tuner;
    Screen screen;
    StreamingPlayer player;
    Projector projector;
    TheaterLights lights;
    PopcornPopper popper;

    public HomeTheaterFacade(Amplifier amp, Tuner tuner, Screen screen, StreamingPlayer player, Projector projector, TheaterLights lights, PopcornPopper popper) {
        this.amp = amp;
        this.tuner = tuner;
        this.screen = screen;
        this.player = player;
        this.projector = projector;
        this.lights = lights;
        this.popper = popper;
    }

    public void watchMovie(String movie){//使用外观进行一系列调用
        System.out.println("Get ready to watch a movie...");
        popper.on();
        popper.pop();
        lights.dim();
        screen.down();
        projector.on();
        projector.wideScreenMode();
        amp.on();
        amp.setStreamingPlayer();
        amp.setSurroundSound();
        amp.setVolume();
        player.on();
        player.play();
    }
}

```

对于客户来说，只需要调用 watchMovie 方法就可以完成这一系列操作

## 7.6 外观模式

我们创建一个外观类，这个类把属于某些子系统的一套复杂的类简化和统一。外观模式让我们避免客户和子系统之间的紧耦合。

外观模式：为子系统中的一组接口提供了统一接口。外观定义了一个更高级别的接口，使得子系统更容易使用。

外观模式和普通封装的区别：外观模式没有封装子系统类，它只是给一系列接口提供一个接口，如果要直接使用子系统的一些功能也是可以直接使用的。

**设计原则7：**最少知识原则：减少对象之间的交互，尽可能地降低类与类之间地耦合

降低耦合地办法，调用原则：

- 对象自身
- 作为参数传给方法的对象
- 该方法创建或实例化的任何对象
- 对象的任何组件：HAS-A的实例
- 前三个：不要调用从其他方法返回的对象的方法

```java
public float getTemp(){//违反最少知道原则
    Thermoetor thermoetor = station.getThermemter();//获得温度计
    return thermoetor.getTemperature();//获得温度
}

public float getTemp(){
    return station.getTemperature();//在气象站station中添加直接获取温度的方法
}
```

```java
public class Car {
    Engine engine;//这个类是一个组件，可以调用它的方法
    public Car(){
        
    }
    
    public void start(Key key){
        Doors doors = new Doors();//可以调用新建对象的方法
        boolean authorized = key.turns();//可以调用从参数传进来对象的方法
        if(authorized){
            engine.start();//可以调用对象内组件方法
            updateDashboardDisplay();//可以调用对象内局部方法
            doors.look();//可以调用创建的对象和实例化的方法
        }
    }
    
    public void updateDashboardDisplay(){
        
    }
}
```

# 8 模板方法模式

# 9 迭代器和组合模式

# 10 状态模式

# 11 代理模式

## 11.1 场景和代码

现在有一个场景，糖果公司有一个机器用来监控糖果机上的状态：包括糖果机位置、糖果数量



监视器编码

```java
public class GumballMachine {
    int count;//糖果数量
    String location;//糖果机位置

    public GumballMachine(String location, int count) {
        this.location = location;
        this.count = count;
    }

    public String getLocation() {
        return location;
    }

    public GumballMachine(int count) {
        this.count = count;
    }
}
```

糖果监视器类，检索机器的位置、库存，以及机器状态，并生成报告

```java
public class GumballMonitor {
    GumballMachine machine;

    public GumballMonitor(GumballMachine machine) {//将糖果机作为参数传入
        this.machine = machine;
    }

    public void report(){
        System.out.println("Gumball Machine: " + machine.getLocation());
        System.out.println("Current inventory: " + machine.getCount() + " gumballs");
        System.out.println("Current state: " + machine.getState());
    }
}
```

上面的调用过程只能在同一台机器上完成，如果我需要坐在办公室监控远程的糖果机呢？

可以在本地使用一个代理对象，这个代理对象代理远程的糖果机实体，然后这个代理对象可以通过网络获取远程的糖果机信息

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Proxy1.png)

客户对象看起来像是在做远程方法调用，实际上只是调用了本地堆中的“代理”对象上的方法，代理对象处理所有网络通信的底层细节

在Java中可以使用RMI来进行远程调用

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Proxy2.png)

## 11.2 使用RMI实现代理

先让GumballMachine编程服务，供客户远程调用

1. 为GumballMachine创建一个远程接口。接口提供一组可以远程调用的方法
2. 确定接口的所有返回类型都是可序列化的
3. 在一个具体类中实现该接口

```java
public interface GumballMachineRemote extends Remote {
    public int getCount() throws RemoteException;
    public String getLocation() throws RemoteException;
    public State getState() throws RemoteException;//所有返回的类型都必须是可序列化类型
}
```

修正State接口的可序列化

```java
public interface State extends Serializable {//实现序列化接口，使得可序列化，在网络上可以传送
    public void insertQuarter();
    public void ejectQuarter();
    public void turnCrank();
    public void dispense();
}
```

```java
public class NoQuarterState implements State{

    private static final long serialVersionUID = 2L;//序列化id。
    // 在每个State实例中，我们给GumballMachine实例变量加上serialVersionUID以及transient关键字
    transient GumballMachine gumballMachine;
}
```

确保GumballMachine中有GumballMachineRemote类中的所有方法

```java
public class GumballMachine extends UnicastRemoteObject implements GumballMachineRemote{//继承给了这个类成为远程服务的能力，然后实现接口
    
    private static final long serialVersionUID = 2L;
    int count;//糖果数量
    String location;//糖果机位置
    State state;//糖果机状态

    public GumballMachine(String location, int count) throws RemoteException{//抛出异常
        this.location = location;
        this.count = count;
    }

    //下面原来的代码不用改
    public String getLocation() {
        return location;
    }

    public int getCount() {
        return count;
    }

    public State getState(){
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
```

作一个测试确保注册完成

```java
public class Main {
    public static void main(String[] args) {
        int count = 0;
        if(args.length < 2){
            System.out.println("GumballMachine <name><inventory>");
            System.exit(1);
        }
        count = Integer.parseInt(args[1]);
        GumballMachine gumballMachine = null;
        try {
            gumballMachine = new GumballMachine(args[0],count);
            Naming.rebind("//"+args[0]+"/gumballmachine",gumballMachine);//参数args[0]是远程的地址，糖果机地址
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

简单修改GumballMonitor类

```java
public class GumballMonitor {
    GumballMachineRemote machine;//修改为依赖远程调用类

    public GumballMonitor(GumballMachineRemote machine) {//将糖果机作为参数传入
        this.machine = machine;
    }

    public void report(){
        try{
            System.out.println("Gumball Machine: " + machine.getLocation());
            System.out.println("Current inventory: " + machine.getCount() + " gumballs");
            System.out.println("Current state: " + machine.getState());
        }catch(RemoteException e){
            e.printStackTrace();
        }
    }
}
```

调用测试，调用前要先注册桩

```java
public class Main {
    public static void main(String[] args) {
        String location = "rmi://localhost/gumballmachine";//这些地址已经注册过，现在直接拿来使用

        GumballMonitor monitor = null;
        try {
            //从参数中得知位置和服务名称，然后在rmiregistry中查找该名称
            GumballMachineRemote machineRemote = (GumballMachineRemote) Naming.lookup(location);//查找，给每个远程机器一个代理
            monitor = new GumballMonitor(machineRemote);//得到了远程机器代理，我们创建一个新的GumballMonitor，把要监视的机器传给它
            System.out.println(monitor);
        } catch (Exception e) {
            e.printStackTrace();
        }

        monitor.report();
    }
}
```

## 11.3 代理模式

**代理模式**为另一个对象提供一个替身或占位符来控制对这个对象的访问。

几种代理模式：

- 远程代理控制对远程对象的访问
- 虚拟代理控制对创建开销大的资源的访问
- 保护代理基于权限控制对资源的访问

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Proxy3.png)

首先，我们有一个Subject，它为RealSubject和Proxy提供了接口。因为实现了和RealSubject一样的接口，Proxy可以在RealSubject出现的任何地方取代它。

RealSubject是真正做事的对象，它是被Proxy代表和控制访问的对象

Proxy持有RealSubject的引用。

## 11.4 场景2

如果我们编写一个应用，需要从某个网站下载图片并显示在封面上，可能因为网络的原因下载很慢。在没下载的时候显示一个图片不可用，开始下载了就要开始显示图像。并且在加载图片的时候，不能让整个应用都挂起，没加载出来也是可以使用应用的。

这里可以用虚拟代理来进行大文件的代理

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Proxy4.png)

ImageProxy如何工作：

1. ImageProxy首先创建了一个ImageIcon，然后开始从网络URL上加载图像
2. 在加载过程中显示“正在加载中”
3. 当图像加载完毕，ImageProxy把所有方法调用委托给ImageIcon，这些方法包括paintIcon()、getWidth()和getHeight()
4. 如果用户请求新的图像，我们就创建新的代理

## 11.5 测试

图像显示代理类

```java
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class ImageProxy implements Icon {//实现接口
    volatile ImageIcon imageIcon;//真正的需要显示的图像
    final URL imageURL;
    Thread retrievalThread;
    boolean retrieving = false;

    public ImageProxy(URL imageURL) {//资源的url传进来
        this.imageURL = imageURL;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if(imageIcon != null){
            imageIcon.paintIcon(c,g,x,y);
        }else{
            g.drawString("Loading album cover...",x+300,y+190);
            if(!retrieving){
                retrieving = true;

                retrievalThread = new Thread(new Runnable() {//真正的加载图像
                    @Override
                    public void run() {
                        setImageIcon(new ImageIcon(imageURL,"Album Cover"));
                        c.repaint();
                    }
                });
                retrievalThread.start();
            }
        }
    }

    @Override
    public int getIconWidth() {
        if (imageIcon != null){
            return imageIcon.getIconWidth();
        }else{
            return 800;
        }
    }

    @Override
    public int getIconHeight() {
        if(imageIcon!=null){
            return imageIcon.getIconHeight();
        }else{
            return 600;
        }
    }

    synchronized void setImageIcon(ImageIcon imageIcon){//同步写
        this.imageIcon = imageIcon;
    }
}
```

我们做了什么？

1. 创建了ImageProxy类来显示图像，这个类对ImageIcon类进行了包装，使它可以异步的加载图像
2. 在某个时间点图像从服务器返回，ImageIcon被完整的实例化
3. ImageIcon被创建后，下次调用paintIcon()时，代理就委托给ImageIcon

代理模式和装饰器模式的区别：

- 通过代理，将客户的请求拦截下来，在其中做一些事（请求分发，提供保护等）。
- 装饰器模式为类添加了行了，代理模式对类进行了控制

## 11.6 Java动态代理

![](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/designpattern/HeadFirst/Proxy5.png)

Java为用户创建了Proxy类，所以需要有办法告诉Proxy类要做什么。客户控制Proxy的代码只能放在InvokecationHandle中，它来响应代理的任何方法的调用。



场景：约会配对

Person接口

```java
public interface Person {
    String getName();
    String getGender();
    String getInterests();
    int getGeekRating();
    
    void setName(String name);
    void setGender(String gender);
    void setInterests(String interests);
    void setGeekRating(int rating);
}
```

```java
public class PersonImpl implements Person{
    String name;
    String gender;
    String interests;
    int rating;
    int ratingCount = 0;
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getGender() {
        return this.gender;
    }

    @Override
    public String getInterests() {
        return this.interests;
    }

    @Override
    public int getGeekRating() {
        if(ratingCount==0) return 0;
        return (rating / ratingCount);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public void setInterests(String interests) {
        this.interests = interests;
    }

    @Override
    public void setGeekRating(int rating) {
        this.rating+=rating;
        ratingCount++;
    }
}
```

## 11.7 为Person创建动态代理

实现一个动态代理类，客户不能自己修改分值，用户也不能修改别人的数据。

为这两个需求创建两个调用类

```java
public class OwnerInvocationHandler implements InvocationHandler {//需要实现该接口
    Person person;
    
    public OwnerInvocationHandler(Person person){
        this.person = person;
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try{
            if(method.getName().startsWith("get")){//getter
                return method.invoke(person,args);
            }else if(method.getName().equals("setGeekRating")){//不能给自己评分
                throw new IllegalAccessException();
            }else if(method.getName().startsWith("set")){
                return method.invoke(person,args);
            }
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }
        
        return null;
    }
}
```

```java
public class NonOwnerInvocationHandler implements InvocationHandler {
    Person person;

    public NonOwnerInvocationHandler(Person person) {
        this.person = person;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try{
            if(method.getName().startsWith("get")){//getter
                return method.invoke(person,args);
            }else if(method.getName().equals("setGeekRating")){//
                return method.invoke(person,args);
            }else if(method.getName().startsWith("set")){//不能随意设置修改别人的信息
                throw new IllegalAccessException();
            }
        }catch (InvocationTargetException e){
            e.printStackTrace();
        }

        return null;
    }
}
```

下面这两个方法放在测试中

```java

Person getOwnerProxy(Person person){
    return (Person) Proxy.newProxyInstance(
        person.getClass().getClassLoader(), //传入类加载器
        person.getClass().getInterfaces(),//代理需要实现的接口集合
        new OwnerInvocationHandler(person)//真正需要调用的处理器   
    );
}

Person getNonOwnerProxy(Person person){
    return (Person) Proxy.newProxyInstance(
        person.getClass().getClassLoader(), //传入类加载器
        person.getClass().getInterfaces(),//代理需要实现的接口集合
        new NonOwnerInvocationHandler(person)//真正需要调用的处理器   
    );
}
```

测试配对服务

通过不同的代理类，使得对同一个对象的同一个方法的调用出现不同的结果

**动态代理动态在，代码运行前，没有代理类，它是根据需要从你传入的接口集创建的。**

**InvocationHandler**不是一个代理类，它是帮助代理类完成代理的辅助类

```java
public class MatchMakingTest {

    public static void main(String[] args) {
        MatchMakingTest test = new MatchMakingTest();
        test.drive();
    }

    public MatchMakingTest(){
        //初始化人员数据
    }

    public void drive(){
        Person joe = getPersonData("joe");//获得人员数据
        Person ownerProxy = getOwnerProxy(joe);//创建所有者代理
        System.out.println("Name is "+joe.getName());
        ownerProxy.setInterests("bowling go");//调用setter

        try{
            ownerProxy.setGeekRating(10);//因为设置过，自己是不能修改自己的评分的
        }catch (Exception e){
            System.out.println("can't set rating from owner");
        }

    }

    Person getOwnerProxy(Person person){//获得代理类，这个过程都由系统创建
        return (Person) Proxy.newProxyInstance(
                person.getClass().getClassLoader(), //传入类加载器
                person.getClass().getInterfaces(),//代理需要实现的接口集合
                new OwnerInvocationHandler(person)//真正需要调用的处理器
        );
    }

    Person getNonOwnerProxy(Person person){
        return (Person) Proxy.newProxyInstance(
                person.getClass().getClassLoader(), //传入类加载器
                person.getClass().getInterfaces(),//代理需要实现的接口集合
                new NonOwnerInvocationHandler(person)//真正需要调用的处理器
        );
    }

    public Person getPersonData(String name){
        Person person = new PersonImpl();
        person.setName(name);
        return person;
    }
}
```











# 12 复合模式