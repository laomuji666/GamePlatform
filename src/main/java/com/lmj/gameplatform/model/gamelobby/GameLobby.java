package com.lmj.gameplatform.model.gamelobby;

import com.lmj.gameplatform.model.account.Account;
import com.lmj.gameplatform.model.account.onlineuser.OnlineUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

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
    //房间
    protected GameLobbyHome [] gameLobbyHomes;
    public GameLobby(int lobbyId,String lobbyName,GameLobbyHome[] homes){
        this.lobbyId=lobbyId;
        this.lobbyName=lobbyName;
        gameLobbyHomes=homes;

    }

    //房间信息,定时更新.
    private String homeData=null;

    //申请加入大厅
    public final String joinGameLobby(String username,String onlineKey){
        //首先向Account获取用户数据
        OnlineUserData userData=account.getOnlineUserData(username, onlineKey);
        //未找到该用户
        if (userData==null) return JOIN_FALSE_NOTFOUND;
        //若为0,表示正常加入
        if (userData.getLobbyId()==0){
            userData.setLobbyId(lobbyId);
            userData.setLobbyName(lobbyName);
            return JOIN_TRUE_NORMAL;
        }
        //若为lobbyId,表示重连
        if (userData.getLobbyId()==lobbyId) {
            userData.setLobbyId(lobbyId);
            userData.setLobbyName(lobbyName);
            return JOIN_TRUE_RECONNECT;
        }else {
            //若不为lobbyId,表示在其它大厅中
            return JOIN_FALSE_OTHERLOBBY;
        }
    }

    //申请退出大厅,只有本大厅才有权限退出本大厅
    public final String exitGameLobby(String username,String onlineKey){
        //首先向Account获取用户数据
        OnlineUserData userData=account.getOnlineUserData(username, onlineKey);
        //未找到该用户
        if (userData==null) return EXIT_FALSE_NOTFOUND;
        //该用户不在本大厅
        if (userData.getLobbyId()!=lobbyId) return EXIT_FALSE_OTHERLOBBY;
        //该用户在房间中
        if (userData.getHomeId()!=-1)return EXIT_FALSE_INHOME;
        //该用户属于本大厅,允许退出
        userData.setLobbyId(0);
        userData.resetLobbyName();
        return EXIT_TRUE;
    }

    //获取所有房间信息,成功返回对应的json信息,失败返回null
    public String getHomeData(String username,String onlineKey){
        if (account.getOnlineUserData(username, onlineKey)==null)return null;
        return homeData;
    }

    //加入房间
    public String joinHome(String username,String onlineKey,Integer homeId,String position){
        String verify = verifyHome(username, onlineKey);
        if (verify!=null && !verify.equals(HOME_FALSE_HOMEID)) return verify;
        OnlineUserData userData=account.getOnlineUserData(username, onlineKey);
        if (homeId==-1)homeId=userData.getHomeId();
        if (homeId==-1)return verify;
        if (userData.getHomeId()==homeId||userData.getHomeId()==-1){
            return gameLobbyHomes[homeId].joinHome(userData,position,homeId);
        }else {
            return HOME_JOIN_FALSE_INHOME;
        }
    }

    //退出房间
    public String exitHome(String username,String onlineKey){
        String verify = verifyHome(username, onlineKey);
        if (verify!=null) return verify;
        OnlineUserData userData=account.getOnlineUserData(username, onlineKey);
        int homeId=userData.getHomeId();
        String json=gameLobbyHomes[homeId].exitHome(userData);
        gameLobbyHomes[homeId].checkReady();
        return json;
    }

    //定时更新房间信息
    @Scheduled(fixedRate = 1000)
    private void updateHomeData(){
        StringBuffer buffer=new StringBuffer();
        for (int i=0;i<gameLobbyHomes.length;i++){
            GameLobbyHome home=gameLobbyHomes[i];
            OnlineUserData top=home.getTopUserData();
            OnlineUserData bottom=home.getBottomUserData();
            String topUsername="空";
            String bottomUsername="空";
            if (top !=null)topUsername=top.getUsername();
            if (bottom !=null)bottomUsername=bottom.getUsername();
            String data= HOME_DATA_ID + i + HOME_DATA_TOP +topUsername + HOME_DATA_BOTTOM + bottomUsername + HOME_DATA_END;
            buffer.append(data+"\r\n");
        }
        homeData=buffer.toString();
    }

    //定时清理房间中的玩家,向在线用户管理器查询是否需要清理
    @Scheduled(fixedRate = 5000)
    private void removeHomePlayer(){
        for (GameLobbyHome home : gameLobbyHomes){
            OnlineUserData top=home.getTopUserData();
            OnlineUserData bottom=home.getBottomUserData();
            if (top!=null){
                if (account.getOnlineUserData(top.getUsername(),top.getOnlineKey())==null){
                    home.setTopUserData(null);
                    home.checkReady();
                }
            }
            if (bottom!=null){
                if (account.getOnlineUserData(bottom.getUsername(),bottom.getOnlineKey())==null){
                    home.setBottomUserData(null);
                    home.checkReady();
                }
            }
        }
    }


    //验证房间数据,返回对应的错误Json信息,若符合条件返回null
    public String verifyHome(String username,String onlineKey){
        OnlineUserData userData = account.getOnlineUserData(username, onlineKey);
        if (userData==null)return HOME_FALSE_NOTFOUND;
        if (userData.getLobbyId()!=this.lobbyId)return HOME_FALSE_LOBBY;
        int homeId=userData.getHomeId();
        if (homeId>=gameLobbyHomes.length || homeId < 0)return HOME_FALSE_HOMEID;
        return null;
    }

    //设置准备状态
    public String setReady(String username,String onlineKey,String isReady){
        String verify = verifyHome(username, onlineKey);
        if (verify!=null)return verify;
        OnlineUserData userData=account.getOnlineUserData(username, onlineKey);
        String json=gameLobbyHomes[userData.getHomeId()].setReady(userData,isReady);
        gameLobbyHomes[userData.getHomeId()].checkReady();
        return json;
    }

    //获取房间状态,
    public String getReady(String username,String onlineKey){
        String verify = verifyHome(username, onlineKey);
        if (verify!=null)return verify;
        OnlineUserData userData=account.getOnlineUserData(username, onlineKey);
        return gameLobbyHomes[userData.getHomeId()].getReady(userData);
    }

    //发送数据到房间
    public String setData(String username,String onlineKey,String data){
        String verify = verifyHome(username, onlineKey);
        if (verify!=null)return verify;
        OnlineUserData userData=account.getOnlineUserData(username, onlineKey);
        return gameLobbyHomes[userData.getHomeId()].setData(userData,data);
    }

    //获取房间里的游戏数据
    public String getData(String username,String onlineKey){
        String verify = verifyHome(username, onlineKey);
        if (verify!=null)return verify;
        OnlineUserData userData=account.getOnlineUserData(username, onlineKey);
        return gameLobbyHomes[userData.getHomeId()].getData(userData);
    }

    //发送GameOver消息
    public String setGameOver(String username,String onlineKey){
        String verify = verifyHome(username, onlineKey);
        if (verify!=null)return verify;
        OnlineUserData userData=account.getOnlineUserData(username, onlineKey);
        return gameLobbyHomes[userData.getHomeId()].setGameOver(userData);
    }

}
