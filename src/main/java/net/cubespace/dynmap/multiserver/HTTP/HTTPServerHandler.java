package net.cubespace.dynmap.multiserver.HTTP;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import net.cubespace.dynmap.multiserver.HTTP.Handler.IHandler;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpMethod.POST;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.METHOD_NOT_ALLOWED;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class HTTPServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
    private HashMap<String, IHandler> handlers = new LinkedHashMap<>();

    @Override
    public void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //Check for bad Request
        if (!request.decoderResult().isSuccess()) {
            HandlerUtil.sendError(ctx, BAD_REQUEST);
            return;
        }

        //Check for correct HTTP Method (only GET and POST should work)
        if (request.method() != GET && request.method() != POST) {
            HandlerUtil.sendError(ctx, METHOD_NOT_ALLOWED);
            return;
        }

        //Check which handler should handle it
        final String uri = request.uri();
        String path = HandlerUtil.sanitizeUri(uri);
        if (path == null) {
            HandlerUtil.sendError(ctx, FORBIDDEN);
            return;
        }

        path = path.split("\\?")[0];

        request.setUri(path);

        for(Map.Entry<String, IHandler> handler : handlers.entrySet()) {
            Pattern pattern = Pattern.compile(handler.getKey());

            if(pattern.matcher(uri).find()) {
                handler.getValue().handle(ctx, request);
                return;
            }
        }

        HandlerUtil.sendError(ctx, INTERNAL_SERVER_ERROR);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        if (ctx.channel().isActive()) {
            HandlerUtil.sendError(ctx, INTERNAL_SERVER_ERROR);
        }
    }

    public void addHandler(String regex, IHandler handler) {
        handlers.put(regex, handler);
    }
}
