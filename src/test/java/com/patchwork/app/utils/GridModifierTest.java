package com.patchwork.app.utils;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridModifierTest {

    public static List<List<Boolean>> makeGrid() {
        List<List<Boolean>> grid = new ArrayList<>();
        for (int x = 0; x < 5; x++) {
            List<Boolean> col = new ArrayList<>();
            for (int y = 0; y < 3; y++) {
                col.add(x <= 1 && y > 0);
            }
            grid.add(col);
        }
        return grid;
    }

    @Test
    public void singleRotateGridLeft() {
        List<List<Boolean>> grid = makeGrid();
        List<List<Boolean>> rotatedGrid = new GridModifier().rotateLeft(grid);

        assertEquals(
                Arrays.asList(
                        Arrays.asList(true, true, false, false, false),
                        Arrays.asList(true, true, false, false, false),
                        Arrays.asList(false, false, false, false, false)
                ),
                rotatedGrid
        );
    }

    @Test
    public void fullRotateGridLeft() {
        List<List<Boolean>> grid = makeGrid();

        for (int i = 0; i < 4; i++) {
            grid = new GridModifier().rotateLeft(grid);
        }

        assertEquals(makeGrid(), grid);
    }

    @Test
    public void singleRotateGridRight() {
        List<List<Boolean>> grid = makeGrid();
        List<List<Boolean>> rotatedGrid = new GridModifier().rotateRight(grid);

        assertEquals(
                Arrays.asList(
                        Arrays.asList(false, false, false, false, false),
                        Arrays.asList(false, false, false, true, true),
                        Arrays.asList(false, false, false, true, true)
                ),
                rotatedGrid
        );
    }

    @Test
    public void fullRotateGridRight() {
        List<List<Boolean>> grid = makeGrid();

        for (int i = 0; i < 4; i++) {
            grid = new GridModifier().rotateRight(grid);
        }

        assertEquals(makeGrid(), grid);
    }

    @Test
    public void singleMirrorGridUp() {
        List<List<Boolean>> grid = makeGrid();
        List<List<Boolean>> rotatedGrid = new GridModifier().mirrorUp(grid);

        assertEquals(
                Arrays.asList(
                        Arrays.asList(true, true, false),
                        Arrays.asList(true, true, false),
                        Arrays.asList(false, false, false),
                        Arrays.asList(false, false, false),
                        Arrays.asList(false, false, false)
                ),
                rotatedGrid
        );
    }

    @Test
    public void fullMirrorGridUp() {
        List<List<Boolean>> grid = makeGrid();

        for (int i = 0; i < 2; i++) {
            grid = new GridModifier().mirrorUp(grid);
        }

        assertEquals(makeGrid(), grid);
    }

    @Test
    public void singleMirrorGridSide() {
        List<List<Boolean>> grid = makeGrid();
        List<List<Boolean>> rotatedGrid = new GridModifier().mirrorSide(grid);

        assertEquals(
                Arrays.asList(
                        Arrays.asList(false, false, false),
                        Arrays.asList(false, false, false),
                        Arrays.asList(false, false, false),
                        Arrays.asList(false, true, true),
                        Arrays.asList(false, true, true)
                ),
                rotatedGrid
        );
    }

    @Test
    public void fullMirrorGridSide() {
        List<List<Boolean>> grid = makeGrid();

        for (int i = 0; i < 2; i++) {
            grid = new GridModifier().mirrorSide(grid);
        }

        assertEquals(makeGrid(), grid);
    }
}
