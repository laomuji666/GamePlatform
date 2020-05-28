package com.lmj.gameplatform.model.gamelobby.gobang;

import com.lmj.gameplatform.model.gamelobby.GameLobby;
import org.springframework.stereotype.Controller;

import static com.lmj.gameplatform.model.gamelobby.GameLobbyId.GOBANG;

@Controller
public class GobangGameLobby extends GameLobby {
    public GobangGameLobby() {
        super(GOBANG, "五子棋");
    }
}
