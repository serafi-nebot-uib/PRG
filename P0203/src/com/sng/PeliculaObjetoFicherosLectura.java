package com.sng;

import java.io.*;

public class PeliculaObjetoFicherosLectura implements Closeable {
    private final ObjectInputStream inputStream;

    public PeliculaObjetoFicherosLectura(String filePath) throws IOException {
        this.inputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
    }

    public Film readFilm() throws IOException, ClassNotFoundException {
        return (Film) this.inputStream.readObject();
    }

    @Override
    public void close() throws IOException {
        this.inputStream.close();
    }
}

