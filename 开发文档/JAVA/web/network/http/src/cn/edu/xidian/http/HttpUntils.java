package cn.edu.xidian.http;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class HttpUntils {
    //用来封装报文
    public static byte[] getResponse (String html){
        return ("http/1.1 200 ok\r\nContent-type:text/html;charset=utf-8\r\nContent-length:" + html.getBytes(StandardCharsets.UTF_8).length + "\r\n\r\n" + html).getBytes(StandardCharsets.UTF_8);
    }

    //读取html文件
    public static String getPage (String filePath) throws Exception {
        StringBuilder sb = new StringBuilder();

        //源文件放在src下，经过编译之后，这个会自己到out/http/下找这个文件
        //非打包
        InputStream resource = Http.class.getClassLoader().getResourceAsStream(filePath);
        //寻找父路径
//        String path = Http.class.getProtectionDomain().getCodeSource().getLocation().getPath();
//        path = path.substring(0,path.lastIndexOf("/")) + "/pages/";
//        InputStream resource = new FileInputStream(path + filePath);
        //没找到需要的页面
        if(resource == null)
        {
            resource = Http.class.getClassLoader().getResourceAsStream("404.html");
        }
        byte[] buf = new byte[1024];
        int len;
        while ((len = resource.read(buf)) != -1) {
            sb.append(new String(buf, 0, len));
        }

        //形成一个动态页面
        String name = "李根";
        String result = sb.toString().replace("{{ name }}",name);

        return result;
    }
}
