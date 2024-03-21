package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        // criar um menu de opcoes
        System.out.println("========================");
        System.out.println("|   Jogo da Forca       |");
        System.out.println("========================");
        System.out.println();
        // perguntar qual o tamanho da palavra
        System.out.println("Quantas letras você quer jogar? (Aceita-se 3, 4 ou 5)");

        int letterSize = scanner.nextInt();

        WordLoader wordLoader = new WordLoader(letterSize);
        // carregar uma palavra aleatoria
        String wordToPlay = wordLoader.load();

        // adivinhar as letras
        GuessWord guessWord = new GuessWord(wordToPlay);

        int maxAttempts = 10;
        int currentAttempts = 0;
        List<Integer> currentPositions = new ArrayList<>();
        List<Character> currentGuessedWord = new ArrayList<>();
        for(int index = 0; index < wordToPlay.length(); index++) {
            currentGuessedWord.add('_');
        }
        // contar as tentativas
        for(int attempt = 1; attempt < maxAttempts; attempt++) {
            // todo desenhar o boneco
            System.out.print("Entre com uma letra: ");
            String letter = scanner.next();
            scanner.nextLine();
            if(letter.length() > 1) {
                System.out.println("Letra inválida entre apenas uma letra");
            }
            Integer[] positionsFound = guessWord.letterExist(letter.charAt(0));
            List<Integer> positions = new ArrayList<>(List.of(positionsFound));
//            currentPositions.addAll(List.of(positions));
            // accumulate the previous guesses characters
            if(positions.isEmpty()) {
                currentAttempts++;
                System.out.println("Você errou tem agora " + (maxAttempts - currentAttempts) + " tentativas");
                printForca(currentGuessedWord, wordToPlay, positions, letter.charAt(0));
            } else {
                // mostrar a advinhação
                System.out.println();
                printForca(currentGuessedWord, wordToPlay, positions, letter.charAt(0));
            }

            if(currentGuessedWord.stream().noneMatch((a) -> a.equals('_') )) {
                System.out.println("You win!!");
                break;
            }
        }

        System.out.println("Game over");
    }

//    "_,v,_"
    private static void printForca(List<Character> guessedLetters, String wordToPlay, List<Integer> positions, Character letter) {
        for(int index = 0; index < wordToPlay.length(); index++) {
            int finalIndex = index;
            boolean positionExists = positions
                    .stream()
                    .anyMatch((pos) -> finalIndex == pos);
            if(positionExists) {
                // 1. "o,v,_"
                // 2. "o,v,_"
                // 3. "o,v,o"
                guessedLetters.set(index, letter);
                System.out.print(letter);
            }else {
                System.out.print(guessedLetters.get(index));
            }
        }
        System.out.println();

    }
}