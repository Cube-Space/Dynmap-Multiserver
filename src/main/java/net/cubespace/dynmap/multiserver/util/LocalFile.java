package net.cubespace.dynmap.multiserver.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LocalFile implements AbstractFile {
    private final File file;

    public LocalFile(File file) {
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
    public long lastModified() {
        return file.lastModified();
    }

    @Override
    public long length() {
        return file.length();
    }

    @Override
    public boolean isHidden() {
        return file.isHidden();
    }

    @Override
    public boolean isFile() {
        return file.isFile();
    }

    @Override
    public String getName() {
        return file.getName();
    }
}
