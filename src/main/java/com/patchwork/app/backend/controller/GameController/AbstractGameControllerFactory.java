package com.patchwork.app.backend.controller.GameController;

import com.patchwork.app.backend.controller.Inputs.GameInput;
import com.patchwork.app.backend.model.AbstractGameFactory;
import com.patchwork.app.backend.model.Game;
import com.patchwork.app.frontend.GUI;
import com.patchwork.app.frontend.TUI;

public abstract class AbstractGameControllerFactory {

    protected AbstractGameFactory gameFactory;
    protected GUI gui;
    protected Game game;
    protected TUI tui;
    protected GameInput gameInput;

    public abstract GameController createGameController();

    protected abstract TUI createTUI(Game game);

    protected abstract GameInput createGameInput();

    public Game getGame() {
        return game;
    }

    public TUI getTui() {
        return tui;
    }

    public GameInput getGameInput() {
        return gameInput;
    }
}
