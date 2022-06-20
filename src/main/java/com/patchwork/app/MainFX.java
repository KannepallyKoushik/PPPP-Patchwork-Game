package com.patchwork.app;

import com.patchwork.app.backend.GameController;
import com.patchwork.app.backend.GameControllerFactory;
import com.patchwork.app.backend.GameFactory;
import com.patchwork.app.frontend.GUI;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.concurrent.CountDownLatch;

public class MainFX extends Application {

    private GUI gui;
    private Stage stage;
    private Thread gameWorkerThread;
    private boolean shouldExit = false;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        gui = new GUI(stage);
        gameWorkerThread = new Thread(this::gameWorker);
        gameWorkerThread.start();
    }

    @Override
    public void stop() throws Exception {
        shouldExit = true;
        joinThread(gameWorkerThread);

        super.stop();
    }

    private void gameWorker() {
        while (true) {
            GameController gameController = makeGameController();
            Thread gameControllerThread = new Thread(gameController);
            gameControllerThread.start();

            waitGameFinished(gameControllerThread);
            gameController.stop();
            joinThread(gameControllerThread);

            if (shouldExit) {
                return;
            }

            waitUntilEnterPressed();
        }
    }

    private void waitUntilEnterPressed() {
        CountDownLatch l = new CountDownLatch(1);
        stage.getScene().setOnKeyReleased(e -> {
            l.countDown();
        });

        try {
            l.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            shouldExit = true;
        }
    }

    private GameController makeGameController() {
        GameFactory gameFactory = new GameFactory();
        GameControllerFactory gameControllerFactory = new GameControllerFactory(gui, gameFactory);
        return gameControllerFactory.createGameController();
    }

    private void waitGameFinished(Thread gameControllerThread) {
        try {
            while (gameControllerThread.isAlive() && !shouldExit) {
                Thread.sleep(500);
            }
        } catch (InterruptedException ignored) {
        }
    }

    private void joinThread(Thread t) {
        if (!t.isAlive()) {
            return;
        }

        try {
            t.join();
        } catch (InterruptedException ignored) {
        }
    }
}
