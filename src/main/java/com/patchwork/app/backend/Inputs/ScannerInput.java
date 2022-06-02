package com.patchwork.app.backend.Inputs;


import com.patchwork.app.backend.Move;

import java.util.Scanner;

public class ScannerInput extends GameInput {

    private final Scanner scanner;

    public ScannerInput() {
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        while (true) {
            String input = scanner.nextLine();
            notify(getMoveFromInput(input));
        }
    }

    public Move getMoveFromInput(String input) {
        if (input.equals("CONFIRM")) {
            return Move.CONFIRM;
        } else if (input.equals("LEFT")) {
            return Move.MOVE_LEFT;
        } else if (input.equals("RIGHT")) {
            return Move.MOVE_RIGHT;
        } else if (input.equals("UP")) {
            return Move.MOVE_UP;
        } else if (input.equals("DOWN")) {
            return Move.MOVE_DOWN;
        } else {
            System.out.println("Please enter a valid command");
        }
        return Move.WAITING;
    }
}

