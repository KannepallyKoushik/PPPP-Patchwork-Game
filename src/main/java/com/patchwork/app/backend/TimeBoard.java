package com.patchwork.app.backend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TimeBoard {

    public List<List<SpaceElement>> spaces;
    public List<Player> players;
    public Map<Player, Integer> playerPositions;

    public TimeBoard(List<Player> players) {
        this.spaces = new ArrayList<>();
        List<Integer> buttonsIndicesList = Arrays.asList(new Integer[]{8,15, 23, 27, 32,35,41,49});
        List<Integer> specialPatchIndicesList = Arrays.asList(new Integer[]{20, 29, 37, 43, 48, 51});
        for (int i = 0; i < 52; i++) {  // TODO: populate board with actual contents
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
            return players.get(0);
        }
        else if(player1Position < player2Position){
            return players.get(1);
        }else{
            List<SpaceElement> s = spaces.get(getPlayerPosition(players.get(0)));
            SpaceElement se = s.get(s.size() - 1);
            return se.getPlayer();
        }
    }

    public int getPlayerPosition(Player player) {
        return playerPositions.get(player);
    }

    public void movePlayer(Player player, int position) {
        Integer currentPlayerPosition = playerPositions.get(player);
        Integer expectedPlayerPosition = currentPlayerPosition + position;

        // 1. Clearing the SpaceElement where the Player is from spaces list
        List<SpaceElement> space = spaces.get(currentPlayerPosition);
        for(SpaceElement se : space){
            if(player.equals(se.getPlayer())){
                space.remove(se);
            }
        }

        // 2. Updating new position of player in spaces list
        List<SpaceElement> newSpace = spaces.get(expectedPlayerPosition);
        newSpace.add(new SpaceElement(SpaceElementType.PLAYER, player));
        spaces.set(expectedPlayerPosition, newSpace);

        playerPositions.put(player, expectedPlayerPosition);
    }
}
