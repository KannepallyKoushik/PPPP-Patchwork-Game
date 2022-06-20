package com.patchwork.app.backend.model;


import com.patchwork.app.backend.exceptions.GameException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuiltBoard {

    public static final int DIM_X = 9;
    public static final int DIM_Y = 9;

    public boolean[][] spaces;
    public List<Patch> patches;


    //Constructor
    public QuiltBoard() {
        this.spaces = new boolean[9][9];
        for (int i = 0; i < DIM_X; i++) {
            for (int j = 0; j < DIM_Y; j++) {
                this.spaces[i][j] = false;
            }
        }
        this.patches = new ArrayList<>();
    }


    /*
    Helper method to avoid duplicate code when transforming the patch to a list of coordinates
    @param patch    The patch which you want to place
    @return         List of tuples which contain the (x,y) coordinates of the spaces of the patch
     */
    public List<List<Integer>> patchToCoords(Patch patch) {
        //Make a list of all the coordinates of the patch
        List<List<Integer>> patchCoordinates = new ArrayList<>();
        //get the coordinates of the spaces of the patch
        for (int i = 0; i < patch.spaces.size(); i++) {
            for (int j = 0; j < patch.spaces.get(i).size(); j++) {
                if (patch.spaces.get(i).get(j)) {
                    patchCoordinates.add(Arrays.asList(j, i));
                }
            }
        }
        return patchCoordinates;
    }


    /*
    Determine if you can place a patch starting at the specified coordinate
    @param patch    The patch which you want to place
    @param x        The left most coordinate of the matrix space which you want to place
    @param y        The top most coordinate of the matrix space which you want to place
    @return         True if patch can be placed, false otherwise
     */
    public boolean canPlace(Patch patch, int x, int y) throws GameException {
        List<List<Integer>> patchCoordinates = patchToCoords(patch);
        //Now check if all these coordinates are available, starting from (x,y)
        if (x < 0 || y < 0 || x >= DIM_X || y >= DIM_Y){
            throw new GameException("Can not place in specified location");
        }
        for (List<Integer> patchCoordinate : patchCoordinates) {
            int xCoord = patchCoordinate.get(0);
            int yCoord = patchCoordinate.get(1);
            if (spaces[y + yCoord][x + xCoord]) {
                return false;
            }
        }
        return true;
    }

    /*
    Actually place the patch after checking if it can be placed
    @param patch    The patch which you want to place
    @param x        The left most coordinate of the matrix space which you want to place
    @param y        The top most coordinate of the matrix space which you want to place
     */
    public void placePatch(Patch patch, int x, int y) throws GameException {
        //Determine if patch can be placed
        if (canPlace(patch, x, y)) {
            //Make a list of all the coordinates of the patch
            List<List<Integer>> patchCoordinates = patchToCoords(patch);

            for (List<Integer> patchCoordinate : patchCoordinates) {
                int xCoord = patchCoordinate.get(0);
                int yCoord = patchCoordinate.get(1);
                spaces[y + yCoord][x + xCoord] = true;
            }

            patches.add(patch);
        } else {
            throw new GameException("Tried to place patch when it can not be placed!");
        }

    }

    /*
    Method to determine if the quiltboard has a 7x7 full field
    @return     True if quiltboard contains a 7x7 full field, false otherwise
     */
    public boolean hasSevenBySeven() {
        int consecutiveCounter = 1;
        for (int i = 1; i < spaces.length; i++) {
            //If at any point a 7x7 matrix is full, return true, as it might get reset if last 2 rows are empty
            //Count only y here because for y to increment x has to >=7
            if (consecutiveCounter >= 7) return true;
            int xCount = determineSevenHorizontalTrues(i);
            //If there are more than or equal 7 consecutive horizontal, check if these also correspond vertically
            if (xCount >= 7) {
                int yCount = determineSevenVerticalTrues(i);
                if (yCount >= 7) consecutiveCounter +=1;
                else consecutiveCounter = 1;
            }
        }
        return false;
    }

    public int determineSevenHorizontalTrues(int i){
        int xCount = 1;
        //Loop to determine if there are 7 consecutive trues horizontal
        for (int j = 1; j < spaces[i].length; j++) {
            if (spaces[i][j] == spaces[i][j - 1] && spaces[i][j]) {
                xCount += 1;
            } else if (xCount < 7) {
                //Reset counter, need 7 consecutive trues
                xCount = 1;
            }
        }
        return xCount;
    }

    public int determineSevenVerticalTrues(int i){
        int yCount = 0;
        for (int j = 0; j < spaces[i].length; j++) {
            if (spaces[i][j] == spaces[i - 1][j] && spaces[i][j]) {
                yCount += 1;
            } else if (yCount < 7) {
                yCount = 1;
            }
        }
        return yCount;
    }

    /**
     * Returns the number of buttons that should be rewarded when the owner of this QuiltBoard gets button income.
     * @return Number of buttons which should be awarded.
     */
    public int getNrRewardButtons() {
        int nrButtons = 0;

        for (Patch patch : patches) {
            nrButtons += patch.buttonScore;
        }

        return nrButtons;
    }

    public int countEmpty(){
        int emptySpaces = 0;
        for (boolean[] space : spaces) {
            for (int j = 0; j < space.length; j++) {
                if (!space[j]) {
                    emptySpaces += 1;
                }
            }
        }
        return emptySpaces;
    }

    public static int fixPatchX(int x, Patch patch) {
        x = Math.max(0, x);
        x = Math.min(QuiltBoard.DIM_X - patch.getWidth(), x);
        return x;
    }

    public static int fixPatchY(int y, Patch patch) {
        y = Math.max(0, y);
        y = Math.min(QuiltBoard.DIM_Y - patch.getHeight(), y);
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuiltBoard that = (QuiltBoard) o;
        return Arrays.deepEquals(spaces, that.spaces);
    }
}
