package com.patchwork.app.backend.controller.GameController;

import com.patchwork.app.backend.controller.Inputs.GameInput;
import com.patchwork.app.backend.model.Game;
import com.patchwork.app.backend.model.GameFactory;
import com.patchwork.app.frontend.GUI;
import com.patchwork.app.frontend.TUI;

public class GameControllerFactory {

    private final GameFactory gameFactory;
    private final GUI gui;
    private Game game;
    private TUI tui;
    private GameInput gameInput;

    public GameControllerFactory(GUI gui, GameFactory gameFactory) {
        this.gameFactory = gameFactory;
        this.gui = gui;
    }

    public GameController createGameController() {
        game = gameFactory.createGame();
        tui = createTUI(game);
        gameInput = createGameInput();

        return new GameController(game, tui, gameInput);
    }

    protected TUI createTUI(Game game) {
        return new TUI(game, gui);
    }

    protected GameInput createGameInput() {
        return gui;
    }

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
