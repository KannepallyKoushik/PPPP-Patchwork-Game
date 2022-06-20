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
    public String getInstructionsString() {
        String pattern = "Use A and D to pick a patch and use Enter to confirm your selection.";

        String word = switch (options.indexOf(selectedPatch)) {
            case 0 -> "first";
            case 1 -> "second";
            case 2 -> "third";
            default -> String.valueOf(options.indexOf(selectedPatch));
        };

        return String.format(pattern, word);
    }
}
