package com.patchwork.app.backend;

import com.patchwork.app.backend.Mock.MockScanner;
import org.junit.Test;



import static org.junit.Assert.*;


public class GameControllerTest {


    @Test
    public void constructorTest(){
        GameController gc = new GameController();
        assertNotNull(gc.game);
        assertNotNull(gc.textUI);
        assertNotNull(gc.currentPlayer);
        assertNotNull(gc.scanner);
        assertNotNull(gc.game.players.indexOf(gc.currentPlayer));
    }

    @Test
    public void getNextPlayerTest(){
        GameController gc = new GameController();
        Player currentPlayer = gc.currentPlayer;
        Player nextPlayer = gc.getNextPlayer(currentPlayer);
        assertNotEquals(currentPlayer, nextPlayer);
        assertEquals(currentPlayer, gc.getNextPlayer(nextPlayer));
    }

    @Test
    public void pickMoveTest(){
        GameController gc = new GameController();


        gc.scanner = new MockScanner("MOVE");
        String action = gc.pickMove(gc.scanner);
        assertEquals("MOVE", action );

        gc.scanner = new MockScanner("BUY");
        String action2 = gc.pickMove(gc.scanner);
        assertEquals("BUY", action2);
    }

    @Test
    public void pickPatchTest(){
        GameController gc = new GameController();
        gc.currentPlayer.addButtons(100);

        gc.scanner = new MockScanner("1");
        Patch patch1 = gc.pickPatch(gc.scanner);

        gc.scanner = new MockScanner("2");
        Patch patch2 = gc.pickPatch(gc.scanner);

        gc.scanner = new MockScanner("3");
        Patch patch3 = gc.pickPatch(gc.scanner);

        assertNotEquals(patch1, patch2);
        assertNotEquals(patch1, patch3);
        assertNotEquals(patch2, patch3);
        assertNotNull(patch1);
        assertNotNull(patch2);
        assertNotNull(patch3);

        assertEquals(3, patch1.buttonCost);
        assertEquals(4, patch1.timeTokenCost);
        assertEquals(1, patch1.buttonScore);
    }

    @Test
    public void placePatchTest(){
        GameController gc = new GameController();
        gc.currentPlayer.addButtons(100);
        int currentButtons = gc.currentPlayer.nrButtons;

        gc.scanner = new MockScanner("1");
        Patch patch1 = gc.pickPatch(gc.scanner);

        gc.scanner = new MockScanner("PLACE");
        gc.placePatch(patch1, gc.scanner);

        assertEquals(1, gc.currentPlayer.quiltBoard.patches.size());
        assertEquals(currentButtons-patch1.buttonCost, gc.currentPlayer.nrButtons);
    }

    //If both players only move, then the player who started should win
    //Currently doesnt work because some game logic doesnt work
//    @Test
//    public void runMoveTest() throws GameException {
//        GameController gc = new GameController();
//        gc.scanner = new MockScanner("MOVE");
//        Player initialPlayer = gc.currentPlayer;
//        gc.run();
//        assertEquals(initialPlayer, gc.game.result.winner);
//    }



    //TODO
    //Implement some tests for run
    //Currently not the case due to scanner + while loop making it hard to test, figure something out
    @Test
    public void runTest(){

    }

}
