package com.sng;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;

public class P08 {
    private static final String FILE_PATH = "películas.dat";


    public static void run() {
        PeliculaObjetoFicherosTextoLectura filmObjectReader = null;
        try {
            filmObjectReader = new PeliculaObjetoFicherosTextoLectura(FILE_PATH);
            Film film = null;
            while ((film = filmObjectReader.read()) != null) {
                System.out.println(film);
            }
        } catch (FileNotFoundException e) {
            System.out.printf("[ERROR] failed to open file \"%s\": %s\r\n", FILE_PATH, e.getMessage());
        } catch (IOException | IllegalClassFormatException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (filmObjectReader != null)
                    filmObjectReader.close();
            } catch (IOException e) {
                System.out.printf("[ERROR] failed to close file: \"%s\"\r\n", FILE_PATH);
            }
        }
    }
}