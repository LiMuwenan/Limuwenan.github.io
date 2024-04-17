[TOC]



# 1 环境配置



## 1.1 下载安卓SDK、Gradle

使用IDEA 2017 2.7时，需要AndoridSKD 24.0.2及以后版本



http://www.androiddevtools.cn/下载SDK Tools，打开SDK Manager管理SDK

[Gradle和Gradle插件对应版本等等](https://developer.android.google.cn/studio/releases/gradle-plugin)

## 1.2 添加系统环境变量



将"\platform-tools"和"\tools"添加到环境变量



## 1.3 新建安卓工程

选择SDK，模版，设置活动、布局名称等



```gradle
// Top-level build file where you can add configuration options common to all sub-projects/modules.
//根目录 build.gradle
buildscript {
    repositories {
        jcenter()
        google()
        maven { url 'file:///D:/Program Files/apache-maven-3.6.3/res'}
        maven { url 'file:///D:/Program Files/gradle-6.7/wrapper/dists'}
        mavenLocal()
        maven { name "Alibaba" ; url "https://maven.aliyun.com/repository/public" }
        maven { name "Bstek" ; url "http://nexus.bsdn.org/content/groups/public/" }
        mavenCentral()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.1.0'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        jcenter()
        google()
        maven { url 'file:///D:/Program Files/apache-maven-3.6.3/res'}
        maven { url 'file:///D:/Program Files/gradle-6.7/wrapper/dists'}
        mavenLocal()
        maven { name "Alibaba" ; url "https://maven.aliyun.com/repository/public" }
        maven { name "Bstek" ; url "http://nexus.bsdn.org/content/groups/public/" }
        mavenCentral()
    }

    buildscript {
        repositories {
            maven { name "Alibaba" ; url 'https://maven.aliyun.com/repository/public' }
            maven { name "Bstek" ; url 'http://nexus.bsdn.org/content/groups/public/' }
            maven { name "M2" ; url 'https://plugins.gradle.org/m2/' }
        }
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

```

```
///gradle/wrapper/properties
#Mon Dec 28 10:00:20 PST 2015
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-4.4-all.zip

```





# 2 View

```java
//设置id属性
android:id="@+id/user";

//设置background属性，设置背景。bg是图片名称，bg先放到mipmap目录中
android:backgroup="@mipmap/bg";
//背景设置为纯色
android:backgroup="#FFF000";

//设置padding属性，设置的是组件内边距
android:padding="16px";
//设置为尺寸资源，需要先定义
android:padding="@dimen/activity_margin";
```

## 2.1 ViewGroup

```java
//ViewGroup是View的子类

//ViewGroup控制组件分布时依赖两个内部类
//ViewGroup.LayoutParams类，控制内边距
//ViewGroup.MarginLayoutParams类，控制外边距
```

### 2.1.2 ViewGroup.LayoutParams类

```java
//设置布局高度
android:layout_height="100dp";

//设置布局高度
android:layout_width="100dp";

//可以使用以下三个常量
FILL_PARENT;//与父容器相同
MATCH_PARENT;//与父容器相同
WRAP_CONTENT;//与自身内容匹配
```

### 2.1.3 ViewGroup.MarginLayoutParams类

```java
//设置上外边距
android:layout_marginTop;

//设置下外边距
android:layout_marginBottom;

//设置做外边距
android:layout_marginLeft;
android:layout_marginStart;

//设置做右边距
android:layout_marginRight;
android:layout_marginEnd;


//可以使用以下三个常量
FILL_PARENT;//与父容器相同
MATCH_PARENT;//与父容器相同
WRAP_CONTENT;//与自身内容匹配
```

## 2.2 UI组件层次结构

![image-20210407151044184](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/android/android-viewgroup.png)

## 3 控制UI界面

### 3.1 使用XML控制UI界面

1. 在Android应用的res/layout目录下编写XML布局文件,名字符合java命名规范

   ```xml
   <?xml version="1.0" encoding="utf-8"?>
   <android.support.constraint.ConstraintLayout
           xmlns:android="http://schemas.android.com/apk/res/android"
           xmlns:tools="http://schemas.android.com/tools"
           xmlns:app="http://schemas.android.com/apk/res-auto"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           tools:context=".MainActivity">
   
       <TextView
               android:layout_width="wrap_content"
               android:layout_height="wrap_content"
               android:text="开心消消乐"
               app:layout_constraintBottom_toBottomOf="parent"
               app:layout_constraintTop_toTopOf="parent" app:layout_constraintHorizontal_bias="0.5"
               app:layout_constraintVertical_bias="0.102" app:layout_constraintStart_toStartOf="parent"
               app:layout_constraintEnd_toEndOf="parent"/>
       <Button
               android:text="开始"
               android:layout_width="wrap_content"
               android:layout_height="wrap_content" android:id="@+id/button" tools:layout_editor_absoluteY="534dp"
               app:layout_constraintStart_toStartOf="parent" app:layout_constraintHorizontal_bias="0.5"
               app:layout_constraintEnd_toEndOf="parent" tools:ignore="MissingConstraints"/>
       <Button
               android:text="退出"
               android:layout_width="wrap_content"
               android:layout_height="wrap_content" android:id="@+id/button2" tools:layout_editor_absoluteY="604dp"
               app:layout_constraintStart_toStartOf="parent" app:layout_constraintHorizontal_bias="0.5"
               app:layout_constraintEnd_toEndOf="parent" tools:ignore="MissingConstraints"/>
   
   </android.support.constraint.ConstraintLayo
   ```

2. 在Activity中使用以下代码显示XML文件中的布局内容

```java
setContentView(R,layout.activity_main);//activity_main就是布局文件的名称
```



### 3.2 在java代码中控制UI界面

```java
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//所有组件代码在这一代码之后写

        //java支持的组件都可以用
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setBackgroundResource(Color.rgb(23,41,00));
        setContentView(frameLayout);

        TextView text1 = new TextView(this);
        text1.setText("开始游戏");
        text1.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        text1.setTextColor(Color.rgb(17,85,114));
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        text1.setLayoutParams(params);

        //设置按钮监听事件
        text1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MainActivity.this).setTitle("系统提示")
                        .setMessage("风险")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Log.i("桌面台球","进入游戏");

                                    }
                                }).setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("桌面台球","进入游戏");
                        finish();
                    }
                }).show();
            }
        });
        frameLayout.addView(text1);
    }


}

```



### 3.3 使用XML和java代码混合控制

```java
//导入图片并设置大小
public class MainActivity extends AppCompatActivity {

    private ImageView[] img = new ImageView[9];//图片数组
    private int imagePath[] = new int[]{//图片路径
        R.mipmap.img01,R.mipmap.img02,R.mipmap.img03,
            R.mipmap.img04,R.mipmap.img05,R.mipmap.img06,
            R.mipmap.img07,R.mipmap.img08,R.mipmap.img09,
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);

        GridLayout layout = (GridLayout) findViewById(R.id.layout);


        for(int i=0;i< imagePath.length;++i)
        {
            img[i] = new ImageView(MainActivity.this);
            img[i].setImageResource(imagePath[i]);
            img[i].setPadding(1,1,1,1);
            ViewGroup.LayoutParams params = new GridLayout.LayoutParams();
            img[i].setLayoutParams(params);
            img[i].setMaxHeight(30);
            img[i].setMaxWidth(30);
            layout.addView(img[i]);//将图片添加到布局管理器
        }
    }


}
```

```xml
<!--网格布局形式-->
<?xml version="1.0" encoding="utf-8"?>
<GridLayout xmlns:android="http://schemas.android.com/apk/res/android"
            android:id="@+id/layout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:columnCount="4"
            android:orientation="horizontal"
            android:rowCount="3">


</GridLayout>
```



### 3.4 开发自定义View

1. 在布局xml文件中添加布局管理器

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
             xmlns:tools="http://schemas.android.com/tools"
            android:id="@+id/layout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
             android:layout_marginBottom="10dp"
             android:layout_marginTop="10dp"
             android:layout_marginEnd="10dp"
             android:layout_marginStart="10dp"
            android:background="#BBFFFF">


</FrameLayout>
```

2. 先写java类，并继承自View类

，包括构造、onDraw方法等

```java
public class RabbitClass extends View {
    public float bitmapX;
    public float bitmapY;

    public RabbitClass(Context context) {
        super(context);
        bitmapX = 50;
        bitmapY = 50;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.mipmap.img04);
        canvas.drawBitmap(bitmap,bitmapX,bitmapY,paint);
        if(bitmap.isRecycled())
        {
            bitmap.recycle();
        }
    }
}
```



3. 在主类中实例化先前的方法，并添加到布局管理器

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);

        FrameLayout framelayout = findViewById(R.id.layout);
        final RabbitClass rabbit = new RabbitClass(this);
        
        
        //触摸事件监听
        rabbit.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                rabbit.bitmapX = event.getX();
                rabbit.bitmapY = event.getY();
                rabbit.invalidate();

                return true;
            }
        });
        framelayout.addView(rabbit);
    }
    
}
```

## 4 布局管理器

### 4.1 相对布局管理器(RelativeLayout)

一些组件相对某一个组件布局

```xml
//android:gravity,定义该布局按什么样式显示，例如：居中显示
//android:ignoreGravity，可以使某个组件忽略前面设定的gravity属性
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
             xmlns:tools="http://schemas.android.com/tools"
            android:id="@+id/layout"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
                android:paddingBottom="16dp"
                android:paddingTop="16dp"
                android:paddingLeft="16dp"
                android:paddingRight="16dp"
                android:gravity="center"
                android:ignoreGravity="@+id/text1"
                tools:context=".MainActivity"
            android:background="#BBFFFF">

    <TextView 
            android:id="@+id/text1"
            android:layout_width="100dp" android:layout_height="100dp"
            android:text="@string/exit"
            android:textSize="50dp"/>

</RelativeLayout>

```

```xml
<!--设定组件相对另一个组件位置-->
android:layout_above="@+id/text1"
android:layout_below=""
android:layout_toLeftOf=""
android:layout_toRightOf=""
<!--设定与父容器对齐-->
 android:layout_alignParentBottom="true"
android:layout_alignParentTop="false"
android:layout_alignParentRight=""
android:layout_alignParentLeft=""
<!--设定与某一个组件对齐-->
 android:layout_alignBottom="@+id/text1"
android:layout_alignTop=""
android:layout_alignRight=""
android:layout_alignLeft=""
<!--设置组件位于布局管理器的居中位置-->
android:layout_centerInParent="true"
android:layout_centerHorizontal=""
android:layout_centerVertical=""
```



