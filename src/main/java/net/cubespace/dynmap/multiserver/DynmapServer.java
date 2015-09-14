package net.cubespace.dynmap.multiserver;

import net.cubespace.dynmap.multiserver.GSON.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface DynmapServer {
    Collection<DynmapWorld> getWorlds();

    DynmapWorldConfig getWorldConfig(String name);

    DynmapConfig getDynmapConfig();

    Collection<Component> getComponents();

    Collection<Player> getPlayers();

    InputStream getAsInputStream(String path) throws IOException;
}
