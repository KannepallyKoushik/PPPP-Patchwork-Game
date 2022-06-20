package com.patchwork.app.frontend;

import com.patchwork.app.backend.controller.GameStates.*;
import com.patchwork.app.backend.model.*;

import java.util.List;

public class TUI {
    public static final char QUILTBOARD_EMPTY_SPACE_CHAR = '·';
    public static final char QUILTBOARD_PATCH_SPACE_CHAR = 'O';
    public static final char QUILTBOARD_HIGHLIGHTED_PATCH_SPACE_CHAR = '▪';
    public static final char QUILTBOARD_CONFLICT_SPACE_CHAR = 'X';


    public Game game;
    public IWritable writable;

    public TUI(Game game, IWritable writable) {
        this.game = game;
        this.writable = writable;
    }

    private void write(String text) {
        this.writable.write(text);
    }

    private void write(char c) {
        write(String.valueOf(c));
    }

    private void writeln(String text) {
        this.writable.writeln(text);
    }

    private void writeln() {
        this.writable.writeln("");
    }

    private void clear() {
        this.writable.clear();
    }

    public void drawMessage(String message) {
        writeln(message);
    }

    public void drawPlacePatchState(PlacePatch gameState) {
        clear();
        this.drawTimeBoard();
        writeln();
        this.drawCurrentPlayer(gameState.player);
        writeln();
        this.drawQuiltBoardWithPatch(gameState.player.quiltBoard, gameState.patch, gameState.x, gameState.y);
        writeln();
        this.drawInstructions(gameState);
    }

    public void drawPickPatchState(PickPatch gameState) {
        clear();
        this.drawTimeBoard();
        writeln();
        this.drawCurrentPlayer(gameState.player);
        writeln();
        this.drawQuiltBoard(gameState.player.quiltBoard);
        writeln();
        this.drawPatches(gameState.options, gameState.options.indexOf(gameState.selectedPatch));
        writeln();
        this.drawInstructions(gameState);
    }

    public void drawPickMoveState(PickMove gameState) {
        clear();
        this.drawTimeBoard();
        writeln();
        this.drawCurrentPlayer(gameState.player);
        writeln();
        this.drawQuiltBoard(gameState.player.quiltBoard);
        writeln();
        this.drawPatches(gameState.patchOptions);
        writeln();
        this.drawSelectedMove(gameState.selectedOption);
        writeln();
        this.drawInstructions(gameState);
    }

    public void drawFinishedState(Finished gameState) {
        clear();

        if (gameState.gameResult == null) {
            write("\n".repeat(15));
            writeln("Game was exited early.");
        } else {
            drawGameResult(gameState);
        }
    }

    public void drawGameResult(Finished gameState) {
        write("\n".repeat(10));
        writeln(String.format("Player %s wins!", gameState.gameResult.winner.name));

        writeln();
        drawPlayerScores(gameState.gameResult);

        writeln();
        drawPlayerQuiltBoards();

        writeln(gameState.getInstructionsString());
    }

    public void drawPlayerScores(GameResult gameResult) {
        writeln("Final scores:");
        for (Player p : game.players) {
            writeln(String.format("  %-13s %d buttons", p.name, gameResult.playerScores.get(p)));
        }
    }

    public void drawPlayerQuiltBoards() {
        for (Player p : game.players) {
            writeln(String.format("Quiltboard of %s", p.name));
            drawQuiltBoard(p.quiltBoard);
            writeln();
        }
    }

    public void drawCurrentPlayer(Player player) {
        writeln(String.format("Current player: %s", player.name));
        writeln(String.format("Balance: %d buttons", player.nrButtons));
    }

    public void drawInstructions(GameState gameState) {
        writeln(gameState.getInstructionsString());
    }

    public void drawSelectedMove(PickMove.MoveOption option) {
        String line = "You are currently selecting: ";
        String underline = " ".repeat(29);

        if (option == PickMove.MoveOption.PLACE_PATCH) {
            line += "Buy a patch";
            underline += "-".repeat(11);
        } else {
            line += "Move past next player";
            underline += "-".repeat(21);
        }


        writeln(line + "\n" + underline);
    }

    public void drawQuiltBoard(QuiltBoard quiltBoard) {
        for (int i = 0; i < quiltBoard.spaces.length; i++) {
            for (int j = 0; j < quiltBoard.spaces[i].length; j++) {
                this.drawQuiltBoardSpace(quiltBoard.spaces[i][j], false);
            }
            writeln();
        }
    }

