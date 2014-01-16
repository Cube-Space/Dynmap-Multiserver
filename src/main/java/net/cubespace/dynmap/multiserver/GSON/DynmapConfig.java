package net.cubespace.dynmap.multiserver.GSON;

import com.google.gson.annotations.SerializedName;

/**
 * @author geNAZt (fabian.fassbender42@googlemail.com)
 */
public class DynmapConfig {
    @SerializedName("login-enabled")
    private Boolean login_enabled = false;
    private Integer maxcount = 200;
    @SerializedName("msg-hiddennamejoin")
    private String msg_hiddennamejoin = "Player joined";
    @SerializedName("webchat-requires-login")
    private Boolean webacht_requires_login = false;
    private String quitmessage = "%playername% quit";
    private Integer confighash;
    private DynmapWorld[] worlds = new DynmapWorld[0];
    private String showlayercontrol = "true";
    private String title = "Awesome Multiserver Dynmap";
    private String defaultmap;
    private Boolean showplayerfacesinmenu = true;
    @SerializedName("msg-players")
    private String msg_players = "Players";
    @SerializedName("msg-chatnotallowed")
    private String msg_chatnotallowed = "You are not permitted to send chat messages";
    private Integer chatlengthlimit = 256;
    private Boolean cyrillic = false;
    private Boolean loginrequired = false;
    private String webprefix = "[WEB]";
    private String coreversion = "1.9.1-1832";
    private Boolean allowchat = false;
    private String dynmapversion = "1.9.1-875";
    private String sidebaropened = "false";
    private Integer updaterate = 2000;
    private Boolean jsonfile = true;
    @SerializedName("msg-maptypes")
    private String msg_maptypes = "Map Types";
    private Integer defaultzoom = 0;
    private String defaultworld;
    private String spammessage = "You may only chat once every %interval% seconds.";
    private String joinmessage = "%playername% joined";
    private Boolean grayplayerswhenhidden = true;
    @SerializedName("webchat-interval")
    private Integer webchat_interval = 5;
    private Boolean allowwebchat = false;
    private Component[] components = new Component[0];
    @SerializedName("msg-chatrequireslogin")
    private String msg_chatrequireslogin = "Chat requires login";
    @SerializedName("msg-hiddennamequit")
    private String msg_hiddennamequit = "Player quit";

    public Boolean getLogin_enabled() {
        return login_enabled;
    }

    public Integer getMaxcount() {
        return maxcount;
    }

    public String getMsg_hiddennamejoin() {
        return msg_hiddennamejoin;
    }

    public Boolean getWebacht_requires_login() {
        return webacht_requires_login;
    }

    public String getQuitmessage() {
        return quitmessage;
    }

    public Integer getConfighash() {
        return confighash;
    }

    public DynmapWorld[] getWorlds() {
        return worlds;
    }

    public String getShowlayercontrol() {
        return showlayercontrol;
    }

    public String getTitle() {
        return title;
    }

    public String getDefaultmap() {
        return defaultmap;
    }

    public Boolean getShowplayerfacesinmenu() {
        return showplayerfacesinmenu;
    }

    public String getMsg_players() {
        return msg_players;
    }

    public String getMsg_chatnotallowed() {
        return msg_chatnotallowed;
    }

    public Integer getChatlengthlimit() {
        return chatlengthlimit;
    }

    public Boolean getCyrillic() {
        return cyrillic;
    }

    public Boolean getLoginrequired() {
        return loginrequired;
    }

    public String getWebprefix() {
        return webprefix;
    }

    public String getCoreversion() {
        return coreversion;
    }

    public Boolean getAllowchat() {
        return allowchat;
    }

    public String getDynmapversion() {
        return dynmapversion;
    }

    public String getSidebaropened() {
        return sidebaropened;
    }

    public Integer getUpdaterate() {
        return updaterate;
    }

    public Boolean getJsonfile() {
        return jsonfile;
    }

    public String getMsg_maptypes() {
        return msg_maptypes;
    }

    public Integer getDefaultzoom() {
        return defaultzoom;
    }

    public String getDefaultworld() {
        return defaultworld;
    }

    public String getSpammessage() {
        return spammessage;
    }

    public String getJoinmessage() {
        return joinmessage;
    }

