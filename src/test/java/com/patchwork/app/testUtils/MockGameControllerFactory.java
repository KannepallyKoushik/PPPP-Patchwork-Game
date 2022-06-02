package com.patchwork.app.testUtils;

import com.patchwork.app.backend.GameControllerFactory;
import com.patchwork.app.backend.GameFactory;
import com.patchwork.app.backend.Inputs.GameInput;
import com.patchwork.app.backend.Inputs.MockGameInput;

public class MockGameControllerFactory extends GameControllerFactory {

    public MockGameControllerFactory(GameFactory gameFactory) {
        super(gameFactory);
    }

    @Override
    protected GameInput createGameInput() {
        return new MockGameInput();
    }
}
