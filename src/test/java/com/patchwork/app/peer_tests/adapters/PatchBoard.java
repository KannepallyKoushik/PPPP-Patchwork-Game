package com.patchwork.app.peer_tests.adapters;

import com.patchwork.app.backend.Exceptions.GameException;
import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.QuiltBoard;

import java.util.Arrays;

public class PatchBoard extends QuiltBoard {

    public PatchBoard(int i, int j) {
        super();
        this.spaces = new boolean[i][j];
        for (int r= 0; r < i; r++){
            for (int c = 0; c < j; c++ ){
                spaces[r][c] = false;
            }
        }
    }


    public boolean isPlaceable(int y, int x, IPatch patch) {
        boolean placeable = false;
        try {
            placeable = canPlace((Patch) patch, x, y);
        } catch (GameException e){
            System.out.println(e.getMessage());
        }
        return placeable;
    }

    public boolean placePatch(int y, int x, IPatch patch) {
        try {
            placePatch((Patch) patch, x, y);
            return true;
        } catch (GameException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public int getEmptyTileCount() {
        return countEmpty();
    }

    public boolean[][] getBoard() {
        return spaces;
    }
}
