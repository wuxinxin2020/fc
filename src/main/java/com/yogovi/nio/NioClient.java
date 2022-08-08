package com.yogovi.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClient {
	
	public static void main(String[] args) throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(true);
		InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8888);
		if(!socketChannel.connect(inetSocketAddress)) {
			while (!socketChannel.finishConnect()) {
				System.out.println("因连接需要时间, 客户端不会阻塞, 可以处理其他任务...");
			}
		}
		String str = "hello, 悠果维";
		ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
		socketChannel.write(byteBuffer);
		//System.in.read();
	}

}
