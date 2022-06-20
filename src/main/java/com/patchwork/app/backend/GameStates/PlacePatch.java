package com.patchwork.app.backend.GameStates;

import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.Player;
import com.patchwork.app.frontend.TUI;

public class PlacePatch extends GameState {

    public Player player;
    public Patch patch;
    public int x;
    public int y;

    public PlacePatch(Player player, Patch patch, int x, int y){
        super();

        this.player = player;
        this.type = GameStateType.PLACE_PATCH;
        this.patch = patch;
        this.x = x;
        this.y = y;
    }

    @Override
    public String getInstructionsString() {
        return "Place your patch using WASD to move it, Q and E to rotate it and Enter to place it.";
    }
}
