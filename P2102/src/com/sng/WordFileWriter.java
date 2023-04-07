package com.sng;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;

public class WordFileWriter implements Closeable {
    private static final char SEPARATOR = ' ';

    private BufferedWriter writer;

    public WordFileWriter(String path, boolean append) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(path, append));
    }

    public void writeWord(String word) throws IOException {
        this.writer.write(word);
    }

    public void writeSeparator() throws IOException {
        this.writer.write(SEPARATOR);
    }

    @Override
    public void close() throws IOException {
        if (this.writer != null) {
            this.writer.close();
            this.writer = null;
        }
    }
}
