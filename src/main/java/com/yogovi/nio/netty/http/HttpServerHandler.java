package com.yogovi.nio.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
		
		//HttpObject 客户端和服务器端通信数据
		if(msg instanceof HttpRequest) {
			//客户端信息
			System.out.println("msg 类型:" + msg.getClass());
			System.out.println("客户端地址:" + ctx.channel().remoteAddress());
			//
			HttpRequest httpRequest = (HttpRequest) msg;
			String uri = httpRequest.uri();
			if("/favicon.ico".equals(uri)) {
				return;
			}
			//回复客户端
			ByteBuf content= Unpooled.copiedBuffer("welcome to netty server", CharsetUtil.UTF_8);
			//构造http响应
			FullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
			httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
			httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());
			//返回httpResponse
			ctx.writeAndFlush(httpResponse);
		}
	}

}
