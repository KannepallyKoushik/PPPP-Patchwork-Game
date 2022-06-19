package com.patchwork.app.backend.controller.GameController;

import com.patchwork.app.backend.model.Game;
import com.patchwork.app.backend.model.GameFactory;
import com.patchwork.app.backend.controller.Inputs.GameInput;
import com.patchwork.app.backend.controller.Inputs.ScannerInput;
import com.patchwork.app.frontend.TUI;

public class GameControllerFactory {

    private final GameFactory gameFactory;
    private Game game;
    private TUI tui;
    private GameInput gameInput;

    public GameControllerFactory(GameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }

    public GameController createGameController() {
        game = gameFactory.createGame();
        tui = createTUI(game);
        gameInput = createGameInput();

        return new GameController(game, tui, gameInput);
    }

    protected TUI createTUI(Game game) {
        return new TUI(game);
    }

    protected GameInput createGameInput() {
        return new ScannerInput();
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
