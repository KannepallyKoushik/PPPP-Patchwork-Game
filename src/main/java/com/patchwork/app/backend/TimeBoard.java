package com.patchwork.app.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimeBoard {

    private List<List<SpaceElement>> spaces;
    private List<Player> players;
    private Map<Player, Integer> playerPositions;

    public TimeBoard(List<Player> players) {
        this.spaces = new ArrayList<>();
        for (int i = 0; i < 52; i++) {  // TODO: populate board with actual contents
            this.spaces.add(new ArrayList<SpaceElement>());
        }
        this.players = players;
    }

    private enum SpaceElementType {
        BUTTON,
        PATCH,
        PLAYER
    }

    private class SpaceElement {

        public SpaceElementType type;
        public Player player;

        public SpaceElement(SpaceElementType type, Player player) {
            this.type = type;
            this.player = player;
        }
    }

    public Player getCurrentPlayer() {
        return null;  // TODO: implement
    }

    public int getPlayerPosition(Player player) {
        return 0;  // TODO: implement
    }

    public void movePlayer(Player player, int position) {
        // TODO: implement
    }
}
