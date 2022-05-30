package com.patchwork.app.backend;

import com.patchwork.app.backend.Exceptions.GameException;

public class Player {

    public String name;
    public QuiltBoard quiltBoard;
    public int nrButtons;

    public Player(String name) {
        this.name = name;
        this.quiltBoard = new QuiltBoard();
        this.nrButtons = 5;
    }

    /*
   Method to add buttons corresponding to amount of spaces moved
   @param nrOfSpaces       number of spaces the player moved, which corresponds to buttons gained
   */
    public void addButtons(int nrOfSpaces) {
        nrButtons += nrOfSpaces;
    }

    /*
    Method to reduce button count, when buying patches for example (left it separate because might be used for other things)
    @param nrOfButtons     number of buttons shown on the patch, which corresponds to how much the player has to pay
    */
    public void payButtons(int nrOfButtons) {
        nrButtons -= nrOfButtons;
    }

    /*
    Actually place the patch after checking if it can be placed
    @param patch    The patch which you want to place
    @param x        The left most coordinate of the matrix space which you want to place
    @param y        The top most coordinate of the matrix space which you want to place
    @return         True if the patch has been placed, false otherwise (either cant place or no buttons)
    */
    public boolean placePatch(Patch patch, int x, int y) throws GameException {
        if (quiltBoard.canPlace(patch, x, y) && patch.buttonCost <= nrButtons) {
            quiltBoard.placePatch(patch, x, y);
            payButtons(patch.buttonCost);
            return true;
        }

        return false;
    }

    /*
    Method to add buttons corresponding to the numbers on the patch
     */
    public void addPatchButtons() {
        for (Patch patch : this.quiltBoard.patches) {
            this.nrButtons += patch.buttonScore;
        }
    }




}
