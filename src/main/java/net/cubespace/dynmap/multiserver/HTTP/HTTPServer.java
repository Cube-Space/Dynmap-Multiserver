package net.cubespace.dynmap.multiserver.HTTP;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.cubespace.dynmap.multiserver.Config.Main;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class HTTPServer extends Thread {
    private final int port;
    private final Main config;

    public HTTPServer(Main config) {
        this.port = config.Webserver_Port;
        this.config = config;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(4);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HTTPServerInitializer(config));

            b.bind(port).sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {

        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
