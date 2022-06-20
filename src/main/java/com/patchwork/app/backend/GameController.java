package com.patchwork.app.backend;

import com.patchwork.app.MoveResult;
import com.patchwork.app.backend.Exceptions.GameException;
import com.patchwork.app.backend.GameStates.*;
import com.patchwork.app.backend.Inputs.GameInput;
import com.patchwork.app.frontend.TUI;

import java.util.ArrayList;
import java.util.List;

public class GameController implements GameInputObserver, Runnable {

    private int tickSpeed = 50;

    private final TUI textUI;
    public Game game;
    public GameInput gameInput;

    private boolean isExited = false;

    public GameState gameState;
    private Move gameInputMove = null;
    private int gameCycleCounter = 0;
    private int specialPatchesCounter = 0;
    private final PatchFactory patchFactory;
    private List<String> messages;

    public GameController(Game game, TUI textUI, GameInput gameInput) {
        this.game = game;
        this.textUI = textUI;
        this.gameInput = gameInput;
        this.gameInput.subscribe(this);
        this.gameState = null;
        this.patchFactory = new PatchFactory();
        this.messages = List.of("Press 'h' for help.");
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
        if (isFinished()) {
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
        try {
            mainloop();
        } catch (GameException e) {
            e.printStackTrace();
        }
        gameInput.unsubscribe(this);
    }

    /**
     * Causes the game loop to exit on the next cycle.
     */
    public void stop() {
        isExited = true;
    }

    public boolean isFinished() {
        return game.isFinished() || isExited;
    }

    private void mainloop() throws GameException {
        gameState = new PickMove(currentPlayer(), game.patchList.getAvailablePatches());

        while (!isFinished()) {
            redrawGame();
            Move move = getMove();

            if (!isFinished()) {
                handleMove(move);
            }

            incrementGameCycleCounter();
        }

        finalizeGame();
        redrawGame();
        incrementGameCycleCounter();
    }

    private void redrawGame() {
        redrawGameState();

        if (!isFinished() && messages.size() != 0) {
            textUI.drawMessage("");  // Add single line of spacing
            for (String s : messages) {
                textUI.drawMessage(s);
            }
            messages = new ArrayList<>();
        }
    }

    private void redrawGameState() {
        switch (gameState.type) {
            case PICK_MOVE -> textUI.drawPickMoveState((PickMove) gameState);
            case PICK_PATCH -> textUI.drawPickPatchState((PickPatch) gameState);
            case PLACE_PATCH -> textUI.drawPlacePatchState((PlacePatch) gameState);
            case FINISHED -> textUI.drawFinishedState((Finished) gameState);
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
        if (move.equals(Move.HELP)) {
            drawMessage(gameInput.getHelpText());
            return;
        }

        switch (gameState.type) {
            case PICK_MOVE -> handlePickMove((PickMove) gameState, move);
            case PICK_PATCH -> handlePickPatch((PickPatch) gameState, move);
            case PLACE_PATCH -> handlePlacePatch((PlacePatch) gameState, move);
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
        } else if (move.isMirror()) {
            handlePlacePatchMirrorPatch(gameState, move);
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
            case MOVE_LEFT -> x--;
            case MOVE_RIGHT -> x++;
            case MOVE_UP -> y--;
            case MOVE_DOWN -> y++;
        }

        x = QuiltBoard.fixPatchX(x, gameState.patch);
        y = QuiltBoard.fixPatchY(y, gameState.patch);

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

        int x = QuiltBoard.fixPatchX(gameState.x, patch);
        int y = QuiltBoard.fixPatchY(gameState.y, patch);

        this.gameState = new PlacePatch(gameState.player, patch, x, y);
    }

    private void handlePlacePatchMirrorPatch(PlacePatch gameState, Move move) {
        Patch patch = gameState.patch;

        switch (move) {
            case MIRROR_HORIZONTAL:
                patch = patch.mirrorUp();
                break;
            case MIRROR_VERTICAL:
                patch = patch.mirrorSide();
                break;
        }

        this.gameState = new PlacePatch(gameState.player, patch, gameState.x, gameState.y);
    }

    // Handler for the result of a move
    private void finalizeMove(Player player, MoveResult moveResult) {
        if (moveResult != null) {
            specialPatchesCounter += moveResult.getNrSpecialPatches();
            drawMoveResult(player, moveResult);

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
            while (gameInputMove == null && !isFinished()) {
                Thread.sleep(tickSpeed);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to obtain next move");
        }

        Move returnMove = gameInputMove;
        gameInputMove = null;

        return returnMove;
    }

    private void drawMessage(String message) {
        messages.add(message);
    }

    private void drawInvalidInput() {
        textUI.drawMessage("Invalid input!");
    }

    private void drawMoveResult(Player player, MoveResult moveResult) {
        if (moveResult.getNrButtons() == 0 && moveResult.getNrSpecialPatches() == 0) {
            return;
        }

        drawMessage(
                String.format(
                        "%s obtained %d buttons and %d special patches",
                        player.name,
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
