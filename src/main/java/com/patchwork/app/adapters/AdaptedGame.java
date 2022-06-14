package com.patchwork.app.adapters;

import com.patchwork.app.backend.*;

import java.util.ArrayList;

public class AdaptedGame {
    private int playerID = 0;
    private GameFactory gameFactory = new GameFactory();

    public Game game = gameFactory.createGame();
    public TimeBoard timeBoard = this.game.timeBoard;
    public Deck deck = new Deck(true);
    public Bank bank = new Bank();

    public AdaptedGame() {
        this.game.players = new ArrayList<>();
    }

    public Player getPlayer(int index) {
        try {
            return this.game.players.get(index);
        } catch (Exception e) {
            return null;
        }
    }

    public void addPlayer(PlayerType playerType, String name) {
        if(this.game.players.size() >= 2) {
            throw new IllegalStateException();
        }

        this.game.players.add(new Player(name, playerID++));
    }

    public Player getOtherPlayer(int playerIndex) {
        Player thisPlayer = this.game.players.get(playerIndex);
        return this.game.getOpponent(thisPlayer);
    }

    public int getPlayerCount() {
        return this.game.players.size();
    }
}
