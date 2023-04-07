package com.sng.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

public class ObjectFileReader<T extends Sentinel<?>> {
    private final Class<com.sng.T> tClass;
    private final ObjectInputStream reader;
    private boolean eof = false;

    /**
     * Create an ObjectFileReader with generic type.
     *
     * @param tClass   class of the target object
     * @param filePath file path
     * @throws IOException if there's a read error
     */
    public ObjectFileReader(Class<com.sng.T> tClass, String filePath) throws IOException {
        this.tClass = tClass;
        this.reader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
    }

    /**
     * Read a Product object from file.
     *
     * @return read product object, or null if EOF has been reached
     * @throws IOException if there's an error during reading
     * @throws ClassNotFoundException if the read data is of an invalid class
     */
    public T read() throws IOException, ClassNotFoundException {
        if (this.eof) return null;
        Object object = this.reader.readObject();
        if (object == null) return null;
        if (!this.tClass.isInstance(object))
            throw new ClassNotFoundException();
        T inst = this.tClass.cast(object);
        this.eof = inst.isSentinel();
        return this.eof ? null : inst;
    }

    /**
     * Read all Product objects from file.
     *
     * @return a list containing all product objects
     * @throws IOException if there's an error during reading
     * @throws ClassNotFoundException if the read data is not a Product object
     */
    public List<T> readAll() throws IOException, ClassNotFoundException {
        List<T> objects = new ArrayList<>();
        T object = null;
        while ((object = read()) != null)
            objects.add(object);
        return objects;
    }
}
