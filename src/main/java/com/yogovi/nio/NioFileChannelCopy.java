package com.yogovi.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannelCopy {

	public static void main(String[] args) throws IOException {
		File file = new File("src/main/resources/1.txt");
		FileInputStream fileInputStream = new FileInputStream(file);
		FileChannel inputFileChannel = fileInputStream.getChannel();
		FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/2.txt");
		FileChannel outputFileChannel = fileOutputStream.getChannel();
//		ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
//		inputFileChannel.read(byteBuffer);
//		byteBuffer.flip();
//		outputFileChannel.write(byteBuffer);
		ByteBuffer byteBuffer = ByteBuffer.allocate(10);
		while(true) {
			byteBuffer.clear();
			int read = inputFileChannel.read(byteBuffer);
			if(read == -1) {
				break;
			}
			byteBuffer.flip();
			outputFileChannel.write(byteBuffer);
		}
		fileInputStream.close();
		fileOutputStream.close();
	}
	
}
