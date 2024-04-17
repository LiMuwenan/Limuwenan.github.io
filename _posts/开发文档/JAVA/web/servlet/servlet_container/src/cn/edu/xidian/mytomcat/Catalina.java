package cn.edu.xidian.mytomcat;

import cn.edu.xidian.mytomcat.servlet.IndexServlet;
import cn.edu.xidian.mytomcat.servlet.NotFound;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Catalina {

    static{
        try {
            Container.WEB_CONFIGURE.load(Catalina.class.getClassLoader().getResourceAsStream("web.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        ServerSocket server =  new ServerSocket();
        server.bind(new InetSocketAddress(2387));

        while(true){
            Socket accept = server.accept();
            InputStream in = accept.getInputStream();
            StringBuilder sb = new StringBuilder();
            int len;
            byte[] buf = new byte[512];
            while ((len = in.read(buf))!=-1){
                sb.append(new String(buf,0,len));
                if (len<512){
                    accept.shutdownInput();
                }
            }
            //构建一个请求对象
            Request request = Request.buildRequest(sb.toString());
            request.setRemoteHost(accept.getInetAddress().getHostAddress());
            String url = request.getUrl();
            if("favicon.ico".equals(url)){
                continue;
            }
            //容器中查找servlet。第一次是空的时候去if里创建，之后直接从Container中就能拿到，体现了生命周期的init
            Servlet servlet = Container.SERVLET_CONTAINER.get(url);
            //图标请求会导致报错
            if(servlet == null){
                //如果容器里没有，去配置文件中找
                String fullClassName = Container.WEB_CONFIGURE.getProperty(url);//全类名
                if(!"".equals(fullClassName)&&fullClassName!=null){
                    //创建一个servlet并放入容器
                    servlet = (Servlet) Class.forName(fullClassName).newInstance();
                }else{
                    servlet = new NotFound();
                }
                Container.SERVLET_CONTAINER.put(url, servlet);
            }

            //构建一个响应
            Response response = new Response();
            response.setOs(accept.getOutputStream());


            servlet.service(request,response);
        }

    }
}
