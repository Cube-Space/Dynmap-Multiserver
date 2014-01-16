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
import net.cubespace.dynmap.multiserver.GSON.DynmapWorld;
import net.cubespace.dynmap.multiserver.GSON.DynmapWorldConfig;
import net.cubespace.dynmap.multiserver.HTTP.HandlerUtil;
import net.cubespace.dynmap.multiserver.Main;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.NOT_FOUND;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class MapConfigHandler implements IHandler {
    private Gson gson = new Gson();

    @Override
    public void handle(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        //Get the correct DynmapServer
        String world = request.getUri().split("/")[3].split("\\.")[0];
        for(DynmapServer dynmapServer : Main.getDynmapServers()) {
            for(DynmapWorld dynmapWorld : dynmapServer.getWorlds()) {
                if(dynmapWorld.getName().equals(world)) {
                    DynmapWorldConfig dynmapWorldConfig = dynmapServer.getWorldConfig(dynmapWorld.getName());
                    dynmapWorldConfig.setConfighash(0);



                    String responseStr = gson.toJson(dynmapWorldConfig);

                    FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(responseStr.getBytes()));
                    response.headers().set(CONTENT_TYPE, "application/json; charset=UTF-8");
                    response.headers().set(CONTENT_LENGTH, response.content().readableBytes());
                    response.headers().set(CONNECTION, HttpHeaders.Values.CLOSE);

                    ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                    return;
                }
            }
        }

        HandlerUtil.sendError(ctx, NOT_FOUND);
    }
}
