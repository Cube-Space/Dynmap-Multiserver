package net.cubespace.dynmap.multiserver.GSON;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Config {
    private final URLConfig url;

    public Config(URLConfig url) {
        this.url = url;
    }

    public URLConfig getUrl() {
        return url;
    }
}
