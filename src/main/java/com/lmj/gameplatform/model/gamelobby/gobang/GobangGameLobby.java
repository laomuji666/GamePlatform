package com.lmj.gameplatform.model.gamelobby.gobang;

import com.lmj.gameplatform.model.gamelobby.GameLobby;
import org.springframework.stereotype.Controller;

import static com.lmj.gameplatform.model.gamelobby.GameLobbyId.GOBANG;

@Controller
public class GobangGameLobby extends GameLobby {
    private static final GobangHome[] homes=new GobangHome[20];
    static {
        for (int i=0;i<homes.length;i++){
            homes[i]=new GobangHome();
        }
    }
    public GobangGameLobby() {
        super(GOBANG, "五子棋",homes);
    }
}
