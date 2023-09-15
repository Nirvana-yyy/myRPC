package com.rpc.handler;

import com.rpc.common.RPCRequest;
import com.rpc.common.RPCResponse;
import com.rpc.service.ServiceProvider;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.val;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@ChannelHandler.Sharable
public class NettyRPCServerHandler extends SimpleChannelInboundHandler<RPCRequest> {
    private ServiceProvider serviceProvider;

    public NettyRPCServerHandler(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }


    @Override
    protected void channelRead0(ChannelHandlerContext cxt, RPCRequest msg) throws Exception {
        System.out.println(msg);
        RPCResponse response = getResponse(msg);
        cxt.writeAndFlush(response);
        cxt.close();
    }

    private RPCResponse getResponse(RPCRequest msg) {
        String interfaceName = msg.getInterfaceName();

        Object service = serviceProvider.getService(interfaceName);
        Method method = null;
        try {
            method = service.getClass().getMethod(msg.getMethodName(), msg.getParamsType());
            Object invoke = method.invoke(service, msg.getParams());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            RPCResponse response = RPCResponse.success(invoke);
            response.setRequestId(msg.getRequestId());
            return response;
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("方法执行错误");
            return RPCResponse.fail();
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }

}
