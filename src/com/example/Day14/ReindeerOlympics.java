package com.example.Day14;

import com.example.Day13.KnightsOfTheDinnerTable;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReindeerOlympics {

    // private static final int TIME_LIMIT = 2503;
    private static final int TIME_LIMIT = 1000;

    @SneakyThrows
    public static void main(String[] args) {

        var url = KnightsOfTheDinnerTable.class.getResource("/reindeer_descriptions_for_test.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        var reindeers = new ArrayList<Reindeer>();
        Files.lines(path).forEach(line -> parse(line, reindeers));

        var maxDistance = 0;
        for (var r : reindeers) maxDistance = Math.max(maxDistance, r.distanceAfter(TIME_LIMIT));

        System.out.println("part 1 solution = " + maxDistance);

        for (var second = 0; second < TIME_LIMIT; second++) {
            reindeers.forEach(Reindeer::tick);
            var currentMaxDistance = 0;
            for (var r : reindeers) currentMaxDistance = Math.max(currentMaxDistance, r.realTimeDistance());
            for (Reindeer r : reindeers) {
                if (r.realTimeDistance() == currentMaxDistance) r.addOnePoint();
            }
        }

        var maxScore = 0;
        for (var r : reindeers) {
            System.out.println(r.name() + ", score = " + r.score());
            maxScore = Math.max(maxScore, r.score());
        }

        System.out.println("part 2 solution = " + maxScore);
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
