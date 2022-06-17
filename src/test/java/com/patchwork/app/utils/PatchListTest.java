package com.patchwork.app.utils;

import com.patchwork.app.adapters.PlayerType;
import com.patchwork.app.backend.Exceptions.GameException;
import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.PatchFactory;
import com.patchwork.app.backend.PatchList;
import com.patchwork.app.backend.PatchListFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class PatchListTest {

    private PatchList pl;


    @Before
    public void setUp() {
        pl = new PatchListFactory().createPatchList();
    }

    @Test
    public void getAvailablePatches() {
        List<Patch> fullPatchList = new PatchFactory().getPatches();

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
        for (int i = 0; i < 33; i++) {
            assertNotEquals(0, pl.getAvailablePatches().size());
            pl.removePatch(pl.getAvailablePatches().get(0));
        }

        assertEquals(0, pl.getAvailablePatches().size());
    }

    @Test
    public void removeUnavailablePatch() {
        Patch rm = pl.getAvailablePatches().get(0);
        Patch rm2 = pl.getAvailablePatches().get(1);
        pl.removePatch(rm2);
        // Patch 0 (assigned to rm) should not be selectable due to change in index
        // ensure that runtime error is raised when removing it

        assertThrows(
                RuntimeException.class,
                () -> {
                    pl.removePatch(rm);
                });
    }
}
