package net.cubespace.dynmap.multiserver.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRemoteFile implements AbstractFile {
    private final String url;
    private final boolean exists;
    private final long length;
    private final long lastModified;
    private final HttpURLConnection connection;

    public HttpRemoteFile(String url) throws IOException {
        this.url = url;

        URL obj = new URL(url);
        connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        int responseCode = connection.getResponseCode();
        exists = responseCode != 404;
        length = connection.getContentLengthLong();
        lastModified = connection.getLastModified();
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return new InputStream() {
            private InputStream inputStream = connection.getInputStream();

            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public int read(byte[] b) throws IOException {
                return inputStream.read(b);
            }

            @Override
            public int read(byte[] b, int off, int len) throws IOException {
                return inputStream.read(b, off, len);
            }

            @Override
            public long skip(long n) throws IOException {
                return inputStream.skip(n);
            }

            @Override
            public int available() throws IOException {
                return inputStream.available();
            }

            @Override
            public void close() throws IOException {
                try {
                    inputStream.close();
                } finally {
                    connection.disconnect();
                }
            }

            @Override
            public synchronized void mark(int readlimit) {
                inputStream.mark(readlimit);
            }

            @Override
            public synchronized void reset() throws IOException {
                inputStream.reset();
            }

            @Override
            public boolean markSupported() {
                return inputStream.markSupported();
            }
        };
    }

    @Override
    public boolean exists() {
        return exists;
    }

    @Override
    public long lastModified() {
        return lastModified;
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
