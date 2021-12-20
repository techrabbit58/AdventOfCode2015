package com.example.Day08;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class Matchsticks {

    @SneakyThrows
    public static void main(String[] args) {

        var url = Matchsticks.class.getResource("/string_literals.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        var literalSizes = new ArrayList<Integer>();
        Files.lines(path).forEach(line -> countLiteralCharacters(line, literalSizes));
        var numLiteralSymbols = literalSizes.stream().reduce(Integer::sum).orElse(-1);

        var memorySizes = new ArrayList<Integer>();
        Files.lines(path).forEach(line -> countInMemoryCharacters(line, memorySizes));
        var numInMemorySymbols = memorySizes.stream().reduce(Integer::sum).orElse(-1);

        var encodedSizes = new ArrayList<Integer>();
        Files.lines(path).forEach(line -> countCharactersAfterEncoding(line, encodedSizes));
        var numSymbolsAfterEncoding = encodedSizes.stream().reduce(Integer::sum).orElse(-1);

        System.out.println("part 1 solution: " + (numLiteralSymbols - numInMemorySymbols));
        System.out.println("part 2 solution: " + (numSymbolsAfterEncoding - numLiteralSymbols));
    }

    private static void countCharactersAfterEncoding(String line, ArrayList<Integer> counts) {

        var literal = line.strip();

        var counter = 2;

        for (var ch : literal.toCharArray()) counter += (ch == '\\' || ch == '"') ? 2 : 1;

        counts.add(counter);
    }

    private static void countInMemoryCharacters(final String line, final ArrayList<Integer> counts) {

        var literal = line.strip().toLowerCase(Locale.ROOT);

        var counter = 0;
        var isEscaped = false;
        var inHexCode = -1;

        for (var ch : literal.toCharArray()) {

            switch (ch) {
                case '\\' -> {
                    if (isEscaped) {
                        isEscaped = false;
                        counter += 1;
                    } else {
                        isEscaped = true;
                    }
                }
                case '"' -> {
                    if (isEscaped) {
                        isEscaped = false;
                        counter += 1;
                    }
                }
                case 'x' -> {
                    if (!isEscaped) {
                        counter += 1;
                    } else {
                        inHexCode = 0;
                        isEscaped = false;
                    }
                }
                case '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' -> {
                    if (inHexCode == 0 || inHexCode == 1) {
                        inHexCode += 1;
                        if (inHexCode == 2) {
                            inHexCode = -1;
                            counter += 1;
                        }
                    } else {
                        counter += 1;
                    }
                }
                default -> counter += 1;
            }
        }

        counts.add(counter);
    }

    private static void countLiteralCharacters(final String line, final ArrayList<Integer> counts) {
        counts.add(line.strip().length());
    }
}
