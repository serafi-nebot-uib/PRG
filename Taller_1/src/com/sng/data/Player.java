package com.sng.data;

import java.io.Serializable;

/**
 * The Player class contains all the attributes which define a Player.
 *
 * <p>
 * This class also has a sentinel object, and a companion method, which are used for serialization using the sentinel
 * technique.
 * </p>
 */
public class Player implements Serializable {
    private static final Player sentinel = new Player("", Team.NONE, -1);

    private String name;
    private Team team;
    private int trophies;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getTrophies() {
        return trophies;
    }

    public void setTrophies(int trophies) {
        this.trophies = trophies;
    }

    public static Player getSentinel() {
        return sentinel;
    }

    public Player(String name, Team team, int trophies) {
        this.name = name;
        this.team = team;
        this.trophies = trophies;
    }

    public Player() {
    }

    /**
     * Check if Player is sentinel.
     *
     * @return true if Player is sentinel, false otherwise
     */
    public boolean isSentinel() {
        return getTrophies() == sentinel.getTrophies();
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", team=" + team +
                ", trophies=" + trophies +
                '}';
    }
}
