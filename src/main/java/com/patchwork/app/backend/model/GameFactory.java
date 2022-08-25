package com.patchwork.app.backend.model;

import java.util.ArrayList;
import java.util.List;

public class GameFactory extends AbstractGameFactory {

    private PatchListFactory patchListFactory = new PatchListFactory();

    public Game createGame() {
        players = createPlayers();
        timeBoard = createTimeBoard(players);
        patchList = createPatchList();

        return new Game(players, timeBoard, patchList);
    }

    @Override
    public List<Player> createPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1", 0));
        players.add(new Player("Player 2", 1));
        return players;
    }

    @Override
    protected TimeBoard createTimeBoard(List<Player> players) {
        return new TimeBoard(players);
    }

    @Override
    protected PatchList createPatchList() {
        return patchListFactory.createPatchList();
    }

    public void setPatchListFactory(PatchListFactory patchListFactory) {
        this.patchListFactory = patchListFactory;
    }
}