### 4.2 线性布局管理器(LinerLayout)

```xml
<!--从上到下排列-->
android:orientation="vertival"
<!--从左到右排列-->
android:orientation="horizontal"

//android:gravity,定义该布局按什么样式显示，例如：居中显示

//layout_weight属性，表示组件占父容器剩余空间的比例，默认为0
//也就是按比例将剩余的空间分配给组件，增加自适应性
```



### 4.3 帧布局管理器(FrameLayout)

先进入的组件在底层，后来的组件往上层叠。

整个布局管理器以左上角为(0,0)点。

用这个布局管理器可以实现拖动效果

```xml
//andorid:foreground,设置一个前景图像
//android:foregroundGravity,设置前景图像的位置，设置过显示位置，图片就会以本来的大小显示
```



### 4.4 表格布局管理器(TableLayout)

可以被网格布局管理器替代

```xml
//<TableRow></TableRow>用该标记添加表格行
<TableRow android:padding="200dp">
	<TextView/>
</TableRow>
<!--让第二列隐藏-->
android:collapseColumns="1"
<!--允许被拉伸填充剩余空间-->
android:stretchColumns="1"
<!--允许被收缩-->
android:shrinkColumns="1"
```



### 4.5 绝对布局管理器(AbsoluteLayout)

缺乏自适应性，已经弃用

### 4.6 网格布局管理器(GridLayout)

可以替代表格布局管理器。可以跨行跨列显示，超出容器的组件会自动换行

```xml
//android:columnCount,定义列数
//android:rowCount，定义行数
//android:orientation，定义排列方式

//android:layout_column="",定义组件在网格第几列
//android:layout_columnSpan="",定义组件横跨几列
//android:layout_columnWeight="",定义子组件在水平方向的权重
//android:layout_gravity=" ",定义子组件以什么方式占据网格空间
//android:layout_row="",定义组件在网格第几行
//android:layout_rowSpan="",定义组件横跨几行
//android:layout_rowWeight="",定义子组件在垂直方向的权重
//span的使用需要配合gravity使用
```



### 4.7 约束布局管理器(ConstraintLayout)

### 4.8 布局管理器的嵌套

#### 4.8.1 布局管理器嵌套原则

- 根布局管理器必须包含xmlns属性；
- 在一个布局文件中，最多只能有一个根布局管理器
- 不能嵌套太深，否则影响性能

# 5 基本UI组件

## 5.1 文本框组件(TextView)

```xml
        <!--text:文本内容，推荐使用字符串资源指定,在strings.xml中定义，直接在这里引用-->
        <!--text="@string/app-name"-->
        <!--layout_width:layout_height:布局形式-->
        <!--textSize:设置文本大小，sp为推荐字号单位-->
        <!--textColor:设置文本颜色-->
        <!--background:设置文本框底色、图片-->
        <!--singleLine:单行文本框-->
        <TextView
                android:text="Hello world"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="18sp"
                android:textColor="#FF0000"
                android:background="#000000"
                android:singleLine="true"
                />
```

## 5.2 编辑框组件(EditText)

```xml
        <!--text:文本内容，推荐使用字符串资源指定,在strings.xml中定义，直接在这里引用-->
        <!--text="@string/app-name"-->
        <!--layout_width:layout_height:布局形式-->
        <!--textSize:设置文本大小，sp为推荐字号单位-->
        <!--textColor:设置文本颜色-->
        <!--background:设置文本框底色-->
        <!--hint:输入提示信息-->
		<!--inputType:设置输入类型，textPassword：密码框-->
		<!--drawable:在编辑框某位置设置图片资源-->
		<!--drawablePadding:设置图片与文字的距离-->
		<!--lines:可以显示的文字行数，超过行数文字向上滚动-->
        <EditText
                android:text="Hello world"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="18sp"
                android:textColor="#FF0000"
                android:background="#000000"
                android:hint="密码"
                android:inputType="textPassword"
                android:drawableLeft="@mipmap/img01"
                android:drawablePadding="18dp"
                android:lines="2"  
                />
```

## 5.3 普通按钮组件(Button)

```xml
        <!--TextView支持的属性，Button也支持-->
        <!--text:文本内容，推荐使用字符串资源指定,在strings.xml中定义，直接在这里引用-->
        <!--text="@string/app-name"-->
        <!--layout_width:layout_height:布局形式-->
        <!--textSize:设置文本大小，sp为推荐字号单位-->
        <!--background:设置文本框底色、底图-->
        <Button
                android:text="Hello world"
                android:layout_height="wrap_content"
                android:layout_width="wrap_content"
                android:textSize="20sp"
                android:background="#FFFFFF"
                />
```

### 5.3.1 单击事件监听器

```java
//方法1
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);

        //匿名内部类实现单击事件监听器
        Button btn = (Button)findViewById(R.id.button1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //单击事件
                //单击事件中使用的变量需要final修饰
            }
        });
    }
}

//方法2
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);
    }

    //通过onClick属性实现
    //1.在Activity中编写一个包含View类型参数的方法

    //2.在android:onClick属性指定为步骤(1)中的方法名
    //android:onClick="myClick"
    public void myClick(View v)
    {
        //单击事件
    }
}
```

## 5.4 图片按钮(ImageButton)

图片按钮没有text属性，可以直接在图片上添加文字

```xml
        <!--layout_width:layout_height:布局形式-->
        <!--src:设置图片资源路径-->
        <!--background:#0000设置背景透明-->
        <!--background:设置文本框底色、底图-->
        <ImageButton
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:src="@mipmap/img01"
                android:background="#0000"/>
```

## 5.5 单选按钮(RadioButton)

```xml
        <!--layout_width:layout_height:布局形式-->
        <!--text:文本-->
        <!--checked:默认选中-->
        <!--多个单选按钮用RadioGroup标签包围，表示一组，一组中只能有一个按钮被选中-->
        <RadioGroup android:id="@+id/radio">
                <RadioButton
                        android:id="@+id/man"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="男"
                />
                <RadioButton
                        android:id="@+id/woman"
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:text="女"
                />
        </RadioGroup>
```

### 5.5.1 单选按钮监听器

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);

        RadioGroup rg = (RadioGroup) findViewById(R.id.radio);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                //这里的checkedId代表被选中的按钮
                RadioButton r = (RadioButton)findViewById(checkedId);
                EditText et = (EditText) findViewById(R.id.edit1);
                et.setText(r.getText());
            }
        });

    }
}
```

```java
 //用此方法可以遍历单选按钮
        for(int i=0;i<rg.getChildCount();++i)
        {
            //获取当前单选按钮
            RadioButton r = (RadioButton) rg.getChildAt(i);
            //判断是否被选中
            if(r.isChecked())
            {
                
            }
        }
```

## 5.6 复选框(CheckBox)

```xml
        <CheckBox
                android:id="@+id/ET"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="体育"
                />
        <CheckBox
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="美术"
        />
        <CheckBox
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="数学"
        />
```

### 5.6.1 复选框监听

```java
public class MainActivity extends AppCompatActivity {
    CheckBox cb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);

        //定义为全局变量，局部变量用final
        cb = (CheckBox) findViewById(R.id.ET);
        //选中监听
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //cb是否被选中
                if(cb.isChecked())
                {

                }
            }
        });
    }
}
```

## 5.7 日期选择器(DataPicker)

```xml
        <DatePicker 
                android:id="@+id/dp"
                android:layout_width="wrap_content" 
                android:layout_height="wrap_content"
                />
```

```java
public class MainActivity extends AppCompatActivity {
    int year,month,day;
    DatePicker datePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);

        //日期选择器对象
        datePicker = (DatePicker) findViewById(R.id.dp);
        //获得日期
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        //初始化日期选择器
        //OnDateChangedListener监听改变
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MainActivity.this.year = year;
                MainActivity.this.month = monthOfYear;
                MainActivity.this.day = dayOfMonth;
            }
        });
    }
}
```

## 5.8 时间选择器(TimePicker)

```xml
        <TimePicker
                android:id="@+id/timepicker"
                android:layout_width="wrap_content" android:layout_height="wrap_content"
                />
