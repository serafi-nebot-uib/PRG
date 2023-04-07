package com.sng;

import java.io.*;

public class P0703 {
    private static final String INPUT_FIlE_PATH = "películas.dat";
    private static final String OUTPUT_FIlE_PATH = "películas_2022.dat";

    public static void main(String[] args) {
        ObjectInputStream in = null;
        ObjectOutputStream out = null;
        try {
            in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(INPUT_FIlE_PATH)));
            out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(OUTPUT_FIlE_PATH)));

            while (true) {
                Film film = (Film) in.readObject();
                if (film != null && film.getYear() == 2022)
                    out.writeObject(film);
            }
        } catch (EOFException ignored) {
        } catch (ClassNotFoundException e) {
            System.out.println("[ERROR] invalid film object found");
        } catch (IOException e) {
            System.out.println("[ERROR] failed read/write files");
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e) {
                System.out.println("[ERROR] failed to close files");
            }
        }
    }
}