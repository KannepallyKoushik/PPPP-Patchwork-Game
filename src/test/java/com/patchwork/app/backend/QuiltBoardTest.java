package com.patchwork.app.backend;

import org.junit.Assert;
import org.junit.Test;

public class QuiltBoardTest {

    @Test
    public void constructorTest() {
        QuiltBoard quiltBoard = new QuiltBoard();

        //Assert a 9x9 empty board is made according to the expected constructor

        Assert.assertEquals(9, quiltBoard.spaces.size());
        Assert.assertEquals(9, quiltBoard.spaces.get(0).size());

        for (int i = 0; i < quiltBoard.spaces.size(); i++) {
            for (int j = 0; j < quiltBoard.spaces.get(i).size(); j++) {
                Assert.assertFalse(quiltBoard.spaces.get(i).get(j));
            }
        }
        Assert.assertEquals(0, quiltBoard.patches.size());
    }


    @Test
    public void canPlaceTest() {
        QuiltBoard quiltBoard = new QuiltBoard();
        PatchFactory patchFactory = new PatchFactory();

        Patch testPatch = patchFactory.getPatches().get(0);
        Assert.assertTrue(quiltBoard.canPlace(testPatch, 0, 0));

    }

    @Test
    public void placeTest() {

        QuiltBoard quiltBoard = new QuiltBoard();
        PatchFactory patchFactory = new PatchFactory();
        Patch testPatch = patchFactory.getPatches().get(0);
        quiltBoard.placePatch(testPatch, 0, 0);

        //Assert patch list got updated on the board
        Assert.assertEquals(1, quiltBoard.patches.size());


        //Because a patch was placed with 4 trues in row 0, trying to place a new patch on these 4 coordinates should be false
        Patch testPatch2 = patchFactory.getPatches().get(1);
        for (int i = 0; i < 4; i++) {
            Assert.assertFalse(quiltBoard.canPlace(testPatch2, i, 0));
        }

        //But this patch could be placed on row 2 without issues
        Assert.assertTrue(quiltBoard.canPlace(testPatch2, 0, 2));
        quiltBoard.placePatch(testPatch2, 0, 2);


    }

    @Test
    public void hasSevenBySevenTest() {
        //Empty quiltboard == false
        QuiltBoard quiltBoard = new QuiltBoard();
        Assert.assertFalse(quiltBoard.hasSevenBySeven());

        //Now make it a 9x9 full true matrix == true
        for (int i = 0; i < quiltBoard.spaces.size(); i++){
            for (int j = 0; j < quiltBoard.spaces.get(i).size(); j++){
                quiltBoard.spaces.get(i).set(j, true);
            }
        }
        Assert.assertTrue(quiltBoard.hasSevenBySeven());

        //Remove row 6, now we have a 6x9 so == false
        for (int i = 0; i < quiltBoard.spaces.get(6).size(); i++) {
            quiltBoard.spaces.get(6).set(i, false);
        }
        Assert.assertFalse(quiltBoard.hasSevenBySeven());

        //Add it back in, should be back to == true
        for (int i = 0; i < quiltBoard.spaces.get(6).size(); i++) {
            quiltBoard.spaces.get(6).set(i, true);
        }
        Assert.assertTrue(quiltBoard.hasSevenBySeven());

        //Because we now want to remove column 6 instead, had to ensure that this is solely because of this removed column == false
        for (int i = 0; i < quiltBoard.spaces.size(); i++) {
            quiltBoard.spaces.get(i).set(6, false);
        }
        Assert.assertFalse(quiltBoard.hasSevenBySeven());


        //Final test, put it back to true and remove 1 space in the middle of the board
        for (int i = 0; i < quiltBoard.spaces.size(); i++) {
            quiltBoard.spaces.get(i).set(6, true);
        }
        Assert.assertTrue(quiltBoard.hasSevenBySeven());

        quiltBoard.spaces.get(4).set(4,false);
        Assert.assertFalse(quiltBoard.hasSevenBySeven());
    }


    @Test
    public void countEmptyTest(){
        QuiltBoard quiltBoard = new QuiltBoard();

        //Should be 9x9 = 81 empty spaces at the start
        Assert.assertEquals(81, quiltBoard.countEmpty());

        PatchFactory patchFactory = new PatchFactory();
        Patch testPatch = patchFactory.getPatches().get(0);
        quiltBoard.placePatch(testPatch, 0, 0);

        Assert.assertEquals(81-quiltBoard.patchToCoords(testPatch).size(), quiltBoard.countEmpty());

        //Now make it a 9x9 full true matrix == true
        for (int i = 0; i < quiltBoard.spaces.size(); i++){
            for (int j = 0; j < quiltBoard.spaces.get(i).size(); j++){
                quiltBoard.spaces.get(i).set(j, true);
            }
        }
        Assert.assertEquals(0, quiltBoard.countEmpty());
    }

    @Test
    public void patchToCoordsTest(){
        QuiltBoard quiltBoard = new QuiltBoard();
        PatchFactory patchFactory = new PatchFactory();
        Patch testPatch = patchFactory.getPatches().get(0);

        int testPatchTrues = 0;
        for (int i = 0; i < testPatch.spaces.size(); i++ ){
            for (int j = 0; j < testPatch.spaces.get(i).size(); j++){
                if (testPatch.spaces.get(i).get(j)){
                    testPatchTrues +=1;
                }
            }
        }
        Assert.assertEquals(testPatchTrues, quiltBoard.patchToCoords(testPatch).size());
    }
}
