package com.rpc.server;

import com.rpc.handler.NettyServerInitializer;
import com.rpc.service.ServiceProvider;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author YJL
 */
@Slf4j
@AllArgsConstructor
public class NettyRPCServer implements RPCserver{

    private ServiceProvider  serviceProvider;


    @Override
    public void start(int port) {
        //netty服务端线程负责奖励连接（TCP/IP连接），worker负责具体的请求
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        log.debug("服务器启动了");
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer(serviceProvider));
            //同步阻塞
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    @Override
    public void stop() {

    }
}
