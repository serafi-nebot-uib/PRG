package com.sng;

import com.sng.data.Player;
import com.sng.data.Team;
import com.sng.io.PlayerReader;
import com.sng.io.PlayerWriter;
import com.sng.util.RandomPlayerGenerator;

import java.io.*;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    private static final int PLAYER_MAX_TROPHIES = 100;
    private static final int PLAYER_MAX = 200;
    private static final String PLAYER_NAME_LIST_FILE_PATH = "LlistaNoms.txt";
    private static final int PLAYER_NAME_LIST_FILE_MAX_COUNT = 510;
    private static final String PLAYER_FILE_PATH = "players.dat";
    private static final String PLAYER_BASTOS_FILE_PATH = "BASTOS.dat";
    private static final String PLAYER_COPES_FILE_PATH = "COPES.dat";
    private static final String PLAYER_ESPASES_FILE_PATH = "ESPASES.dat";
    private static final String PLAYER_OROS_FILE_PATH = "OROS.dat";

    private static final Logger log = Logger.getLogger(Main.class.getName());
    private static final RandomPlayerGenerator playerGenerator = new RandomPlayerGenerator();
    private static final BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        playerGenerator.setPlayerNames(fetchPlayerNameList());
        playerGenerator.setMaxTrophies(PLAYER_MAX_TROPHIES);

        insertRandomPlayers();
        showStoredPlayers();
        separatePlayersByTeam();
    }

    private static String[] fetchPlayerNameList() {
        BufferedReader reader = null;
        String[] names = null;
        try {
            reader = new BufferedReader(new FileReader(PLAYER_NAME_LIST_FILE_PATH));
            String[] nameList = new String[PLAYER_NAME_LIST_FILE_MAX_COUNT];
            String name = null;
            int i = 0;
            while ((name = reader.readLine()) != null && i < nameList.length)
                nameList[i++] = name;
            names = new String[i];
            for (i = 0; i < names.length; i++)
                names[i] = nameList[i];
        } catch (FileNotFoundException e) {
            log.log(Level.SEVERE, String.format("file not found: %s", PLAYER_NAME_LIST_FILE_PATH));
        } catch (IOException e) {
            log.log(Level.SEVERE, String.format("failed to read from file: %s", PLAYER_NAME_LIST_FILE_PATH));
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                log.log(Level.SEVERE, String.format("failed to close file: %s", PLAYER_NAME_LIST_FILE_PATH));
            }
        }
        return names;
    }

    /**
     * Get Players with matching team.
     *
     * @param players Player object array to check for
     * @param team    Team to match
     * @return a Player object array with a subset of <code>players</code> where their teams equals the specified
     * <code>team</code>
     */
    private static Player[] playersWithTeam(Player[] players, Team team) {
        if (players == null || team == null) return null;

        Player[] tmp = new Player[players.length];
        int count = 0;
        for (Player player : players)
            if (player != null && player.getTeam() == team)
                tmp[count++] = player;

        Player[] result = new Player[count];
        for (int i = 0; i < count; i++)
            result[i] = tmp[i];

        return result;
    }

    /**
     * Write Player object array to file.
     *
     * @param path file path to write to
     * @param players Player object array to write
     */
    private static void writePlayersToFile(String path, Player[] players) {
        if (path == null || players == null) {
            log.log(Level.SEVERE, "writePlayersToFile(): file path and player array cannot be null");
            return;
        }

        PlayerWriter writer = null;
        try {
            writer = new PlayerWriter(path);
            writer.write(players);
        } catch (IOException e) {
            log.log(Level.SEVERE, String.format("failed to write to file: %s", path));
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                log.log(Level.SEVERE, "failed to close player writer");
            }
        }
    }

    /**
     * Read Player objects from file.
     *
     * @param path file path to read from
     * @return Player object array with all Players
     */
    private static Player[] readPlayersFromFile(String path) {
        File file = new File(path);

        if (!file.exists()) {
            System.out.printf("ERROR: file \"%s\" does not exist; you must generate players first%n", path);
            return null;
        }

        PlayerReader reader = null;
        Player[] players = null;
        try {
            reader = new PlayerReader(file);
            players = reader.readAll();
        } catch (IOException e) {
            log.log(Level.SEVERE, String.format("failed to read from file: %s", path));
        } catch (ClassNotFoundException e) {
            log.log(Level.SEVERE, "invalid of corrupt Player object found");
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                log.log(Level.SEVERE, "failed to close player reader");
            }
        }

        return players;
    }

    private static void insertRandomPlayers() {
        System.out.println("How many players would you like to generate?");
        Integer playerCount = null;
        while (playerCount == null) {
            try {
                System.out.print("> ");
                playerCount = Integer.parseInt(stdin.readLine());
                if (playerCount < 1 || playerCount > 200) {
                    System.out.println("ERROR: player count must be in the range [1,200]");
                    playerCount = null;
                }
            } catch (NumberFormatException ignored) {
                System.out.println("ERROR: input must be a number!");
            } catch (IOException e) {
                log.log(Level.SEVERE, String.format("filed to read from stdin: %s", e.getMessage()));
            }
        }
        writePlayersToFile(PLAYER_FILE_PATH, playerGenerator.generatePlayers(playerCount));
    }

    private static void showStoredPlayers() {
        Player[] players = readPlayersFromFile(PLAYER_FILE_PATH);
        if (players != null)
            for (Player player : players)
                System.out.println(player);
    }

    private static void separatePlayersByTeam() {
        Player[] players = readPlayersFromFile(PLAYER_FILE_PATH);

        Player[] bastos = playersWithTeam(players, Team.BASTOS);
        writePlayersToFile(PLAYER_BASTOS_FILE_PATH, bastos);

        Player[] copes = playersWithTeam(players, Team.COPES);
        writePlayersToFile(PLAYER_COPES_FILE_PATH, copes);

        Player[] espases = playersWithTeam(players, Team.ESPASES);
        writePlayersToFile(PLAYER_ESPASES_FILE_PATH, espases);

        Player[] oros = playersWithTeam(players, Team.OROS);
        writePlayersToFile(PLAYER_OROS_FILE_PATH, oros);
    }
}