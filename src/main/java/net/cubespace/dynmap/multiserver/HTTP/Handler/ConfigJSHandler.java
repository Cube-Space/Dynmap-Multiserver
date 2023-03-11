package net.cubespace.dynmap.multiserver.HTTP.Handler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderValues;
import net.cubespace.dynmap.multiserver.HTTP.HandlerUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaderNames.IF_MODIFIED_SINCE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ConfigJSHandler implements IHandler {
    private String responseStr;
    private long start;

    public ConfigJSHandler() {
        responseStr = "var config = {\n" +
                " url : {\n" +
                "  configuration: 'standalone/dynmap_config.json?_={timestamp}',\n" +
                "  update: 'standalone/world/{world}.json?_={timestamp}',\n" +
                "  sendmessage: '',\n" +
                "  login: '',\n" +
                "  register: '',\n" +
                "  tiles: 'tiles/',\n" +
                "  markers: 'tiles/'\n" +
                " }\n" +
                "};\n";

        start = System.currentTimeMillis();
    }

    @Override
    public void handle(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        // Cache Validation
        String ifModifiedSince = request.headers().get(IF_MODIFIED_SINCE);
        if (ifModifiedSince != null && !ifModifiedSince.isEmpty()) {
            SimpleDateFormat dateFormatter = new SimpleDateFormat(HandlerUtil.HTTP_DATE_FORMAT, Locale.US);
            Date ifModifiedSinceDate = dateFormatter.parse(ifModifiedSince);

            // Only compare up to the second because the datetime format we send to the client
            // does not have milliseconds
            long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
            long fileLastModifiedSeconds =  start / 1000;
            if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
                HandlerUtil.sendNotModified(ctx);
                return;
            }
        }

        FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(responseStr.getBytes()));
        HandlerUtil.setDateAndCacheHeaders(response, start);
        response.headers().set(CONTENT_TYPE, "application/javascript; charset=UTF-8");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(CONNECTION, HttpHeaderValues.CLOSE);

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
