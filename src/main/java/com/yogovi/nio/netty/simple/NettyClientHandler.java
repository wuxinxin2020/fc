package com.yogovi.nio.netty.simple;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter{
	
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("client ctx = " + ctx);
		ctx.writeAndFlush(Unpooled.copiedBuffer("hello 服务器", CharsetUtil.UTF_8));
	}
	
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		Date current = new Date();
		System.out.println(SDF.format(current) + "-服务器回复的消息:" + buf.toString(CharsetUtil.UTF_8));
		System.out.println("服务器地址:" + ctx.channel().remoteAddress());
	}
	
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
	
}
