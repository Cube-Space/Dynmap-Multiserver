package net.cubespace.dynmap.multiserver.GSON.Components;

import com.google.gson.JsonObject;
import net.cubespace.dynmap.multiserver.GSON.Component;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Clock extends Component {
    private Boolean showdigitalclock;
    private Boolean showweather;

    @Override
    public boolean isComponent(JsonObject jsonObject) {
        return  jsonObject.get("showdigitalclock") != null &&
                jsonObject.get("showweather") != null &&
                jsonObject.get("type").getAsString().equals("timeofdayclock");
    }
}
