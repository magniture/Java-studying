package com.zq.demo.n2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * &#064; com.zq.demo.n2
 *
 * @author: zq
 * &#064; 2023-09-19  10:03
 * &#064; TODO
 */
public class EventLoopServer {
    public static void main(String[] args) {
        EventLoopGroup group = new DefaultEventLoopGroup();

        new ServerBootstrap()
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast("h1", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                ctx.fireChannelRead(msg);
                            }
                        }).addLast(group,"h2", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                try {
                                    Thread.sleep(5000);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                ByteBuf buf = (ByteBuf) msg;
                            }
                        });
                    }
                })
                .bind(7999);
    }
}
