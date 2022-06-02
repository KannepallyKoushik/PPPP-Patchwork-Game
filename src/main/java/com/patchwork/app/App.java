package com.patchwork.app;

import com.patchwork.app.backend.GameController;
import com.patchwork.app.backend.Exceptions.GameException;
import com.patchwork.app.backend.GameControllerFactory;
import com.patchwork.app.backend.GameFactory;
import com.patchwork.app.backend.Inputs.ScannerInput;
import com.patchwork.app.frontend.TUI;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws GameException, InterruptedException {
        System.out.println( "Hello World!" );

        // TODO: maybe do something with arguments, launch the game
        GameController gc = new GameControllerFactory(new GameFactory()).createGameController();
        gc.run();
    }
}
