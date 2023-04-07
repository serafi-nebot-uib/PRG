package com.sng;

import java.io.File;
import java.io.IOException;

public class P_Insert {
    private static final String FILE_PATH = "productos.dat";
    private static final String TMP_FILE_PATH = "productos.tmp.dat";

    public static void main(String[] args) {
        ProductoObjetoFicherosLectura reader = null;
        ProductoObjetoFicherosEscritura writer = null;

        try {
            reader = new ProductoObjetoFicherosLectura(FILE_PATH);
            writer = new ProductoObjetoFicherosEscritura(TMP_FILE_PATH);

            System.out.println("Fill product to insert");
            T newProduct = T.readFromUserInput();

            boolean inserted = false;
            T product = null;
            while ((product = reader.read()) != null) {
                if (product.getCode() == newProduct.getCode()) {
                    System.out.printf("[ERROR] product with code %d already exists\n", product.getCode());
                    break;
                } else if (product.getCode() > newProduct.getCode() && !inserted) {
                    writer.write(newProduct);
                    writer.write(product);
                    inserted = true;
                } else {
                    writer.write(product);
                }
            }
            // if we have not inserted the product but we have cycled through all the file, we should append
            if (!inserted && product == null) {
                writer.write(newProduct);
                inserted = true;
            }
            writer.writeSentinel();

            File srcFile = new File(FILE_PATH);
            File tmpFile = new File(TMP_FILE_PATH);

            if (inserted) {
                if (srcFile.delete()) {
                    if (tmpFile.renameTo(srcFile))
                        System.out.printf("[ERROR] failed to rename file from \"%s\" to \"%s\"\n", tmpFile.getAbsolutePath(), srcFile.getAbsolutePath());
                } else {
                    System.out.printf("[ERROR] failed to delete file \"%s\"\n", srcFile.getAbsolutePath());
                }
            }
            if (!tmpFile.delete())
                System.out.printf("[ERROR] failed to delete file \"%s\"\n", tmpFile.getAbsolutePath());
        } catch (IOException e) {
            System.out.printf("[ERROR] failed to read/write files: %s\n", e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.printf("[ERROR] invalid Product object found: %s\n", e.getMessage());
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.printf("[ERROR] failed to close file \"%s\": %s\n", FILE_PATH, e.getMessage());
            }

            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.printf("[ERROR] failed to close file \"%s\": %s\n", TMP_FILE_PATH, e.getMessage());
            }
        }
    }
}
