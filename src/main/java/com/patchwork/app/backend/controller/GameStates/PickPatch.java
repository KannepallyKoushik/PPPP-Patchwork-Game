package com.patchwork.app.backend.controller.GameStates;

import com.patchwork.app.backend.model.Patch;
import com.patchwork.app.backend.model.Player;
import com.patchwork.app.frontend.TUI;

import java.util.List;

public class PickPatch extends GameState {

    public Player player;
    public List<Patch> options;
    public Patch selectedPatch;

    public PickPatch(Player player, List<Patch> options, Patch selectedPatch) {
        super();

        this.player = player;
        this.type = GameStateType.PICK_PATCH;
        this.options = options;
        this.selectedPatch = selectedPatch;
    }

    @Override
    public void draw(TUI tui) {
        tui.drawPickPatchState(this);
    }

    @Override
    public void drawInstructions(TUI tui) {
        tui.drawMessage("Please choose your patch by typing LEFT or RIGHT or CONFIRM");
        tui.drawMessage("You are currently choosing the " + options.indexOf(selectedPatch) + " patch.");
    }
}
