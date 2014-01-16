package net.cubespace.dynmap.multiserver;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class DynmapInitException extends Exception {
    /**
     * Creates a new instance of DynmapInitException without a message or cause.
     */
    public DynmapInitException() {}

    /**
     * Constructs an instance of DynmapInitException with the specified message.
     *
     * @param msg The details of the exception.
     */
    public DynmapInitException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of DynmapInitException with the specified cause.
     *
     * @param cause The cause of the exception.
     */
    public DynmapInitException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an instance of DynmapInitException with the specified message and cause.
     *
     * @param cause The cause of the exception.
     * @param msg The details of the exception.
     */
    public DynmapInitException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
