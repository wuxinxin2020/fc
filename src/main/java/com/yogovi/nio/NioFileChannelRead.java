package com.yogovi.nio;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannelRead {
	
	public static void main(String[] args) throws IOException {
		FileInputStream fileInputStream = new FileInputStream("D://file_channel.txt");
		FileChannel fileChannel = fileInputStream.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(100);
		fileChannel.read(byteBuffer);
		System.out.println(new String(byteBuffer.array()));
		fileInputStream.close();
	}

}
