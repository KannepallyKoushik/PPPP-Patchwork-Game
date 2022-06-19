package com.patchwork.app.adapters;

import com.patchwork.app.backend.model.Patch;
import com.patchwork.app.backend.model.PatchList;

import java.util.ArrayList;
import java.util.List;

public class Deck extends PatchList {
    private final PatchToken patchToken = new PatchToken();

    public Deck(boolean init) {
        super(new ArrayList<>());
    }

    public void addPatch(PurchasablePatch patch) {
        this.patches.add(patch);
    }

    public List<Patch> getPatches() {
        return this.patches;
    }

    public int relativeIndex(int index) {
        if(this.patches.size() == 0) return 0;

        return (this.patchToken.getPosition() + index) % this.patches.size();
    }

    public IPatch choosePatch(int index) {
        Patch patch = this.patches.get(this.relativeIndex(index));

        this.removePatch(patch);
        this.patchToken.setPosition(this.relativeIndex(index));

        return (IPatch) patch;
    }

    public PatchToken getPatchToken() {
        return this.patchToken;
    }

    public PurchasablePatch[] getPatchSelection() {
        PurchasablePatch[] patches = new PurchasablePatch[3];

        for(int i = 0; i < 3; i++) {
            patches[i] = (PurchasablePatch) this.patches.get(this.relativeIndex(i));
        }

        return patches;
    }
}
