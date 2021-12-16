package com.example;

import java.util.ArrayList;
import java.util.List;

public class Day10LookAndSay {

    public static void main(String[] args) {

        // Test
        String result = lookAndSay("1", 5);
        assert result.equals("312211");

        // Part 1
        result = lookAndSay("3113322113", 40);
        System.out.println("part 1 solution: " + result.length());

        // Part 1
        result = lookAndSay(result, 10);
        System.out.println("part 2 solution: " + result.length());
    }

    private static String lookAndSay(String seed, int numRepetitions) {

        var sequence = List.of(seed.split(""));

        for (var n = 0; n < numRepetitions; n++) {

            var nextSequence = new ArrayList<String>();

            var lastDigit = sequence.get(0);
            var counter = 0;

            for (var digit : sequence) {

                if (digit.equals(lastDigit)) {
                    counter += 1;
                } else {
                    append(nextSequence, lastDigit, counter);
                    lastDigit = digit;
                    counter = 1;
                }
            }
            append(nextSequence, lastDigit, counter);

            sequence = nextSequence;
        }

        return String.join("", sequence);
    }

    private static void append(ArrayList<String> nextSequence, String lastDigit, int counter) {
        nextSequence.add(String.valueOf(counter));
        nextSequence.add(lastDigit);
    }
}