```

### 5.8.1 时间改变监听

```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);
        TimePicker tp = (TimePicker) findViewById(R.id.timepicker);
        //默认为12小时显示，使用以下方法改变为24小时显示
        tp.setIs24HourView(true);
        //时间改变监听
        tp.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //显示改变后的时间
                String str = hourOfDay+"时"+minute+"分";
                Toast.makeText(MainActivity.this,str,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

## 5.9 计时器(Chronomrter)

```xml
        <!--format：设置格式和文字-->
        <!--%s,以时分秒格式-->
        <Chronometer
                android:id="@+id/chronometer"
                android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:format="已用时间:%s"
                />
```

### 5.9.1 计时器监听

```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);
        /*
        * setBase()设置计时器的起始时间，参数是长整型时间
        * setFormat()设置时间显示格式
        * start()指定开始计时
        * stop()指定停止计时
        * setOnChronometerTickListener()为计时器绑定事件监听器，当计时器改变时触发该监听器
        * */
        final Chronometer ch = (Chronometer) findViewById(R.id.chronometer);
        //起始时间设置为当前系统时间
        ch.setBase(SystemClock.elapsedRealtime());
        ch.setFormat("%s");
        ch.start();
        ch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                //当前系统时间减去起始时间
                if(SystemClock.elapsedRealtime()-ch.getBase()>=10000)
                {
                    ch.stop();
                }
            }
        });
    }
}
```

# 6 高级UI组件

## 6.1 进度条组件(ProgressBar)

未学完

## 6.2 拖动条组件(SeekBar)

## 6.3 星级评分组件(RatingBar)

## 6.4 图像视图组件(ImageView)

```xml
        <!--src:需要显示的图片，放在drawable路径下-->
        <!--scaleType:设置图片的缩放类型-->
        <!--adjustViewBounds:是否调整自己的边界保证显示的长宽比，可以添加maxWidth,maxHeight-->
        <ImageView
                android:id="@+id/iv"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:scaleType="center"
                android:adjustViewBounds="true"
                />
```

## 6.5 图像切换器(ImageSwitcher)

```xml
        <ImageSwitcher
                android:id="@+id/is"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                />
```

```java
//点击图片切换下一张
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);

        ImageSwitcher is = (ImageSwitcher) findViewById(R.id.is);
        //设置图片切换效果
        is.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_out));
        is.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_in));
        is.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            //为图像切换器指定图像
            public View makeView() {
                ImageView imageView = new ImageView((MainActivity.this));
                imageView.setImageResource(R.drawable.switch1);
                return imageView;
            }
        });
        //单击事件监听，切换图片
        is.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ImageSwitcher)v).setImageResource(R.drawable.switch2);
            }
        });
    }
}
```

```java
public class MainActivity extends AppCompatActivity {
    private int[] arrayPicture = new int[]{R.drawable.switch1,R.drawable.switch2,R.drawable.switch3};
    private ImageSwitcher imageSwitcher;
    private int index;//记录显示图片在数组中的位置
    private float touchDown,touchUp;//记录手指按下和抬起的坐标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);

        imageSwitcher = (ImageSwitcher) findViewById(R.id.is);
        //添加图片
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(arrayPicture[index]);
                return imageView;
            }
        });
        imageSwitcher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //判断是否按下
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    //获取按下抬起坐标
                    touchDown=event.getX();
                    return true;
                }else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    touchUp = event.getX();
                    
                    imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_out));
                    imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_in));
                    //从左向右滑,这段语句放在外面APP会崩溃
                    if(touchUp-touchDown>100)
                    {
                        index++;
                        imageSwitcher.setImageResource(arrayPicture[index]);
                    }else if(touchDown-touchUp>100)
                    {
                        index--;
                        imageSwitcher.setImageResource(arrayPicture[index]);
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
```

## 6.6 网格视图(GridView)

```xml
        <!--numColumns:设置网格视图列数-->
		<!--图片资源由适配器指定-->
        <GridView
                android:id="@+id/gv"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:numColumns="3"
                />
```

```java
//ArrayAdapter数据适配器，将数据的多个值包装成列表项
//SimpleAdapter简单适配器，将List集合的多个值包装成列表项
//SimpleCursoAdapter，将数据库数据以列表的形式展现出来
//BaseAdapter，定制适配器

public class MainActivity extends AppCompatActivity {
    private int[] arrayPicture = new int[]{R.drawable.switch1,R.drawable.switch2,R.drawable.switch3,R.drawable.switch4,R.drawable.switch5,R.drawable.switch6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);

        GridView gridView = (GridView) findViewById(R.id.grid);
        List<Map<String,Object>> listitem = new ArrayList<Map<String,Object>>();
        //将数组图片加入到List容器中
        for(int i=0;i< arrayPicture.length;++i)
        {
            Map<String,Object> map=  new HashMap<String,Object>();
            map.put("image",arrayPicture[i]);
            listitem.add(map);
        }
        /*
        * @param:上下文环境
        * @param:需要添加的列表容器
        * @param:布局文件,控制图片显示
        * @param:字符串数组，map的key
        * @param:组件id
        * */
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,listitem,R.layout.cell,new String[] {"image"},new int[]{R.id.image});
        //设置适配器
        gridView.setAdapter(simpleAdapter);
    }
}
```

## 6.7 下拉列表框

```xml
        <!--entries:数组资源，需要先定义，用来提供列表框选项-->
        <Spinner
                android:id="@+id/spinner"
                android:entries="@array/ctype"
                android:layout_width="wrap_content" android:layout_height="wrap_content"
                 />
```
### 6.7.1 使用布局文件导入选项数组
```xml
//values/arrays.xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string-array name="ctype">
        <item>全部</item>
        <item>电影</item>
        <item>图书</item>
        <item>游戏</item>
    </string-array>
</resources>
```

### 6.7.2 使用适配器导入选项数组

```java
public class MainActivity extends AppCompatActivity {
    private int[] arrayPicture = new int[]{R.drawable.switch1,R.drawable.switch2,R.drawable.switch3,R.drawable.switch4,R.drawable.switch5,R.drawable.switch6};
    private String[] ctype = new String[]{"全部","美术","体育"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);
        //创建一个列表适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,ctype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        //指定适配器
        spinner.setAdapter(adapter);
    }
}
```

### 6.7.3 选择列表项监听

```java
        //选择列表项监听器
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //获取某位置的选项
                String result = parent.getItemAtPosition(position).toString();
                Toast.makeText(MainActivity.this,result,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
```

## 6.8 列表视图(ListView)

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".activity.MainActivity">

        <ListView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:id="@+id/mainListView"
        />

</LinearLayout>
```

### 6.8.1 设置ListView子部件的样式

**ArrayAdapter**

```xml
<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<Button
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="@dimen/ListButtonMargin"
        >

</Button>
```

### 6.8.2 使用适配器导入选项数组

```java
package cn.edu.xidian.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import cn.edu.xidian.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.mainListView);
        String[] str = {"上海","北京","天津","江苏","河南","西藏","新疆","湖南","湖北"};
        List<String> listdata = new ArrayList<String>();
        listdata.add("上海");
        listdata.add("北京");
        listdata.add("天津");
        listdata.add("江苏");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_button,listdata);//listdata和str均可
        listView.setAdapter(arrayAdapter);
    }
}

```

### 6.8.3 使用BaseAdapter适配器

实体类

```java
/**
 * Created by Jay on 2015/9/18 0018.
 */
public class Animal {
    private String aName;
    private String aSpeak;
    private int aIcon;

    public Animal() {
    }

    public Animal(String aName, String aSpeak, int aIcon) {
        this.aName = aName;
        this.aSpeak = aSpeak;
        this.aIcon = aIcon;
    }

    public String getaName() {
        return aName;
    }

    public String getaSpeak() {
        return aSpeak;
    }

    public int getaIcon() {
        return aIcon;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public void setaSpeak(String aSpeak) {
        this.aSpeak = aSpeak;
    }

    public void setaIcon(int aIcon) {
        this.aIcon = aIcon;
    }
}
```

Adapter

```java
/**
 * Created by Jay on 2015/9/18 0018.
 */
public class AnimalAdapter extends BaseAdapter {

    private LinkedList<Animal> mData;
    private Context mContext;

    public AnimalAdapter(LinkedList<Animal> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mData.size();
    }
    @Override
    public Object getItem(int position) {
        return null;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list_animal,parent,false);
        ImageView img_icon = (ImageView) convertView.findViewById(R.id.img_icon);
        TextView txt_aName = (TextView) convertView.findViewById(R.id.txt_aName);
        TextView txt_aSpeak = (TextView) convertView.findViewById(R.id.txt_aSpeak);
        img_icon.setBackgroundResource(mData.get(position).getaIcon());
        txt_aName.setText(mData.get(position).getaName());
        txt_aSpeak.setText(mData.get(position).getaSpeak());
        return convertView;
    }
}
```

MainActivity

```java
public class MainActivity extends AppCompatActivity {

    private List<Animal> mData = null;
    private Context mContext;
    private AnimalAdapter mAdapter = null;
    private ListView list_animal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        list_animal = (ListView) findViewById(R.id.list_animal);
        mData = new LinkedList<Animal>();
        mData.add(new Animal("狗说", "你是狗么?", R.mipmap.ic_icon_dog));
        mData.add(new Animal("牛说", "你是牛么?", R.mipmap.ic_icon_cow));
        mData.add(new Animal("鸭说", "你是鸭么?", R.mipmap.ic_icon_duck));
        mData.add(new Animal("鱼说", "你是鱼么?", R.mipmap.ic_icon_fish));
        mData.add(new Animal("马说", "你是马么?", R.mipmap.ic_icon_horse));
        mAdapter = new AnimalAdapter((LinkedList<Animal>) mData, mContext);
        list_animal.setAdapter(mAdapter);
    }

}
```



## 6.9 滚动视图(ScrollView)

```xml
        <!--垂直滚动栏-->
        <ScrollView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                >
                <TextView
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:text="sjfjiwhfibashbhvkabshdbeuihfuaihfidgsayvbyyrafgdfahuiefieygf"
                        android:textSize="140sp"
                        />
        </ScrollView>
        <!--水平滚动栏-->
        <HorizontalScrollView
                android:layout_width="match_parent"
                android:layout_height="match_parent"
        >
                <TextView
                        android:layout_width="match_parent"
                        android:layout_height="match_parent"
                        android:text="sjfjiwhfibashbhvkabshdbeuihfuaihfidgsayvbyyrafgdfahuiefieygf"
                        android:textSize="140sp"
                />
        </HorizontalScrollView>
```

### 6.9.1 使用java文件创建滚动视图

```java
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        LinearLayout ll2 = new LinearLayout(MainActivity.this);

        ll2.setOrientation(LinearLayout.VERTICAL);
        //1.使用构造方法ScrollView(Context c)创建一个滚动视图
        ScrollView scrollView = new ScrollView(MainActivity.this);
        //2.将滚动视图添加到布局管理器中
        ll.addView(scrollView);
        //3.应用addView()方法添加组件到滚动视图中
        scrollView.addView(ll2);

        ImageView imageView = new ImageView(MainActivity.this);
        imageView.setImageResource(R.drawable.switch1);
        ll2.addView(imageView);

        TextView  textView = new TextView(MainActivity.this);
        textView.setText("123");
        ll2.addView(textView);
    }
}
```

## 6.10 选项卡(Tab)

```xml
<?xml version="1.0" encoding="utf-8"?>
<TabHost
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@android:id/tabhost"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        >

        <!--1.在布局文件中添加TabHost、TabWidget、TabContent-->
        <LinearLayout android:layout_width="match_parent" android:layout_height="match_parent"
                      android:orientation="vertical"
                      >
                <TabWidget android:layout_width="match_parent" android:layout_height="wrap_content"
                           android:id="@android:id/tabs"></TabWidget>
                <FrameLayout android:layout_width="match_parent" android:layout_height="match_parent"
                             android:id="@android:id/tabcontent"
                             ></FrameLayout>
        </LinearLayout>
        <!--2.编写各标签页的XML布局文件-->
        
        <!--3.获取并初始化TabHost组件-->
        <!--4.为TabHost对象添加标签页-->
