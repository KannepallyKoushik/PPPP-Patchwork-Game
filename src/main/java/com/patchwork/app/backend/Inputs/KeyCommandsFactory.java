package com.patchwork.app.backend.Inputs;

import com.patchwork.app.backend.Move;

import java.awt.event.KeyEvent;

public class KeyCommandsFactory {

    public KeyCommands createKeyCommands(){
        KeyCommands keyCommands = new KeyCommands();
        keyCommands.add('4', Move.MOVE_LEFT);
        keyCommands.add('6', Move.MOVE_RIGHT);
        keyCommands.add('8', Move.MOVE_UP);
        keyCommands.add('5', Move.MOVE_DOWN);
        keyCommands.add('1', Move.ROTATE_CLOCKWISE);
        keyCommands.add('2', Move.ROTATE_COUNTERCLOCKWISE);
        keyCommands.add('3', Move.MIRROR_VERTICAL);
        keyCommands.add('7', Move.MIRROR_HORIZONTAL);
        keyCommands.add('9', Move.HELP);
        keyCommands.add('0', Move.CONFIRM);


        keyCommands.add('a', Move.MOVE_LEFT);
        keyCommands.add('d', Move.MOVE_RIGHT);
        keyCommands.add('w', Move.MOVE_UP);
        keyCommands.add('s', Move.MOVE_DOWN);
        keyCommands.add('q', Move.ROTATE_CLOCKWISE);
        keyCommands.add('e', Move.ROTATE_COUNTERCLOCKWISE);
        keyCommands.add('r', Move.MIRROR_VERTICAL);
        keyCommands.add('t', Move.MIRROR_HORIZONTAL);
        keyCommands.add('h', Move.HELP);
        keyCommands.add(' ', Move.CONFIRM);
        return keyCommands;


    }
}
