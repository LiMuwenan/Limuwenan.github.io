package show;

import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import lib.*;
import java.util.*;

public class ShowServlet extends HttpServlet {
    Map<String, Product> products = new HashMap<String, Product>();

    public void init() {
        products.put("001", new Product("001", "海信电视机", "58英寸，LED液晶显示，安卓操作系统，2014年上市", 6999.00, 100));
        products.put("002", new Product("002", "海尔洗衣机", "洗涤容量6Kg，滚筒式，LED显示屏，内筒材料为不锈钢", 3999.00, 100));
        products.put("003", new Product("003", "格力空调", "三级变频，壁式挂机，超静音，超长质保，强力除湿", 3269.00, 100));
        products.put("004", new Product("004", "海尔热水器", "横式，专利金刚三层胆，60L", 2780.00, 100));
        products.put("005", new Product("005", "西门子冰箱", "三门冰箱，电脑控温，总容积为231-280L，能效等级为一级", 5780.00, 100));
        ServletContext context=getServletContext();
        context.setAttribute("products",products);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html><html>");
        out.println("<head><meta charset=\"UTF-8\"><title>ShowProduct</title></head>");
        out.println("<body><form action=\"./shopping\"><table bgcolor=\"aliceblue\">");
        out.println("<tr><td>编号</td><td>名称</td><td>描述</td><td>库存</td><td>价格</td><td>选择</td></tr>");
        for(String i : products.keySet()){
            out.println("<tr><td>" + products.get(i).getId() + "</td>");
            out.println("<td>" + products.get(i).getName() + "</td>");
            out.println("<td>" + products.get(i).getDescription() + "</td>");
            out.println("<td>" + products.get(i).getStack() + "</td>");
            out.println("<td>" + products.get(i).getPrice() + "</td>");
            out.println("<td><input type=\"checkbox\" name=\"product\" value=\""+products.get(i).getId()+"\"/></td></tr>");
        }  
        out.println("<tr><td><input type=\"submit\" value=\"确定\"></td><td><input type=\"reset\" value=\"重置\"></td></tr>");
        out.println("</table></form></body></html>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public void destroy() {
    }
}