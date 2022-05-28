package com.patchwork.app.backend.GameStates;

import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.Player;

public class PlacePatch extends GameState {
    public Patch patch;
    public int x;
    public int y;

    public PlacePatch(Player player, Patch patch, int x, int y){
        super(player);
        this.player = player;
        this.type = GameStateType.PLACE_PATCH;
        this.patch = patch;
        this.x = x;
        this.y = y;
    }
}
