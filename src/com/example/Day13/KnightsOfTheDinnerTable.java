package com.example.Day13;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static com.example.Day09FindShortestLongestPath.permute;

public class KnightsOfTheDinnerTable {

    @SneakyThrows
    public static void main(String[] args) {

        var url = KnightsOfTheDinnerTable.class.getResource("/happiness_units.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        List<String> persons = new ArrayList<>();
        Map<Sympathy, Integer> units = new HashMap<>();

        Files.lines(path).forEach(line -> parse(line, persons, units));
        var circles = findAllArrangements(persons);

        var maxTotalHappinessChange = getMaxTotalHappinessChange(units, circles);
        System.out.println("part 1 solution: " + maxTotalHappinessChange);

        persons.add("$");
        circles = findAllArrangements((persons));
        maxTotalHappinessChange = getMaxTotalHappinessChange(units, circles);
        System.out.println("part 2 solution: " + maxTotalHappinessChange);
    }

    private static int getMaxTotalHappinessChange(Map<Sympathy, Integer> units, List<LinkedList<String>> circles) {

        var maxTotalHappinessChange = 0;

        for (LinkedList<String> circle : circles) {

            var numPersons = circle.size();

            var totalHappinessChange = 0;

            for (var i = 0; i < numPersons; i++) {

                var subject = circle.get(i);
                var leftSeatMate = new Sympathy(subject, circle.get((i + numPersons - 1) % numPersons));
                var rightSeatMate = new Sympathy(subject, circle.get((i + 1) % numPersons));

                totalHappinessChange += units.getOrDefault(leftSeatMate, 0)
                        + units.getOrDefault(rightSeatMate, 0);
            }

            maxTotalHappinessChange = Math.max(maxTotalHappinessChange, totalHappinessChange);
        }

        return maxTotalHappinessChange;
    }

    private static List<LinkedList<String>> findAllArrangements(List<String> persons) {

        List<LinkedList<String>> circles = new LinkedList<>();
        var head = persons.get(persons.size() - 1);
        var tail = persons.subList(0, persons.size() - 1);

        permute(tail, circles);
        circles.forEach(c -> c.addFirst(head));

        return circles;
    }

    private static void parse(String line, List<String> persons, Map<Sympathy, Integer> units) {

        var parts = line.split("[\\s.]");

        var subject = parts[0];
        var sign = parts[2].equals("gain") ? 1 : -1;
        var value = sign * Integer.parseInt(parts[3]);
        var seatmate = parts[parts.length - 1];

        if (!persons.contains(subject)) persons.add(subject);
        units.put(new Sympathy(subject, seatmate), value);
    }

    private record Sympathy(String person, String seatmate) {}
}
