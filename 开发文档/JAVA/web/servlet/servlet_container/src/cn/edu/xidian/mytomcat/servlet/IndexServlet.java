package cn.edu.xidian.mytomcat.servlet;

import cn.edu.xidian.mytomcat.Request;
import cn.edu.xidian.mytomcat.Response;
import cn.edu.xidian.mytomcat.Servlet;


public class IndexServlet implements Servlet {

    public IndexServlet(){
        init();
    }

    @Override
    public void init() {

    }

    @Override
    public void service(Request request, Response response) {
        System.out.println("这是首页");
        response.write("<h1>Hello World!</h1>");
    }

    @Override
    public void destroy() {

    }
}
