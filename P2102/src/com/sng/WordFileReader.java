package com.sng;

import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class WordFileReader implements Iterator<String>, Closeable {
    private static final List<Character> WORD_DELIMITERS;
    private static final int BUFFER_SIZE = 128;

    private final char[] buffer = new char[BUFFER_SIZE];
    private final long length;
    private BufferedReader reader;
    private long index = 0;

    static {
        WORD_DELIMITERS = Arrays.asList('\r', '\n', ' ');
    }

    public WordFileReader(String path) throws FileNotFoundException {
        File file = new File(path);
        this.length = file.length();
        this.reader = new BufferedReader(new FileReader(path));
    }

    private String readWord() throws IOException {
        Arrays.fill(this.buffer, (char) 0);
        int buff_i = 0;
        char[] buff = new char[1];

        while (this.reader.read(buff, 0, 1) != -1 && buff_i < BUFFER_SIZE) {
            this.index++;
            if (WORD_DELIMITERS.contains(buff[0])) {
                if (buff_i == 0) continue;
                break;
            }
            this.buffer[buff_i++] = buff[0];
        }

        String word = String.valueOf(this.buffer, 0, buff_i);
        return word.length() == 0 ? null : word;
    }

    @Override
    public boolean hasNext() {
        return this.index < this.length;
    }

    @Override
    public String next() {
        try {
            return readWord();
        } catch (Exception ignored) {
            return null;
        }
    }

    @Override
    public void close() throws IOException {
        if (this.reader != null) {
            this.reader.close();
            this.reader = null;
        }
    }
}
