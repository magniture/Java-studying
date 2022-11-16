package org.example.buffer;

import java.nio.ByteBuffer;

/**
 * @BelongsProject: Java-studying
 * @BelongsPackage: org.example.buffer
 * @Author: zhangq
 * @CreateTime: 2022-11-16  16:12
 * @Description: buffer的创建
 */
public class CreateBufferDemo {
    public static void main(String[] args) {
        //1.创建指定长度的缓冲区  ByteBuffer为例
        ByteBuffer allocate = ByteBuffer.allocate(5);
        for (int i = 0; i < 5; i++) {
            System.out.println(allocate.get());//从缓冲区当中拿去数据
        }
        //会报错. 后续讲解
        //System.out.println(allocate.get());//从缓冲区当中拿去数据

        //2.创建一个有内容的缓冲区
        ByteBuffer wrap = ByteBuffer.wrap("lagou".getBytes());
        for (int i = 0; i < 5; i++) {
            System.out.println(wrap.get());
        }
    }
}
