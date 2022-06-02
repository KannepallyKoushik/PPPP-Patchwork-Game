package com.patchwork.app.backend;

import com.patchwork.app.backend.Exceptions.CanNotAffordException;
import com.patchwork.app.backend.Exceptions.GameException;
import com.patchwork.app.backend.GameStates.*;
import com.patchwork.app.backend.Inputs.GameInput;
import com.patchwork.app.frontend.TUI;

import java.util.Random;

public class GameController implements GameInputObserver, Runnable {
    TUI textUI;
    public Game game;

    //Have to make this public for a test
    public Player currentPlayer;


    //realScanner is an actual System.in scanner, as opposed to a mock scanner used in the tests
    //Hopefully this will be gone when gameInput & states is working
    public GameInput gameInput;

    public GameState currentState;
    public boolean isFinished = false;
    private boolean turnFinished = false;
    public Move move = Move.WAITING;

    public GameController(Game game, TUI textUI, GameInput gameInput) {
        this.game = game;
        this.textUI = textUI;
        this.gameInput = gameInput;
        this.gameInput.subscribe(this);
        currentPlayer = game.players.get(new Random().nextInt(game.players.size()));
    }

    public void start() throws GameException, InterruptedException {

        //Start game loop
        while (!isFinished) {
            //Starting state is the current player, this player should know:
            //TimeBoard, available patches and quiltBoard to determine their move
            currentState = new PickMove(currentPlayer, currentPlayer.quiltBoard, game.patchList.getAvailablePatches(), game.timeBoard);

            //Since TUI drawing based on state is not yet implemented:
            textUI.drawPickMoveState((PickMove) currentState);

            //Now wait for user move
            System.out.println("Hello " + currentPlayer.name);
            System.out.println("You have " + currentPlayer.nrButtons + " buttons");


            //Determine what move the player wants

            boolean moveConfirmed = false;

            //Loop for choosing a move
            while (!moveConfirmed) {
                moveConfirmed = pickMove();
            }
            //Loop for picking & placing a patch
            Patch selectedPatch = null;
            boolean patchPlaced = false;
            if (!turnFinished) {
                //Picking patch
                try {
                    while (selectedPatch == null) {
                        selectedPatch = pickPatch();
                    }
                    while (!patchPlaced) {
                        //Now start logic for placing a patch
                        try {
                            patchPlaced = placePatch(selectedPatch);
                        } catch (GameException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                } catch (CanNotAffordException e) {
                    System.out.println("You could not afford any patches, automatically moving you...");
                    game.movePastNextPlayer(currentPlayer);
                }
            }
            System.out.println("Switching player turn");
            currentPlayer = game.timeBoard.getCurrentPlayer();
            isFinished = game.isFinished();
        }
        currentState = new Finished();

        //Game is finished, let tui draw result (when implemented)
        /*
        textUI.drawResult(game.result);
        */
    }

    /**
     * Causes the game loop to exit on the next cycle.
     */
    public void stop() {
        isFinished = true;
    }


    public boolean pickMove() throws GameException, InterruptedException {
        int selectedIndex = 0;
        boolean movePicked = false;

        while (!movePicked) {
            System.out.print("You are currently selecting: ");
            switch (selectedIndex) {
                case 0:
                    System.out.println("Move past next player");
                    break;
                case 1:
                    System.out.println("Buy a patch");
                    break;
            }

            System.out.println("Change your selection by typing LEFT or RIGHT, or confirm with CONFIRM");
            while (move.equals(Move.WAITING)) {
                Thread.sleep(50);
                gameInput.run();
            }
            if (move.equals(Move.CONFIRM)) {
                //Set to true to exit choosing move loop
                movePicked = true;
                //Set to waiting so next move starts fresh
                move = Move.WAITING;
            } else if (move.equals(Move.MOVE_LEFT)) {
                //This might be redundant, since it is always 0 in this case (unless more options gets added later)
//                    selectedIndex = Math.max(0, selectedIndex - 1);
                selectedIndex = 0;
                move = Move.WAITING;
            } else if (move.equals(Move.MOVE_RIGHT)) {
                //Idem as above
//                    selectedIndex = Math.min(1, selectedIndex + 1);
                selectedIndex = 1;
                move = Move.WAITING;
            } else {
                //There was input, but not relevant input
                System.out.println("Please use either LEFT or RIGHT or CONFIRM");
                move = Move.WAITING;
            }
        }


        if (selectedIndex == 0) {
            //User selected move past next player
            game.movePastNextPlayer(currentPlayer);
            turnFinished = true;
            return true;
        } else if (selectedIndex == 1) {
            //User selected buy
            turnFinished = false;
            return true;
        } else {
            throw new GameException("Selected index has to be 0 or 1");
        }

    }


    public Patch pickPatch() throws CanNotAffordException, InterruptedException {

        //First check if user can even buy anything
        boolean canBuy = false;
        for (int i = 0; i < game.patchList.getAvailablePatches().size(); i++) {
            if (currentPlayer.nrButtons >= game.patchList.getAvailablePatches().get(i).buttonCost) {
                canBuy = true;
            }
        }
        if (canBuy) {
            int patchIndex = 0;
            Patch selectedPatch = null;

            while (selectedPatch == null) {
                Patch currentPatch;
                //First draw some useful components for picking a patch
                currentState = new PickPatch(currentPlayer, currentPlayer.quiltBoard, game.patchList.getAvailablePatches(), patchIndex);
                textUI.drawPickPatchState((PickPatch) currentState);

                System.out.println("Please choose your patch by typing LEFT or RIGHT or CONFIRM");
                System.out.println("You are currently choosing the " + patchIndex + " patch.");

                while (move.equals(Move.WAITING)) {
                    Thread.sleep(50);
                    gameInput.run();
                }
                if (move.equals(Move.CONFIRM)) {
                    currentPatch = game.patchList.getAvailablePatches().get(patchIndex);

                    //Check if player can buy the selected patch
                    if (currentPatch.buttonCost > currentPlayer.nrButtons) {
                        System.out.println("You can not afford this patch");
                        move = Move.WAITING;
                    } else {
                        selectedPatch = currentPatch;
                        move = Move.WAITING;
                        return selectedPatch;
                    }
                } else if (move.equals(Move.MOVE_LEFT)) {
                    patchIndex = Math.max(0, patchIndex - 1);
                    move = Move.WAITING;
                } else if (move.equals(Move.MOVE_RIGHT)) {
                    patchIndex = Math.min(2, patchIndex + 1);
                    move = Move.WAITING;
                } else {
                    //There was input, but not relevant input
                    System.out.println("Please use either LEFT or RIGHT or CONFIRM");
                    move = Move.WAITING;
                }
            }
        } else {
            throw new CanNotAffordException("You can not afford any patches");
        }
        return null;
    }


    public boolean placePatch(Patch selectedPatch) throws GameException, InterruptedException {
        //Player has now selected a patch, now loop for placing the patch
        boolean placed = false;
        int x = 0;
        int y = 0;

        while (!placed) {
            currentState = new PlacePatch(currentPlayer, currentPlayer.quiltBoard, selectedPatch, x, y);
            textUI.drawPlacePatchState((PlacePatch) currentState);

            System.out.println("Please place your patch, with either LEFT RIGHT UP DOWN or CONFIRM");
            while (move.equals(Move.WAITING)) {
                Thread.sleep(50);
                gameInput.run();
            }
            System.out.println("MOVE: " + move);
            if (move.equals(Move.CONFIRM)) {
                //Set to waiting so next move starts fresh
                move = Move.WAITING;

                try {
                    game.placePatch(currentPlayer, selectedPatch, x, y);
                    //Set to true to exit choosing move loop
                    placed = true;
                } catch (GameException e) {
                    throw new GameException(e.getMessage());
                }
            } else if (move.equals(Move.MOVE_LEFT)) {
                x = Math.max(0, x - 1);
                move = Move.WAITING;
            } else if (move.equals(Move.MOVE_RIGHT)) {
                x = Math.min(8, x + 1);
                move = Move.WAITING;
            } else if (move.equals(Move.MOVE_UP)) {
                y = Math.max(0, y - 1);
                move = Move.WAITING;
            } else if (move.equals(Move.MOVE_DOWN)) {
                y = Math.min(8, y + 1);
                move = Move.WAITING;
            } else {
                System.out.println("Please enter a valid command");
            }
        }
        System.out.println(placed);
        return placed;
    }


    public void update(Move move) {
        this.move = move;
    }

    public GameState getState() {
        return currentState;
    }


    @Override
    public void run() {
        try {
            start();
        } catch (GameException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
