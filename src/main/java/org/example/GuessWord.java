package org.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class GuessWord {
    private final String word;
    private Map<Integer, Character> currentGuessedWord;

    public static int MAX_ATTEMPTS = 5;
    private int currentAttempt = 0;

    public GuessWord(String word) {
        currentGuessedWord = new HashMap<>();
        this.word = word;
        for(int index = 0;  index < word.length(); index++) {
            this.currentGuessedWord.put(index, '_');
        }
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

    public List<Integer> guess(Character letter) {
        List<Integer> positions = List.of(letterExist(letter));
        boolean guess = false;
        for(int index = 0; index < word.length(); index++) {
            int finalIndex = index;
            boolean positionExists = positions
                    .stream()
                    .anyMatch((pos) -> finalIndex == pos);
            if(positionExists) {
                // 1. "o,v,_"
                // 2. "o,v,_"
                // 3. "o,v,o"
                currentGuessedWord.put(index, letter);
                guess = true;
            }

        }
        if(!guess) {
            currentAttempt++;
        }

        return positions;
    }

    public boolean hasMoreAttempts() {
        return currentAttempt < MAX_ATTEMPTS;
    }

    public boolean win() {

        boolean sameSize = currentGuessedWord.size() == word.length();

        boolean isSame = true;

        for(int index = 0; index < word.length(); index++) {
            if(currentGuessedWord.get(index) != word.charAt(index)) {
                isSame = false;
                break;
            }
        }

        return sameSize && isSame;
    }

    public Map<Integer, Character> toDataStructure() {
        return currentGuessedWord;
    }
}
