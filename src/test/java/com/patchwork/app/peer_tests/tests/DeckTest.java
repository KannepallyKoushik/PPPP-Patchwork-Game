package com.patchwork.app.peer_tests.tests;

import org.junit.Assert;
import org.junit.Test;
import com.patchwork.app.peer_tests.adapters.Deck;
import com.patchwork.app.peer_tests.adapters.PurchasablePatch;
import com.patchwork.app.peer_tests.adapters.PatchToken;

public class DeckTest {
    private void buildDeck(PurchasablePatch[] patches, Deck deck) {
        for (PurchasablePatch patch : patches) {
            deck.addPatch(patch);
        }
    }

    @Test
    public void testRemovePatch() {
        PurchasablePatch[] patches = {
                new PurchasablePatch("10 11", 0, 0, 0),
                new PurchasablePatch("11 01", 0, 0, 0),
                new PurchasablePatch("01 11", 0, 0, 0)
        };
        // by providing false we create an empty deck, useful for testing
        Deck deck = new Deck(false);

        for (PurchasablePatch patch : patches) {
            deck.addPatch(patch);
            Assert.assertTrue(deck.getPatches().contains(patch));
        }

        for (PurchasablePatch patch : patches) {
            deck.choosePatch(0);
            Assert.assertFalse(deck.getPatches().contains(patch));
        }
    }

    @Test
    public void testPatchSelection() {
        PurchasablePatch[] patches = {
                new PurchasablePatch("00 00", 0, 0, 0),
                new PurchasablePatch("00 01", 1, 0, 0),
                new PurchasablePatch("00 11", 2, 0, 0),
                new PurchasablePatch("00 10", 3, 0, 0),
                new PurchasablePatch("00 11", 4, 0, 0),
                new PurchasablePatch("01 00", 5, 0, 0),
                new PurchasablePatch("01 01", 6, 0, 0),
                new PurchasablePatch("01 10", 7, 0, 0),
                new PurchasablePatch("01 11", 8, 0, 0),
                new PurchasablePatch("10 00", 9, 0, 0)
        };
        PurchasablePatch[] patchesCopy = new PurchasablePatch[10];
        System.arraycopy(patches, 0, patchesCopy, 0, 10);

        Deck deck = new Deck(false);
        PatchToken token = deck.getPatchToken();
        PurchasablePatch[] selectedPatches;
        buildDeck(patchesCopy, deck);

        token.setPosition(0);
        selectedPatches = deck.getPatchSelection();

        Assert.assertEquals(patches[0].getButtonCost(), selectedPatches[0].getButtonCost());
        Assert.assertEquals(patches[1].getButtonCost(), selectedPatches[1].getButtonCost());
        Assert.assertEquals(patches[2].getButtonCost(), selectedPatches[2].getButtonCost());

        token.setPosition(5);
        selectedPatches = deck.getPatchSelection();

        Assert.assertEquals(patches[5].getButtonCost(), selectedPatches[0].getButtonCost());
        Assert.assertEquals(patches[6].getButtonCost(), selectedPatches[1].getButtonCost());
        Assert.assertEquals(patches[7].getButtonCost(), selectedPatches[2].getButtonCost());

        token.setPosition(1);
        deck.choosePatch(0);

        token.setPosition(0);
        selectedPatches = deck.getPatchSelection();
        Assert.assertEquals(patches[0].getButtonCost(), selectedPatches[0].getButtonCost());
        Assert.assertEquals(patches[2].getButtonCost(), selectedPatches[1].getButtonCost());
        Assert.assertEquals(patches[3].getButtonCost(), selectedPatches[2].getButtonCost());

        token.setPosition(8);
        selectedPatches = deck.getPatchSelection();

        Assert.assertEquals(patches[9].getButtonCost(), selectedPatches[0].getButtonCost());
        Assert.assertEquals(patches[0].getButtonCost(), selectedPatches[1].getButtonCost());
        Assert.assertEquals(patches[2].getButtonCost(), selectedPatches[2].getButtonCost());
    }

    @Test
    public void testMovePatchToken() {
        PurchasablePatch[] patches = {
                new PurchasablePatch("00 00", 0, 0, 0),
                new PurchasablePatch("00 01", 1, 0, 0),
                new PurchasablePatch("10 00", 9, 0, 0),
                new PurchasablePatch("10 00", 9, 0, 0),
                new PurchasablePatch("10 00", 9, 0, 0)
        };

        Deck deck = new Deck(false);
        PurchasablePatch[] selection;

        buildDeck(patches, deck);

        Assert.assertEquals(0, deck.getPatchToken().getPosition());

        selection = deck.getPatchSelection();
        Assert.assertEquals(selection[0], patches[0]);
        Assert.assertEquals(selection[1], patches[1]);
        Assert.assertEquals(selection[2], patches[2]);

        Assert.assertEquals(patches[0], deck.choosePatch(0));
        Assert.assertEquals(0, deck.getPatchToken().getPosition());

        selection = deck.getPatchSelection();
        Assert.assertEquals(selection[0], patches[1]);
        Assert.assertEquals(selection[1], patches[2]);
        Assert.assertEquals(selection[2], patches[3]);

        Assert.assertEquals(patches[2], deck.choosePatch(1));
        Assert.assertEquals(1, deck.getPatchToken().getPosition());

        selection = deck.getPatchSelection();
        Assert.assertEquals(patches[3], selection[0]);
        Assert.assertEquals(patches[4], selection[1]);
        Assert.assertEquals(patches[1], selection[2]);

        Assert.assertEquals(patches[3], deck.choosePatch(0));
        Assert.assertEquals(1, deck.getPatchToken().getPosition());

        selection = deck.getPatchSelection();
        Assert.assertEquals(selection[0], patches[4]);
        Assert.assertEquals(selection[1], patches[1]);

        Assert.assertEquals(patches[1], deck.choosePatch(1));
        Assert.assertEquals(0, deck.getPatchToken().getPosition());

        selection = deck.getPatchSelection();
        Assert.assertEquals(selection[0], patches[4]);

        Assert.assertEquals(patches[4], deck.choosePatch(0));
        Assert.assertEquals(0, deck.getPatchToken().getPosition());
    }
}
