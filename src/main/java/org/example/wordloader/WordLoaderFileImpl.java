package org.example.wordloader;

import org.example.NotSupportedWordLengthException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class WordLoaderFileImpl implements WordLoader {

    private final int wordLength;

    public WordLoaderFileImpl(int wordLength) {
        this.wordLength = wordLength;
    }

    @Override
    public String load() {

        if(this.wordLength < 3 || this.wordLength > 5) {
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
