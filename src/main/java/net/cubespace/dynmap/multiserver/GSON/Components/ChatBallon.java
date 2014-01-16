package net.cubespace.dynmap.multiserver.GSON.Components;

import com.google.gson.JsonObject;
import net.cubespace.dynmap.multiserver.GSON.Component;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class ChatBallon extends Component {
    private Boolean focuschatballoons;

    @Override
    public boolean isComponent(JsonObject jsonObject) {
        return  jsonObject.get("focuschatballoons") != null &&
                jsonObject.get("type").getAsString().equals("chatballoon");
    }
}
