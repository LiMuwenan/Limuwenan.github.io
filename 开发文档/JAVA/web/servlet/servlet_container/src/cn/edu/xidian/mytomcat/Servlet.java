package cn.edu.xidian.mytomcat;

public interface Servlet {

    /**
     * 初始化方法
     */
    void init();

    /**
     * 处理请求的服务
     * @param request
     * @param response
     */
    void service(Request request,Response response);

    /**
     * 销毁方法
     */
    void destroy();

}
