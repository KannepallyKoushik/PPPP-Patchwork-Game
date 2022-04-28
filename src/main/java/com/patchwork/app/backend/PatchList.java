package com.patchwork.app.backend;

import java.util.List;

public class PatchList {

    public List<Patch> patches;
    public int neutralTokenPosition;

    public PatchList() {
        this.patches = makeDefaultPatchCollection();
        this.neutralTokenPosition = 0;
    }

    public List<Patch> getAvailablePatches() {
        return null; // TODO: implement
    }

    public void removePatch(Patch patch) {
        // TODO: implement
    }

    public static List<Patch> makeDefaultPatchCollection() {
        // TODO: implement
        return null;
    }
}
