package com.yogovi.nio.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		//Pipeline
		ChannelPipeline channelPipeline = ch.pipeline();
		//编解码器
		channelPipeline.addLast("httpServerCodec", new HttpServerCodec());
		//自定义Handler
		channelPipeline.addLast("httpServerHandler", new HttpServerHandler());
	}

}
