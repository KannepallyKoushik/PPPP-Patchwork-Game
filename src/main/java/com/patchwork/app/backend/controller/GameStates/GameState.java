package com.patchwork.app.backend.controller.GameStates;

import com.patchwork.app.frontend.TUI;

public abstract class GameState {

    public GameStateType type;

    public abstract String getInstructionsString();
}
