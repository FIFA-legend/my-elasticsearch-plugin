package org.elasticsearch.transport;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

@ChannelHandler.Sharable
public class NettyByteBufSizer extends MessageToMessageDecoder<ByteBuf> {

    public static final NettyByteBufSizer INSTANCE = new NettyByteBufSizer();

    private NettyByteBufSizer() {
        // sharable singleton
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) {
        int readableBytes = buf.readableBytes();
        if (buf.capacity() >= 1024) {
            ByteBuf resized = buf.discardReadBytes().capacity(readableBytes);
            assert resized.readableBytes() == readableBytes;
            out.add(resized.retain());
        } else {
            out.add(buf.retain());
        }
    }
}
