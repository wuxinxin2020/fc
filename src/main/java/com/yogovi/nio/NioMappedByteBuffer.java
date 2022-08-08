package com.yogovi.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

public class NioMappedByteBuffer {

	public static void main(String[] args) throws IOException {
		//获取文件
		RandomAccessFile randomAccessFile = new RandomAccessFile("src/main/resources/3.txt", "rw");
		//fileChannel
		FileChannel fileChannel = randomAccessFile.getChannel();
		//文件范围
		MappedByteBuffer mappedByteBuffer = fileChannel.map(MapMode.READ_WRITE, 0, 5);
		mappedByteBuffer.put(0, (byte)'A');
		mappedByteBuffer.put(4, (byte)'E');
		//关闭
		randomAccessFile.close();
	}
	
}
