package org.example;

import net.bytebuddy.asm.MemberSubstitution;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GuessWordTest {

    @ParameterizedTest
    @MethodSource("letterSource")
    void testLetterExist(String word, char letter, int[] result) {
        GuessWord guessWord = new GuessWord(word);
        assertThat(guessWord.letterExist(letter))
                .containsExactlyInAnyOrder(result);
    }

    public static Stream<Arguments> letterSource() {
        return Stream.of(
                Arguments.of("bola", 'b', new int[]{0}),
                Arguments.of("bola", 'l', new int[]{2}),
                Arguments.of("paralelepipedo", 'p', new int[]{0,8,10}),
                Arguments.of("leite", 'g', new int[]{}),
                Arguments.of("outra palavra", 'g', new int[]{})
        );
    }
}