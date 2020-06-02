package com.lmj.gameplatform.mapping.gamelobbymapping;

import com.lmj.gameplatform.model.gamelobby.gobang.GobangGameLobby;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lmj.gameplatform.model.account.AccountJson.BAD_REQUEST;

@RestController
public class GobangMapping {
    private static final String url="/Gobang";
    @Autowired
    private GobangGameLobby gameLobby=null;

    @PostMapping(url+"/join")
    private String join(String username,String onlineKey){
        if (username==null||onlineKey==null)return BAD_REQUEST;
        return gameLobby.joinGameLobby(username, onlineKey);
    }

    @PostMapping(url+"/exit")
    private String exit(String username,String onlineKey){
        if (username==null||onlineKey==null)return BAD_REQUEST;
        return gameLobby.exitGameLobby(username, onlineKey);
    }

    @PostMapping(url+"/home/data")
    private String home(String username,String onlineKey){
        if (username==null || onlineKey==null){
            return BAD_REQUEST;
        }
        return gameLobby.getHomeData(username, onlineKey);
    }

    @PostMapping(url+"/home/join")
    private String joinHome(String username,String onlineKey,Integer homeId,String position){
        if (username==null ||onlineKey==null||homeId==null||position==null){
            return BAD_REQUEST;
        }
        return gameLobby.joinHome(username, onlineKey, homeId, position);
    }

    @PostMapping(url+"/home/exit")
    private String exitHome(String username,String onlineKey){
        if (username==null ||onlineKey==null){
            return BAD_REQUEST;
        }
        return gameLobby.exitHome(username, onlineKey);
    }

    @PostMapping(url+"/home/ready/set")
    private String setReady(String username,String onlineKey,String isReady){
        if (username==null || onlineKey==null||isReady==null){
            return BAD_REQUEST;
        }
        return gameLobby.setReady(username,onlineKey,isReady);
    }

    @PostMapping(url+"/home/ready/get")
    private String getReady(String username,String onlineKey){
        if (username==null || onlineKey==null ){
            return BAD_REQUEST;
        }
        return gameLobby.getReady(username, onlineKey);
    }

    @PostMapping(url+"/home/game/data/set")
    private String setData(String username,String onlineKey,String data){
        if (username==null || onlineKey==null ||data==null){
            return BAD_REQUEST;
        }
        return gameLobby.setData(username,onlineKey,data);
    }

    @PostMapping(url+"/home/game/data/get")
    private String getData(String username,String onlineKey){
        if (username==null || onlineKey==null ){
            return BAD_REQUEST;
        }
        return gameLobby.getData(username,onlineKey);
    }

    @PostMapping(url+"/home/game/over")
    private String setGameOver(String username,String onlineKey){
        if (username==null || onlineKey==null){
            return BAD_REQUEST;
        }
        return gameLobby.setGameOver(username, onlineKey);
    }

}