    // patchX and patchY are the coordinates from the top-left of the board
    public void drawQuiltBoardWithPatch(QuiltBoard quiltBoard, Patch patch, int patchX, int patchY) {
        for (int r = 0; r < quiltBoard.spaces.length; r++) {
            for (int c = 0; c < quiltBoard.spaces[r].length; c++) {
                boolean boardSpace = quiltBoard.spaces[r][c];
                boolean patchSpace = false;

                // Check if current space is within bounds of the patch
                if (r - patchY >= 0 &&
                        r - patchY < patch.spaces.size() &&
                        c - patchX >= 0 &&
                        c - patchX < patch.spaces.get(r - patchY).size()) {
                    // Check the state of this space of the patch
                    patchSpace = patch.spaces.get(r - patchY).get(c - patchX);
                }
                this.drawQuiltBoardSpace(boardSpace, patchSpace);
            }
            writeln();
        }
    }

    public void drawTimeBoard() {
        if (this.game.timeBoard.spaces.size() != 52) return; // TO DO: Implement drawTimeBoard for variable sizes

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

    public void drawPatches(List<Patch> patches) {
        writeln("               Available patches:");
        writeln();

        for (int i = 0; i < 3; i++) {
            write("              ");
            for (int p = 0; p < patches.size(); p++) {
                this.drawPatchLine(patches.get(p), i);

                write("  ");
            }
            writeln();
        }

        write("Button cost:");
        for (Patch patch : patches) {
            write(String.format("%5s", patch.buttonCost));
            write("  ");
        }
        writeln();

        write("Time cost:  ");
        for (Patch patch : patches) {
            write(String.format("%5s", patch.timeTokenCost));
            write("  ");
        }
        writeln();

        write("Income:     ");
        for (Patch patch : patches) {
            write(String.format("%5s", patch.buttonScore));
            write("  ");
        }
        writeln();
    }

    public void drawPatches(List<Patch> patches, int selected) {
        drawPatches(patches);

        // Draw arrow highlighting the selected patch
        write(" ".repeat(16 + (7 * selected)));
        writeln("▲");
    }

    private void drawQuiltBoardSpace(boolean hasPatch, boolean placingPatch) {
        if(hasPatch) {
            if(placingPatch) {
                write(QUILTBOARD_CONFLICT_SPACE_CHAR);
            } else {
                write(QUILTBOARD_PATCH_SPACE_CHAR);
            }
        } else {
            if(placingPatch) {
                write(QUILTBOARD_HIGHLIGHTED_PATCH_SPACE_CHAR);
            } else {
                write(QUILTBOARD_EMPTY_SPACE_CHAR);
            }
        }

        write(" ");
    }

    private void drawTimeBoardBorder(int numSpaces, int spacesOffset) {
        for (int i = 0; i < spacesOffset; i++) {
            write("      ");
        }

        for (int i = 0; i < numSpaces; i++) {
            write("+-----");
        }
        writeln("+");
    }

    private void drawTimeBoardSpaces(int numSpaces, int startSpace, int spacesOffset, boolean reverse) {
        for (int j = 0; j < 2; j++) {
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
        List<TimeBoard.SpaceElement> space = game.timeBoard.spaces.get(spaceIndex);

        write("|");

        if ((space.size() == 1 && row == 1)) {
            write(String.format("%5s", this.getSpaceElementName(space.get(0))));
        } else if (space.size() == 2) {
            write(String.format("%5s", this.getSpaceElementName(space.get(row))));
        } else {
            write("     ");
        }
    }

    private void drawPatchLine(Patch patch, int row) {
        StringBuilder patchRow = new StringBuilder();

        if (patch.spaces.size() > row) {
            for (boolean space : patch.spaces.get(row)) {
                patchRow.append(space ? QUILTBOARD_HIGHLIGHTED_PATCH_SPACE_CHAR : " ");
            }
        }

        write(String.format("%5s", patchRow));
    }

    private String getSpaceElementName(TimeBoard.SpaceElement spaceElement) {
        String name = spaceElement.player == null ? spaceElement.type.name() : spaceElement.player.name;

        if (name.length() > 5) {
            return name.substring(0, 4) + name.substring(name.length() - 1);
        } else {
            return name;
        }
    }
}
