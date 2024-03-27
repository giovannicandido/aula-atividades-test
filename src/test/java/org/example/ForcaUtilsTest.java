package org.example;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ForcaUtilsTest {

    @Test
    void deveMontarForca() {
        ForcaUtils utils = new ForcaUtils();
        var wordToPlay = "senhora";
        var guessedLetter = new HashMap<Integer, Character>();
        guessedLetter.put(0, '_');
        guessedLetter.put(1, '_');
        guessedLetter.put(2, '_');
        guessedLetter.put(3, '_');
        guessedLetter.put(4, '_');
        guessedLetter.put(5, '_');
        guessedLetter.put(6, '_');
        List<Integer> positions = new ArrayList<>();
        String forca = utils.montarForca(guessedLetter, wordToPlay, positions, 'b');
        assertThat(forca).isEqualTo("_______");

        positions.add(1);

        String forca2 = utils.montarForca(guessedLetter, wordToPlay, positions, 'e');
        assertThat(forca2).isEqualTo("_e_____");

        positions.add(4);

        String forca3 = utils.montarForca(guessedLetter, wordToPlay, positions, 'o');

        assertThat(forca3).isEqualTo("_o__o__");
    }

    @Test
    void deveMontarForcaComAdivinhacaoAnterior() {
        ForcaUtils utils = new ForcaUtils();
        var wordToPlay = "senhora";
        var guessedLetter = new HashMap<Integer, Character>();
        guessedLetter.put(0, 's');
        guessedLetter.put(1, '_');
        guessedLetter.put(2, '_');
        guessedLetter.put(3, '_');
        guessedLetter.put(4, '_');
        guessedLetter.put(5, '_');
        guessedLetter.put(6, 'a');
        List<Integer> positions = new ArrayList<>();
        String forca = utils.montarForca(guessedLetter, wordToPlay, positions, 'b');
        assertThat(forca).isEqualTo("s_____a");

        positions.add(1);

        String forca2 = utils.montarForca(guessedLetter, wordToPlay, positions, 'e');
        assertThat(forca2).isEqualTo("s_____a");

    }

    @Test
    void deveMontarForcaIgnorandoValoresForaDaPalavra() {
        ForcaUtils utils = new ForcaUtils();
        var wordToPlay = "senhora";
        var guessedLetter = new HashMap<Integer, Character>();
        guessedLetter.put(0, 's');
        guessedLetter.put(1, '_');
        guessedLetter.put(2, '_');
        guessedLetter.put(3, '_');
        guessedLetter.put(4, '_');
        guessedLetter.put(5, '_');
        guessedLetter.put(6, 'a');
        List<Integer> positions = new ArrayList<>();
        positions.add(10);
        String forca = utils.montarForca(guessedLetter, wordToPlay, positions, 'c');
        assertThat(forca).isEqualTo("s_____a");
    }
}