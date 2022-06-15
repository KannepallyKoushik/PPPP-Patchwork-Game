package com.patchwork.app.backend;

import com.patchwork.app.MoveResult;
import com.patchwork.app.backend.Exceptions.GameException;
import com.patchwork.app.backend.GameStates.*;
import com.patchwork.app.backend.Inputs.GameInput;
import com.patchwork.app.frontend.TUI;

import java.util.List;

public class GameController implements GameInputObserver, Runnable {

    private int tickSpeed = 50;

    private final TUI textUI;
    public Game game;
    public GameInput gameInput;

    public boolean isFinished = false;

    public GameState gameState;
    private Move gameInputMove = null;
    private int gameCycleCounter = 0;
    private int specialPatchesCounter = 0;
    private final PatchFactory patchFactory;

    private final Thread gameInputThread;

    public GameController(Game game, TUI textUI, GameInput gameInput) {
        this.game = game;
        this.textUI = textUI;
        this.gameInput = gameInput;
        this.gameInput.subscribe(this);
        this.gameInputThread = new Thread(gameInput);
        this.gameState = null;
        this.patchFactory = new PatchFactory();
    }

    private Player currentPlayer() {
        return game.timeBoard.getCurrentPlayer();
    }

    private void incrementGameCycleCounter() {
        gameCycleCounter++;
    }

    public void setTickSpeed(int tickSpeed) {
        this.tickSpeed = tickSpeed;
    }

    public int getGameCycle() {
        return gameCycleCounter;
    }

