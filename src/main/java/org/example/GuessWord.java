package org.example;

import java.util.ArrayList;
import java.util.List;

public class GuessWord {
    private final String word;

    public GuessWord(String word) {
        this.word = word;
    }

    /**
     * Return the position of the letter in the word or empty array if not found
     * @param letter The letter to check
     * @return Position of the letter
     */
    public Integer[] letterExist(char letter) {
        List<Integer> letterPositions = new ArrayList<>();

        for(int index = 0; index < this.word.length(); index++) {
           char possibleLetter = this.word.charAt(index);
           if(possibleLetter == letter) {
               letterPositions.add(index);
           }
        }
//        char possibleLetter = word.charAt(2);
        return letterPositions.toArray(Integer[]::new);
    }
}
