package com.patchwork.app.backend.model;

import java.util.List;

public abstract class AbstractGameFactory {

    protected List<Player> players;
    protected TimeBoard timeBoard;
    protected PatchList patchList;

    public abstract Game createGame();

    public abstract List<Player> createPlayers();

    protected abstract TimeBoard createTimeBoard(List<Player> players);

    protected abstract PatchList createPatchList();

    public List<Player> getPlayers() {
        return players;
    }

    public TimeBoard getTimeBoard() {
        return timeBoard;
    }

    public PatchList getPatchList() {
        return patchList;
    }
}
