package com.example;

import lombok.SneakyThrows;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class Day01Parentheses {

    @SneakyThrows
    public static void main(String[] args) {

        var app = new Day01Parentheses("/parentheses.txt");
        var scanner = new Scanner(app.input);

        var floor = 0L;
        var position = 0;
        var firstToEnterBasement = 0L;

        while (scanner.hasNextLine()) {

            var line = scanner.nextLine();

            for (var ch : line.toCharArray()) {

                position += 1;

                if (!(ch == '(' || ch == ')')) throw new RuntimeException("unexpected character: '" + ch + "'");

                if (ch == '(') floor += 1L;
                else floor -= 1L;

                if (firstToEnterBasement == 0 && floor < 0) firstToEnterBasement = position;
            }
        }

        System.out.println("part 1 solution = " + floor);
        System.out.println("part 2 solution = " + firstToEnterBasement);
    }

    private final File input;

    @SneakyThrows
    public Day01Parentheses(final String fileName) {

        input = new File(Objects.requireNonNull(getClass().getResource(fileName)).toURI());
    }
}
