# 1 Spring Security

- 简介
  - 为Java应用提供身份认证和授权的框架
- 特征
  - 对身份的认证和授权提供全面的、可扩展的支持
  - 防止各种攻击，比如会话固定攻击、点击劫持、csrf攻击等
  - 支持与Servlet API，Spring MVC等Web技术集成

[可以参考网站 - Spring For All](http://www.spring4all.com/article/428)

<img src="https://raw.githubusercontent.com/LiMuwenan/PicBed/master/img/dev/java/Springboot/security.png" style="zoom:50%;" />

security底层使用Filter进行管理

## 1.1 简单使用

**导入依赖**

```xml
<dependency>
   <groupId>org.springframework.boot</groupId>
   <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

导入该依赖后，该项目自动被sercurity接管，在未进行设置的情况下，该web项目会弹出默认的登陆界面，控制台会打印一个默认密码，用户名为user

## 1.2 权限设置

### 1.2.1 实体类

通常用户类就是我们要设置权限的地方，其中type属性代表了用户的权限

```
public class User{

    private int id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private int type;//0普通用户，1超级管理员，2版主
    private int status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;
}
```

下面的方法不唯一，通常是这样的做法

**用户实体类实现UserDetails接口**

- 权限设置

```java
public class User implements UserDetails {

    private int id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private int type;
    private int status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;
    
    //true：用户未过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //true：用户未锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //true：凭证为过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //true：账号可用
    @Override
    public boolean isEnabled() {
        return true;
    }

    //用户拥有的权限，用户可能有多个权限，所以是集合
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new GrantedAuthority() {//每个权限都实现这样的接口
            @Override
            public String getAuthority() {
                //该用户根据type进行判断用户类型
                switch (type){
                    case 1:
                        return "ADMIN";
                    default:
                        return "USER";
                }
            }
        });
//        list.add(new GrantedAuthority() {//第二个权限
//            @Override
//            public String getAuthority() {
//                //该用户根据type进行判断用户类型
//                switch (type){
//                    case 1:
//                        return "ADMIN";
//                    default:
//                        return "USER";
//                }
//            }
//        });
        return list;
    }
}
```

### 1.2.2 业务

**实现UserDetailsService接口**

该接口只有一个方法，根据用户名查询用户

security底层使用这个接口对我们的应用进行用户验证

```java
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    public User findUserByName(String username) {
        return userMapper.selectByName(username);
    }

    //根据用户名查询用户，s其实是username
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.findUserByName(s);
    }
}
```

### 1.2.3 security配置类

因为security底层使用的是Java EE标准中的Filter进行管理的，所以我们可以通过一个配置类对我们的web应用进行权限控制

**认证、授权、路径忽略**

```java
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //对User进行权限控制且其实现了UserDetailsService接口
    @Autowired
    private UserService userService;

    //重写三个父类的方法

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/resources/**");//忽略静态资源，Springboot的双*写法就是所有的意思
    }

    //处理认证相关的
    //AuthenticationManager：认证的核心接口
    //AuthenticationManagerBuilder：用户构建AuthenticationManager的工具
    //ProviderManager：AuthenticationManager的默认实现类
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //内置的认证规则,底层自动认证
        //userDetailsService(userService)：需要该接口才能帮开发者查出user的数据才能进而判断登录信息
        //passwordEncoder()：对密码进行编码
        //new Pbkdf2PasswordEncoder("1234")：相当于密码盐，对每个用户的密码都用这个盐加密
        //这是一种实现方法
//        auth.userDetailsService(userService).passwordEncoder(new Pbkdf2PasswordEncoder("12345"));

        //自定义认证规则
        //AuthenticationProvider：ProviderManager持有一组AuthenticationProvider，每个AuthenticationProvider负责一种认证（账号密码，微信，指纹等认证方式）
        //ProviderManager不会自己去做认证，而是通过持有的AuthenticationProvider进行认证（委托模式）
        //该接口在登录时被调用
        auth.authenticationProvider(new AuthenticationProvider() {
            //Authentication：用户封装认证信息的接口（账号、密码等），不同的实现类代表不同类型的认证信息
            //该方法就是做认证
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                String password = (String) authentication.getCredentials();

                User user = userService.findUserByName(username);

                if(user==null){
                    throw new UsernameNotFoundException("账号不存在");
                }
                password = CommunityUtil.md5(password+user.getSalt());
                if(!user.getPassword().equals(password)){
                    throw new BadCredentialsException("密码不正确");
                }

                //要返回接口的实例，因为我们是账号密码登录，所以返回UsernamePasswordAuthenticationToken
                //principal:主要信息
                //credential:证书（此处为密码）
                //authorities:权限
                return new UsernamePasswordAuthenticationToken(user,user.getPassword(),user.getAuthorities());
            }

            //返回当前接口（AuthenticationProvider）支持的是哪种认证类型
            @Override
            public boolean supports(Class<?> aClass) {
                //UsernamePasswordAuthenticationToken：Authentication接口的常用实现类
                //这里代表这个接口支持账号密码的认证模式
                return UsernamePasswordAuthenticationToken.class.equals(aClass);
            }
        });

    }

    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);//就是这里会将所有的请求过滤，变成security的默认登录页面
        //登录相关配置
        http.formLogin()
                .loginPage("/loginPage")//登录页面
                .loginProcessingUrl("/login")//处理登录请求的url。前端表单的请求路径，请求后会调用上面的authenticationProvider
                .successHandler(new AuthenticationSuccessHandler() {//请求成功后
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        //请求成功后的逻辑
                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/index");
                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {//请求失败后
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        //请求失败后的逻辑
                        //把失败信息绑定到request，转发到登录页面
                        httpServletRequest.setAttribute("error",e.getMessage());
                        httpServletRequest.getRequestDispatcher("/loginPage").forward(httpServletRequest,httpServletResponse);
                    }
                });
        //退出相关配置
        http.logout()
                .logoutUrl("/logout")//security要求退出必须时post请求
                .logoutSuccessHandler(new LogoutSuccessHandler() {
                    @Override
                    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        //退出成功后的逻辑
                        httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/index");
                    }
                });
        //授权配置
        http.authorizeRequests()
                .antMatchers("/letter").hasAnyAuthority("USER","ADMIN")//letter路径需要有USER,ADMIN才能访问
                .antMatchers("/admin").hasAnyAuthority("ADMIN")
                .and().exceptionHandling().accessDeniedPage("/denied");//访问失败的跳转请求
                //增加filter，处理验证码
        http.addFilterBefore(new Filter() {
            @Override
            public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
                HttpServletRequest request = (HttpServletRequest) servletRequest;//习惯使用Http子类
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                if("/login".equals(request.getServletPath())){
                    String verifyCode = request.getParameter("verifyCode");
                    if(!"1234".equals(verifyCode)){
                        request.setAttribute("error","验证码错误");
                        request.getRequestDispatcher("/loginpage").forward(request,response);
                        return;
                    }
                }
                filterChain.doFilter(request,response);//让请求继续向下执行
            }
        }, UsernamePasswordAuthenticationFilter.class);//在该filter前增加doFilter

        //记住我
        http.rememberMe()//前端页面记住我选项的name必须是 remember-me
                .tokenRepository(new InMemoryTokenRepositoryImpl())//将用户保存在内存还是数据库等等
                .tokenValiditySeconds(3600*24)
                .userDetailsService(userService);//第二次登录时，自动认证
    }
}
```



## 1.3 CSRF配置

Security默认开始CSRF保护，所以我们需要在每一个异步请求上加入下面的令牌

CSRF：钓鱼网站盗取用户的cookie信息，并代替用户向服务器发送请求，从而达到窃取资源的目的

Security底层给用户返回表单页面的时候附带一个隐藏的Token，当用户提交表单时有这个Token，那么就是正常的；如果没有，则说明这是伪造信息。



如果是异步请求就需要自己处理,哪个异步请求需要安全设置，就加在哪个页面

### 1.3.1 异步请求

**页面头部生成令牌**

```html
<!--    访问该页面时，在此处生成一个CSRF令牌-->
<meta name="_csrf" th:content="${_csrf.token}"/>
<meta name="_csrf_header" th:content="${_csrf.headerName}"/>
```

**将令牌加入异步请求头**

```js
//发送ajax请求之前，将CSRF设置到消息头中
var token = $("meta[name='_csrf']").attr("content");//获得meta标签name为_csrf的content属性值
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e,xhr,options){
   xhr.setRequestHeader(header,token);//key-value
});
```

### 1.3.2 关闭CSRF

在SecurityConfig配置中关闭csrf

```java
http.authorizeRequests().and().csrf().disable();//关闭csrf
```

