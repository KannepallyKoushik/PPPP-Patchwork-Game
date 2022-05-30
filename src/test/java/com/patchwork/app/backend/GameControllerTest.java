//package com.patchwork.app.backend;
//
//import com.patchwork.app.backend.Exceptions.CanNotAffordException;
//import com.patchwork.app.backend.Exceptions.GameException;
//import com.patchwork.app.backend.Inputs.MockGameInput;
//import org.junit.Before;
//import org.junit.Test;
//
//
//
//import static org.junit.Assert.*;
//
//
//public class GameControllerTest {
//    private GameController gc;
//    private Thread t;
//    private MockGameInput gameInput;
//
//    @Before
//    public void setUp(){
//        gc = new GameController(new MockGameInput());
//        gameInput = (MockGameInput) gc.input;
//    }
//
//    @Test
//    public void constructorTest(){
//        assertNotNull(gc.game);
//        assertNotNull(gc.textUI);
//        assertNotNull(gc.currentPlayer);
//        assertNotNull(gc.input);
//        assertNotNull(gc.game.players.indexOf(gc.currentPlayer));
//    }
//
//    @Test
//    public void getOtherPlayerTest(){
//        Player currentPlayer = gc.currentPlayer;
//        Player otherPlayer = gc.getOtherPlayer(currentPlayer);
//        assertNotEquals(currentPlayer, otherPlayer);
//        assertEquals(currentPlayer, gc.getOtherPlayer(otherPlayer));
//    }
//
//    @Test
//    public void pickMoveTest() throws InterruptedException, GameException {
//
//        boolean pickMove = gc.pickMove();
//        Thread.sleep(300);
//        gameInput.updateMove(Move.MOVE_LEFT);
//        Thread.sleep(300);
//        gameInput.updateMove(Move.CONFIRM);
//        Thread.sleep(300);
//        assertEquals(gc.move, Move.WAITING);
//        assertTrue(pickMove);
//    }
//
//    @Test
//    public void pickPatchTest() throws CanNotAffordException, InterruptedException {
//        gc.currentPlayer.addButtons(100);
//        Patch patch1 = gc.pickPatch();
//        gameInput.updateMove(Move.MOVE_LEFT);
//        Thread.sleep(300);
//        gameInput.updateMove(Move.CONFIRM);
//        Thread.sleep(300);
//
//        assertNotNull(patch1);
//
//        assertEquals(3, patch1.buttonCost);
//        assertEquals(4, patch1.timeTokenCost);
//        assertEquals(1, patch1.buttonScore);
//    }
//
//    @Test
//    public void placePatchTest() throws InterruptedException, GameException {
//        gc.currentPlayer.addButtons(100);
//        int currentButtons = gc.currentPlayer.nrButtons;
//        Patch patch1 = gc.game.patchList.getAvailablePatches().get(0);
//
//        boolean placed = gc.placePatch(patch1);
//        gameInput.updateMove(Move.MOVE_RIGHT);
//        Thread.sleep(300);
//        gameInput.updateMove(Move.MOVE_DOWN);
//        Thread.sleep(300);
//        gameInput.updateMove(Move.CONFIRM);
//        Thread.sleep(300);
//        assertEquals(1, gc.currentPlayer.quiltBoard.patches.size());
//        assertEquals(currentButtons-patch1.buttonCost, gc.currentPlayer.nrButtons);
//        assertTrue(gc.currentPlayer.quiltBoard.spaces[1][1]);
//    }
//
//
//
//    /*
//    run is tested in GameFlowTest
//     */
//
//}
