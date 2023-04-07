package com.sng;

import java.io.IOException;

public class P03 {
    private static final String OUTPUT_FILE_PATH = "productos.dat";

    public static void main(String[] args) {
        P03.run();
    }

    public static void run() {
        ProductoObjetoFicherosEscritura writer = null;

        try {
            writer = new ProductoObjetoFicherosEscritura(OUTPUT_FILE_PATH);

            // int counter = 0;
            boolean end = false;
            while (!end) {
                T product = T.readFromUserInput();
                // product.setCode(++counter);
                writer.write(product);
                System.out.print("Would you like to continue? [Y/n]: ");
                char choice = Character.toUpperCase(LT.readChar());
                end = choice != 'Y' && choice != '\n';
            }
            writer.writeSentinel();
        } catch (IOException e) {
            System.out.printf("[ERROR] failed to write to file: \"%s\"\n", e.getMessage());
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                System.out.printf("[ERROR] failed to close file: \"%s\"\n", e.getMessage());
            }
        }
    }
}
