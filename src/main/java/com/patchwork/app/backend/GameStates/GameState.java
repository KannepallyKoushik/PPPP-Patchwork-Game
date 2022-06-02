package com.patchwork.app.backend.GameStates;

import com.patchwork.app.backend.Player;
import com.patchwork.app.backend.QuiltBoard;
import com.patchwork.app.frontend.TUI;

public abstract class GameState {

    public GameStateType type;

    public abstract void draw(TUI tui);

    public abstract void drawInstructions(TUI tui);
}
