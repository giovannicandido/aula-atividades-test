package org.example;

import java.util.List;

public interface Sort {

    public List<Integer> orderBy(List<Integer> list, Direction direction);
    public List<Integer> orderByAsc(List<Integer> list);
    public List<Integer> orderByDesc(List<Integer> list);
}
