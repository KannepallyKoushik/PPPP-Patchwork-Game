package com.patchwork.app.backend;

import org.junit.Assert;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void constructorTest(){
        Player player = new Player("Test");
        Assert.assertEquals( "Test", player.name);
        Assert.assertEquals(5, player.nrButtons);
        Assert.assertEquals(0, player.quiltBoard.patches.size());
    }

    @Test
    public void addButtonsTest(){
        Player player = new Player("Test");
        player.addButtons(5);
        Assert.assertEquals(10, player.nrButtons);
    }

    @Test
    public void payButtonsTest(){
        Player player = new Player("Test");
        player.payButtons(5);
        Assert.assertEquals(0, player.nrButtons);
    }

    @Test
    public void placePatchTest(){

        Player player = new Player("Test");
        PatchFactory patchFactory = new PatchFactory();


        //Make 2 test patches
        Patch testPatch = patchFactory.getPatches().get(0);
        Patch testPatch2 = patchFactory.getPatches().get(1);

        //We can place the first, but not the second one due to the coordinates
        Assert.assertTrue(player.placePatch(testPatch, 0, 0));
        Assert.assertFalse(player.placePatch(testPatch2, 0, 0));

        //Assert we can not place the second one at different coordinate, due to cost
        Assert.assertFalse(player.placePatch(testPatch2, 0, 2));

        //Add buttons then we can place it
        player.addButtons(10);
        Assert.assertTrue(player.placePatch(testPatch2, 0, 2));

        //Assert total button amount is correct
        Assert.assertEquals(5+10-testPatch.buttonCost - testPatch2.buttonCost, player.nrButtons);

    }

    @Test
    public void addPatchButtons(){
        Player player = new Player("Test");
        PatchFactory patchFactory = new PatchFactory();

        //Make 2 test patches
        Patch testPatch = patchFactory.getPatches().get(0);
        Patch testPatch2 = patchFactory.getPatches().get(1);

        //Add 1 patch, and add buttons. Then see if final count = 5 + cost + score
        player.placePatch(testPatch, 0, 0);
        player.addPatchButtons();
        Assert.assertEquals(5-testPatch.buttonCost+testPatch.buttonScore, player.nrButtons);

        //Reset buttons, then add second patch to see if this also remains consistent with multiple patches
        player.nrButtons = 5;
        player.placePatch(testPatch2, 0, 2);
        player.addPatchButtons();
        Assert.assertEquals(5-testPatch2.buttonCost+testPatch.buttonScore + testPatch2.buttonScore, player.nrButtons);
    }


}
