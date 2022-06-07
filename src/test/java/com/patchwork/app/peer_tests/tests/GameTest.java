package com.patchwork.app.peer_tests.tests;

/*
import patchwork.core.model.Game;
import patchwork.players.IPlayer;
import patchwork.players.PlayerType;

public class GameTest {

    @Test
    public void GameConstructorTest() {
        Game game = new Game();
        // getPlayer returns a player based on id, usually (essentially player index)
        Assert.assertNull(game.getPlayer(-1));
        Assert.assertNull(game.getPlayer(0));
        Assert.assertNull(game.getPlayer(1));
        Assert.assertNull(game.getPlayer(9));
        Assert.assertNotNull(game.deck);
        Assert.assertNotNull(game.bank);
        Assert.assertNotNull(game.timeBoard);
        // the physical game has 162 credits total apparently
        Assert.assertEquals(162, game.bank.getCredits());
    }

    @Test
    public void playerTest() {
        Game game = new Game();

        // add 2 players
        game.addPlayer(PlayerType.HUMAN, "P1");
        game.addPlayer(PlayerType.HUMAN, "P2");
        IPlayer p1 = game.getPlayer(0);
        IPlayer p2 = game.getPlayer(1);
        Assert.assertNotNull(p1);
        Assert.assertNotNull(p2);

        // Starts with 5 credits
        Assert.assertTrue(game.bank.getCredits() >= 10);
        Assert.assertEquals(5, p1.getCredits());
        Assert.assertEquals(5, p2.getCredits());

        // non equal ids
        Assert.assertNotEquals(game.getPlayer(0).getId(), game.getPlayer(1).getId());

        // cannot add third player
        Assert.assertThrows(IllegalStateException.class, () -> game.addPlayer(PlayerType.HUMAN, "P3"));
        Assert.assertNull(game.getPlayer(2));

        // check player count
        Assert.assertEquals(2, game.getPlayerCount());

        // check that getOtherPlayer works as expected
        Assert.assertEquals(p1, game.getOtherPlayer(p2.getId()));
        Assert.assertEquals(p2, game.getOtherPlayer(p1.getId()));
    }
}
*/