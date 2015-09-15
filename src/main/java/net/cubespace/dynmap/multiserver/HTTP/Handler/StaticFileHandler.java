package net.cubespace.dynmap.multiserver.HTTP.Handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.*;
import net.cubespace.dynmap.multiserver.HTTP.HandlerUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class StaticFileHandler implements IHandler {
    private ArrayList<String> indexFiles = new ArrayList<>();
    private String webDir = System.getProperty("user.dir");

    @Override
    public void handle(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //Check for index
        final String path = webDir + File.separator + request.getUri();
        final String uri = request.getUri();
        if (uri.endsWith("/")) {
            for (String index : indexFiles) {
                File checkFile = new File(path, index);
                if (checkFile.exists()) {
                    HandlerUtil.sendRedirect(ctx, uri + index);
                    return;
                }
            }
        }

        File file = new File(path.replace("/", File.separator));
        if (file.isHidden() || !file.exists()) {
            HandlerUtil.sendError(ctx, NOT_FOUND);
            return;
        }

        if (file.isDirectory()) {
            if (uri.endsWith("/")) {
                HandlerUtil.sendError(ctx, FORBIDDEN);
            } else {
                HandlerUtil.sendRedirect(ctx, uri + '/');
            }

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

        HandlerUtil.setContentTypeHeader(response, file.getName());
        HandlerUtil.setDateAndCacheHeaders(response, file.lastModified());

        response.headers().set(CONTENT_LENGTH, fileLength);
        response.headers().set(CONNECTION, HttpHeaders.Values.CLOSE);
        response.headers().set(VARY, ACCEPT_ENCODING);

        // Write the initial line and the header.
        ctx.write(response);

        // Write the content.
        ctx.write(new DefaultFileRegion(raf.getChannel(), 0, fileLength));
        ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);
    }

    public void addIndex(String file) {
        indexFiles.add(file);
    }

    public void setWebDir(String path) {
        webDir = path;
    }
}
