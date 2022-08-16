package com.yogovi.nio.netty.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {

	public static void main(String[] args) {
		//事件循环组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			//客户端启动对象
			Bootstrap bootstrap = new Bootstrap();
			//设置参数
			bootstrap.group(group)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new NettyClientHandler());
				}
			});
			System.out.println("客户端完毕");
			//连接服务端
			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6668).sync();
			//
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
	
}
