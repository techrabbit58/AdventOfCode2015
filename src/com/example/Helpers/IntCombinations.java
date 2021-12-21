package com.example.Helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Ref.: https://www.baeldung.com/java-combinations-algorithm
 */
public class IntCombinations {

    public static List<int[]> generate(int n, int r) {

        var combinations = new ArrayList<int[]>();
        combine(combinations, new int[r], 0, n - 1, 0);
        return combinations;
    }

    private static void combine(ArrayList<int[]> combinations, int[] ints, int start, int end, int index) {

        if (index == ints.length) {
            int[] combination = ints.clone();
            combinations.add(combination);
        } else if (start <= end) {
            ints[index] = start;
            combine(combinations, ints, start + 1, end, index + 1);
            combine(combinations, ints, start + 1, end, index);
        }
    }
}
