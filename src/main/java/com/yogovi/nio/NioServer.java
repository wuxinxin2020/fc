package com.yogovi.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioServer {
	
	public static void main(String[] args) throws IOException {
		//创建channel
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		//创建selector
		Selector selector = Selector.open();
		serverSocketChannel.socket().bind(new InetSocketAddress(6666));
		//设置非阻塞
		serverSocketChannel.configureBlocking(false);
		//channel注册到selector
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println();
		//循环等待客户端连接
		while(true) {
			if(selector.select(5000) == 0) {
				System.out.println("服务器等待5s, 无连接");
				continue;
			}
			Set<SelectionKey> selectionKeys = selector.selectedKeys();
			Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
			while(keyIterator.hasNext()) {
				SelectionKey key = keyIterator.next();
				if(key.isAcceptable()) {
					SocketChannel socketChannel = serverSocketChannel.accept();
					socketChannel.configureBlocking(false);
					socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
				}
				if(key.isReadable()) {
					SocketChannel socketChannel = (SocketChannel) key.channel();
					ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
					socketChannel.read(byteBuffer);
					System.out.println("从客户端读取内容:" + new String(byteBuffer.array()));
					socketChannel.close();
				}
				selectionKeys.remove(key);
			}
		}
	}

}
