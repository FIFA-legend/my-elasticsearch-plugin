package org.elasticsearch.http.netty4;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.elasticsearch.ExceptionsHelper;
import org.elasticsearch.http.HttpPipelinedRequest;

@ChannelHandler.Sharable
class Netty4HttpRequestHandler extends SimpleChannelInboundHandler<HttpPipelinedRequest> {

    private final Netty4HttpServerTransport serverTransport;

    Netty4HttpRequestHandler(Netty4HttpServerTransport serverTransport) {
        this.serverTransport = serverTransport;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpPipelinedRequest httpRequest) {
        final Netty4HttpChannel channel = ctx.channel().attr(Netty4HttpServerTransport.HTTP_CHANNEL_KEY).get();
        boolean success = false;
        try {
            serverTransport.incomingRequest(httpRequest, channel);
            success = true;
        } finally {
            if (success == false) {
                httpRequest.release();
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ExceptionsHelper.maybeDieOnAnotherThread(cause);
        Netty4HttpChannel channel = ctx.channel().attr(Netty4HttpServerTransport.HTTP_CHANNEL_KEY).get();
        if (cause instanceof Error) {
            serverTransport.onException(channel, new Exception(cause));
        } else {
            serverTransport.onException(channel, (Exception) cause);
        }
    }
}
