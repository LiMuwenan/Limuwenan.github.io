package cn.edu.xidian.tcp;

import cn.edu.xidian.tcp.MsgHandler;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws Exception {
        //创建一个服务器
        ServerSocket server = new ServerSocket();
        //绑定端口
        server.bind(new InetSocketAddress(2837));
        System.out.println("服务器已启动，监听在2837端口：\n");

        //多线程监听
        while(true){
            Socket accept = server.accept();
            //启动线程
            new MsgHandler(accept).start();
        }
    }

}
