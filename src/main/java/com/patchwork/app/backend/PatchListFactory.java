package com.patchwork.app.backend;

public class PatchListFactory {

    public PatchList createPatchList() {
        PatchFactory pf = new PatchFactory();
        pf.createTimeBoardPatches();
        return new PatchList(pf.getPatches());
    }
}
