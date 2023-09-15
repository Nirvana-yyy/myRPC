package com.rpc.protocol;

import com.extension.spi.extension.ExtensionLoader;
import com.rpc.common.RPCRequest;
import com.rpc.common.RPCResponse;

import com.rpc.service.pluginservice.SerializerService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class MyDecode extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf magicNum = in.readBytes(new byte[4]);

        int contentLength = in.readInt();
        short headLength = in.readShort();
        byte version = in.readByte();
        byte type = in.readByte();

        byte serializeType = in.readByte();
        int msgId = in.readInt();
        byte[] bytes = new byte[contentLength];
        in.readBytes(bytes,0,contentLength);
        Object deserialize = null;
        //Request
        SerializerService serialize = ExtensionLoader.getExtensionLoader(SerializerService.class).getActivate("serialize");
        if (type == 0){
            deserialize = serialize.deserialize(bytes, RPCRequest.class);
        }
        else if (type == 1){
            deserialize = serialize.deserialize(bytes, RPCResponse.class);
        }
        out.add(deserialize);
        System.out.println("反序列化："+deserialize);
    }
}
