package com.example;

import lombok.SneakyThrows;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class Day05NaughtyOrNice {

    @SneakyThrows
    public static void main(String[] args) {

        var url = Day05NaughtyOrNice.class.getResource("/naughtyornice.txt");
        var file = new File(Objects.requireNonNull(url).toURI());
        var input = new Scanner(file);

        var niceCounter = 0;
        var stillNiceCounter = 0;
        var lineCounter = 0;

        while (input.hasNextLine()) {
            var text = input.nextLine();
            if (isNice(text)) niceCounter += 1;
            if (isStillNice(text)) stillNiceCounter += 1;
            lineCounter += 1;
        }

        System.out.println("part 1 solution: " + niceCounter + " of " + lineCounter);
        System.out.println("part 2 solution: " + stillNiceCounter + " of " + lineCounter);
    }

    private static boolean isStillNice(String text) {

        return hasPairThatRepeatsSomewhereNonOverlapping(text)
                && containsAtLeastOneLetterWhichRepeatsWithExactlyOneLetter(text);
    }

    private static boolean isNice(String text) {

        return hasAtLeastThreeVowels(text)
                && hasAtLeastOneLetterAtLeastTwiceInSequence(text)
                && doesNotHaveStopWords(text);
    }

    private static boolean doesNotHaveStopWords(String text) {
        return !text.matches(".*(ab|cd|pq|xy).*");
    }

    private static boolean hasAtLeastOneLetterAtLeastTwiceInSequence(String text) {
        return text.matches(".*(\\w)\\1+.*");
    }

    private static boolean hasAtLeastThreeVowels(String text) {

        var vowels = Set.of('a', 'e', 'i', 'o', 'u');

        var n = 0;

        for (var ch : text.toCharArray()) {
            if (vowels.contains(ch)) n += 1;
        }

        return n >= 3;
    }

    private static boolean containsAtLeastOneLetterWhichRepeatsWithExactlyOneLetter(String text) {
        return text.matches(".*(\\w).\\1.*");
    }

    private static boolean hasPairThatRepeatsSomewhereNonOverlapping(String text) {
        return text.matches(".*(\\w\\w).*\\1.*");
    }
}
