package com.patchwork.app.backend.controller.Inputs;


import com.patchwork.app.backend.controller.GameStates.Move;

public class MockGameInput extends GameInput {

    public void updateMove(Move move) {
        notify(move);
    }

    @Override
    public String getHelpText() {
        return "mockhelp";
    }
}
