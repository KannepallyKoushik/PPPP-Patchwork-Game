package com.patchwork.app.backend.Inputs;

import com.patchwork.app.backend.Move;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ScannerCommands {

    private final Map<String, Move> scannerCommands;

    public ScannerCommands() {
        this.scannerCommands = new HashMap<>();
    }

    public void add(String input, Move move){
        this.scannerCommands.put(input, move);
    }


    public Set<String> getInputs(){
        return this.scannerCommands.keySet();
    }



    public Map<String, Move> getScannerCommands(){
        return this.scannerCommands;
    }
}
