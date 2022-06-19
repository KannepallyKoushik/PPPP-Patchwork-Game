package com.patchwork.app.backend.controller.GameStates;

import com.patchwork.app.frontend.TUI;

public abstract class GameState {

    public GameStateType type;

    public abstract void draw(TUI tui);

    public abstract void drawInstructions(TUI tui);
}
