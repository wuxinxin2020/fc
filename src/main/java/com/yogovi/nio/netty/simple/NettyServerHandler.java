package com.yogovi.nio.netty.simple;

import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutor;

public class NettyServerHandler extends ChannelInboundHandlerAdapter{

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		System.out.println("服务器读取线程:" + Thread.currentThread().getName());
//		System.out.println("server ctx =" + ctx);
//		Channel channel = ctx.channel();
//		ChannelPipeline pipeline = ctx.pipeline();
//		
//		ByteBuf buf = (ByteBuf) msg;
//		System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
//		System.out.println("客户端地址:" + channel.remoteAddress());
		
		
		EventLoop eventLoop = ctx.channel().eventLoop();
		eventLoop.execute(()->{
			try {
				TimeUnit.SECONDS.sleep(2);
				ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端1", CharsetUtil.UTF_8));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		eventLoop.execute(()->{
			try {
				TimeUnit.SECONDS.sleep(3);
				ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端2", CharsetUtil.UTF_8));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		eventLoop.schedule(()-> {
			ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端3", CharsetUtil.UTF_8));
		}, 6, TimeUnit.SECONDS);
		
		System.out.println("go on ...");
	}
	
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		/**
		 * 读取完回消息
		 */
		ctx.writeAndFlush(Unpooled.copiedBuffer("hello 客户端", CharsetUtil.UTF_8));
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		ctx.close();
	}
	
}
