package showcart;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import lib.*;
import java.util.*;

public class ShowCartServlet extends HttpServlet {
    public void init() {
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext context = getServletContext();
        Map<String,Product> products = (Map<String,Product>) context.getAttribute("products");
        String str[] = request.getParameterValues("product");

        out.println("<!DOCTYPE html><html>");
        out.println("<head><meta charset=\"UTF-8\"><title>ShowProduct</title></head>");
        out.println("<body><form action=\"./showcart\"><table bgcolor=\"aliceblue\">");
        out.println("<tr><td>编号</td><td>名称</td><td>描述</td><td>库存</td><td>价格</td><td>选择</td></tr>");
        for(int i=0;i<str.length;++i){
            String tmp = str[i];
            out.println("<tr><td>" + products.get(tmp).getId()+ "</td>");
            out.println("<td>" + products.get(tmp) .getName()+ "</td>");
            out.println("<td>" + products.get(tmp).getDescription() + "</td>");
            out.println("<td>" + products.get(tmp).getStack() + "</td>");
            out.println("<td>" + products.get(tmp).getPrice() + "</td>");
            out.println("<td><input type=\"checkbox\" name=\"Product\" value=\""+products.get(tmp).getId()+"\"/></td></tr>");
        }  
        out.println("</table></form></body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}