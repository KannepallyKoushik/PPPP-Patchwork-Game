package com.patchwork.app.backend;

import com.patchwork.app.backend.Exceptions.GameException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {

    public List<Player> players;
    public TimeBoard timeBoard;
    public PatchList patchList;
    public GameResult result;

    public Player specialTilePlayer;
    public Player firstFinishedPlayer;

    public Game(List<Player> players, TimeBoard timeBoard, PatchList patchList) {
        this.players = players;
        this.timeBoard = timeBoard;
        this.patchList = patchList;
        this.specialTilePlayer = null;
        this.result = null;
    }

    /**
     * Returns the opponent of a player.
     *
     * @param player The player to obtain the opponent of
     * @return The opponent of the provided player
     */
    public Player getOpponent(Player player) {
        for (Player p : players) {
            if (player != p) {
                return p;
            }
        }
        return null;
    }

    /**
     * Returns whether all players have reached the end of the timeboard.
     *
     * @return whether all players have reached the end of the timeboard.
     */
    private boolean gameShouldFinish() {
        for (Player player : players) {
            if (timeBoard.getPlayerPosition(player) != 51) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the score of a player.
     *
     * @param player The player to determine the score for.
     * @return The score of the provided player.
     */
    private int getPlayerScore(Player player) {
        int score = player.nrButtons;
        score -= player.quiltBoard.countEmpty() * 2;

        if (specialTilePlayer == player) {
            score += 7;
        }

        return score;
    }

    /**
     * Called when the game has finished. Creates a GameResult and ends the game.
     */
    private void finalizeGame() {
        Map<Player, Integer> playerScores = new HashMap<>();
        int highestScore = -1;
        Player highestScorePlayer = null;

        for (Player player : players) {
            // Determine and set score
            int score = getPlayerScore(player);
            playerScores.put(player, score);

            // Handle highest score calculation.
            if (score > highestScore) {
                highestScore = score;
                highestScorePlayer = player;
            } else if (score == highestScore) {
                // Only override highestScorePlayer if the current player finished earlier
                if (firstFinishedPlayer == player) {
                    highestScorePlayer = player;
                }
            }
        }

        result = new GameResult(playerScores, highestScorePlayer);
    }

    public boolean isFinished() {
        return result != null;
    }

    /**
     * Places a patch on the quiltboard of a player. Also checks if the special tile should be awarded afterwards.
     *
     * @param player The player whose QuiltBoard to place the patch on
     * @param patch  The patch to place
     * @param x      The horizontal coordinate to place the left side of the patch on
     * @param y      The vertical coordinate to place the right side of the patch on
     * @throws GameException When the patch cannot be placed at the specified location or the player cannot afford the
     *                       patch.
     */
    public void placePatch(Player player, Patch patch, int x, int y) throws GameException {
        // TODO: properly implement patch button awards.
        if (isFinished()) {
            throw new GameException("Game is already finished");
        }
        if (!player.quiltBoard.canPlace(patch, x, y)) {
            throw new GameException("Cannot place patch at the specified location");
        }
        if (patch.buttonCost > player.nrButtons) {
            throw new GameException("Player cannot afford this patch");
        }

        player.payButtons(patch.buttonCost);
        player.quiltBoard.placePatch(patch, x, y);
        patchList.removePatch(patch);

        int newPosition = Math.min(51, timeBoard.getPlayerPosition(player) + patch.timeTokenCost);
        timeBoard.movePlayer(player, newPosition);

        // Award special tile if obtained
        if (specialTilePlayer == null && player.quiltBoard.hasSevenBySeven()) {
            specialTilePlayer = player;
        }

        // Finalize the game if all players have reached the end of the time board.
        if (gameShouldFinish()) {
            finalizeGame();
        }
    }

    /**
     * Moves the current player past its opponent. Finishes the game if both players have reached the end of the time
     * board.
     *
     * @param player The player to move past its opponent.
     * @throws GameException If the game has already finished.
     */
    public void movePastNextPlayer(Player player) throws GameException {
        if (isFinished()) {
            throw new GameException("Game is already finished");
        }

        int currentPosition = timeBoard.getPlayerPosition(player);
        int newPosition = Math.min(51, timeBoard.getPlayerPosition(getOpponent(player)) + 1);

        if (currentPosition >= newPosition) {
            throw new GameException("Next player is behind the current player");
        }

        timeBoard.movePlayer(player, newPosition);
        player.addButtons(newPosition - currentPosition);

        // Set first finished player if it is the case.
        if (firstFinishedPlayer == null && newPosition == 51) {
            firstFinishedPlayer = player;
        }

        // Finalize the game if all players have reached the end of the time board.
        if (gameShouldFinish()) {
            finalizeGame();
        }
    }
}