</TabHost>
```

```java
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);
        //        <!--3.获取并初始化TabHost组件-->
        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
        //初始化
        tabHost.setup();
        LayoutInflater inflater = LayoutInflater.from(this);
        //        <!--4.为TabHost对象添加标签页-->
        inflater.inflate(R.layout.tab1,tabHost.getTabContentView());
        inflater.inflate(R.layout.tab2,tabHost.getTabContentView());
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("精选表情").setContent(R.id.left));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("投稿表情").setContent(R.id.right));

    }
}
```

# 7 基本程序单元Activity

## 7.1 Activity概述

- 运行状态：正常运行
- 暂停状态：按返回键弹出退出对话框时
- 停止状态：按下退出确认键后
- 销毁状态：强制停止一个APP

![activity](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/android/android-activity.jpg)

## 7.2 创建和配置Activity

```java
//MainActivity
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);
    }
}
```

```java
/*
* 1.创建Activity继承自Activity
* 2.重写需要的回调方法
* 3.设置要显示的视图
* */
public class ActivitySecond extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab1);
    }
}
```

```xml
<!--在AndroidManifest.xml中配置新建的Activity-->
<activity android:name=".ActivitySecond" android:label="详细">
</activity>
```

或者直接通过idea的向导直接创建Activity

## 7.3 启动和关闭Activity

### 7.3.1 入口Activity

```xml
<!--需要在AndroidManifest.xml中配置-->
        <activity android:name=".MainActivity">
            <intent-filter>
                <!--响应动作名，将该Activity定义为入口-->
                <action android:name="android.intent.action.MAIN"/>
				<!--在什么情况下该动作会被响应-->
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
```

### 7.3.2 启动其他Activity

[使用另一种方法启动Activity](# 8.1.1 Component name)

新的Activity需要在AndroidManifest.xml中配置

```xml
<activity android:name=".ActivitySec"> </activity>
```

```java
//MainActivity
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);

        Button btn = (Button) findViewById(R.id.button1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动Activity需要一个Intent对象
                Intent intent = new Intent(MainActivity.this,ActivitySecond.class);
                startActivity(intent);
            }
        });
    }
}
```

### 7.3.3 关闭Activity

```java
public class ActivitySecond extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab1);

        Button btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
```

### 7.3.4 刷新当前Activity

```java
onCreate(null);
```

## 7.4 使用Bundle在多个Activity之间交换数据

在一个Activity中启动另一个Activity时，需要使用Intent传递消息，而Intent本身不具备保存数据的能力，这个时候需要使用Bundle<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/android/android-Bundle.jpg" alt="Bundle" style="zoom:50% ; " />

Bundle是键值对组合，需要通过Key来访问信息

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/android/android-BundleKey.jpg" alt="BundleKey" style="zoom: 50%;" />

### 7.4.1 在另一个Activity中显示前面输入的信息

1. 使用putXXX()方法将数据写进Bundle
2. 使用putExtras()方法将Bundle绑定到Intent
3. 使用startActivity()启动新Activity

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@android:id/tabhost"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:orientation="vertical"
        >
        <EditText
                android:id="@+id/et1"
                android:layout_width="match_parent" android:layout_height="wrap_content"
                android:hint="地址"/>
        <EditText
                android:id="@+id/et2"
                android:layout_width="match_parent" android:layout_height="wrap_content"
                android:hint="姓名"/>
        <EditText
                android:id="@+id/et3"
                android:layout_width="match_parent" android:layout_height="wrap_content"
                android:hint="电话"/>

        <Button android:layout_width="wrap_content" android:layout_height="wrap_content"
                android:id="@+id/button1"
                android:text="保存"
                />
</LinearLayout>
```

```java
//MainActivity
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);

        Button btn = (Button) findViewById(R.id.button1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = ((EditText)findViewById(R.id.et1)).getText().toString();
                String name = ((EditText)findViewById(R.id.et2)).getText().toString();
                String phone = ((EditText)findViewById(R.id.et3)).getText().toString();
                //启动Activity需要一个Intent对象
                Intent intent = new Intent(MainActivity.this,ActivitySecond.class);
                //给Bundle输入数据
                Bundle bundle = new Bundle();
                bundle.putCharSequence("address",address);
                bundle.putCharSequence("name",name);
                bundle.putCharSequence("phone",phone);
                //将Bundle绑定到intent
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
```

```java
public class ActivitySecond extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.tab1);
        //获取intent
        Intent intent = getIntent();
        //构造bundle
        Bundle bundle = intent.getExtras();
        //从Bundle获取数据
        String address = bundle.getString("address");
        String name = bundle.getString("name");
        String phone = bundle.getString("phone");

        TextView textView = (TextView) findViewById(R.id.text1);
        textView.setText(address+"\n"+name+"\n"+phone);
    }
}
```

### 7.4.2 调用另一个Activity并返回结果

```java
//需要返回结果的时候需要使用如下方法启动Activity
public void startActivityForResult(Intent intent, int RequestCode);
//请求码用于标识请求来源
startActivityForResult(intent,0x007);
```

```java
//主activity中
//重写该方法，请求码与结果码一致进行后续操作
//     * @param:  requestCode: 标识请求源
//     * @param:  resultCode: 标识从哪个activity返回
	@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0x00&&resultCode==0x00)
        {
            Bundle bundle=data.getExtras();
            int imageId = bundle.getInt("image");
            ImageView imageView = (ImageView)findViewById(R.id.main_image);
            imageView.setImageResource(imageId);
        }
    }
```

```java
//第二个activity中
Intent intent  = getIntent();
Bundle bundle = new Bundle();
bundle.putInt("id",id);
intent.putExtras(bundle);
//设置请求码
setResult(0x00,intent);
finish();
```

### 7.4.3 Bundle传输自定义对象

```java
package cn.edu.xidian;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @version: V0.01
 * @author: ligen
 * @packageName: cn.edu.xidian
 * @className: UserItem
 * @description: 该类用来描述网站信息，并实现Parcelable接口用于bundle传值
 * @Date: 2021-5-8
 **/
public class UserItem implements Parcelable {

    public String website,username,pwd;


    //无参构造
    public UserItem(){
        website = "";
        username = "";
        pwd = "";
    }

    //有参构造
    public UserItem(String website, String username, String pwd){
        this.website = website;
        this.username = username;
        this.pwd = pwd;
    }

    /*****实现parcel接口****/
    //有参构造
    public UserItem(Parcel source){
        website = source.readString();
        username = source.readString();
        pwd = source.readString();
    }
	
    //传输ArrayList<Parcelable>时返回hashcode(),否则返回0
    @Override
    public int describeContents() {
        return this.hashCode();
    }

    /**
     * @author:  ligen
     * @methodsName: writeToParcel
     * @description: 往parcel中写数据
     * @param:
     * @return: void
     * @Date: 2021-5-8
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(website);
        dest.writeString(username);
        dest.writeString(pwd);
    }

    /**
     * @author:  ligen
     * @methodsName: Creator
     * @description: 反序列化
     * @param:
     * @Date: 2021-5-8
     */
    public static final Creator<UserItem> CREATOR = new Creator<UserItem>() {
        /**
         * @author:  ligen
         * @methodsName: createFromParcel
         * @description: 从序列化对象中获取对象
         * @param:
         * @return: UserItem
         * @Date: 2021-5-8
         */
        @Override
        public UserItem createFromParcel(Parcel source) {
            return new UserItem(source);
        }

        /**
         * @author:  ligen
         * @methodsName: newArray
         * @description: 创建指定长度的原始对象数组
         * @param:
         * @return: UserItem[]
         * @Date: 2021-5-8
         */
        @Override
        public UserItem[] newArray(int size) {
            return new UserItem[size];
        }
    };

}

```



## 7.5 Fragment

fragment就像选项卡，可以将多个fragment嵌入到一个activity中使用，类似于微信的主页面，用下面的标签进行多个fragment切换。

### 7.5.1 创建fragment

```java
public class ListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //fragment为布局文件名
        View view = inflater.inflate(R.layout.fragment,container,false);
        return view;

    }
}
```

### 7.5.2 在Activity中添加Fragment

#### 直接在布局文件中添加

1. 创建两个Fragment，分别有java类和布局文件

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#382732">
        <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
                  android:text="1234567890fwiefu" android:textSize="30sp"
                  />
</LinearLayout>
```

```java
package com.example.kaixinxiaoxiaole;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ListFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //fragment为布局文件名
        View view = inflater.inflate(R.layout.fragment_list,container,false);
        return view;

    }
}

```

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#382732">
    <TextView android:layout_width="wrap_content" android:layout_height="wrap_content"
              android:text="slkjfiejiojfaiojdf" android:textSize="30sp"
    />
</LinearLayout>
```

```java
package com.example.kaixinxiaoxiaole;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //fragment为布局文件名
        View view = inflater.inflate(R.layout.fragment_detail,container,false);
        return view;

    }
}

```

2. 主类中显示主布局文件

```java
//MainActivity
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);
    }
}
```

3. 在主布局文件中添加两个fragment

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@android:id/tabhost"
        android:layout_height="match_parent"
        android:layout_width="match_parent"
        android:orientation="vertical"
        xmlns:tools="http://schemas.android.com/tools"
        tools:context=".MainActivity"
        >
        <fragment
                android:layout_width="match_parent" android:layout_height="wrap_content"
                android:id="@+id/frag1" android:name="com.example.kaixinxiaoxiaole.ListFragment"
                android:layout_weight="1"/>

        <fragment
                android:layout_width="match_parent" android:layout_height="wrap_content"
                android:id="@+id/frag2" android:name="com.example.kaixinxiaoxiaole.DetailFragment"
                android:layout_weight="2"/>

</LinearLayout>
```

#### 动态运行Fragment

```java
//MainActivity
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);
        //1.实例化一个自己写好的fragment类
        ListFragment listFragment = new ListFragment();
        //28版本已经弃用
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        //第一个参数指定fragment要放在哪个容器中，第二个参数使我们要添加的fragment
        ft.add(android.R.id.content,listFragment);
        ft.commit();
    }
}
```

# 8 Intent

![Intent](https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/android/android-Intent.jpg)

作用：

- Intent开启另一个Activity
- 开启一个service(后台下载等)
- 广播

## 8.1 Intent对象属性

### 8.1.1 Component name

[使用前面介绍的方法启动Activity](# 7.3.2 启动其他Activity) 

```java
//MainActivity
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_layout);

        Button btn = (Button) findViewById(R.id.button1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动Activity需要一个Intent对象
                Intent intent = new Intent();
                //指定包名，指定包名.类名
                ComponentName componentName = new ComponentName("cn.edu.xidian","cn.edu.xidian.ActivitySec");
                intent.setComponent(componentName);
                startActivity(intent);
            }
        });
    }
}
```

```java
package cn.edu.xidian;

import android.app.*;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
//继承Activity
public class ActivitySec extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);
    }
}

