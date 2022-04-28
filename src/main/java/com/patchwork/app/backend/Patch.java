package com.patchwork.app.backend;

import java.util.List;

public class Patch {

    public List<List<Boolean>> spaces;
    public int nrButtons;
    public int nrTimeTokens;

    public Patch(List<List<Boolean>> spaces, int nrButtons, int nrTimeTokens) {
        this.spaces = spaces;
        this.nrButtons = nrButtons;
        this.nrTimeTokens = nrTimeTokens;
    }

    public Patch rotateLeft() {
        // TODO: implement
        return null;
    }

    public Patch rotateRight() {
        // TODO: implement
        return null;
    }

    public Patch flipUp() {
        // TODO: implement
        return null;
    }

    public Patch flipSide() {
        // TODO: implement
        return null;
    }
}
