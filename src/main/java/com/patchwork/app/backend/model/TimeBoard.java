package com.patchwork.app.backend.model;

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
        arrangingTimeBoard(players);
        setting_players_and_playerPositions(players);
    }

    public void arrangingTimeBoard( List<Player> players ) {
        List<Integer> buttonsIndicesList = Arrays.asList(8,15, 23, 27, 32,35,41,49);
        List<Integer> specialPatchIndicesList = Arrays.asList(20, 29, 37, 43, 48, 51);
        for (int i = 0; i < NR_SPACES; i++) {
            if(i == 0){
                add_both_players_onTimeBoard(players);
            }
            else if(buttonsIndicesList.contains(i)){
                add_button_to_TimeBoard();
            }
            else if(specialPatchIndicesList.contains(i)){
                add_special_patch_to_TimeBoard();
            }
            else{
                this.spaces.add(new ArrayList<>());
            }
        }
    }

    public void setting_players_and_playerPositions(List<Player> players){
        this.players = players;
        playerPositions.put(players.get(0), 0);
        playerPositions.put(players.get(1), 0);
    }

    public void add_both_players_onTimeBoard(List<Player> players){
        ArrayList<SpaceElement> firstSpace = new ArrayList<>();
        firstSpace.add(new SpaceElement(SpaceElementType.PLAYER, players.get(0)));
        firstSpace.add(new SpaceElement(SpaceElementType.PLAYER,players.get(1)));
        this.spaces.add(firstSpace);
    }

    public void add_button_to_TimeBoard(){
        ArrayList<SpaceElement> space = new ArrayList<>();
        space.add(new SpaceElement(SpaceElementType.BUTTON, null));
        this.spaces.add(space);
    }

    public void add_special_patch_to_TimeBoard(){
        ArrayList<SpaceElement> space = new ArrayList<>();
        space.add(new SpaceElement(SpaceElementType.PATCH, null));
        this.spaces.add(space);
    }

    public enum SpaceElementType {
        BUTTON,
        PATCH,
        PLAYER
    }

    public static class SpaceElement {

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

        clearSpaceWherePlayerIsIn(player, currentPlayerPosition);
        updateNewPositionofPlayer(player, expectedPlayerPosition);
        List<SpaceElement> obtainedSpaceElements = NonPlayerSpaceElements(currentPlayerPosition,expectedPlayerPosition);
        removeSpecialPatches(currentPlayerPosition, expectedPlayerPosition);

        return obtainedSpaceElements;
    }

    public List<SpaceElement> NonPlayerSpaceElements(int currentPlayerPosition, int expectedPlayerPosition){
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

    public void clearSpaceWherePlayerIsIn(Player player, int currentPlayerPosition){
        //  Clearing the SpaceElement where the Player is from spaces list
        List<SpaceElement> space = spaces.get(currentPlayerPosition);
        List<SpaceElement> toRemove = new ArrayList<>();
        for(SpaceElement se : space){
            if(player.equals(se.getPlayer())){
                toRemove.add(se);
            }
        }
        space.removeAll(toRemove);
    }

    public void updateNewPositionofPlayer(Player player, int expectedPlayerPosition){
        //  Updating new position of player in spaces list
        List<SpaceElement> newSpace = spaces.get(expectedPlayerPosition);
        newSpace.add(new SpaceElement(SpaceElementType.PLAYER, player));
        spaces.set(expectedPlayerPosition, newSpace);

        playerPositions.put(player, expectedPlayerPosition);
    }

    public void removeSpecialPatches(int currentPlayerPosition, int expectedPlayerPosition) {
        List<SpaceElement> toRemove;

        for (int i = currentPlayerPosition + 1; i <= expectedPlayerPosition; i++) {
            toRemove = new ArrayList<>();
            for (SpaceElement se : spaces.get(i)) {
                if (se.type == SpaceElementType.PATCH) {
                    toRemove.add(se);
                }
            }
            spaces.get(i).removeAll(toRemove);
        }
    }
}



