package com.zq.demo.n1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * &#064; com.zq.demo
 *
 * @author: zq
 * &#064; 2023-09-15  14:49
 * &#064; TODO
 */
public class HelloClient {
    public static void main(String[] args) throws InterruptedException {
        // 1. 启动客户端
        new Bootstrap()
                // 2. 添加 EventLoop
                .group(new NioEventLoopGroup())
                // 3. 选择 channel 实现
                .channel(NioSocketChannel.class)
                // 4. 添加 handler
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nsc) throws Exception {
                        nsc.pipeline().addLast(new StringEncoder());
                    }
                })
                // 5. 连接到服务器
                .connect(new InetSocketAddress(7999))
                .sync()
                .channel()
                // 6. 向服务器发送数据
                .writeAndFlush("Hello World! --sidiot.");
    }
}

