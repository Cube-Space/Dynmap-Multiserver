package net.cubespace.dynmap.multiserver.GSON.Components;

import com.google.gson.JsonObject;
import net.cubespace.dynmap.multiserver.GSON.Component;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Chat extends Component {
    private Boolean allowurlname;

    @Override
    public boolean isComponent(JsonObject jsonObject) {
        return  jsonObject.get("allowurlname") != null &&
                jsonObject.get("type").getAsString().equals("chat");
    }
}
