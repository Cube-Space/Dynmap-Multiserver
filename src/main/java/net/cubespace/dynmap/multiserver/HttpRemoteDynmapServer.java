package net.cubespace.dynmap.multiserver;

import net.cubespace.dynmap.multiserver.Config.Dynmap;
import net.cubespace.dynmap.multiserver.util.AbstractFile;
import net.cubespace.dynmap.multiserver.util.HttpRemoteFile;

import java.io.File;
import java.io.IOException;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class HttpRemoteDynmapServer extends AbstractDynmapServer {
    private final String url;

    public HttpRemoteDynmapServer(Dynmap config) {
        super(config);
        this.url = config.Url;
    }

    @Override
    public AbstractFile getFile(String path) throws IOException {
        path = path.replace(File.separator, "/");
        if (url.endsWith("/")) {
            if (path.startsWith("/")) {
                return new HttpRemoteFile(url + path.substring(1));
            } else {
                return new HttpRemoteFile(url + path.substring(1));
            }
        } else {
            if (path.startsWith("/")) {
                return new HttpRemoteFile(url + path);
            } else {
                return new HttpRemoteFile(url + "/" + path);
            }
        }
    }
}
