package com.example.Day20;

import com.example.Helpers.Miscellaneous;

public class InfiniteElvesInfiniteHouses {

    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 20: Infinite Elves and Infinite Houses");

        final var limit = 34_000_000 / 10;

        for (var house = 1; house < 10; house++) {
            var numPresents = 1;
            for (var elf = 2; elf <= (house + 1); elf++) {
                if (house % elf == 0) numPresents += elf;
            }
            System.out.println("house=" + house + ", numPresents=" + numPresents * 10);
            if (numPresents >= limit) break;
        }
    }
}
