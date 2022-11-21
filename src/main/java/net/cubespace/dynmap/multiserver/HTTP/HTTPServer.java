package net.cubespace.dynmap.multiserver.HTTP;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import net.cubespace.dynmap.multiserver.Config.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class HTTPServer extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(HTTPServer.class);
    private final String ip;
    private final int port;
    private final Main config;
    private final int workerThreads;

    public HTTPServer(Main config) {
        this.ip = config.Webserver_IP;
        this.port = config.Webserver_Port;
        this.workerThreads = config.Webserver_WorkerThreads;
        this.config = config;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup(workerThreads);

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HTTPServerInitializer(config));

            b.bind(ip, port).sync().channel().closeFuture().sync();
            logger.info("Bound to " + ip + ":" + port);
        } catch (InterruptedException e) {
            logger.error("Could not bind to that IP", e);
            System.exit(-1);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
