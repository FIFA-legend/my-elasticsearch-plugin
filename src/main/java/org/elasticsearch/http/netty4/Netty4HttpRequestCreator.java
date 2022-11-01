package org.elasticsearch.http.netty4;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import org.elasticsearch.ExceptionsHelper;

import java.util.List;

@ChannelHandler.Sharable
class Netty4HttpRequestCreator extends MessageToMessageDecoder<FullHttpRequest> {

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest msg, List<Object> out) {
        if (msg.decoderResult().isFailure()) {
            final Throwable cause = msg.decoderResult().cause();
            final Exception nonError;
            if (cause instanceof Error) {
                ExceptionsHelper.maybeDieOnAnotherThread(cause);
                nonError = new Exception(cause);
            } else {
                nonError = (Exception) cause;
            }
            out.add(new Netty4HttpRequest(msg.retain(), nonError));
        } else {
            out.add(new Netty4HttpRequest(msg.retain()));
        }
    }
}
