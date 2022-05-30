package com.patchwork.app.backend.GameStates;

import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.Player;
import com.patchwork.app.backend.QuiltBoard;

import java.util.List;

public class PickPatch extends GameState  {
    public List<Patch> options;
    public int selectedPatch;


    public PickPatch(Player player, QuiltBoard quiltBoard, List<Patch> options, int selectedPatch ){
        super(player, quiltBoard);
        this.player = player;
        this.type = GameStateType.PICK_PATCH;
        this.options = options;
        this.selectedPatch = selectedPatch;

    }


}
