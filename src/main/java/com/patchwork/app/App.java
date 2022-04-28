package com.patchwork.app;

import com.patchwork.app.backend.Game;
import com.patchwork.app.frontend.TUI;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        // TODO: maybe do something with arguments, launch the game
        Game game = new Game();
        TUI tui = new TUI(game);
        tui.run();
    }
}
