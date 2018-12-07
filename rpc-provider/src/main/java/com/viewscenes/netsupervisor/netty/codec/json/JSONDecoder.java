package com.viewscenes.netsupervisor.netty.codec.json;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @program: rpc-provider
 * @description: ${description}
 * @author: shiqizhen
 * @create: 2018-12-07 10:00
 **/
public class JSONDecoder extends LengthFieldBasedFrameDecoder {

    public JSONDecoder() {
        super(65535, 0, 4,0,4);
    }

    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf decode = (ByteBuf) super.decode(ctx, in);
        if (decode==null){
            return null;
        }
        int data_len = decode.readableBytes();
        byte[] bytes = new byte[data_len];
        decode.readBytes(bytes);
        Object parse = JSON.parse(bytes);
        return parse;
    }
}
