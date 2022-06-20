package com.patchwork.app.testutils;

import com.patchwork.app.backend.model.Patch;
import com.patchwork.app.backend.model.PatchList;
import com.patchwork.app.backend.model.PatchListFactory;

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
