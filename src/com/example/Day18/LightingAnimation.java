package com.example.Day18;

import com.example.Helpers.Assertions;
import com.example.Helpers.LineByLineInput;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static com.example.Helpers.Miscellaneous.printHeading;

public class LightingAnimation {

    public static void main(String[] args) {

        printHeading("Day 18: Like a GIF For Your Yard");

        int result;

        // T E S T

        result = part1("/lights_six_by_six.txt", 4);
        Assertions.assertEquals(4, result);

        result = part2("/lights_six_by_six.txt", 5);
        Assertions.assertEquals(17, result);

        // S O L U T I O N

        result = part1("/lights_hundred_by_hundred.txt", 100);
        System.out.println("part 1 solution: " + result);

        result = part2("/lights_hundred_by_hundred.txt", 100);
        System.out.println("part 2 solution: " + result);
    }

    private static int part1(String fileName, int steps) {

        Set<GridElement> lights;
        var dimension = new Dimension();

        lights = parse(new LineByLineInput(fileName), dimension);
        for (var step = 0; step < steps; step++) lights = updateGrid(lights, dimension);

        return lights.size();
    }

    /**
     * For part two, in addition to the given initial grid, the four corners are always on before
     * and after update.
     */
    private static int part2(String fileName, int steps) {

        Set<GridElement> lights;
        Set<GridElement> fourCorners;
        var dimension = new Dimension();

        lights = parse(new LineByLineInput(fileName), dimension);
        fourCorners = Set.of(
                new GridElement(0, 0),
                new GridElement(0, dimension.width - 1),
                new GridElement(dimension.height - 1, 0),
                new GridElement(dimension.height - 1, dimension.width - 1));

        lights.addAll(fourCorners);
        for (var step = 0; step < steps; step++) {
            lights = updateGrid(lights, dimension);
            lights.addAll(fourCorners);
        }

        lights.addAll(fourCorners);
        return lights.size();
    }

    private static HashSet<GridElement> updateGrid(Set<GridElement> lights, Dimension dimension) {

        var newLights = new HashSet<GridElement>();

        for (var row = 0; row < dimension.height; row++) {
            for (var col = 0; col < dimension.width; col++) {
                var thisLight = new GridElement(row, col);
                if (shallBeSwitchedOn(thisLight, lights)) newLights.add(thisLight);
            }
        }

        return newLights;
    }

    private static boolean shallBeSwitchedOn(GridElement thisLight, Set<GridElement> lights) {

        var neighbourCount = countNeighbours(thisLight, lights);
        return (lights.contains(thisLight) && (neighbourCount == 2 || neighbourCount == 3)) ||
                (!lights.contains(thisLight) && neighbourCount == 3);
    }

    private static int countNeighbours(GridElement thisLight, Set<GridElement> lights) {

        var counter = 0;
        var it = thisLight.neighbourIterator();

        while (it.hasNext()) if (lights.contains(it.next())) counter++;

        return counter;
    }

    /**
     * parse input to a set representing the lights that are initially switched on
     * @param initialGrid   one input line marking ON lights with '#' and OFF lights with '.'
     * @param dimension     at end of the parse(), contains the height and width of the total lights grid
     * @return      the lights that are initially switched on in the grid
     */
    private static Set<GridElement> parse(LineByLineInput initialGrid, Dimension dimension) {

        var lights = new HashSet<GridElement>();

        AtomicInteger row = new AtomicInteger();
        initialGrid.forEach(line -> {
            dimension.width = line.length();
            for (var column = 0; column < line.length(); column++) {
                if (line.charAt(column) == GridElement.IS_ON) lights.add(new GridElement(row.get(), column));
            }
            row.addAndGet(1);
        });
        dimension.height = row.get();

        return lights;
    }
}
