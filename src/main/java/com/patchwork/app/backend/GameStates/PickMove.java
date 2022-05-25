package com.patchwork.app.backend.GameStates;

import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.Player;
import com.patchwork.app.backend.QuiltBoard;
import com.patchwork.app.backend.TimeBoard;

import java.util.List;

public class PickMove extends GameState {
    public List<Patch> options;
    public TimeBoard timeBoard;


    public PickMove(Player player, List<Patch> options, TimeBoard timeBoard){
        super(player);
        this.type = GameStateType.PICK_MOVE;
        this.options = options;
        this.timeBoard = timeBoard;
    }
}