```



### 8.1.2 Action和Data

[Android API](https://developer.android.google.cn/reference/android/content/Intent)

Action：用来指定将要执行的动作

Data：用来指定将要执行的数据

```java
package cn.edu.xidian;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_mail = (Button)findViewById(R.id.btn_mail);
        Button btn_phone = (Button)findViewById(R.id.btn_phone);

        //添加监听器
        btn_phone.setOnClickListener(listener);
        btn_mail.setOnClickListener(listener);

    }

    //两个按钮的监听事件基本相同，可以实例化一个监听对象
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            Button btn = (Button) v;
            //判断是哪个按钮被单击
            switch(btn.getId())
            {
                case R.id.btn_phone:
                    //表示调用拨号面板,相关常量可以查看手册
                    intent.setAction(intent.ACTION_DIAL);
                    //设置要拨打的电话号码
                    intent.setData(Uri.parse("tel:631"));
                    startActivity(intent);
                    break;

                case R.id.btn_mail:
                    //表示调用短信界面
                    intent.setAction(intent.ACTION_SENDTO);
                    //指定发送短信的号码
                    intent.setData(Uri.parse("smsto:631"));
                    //设置默认发送短信的内容
                    intent.putExtra("sms_body","Welcome to Android");
                    startActivity(intent);
                    break;
            }
        }
    };

}
```

### 8.1.3 Action和Category

返回系统桌面需要这两个属性

```java
package cn.edu.xidian;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_MAIN);
                intent.addCategory(intent.CATEGORY_HOME);
                startActivity(intent);
            }
        });

    }

}
```



### 8.1.4 Extras和Flags

Extras有常用的两个方法putExtra()和getExtra()，主要用于Activity间使用Bundle传递信息,见[章节 7.4](# 7.4.1 在另一个Activity中显示前面输入的信息)



Flags属性用于指定如何启动Activity，

例如每次进入程序都进入主页

```java
package cn.edu.xidian;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button)findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ActivitySec.class);
                //当前Activity不在历史栈中保留，用户离开就会被销毁
                intent.setFlags(intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        });

    }

}
```

## 8.2 Intent种类

### 8.2.1 显式Intent

1. 创建Intent对象
2. 指定目标组件名称
3. 启动目标组件

```java
Intent intent = new Intent(MainActivity.this,ActivitySecond.class);
startActivity(intent);
```

### 8.2.2 隐式Intent

创建Intent对象

指定action、category或者data

Android系统自动匹配目标组件

```java
Intent intent = new Intent();
intent.setAction(intent.ACTION_MAIN);
intent.addCategory(intent.CATEGORY_HOME);
startActivity(intent);
```

## 8.3 Intent过滤

主要用于隐式Intent。当我们需要打开一个Activity时，并不直接打开，而是设置一个过滤器，让能通过过滤器的属性应用到将要打开的Activity上。

通过<intent-filter>标记在AndroidManifest.xml中设置

```xml
<intent-filter>
    <!--组件所能响应的动作-->
	<action  .../>
    <!--组件以哪种方式响应-->
	<category  .../>
    <!--向action提供要操作的数据-->
	<data  .../>
</intent-filter>
```

# 9 Android调试

## 9.1 DDMS工具

可以在sdk文件中打开ddms.bat

左下是过滤器，用来筛选调试信息，右下是调试信息

## 9.2 输出日志信息

```java
//Log类继承自Object
//Log.e(),错误日志
Log.e(TAG,"I'm error");
//Log.w(),警告日志
Log.w(TAG,"I'm warning");
//Log.i(),普通日志
Log.i(TAG,"I'm common");
//Log.e(),调试日志
Log.d(TAG,"I'm debug");
//Log.e(),冗余日志
Log.v(TAG,"I'm v");
```

# 10 事件处理

## 10.1 基于监听的事件处理

特定事件一般使用监听，回调时间用在通用性的事件上。

### 10.1.1 单击事件监听

[为UI组件绑定监听事件](# 5 基本UI组件)

### 10.1.2 长按事件监听

```java
package cn.edu.xidian;

import android.content.*;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn);

        btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //长按事件注册到菜单中
                registerForContextMenu(v);
                openContextMenu(v);
                return true;
            }
        });
    }
    //弹出菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add("收藏");
        menu.add("举报");
    }
}
```

### 10.1.3 触摸事件监听

触摸事件与单击事件的区别：

- 先执行触摸事件再执行单击事件
- 如果触摸事件返回true则完全消耗掉此时间，单击事件就不会触发

```java
imageSwitcher.setOnTouchListener(new View.OnTouchListener() {
            @Override
    		//MotionEvent event保存触摸的位置和时间
            public boolean onTouch(View v, MotionEvent event) {
                //判断是否按下
                if(event.getAction()==MotionEvent.ACTION_DOWN)
                {
                    //获取按下抬起坐标
                    touchDown=event.getX();
                    return true;
                }else if(event.getAction()==MotionEvent.ACTION_UP)
                {
                    touchUp = event.getX();
                    
                    imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_out));
                    imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(MainActivity.this,android.R.anim.fade_in));
                    //从左向右滑,这段语句放在外面APP会崩溃
                    if(touchUp-touchDown>100)
                    {
                        index++;
                        imageSwitcher.setImageResource(arrayPicture[index]);
                    }else if(touchDown-touchUp>100)
                    {
                        index--;
                        imageSwitcher.setImageResource(arrayPicture[index]);
                    }
                    return true;
                }
                return false;
            }
        });
```



## 10.2 基于回调的事件处理

```java
    @Override
	//触摸事件，例如触摸屏幕
    public boolean onTouchEvent(MotionEvent event) {
        //回调事件
        return super.onTouchEvent(event);
    }

    @Override
	//按键按下，例如音量键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //回调事件
        return super.onKeyDown(keyCode, event);
    }
```

## 10.3 物理按键事件处理

```java
//onKeyDown(),按键按下
//onKeyUp(),按键抬起
//onKeyLongPress(),长按
//KEYCODE_VOLUME_UP，音量上键
//KEYCODE_VOLUME_UP，音量下键
//KEYCODE_POWER，电源键
//KEYCODE_BACK，返回键
//KEYCODE_HOME，主屏键
//KEYCODE_MENU，菜单键
```

```java
    @Override
    //keycode代表按钮常量
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_VOLUME_UP)
        {
            Toast.makeText(this, "VOLUMN UP", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
```

## 10.4 手势检测

```java
//GestureDetector类，传入GestureDetector.OnGestureListener接口，需要重写以下方法
//onDown(),触摸按下
//onFling(),触摸拖动
//OnLongPress(),触摸长按
//onScroll(),
//onShowPress(),
//onSingleTapUp(),触摸轻击
```

未学完

# 11 Android资源

Android资源保存在/res目录下

## 11.1 字符串资源

```xml
<!--/res/values/strings.xml-->
<!--定义-->
<resources>
    <string name="app_name">My Application</string>
    <string name="id">将会显示的字符</string>
</resources>
```

```xml
<!--xml中使用-->
<Button android:layout_width="wrap_content" android:layout_height="wrap_content"
        android:text="@string/id"
        android:id="@+id/btn"/>
```

```java
//java中使用
TextView textView = (TextView) findViewById(R.id.btn);
textView.setText(getResources().getString(R.string.id));
```



## 11.2 颜色资源

```xml
<!--/res/values/colors.xml-->
<!--定义-->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!--# A R G B-->
    <color name="colorPrimary">#6200EE</color>
    <color name="colorPrimaryDark">#3700B3</color>
    <color name="colorAccent">#03DAC5</color>
</resources>
```

```xml
<!--xml中使用-->
    <TextView android:layout_width="wrap_content" 
              android:layout_height="wrap_content"
              android:id="@+id/btn"
              android:textColor="@color/colorPrimary"
              android:background="@color/colorAccent"
    />
```

```java
//java中使用
TextView textView = (TextView) findViewById(R.id.btn);
textView.setBackground(getDrawable(R.color.colorAccent));
```



## 11.3 尺寸资源

```xml
<!--dp,设备独立像素，每个设备显示的大小不同，主要用来设置边距和组件大小-->
<!--sp,可伸缩像素，字体大小会随手机设置变化，主要用来设置字体-->
```

```xml
<!--/res/values/dimens.xml-->
<!--定义-->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <dimen name = "hello">16dp</dimen>
</resources>
```

```xml
<!--xml中使用-->    
<TextView android:layout_width="match_parent"
              android:layout_height="wrap_content"
              android:text="@string/id"
              android:textSize="@dimen/hello"
              android:id="@+id/btn"
/>
```

```java
//java中使用
TextView textView = (TextView) findViewById(R.id.btn);
textView.setTextSize(getResources().getDimension(R.dimen.hello));
```



## 11.4 布局资源

```xml
<!--/res/layout-->
<!--xml中使用-->
<!--在一个布局中引入另一个布局-->
<include layout="@layout/detail"></include>
```

```java
//java中使用
setContentView(R.layout.activity_main);
```

## 11.5 数组资源

```xml
<!--/res/values/arrays.xml-->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <!--可保存颜色、字符串、尺寸等资源-->
    <array name="array"> </array>
    <!--整型数组-->
    <integer-array name="int_array"> </integer-array>
    <!--字符串数组-->
    <string-array name="str_array">
        <item>活着就是为了改变世界</item>
        <item>尝试很重要</item>
        <item>求知若饥，虚心若愚</item>
    </string-array>
</resources>
```

```xml
<!--xml中使用-->
<!--数组资源在ListView标签的entries中使用-->
<ListView android:layout_height="match_parent" android:layout_width="match_parent"
          android:entries="@array/str_array" />
```

```java
//java中使用
String[] str = getResources().getStringArray(R.array.str_array);
int[] arr = getResources().getIntArray(R.array.int_array);

//为每一个组件添加数组时，先定义一个整型数组用来保存组件id，然后循环应用
```



## 11.6 图像资源

### 11.6.1 .9.png格式

xxx.9.png格式图片，可以设定图片的哪些区域可缩放，哪些不可缩放

在Android SDK tools文件夹下有一个draw9patch.bat程序，启动后就可以绘制该类型图片。

需要先导入一张图片

```xml
<!--/res/drawable/-->
<!--xml中使用-->
android:background="@drawable/test"
```

### 11.6.2 StateListDrawable资源

设置图片按下抬起等时不同的效果，需要再xml文件中设置

```xml
<!--/res/drawable/xxx.xml-->
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
    <!--得到焦点-->
    <!--也可以在drawable属性下设置一个图片-->
    <item android:state_focused="true" android:color="#f60"/>
    <!--失去焦点-->
    <item android:state_focused="false" android:color="#0a0"/>
</selector>
```

```xml
<!--xml中使用-->
android:textColor="@drawable/state"
```

### 11.6.3 mipmap

一般是应用图标

```xml
<!--/res/mipmap/-->
<!--xml中使用-->
android:src="@mipmap/ic_launcher"
```



## 11.7 主题和样式资源

可以在AppTheme中设置系统自带主题

也可以在styles.xml定义主题资源

```xml
<!--/res/values/styles.xml-->
<resources>
    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
</resources>
```

```xml
<!--在AndroidManifest.xml中使用-->
```

```java
//java中使用
setTheme(R.style.theme);
setContentView(R.layout.activity_main);
```

```xml
<!--/res/values/styles.xml-->
<resources>
    <style name="text">
        <item name="android:textSize">30sp</item>
        <item name="android:textColor">#06F</item>
    </style>
    <!--样式继承:父子不同时采用子样式-->
    <style name="context" parent="text">
        <item name="android:textSize">30sp</item>
        <item name="android:textColor">#06F</item>
    </style>
</resources>
```



## 11.8 菜单资源

右上角菜单

```xml
<!--/res/menu/xxx.xml-->
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item android:id="@+id/message" android:title="消息"> </item>
    <item android:id="@+id/mail" android:title="邮件"> </item>
    <item android:id="@+id/help" android:title="帮助"> </item>
    <item android:id="@+id/feedback" android:title="反馈"> </item>
</menu>
```

```java
package cn.edu.xidian;

import android.content.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //重写显示菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //对菜单的选项进行操作
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //switch判断id
        switch(item.getItemId())
        {
            case R.id.feedback:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
```

上下文菜单（长按菜单）

```java
package cn.edu.xidian;

import android.content.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //为文本框注册上下文菜单
        text = (TextView) findViewById(R.id.tv);
        registerForContextMenu(text);
    }

    //重写onCreateContextMenu方法，添加一个上下文菜单
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater  = new MenuInflater(this);
        menuInflater.inflate(R.menu.menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    //重写onContextItemSelected方法，用来指定菜单项被选择时的操作

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.mail:
                break;
        }
        return super.onContextItemSelected(item);
    }
}
```



# 12 Action Bar

app顶部的一些常用操作都会放置在Action Bar中

## 12.1 显示和隐藏Action Bar

静态显示与隐藏

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="cn.edu.xidian">

    <application
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/AppTheme">
            <!--通过主题属性中的.NoActionBar类型隐藏-->
            <!--只想让某个Activity不显示就是其中使用theme属性-->
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>

                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>
        <activity android:name=".ActivitySec"> </activity>
    </application>

</manifest>
```

