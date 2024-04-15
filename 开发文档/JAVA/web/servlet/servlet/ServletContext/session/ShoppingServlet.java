package shopping;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import lib.*;
import java.util.*;

public class ShoppingServlet extends HttpServlet{
    ArrayList<Product> cart = null;
    public void init(){}

    public void doGet(HttpServletRequest request,HttpServletResponse response)
    throws ServletException,IOException{
        response.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        ServletContext context = getServletContext();
        Map<String,Product> products = (Map<String,Product>) context.getAttribute("products");
        HttpSession session = request.getSession();
        String str[] = request.getParameterValues("product");
        for(int i=0;i<str.length;++i){
            Product tmp = products.get(str[i]);
            int stack = tmp.getStack();
            tmp.setStack(stack--);
            cart.add(tmp);
        }
        out.println("<!DOCTYPE html><html>");
        out.println("<head><meta charset=\"UTF-8\"><title>ShowProduct</title></head>");
        out.println("<body><form action=\"./showcart\"><table bgcolor=\"aliceblue\">");
        out.println("<tr><td>编号</td><td>名称</td><td>描述</td><td>库存</td><td>价格</td><td>选择</td></tr>");
        for(int i=0;i<cart.size();++i){
            out.println("<tr><td>" + cart.get(i).getId()+ "</td>");
            out.println("<td>" + cart.get(i) .getName()+ "</td>");
            out.println("<td>" + cart.get(i).getDescription() + "</td>");
            out.println("<td>" + cart.get(i).getStack() + "</td>");
            out.println("<td>" + cart.get(i).getPrice() + "</td>");
        }
        out.println("</table></form></body></html>");
        context.setAttribute("products", products);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response)
    throws ServletException,IOException{
        doGet(request, response);
    }

    public void destroy(){}
}