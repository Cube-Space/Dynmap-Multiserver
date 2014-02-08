package net.cubespace.dynmap.multiserver;

import net.cubespace.dynmap.multiserver.Config.Dynmap;
import net.cubespace.dynmap.multiserver.GSON.ComponentDeserializer;
import net.cubespace.dynmap.multiserver.GSON.Components.Chat;
import net.cubespace.dynmap.multiserver.GSON.Components.ChatBallon;
import net.cubespace.dynmap.multiserver.GSON.Components.Chatbox;
import net.cubespace.dynmap.multiserver.GSON.Components.Clock;
import net.cubespace.dynmap.multiserver.GSON.Components.Link;
import net.cubespace.dynmap.multiserver.GSON.Components.Location;
import net.cubespace.dynmap.multiserver.GSON.Components.PlayerMarkers;
import net.cubespace.dynmap.multiserver.GSON.Components.Spawn;
import net.cubespace.dynmap.multiserver.GSON.Player;
import net.cubespace.dynmap.multiserver.HTTP.HTTPServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;

import static net.cubespace.dynmap.multiserver.Lib.Util.Concat.concat;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Main {
    static Logger logger = LoggerFactory.getLogger(Main.class);

    private static ArrayList<DynmapServer> dynmapServers = new ArrayList<>();
    private static String dynmapVersion;
    private static String coreVersion;

    public static void main(String[] args) {
        //Init the Logger
        logger.info("Booting up Dynmap-MultiServer v0.2.5");
        logger.info("Running on Java Version: " + System.getProperty("java.version") + " " + System.getProperty("os.arch"));
        logger.info("Running on OS: " + System.getProperty("os.name"));

        //Init the Config
        logger.info("Getting the config...");
        net.cubespace.dynmap.multiserver.Config.Main config = new net.cubespace.dynmap.multiserver.Config.Main();
        config.init();

        //Normalize the WebPath
        File file = new File(System.getProperty("user.dir"), config.Webserver_webDir);

        if (!file.exists()) {
            file.mkdirs();

            logger.error("Please install a stripped Web Directory from Dynmap into the Webdir");
            System.exit(-1);
        }

        logger.info("Config holds Information for " + config.DynMap.size() + " DynMap(s)");

        //Register all Components
        ComponentDeserializer.addComponent(new Spawn());
        ComponentDeserializer.addComponent(new PlayerMarkers());
        ComponentDeserializer.addComponent(new Chat());
        ComponentDeserializer.addComponent(new ChatBallon());
        ComponentDeserializer.addComponent(new Clock());
        ComponentDeserializer.addComponent(new Location());
        ComponentDeserializer.addComponent(new Chatbox());
        ComponentDeserializer.addComponent(new Link());

        //Load up the Dynmaps
        logger.info("Loading the Dynmaps");

        for (Dynmap dynmap : config.DynMap) {
            logger.info("Booting up Dynmap " + dynmap.Folder);

            try {
                DynmapServer dynmapServer = new DynmapServer(dynmap);
                dynmapServers.add(dynmapServer);
            } catch (DynmapInitException e) {
                logger.error("Could not boot up this Dynmap", e);
                System.exit(-1);
            }
        }

        //Start up the Webserver
        HTTPServer httpServer = new HTTPServer(config);
        httpServer.start();
    }

    public static ArrayList<DynmapServer> getDynmapServers() {
        return new ArrayList<>(dynmapServers);
    }

    public static Player[] getPlayers() {
        Player[] players = new Player[0];
        for(DynmapServer dynmapServer : dynmapServers) {
            players = concat(players, dynmapServer.getPlayers());
        }

        return players;
    }

    public static synchronized void updateCoreVersion(String coreVersion) {
        if(Main.coreVersion != null) {
            if(!Main.coreVersion.equals(coreVersion)) {
                logger.error("You use different Versions of Dynmaps");
                System.exit(-1);
            }
        } else {
            Main.coreVersion = coreVersion;
        }
    }

    public static synchronized void updateDynmapVersion(String dynmapVersion) {
        if(Main.dynmapVersion != null) {
            if(!Main.dynmapVersion.equals(dynmapVersion)) {
                logger.error("You use different Versions of Dynmaps");
                System.exit(-1);
            }
        } else {
            Main.dynmapVersion = dynmapVersion;
        }
    }

    public static String getDynmapVersion() {
        return dynmapVersion;
    }

    public static String getCoreVersion() {
        return coreVersion;
    }
}
