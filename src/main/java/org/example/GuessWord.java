package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class GuessWord {
    private final String word;

    public GuessWord(String word) {
        this.word = word;
    }

    /**
     * Return the position of the letter in the word or empty array if not found
     *
     * @param letter The letter to check
     * @return Position of the letter
     */
    public Integer[] letterExist(char letter) {
        List<Integer> letterPositions = new ArrayList<>();

        IntStream.range(0, word.length())
                .forEach(index -> {
                    char possibleLetter = word.charAt(index);
                    if (possibleLetter == letter) {
                        letterPositions.add(index);
                    }
                });

        return letterPositions.toArray(Integer[]::new);
    }
}
