package com.patchwork.app.peer_tests.adapters;


import com.patchwork.app.backend.Patch;

import java.util.Collections;

public class LeatherPatch extends Patch implements IPatch {

    public LeatherPatch() {
        super(Collections.singletonList(Collections.singletonList(true)), 0,0,0);
    }



    //These are not used but are from interface
    @Override
    public boolean[][] getShape() {
        return new boolean[0][];
    }

    @Override
    public void rotateClockwise() {

    }

    @Override
    public void rotateCounterClockwise() {

    }

    @Override
    public void flipOverHorizontal() {

    }

    @Override
    public void flipOverVertical() {

    }

}
