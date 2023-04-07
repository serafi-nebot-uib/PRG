package com.sng;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoObjetoFicherosLectura implements Closeable {
    private final ObjectInputStream reader;
    private boolean eof = false;

    public ProductoObjetoFicherosLectura(String filePath) throws IOException {
        this.reader = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filePath)));
    }

    /**
     * Read a Product object from file.
     *
     * @return read product object, or null if EOF has been reached
     * @throws IOException if there's an error during reading
     * @throws ClassNotFoundException if the read data is not a Product object
     */
    public T read() throws IOException, ClassNotFoundException {
        if (this.eof) return null;
        T product = (T) this.reader.readObject();
        if (product == null) return null;
        this.eof = product.isSentinel();
        return this.eof ? null : product;
    }

    /**
     * Read all Product objects from file.
     *
     * @return a list containing all product objects
     * @throws IOException if there's an error during reading
     * @throws ClassNotFoundException if the read data is not a Product object
     */
    public List<T> readAll() throws IOException, ClassNotFoundException {
        List<T> products = new ArrayList<>();
        T product = null;
        while ((product = read()) != null)
            products.add(product);
        return products;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }
}
