package com.lmj.gameplatform.model.gamelobby.gobang;

import com.lmj.gameplatform.model.gamelobby.GameLobbyHome;


public class GobangHome extends GameLobbyHome {
    //游戏功能
    private Gobang game=new Gobang();

    //重新开始游戏
    @Override
    public void rePlay() {
        game.rePlay();
    }

    //检测玩家,并调用是否允许下棋功能
    @Override
    public boolean setGameData(String position,String data) {
        return game.checkChess(position, data);
    }

    //获取棋盘数据,第一位表示谁在下棋,第二位表示谁胜利
    @Override
    public String getGameData() {
        return game.getGameData();
    }

}
