package com.example.Helpers;

public class Assertions {

    public static <T> void assertEquals(final T expected, final T actual) {
        if (!expected.equals(actual)) throw new AssertionError("part 1 test failed");
    }
}
