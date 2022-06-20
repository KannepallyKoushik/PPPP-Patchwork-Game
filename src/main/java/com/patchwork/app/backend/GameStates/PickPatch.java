package com.patchwork.app.backend.GameStates;

import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.Player;
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
        String pattern = "You are currently choosing the %s patch.";

        String word = switch (options.indexOf(selectedPatch)) {
            case 0 -> "first";
            case 1 -> "second";
            case 2 -> "third";
            default -> String.valueOf(options.indexOf(selectedPatch));
        };

        return String.format(pattern, word);
    }
}
