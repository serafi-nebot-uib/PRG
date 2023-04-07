package com.sng;

import java.io.*;
import java.lang.instrument.IllegalClassFormatException;

public class PeliculaObjetoFicherosTextoLectura implements Closeable {
    private final BufferedReader reader;

    public PeliculaObjetoFicherosTextoLectura(String filePath) throws FileNotFoundException {
        this.reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
    }

    private String readLine() throws IOException {
        String line = this.reader.readLine();
        if (line == null)
            throw new EOFException("EOF reached");
        return line;
    }

    public Film read() throws IOException, IllegalClassFormatException {
        Film film = new Film();
        try {
            film.setTitle(readLine());
            film.setDirector(readLine());
            try {
                film.setYear(Integer.parseInt(readLine()));
            } catch (NumberFormatException e) {
                throw new IllegalClassFormatException("invalid year encoding found");
            }
        } catch (EOFException ignored) {
            film = null;
        }
        return film;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
