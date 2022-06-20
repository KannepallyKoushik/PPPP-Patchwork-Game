package com.patchwork.app.backend.model;

import java.util.Map;

public class GameResult {

    public Map<Player, Integer> playerScores;
    public Player winner;

    public GameResult(Map<Player, Integer> playerScores, Player winner) {
        this.playerScores = playerScores;
        this.winner = winner;
    }
}
