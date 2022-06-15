package com.patchwork.app.backend.Inputs;

import com.patchwork.app.backend.Move;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class KeyCommands {

    private final Map<Character, Move> keyCommands;

    public KeyCommands() {
        this.keyCommands = new HashMap<>();
    }

    public void add(char input, Move move) {
        this.keyCommands.put(input, move);
    }


    public Set<Character> getInputs() {
        return this.keyCommands.keySet();
    }

    public Set<Map.Entry<Character, Move>> getMoves() {
        return this.keyCommands.entrySet();
    }


    public Map<Character, Move> getKeyCommands() {
        return this.keyCommands;
    }
}
