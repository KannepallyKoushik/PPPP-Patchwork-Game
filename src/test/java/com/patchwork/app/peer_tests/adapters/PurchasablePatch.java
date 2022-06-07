package com.patchwork.app.peer_tests.adapters;

import com.patchwork.app.backend.Patch;

import java.util.ArrayList;
import java.util.List;

public class PurchasablePatch extends Patch implements IPatch {

    public PurchasablePatch(String patchString, int buttonCost, int timeTokenCost, int buttonScore) {
        super( transform(patchString), buttonCost, timeTokenCost, buttonScore);
    }

    private static List<List<Boolean>> transform(String patchString){
        List<List<Boolean>> spaces = new ArrayList<>();
        //Split on empty space " "
        String[] strings = patchString.split(" ");
        //Convert each element of array to true/false
        int maxY = strings.length;
        int maxX = strings[0].length();
        //Loop over array
        for (String string : strings) {
            List<Boolean> rowSpaces = new ArrayList<>();
            for (int x = 0; x < maxX; x++) {
                char c = string.charAt(x);
                rowSpaces.add(c == ('1'));
            }
            spaces.add(rowSpaces);
        }
        return spaces;
    }

    @Override
    public boolean[][] getShape() {
        boolean[][] shape = new boolean[this.spaces.size()][this.spaces.get(0).size()];
        for (int i = 0; i < this.spaces.size(); i++) {
            for(int j = 0; j < this.spaces.get(i).size(); j++){
                shape[i][j] = spaces.get(i).get(j);
            }
        }
        return shape;
    }

    @Override
    public void rotateClockwise() {
        Patch rotatedPatch = rotateRight();
        this.spaces = rotatedPatch.spaces;
    }

    @Override
    public void rotateCounterClockwise() {
        Patch rotatedPatch = rotateLeft();
        this.spaces = rotatedPatch.spaces;
    }

    public void flipOverHorizontal(){
        Patch rotatedPatch = mirrorSide();
        this.spaces = rotatedPatch.spaces;
    }

    @Override
    public void flipOverVertical() {
        Patch rotatedPatch = mirrorUp();
        this.spaces = rotatedPatch.spaces;
    }
}
