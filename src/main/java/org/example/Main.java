package org.example;

import org.example.geminiai.GeminiWordLoader;
import org.example.wordloader.WordLoader;
import org.example.wordloader.WordLoaderFileImpl;

import java.util.*;

public class Main {
    private static Scanner scanner;
    private static final ForcaUtils forcaUtils = new ForcaUtils();

    public static void main(String[] args) {
        int maxAttemps = 5;
        scanner = new Scanner(System.in);
        // criar um menu de opcoes
        System.out.println("========================");
        System.out.println("|   Jogo da Forca       |");
        System.out.println("========================");
        System.out.println();
        // perguntar qual o tamanho da palavra
        System.out.println("Quantas letras você quer jogar? (Aceita-se 3, 4 ou 5)");
        System.out.println("Qual o maximo de jogas que quer? ");
        maxAttemps = scanner.nextInt();

        int letterSize = scanner.nextInt();

        WordLoader wordLoader = new GeminiWordLoader(2, letterSize);
        // carregar uma palavra aleatoria
        String wordToPlay = wordLoader.load();
        System.out.println("A palavra é " + wordToPlay);

        // adivinhar as letras
        GuessWord guessWord = new GuessWord(wordToPlay);

        for(int attempt = 1; attempt <= maxAttemps; attempt++) {
            // todo desenhar o boneco
            System.out.print("Entre com uma letra: ");
            String letter = scanner.next();
            scanner.nextLine();
            if(letter.length() > 1) {
                System.out.println("Letra inválida entre apenas uma letra");
            }
            List<Integer> guess = guessWord.guess(letter.charAt(0));
            Map<Integer, Character> dataStructure = guessWord.toDataStructure();
            var guessWords = Collections.unmodifiableMap(dataStructure);
            var forca = forcaUtils.montarForca(guessWords, wordToPlay, guess, letter.charAt(0));
            System.out.println(forca);
            if(!guessWord.hasMoreAttempts()) {
                System.out.println("Game over");
                break;
            }
            if(guessWord.win()) {
                System.out.println("You win");
                break;
            }
        }

    }

}