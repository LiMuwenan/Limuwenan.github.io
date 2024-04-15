package cn.edu.xidian.tcp;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws Exception {
        //不绑定端口就随机生成
        Socket socket = new Socket();
        //连接到一个地址
        socket.connect(new InetSocketAddress(InetAddress.getByName("127.0.0.1"), 2837));
        Scanner scanner = new Scanner(System.in);
        String msg = scanner.next();
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(msg.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
        socket.close();
    }
}
