package net.cubespace.dynmap.multiserver.GSON;

import com.google.gson.JsonObject;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public abstract class Component {
    public String type;

    public abstract boolean isComponent(JsonObject jsonObject);
}
