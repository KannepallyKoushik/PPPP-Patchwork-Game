package com.patchwork.app.systemTests;

import com.patchwork.app.backend.*;
import com.patchwork.app.backend.GameStates.GameStateType;
import com.patchwork.app.backend.GameStates.PlacePatch;
import com.patchwork.app.backend.Inputs.GameInput;
import com.patchwork.app.backend.Inputs.MockGameInput;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;


//TEST CASES
// [x] 1. Correct setup at start of game                        setupTest
// [x] 2. Correct player takes turn                             correctPlayerTest
// [x] 3. Correct execution of “Advance and Receive Buttons”    advanceToNextPlayerTest
// [ ] 4. Correct execution of “Take and Place Patch”           placePatchTest (?)
// [x] 5. An unusual input or event                             testPlacePatchInvalidLocation
// [ ] Finalizing game test
// [x] Seven x seven test                                       testPlacePatchSpecialTile
// [x] Placing overlapping patch should fail                    testPlacePatchOverlap
// [x] Trying to buy patch but not enough buttons should fail   testPlacePatchNotEnoughButtons
// [x] Trying to move after game is finished should fail        testMovePastNextPlayerGameFinished


public class GameFlowTest {

    Game game;
    GameController gameController;
    MockGameInput gameInput;
    public static final int DEFAULT_NR_BUTTONS = 5;


    private static Patch makeSevenBySevenPatch() {
        return new Patch(
                Arrays.asList(
                        Arrays.asList(true, true, true, true, true, true, true),
                        Arrays.asList(true, true, true, true, true, true, true),
                        Arrays.asList(true, true, true, true, true, true, true),
                        Arrays.asList(true, true, true, true, true, true, true),
                        Arrays.asList(true, true, true, true, true, true, true),
                        Arrays.asList(true, true, true, true, true, true, true),
                        Arrays.asList(true, true, true, true, true, true, true)
                ),
                0,
                0,
                0
        );
    }

    @Before
    public void tearDown() {
        gameController = new GameController();
        gameController.scanner = new MockGameInput();
        game = new Game();
        gameController = new GameController();
    }

    @Test
    public void setupTest(){
        GameController gc = new GameController();

        //Assert 3 possible choices for patches
        assertEquals(3, gc.game.patchList.getAvailablePatches().size());

        //Assert 2 players are made
        assertEquals(2, gc.game.players.size());

        //Assert no one has specialtile
        assertNull(gc.game.specialTilePlayer);

        //Assert game is not finished
        assertFalse(gc.game.isFinished());

        //Assertions based on each player
        for (int i = 0; i < gc.game.players.size(); i++){
            //Assert starting button count
            assertEquals(DEFAULT_NR_BUTTONS, gc.game.players.get(i).nrButtons);
            //Assert empty quiltboard
            assertEquals(0, gc.game.players.get(i).quiltBoard.patches.size());

            //Assert each player starting on position 0
            assertEquals(0, gc.game.timeBoard.getPlayerPosition(gc.game.players.get(i)));
        }
    }

    @Test
    public void correctPlayerTest() {
        GameController gameController = new GameController();
        Player currentPlayer = gameController.game.players.get(0);
        Player nextPlayer = gameController.game.players.get(1);

        // Mock move of Player1 to somewhere on the board
        gameController.game.timeBoard.movePlayer(currentPlayer, 5);

        // Check Player2 is next to move, as they are the furthest behind
        assertEquals(gameController.getNextPlayer(currentPlayer), nextPlayer);
    }

    @Test
    public void advanceToNextPlayerTest() {
        Player currentPlayer = game.timeBoard.getCurrentPlayer();
        game.timeBoard.movePlayer(currentPlayer, 10);

        // Select 'move to next player'
        gameInput.doMove(Move.MOVE_LEFT);
        gameInput.doMove(Move.CONFIRM);

        // TODO: find a way to ensure that a GameController cycle has passed in between
        currentPlayer = game.timeBoard.getCurrentPlayer();
        Assert.assertEquals(11, game.timeBoard.getPlayerPosition(currentPlayer));
        Assert.assertEquals(DEFAULT_NR_BUTTONS + 11, currentPlayer.nrButtons);
    }

