package org.elasticsearch.transport;

import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.internal.SocketUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.channels.SocketChannel;
import java.util.List;

/**
 * This class is adapted from {@link NioServerSocketChannel} class in the Netty project. It overrides the
 * channel read messages behavior to ensure that a {@link CopyBytesSocketChannel} socket channel is created.
 */
public class CopyBytesServerSocketChannel extends NioServerSocketChannel {

    private static final Logger logger = LogManager.getLogger(CopyBytesServerSocketChannel.class);

    @Override
    protected int doReadMessages(List<Object> buf) throws Exception {
        SocketChannel ch = SocketUtils.accept(javaChannel());

        try {
            if (ch != null) {
                buf.add(new CopyBytesSocketChannel(this, ch));
                return 1;
            }
        } catch (Throwable t) {
            logger.warn("Failed to create a new channel from an accepted socket.", t);

            try {
                ch.close();
            } catch (Throwable t2) {
                logger.warn("Failed to close a socket.", t2);
            }
        }

        return 0;
    }
}