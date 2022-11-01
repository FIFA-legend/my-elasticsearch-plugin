package org.elasticsearch.transport.netty4;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

final class ESLoggingHandler extends LoggingHandler {

    ESLoggingHandler() {
        super(LogLevel.TRACE);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // We do not want to log read complete events because we log inbound messages in the TcpTransport.
        ctx.fireChannelReadComplete();
    }
}
