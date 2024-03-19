package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WordLoaderTest {
    @Test
    void testWordSize() {
        WordLoader wordLoader = new WordLoader(4);
        String word = wordLoader.load();
        assertThat(word).hasSize(4);
    }
    @ParameterizedTest
    @ValueSource(ints = {-1,0,1,2,6})
    void testWordSizeNotExist(int size) {
        WordLoader wordLoader = new WordLoader(size);
        final WordLoader finalWordLoader1 = wordLoader;
        assertThatThrownBy(() -> {
            finalWordLoader1.load();
        }).isInstanceOf(NotSupportedWordLengthException.class);
    }

    /**
     * This test is not predictable
     */
    @Test
    void testRandomWord() {
        WordLoader wordLoader = new WordLoader(3);

        String word1 = wordLoader.load();
        String word2 = wordLoader.load();

        assertThat(word1).isNotEqualTo(word2);

    }
}