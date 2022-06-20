package com.patchwork.app.backend.Inputs;

import com.patchwork.app.backend.Move;

public class MockGameInput extends GameInput {

    public void updateMove(Move move) {
        notify(move);
    }

    @Override
    public String getHelpText() {
        return "mockhelp";
    }
}
