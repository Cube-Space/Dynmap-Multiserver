package net.cubespace.dynmap.multiserver.GSON.Components;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import net.cubespace.dynmap.multiserver.GSON.Component;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Location extends Component {
    private Boolean hidey;
    @SerializedName("show-mcr")
    private Boolean show_mcr;
    private String label;

    @Override
    public boolean isComponent(JsonObject jsonObject) {
        return  jsonObject.get("hidey") != null &&
                jsonObject.get("show-mcr") != null &&
                jsonObject.get("type").getAsString().equals("coord");
    }
}
