package com.sng.util;

import com.sng.data.Player;

/**
 * The PlayerStatistics class provides all the necessary methods to calculate player statistics.
 */
public class PlayerStatistics {
    /**
     * Calculate number of Player objects in array.
     *
     * @param players Player object array
     * @return number of Players in array or 0 if <code>player</code> is null
     */
    public static int numberOfPlayers(Player[] players) {
        return players == null ? 0 : players.length;
    }

    /**
     * Calculate the total number of trophies in a Player object array.
     *
     * @param players Player object array
     * @return total number of trophies
     */
    public static int trophySum(Player[] players) {
        if (players == null) return 0;
        int count = 0;
        for (Player player : players)
            count += player.getTrophies();
        return count;
    }

    /**
     * Calculate the average number of trophies per player.
     *
     * @param players Player object array
     * @return average number of trophies per player
     */
    public static float trophyMean(Player[] players) {
        return (float) trophySum(players) / numberOfPlayers(players);
    }

    /**
     * Calculate the best 3 players from a Player object array.
     *
     * <p>
     * The resulting array will contain the 3 best players in descending order. That is, player at index 0 is the
     * best player, player at index 1 is the second-best player and, player at index 2 is the third-best player.
     * </p>
     *
     * @param players Player object array
     * @return best 3 players
     */
    public static Player[] bestPlayers(Player[] players) {
        if (players == null || players.length < 3) return players;
        Player[] best = new Player[3];
        for (Player player : players) {
            // if a better player is found, cascade inferior players downwards
            if (best[0] == null || best[0].getTrophies() < player.getTrophies()) {
                best[2] = best[1];
                best[1] = best[0];
                best[0] = player;
            } else if (best[1] == null || best[1].getTrophies() < player.getTrophies()) {
                best[2] = best[1];
                best[1] = player;
            } else if (best[2] == null || best[2].getTrophies() < player.getTrophies()) {
                best[2] = player;
            }
        }
        return best;
    }

    /**
     * Calculate the standard deviation for trophies from a Player object array.
     *
     * @param players Player object array
     * @return standard deviation of trophies
     */
    public static double trophyStandardDeviation(Player[] players) {
        double mean = trophyMean(players);
        double sum = 0;
        for (Player player : players)
            sum += Math.pow(player.getTrophies() - mean, 2);
        return Math.sqrt(sum / numberOfPlayers(players));
    }
}
