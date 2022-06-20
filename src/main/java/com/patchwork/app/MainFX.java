package com.patchwork.app;

import com.patchwork.app.backend.GameController;
import com.patchwork.app.backend.GameControllerFactory;
import com.patchwork.app.backend.GameFactory;
import com.patchwork.app.frontend.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainFX extends Application {

    private Thread gameControllerThread;
    private GameController gameController;

    @Override
    public void start(Stage stage) {
        GUI gui = new GUI(stage);

        GameFactory gameFactory = new GameFactory();
        GameControllerFactory gameControllerFactory = new GameControllerFactory(gui, gameFactory);

        gameController = gameControllerFactory.createGameController();

        gameControllerThread = new Thread(gameController);
        gameControllerThread.start();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        try {
            gameController.stop();
            gameControllerThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}
