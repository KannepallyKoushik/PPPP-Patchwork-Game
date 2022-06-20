package com.patchwork.app.backend.GameStates;

import com.patchwork.app.backend.GameResult;
import com.patchwork.app.frontend.TUI;

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
