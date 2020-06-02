package com.lmj.gameplatform.model.gamelobby;

import com.lmj.gameplatform.model.account.onlineuser.OnlineUserData;
import static com.lmj.gameplatform.model.gamelobby.GameLobbyJson.*;
/*
    游戏大厅的房间数据
 */
public abstract class GameLobbyHome {
    //上方和下方玩家
    protected OnlineUserData topUserData=null,bottomUserData=null;
    //准备状态
    protected boolean topReady,bottomReady;
    //是否开始
    protected boolean isPlay;
    //记录两位玩家是否都发送胜利信息
    protected boolean topSendGameOver,bottomSendGameOver;

    public OnlineUserData getTopUserData() {
        return topUserData;
    }

    public void setTopUserData(OnlineUserData topUserData) {
        this.topUserData = topUserData;
    }

    public OnlineUserData getBottomUserData() {
        return bottomUserData;
    }

    public void setBottomUserData(OnlineUserData bottomUserData) {
        this.bottomUserData = bottomUserData;
    }

    //加入房间
    public String joinHome(OnlineUserData userData,String position,int homeId){
        if (topUserData!=null && userData.getUsername().equals(topUserData.getUsername())) position="top";
        if (bottomUserData!=null && userData.getUsername().equals(bottomUserData.getUsername())) position="bottom";
        if (position.equals("top")){
            if (bottomUserData!=null){
                if (bottomUserData.getUsername().equals(userData.getUsername())){
                    return HOME_JOIN_FALSE_INHOME;
                }
            }
            if (topUserData==null){
                topUserData=userData;
                userData.setHomeId(homeId);
                return HOME_JOIN_TRUE_NORMAL;
            }else if (topUserData.getUsername().equals(userData.getUsername())){
                topUserData=userData;
                return HOME_JOIN_TRUE_RECONNECT;
            }
        }
        if(position.equals("bottom")){
            if (topUserData!=null){
                if (topUserData.getUsername().equals(userData.getUsername())){
                    return HOME_JOIN_FALSE_INHOME;
                }
            }
            if (bottomUserData==null){
                bottomUserData=userData;
                userData.setHomeId(homeId);
                return HOME_JOIN_TRUE_NORMAL;
            }else if (bottomUserData.getUsername().equals(userData.getUsername())){
                bottomUserData=userData;
                return HOME_JOIN_TRUE_RECONNECT;
            }
        }
        return HOME_FALSE_POSITION;
    }

    //退出房间
    public String exitHome(OnlineUserData userData){
        if (userData==topUserData){
            topUserData=null;
            userData.setHomeId(-1);
            return HOME_EXIT_TRUE_NORMAL;
        }
        if (userData==bottomUserData){
            bottomUserData=null;
            userData.setHomeId(-1);
            return HOME_EXIT_TRUE_NORMAL;
        }

        return HOME_EXIT_FALSE_NOPLAYER;
    }

    //设置准备或为准备状态
    public String setReady(OnlineUserData userData,String isReady){
        if (isPlay)return HOME_FALSE_PLAY_YES;
        if (topUserData==userData){
            if (isReady.equals("yes")){
                topReady=true;
                return HOME_READY_SET_TRUE_YES;
            }
            if (isReady.equals("no")){
                topReady=false;
                return HOME_READY_SET_TRUE_NO;
            }
            return HOME_FALSE_ISREADY;
        }else if (bottomUserData==userData){
            if (isReady.equals("yes")){
                bottomReady=true;
                return HOME_READY_SET_TRUE_YES;
            }
            if (isReady.equals("no")){
                bottomReady=false;
                return HOME_READY_SET_TRUE_NO;
            }
            return HOME_FALSE_ISREADY;
        }
        return HOME_FALSE_HOME;
    }

    //获取房间的准备状态
    public String getReady(OnlineUserData userData){
        if (userData==topUserData||userData==bottomUserData){
            if (isPlay)return HOME_READY_GET_YES;
            if (topReady==true && bottomReady==false) return HOME_READY_GET_NO_ONLY_TOP;
            if (topReady==false && bottomReady==true) return HOME_READY_GET_NO_ONLY_BOTTOM;
            if (topReady==false && bottomReady==false) return HOME_READY_GET_NO;
        }
        return HOME_FALSE_HOME;
    }

    //检测准备状态并进行处理
    public void checkReady(){
        //游戏结束,重置各种信息
        if (topSendGameOver&&bottomSendGameOver){
            topReady=false;
            topSendGameOver=false;
            bottomReady=false;
            bottomSendGameOver=false;
        }
        //玩家退出,重置对应的准备状态
        if (topUserData==null){
            topReady=false;
            topSendGameOver=false;
        }
        if (bottomUserData==null){
            bottomReady=false;
            bottomSendGameOver=false;
        }
        //若双方都准备了就开始游戏,否则重置对局状态,数据,并调用replay
        if (topReady==true && bottomReady==true){
            isPlay=true;
        }else {
            isPlay=false;
            rePlay();
        }
    }

    //发送数据,只有在游戏开始时才允许发送
    public String setData(OnlineUserData userData,String data){
        if (userData==topUserData || userData==bottomUserData){
            String position;
            if (userData==topUserData){
                position="top";
            }else {
                position = "bottom";
            }
            if (isPlay==true){
                if (setGameData(position,data)){
                    return HOME_DATA_SET_TRUE;
                }else {
                    return HOME_DATA_SET_FALSE;
                }
            }else {
                return HOME_FALSE_PLAY_NO;
            }
        }
        return HOME_FALSE_HOME;
    }

    //获取数据,只有在游戏开始时才允许获取,失败返回null
    public String getData(OnlineUserData userData){
        if (userData==topUserData || userData==bottomUserData){
            if (isPlay==true){
                return HOME_DATA_GET_TRUE_BEGIN+getGameData()+HOME_DATA_GET_TRUE_END;
            }else {
                return HOME_FALSE_PLAY_NO;
            }
        }
        return HOME_FALSE_HOME;
    }

    //设置GameOver,如果两个玩家都发送GameOver,则将两位玩家的准备状态设置为未准备
    public String setGameOver(OnlineUserData userData){
        if (userData==topUserData){
            topSendGameOver=true;
            checkReady();
            return HOME_GAMEOVER_SET_TRUE;
        }else if (userData==bottomUserData){
            bottomSendGameOver=true;
            checkReady();
            return HOME_GAMEOVER_SET_TRUE;
        }
        return HOME_FALSE_HOME;
    }

    //重新开始,checkReady调用
    public abstract void rePlay();

    //是否允许修改数据
    public abstract boolean setGameData(String position,String data);

    //是否允许获取数据
    public abstract String getGameData();


}
