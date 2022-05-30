package com.patchwork.app.backend.GameStates;

import com.patchwork.app.backend.Player;
import com.patchwork.app.backend.QuiltBoard;

public abstract class GameState {
    public Player player;
    public GameStateType type;
    public QuiltBoard quiltBoard;

    public GameState(Player player, QuiltBoard quiltBoard) {
        this.player = player;
        this.quiltBoard = quiltBoard;
    }

    public GameState() {

    }
}
