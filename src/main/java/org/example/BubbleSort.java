package org.example;

import java.util.ArrayList;
import java.util.List;

public class BubbleSort implements Sort {
    @Override
    public List<Integer> orderBy(List<Integer> list, Direction direction) {
        var temporaryList = new ArrayList<>(list);
        temporaryList.sort(Integer::compareTo);
        return temporaryList;
    }

    @Override
    public List<Integer> orderByAsc(List<Integer> list) {
        return orderBy(list, Direction.ASC);
    }

    @Override
    public List<Integer> orderByDesc(List<Integer> list) {
        return orderBy(list, Direction.DESC);
    }
}
