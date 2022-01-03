package com.example.Helpers;

public class Validations {

    public static boolean isIntArrayDifferent(int[] one, int[] other) {

        for (var a : one) {
            for (var b : other) {
                if (a == b) return false;
            }
        }

        return true;
    }
}
