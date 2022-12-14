package com.yogovi.nio.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

	public static void main(String[] args) {
		/**
		 * bossGroup 和 workerGroup
		 * bossGroup --> accept
		 * workerGroup --> handler
		 * 都是无限循环
		 */
		NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
		NioEventLoopGroup workerGroup = new NioEventLoopGroup(3);
		try {
			//启动对象，配置参数
			ServerBootstrap bootstrap = new ServerBootstrap();
			bootstrap.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class) //使用NioServerSocketChannel作为服务器的通道实现
			.option(ChannelOption.SO_BACKLOG, 128) //线程队列连接个数
			.childOption(ChannelOption.SO_KEEPALIVE, true) //通道保持连接
			//.handler(null)
			.childHandler(new ChannelInitializer<SocketChannel>() { //workergroup EventLoop 对应的管道设置的处理器
				
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					//可以使用集合管理用户SocketChannel
					System.out.println("客户socketChannel hashCode = " + ch.hashCode());
					ch.pipeline().addLast(new NettyServerHandler());
				}
				
			});
			System.out.println("...服务器准备完毕...");
			//启动服务并绑定端口
			ChannelFuture channelFuture = bootstrap.bind(6668).sync();
			//添加监听器
			channelFuture.addListener(new ChannelFutureListener() {

				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if(future.isSuccess()) {
						System.out.println("监听6668端口成功");
					} else {
						System.out.println("监听6668端口失败");
					}
				}
				
			});
			//关闭通道进行监听
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
