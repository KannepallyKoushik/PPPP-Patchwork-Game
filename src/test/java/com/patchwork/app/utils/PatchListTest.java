package com.patchwork.app.utils;

import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.PatchFactory;
import com.patchwork.app.backend.PatchList;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PatchListTest {

    @Test
    public void getAvailablePatches() {
        List<Patch> fullPatchList = new PatchFactory().getPatches();
        PatchList pl = new PatchList();

        assertEquals(fullPatchList.subList(0, 3), pl.getAvailablePatches());

        pl.removePatch(pl.getAvailablePatches().get(2));
        assertEquals(fullPatchList.subList(3, 6), pl.getAvailablePatches());

        for (int i = 0; i < 10; i++) {
            pl.removePatch(pl.getAvailablePatches().get(2));
        }

        // Should have reached exactly the start of the list again
        assertEquals(
                Arrays.asList(fullPatchList.get(0), fullPatchList.get(1), fullPatchList.get(3)),
                pl.getAvailablePatches()
        );

        // Enter the second round
        pl.removePatch(pl.getAvailablePatches().get(0));
        assertEquals(
                Arrays.asList(fullPatchList.get(1), fullPatchList.get(3), fullPatchList.get(4)),
                pl.getAvailablePatches()
        );
    }

    @Test
    public void removeAllPatches() {
        PatchList pl = new PatchList();

        for (int i = 0; i < 33; i++) {
            assertNotEquals(0, pl.getAvailablePatches().size());
            pl.removePatch(pl.getAvailablePatches().get(0));
        }

        assertEquals(0, pl.getAvailablePatches().size());
    }

    @Test(expected = RuntimeException.class)
    public void removeUnavailablePatch() {
        PatchList pl = new PatchList();

        Patch rm = pl.getAvailablePatches().get(0);
        pl.removePatch(pl.getAvailablePatches().get(1));

        // Patch 0 (assigned to rm) should not be selectable, ensure that runtime error is raised when removing it
        pl.removePatch(rm);
    }
}
