package index;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class Index extends HttpServlet{
    public void init(){}

    public void doGet(HttpServletRequest request,HttpServletResponse response)
    throws ServletException,IOException{
        response.setContentType("text:html;charset=GBK");
        PrintWriter out=response.getWriter();
        request.getRequestDispatcher("top").include(request,response);
        out.println("<h1>woshizhongjian</h1><br>");
        request.getRequestDispatcher("bottom").include(request,response);
    }

    public void destroy(){}
}