package com.rpc.client;

import com.extension.spi.extension.ExtensionLoader;
import com.rpc.common.RPCRequest;
import com.rpc.common.RPCResponse;
import com.rpc.service.register.ServiceRegistry;
import com.rpc.util.SingletonFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.concurrent.CompletableFuture;

/**
 * @author YJL
 */
@Slf4j
public class NettyRPCClient implements RPCClient{

    private static final Bootstrap bootstrap;
    private static final EventLoopGroup eventLoopGroup;
    private String host;
    private int port;
    private ServiceRegistry serviceRegistry;
    private final UnprocessedRequests unprocessedRequests;


    public NettyRPCClient(){
        this.unprocessedRequests = SingletonFactory.getInstance(UnprocessedRequests.class);
        this.serviceRegistry = ExtensionLoader.getExtensionLoader(ServiceRegistry.class).getActivate("nacos");
    }

    static {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }

    @Override
    public CompletableFuture sendRequest(RPCRequest request) {
        InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(request.getInterfaceName());
        host = inetSocketAddress.getHostName();
        port = inetSocketAddress.getPort();
        try {
            ChannelFuture channelFuture = bootstrap.connect(host,port).sync();
            Channel channel = channelFuture.channel();
            CompletableFuture<RPCResponse> resultFuture = new CompletableFuture<>();
            if (channel != null && channel.isActive()) {
                //放入未处理的请求
                unprocessedRequests.put(request.getRequestId(), resultFuture);
                channel.writeAndFlush(request);
            }else{
                throw new IllegalStateException();
            }

//            //channel.writeAndFlush("艹".getBytes(StandardCharsets.UTF_8));
//            channel.closeFuture().sync();
//            AttributeKey<Object> key = AttributeKey.valueOf("RPCResponse");
//            RPCResponse response = (RPCResponse) channel.attr(key).get();
//            System.out.println(response);
            return resultFuture;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
