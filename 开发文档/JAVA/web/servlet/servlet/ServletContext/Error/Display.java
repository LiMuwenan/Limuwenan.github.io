package Turnme;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Display extends HttpServlet{
    public void init(){}

    public void doGet(HttpServletRequest request,HttpServletResponse response)
    throws ServletException,IOException{
        
        PrintWriter out=response.getWriter();
        out.println("<h1>Turn me</h1>\n");
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response)
    throws ServletException,IOException{
        
    }

    public void destroy(){}
}