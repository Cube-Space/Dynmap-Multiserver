package net.cubespace.dynmap.multiserver.GSON.Components;

import com.google.gson.JsonObject;
import net.cubespace.dynmap.multiserver.GSON.Component;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Chatbox extends Component {
    private Integer messagettl;
    private Boolean sendbutton;
    private Boolean showplayerfaces;

    @Override
    public boolean isComponent(JsonObject jsonObject) {
        return  jsonObject.get("type").getAsString().equals("chatbox");
    }
}
