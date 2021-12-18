package com.example.Day13;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class KnightsOfTheDinnerTable {

    @SneakyThrows
    public static void main(String[] args) {

        var url = KnightsOfTheDinnerTable.class.getResource("/happiness_units_for_test.txt");
        var path = Paths.get(Objects.requireNonNull(url).toURI());

        Set<String> persons = new TreeSet<>();
        Map<Sympathy, Integer> units = new HashMap<>();

        Files.lines(path).forEach(line -> parse(line, persons, units));

        List<LinkedList<String>> circles = new ArrayList<>();
        permute(persons.stream().toList(), circles);
        circles.forEach(c -> c.addLast(c.get(0)));

        circles.forEach(System.out::println);
        System.out.println();
        units.forEach((key, value) -> System.out.println(key + " = " + value));
    }

    private static void parse(String line, Set<String> persons, Map<Sympathy, Integer> units) {

        var parts = line.split("[\\s.]");

        var person = parts[0];
        var sign = parts[2].equals("gain") ? 1 : -1;
        var value = sign * Integer.parseInt(parts[3]);
        var seatmate = parts[parts.length - 1];

        persons.add(person);
        persons.add(seatmate);

        units.put(new Sympathy(person, seatmate), value);
    }

    private record Sympathy(String person, String seatmate) {}

    private static void permute(List<String> persons, List<LinkedList<String>> circles) {

        if (persons.size() == 0) circles.add(new LinkedList<>());
        else for (var element : persons) {

            var p = new ArrayList<LinkedList<String>>();
            permute(persons.stream().filter(e -> !element.equals(e)).toList(), p);

            for (var q : p) {
                q.addFirst(element);
                circles.add(q);
            }
        }
    }

}
