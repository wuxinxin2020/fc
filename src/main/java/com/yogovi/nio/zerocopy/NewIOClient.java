package com.yogovi.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NewIOClient {

	public static void main(String[] args) throws IOException {
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.connect(new InetSocketAddress(8001));
		String fileName = "F:\\BaiduYunDownload\\故宫之神思.mp4";
		FileInputStream fileInputStream = new FileInputStream(fileName);
		FileChannel fileChannel = fileInputStream.getChannel();
		long startTime = System.currentTimeMillis();
		//linux transferTo 一次传输完成
		//Windows下一次只能传输8M，需要分段传输
		long transferCount = 0;
		long remindSize = fileChannel.size();
		int baseSize = 1024 * 8 * 1024;
		while(remindSize > 0) {
			long transferSize = baseSize < remindSize ? baseSize : remindSize;
			transferCount += fileChannel.transferTo(transferCount, transferSize, socketChannel);
			System.out.println("transferCount = " + transferCount);
			remindSize = remindSize - transferSize;
			System.out.println("remindSize = " + remindSize);
		}
		System.out.println("耗时:" + (System.currentTimeMillis() - startTime));
		fileChannel.close();
		fileInputStream.close();
		//System.in.read();
	}
	
}
