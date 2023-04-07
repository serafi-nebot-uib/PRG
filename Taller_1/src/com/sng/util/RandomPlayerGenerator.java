package com.sng.util;

import com.sng.data.Player;
import com.sng.data.Team;

import java.util.Random;

/**
 * The RandomPlayerGenerator provides all the necessary methods to generate Player objects with random values.
 *
 * <p>
 *     <b>Note:</b> a player name list must be provided through <code>setPlayerNames(playerNames)</code> method and
 *     player names will be generated using that list. Otherwise all player names will be empty strings.
 * </p>
 */
public class RandomPlayerGenerator {
    private static final Random random = new Random();

    private String[] playerNames;
    private int maxTrophies;

    public String[] getPlayerNames() {
        return playerNames;
    }

    public void setPlayerNames(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public int getMaxTrophies() {
        return maxTrophies;
    }

    public void setMaxTrophies(int maxTrophies) {
        this.maxTrophies = maxTrophies;
    }

    /**
     * Create a RandomPlayerGenerator which generates Player objects with random values.
     *
     * <p>
     *     <b>Note:</b> if this constructor is used, a player names list must be provided through the
     *     <code>setPlayerNames(playerNames)</code> method.
     * </p>
     */
    public RandomPlayerGenerator() {
        this.playerNames = new String[]{""};
    }

    /**
     * Create a RandomPlayerGenerator which generates Player objects with random values.
     *
     * @param playerNames player names list which will be used to generate player names
     * @throws IllegalArgumentException if playerNames is null
     */
    public RandomPlayerGenerator(String[] playerNames) throws IllegalArgumentException {
        if (playerNames == null) throw new IllegalArgumentException("player names list must not be null");
        this.playerNames = playerNames;
    }

    /**
     * Generate Player object with random values.
     *
     * @return Player object with random values
     */
    public Player generatePlayer() {
        Team[] teams = Team.values();
        Player player = new Player();
        player.setName(this.playerNames[random.nextInt(this.playerNames.length)]);

        // make sure the randomly selected Team is not NONE
        Team team = null;
        do {
            team = teams[random.nextInt(teams.length)];
        } while (team == Team.NONE);
        player.setTeam(team);

        player.setTrophies(random.nextInt(this.maxTrophies));
        return player;
    }

    /**
     * Check if a name already exists inside an array of Players.
     *
     * @param players Player object array to check for names
     * @param name name to match
     * @return <code>true</code> if no Player exists with the specified <code>name</code>, <code>false</code> otherwise
     */
    private boolean isNameUnique(Player[] players, String name) {
        if (players == null || name == null) return false;
        for (Player player : players)
            if (player != null && name.equals(player.getName()))
                return false;
        return true;
    }

    /**
     * Generate a number of Players with random values.
     *
     * @param count number of Player objects to generate
     * @return randomly generated Player object array
     */
    public Player[] generatePlayers(int count) {
        Player[] players = new Player[count];

        Player player = null;
        for (int i = 0; i < count; i++) {
            // make sure the generated player does not have an already chosen name
            do {
                player = generatePlayer();
            } while (!isNameUnique(players, player.getName()));
            players[i] = generatePlayer();
        }

        return players;
    }
}
