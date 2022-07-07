package com.patchwork.app.backend.controller.GameController;

import com.patchwork.app.backend.controller.Inputs.GameInput;
import com.patchwork.app.backend.model.AbstractGameFactory;
import com.patchwork.app.backend.model.Game;
import com.patchwork.app.frontend.GUI;
import com.patchwork.app.frontend.TUI;

public class GameControllerFactory extends AbstractGameControllerFactory {


    public GameControllerFactory(GUI gui, AbstractGameFactory gameFactory) {
        this.gameFactory = gameFactory;
        this.gui = gui;
    }

    @Override
    public GameController createGameController() {
        game = gameFactory.createGame();
        tui = createTUI(game);
        gameInput = createGameInput();

        return new GameController(game, tui, gameInput);
    }

    @Override
    protected TUI createTUI(Game game) {
        return new TUI(game, gui);
    }

    @Override
    protected GameInput createGameInput() {
        return gui;
    }
}
