package cn.edu.xidian.mytomcat.servlet;

import cn.edu.xidian.mytomcat.Request;
import cn.edu.xidian.mytomcat.Response;
import cn.edu.xidian.mytomcat.Servlet;

public class NotFound implements Servlet {
    public NotFound(){
        init();
    }

    @Override
    public void init() {
        System.out.println("404 Not Found");
    }

    @Override
    public void service(Request request, Response response) {
        response.write("<h1>404 Not Found</h1>");
    }

    @Override
    public void destroy() {

    }
}
