package com.sng.io;

import java.io.*;
import java.util.List;

public class ObjectWriter<T extends Serializable> implements Closeable {
    private final ObjectOutputStream stream;

    public ObjectWriter(String path) throws IOException {
        this.stream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)));
    }

    public void write(T obj) throws IOException {
        if (obj == null) return;
        this.stream.writeObject(obj);
    }

    public void write(List<T> objs) throws IOException {
        if (objs == null) return;
        for (int i = 0; i < objs.size(); i++) this.write(objs.get(i));
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }
}
