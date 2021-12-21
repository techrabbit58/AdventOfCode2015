package com.example.Helpers;

public class Assertions {

    public static <T> void assertEquals(final T expected, final T actual) {
        if (!expected.equals(actual)) {
            System.err.println("expected '" + expected + "', got '" + actual + "'");
            throw new AssertionError("test failed");
        }
    }
}
