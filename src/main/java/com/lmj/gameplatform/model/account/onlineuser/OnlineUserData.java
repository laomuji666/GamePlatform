package com.lmj.gameplatform.model.account.onlineuser;


public class OnlineUserData {

    public OnlineUserData(String username, String onlineKey) {
        this.username = username;
        this.onlineKey = onlineKey;
        updateTime();
    }

    //onlineKey,用于验证在线状态
    private String onlineKey;
    public void setOnlineKey(String onlineKey) { this.onlineKey = onlineKey; }
    public String getOnlineKey() { return onlineKey; }

    //updateTime,用于检测在线时间
    private Long updateTime;
    public void updateTime(){
        updateTime = System.currentTimeMillis();
    }
    public Long getUpdateTime(){return updateTime;}

    //username
    private String username;
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    //lobbyId
    private int lobbyId;
    public int getLobbyId() { return lobbyId; }
    public void setLobbyId(int lobbyId) { this.lobbyId = lobbyId; }
    //lobbyName
    private static final String NORMAL_LOBBYNAME="欢迎进入老母鸡对战平台";
    private String lobbyName=NORMAL_LOBBYNAME;
    //重置lobbyName状态
    public void resetLobbyName(){this.lobbyName=NORMAL_LOBBYNAME;}
    public String getLobbyName() { return lobbyName; }
    public void setLobbyName(String lobbyName) { this.lobbyName = lobbyName; }
    //获取大厅信息
    public final String getLobbyInfo(){ return "{\"id:\":\""+lobbyId+"\",\"str\":\""+lobbyName+"\"}"; }


}
