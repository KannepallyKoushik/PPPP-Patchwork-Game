package com.patchwork.app.backend;

import com.patchwork.app.backend.exceptions.GameException;
import com.patchwork.app.backend.model.Game;
import com.patchwork.app.backend.model.Patch;
import com.patchwork.app.backend.model.Player;
import com.patchwork.app.testutils.AbstractGameTest;
import com.patchwork.app.testutils.MockGameFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class GameTest extends AbstractGameTest {

    private static Game makeFinishedGame() {
        Game game = new MockGameFactory().createGame();

        game.timeBoard.movePlayer(game.players.get(0), 51);
        try {
            game.movePastNextPlayer(game.players.get(1));
        } catch (GameException e) {
            throw new RuntimeException("Failed to create finished game");
        }

        if (!game.isFinished()) {
            throw new RuntimeException("Created game is not finished");
        }

        return game;
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

    @Test
    public void testPlacePatch() throws GameException {
        Player currentPlayer = game.timeBoard.getCurrentPlayer();
        Patch patch = game.patchList.getAvailablePatches().get(0);

        int nrButtons = currentPlayer.nrButtons - patch.buttonCost;

        game.placePatch(currentPlayer, patch, 0, 0);

        Assert.assertEquals(nrButtons, currentPlayer.nrButtons);
        Assert.assertEquals(1, currentPlayer.quiltBoard.patches.size());
        Assert.assertEquals(patch.timeTokenCost, game.timeBoard.getPlayerPosition(currentPlayer));
    }

    @Test
    public void testPlacePatchSpecialTile() throws GameException {
        createGameWithPatchList(Arrays.asList(makeSevenBySevenPatch(), makeSevenBySevenPatch()));

        Player player1 = game.players.get(0);
        Player player2 = game.players.get(1);

        // Should obtain special patch by placing the custom 7x7 patch
        game.placePatch(player1, makeSevenBySevenPatch(), 0, 0);
        Assert.assertEquals(player1, game.specialTilePlayer);

        // Second player should not override specialTilePlayer because its already been obtained.
        game.placePatch(player2, makeSevenBySevenPatch(), 0, 0);
        Assert.assertEquals(player1, game.specialTilePlayer);
    }

    @Test
    public void testPlacePatchInvalidLocation()  {
        Player currentPlayer = game.timeBoard.getCurrentPlayer();
        Patch patch = game.patchList.getAvailablePatches().get(0);

        assertThrows(
                GameException.class,
                () -> game.placePatch(currentPlayer, patch, -1, -1));
        assertEquals(0,currentPlayer.quiltBoard.patches.size());
    }

    @Test
    public void testPlacePatchOverlap() throws GameException {
        Player currentPlayer = game.timeBoard.getCurrentPlayer();
        Patch patch = game.patchList.getAvailablePatches().get(0);

        game.placePatch(currentPlayer, patch, 0, 0);

        assertEquals(1,currentPlayer.quiltBoard.patches.size());
        assertThrows(
                GameException.class,
                () -> game.placePatch(currentPlayer, patch, 0, 0));
        assertEquals(1,currentPlayer.quiltBoard.patches.size());
    }

    @Test
    public void testPlacePatchNotEnoughButtons()  {
        Player currentPlayer = game.timeBoard.getCurrentPlayer();
        currentPlayer.nrButtons = 0; // Set nrButtons to 0 so the player cant afford the patch
        Patch patch = game.patchList.getAvailablePatches().get(0);

        assertThrows(
                GameException.class,
                () -> game.placePatch(currentPlayer, patch, 0, 0));
        assertEquals(0,currentPlayer.quiltBoard.patches.size());
    }

    @Test
    public void testPlacePatchGameFinished() {
        Game game = makeFinishedGame();

        Player player = game.players.get(0);
        Patch patch = game.patchList.getAvailablePatches().get(0);

        assertThrows(
                GameException.class,
                () -> game.placePatch(player, patch, 0, 0));
        assertEquals(0,player.quiltBoard.patches.size());
    }

    @Test
    public void testMovePastNextPlayer() throws GameException {
        Player player1 = game.players.get(0);
        Player player2 = game.players.get(1);

        game.timeBoard.movePlayer(player1, 10);
        game.movePastNextPlayer(player2);

        Assert.assertEquals(11, game.timeBoard.getPlayerPosition(player2));
    }

    @Test
    public void testMovePastNextPlayerBehind()  {
        Player player = game.players.get(1);

        game.timeBoard.movePlayer(player, 1);


        assertThrows(
                GameException.class,
                () -> game.movePastNextPlayer(player));
        assertEquals(1, game.timeBoard.getPlayerPosition(player));
    }

    @Test
    public void testMovePastNextPlayerGameFinished() {
        Game game = makeFinishedGame();

        Player player = game.timeBoard.getCurrentPlayer();

        assertThrows(
                GameException.class,
                () -> game.movePastNextPlayer(player));
        assertEquals(51, game.timeBoard.getPlayerPosition(player));
    }
}
