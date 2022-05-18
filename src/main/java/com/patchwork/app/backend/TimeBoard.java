package com.patchwork.app.backend;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TimeBoard {

    public List<List<SpaceElement>> spaces;
    public List<Player> players;
    public Map<Player, Integer> playerPositions;

    public TimeBoard(List<Player> players) {
        this.spaces = new ArrayList<>();
        for (int i = 0; i < 52; i++) {  // TODO: populate board with actual contents
            this.spaces.add(new ArrayList<SpaceElement>());
        }
        this.players = players;
    }

    public enum SpaceElementType {
        BUTTON,
        PATCH,
        PLAYER
    }

    public class SpaceElement {

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
