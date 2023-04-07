package com.sng;

import java.io.IOException;

public class P06 {
    private static final String INPUT_FILE_PATH = "productos.dat";

    public static void main(String[] args) {
        P06.run();
    }

    public static void run() {
        ProductoObjetoFicherosLectura reader = null;
        try {
            reader = new ProductoObjetoFicherosLectura(INPUT_FILE_PATH);

            System.out.print("Enter description: ");
            String description = LT.readLine();

            T product = null;
            while ((product = reader.read()) != null && !product.getDescription().equals(description));
            if (product != null) {
                System.out.println(product);
            } else {
                System.out.println("Product not found!");
            }
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
