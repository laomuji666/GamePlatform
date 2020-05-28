package com.lmj.gameplatform.mapping;

import com.lmj.gameplatform.model.gamelobby.GameLobby;
import com.lmj.gameplatform.model.gamelobby.chinesechess.ChineseChessGameLobby;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.lmj.gameplatform.model.account.AccountJson.BAD_REQUEST;

@RestController
public class ChineseChessMapping {
    private static final String url="/ChineseChess";
    @Autowired private ChineseChessGameLobby gameLobby=null;

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
}
