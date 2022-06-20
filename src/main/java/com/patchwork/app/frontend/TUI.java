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
    public IWritable writable;

    public TUI(Game game, IWritable writable) {
        this.game = game;
        this.writable = writable;
    }

    private void write(String text) {
        this.writable.write(text);
    }

    private void write(ConsoleColor color) {
        write(color.toString());
    }

    private void writeln(String text) {
        this.writable.writeln(text);
    }
    
    private void writeln() {
        this.writable.writeln("");
    }

    private void writeln(ConsoleColor color) {
        writeln(color.toString());
    }

    private void clear() {
        this.writable.clear();
    }

    public void drawMessage(String message) {
        writeln(message);
    }

    public void drawPlacePatchState(PlacePatch gameState) {
        clear();
        this.drawQuiltBoardWithPatch(gameState.player.quiltBoard,
                gameState.patch,
                gameState.x,
                gameState.y);
    }

    public void drawPickPatchState(PickPatch gameState) {
        clear();
        this.drawQuiltBoard(gameState.player.quiltBoard);
        this.drawPatches(gameState.options, gameState.options.indexOf(gameState.selectedPatch));
    }

    public void drawPickMoveState(PickMove gameState) {
        clear();
        this.drawTimeBoard();
        this.drawQuiltBoard(gameState.player.quiltBoard);
        this.drawPatches(gameState.patchOptions, -1);
    }

    public void drawQuiltBoard(QuiltBoard quiltBoard) {
        for(int i=0; i < quiltBoard.spaces.length;i++){
            for(int j=0; j < quiltBoard.spaces[i].length;j++){
                this.drawQuiltBoardSpace(quiltBoard.spaces[i][j],false);
            }
            writeln();
        }
    }

    // patchX and patchY are the coordinates from the top-left of the board
    public void drawQuiltBoardWithPatch(QuiltBoard quiltBoard, Patch patch, int patchX, int patchY) {
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
                this.drawQuiltBoardSpace(boardSpace, patchSpace);
            }
            writeln();
        }
    }

    public void drawTimeBoard() {
        if(this.game.timeBoard.spaces.size() != 52) return; // TO DO: Implement drawTimeBoard for variable sizes

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
        writeln("               Available patches:");
        writeln();

        for(int i = 0; i < 3; i++) {
            write("              ");
            for(int p = 0; p < patches.size(); p++) {
                write(p == selected ? ConsoleColor.GREEN : ConsoleColor.WHITE);

                this.drawPatchLine(patches.get(p), i);

                write("  ");
            }
            writeln(ConsoleColor.WHITE);
        }

        write("Button cost:");
        for(Patch patch : patches) {
            write(String.format("%5s", patch.buttonCost));
            write("  ");
        }
        writeln();

        write("Time cost:  ");
        for(Patch patch : patches) {
            write(String.format("%5s", patch.timeTokenCost));
            write("  ");
        }
        writeln();

        write("Income:     ");
        for(Patch patch : patches) {
            write(String.format("%5s", patch.buttonScore));
            write("  ");
        }
        writeln();
    }

    private void drawQuiltBoardSpace(boolean hasPatch, boolean placingPatch) {
        if(hasPatch) {
            write(placingPatch ? ConsoleColor.RED : ConsoleColor.BLUE);
        } else {
            write(placingPatch ? ConsoleColor.GREEN : ConsoleColor.WHITE);
        }
        write(SPACE_CHAR + " ");
    }

    private void drawTimeBoardBorder(int numSpaces, int spacesOffset) {
        for(int i = 0; i < spacesOffset; i++) {
            write("      ");
        }

        for(int i = 0; i < numSpaces; i++) {
            write("+-----");
        }
        writeln("+");
    }

    private void drawTimeBoardSpaces(int numSpaces, int startSpace, int spacesOffset, boolean reverse) {
        for(int j = 0; j < 2; j++) {
            for (int i = 0; i < spacesOffset; i++) {
                write("      ");
            }

            for (int i = 0; i < numSpaces; i++) {
                int spaceIndex = reverse ? (numSpaces + startSpace - i - 1) : (startSpace + i);

                this.drawTimeBoardSpace(spaceIndex, j);
            }
            writeln("|");
        }
    }

    private void drawTimeBoardSpace(int spaceIndex, int row) {
        List<SpaceElement> space = game.timeBoard.spaces.get(spaceIndex);

        write("|");

        if((space.size() == 1 && row == 1)) {
            write(String.format("%5s", this.getSpaceElementName(space.get(0))));
        } else if(space.size() == 2) {
            write(String.format("%5s", this.getSpaceElementName(space.get(row))));
        } else {
            write("     ");
        }
    }

    private void drawPatchLine(Patch patch, int row) {
        StringBuilder patchRow = new StringBuilder();

        if(patch.spaces.size() > row) {
            for(boolean space : patch.spaces.get(row)){
                patchRow.append(space ? "X" : " ");
            }
        }

        write(String.format("%5s", patchRow));
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
