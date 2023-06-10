package com.sng;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ObjectReader<T extends Serializable> {
    private final ObjectInputStream stream;
    private final Class<T> clazz;

    public ObjectReader(Class<T> clazz, String path) throws IOException {
        this.clazz = clazz;
        this.stream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path)));
    }

    public T read() throws IOException, ClassNotFoundException {
        return (T) this.stream.readObject();
    }

    public List<T> readAll() throws IOException, ClassNotFoundException {
        List<T> objs = new ArrayList<>();
        T obj = null;
        while ((obj = this.read()) != null) objs.add(obj);
        return objs;
    }
}
