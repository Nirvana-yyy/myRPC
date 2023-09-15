package com.rpc.protocol;

import com.extension.spi.extension.ExtensionLoader;
import com.rpc.common.RPCRequest;
import com.rpc.common.RPCResponse;
import com.rpc.service.pluginservice.SerializerService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author YJL
 */
public class MyEncode extends MessageToByteEncoder {

    private final AtomicInteger count = new AtomicInteger(0);

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {

        SerializerService serialize = ExtensionLoader.getExtensionLoader(SerializerService.class).getActivate("serialize");

        //序列化
        byte[] serializer = serialize.serializer(msg);

        ByteBuf buf = ctx.alloc().buffer();
        //魔数 4
        buf.writeBytes("FUCK".getBytes(StandardCharsets.UTF_8));
        //消息长度 4
        buf.writeInt(serializer.length);
        //头长度 2
        buf.writeShort(17);
        //协议版本 1
        buf.writeByte(1);
        //消息类型 1
        if(msg instanceof RPCRequest){
            buf.writeByte(0);//0表示Request
        }
        if (msg instanceof RPCResponse){
            buf.writeByte(1);//1表示Response
        }
        //序列化方式 1
        buf.writeByte(1);
        //消息id 4
        buf.writeInt(count.incrementAndGet());

        buf.writeBytes(serializer);
        out.writeBytes(buf);
        System.out.println("序列化消息");
    }
}
