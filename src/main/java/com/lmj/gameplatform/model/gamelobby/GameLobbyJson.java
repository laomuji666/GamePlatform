package com.lmj.gameplatform.model.gamelobby;

public class GameLobbyJson {

    /*
        GameLobby部分
     */

    //加入大厅
    public static final String JOIN_TRUE_NORMAL="{\"id\":\"0\",\"str\":\"加入成功\"}";
    public static final String JOIN_TRUE_RECONNECT="{\"id\":\"1\",\"str\":\"重连成功\"}";
    public static final String JOIN_FALSE_NOTFOUND="{\"id\":\"3\",\"str\":\"加入失败,未找到该用户信息\"}";
    public static final String JOIN_FALSE_OTHERLOBBY="{\"id\":\"4\",\"str\":\"加入失败,该用户在其它大厅\"}";

    //退出大厅
    public static final String EXIT_TRUE="{\"id\":\"0\",\"str\":\"退出成功\"}";
    public static final String EXIT_FALSE_NOTFOUND="{\"id\":\"1\",\"str\":\"退出失败,未找到用户信息\"}";
    public static final String EXIT_FALSE_OTHERLOBBY="{\"id\":\"2\",\"str\":\"退出失败,该用户在其它大厅\"}";
    public static final String EXIT_FALSE_INHOME="{\"id\":\"3\",\"str\":\"退出失败,该用户在房间中\"}";

    /*
     GameLobbyHome部分
    */

    //加入房间
    public static final String HOME_JOIN_TRUE_NORMAL="{\"id\":\"0\",\"str\":\"加入房间成功\"}";
    public static final String HOME_JOIN_TRUE_RECONNECT="{\"id\":\"0\",\"str\":\"加入房间成功,重新连接\"}";
    public static final String HOME_JOIN_FALSE_INHOME="{\"id\":\"1\",\"str\":\"加入房间失败,你已经在一个房间里了\"}";

    //退出房间
    public static final String HOME_EXIT_TRUE_NORMAL="{\"id\":\"0\",\"str\":\"退出房间成功\"}";
    public static final String HOME_EXIT_FALSE_NOPLAYER="{\"id\":\"1\",\"str\":\"退出房间失败,该位置没有玩家\"}";

    //房间信息
    public static final String HOME_DATA_ID="{\"id\":\"";
    public static final String HOME_DATA_TOP="\",\"top\":\"";
    public static final String HOME_DATA_BOTTOM="\",\"bottom\":\"";
    public static final String HOME_DATA_END="\"}";//如{"id":"3","top":"laomuji","bottom":"yasuo"}

    //设置准备状态
    public static final String HOME_READY_SET_TRUE_YES="{\"id\":\"0\",\"str\":\"已准备\"}";
    public static final String HOME_READY_SET_TRUE_NO="{\"id\":\"1\",\"str\":\"未准备\"}";

    //获取准备状态
    public static final String HOME_READY_GET_YES="{\"id\":\"0\",\"str\":\"已开始\"}";
    public static final String HOME_READY_GET_NO="{\"id\":\"1\",\"str\":\"top:no,bottom:no\"}";
    public static final String HOME_READY_GET_NO_ONLY_TOP="{\"id\":\"1\",\"str\":\"top:yes,bottom:no\"}";
    public static final String HOME_READY_GET_NO_ONLY_BOTTOM="{\"id\":\"1\",\"str\":\"top:no,bottom:yes\"}";

    //发送数据
    public static final String HOME_DATA_SET_TRUE="{\"id\":\"0\",\"str\":\"成功\"}";
    public static final String HOME_DATA_SET_FALSE="{\"id\":\"1\",\"str\":\"失败,数据不合法\"}";

    //获取数据
    public static final String HOME_DATA_GET_TRUE_BEGIN="{\"id\":\"0\",\"str\":\"";
    public static final String HOME_DATA_GET_TRUE_END="\"}";

    //设置游戏结束
    public static final String HOME_GAMEOVER_SET_TRUE="{\"id\":\"0\",\"str\":\"设置成功\"}";

    //房间公用
    public static final String HOME_FALSE_NOTFOUND="{\"id\":\"2\",\"str\":\"失败,未找到用户信息\"}";
    public static final String HOME_FALSE_LOBBY="{\"id\":\"3\",\"str\":\"失败,不在该大厅\"}";
    public static final String HOME_FALSE_HOME="{\"id\":\"4\",\"str\":\"失败,不属于该房间\"}";
    public static final String HOME_FALSE_BELONG="{\"id\":\"5\",\"str\":\"失败,不属于该位置\"}";
    public static final String HOME_FALSE_HOMEID="{\"id\":\"6\",\"str\":\"失败,homeId不正确\"}";
    public static final String HOME_FALSE_ISREADY="{\"id\":\"7\",\"str\":\"失败,isReady参数错误\"}";
    public static final String HOME_FALSE_PLAY_YES="{\"id\":\"8\",\"str\":\"失败,已开始游戏\"}";
    public static final String HOME_FALSE_PLAY_NO="{\"id\":\"9\",\"str\":\"失败,未开始游戏\"}";
    public static final String HOME_FALSE_POSITION="{\"id\":\"10\",\"str\":\"失败,position错误\"}";


}
