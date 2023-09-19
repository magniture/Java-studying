package com.zq.demo.n2;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.util.concurrent.TimeUnit;

/**
 * &#064; com.zq.demo.n2
 *
 * @author: zq
 * &#064; 2023-09-19  10:06
 * &#064; TODO
 */
public class TestEventLoop {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup(2);

        group.next().submit(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        group.next().scheduleAtFixedRate(() -> {
        }, 0, 1, TimeUnit.SECONDS);


        group.shutdownGracefully();
    }
}
