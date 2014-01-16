package net.cubespace.dynmap.multiserver.GSON;

import com.google.gson.annotations.SerializedName;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class Maps {
    private Boolean nightandday;
    private Double[] worldtomap;
    private Integer scale;
    private String icon;
    private Integer mapzoomin;
    private String shader;
    private Double[] maptoworld;
    private Boolean bigmap;
    private Integer inclination;
    private String type;
    private String backgroundday;
    private String backgroundnight;
    private Integer mapzoomout;
    private String title;
    @SerializedName("protected")
    private Boolean protec;
    private String lighting;
    private String name;
    private String prefix;
    private String background;
    private Integer boostzoom;
    private String compassview;
    private String perspective;
    private Integer azimuth;
    @SerializedName("image-format")
    private String image_format;

    public Boolean getNightandday() {
        return nightandday;
    }

    public Double[] getWorldtomap() {
        return worldtomap;
    }

    public Integer getScale() {
        return scale;
    }

    public String getIcon() {
        return icon;
    }

    public Integer getMapzoomin() {
        return mapzoomin;
    }

    public String getShader() {
        return shader;
    }

    public Double[] getMaptoworld() {
        return maptoworld;
    }

    public Boolean getBigmap() {
        return bigmap;
    }

    public Integer getInclination() {
        return inclination;
    }

    public String getType() {
        return type;
    }

    public String getBackgroundday() {
        return backgroundday;
    }

    public String getBackgroundnight() {
        return backgroundnight;
    }

    public Integer getMapzoomout() {
        return mapzoomout;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getProtec() {
        return protec;
    }

    public String getLighting() {
        return lighting;
    }

    public String getName() {
        return name;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getBackground() {
        return background;
    }

    public Integer getBoostzoom() {
        return boostzoom;
    }

    public String getCompassview() {
        return compassview;
    }

    public String getPerspective() {
        return perspective;
    }

    public Integer getAzimuth() {
        return azimuth;
    }

    public String getImage_format() {
        return image_format;
    }
}
