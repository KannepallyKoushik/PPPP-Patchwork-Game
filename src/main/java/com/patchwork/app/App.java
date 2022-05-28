package com.patchwork.app;

import com.patchwork.app.backend.Game;
import com.patchwork.app.backend.GameController;
import com.patchwork.app.backend.GameException;
import com.patchwork.app.frontend.TUI;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws GameException {
        System.out.println( "Hello World!" );

        // TODO: maybe do something with arguments, launch the game
        GameController gc = new GameController();
        gc.run();


    }
}
