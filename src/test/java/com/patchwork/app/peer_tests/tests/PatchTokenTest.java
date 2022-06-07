package com.patchwork.app.peer_tests.tests;

import org.junit.Assert;
import org.junit.Test;
import com.patchwork.app.peer_tests.adapters.PatchToken;

public class PatchTokenTest {

    @Test(expected = IllegalArgumentException.class)
    public void testSetAndGetPosition() {
        PatchToken token = new PatchToken();
        Assert.assertEquals(0, token.getPosition());

        token.setPosition(10);
        Assert.assertEquals(10, token.getPosition());

        token.setPosition(15);
        Assert.assertEquals(15, token.getPosition());

        token.setPosition(0);
        Assert.assertEquals(0, token.getPosition());

        token.setPosition(-1);
        token.setPosition(-15);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMovingToken() {
        PatchToken token = new PatchToken();
        Assert.assertEquals(0, token.getPosition());

        token.move(10);
        Assert.assertEquals(10, token.getPosition());

        token.move(15);
        Assert.assertEquals(25, token.getPosition());

        token.move(0);
        Assert.assertEquals(25, token.getPosition());

        token.move(-1);
        token.move(-15);
    }
}
