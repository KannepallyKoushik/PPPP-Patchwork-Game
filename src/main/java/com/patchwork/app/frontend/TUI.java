package com.patchwork.app.frontend;

import com.patchwork.app.backend.Game;
import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.QuiltBoard;
import com.patchwork.app.utils.ConsoleColor;

import java.util.List;

public class TUI {
    // TODO: Get tests to pass with Unicode SPACE_CHAR \ u2B1B (Black Large Square)
    //public static char SPACE_CHAR = '\ u2B1B'; // Unicode Black Large Square
    public static char SPACE_CHAR = 'X';

    public Game game;

    public TUI(Game game) {
        this.game = game;
    }

    public void run() {
        // TODO: implement
    }

    public void drawQuiltBoard(QuiltBoard quiltBoard) {
        for(List<Boolean> row : quiltBoard.spaces) {
            for(Boolean boardSpace : row) {
                System.out.print(boardSpace ? ConsoleColor.BLUE : ConsoleColor.WHITE);
                System.out.print(SPACE_CHAR + " ");
            }
            System.out.println();
        }
    }

    // patchX and patchY are the coordinates from the top-left of the board
    public void drawQuiltBoardWithPatch(QuiltBoard quiltBoard, Patch patch, int patchX, int patchY) {
        for(int r = 0; r < quiltBoard.spaces.size(); r++) {
            List<Boolean> row = quiltBoard.spaces.get(r);
            for(int c = 0; c < row.size(); c++) {
                Boolean boardSpace = row.get(c);
                Boolean patchSpace = false;

                // Check if current space is within bounds of the patch
                if(r-patchY >= 0 &&
                        r-patchY < patch.spaces.size() &&
                        c-patchX >= 0 &&
                        c-patchX < patch.spaces.get(r-patchY).size()) {
                    // Check the state of this space of the patch
                    patchSpace = patch.spaces.get(r-patchY).get(c-patchX);
                }

                if(boardSpace) {
                    System.out.print(patchSpace ? ConsoleColor.RED : ConsoleColor.BLUE);
                } else {
                    System.out.print(patchSpace ? ConsoleColor.GREEN : ConsoleColor.WHITE);
                }
                System.out.print(SPACE_CHAR + " ");
            }
            System.out.println();
        }
    }
}
