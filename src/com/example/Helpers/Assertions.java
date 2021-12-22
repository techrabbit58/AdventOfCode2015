package com.example.Helpers;

public class Assertions {

    public static <T> void assertEquals(final T expected, final T actual) {
        if (!expected.equals(actual)) {
            System.err.println("expected '" + expected + "', got '" + actual + "'");
            throw new AssertionError("test failed");
        }
    }

    public static <T> void assertEquals(final T expected, final T actual, final String message) {
        if (!expected.equals(actual)) {
            System.err.println(message);
            System.err.println("expected '" + expected + "', got '" + actual + "'");
            throw new AssertionError("test failed");
        }
    }

    public static void assertTrue(final boolean predicate, final String message) {
        if (!predicate) {
            System.err.println(message);
            throw new AssertionError("test failed");
        }
    }
}
