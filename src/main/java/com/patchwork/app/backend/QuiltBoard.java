package com.patchwork.app.backend;

import java.util.ArrayList;
import java.util.List;

public class QuiltBoard {

    public List<List<Boolean>> spaces;
    public List<Patch> patches;

    public QuiltBoard() {
        this.spaces = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ArrayList<Boolean> row = new ArrayList<>();
            for (int j = 0; j < 9; j++) {
                row.add(false);
            }
            this.spaces.add(row);
        }
        this.patches = new ArrayList<>();
    }
}
