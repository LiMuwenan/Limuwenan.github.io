package cn.edu.xidian.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class MyTask implements Runnable{

    Socket accept = null;
    static int cnt = 1;

    public MyTask(Socket accept){
        this.accept  =accept;
    }

    @Override
    public void run(){
        StringBuilder sb = new StringBuilder();
        InputStream in = null;
        OutputStream out = null;
        try{
            in = accept.getInputStream();
            int len;
            byte[] buf = new byte[512];
            while ((len = in.read(buf)) != -1) {
//                System.out.println(new String(buf, 0, len));
                sb.append(new String(buf,0,len));
                if (len < 512) {
                    accept.shutdownInput();
                }
            }

            //使用正则进行分割，分割后相当于数组，通过下标访问
            String url = sb.toString().split("\r\n")[0].split(" ")[1].substring(1);
            //忽略图标请求
            if("favicon.ico".equals(url)||"".equals(url))
            {
                return;
            }
            //定义一个响应
            out = accept.getOutputStream();
            if(out!=null)
            {
                System.out.println("对第" + cnt++ + "次请求进行响应!");
            }
            out.write(HttpUntils.getResponse(HttpUntils.getPage(url)));

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                out.flush();
                if(out!=null){
                    out.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
