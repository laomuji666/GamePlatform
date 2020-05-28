package com.lmj.gameplatform.model.gamelobby;

import com.lmj.gameplatform.model.account.Account;
import com.lmj.gameplatform.model.account.onlineuser.OnlineUserData;
import org.springframework.beans.factory.annotation.Autowired;

import static com.lmj.gameplatform.model.gamelobby.GameLobbyJson.*;
/*
    GameLobby,游戏大厅的基类
    所有游戏大厅都必须继承该大厅
 */
public abstract class GameLobby {
    @Autowired
    protected Account account = null;

    //游戏大厅的名称,编号
    protected final int lobbyId;
    protected final String lobbyName;
    public GameLobby(int lobbyId,String lobbyName){
        this.lobbyId=lobbyId;
        this.lobbyName=lobbyName;
    }

    //申请加入大厅
    public final String joinGameLobby(String username,String onlineKey){
        //首先向Account获取用户数据
        OnlineUserData onlineUserData=account.getOnlineUserData(username, onlineKey);
        //未找到该用户
        if (onlineUserData==null) return JOIN_FALSE_NOTFOUND;
        //若为0,表示正常加入
        if (onlineUserData.getLobbyId()==0){
            onlineUserData.setLobbyId(lobbyId);
            onlineUserData.setLobbyName(lobbyName);
            return JOIN_TRUE_NORMAL;
        }
        //若为lobbyId,表示重连
        if (onlineUserData.getLobbyId()==lobbyId) {
            onlineUserData.setLobbyId(lobbyId);
            onlineUserData.setLobbyName(lobbyName);
            return JOIN_TRUE_RECONNECT;
        }else {
            //若不为lobbyId,表示在其它大厅中
            return JOIN_FALSE_OTHERLOBBY;
        }
    }

    //申请退出大厅,只有本大厅才有权限退出本大厅
    public final String exitGameLobby(String username,String onlineKey){
        //首先向Account获取用户数据
        OnlineUserData onlineUserData=account.getOnlineUserData(username, onlineKey);
        //未找到该用户
        if (onlineUserData==null) return EXIT_FALSE_NOTFOUND;
        //该用户不在本大厅
        if (onlineUserData.getLobbyId()!=lobbyId) return EXIT_FALSE_OTHERLOBBY;
        //该用户属于本大厅,允许退出
        onlineUserData.setLobbyId(0);
        onlineUserData.resetLobbyName();
        return EXIT_TRUE;
    }

}
