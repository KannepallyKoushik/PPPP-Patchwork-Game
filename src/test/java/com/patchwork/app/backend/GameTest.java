package com.patchwork.app.backend;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class GameTest {

    private static Game makeFinishedGame() {
        Game game = new Game();

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
        Game game = new Game();

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

    @Test(expected = GameException.class)
    public void testPlacePatchNotEnoughButtons() throws GameException {
        Game game = new Game();

        Player currentPlayer = game.timeBoard.getCurrentPlayer();
        currentPlayer.nrButtons = 0; // Set nrButtons to 0 so the player cant afford the patch
        Patch patch = game.patchList.getAvailablePatches().get(0);

        game.placePatch(currentPlayer, patch, 0, 0);
    }

    @Test(expected = GameException.class)
    public void testPlacePatchGameFinished() throws GameException {
        Game game = makeFinishedGame();

        Player player = game.players.get(0);
        Patch patch = game.patchList.getAvailablePatches().get(0);

        game.placePatch(player, patch, 0, 0);
    }

    @Test
    public void testMovePastNextPlayer() throws GameException {
        Game game = new Game();

        Player player1 = game.players.get(0);
        Player player2 = game.players.get(1);

        game.timeBoard.movePlayer(player1, 10);
        game.movePastNextPlayer(player2);

        Assert.assertEquals(11, game.timeBoard.getPlayerPosition(player2));
    }

    @Test(expected = GameException.class)
    public void testMovePastNextPlayerBehind() throws GameException {
        Game game = new Game();

        Player player = game.players.get(1);

        game.timeBoard.movePlayer(player, 1);

        game.movePastNextPlayer(player);
    }

    @Test(expected = GameException.class)
    public void testMovePastNextPlayerGameFinished() throws GameException {
        Game game = makeFinishedGame();

        Player player = game.timeBoard.getCurrentPlayer();

        game.movePastNextPlayer(player);
    }
}
