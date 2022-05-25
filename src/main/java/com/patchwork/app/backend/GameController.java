package com.patchwork.app.backend;

import com.patchwork.app.backend.Mock.Scanners;
import com.patchwork.app.backend.Mock.RealScanner;
import com.patchwork.app.frontend.TUI;

import java.util.Random;
import java.util.Scanner;

public class GameController {
    public TUI textUI;
    public Game game;

    //Have to make this public for a test
    public Player currentPlayer;


    //realScanner is an actual System.in scanner, as opposed to a mock scanner used in the tests
    //Hopefully this will be gone when gameInput & states is working
    public Scanners scanner;

    // TODO Currently not working due to missing implementation
    //    public GameInput input;
    //    public GameState currentState;

    public GameController(){
        this.game = new Game();
        this.textUI = new TUI(game);
        this.currentPlayer = game.players.get(new Random().nextInt(game.players.size()));
        this.scanner = new RealScanner(new Scanner(System.in));
    }

    public void run() throws GameException {
        textUI = new TUI(game);
        boolean isFinished = false;


        //Start game loop
        while (!isFinished) {


            //Let TUI draw necessary components before picking move
            textUI.drawTimeBoard();
            textUI.drawQuiltBoard(currentPlayer.quiltBoard);
            textUI.drawPatches();

            //Now wait for user move
            System.out.println("You have " + currentPlayer.nrButtons + " buttons");
            System.out.println("Please choose your action:");
            System.out.println("Choice between moving (MOVE) or buying a patch (BUY)");
            //Determine what move the player wants
            String next = pickMove(scanner);

            if (next.equals("MOVE")) {
                //Simply move the player according to game logic
                game.movePastNextPlayer(currentPlayer);
            } else if (next.equals("BUY")) {
                //First draw some useful components for picking a patch
                //Implement state drawing later when this is implemented in TUI
                //currentState = new PickPatch(currentPlayer, game.patchList.getAvailablePatches(), game.patchList.getAvailablePatches().get(0));
                textUI.drawQuiltBoard(currentPlayer.quiltBoard);
                textUI.drawPatches();
                System.out.println("Please choose your patch, with numbers 1-3");

                //Let the player pick the patch
                Patch selectedPatch = pickPatch(scanner);
                //Then options to place the patch
                placePatch(selectedPatch,scanner);
            } else {
                System.out.println("Please enter a valid command");
            }

            currentPlayer = getNextPlayer(currentPlayer);
            isFinished = game.isFinished();
        }

        //Game is finished, let tui draw result (when implemented)
        /*
        textUI.drawResult(game.result);
        */
    }


    //Helper function to determine next player
    public Player getNextPlayer(Player player) {
        int index = game.players.indexOf(player);
        if (index + 1 == game.players.size()) {
            return game.players.get(0);
        }
        return game.players.get(index + 1);
    }


    //Helper function to interact with console
    //Should later on be redundant once GameInput works
    private String scan(Scanners scanners) {
        return scanners.nextLine();
    }


    public String pickMove(Scanners scanner) {



        /*
        Normally translate this input to GameInput, like
        input = new GameInput(input);
        For now implement some hardcoded stuff to avoid GameInput class,
        this decision should eventually be done interacting with the GameInput class
        */

        //This scanner is because in the tests you want to mock the input data, because System.in does not work in JUNIT
        while (true) {
            String input = scan(scanner);
            if (input.equals("MOVE")) {
                return "MOVE";
            } else if (input.equals("BUY")) {
                boolean canBuy = false;
                for (int i = 0; i < game.patchList.getAvailablePatches().size(); i++){
                    if (currentPlayer.nrButtons > game.patchList.getAvailablePatches().get(i).buttonCost){
                        canBuy = true;
                    }
                }
                if (canBuy){
                    return "BUY";
                } else{
                    System.out.println("Can not afford any patches, automatically choose move");
                    return "MOVE";
                }
            } else {
                System.out.println("Please enter a valid command");
            }
        }
    }

    public Patch pickPatch(Scanners scanner) {


        //For now, implement with scanner as GameInput has not been implemented

        boolean selected = false;
        Patch selectedPatch = null;
        String input;

        //Same scanner issue as with pickMove();
        while (!selected) {
            input = scan(scanner);
            int choice;
            try {
                choice = Integer.parseInt(input);
                Patch chosenPatch = game.patchList.getAvailablePatches().get(choice - 1);
                if (chosenPatch.buttonCost > currentPlayer.nrButtons) {
                    System.out.println("Can not afford button cost of the selected patch, please pick again");
                } else {
                    selected = true;
                    selectedPatch = chosenPatch;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice, please enter a number 1-3");
            }
        }
        return selectedPatch;
    }

    public void placePatch(Patch selectedPatch, Scanners scanner) {
        //Player has now selected a patch, now loop for placing the patch
        boolean placed = false;
        int x = 0;
        int y = 0;
        System.out.println("Please place your patch, with either LEFT RIGHT UP DOWN or PLACE");

        String input;
        while (!placed) {
            textUI.drawQuiltBoardWithPatch(currentPlayer.quiltBoard, selectedPatch, x, y);
            input = scan(scanner);
            switch (input) {
                case "UP":
                    if (y == 0) {
                        System.out.println("Can not move up any more");
                    } else {
                        y -= 1;
                    }

                    break;
                case "DOWN":
                    if (y == 9) {
                        System.out.println("Can not move down any more");
                    } else {
                        y += 1;
                    }
                    break;
                case "LEFT":
                    if (x == 0) {
                        System.out.println("Can not move left any more");
                    } else {
                        x -= 1;
                    }
                    break;
                case "RIGHT":
                    if (x == 9) {
                        System.out.println("Can not move right any more");
                    } else {
                        x += 1;
                    }
                    break;
                case "PLACE":
                    try {
                        game.placePatch(currentPlayer, selectedPatch, x, y);
                        placed = true;
                    } catch (GameException e) {
                        System.out.println("Can not place patch currently");
                    }
                    break;
                default:
                    System.out.println("Please enter a valid command");
                    break;
            }
        }
    }
}
