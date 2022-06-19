package com.patchwork.app.backend.controller.GameStates;

public class MoveResult {

    private final int nrButtons;
    private final int nrSpecialPatches;

    public MoveResult(int nrButtons, int nrSpecialPatches) {
        this.nrButtons = nrButtons;
        this.nrSpecialPatches = nrSpecialPatches;
    }

    public int getNrButtons() {
        return nrButtons;
    }

    public int getNrSpecialPatches() {
        return nrSpecialPatches;
    }
}
