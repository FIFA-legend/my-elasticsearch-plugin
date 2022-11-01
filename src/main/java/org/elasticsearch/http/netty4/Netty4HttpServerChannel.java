package org.elasticsearch.http.netty4;

import io.netty.channel.Channel;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.core.CompletableContext;
import org.elasticsearch.http.HttpServerChannel;
import org.elasticsearch.transport.netty4.Netty4TcpChannel;

import java.net.InetSocketAddress;

public class Netty4HttpServerChannel implements HttpServerChannel {

    private final Channel channel;
    private final CompletableContext<Void> closeContext = new CompletableContext<>();

    Netty4HttpServerChannel(Channel channel) {
        this.channel = channel;
        Netty4TcpChannel.addListener(this.channel.closeFuture(), closeContext);
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        return (InetSocketAddress) channel.localAddress();
    }

    @Override
    public void addCloseListener(ActionListener<Void> listener) {
        closeContext.addListener(ActionListener.toBiConsumer(listener));
    }

    @Override
    public boolean isOpen() {
        return channel.isOpen();
    }

    @Override
    public void close() {
        channel.close();
    }

    @Override
    public String toString() {
        return "Netty4HttpChannel{localAddress=" + getLocalAddress() + "}";
    }
}
