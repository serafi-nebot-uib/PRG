package com.sng;

import java.io.*;

public class PeliculaObjetoFicherosTextoEscritura implements Closeable {
    private final BufferedWriter writer;

    public PeliculaObjetoFicherosTextoEscritura(String filePath) throws FileNotFoundException {
        this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
    }

    public void write(Film film) throws IOException {
        this.writer.write(film.getTitle() + "\n");
        this.writer.write(film.getDirector() + "\n");
        this.writer.write(film.getYear().toString() + "\n");
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }
}
