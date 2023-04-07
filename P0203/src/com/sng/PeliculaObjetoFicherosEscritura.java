package com.sng;

import java.io.*;

public class PeliculaObjetoFicherosEscritura implements Closeable {
    private final ObjectOutputStream outputStream;

    public PeliculaObjetoFicherosEscritura(String filePath) throws IOException {
        this.outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
    }

    public void writeFilm(Film film) throws IOException {
        this.outputStream.writeObject(film);
    }

    @Override
    public void close() throws IOException {
        this.outputStream.close();
    }
}
