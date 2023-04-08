package com.sng;

import com.sng.data.Player;
import com.sng.data.Team;
import com.sng.io.PlayerReader;
import com.sng.io.PlayerWriter;
import com.sng.util.PlayerStatistics;
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

    private static final Random random = new Random();
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private static final RandomPlayerGenerator playerGenerator = new RandomPlayerGenerator();
    private static final BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        /*
        we must feed PlayerGenerator a player name pool from which it will randomly select names and
        the maximum number of trophies allowed for each player
         */
        playerGenerator.setPlayerNames(fetchPlayerNameList());
        playerGenerator.setMaxTrophies(PLAYER_MAX_TROPHIES);

        boolean end = false;
        while (!end) {
            try {
                System.out.println("\n\nMENU\n");
                System.out.println("\t[1] Create a random number of players with random values");
                System.out.println("\t[2] Show global players");
                System.out.println("\t[3] Show global players statistics");
                System.out.println("\t[4] Separate global players into teams");
                System.out.println("\t[5] Show global players separated into teams");
                System.out.println("\t[6] Show players statistics from all files");
                System.out.println("\t[7] Exit");
                System.out.println();

                // ask user's for choice until a valid option is selected
                Integer choice = null;
                while (choice == null) {
                    try {
                        System.out.print("> ");
                        choice = Integer.parseInt(stdin.readLine());
                        // make sure selected choice is a valid one
                        if (choice < 1 || choice > 7) {
                            System.out.printf("ERROR: unknown option: %d\n", choice);
                            choice = null;
                        }
                    } catch (NumberFormatException ignored) {
                        // input is not a number, display error to user
                        System.out.println("ERROR: input must be a number");
                    } catch (IOException e) {
                        logger.log(Level.SEVERE, String.format("failed to read from stdin: %s", e.getMessage()));
                    }
                }

                switch (choice) {
                    case 1 -> insertRandomPlayers();
                    case 2 -> showStoredPlayers();
                    case 3 -> showPlayerStatistics(PLAYER_FILE_PATH);
                    case 4 -> separatePlayersByTeam();
                    case 5 -> showStoredPlayersByTeam();
                    case 6 -> showPlayerStatistics(
                            PLAYER_FILE_PATH,
                            PLAYER_BASTOS_FILE_PATH,
                            PLAYER_COPES_FILE_PATH,
                            PLAYER_ESPASES_FILE_PATH,
                            PLAYER_OROS_FILE_PATH);
                    case 7 -> end = true;
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, String.format("critical error: %s", e.getMessage()));
            }
        }
    }

    /**
     * Fetch player names stored in file: <code>LlistaNoms.txt</code>
     *
     * @return String array containing all names in the file
     */
    private static String[] fetchPlayerNameList() {
        BufferedReader reader = null;
        String[] names = null;
        try {
            reader = new BufferedReader(new FileReader(PLAYER_NAME_LIST_FILE_PATH));
            // create a temporary array with maximum possible size so that we can store all names
            String[] tmpNames = new String[PLAYER_NAME_LIST_FILE_MAX_COUNT];
            String name = null;
            int i = 0;
            while ((name = reader.readLine()) != null && i < tmpNames.length)
                tmpNames[i++] = name;
            // initialize final name array with the actual name count
            names = new String[i];
            // copy the contents of the temporary array into the final array
            for (i = 0; i < names.length; i++)
                names[i] = tmpNames[i];
        } catch (FileNotFoundException e) {
            logger.log(Level.SEVERE, String.format("file not found: %s", PLAYER_NAME_LIST_FILE_PATH));
        } catch (IOException e) {
            logger.log(Level.SEVERE, String.format("failed to read from file: %s", PLAYER_NAME_LIST_FILE_PATH));
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, String.format("failed to close file: %s", PLAYER_NAME_LIST_FILE_PATH));
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

        // create a temporary array with maximum possible size
        // because the number of players with the specified team is unknown
        Player[] tmp = new Player[players.length];
        int count = 0;
        for (Player player : players)
            if (player != null && player.getTeam() == team)
                tmp[count++] = player;

        // create final player array with correct size and copy contents from the temporary array
        Player[] result = new Player[count];
        for (int i = 0; i < count; i++)
            result[i] = tmp[i];

        return result;
    }

    /**
     * Write Player object array to file.
     *
     * @param path    file path to write to
     * @param players Player object array to write
     */
    private static void writePlayersToFile(String path, Player[] players) {
        if (path == null || players == null) {
            logger.log(Level.SEVERE, "writePlayersToFile(): file path and player array cannot be null");
            return;
        }

        PlayerWriter writer = null;
        try {
            writer = new PlayerWriter(path);
            writer.write(players);
        } catch (IOException e) {
            logger.log(Level.SEVERE, String.format("failed to write to file: %s", path));
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "failed to close player writer");
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
            logger.log(Level.SEVERE, String.format("failed to read from file: %s", path));
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "invalid of corrupt Player object found");
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "failed to close player reader");
            }
        }

        return players;
    }

    /**
     * Create a random number of Player objects with randomly generate values and insert them into the global players
     * file.
     */
    private static void insertRandomPlayers() {
        writePlayersToFile(PLAYER_FILE_PATH, playerGenerator.generatePlayers(random.nextInt(PLAYER_MAX + 1)));
    }

    /**
     * Show all Players in Player object array
     *
     * @param players Player object array
     */
    private static void showPlayers(Player[] players) {
        if (players != null)
            for (Player player : players)
                System.out.println(player);
    }

    /**
     * Load all players from global file and show them.
     */
    private static void showStoredPlayers() {
        System.out.printf("Players from file: \"%s\"", PLAYER_FILE_PATH);
        showPlayers(readPlayersFromFile(PLAYER_FILE_PATH));
        System.out.println();
    }

    /**
     * Load all players from separate team files and show them separately.
     */
    private static void showStoredPlayersByTeam() {
        Player[] players = readPlayersFromFile(PLAYER_FILE_PATH);
        System.out.println("Team: BASTOS");
        showPlayers(playersWithTeam(players, Team.BASTOS));
        System.out.println();
        System.out.println("Team: COPES");
        showPlayers(playersWithTeam(players, Team.COPES));
        System.out.println();
        System.out.println("Team: ESPASES");
        showPlayers(playersWithTeam(players, Team.ESPASES));
        System.out.println();
        System.out.println("Team: OROS");
        showPlayers(playersWithTeam(players, Team.OROS));
        System.out.println();
    }

    /**
     * Load players from global file and separate players by team into their corresponding file.
     */
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

    /**
     * Load players from a given file and show their statistics.
     *
     * @param path Player object file path
     */
    private static void showPlayerStatistics(String path) {
        Player[] players = readPlayersFromFile(path);

        if (players == null) {
            logger.log(Level.SEVERE, "failed to load players from file");
            return;
        }

        System.out.println("PLAYER STATISTICS");
        System.out.printf("File name: \"%s\"\n", path);
        System.out.printf("Number of players: %d\n", PlayerStatistics.numberOfPlayers(players));
        System.out.printf("Sum of all trophies: %d\n", PlayerStatistics.trophySum(players));
        System.out.printf("Trophy arithmetic mean per player: %f\n", PlayerStatistics.trophyMean(players));
        System.out.printf("Trophy standard deviation: %f\n", PlayerStatistics.trophyStandardDeviation(players));
        System.out.println("---- Player podium ----");
        Player[] bestPlayers = PlayerStatistics.bestPlayers(players);
        System.out.print("\t1st: ");
        System.out.println(bestPlayers[0]);
        System.out.print("\t2nd: ");
        System.out.println(bestPlayers[1]);
        System.out.print("\t3rd: ");
        System.out.println(bestPlayers[2]);
        System.out.println();
    }

    /**
     * Load players from a given list of files and show their statistics.
     *
     * @param paths list of Player object file paths
     */
    private static void showPlayerStatistics(String... paths) {
        for (String path : paths)
            showPlayerStatistics(path);
    }
}