package net.cubespace.dynmap.multiserver.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LocalAbstractFile implements AbstractFile {
    private final File file;

    public LocalAbstractFile(File file) {
        this.file = file;
    }

    @Override
    public InputStream getInputStream() throws FileNotFoundException {
        return new FileInputStream(file);
    }

    @Override
    public boolean exists() {
        return file.exists();
    }

    @Override
    public long getLastModified() {
        return file.lastModified();
    }
}
