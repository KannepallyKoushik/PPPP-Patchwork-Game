package com.patchwork.app.backend;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class PatchList {

    private final ArrayList<Patch> patches;
    private int neutralTokenPosition;

    public PatchList(ArrayList<Patch> patches) {
        this.patches = patches;
        this.neutralTokenPosition = 0;
    }

    public List<Patch> getAvailablePatches() {
        // Returns up to 3 available patches based on the current neutral token position
        if (patches.size() == 0) {
            return new ArrayList<>();
        }

        List<Patch> extendedPatches = new ArrayList<>();
        extendedPatches.addAll(patches);
        extendedPatches.addAll(patches);

        List<Patch> result = extendedPatches.subList(
                neutralTokenPosition, Math.min(neutralTokenPosition + 3, extendedPatches.size() - 1)
        );

        // Remove duplicates
        return new ArrayList<>(new LinkedHashSet<>(result));
    }

    public void removePatch(Patch patch) {
        if (!patches.contains(patch)) {
            //This case is the special patch, so dont do anything
            return;
        }
        if (!getAvailablePatches().contains(patch)) {
            throw new RuntimeException("Patch is not present in patch list");
        }
        int patchIndex = patches.indexOf(patch);
        patches.remove(patchIndex);
        neutralTokenPosition = patchIndex;
    }
}
