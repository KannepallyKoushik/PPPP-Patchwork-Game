package com.patchwork.app.utils;

import com.patchwork.app.backend.model.Patch;
import com.patchwork.app.backend.model.PatchFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PatchFactoryTest {

    @Test
    public void createTimeBoardPatches() {
        PatchFactory pf = new PatchFactory();
        pf.createTimeBoardPatches();
        List<Patch> patches = pf.getPatches();

        Assert.assertEquals(33, patches.size());
    }
}
