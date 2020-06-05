package com.lmj.gameplatform.model.gamelobby.chinesechess;

import com.lmj.gameplatform.model.account.onlineuser.OnlineUserData;
import com.lmj.gameplatform.model.gamelobby.GameLobbyHome;


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
        return game.moveChess(who,data);

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
