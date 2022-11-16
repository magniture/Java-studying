package org.example.selector;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.example.selector
 * @Author: zhangq
 * @CreateTime: 2022-11-16  17:36
 * @Description: 服务端-选择器
 */
public class NIOSelectorServer {
    public static void main(String[] args) throws IOException {
        //1. 打开一个服务端通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //2. 绑定对应的端口号
        serverSocketChannel.bind(new InetSocketAddress(9999));
        //3. 通道默认是阻塞的，需要设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        //4. 创建选择器
        Selector selector = Selector.open();
        //5. 将服务端通道注册到选择器上,并指定注册监听的事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务端启动成功.....");
        while (true) {
            //6. 检查选择器是否有事件
            int select = selector.select(2000);
            if (select == 0) {
                System.out.println("没有事件发生....");
                continue;
            }
            //7. 获取事件集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                //8. 判断事件是否是客户端连接事件SelectionKey.isAcceptable()
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    //9. 得到客户端通道,并将通道注册到选择器上, 并指定监听事件为OP_READ
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("有客户端连接.....");
                    //将通道必须设置成非阻塞的状态.因为selector选择器需要轮询监听每个通道的事件
                    socketChannel.configureBlocking(false);
                    //指定监听事件为OP_READ 读就绪事件
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                //10. 判断是否是客户端读就绪事件SelectionKey.isReadable()
                if (key.isReadable()) {
                    //11.得到客户端通道,读取数据到缓冲区
                    SocketChannel socketChannel = (SocketChannel) key.channel();
                    ByteBuffer allocate = ByteBuffer.allocate(1024);
                    int read = socketChannel.read(allocate);
                    if (read > 0) {
                        System.out.println("客户端消息:" + new String(allocate.array(), 0, read
                                , StandardCharsets.UTF_8));
                        //12. 给客户端回写数据
                        socketChannel.write(ByteBuffer.wrap("没钱".getBytes(StandardCharsets.UTF_8)));
                        socketChannel.close();
                    }
                }
                //13. 从集合中删除对应的事件, 因为防止二次处理.
                iterator.remove();
            }
        }
    }
}

