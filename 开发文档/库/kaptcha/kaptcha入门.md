# 1 依赖

```xml
<!-- https://mvnrepository.com/artifact/com.github.penggle/kaptcha -->
<dependency>
    <groupId>com.github.penggle</groupId>
    <artifactId>kaptcha</artifactId>
    <version>2.3.2</version>
</dependency>

```



创建配置类

```java
/**
 * 验证码生成kaptcha配置类
 */
@Configuration
public class KaptchaConfig {

    @Bean
    public Producer kaptchaProducer(){
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width","100");//宽度
        properties.setProperty("kaptcha.image.height","40");//高度
        properties.setProperty("kaptcha.textproducer.font.size","32");//字体大小
        properties.setProperty("kaptcha.textproducer.font.color","0,0,0");//字体颜色
        properties.setProperty("kaptcha.textproducer.char.string","0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ");//随机字符
        properties.setProperty("kaptcha.textproducer.char.length","4");//4位
        properties.setProperty("kaptcha.noise.impl","com.google.code.kaptcha.impl.NoNoise");//无干扰

        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Config config = new Config(properties);
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
```

创建验证码

```java
/**
 * 获取验证码单独写一个请求，方便后面刷新验证码，用response单独返回
 * 该验证码属于敏感信息，所以使用session存储在服务端
 * @param response
 * @param session
 */
@RequestMapping(value = "/kaptcha",method = RequestMethod.GET)
public void getKaptcha(HttpServletResponse response, HttpSession session){
    //生成验证码
    String text = kaptcha.createText();
    BufferedImage image = kaptcha.createImage(text);

    //将验证码存入session
    session.setAttribute("kaptcha",text);

    // 将图片输出给浏览器
    response.setContentType("image/png");
    try {
        OutputStream os = response.getOutputStream();
        ImageIO.write(image,"png",os);
    } catch (IOException e) {
        logger.error("验证码响应失败"+e.getMessage());
    }
}
```

