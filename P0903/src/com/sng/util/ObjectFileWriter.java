package com.sng.util;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ObjectFileWriter<T extends Sentinel<?>> implements Closeable {
    private final ObjectOutputStream writer;
    private final Class<T> tClass;

    /**
     * Create an ObjectFileWriter with the specified generic type.
     *
     * @param filePath target file path
     * @param tClass target object class
     * @throws IOException on write error
     */
    public ObjectFileWriter(Class<T> tClass, String filePath) throws IOException {
        this.tClass = tClass;
        this.writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
    }

    /**
     * Write an object to file.
     *
     * @param object object to write
     * @throws IOException          if there's an error during writing
     * @throws NullPointerException if object is null
     */
    public void write(T object) throws IOException, NullPointerException {
        if (object == null)
            throw new NullPointerException("product cannot be null");
        this.writer.writeObject(object);
    }

    /**
     * Write a list of objects to file.
     *
     * @param objects objects list to write
     * @throws IOException          if there's an error during writing
     * @throws NullPointerException if object is null
     */
    public void write(List<T> objects) throws IOException, NullPointerException {
        if (objects == null)
            throw new NullPointerException("product list cannot be null");
        for (T object : objects)
            write(object);
    }

    /**
     * Write End-Of-File object (sentinel object).
     */
    public void writeEOF() {
        try {
            Constructor<T> constructor = this.tClass.getConstructor();
            T object = constructor.newInstance();
            write(tClass.cast(object.getSentinel()));
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException |
                 IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }
}
