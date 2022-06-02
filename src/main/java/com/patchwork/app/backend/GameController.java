package com.patchwork.app.backend;

import com.patchwork.app.backend.Exceptions.GameException;
import com.patchwork.app.backend.GameStates.*;
import com.patchwork.app.backend.Inputs.GameInput;
import com.patchwork.app.frontend.TUI;

import java.util.List;

public class GameController implements GameInputObserver, Runnable {

    private int tickSpeed = 50;

    private TUI textUI;
    public Game game;
    public GameInput gameInput;

    private boolean turnFinished = false;
    public boolean isFinished = false;

    public GameState gameState;
    private Move gameInputMove = null;
    private int gameCycleCounter = 0;

    private Thread gameInputThread = null;

    public GameController(Game game, TUI textUI, GameInput gameInput) {
        this.game = game;
        this.textUI = textUI;
        this.gameInput = gameInput;
        this.gameInput.subscribe(this);
        this.gameInputThread = new Thread(gameInput);
        this.gameState = null;
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

    public void waitUntilNextGameCycle() {
        int ctr = gameCycleCounter;

        try {
            while (ctr == gameCycleCounter) {
                Thread.sleep(tickSpeed / 2);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to wait for next game cycle");
        }
    }

//    public void start() throws GameException, InterruptedException {
//
//        //Start game loop
//        while (!isFinished) {
//            Player currentPlayer = currentPlayer();
//
//            //Starting state is the current player, this player should know:
//            //TimeBoard, available patches and quiltBoard to determine their move
//            gameState = new PickMove(currentPlayer, currentPlayer.quiltBoard, game.patchList.getAvailablePatches(), game.timeBoard);
//
//            //Since TUI drawing based on state is not yet implemented:
//            textUI.drawPickMoveState((PickMove) gameState);
//
//            //Now wait for user move
//            System.out.println("Hello " + currentPlayer.name);
//            System.out.println("You have " + currentPlayer.nrButtons + " buttons");
//
//
//            //Determine what move the player wants
//            boolean moveConfirmed = false;
//
//            //Loop for choosing a move
//            while (!moveConfirmed) {
//                moveConfirmed = pickMove();
//            }
//            //Loop for picking & placing a patch
//            Patch selectedPatch = null;
//            boolean patchPlaced = false;
//            if (!turnFinished) {
//                //Picking patch
//                try {
//                    while (selectedPatch == null) {
//                        selectedPatch = pickPatch();
//                    }
//                    while (!patchPlaced) {
//                        //Now start logic for placing a patch
//                        try {
//                            patchPlaced = placePatch(selectedPatch);
//                        } catch (GameException e) {
//                            System.out.println(e.getMessage());
//                        }
//                    }
//                } catch (CanNotAffordException e) {
//                    System.out.println("You could not afford any patches, automatically moving you...");
//                    game.movePastNextPlayer(currentPlayer);
//                }
//            }
//            System.out.println("Switching player turn");
//            isFinished = game.isFinished();
//        }
//        gameState = new Finished();
//
//        //Game is finished, let tui draw result (when implemented)
//        /*
//        textUI.drawResult(game.result);
//        */
//    }

    /**
     * Causes the game loop to exit on the next cycle.
     */
    public void stop() {
        isFinished = true;
    }
//
//
//    public boolean pickMove() throws GameException, InterruptedException {
//        int selectedIndex = 0;
//        boolean movePicked = false;
//
//        while (!movePicked) {
//            System.out.print("You are currently selecting: ");
//            switch (selectedIndex) {
//                case 0:
//                    System.out.println("Move past next player");
//                    break;
//                case 1:
//                    System.out.println("Buy a patch");
//                    break;
//            }
//
//            System.out.println("Change your selection by typing LEFT or RIGHT, or confirm with CONFIRM");
//            while (move.equals(Move.WAITING)) {
//                Thread.sleep(50);
//                gameInput.run();
//            }
//            if (move.equals(Move.CONFIRM)) {
//                //Set to true to exit choosing move loop
//                movePicked = true;
//                //Set to waiting so next move starts fresh
//                move = Move.WAITING;
//            } else if (move.equals(Move.MOVE_LEFT)) {
//                //This might be redundant, since it is always 0 in this case (unless more options gets added later)
////                    selectedIndex = Math.max(0, selectedIndex - 1);
//                selectedIndex = 0;
//                move = Move.WAITING;
//            } else if (move.equals(Move.MOVE_RIGHT)) {
//                //Idem as above
////                    selectedIndex = Math.min(1, selectedIndex + 1);
//                selectedIndex = 1;
//                move = Move.WAITING;
//            } else {
//                //There was input, but not relevant input
//                System.out.println("Please use either LEFT or RIGHT or CONFIRM");
//                move = Move.WAITING;
//            }
//        }
//
//
//        if (selectedIndex == 0) {
//            //User selected move past next player
//            game.movePastNextPlayer(currentPlayer());
//            turnFinished = true;
//            return true;
//        } else if (selectedIndex == 1) {
//            //User selected buy
//            turnFinished = false;
//            return true;
//        } else {
//            throw new GameException("Selected index has to be 0 or 1");
//        }
//
//    }
//
//
//    public Patch pickPatch() throws CanNotAffordException, InterruptedException {
//
//        //First check if user can even buy anything
//        boolean canBuy = false;
//        for (int i = 0; i < game.patchList.getAvailablePatches().size(); i++) {
//            if (currentPlayer.nrButtons >= game.patchList.getAvailablePatches().get(i).buttonCost) {
//                canBuy = true;
//            }
//        }
//        if (canBuy) {
//            int patchIndex = 0;
//            Patch selectedPatch = null;
//
//            while (selectedPatch == null) {
//                Patch currentPatch;
//                //First draw some useful components for picking a patch
//                gameState = new PickPatch(currentPlayer, currentPlayer.quiltBoard, game.patchList.getAvailablePatches(), patchIndex);
//                textUI.drawPickPatchState((PickPatch) gameState);
//
//                System.out.println("Please choose your patch by typing LEFT or RIGHT or CONFIRM");
//                System.out.println("You are currently choosing the " + patchIndex + " patch.");
//
//                while (move.equals(Move.WAITING)) {
//                    Thread.sleep(50);
//                    gameInput.run();
//                }
//                if (move.equals(Move.CONFIRM)) {
//                    currentPatch = game.patchList.getAvailablePatches().get(patchIndex);
//
//                    //Check if player can buy the selected patch
//                    if (currentPatch.buttonCost > currentPlayer.nrButtons) {
//                        System.out.println("You can not afford this patch");
//                        move = Move.WAITING;
//                    } else {
//                        selectedPatch = currentPatch;
//                        move = Move.WAITING;
//                        return selectedPatch;
//                    }
//                } else if (move.equals(Move.MOVE_LEFT)) {
//                    patchIndex = Math.max(0, patchIndex - 1);
//                    move = Move.WAITING;
//                } else if (move.equals(Move.MOVE_RIGHT)) {
//                    patchIndex = Math.min(2, patchIndex + 1);
//                    move = Move.WAITING;
//                } else {
//                    //There was input, but not relevant input
//                    System.out.println("Please use either LEFT or RIGHT or CONFIRM");
//                    move = Move.WAITING;
//                }
//            }
//        } else {
//            throw new CanNotAffordException("You can not afford any patches");
//        }
//        return null;
//    }
//
//
//    public boolean placePatch(Patch selectedPatch) throws GameException, InterruptedException {
//        //Player has now selected a patch, now loop for placing the patch
//        boolean placed = false;
//        int x = 0;
//        int y = 0;
//
//        while (!placed) {
//            gameState = new PlacePatch(currentPlayer, currentPlayer.quiltBoard, selectedPatch, x, y);
//            textUI.drawPlacePatchState((PlacePatch) gameState);
//
//            System.out.println("Please place your patch, with either LEFT RIGHT UP DOWN or CONFIRM");
//            while (move.equals(Move.WAITING)) {
//                Thread.sleep(50);
//                gameInput.run();
//            }
//            System.out.println("MOVE: " + move);
//            if (move.equals(Move.CONFIRM)) {
//                //Set to waiting so next move starts fresh
//                move = Move.WAITING;
//
//                try {
//                    game.placePatch(currentPlayer, selectedPatch, x, y);
//                    //Set to true to exit choosing move loop
//                    placed = true;
//                } catch (GameException e) {
//                    throw new GameException(e.getMessage());
//                }
//            } else if (move.equals(Move.MOVE_LEFT)) {
//                x = Math.max(0, x - 1);
//                move = Move.WAITING;
//            } else if (move.equals(Move.MOVE_RIGHT)) {
//                x = Math.min(8, x + 1);
//                move = Move.WAITING;
//            } else if (move.equals(Move.MOVE_UP)) {
//                y = Math.max(0, y - 1);
//                move = Move.WAITING;
//            } else if (move.equals(Move.MOVE_DOWN)) {
//                y = Math.min(8, y + 1);
//                move = Move.WAITING;
//            } else {
//                System.out.println("Please enter a valid command");
//            }
//        }
//        System.out.println(placed);
//        return placed;
//    }

    private void mainloop() throws GameException {
        gameState = new PickMove(currentPlayer(), game.patchList.getAvailablePatches());

        while (!isFinished) {
            gameState.draw(textUI);
            gameState.drawInstructions(textUI);

            Move move = getMove();
            handleMove(move);
            incrementGameCycleCounter();
            isFinished = isFinished || game.isFinished();
        }

        finalizeGame();
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
            handlePickMoveConfirm(gameState, move);
        } else {
            drawInvalidInput();
        }
    }

    private void handlePickMoveConfirm(PickMove gameState, Move move) throws GameException {
        if (gameState.selectedOption == PickMove.MoveOption.PLACE_PATCH) {
            List<Patch> availablePatches = game.patchList.getAvailablePatches();
            this.gameState = new PickPatch(gameState.player, availablePatches, availablePatches.get(0));
        } else {
            game.movePastNextPlayer(currentPlayer());
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
            this.gameState = new PickMove(gameState.player, game.patchList.getAvailablePatches());
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
            game.placePatch(gameState.player, gameState.patch, gameState.x, gameState.y);
            this.gameState = new PickMove(gameState.player, game.patchList.getAvailablePatches());
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

    // Utilities
    private static <T> T handleSelectMove(Move move, T selected, List<T> options) {
        if(!move.isLeftRight()) {
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

    public void update(Move move) {
        this.gameInputMove = move;
    }

    public GameState getState() {
        return gameState;
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
}
