package com.example.Day25;

import com.example.Helpers.Miscellaneous;

public class LetItSnow {

    public static void main(String[] args) {

        Miscellaneous.printHeading("Day 25: Let It Snow");

        var code = 20151125L;
        for (var n = 0; n < 20; n++) {

            code = nextCode(code);
            System.out.println(code);
        }
    }

    private static long nextCode(long code) {
        return (code * 252533L) % 33554393L;
    }
}
