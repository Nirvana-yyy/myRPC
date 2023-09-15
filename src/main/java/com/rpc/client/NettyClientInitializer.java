package com.rpc.client;

import com.rpc.handler.NettyClientHandler;
import com.rpc.protocol.MyDecode;
import com.rpc.protocol.MyEncode;
import com.rpc.protocol.ProtocolFrameDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;


public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline pipeline = channel.pipeline();
        //解码器
        pipeline.addLast(new ProtocolFrameDecoder());
        //打印消息日志
        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));

        pipeline.addLast(new MyDecode());
        pipeline.addLast(new MyEncode());
        //pipeline.addLast(new MessageCodec());

        pipeline.addLast(new NettyClientHandler());


    }
}
