package net.cubespace.dynmap.multiserver;

import net.cubespace.dynmap.multiserver.Config.Dynmap;
import net.cubespace.dynmap.multiserver.util.AbstractFile;
import net.cubespace.dynmap.multiserver.util.LocalFile;

import java.io.File;

public class LocalDynmapServer extends AbstractDynmapServer {
    private File file;

    public LocalDynmapServer(Dynmap config) {
        super(config);
        this.file = new File(config.Folder);
    }

    @Override
    public AbstractFile getFile(String path) {
        return new LocalFile(new File(file, path.replace("/", File.separator)));
    }
}
