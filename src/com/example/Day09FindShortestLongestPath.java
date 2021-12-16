package com.example;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Day09FindShortestLongestPath {

    @SneakyThrows
    public static void main(String[] args) {

        var url = Day09FindShortestLongestPath.class.getResource("/distances.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        Map<String, Integer> distances = new HashMap<>();
        Set<String> locations = new HashSet<>();

        Files.lines(path).forEach(line -> parse(line, locations, distances));

        List<LinkedList<String>> trips = new ArrayList<>();
        permute(locations.stream().toList(), trips);

        var minTripSize = Integer.MAX_VALUE;
        var maxTripSize = 0;
        for (var singleTrip : trips) {

            var tripSize = 0;

            for (var i = 0; i < singleTrip.size() - 1; i++) {
                tripSize += distances.get(leg(singleTrip.get(i), singleTrip.get(i + 1)));
            }

            minTripSize = Math.min(minTripSize, tripSize);
            maxTripSize = Math.max(maxTripSize, tripSize);
        }

        System.out.println("part 1 solution = " + minTripSize);
        System.out.println("part 2 solution = " + maxTripSize);
    }

    private static void permute(List<String> items, List<LinkedList<String>> permutations) {

        if (items.size() == 0) permutations.add(new LinkedList<>());
        else for (var element : items) {

            var p = new ArrayList<LinkedList<String>>();
            permute(items.stream().filter(e -> !element.equals(e)).toList(), p);

            for (var q : p) {
                q.addFirst(element);
                permutations.add(q);
            }
        }
    }

    private static void parse(String line, Set<String> locations, Map<String, Integer> distances) {

        var parts = line.split(" ");
        var distance = Integer.parseInt(parts[4]);

        locations.add(parts[0]);
        locations.add(parts[2]);

        distances.put(leg(parts[0], parts[2]), distance);
        distances.put(leg(parts[2], parts[0]), distance);
    }

    private static String leg(String s, String t) {
        return s + " -> " + t;
    }
}
