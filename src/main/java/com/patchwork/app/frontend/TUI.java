package com.patchwork.app.frontend;

import com.patchwork.app.backend.model.Game;
import com.patchwork.app.backend.controller.GameStates.PickMove;
import com.patchwork.app.backend.controller.GameStates.PickPatch;
import com.patchwork.app.backend.controller.GameStates.PlacePatch;
import com.patchwork.app.backend.model.Patch;
import com.patchwork.app.backend.model.QuiltBoard;
import com.patchwork.app.backend.model.TimeBoard.SpaceElement;
import com.patchwork.app.utils.ConsoleColor;

import java.io.PrintStream;
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

    public void drawMessage(String message) {
        System.out.println(message);
    }

    public void drawPlacePatchState(PlacePatch gameState) {
        this.drawQuiltBoardWithPatch(gameState.player.quiltBoard,
                gameState.patch,
                gameState.x,
                gameState.y,
                System.out);
    }

    public void drawPickPatchState(PickPatch gameState) {
        this.drawQuiltBoard(gameState.player.quiltBoard, System.out);
        this.drawPatches(gameState.options, gameState.options.indexOf(gameState.selectedPatch), System.out);
    }

    public void drawPickMoveState(PickMove gameState) {
        this.drawTimeBoard(System.out);
        this.drawQuiltBoard(gameState.player.quiltBoard, System.out);
        this.drawPatches(gameState.patchOptions, -1, System.out);
    }

    public void drawQuiltBoard(QuiltBoard quiltBoard, PrintStream out) {
        for(int i=0; i < quiltBoard.spaces.length;i++){
            for(int j=0; j < quiltBoard.spaces[i].length;j++){
                this.drawQuiltBoardSpace(quiltBoard.spaces[i][j],false, out);
            }
            out.println();
        }
    }

    // patchX and patchY are the coordinates from the top-left of the board
    public void drawQuiltBoardWithPatch(QuiltBoard quiltBoard, Patch patch, int patchX, int patchY, PrintStream out) {
        for(int r = 0; r < quiltBoard.spaces.length; r++) {
            for(int c = 0; c < quiltBoard.spaces[r].length ; c++){
                boolean boardSpace = quiltBoard.spaces[r][c];
                boolean patchSpace = false;

                // Check if current space is within bounds of the patch
                if(r-patchY >= 0 &&
                        r-patchY < patch.spaces.size() &&
                        c-patchX >= 0 &&
                        c-patchX < patch.spaces.get(r-patchY).size()) {
                    // Check the state of this space of the patch
                    patchSpace = patch.spaces.get(r-patchY).get(c-patchX);
                }
                this.drawQuiltBoardSpace(boardSpace, patchSpace, out);
            }
            out.println();
        }
    }

    public void drawTimeBoard(PrintStream out) {
        if(this.game.timeBoard.spaces.size() != 52) return; // TO DO: Implement drawTimeBoard for variable sizes

        this.drawTimeBoardBorder(13, 0, out);
        this.drawTimeBoardSpaces(13, 0, 0, false, out);
        this.drawTimeBoardBorder(13, 0, out);
        this.drawTimeBoardSpaces(1, 13, 12, false, out);
        this.drawTimeBoardBorder(13, 0, out);
        this.drawTimeBoardSpaces(13, 14, 0, true, out);
        this.drawTimeBoardBorder(13, 0, out);
        this.drawTimeBoardSpaces(1, 27, 0, false, out);
        this.drawTimeBoardBorder(13, 0, out);
        this.drawTimeBoardSpaces(13, 28, 0, false, out);
        this.drawTimeBoardBorder(13, 0, out);
        this.drawTimeBoardSpaces(1, 41, 12, false, out);
        this.drawTimeBoardBorder(10, 3, out);
        this.drawTimeBoardSpaces(10, 42, 3, true, out);
        this.drawTimeBoardBorder(10, 3, out);
    }

    public void drawPatches(List<Patch> patches, int selected, PrintStream out) {
        out.println("               Available patches:");
        out.println();

        for(int i = 0; i < 3; i++) {
            out.print("              ");
            for(int p = 0; p < patches.size(); p++) {
                out.print(p == selected ? ConsoleColor.GREEN : ConsoleColor.WHITE);

                this.drawPatchLine(patches.get(p), i, out);

                out.print("  ");
            }
            out.println(ConsoleColor.WHITE);
        }

        out.print("Button cost:");
        for(Patch patch : patches) {
            out.format("%5s", patch.buttonCost);
            out.print("  ");
        }
        out.println();

        out.print("Time cost:  ");
        for(Patch patch : patches) {
            out.format("%5s", patch.timeTokenCost);
            out.print("  ");
        }
        out.println();

        out.print("Income:     ");
        for(Patch patch : patches) {
            out.format("%5s", patch.buttonScore);
            out.print("  ");
        }
        out.println();
    }

    private void drawQuiltBoardSpace(boolean hasPatch, boolean placingPatch, PrintStream out) {
        if(hasPatch) {
            out.print(placingPatch ? ConsoleColor.RED : ConsoleColor.BLUE);
        } else {
            out.print(placingPatch ? ConsoleColor.GREEN : ConsoleColor.WHITE);
        }
        out.print(SPACE_CHAR + " ");
    }

    private void drawTimeBoardBorder(int numSpaces, int spacesOffset, PrintStream out) {
        for(int i = 0; i < spacesOffset; i++) {
            out.print("      ");
        }

        for(int i = 0; i < numSpaces; i++) {
            out.print("+-----");
        }
        out.println("+");
    }

    private void drawTimeBoardSpaces(int numSpaces, int startSpace, int spacesOffset, boolean reverse, PrintStream out) {
        for(int j = 0; j < 2; j++) {
            for (int i = 0; i < spacesOffset; i++) {
                out.print("      ");
            }

            for (int i = 0; i < numSpaces; i++) {
                int spaceIndex = reverse ? (numSpaces + startSpace - i - 1) : (startSpace + i);

                this.drawTimeBoardSpace(spaceIndex, j, out);
            }
            out.println("|");
        }
    }

    private void drawTimeBoardSpace(int spaceIndex, int row, PrintStream out) {
        List<SpaceElement> space = game.timeBoard.spaces.get(spaceIndex);

        out.print("|");

        if((space.size() == 1 && row == 1)) {
            out.format("%5s", this.getSpaceElementName(space.get(0)));
        } else if(space.size() == 2) {
            out.format("%5s", this.getSpaceElementName(space.get(row)));
        } else {
            out.print("     ");
        }
    }

    private void drawPatchLine(Patch patch, int row, PrintStream out) {
        StringBuilder patchRow = new StringBuilder();

        if(patch.spaces.size() > row) {
            for(boolean space : patch.spaces.get(row)){
                patchRow.append(space ? "X" : " ");
            }
        }

        out.format("%5s", patchRow);
    }

    private String getSpaceElementName(SpaceElement spaceElement) {
        String name = spaceElement.player == null ? spaceElement.type.name() : spaceElement.player.name;

        if(name.length() > 5) {
            return name.substring(0, 4) + name.substring(name.length() - 1);
        } else {
            return name;
        }
    }
}