动态显示与隐藏

```java
package cn.edu.xidian;

import android.content.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_show = findViewById(R.id.btn_show);
        Button btn_hide = findViewById(R.id.btn_hide);
        //获取ActionBar对象
        final ActionBar actionBar = getSupportActionBar();

        btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置显示
                actionBar.show();
            }
        });
        btn_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置隐藏
                actionBar.hide();
            }
        });
    }
}
```

## 12.2 Action Item

显示在Action Bar上的具有功能的按钮

```xml
<!--/res/menu/xxx.xml-->
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android" xmlns:app="http://schemas.android.com/apk/res-auto">
    <!--icon属性指定图标-->
    <!--showAsAction设定显示在ActionBar上还是溢出菜单中-->
    <item android:id="@+id/search" android:icon="@drawable/search"
          android:title="search"   app:showAsAction="always"
    > </item>
    <item android:id="@+id/message" android:title="消息"> </item>
    <item android:id="@+id/mail" android:title="邮件"> </item>
    <item android:id="@+id/help" android:title="帮助"> </item>
    <item android:id="@+id/feedback" android:title="反馈"> </item>
</menu>
```

```java
package cn.edu.xidian;

import android.content.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
```

## 12.3 Action View

显示在Action Bar上的搜索栏等等

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android" xmlns:app="http://schemas.android.com/apk/res-auto">
    <!--showAsAction设定显示在ActionBar上还是溢出菜单中-->
    <!--actionViewClass设定一些固定的ActionBar按钮功能-->
    <item android:id="@+id/search" android:icon="@drawable/search"
          android:title="search"   app:showAsAction="always"
          app:actionViewClass="android.widget.SearchView"
    > </item>
    <item android:id="@+id/add" android:title="添加" android:icon="@drawable/add"
      app:showAsAction="ifRoom" app:actionLayout="@layout/activity_add"/>
</menu>
```

# 13 消息、通知和广播

## 13.1 Toast消息提示框

```java
Toast.makeText(MainActivity.this, "显示的内容", Toast.LENGTH_SHORT).show();
```

## 13.2 AlertDialog实现对话框

```java
//四种形式，好好学习
package cn.edu.xidian;

import android.content.*;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.*;
import android.view.accessibility.AccessibilityManager;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);
        Button btn4 = findViewById(R.id.btn4);

        btn1.setOnClickListener(ls);
        btn2.setOnClickListener(ls);
        btn3.setOnClickListener(ls);
        btn4.setOnClickListener(ls);

    }
    View.OnClickListener ls = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            Button btn = (Button)v;
            switch (btn.getId()){
                //显示带确定取消按钮的对话框
                case R.id.btn1:
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    //设置图标资源
                    alertDialog.setIcon(R.drawable.search);
                    //设置标题
                    alertDialog.setTitle("li gen: ");
                    //指定内容
                    alertDialog.setMessage("活着就是为了改变世界");
                    //第一个参数用来设置是什么按钮，第二个参数是文本，第三个参数是监听器
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "取消了", Toast.LENGTH_SHORT).show();
                        }
                    });//取消按钮
                    //第一个参数用来设置是什么按钮，第二个参数是文本，第三个参数是监听器
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "确定了", Toast.LENGTH_SHORT).show();
                        }
                    });//确定按钮
                    alertDialog.show();
                    break;
                //显示带列表项对话框
                case R.id.btn2:
                    final String[] items = new String[]{"活着就是为了改变世界","尝试很重要"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    //设置图标资源
                    builder.setIcon(R.drawable.search);
                    //设置标题
                    builder.setTitle("请选择");
                    //指定内容
                    //第一个参数也可以使用数组文件，R.array.xxx
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        //which代表选择的哪一个
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "你选择了："+items[which], Toast.LENGTH_SHORT).show();
                        }
                    });
                    //创建并显示对话框
                    builder.create().show();
                    break;
                //显示单选列表框对话框
                case R.id.btn3:
                    final String[] names = new String[]{"李根","文丹"};
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    builder1.setIcon(R.drawable.search);
                    builder1.setTitle("请选择");
                    //第二个参数是默认选中第一个
                    builder1.setSingleChoiceItems(names, 0, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(MainActivity.this, "你选择的是:"+names[which], Toast.LENGTH_SHORT).show();
                        }
                    });
                    //添加确定按钮
                    builder1.setPositiveButton("确定",null);
                    builder1.create().show();
                    break;
                //设置多选对话框
                case R.id.btn4:
                    final String[] choice= new String[]{"李根","文丹","何朝阳","张月","文乐"};
                    final boolean[] checked = new boolean[]{false,true,false,false,false};//记录各项状态
                    AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this);
                    builder2.setIcon(R.drawable.search);
                    builder2.setTitle("请选择");
                    //第二个参数是默认选中第一个
                    builder2.setMultiChoiceItems(choice, checked, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            checked[which]=isChecked;
                        }
                    });
                    //添加确定按钮
                    builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //获取选中的选项
                            String result = "";//记录结果的字符串
                            for(int i=0;i<checked.length;++i)
                            {
                                if(checked[i])
                                {
                                    result+=choice[i]+'、';
                                }
                            }
                            if(!"".equals(result))
                            {
                                Toast.makeText(MainActivity.this,result, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder2.create().show();
                    break;
            }
        }
    };

}
```

## 13.3 Notification状态栏

未学完

## 13.4 BroadcastReceiver

## 13.5 AlarmManager设置闹钟

# 14 图形图像处理

未学完

## 14.1 画笔和画布

```java
//Paint类，为画笔
//Canvas类，为画布
```

## 14.2 绘制几何图形

## 14.3 绘制文本

## 14.4 绘制图片

## 14.5 绘制路径

## 14.6 逐帧动画

## 14.7 补间动画

# 15 多媒体应用

## 15.1 播放音频

### 15.1.1 MediaPlayer

使用MediaPlayer可以播放以下格式音频：

- mp3
- ogg
- 3gp
- wav

缺点：延迟时间长，不支持同时播放多个音频

```java
//资源文件保存在/res/raw/中，资源名称英文小写
package cn.edu.xidian;

