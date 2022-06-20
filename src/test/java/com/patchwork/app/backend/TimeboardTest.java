package com.patchwork.app.backend;

import com.patchwork.app.backend.model.Player;
import com.patchwork.app.backend.model.TimeBoard;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TimeboardTest {

    @Test
    public void constructTimeboardTest() {
        // Assert a Timeboard with  52 time spaces got created when called constructor

        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));

        TimeBoard timeBoard = new TimeBoard(players);
        Assert.assertEquals(52, timeBoard.spaces.size());
    }

    @Test
    public void testInitialPositionsOfPlayers(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        TimeBoard timeBoard = new TimeBoard(players);

        // Assert if the initial position of players in Timerboard is in the first space
        Assert.assertEquals(0, timeBoard.getPlayerPosition(players.get(0)));
        Assert.assertEquals(0, timeBoard.getPlayerPosition(players.get(1)));
    }

    @Test
    public void checkPositionOfPlayerAfterMoved(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        TimeBoard timeBoard = new TimeBoard(players);

        // Assert if the player has moved to the correct position of space after it is moved
        timeBoard.movePlayer(players.get(1), 5);
        Assert.assertEquals(5,timeBoard.getPlayerPosition(players.get(1)));

        // Since Player-1 is not moved check if he is in 0th space and not 5th space as Player-2
        Assert.assertNotEquals( 5, timeBoard.getPlayerPosition(players.get(0)) );
        Assert.assertEquals(0,timeBoard.getPlayerPosition(players.get(0)));
    }

    @Test
    public void testGetCurrentPlayer(){
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        TimeBoard timeBoard = new TimeBoard(players);
        timeBoard.movePlayer(players.get(1), 5);

        // Now Since Player-2 is ahead in Timer board Assert if the current player is Player-1
        Assert.assertEquals(players.get(0), timeBoard.getCurrentPlayer());

        // Move the Player-1 ahead of Player-2 and check if the current player is Player-2 since he is behind in Timer board
        timeBoard.movePlayer(players.get(0), 10);
        Assert.assertEquals(players.get(1), timeBoard.getCurrentPlayer());

        // Move the Player-2 to the same position of Player-1 making it sit over the top and Assert if the current player is Player-2
        timeBoard.movePlayer(players.get(1), 10);
        Assert.assertEquals(players.get(1), timeBoard.getCurrentPlayer());
        Assert.assertNotSame(players.get(0), timeBoard.getCurrentPlayer());
    }
}
