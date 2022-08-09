package com.yogovi.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class GroupChatClient {
	
	private static final String HOST = "127.0.0.1";
	
	private static final int PORT = 6667;
	
	private Selector selector;
	
	private SocketChannel socketChannel;
	
	private String userName;

	public GroupChatClient() {
		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
			socketChannel.configureBlocking(false);
			socketChannel.register(selector, SelectionKey.OP_READ);
			userName = socketChannel.getLocalAddress().toString().substring(1);
			System.out.println(userName + " is ok...");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void send(String msg) {
		msg = userName + " 说: " + msg;
		try {
			socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void read() {
		try {
			while(true) {
				int readChannels = selector.select();
				if(readChannels > 0) {
					Set<SelectionKey> selectionKeys = selector.selectedKeys();
					Iterator<SelectionKey> iterator = selectionKeys.iterator();
					while(iterator.hasNext()) {
						SelectionKey selectionKey = iterator.next();
						if(selectionKey.isReadable()) {
							SocketChannel otherChannel = (SocketChannel) selectionKey.channel();
							ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
							otherChannel.read(byteBuffer);
							System.out.println(new String(byteBuffer.array()).trim());
						}
					}
					iterator.remove();
				}
				try {
					TimeUnit.SECONDS.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		GroupChatClient chatClient = new GroupChatClient();
		new Thread(()-> {
			chatClient.read();
		}).start();
		Scanner scanner = new Scanner(System.in);
		while(scanner.hasNextLine()) {
			String msg = scanner.nextLine();
			chatClient.send(msg);
		}
	}

}