    public void waitUntilGameCycle(int cycle) {
        if (isFinished) {
            return;
        }

        try {
            while (gameCycleCounter != cycle) {
                Thread.sleep(tickSpeed / 2);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to wait for next game cycle");
        }
    }

    @Override
    public void run() {
        gameInputThread.start();
        try {
            mainloop();
        } catch (GameException e) {
            e.printStackTrace();
        } finally {
            gameInputThread.stop();
        }
    }

    /**
     * Causes the game loop to exit on the next cycle.
     */
    public void stop() {
        isFinished = true;
    }

    private void mainloop() throws GameException {
        gameState = new PickMove(currentPlayer(), game.patchList.getAvailablePatches());

        while (!isFinished) {
            gameState.draw(textUI);
            gameState.drawInstructions(textUI);

            Move move = getMove();
            handleMove(move);
            isFinished = isFinished || game.isFinished();

            if (isFinished) {
                finalizeGame();
            }

            incrementGameCycleCounter();
        }
    }

    private void finalizeGame() {
        if (game.isFinished()) {
            gameState = new Finished(game.result);
        } else {
            gameState = new Finished(null);
        }
    }

    private void handleMove(Move move) throws GameException {
        switch (gameState.type) {
            case PICK_MOVE:
                handlePickMove((PickMove) gameState, move);
                break;
            case PICK_PATCH:
                handlePickPatch((PickPatch) gameState, move);
                break;
            case PLACE_PATCH:
                handlePlacePatch((PlacePatch) gameState, move);
                break;
        }
    }

    // Pick move handlers
    private void handlePickMove(PickMove gameState, Move move) throws GameException {
        if (move.isLeftRight()) {
            PickMove.MoveOption selectedOption = handleSelectMove(move, gameState.selectedOption, gameState.moveOptions);
            this.gameState = new PickMove(gameState.player, selectedOption, gameState.patchOptions);
        } else if (move == Move.CONFIRM) {
            handlePickMoveConfirm(gameState);
        } else {
            drawInvalidInput();
        }
    }

    private void handlePickMoveConfirm(PickMove gameState) throws GameException {
        if (gameState.selectedOption == PickMove.MoveOption.PLACE_PATCH) {
            List<Patch> availablePatches = game.patchList.getAvailablePatches();
            this.gameState = new PickPatch(gameState.player, availablePatches, availablePatches.get(0));
        } else {
            MoveResult moveResult = game.movePastNextPlayer(currentPlayer());
            finalizeMove(gameState.player, moveResult);
        }
    }

    // Pick patch handlers
    private void handlePickPatch(PickPatch gameState, Move move) {
        if (move.isLeftRight()) {
            Patch selectedOption = handleSelectMove(move, gameState.selectedPatch, gameState.options);
            this.gameState = new PickPatch(gameState.player, gameState.options, selectedOption);
        } else if (move == Move.CONFIRM) {
            handlePickPatchConfirm(gameState);
        } else {
            drawInvalidInput();
        }
    }

    private void handlePickPatchConfirm(PickPatch gameState) {
        if (gameState.selectedPatch.buttonCost > gameState.player.nrButtons) {
            drawMessage("Cannot afford patch!");
            finalizeMove(gameState.player, null);
        } else {
            this.gameState = new PlacePatch(gameState.player, gameState.selectedPatch, 0, 0);
        }
    }

    // Place patch handlers
    private void handlePlacePatch(PlacePatch gameState, Move move) throws GameException {
        if (move.isMove()) {
            handlePlacePatchMovePatch(gameState, move);
        } else if (move.isRotate()) {
            handlePlacePatchRotatePatch(gameState, move);
        } else if (move == Move.CONFIRM) {
            if (!gameState.player.quiltBoard.canPlace(gameState.patch, gameState.x, gameState.y)) {
                drawMessage("Cannot place patch here");
                return;
            }
            MoveResult moveResult = game.placePatch(gameState.player, gameState.patch, gameState.x, gameState.y);
            finalizeMove(gameState.player, moveResult);
        } else {
            drawInvalidInput();
        }
    }

    private void handlePlacePatchMovePatch(PlacePatch gameState, Move move) {
        int x = gameState.x;
        int y = gameState.y;

        switch (move) {
            case MOVE_LEFT:
                x = Math.max(0, x - 1);
                break;
            case MOVE_RIGHT:
                x = Math.min(QuiltBoard.DIM_X - 1, x + 1);
                break;
            case MOVE_UP:
                y = Math.max(0, y - 1);
                break;
            case MOVE_DOWN:
                y = Math.min(QuiltBoard.DIM_Y - 1, y + 1);
        }

        this.gameState = new PlacePatch(gameState.player, gameState.patch, x, y);
    }

    private void handlePlacePatchRotatePatch(PlacePatch gameState, Move move) {
        Patch patch = gameState.patch;

        switch (move) {
            case ROTATE_CLOCKWISE:
                patch = patch.rotateRight();
                break;
            case ROTATE_COUNTERCLOCKWISE:
                patch = patch.rotateLeft();
                break;
        }

        this.gameState = new PlacePatch(gameState.player, patch, gameState.x, gameState.y);
    }

    // Handler for the result of a move
    private void finalizeMove(Player player, MoveResult moveResult) {
        if (moveResult != null) {
            specialPatchesCounter += moveResult.getNrSpecialPatches();
            drawMoveResult(moveResult);

            if (specialPatchesCounter != 0) {
                gameState = new PlacePatch(player, patchFactory.createSpecialPatch(), 0, 0);
                specialPatchesCounter--;
                return;
            }
        }
        gameState = new PickMove(game.timeBoard.getCurrentPlayer(), game.patchList.getAvailablePatches());
    }

    // Utilities
    private static <T> T handleSelectMove(Move move, T selected, List<T> options) {
        if (!move.isLeftRight()) {
            throw new RuntimeException("Cannot handle move which is not either left or right");
        }

        int i = options.indexOf(selected);

        if (move == Move.MOVE_LEFT) {
            i = Math.max(0, i - 1);
        } else if (move == Move.MOVE_RIGHT) {
            i = Math.min(options.size() - 1, i + 1);
        }

        return options.get(i);
    }

    private Move getMove() {
        try {
            while (gameInputMove == null) {
                Thread.sleep(tickSpeed);
            }
        } catch (InterruptedException e) {
            if (isFinished) {
                return null;
            }
            throw new RuntimeException("Failed to obtain next move");
        }

        Move returnMove = gameInputMove;
        gameInputMove = null;

        return returnMove;
    }

    private void drawMessage(String message) {
        textUI.drawMessage(message);
    }

    private void drawInvalidInput() {
        textUI.drawMessage("Invalid input!");
    }

    private void drawMoveResult(MoveResult moveResult) {
        drawMessage(
                String.format(
                        "You obtained %d buttons and %d special patches",
                        moveResult.getNrButtons(),
                        moveResult.getNrSpecialPatches()
                )
        );
    }

    public void update(Move move) {
        this.gameInputMove = move;
    }

    public GameState getState() {
        return gameState;
    }
}
