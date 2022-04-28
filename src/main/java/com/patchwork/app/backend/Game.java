package com.patchwork.app.backend;

import java.util.ArrayList;
import java.util.List;

public class Game {

    public List<Player> players;
    public TimeBoard timeBoard;
    public PatchList patchList;
    public Player specialTilePlayer;
    public GameResult result;

    public Game() {
        this.players = new ArrayList<>();
        this.players.add(new Player("Player 1"));
        this.players.add(new Player("Player 2"));
        this.timeBoard = new TimeBoard(this.players);
        this.patchList = new PatchList();
        this.specialTilePlayer = null;
        this.result = null;
    }

    public boolean isFinished() {
        return this.result == null;
    }

    public void placePatch(Player player, Patch patch, int x, int y) {
        // TODO: implement
    }

    public void movePastNextPlayer(Player player) {
        // TODO: implement
    }
}
