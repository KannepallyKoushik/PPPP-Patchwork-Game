package com.patchwork.app.peer_tests.tests;

import com.patchwork.app.peer_tests.adapters.LeatherPatch;
import com.patchwork.app.peer_tests.adapters.PurchasablePatch;
import com.patchwork.app.peer_tests.adapters.PatchBoard;
import org.junit.Assert;
import org.junit.Test;

public class PatchBoardTest {
    @Test
    public void testPlaceableAndNonPlaceable() {
        // simple 2x2 board
        PatchBoard board = new PatchBoard(2, 2);

        // simply put leather patch at (1,0) -> board: "00 10"
        LeatherPatch leather = new LeatherPatch();
        Assert.assertTrue(board.isPlaceable(1, 0, leather));
        Assert.assertTrue(board.placePatch(1, 0, leather));

        // try placing a full 2x2 patch, should fail -> board: "00 10"
        PurchasablePatch square = new PurchasablePatch("11 11", 0, 0, 0);
        Assert.assertFalse(board.isPlaceable(0, 0, square));
        Assert.assertFalse(board.placePatch(0, 0, square));

        // try placing patch that should fit the entire board -> board: "11 11"
        PurchasablePatch shouldFit = new PurchasablePatch("11 01", 0, 0, 0);
        Assert.assertTrue(board.isPlaceable(0, 0, shouldFit));
        Assert.assertTrue(board.placePatch(0, 0, shouldFit));

        // test sanity check
        Assert.assertEquals(0, board.getEmptyTileCount());
        // for completeness, actually make sure that all board tiles are occupied
        boolean[][] boardShape = board.getBoard();
        for (boolean[] booleans : boardShape)
            for (boolean aBoolean : booleans)
                Assert.assertTrue(aBoolean);
    }
}