import android.app.*;
import android.content.*;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.text.*;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //方法一，通过create方法装载一个音频文件
        mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.her);//创建一个MediaPlayer对象，并装在要播放的音频
        //方法二，通过无参构造方法装载不同的音频文件

        Button btn1 = findViewById(R.id.btn1);
        Button btn2 = findViewById(R.id.btn2);
        Button btn3 = findViewById(R.id.btn3);

        btn1.setOnClickListener(ls);
        btn2.setOnClickListener(ls);
        btn3.setOnClickListener(ls);

    }

    View.OnClickListener ls  =  new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(((Button)v).getId())
            {
                case R.id.btn1:
                    //播放
                    mediaPlayer.start();
                    break;
                case R.id.btn2:
                    //暂停
                    mediaPlayer.pause();
                    break;

                case R.id.btn3:
                    //终止
                    mediaPlayer.stop();
                    //重新装载，再点启动就能继续播放，否则终止后不能再播放
                    mediaPlayer = MediaPlayer.create(MainActivity.this,R.raw.her);//创建一个MediaPlayer对象，并装在要播放的音频
                    break;
            }
        }
    };
}
//        mediaPlayer.release();//释放资源
```

### 15.1.2 SoundPool

可以改善上面两个缺点，但是只能播放短音频（提示音等），管理多个短促音

## 15.2 播放视频

### 15.2.1 VideoView

常用视频格式：

- mp4
- 3gp



### 15.2.2 MediaPlayer和SurfaceView

## 15.3 控制摄像头

### 15.3.1 拍照

### 15.3.2 录制视频

# 16 数据存储

## 16.1 Shared Preference存储

安卓提供的最简便的存储信息功能

文件保存格式为xml

存储路径为--data/data/软件包名/shared_prefs/xxx.xml

使用SharedPerference存储数据步骤：

1. 获取SharedPreferences对象
   - getSharedPreerences(String name, int mode)//存储的文件名称、存储权限
   - getPreferences(int mode)
2. 获得SharedPreferences.Editor对象
   - SharedPreferences.edit()
3. 向SharedPreferences.Editor对象中添加数据
   - putBoolean()
   - putString()
   - putInt()
4. 提交数据
   - edit.commit()

提取数据的步骤：

1. 获取SharedPreferences对象

   - getSharedPreerences(String name, int mode)//存储的文件名称、存储权限
   - getPreferences(int mode)

2. 使用SharedPreferences类提供的getXXX()方法获取数据

   - getBoolean()
   - getString()
   - getInt()

   ```java
   //登陆跳转，并保存密码
   package cn.edu.xidian;
   
   import android.app.*;
   import android.content.*;
   import android.media.MediaPlayer;
   import android.provider.MediaStore;
   import android.text.*;
   import android.view.*;
   import android.widget.*;
   import androidx.appcompat.app.*;
   import androidx.appcompat.app.AppCompatActivity;
   import android.os.Bundle;
   
   public class MainActivity extends AppCompatActivity {
       String ligen = "ligen", gen = "gen";
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_main);
   
           final EditText et_username = findViewById(R.id.ETausername);
           final EditText et_password = findViewById(R.id.ETpass);
           Button btn_login = findViewById(R.id.BTN_login);
   
   
           //获取Shared Preference对象
           final SharedPreferences sharedPreferences = getSharedPreferences("xidian",MODE_PRIVATE);
           //实现自动登录功能
           String username = sharedPreferences.getString("username",null);//获取帐号信息
           String password = sharedPreferences.getString("password",null);//获取密码
           if(username!=null && password!=null)
           {
               if(username.equals(ligen)&&password.equals(gen))
               {
                   Intent intent = new Intent(MainActivity.this,ActivitySec.class);
                   startActivity(intent);
               }
           }else{//实现手动登录并保存帐号密码
               btn_login.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       //获取输入的帐号密码
                       String in_username = et_username.getText().toString();
                       String in_password = et_password.getText().toString();
                       //保存帐号密码
                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       if(in_username.equals(ligen)&&in_password.equals(gen))
                       {
                           editor.putString("username",in_username);
                           editor.putString("password",in_password);
                           editor.commit();
                           Intent intent = new Intent(MainActivity.this,ActivitySec.class);
                           startActivity(intent);
                       }else{
                           Toast.makeText(MainActivity.this, "用户名或者密码错误，请检查重试", Toast.LENGTH_SHORT).show();
                       }
                   }
               });
           }
       }
   }
   ```

   

## 16.2 文件存储

通过java的io流读取磁盘上的文件

### 16.2.1 内部存储

```java
//内部存储一般保存在data/data/包名/
package cn.edu.xidian;

import android.app.*;
import android.content.*;
import android.media.MediaPlayer;
import android.provider.MediaStore;
import android.text.*;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    byte[] buffer = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et_message = findViewById(R.id.ETmessage);
        Button btn_save = findViewById(R.id.BTN_save);
        Button btn_cancel = findViewById(R.id.BTN_cancel);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//保存备忘信息
                FileOutputStream fos = null;//声明文件输出流，从app输出
                String text = et_message.getText().toString();//获取到备忘信息
                try {
                    fos = openFileOutput("memo",MODE_PRIVATE);//获得文件输出流对象，文件名，读取格式
                    fos.write(text.getBytes());//保存备忘信息
                    fos.flush();//清除缓存

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fos!=null){
                        try {
                            fos.close();//关闭输出流
                            Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        //读取保存的备忘信息
        FileInputStream fis = null;//声明文件输入流对象
        try {
            fis = openFileInput("memo");
            buffer = new byte[fis.available()];//实例化字节数组
            fis.read(buffer);//从输入流中读取数据
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
                if(fis!=null){
                try {
                    fis.close();//关闭输入流对象
                    String data = new String(buffer);//把字节数组中的数据转换成字符串
                    et_message.setText(data);//显示读取的内容
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//取消则退出
                finish();
            }
        });

    }
}
```

### 16.2.32 外部存储

读写文件步骤：

1. 获取外部存储目录

   - Environment.getExternalStorageDirectory()

2. 读、写外部存储空间中的文件

   - FileInputStream,读
   - FileOutputStream,写

```xml
   <!--读写外部存储的权限-->
   <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
   <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

```java
//外部存储可以是sd卡或者内置存储的一部分分区，storage文件夹或者mnt文件夹
package cn.edu.xidian;

import android.app.*;
import android.content.*;
import android.media.MediaPlayer;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.*;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {
    byte[] buffer = null;
    File file;//声明一个文件对象，用来储存外部文件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText et_message = findViewById(R.id.ETmessage);
        Button btn_save = findViewById(R.id.BTN_save);
        Button btn_cancel = findViewById(R.id.BTN_cancel);
        Button btn_load = findViewById(R.id.BTN_load);
        file = new File(Environment.getExternalStorageDirectory(),"beiwang.txt");//实例化文件对象，外部存储根目录，文件名称
        btn_load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读取保存的备忘信息
                FileInputStream fis = null;//声明文件输入流对象
                try {
                    fis = new FileInputStream(file);//声明输入流
                    buffer = new byte[fis.available()];//实例化字节数组
                    fis.read(buffer);//从输入流中读取数据
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fis!=null){
                        try {
                            fis.close();//关闭输入流对象
                            String data = new String(buffer);//把字节数组中的数据转换成字符串
                            et_message.setText(data);//显示读取的内容
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//保存备忘信息
                FileOutputStream fos = null;//声明文件输出流，从app输出
                String text = et_message.getText().toString();//获取到备忘信息
                try {
                    fos = new FileOutputStream(file);//声明输出流
                    fos.write(text.getBytes());//保存备忘信息
                    fos.flush();//清除缓存

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if(fos!=null){
                        try {
                            fos.close();//关闭输出流
                            Toast.makeText(MainActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//取消则退出
                finish();
            }
        });

    }
}
```



## 16.3 数据库存储

安卓开发中使用sqlite3数据库，（应该是个本地数据库）

### 16.3.1 使用sqlite3操作数据库

### 16.3.2 使用java代码操作数据库

1. 创建数据库
   - openOrCreateDatabase()
   - SQLiteOpenHelper类
2. 操作数据库
   - 插入insert()
   - 删除delete()
   - 更新update()
   - 查询query()

Android系统下的SQLite不具有创建和管理数据库的接口，通过java代码操作数据库需要创建一个SQLiteOpenHelper类的子类来处理数据库所有的操作。

插入、删除、更新可以使用SQLiteDatabase的execSQL(String str)来进行操作，也可以使用insert(),delete(),update()

查询使用db.rawQuery()和db.Query()

游标Cursor是数据库系统为用户开设的一个数据缓冲区，存放SQL语句查询的执行结果

常用的一些方法：

- getCount():获得总的数据项数
- isFirst():判断是否是第一条记录
- isLast()
- moveToFirst():移动到第一条记录
- moveToLast()
- move(int offset):移动到指定记录
- moveToNext():移动到下一条记录
- moveToPrevious():移动到上一条记录
- getColumnIndexOrThrow(String columnName):根据列名获得列索引
- getInt(int columnIndex):获得指定列索引的jint类型值

```java
//Helper类中加入这个方法禁止sqlite生成.db-shm和.db-wal文件    
@Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //禁用WAL
        db.disableWriteAheadLogging();
    }
```

```java
//SQLiteHelper类
package cn.edu.xidian;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    /**
     * @param: String name :数据库名称，"test.db"
     */
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建一个名称为user的表到数据库中
        String sql = "create table user(id integer primary key autoincrement,username text not null,userpwd text not null)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //对数据库进行变更的时候，增加表的字段，修改表的结构等
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //禁用WAL
        db.disableWriteAheadLogging();
    }
}

```

```java
//主activity
package cn.edu.xidian;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etUserpass;

    DatabaseHelper databaseHelper;
    SQLiteDatabase database;
    Cursor cursor;

    //用来保存从编辑框获取的值
    String uname = null,upwd = null;

    String sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //实例化数据库对象,只建了库，里面还没有表
        databaseHelper = new DatabaseHelper(this,"test.db",null,1);
        //获取一个可写的数据库
        database = databaseHelper.getWritableDatabase();

        etUsername = findViewById(R.id.etUsername);
        etUserpass = findViewById(R.id.etPass);

    }

    public void onBtnClick(View v)
    {
        uname = etUsername.getText().toString();
        upwd = etUserpass.getText().toString();
        switch(v.getId())
        {
            case R.id.btnAdd://插入
                //插入 表名(列名) 数据(数据值)
                sql = "INSERT INTO user(username,userpwd) VALUES('"+ uname +"','"+ upwd +"')";
                database.execSQL(sql);

                uname = null;
                upwd = null;
                Toast.makeText(this, "内容已添加", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnQuery://查询
                sql = "SELECT * FROM user";//查询所有的行
                String[] args = null;

                if(uname!=null&&!uname.equals(""))
                {
                    if(!uname.equals("all"))
                    {
                        //问号的值由rawQuery得第二个参数补齐
                        sql = sql + " WHERE username=?";
                        args = new String[]{uname};

                        if(upwd!=null&&!upwd.equals(""))
                        {
                            sql = sql + " and userpwd=?";
                            args = new String[]{uname,upwd};
                        }
                    }else{
                        args = null;
                    }
                }else{
                    Toast.makeText(this, "请输入要查询的用户名", Toast.LENGTH_SHORT).show();
                }



                //通过sql语句把所有行的保存到了cursor中
                cursor = database.rawQuery(sql,args);
                cursor.moveToFirst();//游标移动到第一个数据

                //保存查询到值并和uname对比
                String name =null;
                String pwd =null;

                //是否在最后一个后面
                while(!cursor.isAfterLast())
                {
                    name = cursor.getString(cursor.getColumnIndex("username"));
                    pwd = cursor.getString(cursor.getColumnIndex("userpwd"));
                    Log.i("SQLITE","用户名："+name+"  密码："+pwd);
                    name  = null;
                    pwd = null;
                    cursor.moveToNext();
                }
                break;
            case R.id.btnDelete://删除
                sql = "DELETE FROM user WHERE username='"+uname+"'";
                database.execSQL(sql);
                etUsername.setText("");
                etUserpass.setText("");
                Toast.makeText(this, "内容已删除", Toast.LENGTH_SHORT).show();

                break;
            case R.id.btnUpdate://更新
                //方法一：
//                sql = "UPDATE user set userpwd = '"+upwd+"' WHERE username = '"+uname+"'";
//                database.execSQL(sql);

                //方法二：替代了sql语句
                ContentValues values = new ContentValues();
                values.put("userpwd",upwd);
                database.update("user",values,"username",new String[]{uname});

                Toast.makeText(this, "内容已更新", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
```



## 16.4 数据共享

# 17 Handler消息处理

