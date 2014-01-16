package net.cubespace.dynmap.multiserver.GSON.Components;

import com.google.gson.JsonObject;
import net.cubespace.dynmap.multiserver.GSON.Component;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class PlayerMarkers extends Component {
    private Integer layerprio;
    private Boolean smallplayerfaces;
    private String label;
    private Boolean showplayerbody;
    private Boolean showplayerhealth;
    private Boolean hidebydefault;
    private Boolean showplayerfaces;

    @Override
    public boolean isComponent(JsonObject jsonObject) {
        return  jsonObject.get("smallplayerfaces") != null &&
                jsonObject.get("showplayerbody") != null;
    }
}
