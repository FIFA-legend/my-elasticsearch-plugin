package org.elasticsearch.transport;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.nio.channels.SocketChannel;

/**
 * Helper class to expose {@link #javaChannel()} method
 */
public class Netty4NioSocketChannel extends NioSocketChannel {

    public Netty4NioSocketChannel() {
        super();
    }

    public Netty4NioSocketChannel(Channel parent, SocketChannel socket) {
        super(parent, socket);
    }

    @Override
    public SocketChannel javaChannel() {
        return super.javaChannel();
    }

}
