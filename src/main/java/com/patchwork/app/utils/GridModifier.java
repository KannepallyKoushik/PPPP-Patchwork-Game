package com.patchwork.app.utils;

import java.util.ArrayList;
import java.util.List;

public class GridModifier {

    public List<List<Boolean>> rotateLeft(List<List<Boolean>> grid) {
        return new GridLeftRotateModifier(grid).modify();
    }

    public List<List<Boolean>> rotateRight(List<List<Boolean>> grid) {
        return new GridRightRotateModifier(grid).modify();
    }

    public List<List<Boolean>> mirrorUp(List<List<Boolean>> grid) {
        return new GridMirrorUpModifier(grid).modify();
    }

    public List<List<Boolean>> mirrorSide(List<List<Boolean>> grid) {
        return new GridMirrorSidewaysModifier(grid).modify();
    }

    private abstract class AbstractGridModifier {

        List<List<Boolean>> grid;

        public AbstractGridModifier(List<List<Boolean>> grid) {
            this.grid = grid;
        }

        // Returns the value of the boolean that should it at i,j in the rotated grid
        public abstract Boolean getElement(int i, int j, int maxI, int maxJ);

        // Returns the number of rows that the rotated grid should have
        public abstract int getNrRows();

        // Returns the number of columns that the rotated grid should have
        public abstract int getNrCols();

        public List<List<Boolean>> modify() {
            List<List<Boolean>> newGrid = new ArrayList<>();
            int maxI = getNrCols() - 1;
            int maxJ = getNrRows() - 1;

            for (int i = 0; i <= maxI; i++) {
                List<Boolean> row = new ArrayList<>();
                for (int j = 0; j <= maxJ; j++) {
                    row.add(getElement(i, j, maxI, maxJ));
                }
                newGrid.add(row);
            }

            return newGrid;
        }
    }

    private class GridLeftRotateModifier extends AbstractGridModifier {

        public GridLeftRotateModifier(List<List<Boolean>> grid) {
            super(grid);
        }

        @Override
        public Boolean getElement(int i, int j, int maxI, int maxJ) {
            return grid.get(j).get(maxI - i);
        }

        @Override
        public int getNrRows() {
            return grid.size();
        }

        @Override
        public int getNrCols() {
            return grid.get(0).size();
        }
    }

    private class GridRightRotateModifier extends AbstractGridModifier {

        public GridRightRotateModifier(List<List<Boolean>> grid) {
            super(grid);
        }

        @Override
        public Boolean getElement(int i, int j, int maxI, int maxJ) {
            return grid.get(maxJ - j).get(i);
        }

        @Override
        public int getNrRows() {
            return grid.size();
        }

        @Override
        public int getNrCols() {
            return grid.get(0).size();
        }
    }

    private class GridMirrorUpModifier extends AbstractGridModifier {

        public GridMirrorUpModifier(List<List<Boolean>> grid) {
            super(grid);
        }

        @Override
        public Boolean getElement(int i, int j, int maxI, int maxJ) {
            return grid.get(i).get(maxJ - j);
        }

        @Override
        public int getNrRows() {
            return grid.get(0).size();
        }

        @Override
        public int getNrCols() {
            return grid.size();
        }
    }

    private class GridMirrorSidewaysModifier extends AbstractGridModifier {

        public GridMirrorSidewaysModifier(List<List<Boolean>> grid) {
            super(grid);
        }

        @Override
        public Boolean getElement(int i, int j, int maxI, int maxJ) {
            return grid.get(maxI - i).get(j);
        }

        @Override
        public int getNrRows() {
            return grid.get(0).size();
        }

        @Override
        public int getNrCols() {
            return grid.size();
        }
    }
}