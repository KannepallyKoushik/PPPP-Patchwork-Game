package com.patchwork.app.backend.controller.Inputs;

import com.patchwork.app.backend.controller.GameStates.Move;

public class ScannerCommandsFactory {

    public ScannerCommands createScannerCommands(){
        ScannerCommands scannerCommands = new ScannerCommands();
        scannerCommands.add("CONFIRM", Move.CONFIRM);
        scannerCommands.add("LEFT", Move.MOVE_LEFT);
        scannerCommands.add("RIGHT", Move.MOVE_RIGHT);
        scannerCommands.add("UP", Move.MOVE_UP);
        scannerCommands.add("DOWN", Move.MOVE_DOWN);
        scannerCommands.add("ROTATECW", Move.ROTATE_CLOCKWISE);
        scannerCommands.add("ROTATECCW", Move.ROTATE_COUNTERCLOCKWISE);
        scannerCommands.add("MIRRORV", Move.MIRROR_VERTICAL);
        scannerCommands.add("MIRRORH", Move.MIRROR_HORIZONTAL);
        scannerCommands.add("HELP", Move.HELP);
        return scannerCommands;


    }
}
