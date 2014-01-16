package net.cubespace.dynmap.multiserver.GSON.Components;

import com.google.gson.JsonObject;
import net.cubespace.dynmap.multiserver.GSON.Component;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Spawn extends Component {
    private Boolean offlinehidebydefault;
    private Boolean showspawnbeds;
    private String spawnicon;
    private Integer spawnbedminzoom;
    private String offlinelabel;
    private Boolean showspawn;
    private String spawnbedicon;
    private Boolean showlabel;
    private Integer maxofflinetime;
    private String spawnbedformat;
    private Boolean spawnbedhidebydefault;
    private String spawnbedlabel;
    private String spawnlabel;
    private Integer offlineminzoom;
    private Boolean enablesigns;
    private String offflineicon;
    private Boolean showofflineplayers;

    @Override
    public boolean isComponent(JsonObject jsonObject) {
        return  jsonObject.get("offlinehidebydefault") != null &&
                jsonObject.get("showspawnbeds") != null;
    }
}
