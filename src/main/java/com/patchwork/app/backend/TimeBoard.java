package com.patchwork.app.backend;

import java.util.*;

public class TimeBoard {

    public int NR_SPACES;

    public List<List<SpaceElement>> spaces;
    public List<Player> players;
    public Map<Player, Integer> playerPositions = new HashMap<>();

    public TimeBoard(List<Player> players) {
        this(players, 52);
    }

    public TimeBoard(List<Player> players, int size) {
        this.NR_SPACES = size;
        this.spaces = new ArrayList<>();
        List<Integer> buttonsIndicesList = Arrays.asList(8,15, 23, 27, 32,35,41,49);
        List<Integer> specialPatchIndicesList = Arrays.asList(20, 29, 37, 43, 48, 51);
        for (int i = 0; i < NR_SPACES; i++) {
            if(i == 0){
                ArrayList<SpaceElement> firstSpace = new ArrayList<>();
                firstSpace.add(new SpaceElement(SpaceElementType.PLAYER, players.get(0)));
                firstSpace.add(new SpaceElement(SpaceElementType.PLAYER,players.get(1)));
                this.spaces.add(firstSpace);
            }
            else if(buttonsIndicesList.contains(i)){
                ArrayList<SpaceElement> space = new ArrayList<>();
                space.add(new SpaceElement(SpaceElementType.BUTTON, null));
                this.spaces.add(space);
            }
            else if(specialPatchIndicesList.contains(i)){
                ArrayList<SpaceElement> space = new ArrayList<>();
                space.add(new SpaceElement(SpaceElementType.PATCH, null));
                this.spaces.add(space);
            }
            else{
                this.spaces.add(new ArrayList<SpaceElement>());
            }
        }
        this.players = players;

        playerPositions.put(players.get(0), 0);
        playerPositions.put(players.get(1), 0);
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

        public Player getPlayer(){
            return this.player;
        }
    }

    public Player getCurrentPlayer() {
        Integer player1Position = playerPositions.get(players.get(0));
        Integer player2Position = playerPositions.get(players.get(1));
        if(player1Position > player2Position ){
            return players.get(1);
        }
        else if(player1Position < player2Position){
            return players.get(0);
        }else{
            List<SpaceElement> s = spaces.get(getPlayerPosition(players.get(0)));
            SpaceElement se = s.get(s.size() - 1);
            return se.getPlayer();
        }
    }

    public int getPlayerPosition(Player player) {
        return playerPositions.get(player);
    }

    public List<SpaceElement> movePlayer(Player player, int position) {
        int currentPlayerPosition = playerPositions.get(player);
        int expectedPlayerPosition = position;

        // 1. Clearing the SpaceElement where the Player is from spaces list
        List<SpaceElement> space = spaces.get(currentPlayerPosition);
        List<SpaceElement> toRemove = new ArrayList<>();
        for(SpaceElement se : space){
            if(player.equals(se.getPlayer())){
                toRemove.add(se);
            }
        }

        space.removeAll(toRemove);


        // 2. Updating new position of player in spaces list
        List<SpaceElement> newSpace = spaces.get(expectedPlayerPosition);
        newSpace.add(new SpaceElement(SpaceElementType.PLAYER, player));
        spaces.set(expectedPlayerPosition, newSpace);

        playerPositions.put(player, expectedPlayerPosition);

        // Determine and return the non-player SpaceElements that were obtained
        List<SpaceElement> obtainedSpaceElements = new ArrayList<>();
        for (int i = currentPlayerPosition + 1; i <= expectedPlayerPosition; i++) {
            for (SpaceElement se : spaces.get(i)) {
                if (se.type != SpaceElementType.PLAYER) {
                    obtainedSpaceElements.add(se);
                }
            }
        }
        return obtainedSpaceElements;
    }
}
