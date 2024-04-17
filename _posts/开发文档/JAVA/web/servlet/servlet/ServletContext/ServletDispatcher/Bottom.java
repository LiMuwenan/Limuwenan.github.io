package bottom;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class Bottom extends HttpServlet{
    public void init(){}

    public void doGet(HttpServletRequest request,HttpServletResponse response)
    throws ServletException,IOException{
        response.setContentType("text:html;charset=GBK");
        PrintWriter out=response.getWriter();
        out.println("<h1>yejiao</h1><br>");
    }

    public void destroy(){}
}