package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForcaUtils {
    public String montarForca(Map<Integer, Character> guessedLetters, String wordToPlay, List<Integer> positions, Character letter) {
        StringBuilder forcaBuilder = new StringBuilder();
        Map<Integer, Character> guessedCopy = new HashMap<>();
        guessedCopy.putAll(guessedLetters);

        for(int index = 0; index < wordToPlay.length(); index++) {
            int finalIndex = index;
            boolean positionExists = positions
                    .stream()
                    .anyMatch((pos) -> finalIndex == pos);
            if(positionExists) {
                // 1. "o,v,_"
                // 2. "o,v,_"
                // 3. "o,v,o"
                guessedCopy.put(index, letter);
            }
        }

        for(Map.Entry<Integer, Character> entries : guessedCopy.entrySet()) {
            forcaBuilder.append(entries.getValue());
        }

        return forcaBuilder.toString();
    }
}
