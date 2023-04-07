package com.sng;

import java.io.*;

public class P01 {
    private static final String FILE_NAME = "primerosEnterosPositivos.dat";

    public static final int INTEGER_COUNT = 1000;

    public static void run() {
        DataOutputStream out = null;
        try {
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(FILE_NAME)));

            for (int i = 0; i < INTEGER_COUNT; i++)
                out.writeInt(i);
        } catch (FileNotFoundException e) {
            System.out.printf("[ERROR] failed to open file \"%s\"\r\n", FILE_NAME);
        } catch (IOException e) {
            System.out.printf("[ERROR] failed to write to file \"%s\": %s\r\n", FILE_NAME, e.getMessage());
        } finally {
            try {
                if (out != null) out.close();
            } catch (IOException e) {
                System.out.printf("[ERROR] failed to close file \"%s\": %s\r\n", FILE_NAME, e.getMessage());
            }
        }
    }
}
