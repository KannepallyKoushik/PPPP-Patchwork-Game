package com.patchwork.app.backend;

import java.util.Map;

public class GameResult {

    public Map<Player, Integer> playerScores;

    public GameResult(Map<Player, Integer> playerScores) {
        this.playerScores = playerScores;
    }
}
