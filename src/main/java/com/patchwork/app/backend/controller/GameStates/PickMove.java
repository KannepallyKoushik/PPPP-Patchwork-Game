package com.patchwork.app.backend.controller.GameStates;

import com.patchwork.app.backend.model.Patch;
import com.patchwork.app.backend.model.Player;
import com.patchwork.app.frontend.TUI;

import java.util.Arrays;
import java.util.List;

public class PickMove extends GameState {

    public Player player;
    public List<Patch> patchOptions;
    public List<MoveOption> moveOptions = Arrays.asList(MoveOption.MOVE_TO_NEXT_PLAYER, MoveOption.PLACE_PATCH);
    public MoveOption selectedOption;


    public PickMove(Player player, MoveOption selectedOption, List<Patch> patchOptions) {
        super();

        this.player = player;
        this.type = GameStateType.PICK_MOVE;
        this.selectedOption = selectedOption;
        this.patchOptions = patchOptions;
    }

    public PickMove(Player player, List<Patch> patchOptions) {
        super();

        this.player = player;
        this.type = GameStateType.PICK_MOVE;
        this.selectedOption = MoveOption.MOVE_TO_NEXT_PLAYER;
        this.patchOptions = patchOptions;
    }

    public enum MoveOption {
        MOVE_TO_NEXT_PLAYER,
        PLACE_PATCH
    }

    @Override
    public String getInstructionsString() {
        return "Use WASD and Enter to select your move.";
    }
}
