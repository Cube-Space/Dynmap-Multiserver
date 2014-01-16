package net.cubespace.dynmap.multiserver.HTTP.Handler;

import com.google.gson.Gson;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import net.cubespace.dynmap.multiserver.DynmapServer;
import net.cubespace.dynmap.multiserver.GSON.Component;
import net.cubespace.dynmap.multiserver.GSON.DynmapConfig;
import net.cubespace.dynmap.multiserver.GSON.DynmapWorld;
import net.cubespace.dynmap.multiserver.HTTP.HandlerUtil;
import net.cubespace.dynmap.multiserver.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.Names.IF_MODIFIED_SINCE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class DynmapConfigJSONHandler implements IHandler {
    static Logger logger = LoggerFactory.getLogger(DynmapConfigJSONHandler.class);

    private static DynmapConfigJSONUpdater dynmapConfigJSONUpdater;

    private static String responseStr;
    private static DynmapConfig config;
    private static Gson gson;
    private static long start = 0;

    private class DynmapConfigJSONUpdater extends Thread {
        public void run() {
            while(true) {
                try {
                    updateJson();
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public DynmapConfigJSONHandler() {
        if(gson == null) gson = new Gson();

        if(config == null) {
            config = new DynmapConfig();
            config.setDefaultmap("flat");
            config.setDefaultworld("world");
            config.setConfighash(0);

            responseStr = gson.toJson(config);
        }

        if(start == 0) start = System.currentTimeMillis();

        if(dynmapConfigJSONUpdater == null) {
            dynmapConfigJSONUpdater = new DynmapConfigJSONUpdater();
            dynmapConfigJSONUpdater.start();
        }
    }

    private void updateJson() {
        //Worlds
        DynmapWorld[] dynmapWorlds = new DynmapWorld[0];
        Component[] components = new Component[0];

        ArrayList<String> addedComponents = new ArrayList<>();

        config.setConfighash(0);
        String temp = gson.toJson(config);

        for(DynmapServer dynmapServer : Main.getDynmapServers()) {
            dynmapWorlds = concat(dynmapWorlds, dynmapServer.getWorlds());

            for(Component component : dynmapServer.getComponents()) {
                if(component != null) {
                    if(!addedComponents.contains(component.type)) {
                        addedComponents.add(component.type);

                        components = concat(components, new Component[]{component});
                    }
                }
            }
        }

        config.setWorlds(dynmapWorlds);
        config.setComponents(components);

        if(!responseStr.equals(temp)) {
            config.setConfighash(0);
            responseStr = gson.toJson(config);
            start = System.currentTimeMillis();
        }
    }

    private DynmapWorld[] concat(DynmapWorld[] A, DynmapWorld[] B) {
        int aLen = A.length;
        int bLen = B.length;
        DynmapWorld[] C= new DynmapWorld[aLen+bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
    }

    private Component[] concat(Component[] A, Component[] B) {
        int aLen = A.length;
        int bLen = B.length;
        Component[] C= new Component[aLen+bLen];
        System.arraycopy(A, 0, C, 0, aLen);
        System.arraycopy(B, 0, C, aLen, bLen);
        return C;
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
        response.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");
        response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
        response.headers().set(CONNECTION, HttpHeaders.Values.CLOSE);

        ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
    }
}
