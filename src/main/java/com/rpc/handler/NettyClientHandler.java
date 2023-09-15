package com.rpc.handler;

import com.rpc.client.UnprocessedRequests;
import com.rpc.common.RPCResponse;
import com.rpc.util.SingletonFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

@ChannelHandler.Sharable
public class NettyClientHandler extends SimpleChannelInboundHandler<RPCResponse> {
    private final UnprocessedRequests unprocessedRequests;
    public NettyClientHandler(){
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCResponse o) throws Exception {
        System.out.println("建立客户端与服务端的channel");

//        AttributeKey<Object> key = AttributeKey.valueOf("RPCResponse");
//        channelHandlerContext.channel().attr(key).set(o);
        unprocessedRequests.complete(o);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
