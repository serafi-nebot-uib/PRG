package com.sng.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectReader<T extends Serializable> implements Closeable {
    private final ObjectInputStream stream;

    public ObjectReader(String path) throws IOException {
        this.stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path)));
    }

    public T read() throws IOException, ClassNotFoundException {
        T obj = null;
        try {
            obj = (T) this.stream.readObject();
        } catch (EOFException ignored) {
        }
        return obj;
    }

    public List<T> readAll() throws IOException, ClassNotFoundException {
        List<T> objs = new ArrayList<>();
        T obj = null;
        while ((obj = this.read()) != null) objs.add(obj);
        return objs;
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }
}
