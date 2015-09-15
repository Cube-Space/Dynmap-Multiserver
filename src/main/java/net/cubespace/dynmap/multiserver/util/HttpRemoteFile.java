package net.cubespace.dynmap.multiserver.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRemoteFile implements AbstractFile {
    private final String url;
    private final boolean exists;
    private final long length;
    private final HttpURLConnection connection;

    public HttpRemoteFile(String url) throws IOException {
        this.url = url;

        URL obj = new URL(url);
        connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        exists = responseCode != 404;
        length = connection.getContentLengthLong();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return connection.getInputStream();
    }

    @Override
    public boolean exists() {
        return exists;
    }

    @Override
    public long lastModified() {
        return 0;
    }

    @Override
    public long length() {
        return length;
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public boolean isFile() {
        return getName().contains(".");
    }

    @Override
    public String getName() {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }
}
