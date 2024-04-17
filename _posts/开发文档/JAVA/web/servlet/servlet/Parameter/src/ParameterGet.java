import javax.servlet.http.*;
import java.io.*;
import javax.servlet.*;

public class ParameterGet extends HttpServlet {
    public void init(){}

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        response.setContentType("text/html;charset=GBK");
        PrintWriter out =response.getWriter();
        String[] fond=request.getParameterValues("fond");//取得name为fond的项的value值
        out.println("你的爱好是:<br>");
        String str="";
        for(int i=0;i<fond.length;++i){
            if("sport".equals(fond[i])){
                str="体育";
            }else if("music".equals(fond[i])){
                str="音乐";
            }else if("paint".equals(fond[i])){
                str="美术";
            }else if("calligraphy".equals(fond[i])){
                str="书法";
            }else if("reading".equals(fond[i])){
                str="看书";
            }
            out.println(str+"<br/>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws ServletException,IOException{
        super.doGet(request,response);
    }
}