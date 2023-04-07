package com.sng;

import java.io.*;

public class PeliculaObjetoFicherosAdicion implements Closeable {
    private final ObjectOutputStream outputStream;

    public PeliculaObjetoFicherosAdicion(String filePath, boolean append) throws IOException {
        this.outputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath, append)));
    }

    public void writeFilm(Film film) throws IOException {
        this.outputStream.writeObject(film);
    }

    @Override
    public void close() throws IOException {
        this.outputStream.close();
    }
}
