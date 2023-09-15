package com.rpc.handler;

import com.rpc.protocol.MyDecode;
import com.rpc.protocol.MyEncode;
import com.rpc.protocol.ProtocolFrameDecoder;
import com.rpc.service.ServiceProvider;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private ServiceProvider serviceProvider;

    public NettyServerInitializer(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //解码器
        pipeline.addLast(new ProtocolFrameDecoder());
        //打印消息日志
        pipeline.addLast(new LoggingHandler(LogLevel.DEBUG));
        pipeline.addLast(new MyDecode());
        pipeline.addLast(new MyEncode());
        //pipeline.addLast(new MessageCodec());
        //调用方法
        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));


    }
}
