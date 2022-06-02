package com.patchwork.app.frontend;

import com.patchwork.app.backend.Game;
import com.patchwork.app.backend.GameStates.PickMove;
import com.patchwork.app.backend.GameStates.PickPatch;
import com.patchwork.app.backend.GameStates.PlacePatch;
import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.QuiltBoard;
import com.patchwork.app.backend.TimeBoard.SpaceElement;
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

    public void drawPlacePatchState(PlacePatch gameState) {
        this.drawQuiltBoardWithPatch(gameState.player.quiltBoard,
                gameState.patch,
                gameState.x,
                gameState.y);
    }

    public void drawPickPatchState(PickPatch gameState) {
        this.drawQuiltBoard(gameState.player.quiltBoard);
        this.drawPatches(gameState.options, gameState.selectedPatch);
    }

    public void drawPickMoveState(PickMove gameState) {
        this.drawTimeBoard();
        this.drawQuiltBoard(gameState.player.quiltBoard);
        this.drawPatches(gameState.options, -1);
    }

    public void drawQuiltBoard(QuiltBoard quiltBoard) {
        for(int i=0; i < quiltBoard.spaces.length;i++){
            for(int j=0; j < quiltBoard.spaces[i].length;j++){
                this.drawQuiltBoardSpace(quiltBoard.spaces[i][j],false);
            }
            System.out.println();
        }
    }

    // patchX and patchY are the coordinates from the top-left of the board
    public void drawQuiltBoardWithPatch(QuiltBoard quiltBoard, Patch patch, int patchX, int patchY) {
        for(int r = 0; r < quiltBoard.spaces.length; r++) {
            for(int c = 0; c < quiltBoard.spaces[r].length ; c++){
                Boolean boardSpace = quiltBoard.spaces[r][c];
                Boolean patchSpace = false;

                // Check if current space is within bounds of the patch
                if(r-patchY >= 0 &&
                        r-patchY < patch.spaces.size() &&
                        c-patchX >= 0 &&
                        c-patchX < patch.spaces.get(r-patchY).size()) {
                    // Check the state of this space of the patch
                    patchSpace = patch.spaces.get(r-patchY).get(c-patchX);
                }
                this.drawQuiltBoardSpace(boardSpace, patchSpace);
            }
            System.out.println();
        }
    }

    public void drawTimeBoard() {
        this.drawTimeBoardBorder(13, 0);
        this.drawTimeBoardSpaces(13, 0, 0, false);
        this.drawTimeBoardBorder(13, 0);
        this.drawTimeBoardSpaces(1, 13, 12, false);
        this.drawTimeBoardBorder(13, 0);
        this.drawTimeBoardSpaces(13, 14, 0, true);
        this.drawTimeBoardBorder(13, 0);
        this.drawTimeBoardSpaces(1, 27, 0, false);
        this.drawTimeBoardBorder(13, 0);
        this.drawTimeBoardSpaces(13, 28, 0, false);
        this.drawTimeBoardBorder(13, 0);
        this.drawTimeBoardSpaces(1, 41, 12, false);
        this.drawTimeBoardBorder(10, 3);
        this.drawTimeBoardSpaces(10, 42, 3, true);
        this.drawTimeBoardBorder(10, 3);
    }

    public void drawPatches(List<Patch> patches, int selected) {
        System.out.println("               Available patches:");
        System.out.println();

        for(int i = 0; i < 3; i++) {
            System.out.print("              ");
            for(Patch patch : patches) {
                System.out.print(i == selected ? ConsoleColor.GREEN : ConsoleColor.WHITE);

                this.drawPatchLine(patch, i);

                System.out.print("  ");
            }
            System.out.println();
        }

        System.out.print("Button cost:");
        for(Patch patch : patches) {
            System.out.format("%5s", patch.buttonCost);
            System.out.print("  ");
        }
        System.out.println();

        System.out.print("Time cost:  ");
        for(Patch patch : patches) {
            System.out.format("%5s", patch.timeTokenCost);
            System.out.print("  ");
        }
        System.out.println();

        System.out.print("Income:     ");
        for(Patch patch : patches) {
            System.out.format("%5s", patch.buttonScore);
            System.out.print("  ");
        }
        System.out.println();
    }

    private void drawQuiltBoardSpace(boolean hasPatch, boolean placingPatch) {
        if(hasPatch) {
            System.out.print(placingPatch ? ConsoleColor.RED : ConsoleColor.BLUE);
        } else {
            System.out.print(placingPatch ? ConsoleColor.GREEN : ConsoleColor.WHITE);
        }
        System.out.print(SPACE_CHAR + " ");
    }

    private void drawTimeBoardBorder(int numSpaces, int spacesOffset) {
        for(int i = 0; i < spacesOffset; i++) {
            System.out.print("      ");
        }

        for(int i = 0; i < numSpaces; i++) {
            System.out.print("+-----");
        }
        System.out.println("+");
    }

    private void drawTimeBoardSpaces(int numSpaces, int startSpace, int spacesOffset, boolean reverse) {
        for(int j = 0; j < 2; j++) {
            for (int i = 0; i < spacesOffset; i++) {
                System.out.print("      ");
            }

            for (int i = 0; i < numSpaces; i++) {
                int spaceIndex = reverse ? (numSpaces + startSpace - i - 1) : (startSpace + i);

                this.drawTimeBoardSpace(spaceIndex, j);
            }
            System.out.println("|");
        }
    }

    private void drawTimeBoardSpace(int spaceIndex, int row) {
        List<SpaceElement> space = game.timeBoard.spaces.get(spaceIndex);

        System.out.print("|");

        if((space.size() == 1 && row == 1)) {
            System.out.format("%5s", this.getSpaceElementName(space.get(0)));
        } else if(space.size() == 2) {
            System.out.format("%5s", this.getSpaceElementName(space.get(row)));
        } else {
            System.out.print("     ");
        }
    }

    private void drawPatchLine(Patch patch, int row) {
        StringBuilder patchRow = new StringBuilder();

        if(patch.spaces.size() > row) {
            for(boolean space : patch.spaces.get(row)){
                patchRow.append(space ? "X" : " ");
            }
        }

        System.out.format("%5s", patchRow);
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
