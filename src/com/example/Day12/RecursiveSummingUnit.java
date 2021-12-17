package com.example.Day12;

import java.util.ArrayList;
import java.util.List;

public class RecursiveSummingUnit implements SummingUnit {

    private final List<Integer> allNumbers = new ArrayList<>();

    @Override
    public List<Integer> getAllNumbers() {
        return allNumbers;
    }

    @Override
    public void accept(String ch) {

    }
}
