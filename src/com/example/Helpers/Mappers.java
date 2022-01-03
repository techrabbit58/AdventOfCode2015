package com.example.Helpers;

import java.util.Arrays;

public class Mappers {

    public static int intArraySum(int[] ints) {
        return Arrays.stream(ints).reduce(Integer::sum).orElse(-1);
    }
}
