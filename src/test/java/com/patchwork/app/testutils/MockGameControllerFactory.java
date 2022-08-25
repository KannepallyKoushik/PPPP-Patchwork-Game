package com.patchwork.app.testutils;

import com.patchwork.app.backend.controller.GameController.AbstractGameControllerFactory;
import com.patchwork.app.backend.controller.GameController.GameController;
import com.patchwork.app.backend.controller.Inputs.GameInput;
import com.patchwork.app.backend.controller.Inputs.MockGameInput;
import com.patchwork.app.backend.model.AbstractGameFactory;
import com.patchwork.app.backend.model.Game;
import com.patchwork.app.frontend.TUI;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MockGameControllerFactory extends AbstractGameControllerFactory {

    private ByteArrayOutputStream tuiOutputStream;
    private final AbstractGameFactory gameFactory;

    public MockGameControllerFactory(AbstractGameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }

    @Override
    public GameController createGameController() {
        game = gameFactory.createGame();
        tui = createTUI(game);
        gameInput = createGameInput();

        GameController gameController = new GameController(game, tui, gameInput);
        gameController.setTickSpeed(5);

        return gameController;
    }

    @Override
    protected GameInput createGameInput() {
        return new MockGameInput();
    }

    @Override
    protected TUI createTUI(Game game) {
        tuiOutputStream = new ByteArrayOutputStream();
        return new TUI(game, new MockGUIWritable(new PrintStream(tuiOutputStream)));
    }

    public ByteArrayOutputStream getTuiOutputStream() {
        return tuiOutputStream;
    }
}
