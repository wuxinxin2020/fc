package com.yogovi.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;
import java.util.List;

public class NioScatteringAndGathering {

	public static void main(String[] args) throws IOException {
		//channel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); 
		//绑定端口
		SocketAddress endpoint = new InetSocketAddress(7000);
		serverSocketChannel.socket().bind(endpoint);
		//ByteBuffer数组
		ByteBuffer[] byteBuffers = new ByteBuffer[2];
		byteBuffers[0] = ByteBuffer.allocate(5);
		byteBuffers[1] = ByteBuffer.allocate(3);
		List<ByteBuffer> bufferList = Arrays.asList(byteBuffers);
		
		//socket,等待客户端连接
		int messageLength = 8;
		SocketChannel socketChannel = serverSocketChannel.accept();
		while(true) {
			int readLength = 0;
			while(readLength < messageLength) {
				long l = socketChannel.read(byteBuffers);
				System.out.println("rl = " + l);
				readLength += l;
				System.out.println("readLength = " + readLength);
				bufferList.forEach(byteBuffer ->{
					//读取
					System.out.println("position=" + byteBuffer.position() 
					+ ", limit=" + byteBuffer.limit());
				});
			}
			bufferList.forEach(byteBuffer ->{
				byteBuffer.flip();
			});
			long writelength = 0;
			while(writelength < messageLength) {
				long l = socketChannel.write(byteBuffers);
				writelength += l;
			}
			bufferList.forEach(byteBuffer ->{
				byteBuffer.clear();
			});
			System.out.println("readLength = " + readLength + ", writelength = " + writelength 
					+ ", messageLength = " + messageLength);
		}
		
	}
	
}
