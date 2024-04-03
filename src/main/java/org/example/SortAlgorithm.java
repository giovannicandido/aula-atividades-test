package org.example;

import java.util.List;

public abstract class SortAlgorithm {
    public abstract List<Integer> orderBy(List<Integer> list, Direction direction);

    protected void change(Integer a, Integer b) {

    }
}
