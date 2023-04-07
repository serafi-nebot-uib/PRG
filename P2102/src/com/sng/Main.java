package com.sng;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        WordFileReader reader = null;
        WordFileWriter writer = null;
        try {
            reader = new WordFileReader("/Users/hexdhog/Documents/CS/S2/PRG/P2102/test.txt");
            writer = new WordFileWriter("/Users/hexdhog/Documents/CS/S2/PRG/P2102/test-output.txt", true);
            Frequency<String> frequency = new Frequency<>();
            int wordCount = 0;
            while (reader.hasNext()) {
                String word = reader.next();
                System.out.println(word);
                frequency.add(word);
                if (word.length() > 10) {
                    writer.writeWord(word);
                    writer.writeSeparator();
                }
                wordCount++;
            }
            System.out.println();
            System.out.printf("word count: %d\r\n", wordCount);
            System.out.println();

            for (String word : frequency.getAllWords())
                System.out.printf("%s: %d\r\n", word, frequency.getFrequency(word));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}