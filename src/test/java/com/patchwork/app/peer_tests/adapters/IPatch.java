package com.patchwork.app.peer_tests.adapters;

import com.patchwork.app.backend.Patch;

public interface IPatch {
    boolean[][] getShape();
    void rotateClockwise();
    void rotateCounterClockwise();
    void flipOverHorizontal();
    void flipOverVertical();
}
