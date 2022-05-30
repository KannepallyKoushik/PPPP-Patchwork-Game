package com.patchwork.app.backend.GameStates;

//Game State when game is finished

public class Finished extends GameState {
    public Finished() {
        super();
        this.type = GameStateType.FINISHED;
    }
}
