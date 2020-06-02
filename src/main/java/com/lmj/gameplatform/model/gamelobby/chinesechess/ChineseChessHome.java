package com.lmj.gameplatform.model.gamelobby.chinesechess;

import com.lmj.gameplatform.model.account.onlineuser.OnlineUserData;
import com.lmj.gameplatform.model.gamelobby.GameLobbyHome;

import static com.lmj.gameplatform.model.gamelobby.GameLobbyJson.*;
import static com.lmj.gameplatform.model.gamelobby.GameLobbyJson.HOME_FALSE_HOME;

public class ChineseChessHome extends GameLobbyHome {
    private ChineseChess game=new ChineseChess();

    @Override
    public void rePlay() {
        game.replay();
    }

    @Override
    public boolean setGameData(String position,String data) {
        int who=0;
        if(position=="top")who=2;
        if(position=="bottom")who=1;
        String[] split = data.split(",");
        if (split.length==4){
            int beginX=Integer.parseInt(split[0]);
            int beginY=Integer.parseInt(split[1]);
            int endX=Integer.parseInt(split[2]);
            int endY=Integer.parseInt(split[3]);
            return game.moveChess(who,beginX,beginY,endX,endY);
        }
        return false;
    }

    @Override
    public String getGameData() {
        return game.getGameData();
    }

    //提示落子位置功能
    public String getAllCanChess(OnlineUserData userData,String data){
        int who=-1;
        if (userData==topUserData)who=2;
        if (userData==bottomUserData)who=1;
        String[]list=data.split(",");
        if(list.length==2){
            return game.getAllCanChess(who,Integer.parseInt(list[0]),Integer.parseInt(list[1]));
        }
        return null;
    }
}
