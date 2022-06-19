package com.patchwork.app.backend.controller.GameStates;

import com.patchwork.app.backend.model.Patch;
import com.patchwork.app.backend.model.Player;
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
    public void draw(TUI tui) {
        tui.drawPlacePatchState(this);
    }

    @Override
    public void drawInstructions(TUI tui) {
        tui.drawMessage("Please place your patch, with either LEFT RIGHT UP DOWN or CONFIRM");
    }
}
