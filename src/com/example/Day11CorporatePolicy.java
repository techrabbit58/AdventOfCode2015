package com.example;

import java.util.regex.Pattern;

public class Day11CorporatePolicy {

    public static void main(String[] args) {

        var pwd = "hepxcrrq";

        do {
            pwd = increment(pwd);
        } while (isNotValid(pwd));

        System.out.println("part 1 solution: " + pwd);

        do {
            pwd = increment(pwd);
        } while (isNotValid(pwd));

        System.out.println("part 2 solution: " + pwd);
    }

    private static boolean isNotValid(String word) {
        return !(
                hasIncreasingStraitOfThree(word)
                && doesNotContainIOL(word)
                && hasTwoNonOverlappingDifferentPairs(word)
        );
    }

    private static boolean hasTwoNonOverlappingDifferentPairs(String word) {

        var pattern = Pattern.compile("^.*(.)\\1.*(.)\\2.*$");
        var matcher = pattern.matcher(word);

        var hasTwoPairs = false;

        if (matcher.matches()) hasTwoPairs = !matcher.group(1).equals(matcher.group(2));

        return hasTwoPairs;
    }

    private static boolean doesNotContainIOL(String word) {
        return !(word.contains("i") || word.contains("o") || word.contains("l"));
    }

    private static boolean hasIncreasingStraitOfThree(String word) {

        for (var i = 0; i < word.length() - 3; i++) {

            var sub = word.substring(i, i + 3);
            var p = Pattern.compile(sub);
            var m = p.matcher(ALPHABET);

            if (m.find()) return true;
        }
        return false;
    }

    private static String increment(String word) {

        var sequence = word.toLowerCase().toCharArray();
        var length = sequence.length;

        var carry = true;
        for (var i = length - 1; i >= 0; i--) {
            if (carry && sequence[i] == 'z') {
                sequence[i] = 'a';
            } else if (carry) {
                sequence[i] = nextCharacter(sequence[i]);
                carry = false;
            } else break;
        }

        return new String(sequence);
    }

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    private static char nextCharacter(char ch) {
        return ALPHABET.charAt(ALPHABET.indexOf(ch) + 1);
    }
}