    public Boolean getGrayplayerswhenhidden() {
        return grayplayerswhenhidden;
    }

    public Integer getWebchat_interval() {
        return webchat_interval;
    }

    public Boolean getAllowwebchat() {
        return allowwebchat;
    }

    public Component[] getComponents() {
        return components;
    }

    public String getMsg_chatrequireslogin() {
        return msg_chatrequireslogin;
    }

    public String getMsg_hiddennamequit() {
        return msg_hiddennamequit;
    }

    public void setLogin_enabled(Boolean login_enabled) {
        this.login_enabled = login_enabled;
    }

    public void setMaxcount(Integer maxcount) {
        this.maxcount = maxcount;
    }

    public void setMsg_hiddennamejoin(String msg_hiddennamejoin) {
        this.msg_hiddennamejoin = msg_hiddennamejoin;
    }

    public void setWebacht_requires_login(Boolean webacht_requires_login) {
        this.webacht_requires_login = webacht_requires_login;
    }

    public void setQuitmessage(String quitmessage) {
        this.quitmessage = quitmessage;
    }

    public void setConfighash(Integer confighash) {
        this.confighash = confighash;
    }

    public void setWorlds(DynmapWorld[] worlds) {
        this.worlds = worlds;
    }

    public void setShowlayercontrol(String showlayercontrol) {
        this.showlayercontrol = showlayercontrol;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDefaultmap(String defaultmap) {
        this.defaultmap = defaultmap;
    }

    public void setShowplayerfacesinmenu(Boolean showplayerfacesinmenu) {
        this.showplayerfacesinmenu = showplayerfacesinmenu;
    }

    public void setMsg_players(String msg_players) {
        this.msg_players = msg_players;
    }

    public void setMsg_chatnotallowed(String msg_chatnotallowed) {
        this.msg_chatnotallowed = msg_chatnotallowed;
    }

    public void setChatlengthlimit(Integer chatlengthlimit) {
        this.chatlengthlimit = chatlengthlimit;
    }

    public void setCyrillic(Boolean cyrillic) {
        this.cyrillic = cyrillic;
    }

    public void setLoginrequired(Boolean loginrequired) {
        this.loginrequired = loginrequired;
    }

    public void setWebprefix(String webprefix) {
        this.webprefix = webprefix;
    }

    public void setCoreversion(String coreversion) {
        this.coreversion = coreversion;
    }

    public void setAllowchat(Boolean allowchat) {
        this.allowchat = allowchat;
    }

    public void setDynmapversion(String dynmapversion) {
        this.dynmapversion = dynmapversion;
    }

    public void setSidebaropened(String sidebaropened) {
        this.sidebaropened = sidebaropened;
    }

    public void setUpdaterate(Integer updaterate) {
        this.updaterate = updaterate;
    }

    public void setJsonfile(Boolean jsonfile) {
        this.jsonfile = jsonfile;
    }

    public void setMsg_maptypes(String msg_maptypes) {
        this.msg_maptypes = msg_maptypes;
    }

    public void setDefaultzoom(Integer defaultzoom) {
        this.defaultzoom = defaultzoom;
    }

    public void setDefaultworld(String defaultworld) {
        this.defaultworld = defaultworld;
    }

    public void setSpammessage(String spammessage) {
        this.spammessage = spammessage;
    }

    public void setJoinmessage(String joinmessage) {
        this.joinmessage = joinmessage;
    }

    public void setGrayplayerswhenhidden(Boolean grayplayerswhenhidden) {
        this.grayplayerswhenhidden = grayplayerswhenhidden;
    }

    public void setWebchat_interval(Integer webchat_interval) {
        this.webchat_interval = webchat_interval;
    }

    public void setAllowwebchat(Boolean allowwebchat) {
        this.allowwebchat = allowwebchat;
    }

    public void setComponents(Component[] components) {
        this.components = components;
    }

    public void setMsg_chatrequireslogin(String msg_chatrequireslogin) {
        this.msg_chatrequireslogin = msg_chatrequireslogin;
    }

    public void setMsg_hiddennamequit(String msg_hiddennamequit) {
        this.msg_hiddennamequit = msg_hiddennamequit;
    }
}
