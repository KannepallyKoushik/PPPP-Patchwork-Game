package com.patchwork.app.backend.GameStates;

import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.Player;
import com.patchwork.app.backend.QuiltBoard;

public abstract class GameState {
    public Player player;
    public GameStateType type;

    public GameState(Player player){
        this.player = player;
    }
}
