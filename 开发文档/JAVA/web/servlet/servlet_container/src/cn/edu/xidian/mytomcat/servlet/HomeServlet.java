package cn.edu.xidian.mytomcat.servlet;

import cn.edu.xidian.mytomcat.Request;
import cn.edu.xidian.mytomcat.Response;
import cn.edu.xidian.mytomcat.Servlet;

public class HomeServlet implements Servlet {

    public HomeServlet(){
        init();
    }

    @Override
    public void init() {
        System.out.println("HomeServlet init");
    }

    @Override
    public void service(Request request, Response response) {

        response.write("<h1>Home</h1>");
    }

    @Override
    public void destroy() {

    }
}
