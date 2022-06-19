package com.patchwork.app;

import com.patchwork.app.backend.controller.GameController.GameController;
import com.patchwork.app.backend.exceptions.GameException;
import com.patchwork.app.backend.controller.GameController.GameControllerFactory;
import com.patchwork.app.backend.model.GameFactory;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )  {
        System.out.println( "Hello World!" );

        // TODO: maybe do something with arguments, launch the game
        GameController gc = new GameControllerFactory(new GameFactory()).createGameController();
        gc.run();
    }
}
