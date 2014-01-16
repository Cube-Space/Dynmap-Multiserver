package net.cubespace.dynmap.multiserver.Lib.Config;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class InvalidConfigurationException extends Exception {
    public InvalidConfigurationException() {}

    public InvalidConfigurationException(String msg) {
        super(msg);
    }

    public InvalidConfigurationException(Throwable cause) {
        super(cause);
    }

    public InvalidConfigurationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
