package com.patchwork.app.backend.Inputs;


import com.patchwork.app.backend.Move;

import java.util.Scanner;

public class ScannerInput extends GameInput {

    private final Scanner scanner;
    private final ScannerCommands scannerCommands;

    public ScannerInput() {
        scanner = new Scanner(System.in);
        scannerCommands = new ScannerCommandsFactory().createScannerCommands();
    }


    @Override
    public void run() {
        while (true) {
            String input = scanner.nextLine();
            Move move = getMoveFromInput(input);
            if (move != null) {
                notify(move);
            }
        }
    }

    public Move getMoveFromInput(String input) {
        Move move = scannerCommands.getScannerCommands().get(input);
        if (move == null){
            System.out.println("Please enter a valid command; see list of commands with HELP");
            return null;
        }
        else {
            return move;
        }
    }

    public ScannerCommands getScannerCommands(){
        return this.scannerCommands;
    }
}

