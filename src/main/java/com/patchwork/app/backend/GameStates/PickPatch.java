package com.patchwork.app.backend.GameStates;

import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.Player;

import java.util.List;

public class PickPatch extends GameState  {
    public List<Patch> options;
    public Patch selectedPatch;


    public PickPatch(Player player, List<Patch> options, Patch selectedPatch){
        super(player);
        this.player = player;
        this.type = GameStateType.PICK_PATCH;
        this.options = options;
        this.selectedPatch = selectedPatch;
    }


}
