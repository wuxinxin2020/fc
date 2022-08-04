package com.yogovi.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannel {
	
	public static void main(String[] args) throws IOException {
		//获取输出流
		FileOutputStream fileOutputStream = new FileOutputStream("D://file_channel.txt");
		//通过输出流获取通道
		FileChannel fileChannel = fileOutputStream.getChannel();
		//创建byteBuffer
		ByteBuffer byteBuffer = ByteBuffer.allocate(100);
		String content = "hello,悠果维";
		//写入缓冲区
		byteBuffer.put(content.getBytes());
		//position复初始位
		byteBuffer.flip();
		//通道写从缓冲区读取数据
		fileChannel.write(byteBuffer);
		//关闭输出流
		fileOutputStream.close();
	}

}
