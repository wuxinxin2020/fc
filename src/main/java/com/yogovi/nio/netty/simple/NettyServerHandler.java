package com.yogovi.nio.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyServerHandler extends ChannelInboundHandlerAdapter{

	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("server ctx =" + ctx);
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("客户端发送消息是:" + buf.toString(CharsetUtil.UTF_8));
		System.out.println("客户端地址:" + ctx.channel().remoteAddress());
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
