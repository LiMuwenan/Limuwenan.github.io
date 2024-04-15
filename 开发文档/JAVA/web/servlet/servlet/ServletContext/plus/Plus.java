package plus;
//只要不关闭服务器，该结果将一直累加

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class Plus extends HttpServlet{
    ServletContext sc;
    public void init(){
        sc=this.getServletContext();//实例化
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response)
    throws ServletException,IOException{
        int count;
        
        String str=request.getParameter("number");
        int num=Integer.parseInt(str);//字符串变为整形
        String o=(String) sc.getAttribute("count");
        if(o!=null)//只要调用过这个程序就会有count对象
        {
            count=Integer.parseInt(str);
        }
        else
        {
            count=0;
        }
        count+=num;
        PrintWriter out=response.getWriter();
        sc.setAttribute("count", String.valueOf(count));//将ServletContext中的属性更新，便与共享
        out.println("now result："+count);
    }

    public void destroy(){}
}