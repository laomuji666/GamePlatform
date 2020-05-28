package com.lmj.gameplatform.model.gamelobby.chinesechess;

import com.lmj.gameplatform.model.gamelobby.GameLobby;
import com.lmj.gameplatform.model.gamelobby.GameLobbyId;
import org.springframework.stereotype.Controller;

@Controller
public class ChineseChessGameLobby extends GameLobby {
    ChineseChessGameLobby(){
        super(GameLobbyId.CHINESECHESS,"中国象棋");
    }
}
