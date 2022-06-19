package com.patchwork.app.backend.model;

import com.patchwork.app.utils.GridModifier;

import java.util.List;
import java.util.Objects;

public class Patch {

    // This matrix should be interpreted as a list of columns so spaces[x][y] with x=0,y=0 is the bottom left
    public List<List<Boolean>> spaces;
    public int buttonCost;
    public int timeTokenCost;
    public int buttonScore;

    public Patch(List<List<Boolean>> spaces, int buttonCost, int timeTokenCost, int buttonScore) {
        this.spaces = spaces;
        this.buttonCost = buttonCost;
        this.timeTokenCost = timeTokenCost;
        this.buttonScore = buttonScore;
    }

    public Patch rotateLeft() {
        GridModifier gm = new GridModifier();
        return new Patch(gm.rotateLeft(spaces), buttonCost, timeTokenCost, buttonScore);
    }

    public Patch rotateRight() {
        GridModifier gm = new GridModifier();
        return new Patch(gm.rotateRight(spaces), buttonCost, timeTokenCost, buttonScore);
    }

    public Patch mirrorUp() {
        GridModifier gm = new GridModifier();
        return new Patch(gm.mirrorUp(spaces), buttonCost, timeTokenCost, buttonScore);
    }

    public Patch mirrorSide() {
        GridModifier gm = new GridModifier();
        return new Patch(gm.mirrorSide(spaces), buttonCost, timeTokenCost, buttonScore);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patch patch = (Patch) o;
        return buttonCost == patch.buttonCost
                && timeTokenCost == patch.timeTokenCost
                && buttonScore == patch.buttonScore
                && Objects.equals(spaces, patch.spaces);
    }
}
