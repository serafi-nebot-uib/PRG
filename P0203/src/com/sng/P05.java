package com.sng;

import java.io.IOException;
import java.lang.instrument.IllegalClassFormatException;

public class P05 {
    private static final String FILE_NAME = "películas.txt";
    private static final int FILM_COUNT = 5;

    public P05() {
    }

    public void writeFilmsFromUserInput() throws IOException {
        PeliculaObjetoFicherosTextoEscritura writer = new PeliculaObjetoFicherosTextoEscritura(FILE_NAME);
        for (int i = 0; i < FILM_COUNT; i++) {
            System.out.println("CREATING FILM NUMBER " + i);
            writer.write(Film.createFromUserInput());
        }
        writer.close();
    }

    public void displayFilmsFromFile() throws IOException, IllegalClassFormatException {
        PeliculaObjetoFicherosTextoLectura reader = new PeliculaObjetoFicherosTextoLectura(FILE_NAME);
        for (int i = 0; i < FILM_COUNT; i++)
            System.out.println(reader.read());
        reader.close();
    }
}
