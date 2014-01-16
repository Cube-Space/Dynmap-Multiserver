package net.cubespace.dynmap.multiserver.GSON;

import com.google.gson.annotations.SerializedName;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class DynmapWorld {
    private Location center;
    private Integer extrazoomout;
    private String title;
    private Integer worldheight;
    @SerializedName("protected")
    private Boolean protec;
    private Maps[] maps;
    private Integer sealevel;
    private Boolean bigworld;
    private String name;

    public Location getCenter() {
        return center;
    }

    public Integer getExtrazoomout() {
        return extrazoomout;
    }

    public String getTitle() {
        return title;
    }

    public Integer getWorldheight() {
        return worldheight;
    }

    public Boolean getProtec() {
        return protec;
    }

    public Maps[] getMaps() {
        return maps;
    }

    public Integer getSealevel() {
        return sealevel;
    }

    public Boolean getBigworld() {
        return bigworld;
    }

    public String getName() {
        return name;
    }
}
