package com.patchwork.app.peer_tests.adapters;

public interface IPatch {
    boolean[][] getShape();
    void rotateClockwise();
    void rotateCounterClockwise();
    void flipOverHorizontal();
    void flipOverVertical();
}
