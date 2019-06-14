package com.bise.niodemo.channel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: bise
 * @Date: 2019/6/13 15:52
 * @Version 1.0
 */
public class ScatterAndGatherDemo {
    public static void main(String[] args) throws IOException {

        //访问文件
        RandomAccessFile aFile = new RandomAccessFile("D:/NioDemoFiles/nio-data.txt", "rw");
        //将文件内容扔到FileChannel进行传输
        FileChannel inChannel = aFile.getChannel();
        //用于保存头部的缓存区（假设我的数据头只有128Byte）
        ByteBuffer header = ByteBuffer.allocate(128);
        //用于保存剩下
        ByteBuffer body   = ByteBuffer.allocate(1024);

        ByteBuffer[] bufferArray = { header, body };

        inChannel.read(bufferArray);
    }
}
