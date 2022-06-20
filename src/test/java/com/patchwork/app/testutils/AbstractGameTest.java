package com.patchwork.app.testutils;

import com.patchwork.app.backend.controller.Inputs.MockGameInput;
import com.patchwork.app.backend.controller.GameController.GameController;
import com.patchwork.app.backend.controller.GameController.GameControllerFactory;
import com.patchwork.app.backend.model.Game;
import com.patchwork.app.backend.model.GameFactory;
import com.patchwork.app.backend.model.Patch;
import com.patchwork.app.backend.model.Player;
import com.patchwork.app.frontend.TUI;
import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class AbstractGameTest {

    protected boolean autostart_game_controller = true;

    protected Game game;
    protected GameController gameController;
    protected MockGameInput gameInput;
    protected TUI tui;
    protected Thread gameControllerThread;

    protected Player startingPlayer;
    protected Player nextPlayer;

    protected ByteArrayOutputStream tuiOutput;

    private void createAndSetGame(GameControllerFactory gameControllerFactory) {
        if (gameControllerThread != null) {
            gameController.stop();
            gameControllerThread.stop();
        }

        gameController = gameControllerFactory.createGameController();
        game = gameControllerFactory.getGame();
        gameInput = (MockGameInput) gameControllerFactory.getGameInput();
        tui = gameControllerFactory.getTui();
        startingPlayer = game.timeBoard.getCurrentPlayer();
        nextPlayer = game.getOpponent(startingPlayer);

        gameControllerThread = new Thread(gameController);

        if(autostart_game_controller) {
            gameControllerThread.start();
        }
    }

    @Before
    public void setUp() {
        // Create game
        GameFactory gameFactory = new GameFactory();
        MockGameControllerFactory gameControllerFactory = new MockGameControllerFactory(gameFactory);
        createAndSetGame(gameControllerFactory);

        // Override TUI output
        tuiOutput = gameControllerFactory.getTuiOutputStream();
    }

    @After
    public void tearDown() throws InterruptedException {
        gameController.stop();
        gameControllerThread.join();
    }

    public void createGameWithPatchList(List<Patch> patches) {
        GameFactory gameFactory = new GameFactory();
        gameFactory.setPatchListFactory(new MockPatchListFactory(patches));
        GameControllerFactory gameControllerFactory = new MockGameControllerFactory(gameFactory);

        createAndSetGame(gameControllerFactory);
    }
}
