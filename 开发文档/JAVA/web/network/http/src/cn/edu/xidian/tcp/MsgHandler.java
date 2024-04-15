package cn.edu.xidian.tcp;

import java.io.InputStream;
import java.net.Socket;

public class MsgHandler extends Thread{
    Socket accept = null;
    public MsgHandler(Socket accept){
        this.accept = accept;
    }

    @Override
    public void run(){
        InputStream inputStream = null;
        try{
            //拿到数据进行读取
            inputStream = accept.getInputStream();
            //这里定义读取1MB数据
            byte[] buf = new byte[1024];
//        int len = inputStream.read(buf);
            //读取任意长度
            int len;
            while ((len = inputStream.read(buf)) != -1)
                System.out.println(new String(buf, 0, len));
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            if(inputStream != null){
                try{
                    inputStream.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

    }
}
