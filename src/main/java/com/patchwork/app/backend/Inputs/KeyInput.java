package com.patchwork.app.backend.Inputs;

import com.patchwork.app.backend.Move;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.NonBlockingReader;

import java.awt.event.KeyEvent;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.io.IOException;
import java.io.Reader;
import java.util.Scanner;


public class KeyInput extends GameInput {

    private final KeyCommands keyCommands;
    private final Scanner scanner;


    public KeyInput()  {
        scanner = new Scanner(System.in);
        keyCommands = new KeyCommandsFactory().createKeyCommands();
    }

    @Override
    public void run() {
        while (true) {
            /*
            Terminal terminal = null;
            try {
                terminal = TerminalBuilder.builder().jna(true).system(true).build();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // raw mode *should* read for keypresses rather than full lines (after enter) but doesnt work
            terminal.enterRawMode();
            NonBlockingReader reader = terminal.reader();
            try {
                int read = reader.read();
                reader.close();
                terminal.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            So instead just do it with scanner
            */
            char input = scanner.next().charAt(0);
            Move move = getMoveFromInput(input);
            if (move != null) {
                notify(move);
            }
        }
    }


    public Move getMoveFromInput(char input) {
        Move move = keyCommands.getKeyCommands().get(input);
        if (move == null) {
            System.out.println("Please enter a valid command; see list of commands with HELP");
            return null;
        } else {
            return move;
        }
    }

    public KeyCommands getKeyCommands() {
        return this.keyCommands;
    }
}
