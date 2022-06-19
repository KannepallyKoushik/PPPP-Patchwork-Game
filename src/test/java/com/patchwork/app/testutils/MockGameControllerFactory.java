package com.patchwork.app.testutils;

import com.patchwork.app.backend.controller.GameController.GameController;
import com.patchwork.app.backend.controller.GameController.GameControllerFactory;
import com.patchwork.app.backend.model.GameFactory;
import com.patchwork.app.backend.controller.Inputs.GameInput;
import com.patchwork.app.backend.controller.Inputs.MockGameInput;

public class MockGameControllerFactory extends GameControllerFactory {

    public MockGameControllerFactory(GameFactory gameFactory) {
        super(gameFactory);
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
}