    @Test
    public void placePatchTest() {
        Player currentPlayer = game.timeBoard.getCurrentPlayer();

        // Select 'place a patch'
        gameInput.doMove(Move.MOVE_RIGHT);
        gameInput.doMove(Move.CONFIRM);
        Assert.assertEquals(GameStateType.PICK_PATCH, gameController.getState().type);

        // Pick the first patch
        gameInput.doMove(Move.CONFIRM);
        Assert.assertEquals(GameStateType.PLACE_PATCH, gameController.getState().type);

        // Make sure that the right patch was selected
        Patch selectedPatch = game.patchList.getAvailablePatches().get(0);
        Assert.assertEquals(selectedPatch, ((PlacePatch) gameController.getState()).patch);

        // Move the patch a few times and then place it
        gameInput.doMove(Move.MOVE_RIGHT);
        gameInput.doMove(Move.MOVE_RIGHT);
        gameInput.doMove(Move.MOVE_RIGHT);
        gameInput.doMove(Move.MOVE_DOWN);
        gameInput.doMove(Move.CONFIRM);
        Assert.assertNotEquals(currentPlayer, game.timeBoard.getCurrentPlayer());
        Assert.assertEquals(GameStateType.PICK_MOVE, gameController.getState().type);

        // Check that the patch was properly placed
        QuiltBoard qb = new QuiltBoard();
        qb.placePatch(selectedPatch, 3, 1);
        Assert.assertEquals(qb, currentPlayer.quiltBoard);
    }

    @Test(expected = GameException.class)
    public void testPlacePatchInvalidLocation() throws GameException {
        Game game = new Game();

        Player currentPlayer = game.timeBoard.getCurrentPlayer();
        Patch patch = game.patchList.getAvailablePatches().get(0);

        game.placePatch(currentPlayer, patch, -1, -1);
    }

    @Test(expected = GameException.class)
    public void testPlacePatchOverlap() throws GameException {
        Game game = new Game();

        Player currentPlayer = game.timeBoard.getCurrentPlayer();
        Patch patch = game.patchList.getAvailablePatches().get(0);

        game.placePatch(currentPlayer, patch, 0, 0);

        patch = game.patchList.getAvailablePatches().get(0);
        game.placePatch(currentPlayer, patch, 0, 0); // Place the patch in the same location as the previous one
    }

    @Test
    public void testPlacePatchSpecialTile() throws GameException {
        Game game = new Game();

        Player player1 = game.players.get(0);
        Player player2 = game.players.get(1);

        // Should obtain special patch by placing the custom 7x7 patch
        game.placePatch(player1, makeSevenBySevenPatch(), 0, 0);
        Assert.assertEquals(player1, game.specialTilePlayer);

        // Second player should not override specialTilePlayer because its already been obtained.
        game.placePatch(player2, makeSevenBySevenPatch(), 0, 0);
        Assert.assertEquals(player1, game.specialTilePlayer);
    }

    @Test(expected = GameException.class)
    public void testPlacePatchNotEnoughButtons() throws GameException {
        Game game = new Game();
        Player currentPlayer = game.timeBoard.getCurrentPlayer();
        currentPlayer.nrButtons = 0; // Set nrButtons to 0 so the player cant afford the patch
        Patch patch = game.patchList.getAvailablePatches().get(0);

        game.placePatch(currentPlayer, patch, 0, 0);
    }

    @Test(expected = GameException.class)
    public void testMovePastNextPlayerGameFinished() throws GameException {
        //Make new gamecontroller with a finished game
        GameController gc = new GameController();
        gc.game = makeFinishedGame();
        gc.scanner = new MockGameInput();
        gameInput.doMove(Move.MOVE_LEFT);
        gameInput.doMove(Move.CONFIRM);
    }

    //Method used in moving past a player after a finished game
    private Game makeFinishedGame() {
        Game game = new Game();
        game.timeBoard.movePlayer(game.players.get(0), 51);


        if (!game.isFinished()) {
            throw new RuntimeException("Created game is not finished");
        }

        return game;
    }
}
