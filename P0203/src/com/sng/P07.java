package com.sng;

import java.io.FileNotFoundException;
import java.io.IOException;

public class P07 {
    private static final String FILE_PATH = "películas.dat";

    public static void run() {
        PeliculaObjetoFicherosTextoEscritura filmObjectWriter = null;
        try {
            filmObjectWriter = new PeliculaObjetoFicherosTextoEscritura(FILE_PATH);
            boolean end = false;
            while (!end) {
                System.out.println("FILM CREATOR");
                Film film = Film.createFromUserInput();
                filmObjectWriter.write(film);
                System.out.print("Would you like to continue? [Y/n]: ");
                char choice = Character.toUpperCase(LT.readChar());
                end = choice != 'Y' && choice != '\n';
            }
        } catch (FileNotFoundException e) {
            System.out.printf("[ERROR] failed to open file \"%s\": %s\r\n", FILE_PATH, e.getMessage());
        } catch (IOException e) {
            System.out.printf("[ERROR] failed to write to file \"%s\": %s\r\n", FILE_PATH, e.getMessage());
        } finally {
            try {
                if (filmObjectWriter != null)
                    filmObjectWriter.close();
            } catch (IOException e) {
                System.out.printf("[ERROR] failed to close file: \"%s\"\r\n", FILE_PATH);
            }
        }
    }
}