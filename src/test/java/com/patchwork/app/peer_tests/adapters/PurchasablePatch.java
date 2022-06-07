package com.patchwork.app.peer_tests.adapters;

import com.patchwork.app.backend.Patch;

import java.util.ArrayList;
import java.util.List;

public class PurchasablePatch extends Patch implements IPatch {

    public PurchasablePatch(String patchString, int buttonCost, int timeTokenCost, int buttonScore) {
        super( stringToList(patchString), buttonCost, timeTokenCost, buttonScore);
    }

    //Adapt their patch string into our List<List>> Boolean
    private static List<List<Boolean>> stringToList(String patchString){
        List<List<Boolean>> spaces = new ArrayList<>();
        //Split on empty space " "
        String[] strings = patchString.split(" ");
        //Convert each element of array to true/false
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



    //Since our method returns a new Patch, as opposed to changing the old patch in place, have to set spaces again
    //This will be done in all 4 methods
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
