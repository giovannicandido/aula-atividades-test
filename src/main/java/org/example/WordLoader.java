package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class WordLoader {

    private final int wordLength;

    private static final int[] SUPPORTED_SIZES = new int[]{3,4,5};

    public WordLoader(int wordLength) {
        this.wordLength = wordLength;
    }

    public String load() {
        boolean containsSize = false;
        for (int size : SUPPORTED_SIZES) {
           if(size == this.wordLength) {
               containsSize = true;
               break;
           }
        }

        if(!containsSize) {
            throw new NotSupportedWordLengthException();
        }

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        URL resourceAsStream = classLoader.getResource("words_" + wordLength + ".txt");
        try {
            List<String> lines = Files.readAllLines(Paths.get(resourceAsStream.toURI()));
            int numberOfLines = lines.size();
            Random random = new Random();
            int line = random.nextInt(0, numberOfLines);
            return lines.get(line);

        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
