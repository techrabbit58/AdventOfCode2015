package com.example.Day20;

import com.example.Helpers.Miscellaneous;

import java.util.HashSet;
import java.util.Set;

public class InfiniteElvesInfiniteHouses {

    private static final int PUZZLE_INPUT = 34_000_000;

    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 20: Infinite Elves and Infinite Houses");

        System.out.println("part 1 solution: " + part1());
        System.out.println("part 2 solution: " + part2());
    }

    private static int part2() {

        var house = 1;
        while(true) {
            var numPresents = 11 * modifiedSigma(house);
            if (numPresents >= PUZZLE_INPUT) break;
            house += 1;
        }

        return house;
    }

    private static int part1() {

        var house = 1;
        while (true) {
            int numPresents = 10 * sigma(house);
            if (numPresents >= PUZZLE_INPUT) break;
            house += 1;
        }

        return house;
    }

    /**
     * The sigma() function. It is the sum of all divisors of n. (Divisors include 1 and n!)
     */
    private static int sigma(final int n) {
        return getDivisors(n).stream().reduce(Integer::sum).orElse(0);
    }

    /**
     * A variant of the sigma() function that limits the responsibility to 50 houses per elf,
     * by applying a filter stage before the reduction stage of the stream processing.
     */
    private static int modifiedSigma(final int n) {
        return getDivisors(n).stream().filter(d -> n < d * 50).reduce(Integer::sum).orElse(0);
    }

    /**
     * Find all divisors of integer n, including 1 and n itself.
     */
    private static Set<Integer> getDivisors(final int n) {

        Set<Integer> divisors = new HashSet<>();

        int limit = (int) (1 + Math.sqrt(n));
        for (var d = 1; d < limit; d++) {
            if (n % d == 0) {
                divisors.add(d);
                divisors.add(n / d);
            }
        }

        return divisors;
    }
}
