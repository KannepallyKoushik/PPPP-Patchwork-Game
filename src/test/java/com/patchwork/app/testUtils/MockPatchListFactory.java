package com.patchwork.app.testUtils;

import com.patchwork.app.backend.Patch;
import com.patchwork.app.backend.PatchList;
import com.patchwork.app.backend.PatchListFactory;

import java.util.ArrayList;
import java.util.List;

public class MockPatchListFactory extends PatchListFactory {

    private final ArrayList<Patch> patches;

    public MockPatchListFactory(List<Patch> patches) {
        this.patches = new ArrayList<>(patches);
    }

    @Override
    public PatchList createPatchList() {
        return new PatchList(patches);
    }
}
