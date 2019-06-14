package com.bise.niodemo.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: bise
 * @Date: 2019/6/12 18:01
 * @Version 1.0
 */
public class FileChannelDemo {

    public static void main(String[] args) throws IOException {
        //访问文件
        RandomAccessFile aFile = new RandomAccessFile("D:/NioDemoFiles/nio-data.txt", "rw");

        //将文件内容扔到FileChannel进行传输
        FileChannel inChannel = aFile.getChannel();

        //建立缓存区
        ByteBuffer buf = ByteBuffer.allocate(168);

        //从Channel读取内容到ByteBuffer，返回读取到的字节数（最多不会超过缓存区的大小，
        // 剩下的仍然会保存在Channel里）
        int bytesRead = inChannel.read(buf);

        //遍历获取内容（当）
        while (bytesRead != -1) {
            System.out.println("   Read " + bytesRead);
            buf.flip(); //更改缓存区为读取

            //判断Buffer里的 当前位置（position） < 包含的数据数（limit）
            while (buf.hasRemaining()) {
                //从Buffer中获取一个数据
                System.out.print((char) buf.get());
            }
            //清除Buffer的数据
            buf.clear();
            //重新从Channel取出剩下的数据到Buffer（最多不会超过缓存区的大小，剩下的仍然会保存在Channel里）
            bytesRead = inChannel.read(buf);
        }
        //关闭这个文件
        aFile.close();
    }

}
