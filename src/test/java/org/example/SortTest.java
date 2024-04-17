package org.example;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SortTest {

    @ParameterizedTest
    @MethodSource("algorithmFactory")
    void testOrderByAsc(Sort algorithm) {
        List<Integer> unordered = List.of(1, 3, 5, 2, 7, 8);

        Instant start = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        assertThat(algorithm.orderByAsc(unordered))
                .containsExactly(
                     1,2,3,5,7,8
                );

        Instant finished = LocalDateTime.now().toInstant(ZoneOffset.UTC);
        var spendMillisecond = finished.minus(start.toEpochMilli(), ChronoUnit.MILLIS).toEpochMilli();

        assertThat(spendMillisecond).isLessThan(40);

        System.out.println("Gastou "
                + spendMillisecond
                + " milisegundos"
        );
    }

    @ParameterizedTest
    @MethodSource("algorithmFactory")
    void testOrderByAscListAlreadyOrdered(Sort sort) {
        List<Integer> unordered = List.of(1, 2, 3, 5, 7, 8);

        assertThat(sort.orderByAsc(unordered))
                .containsExactly(
                        1,2,3,5,7,8
                );
    }

    @ParameterizedTest
    @MethodSource("algorithmFactory")
    void testOrderByAscListContraryOrder(Sort sort) {
        List<Integer> unordered = List.of(8, 7, 6, 5, 4, 1);

        assertThat(sort.orderByAsc(unordered))
                .containsExactly(
                        1,4,5,6,7,8
                );
    }

    public static Stream<Arguments> algorithmFactory() {
        return Stream.of(
                Arguments.of(new BubbleSort()),
                Arguments.of(new InsertionSort())
        );
    }
}
