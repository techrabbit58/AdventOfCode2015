package com.example.Day14;

import com.example.Day13.KnightsOfTheDinnerTable;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReindeerOlympics {

    private static final int TIME_LIMIT = 2503;

    @SneakyThrows
    public static void main(String[] args) {

        var url = KnightsOfTheDinnerTable.class.getResource("/reindeer_descriptions.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        var reindeers = new ArrayList<Reindeer>();
        Files.lines(path).forEach(line -> parse(line, reindeers));

        var maxDistance = 0;
        for (var r : reindeers) maxDistance = Math.max(maxDistance, r.distanceAfter(TIME_LIMIT));

        System.out.println("part 1 solution = " + maxDistance);
    }

    private static void parse(String line, List<Reindeer> reindeers) {

        var parts = line.split("[\\s]");

        var name = parts[0];
        var velocity = Integer.parseInt(parts[3]);
        var runTime = Integer.parseInt(parts[6]);
        var offTime = Integer.parseInt(parts[parts.length - 2]);

        var reindeer = new Reindeer(name, velocity, runTime, offTime);
        reindeers.add(reindeer);
    }
}
