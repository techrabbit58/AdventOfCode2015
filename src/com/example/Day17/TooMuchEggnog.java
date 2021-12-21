package com.example.Day17;

import lombok.SneakyThrows;

import java.nio.file.Paths;
import java.util.*;

public class TooMuchEggnog {

    public static void main(String[] args) {

        System.out.println("Advent of Code 2015 - Day 17");

        var result = solution(25, "/container_sizes_for_test.txt");
        assertEquals(4, result[0]);
        assertEquals(3, result[1]);

        result = solution(150, "/container_sizes.txt");
        System.out.println("part 1 solution: " + result[0]);
        System.out.println("part 2 solution: " + result[1]);
    }

    private static <T> void assertEquals(T expected, T actual) {
        if (!expected.equals(actual)) throw new AssertionError("part 1 test failed");
    }

    @SneakyThrows
    private static int[] solution(int necessaryVolume, String containerSizes) {

        var url = TooMuchEggnog.class.getResource(containerSizes);
        var path = Paths.get(Objects.requireNonNull(url).toURI());
        var scanner = new Scanner(path);

        var containers = new ArrayList<Integer>();
        while (scanner.hasNextInt()) {
            containers.add(scanner.nextInt());
        }

        var n = containers.size();
        List<int[]> combinations = new ArrayList<>();

        for (var r = 1; r <= n; r++) combinations.addAll(IntCombinations.generate(n, r));

        var numberOfValidCombinations = new int[2];
        var numContainersUsed = new HashMap<Integer, Integer>();

        for (int[] combination : combinations) {

            var totalVolume = 0;
            for (var i : combination) totalVolume += containers.get(i);
            if (totalVolume == necessaryVolume) {
                numberOfValidCombinations[0] += 1;
                var len = combination.length;
                numContainersUsed.put(len, numContainersUsed.getOrDefault(len, 0) + 1);
            }
        }
        numberOfValidCombinations[1] = numContainersUsed.get(
                numContainersUsed.keySet().stream().reduce(Integer::min).orElse(-1));

        return numberOfValidCombinations;
    }
}
