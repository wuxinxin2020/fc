package com.yogovi.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServer {
	
	private static final int PORT = 6667;
	
	private Selector selector;
	
	private ServerSocketChannel listenChannel;

	public GroupChatServer() {
		try {
			selector = Selector.open();
			listenChannel = ServerSocketChannel.open();
			listenChannel.socket().bind(new InetSocketAddress(PORT));
			listenChannel.configureBlocking(false);
			listenChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void listen() {
		try {
			while(true) {
				if(selector.select(2000) == 0) {
					continue;
				}
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterators = selectionKeys.iterator();
				while(iterators.hasNext()) {
					SelectionKey selectionKey = iterators.next();
					if(selectionKey.isAcceptable()) {
						SocketChannel socketChannel = listenChannel.accept();
						socketChannel.configureBlocking(false);
						socketChannel.register(selector, SelectionKey.OP_READ);
						System.out.println(socketChannel.getRemoteAddress().toString().substring(1) + ", 上线了");
					}
					if(selectionKey.isReadable()) {
						read(selectionKey);
					}
					selectionKeys.remove(selectionKey);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	private void read(SelectionKey selectionKey) {
		SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		try {
			int count = socketChannel.read(byteBuffer);
			if(count > 0) {
				String msg = new String(byteBuffer.array());
				System.out.println("from 客户端:" + msg);
				send(msg, socketChannel);
			}
		} catch (IOException e) {
			try {
				System.out.println(socketChannel.getRemoteAddress().toString().substring(1) + " 离线了");
				selectionKey.cancel();
				socketChannel.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	private void send(String msg, SocketChannel self) throws IOException {
		System.out.println("服务器转发消息中...");
		for(SelectionKey selectionKey : selector.keys()) {
			Channel targetChannel = selectionKey.channel();
			if(targetChannel instanceof SocketChannel && targetChannel != self) {
				SocketChannel destChannel = (SocketChannel) targetChannel;
				ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
				destChannel.write(byteBuffer);
			}
		}
	}
	
	public static void main(String[] args) {
		GroupChatServer groupChatServer = new GroupChatServer();
		groupChatServer.listen();
	}

}
