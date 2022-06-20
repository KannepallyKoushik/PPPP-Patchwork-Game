package com.patchwork.app.backend.controller.GameStates;

import com.patchwork.app.backend.model.GameResult;

public class Finished extends GameState {

    public GameResult gameResult;

    public Finished(GameResult gameResult) {
        super();

        this.type = GameStateType.FINISHED;
        this.gameResult = gameResult;
    }

    @Override
    public String getInstructionsString() {
        return "Press Enter to play another game, or close the game to quit.";
    }
}
