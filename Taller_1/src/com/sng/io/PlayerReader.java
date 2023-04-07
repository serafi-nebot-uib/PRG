package com.sng.io;

import com.sng.data.Player;

import java.io.*;

/**
 * The PlayerReader class provides all the methods necessary for reading Player objects from a file using the sentinel
 * technique.
 */
public class PlayerReader implements Closeable {
    private static final Integer PLAYER_CHUNK_SIZE = 50;

    private final ObjectInputStream reader;

    /**
     * Creates a PlayerReader that reads the contents of the specified file.
     *
     * @param path file path to read from
     * @throws IOException if an I/O error occurs when reading from the file
     */
    public PlayerReader(String path) throws IOException {
        reader = new ObjectInputStream(new FileInputStream(path));
    }

    /**
     * Creates a PlayerReader that reads the contents of the specified file.
     *
     * @param file file object to read from
     * @throws IOException if an I/O error occurs when reading from the file
     */
    public PlayerReader(File file) throws IOException {
        this(file.getAbsolutePath());
    }

    /**
     * Resize array to specified size.
     *
     * @param src array to resize
     * @param size new array size
     * @return otherwise resized array to new size or, source array if size < 0 or source array is null
     */
    private static Player[] resizeArray(Player[] src, int size) {
        if (size < 0 || src == null) return src;
        Player[] dst = new Player[size];
        for (int i = 0; i < Math.min(src.length, dst.length); i++)
            dst[i] = src[i];
        return dst;
    }

    /**
     * Read next Player object from file.
     *
     * @return next Player object
     * @throws IOException if an I/O error occurs when reading from the file
     * @throws ClassNotFoundException if an object different from Player is found
     */
    public Player read() throws IOException, ClassNotFoundException {
        Player player = (Player) reader.readObject();
        if (player == null || player.isSentinel()) return null;
        return player;
    }

    /**
     * Read remaining Player objects from file.
     *
     * @return Player array containing all remaining Player objects
     * @throws IOException if an I/O error occurs when reading from the file
     * @throws ClassNotFoundException if an object different from Player is found
     */
    public Player[] readAll() throws IOException, ClassNotFoundException {
        Player[] players = new Player[PLAYER_CHUNK_SIZE];

        int i = 0;
        Player player = null;
        while ((player = read()) != null) {
            if (i >= players.length)
                players = resizeArray(players, players.length + PLAYER_CHUNK_SIZE);
            players[i++] = player;
        }

        return resizeArray(players, i);
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
