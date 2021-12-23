package com.example.Day19;

import com.example.Helpers.Assertions;

public record Pair(String a, String b) {

    public static Pair fromString(String line) {
        var parts = line.split(" => ");
        Assertions.assertEquals(2, parts.length, "Pair.fromString: rule syntax error '" + line + "'");
        return new Pair(parts[0], parts[1]);
    }
}
