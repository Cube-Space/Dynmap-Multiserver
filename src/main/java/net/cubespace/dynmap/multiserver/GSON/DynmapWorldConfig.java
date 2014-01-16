package net.cubespace.dynmap.multiserver.GSON;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class DynmapWorldConfig {
    private Long timestamp;
    private Boolean hasStorm;
    private Object[] updates;
    private Boolean isThundering;
    private Integer servertime;
    private Integer currentcount;
    private Object[] players;
    private Integer confighash;

    public Long getTimestamp() {
        return timestamp;
    }

    public Boolean getHasStorm() {
        return hasStorm;
    }

    public Object[] getUpdates() {
        return updates;
    }

    public Boolean getIsThundering() {
        return isThundering;
    }

    public Integer getServertime() {
        return servertime;
    }

    public Integer getCurrentcount() {
        return currentcount;
    }

    public Object[] getPlayers() {
        return players;
    }

    public Integer getConfighash() {
        return confighash;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public void setHasStorm(Boolean hasStorm) {
        this.hasStorm = hasStorm;
    }

    public void setUpdates(Object[] updates) {
        this.updates = updates;
    }

    public void setIsThundering(Boolean isThundering) {
        this.isThundering = isThundering;
    }

    public void setServertime(Integer servertime) {
        this.servertime = servertime;
    }

    public void setCurrentcount(Integer currentcount) {
        this.currentcount = currentcount;
    }

    public void setPlayers(Object[] players) {
        this.players = players;
    }

    public void setConfighash(Integer confighash) {
        this.confighash = confighash;
    }
}


