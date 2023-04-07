package com.sng.io;

import com.sng.data.Player;

import java.io.*;

/**
 * The PlayerWriter class provides all the methods necessary for writing Player objects to a file using the sentinel
 * technique.
 */
public class PlayerWriter implements Closeable {
    private final ObjectOutputStream writer;

    /**
     * Creates a PlayerWriter that writes Player objects to the specified file.
     *
     * @param path file path to write to
     * @throws IOException if an I/O error occurs when writing to the file
     */
    public PlayerWriter(String path) throws IOException {
        writer = new ObjectOutputStream(new FileOutputStream(path));
    }

    /**
     * Creates a PlayerWriter that writes Player objects to the specified file.
     *
     * @param file file to write to
     * @throws IOException if an I/O error occurs when writing to the file
     */
    public PlayerWriter(File file) throws IOException {
        this(file.getAbsolutePath());
    }

    /**
     * Write Player object to file.
     *
     * @param player Player object to write
     * @throws IOException if an I/O error occurs when writing to the file
     */
    public void write(Player player) throws IOException {
        if (player == null) return;
        this.writer.writeObject(player);
    }

    /**
     * Write Player array to file.
     *
     * @param players Player array to write
     * @throws IOException if an I/O error occurs when writing to the file
     */
    public void write(Player[] players) throws IOException {
        if (players == null) return;
        for (Player player : players) write(player);
    }

    @Override
    public void close() throws IOException {
        this.writer.writeObject(Player.getSentinel());
        this.writer.close();
    }
}
