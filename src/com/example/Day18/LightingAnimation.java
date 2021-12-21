package com.example.Day18;

import com.example.Helpers.LineByLineInput;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.Helpers.Miscellaneous.printHeading;

public class LightingAnimation {

    public static void main(String[] args) {

        printHeading("Day 18: Like a GIF For Your Yard");

        HashSet<Light> lights;

        lights = parse(new LineByLineInput("/lights_six_by_six.txt"));
        lights.forEach(System.out::println);
    }

    private static HashSet<Light> parse(LineByLineInput initialGrid) {

        var lights = new HashSet<Light>();
        AtomicInteger row = new AtomicInteger();
        initialGrid.forEach(line -> {
            for (var column = 0; column < line.length(); column++) {
                if (line.charAt(column) == Light.IS_ON) lights.add(new Light(row.get(), column));
            }
            row.addAndGet(1);
        });
        return lights;
    }
}
