package net.cubespace.dynmap.multiserver.GSON;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class URLConfig {
    private final String configuration;
    private final String update;
    private final String sendmessage;
    private final String login;
    private final String register;
    private final String tiles;
    private final String markers;

    public URLConfig(String configuration, String update, String sendmessage, String login, String register, String tiles, String markers) {
        this.configuration = configuration;
        this.update = update;
        this.sendmessage = sendmessage;
        this.login = login;
        this.register = register;
        this.tiles = tiles;
        this.markers = markers;
    }

    public String getConfiguration() {
        return configuration;
    }

    public String getUpdate() {
        return update;
    }

    public String getSendmessage() {
        return sendmessage;
    }

    public String getLogin() {
        return login;
    }

    public String getTiles() {
        return tiles;
    }

    public String getMarkers() {
        return markers;
    }
}
