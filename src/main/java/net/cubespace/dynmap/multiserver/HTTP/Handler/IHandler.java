package net.cubespace.dynmap.multiserver.HTTP.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public interface IHandler {
    public void handle(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception;
}
