package com.example.Day02;

import lombok.SneakyThrows;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class Boxes {

    @SneakyThrows
    public static void main(String[] args) {

        var app = new Boxes("/boxes.txt");
        var scanner = new Scanner(app.input);

        var totalPaper = 0L;
        var totalRibbon = 0L;

        while (scanner.hasNext()) {

            var triple = scanner.next();
            var lwh = triple.split("x");

            var length = Integer.parseInt(lwh[0]);
            var width = Integer.parseInt(lwh[1]);
            var height = Integer.parseInt(lwh[2]);

            var surface = 2 * length * width + 2 * width * height + 2 * height * length;

            var twoSmallestSides = app.twoSmallest(length, width, height);
            var slack = twoSmallestSides[0] * twoSmallestSides[1];

            totalPaper += surface + slack;
            totalRibbon += 2L * twoSmallestSides[0] + 2L * twoSmallestSides[1] + (long) length * width * height;
        }

        System.out.println("part 1 solution = " + totalPaper);
        System.out.println("part 2 solution = " + totalRibbon);
    }

    private int[] twoSmallest(int a, int b, int c) {

        int min1, min2, max;

        if (a < b) {
            min1 = a;
            max = b;
        } else {
            min1 = b;
            max = a;
        }

        min2 = Math.min(max, c);

        return new int[]{min1, min2};
    }

    private final File input;

    @SneakyThrows
    public Boxes(final String fileName) {

        input = new File(Objects.requireNonNull(getClass().getResource(fileName)).toURI());
    }
}
