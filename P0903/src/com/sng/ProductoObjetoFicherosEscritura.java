package com.sng;

import java.io.*;
import java.util.List;

public class ProductoObjetoFicherosEscritura implements Closeable {
    private final ObjectOutputStream writer;

    public ProductoObjetoFicherosEscritura(String filePath) throws IOException {
        this.writer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
    }

    /**
     * Write a Product object to file.
     *
     * @param product product object to write
     * @throws IOException if there's an error during writing
     * @throws NullPointerException if product is null
     */
    public void write(T product) throws IOException, NullPointerException {
        if (product == null)
            throw new NullPointerException("product cannot be null");
        this.writer.writeObject(product);
    }

    /**
     * Write a list of Product objects to file.
     *
     * @param products product objects list to write
     * @throws IOException if there's an error during writing
     * @throws NullPointerException if product is null
     */
    public void write(List<T> products) throws IOException, NullPointerException {
        if (products == null)
            throw new NullPointerException("product list cannot be null");
        for (T product : products)
            write(product);
    }

    /**
     * Write a sentinel Product object to file.
     *
     * @throws IOException if there's an error during writing
     * @throws NullPointerException if product is null
     */
    public void writeSentinel() throws IOException, NullPointerException {
        write(T.getClassSentinel());
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }
}
