package com.sng;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;

public class P09 {
    private static final String FILE_PATH = "películas.dat";

    private static void readFromFile() {
        PeliculaObjetoFicherosLectura reader = null;
        try {
            reader = new PeliculaObjetoFicherosLectura(FILE_PATH);
            Film film = null;
            while ((film = reader.readFilm()) != null)
                System.out.println(film);
        } catch (EOFException ignored) {
        } catch (FileNotFoundException e) {
            System.out.printf("[ERROR] failed to open file \"%s\": %s\r\n", FILE_PATH, e.getMessage());
        } catch (IOException e) {
            System.out.printf("[ERROR] failed to read from file \"%s\": %s\r\n", FILE_PATH, e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.printf("[ERROR] invalid film found: %s\r\n", e.getMessage());
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                System.out.printf("[ERROR] failed to close file: \"%s\"\r\n", FILE_PATH);
            }
        }
    }

    private static void createAndStoreToFile() {
        System.out.print("Would you like to append to file? [Y/n]: ");
        char choice = Character.toUpperCase(LT.readChar());
        boolean append = choice == 'Y' || choice == '\n';

        PeliculaObjetoFicherosAdicion writer = null;
        try {
            writer = new PeliculaObjetoFicherosAdicion(FILE_PATH, append);
            boolean end = false;
            while (!end) {
                System.out.println("FILM CREATOR");
                Film film = Film.createFromUserInput();
                writer.writeFilm(film);
                System.out.print("Would you like to continue? [Y/n]: ");
                choice = Character.toUpperCase(LT.readChar());
                end = choice != 'Y' && choice != '\n';
            }
        } catch (FileNotFoundException e) {
            System.out.printf("[ERROR] failed to open file \"%s\": %s\r\n", FILE_PATH, e.getMessage());
        } catch (IOException e) {
            System.out.printf("[ERROR] failed to write to file \"%s\": %s\r\n", FILE_PATH, e.getMessage());
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                System.out.printf("[ERROR] failed to close file: \"%s\"\r\n", FILE_PATH);
            }
        }
    }

    public static void run() {
        boolean end = false;
        while (!end) {
            System.out.println("What would you like to do?");
            System.out.println("[1] Read stored films");
            System.out.println("[2] Create and store to file");
            System.out.println("[3] Exit");
            System.out.print("\n> ");

            try {
                switch (LT.readInt()) {
                    case 1 -> readFromFile();
                    case 2 -> createAndStoreToFile();
                    case 3 -> end = true;
                    default -> System.out.println("[ERROR] selected option must be a valid option");
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] selected option must be a number");
            }
        }
    }
}
