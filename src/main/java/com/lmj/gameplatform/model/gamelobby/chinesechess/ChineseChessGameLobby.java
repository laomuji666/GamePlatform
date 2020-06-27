package com.lmj.gameplatform.model.gamelobby.chinesechess;

import com.lmj.gameplatform.model.account.onlineuser.OnlineUserData;
import com.lmj.gameplatform.model.gamelobby.GameLobby;
import com.lmj.gameplatform.model.gamelobby.GameLobbyId;
import org.springframework.stereotype.Controller;

@Controller
public class ChineseChessGameLobby extends GameLobby {
    private static final ChineseChessHome[] homes=new ChineseChessHome[25];
    static {
        for (int i=0;i<homes.length;i++){
            homes[i]=new ChineseChessHome();
        }
    }
    ChineseChessGameLobby(){
        super(GameLobbyId.CHINESECHESS,"中国象棋",homes);
    }

    //获取落子位置提示
    public String getAllCanChess(String username,String onlineKey,String data){
        OnlineUserData userData=account.getOnlineUserData(username, onlineKey);
        if (userData==null)return null;
        int homeId=userData.getHomeId();
        if (homeId<0 || homeId>homes.length)return null;
        return homes[homeId].getAllCanChess(userData,data);
    }
}
