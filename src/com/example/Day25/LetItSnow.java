package com.example.Day25;

import com.example.Helpers.Assertions;
import com.example.Helpers.Miscellaneous;

public class LetItSnow {

    private static final long CODE_1 = 20151125L;

    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 25: Let It Snow");

        Assertions.assertEquals(12L, pairToSerial(4, 2));
        Assertions.assertEquals(15L, pairToSerial(1, 5));

        Assertions.assertEquals(32451966L, getCode(pairToSerial(4, 2)));
        Assertions.assertEquals(10071777L, getCode(pairToSerial(1, 5)));
        Assertions.assertEquals(10600672L, getCode(pairToSerial(4, 5)));
        Assertions.assertEquals(27995004L, getCode(pairToSerial(6, 6)));

        System.out.println("part 1 solution: " + getCode(pairToSerial(3010, 3019)));
        System.out.println("part 2 solution: One star gifted by Eric Wastl. Finished.");
    }

    private static long getCode(long serial) {
        var code = CODE_1;
        for (var n = 2; n <= serial; n++) code = nextCode(code);
        return code;
    }

    private static long nextCode(long code) {
        return (code * 252533L) % 33554393L;
    }

    private static long pairToSerial(long row, long col) {
        return col + ((row + col - 2) * (row + col - 1)) / 2;
    }
}
