package external;

import com.patchwork.app.backend.*;
import com.patchwork.app.backend.Exceptions.GameException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

/*

TEST CASES
 [x] 1. Correct setup at start of game                        setupTest
 [x] 2. Correct player takes turn                             correctPlayerTest
 [x] 3. Correct execution of “Advance and Receive Buttons”    advanceToNextPlayerTest
 [x] 4. Correct execution of “Take and Place Patch”           placePatchTest
 [x] 5. An unusual input or event                             testPlacePatchInvalidLocation
 [x] Finalizing game test                                     testFinishGame
 [x] Seven x seven test                                       testPlacePatchSpecialTile
 [x] Placing overlapping patch should fail                    testPlacePatchOverlap
 [x] Trying to buy patch but not enough buttons should fail   testPlacePatchNotEnoughButtons
 [x] Trying to buy a patch when game is finished should fail  testBuyPatchGameFinished

 */

public class ExternalTests {

    private Game game;

    private Player startingPlayer;
    private Player otherPlayer;
    public static final int DEFAULT_NR_BUTTONS = 5;


    //Set up method
    @Before
    public void setUp() {
        GameFactory gameFactory = new GameFactory();
        this.game = gameFactory.createGame();
        this.startingPlayer = game.timeBoard.getCurrentPlayer();
        this.otherPlayer = game.getOpponent(startingPlayer);
    }

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



    //Test to make sure game makes a correct game, implementing required test #1
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

        //Assert both players exist
        assertTrue(game.players.contains(startingPlayer));
        assertTrue(game.players.contains(otherPlayer));

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
    public void correctPlayerTest() throws GameException {
        // Mock player 1 turn, i.e. move to somewhere on the board should end turn as well
        game.movePastNextPlayer(startingPlayer);

        // Check Player2 is now current player, as they are the furthest behind
        assertSame(game.timeBoard.getCurrentPlayer(), otherPlayer);
    }

    //Test whether advancing (i.e. move) works properly, for test #3
    @Test
    public void advanceToNextPlayerTest() throws GameException {

        //Change opponents place
        game.timeBoard.movePlayer(otherPlayer, 10);

        // Select 'move to next player'
        game.movePastNextPlayer(startingPlayer);

        //Now current player should have moved to tile 10+1, gaining 10+1 buttons
        Assert.assertEquals(10 + 1, game.timeBoard.getPlayerPosition(startingPlayer));
        Assert.assertEquals(DEFAULT_NR_BUTTONS + 10 + 1, startingPlayer.nrButtons);
    }

    //Implement required test 4, picking and placing patch
    @Test
    public void placePatchTest() throws GameException {

        //Get random patch from 0-2
        int randomIndex = (int) (Math.random() * 2);
        Patch patch = game.patchList.getAvailablePatches().get(randomIndex);


        assertTrue(patch.buttonCost > 0);
        assertTrue(patch.timeTokenCost > 0);


        int nrButtons = startingPlayer.nrButtons - patch.buttonCost;

        game.placePatch(startingPlayer, patch, 0, 0);

        Assert.assertEquals(nrButtons, startingPlayer.nrButtons);
        Assert.assertEquals(1, startingPlayer.quiltBoard.patches.size());
        Assert.assertEquals(patch.timeTokenCost, game.timeBoard.getPlayerPosition(startingPlayer));
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
    public void testFinishGame() throws GameException {

        //Let opponent finish
        game.timeBoard.movePlayer(otherPlayer, 51);


        // Select 'move to next player'
        game.movePastNextPlayer(startingPlayer);


        //assert game is finished and there is a result
        assertTrue(game.isFinished());
        assertNotNull(game.result);


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


    //Test when a player tries to place patch on an already occupied spot, expect exception
    @Test(expected = GameException.class)
    public void testPlacePatchOverlap() throws GameException {
        //Add buttons to startingPlayer to ensure that they can always buy patches
        startingPlayer.addButtons(100);
        Patch patch1 = game.patchList.getAvailablePatches().get(0);

        game.placePatch(startingPlayer, patch1, 5, 5);

        Patch patch2 = game.patchList.getAvailablePatches().get(0);

        //assert patch 1 != patch 2, i.e. availablepatches updates correctly
        assertNotEquals(patch1, patch2);

        game.placePatch(startingPlayer, patch2, 5, 5);

        //Player still only has 1 patch placed
        assertEquals(1, startingPlayer.quiltBoard.patches.size());

    }


    //Test when someone tries to buy a patch without enough buttons
    @Test(expected = GameException.class)
    public void testPlacePatchNotEnoughButtons() throws GameException {
        startingPlayer.nrButtons = 0; // Set nrButtons to 0 so the player cant afford the patch

        Patch patch1 = game.patchList.getAvailablePatches().get(0);
        assertTrue(patch1.buttonCost > 0);

        game.placePatch(startingPlayer, patch1, 0, 0);

        assertEquals(0, startingPlayer.quiltBoard.patches.size());
    }

    @Test(expected = GameException.class)
    public void testBuyPatchGameFinished() throws GameException {

        //Initial stuff is same as testFinishGame()
        //Let opponent finish
        game.timeBoard.movePlayer(otherPlayer, 51);

        // Select 'move to next player'
        game.movePastNextPlayer(startingPlayer);

        //assert game is finished and there is a result
        assertTrue(game.isFinished());
        assertNotNull(game.result);

        //Now game is finished so:
        //Now try to still place a patch
        Patch patch1 = game.patchList.getAvailablePatches().get(0);

        //Assert that the patch can be placed on the quiltboard, if there are no system checks
        //To make sure that the player has enough buttons, no overlap etc
        assertTrue(startingPlayer.quiltBoard.canPlace(patch1, 5, 5));


        //Now let the game try to place it, throwing an exception
        game.placePatch(startingPlayer, patch1, 5 ,5 );

        //Patch should not be placed, nor buttons deducted or size increased;
        assertTrue(startingPlayer.quiltBoard.canPlace(patch1, 5, 5));
        assertEquals(DEFAULT_NR_BUTTONS, startingPlayer.nrButtons);
        assertEquals(0, startingPlayer.quiltBoard.patches.size());


    }
}
