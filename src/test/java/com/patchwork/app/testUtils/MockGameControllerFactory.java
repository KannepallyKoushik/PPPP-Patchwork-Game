package com.patchwork.app.testUtils;

import com.patchwork.app.backend.Game;
import com.patchwork.app.backend.GameController;
import com.patchwork.app.backend.GameControllerFactory;
import com.patchwork.app.backend.GameFactory;
import com.patchwork.app.backend.Inputs.GameInput;
import com.patchwork.app.backend.Inputs.MockGameInput;
import com.patchwork.app.frontend.TUI;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MockGameControllerFactory extends GameControllerFactory {

    private ByteArrayOutputStream tuiOutputStream;

    public MockGameControllerFactory(GameFactory gameFactory) {
        super(null, gameFactory);
    }

    @Override
    public GameController createGameController() {
        GameController gameController = super.createGameController();
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
