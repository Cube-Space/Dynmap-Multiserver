package net.cubespace.dynmap.multiserver.HTTP.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.stream.ChunkedStream;
import net.cubespace.dynmap.multiserver.DynmapServer;
import net.cubespace.dynmap.multiserver.HTTP.HandlerUtil;
import net.cubespace.dynmap.multiserver.Main;
import net.cubespace.dynmap.multiserver.util.AbstractFile;

import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class FacesFileHandler implements IHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //Get the correct DynmapServer
        for (DynmapServer dynmapServer : Main.getDynmapServers()) {
            AbstractFile file = dynmapServer.getFile(request.uri());
            if (file.exists()) {
                if (file.isHidden() || !file.exists()) {
                    HandlerUtil.sendError(ctx, NOT_FOUND);
                    return;
                }

                if (!file.isFile()) {
                    HandlerUtil.sendError(ctx, FORBIDDEN);

                    return;
                }

                // Cache Validation
                String ifModifiedSince = request.headers().get(IF_MODIFIED_SINCE);
                if (ifModifiedSince != null && !ifModifiedSince.isEmpty()) {
                    SimpleDateFormat dateFormatter = new SimpleDateFormat(HandlerUtil.HTTP_DATE_FORMAT, Locale.US);
                    Date ifModifiedSinceDate = dateFormatter.parse(ifModifiedSince);

                    // Only compare up to the second because the datetime format we send to the client
                    // does not have milliseconds
                    long ifModifiedSinceDateSeconds = ifModifiedSinceDate.getTime() / 1000;
                    long fileLastModifiedSeconds = file.lastModified() / 1000;
                    if (ifModifiedSinceDateSeconds == fileLastModifiedSeconds) {
                        HandlerUtil.sendNotModified(ctx);
                        return;
                    }
                }

                ReadableByteChannel channel = Channels.newChannel(file.getInputStream());

                HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);

                HandlerUtil.setContentTypeHeader(response, file.getName());
                HandlerUtil.setDateAndCacheHeaders(response, file.lastModified());

                response.headers().set(CONTENT_LENGTH, file.length());
                response.headers().set(CONNECTION, HttpHeaderValues.CLOSE);
                response.headers().set(VARY, ACCEPT_ENCODING);

                // Write the initial line and the header.
                ctx.write(response);

                // Write the content.
                ctx.write(new ChunkedStream(file.getInputStream()));
                ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
                return;
            }
        }

        HandlerUtil.sendError(ctx, NOT_FOUND);
    }
}
