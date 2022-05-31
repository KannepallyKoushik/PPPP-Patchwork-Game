package com.patchwork.app.systemTests;

import com.patchwork.app.backend.Exceptions.GameException;
import com.patchwork.app.backend.GameStates.GameStateType;
import com.patchwork.app.backend.GameStates.PlacePatch;
import com.patchwork.app.backend.Move;
import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.Player;
import com.patchwork.app.backend.QuiltBoard;
import com.patchwork.app.testUtils.AbstractGameTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;


//TEST CASES
// [x] 1. Correct setup at start of game                        setupTest
// [x] 2. Correct player takes turn                             correctPlayerTest
// [x] 3. Correct execution of “Advance and Receive Buttons”    advanceToNextPlayerTest
// [x] 4. Correct execution of “Take and Place Patch”           placePatchTest
// [x] 5. An unusual input or event                             testPlacePatchInvalidLocation
// [x] Finalizing game test                                     testFinishGame
// [x] Seven x seven test                                       testPlacePatchSpecialTile
// [x] Placing overlapping patch should fail                    testPlacePatchOverlap
// [x] Trying to buy patch but not enough buttons should fail   testPlacePatchNotEnoughButtons
// [x] Trying to buy a patch when game is finished should fail  testBuyPatchGameFinished


public class GameFlowTest extends AbstractGameTest {

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

    //Method used in moving past a player after a finished game
    private void makeFinishedGame() throws GameException, InterruptedException {
        game.timeBoard.movePlayer(nextPlayer, 51);
        gameInput.updateMove(Move.MOVE_LEFT);
        Thread.sleep(300);
        gameInput.updateMove(Move.CONFIRM);
        Thread.sleep(300);
        if (!game.isFinished()) {
            throw new RuntimeException("Created game is not finished");
        }
    }




    /*
    TODO
     Done in a not-that-well programmed manner, i.e. using sleeps as opposed to making sure the threads run nicely separately
     Could be done in the future if there is time
     */

    private void executeMoves(Move... moves) {
        for (Move move : moves) {
            gameInput.updateMove(move);
            gameController.waitNextCycle();
        }
    }

    private void selectFirst() {
        executeMoves(
                Move.MOVE_LEFT,
                Move.MOVE_LEFT,
                Move.CONFIRM
        );
    }

    //Second is selecting to buy a patch, or buying the second patch
    private void selectSecond() {
        executeMoves(
                Move.MOVE_LEFT,
                Move.MOVE_LEFT,
                Move.MOVE_RIGHT,
                Move.CONFIRM
        );
    }


    //Test to make sure gameController makes a correct game, implementing required test #1
    @Test
    public void setupTest() {
        //Assert 3 possible choices for patches
        assertEquals(3, game.patchList.getAvailablePatches().size());

        //Assert 2 players are made
        assertEquals(2, game.players.size());

        //Assert no one has specialtile
        assertNull(game.specialTilePlayer);

        //Assert game is not finished
        assertFalse(game.isFinished());

        //Assert the current player exists
        assertTrue(game.players.contains(startingPlayer));

        //Assertions based on each player
        for (int i = 0; i < game.players.size(); i++) {
            //Assert starting button count
            assertEquals(DEFAULT_NR_BUTTONS, game.players.get(i).nrButtons);
            //Assert empty quiltboard
            assertEquals(0, game.players.get(i).quiltBoard.patches.size());

            //Assert each player starting on position 0
            assertEquals(0, game.timeBoard.getPlayerPosition(game.players.get(i)));
        }
    }


    //Test to make sure the correct player takes the turn, implement required test #2
    @Test
    public void correctPlayerTest() throws InterruptedException {
        // Mock player 1 turn, i.e. move to somewhere on the board should end turn as well
        selectFirst();


        // Check Player2 is now current player, as they are the furthest behind
        assertEquals(gameController.currentPlayer.name, nextPlayer.name);
    }


    //Test whether advancing (i.e. move) works properly, for test #3
    @Test
    public void advanceToNextPlayerTest() throws InterruptedException {

        //Change opponents place
        game.timeBoard.movePlayer(nextPlayer, 10);

        // Select 'move to next player'
        selectFirst();

        //Now current player should have moved to tile 10+1, gaining 10+1 buttons
        Assert.assertEquals(11, game.timeBoard.getPlayerPosition(startingPlayer));
        Assert.assertEquals(DEFAULT_NR_BUTTONS + 11, startingPlayer.nrButtons);
    }

