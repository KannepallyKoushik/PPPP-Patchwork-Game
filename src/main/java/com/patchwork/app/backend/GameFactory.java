package com.patchwork.app.backend;

import java.util.ArrayList;
import java.util.List;

public class GameFactory {

    private List<Player> players;
    private TimeBoard timeBoard;
    private PatchList patchList;
    private PatchListFactory patchListFactory = new PatchListFactory();

    public Game createGame() {
        players = createPlayers();
        timeBoard = createTimeBoard(players);
        patchList = createPatchList();

        return new Game(players, timeBoard, patchList);
    }

    private List<Player> createPlayers() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1"));
        players.add(new Player("Player 2"));
        return players;
    }

    private TimeBoard createTimeBoard(List<Player> players) {
        return new TimeBoard(players);
    }

    private PatchList createPatchList() {
        return patchListFactory.createPatchList();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public TimeBoard getTimeBoard() {
        return timeBoard;
    }

    public PatchList getPatchList() {
        return patchList;
    }

    public void setPatchListFactory(PatchListFactory patchListFactory) {
        this.patchListFactory = patchListFactory;
    }
}
