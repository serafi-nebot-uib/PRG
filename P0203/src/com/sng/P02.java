package com.sng;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class P02 {
    private static final String FILE_NAME = "primerosEnterosPositivos.dat";

    private static boolean isPrime(int value) {
        if (value < 2) return false;
        for (int i = 2; i < value; i++)
            if (value % i == 0) return false;
        return true;
    }

    public static void run() {
        List<Integer> primes = new ArrayList<>();
        DataInputStream in = null;
        try {
            in = new DataInputStream(new BufferedInputStream(new FileInputStream(FILE_NAME)));

            for (int i = 0; i < P01.INTEGER_COUNT; i++) {
                int value = in.readInt();
                if (isPrime(value))
                    primes.add(value);
            }
        } catch (FileNotFoundException e) {
            System.out.printf("[ERROR] failed to open file \"%s\"\r\n", FILE_NAME);
        } catch (IOException e) {
            System.out.printf("[ERROR] failed to read from file \"%s\": %s\r\n", FILE_NAME, e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
            } catch (IOException e) {
                System.out.printf("[ERROR] failed to close file \"%s\": %s\r\n", FILE_NAME, e.getMessage());
            }
        }

        DataOutputStream out = null;
        try {
            out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(FILE_NAME)));
            for (int value : primes) {
                out.writeInt(value);
            }
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
