package cn.edu.xidian.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Http {
    public static void main(String[] args) throws Exception {
        //创建服务器
        ServerSocket server = new ServerSocket();
        //绑定端口
        server.bind(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 2387));
        System.out.println("开启监听：\n");
        //监听
        int cnt = 1;
        while (true) {
            StringBuilder sb = new StringBuilder();
            Socket accept = server.accept();
            //定义一个线程池
            ExecutorService executorService = Executors.newFixedThreadPool(20);
            //每有一个服务另起一个线程
            executorService.submit(new MyTask(accept));
        }
    }


}
