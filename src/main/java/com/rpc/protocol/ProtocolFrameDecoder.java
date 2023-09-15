package com.rpc.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class ProtocolFrameDecoder extends LengthFieldBasedFrameDecoder{
    public ProtocolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }

    public ProtocolFrameDecoder(){
        super(Integer.MAX_VALUE,4,4,9,0);
        System.out.println("解码器创建");
    }
}