    //Implement required test 4, picking and placing patch
    @Test
    public void placePatchTest() throws GameException, InterruptedException {

        // Select 'buy a patch'
        selectSecond();
        Assert.assertEquals(GameStateType.PICK_PATCH, gameController.getState().type);

        // Pick the first patch
        selectFirst();
        Assert.assertEquals(GameStateType.PLACE_PATCH, gameController.getState().type);

        // Make sure that the right patch was selected
        Patch selectedPatch = game.patchList.getAvailablePatches().get(0);
        Assert.assertEquals(selectedPatch, ((PlacePatch) gameController.getState()).patch);

        // Move the patch a few times and then place it
        gameInput.updateMove(Move.MOVE_RIGHT);
        Thread.sleep(300);
        gameInput.updateMove(Move.MOVE_RIGHT);
        Thread.sleep(300);
        gameInput.updateMove(Move.MOVE_RIGHT);
        Thread.sleep(300);
        gameInput.updateMove(Move.MOVE_DOWN);
        Thread.sleep(300);
        gameInput.updateMove(Move.CONFIRM);

        //Assert that currentPlayer in the game has now changed
        Assert.assertNotEquals(startingPlayer, game.timeBoard.getCurrentPlayer());

        //Assert that the second player is now in pick_move state
        Assert.assertEquals(GameStateType.PICK_MOVE, gameController.getState().type);

        // Check that the patch was properly placed
        QuiltBoard qb = new QuiltBoard();
        qb.placePatch(selectedPatch, 3, 1);
        Assert.assertEquals(qb.spaces, startingPlayer.quiltBoard.spaces);
    }


    /*
    For our unusual input or event, we test that if a player somehow manages to break the input system,
    they still cant place a patch in an invalid location as an exception gets thrown and nothing happens to the game
    So the system still has proper checks beyond just input checking
     */
    @Test(expected = GameException.class)
    public void testPlacePatchInvalidLocation() throws GameException {

        Patch patch = game.patchList.getAvailablePatches().get(0);

        game.placePatch(startingPlayer, patch, -1, -1);

        //Assert the patch did not get placed
        assertEquals(0, startingPlayer.quiltBoard.patches.size());

        //Assertion by annotation should also pass
    }


    /*
    For our next 5 tests, we test either special circumstances of the game (special tile and finished game) or
    invalid cases which should throw exceptions, to test if these work properly
     */

    //Test whether the game correctly finishes after both players are at position 51
    @Test
    public void testFinishGame() throws InterruptedException {

        //Let opponent finish
        game.timeBoard.movePlayer(nextPlayer, 51);

        //Select 'move to opponent' for current player
        // Select 'move to next player'
        selectFirst();

        //assert game is finished and there is a result
        assertTrue(game.isFinished());
        assertNotNull(game.result);
        assertEquals(gameController.currentState.type, GameStateType.FINISHED);


    }

    //Test special patch for 7x7 patch, if second player gets 7x7 it should still be only first player
    @Test
    public void testPlacePatchSpecialTile() throws GameException {

        Player player1 = game.players.get(0);
        Player player2 = game.players.get(1);

        // Should obtain special patch by placing the custom 7x7 patch
        game.placePatch(player1, makeSevenBySevenPatch(), 0, 0);
        Assert.assertEquals(player1, game.specialTilePlayer);

        // Second player should not override specialTilePlayer because its already been obtained.
        game.placePatch(player2, makeSevenBySevenPatch(), 0, 0);
        Assert.assertEquals(player1, game.specialTilePlayer);
    }


    //Test when a player tries to place patch on an already occupied spot
    @Test
    public void testPlacePatchOverlap() throws InterruptedException {
        //Add buttons to startingPlayer to ensure that they can always buy patches
        startingPlayer.addButtons(1000);

        // Select 'buy a patch'
        selectSecond();
        Assert.assertEquals(GameStateType.PICK_PATCH, gameController.getState().type);

        // Pick the first patch
        selectFirst();
        //Should now be in placing patch state
        Assert.assertEquals(GameStateType.PLACE_PATCH, gameController.getState().type);
        //Place the patch and go to next player turn
        doMove(Move.CONFIRM);

        //Assert now it is next player turn
        assertEquals(gameController.currentPlayer, gameController.getOtherPlayer(startingPlayer));

        //Let next player move just to skip turn
        selectFirst();
        //assert it is again starting player turn
        assertEquals(gameController.currentPlayer, startingPlayer);

        //Repeat turn again, i.e.:
        // Select 'buy a patch'
        selectSecond();
        Assert.assertEquals(GameStateType.PICK_PATCH, gameController.getState().type);

        // Pick the first patch
        selectFirst();
        Assert.assertEquals(GameStateType.PLACE_PATCH, gameController.getState().type);
        doMove(Move.CONFIRM);

        //Assert that it is still currently in place patch state
        Assert.assertEquals(GameStateType.PLACE_PATCH, gameController.getState().type);

    }

    //Test when someone tries to buy a patch without enough buttons
    @Test
    public void testPlacePatchNotEnoughButtons() throws InterruptedException {
        startingPlayer.nrButtons = 0; // Set nrButtons to 0 so the player cant afford the patch

        //Select buy patch
        selectSecond();

        //Select first patch ( any should work)
        selectFirst();

        //Game should not go into the next state, i.e. placing patch
        assertNotSame(gameController.currentState.type, GameStateType.PLACE_PATCH);

    }

    @Test
    public void testBuyPatchGameFinished() throws GameException, InterruptedException {

        makeFinishedGame();
        assertEquals(gameController.currentState.type, GameStateType.FINISHED);
        //Try to buy a patch
        selectSecond();

        //Assert that state is still finished, i.e. not in pick patch
        assertEquals(gameController.currentState.type, GameStateType.FINISHED);
    }


}
