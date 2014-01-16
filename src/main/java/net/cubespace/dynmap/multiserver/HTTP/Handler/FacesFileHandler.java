package net.cubespace.dynmap.multiserver.HTTP.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import net.cubespace.dynmap.multiserver.DynmapServer;
import net.cubespace.dynmap.multiserver.HTTP.HandlerUtil;
import net.cubespace.dynmap.multiserver.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static io.netty.handler.codec.http.HttpHeaders.Names.ACCEPT_ENCODING;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.IF_MODIFIED_SINCE;
import static io.netty.handler.codec.http.HttpHeaders.Names.VARY;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class FacesFileHandler implements IHandler {
    @Override
    public void handle(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //Get the correct DynmapServer
        for(DynmapServer dynmapServer : Main.getDynmapServers()) {
            File file = new File(dynmapServer.getFolder(), request.getUri());
            if(file.exists()) {
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

                RandomAccessFile raf;
                try {
                    raf = new RandomAccessFile(file, "r");
                } catch (FileNotFoundException fnfe) {
                    HandlerUtil.sendError(ctx, NOT_FOUND);
                    return;
                }

                long fileLength = raf.length();

                HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);

                HandlerUtil.setContentTypeHeader(response, file);
                HandlerUtil.setDateAndCacheHeaders(response, file.lastModified());

                response.headers().set(CONTENT_LENGTH, fileLength);
                response.headers().set(CONNECTION, HttpHeaders.Values.CLOSE);
                response.headers().set(VARY, ACCEPT_ENCODING);

                // Write the initial line and the header.
                ctx.write(response);

                // Write the content.
                ctx.write(new DefaultFileRegion(raf.getChannel(), 0, fileLength));
                ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
                return;
            }
        }

        HandlerUtil.sendError(ctx, NOT_FOUND);
    }
}
