package org.example;

import org.example.chatgpt.WordGpt;

import java.util.List;
import java.util.stream.Collectors;

public class GptTest {
    public static void main(String[] args) {
        WordGpt wordGpt = new WordGpt();
        List<String> wordList = wordGpt.load(5, 8);
        String words = wordList.stream()
                        .collect(Collectors.joining(", "));
        System.out.println(words);
    }
}
