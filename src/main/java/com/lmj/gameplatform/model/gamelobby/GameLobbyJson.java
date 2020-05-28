package com.lmj.gameplatform.model.gamelobby;

public class GameLobbyJson {
    //加入大厅
    public static final String JOIN_TRUE_NORMAL="{\"id\":\"0\",\"str\":\"加入成功\"}";
    public static final String JOIN_TRUE_RECONNECT="{\"id\":\"1\",\"str\":\"重连成功\"}";
    public static final String JOIN_FALSE_NOTFOUND="{\"id\":\"3\",\"str\":\"加入失败,未找到该用户信息\"}";
    public static final String JOIN_FALSE_OTHERLOBBY="{\"id\":\"4\",\"str\":\"加入失败,该用户在其它大厅\"}";

    //退出大厅
    public static final String EXIT_TRUE="{\"id\":\"0\",\"str\":\"退出成功\"}";
    public static final String EXIT_FALSE_NOTFOUND="{\"id\":\"1\",\"str\":\"退出失败,未找到用户信息\"}";
    public static final String EXIT_FALSE_OTHERLOBBY="{\"id\":\"2\",\"str\":\"退出失败,该用户在其它大厅\"}";

}
