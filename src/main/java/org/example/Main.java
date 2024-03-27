package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner;
    private static final ForcaUtils forcaUtils = new ForcaUtils();

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

        for(int attempt = 1; attempt < GuessWord.MAX_ATTEMPTS; attempt++) {
            // todo desenhar o boneco
            System.out.print("Entre com uma letra: ");
            String letter = scanner.next();
            scanner.nextLine();
            if(letter.length() > 1) {
                System.out.println("Letra inválida entre apenas uma letra");
            }
            List<Integer> guess = guessWord.guess(letter.charAt(0));
            var forca = forcaUtils.montarForca(guessWord.toDataStructure(), wordToPlay, guess, letter.charAt(0));
            System.out.printf(forca);
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