因为子线程不能操作主线程中的组件，所以提供Handler机制，由子线程发送消息给Handler，再由Handler发送消息给主线程

```java
//课程中这个演示程序终止了，不知道为什么自己实机演示完成了功能
package cn.edu.xidian;

import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView textView = findViewById(R.id.TV);
        Button btnNext = findViewById(R.id.btnNext);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个新线程
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText("Bye bye");

                    }
                });
                thread.start();//启动线程
            }
        });
    }
}
```

加入Handler机制

```java
package cn.edu.xidian;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final TextView textView = findViewById(R.id.TV);
        Button btnNext = findViewById(R.id.btnNext);
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                //msg保存的是消息码
                super.handleMessage(msg);
                //对发送的消息进行处理,如果是对应消息，就执行逻辑代码
                if(msg.what==0x666)
                {
                    textView.setText("Bye bye");
                }
            }
        };
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建一个新线程
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //传递空消息，设置消息代码
                        handler.sendEmptyMessage(0x666);
                    }
                });
                thread.start();//启动线程
            }
        });
    }
}
```

使用Handler定时

```java
package cn.edu.xidian;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {
    private Integer time;
    private int overTime = 0;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = new Integer(60);
        textView = findViewById(R.id.TV);
        textView.setText(time.toString());
        //启动该handler
        handler.sendEmptyMessage(0x666);
    }
    //创建Handler对象，实现1秒更新一次
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(time-overTime>0)//当前进度大于0
            {
                time--;
                textView.setText(time.toString());
                //每隔一秒发送一次消息
                handler.sendEmptyMessageDelayed(0x666,1000);
            }else{
                Toast.makeText(MainActivity.this, "时间到", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
```



## 17.1 Message

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/android/android-Lopper.jpg" alt="Lopper" style="zoom:67%;" />

一个线程对应一个Looper，一个Looper对应一个MessageQueue，MessageQueue中保存着Message。

，因为这个一个队列，所以满足先进先出的规则。先进入的消息则先由Looper取出给Handler进行处理

Message对象的属性：

- arg1,arg2，整型
- obj Object类型
- replyTo 发送到何处
- what 自定义消息代码

获取Message对象：

- Message.obtain()
- Handler.obtainMessage()

```java
//message应用，整体程序和上面的大差不差
package cn.edu.xidian;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {
    private Integer time;
    private int overTime = 0;
    private TextView textView;
    private Message message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = new Integer(60);
        textView = findViewById(R.id.TV);
        textView.setText(time.toString());
        //启动该handler，Message
        message = Message.obtain();
        message.what=0x666;
        handler.sendEmptyMessage(0x666);
    }
    //创建Handler对象，实现1秒更新一次
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x666)
            {
                message = handler.obtainMessage(0x666);
                if(time-overTime>0)//当前进度大于0
                {
                    time--;
                    textView.setText(time.toString());
                    //每隔一秒发送一次消息
                    handler.sendMessageDelayed(message,1000);
                }else{
                    Toast.makeText(MainActivity.this, "时间到", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };
}
```

## 17.2 Looper

在主线程中，会自动创建Looper对象

子线程中创建Looper对象：

1. 初始化Looper对象
   - prepare()
2. 创建Handler对象
   - new Handler()
3. 启动Looper
   - loop()

```java
//Looper类
package cn.edu.xidian;

import android.os.*;
import android.util.Log;

import java.util.logging.LogRecord;

public class LooperThread extends Thread{
    public Handler handler;
    @Override
    public void run() {
        super.run();
        Looper.prepare();//初始化Looper对象
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i("Looper",String.valueOf(msg.what));
            }
        };
        Message message = handler.obtainMessage();
        message.what=0x666;
        handler.sendMessage(message);
        Looper.loop();//启动Looper
    }
}

```

```java
//主类
package cn.edu.xidian;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        LooperThread thread = new LooperThread();
        thread.start();
    }

}
```

# 18 Service

能够在后台长期运行，没有用户界面的应用程序组件。

可以后台下载文件，后台播放歌曲等等后台操作
<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/android/android-startservice.jpg" alt="startservice" style="zoom:50%;" /><img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/android/android-BoundService.jpg" alt="BoundService" style="zoom:50%;" />



started service：service需要等待activity通知才能启动。

bound service：service和activity绑定到一起，同时启动同时暂停

## 18.1 普通service

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/android/android-servicelife.jpg" style="zoom:50%;" />

```java
//主activity
package cn.edu.xidian;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStart = findViewById(R.id.btnStart);
        Button btnStop = findViewById(R.id.btnStart);
        final Intent intent = new Intent(MainActivity.this,MyService.class);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(intent);//启动service
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //其他组件调用这个方法停止。service自己调用stopSelf()方法停止
                stopService(intent);//停止service
            }
        });
    }

}
```

```java
//服务类，这个类要在AndroidManifest.xml中配置
<!--配置service，向导创建自动配置-->
/*<service
        android:name=".MyService"
        android:enabled="true"
        android:exported="true">
</service>*/
package cn.edu.xidian;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //每次启动service时候调用
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i=0;
                while(isRunning())
                {//如果service正在运行，输出变量i的值
                    Log.i("service",String.valueOf(i));
                    i++;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();//开启线程
        return super.onStartCommand(intent, flags, startId);
    }

    //判断service是否正在运行
    public boolean isRunning()
    {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        //获取所有正在运行的service
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) activityManager.getRunningServices(5);
        for(int i=0;i<runningService.size();++i)
        {
            if(runningService.get(i).service.getClassName().toString().equals("cn.edu.xidian.MyService"))
            {
                return true;
            }
        }
        return false;
    }
}

```

### 18.1.1 实现背景音乐播放

```java
//MainActivity
//主activity
package cn.edu.xidian;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(MainActivity.this,MusicService.class);
        Button btn = findViewById(R.id.btControl);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动和停止service
                if(MusicService.isPlay == false)
                {
                    startService(intent);//启动service
                }else{
                    stopService(intent);//停止service
                }
            }
        });
    }

    //进入页面就行直接播放音乐
    @Override
    protected void onStart() {
        startService(new Intent(MainActivity.this,MusicService.class));//启动service

        super.onStart();
    }
}
```

```java
//MusicService
package cn.edu.xidian;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MusicService extends Service {
    static boolean isPlay;//记录当前播放状态
    MediaPlayer mediaPlayer;//音乐媒体
    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        //创建MediaPlaer对象，加载音频文件
        mediaPlayer = MediaPlayer.create(this,R.raw.her);
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!mediaPlayer.isPlaying())
        {//判断播放状态
            mediaPlayer.start();
            isPlay = mediaPlayer.isPlaying();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        //服务停止，停止音乐播放
        mediaPlayer.stop();
        isPlay = mediaPlayer.isPlaying();
        mediaPlayer.release();//释放资源
        super.onDestroy();
    }
}

```



## 18.2 Bound Service

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/android/android-BoundServiceLife.jpg" style="zoom:50%;" />

### 18.2.1 实例演示3D号码生成

```xml
<!-xml布局文件--->
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context=".MainActivity" >
    <LinearLayout android:layout_width="match_parent" android:layout_height="wrap_content"
                  android:id="@+id/innerLinear" android:orientation="horizontal">

        <TextView android:layout_width="50dp" android:layout_height="50dp"
                  android:gravity="center"
                  android:text="0" android:textSize="30sp"
                  android:layout_weight="1"
                  android:id="@+id/num1"/>
        <TextView android:layout_width="50dp" android:layout_height="50dp"
                  android:gravity="center"
                  android:layout_weight="1"
                  android:text="0" android:textSize="30sp"
                  android:id="@+id/num2"/>
        <TextView android:layout_width="50dp" android:layout_height="50dp"
                  android:gravity="center"
                  android:layout_weight="1"
                  android:text="0" android:textSize="30sp"
                  android:id="@+id/num3"/>

    </LinearLayout>

    <Button android:layout_width="match_parent" android:layout_height="wrap_content"
            android:id="@+id/btnCreate" android:text="生成号码"/>


</LinearLayout>

```

```java
//MainActivity
//主activity
package cn.edu.xidian;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.*;

public class MainActivity extends AppCompatActivity {
    BoundService boundService;
    int[] tvId = {R.id.num1,R.id.num2,R.id.num3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.btnCreate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List number = boundService.getRandomNumber();
                for(int i=0;i<number.size();++i)
                {
                    TextView tv = findViewById(tvId[i]);//获取文本框组件
                    tv.setText(number.get(i).toString());
                }
            }
        });
    }

    //4.创建ServiceConnection对象
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            boundService = ((BoundService.MyBinder)service).getService();//获取后台service
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onStart() {
        //启动activity时与service绑定
        super.onStart();
        Intent intent = new Intent(this,BoundService.class);
        //5.绑定service，第二个参数是一个ServiceConnection对象，第三个值是是否进行自动创建service
        bindService(intent,conn,BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);//6.
    }
}
```

```java
//BoundService
package cn.edu.xidian;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.*;

public class BoundService extends Service {
    public BoundService() {
    }
    //1.创建一个内部类
    public class MyBinder extends Binder {
        public BoundService getService(){//创建获取service的方法
            return BoundService.this;//返回当前的service类
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        return new MyBinder();//2.返回一个MyBinder Service对象
    }

    //3.生成随机数
    public List getRandomNumber(){
        List resArr = new ArrayList();
        String strNumber="";//用于保存生成的随机数
        for(int i=0;i<3;++i)
        {
            int number = new Random().nextInt(9);//生成0-9数字
            strNumber = String.valueOf(number);
            resArr.add(strNumber);
        }
        return resArr;
    }

    @Override
    public void onDestroy() {//销毁service
        super.onDestroy();
    }
}

```

## 18.2 Intent Service

IntentService是Service的子类
普通Service不能自动开启线程，不能自动停止服务
IntentService可以自动开启线程，可以自动停止服务

```java
public class MyIntentService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        //写一些耗时的任务
    }
}
```



# 19 传感器

1. 获取SensorManager对象
   - getSystemService(Content.SENSOR_SERVICE)

2. 获取指定类型的传感器
   - SensorManager的getDefaultSensor(inttype)方法

3. 为制定的传感器注册监听器

   - 在Activity的onResume()方法中调用SensorManager的registerListener()方法

   - 调用上面的方法需要实现SensorEventListener接口，需要实现onSensorChanged(SensorEvent event)，该方法在传感器的值发生变化时调用，还需要实现onAccuracyChanged(Sensor sensor, int accrucy)，该方法在传感器精度发生变化时调用

## 19.1 