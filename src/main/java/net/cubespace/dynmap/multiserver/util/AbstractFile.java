package net.cubespace.dynmap.multiserver.util;

import java.io.IOException;
import java.io.InputStream;

public interface AbstractFile {
    InputStream getInputStream() throws IOException;

    boolean exists();

    long getLastModified();
}
