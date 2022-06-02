package com.patchwork.app.backend.GameStates;

//Game State when game is finished

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
    public void draw(TUI tui) {
        if (gameResult == null) {
            tui.drawMessage("Game was interrupted, no results to show.");
        } else {
            // TODO: implement
        }
    }

    @Override
    public void drawInstructions(TUI tui) {
        // Do nothing
    }
}
