package external.to_implement;
/*
import org.junit.Test;
import patchwork.core.model.patches.IPatch;
import patchwork.core.model.patches.PurchasablePatch;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PatchTest {
    @Test
    public void matrixManipulationTest() {
        // tests matrix manipulation
        String patchString = "1011 0101 0110 1001";
        boolean[][] reference = new boolean[][]{
                new boolean[]{true, false, true, true},
                new boolean[]{false, true, false, true},
                new boolean[]{false, true, true, false},
                new boolean[]{true, false, false, true}
        };
        IPatch patch = new PurchasablePatch(patchString, 0, 0, 0);
        assertTrue(Arrays.deepEquals(reference, patch.getShape()));

        boolean[][] refClockwise = new boolean[][]{
                new boolean[]{true, false, false, true},
                new boolean[]{false, true, true, false},
                new boolean[]{false, true, false, true},
                new boolean[]{true, false, true, true}
        };
        patch.rotateClockwise();
        assertTrue(Arrays.deepEquals(refClockwise, patch.getShape()));
        assertFalse(Arrays.deepEquals(reference, patch.getShape()));

        // back to starting position
        patch.rotateCounterClockwise();
        assertTrue(Arrays.deepEquals(reference, patch.getShape()));

        boolean[][] refCounterClockwise = new boolean[][]{
                new boolean[]{true, true, false, true},
                new boolean[]{true, false, true, false},
                new boolean[]{false, true, true, false},
                new boolean[]{true, false, false, true}
        };
        patch.rotateCounterClockwise();
        assertTrue(Arrays.deepEquals(refCounterClockwise, patch.getShape()));
        assertFalse(Arrays.deepEquals(reference, patch.getShape()));

        // back to starting position
        patch.rotateClockwise();
        assertTrue(Arrays.deepEquals(reference, patch.getShape()));

        boolean[][] refFlipHorizontal = new boolean[][]{
                new boolean[]{true, false, false, true},
                new boolean[]{false, true, true, false},
                new boolean[]{false, true, false, true},
                new boolean[]{true, false, true, true}
        };
        patch.flipOverHorizontal();
        assertTrue(Arrays.deepEquals(refFlipHorizontal, patch.getShape()));
        assertFalse(Arrays.deepEquals(reference, patch.getShape()));

        // back to starting position
        patch.flipOverHorizontal();
        assertTrue(Arrays.deepEquals(reference, patch.getShape()));

        boolean[][] refFlipVertical = new boolean[][]{
                new boolean[]{true, true, false, true},
                new boolean[]{true, false, true, false},
                new boolean[]{false, true, true, false},
                new boolean[]{true, false, false, true}
        };
        patch.flipOverVertical();
        assertTrue(Arrays.deepEquals(refFlipVertical, patch.getShape()));
        assertFalse(Arrays.deepEquals(reference, patch.getShape()));
    }
}
*/