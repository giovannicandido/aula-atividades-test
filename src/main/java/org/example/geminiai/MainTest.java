package org.example.geminiai;

import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        GeminiWordLoader wordLoader = new GeminiWordLoader();
        List<String> words = wordLoader.load(5, 6);
        words.forEach(System.out::println);
    }
}
