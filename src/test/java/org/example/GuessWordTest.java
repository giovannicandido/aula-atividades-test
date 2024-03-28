package org.example;

import net.bytebuddy.asm.MemberSubstitution;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class GuessWordTest {

    @ParameterizedTest
    @MethodSource("letterSource")
    void testLetterExist(String word, char letter, Integer[] result) {
        GuessWord guessWord = new GuessWord(word);
        assertThat(guessWord.letterExist(letter))
                .containsExactlyInAnyOrder(result);
    }

    @Test
    void deveRetonarEstruturaDeDados() {
        GuessWord guessWord = new GuessWord("nadador");

        Map<Integer, Character> dataStructure = guessWord.toDataStructure();

        assertThat(dataStructure).hasSize(7);
        assertThat(dataStructure.get(0)).isEqualTo('_');
        assertThat(dataStructure.get(1)).isEqualTo('_');
        assertThat(dataStructure.get(2)).isEqualTo('_');
        assertThat(dataStructure.get(3)).isEqualTo('_');
        assertThat(dataStructure.get(4)).isEqualTo('_');
        assertThat(dataStructure.get(5)).isEqualTo('_');
        assertThat(dataStructure.get(6)).isEqualTo('_');

    }

    @Test
    void deveAdivinharPalavra() {
        GuessWord guessWord = new GuessWord("nadador");

        List<Integer> guesses = guessWord.guess('n');
        assertThat(guesses).hasSize(1);
        assertThat(guesses).containsOnly(0);

        guesses = guessWord.guess('d');
        assertThat(guesses).hasSize(2);
        assertThat(guesses).containsOnly(2, 4);
    }

    @Test
    void deveAdivinharPalavraSemInterferenciaExterna() {
        GuessWord guessWord = new GuessWord("nadador");

        Map<Integer, Character> dataStructure = guessWord.toDataStructure();

        dataStructure.put(0, 'b');

        List<Integer> guesses = guessWord.guess('b');

        assertThat(guesses).hasSize(0);
    }

    @Test
    void deveChecarWinSemInterferencia() {
        GuessWord guessWord = new GuessWord("nadador");

        Map<Integer, Character> dataStructure = guessWord.toDataStructure();

        dataStructure.put(0, 'b');
        dataStructure.put(1, 'b');
        dataStructure.put(2, 'b');
        dataStructure.put(3, 'b');
        dataStructure.put(4, 'b');
        dataStructure.put(5, 'b');
        dataStructure.put(6, 'b');

        assertThat(guessWord.win()).isFalse();
    }

    @Test
    void deveChecarWinEstadoInicial() {
        GuessWord guessWord = new GuessWord("nadador");

        assertThat(guessWord.win()).isFalse();
    }

    @Test
    void deveChecarWinAdivinhacoesErradas() {
        GuessWord guessWord = new GuessWord("nadador");
        guessWord.guess('n');
        guessWord.guess('a');
        guessWord.guess('d');
        guessWord.guess('r');
        assertThat(guessWord.win()).isFalse();
    }

    @Test
    void deveChecarWinAdivinhacoesCorretas() {
        GuessWord guessWord = new GuessWord("nadador");
        guessWord.guess('n');
        guessWord.guess('a');
        guessWord.guess('d');
        guessWord.guess('r');
        guessWord.guess('o');
        assertThat(guessWord.win()).isTrue();
    }

    @Test
    void deveChecarWinAdivinhacoesCorreta() {
        GuessWord guessWord = new GuessWord("nadador");
        guessWord.guess('a');
        guessWord.guess('d');
        guessWord.guess('r');
        guessWord.guess('n');
        guessWord.guess('o');
        assertThat(guessWord.win()).isTrue();
    }

    @Test
    void deveChecarWinAdivinhacoesCorretasSegundaPossibilidade() {
        GuessWord guessWord = new GuessWord("nadador");
        guessWord.guess('a');
        guessWord.guess('a');
        guessWord.guess('a');
        guessWord.guess('a');
        guessWord.guess('a');
        guessWord.guess('a');
        guessWord.guess('a');
        assertThat(guessWord.win()).isFalse();
    }

    @Test
    void deveRetornarQuePossuiMaisTentativas() {
        GuessWord guessWord = new GuessWord("nadador");
        assertThat(guessWord.hasMoreAttempts()).isTrue();
    }

    @Test
    void deveRetornarQuePossuiMaisTentativasScenario2() {
        GuessWord guessWord = new GuessWord("nadador");
        guessWord.guess('b');
        assertThat(guessWord.hasMoreAttempts()).isTrue();
    }

    @Test
    void deveRetornarQuePossuiMaisTentativasScenario3() {
        GuessWord guessWord = new GuessWord("paralelepipido");
        guessWord.guess('p');
        guessWord.guess('a');
        guessWord.guess('k');
        guessWord.guess('k');
        guessWord.guess('k');
        assertThat(guessWord.hasMoreAttempts()).isTrue();
    }

    @Test
    void deveRetornarQuePossuiMaisTentativasScenario4() {
        GuessWord guessWord = new GuessWord("paralelepipido");
        guessWord.guess('k');
        guessWord.guess('w');
        guessWord.guess('k');
        guessWord.guess('y');
        guessWord.guess('y');
        assertThat(guessWord.hasMoreAttempts()).isFalse();
    }

    @Test
    void deveRetornarQuePossuiMaisTentativasScenario5() {
        GuessWord guessWord = new GuessWord("paralelepipido");
        guessWord.guess('k');
        guessWord.guess('w');
        guessWord.guess('k');
        guessWord.guess('y');
        guessWord.guess('y');
        guessWord.guess('z');
        assertThat(guessWord.hasMoreAttempts()).isFalse();
    }



    public static Stream<Arguments> letterSource() {
        return Stream.of(
                Arguments.of("bola", 'b', new Integer[]{0}),
                Arguments.of("bola", 'l', new Integer[]{2}),
                Arguments.of("paralelepipedo", 'p', new Integer[]{0,8,10}),
                Arguments.of("leite", 'g', new Integer[]{}),
                Arguments.of("outra palavra", 'g', new Integer[]{})
        );
    }
}