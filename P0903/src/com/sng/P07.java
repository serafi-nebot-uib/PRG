package com.sng;

import java.io.IOException;

public class P07 {
    private static final String INPUT_FILE_PATH = "productos.dat";
    private static final String OUTPUT_FILE_PATH = "productos_%s.dat";

    public static void main(String[] args) {
        P07.run();
    }

    public static void run() {
        ProductoObjetoFicherosLectura reader = null;
        ProductoObjetoFicherosEscritura writer = null;
        try {
            reader = new ProductoObjetoFicherosLectura(INPUT_FILE_PATH);

            System.out.print("Enter supplier: ");
            String supplier = LT.readLine();

            writer = new ProductoObjetoFicherosEscritura(String.format(OUTPUT_FILE_PATH, supplier));

            for (T product : reader.readAll())
                if (supplier.equals(product.getSupplier()))
                    writer.write(product);

            writer.writeSentinel();
        } catch (IOException e) {
            System.out.printf("[ERROR] failed to read from file: \"%s\"\n", e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.printf("[ERROR] invalid product object found: \"%s\"\n", e.getMessage());
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                System.out.printf("[ERROR] failed to close file \"%s\": \"%s\"\n", INPUT_FILE_PATH, e.getMessage());
            }

            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                System.out.printf("[ERROR] failed to close file \"%s\": \"%s\"\n", OUTPUT_FILE_PATH, e.getMessage());
            }
        }
    }
}
