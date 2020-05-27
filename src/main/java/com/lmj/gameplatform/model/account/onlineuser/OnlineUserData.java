package com.lmj.gameplatform.model.account.onlineuser;


class OnlineUserData {

    private String username;

    private String onlineKey;

    private Long updateTime;

    public OnlineUserData(String username, String onlineKey) {
        this.username = username;
        this.onlineKey = onlineKey;
        updateTime();
    }

    public String getOnlineKey() {
        return onlineKey;
    }

    public void setOnlineKey(String onlineKey) {
        this.onlineKey = onlineKey;
    }

    public void updateTime(){
        updateTime = System.currentTimeMillis();
    }

    public Long getUpdateTime(){return updateTime;}
}
