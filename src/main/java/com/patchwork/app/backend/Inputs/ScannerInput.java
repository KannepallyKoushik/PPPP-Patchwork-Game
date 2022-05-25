package com.patchwork.app.backend.Inputs;


import com.patchwork.app.backend.Move;

import java.util.Scanner;

public class ScannerInput extends GameInput {

    private Scanner s;

    @Override
    public void run() {

        while(true){
            String input = s.nextLine();
//            if (inpu)
        }
    }


    public Move getMoveFromInput(String input ) {
        if (input.equals("CONFIRM")) {
            return Move.CONFIRM;
        } else if (input.equals("BUY")) {
            boolean canBuy = false;
//            for (int i = 0; i < game.patchList.getAvailablePatches().size(); i++) {
//                if (currentPlayer.nrButtons > game.patchList.getAvailablePatches().get(i).buttonCost) {
//                    canBuy = true;
//                }
//            }
            if (canBuy) {
//                return "BUY";
            } else {
                System.out.println("Can not afford any patches, automatically choose move");
//                return "MOVE";
            }
        } else {
            System.out.println("Please enter a valid command");
        }
        return null;
    }
}

