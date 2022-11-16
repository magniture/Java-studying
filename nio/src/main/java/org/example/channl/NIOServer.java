package org.example.channl;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.example.channl
 * @Author: zhangq
 * @CreateTime: 2022-11-16  16:33
 * @Description: 服务端
 */
public class NIOServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        //1.打开一个服务端通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2. 绑定对应的端口号
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //3.通道默认是阻塞的，需要设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        System.out.println("服务器启动成功......");
        while (true) {
            //4.检查是否有客户端连接，有客户端连接会返回对应的通道
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel == null) {
                System.out.println("没有客户端连接....");
                Thread.sleep(2000);
                continue;
            }
            //5.获取客户端传递过来的数据，并把数据放在byteBuffer这个缓冲区中
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            //返回值
            //正数：表示本地督导有效字节数
            //0：表示本次没有读到数据
            //-1：表示都到末尾
            int read = socketChannel.read(allocate);
            System.out.println("客户端消息:"+new String(allocate.array(),0,read, StandardCharsets.UTF_8));
            //6. 给客户端回写数据
            socketChannel.write(ByteBuffer.wrap("没钱".getBytes(StandardCharsets.UTF_8)));
            //7. 释放资源
            socketChannel.close();
        }

    }
}
