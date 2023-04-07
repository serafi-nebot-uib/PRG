package com.sng;

import java.io.IOException;

public class P04 {
    private static final String INPUT_FILE_PATH = "productos.dat";
    public static void main(String[] args) {
        P04.run();
    }

    public static void run() {
        ProductoObjetoFicherosLectura reader = null;
        try {
            reader = new ProductoObjetoFicherosLectura(INPUT_FILE_PATH);

            T product = null;
            while ((product = reader.read()) != null)
                System.out.println(product);
        } catch (IOException e) {
            System.out.printf("[ERROR] failed to read from file: \"%s\"\n", e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.printf("[ERROR] invalid product object found: \"%s\"\n", e.getMessage());
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                System.out.printf("[ERROR] failed to close file: \"%s\"\n", e.getMessage());
            }
        }
    }
}
