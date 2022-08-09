package com.yogovi.nio.zerocopy;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIOServer {
	
	public static void main(String[] args) throws IOException {
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(8001));
		SocketChannel socketChannel = serverSocketChannel.accept();
		int readCount = 0;
		FileOutputStream fileOutputStream = new FileOutputStream("D:\\101.mp4");
		FileChannel fileChannel = fileOutputStream.getChannel();
		while(readCount != -1) {
			ByteBuffer byteBuffer = ByteBuffer.allocate(4096);
			readCount = socketChannel.read(byteBuffer);
			if(readCount != -1) {
				byteBuffer.flip();
				fileChannel.write(byteBuffer);
			}
			//byteBuffer.rewind();
		}
		fileOutputStream.close();
	}

}
