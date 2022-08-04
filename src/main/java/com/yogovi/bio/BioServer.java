package com.yogovi.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {

	public static void main(String[] args) throws IOException {
		final ServerSocket serverSocket = new ServerSocket(6666);
		ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
		while (true) {
			final Socket socket = serverSocket.accept();
			newCachedThreadPool.execute(()->{
				handler(socket);
			});
		}
	}
	
	private static void handler(Socket socket) {
		InputStream inputStream = null;
		try {
			byte[] bytes = new byte[1024];
			inputStream = socket.getInputStream();
			int length = 0;
			while((length = inputStream.read(bytes)) != -1) {
				System.out.println(new String(bytes, 0, length));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
