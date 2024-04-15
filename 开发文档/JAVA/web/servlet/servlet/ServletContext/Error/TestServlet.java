package Error;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class TestServlet extends HttpServlet{
    public void init(){}

    public void doGet(HttpServletRequest request,HttpServletResponse response)
    throws ServletException,IOException{
        
        response.sendError(404,"you are error\n");
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response)
    throws ServletException,IOException{
        
    }

    public void destroy(){}
}