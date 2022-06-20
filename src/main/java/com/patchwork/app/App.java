package com.patchwork.app;

import com.patchwork.app.backend.GameController;
import com.patchwork.app.backend.Exceptions.GameException;
import com.patchwork.app.backend.GameControllerFactory;
import com.patchwork.app.backend.GameFactory;
import com.patchwork.app.frontend.TUI;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) throws GameException, InterruptedException, IOException {
        System.out.println( "Hello World!" );

        Reader reader = new InputStreamReader(System.in);

        while (true) {
            int data = reader.read();
            System.out.println(data);
        }

        // TODO: maybe do something with arguments, launch the game
//        GameController gc = new GameControllerFactory(new GameFactory()).createGameController();
//        gc.run();
    }
}
