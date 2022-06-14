package com.patchwork.app.adapters;

public interface IPatch {
    boolean[][] getShape();
    void rotateClockwise();
    void rotateCounterClockwise();
    void flipOverHorizontal();
    void flipOverVertical();
}
